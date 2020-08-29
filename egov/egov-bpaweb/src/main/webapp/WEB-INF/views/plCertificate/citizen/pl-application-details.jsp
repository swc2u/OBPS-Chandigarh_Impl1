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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="panel-heading custom_form_panel_heading">
	<div class="panel-title">
		<spring:message code="lbl.basic.info" />
	</div>
</div>
<div class="panel-body">
	<c:if
		test="${plinthLevelCertificate.parent.planPermissionNumber ne null && plinthLevelCertificate.parent.planPermissionNumber ne ''}">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.build.plan.permission.date" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<fmt:formatDate value="${plinthLevelCertificate.parent.planPermissionDate}"
					pattern="dd/MM/yyyy" var="planPermissionDate" />
				<c:out value="${planPermissionDate}" default="N/A"></c:out>
			</div>
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.plan.permission.no" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${plinthLevelCertificate.parent.planPermissionNumber}" default="N/A"></c:out>
			</div>
		</div>
	</c:if>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.occupancy" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out
				value="${plinthLevelCertificate.parent.occupanciesName ne '' ?  plinthLevelCertificate.parent.occupanciesName : 'N/A'}"></c:out>
		</div>
		<c:if
			test="${plinthLevelCertificate.parent.eDcrNumber ne null && plinthLevelCertificate.parent.eDcrNumber ne ''}">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.edcr.number" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<input type="hidden" id="eDcrNumber"
					value="${plinthLevelCertificate.parent.eDcrNumber}">
				<c:out value="${plinthLevelCertificate.parent.eDcrNumber}" default="N/A"></c:out>
			</div>
		</c:if>
	</div>

	<c:if test="${plinthLevelCertificate.parent.isOneDayPermitApplication}">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.applctn.type" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<input type="hidden" id="isOneDayPermitApplication"
					value="${plinthLevelCertificate.parent.applicationType.name}">
				<c:out
					value="${plinthLevelCertificate.parent.applicationType.description}"
					default="N/A"></c:out>
			</div>

			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.type.land" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${plinthLevelCertificate.parent.typeOfLand}" default="N/A"></c:out>
			</div>
		</div>
	</c:if>
	<c:if test="${plinthLevelCertificate.parent.siteDetail[0].isappForRegularization}">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.if.regularized" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out
					value="${plinthLevelCertificate.parent.siteDetail[0].isappForRegularization ? 'YES' : 'NO'}"></c:out>
			</div>
		</div>
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.cons.stages" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out
					value="${plinthLevelCertificate.parent.siteDetail[0].constStages.description}"
					default="N/A"></c:out>
			</div>
			<c:if
				test="${plinthLevelCertificate.parent.siteDetail[0].constStages.description eq 'In Progress'}">
				<div class="col-sm-3 add-margin">
					<spring:message code="lbl.if.cons.not.cmplted" />
				</div>
				<div class="col-sm-3 add-margin view-content">
					<c:out value="${plinthLevelCertificate.parent.siteDetail[0].stateOfConstruction}"
						default="N/A"></c:out>
				</div>
			</c:if>
		</div>
		<div class="row add-border">
			<c:choose>
				<c:when
					test="${plinthLevelCertificate.parent.siteDetail[0].constStages.description eq 'In Progress'}">
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.work.commence.date" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<fmt:formatDate
							value="${plinthLevelCertificate.parent.siteDetail[0].workCommencementDate}"
							pattern="dd/MM/yyyy" var="workCommencementDate" />
						<c:out value="${workCommencementDate}" default="N/A"></c:out>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.work.commence.date" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<fmt:formatDate
							value="${plinthLevelCertificate.parent.siteDetail[0].workCommencementDate}"
							pattern="dd/MM/yyyy" var="workCommencementDate1" />
						<c:out value="${workCommencementDate1}" default="N/A"></c:out>
					</div>
					<div class="col-sm-3 add-margin">
						<spring:message code="lbl.work.completion.date" />
					</div>
					<div class="col-sm-3 add-margin view-content">
						<fmt:formatDate
							value="${plinthLevelCertificate.parent.siteDetail[0].workCompletionDate}"
							pattern="dd/MM/yyyy" var="workCompletionDate" />
						<c:out value="${workCompletionDate}" default="N/A"></c:out>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.service.type" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<input type="hidden" id="serviceType"
				value="${plinthLevelCertificate.parent.serviceType.description}">
			<c:out value="${plinthLevelCertificate.parent.serviceType.description}"
				default="N/A"></c:out>
		</div>
	</div>
	<c:if
		test="${ empty  plinthLevelCertificate.parent.receipts && (plinthLevelCertificate.parent.status.code eq 'Created' || plinthLevelCertificate.parent.status.code eq 'Registered')}">
		<div class="row add-border">
			<div class="col-sm-3 add-margin">
				<spring:message code="lbl.admission.fees" />
			</div>
			<div class="col-sm-3 add-margin view-content">
				<c:out value="${plinthLevelCertificate.parent.admissionfeeAmount}" default="N/A"></c:out>
			</div>
		</div>
	</c:if>
	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.stakeholder.type" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out
				value="${plinthLevelCertificate.parent.stakeHolder[0].stakeHolder.stakeHolderType.name}"
				default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.stakeholder.name" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${plinthLevelCertificate.parent.stakeHolder[0].stakeHolder.name}"
				default="N/A"></c:out>
		</div>
	</div>

	<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.application.no" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<c:out value="${plinthLevelCertificate.parent.applicationNumber}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.application.date" />
		</div>
		<div class="col-sm-3 add-margin view-content">
			<fmt:formatDate value="${plinthLevelCertificate.parent.applicationDate}"
				pattern="dd/MM/yyyy" var="applicationDate" />
			<c:out value="${applicationDate}" default="N/A"></c:out>
		</div>
	</div>
		<div class="row add-border">
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.remarks" />
		</div>
		<div class="col-sm-3 add-margin view-content text-justify">
			<c:out value="${plinthLevelCertificate.parent.remarks}" default="N/A"></c:out>
		</div>
		<div class="col-sm-3 add-margin">
			<spring:message code="lbl.application.type" />
		</div>
		<div class="col-sm-3 add-margin view-content text-justify">
			<c:out value="${plinthLevelCertificate.parent.applicationType.description}" default="N/A"></c:out>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.pl.contst.start.date" /><span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<form:input path="commencedDate" class="form-control datepicker"
				data-date-end-date="0d" id="commencedDate"
				data-inputmask="'mask': 'd/m/y'" required="required" />
			<form:errors path="commencedDate" cssClass="add-margin error-msg" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.pl.contst.reached.to.pl" /><span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<form:radiobutton path = "isConstructionReachedToPL" value = "true" label = "Yes" />
            <form:radiobutton path = "isConstructionReachedToPL" value = "false" label = "No" />
			<form:errors path="isConstructionReachedToPL" cssClass="add-margin error-msg" />
		</div>
		<label class="col-sm-3 control-label text-right"><spring:message
				code="lbl.pl.contst.done" /><span class="mandatory"></span> </label>
		<div class="col-sm-3 add-margin">
			<form:radiobutton path = "isConstructionDone" value = "true" label = "Yes" />
            <form:radiobutton path = "isConstructionDone" value = "false" label = "No" />
			<form:errors path="isConstructionDone" cssClass="add-margin error-msg" />
		</div>
	</div>
</div>
