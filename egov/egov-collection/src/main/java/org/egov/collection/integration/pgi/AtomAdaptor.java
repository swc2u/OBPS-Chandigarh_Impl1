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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

/**
 * The PaymentRequestAdaptor class frames the request object for the payment
 * service.
 */
@Service
public class AtomAdaptor implements PaymentGatewayAdaptor {

	private static final Logger LOGGER = Logger.getLogger(AtomAdaptor.class);
	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;
	@PersistenceContext
	private EntityManager entityManager;

	AtomAES atomAES = new AtomAES();

	/**
	 * This method invokes APIs to frame request object for the payment service
	 * passed as parameter
	 *
	 * @param serviceDetails
	 * @param receiptHeader
	 * @return
	 */
	@Override
	public PaymentRequest createPaymentRequest(final ServiceDetails paymentServiceDetails,
			final ReceiptHeader receiptHeader) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("inside  AtomAdaptor createPaymentRequest");
		}
		final DefaultPaymentRequest paymentRequest = new DefaultPaymentRequest();
		final List<NameValuePair> formData = new ArrayList<>(0);
		
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_PASS, collectionApplicationProperties.atomPass()));
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_TTYPE, collectionApplicationProperties.atomTtype()));
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_PRODID, collectionApplicationProperties.atomProdid()));
		
		String totalAmt = (receiptHeader.getTotalAmount().compareTo(new BigDecimal(50000))>0)?"50000.00":String.valueOf(receiptHeader.getTotalAmount());
		
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_AMT, new BigDecimal(totalAmt).setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString()));
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_TXNCURR,collectionApplicationProperties.atomTxncurr()));		
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_TXNSCAMT,collectionApplicationProperties.atomTxnscamt()));
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_CLIENTCODE,collectionApplicationProperties.atomClientcode()));
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_TXNID, receiptHeader.getId().toString()));
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_DATE,DateUtils.getFormattedDate(receiptHeader.getCreatedDate(), "dd/MM/yyyy HH:mm:ss")));
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_CUSTACC,collectionApplicationProperties.atomCustacc()));

		StringBuilder returnUrl = new StringBuilder();
		returnUrl.append(paymentServiceDetails.getCallBackurl()).append("?paymentServiceId=").append(paymentServiceDetails.getId());
		formData.add(new BasicNameValuePair(CollectionConstants.ATOM_RU, returnUrl.toString()));
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("First request ATOM: " + formData);
		}
		StringBuffer sbb = new StringBuffer(CollectionConstants.ATOM_LOGIN + "=" + collectionApplicationProperties.atomLogin());
		for (NameValuePair nvp : formData) {
			sbb.append("&" + nvp.getName() + "=" + nvp.getValue());
		}
		String encrypt = null;
		try {
			String signature_request = getEncodedValueWithSha2(collectionApplicationProperties.atomReqHashKey(),
					collectionApplicationProperties.atomLogin(), collectionApplicationProperties.atomPass(),
					collectionApplicationProperties.atomTtype(), collectionApplicationProperties.atomProdid(),receiptHeader.getId().toString(),
					new BigDecimal(totalAmt).setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString(),
					collectionApplicationProperties.atomTxncurr());
			sbb.append("&signature=" + signature_request);
			encrypt = atomAES.encrypt(sbb.toString(), collectionApplicationProperties.atomAESRequestKey(),
					collectionApplicationProperties.atomRequestIV_Salt());
		} catch (Exception exp) {
			LOGGER.error("ATOM encryption error : " + exp.getMessage());
		}
		String secondRequestStr = paymentServiceDetails.getServiceUrl() + "?login=" + collectionApplicationProperties.atomLogin() + "&encdata=" + encrypt;
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Second request ATOM : " + secondRequestStr);
		}
		paymentRequest.setParameter(CollectionConstants.ONLINEPAYMENT_INVOKE_URL, secondRequestStr);
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Second paymentRequest: " + paymentRequest.getRequestParameters());
		}
		return paymentRequest;
	}
	
	
	@Transactional
	public PaymentResponse createOfflinePaymentRequest(final OnlinePayment onlinePayment) {
		LOGGER.debug("Inside AtomAdaptor createOfflinePaymentRequest");
		PaymentResponse atomResponse = new DefaultPaymentResponse();
		try {
			
			final List<NameValuePair> formData = new ArrayList<>(0);
			formData.add(new BasicNameValuePair(CollectionConstants.ATOM_MERCHANTID,
					collectionApplicationProperties.atomLogin()));
			formData.add(new BasicNameValuePair(CollectionConstants.ATOM_MERCHANT_TXNID,
					onlinePayment.getReceiptHeader().getId().toString()));
			formData.add(new BasicNameValuePair(CollectionConstants.ATOM_AMT,
					onlinePayment.getReceiptHeader().getTotalAmount()
							.setScale(CollectionConstants.AMOUNT_PRECISION_DEFAULT, BigDecimal.ROUND_UP).toString()));
			formData.add(new BasicNameValuePair(CollectionConstants.ATOM_TDATE,
					DateUtils.getFormattedDate(onlinePayment.getCreatedDate(), "yyyy-MM-dd")));
			LOGGER.debug("ATOM  Reconcilation request : " + formData);
			
			StringBuffer sbb = new StringBuffer(
					CollectionConstants.ATOM_LOGIN + "=" + collectionApplicationProperties.atomLogin());
			for (NameValuePair nvp : formData) {
				sbb.append("&" + nvp.getName() + "=" + nvp.getValue());
			}
			
			String encrypt=null;
			encrypt = atomAES.encrypt(sbb.toString(), collectionApplicationProperties.atomAESRequestKey(),
					collectionApplicationProperties.atomRequestIV_Salt());
			String secondRequestStr=collectionApplicationProperties.atomReconcileUrl()+"?login="+collectionApplicationProperties.atomLogin()+"&encdata="+encrypt;
			
			final HttpPost httpPost = new HttpPost(secondRequestStr);
			
			final List<NameValuePair> formData1 = new ArrayList<>(0);
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formData1);
			httpPost.setEntity(urlEncodedFormEntity);
			
			final CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity responseAtom = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(responseAtom.getContent()));
			final StringBuilder data = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
				data.append(line);
			reader.close();
			LOGGER.info("ATOM Reconcile Response : " + data.toString());
			Gson gson = new Gson();

			String decryptEncdata = atomAES.decrypt(data.toString(), collectionApplicationProperties.atomAESResponseKey(),
					collectionApplicationProperties.atomResponseIV_Salt());
			ResponseAtomReconcilation[] responseAtomReconcilations = gson.fromJson(decryptEncdata, ResponseAtomReconcilation[].class);
			ResponseAtomReconcilation responseAtomReconcilation=responseAtomReconcilations[0];
			atomResponse.setAuthStatus((null != responseAtomReconcilation.getVerified()
					&& responseAtomReconcilation.getVerified().equals("SUCCESS"))
							? CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS
							: responseAtomReconcilation.getVerified());
			atomResponse.setErrorDescription(responseAtomReconcilation.getVerified());
			atomResponse.setReceiptId(responseAtomReconcilation.getMerchantTxnID());
			if (CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS.equals(atomResponse.getAuthStatus())) {
				atomResponse.setTxnReferenceNo(responseAtomReconcilation.getAtomtxnId());
				atomResponse.setTxnAmount(new BigDecimal(responseAtomReconcilation.getAmt()));
				String[] udf9 = responseAtomReconcilation.getUdf9().split("\\|");
				atomResponse.setAdditionalInfo6(udf9[1]);
				atomResponse.setAdditionalInfo2(udf9[0]);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
				Date transactionDate = null;
				try {
					transactionDate = sdf.parse(responseAtomReconcilation.getTxnDate());
					atomResponse.setTxnDate(transactionDate);
				} catch (ParseException e) {
					LOGGER.error("Error occured in parsing the transaction date ["
							+ responseAtomReconcilation.getTxnDate() + "]", e);
					throw new ApplicationException(".transactiondate.parse.error", e);
				}
			} else {
				atomResponse.setAdditionalInfo6(
						onlinePayment.getReceiptHeader().getConsumerCode().replace("-", "").replace("/", ""));
				atomResponse.setAdditionalInfo2(ApplicationThreadLocals.getCityCode());
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return atomResponse;
	}

	/*
	 * Encoded with HMACSHA512 and encoded with utf-8 using url encoder for given
	 * list of parameter values appended with the key
	 */
	private static String getEncodedValueWithSha2(String hashKey, String... param) {
		String resp = null;

		StringBuilder sb = new StringBuilder();
		for (String s : param) {
			sb.append(s);
		}

		try {
			System.out.println("[getEncodedValueWithSha2]String to Encode =" + sb.toString());
			resp = byteToHexString(encodeWithHMACSHA2(sb.toString(), hashKey));
			// resp = URLEncoder.encode(resp,"UTF-8");

		} catch (Exception e) {
			System.out.println("[getEncodedValueWithSha2]Unable to encocd value with key :" + hashKey + " and input :"
					+ sb.toString());
			e.printStackTrace();
		}

		return resp;
	}

	/*
	 * Hashing using key with HMACSHA512
	 */
	private static byte[] encodeWithHMACSHA2(String text, String keyString)
			throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException,
			java.io.UnsupportedEncodingException {

		java.security.Key sk = new javax.crypto.spec.SecretKeySpec(keyString.getBytes("UTF-8"), "HMACSHA512");
		javax.crypto.Mac mac = javax.crypto.Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);

		byte[] hmac = mac.doFinal(text.getBytes("UTF-8"));

		return hmac;
	}

	/*
	 * Convert from byte array to HexString
	 */
	private static String byteToHexString(byte byData[]) {
		StringBuilder sb = new StringBuilder(byData.length * 2);

		for (int i = 0; i < byData.length; i++) {
			int v = byData[i] & 0xff;
			if (v < 16)
				sb.append('0');
			sb.append(Integer.toHexString(v));
		}

		return sb.toString();
	}

	@Override
	public PaymentResponse parsePaymentResponse(final String response) {
		LOGGER.debug("inside  ATOM createPaymentRequest");
		LOGGER.info("Response message from Atom Payment gateway: " + response);
		String[] keyValueStr = response.replace("{", "").replace("}", "").split(",");
		PaymentResponse atomResponse = new DefaultPaymentResponse();
		Map<String, String> responseMap1 = new HashMap<String, String>(0);
		for (String pair : keyValueStr) {
			String[] entry = pair.split("=");
			responseMap1.put(entry[0].trim(), entry[1].trim());
		}

		String encdata = responseMap1.get(CollectionConstants.ATOM_ENCDATA);
		Map<String, String> responseMap = new HashMap<String, String>();
		
		try {
			String decryptEncdata = atomAES.decrypt(encdata, collectionApplicationProperties.atomAESResponseKey(),
					collectionApplicationProperties.atomResponseIV_Salt());
			String[] keyValueStr1 = decryptEncdata.split("&");
			
			for (String pair : keyValueStr1) {
				String[] entry = pair.split("=");
				System.out.println(pair);
				
				if(entry.length>1)
				responseMap.put(entry[0].trim(), entry[1].trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		atomResponse.setAuthStatus(responseMap.get(CollectionConstants.ATOM_F_CODE).equalsIgnoreCase("Ok")
				? CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS
				: responseMap.get(CollectionConstants.ATOM_F_CODE));
		atomResponse.setErrorDescription(responseMap.get(CollectionConstants.ATOM_F_CODE));
		atomResponse.setReceiptId(responseMap.get(CollectionConstants.ATOM_MER_TXN));
		atomResponse.setTxnAmount(new BigDecimal(responseMap.get(CollectionConstants.ATOM_AMT)));
		atomResponse.setTxnReferenceNo(responseMap.get(CollectionConstants.ATOM_MMP_TXN));
		if (responseMap.get(CollectionConstants.ATOM_UDF9) != null && responseMap.get(CollectionConstants.ATOM_UDF9).split("\\|").length>1) {
			String[] udf9 = responseMap.get(CollectionConstants.ATOM_UDF9).split("\\|");
			if(udf9.length>1) {
				atomResponse.setAdditionalInfo6(udf9[1]);
				atomResponse.setAdditionalInfo2(udf9[0]);
			}
		} else { 
			final String receiptId = responseMap.get(CollectionConstants.ATOM_MER_TXN);
			final String ulbCode = ApplicationThreadLocals.getCityCode();
			final ReceiptHeader receiptHeader;
			final Query qry = entityManager.createNamedQuery(CollectionConstants.QUERY_RECEIPT_BY_ID_AND_CITYCODE);
			qry.setParameter(1, Long.valueOf(receiptId));
			qry.setParameter(2, ulbCode);
			receiptHeader = (ReceiptHeader) qry.getSingleResult();
			atomResponse.setAdditionalInfo6(receiptHeader.getConsumerCode().replace("-", "").replace("/", ""));
			atomResponse.setAdditionalInfo2(ulbCode);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
		Date transactionDate = null;
		try {
			transactionDate = sdf.parse(responseMap.get(CollectionConstants.ATOM_DATE));
			atomResponse.setTxnDate(transactionDate);
		} catch (ParseException e) {
			LOGGER.error("Error occured in parsing the transaction date [" + transactionDate + "]", e);
			try {
				throw new ApplicationException(".transactiondate.parse.error", e);
			} catch (ApplicationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return atomResponse;
	}
	
}
