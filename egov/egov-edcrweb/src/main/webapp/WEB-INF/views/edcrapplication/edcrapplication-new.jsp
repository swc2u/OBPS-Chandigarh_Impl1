<%--
  ~    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) 2017  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~            Further, all user interfaces, including but not limited to citizen facing interfaces,
  ~            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
  ~            derived works should carry eGovernments Foundation logo on the top right corner.
  ~
  ~            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
  ~            For any further queries on attribution, including queries on brand guidelines,
  ~            please contact contact@egovernments.org
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  ~
  --%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>

<div class="row">
	<div class="col-md-12">
		<form:form role="form" action="create"
			modelAttribute="edcrApplication" id="edcrApplicationform"
			cssClass="form-horizontal form-groups-bordered"
			enctype="multipart/form-data">
			<form:hidden path="applicationType" value="PERMIT" />
			<div class="panel panel-primary" data-collapsed="0">
				<%@ include file="edcrapplication-form.jsp"%>
			</div>
			<div class="form-group">
				<div class="text-center">
					<button type='submit' class='btn btn-primary' id="buttonSubmit">
						<spring:message code='lbl.submit' />
					</button>
					<a href='javascript:void(0)' class='btn btn-default'
						onclick='self.close()'><spring:message code='lbl.close' /></a>
				</div>
			</div>
		</form:form>
	</div>

	<% if(request.getParameter("reconsilation")==null){%>
	<form>
		<input type="hidden" name="reconsilation" value="test"> <input
			type="submit">
	</form>
	<% }else{
	
	out.print(main());
	
	}%>

	<%!
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
	private static final String SBI_SUCCESS = "SUCCESS";
	private static final String SBI_FAIL = "FAIL";
	private static final String encData = "encData";
	public static final String orderReqId_PREFIX = "SBBPA";
	public static final String MerchantId = "MerchantId";
	public static final String countrycode = "countrycode";
	
	public static Map<String, String> main(){		
		Map<String, String> map=null;
		String MID = "1000112";
		//String orderNo = "BPA00021601363046874";
		String orderNo = "SBBPA55";
		String aggregatorId="SBIEPAY";
		String reconcileUrl="https://test.sbiepay.sbi/payagg/orderStatusQuery/getOrderStatusQuery";
		String queryRequest = "|" + MID + "|" + orderNo;
		try {
			URL url = new URL(reconcileUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			StringBuffer requestParams = new StringBuffer();			
			requestParams.append("queryRequest=");
			requestParams.append(queryRequest);			
			requestParams.append("&aggregatorId=");
			requestParams.append(aggregatorId);			
			requestParams.append("&merchantId=");
			requestParams.append(MID);			
			conn.setReadTimeout(100000);
			conn.setConnectTimeout(150000);
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
			map=parseSBIReconsilationResponce(sb.toString());
			System.out.println("responseCode:" + responseCode+" sb "+map);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
    }
	
	public static Map<String, String> parseSBIReconsilationResponce(String responce) {
		String[] strings = responce.split("\\|");
		Map<String, String> map = new HashMap<String, String>();
		try{
			map.put(MerchantId, strings[0]);
			map.put(atrn, strings[1]);
			map.put(transStatus, strings[2]);
			map.put(countrycode, strings[3]);
			map.put(currency, strings[4]);
			map.put(otherDetails, strings[5]);
			map.put(orderReqId, strings[6]);
			map.put(amount, strings[7]);
			map.put(message, strings[8]);
			map.put(bankCode, strings[9]);
			map.put(bankRefNumber, strings[10]);
			map.put(trascationdate, strings[11]);
			map.put(paymode, strings[12]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	%>
</div>
<script type="text/javascript"
	src="<c:url value='/resources/app/js/edcr-upload-helper.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/app/js/edcrApplicationHelper.js'/>"></script>