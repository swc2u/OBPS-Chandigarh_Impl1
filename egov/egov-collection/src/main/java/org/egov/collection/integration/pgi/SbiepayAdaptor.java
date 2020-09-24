package org.egov.collection.integration.pgi;

import static org.egov.collection.constants.CollectionConstants.KEY_RURAL;
import static org.egov.collection.constants.CollectionConstants.KEY_URBAN;
import static org.egov.collection.constants.CollectionConstants.RURAL;
import static org.egov.collection.constants.CollectionConstants.URBAN;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.exception.ApplicationException;
import org.egov.infstr.models.ServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

	private static final String orderReqId = "orderReqId";
	private static final String atrn = "atrn";
	private static final String transStatus = "transStatus";
	private static final String amount = "amount";
	private static final String currency = "currency";
	private static final String paymode = "paymode";
	private static final String otherDetails = "otherDetails";
	private static final String message = "message";
	private static final String bankCode = "bankCode";
	private static final String bankRefNumber = "bankRefNumber";
	private static final String trascationdate = "trascationdate";
	private static final String Country = "Country";
	private static final String CIN = "CIN";
	private static final String SBI_SUCCESS="SUCCESS";
 	private static final String SBI_FAIL="FAIL";
 	private static final String encData="encData";
 	public static final String orderReqId_PREFIX="SBBPA";


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
		//rbt = URLEncoder.encode(rbt);
		returnUrl.append(paymentServiceDetails.getCallBackurl()).append("?paymentServiceId=")
				.append(paymentServiceDetails.getId()).append(rbt);

		String Success_URL = returnUrl.toString();
		String Failure_URL = returnUrl.toString();
		String Amount = receiptHeader.getTotalAmount().toString();
		String Other_Details = "Other";
		String Order_Number = orderReqId_PREFIX + receiptHeader.getId().toString();

		String hashSequence = MID + "|" + Operating_Mode + "|" + Country + "|" + Currency + "|" + Amount + "|"
				+ Other_Details + "|" + Success_URL + "|" + Failure_URL + "|" + Collaborator_Id + "|" + Order_Number
				+ "|" + "2" + "|" + "NB" + "|" + "ONLINE" + "|" + "ONLINE";

		System.out.println("SBI - hashSequence " + hashSequence);

		String hash = AES256Bit.encrypt(hashSequence,
				AES256Bit.readKeyBytes(collectionApplicationProperties.sbiMkey(prefix)));

		System.out.println("SBI hash " + hash);

		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https")
				.host(collectionApplicationProperties.sbiUrl(prefix))
				.path(collectionApplicationProperties.sbiPath(prefix)).build();

		paymentRequest.setParameter(CollectionConstants.ONLINEPAYMENT_INVOKE_URL, uriComponents.toUriString());
		paymentRequest.setParameter("EncryptTrans", hash);
		paymentRequest.setParameter("merchIdVal", MID);

		return paymentRequest;
	}

	@Override
	public PaymentResponse parsePaymentResponse(final String response, final String rbt) {
		LOGGER.info("response " + response + " rbt " + rbt);
		String prefix = null;
		if (KEY_URBAN.equals(rbt))
			prefix = URBAN;
		else if (KEY_RURAL.equals(rbt))
			prefix = RURAL;

		String[] keyValueStr = response.replace("{", "").replace("}", "").split(",");
		
		Map<String, String> responseM = new HashMap<String, String>(0);
		for (String pair : keyValueStr) {
			String[] entry = pair.split("=");
			responseM.put(entry[0].trim(), entry[1].trim());
		}
		
		String mkey = collectionApplicationProperties.sbiMkey(prefix);
		String decryptedParam = AES256Bit.decrypt(responseM.get(encData), AES256Bit.readKeyBytes(mkey));
		Map<String, String> responseMap = parseSBIResponce(decryptedParam);

		PaymentResponse sbiResponce = new DefaultPaymentResponse();
		sbiResponce.setAuthStatus(
				responseMap.get(transStatus).equalsIgnoreCase(SBI_SUCCESS)
						? CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS
						: responseMap.get(SBI_FAIL));

		final String receiptId=responseMap.get("orderReqId").replace(orderReqId_PREFIX, "");//SBBPA
		
		sbiResponce.setErrorDescription(responseMap.get(message));
		sbiResponce.setReceiptId(receiptId);
		sbiResponce.setTxnAmount(new BigDecimal(responseMap.get(amount)));
		sbiResponce.setTxnReferenceNo(responseMap.get(atrn));
		
		
		
		//final String receiptId = responseMap.get("orderReqId");
		final String ulbCode = ApplicationThreadLocals.getCityCode();
		final ReceiptHeader receiptHeader;
		final Query qry = entityManager.createNamedQuery(CollectionConstants.QUERY_RECEIPT_BY_ID_AND_CITYCODE);
		qry.setParameter(1, Long.valueOf(receiptId));
		qry.setParameter(2, ulbCode);
		receiptHeader = (ReceiptHeader) qry.getSingleResult();
		sbiResponce.setAdditionalInfo6(receiptHeader.getConsumerCode().replace("-", "").replace("/", ""));
		sbiResponce.setAdditionalInfo2(ulbCode);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date transactionDate = null;
		try {
			transactionDate = sdf.parse(responseMap.get("trascationdate"));
			sbiResponce.setTxnDate(transactionDate);
		} catch (java.text.ParseException e) {
			LOGGER.error("Error occured in parsing the transaction date [" + transactionDate + "]", e);
			try {
				throw new ApplicationException(".transactiondate.parse.error", e);
			} catch (ApplicationException e1) {
				LOGGER.error(e.getMessage());
			}
		}
		
		return sbiResponce;
	}

	public static Map<String, String> parseSBIResponce(String responce) {
		LOGGER.info("==========================");
		// orderReqId|atrn|transStatus|amount|currency|paymode|otherDetails|message|bankCode|bankRefNumber|trascationdate|Country|CIN||||||||||
		String[] strings = responce.split("\\|");
		LOGGER.info(responce);
		Map<String, String> map = new HashMap<String, String>();
		map.put(orderReqId, strings[0]);
		map.put(atrn, strings[1]);
		map.put(transStatus, strings[2]);
		map.put(amount, strings[3]);
		map.put(currency, strings[4]);
		map.put(paymode, strings[5]);
		map.put(otherDetails, strings[6]);
		map.put(message, strings[7]);
		map.put(bankCode, strings[8]);
		map.put(bankRefNumber, strings[9]);
		map.put(trascationdate, strings[10]);
		map.put(Country, strings[11]);
		map.put(CIN, strings[12]);
		LOGGER.info(map);
		LOGGER.info("==========================");
		return map;
	}
}
