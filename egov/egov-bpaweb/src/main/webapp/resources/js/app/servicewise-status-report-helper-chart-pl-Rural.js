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
                    $('.plotAreaHideShow').hide();
                    $('.builtUpAreaHideShow').hide();
					$('#btnSearch').click(function() {
                        var isValid = false;
                        $('#serviceTypeWiseStatusRuralPLReport').find(':input',':select',':textarea').each(function() {
                            if($(this).val()) {
                                isValid =true;
                                return false;
                            } else
                                isValid =false;
                        });
                        if(isValid) {
                            callAjaxSearch();
                           
                        } else {
                            bootbox.alert($('#atleastOneInputReq').val());
                            return false;
                        }
					});
					
					var formdata;
					
				function graph(){
						$.ajax({
						  type: "POST",
						  url: "/bpa/reports/servicewise-statusreport-pl/d/r",
						  data: formdata,
						  success: function(data){
						 	showGraph(data);
						  },
						  dataType: "json"
						});
						
					}
					
					
					function showGraph(searchCount) {
						
						var chart = new CanvasJS.Chart("chartContainer", {
							exportEnabled: true,
							animationEnabled: true,
							title:{
								text: "Rural"
							},
							legend:{
								cursor: "pointer",
								itemclick: explodePie
							},
							data: [{
								type: "pie",
								showInLegend: true,
								toolTipContent: "{name}: <strong>{y}</strong>",
								indexLabel: "{name} - {y}",
								dataPoints: [
									{ y: searchCount["RURAL"], name: "RURAL", exploded: true }
								]
							}]
						});
						chart.render();
						}
						
						function explodePie (e) {
							if(typeof (e.dataSeries.dataPoints[e.dataPointIndex].exploded) === "undefined" || !e.dataSeries.dataPoints[e.dataPointIndex].exploded) {
								e.dataSeries.dataPoints[e.dataPointIndex].exploded = true;
							} else {
								e.dataSeries.dataPoints[e.dataPointIndex].exploded = false;
							}
							e.chart.render();
						
						}

					function callAjaxSearch() {
						var from = $('#fromDate').val();
						var to = $('#toDate').val();
						var applicantName = $('#applicantName').val();
						var applicationNumber = $('#applicationNumber').val();
						var applicationTypeId = $('#applicationTypeId').val();
						var plotNumber = $('#plotNumber').val();
						var sector = $('#sector').val();
						var wardId = $('#ward').val();
						var electionWardId = $('#electionBoundary').val();
						var zoneId = $('#zone').val();
						var serviceTypeEnum = $('#serviceTypeEnum').val();
						formdata=getFormData($('form'));
						
						$('.ss-rural-pl-report-section').removeClass('display-hide');
						$("#searchStatusByServiceTypeReportRuralPLTable")
								.dataTable(
										{
											ajax : {
												url : "/bpa/reports/servicewise-statusreport-pl-rural",
												type : "POST",
												beforeSend : function() {
													$('.loader-class')
															.modal(
																	'show',
																	{
																		backdrop : 'static'
																	});
												},
												"data" : formdata,
												"dataSrc": function ( json ) {
													json.data.forEach(function(item){
														item["rowTotal"] = item.serviceType01/* + item.serviceType02*/ + item.serviceType03 + item.serviceType04 /*+ item.serviceType05 */
															+ item.serviceType06 + item.serviceType07 /*+ item.serviceType08 + item.serviceType09 + item.serviceType14*/;
													});
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
                                                title: 'ServiceType wise status report',
                                                filename: 'Servicetype_wise_status_report',
                                                orientation: 'landscape',
                                                pageSize: 'A3',
                                                exportOptions: {
                                                    columns: ':visible'
                                                }
                                            }, {
                                                extend: 'excel',
                                                title: 'ServiceType wise status report',
                                                filename: 'Servicetype_wise_status_report',
                                                exportOptions: {
                                                    columns: ':visible'
                                                }
                                            }, {
                                                extend: 'print',
                                                title: 'ServiceType wise status report',
                                                filename: 'Servicetype_wise_status_report',
                                                orientation: 'landscape',
                                                pageSize: 'A3',
                                                exportOptions: {
                                                    columns: ':visible'
                                                }
                                            }],
											aaSorting : [],
											columns : [
												{
													"data" : null,
													render : function(data, type, row, meta) {
														return meta.row
																+ meta.settings._iDisplayStart
																+ 1;
													},
													"sClass" : "text-center"
												},
													{
														"data" : "status",
														"sClass" : "text-left"
													},
													{
														"data" : "serviceType01",
														 render : function(data, type, row, meta) {
															return parseInt(row.serviceType01)!==0? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=New Construction'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='
																	+ '&'
																	+ 'electionWard='
																	+ '&'
																	+ 'zoneId='
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='
                                                                    + '&'
                                                                    + 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType01 + '</a>':row.serviceType01;
														},
														"sClass" : "text-center"
													},
													/*{
														"data" : "serviceType02",
														render : function(data, type, row, meta) {
															return parseInt(row.serviceType02)!==0? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=Demolition'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='+wardId
																	+ '&'
																	+ 'electionWard='+electionWardId
																	+ '&'
																	+ 'zoneId='+zoneId
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='+wardId
                                                                    + '&'
                                                                    + 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType02 + '</a>':row.serviceType02;
														},
														"sClass" : "text-center"
													},*/
													{
														"data" : "serviceType03",
														render : function(data, type, row, meta) {
															return parseInt(row.serviceType03)!==0 ? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=Reconstruction'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='/*+wardId*/
																	+ '&'
																	+ 'electionWard='/*+electionWardId*/
																	+ '&'
																	+ 'zoneId='/*+zoneId*/
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='/*+wardId*/
                                                                    + '&'
                                                                    + 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType03 + '</a>':row.serviceType03;
														},
														"sClass" : "text-center"
													},
													{
														"data" : "serviceType04",
														render : function(data, type, row, meta) {
															return parseInt(row.serviceType04)!== 0 ? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=Alteration'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='/*+wardId*/
																	+ '&'
																	+ 'electionWard='/*+electionWardId*/
																	+ '&'
																	+ 'zoneId='/*+zoneId*/
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='/*+wardId*/
                                                                    + '&'
                                                                    + 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType04 + '</a>':row.serviceType04;
														},
														"sClass" : "text-center"
													},
													/*{
														"data" : "serviceType05",
														render : function(data, type, row, meta) {
						    									return parseInt(row.serviceType05)!== 0 ? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=Sub-Division of plot/Land Development'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='+wardId
																	+ '&'
																	+ 'electionWard='+electionWardId
																	+ '&'
																	+ 'zoneId='+zoneId
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='+wardId
                                                                    + '&'
                                                                    + 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType05 + '</a>':row.serviceType05;
														},
														"sClass" : "text-center"
													},*/
													{
														"data" : "serviceType06",
														render : function(data, type, row, meta) {
															return parseInt(row.serviceType06)!== 0 ? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=Addition or Extension'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='/*+wardId*/
																	+ '&'
																	+ 'electionWard='/*+electionWardId*/
																	+ '&'
																	+ 'zoneId='/*+zoneId*/
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='/*+wardId*/
                                                                    + '&'
                                                                    + 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType06 + '</a>':row.serviceType06;
														},
														"sClass" : "text-center"
													},
													{
														"data" : "serviceType07",
														render : function(data, type, row, meta) {
															return parseInt(row.serviceType07)!== 0 ? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=Change in occupancy'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='/*+wardId*/
																	+ '&'
																	+ 'electionWard='/*+electionWardId*/
																	+ '&'
																	+ 'zoneId='/*+zoneId*/
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='/*+wardId*/
																    + '&'
                                                                    + 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType07 + '</a>':row.serviceType07;
														},
														"sClass" : "text-center"
													},
													/*{
														"data" : "serviceType08",
														render : function(data, type, row, meta) {
															return parseInt(row.serviceType08)!== 0 ? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=Amenities'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='+wardId
																	+ '&'
																	+ 'electionWard='+electionWardId
																	+ '&'
																	+ 'zoneId='+zoneId
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='+wardId
                                                                    + '&'
                                                                    + 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType08 + '</a>':row.serviceType08;
														},
														"sClass" : "text-center"
													},
													{
														"data" : "serviceType09",
														render : function(data, type, row, meta) {
															return parseInt(row.serviceType09)!==0 ? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=Huts and Sheds'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='+wardId
																	+ '&'
																	+ 'electionWard='+electionWardId
																	+ '&'
																	+ 'zoneId='+zoneId
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='+wardId
																	+ '&'
																	+ 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType09 + '</a>':row.serviceType09;
														},
														"sClass" : "text-center"
													},{
														"data" : "serviceType14",
														render : function(data, type, row, meta) {
															return parseInt(row.serviceType14)!==0 ? '<a onclick="openPopup(\'/bpa/reports/servicewise-statusreport-pl/view/d/r?'
																	+ 'serviceType=Tower Construction'
																	+ '&'
																	+ 'applicantName='+applicantName
																	+ '&'
																	+ 'applicationNumber='+applicationNumber
																	+ '&'
																	+ 'applicationTypeId='+applicationTypeId
																	+ '&'
																	+ 'plotNumber='+plotNumber
																	+ '&'
																	+ 'sector='+sector
																	+ '&'
																	+ 'fromDate='+from
																	+ '&'
																	+ 'toDate='+to
																	+ '&'
																	+ 'ward='+wardId
																	+ '&'
																	+ 'electionWard='+electionWardId
																	+ '&'
																	+ 'zoneId='+zoneId
																	+ '&'
																	+ 'zone='
																	+ '&'
																	+ 'status='+row.status
																	+ '&'
																	+ 'revenueWard='+wardId
																	+ '&'
																	+ 'serviceTypeEnum='+serviceTypeEnum
																	+ '\')" href="javascript:void(0);">'
																	+ row.serviceType14 + '</a>':row.serviceType14;
														},
														"sClass" : "text-center"
													},*/{
														"data":"rowTotal",
														"sClass" : "text-center"
													}],
													
													"footerCallback" : function(row, data, start, end, display) {
														var api = this.api(), data;
														if (data.length == 0) {
															jQuery('#report-footer').hide();
														} else {
															jQuery('#report-footer').show(); 
														}
														if (data.length > 0) {
															updateTotalFooter(2, api);
															/*updateTotalFooter(3, api);*/
															updateTotalFooter(3, api);
															updateTotalFooter(4, api);
															/*updateTotalFooter(6, api);*/
															updateTotalFooter(5, api);
															updateTotalFooter(6, api);
															/*updateTotalFooter(9, api);
															updateTotalFooter(10, api);
															updateTotalFooter(11, api);*/
															updateTotalFooter(7, api);
															}
													},
													"aoColumnDefs" : [ {
														"aTargets" : /*[2,3,4,5,6,7,8,9,10,11,12]*/ [2,3,4,5,6,7],
														"mRender" : function(data, type, full) {
															return formatNumberInr(data);    
														}
													} ]	
										});
										
									 graph();
					}
					
					
					$(document).on('click','.dropchange',function(){
					    var url = $(this).val();
					    if(url){
					    	openPopup(url);
					    }
					    
					});

					function openPopup(url)
					{
						window.open(url,'window','scrollbars=yes,resizable=yes,height=700,width=800,status=yes');
					}

				});

