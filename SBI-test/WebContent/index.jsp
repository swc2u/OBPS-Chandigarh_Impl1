<%@page import="com.util.SBIePayUtill"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String EncryptTrans=SBIePayUtill.createPaymentRequest();
%>

	<form method="post" action="https://www.sbiepay.sbi/secure/AggregatorHostedListener">
		<input type="hidden" name="EncryptTrans" value="<%=EncryptTrans%>">
		<input type="hidden" name="merchIdVal" value="1000112" /> 
		<input type="submit" name="submit" value="Submit">
	</form>

</body>
</html>