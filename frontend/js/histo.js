
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
	$('#data3').click(function(){
		myhistoG("data/data3.csv");
	});
	$(target).append($('<li/>', {
		'id' : 'xxx3'
	}).append($('<a/>', {
		'id' : 'data1',
		text : 'data sample 1'
	})));
	$('#data1').click(function(){
		myhistoG("data/data1.csv");
	});
	$(target).append($('<li/>', {
		'id' : 'xxx3'
	}).append($('<a/>', {
		'id' : 'data2',
		text : 'data sample 2'
	})));
	$('#data2').click(function(){
		myhistoG("data/data2.csv");
	});
		
	
	
}
 

function histo(target, data) {
	
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

	d3.select(target).selectAll("svg").remove();

	var svg = d3.select(target).append("svg").attr("id", "charts").attr("width", width + margin.left + margin.right).attr("height", height + margin.top + margin.bottom).append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	progress += 20;
	progressUp(".progress-bar", progress);
	
	var numBars = Math.round(width / data.length*data.length);
	
	// Axes
	var x = d3.scale.ordinal().rangeRoundBands([0, width], .05,0);

	var y = d3.scale.linear().range([height, 0]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom").ticks(10);

	var yAxis = d3.svg.axis().scale(y).orient("left").ticks(10);
	
	progress += 20;
	progressUp(".progress-bar", progress);
	
	var keys;
	data.forEach(function(d) {
		keys = Object.keys(d);
	});
	alert(keys+" = "+keys.length);
	
	
	data.forEach(function(d) {
		d.value = parseInt(d.value);
		if(d.value==0){
			d.value = parseInt(d.moy);
		}
	});
	
	
	
	x.domain(data.slice(0, numBars).map(function(d) {
		return d.label;
	}));
	y.domain([0, d3.max(data, function(d) {
		return d.value;
	})]);
	
	

	d3.selectAll(".axis").remove();
	d3.selectAll(".bar").remove();
	
	var line = d3.svg.line().interpolate("basis")
    .x(function(d) { return x(d.label); })
    .y(function(d) { return y(d.value); });

	svg.append("g").attr("class", "x axis").attr("transform", "translate(0," + height + ")").call(xAxis).selectAll("text").style("text-anchor", "start").attr("dx", "-0.5em").attr("dy", "1em").attr("transform", "rotate(0)");

	svg.append("g").attr("class", "y axis").call(yAxis).append("text").attr("transform", "rotate(-90)").attr("y", 6).attr("dy", ".71em").style("text-anchor", "start");

	var sel = svg.selectAll(".bar").data(data);
	
	sel.enter().append("rect").attr("class", "bar").style("fill", "steelblue");
	
	
	
	
	sel.attr("x", function(d) {
		return x(d.label);
	}).attr("width", x.rangeBand());

	sel.attr("y", height).attr("height", 0);
	
	sel.on('mouseover', function(d, i) {
		d3.select(this).attr("class", "baro").append("title").text(function(d) {
			return d.label+" : " + d.value;
		});
	}).on('mouseout', function(d) {
		d3.select(this).attr("class", "bari");
	});
	
	sel.transition().attr("y", function(d) {
		return y(d.value)-1;
	}).attr("height", function(d) {
		return height - y(d.value);
	});
	
	
	svg.append("path")
      .datum(data)
      .attr("class", "line")
      .attr("d", line);
	

	progress += 20;
	progressUp(".progress-bar", progress);
	// Remove the placeholder.
	progress += 20;
	progressUp(".progress-bar", progress);
	d3.selectAll(".holder").remove();

	//d3.select(target).append(svg);

}

/* Nouveau fichier Oscaro */
function histoMinMaxMoy(target, data) {
	var progress = 90;
	progressUp(".progress-bar", progress);
	
	var width = $(target).width();
	
	
	
	var height = Math.max($(target).height(), $(window).height() / 2);
	
	
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

	d3.select(target).selectAll("svg").remove();

	var svg = d3.select(target).append("svg").attr("id", "charts").attr("width", width + margin.left + margin.right).attr("height", height + margin.top + margin.bottom).append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	progress += 20;
	progressUp(".progress-bar", progress);

	var numBars = Math.round(width / data.length*data.length);

	// Axes
	var x = d3.scale.ordinal().rangeRoundBands([0, width], .05,0);

	var y = d3.scale.linear().range([height, 0]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom").ticks(10);

	var yAxis = d3.svg.axis().scale(y).orient("left").ticks(10);
	
	progress += 20;
	progressUp(".progress-bar", progress);

	data.forEach(function(d) {
		
		d.value = parseInt(d.moy);
		
	});

	x.domain(data.slice(0, numBars).map(function(d) {
		return d.label;
	}));
	y.domain([0, d3.max(data, function(d) {
		return d.max;
	})]);
	
	

	d3.selectAll(".axis").remove();
	d3.selectAll(".bar").remove();

	svg.append("g").attr("class", "x axis").attr("transform", "translate(0," + height + ")").call(xAxis).selectAll("text").style("text-anchor", "start").attr("dx", "-0.5em").attr("dy", "1em").attr("transform", "rotate(0)");
	/**************************************************.attr("transform", "translate(10,0)")*/
	svg.append("g").attr("class", "y axis").call(yAxis).append("text").attr("transform", "rotate(-90)").attr("transform", "translate(5px,0px)").attr("y", 0).attr("dy", ".71em").style("text-anchor", "start");
	
	var sel = svg.selectAll(".bar").data(data);
	
	sel.enter().append("rect").attr("class", "bar").style("fill", "steelblue");
	
	
	var line = d3.svg.line()
    .x(function(d) { return x(d.label); })
    .y(function(d) { return y(d.value); });
	
	sel.attr("x", function(d,i) {
		return x(d.label);
	}).attr("width", x.rangeBand());

	sel.attr("y", height).attr("height", 0);
	
	sel.on('mouseover', function(d, i) {
		d3.select(this).attr("class", "baro").append("title").text(function(d) {
			return d.label+" : " + d.value;
		});
	}).on('mouseout', function(d) {
		d3.select(this).attr("class", "bari");
	});
	
	sel.transition().attr("y", function(d) {
		return y(d.value)-1;
	}).attr("height", function(d) {
		return height - y(d.value);
	});

	
	var line = d3.svg.line().interpolate("basis")
    .x(function(d) { return x(d.label); })
    .y(function(d) { return y(d.moy); });
    
    var lineMin = d3.svg.line().interpolate("basis")
    .x(function(d) { return x(d.label); })
    .y(function(d) { return y(d.min); });
    
    var lineMax = d3.svg.line().interpolate("basis")
    .x(function(d) { return x(d.label); })
    .y(function(d) { return y(d.max); });	
	
	svg.append("path")
      .datum(data)
      .attr("class", "line")
      .attr("d", line)
   .on('mouseover', function(d, i) {
		d3.select(this).append("title").text(function(d) {
			return "Moyenne";
		});
	}).on('mouseout', function(d) {
		d3.select(this);
	});
	
   svg.append("path")
      .datum(data)
      .attr("class", "linemi")
      .attr("d", lineMin) .on('mouseover', function(d, i) {
		d3.select(this).append("title").text(function(d) {
			return "Min";
		});
	}).on('mouseout', function(d) {
		d3.select(this);
	});
   
      svg.append("path")
      .datum(data)
      .attr("class", "linemx")
      .attr("d", lineMax).on('mouseover', function(d, i) {
		d3.select(this).append("title").text(function(d) {
			return "Max";
		});
	}).on('mouseout', function(d) {
		d3.select(this);
	});   
      
	

	progress += 20;
	progressUp(".progress-bar", progress);
	// Remove the placeholder.
	progress += 20;
	progressUp(".progress-bar", progress);
	d3.selectAll(".holder").remove();

	//d3.select(target).append(svg);

}

function createHistogram(target,filename) {
	
	$("#dl1").attr("download","data.csv").attr("href",filename);
	
	d3.csv(filename, function(error, data) {

		histo(target, data);
		create_table(data);
	});
}

function loadHisto(data){
	$("#dl1").attr("display","none");
	var target = "#out1";
	histo(target, data);
	$(window).resize(function() {
		histo(target, data);
	});
}

function createHistogram2(target,filename) {
	
	$("#dl1").attr("download","data.csv").attr("href",filename);
	
	d3.csv(filename, function(error, data) {

		histoMinMaxMoy(target, data);
		create_table(data);
	});
}


function myhistoG(datan) {
	var outputDiv = "#out1";
	
	var barSize = 40;
	createHistogram(outputDiv,datan);
	$(window).resize(function() {
		createHistogram(outputDiv,datan);
	});
}


function myhistoGm3(datan) {
	var outputDiv = "#out1";
	
	var barSize = 40;
	createHistogram2(outputDiv, datan);
	
	$(window).resize(function() {
		createHistogram2(outputDiv, datan);
	});
}





function createViz2() {
	
	var datan = "data/minMaxMoy.csv";
	progressUp(".progress-bar", 10);
	
	myhistoGm3(datan);
	progressUp(".progress-bar", 20);
	
	
	
}


function createViz() {
	
	var datan = "data/data3.csv";
	progressUp(".progress-bar", 10);
	
	myhistoG(datan);
	progressUp(".progress-bar", 20);
	
	optiondd("#opt1","#title1", "Files History");
	
	openFiles();
	
	
}