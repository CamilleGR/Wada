/**
 * @author user
 */
function graph(target, data) {

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
		 d[key] = parseFloat(d[key]);
		 //d[key] = d[key].toFixed(3);
		 	}
		 });
		 
	});
	

	/* Main svg */
	var svg = d3.select(target).append("svg").attr("id", "charts").attr("width", width + margin.left + margin.right).attr("height", height + margin.top + margin.bottom).append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	progress += 20;
	progressUp(".progress-bar", progress);

	/* propagation event tooltip*/
	
	var colors = d3.scale.category10();

	// Axes
	var x = d3.scale.linear().range([0, width]);

	var y = d3.scale.linear().range([0, height]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom").ticks(10);

	var yAxis = d3.svg.axis().scale(y).orient("left").ticks(10);

	var xMin = d3.min(data, function(d) {
		return d[keys[1]];
	});
	var xMax = d3.max(data, function(d) {
		return d[keys[1]];
	});
	var yMin = d3.min(data, function(d) {
		
			return d[keys[2]];
		
	});
	var yMax = d3.max(data, function(d) {
		
			return d[keys[2]];
		
	});
	console.log(keys[1]+":"+xMin+"/"+xMax);	
	
	var z = 5;
	
	x.domain([xMin-z,xMax+z ]);
	y.domain([yMin-z,yMax+z ]);

	d3.selectAll(".axis").remove();
	//d3.selectAll(".bar").remove();

	svg.append("g").attr("class", "x axis").attr("transform", "translate(0," + height + ")").call(xAxis).selectAll("text").style("text-anchor", "start").attr("dx", "-0.5em").attr("dy", "1em").attr("transform", "rotate(0)");

	svg.append("g").attr("class", "y axis").call(yAxis).append("text").attr("transform", "rotate(-90)").attr("y", 6).attr("dy", ".71em").style("text-anchor", "start");
	
	
	function drawGraphpoints(data, svg) {

		var sel = svg.selectAll(".point").data(data);
		sel.enter().append("circle")
		.attr("class", "point").attr("data-centre", function(d){return d[keys[0]];})
		.attr("cx", function(d){
			
			 return x(d[keys[1]]);})
		.attr("cy", function(d){return y(d[keys[2]]);})
		.attr("r",2).attr("fill", function(d){
			
			return colors(d[keys[0]]);});

	}
	
	drawGraphpoints(data, svg);
	

	
	// Remove the placeholder.

	d3.selectAll(".holder").remove();



}

function createGraph(target, filename) {

	$("#dl1").attr("download", "data.csv").attr("href", filename);

	d3.csv(filename, function(error, data) {

		graph(target, data);
		//create_table(data);
	});
}
//$("#dl1").attr("display", "none");

function myGraph(datan) {
	var outputDiv = "#out1";

	createGraph(outputDiv, datan);
	$(window).resize(function() {
		createGraph(outputDiv, datan);
	});
}
