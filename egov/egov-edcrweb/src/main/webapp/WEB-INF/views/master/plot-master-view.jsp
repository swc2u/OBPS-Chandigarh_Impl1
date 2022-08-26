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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<div class="row" id="page-content">
    <div class="col-md-18">
        <div class="panel" data-collapsed="0">
            <div class="panel-body">
                <c:if test="${not empty warning}">
                    <div class="alert alert-danger" role="alert"><spring:message code="${warning}"/></div>
                </c:if>
 
                        <form:form method="get" class="form-horizontal form-groups-bordered" commandName="plotMaster" id="plotMasterView" modelAttribute="plotMaster">
                            <div class="panel panel-primary" data-collapsed="0">
                                <div class="panel-heading">
                                    <div class="panel-title">
                                        <strong><spring:message code="lbl.hdr.viewPlotMaster"/> ${plotMaster.allowedsuboccupancy.plot.plotNum}</strong>
                                    </div>
                                </div>
                             </div>
                             <input type="hidden" name="subOccupancyId" value="<c:out value="${plotMaster.allowedsuboccupancy.subOccupancy}" />"/>
                              <input type="hidden" name="plotId" value="<c:out value="${plotMaster.allowedsuboccupancy.plot.id}" />"/>
                                    
                                    <div class="panel-body custom-form" height="100%">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.code"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                             <strong><c:out value="${plotMaster.code}"/></strong>
                                    			<input type="hidden" id="code" value="<c:out value="${plotMaster.code}" />"/>
                                            </div>
                                        </div>
                                         <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.phase"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.allowedsuboccupancy.plot.phase}"/></strong>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.local.sector"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                                <strong><c:out value="${plotMaster.allowedsuboccupancy.plot.boundary.name}"/></strong>
                                    			<input type="hidden" id="sector" value="<c:out value="${plotMaster.allowedsuboccupancy.plot.boundary.name}"/>"/>
                                            
                                    		</div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.plot.number"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.allowedsuboccupancy.plot.plotNum}"/></strong>
                                    			<input type="hidden" id="plotNum" value="<c:out value="${plotMaster.allowedsuboccupancy.plot.plotNum}" />"/>
                                             </div>
                                        </div>
                                       <div class="form-group">
                                             <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.plot.area"/>
                                             </label>
                                             <div class="col-sm-6" style="padding-top: 7px"> 
                                             	<strong><c:out value="${plotMaster.allowedsuboccupancy.plot.plotArea}"/></strong>
                                             </div>
                                       </div> 
                                         <div class="form-group"> 
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.plot.areatype"/>
                                            </label> 
                                             <div class="col-sm-6" style="padding-top: 7px"> 
                                             	<strong><c:out value="${plotMaster.allowedsuboccupancy.plot.areaType}"/></strong>
                                             </div>
                                         </div>
                                         <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.plot.depth"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.allowedsuboccupancy.plot.plotDepth}"/></strong>
                                             </div>
                                        </div>
                                         <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.plot.width"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.allowedsuboccupancy.plot.plotWidth}"/></strong>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.bcy.width"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.backCourtyardWidth}"/></strong>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.bcy.height"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                                <strong><c:out value="${plotMaster.backCourtyardHeight}"/></strong>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.perm.stories"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.permissibleBuildingStories}"/></strong>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.perm.height"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.permissibleBuildingHeight}"/></strong>
                                            </div>
                                        </div>
                                         <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.perm.far"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.maxmimumPermissibleFAR}"/></strong>
                                           </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.perm.setback.front"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.minimumPermissibleSetback_Front}"/></strong>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.perm.setback.rear"/>
                                            </label>
                                            <div class="col-sm-6" style="padding-top: 7px">
                                            	<strong><c:out value="${plotMaster.minimumPermissibleSetback_Rear}"/></strong>
                                            </div>
                                        </div>
<%--                                         <c:choose> --%>
<%-- 	                                        <c:when test="${not empty plotMaster.minimumPermissibleSetback_Left}"> --%>
		                                        <div class="form-group">
		                                            <label class="col-sm-3 control-label">
		                                                <spring:message code="lbl.perm.setback.left"/>
		                                            </label>
		                                            <div class="col-sm-6" style="padding-top: 7px">
		                                           	 	<strong><c:out value="${plotMaster.minimumPermissibleSetback_Left}"/></strong>
		                                            </div>
		                                        </div>
