<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2017>  eGovernments Foundation
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
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<form:form role="form" action=""
	modelAttribute="searchBpaApplicationForm" id="collectionSummaryHeadwiseReport" cssClass="form-horizontal form-groups-bordered" enctype="multipart/form-data">
	<div class="col-md-12">
	        <div class="panel panel-primary" data-collapsed="0">
	            <div class="panel-heading">
	            </div>
	            <div class="panel-body">	
	                <div class="form-group">
	                    <label class="col-sm-3 control-label text-right"><spring:message
 	                            code="lbl.applctn.type"/></label>  
	                    <div class="col-sm-3 add-margin">
	                        <form:select path="applicationTypeId" data-first-option="false"
 	                                     id="applicationTypeId" cssClass="form-control applicationType" required="required"> 
	                            <form:option value="">
	                                <spring:message code="lbl.select"/>
	                            </form:option>
	                            <form:options items="${appTypes}" itemLabel="description" itemValue="id"/>
	                        </form:select>
	                    </div>
	                    <label class="col-sm-2 control-label text-right"><spring:message
	                            code="lbl.payment.mode"/></label>
	                    <div class="col-sm-3 add-margin">
	                        <form:select path="paymentMode" data-first-option="false"
	                                     id="paymentMode" cssClass="form-control">
	                            <form:option value="">
	                                <spring:message code="lbl.select"/>
	                            </form:option>
	                            <c:forEach var="paymentmode" items="${paymentModes}">
							        <option value="${paymentmode.key}"><c:out value="${paymentmode.value}"/></option>
						      </c:forEach>
	                        </form:select>
	                    </div>
	                </div>
					<div class="form-group">
	                    <label class="col-sm-3 control-label text-right"><spring:message
	                            code="lbl.fromDate"/><span class="mandatory"></span></label>
	                    <div class="col-sm-3 add-margin">
	                        <form:input path="fromDate" class="form-control datepicker"
	                                    data-date-end-date="0d" id="fromDate"
	                                    data-inputmask="'mask': 'd/m/y'"/>
	                        <form:errors path="fromDate" cssClass="add-margin error-msg"/>
	                    </div>
	                    <label class="col-sm-2 control-label text-right"><spring:message
	                            code="lbl.toDate"/><span class="mandatory"></span></label>
	                    <div class="col-sm-3 add-margin">
	                        <form:input path="toDate" class="form-control datepicker"
	                                    data-date-end-date="0d" id="toDate"
	                                    data-inputmask="'mask': 'd/m/y'"/>
	                        <form:errors path="toDate" cssClass="add-margin error-msg"/>
	                    </div>
	                </div>      
	                            
	                      
	            </div>
	        </div>
	    </div>
	</div>
	<div class="text-center">
		<button type='button' class='btn btn-primary' id="btnSearch">
			<spring:message code='lbl.search' />
		</button>
		<button type="reset" id="reset" class="btn btn-danger">
			<spring:message code="lbl.reset" />
		</button>
		<a href='javascript:void(0)' class='btn btn-default' onclick='self.close()'><spring:message code='lbl.close' /></a>
	</div>
</form:form>
<br>

<div id="aa" style="height: 100px; width: 100%;"></div>

<div class="row display-hide collection-headwise-section" id="table_container">
	<div class="col-md-12 form-group report-table-container">
		<table class="table table-bordered table-hover multiheadertbl nowrap display"
                       id="collectionSummaryHeadwiseTable" >
                    <thead>
<!--                     <tr> -->
                        
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<%--                         <th colspan="8"><spring:message code="lbl.fee.details"/></th> --%>
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                         <th colspan="1"></th> -->
<!--                     </tr> -->
                    <tr>
<%--                         <th><spring:message code="lbl.slno"/></th> --%>
						<th><spring:message code="lbl.fromDate"/></th>
						<th><spring:message code="lbl.toDate"/></th>
						<th><spring:message code="lbl.applctn.type"/></th>
						<th><spring:message code="lbl.payment.mode"/></th>
                        <th><spring:message code="lbl.source"/></th>
                        <th><spring:message code="lbl.revenue.head"/></th>
                        <th><spring:message code="lbl.cash.receipt"/></th>
                        <th><spring:message code="lbl.cash.amount"/></th>
                        <th><spring:message code="lbl.cheque.receipt"/></th>
                        <th><spring:message code="lbl.cheque.amont"/></th>
                        <th><spring:message code="lbl.online.receipt"/></th>
                        <th><spring:message code="lbl.online.amount"/></th>                   
                        <th><spring:message code="lbl.card.receipt"/></th>
                        <th><spring:message code="lbl.card.amount"/></th>
                        <th><spring:message code="lbl.total.receipt"/></th>
                        <th><spring:message code="lbl.total.amount"/></th>
                        
                    </tr>
                    </thead>
                    <tfoot id="report-footer">
				<tr>
						<td colspan="6"><spring:message code="lbl.total.collection"/></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
-						<td></td>
						<td></td>
<!-- 						<td></td>  -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
					</tr>
<!-- 					<tr> -->
<%-- 						<td colspan="6"><spring:message code="lbl.rebate.amount"/></td> --%>
<!-- 						<td id="cashR"></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- -						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td>  -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<%-- 						<td colspan="6"><spring:message code="lbl.total.collection"/></td> --%>
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- -						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td>  -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 						<td></td> -->
<!-- 					</tr> -->
				</tfoot>
                </table>
	</div>
	<input type="hidden" id="atleastOneInputReq" value="<spring:message code='msg.validate.atleast.oneinput.required'/>"/>
</div>

<script src="<cdn:url value='/resources/global/js/bts/bts-datepicker.js' context='/egi'/>"></script>	
<link rel="stylesheet"
	  href="<cdn:url value='/resources/global/css/jq/plugins/datatables/jq.dataTables.min.css' context='/egi'/>"/>
<link rel="stylesheet"
	  href="<cdn:url value='/resources/global/css/jq/plugins/datatables/dataTables.bts.min.css' context='/egi'/>">
<script type="text/javascript"
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/jq.dataTables.min.js' context='/egi'/>"></script>
<script type="text/javascript"
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/dataTables.bts.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/extensions/buttons/dataTables.buttons.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/extensions/buttons/buttons.bts.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/extensions/buttons/buttons.flash.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/extensions/buttons/jszip.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/extensions/buttons/pdfmake.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/extensions/buttons/vfs_fonts.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/extensions/buttons/buttons.html5.min.js' context='/egi'/>"></script>
<script
		src="<cdn:url value='/resources/global/js/jq/plugins/datatables/extensions/buttons/buttons.print.min.js' context='/egi'/>"></script>
<script type="text/javascript"
		src="<cdn:url value='/resources/global/js/jq/plugins/jq.validate.min.js' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/global/js/jq/plugins/datatables/datetime-moment.js' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/global/js/bts/bts-datepicker.js' context='/egi'/>"
		type="text/javascript"></script>
<script src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>

<script src="<cdn:url value='/resources/js/app/bpa-ajax-helper.js?rnd=${app_release_no}'/> "></script>
<script src="<cdn:url value='/resources/js/app/collection-summary-headwise-report-urban.js?rnd=${app_release_no}'/> "></script>


<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
