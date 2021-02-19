<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>

<div class="panel-body">
	<c:if test="${not empty tempFees}">
		<div class="panel-heading custom_form_panel_heading">
		    <div class="panel-title">
		        <spring:message code="lbl.fee.details"/>
		    </div>
		</div>
		<table class="table table-striped table-bordered" id="bpaChargesDetail" style="width:50%;margin:0 auto;">
			<thead>
	            <tr>
	                <th class="text-center"><spring:message code="lbl.slno"/></th>
	                <th style="width:50%;"><spring:message code="lbl.applicationFee.feeType"/></th>
	                <th class="text-right"><spring:message code="lbl.applicationFee.amount"/></th>
	            </tr>
	        </thead>
	        <tbody id="tblBody">
	            <c:set var="totalAmount" value="${0}"/>
	            <c:forEach var="fee" items="${tempFees}" varStatus="counter">
	                <tr>
	                    <td class="text-center view-content"><c:out value="${counter.index+1}"/></td>
	                    <td id="description" class="view-content"><c:out value="${fee.key}"/></td>
	                    <td class="text-right view-content">
	                    	<c:set var="totalAmount" value="${totalAmount + fee.value}"/>
	                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${fee.value}"/>
	                    </td>
	                </tr>
	            </c:forEach>
	        </tbody>
	        <tfoot>
	            <tr>
	                <td></td>
	                <td class="text-right view-content"><spring:message code="lbl.total.amount"/></td>
	                <td class="text-right view-content">
	                	<fmt:formatNumber type="number" maxFractionDigits="2" value="${totalAmount}"/>
	                </td>
	            </tr>
	        </tfoot>
		</table>
	</c:if>
</div>
