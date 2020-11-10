<!-- #-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2017>  eGovernments Foundation
# 
#     The updated version of eGov suite of products as by eGovernments Foundation 
#     is available at http://www.egovernments.org
# 
#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     any later version.
# 
#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
# 
#     You should have received a copy of the GNU General Public License
#     along with this program. If not, see http://www.gnu.org/licenses/ or 
#     http://www.gnu.org/licenses/gpl.html .
# 
#     In addition to the terms of the GPL license to be adhered to in using this
#     program, the following additional terms are to be complied with:
# 
# 	1) All versions of this program, verbatim or modified must carry this 
# 	   Legal Notice.
# 
# 	2) Any misrepresentation of the origin of the material is prohibited. It 
# 	   is required that all modified versions of this material be marked in 
# 	   reasonable ways as different from the original version.
# 
# 	3) This license does not grant any rights to any user of the program 
# 	   with regards to rights under trademark law for use of the trade names 
# 	   or trademarks of eGovernments Foundation.
# 
#   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#------------------------------------------------------------------------------- -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="panel-heading slide-history-menu">
	<div class="panel-title">
		<spring:message code="lbl.apphistory" />
	</div>
	<div class="history-icon">
		<i class="fa fa-angle-up fa-2x" id="toggle-his-icon"></i>
	</div>
</div>
<div class="panel-body history-slide display-hide">
	<div
		class="row add-margin hidden-xs visible-sm visible-md visible-lg header-color">
		<div class="col-sm-2 col-xs-6 add-margin">
			<spring:message code="lbl.date" />
		</div>
		<div class="col-sm-2 col-xs-6 add-margin">
			<spring:message code="lbl.updatedby" />
		</div>
		<div class="col-sm-2 col-xs-6 add-margin">
			<spring:message code="lbl.status" />
		</div>
		<div class="col-sm-2 col-xs-6 add-margin currentOwner">
			<spring:message code="lbl.currentowner" />
		</div>
		<div class="col-sm-2 col-xs-6 add-margin">
			<spring:message code="lbl.department" />
		</div>
		<div class="col-sm-2 col-xs-6 add-margin">
			<spring:message code="lbl.comments" />
		</div>
	</div>
	<c:choose>
		<c:when test="${!applicationHistory.isEmpty()}">
			<c:forEach items="${applicationHistory}" var="history">
				<div class="row add-margin">
					<div class="col-sm-2 col-xs-12 add-margin">
						<fmt:formatDate value="${history.date}" var="historyDate"
							pattern="dd/MM/yyyy HH:mm a E" />
						<c:out value="${historyDate}" />
					</div>
					<div class="col-sm-2 col-xs-12 add-margin">
						<c:out value="${history.updatedBy}" />
					</div>
					<div class="col-sm-2 col-xs-12 add-margin">
						<c:out value="${history.status}" />
					</div>
					<div class="col-sm-2 col-xs-12 add-margin currentOwner">
						<c:out value="${history.user}" />
					</div>
					<div class="col-sm-2 col-xs-12 add-margin">
						<c:out value="${history.department}" />
					</div>
					<div class="col-sm-2 col-xs-12 add-margin text-justify">
						<c:choose>
							<c:when test="${history.comments.length() < 70}">
								<%-- <c:out value="${history.comments}" />&nbsp; --%>
							<c:out value="${history.comments}"></c:out>&nbsp;
						</c:when>
							<c:otherwise>
								<a href="#" onclick="showComent('${history.comments}')">click here</a>&nbsp;
						</c:otherwise>
						</c:choose>
					</div>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div class="col-md-3 col-xs-6 add-margin">No history Present.</div>
		</c:otherwise>
	</c:choose>
</div>
<button id="myBtn" class="display:none"></button>
<!-- The Modal -->
<div id="myModal" class="modal" style="top:195px;left:20px;right:20px;">
  <!-- Modal content -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <p id="content">...</p>
  </div>
</div>
<script>
	$('.slide-history-menu').click(
			function() {
				$('.history-slide').slideToggle();
				if ($('#toggle-his-icon').hasClass('fa fa-angle-down')) {
					$('#toggle-his-icon').removeClass('fa fa-angle-down')
							.addClass('fa fa-angle-up');
					//$('#see-more-link').hide();
				} else {
					$('#toggle-his-icon').removeClass('fa fa-angle-up')
							.addClass('fa fa-angle-down');
					//$('#see-more-link').show();
				}
			});
</script>

<script>

var modal = document.getElementById("myModal");
function showComent(data){
document.getElementById("content").innerHTML = data;
modal.style.display = "block";
}

// Get the modal


// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btn.onclick = function() {
  
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
</script>