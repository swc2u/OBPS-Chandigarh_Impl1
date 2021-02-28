$(document).ready(function($) {
	$('.nav-tabs a[href="#view-fee"]').click(function() {
		if($('#bpaChargesDetail').length){
			$('#bpaChargesDetail').remove();
		}
		if ($('#eDcrNumber').val() === "") {
			alert("Please provide, Building Plan Scrutiny Number.");
			$('.nav-tabs a[href="#appliccation-info"]').tab('show');
		} else {
			searchViaAjax();
		}
	});
});

function searchViaAjax() {

	$
			.ajax({
				url : "/bpa/ajax/getFeeDetailsDuringRegister",
				type : "GET",
				cache : false,
				data : {
					edcr : $('#eDcrNumber').val(),
					serviceType : $('#serviceType').val(),
					applicationType : $('#applicationType').val()
				},
				dataType : "json",
				success : function(response) {
					console.log(response);
					var trHTML = '';
					var total = 0;
					
					trHTML += '<table class="table table-striped table-bordered" id="bpaChargesDetail" style="width:50%;margin:0 auto;">';
					trHTML += '<thead> <tr>';
					trHTML += '<th class="text-center">Serial No.</th>';
					trHTML += '<th style="width:50%;">Fee Description</th>';
					trHTML += '<th class="text-right">Amount (Rs)</th> </tr> </thead>';
					$
							.each(
									response,
									function(key, value) {
										var sl=parseInt(key)+1;
										trHTML += '<tr><td class="text-center view-content">'
												+ sl
												+ '</td><td id="description" class="view-content">'
												+ value.name
												+ '</td><td class="text-right view-content">'
												+ value.amount + '</td></tr> ';
										total = total + value.amount;

									});
					console.log("hie" + total)
					trHTML += '<tfoot><tr><td></td><td class="text-right view-content">Total Amount</td><td class="text-right view-content">'
							+ total + '</td></tr></tfoot>';
					trHTML += '</table>';
					$('#neha').append(trHTML);

				},
				error : function(response) {
					//
				}
			});

	// function searchViaAjax() {
	//
	// $.ajax({
	// url : "/bpa/ajax/getFeeDetailsDuringRegister",
	// type : "GET",
	// cache : false,
	// dataType : "json",
	// success : function(response) {
	// var trHTML = '';
	// $.each(response, function(key, value) {
	// var trHTML = '';
	// trHTML += '<tr><td class="text-center view-content">' + key
	// + '</td><td id="description" class="view-content">'
	// + value.name
	// + '</td><td class="text-right view-content">'
	// + value.amount + '</td></tr> ';
	// $('#neha').append(trHTML);
	// });
	// //$('#neha').append(trHTML);
	//
	// },
	// error : function(response) {
	// //
	// }
	//		});
	//
	//	}

}