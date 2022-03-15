/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */
package org.egov.collection.integration.pgi;

import static org.egov.collection.constants.CollectionConstants.RURAL;
import static org.egov.collection.constants.CollectionConstants.URBAN;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.OnlinePayment;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.collection.integration.models.ResponseAtomReconcilation;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.exception.ApplicationException;
import org.egov.infra.utils.DateUtils;
import org.egov.infstr.models.ServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import com.paytm.pg.merchant.PaytmChecksum;

/**
 * The PaymentRequestAdaptor class frames the request object for the payment
 * service.
 */
@Service
public class PaytmAdaptor implements PaymentGatewayAdaptor {

	private static final Logger LOGGER = Logger.getLogger(PaytmAdaptor.class);

	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private RestTemplate restTemplate;
	/**
	 * This method invokes APIs to frame request object for the payment service
	 * passed as parameter
	 *
	 * @param serviceDetails
	 * @param receiptHeader
	 * @return
	 */
	public static final String PAYTM_PREFIX = "PAYTM1";
	public static final String CALLBACK_URL = "CALLBACK_URL";
	public static final String CHANNEL_ID = "CHANNEL_ID";
	public static final String CHECKSUMHASH = "CHECKSUMHASH";
	public static final String CUST_ID = "CUST_ID";
	public static final String EMAIL = "EMAIL";
	public static final String INDUSTRY_TYPE_ID = "INDUSTRY_TYPE_ID";
	public static final String MID = "MID";
	public static final String MOBILE_NO = "MOBILE_NO";
	public static final String ORDER_ID = "ORDER_ID";
	public static final String TXN_AMOUNT = "TXN_AMOUNT";
	public static final String WEBSITE = "WEBSITE";
	public static final String RESPCODE = "RESPCODE";//RESPCODE
	public static final String RESPMSG = "RESPMSG";
	public static final String BANKTXNID = "BANKTXNID";
	public static final String TXNAMOUNT = "TXNAMOUNT";
	public static final String TXNID = "TXNID";
	public static final String TXNDATE = "TXNDATE";
	//public static final String MERC_UNI_REF="Merc_Uni_Ref";
	public static final String MERC_UNI_REF="MERC_UNQ_REF";

	@Override
	public PaymentRequest createPaymentRequest(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("inside  paytm createPaymentRequest");
		}
		String prefix = null;

		if (URBAN.equals(receiptHeader.getRootBoundaryType())) {
			prefix = URBAN;
		} else if (RURAL.equals(receiptHeader.getRootBoundaryType())) {
			prefix = RURAL;
		}

		TreeMap<String, String> parameters = new TreeMap<>();

		StringBuilder returnUrl = new StringBuilder();
		// String rbt = "&&rbt=" + (URBAN.equals(prefix) ? KEY_URBAN : KEY_RURAL);
		// rbt = URLEncoder.encode(rbt);
		returnUrl.append(paymentServiceDetails.getCallBackurl()).append("?paymentServiceId=")
				.append(paymentServiceDetails.getId());// .append(rbt);

		parameters.put(MID, collectionApplicationProperties.paytmValue(prefix + ".paytm.mid"));
		parameters.put(CHANNEL_ID, collectionApplicationProperties.paytmValue(prefix + ".paytm.channelId"));
		parameters.put(INDUSTRY_TYPE_ID, collectionApplicationProperties.paytmValue(prefix + ".paytm.industryTypeId"));
		parameters.put(WEBSITE, collectionApplicationProperties.paytmValue(prefix + ".paytm.website"));
		parameters.put(CALLBACK_URL, returnUrl.toString());

