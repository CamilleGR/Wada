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
	var height = (Math.max($(target).height(), $(window).height() / 2))/1.5;

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

	//d3.selectAll(".axis").remove();
	//d3.selectAll(".bar").remove();

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
	drawAttrBar(data,height,keys[1],svg);

	

	progress += 20;
	progressUp(".progress-bar", progress);
	// Remove the placeholder.
	progress += 20;
	progressUp(".progress-bar", progress);
	d3.selectAll(".holder").remove();

	//d3.select(target).append(svg);

}

function createHistogram(target, filename) {

	//$("#dl1").attr("download", "data.csv").attr("href", filename);

	//d3.csv(filename, function(error, data) {

		histo(target, filename);
		//create_table(filename.tab, filename.stats);
	//});
}

function loadHisto(data, target) {
	$("#dl1").attr("display", "none");
	
	
	//var target = "#out1";
	histo(target, data);
	$(window).resize(function() {
		histo(target, data);
	});
}

function myhistoG(datan, outputDiv) {
	//var outputDiv = "#out1";
	var barSize = 40;
	createHistogram(outputDiv, datan);
	$(window).resize(function() {
		createHistogram(outputDiv, datan);
	});
}

function createViz(object, target) {
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

	myhistoG(object, target);
	progressUp(".progress-bar", 20);

	optiondd("#opt1", "#title1", "Files History");

	openFiles();

}

/********* PIE **********/

function pieChart(target, colors, data) {
	var  progress = 0;
	progressUp(".progress-bar", progress);
	var width = $(target).width();
	var height = (Math.max($(target).height(), $(window).height() / 2))/1.5;
	
	
	//alert(radius);
	var color = d3.scale.ordinal().range(colors);
	
	var keys;
	data.forEach(function(d) {
		keys = Object.keys(d);

		keys.forEach(function(key) {
			if (!isNaN(d[key])) {
				d[key] = parseInt(d[key]);
			}
		});

	});
	d3.select(target).selectAll("svg").remove();
	progress = 20;
	progressUp(".progress-bar", progress);
	
	

	var color = d3.scale.ordinal()
    .range(colors);


    var nb = keys.length-1;

var radius = Math.min(width, height)/Math.max(nb,2);

var arc = d3.svg.arc()
    .outerRadius(radius - 2)
    .innerRadius(0);
    //var div = d3.select(target).append(div).attr("class","row");
    
function pieFactory(nb,index){
//div = div.append("div").attr("class","col-md-"+(12/index));



var svg = d3.select(target).append("svg")
    .attr("width", (width/nb)-nb*2)
    .attr("height", height)
  .append("g")
    .attr("transform", "translate(" + (width/nb) / 2 + "," + height / 2 + ")");



var pie = d3.layout.pie()

    .value(function(d) { return d[keys[index]]; });

	progressUp(".progress-bar", progress);
var  g = svg.selectAll("rect")
      .data(pie(data))
    .enter().append("g")
      .attr("class", "arc");

  g.append("path")
      .attr("d", arc)
      .style("fill", function(d) { return color(d.data[keys[0]]); });

  g.append("text")
      .attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")"; })
      .style("text-anchor", "middle")
      .text(function(d) { return d.data[keys[0]]; });
     } 
     
    for(var i=0;i<nb;i++){
    	
    	pieFactory(nb,i+1);
    	
    }
    
    
// Remove the placeholder.
	d3.selectAll(".holder").remove();
progress += 100;
	progressUp(".progress-bar", progress);

}

function createPieChart(target, colors, filename) {

	//$("#dl1").attr("download", "data.csv").attr("href", filename);

	//d3.csv(filename, function(error, data) {
		pieChart(target, colors, filename);
		//create_table(filename.tab, filename.stats);

	//});
}

function loadPie(data, target) {
	$("#dl1").attr("display", "none");

	var colors = get_random_color_set(10);

	//var target = "#out1";
	pieChart(target, colors, data);
	$(window).resize(function() {
		pieChart(target, colors, data);
	});
}

function createVizPie(object, target) {

	//var datan = "data/data1.csv";
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
	progressUp(".progress-bar", 0);

	var colors = get_random_color_set(10);

	myPieChart(object, colors, target);

	$(window).resize(function() {
		myPieChart(object, colors, target);
	});
	progressUp(".progress-bar", 20);

}

function myPieChart(datan, colors, outputDiv) {
	//var outputDiv = "#out1";
	createPieChart(outputDiv, colors, datan);
}
