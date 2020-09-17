package org.egov.collection.integration.pgi;

import static java.util.Objects.isNull;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.egov.collection.constants.CollectionConstants.KEY_RURAL;
import static org.egov.collection.constants.CollectionConstants.KEY_URBAN;
import static org.egov.collection.constants.CollectionConstants.RURAL;
import static org.egov.collection.constants.CollectionConstants.URBAN;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.OnlinePayment;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.exception.ApplicationException;
import org.egov.infstr.models.ServiceDetails;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The PaymentRequestAdaptor class frames the request object for the payment
 * service.
 */
@Service
public class PayUMoneyAdaptor implements PaymentGatewayAdaptor {

	private static final Logger LOGGER = Logger.getLogger(PayUMoneyAdaptor.class);
	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;

	@Autowired
	private RestTemplate restTemplate;
	@PersistenceContext
	private EntityManager entityManager;
	//@Autowired
	private ObjectMapper objectMapper=new ObjectMapper();

	@Override
	public PaymentRequest createPaymentRequest(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {
		LOGGER.debug("inside  PayUMoneyAdaptor createPaymentRequest");
		final DefaultPaymentRequest paymentRequest = new DefaultPaymentRequest();

		String prefix = null;

		if (URBAN.equals(receiptHeader.getRootBoundaryType())) {
			prefix = URBAN;
		} else if (RURAL.equals(receiptHeader.getRootBoundaryType())) {
			prefix = RURAL;
		}

		// String totalAmt = (receiptHeader.getTotalAmount().compareTo(new
		// BigDecimal(50000))>0)?"50000.00":String.valueOf(receiptHeader.getTotalAmount());

		String totalAmt = receiptHeader.getTotalAmount().toString();

		String hashSequence = "key|txnid|amount|productinfo|firstname|email|||||||||||";
		hashSequence = hashSequence.concat(collectionApplicationProperties.payuMerchantSalt(prefix));
		hashSequence = hashSequence.replace("key", collectionApplicationProperties.payuMerchantkey(prefix));
		hashSequence = hashSequence.replace("txnid", receiptHeader.getId().toString());
		hashSequence = hashSequence.replace("amount", new BigDecimal(totalAmt)
				.setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString());
		hashSequence = hashSequence.replace("productinfo", receiptHeader.getDisplayMsg());
		hashSequence = hashSequence.replace("firstname", receiptHeader.getPayeeName());
		hashSequence = hashSequence.replace("email", Objects.toString(receiptHeader.getPayeeEmail(), ""));

		String hash = hashCal(hashSequence);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("key", collectionApplicationProperties.payuMerchantkey(prefix));
		params.add("txnid", receiptHeader.getId().toString());
		params.add("amount", new BigDecimal(totalAmt)
				.setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString());
		params.add("productinfo", receiptHeader.getDisplayMsg());
		params.add("firstname", receiptHeader.getPayeeName());
		params.add("email", Objects.toString(receiptHeader.getPayeeEmail(), ""));
		// params.add("phone", receiptHeader.get);
		StringBuilder returnUrl = new StringBuilder();
		String rbt = "&&rbt=" + (URBAN.equals(prefix) ? KEY_URBAN : KEY_RURAL);
		rbt = URLEncoder.encode(rbt);
		returnUrl.append(paymentServiceDetails.getCallBackurl()).append("?paymentServiceId=")
				.append(paymentServiceDetails.getId()).append(rbt);

//      params.add("surl", paymentServiceDetails.getCallBackurl());
//      params.add("furl", paymentServiceDetails.getCallBackurl());
//
		params.add("surl", returnUrl.toString());
		params.add("furl", returnUrl.toString());
		params.add("hash", hash);

		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https")
				.host(collectionApplicationProperties.payuUrl(prefix))
				.path(collectionApplicationProperties.payuPathPay(prefix)).build();

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

			URI redirectUri = restTemplate.postForLocation(uriComponents.toUriString(), entity);

			if (isNull(redirectUri))
				throw new RuntimeException("PAYU_REDIRECT_URI_GEN_FAILED" + "Failed to generate redirect URI");
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
		LOGGER.debug("inside  PayUMoneyAdaptor parsePaymentResponse");
		LOGGER.debug("Response message from PayUMoneyAdaptor Payment gateway: " + response);
		PaymentResponse payuResponce = new DefaultPaymentResponse();

		String prefix = null;
		if (KEY_URBAN.equals(rbt))
			prefix = URBAN;
		else if (KEY_RURAL.equals(rbt))
			prefix = RURAL;
		
		String[] keyValueStr = response.replace("{", "").replace("}", "").split(",");
		
		Map<String, String> responseMap = new HashMap<String, String>(0);
		for (String pair : keyValueStr) {
			String[] entry = pair.split("=");
			responseMap.put(entry[0].trim(), entry[1].trim());
		}
		
		payuResponce.setAuthStatus(responseMap.get(CollectionConstants.PAYU_STATUS).equalsIgnoreCase(CollectionConstants.PAYU_SUCCESS)
					? CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS
					: responseMap.get(CollectionConstants.PAYU_FAILURE));
		
		payuResponce.setErrorDescription(responseMap.get("error_Message"));
		payuResponce.setReceiptId(responseMap.get("txnid"));
		payuResponce.setTxnAmount(new BigDecimal(responseMap.get("net_amount_debit")));
		payuResponce.setTxnReferenceNo(responseMap.get("mihpayid"));
		
		final String receiptId = responseMap.get("txnid");
		final String ulbCode = ApplicationThreadLocals.getCityCode();
		final ReceiptHeader receiptHeader;
		final Query qry = entityManager.createNamedQuery(CollectionConstants.QUERY_RECEIPT_BY_ID_AND_CITYCODE);
		qry.setParameter(1, Long.valueOf(receiptId));
		qry.setParameter(2, ulbCode);
		receiptHeader = (ReceiptHeader) qry.getSingleResult();
		payuResponce.setAdditionalInfo6(receiptHeader.getConsumerCode().replace("-", "").replace("/", ""));
		payuResponce.setAdditionalInfo2(ulbCode);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date transactionDate = null;
		try {
			transactionDate = sdf.parse(responseMap.get("addedon"));
			payuResponce.setTxnDate(transactionDate);
		} catch (java.text.ParseException e) {
			LOGGER.error("Error occured in parsing the transaction date [" + transactionDate + "]", e);
			try {
				throw new ApplicationException(".transactiondate.parse.error", e);
			} catch (ApplicationException e1) {
				LOGGER.error(e.getMessage());
			}
		}

		return payuResponce;
	}
	
	
	@Transactional
	public PaymentResponse createOfflinePaymentRequest(final OnlinePayment onlinePayment) {
		
		PaymentResponse payuResponce = new DefaultPaymentResponse();
		String txnRef = onlinePayment.getReceiptHeader().getId().toString();
		String prefix = onlinePayment.getReceiptHeader().getRootBoundaryType();
		String merchantKey=collectionApplicationProperties.payuMerchantkey(prefix);
		String merchantSalt=collectionApplicationProperties.payuMerchantSalt(prefix);
        String hash = hashCal(merchantKey + "|"
                + "verify_payment" + "|"
                + txnRef + "|"
                + merchantSalt);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("form", "2");
        
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(collectionApplicationProperties.payuUrlStatus(prefix)).path
                (collectionApplicationProperties.payuPathStatus(prefix)).queryParams(queryParams).build();
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("key", merchantKey);
            params.add("command", "verify_payment");
            params.add("hash", hash);
            params.add("var1", txnRef);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(uriComponents.toUriString(), entity, String.class);
            String responceBody=response.getBody();
            
            String[] keyValueStr = responceBody.replace("{", "").replace("}", "").split(",");
    		Map<String, String> responseMap = new HashMap<String, String>(0);
    		for (String pair : keyValueStr) {
    			pair=pair.replace("\"", "");
    			String[] entry=null;
    			if(pair.contains("addedon")) {
    				entry=pair.split(":", 2);
    			}else {
    				entry= pair.split(":");
    			}
    			if(entry.length>=2)
    				responseMap.put(entry[0].trim(), entry[1].trim());
    		}

            LOGGER.info(response.getBody());
            payuResponce.setAuthStatus(responseMap.get(CollectionConstants.PAYU_STATUS).equalsIgnoreCase(CollectionConstants.PAYU_SUCCESS)
					? CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS
					: responseMap.get(CollectionConstants.PAYU_FAILURE));
		
		payuResponce.setErrorDescription(responseMap.get("error_Message"));
		payuResponce.setReceiptId(responseMap.get("txnid"));
		payuResponce.setTxnAmount(new BigDecimal(responseMap.get("net_amount_debit")));
		payuResponce.setTxnReferenceNo(responseMap.get("mihpayid"));
		
		final String receiptId = responseMap.get("txnid");
		final String ulbCode = ApplicationThreadLocals.getCityCode();
		final ReceiptHeader receiptHeader;
		final Query qry = entityManager.createNamedQuery(CollectionConstants.QUERY_RECEIPT_BY_ID_AND_CITYCODE);
		qry.setParameter(1, Long.valueOf(receiptId));
		qry.setParameter(2, ulbCode);
		receiptHeader = (ReceiptHeader) qry.getSingleResult();
		payuResponce.setAdditionalInfo6(receiptHeader.getConsumerCode().replace("-", "").replace("/", ""));
		payuResponce.setAdditionalInfo2(ulbCode);
		payuResponce.setBankId(new BigDecimal(responseMap.get("bank_ref_num")).intValue());
		payuResponce.setBankReferenceNo(responseMap.get("card_no"));
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date transactionDate = null;
		try {
			transactionDate = sdf.parse(responseMap.get("addedon"));
			payuResponce.setTxnDate(transactionDate);
		} catch (java.text.ParseException e) {
			LOGGER.error("Error occured in parsing the transaction date [" + transactionDate + "]", e);
			try {
				throw new ApplicationException(".transactiondate.parse.error", e);
			} catch (ApplicationException e1) {
				LOGGER.error(e.getMessage());
			}
		}
            

        }catch (RestClientException e){
            LOGGER.error("Unable to fetch status from payment gateway for txnid: "+ txnRef, e);
            throw new RuntimeException("Error occurred while fetching status from payment gateway");
        }
        
		return payuResponce;
	}

	
	private String hashCal(String str) {
		byte[] hashSequence = str.getBytes();
		StringBuilder hexString = new StringBuilder();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
			algorithm.reset();
			algorithm.update(hashSequence);
			byte messageDigest[] = algorithm.digest();

			for (byte aMessageDigest : messageDigest) {
				String hex = Integer.toHexString(0xFF & aMessageDigest);
				if (hex.length() == 1)
					hexString.append("0");
				hexString.append(hex);
			}

		} catch (NoSuchAlgorithmException nsae) {
			LOGGER.error("Error occurred while generating hash " + str, nsae);
			throw new RuntimeException(
					"CHECKSUM_GEN_FAILED" + "Hash generation failed, gateway redirect URI " + "cannot be generated");
		}

		return hexString.toString();
	}

}
