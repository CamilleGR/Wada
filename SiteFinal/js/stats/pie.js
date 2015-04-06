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

	//$("#dl1").attr("download", "data.csv").attr("href", filename);

	//d3.csv(filename, function(error, data) {
		pieChart(target, colors, filename.tab);
		create_table(filename.tab, filename.stats);

	//});
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

function createVizPie(object) {

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

	myPieChart(object, colors);

	$(window).resize(function() {
		myPieChart(object, colors);
	});
	progressUp(".progress-bar", 20);

}

function myPieChart(datan, colors) {
	var outputDiv = "#out1";
	createPieChart(outputDiv, colors, datan);
}
