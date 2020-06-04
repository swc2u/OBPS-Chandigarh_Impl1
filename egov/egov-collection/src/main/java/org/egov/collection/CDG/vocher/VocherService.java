package org.egov.collection.CDG.vocher;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.infstr.models.ServiceDetails;
import org.egov.infra.microservice.models.RequestInfo;
import org.egov.infra.microservice.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VocherService {
	private static final Logger LOGGER = Logger.getLogger(VocherService.class);

	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;
	
	public void createVocher(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {
		
		RestTemplate restTemplate = new RestTemplate();
		VoucherRequest voucherRequest=getVoucherRequest(paymentServiceDetails,receiptHeader);
		HttpEntity<VoucherRequest> request = new HttpEntity<>(voucherRequest);
		VoucherResponse voucherResponse=restTemplate.postForObject(collectionApplicationProperties.financeVocherUrl(),request ,VoucherResponse.class);
		
	}
	
	
	public String getAccessToken() {
		String accessToken=null;
		final List<NameValuePair> formData = new ArrayList<>(0);
		
		
		
		return accessToken;
	}
	
	public VoucherRequest getVoucherRequest(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {
		VoucherRequest voucherRequest=new VoucherRequest();
		voucherRequest.setTenantId("ch.chandigarh");
		RequestInfo requestInfo=new RequestInfo();
		requestInfo.setAuthToken(collectionApplicationProperties.financeVocherAuthToken());
		requestInfo.setCorrelationId(collectionApplicationProperties.financeVocherCorrelationId());
		voucherRequest.setRequestInfo(requestInfo);
		
		UserInfo userInfo=new UserInfo();
	//	userInfo.setName(receiptHeader.);
		
		
		return voucherRequest;
	}

}