		parameters.put(MOBILE_NO, "88888888");
		parameters.put(EMAIL, receiptHeader.getPayeeEmail());
		parameters.put(ORDER_ID, PAYTM_PREFIX + receiptHeader.getId().toString());
		parameters.put(TXN_AMOUNT, new BigDecimal(receiptHeader.getTotalAmount() + "")
				.setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString());
		parameters.put(CUST_ID, receiptHeader.getConsumerCode());
		parameters.put(MERC_UNI_REF, "obps");
		String checkSum = null;
		try {
			checkSum = getCheckSum(parameters,
					collectionApplicationProperties.paytmValue(prefix + ".paytm.merchantKey"));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
		parameters.put(CHECKSUMHASH, checkSum);

		final DefaultPaymentRequest paymentRequest = new DefaultPaymentRequest();

		paymentRequest.setParameter(CollectionConstants.ONLINEPAYMENT_INVOKE_URL,
				collectionApplicationProperties.paytmValue(prefix + ".paytm.url"));

		for (Entry<String, String> parameter : parameters.entrySet()) {
			paymentRequest.setParameter(parameter.getKey(), parameter.getValue());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Second paymentRequest: " + paymentRequest.getRequestParameters());
		}
		return paymentRequest;
	}

	@Override
	public PaymentResponse parsePaymentResponse(final String response, final String rbt) {
		LOGGER.debug("inside  paytm createPaymentRequest");
		String[] keyValueStr = response.replace("{", "").replace("}", "").split(",");
		PaymentResponse paytmResponse = new DefaultPaymentResponse();
		TreeMap<String, String> responseMap1 = new TreeMap<String, String>();
		String paytmChecksum = "";
		String paymentServiceId = "";
		for (String pair : keyValueStr) {
			String[] entry = pair.split("=", 2);
			// responseMap1.put(entry[0].trim(), entry[1].trim());
			if ("CHECKSUMHASH".equalsIgnoreCase(entry[0].trim())) {
				paytmChecksum = entry[1].trim();
			} else if ("paymentServiceId".equalsIgnoreCase(entry[0].trim())) {
				paymentServiceId = entry[1].trim();
			} else {
				responseMap1.put(entry[0].trim(), entry[1].trim());
			}
		}

		String prefix = prefix = RURAL;
//		if (KEY_URBAN.equals(rbt))
//			prefix = URBAN;
//		else if (KEY_RURAL.equals(rbt))
//			prefix = RURAL;

		// paytmChecksum = responseMap1.get(CHECKSUMHASH);
		boolean isValideChecksum = false;
		LOGGER.info("RESULT : " + responseMap1.toString());
		String result = "";
//		TreeMap<String, String> checkSumMap=new TreeMap<String, String>();
//		checkSumMap.put("mid", collectionApplicationProperties.paytmValue(prefix + ".paytm.mid"));
//		checkSumMap.put("orderId", responseMap1.get("ORDERID"));
		try {
			isValideChecksum = validateCheckSum(responseMap1, paytmChecksum,
					collectionApplicationProperties.paytmValue(prefix + ".paytm.merchantKey"));
			if (isValideChecksum && responseMap1.containsKey(RESPCODE)) {
				if (responseMap1.get(RESPCODE).equals("01")) {
					result = CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS;
				} else {
					result = responseMap1.get(RESPCODE);
					paytmResponse.setErrorDescription(responseMap1.get(RESPMSG));
				}
			} else {
				result = "Checksum mismatched";
				if (responseMap1.get(RESPCODE).equals("01")) {
					result = CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS;
				} else {
					result = responseMap1.get(RESPCODE);
					paytmResponse.setErrorDescription(responseMap1.get(RESPMSG));
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		paytmResponse.setAuthStatus(result);
		paytmResponse.setReceiptId(responseMap1.get("ORDERID").replace(PAYTM_PREFIX, ""));
		// paytmResponse.setReceiptId(responseMap1.get(BANKTXNID));
		paytmResponse.setTxnAmount(new BigDecimal(responseMap1.get(TXNAMOUNT)));
		paytmResponse.setTxnReferenceNo(responseMap1.get(TXNID));

		final String receiptId = responseMap1.get("ORDERID").replace(PAYTM_PREFIX, "");
		final String ulbCode = ApplicationThreadLocals.getCityCode();
		final ReceiptHeader receiptHeader;
		final Query qry = entityManager.createNamedQuery(CollectionConstants.QUERY_RECEIPT_BY_ID_AND_CITYCODE);
		qry.setParameter(1, Long.valueOf(receiptId));
		qry.setParameter(2, ulbCode);
		receiptHeader = (ReceiptHeader) qry.getSingleResult();
		paytmResponse.setAdditionalInfo6(receiptHeader.getConsumerCode().replace("-", "").replace("/", ""));
		paytmResponse.setAdditionalInfo2(ulbCode);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.getDefault());
		Date transactionDate = null;
		try {
			if (responseMap1.get(TXNDATE) != null) {
				transactionDate = sdf.parse(responseMap1.get(TXNDATE));
				paytmResponse.setTxnDate(transactionDate);
			}
		} catch (ParseException e) {
			LOGGER.error("Error occured in parsing the transaction date [" + transactionDate + "]", e);
			try {
				throw new ApplicationException(".transactiondate.parse.error", e);
			} catch (ApplicationException e1) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
			}
		}
		return paytmResponse;
	}

	@Transactional
	public PaymentResponse createOfflinePaymentRequest(final OnlinePayment onlinePayment) {
		LOGGER.debug("Inside paytm createOfflinePaymentRequest");

		//String prefix = onlinePayment.getReceiptHeader().getRootBoundaryType();
		String prefix = RURAL;
		HashMap<String, String> parameters = new HashMap<>();
		ReceiptHeader receiptHeader = onlinePayment.getReceiptHeader();
		ServiceDetails paymentServiceDetails = onlinePayment.getService();
		StringBuilder returnUrl = new StringBuilder();
		// String rbt = "&&rbt=" + (URBAN.equals(prefix) ? KEY_URBAN : KEY_RURAL);
		// rbt = URLEncoder.encode(rbt);
		returnUrl.append(paymentServiceDetails.getCallBackurl()).append("?paymentServiceId=")
				.append(paymentServiceDetails.getId());// .append(rbt);

		parameters.put(MID, collectionApplicationProperties.paytmValue(prefix + ".paytm.mid"));
		parameters.put(CHANNEL_ID, collectionApplicationProperties.paytmValue(prefix + ".paytm.channelId"));
		parameters.put(INDUSTRY_TYPE_ID, collectionApplicationProperties.paytmValue(prefix + ".paytm.industryTypeId"));
		parameters.put(WEBSITE, collectionApplicationProperties.paytmValue(prefix + ".paytm.website"));
		parameters.put(CALLBACK_URL, returnUrl.toString());

		parameters.put(MOBILE_NO, "88888888");
		parameters.put(EMAIL, receiptHeader.getPayeeEmail());
		parameters.put(ORDER_ID, PAYTM_PREFIX + receiptHeader.getId().toString());
		parameters.put(TXN_AMOUNT, new BigDecimal(receiptHeader.getTotalAmount() + "")
				.setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString());
		parameters.put(CUST_ID, receiptHeader.getConsumerCode());
		String checkSum = null;
//		try {
//			checkSum = getCheckSum(parameters,
//					collectionApplicationProperties.paytmValue(prefix + ".paytm.merchantKey"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error(e);
//		}
		parameters.put(CHECKSUMHASH, checkSum);
		PaymentResponse paytmResponse = new DefaultPaymentResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			String body = objectMapper.writeValueAsString(parameters);
			System.out.println("request: " + body);
			org.springframework.http.HttpEntity<String> request = new org.springframework.http.HttpEntity<String>(body,
					headers);
			String response = restTemplate.postForObject(
					collectionApplicationProperties.paytmValue(prefix + ".paytm.reconcile.url"), request, String.class);
			LOGGER.info("paytm response: " + response);

			// Process responce
			String[] keyValueStr = response.replace("{", "").replace("}", "").split(",");
			// PaymentResponse paytmResponse = new DefaultPaymentResponse();
			HashMap<String, String> responseMap = new HashMap<>();
			String paytmChecksum = "";
			String paymentServiceId = "";
			for (String pair : keyValueStr) {
				String[] entry = pair.split(":");
				if ("CHECKSUMHASH".equalsIgnoreCase(entry[0].trim())) {
					paytmChecksum = entry[1].trim();
				} else if ("paymentServiceId".equalsIgnoreCase(entry[0].trim())) {
					paymentServiceId = entry[1].trim();
				} else {
					responseMap.put(entry[0].trim(), entry[1].trim());
				}
			}

			boolean isValideChecksum = false;
			LOGGER.info("RESULT : " + responseMap.toString());
			String result = "";
			System.out.println(responseMap.containsKey("ORDERID"));
			System.out.println(responseMap.containsKey("RESPCODE"));
//			isValideChecksum = validateCheckSum(responseMap1, paytmChecksum,
//					collectionApplicationProperties.paytmValue(prefix + ".paytm.merchantKey"));
			if (isValideChecksum && responseMap.containsKey(RESPCODE)) {
				if (responseMap.get(RESPCODE).equals("01")) {
					result = CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS;
				} else {
					result = responseMap.get(RESPCODE);
					paytmResponse.setErrorDescription(responseMap.get(RESPMSG));
				}
			} else {
				result = "Checksum mismatched";
				if (responseMap.get(RESPCODE).equals("01")) {// RESPCODE
					result = CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS;
				} else {
					result = responseMap.get(RESPCODE);
					paytmResponse.setErrorDescription(responseMap.get(RESPMSG));
				}
			}

			paytmResponse.setAuthStatus(result);
			paytmResponse.setReceiptId(responseMap.get("ORDERID").replace(PAYTM_PREFIX, ""));
			// paytmResponse.setReceiptId(responseMap1.get(BANKTXNID));
			paytmResponse.setTxnAmount(new BigDecimal(responseMap.get(TXNAMOUNT)));
			paytmResponse.setTxnReferenceNo(responseMap.get(TXNID));

			final String receiptId = responseMap.get("ORDERID");
			final String ulbCode = ApplicationThreadLocals.getCityCode();
//			final ReceiptHeader receiptHeader;
//			final Query qry = entityManager.createNamedQuery(CollectionConstants.QUERY_RECEIPT_BY_ID_AND_CITYCODE);
//			qry.setParameter(1, Long.valueOf(receiptId));
//			qry.setParameter(2, ulbCode);
//			receiptHeader = (ReceiptHeader) qry.getSingleResult();
			paytmResponse.setAdditionalInfo6(receiptHeader.getConsumerCode().replace("-", "").replace("/", ""));
			paytmResponse.setAdditionalInfo2(ulbCode);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.getDefault());
			Date transactionDate = null;
			try {
				if (responseMap.get(TXNDATE) != null) {
					transactionDate = sdf.parse(responseMap.get(TXNDATE));
					paytmResponse.setTxnDate(transactionDate);
				}
			} catch (ParseException e) {
				LOGGER.error("Error occured in parsing the transaction date [" + transactionDate + "]", e);
				try {
					throw new ApplicationException(".transactiondate.parse.error", e);
				} catch (ApplicationException e1) {
					// TODO Auto-generated catch block
					LOGGER.error(e.getMessage());
				}
			}

		} catch (Exception exp) {
			LOGGER.error(exp.getMessage());
		}
		return paytmResponse;
	}

	private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum, String merchantKey)
			throws Exception {
		return PaytmChecksum.verifySignature(parameters, merchantKey, paytmChecksum);
	}

	private String getCheckSum(TreeMap<String, String> parameters, String merchantKey) throws Exception {
		return PaytmChecksum.generateSignature(parameters, merchantKey);
	}

}
