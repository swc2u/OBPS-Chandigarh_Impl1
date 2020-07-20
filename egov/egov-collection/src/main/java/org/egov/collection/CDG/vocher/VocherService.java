package org.egov.collection.CDG.vocher;

import org.apache.log4j.Logger;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.infra.microservice.models.RequestInfo;
import org.egov.infra.microservice.models.UserInfo;
import org.egov.infstr.models.ServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Service
public class VocherService {
	private static final Logger LOGGER = Logger.getLogger(VocherService.class);

	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;
	@Autowired
	private RestTemplate restTemplate;
	
	public void createVocher(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {
		
		RestTemplate restTemplate = new RestTemplate();
		VoucherRequest voucherRequest=getVoucherRequest(paymentServiceDetails,receiptHeader);
		HttpEntity<VoucherRequest> request = new HttpEntity<>(voucherRequest);
		VoucherResponse voucherResponse=restTemplate.postForObject(collectionApplicationProperties.financeVocherUrl(),request ,VoucherResponse.class);
		
	}
	
	
	public String getAccessToken() {
		String accessToken=null;
		try {
			
		}catch (Exception e) {
			LOGGER.error("getAccessToken : "+e.getMessage());
		}
		
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
