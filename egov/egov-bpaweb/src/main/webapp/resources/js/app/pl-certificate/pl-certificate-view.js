var atkGenDocs = [
	"DOCUMENTATION-01","DOCUMENTATION-02","DOCUMENTATION-03","DOCUMENTATION-10","DOCUMENTATION-11","DOCUMENTATION-12","DOCUMENTATION-13",
	"DOCUMENTATION-14","DOCUMENTATION-15","DOCUMENTATION-16","DOCUMENTATION-18","DOCUMENTATION-19","DOCUMENTATION-06","DOCUMENTATION-07",
	"DOCUMENTATION-08","DOCUMENTATION-09","DOCUMENTATION-17","DOCUMENTATION-22","DOCUMENTATION-34","DOCUMENTATION-23","DOCUMENTATION-24",
	"DOCUMENTATION-25","DOCUMENTATION-26","DOCUMENTATION-27"
];

var btkGenDocs = [
	"DOCUMENTATION-04","DOCUMENTATION-05","DOCUMENTATION-10","DOCUMENTATION-11","DOCUMENTATION-12","DOCUMENTATION-13","DOCUMENTATION-14",
	"DOCUMENTATION-15","DOCUMENTATION-16","DOCUMENTATION-21","DOCUMENTATION-06","DOCUMENTATION-07","DOCUMENTATION-08","DOCUMENTATION-09",
	"DOCUMENTATION-17","DOCUMENTATION-22","DOCUMENTATION-26","DOCUMENTATION-27"
];

var ruralDocs = [
	"DOCUMENTATION-01","DOCUMENTATION-28","DOCUMENTATION-29","DOCUMENTATION-30","DOCUMENTATION-31","DOCUMENTATION-32","DOCUMENTATION-33"
];

jQuery(document).ready(function() {
	
	var uom = $('#drawingPreference').val();
	if(uom == "feet"){
		$('#drawPref').html("All dimensions are in feet or square feet.");
	}else{
		$('#drawPref').html("All dimensions are in metre or square metre.");
	}
	
	$(".genDocs").each(function() {
		var docId = $(this).attr('id');
		var appType=$('#appType').val();
		var docArr=[];
    	if(appType=="High Risk"){
    		docArr=atkGenDocs;
    	}else if(appType=="Low Risk"){
    		docArr=btkGenDocs;
    	}else if(appType=="Medium Risk"){
    		docArr=ruralDocs;
    	}
    	var isExist=false;
    	$.each(docArr, function( index, value ) {	   				
			if(docId == value){ 
			    isExist=true; 				    				    
			}        				       				
		});
    	if(!isExist){
    		$(this).hide();
    	}
	});
	
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