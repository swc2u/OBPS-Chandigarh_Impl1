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

$(document)
		.ready(
				function() {
					var viewurl = '/bpa/application/view/';
					
					$('.report-section-rural').removeClass('display-hide');
					$("#bpaServiceWiseApplnsDetailsRural")
							.dataTable(
									{
										ajax : {
											url : "/bpa/reports/servicewise-statusreport-rural/view",
											type : "POST",
											beforeSend : function() {
												$('.loader-class')
														.modal(
																'show',
																{
																	backdrop : 'static'
																});
											},
											"data" : getFormData($('form')),
											"dataSrc": function ( json ) {
								                return json.data;
								            },  
											complete : function() {
												$('.loader-class').modal(
														'hide');
											}
										},
                                        "searching": false,
										"bDestroy" : true,
                                        "aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
                                        dom: "<'row'<'col-xs-4 pull-right'f>r>t<'row add-margin'<'col-md-3 col-xs-6'i><'col-md-2 col-xs-6'l>" +
                                        "<'col-md-2 col-xs-6 text-left'B><'col-md-5 col-xs-6 text-right'p>>",
                                        buttons: [{
                                            extend: 'pdf',
                                            title: 'Building plan approval Servicewise status report',
                                            filename: 'bpa_servicewise_status_report',
                                            orientation: 'landscape',
                                            pageSize: 'A3',
                                            exportOptions: {
                                                columns: ':visible'
                                            }
                                        }, {
                                            extend: 'excel',
                                            title: 'Building plan approval Servicewise status report',
                                            filename: 'bpa_servicewise_status_report',
                                            exportOptions: {
                                                columns: ':visible'
                                            }
                                        }, {
                                            extend: 'print',
                                            title: 'Building plan approval Servicewise status report',
                                            filename: 'bpa_servicewise_status_report',
                                            orientation: 'landscape',
                                            pageSize: 'A3',
                                            exportOptions: {
                                                columns: ':visible'
                                            }
                                        }],
										aaSorting : [],
										columns : [
										   {"data":null,
								        	   render: function (data, type, row, meta) {
								        	        return meta.row + meta.settings._iDisplayStart + 1;
								        	   },   
								           },
											{
                                                "data" : "applicantName",
                                                "sClass" : "text-left"
                                            },
								           {
												"data" : "applicationNumber",
												"sClass" : "text-left"
										   },
										  
										   {
												"data" : "sector",
												"sClass" : "text-left"
										   },
										    {
												"data" : "plotNumber",
												"sClass" : "text-left"
										   },
										   {
												"data" : "occupancy",
												"sClass" : "text-left"
										   },
										   {
												"data" : "applicationDate",
												"sClass" : "text-left"
										   },
											{
												"data" : "address",
												"sClass" : "text-left"
											},
											{
												"data" : "serviceType",
												"sClass" : "text-left"
											},
											{
												"data" : "zone",
												"sClass" : "text-left"
											},
											{
												"data" : "ward",
												"sClass" : "text-left"
											},
											{
												"data" : "reSurveyNumber",
												"sClass" : "text-left"
											},
                                            {
                                                "data" : "status",
                                                "sClass" : "text-left"
                                            },
											{
												"data" : "currentOwner",
												"sClass" : "text-left"
											},
											{
												"data" : "pendingAction",
												"sClass" : "text-left"
											},
											{
												"data": null,
												"sClass": "text-left",
												"render": function(data, type, row, meta) {
													var commonOptions = '<option value="">---Select an Action----</option><option  value=' + viewurl + row.applicationNumber + '>View</option>';
													return ('<select class="dropchange" style="width:160px;font-size: small">' + commonOptions + '></select>');
												}
											}]
									});
				});

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
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