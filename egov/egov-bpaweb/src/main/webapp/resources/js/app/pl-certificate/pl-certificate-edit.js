jQuery(document).ready(function () {
	
	var row = '<tr>' +
	    '<td class="text-center"><span class="serialNo text-center" id="slNoInsp">{{sno}}</span><input type="hidden" name="additionalRejectReasonsTemp[{{idx}}].oc" value="{{applicationId}}" />'
	    +'<input type="hidden" class="additionalPermitCondition" name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.type" value="ADDITIONALREJECTIONREASONS"/>'
	    +'<input type="hidden" class="additionalPermitCondition" name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.checklistServicetype" value="{{permitConditionId}}"/>'
	    +'<input type="hidden" class="serialNo" data-sno name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.orderNumber"/></td>' +
	    '<td><textarea class="form-control patternvalidation additionalPermitCondition" data-pattern="alphanumericspecialcharacters" rows="2" maxlength="500" name="additionalRejectReasonsTemp[{{idx}}].noticeCondition.additionalCondition"/></td>';
	
	var tbody = $('#bpaAdditionalRejectionReasons').children('tbody');
	var table = tbody.length ? tbody : $('#bpaAdditionalRejectionReasons');
	$('#addAddnlRejectRow').click(function () {
	    var idx = $(tbody).find('tr').length;
	    if(validateAdditionalConditionsOrReasonsOnAdd('bpaAdditionalRejectionReasons')) {
	    	//Add row
	        var row = {
	            'sno': idx + 1,
	            'idx': idx,
	            'permitConditionId': $('#additionalPermitCondition').val(),
	            'applicationId': $('#scrutinyapplicationid').val()
	        };
	        addRowFromObject(row);
	        patternvalidation();
	    }
	});
	
	function validateAdditionalConditionsOrReasonsOnAdd(tableId){
		var isValid=true;
	    $('#'+tableId+' tbody tr').each(function(index){
	    	var additionalPermitCondition  = $(this).find('*[name$="additionalCondition"]').val();
		    if(!additionalPermitCondition) { 
		    	bootbox.alert($('#valuesCannotEmpty').val());
		    	isValid=false;
		    	return false;
		    } 
	    });
	    return isValid;
	}
	
	function addRowFromObject(rowJsonObj) {
	    table.append(row.compose(rowJsonObj));
	}
	
	String.prototype.compose = (function () {
        var re = /\{{(.+?)\}}/g;
        return function (o) {
            return this.replace(re, function (_, k) {
                return typeof o[k] != 'undefined' ? o[k] : '';
            });
        }
    }());
	
	// toggle between multiple tab
    jQuery('form').validate({
        ignore: ".ignore",
        invalidHandler: function (e, validator) {
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
    
    $(".rejectionReasons").change(function () {
        setCheckBoxValue($(this));
    });

    function setCheckBoxValue(currentVal) {
        var $hiddenName = currentVal.data('change-to');
        if (currentVal.is(':checked')) {
            $('input[name="' + $hiddenName + '"]').val(true);
        } else {
            $('input[name="' + $hiddenName + '"]').val(false);
        }
    }

    var validator = $("#plinthLevelCertificateUpdateForm").validate({
        highlight: function (element, errorClass) {
            $(element).fadeOut(function () {
                $(element).fadeIn();
            });
        }
    });
    
    $(".workAction").click(function (e) {
            var action = document.getElementById("workFlowAction").value;
        if (action === 'Reject') {
            $('#Reject').attr('formnovalidate', 'true');
            if (validateForm(validator) && validateOnReject(true)) {
                bootbox
                    .dialog({
                        message: $('#rejectAppln').val(),
                        buttons: {
                        	'confirm': {
                                label: 'Yes',
                                className: 'btn-primary',
                                callback: function (result) {
                                    $('#plinthLevelCertificateUpdateForm').trigger('submit');
                                }
                            },
                            'cancel': {
                                label: 'No',
                                className: 'btn-danger',
                                callback: function (result) {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        }
                    });
            } else {
                e.preventDefault();
            }
            return false;
        } else if (action === 'Initiate Rejection') {
            if (validateForm(validator) && validateOnReject(true)) {
                bootbox
                    .dialog({
                        message: $('#intiateRejectionAppln').val(),
                        buttons: {
                            'confirm': {
                                label: 'Yes',
                                className: 'btn-primary',
                                callback: function (result) {
                                    $('#plinthLevelCertificateUpdateForm').trigger('submit');
                                }
                            },
                            'cancel': {
                                label: 'No',
                                className: 'btn-danger',
                                callback: function (result) {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        }
                    });
            } else {
                e.preventDefault();
            }
            return false;
        } else if (action === 'Revert') {
            if (validateForm(validator) && validateOnRevert()) {
                bootbox
                    .dialog({
                        message: $('#sendBackApplnPreOfficial').val(),
                        buttons: {
                            'confirm': {
                                label: 'Yes',
                                className: 'btn-primary',
                                callback: function (result) {
                                    $('#plinthLevelCertificateUpdateForm').trigger('submit');
                                }
                            },
                            'cancel': {
                                label: 'No',
                                className: 'btn-danger',
                                callback: function (result) {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        }
                    });
            } else {
                e.preventDefault();
            }
            return false;
        } else if (action === 'Approve') {
            if (validateOnApproveAndForward(validator, action)) {
                bootbox
                    .dialog({
                        message: $('#approveAppln').val(),
                        buttons: {
                        	'confirm': {
                                label: 'Yes',
                                className: 'btn-primary',
                                callback: function (result) {
                                    $('#plinthLevelCertificateUpdateForm').trigger('submit');
                                }
                            },
                            'cancel': {
                                label: 'No',
                                className: 'btn-danger',
                                callback: function (result) {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        }
                    });
            } else {
                e.preventDefault();
            }
            return false;
        } else if (action === 'Forward') {
            if (validateOnApproveAndForward(validator, action)) {
                bootbox
                    .dialog({
                        message: $('#forwardAppln').val(),
                        buttons: {
                        	'confirm': {
                                label: 'Yes',
                                className: 'btn-primary',
                                callback: function (result) {
                                    $('#plinthLevelCertificateUpdateForm').trigger('submit');
                                }
                            },
                            'cancel': {
                                label: 'No',
                                className: 'btn-danger',
                                callback: function (result) {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        }
                    });
            } else {
                e.preventDefault();
            }
            return false;
        } else if (action === 'Generate Plinth Level Certificate') {
            $('#Generate Plinth Level Certificate').attr('formnovalidate', 'true');
            if (validateOnApproveAndForward(validator, action)) {
                bootbox
                    .dialog({
                        message: $('#generatePLC').val(),
                        buttons: {
                        	'confirm': {
                                label: 'Yes',
                                className: 'btn-primary',
                                callback: function (result) {
                                    $('#plinthLevelCertificateUpdateForm').trigger('submit');
                                }
                            },
                            'cancel': {
                                label: 'No',
                                className: 'btn-danger',
                                callback: function (result) {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        }
                    });
            } else {
                e.preventDefault();
            }
            return false;
        } else if (action === 'Generate Rejection Notice') {
            if (validateOnApproveAndForward(validator, action)) {
                bootbox
                    .dialog({
                        message: $('#generateRejectNotice').val(),
                        buttons: {
                        	'confirm': {
                                label: 'Yes',
                                className: 'btn-primary',
                                callback: function (result) {
                                    $('#plinthLevelCertificateUpdateForm').trigger('submit');
                                }
                            },
                            'cancel': {
                                label: 'No',
                                className: 'btn-danger',
                                callback: function (result) {
                                    e.stopPropagation();
                                    e.preventDefault();
                                }
                            }
                        }
                    });
            } else {
                e.preventDefault();
            }
            return false;
        } else {
            validateOnApproveAndForward(validator, action);
        }
    });

	$("#btnSave").click(function (e) {document.getElementById("workFlowAction").value = 'Save';
        if(validateForm(validator))
            $('#plinthLevelCertificateUpdateForm').trigger('submit');
    });
});

function validateOnReject(isCommentsRequire) {
    var approvalComent = $('#approvalComent').val();
    var rejectionReasonsLength = $('.rejectionReasons:checked').length;
    if (rejectionReasonsLength <= 0) {
        $('.rejectionReason').show();
        bootbox.alert($('#rejectionReasonMandatory').val());
        return false;
    } else if (approvalComent === "" && isCommentsRequire) {
        bootbox.alert($('#rejectionCommentsRequired').val());
        $('#approvalComent').focus();
        return false;
    }
    return true;
}

function validateOnRevert() {
    var approvalComent = $('#approvalComent').val();
    if (approvalComent === "") {
        $('#approvalComent').focus();
        bootbox.alert($('#applnSendbackCommentsRequired').val());
        return false;
    }
    return true;
}

function validateForm(validator) {
    if ($('#plinthLevelCertificateUpdateForm').valid()) {
        return true;
    } else {
        $errorInput = undefined;

        $.each(validator.invalidElements(), function (index, elem) {

            if (!$(elem).is(":visible") && !$(elem).val() && index === 0
                && $(elem).closest('div').find('.bootstrap-tagsinput').length > 0) {
                $errorInput = $(elem);
            }

            if (!$(elem).is(":visible") && !$(elem).closest('div.panel-body').is(":visible")) {
                $(elem).closest('div.panel-body').show();
                console.log("elem", $(elem));
            }
        });

        if ($errorInput)
            $errorInput.tagsinput('focus');

        validator.focusInvalid();
        return false;
    }
}


function validateOnApproveAndForward(validator, action) {
    validateWorkFlowApprover(action);
    if ($('#wfstateDesc').val() === 'NEW') {
        $('#approvalDepartment').removeAttr('required');
        $('#approvalDesignation').removeAttr('required');
        $('#approvalPosition').removeAttr('required');
        return true;
    } else {
        return validateForm(validator);
    }
}