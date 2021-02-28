package com.pixeltrice.springbootpaytmpayment;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.paytm.pg.merchant.PaytmChecksum;

@Controller
public class PaymentController {
	
	@Autowired
	private PaytmDetailPojo paytmDetailPojo;
	@Autowired
	private Environment env;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	
	@GetMapping("/status")
	public String status() {
		return "statusTest";
	}

	 @PostMapping(value = "/submitPaymentDetail")
	    public ModelAndView getRedirect(@RequestParam(name = "CUST_ID") String customerId,
	                                    @RequestParam(name = "TXN_AMOUNT") String transactionAmount,
	                                    @RequestParam(name = "ORDER_ID") String orderId) throws Exception {

	        ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetailPojo.getPaytmUrl());
	        TreeMap<String, String> parameters = new TreeMap<>();
	        paytmDetailPojo.getDetails().forEach((k, v) -> parameters.put(k, v));
	        parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
	        parameters.put("EMAIL", env.getProperty("paytm.email"));
	        parameters.put("ORDER_ID", orderId);
	        parameters.put("TXN_AMOUNT", transactionAmount);
	        parameters.put("CUST_ID", customerId);
	        String checkSum = getCheckSum(parameters);
	        parameters.put("CHECKSUMHASH", checkSum);
	        modelAndView.addAllObjects(parameters);
	        return modelAndView;
	    }
	 
	 
	 
	 @PostMapping(value = "/submitTxnId")
	    public ModelAndView getStatusRedirect(@RequestParam(name = "TXN_ID") String orderId) throws Exception {

	        ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetailPojo.getPaytmStatusUrl());
	        TreeMap<String, String> parameters = new TreeMap<>();
	        //paytmDetailPojo.getDetails().forEach((k, v) -> parameters.put(k, v));
	        parameters.put("orderId", orderId);
	        parameters.put("mid", paytmDetailPojo.getMerchantId());
	        String checkSum = getCheckSum(parameters);
	        parameters.put("CHECKSUMHASH", checkSum);
	        modelAndView.addAllObjects(parameters);
	        return modelAndView;
	    }
	 
	 public static void main(String[] args) {
//			String date="2021-02-19 03:14:28.0";
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.getDefault());
//			Date transactionDate = null;
//			try {
//				transactionDate = sdf.parse(date);
//				System.out.println(transactionDate);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
		 
		 String response="{CURRENCY=INR, ORDERID=118, STATUS=TXN_FAILURE, RESPMSG=Your payment has been declined by your bank. Please contact your bank for any queries. If money has been deducted from your account, your bank will inform us within 48 hrs and we will refund the same, paymentServiceId=10, MID=Chandi23435952923771, RESPCODE=501, CHECKSUMHASH=egki9Vy1rrOWSF165nYVmbEtGXiRMXrTE6dLA683IktNye5dAIVf4p4FT3xAE3UPsrU8PodRI2k8PmiLanOi9VzDTQhlv/NxdgIxHrnp98g=, TXNAMOUNT=52340.00}";
	//	 String response="{CURRENCY=INR, GATEWAYNAME=SBI, RESPMSG=Txn Success, BANKNAME=SBI, PAYMENTMODE=NB, MID=Chandi23435952923771, RESPCODE=01, TXNID=20210227111212800110168802602399804, TXNAMOUNT=52340.00, ORDERID=114PYTM, STATUS=TXN_SUCCESS, BANKTXNID=18603628754, TXNDATE=2021-02-27 19:39:53.0, paymentServiceId=10, CHECKSUMHASH=+mA6x0GKVcRVlY2ItkDtPZiokRa25Fi3EJbd5yoW2VgeU863rg9WnqLE96Ps2A7dYN9MryvYoCAmmn/lkByeoGWp2x2gDJqwkcur/TEWD1A=}";
			 String[] keyValueStr = response.replace("{", "").replace("}", "").split(",");
			//PaymentResponse paytmResponse = new DefaultPaymentResponse();
			TreeMap<String, String> responseMap1 = new TreeMap<String, String>();
			for (String pair : keyValueStr) {
				String[] entry = pair.split("=",2);
				responseMap1.put(entry[0].trim(), entry[1].trim());
			}
			
		}
	 
	 @PostMapping(value = "/pgresponse")
	    public String getResponseRedirect(HttpServletRequest request, Model model) {

	        Map<String, String[]> mapData = request.getParameterMap();
	        TreeMap<String, String> parameters = new TreeMap<String, String>();
	        String paytmChecksum = "";
	        for (Entry<String, String[]> requestParamsEntry : mapData.entrySet()) {
	            if ("CHECKSUMHASH".equalsIgnoreCase(requestParamsEntry.getKey())){
	                paytmChecksum = requestParamsEntry.getValue()[0];
	            } else {
	            	parameters.put(requestParamsEntry.getKey(), requestParamsEntry.getValue()[0]);
	            }
	        }
	        String result;

	        boolean isValideChecksum = false;
	        System.out.println("RESULT : "+parameters.toString());
	        try {
	            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
	            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
	                if (parameters.get("RESPCODE").equals("01")) {
	                    result = "Payment Successful";
	                } else {
	                    result = "Payment Failed";
	                }
	            } else {
	                result = "Checksum mismatched";
	            }
	        } catch (Exception e) {
	            result = e.toString();
	        }
	        model.addAttribute("result",result);
	        parameters.remove("CHECKSUMHASH");
	        model.addAttribute("parameters",parameters);
	        return "report";
	    }

	    private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
	        return PaytmChecksum.verifySignature(parameters,
	                paytmDetailPojo.getMerchantKey(), paytmChecksum);
	    }


	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		return PaytmChecksum.generateSignature(parameters, paytmDetailPojo.getMerchantKey());
	}
	
	
	
	
	
}

