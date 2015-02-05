/* Supposé creer un element de type diagramme circulaire

 * @target : id de la cible DOM
 * @ width,height
 * @ data : objet de représentation csv
 *
 * */

function pieChart(target, colors, data) {
	var  progress = 0;
	progressUp(".progress-bar", progress);
	var width = $(target).width();
	var height = Math.max($(target).height(), $(window).height() / 2);
	
	
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

	$("#dl1").attr("download", "data.csv").attr("href", filename);

	d3.csv(filename, function(error, data) {
		create_table(data);
		pieChart(target, colors, data);

	});
}

function loadPie(data) {
	$("#dl1").attr("display", "none");

	var colors = get_random_color_set(10);

	var target = "#out1";
	pieChart(target, colors, data);
	$(window).resize(function() {
		pieChart(target, colors, data);
	});
}

function createVizPie() {

	var datan = "data/data1.csv";
	progressUp(".progress-bar", 0);

	var colors = get_random_color_set(10);

	myPieChart(datan, colors);

	$(window).resize(function() {
		myPieChart(datan, colors);
	});
	progressUp(".progress-bar", 20);

}

function myPieChart(datan, colors) {
	var outputDiv = "#out1";
	createPieChart(outputDiv, colors, datan);
}
