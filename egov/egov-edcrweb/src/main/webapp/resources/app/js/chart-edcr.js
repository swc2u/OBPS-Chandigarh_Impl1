function scrutinyBarChart(args) {
	var dataPoints = [];
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
	
		var i=0;
		var aborted=0;
		var notAccepted=0;
		var accepted=0;
		for (i; i < args.length; i++) {
			
			if(args[i].status=="Accepted"){
				accepted++;
			}else if(args[i].status=="Not Accepted"){
				notAccepted++;
			}else if(args[i].status=="Aborted"){
				aborted++;
			}
		}
		
		dataPoints.push({
			label: "Total drawing scrutinized",
			y: i
		});
		dataPoints.push({
			label: "Scruntiny Report Aborted",
			y: aborted
		});
		dataPoints.push({
			label: "Scruntiny Report Not Accepted",
			y: notAccepted
		});
		dataPoints.push({
			label: "Scruntiny Report Accepted",
			y: accepted
		});
		chart.render();
	
}