function openPopup(url) {
	window.open(url, 'window',
			'scrollbars=1,resizable=yes,height=600,width=800,status=yes');

}

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}

function updateTotalFooter(colidx, api) {
	// Remove the formatting to get integer data for summation
	var intVal = function(i) {
		return typeof i === 'string' ? i.replace(/[\$,]/g, '') * 1
				: typeof i === 'number' ? i : 0;
	};

	// Total over all pages
	
	if (api.column(colidx).data().length){
        var total = api
        .column(colidx )
        .data()
        .reduce( function (a, b) {
        return intVal(a) + intVal(b);
        } ) }
    else {
        total = 0
    }
    // Total over this page
	
	if (api.column(colidx).data().length){
        var pageTotal = api
            .column( colidx, { page: 'current'} )
            .data()
            .reduce( function (a, b) {
                return intVal(a) + intVal(b);
            } ) }
    else {
        pageTotal = 0
    }
    // Update footer
	jQuery(api.column(colidx).footer()).html(
			formatNumberInr(pageTotal) + ' (' + formatNumberInr(total)
					+ ')');
}


//inr formatting number
function formatNumberInr(x) {
	if (x) {
		x = x.toString();
		var afterPoint = '';
		if (x.indexOf('.') > 0)
			afterPoint = x.substring(x.indexOf('.'), x.length);
		x = Math.floor(x);
		x = x.toString();
		var lastThree = x.substring(x.length - 3);
		var otherNumbers = x.substring(0, x.length - 3);
		if (otherNumbers != '')
			lastThree = ',' + lastThree;
		var res = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",")
				+ lastThree + afterPoint;
		return res;
	}
	return x;
}
