var width = Math.max(document.documentElement.clientWidth, window.innerWidth || 0);
var height = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);

var bodySelection = d3.select("body");

var svgBack = bodySelection.append("svg")
    .attr("width", width)
    .attr("height", height);

var drag = d3.behavior.drag()
    .on("dragstart", function () {
    d3.select(this).style("fill", "red");
})
    .on("drag", function () {
    d3.select(this).attr("cx", d3.event.x)
        .attr("cy", d3.event.y);
})
    .on('dragend', function () {
    d3.select(this).style("fill", "black");
});





var circle = svgBack.selectAll("circle").data(d3.range(20,400)).enter()
	.append("circle").attr("cx", function (d) {
    return d;
})
    .attr("cy", function (d) {
    return d;
})
    .attr("r", function (d) {
    return d/10;
}).style("fill", "green")
    .call(drag);


function spread(transition){
	transition.attr("cx",Math.floor((Math.random() * width) + 1)).each("end",spread);
	
}

circle.transition().call(spread);
var data = [{x: 100,y: 200}];
var circle = svgBack.append("circle").attr("cx", 100)
    .attr("cy", 100)
    .attr("r", 50).style("fill", "green")
    .call(drag);


var circleSelection = svgSelection.append("rect")
                                 .attr("x", x)
                                  .attr("y", y)
                                  .attr("width", 25)
                                  .style("fill", "orange");
/*

MAth range data 




*/





svg.on("click", function() { console.log("svg"); }

var bodySelection = d3.select("body");
	var svgSelection = bodySelection.append("svg")
                                .attr("width",500 )
                               .attr("height",500 );

var drawSquare = function (x, y,size,color) {
	};

var []squares = function(){ 

var size=50;
var nbSquares=10;
var color="blue";
var squares[0]=svgSelection;
for(var i=0;i<nbSquares;i++){
squares[i] = svgSelection.append("rect").attr("x",i*10 )
                                  .attr("y",i*10 )
                                  .attr("width",size)
					.attr("height",size)
                                  .style("fill", color);
					
};
		return squares			};
var paledot = drawSquare(0,0,30,"green");
function runSquare(){
if(paledot.attr("x")<500){	
	paledot.transition().attr("x",paledot.attr("x")+10)
}else if(paledot.attr("y")<500){
	paledot.transition().attr("x",paledot.attr("y")+10)
}

}
setTimeout(function(){runSquare();},5000);

var jsonCircles = [
  { "x_axis": 30, "y_axis": 30, "radius": 30, "color" : "green" },
  { "x_axis": 70, "y_axis": 70, "radius": 30, "color" : "purple"},
  { "x_axis": 110, "y_axis": 100, "radius": 30, "color" : "red"}];

var svgContainer = d3.select("body").append("svg")
                                    .attr("width", 1000)
                                    .attr("height", 1000);

var circles = svgContainer.selectAll("circle")
                          .data(jsonCircles)
                          .enter()
                          .append("circle");

var circleAttributes = circles
                       .attr("cx", function (d) { return d.x_axis; })
                       .attr("cy", function (d) { return d.y_axis; })
                       .attr("r", function (d) { return d.radius; })
                       .style("fill", function(d) { return d.color; });
