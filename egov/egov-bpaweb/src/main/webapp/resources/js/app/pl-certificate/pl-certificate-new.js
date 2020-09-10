/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

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
	var inputs = $('#emailId');
    inputs.each(function () {
        this.style.textTransform = 'lowercase';
    }).keyup(function () {
        this.value = this.value.toLowerCase();
    });

    //toggle between multiple tab
    jQuery('form').validate({
        ignore: ".ignore",
        invalidHandler: function (e, validator) {
            if (validator.errorList.length)
                $('#settingstab a[href="#' + jQuery(validator.errorList[0].element).closest(".tab-pane").attr('id') + '"]').tab('show');
        }
    });
    
    var validator = $("#plinthLevelCertificateForm").validate({
        highlight: function (element, errorClass) {
            $(element).fadeOut(function () {
                $(element).fadeIn();
            });
        }
    });

    if ($('#noJAORSAMessage') && $('#noJAORSAMessage').val())
        bootbox.alert($('#noJAORSAMessage').val());
    
    function validateFormOnSubmit(button, validator) {
        if ($('#plinthLevelCertificateForm').valid()) {
            document.getElementById("workFlowAction").value = button;
            return true;
        } else {
            return validateMandatoryAndFocus(validator);
        }
    }

    function validateMandatoryAndFocus(validator) {
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

    function removeDisabledAttribute() {
        $('#plinthLevelCertificateForm').find(':input', ':select', ':textarea').each(function () {
            $(this).removeAttr("disabled");
        });
    }
    
    $('#plSubmit').click(function (e) {
        var button = $('#plSubmit').val();
        if (validateFormOnSubmit(button, validator)) {
            bootbox
                .dialog({
                    message: $('#submitAppln').val(),
                    buttons: {
                    	'confirm': {
                            label: 'Yes',
                            className: 'btn-primary',
                            callback: function (result) {
                                removeDisabledAttribute();
                                $('#plinthLevelCertificateForm').trigger('submit');
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
    });
});
