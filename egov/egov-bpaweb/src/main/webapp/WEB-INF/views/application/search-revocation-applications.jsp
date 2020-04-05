<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2015>  eGovernments Foundation
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
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<form:form role="form" action=""
	modelAttribute="searchBpaApplicationForm"
	id="searchScrutinyApplicationForm"
	cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading"></div>
				<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.plan.permission.no" /></label>
						<div class="col-sm-3 add-margin">
							<form:input class="form-control patternvalidation" maxlength="50"
								id="planPermissionNumber" path="planPermissionNumber" />
							<form:errors path="planPermissionNumber"
								cssClass="add-margin error-msg" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.fromDate" /></label>
						<div class="col-sm-3 add-margin">
							<form:input path="fromDate" class="form-control datepicker"
								data-date-end-date="0d" id="fromDate"
								data-inputmask="'mask': 'd/m/y'" />
							<form:errors path="fromDate" cssClass="add-margin error-msg" />
						</div>
						<label class="col-sm-2 control-label text-right"><spring:message
								code="lbl.toDate" /></label>
						<div class="col-sm-3 add-margin">
							<form:input path="toDate" class="form-control datepicker"
								data-date-end-date="0d" id="toDate"
								data-inputmask="'mask': 'd/m/y'" />
							<form:errors path="toDate" cssClass="add-margin error-msg" />
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
		<button type="button" id="resetbutton" class="btn btn-danger">
			<spring:message code="lbl.reset" />
		</button>
		<a href='javascript:void(0)' class='btn btn-default'
			onclick='self.close()'><spring:message code='lbl.close' /></a>
	</div>
</form:form>
<div class="row display-hide report-section" id="table_container">
	<div class="col-md-12 table-header text-left"></div>
	<div class="col-md-12 form-group report-table-container">
		<table class="table table-bordered table-hover multiheadertbl"
			id="search_bpa_application_table">
			<thead>
				<tr>
					<th><spring:message code="lbl.applicant.name" /></th>
					<th><spring:message code="lbl.plan.permission.no" /></th>
					<th><spring:message code="lbl.build.plan.permission.date" /></th>
					<th><spring:message code="lbl.service.type" /></th>
					<th><spring:message code="lbl.occupancy" /></th>
					<th><spring:message code="lbl.status" /></th>
					<th><spring:message code="lbl.action" /></th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<script
	src="<cdn:url value='/resources/global/js/bts/bts-datepicker.js' context='/egi'/>"></script>
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/jq/plugins/datatables/jq.dataTables.min.css' context='/egi'/>" />
<link rel="stylesheet"
	href="<cdn:url value='/resources/global/css/jq/plugins/datatables/dataTables.bts.min.css' context='/egi'/>">
<script
	src="<cdn:url value='/resources/global/js/jq/plugins/datatables/jq.dataTables.min.js' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/jq/plugins/datatables/responsive/js/datatables.responsive.js' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/global/js/jq/plugins/datatables/dataTables.bts.js' context='/egi'/>"></script>
<script
	src="<cdn:url value='/resources/js/app/search-for-revocations.js?rnd=${app_release_no}'/> "></script>
