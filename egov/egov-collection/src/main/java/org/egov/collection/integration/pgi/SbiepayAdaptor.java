package org.egov.collection.integration.pgi;

import static java.util.Objects.isNull;
import static org.egov.collection.constants.CollectionConstants.KEY_RURAL;
import static org.egov.collection.constants.CollectionConstants.KEY_URBAN;
import static org.egov.collection.constants.CollectionConstants.RURAL;
import static org.egov.collection.constants.CollectionConstants.URBAN;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.infstr.models.ServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SbiepayAdaptor implements PaymentGatewayAdaptor {
	private static final Logger LOGGER = Logger.getLogger(PayUMoneyAdaptor.class);
	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;
	@Autowired
	private RestTemplate restTemplate;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public PaymentRequest createPaymentRequest(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {
		LOGGER.debug("inside  SbiepayAdaptor createPaymentRequest");
		final DefaultPaymentRequest paymentRequest = new DefaultPaymentRequest();

		String prefix = null;

		if (URBAN.equals(receiptHeader.getRootBoundaryType())) {
			prefix = URBAN;
		} else if (RURAL.equals(receiptHeader.getRootBoundaryType())) {
			prefix = RURAL;
		}

		String MID = collectionApplicationProperties.sbiMID(prefix);
		String Collaborator_Id = collectionApplicationProperties.sbiCollaboratorId(prefix);
		String Operating_Mode = collectionApplicationProperties.sbiOperatingMode(prefix);
		String Country = collectionApplicationProperties.sbiCountry(prefix);
		String Currency = collectionApplicationProperties.sbiCurrency(prefix);

		StringBuilder returnUrl = new StringBuilder();
		String rbt = "&&rbt=" + (URBAN.equals(prefix) ? KEY_URBAN : KEY_RURAL);
		rbt = URLEncoder.encode(rbt);
		returnUrl.append(paymentServiceDetails.getCallBackurl()).append("?paymentServiceId=")
				.append(paymentServiceDetails.getId()).append(rbt);

		String Success_URL = returnUrl.toString();
		String Failure_URL = returnUrl.toString();
		String Amount = receiptHeader.getTotalAmount().toString();
		String Other_Details = "Other";
		String Order_Number = receiptHeader.getId().toString();
		
		String hashSequence = MID + "|" + Operating_Mode + "|" + Country + "|" + Currency + "|" + Amount + "|" + Other_Details + "|"
				 + Success_URL + "|" + Failure_URL + "|" + Collaborator_Id + "|" + Order_Number + "|" + "2" + "|" 
                + "NB" + "|" + "ONLINE" + "|" + "ONLINE";
		
		
		String hash = AES256Bit.encrypt(hashSequence, AES256Bit.readKeyBytes(collectionApplicationProperties.sbiMkey(prefix)));

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("EncryptTrans", hash);
		params.add("merchIdVal", collectionApplicationProperties.sbiMID(prefix));

		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https")
				.host(collectionApplicationProperties.sbiUrl(prefix))
				.path(collectionApplicationProperties.sbiPath(prefix)).build();

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

			URI redirectUri = restTemplate.postForLocation(uriComponents.toUriString(), entity);

			if (isNull(redirectUri))
				throw new RuntimeException("SBI_REDIRECT_URI_GEN_FAILED" + "Failed to generate redirect URI");
			else
				paymentRequest.setParameter(CollectionConstants.ONLINEPAYMENT_INVOKE_URL, redirectUri);

		} catch (RestClientException e) {
			LOGGER.error("Unable to retrieve redirect URI from gateway", e);
			throw new RuntimeException("Redirect URI generation failed, invalid response received from gateway");
		}

		return paymentRequest;
	}

	@Override
	public PaymentResponse parsePaymentResponse(String response, String rbt) {
		// TODO Auto-generated method stub
		return null;
	}

}
