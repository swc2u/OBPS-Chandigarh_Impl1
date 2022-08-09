/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
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
 *            Further, all user interfaces, including but not limited to citizen facing interfaces, 
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any 
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines, 
 *            please contact contact@egovernments.org
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
 *
 */

function populateSubOccupancy(dropdown) {
    populatesubOccupancy({
        occupancyId: dropdown.value
    });
}

$("#subOccupancy").change(function () {
    $('#plotNumber').find('option:gt(0)').remove();
    if ($("#subOccupancy").val() !== '') {
        $.ajax({
            type: "GET",
            url: "/edcr/plotMaster/search/by-subOccupancyId",
            data: {'subOccupancyId': $("#subOccupancy").val()},
            dataType: "json",
            success: function (response) {
                $.each(JSON.parse(response), function (key, plot) {
                    $('#plotNumber').append('<option value="' + plot.plotId + '">' + plot.plotNum + '</option>');
                });
            }
        });
    }
});

var table;

$('#searchBtn').click(function () {
    if ($("#subOccupancy").val() === '' || $("#occupancy").val() === '') {
        bootbox.alert("Please select a valid Sub occupancy");
        return;
    }
     if ($("#plotNumber").val() !== '') {
        $("#plotMasterCreateSearchForm").attr('action', $("#plotNumber").val());
        $("#plotMasterCreateSearchForm").submit();
    } 
    else {
	    table = $('#view-plot-master-data').DataTable({
	        processing: true,
	        serverSide: true,
	        type: 'POST',
	        sort: true,
	        filter: true,
	        responsive: true,
	        destroy: true,
	        "autoWidth": false,
	        "order": [[0, 'asc']],
	        ajax: {
	            type: "POST",
	            data: function (args) {
	                return {"args": JSON.stringify(args), "subOccupancyId": $("#subOccupancy").val()};
	            }
	        },
	        "aLengthMenu": [[10, 25, 50, -1],
	            [10, 25, 50, "All"]],
	        "sDom": "<'row'<'col-xs-12 hidden col-right'f>r>t<'row'<'col-md-6 col-xs-12'i><'col-md-3 col-xs-6'l><'col-md-3 col-xs-6 text-right'p>>",
	        "columns": [{
	            "mData": "code",
	            "name": "code",
	            "sTitle": "Code",
	        }, {
	            "mData": "phase",
	            "name": "phase",
	            "sTitle": "Phase"
	        }, {
	            "mData": "sector",
	            "name": "sector",
	            "sTitle": "Sector"
	        }, {
	            "mData": "plotNumber",
	            "name": "plotNumber",
	            "sTitle": "plot Number"
	        }, {
	            "mData": "backCourtyardWidth",
	            "name": "backCourtyardWidth",
	            "sTitle": "back courtyard construction Width"
	        }, {
	            "mData": "backCourtyardHeight",
	            "name": "backCourtyardHeight",
	            "sTitle": "back courtyard construction Height"
	        }, {
	            "mData": "plotArea",
	            "name": "plotArea",
	            "sTitle": "Plot Area"
	        }, {
	            "mData": "areaType",
	            "name": "areaType",
	            "sTitle": "Area Type"
	        }, {
	            "mData": "plotDepth",
	            "name": "plotDepth",
	            "sTitle": "Plot Depth"
	        },{
	            "mData": "plotWidth",
	            "name": "plotWidth",
	            "sTitle": "Plot Width"
	        },{
	            "mData": "permissibleBuildingStories",
	            "name": "permissibleBuildingStories",
	            "sTitle": "Permissible Building Stories"
	        },{
	            "mData": "permissibleBuildingHeight",
	            "name": "permissibleBuildingHeight",
	            "sTitle": "Permissible Building Height"
	        },{
	            "mData": "maxmimumPermissibleFAR",
	            "name": "maxmimumPermissibleFAR",
	            "sTitle": "Maxmimum Permissible FAR"
	        },{
	            "mData": "minimumPermissibleSetback_Front",
	            "name": "minimumPermissibleSetback_Front",
	            "sTitle": "Minimum Permissible Setback Front"
	        },{
	            "mData": "minimumPermissibleSetback_Rear",
	            "name": "minimumPermissibleSetback_Rear",
	            "sTitle": "minimum Permissible Setback Rear"
	        },{
	            "mData": "minimumPermissibleSetback_Right",
	            "name": "minimumPermissibleSetback_Right",
	            "sTitle": "Minimum Permissible Setback Right"
	        },{
	            "mData": "minimumPermissibleSetback_Left",
	            "name": "minimumPermissibleSetback_Left",
	            "sTitle": "Minimum Permissible Setback Left"
	        },{
	            "mData": "pmId",
	            "visible": false,
	            "bSortable": false
	        }]
	        })
	        }
    });


$("#view-plot-master-data").on('click', 'tbody tr td span i.edit', function (event) {
    var id = oTable.row($(this).closest('tr')).data().id;
    var url = '/edcr/plotMaster/update/' + id;
    window.open(url, id, 'width=900, height=700, top=300, left=260,scrollbars=yes');

});

$("#view-plot-master-data").on('click', 'tbody tr td span i.view', function (event) {
    var id = oTable.row($(this).closest('tr')).data().id;
    var url = '/edcr/plotMaster/view/' + id;
    window.open(url, id, 'width=900, height=700, top=300, left=260,scrollbars=yes');

});

$("#view-plot-master-data").on('click', 'tbody tr td span i.delete', function (event) {
    var id = oTable.row($(this).closest('tr')).data().id;
    var url = '/edcr/plotMaster/delete/' + id;
    window.open(url, id, 'width=900, height=700, top=300, left=260,scrollbars=yes');

});

$('#backBtnId').click(function () {
    window.location = '/edcr/plotMaster/update/';
});
