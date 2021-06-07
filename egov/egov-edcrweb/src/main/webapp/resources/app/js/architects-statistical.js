$(document)
    .ready(
        function () {
                featchArchitectsData();
              showTable();
              showGraph();
            });
            

var architectsData;

function featchArchitectsData(){
 				$.ajax({
                    async: false,
                    crossDomain: true,
                    url: '/bpa/rest/getStakeHolderNameAndIdByType/Architect',
                    type: "GET",
                    contentType: 'application/json; charset=utf-8',
                    success: function (response) {
                    console.log(response);
                       architectsData= response;
                    },
                    error: function (response) {
                        console.log("Error occurred, while getting building licencee names from type !!!!!!!");
                    }
                });

}


function showTable(){
$('.report-section').removeClass('display-hide');
$('#architectsTable').DataTable( {
        "data": architectsData,
        "columns": [
            { "data": "id" },
            { "data": "name" }
        ]
    } );
}

function showGraph() {

var chart = new CanvasJS.Chart("chartContainer", {
	exportEnabled: true,
	animationEnabled: true,
	title:{
		text: "Architects Statistical"
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
			{ y: architectsData.length, name: "Registered Architects", exploded: true },
			{ y: 1, name: "Architects using Application" }
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

function openPopup(url) {
    window.open(url, 'window', 'scrollbars=yes,resizable=yes,height=700,width=800,status=yes');
}