<%-- 	                                        </c:when> --%>
<%-- 	                                        <c:otherwise> --%>
	                                        	 <div class="form-group">
		                                            <label class="col-sm-3 control-label">
		                                                <spring:message code="lbl.perm.setback.left.depth"/>
		                                            </label>
		                                            <div class="col-sm-6" style="padding-top: 7px">
		                                           	 	<strong><c:out value="${plotMaster.minimumPermissibleSetback_Left_depth}"/></strong>
		                                            </div>
		                                        </div>
	                                        	 <div class="form-group">
		                                            <label class="col-sm-3 control-label">
		                                                <spring:message code="lbl.perm.setback.left.width"/>
		                                            </label>
		                                            <div class="col-sm-6" style="padding-top: 7px">
		                                           	 	<strong><c:out value="${plotMaster.minimumPermissibleSetback_Left_width}"/></strong>
		                                            </div>
		                                        </div>
<%-- 	                                        </c:otherwise> --%>
<%--                                         </c:choose> --%>
<%--                                         <c:choose> --%>
<%-- 	                                        <c:when test="${not empty plotMaster.minimumPermissibleSetback_Right}"> --%>
		                                        <div class="form-group">
		                                            <label class="col-sm-3 control-label">
		                                                <spring:message code="lbl.perm.setback.right"/>
		                                            </label>
		                                            <div class="col-sm-6" style="padding-top: 7px">
		                                            	<strong><c:out value="${plotMaster.minimumPermissibleSetback_Right}"/></strong>
		                                            </div>
		                                        </div>
<%-- 	                                        </c:when> --%>
<%-- 	                                        <c:otherwise> --%>
		                                         <div class="form-group">
			                                            <label class="col-sm-3 control-label">
			                                                <spring:message code="lbl.perm.setback.right.width"/>
			                                            </label>
			                                            <div class="col-sm-6" style="padding-top: 7px">
			                                            	<strong><c:out value="${plotMaster.minimumPermissibleSetback_Right_width}"/></strong>
			                                            </div>
			                                        </div>
			                                         <div class="form-group">
			                                            <label class="col-sm-3 control-label">
			                                                <spring:message code="lbl.perm.setback.right.depth"/>
			                                            </label>
			                                            <div class="col-sm-6" style="padding-top: 7px">
			                                            	<strong><c:out value="${plotMaster.minimumPermissibleSetback_Right_depth}"/></strong>
			                                            </div>
			                                        </div>
<%-- 	                                        </c:otherwise> --%>
<%--                                         </c:choose> --%>
                                        <div class="form-group">
			                                <label class="col-sm-3 control-label">
			                                    <spring:message code="lbl.fromDate"/>
			                                </label>
			                                <div class="col-sm-6" style="padding-top: 7px">
			                                    <fmt:formatDate value="${plotMaster.fromDate}" pattern="dd/MM/yyyy" var="fromDate"/>
			                                    <strong><c:out value="${fromDate}"/></strong>
			                                </div>
			                            </div>
			                            <div class="form-group">
			                                <label class="col-sm-3 control-label">
			                                    <spring:message code="lbl.toDate"/>
			                                </label>
			                                <div class="col-sm-6" style="padding-top: 7px">
			                                    <fmt:formatDate value="${plotMaster.toDate}" pattern="dd/MM/yyyy" var="toDate"/>
			                                    <strong><c:out value="${toDate}" default="NA"/></strong>
			                                </div>
			                            </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                        <div class="text-center">
                            <c:if test="${create}">
                                <button id="buttonCreate" class="btn btn-primary">
                                    <spring:message code="lbl.create"/>&nbsp;<spring:message code="lbl.next"/>
                                </button>
                                <button id="buttonCreateSearch" class="btn btn-primary">
                                    <spring:message code="lbl.create"/>&nbsp;<spring:message code="lbl.search"/>
                                </button>
                            </c:if>
                            <c:if test="${edit}">
                                <button id="buttonEdit" class="btn btn-primary">
                                    <spring:message code="lbl.edit"/>
                                </button>
                                <button id="buttonEditSearch" class="btn btn-primary">
                                    <spring:message code="lbl.edit"/>&nbsp;<spring:message code="lbl.search"/>
                                </button>
                            </c:if>
                            <a href="javascript:void(0)" class="btn btn-default" onclick="self.close()">
                                <spring:message code="lbl.close"/>
                            </a>
                        </div>
                    </div>
                        </form:form>
            </div>
        </div>
    </div>
</div>
<script >
$('#buttonCreate').click(function () {
    $('#plotMasterView').attr('action', '/edcr/plotMaster/create/' + $('#subOccupancyId').val());
});

$('#buttonCreateSearch').click(function () {
    $('#plotMasterView').attr('action', '/edcr/plotMaster/create');
});

$('#buttonEdit').click(function () {
    $('#plotMasterView').attr('action', '/edcr/plotMaster/update/' + $('#plotId').val());
});

$('#buttonEditSearch').click(function () {
    $('#plotMasterView').attr('action', '/edcr/plotMaster/update/');
});
></script>
