package org.egov.collection.integration.pgi;

import java.math.BigDecimal;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.common.entity.dcr.helper.EdcrApplicationInfo;
import org.egov.infstr.models.ServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static java.util.Objects.isNull;

/**
 * The PaymentRequestAdaptor class frames the request object for the payment
 * service.
 */
@Service
public class PayUMoneyAdaptor implements PaymentGatewayAdaptor{

	private static final Logger LOGGER = Logger.getLogger(PayUMoneyAdaptor.class);
	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;
	
	@Autowired
	private RestTemplate restTemplate;
	  
	@Override
	public PaymentRequest createPaymentRequest(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {
		LOGGER.debug("inside  PayUMoneyAdaptor createPaymentRequest");
		final DefaultPaymentRequest paymentRequest = new DefaultPaymentRequest();
		
		
		
		
		//String totalAmt = (receiptHeader.getTotalAmount().compareTo(new BigDecimal(50000))>0)?"50000.00":String.valueOf(receiptHeader.getTotalAmount());
		
		String totalAmt=receiptHeader.getTotalAmount().toString();
		
		String hashSequence = "key|txnid|amount|productinfo|firstname|email|||||||||||";
        hashSequence = hashSequence.concat(collectionApplicationProperties.payuMerchantSalt());
        hashSequence = hashSequence.replace("key", collectionApplicationProperties.payuMerchantkey());
        hashSequence = hashSequence.replace("txnid", receiptHeader.getId().toString());
        hashSequence = hashSequence.replace("amount", new BigDecimal(totalAmt).setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString());
        hashSequence = hashSequence.replace("productinfo", receiptHeader.getDisplayMsg());
        hashSequence = hashSequence.replace("firstname", receiptHeader.getPayeeName());
        hashSequence = hashSequence.replace("email", Objects.toString(receiptHeader.getPayeeEmail(), ""));
        
        String hash = hashCal(hashSequence);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", collectionApplicationProperties.payuMerchantkey());
        params.add("txnid", receiptHeader.getId().toString());
        params.add("amount",new BigDecimal(totalAmt).setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString());
        params.add("productinfo", receiptHeader.getDisplayMsg());
        params.add("firstname", receiptHeader.getPayeeName());
        params.add("email", Objects.toString(receiptHeader.getPayeeEmail(), ""));
       // params.add("phone", receiptHeader.get);
        params.add("surl", paymentServiceDetails.getCallBackurl());
        params.add("furl", paymentServiceDetails.getCallBackurl());
        params.add("hash", hash);
        
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(collectionApplicationProperties.payuUrl()).path
                (collectionApplicationProperties.payuPathPay()).build();
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

            URI redirectUri = restTemplate.postForLocation(
                    uriComponents.toUriString(), entity
            );
            

            if(isNull(redirectUri))
                throw new RuntimeException("PAYU_REDIRECT_URI_GEN_FAILED"+ "Failed to generate redirect URI");
            else
            	paymentRequest.setParameter(CollectionConstants.ONLINEPAYMENT_INVOKE_URL,redirectUri);

        } catch (RestClientException e){
            LOGGER.error("Unable to retrieve redirect URI from gateway", e);
            throw new RuntimeException( "Redirect URI generation failed, invalid response received from gateway");
        }
        
		return paymentRequest;
	}
	
	public PaymentRequest createPaymentRequestNew(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {
		LOGGER.debug("inside  PayUMoneyAdaptor createPaymentRequest");
		final DefaultPaymentRequest paymentRequest = new DefaultPaymentRequest();
		
		//String totalAmt = (receiptHeader.getTotalAmount().compareTo(new BigDecimal(50000))>0)?"50000.00":String.valueOf(receiptHeader.getTotalAmount());
		
		String totalAmt=receiptHeader.getTotalAmount().toString();
		
		String hashSequence = "key|txnid|amount|productinfo|firstname|email|||||||||||";
        hashSequence = hashSequence.concat(collectionApplicationProperties.payuMerchantSalt());
        hashSequence = hashSequence.replace("key", collectionApplicationProperties.payuMerchantkey());
        hashSequence = hashSequence.replace("txnid", receiptHeader.getId().toString());
        hashSequence = hashSequence.replace("amount", new BigDecimal(totalAmt).setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString());
        hashSequence = hashSequence.replace("productinfo", receiptHeader.getDisplayMsg());
        hashSequence = hashSequence.replace("firstname", receiptHeader.getPayeeName());
        hashSequence = hashSequence.replace("email", Objects.toString(receiptHeader.getPayeeEmail(), ""));
        
        String hash = hashCal(hashSequence);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", collectionApplicationProperties.payuMerchantkey());
        params.add("txnid", receiptHeader.getId().toString());
        params.add("amount",new BigDecimal(totalAmt).setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString());
        params.add("productinfo", receiptHeader.getDisplayMsg());
        params.add("firstname", receiptHeader.getPayeeName());
        params.add("email", Objects.toString(receiptHeader.getPayeeEmail(), ""));
       // params.add("phone", receiptHeader.get);
        params.add("surl", paymentServiceDetails.getCallBackurl());
        params.add("furl", paymentServiceDetails.getCallBackurl());
        params.add("hash", hash);
        
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(collectionApplicationProperties.payuUrl()).path
                (collectionApplicationProperties.payuPathPay()).build();
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

            URI redirectUri = restTemplate.postForLocation(
                    uriComponents.toUriString(), entity
            );
            
            String url=restTemplate.exchange(
            		uriComponents.toUriString(), HttpMethod.POST, entity, String.class).getBody();

            if(isNull(url))
                throw new RuntimeException("PAYU_REDIRECT_URI_GEN_FAILED"+ "Failed to generate redirect URI");
            else
            	paymentRequest.setParameter(CollectionConstants.ONLINEPAYMENT_INVOKE_URL,url );

        } catch (RestClientException e){
            LOGGER.error("Unable to retrieve redirect URI from gateway", e);
            throw new RuntimeException( "Redirect URI generation failed, invalid response received from gateway");
        }
        
		return paymentRequest;
	}
	

	@Override
	public PaymentResponse parsePaymentResponse(String response) {
		LOGGER.debug("inside  PayUMoneyAdaptor parsePaymentResponse");
		return null;
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
                if (hex.length() == 1) hexString.append("0");
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException nsae) {
            LOGGER.error("Error occurred while generating hash "+str, nsae);
            throw new RuntimeException("CHECKSUM_GEN_FAILED"+"Hash generation failed, gateway redirect URI " +
                    "cannot be generated");
        }

        return hexString.toString();
    }
	
}
