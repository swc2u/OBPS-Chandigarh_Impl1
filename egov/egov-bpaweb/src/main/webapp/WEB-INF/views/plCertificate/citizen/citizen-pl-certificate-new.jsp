<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~      accountability and the service delivery of the government  organizations.
  ~
  ~       Copyright (C) <2017>  eGovernments Foundation
  ~
  ~       The updated version of eGov suite of products as by eGovernments Foundation
  ~       is available at http://www.egovernments.org
  ~
  ~       This program is free software: you can redistribute it and/or modify
  ~       it under the terms of the GNU General Public License as published by
  ~       the Free Software Foundation, either version 3 of the License, or
  ~       any later version.
  ~
  ~       This program is distributed in the hope that it will be useful,
  ~       but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~       GNU General Public License for more details.
  ~
  ~       You should have received a copy of the GNU General Public License
  ~       along with this program. If not, see http://www.gnu.org/licenses/ or
  ~       http://www.gnu.org/licenses/gpl.html .
  ~
  ~       In addition to the terms of the GPL license to be adhered to in using this
  ~       program, the following additional terms are to be complied with:
  ~
  ~           1) All versions of this program, verbatim or modified must carry this
  ~              Legal Notice.
  ~
  ~           2) Any misrepresentation of the origin of the material is prohibited. It
  ~              is required that all modified versions of this material be marked in
  ~              reasonable ways as different from the original version.
  ~
  ~           3) This license does not grant any rights to any user of the program
  ~              with regards to rights under trademark law for use of the trade names
  ~              or trademarks of eGovernments Foundation.
  ~
  ~     In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<div class="row">
	<div class="col-md-12">
		<form:form role="form" action="/bpa/application/citizen/pl-certificate/create" method="post" modelAttribute="plinthLevelCertificate"
				   id="plinthLevelCertificateForm" cssClass="form-horizontal form-groups-bordered" enctype="multipart/form-data">
			<input type="hidden" id="noJAORSAMessage" name="noJAORSAMessage" value="${noJAORSAMessage}" />
			<input type="hidden" id="citizenOrBusinessUser" name="citizenOrBusinessUser" value="${citizenOrBusinessUser}" />
			<input type="hidden" id="isCitizen" name="isCitizen" value="${isCitizen}" />
			<input type="hidden" id="cityName" name="cityName" value="${cityName}" />
			<input type="hidden" name="parent" id="bpaApplicationId" value="${plinthLevelCertificate.parent.id}"/>
			<form:hidden path="" id="workFlowAction" name="workFlowAction" />
			<input type="hidden" id="mode" name="mode" value="${mode}" />
			<form:hidden path="plinthLevelCertificateType" id="plinthLevelCertificateType" name="plinthLevelCertificateType" />
			<input type="hidden" id="drawingPreference" value="${plinthLevelCertificate.parent.drawingPreference}">
			<div class="text-right text-info view-content col-sm-12" style="font-size: 14px;color: #e4841b;">
			    <span id="drawPref"></span>
			</div>
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active">
					<a data-toggle="tab" href="#application-info" data-tabidx=0>
						<spring:message code='lbl.appln.details' />
					</a>
				</li>
				<li>
					<a data-toggle="tab" href="#document-info" data-tabidx=1>
						<spring:message code='title.documentdetail' />
					</a>
				</li>
			</ul>
			<div class="tab-content">
				<div id="application-info" class="tab-pane fade in active">
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="pl-application-details.jsp"></jsp:include>
					</div>
					<c:if test="${not empty  plinthLevelCertificate.parent.buildingSubUsages}">
						<div class="panel panel-primary edcrApplnDetails" data-collapsed="0">
							<jsp:include page="../view-building-subusages.jsp" />
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<c:set value="${plinthLevelCertificate.parent.owner}" scope="request" var="owner"></c:set>
						<jsp:include page="../view-applicantdetails.jsp"></jsp:include>
						<c:if test="${not empty plinthLevelCertificate.parent.coApplicants}">
							<c:set value="${plinthLevelCertificate.parent.coApplicants}" scope="request" var="coApplicants"></c:set>						
							<jsp:include page="../view-co-applicant-details.jsp"></jsp:include>							
						</c:if>
					</div>
                    <div class="panel panel-primary" data-collapsed="0">
	                    <jsp:include page="../../common/generic-boundary-view.jsp">
	                    <jsp:param name="boundaryData" value="${plinthLevelCertificate.parent.adminBoundary}:${plinthLevelCertificate.parent.revenueBoundary}:${plinthLevelCertificate.parent.locationBoundary}" />
	                    </jsp:include>
                    </div>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../view-sitedetail.jsp"></jsp:include>
					</div>
					<div class="panel panel-primary demolitionDetails" data-collapsed="0">
						<jsp:include page="../view-demolition-details.jsp" />
					</div>
					<c:if test="${not empty  plinthLevelCertificate.parent.existingBuildingDetails}">
						<div class="panel panel-primary buildingdetails" data-collapsed="0">
							<jsp:include page="../view-existing-building-details.jsp" />
						</div>
					</c:if>
					<div class="panel panel-primary buildingdetails" data-collapsed="0">
						<jsp:include page="../view-building-details.jsp" />
					</div>
				</div>
				<div id="document-info" class="tab-pane fade">
					<c:if test="${not empty plinthLevelCertificate.parent.permitDcrDocuments}">
						<div class="panel panel-primary dcrDocuments" data-collapsed="0">
							<jsp:include page="../view-dcr-documentdetails.jsp"></jsp:include>
						</div>
					</c:if>
					<div class="panel panel-primary" data-collapsed="0">
						<jsp:include page="../view-bpaDocumentdetails.jsp"></jsp:include>
					</div>
				</div>
			</div>
			
			<div align="center">
				<form:button type="submit" id="plSubmit" class="btn btn-primary"
					value="Submit">
					<spring:message code="lbl.submit" />
				</form:button>
				<input type="button" name="button2" id="button2" value="Close"
					class="btn btn-default" onclick="window.close();" />
			</div>
		</form:form>
		<!-- Start --- For javascript messages localization purpose following hidden input tags used -->
		<input type="hidden" id="saveAppln" value="<spring:message code='msg.confirm.save.appln'/>"/>
		<input type="hidden" id="submitAppln" value="<spring:message code='msg.confirm.submit.appln'/>"/>
       <!-- End --- For javascript messages localization purpose following hidden input tags used -->
	</div>
</div>
<link rel="stylesheet" href="<c:url value='/resources/global/css/bts/bts-tagsinput.css?rnd=${app_release_no}' context='/egi'/>">
<script src="<c:url value='/resources/global/js/bts/bts-tagsinput.min.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script src="<c:url value='/resources/global/js/handlebars/handlebars.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/global/js/egov/inbox.js?rnd=${app_release_no}' context='/egi'/>"></script>
<script src="<cdn:url value='/resources/js/app/pl-certificate/pl-certificate-new.js?rnd=${app_release_no}'/>"></script>