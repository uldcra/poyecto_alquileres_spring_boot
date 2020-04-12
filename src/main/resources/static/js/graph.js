$(document).ready(function(){
	
	var strings = [];
	var i;
	
	for (i=0;i<10;i++){
		strings[i] = $("#invisible1")[0].children[i].id;
	}
	var dataPoints = [{ label: strings[0], y: parseInt(strings[1]) },{ label: strings[2], y: parseInt(strings[3]) },
	{ label: strings[4], y: parseInt(strings[5]) },{ label: strings[6], y: parseInt(strings[7]) },
	{ label: strings[8], y: parseInt(strings[9]) }];
  
var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
	exportEnabled: true,
	theme: "light2", // "light1", "light2", "dark1", "dark2"
	title:{
		text: "Anuncios publicados por Localidad"
	},
	data: [{
		type: "column", //change type to bar, line, area, pie, etc
		//indexLabel: "{y}", //Shows y value on all Data Points
		indexLabelFontColor: "#5A5757",
		indexLabelPlacement: "outside",
		dataPoints: dataPoints
	}]
});
chart.render();



});


