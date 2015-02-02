


/* Supposé creer un element de type diagramme circulaire

 * @target : id de la cible DOM
 * @ width,height
 * @ data : objet de représentation csv
 *
 * */

function pieChart(target,colors, width, height, data) {

	radius = Math.min(width, height) / 2;

	var color = d3.scale.ordinal().range(colors);

	var arc = d3.svg.arc().outerRadius(radius - 0).innerRadius(radius - radius);

	var pie = d3.layout.pie().value(function(d) {
		return d.value;
	});
	/*******/
	progressUp(".progress-bar", 20);
	/*******/
	/**Refresh old svg'z*/
	d3.select(target).selectAll("svg").remove();
	
	var svg = d3.select(target).append("svg").attr("width", width).attr("height", height).append("g").attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
	/*Remove place holders*/
	d3.selectAll(".holder").remove();
	
	
	data.forEach(function(d) {
		d.value = +d.value;
	});

	g = svg.selectAll("rect").data(pie(data)).enter().append("g").attr("class", "arc");
	
	
	/*******/
	progressUp(".progress-bar", 20);
	/*******/
	g.append("path").attr("d", arc).on('mouseover', function(d, i) {
		d3.select(this).attr("class", "baro").append("title").text(function(d) {
			return d.data.label+" : " + d.data.value;
		});
	}).on('mouseout', function(d) {
		d3.select(this).attr("class", "bari");
	}).style("fill", function(d) {
		return color(d.data.label);
	});
	/*.transition().ease("bounce").duration(2000).attrTween("d", tweenPie).style("fill", function(d) {
		return color(d.data.label);
	});*/


	function tweenPie(b) {
	b.innerRadius = 0;
	var i = d3.interpolate({
		startAngle : 0,
		endAngle : 0
	}, b);
	return function(t) {
		return arc(i(t));
	};
}

	g.append("text").attr("transform", function(d) {
		return "translate(" + arc.centroid(d) + ")";
	}).style("text-anchor", "middle").text(function(d) {
		return d.data.label;
	});
	
	/*******/
	progressUp(".progress-bar", 20);
	/*******/
	
}

function createPieChart(target,colors, width, height, filename) {
	d3.csv(filename, function(error, data) {

		pieChart(target,colors, width, height, data);

	});
}

function createVizPie() {

	var datan = "data/data1.csv";
	progressUp(".progress-bar", 0);
	
	var colors = get_random_color_set(10);
	
	myPieChart(datan,colors);
	
	$(window).resize(function() {
		myPieChart(datan,colors);
	});
	progressUp(".progress-bar", 20);

}

function myPieChart(datan,colors) {
	var outputDiv = "#out1";
	createPieChart(outputDiv,colors, $(outputDiv).width(), Math.max($(outputDiv).height(), $(window).height() / 2), datan);
}
