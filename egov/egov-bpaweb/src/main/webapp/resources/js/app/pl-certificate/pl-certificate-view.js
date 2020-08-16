jQuery(document).ready(function() {
	
	var uom = $('#drawingPreference').val();
	if(uom == "feet"){
		$('#drawPref').html("All dimensions are in feet or square feet.");
	}else{
		$('#drawPref').html("All dimensions are in metre or square metre.");
	}
	
	//Don't allow double quotes in approval comments, replace double quotes(") with single quote(')
	$('#approvalComent').keyup(function () {
	    $(this).val($(this).val().replace(/["]/g, "'"));
	});
		
	$("#plinthLevelCertificateUpdateForm").validate({
		highlight : function(element, errorClass) {
			$(element).fadeOut(function() {
				$(element).fadeIn();
			});
		}
	});					

	// By default to point update noc details tab
	var mode = $('#mode').val();
					
	if ($('#wfstateDesc').val() != 'Registered'
			&& $('#wfstateDesc').val() != 'NEW'
				&& mode == 'newappointment') {
		$(".show-row").hide();
		$("#Forward").hide();
        $("#approverDetailBody").hide();
	}
	
	if ($('#wfstateDesc').val() == 'NEW'
			&& mode == 'newappointment') {
		removeWorkFlowMandatoryAndHideDepartmentDetails();
	}
	if ($('#wfstateDesc').val() == 'Rejected') {
		$("#approverDetailBody").hide();
	} else if($('#wfstateDesc').val() === 'Record Approved') {
        removeWorkFlowMandatoryAndHideDepartmentDetails();
        $("#approverDetailBody").hide();
	}

	function removeWorkFlowMandatoryAndHideDepartmentDetails() {
		$('#approvalDepartment').removeAttr('required');
		$('#approvalDesignation').removeAttr('required');
		$('#approvalPosition').removeAttr('required');
        $("#approverDetailBody").hide();
	}

	var tabfocus;
	if ($('#wfstateDesc').val() == 'Document Verified') {
        tabfocus = '#view-inspection';
    } else if ($('#wfstateDesc').val() == 'Site Inspected') {
        tabfocus = '#view-inspection';
    } else if($('#wfstateDesc').val() == 'Scheduled For Site Inspection'
        || $('#wfstateDesc').val() == 'Rescheduled For Site Inspection') {
        tabfocus = '#doc-scrutiny-info';
    } else {
		tabfocus = '#applicant-info';
	}

	var prefix = "tab_";
	if (tabfocus) {
		$('.nav-tabs a[href="'+ tabfocus.replace(prefix, "") + '"]').tab('show');
	}

	// toggle between multiple tab
	jQuery('form').validate({
		ignore : ".ignore",
		invalidHandler : function(e, validator) {
			if (validator.errorList.length)
				$(
						'#settingstab a[href="#'
								+ jQuery(
										validator.errorList[0].element)
										.closest(
												".tab-pane")
										.attr(
												'id')
								+ '"]').tab(
						'show');
		}
	});
	
	var seviceTypeName = $("#serviceType").val();
	$('.show-hide').hide();
	$('.areaOfBase').hide();
	$('.extentOfLand').show();     
});