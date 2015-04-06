function optiondd(target, label, text) {

	var $link = $(label);
	var $img = $link.find('span');
	$link.html(text);
	$link.append($img);

	$(target).append($('<li/>', {
		'id' : 'xxx3'
	}).append($('<a/>', {
		'id' : 'data3',
		text : 'data sample 3'
	})));
	$('#data3').click(function() {
		myhistoG("data/data3.csv", false);
	});
	$(target).append($('<li/>', {
		'id' : 'xxx3'
	}).append($('<a/>', {
		'id' : 'data1',
		text : 'data sample 1'
	})));
	$('#data1').click(function() {
		myhistoG("data/data1.csv", false);
	});
	$(target).append($('<li/>', {
		'id' : 'xxx3'
	}).append($('<a/>', {
		'id' : 'data2',
		text : 'data sample 2'
	})));
	$('#data2').click(function() {
		myhistoG("data/data2.csv", false);
	});

}

function histo(target, data, bool) {

	var width = $(target).width();
	var height = Math.max($(target).height(), $(window).height() / 2);

	var progress = 0;
	progressUp(".progress-bar", progress);

	var progress = 0;
	progressUp(".progress-bar", progress);
	progress += 20;
	// Future size of svg node.
	var margin = {
		top : 80,
		right : 80,
		bottom : 80,
		left : 80
	},
	    width = width - margin.left - margin.right,
	    height = height - margin.top - margin.bottom;

	/*Clean svg*/
	d3.select(target).selectAll("svg").remove();
	d3.selectAll(".d3-tip").remove();
	
	
	/*Learning new keys in csv data*/
	var keys;
	data.forEach(function(d) {
		keys = Object.keys(d);
		
		 keys.forEach(function(key) {
		 	if(!isNaN(d[key])){
		 d[key] = parseInt(d[key]);
		 	}
		 });
		 
	});

	/* MODEL FOR TOOLTIP */
	var tip = d3.tip().attr('class', 'd3-tip tooltip top').offset([-10, 0]).html(function(d) {
		
		/*.attr("x",event.clientX).attr("y",event.clientY)
		 var x = event.clientX;
    	var y = event.clientY;
    	*/
		var start = "<div class='tooltip-arrow'></div><div class='tooltip-inner'><h4> ";
		var end = " </h4></div></div>";
		var text = "X/Y";
		if (d[keys[1]]) {
			text = d[keys[0]] + " : " + d[keys[1]];
			return start + text + end;
		} else if (d3.select(this).attr("class") == "line") {
			text = " " + d3.select(this).attr("data") + " ";

			return start + text + end;
		}

	});

	/* Main svg */
	var svg = d3.select(target).append("svg").attr("id", "charts").attr("width", width + margin.left + margin.right).attr("height", height + margin.top + margin.bottom).append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	progress += 20;
	progressUp(".progress-bar", progress);

	/* propagation event tooltip*/
	svg.call(tip);
	var numBars = Math.round(width / data.length * data.length);

	// Axes
	var x = d3.scale.ordinal().rangeRoundBands([0, width], .05, 0);

	var y = d3.scale.linear().range([height, 0]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom").ticks(10);

	var yAxis = d3.svg.axis().scale(y).orient("left").ticks(10);

	progress += 20;
	progressUp(".progress-bar", progress);

	x.domain(data.slice(0, numBars).map(function(d) {
		return d[keys[0]];
	}));
	y.domain([0, d3.max(data, function(d) {
		
			var line = [];
			keys.forEach(function(key) {
						if(!isNaN(d[key])){
							line.push(d[key]);
						}	
			});
			return d3.max(line);
		
	})]);

	d3.selectAll(".axis").remove();
	d3.selectAll(".bar").remove();

	svg.append("g").attr("class", "x axis").attr("transform", "translate(0," + height + ")").call(xAxis).selectAll("text").style("text-anchor", "start").attr("dx", "-0.5em").attr("dy", "1em").attr("transform", "rotate(0)");

	svg.append("g").attr("class", "y axis").call(yAxis).append("text").attr("transform", "rotate(-90)").attr("y", 6).attr("dy", ".71em").style("text-anchor", "start");

	function drawAttrBar(data,height,key,svg) {

		var sel = svg.selectAll(".bar").data(data);

		sel.enter().append("rect").call(tip).attr("class", "bar").style("fill", "steelblue").on('mouseover', tip.show).on('mouseout', tip.hide);

		sel.attr("x", function(d) {
			return x(d[keys[0]]);
		}).attr("width", x.rangeBand());

		/*sel.attr("y", height).attr("height", 0);
		.transition()*/
		sel.attr("y", function(d) {
			
			return y(d[key]) - 1;
		}).attr("height", function(d) {
			return height - y(d[key]);
		});

	}
	console.log(bool);
	if (!bool) drawAttrBar(data,height,keys[1],svg);
	
	function drawAttributeLine(data, key, svg) {
		var lineX = d3.svg.line().interpolate("basis").x(function(d) {
			return x(d[keys[0]]);
		}).y(function(d) {
			return y(d[key]);
		});

		svg.append("path").datum(data).attr("class", "line").attr("data", key).attr("d", lineX).attr("stroke", get_random_color2()).on('mouseover', tip.show).on('mouseout', tip.hide);

	}
	
	if (keys.length > 2) {
		keys.forEach(function(d) {
			if (d != keys[0] || d != keys[2] ) {
				drawAttributeLine(data, d, svg);
			}
		});
	}
	

	

	progress += 20;
	progressUp(".progress-bar", progress);
	// Remove the placeholder.
	progress += 20;
	progressUp(".progress-bar", progress);
	d3.selectAll(".holder").remove();

	//d3.select(target).append(svg);

}

function createHistogram(target, filename, bool) {
	console.log(bool);

	//$("#dl1").attr("download", "data.csv").attr("href", filename);

	//d3.csv(filename, function(error, data) {

		histo(target, filename.tab, bool);
		create_table(filename.tab, filename.stats);
	//});
}

function loadHisto(data) {
	$("#dl1").attr("display", "none");
	
	
	var target = "#out1";
	histo(target, data);
	$(window).resize(function() {
		histo(target, data);
	});
}

function myhistoG(datan, bool) {
	var outputDiv = "#out1";
	console.log(bool);
	var barSize = 40;
	createHistogram(outputDiv, datan, bool);
	$(window).resize(function() {
		createHistogram(outputDiv, datan, bool);
	});
}

function createViz(object) {
	/*var json = {
"stats" : [
	{"label":"count","value":1000},
	{"label":"med","value":73200},
	{"label":"min","value":64.0},
	{"label":"max","value":99914.0},
	{"label":"moy","value":"50024.388"}],
"tab" : [
	{"label":"64 Ã  16705.67","value":168},
	{"label":"16705.67 Ã  33347.33","value":171},
	{"label":"33347.33 Ã  49989","value":149},
	{"label":"49989 Ã  66630.67","value":173},
	{"label":"66630.67 Ã  83272.33","value":171},
	{"label":"83272.33 Ã  99914","value":168}]
,
"moySeg" : [
	{"label":"64.0 - 16705.666666666668","value":"8475.790419161674"},
	{"label":"16705.666666666668 - 33347.333333333336","value":"25268.397660818715"},
	{"label":"33347.333333333336 - 49989.0","value":"41933.57718120806"},
	{"label":"49989.0 - 66630.66666666667","value":"57982.15028901733"},
	{"label":"66630.66666666667 - 83272.33333333334","value":"74035.45029239765"},
	{"label":"83272.33333333334 - 99914.0","value":"91311.25748502993"}]
}*/
	//var datan = "data/data3.csv";
	progressUp(".progress-bar", 10);

	myhistoG(object, false);
	progressUp(".progress-bar", 20);

	optiondd("#opt1", "#title1", "Files History");

	openFiles();

}

function createViz2(object) {

	/*var json = {
"stats" : [
	{"label":"count","value":1000},
	{"label":"med","value":73200},
	{"label":"min","value":64.0},
	{"label":"max","value":99914.0},
	{"label":"moy","value":"50024.388"}],
"tab" : [
	{"label":"5","min":791.0,"max":92734.0,"moy":39052.0},
	{"label":"6","min":6171.0,"max":90924.0,"moy":46718.3},
	{"label":"7","min":64.0,"max":97431.0,"moy":57234.75},
	{"label":"8","min":2221.0,"max":99547.0,"moy":43225.14},
	{"label":"9","min":2745.0,"max":96375.0,"moy":53296.93},
	{"label":"10","min":10294.0,"max":91702.0,"moy":42394.0},
	{"label":"11","min":565.0,"max":86919.0,"moy":44872.67},
	{"label":"12","min":1433.0,"max":99838.0,"moy":56403.84},
	{"label":"13","min":11368.0,"max":96121.0,"moy":53883.57},
	{"label":"14","min":18843.0,"max":94336.0,"moy":55475.81},
	{"label":"15","min":7199.0,"max":96461.0,"moy":46737.85},
	{"label":"16","min":816.0,"max":97093.0,"moy":46754.0},
	{"label":"17","min":8522.0,"max":96361.0,"moy":48607.58},
	{"label":"18","min":15165.0,"max":95857.0,"moy":53427.31},
	{"label":"19","min":706.0,"max":97548.0,"moy":35832.82},
	{"label":"20","min":505.0,"max":91480.0,"moy":42007.45},
	{"label":"21","min":1156.0,"max":94726.0,"moy":43328.12},
	{"label":"22","min":6420.0,"max":95789.0,"moy":57720.88},
	{"label":"23","min":9877.0,"max":98231.0,"moy":61385.93},
	{"label":"24","min":549.0,"max":96210.0,"moy":63344.28},
	{"label":"25","min":1506.0,"max":99914.0,"moy":56639.5},
	{"label":"26","min":2584.0,"max":98583.0,"moy":49316.44},
	{"label":"27","min":19406.0,"max":90671.0,"moy":56416.56},
	{"label":"28","min":23226.0,"max":97707.0,"moy":68621.8},
	{"label":"29","min":7002.0,"max":80779.0,"moy":51555.5},
	{"label":"30","min":12174.0,"max":93727.0,"moy":47749.47},
	{"label":"31","min":6431.0,"max":97961.0,"moy":41860.56},
	{"label":"32","min":1406.0,"max":98440.0,"moy":53768.58},
	{"label":"33","min":2940.0,"max":96986.0,"moy":42189.29},
	{"label":"34","min":2877.0,"max":93350.0,"moy":64830.33},
	{"label":"35","min":1584.0,"max":94898.0,"moy":52049.86},
	{"label":"36","min":4459.0,"max":89446.0,"moy":54877.67},
	{"label":"37","min":5860.0,"max":96345.0,"moy":56606.06},
	{"label":"38","min":1632.0,"max":85798.0,"moy":50011.76},
	{"label":"39","min":6222.0,"max":97672.0,"moy":48672.12},
	{"label":"40","min":13443.0,"max":89153.0,"moy":49129.38},
	{"label":"41","min":4108.0,"max":97726.0,"moy":50816.65},
	{"label":"42","min":5073.0,"max":94713.0,"moy":43847.18},
	{"label":"43","min":2942.0,"max":85029.0,"moy":41351.6},
	{"label":"44","min":9261.0,"max":94626.0,"moy":47508.93},
	{"label":"45","min":2004.0,"max":92329.0,"moy":45273.9},
	{"label":"46","min":3319.0,"max":95607.0,"moy":39739.38},
	{"label":"47","min":322.0,"max":67032.0,"moy":38402.83},
	{"label":"48","min":1152.0,"max":95513.0,"moy":48285.39},
	{"label":"49","min":723.0,"max":99713.0,"moy":58277.38},
	{"label":"50","min":7505.0,"max":93785.0,"moy":47327.87},
	{"label":"51","min":1758.0,"max":91414.0,"moy":40584.71},
	{"label":"52","min":2693.0,"max":97399.0,"moy":55436.38},
	{"label":"53","min":14872.0,"max":95731.0,"moy":50037.85},
	{"label":"54","min":4729.0,"max":95672.0,"moy":50453.56},
	{"label":"55","min":1117.0,"max":93513.0,"moy":55346.04},
	{"label":"56","min":81.0,"max":91599.0,"moy":44263.11},
	{"label":"57","min":6747.0,"max":94577.0,"moy":53439.19},
	{"label":"58","min":5043.0,"max":99837.0,"moy":41206.47},
	{"label":"59","min":5229.0,"max":95745.0,"moy":55502.29},
	{"label":"60","min":6396.0,"max":76834.0,"moy":50284.2},
	{"label":"61","min":10614.0,"max":83064.0,"moy":51250.65},
	{"label":"62","min":4803.0,"max":99351.0,"moy":52598.61},
	{"label":"63","min":3938.0,"max":93207.0,"moy":45522.71},
	{"label":"64","min":2426.0,"max":98404.0,"moy":52782.44},
	{"label":"65","min":6413.0,"max":92518.0,"moy":52888.19},
	{"label":"66","min":2790.0,"max":98463.0,"moy":49229.2},
	{"label":"67","min":2579.0,"max":95262.0,"moy":57291.08},
	{"label":"68","min":5614.0,"max":72254.0,"moy":41718.5},
	{"label":"69","min":15500.0,"max":98671.0,"moy":55866.92},
	{"label":"70","min":2736.0,"max":93994.0,"moy":49116.55}]
}*/

	//var datan = "data/minMaxMoy.csv";
	progressUp(".progress-bar", 10);

	myhistoG(object, true);
	progressUp(".progress-bar", 20);

	optiondd("#opt1", "#title1", "Files History");

	openFiles();

}
