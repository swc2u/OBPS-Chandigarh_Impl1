package org.egov.collection.cdg.finance.service;

import static org.egov.collection.constants.CollectionConstants.KEY_RURAL;
import static org.egov.collection.constants.CollectionConstants.KEY_URBAN;
import static org.egov.collection.constants.CollectionConstants.RURAL;
import static org.egov.collection.constants.CollectionConstants.URBAN;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.egov.collection.cdg.finance.model.FinanceAuthResponce;
import org.egov.collection.cdg.finance.model.Function;
import org.egov.collection.cdg.finance.model.Functionary;
import org.egov.collection.cdg.finance.model.Fund;
import org.egov.collection.cdg.finance.model.Ledgers;
import org.egov.collection.cdg.finance.model.RequestInfo;
import org.egov.collection.cdg.finance.model.Scheme;
import org.egov.collection.cdg.finance.model.Voucher;
import org.egov.collection.cdg.finance.model.VoucherCreateRequest;
import org.egov.collection.cdg.finance.model.VoucherCreateResponse;
import org.egov.collection.cdg.finance.unit.VocherConstant;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.infstr.models.ServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class VocherService {
	private static final Logger LOGGER = Logger.getLogger(VocherService.class);
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;

	private FinanceAuthResponce getAccessToken() {
		FinanceAuthResponce result = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
			headers.add("Authorization", collectionApplicationProperties.getValue(VocherConstant.KEY_AUTHORIZATION));
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("grant_type", collectionApplicationProperties.getValue(VocherConstant.KEY_GRANT_TYPE));
			params.add("scope", collectionApplicationProperties.getValue(VocherConstant.KEY_SCOPE));
			params.add("username", collectionApplicationProperties.getValue(VocherConstant.KEY_USERNAME));
			params.add("password", collectionApplicationProperties.getValue(VocherConstant.KEY_PASSWORD));
			params.add("tenantId", collectionApplicationProperties.getValue(VocherConstant.KEY_TENANTID));
			params.add("userType", collectionApplicationProperties.getValue(VocherConstant.KEY_USERTYPE));
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
			result = restTemplate.exchange(collectionApplicationProperties.getValue(VocherConstant.KEY_URL),
					HttpMethod.POST, entity, FinanceAuthResponce.class).getBody();
		} catch (Exception e) {
			LOGGER.error("getAccessToken : " + e.getMessage());
		}
		return result;
	}

	public boolean isVocherApplicable(final String rbt) {
		boolean flage = false;
		if (KEY_RURAL.equals(rbt))
			flage = true;
		return flage;
	}

	private void createVocher(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {
		FinanceAuthResponce financeAuthResponce=getAccessToken();
		VoucherCreateRequest voucherCreateRequest=new VoucherCreateRequest();
		RequestInfo requestInfo=new RequestInfo();
		requestInfo.setAuthToken(financeAuthResponce.getAccess_token());
		voucherCreateRequest.setRequestInfo(requestInfo);
		voucherCreateRequest.setTenantId("ch.chandigarh");
		List<Voucher> vouchers=new ArrayList<Voucher>();
		
		Voucher voucher=new Voucher();
		voucher.setName(VocherConstant.VOUCHER_NAME);
		voucher.setType(VocherConstant.VOUCHER_TYPE);
		voucher.setDescription(VocherConstant.VOUCHER_DESCRIPTION);
		voucher.setVoucherDate(new Date(System.currentTimeMillis()));
		voucher.setModuleId(VocherConstant.VOUCHER_MODEL_ID);
		voucher.setDepartment(VocherConstant.VOUCHER_DEPT);
		voucher.setSource("/services/collection/receipts/receipt-viewReceipts.action?selectedReceipts="+receiptHeader.getId());
		voucher.setTenantId(collectionApplicationProperties.getValue(VocherConstant.KEY_TENANTID));
		voucher.setReferenceDocument(receiptHeader.getReceiptnumber());
		voucher.setServiceName(VocherConstant.VOUCHER_SERVICE_NAME);
		
		Fund fund=new Fund();
		fund.setCode(paymentServiceDetails.getFund().getCode());
		voucher.setFund(fund);
		
		Function function=new Function();
		function.setCode(paymentServiceDetails.getFunctionary().getCode().toString());
		voucher.setFunction(function);
		
		Scheme scheme=new Scheme();
		voucher.setScheme(scheme);
		
		Functionary functionary=new Functionary();
		voucher.setFunctionary(functionary);
		
		//ledgers 
		ArrayList<Ledgers> ledgers=new ArrayList<Ledgers>();
		//credit ledger
		Ledgers ledgers1=new Ledgers();
		ledgers1.setGlcode("1601004");
		ledgers1.setCreditAmount(10L);
		Function function2=new Function();
		function2.setCode("000300");
		ledgers1.setFunction(function2);
		
		ledgers.add(ledgers1);
		
		//add other ledger for debit
		Ledgers ledgers2=new Ledgers();
		
		ledgers2.setDebitAmount(receiptHeader.getTotalAmount().longValue());
		Function function3=new Function();
		
		ledgers2.setFunction(function3);
		
		ledgers.add(ledgers2);
		
		voucher.setLedgers(ledgers);
		vouchers.add(voucher);
		voucherCreateRequest.setVouchers(vouchers);
		HttpEntity<VoucherCreateRequest> request = new HttpEntity<>(voucherCreateRequest);
		VoucherCreateResponse result=restTemplate.exchange(collectionApplicationProperties.getValue(VocherConstant.VOUCHER_SERVICE_URL), HttpMethod.POST, request, VoucherCreateResponse.class).getBody();
		
	}
	
	public void processVocher(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader,final String rbt) {
		if(isVocherApplicable(rbt))
			createVocher(paymentServiceDetails, receiptHeader);
			
	}
}
