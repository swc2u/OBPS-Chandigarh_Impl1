package com.util;

public class SBIePayUtill{	
	public static String createPaymentRequest() {
		String MID = "1000112";
        String Collaborator_Id = "SBIEPAY";
        String Operating_Mode = "DOM";
        String Country = "IN";
        String Currency = "INR";
        String Amount = "2";
        String Order_Number = "CHBPADUMMY00103";
        String Other_Details = "Other";
        String Success_URL = "https://obps-test.chandigarhsmartcity.in/egi/login/secure";
        String Failure_URL = "https://obps-test.chandigarhsmartcity.in/egi/login/secure";
		String Requestparameter = MID + "|" + Operating_Mode + "|" + Country + "|" + Currency + "|" + Amount + "|" + Other_Details + "|"
								 + Success_URL + "|" + Failure_URL + "|" + Collaborator_Id + "|" + Order_Number + "|" + "2" + "|" 
				                 + "NB" + "|" + "ONLINE" + "|" + "ONLINE";
		
		String EncryptedParam = AES256Bit.encrypt(Requestparameter, AES256Bit.readKeyBytes("A7C9F96EEE0602A61F184F4F1B92F0566B9E61D98059729EAD3229F882E81C3A"));
		
		System.out.println(EncryptedParam);
		return EncryptedParam;
	}
	
	public static void main(String[] args) {
		createPaymentRequest();
	}
}
