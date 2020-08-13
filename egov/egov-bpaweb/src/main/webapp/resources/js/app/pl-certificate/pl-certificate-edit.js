jQuery(document).ready(function () {
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
            if (validateOnApproveAndForward(validator, action) && validateOnReject(false)) {
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