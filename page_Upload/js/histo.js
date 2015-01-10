var margin = {
	top : 40,
	right : 40,
	bottom : 40,
	left : 40
}, width = 600 - margin.left - margin.right, height = 300 - margin.top - margin.bottom, barWidth = 30;

var svg = d3.select("#diagram").append("svg").attr("id","charts").attr("width", width + margin.left + margin.right)
												.attr("height", height + margin.top + margin.bottom).append("g")
												.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

var numBars = Math.round(width / barWidth);

// Axes
var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

var y = d3.scale.linear().range([height, 0]);

var xAxis = d3.svg.axis().scale(x).orient("bottom").ticks(10);

var yAxis = d3.svg.axis().scale(y).orient("left").ticks(10);


function histo(data) {
	
	

	data.forEach(function(d) {
		d.value = parseInt(d.value);
	});

	x.domain(data.slice(0, numBars).map(function(d) {
		return d.label;
	}));
	y.domain([0, d3.max(data, function(d) {
		return d.value;
	})]);
	
	
	d3.selectAll(".axis").remove();
	
	
	svg.append("g").attr("class", "x axis").attr("transform", "translate(0," + height + ")").call(xAxis)
	.selectAll("text").style("text-anchor", "start").attr("dx", "-1em").attr("dy", "0.5em").attr("transform", "rotate(0)");

	svg.append("g").attr("class", "y axis").call(yAxis).append("text").attr("transform", "rotate(-90)").attr("y", 6).attr("dy", ".71em").style("text-anchor", "end");

	var sel = svg.selectAll(".bar").data(data);

	sel.enter().append("rect").attr("class", "bar").style("fill", "steelblue");

	sel.attr("x", function(d) {
		return x(d.label);
	}).attr("width", x.rangeBand());

	sel.attr("y", height).attr("height", 0);

	sel.transition().attr("y", function(d) {
		return y(d.value);
	}).attr("height", function(d) {
		return height - y(d.value);
	});

}

function createHistogram(filename) {
	d3.csv(filename, function(error, data) {

		histo(data);

	});
}


function init () {
	d3.select("#data1")
        .on("click", function(d,i) {
            createHistogram('data/data1.csv');
        });
    d3.select("#data2")
        .on("click", function(d,i) {
            createHistogram('data/data2.csv');
        });
   d3.select("#data3")
        .on("click", function(d,i) {
            createHistogram('data/data3.csv');
        });
  
}

init();
