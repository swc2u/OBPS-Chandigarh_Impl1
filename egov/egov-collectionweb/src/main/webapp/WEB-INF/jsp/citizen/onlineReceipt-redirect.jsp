<%@ include file="/includes/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><s:text name="page.title"/></title>
	<script type="text/javascript">
		function onload() {
			var form = document.createElement("form");
			form.setAttribute("method", "POST");

			<s:iterator value="paymentRequest.requestParameters">
		<s:if test='key.equals("paymentGatewayURL")'>
		   var 	actionvalue =  '<s:property value='value' />';
		   actionvalue = actionvalue.replace(/&amp;/g,'&');
			form.setAttribute("action", actionvalue);			
		</s:if>
		<s:else>
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type","hidden");              
			hiddenField.setAttribute("name",  "<s:property value='key' />");
			hiddenField.setAttribute("value", "<s:property value='value' />");
			form.appendChild(hiddenField);	
		</s:else>
			
	</s:iterator> 
   	document.body.appendChild(form);
   	console.log(form);
	form.submit();
}

</script>
</head>
<body onload="onload()">
</body>
</html>
