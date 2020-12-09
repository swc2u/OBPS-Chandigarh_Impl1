package org.egov.bpa.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class testCode {
	public static void main(String []args){		
		String MID = "1000112";
		String orderNo = "BPA00021601363046874";
		String queryRequest = "|" + MID + "|" + orderNo;
		String aggregatorId="SBIEPAY";

		try {
			URL url = new URL("https://test.sbiepay.sbi/payagg/orderStatusQuery/getOrderStatusQuery");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			StringBuffer requestParams = new StringBuffer();			
			requestParams.append("queryRequest=");
			requestParams.append(queryRequest);			
			requestParams.append("&aggregatorId=");
			requestParams.append(aggregatorId);			
			requestParams.append("&merchantId=");
			requestParams.append(MID);			
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(requestParams.toString());
			wr.flush();
			wr.close();
			// Response Code
			int responseCode = conn.getResponseCode();
			// Reading Response
			InputStream stream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			stream.close();
			System.out.println("responseCode:" + responseCode+" sb "+sb);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
//	public static void main(String []args){	
//		System.out.println((((new BigDecimal(900.52)).compareTo(new BigDecimal(900))) <= 0));
//	}
}
