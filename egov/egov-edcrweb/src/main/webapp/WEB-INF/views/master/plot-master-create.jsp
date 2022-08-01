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
<%@ taglib prefix="egov" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn" %>
<div class="row" id="page-content">
    <div class="col-md-12">
        <div class="panel" data-collapsed="0">
            <div class="panel-body">
                <c:if test="${not empty warning}">
                    <div class="alert alert-danger" role="alert"><spring:message code="${warning}"/></div>
                </c:if>
                <c:choose>
                    <c:when test="${search}">
                        <form:form id="plotMasterCreateSearchForm" class="form-horizontal form-groups-bordered" method="get">
                            <div class="panel panel-primary" data-collapsed="0">
                                <div class="panel-heading">
                                    <div class="panel-title">
                                        <strong><spring:message code="lbl.hdr.searchPlot"/></strong>
                                    </div>
                                </div>

                                <div class="panel-body custom-form">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <spring:message code="lbl.Occupancy"/>
                                            <span class="mandatory"></span>
                                        </label>
                                        <div class="col-sm-6 add-margin">
                                            <select id="occupancySelect" class="form-control" onchange="populateSubOccupancy(this);" required="required">
                                                <option value=""><spring:message code="lbl.select"/></option>
                                                <c:forEach items="${occupancy}" var="occupancy">
                                                    <option value="${occupancy.id}">${occupancy.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label"><spring:message
                                                code="lbl.subOccupancy"/><span class="mandatory"></span></label>
                                        <div class="col-sm-6 add-margin">
                                            <egov:ajaxdropdown id="subOccupancyAjax" fields="['Text','Value']"
                                                               dropdownId="subOccupancy" url="suboccupancy/ajax/suboccupancylist-for-hierarchy"/>
                                            <select id="subOccupancy" class="form-control" required="required">
                                                <option value=""><spring:message code="lbl.select"/></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="text-center">
                                <button id="searchBtn" type="button" class="btn btn-primary"><spring:message code="lbl.search"/></button>
                                <button type="reset" class="btn btn-default"><spring:message code="lbl.reset"/></button>
                                <a href="javascript:void(0)" class="btn btn-default" onclick="self.close()"><spring:message code="lbl.close"/></a>
                            </div>

                        </form:form>
                    </c:when>
                    <c:otherwise>
                        <form:form method="post" action="/edcr/plotMaster/create" class="form-horizontal form-groups-bordered" commandName="plotMaster" id="plotMasterCreate">
                            <div class="panel panel-primary" data-collapsed="0">
                                <div class="panel-heading">
                                    <div class="panel-title">
                                        <strong><spring:message code="lbl.hdr.createBoundary"/></strong>
                                    </div>
                                </div>

                                <div class="panel-body custom-form">
                                   <div class="form-group">
                                        <label class="col-sm-3 control-label"><spring:message code="lbl.occupancy"/></label>
                                        <div class="col-sm-6" style="padding-top: 7px">
                                            <strong><c:out value="${subOccupancy.occupancy.name}"/></strong>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <spring:message code="lbl.subOccupancy"/>
                                        </label>
                                        <div class="col-sm-6" style="padding-top: 7px">
                                            <strong><c:out value="${subOccupancy.name}"/></strong>
                                            <input type="hidden" name="boundaryType" value="<c:out value="${subOccupancy.id}" />"/>
                                        </div>
                                    </div>
                                    
                                    <div class="panel-body custom-form">
                                       
                                       
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.code"/><span class="mandatory"></span>
                                            </label>
                                            <div class="col-sm-6">
                                                <form:input path="code" id="code" type="text" class="form-control low-width patternvalidation" data-pattern="masterCode" value="${subOccupancy.code}" readonly="true" autocomplete="off"  maxlength="25"/>
                                            </div>
                                        </div>
                                         <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.phase"/><span class="mandatory"></span>
                                            </label>
                                            <div class="col-sm-6">
                                                <form:input path="phase" id="phase" type="text" class="form-control low-width patternvalidation" data-pattern="specialName" placeholder="" autocomplete="off" required="required"/>
                                                <form:errors path="phase" cssClass="add-margin error-msg"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.local.sector"/>
                                            </label>
                                            <div class="col-sm-6">
                                                <form:input path="sector" id="sector" type="text" class="form-control low-width patternvalidation" data-pattern="specialName" placeholder="" autocomplete="off"/>
                                                <form:errors path="sector" cssClass="add-margin error-msg"/>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.plot.number"/><span class="mandatory"></span>
                                            </label>
                                            <div class="col-sm-6">
                                                <form:input path="plotNum" id="name" type="text" class="form-control low-width is_valid_number" placeholder="" autocomplete="off" required="required"/>
                                                <form:errors path="plotNum" cssClass="add-margin error-msg"/>
                                            </div>
                                        </div>
                                        
                                        
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary"><spring:message code="lbl.submit"/></button>
                                    <a href="javascript:void(0)" class="btn btn-default" id="backBtnId">
                                        <spring:message code="lbl.create"/>&nbsp;<spring:message code="lbl.search"/>
                                    </a>
                                    <a href="javascript:void(0)" class="btn btn-default" onclick="self.close()"><spring:message code="lbl.close"/></a>
                                </div>
                            </div>
                        </form:form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<script src="<cdn:url  value='/resources/js/app/boundary-create.js?rnd=${app_release_no}'/>"></script>
