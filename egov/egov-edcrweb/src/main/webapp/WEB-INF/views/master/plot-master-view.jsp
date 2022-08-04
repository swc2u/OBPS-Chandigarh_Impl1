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
 
                        <form:form method="post" class="form-horizontal form-groups-bordered" commandName="plotMaster" id="plotMasterView" modelAttribute="plotMaster">
                            <div class="panel panel-primary" data-collapsed="0">
                                <div class="panel-heading">
                                    <div class="panel-title">
                                        <strong><spring:message code="lbl.hdr.viewPlotMaster"/></strong>
                                    </div>
                                </div>
							<div class="panel-body custom-form">
<!--                                    <div class="form-group"> -->
<%--                                         <label class="col-sm-3 control-label"><spring:message code="lbl.occupancy"/></label> --%>
<!--                                         <div class="col-sm-6" style="padding-top: 7px"> -->
<%--                                             <strong><c:out value="${subOccupancy.occupancy.name}"/></strong> --%>
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                     <div class="form-group"> -->
<!--                                         <label class="col-sm-3 control-label"> -->
<%--                                             <spring:message code="lbl.subOccupancy"/> --%>
<!--                                         </label> -->
<!--                                         <div class="col-sm-6" style="padding-top: 7px"> -->
<%-- <%--                                         <form:input path="allowedsuboccupancy.SubOccupancy.name" id="subOccupancy" type="text" class="form-control low-width patternvalidation" data-pattern="masterCode" value="${subOccupancy.name}" readonly="true" autocomplete="off"  maxlength="25"/> --%> 
<%--                                            <strong><c:out value="${subOccupancy.name}"/></strong> --%>
<%--                                             <input type="hidden" name="subOccupancyId" value="<c:out value="${subOccupancy.id}" />"/> --%>
<!--                                         </div> -->
<!--                                     </div> -->
                                    
                                    <div class="panel-body custom-form">
                                       
                                       
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.code"/>
                                            </label>
                                            <div class="col-sm-6">
                                             <strong><c:out value="${plotMaster.code}"/></strong>
                                    			<input type="hidden" id="code" value="<c:out value="${plotMaster.code}" />"/>
                                                
                                            </div>
                                        </div>
                                         <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.phase"/>
                                            </label>
                                            <div class="col-sm-6">
                                            <strong><c:out value="${plotMaster.allowedsuboccupancy.plot.phase}"/></strong>
                                    			<input type="hidden" id="phase" value="<c:out value="${plotMaster.allowedsuboccupancy.plot.phase}" />"/>
                                                
                                               </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <spring:message code="lbl.local.sector"/>
                                            </label>
                                            <div class="col-sm-6">
                                                <strong><c:out value="${plotMaster.allowedsuboccupancy.plot.boundary.name}"/></strong>
                                    			<input type="hidden" id="sector" value="<c:out value="${plotMaster.allowedsuboccupancy.plot.boundary.name}" />"/>
                                                
                                                </div>
                                        </div>

<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.plot.number"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="allowedsuboccupancy.plot.plotNum" id="plotNum" type="text" class="form-control low-width is_valid_number" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="allowedsuboccupancy.plot.plotNum" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.plot.area"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="allowedsuboccupancy.plot.plotArea" id="plotArea" type="number" class="form-control low-width " step="0.01" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="allowedsuboccupancy.plot.plotArea" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.plot.areatype"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="allowedsuboccupancy.plot.areaType" id="areaType" type="text" class="form-control low-width patternvalidation" data-pattern="specialName" placeholder="" autocomplete="off"/> --%>
<%--                                                 <form:errors path="allowedsuboccupancy.plot.areaType" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                          <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.plot.depth"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="allowedsuboccupancy.plot.plotDepth" id="plotDepth" type="text" class="form-control low-width is_valid_alphanumeric" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="allowedsuboccupancy.plot.plotDepth" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                          <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.plot.width"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="allowedsuboccupancy.plot.plotWidth" id="plotWidth" type="text" class="form-control low-width is_valid_alphanumeric" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="allowedsuboccupancy.plot.plotWidth" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.bcy.width"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="backCourtyardWidth" id="bcyWidth" type="text" class="form-control low-width is_valid_alphanumeric" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="backCourtyardWidth" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.bcy.height"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="backCourtyardHeight" id="bcyHeight" type="text" class="form-control low-width is_valid_alphanumeric" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="backCourtyardHeight" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.perm.stories"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="permissibleBuildingStories" id="permStories" type="text" class="form-control low-width is_valid_number" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="permissibleBuildingStories" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.perm.height"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="permissibleBuildingHeight" id="permHeight" type="number"  step="0.01" class="form-control low-width " placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="permissibleBuildingHeight" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                          <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.perm.far"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="maxmimumPermissibleFAR" id="permFAR" type="text" class="form-control low-width is_valid_number" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="maxmimumPermissibleFAR" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.perm.setback.front"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="minimumPermissibleSetback_Front" id="setbackFront" type="text" class="form-control low-width is_valid_alphanumeric" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="minimumPermissibleSetback_Front" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.perm.setback.rear"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="minimumPermissibleSetback_Rear" id="setbackRear" type="text" class="form-control low-width is_valid_alphanumeric" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="minimumPermissibleSetback_Rear" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.perm.setback.left"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="minimumPermissibleSetback_Left" id="setbackLeft" type="text" class="form-control low-width is_valid_alphanumeric" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="minimumPermissibleSetback_Left" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="form-group"> -->
<!--                                             <label class="col-sm-3 control-label"> -->
<%--                                                 <spring:message code="lbl.perm.setback.right"/><span class="mandatory"></span> --%>
<!--                                             </label> -->
<!--                                             <div class="col-sm-6"> -->
<%--                                                 <form:input path="minimumPermissibleSetback_Right" id="setbackRight" type="text" class="form-control low-width is_valid_alphanumeric" placeholder="" autocomplete="off" required="required"/> --%>
<%--                                                 <form:errors path="minimumPermissibleSetback_Right" cssClass="add-margin error-msg"/> --%>
<!--                                             </div> -->
<!--                                         </div> -->
                                        
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="text-center">
                                    <button type="submit" class="btn btn-primary"><spring:message code="lbl.submit"/></button>
<!--                                     <a href="javascript:void(0)" class="btn btn-default" id="backBtnId"> -->
<%--                                         <spring:message code="lbl.create"/>&nbsp;<spring:message code="lbl.search"/> --%>
<!--                                     </a> -->
                                    <a href="javascript:void(0)" class="btn btn-default" onclick="self.close()"><spring:message code="lbl.close"/></a>
                                </div>
                            </div>
                                
                        </form:form>
            </div>
        </div>
    </div>
</div>
<script src="<cdn:url  value='/resources/app/js/plot-master-create.js?rnd=${app_release_no}'/>"></script>
