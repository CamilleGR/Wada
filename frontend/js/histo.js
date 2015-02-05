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
		myhistoG("data/data3.csv");
	});
	$(target).append($('<li/>', {
		'id' : 'xxx3'
	}).append($('<a/>', {
		'id' : 'data1',
		text : 'data sample 1'
	})));
	$('#data1').click(function() {
		myhistoG("data/data1.csv");
	});
	$(target).append($('<li/>', {
		'id' : 'xxx3'
	}).append($('<a/>', {
		'id' : 'data2',
		text : 'data sample 2'
	})));
	$('#data2').click(function() {
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
	
	drawAttrBar(data,height,keys[1],svg);
	
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

function createHistogram(target, filename) {

	$("#dl1").attr("download", "data.csv").attr("href", filename);

	d3.csv(filename, function(error, data) {

		histo(target, data);
		create_table(data);
	});
}

function loadHisto(data) {
	$("#dl1").attr("display", "none");
	
	
	var target = "#out1";
	histo(target, data);
	$(window).resize(function() {
		histo(target, data);
	});
}

function myhistoG(datan) {
	var outputDiv = "#out1";

	var barSize = 40;
	createHistogram(outputDiv, datan);
	$(window).resize(function() {
		createHistogram(outputDiv, datan);
	});
}

function createViz() {

	var datan = "data/data3.csv";
	progressUp(".progress-bar", 10);

	myhistoG(datan);
	progressUp(".progress-bar", 20);

	optiondd("#opt1", "#title1", "Files History");

	openFiles();

}