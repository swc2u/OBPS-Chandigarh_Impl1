package org.egov.collection.cdg.finance.service;

import org.apache.log4j.Logger;
import org.egov.collection.cdg.finance.model.FinanceAuthResponce;
import org.egov.collection.cdg.finance.unit.VocherConstant;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class VocherService {
	private static final Logger LOGGER = Logger.getLogger(VocherService.class);
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;

	private String getAccessToken() {
		String accessToken = null;
		FinanceAuthResponce result=null;
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
			result=restTemplate.exchange(collectionApplicationProperties.getValue(VocherConstant.KEY_URL), HttpMethod.POST, entity, FinanceAuthResponce.class).getBody();
			accessToken=result.getAccess_token();
		} catch (Exception e) {
			LOGGER.error("getAccessToken : " + e.getMessage());
		}
		return accessToken;
	}
}
