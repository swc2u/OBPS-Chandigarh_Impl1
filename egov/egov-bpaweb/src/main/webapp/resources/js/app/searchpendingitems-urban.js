$(document).ready(function() {
	$('#btnSearch').click(function() {
		
		var isValid = false;
		$('#searchPendingItemsForm').find(':input', ':select', ':textarea').each(function() {
			if ($(this).val()) {
				isValid = true;
				return false;
			} else
				isValid = false;
		});
		if (isValid) {
			callAjaxSearch();
			graph();
		} else {
			bootbox.alert($('#atleastOneInputReq').val());
			return false;
		}
	});
});

var formdata;

function graph() {
	$.ajax({
		type: "POST",
		url: "/bpa/application/searchPendingItems/d/u",
		data: formdata
		,
		success: function(data) {
			showGraph(data);
		},
		dataType: "json"
	});

}

function showGraph(json) {

	var dataPoints = [];

	for (var key in json) {
		if (json.hasOwnProperty(key)) {
			dataPoints.push({ label: key, y: json[key] });
		}
	}
	

	var chart = new CanvasJS.Chart("chartContainer", {
		animationEnabled: true,
		theme: "light2",
		title: {
			text: "Scrutiny Data"
		},
		axisY: {
			title: "Units",
			titleFontSize: 24,
			includeZero: true
		},
		data: [{
			type: "column",
			yValueFormatString: "#,### ",
			dataPoints: dataPoints
		}]
	});

	chart.render();
}


function callAjaxSearch() {
	var viewurl = '/bpa/application/view/';
	$('.report-section').removeClass('display-hide');
	$("#search_bpa_pending_items_table").dataTable({
		processing: true,
		serverSide: true,
		sort: true,
		filter: true,
		"searching": false,
		responsive: true,
		rowReorder: true,
		"order": [[1, 'asc']],
		ajax: {
			url: "/bpa/application/searchPendingItems/u",
			type: "POST",
			beforeSend: function() {
				$('.loader-class').modal('show', { backdrop: 'static' });
			},
			data: function(args) {
				formdata=	 {
					"args": JSON.stringify(args),
					"serviceTypeId": $("#serviceTypeId").val(),
					"applicationTypeId": $("#applicationTypeId").val(),
					"currentOwnerDesg": $("#currentOwnerDesg").val(),
					"applicantName": $("#applicantName").val(),
					"applicationNumber": $("#applicationNumber").val(),
					"fromDate": $("#fromDate").val(),
					"toDate": $("#toDate").val(),
					"sector":$("#sector").val(),
					"plotNumber":$("#plotNumber").val(),
					"ownerName":$("#applicantName").val()
				};
				console.log(formdata);
				return formdata;
			},
			complete: function() {
				$('.loader-class').modal('hide');
			}
		},
		"bDestroy": true,
		"sDom": "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-xs-3'i><'col-xs-3 col-right'l><'col-xs-3 col-right'<'export-data'T>><'col-xs-3 text-right'p>>",
		"oTableTools": {
			"sSwfPath": "../../../../../../egi/resources/global/swf/copy_csv_xls_pdf.swf",
			"aButtons": ["xls", "pdf",
				"print"]
		},
		aaSorting: [],
		columns: [
			{
				"data": "applicationType",
				"sClass": "text-left"
			},
			{
				"data": "applicantName",
				"sClass": "text-left"
			},
			{
				"data": "applicationNumber",
				"sClass": "text-left"
			},

			{
				"data": "applicationDate",
				"sClass": "text-left",
				render: function(data) {
					return data.split("-").reverse().join("/");
				}
			},
			{
				"data": "serviceType",
				"sClass": "text-left"
			},
			{
				"data": "sector",
				"sClass": "text-left"
			},
				{
				"data": "plotNumber",
				"sClass": "text-left"
			},
			{
				"data": "occupancy",
				"sClass": "text-left"
			},
			{
				"data": "status",
				"sClass": "text-left"
			},
			{
				"data": "currentOwner",
				"sClass": "text-left"
			},
			{
				"data": "currentOwnerDesignation",
				"sClass": "text-left"
			},
			{
				"data": "pendingAction",
				"sClass": "text-left"
			},
			{
				"data": "ellapseTime",
				"sClass": "text-left"
			},
			{
				"data": null,
				"sClass": "text-left",
				"render": function(data, type, row, meta) {
					var commonOptions = '<option value="">---Select an Action----</option><option  value=' + viewurl + row.applicationNumber + '>View</option>';
					return ('<select class="dropchange" style="width:160px;font-size: small">' + commonOptions + '></select>');
				}
			}
		]
	});
}

$(document).on('change', '.dropchange', function() {
	var url = $(this).val();
	if (url) {
		openPopup(url);
	}
	// reset dropdown value to default
	$('.dropchange').val('');
});

function openPopup(url) {
	window.open(url, 'window', 'scrollbars=yes,resizable=yes,height=700,width=800,status=yes');
}

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}