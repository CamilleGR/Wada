
 d3.csv("data/data3.csv", function (data) { //   --------------------------------------------------------	NOM OU CHEMIN DU CSV ------------------------------------------------------ // 

        data.forEach( function (d) {
            d.value = parseInt(d.value); // ----------------------------------------------------------- value est le 2eme attribut dans MON fichier csv en local, il faudra le modifier avec l'attribut de VOTRE csv
        });


        var margin =  {top: 20, right: 10, bottom: 20, left: 40},
            selectorHeight = 20
            width = 1000 - margin.left - margin.right,
            height = 500 - margin.top - margin.bottom - selectorHeight,
            barWidth = 30;

        var numBars = Math.round(width/barWidth);


        var xscale = d3.scale.ordinal()
                .domain(data.slice(0,numBars).map(function (d) { return d.label; })) // ------------------------------------------------------------------- Pareil pour label , c'est le 1er attribut il faudra le changer 
                .rangeBands([0, width]),
            yscale = d3.scale.linear()
                .domain([0, d3.max(data, function (d) { return d.value; })])
                .range([height, 0]);

        var xAxis  = d3.svg.axis().scale(xscale).orient("bottom"),
            yAxis  = d3.svg.axis().scale(yscale).orient("left");

        var svg = d3.select("#diagram").append("svg")
                    .attr("width", width + margin.left + margin.right)
                    .attr("height", height + margin.top + margin.bottom + selectorHeight);

        var diagram = svg.append("g")
                    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        diagram.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0, " + height + ")")
                .call(xAxis);

        diagram.append("g")
                .attr("class", "y axis")
                .call(yAxis);

        var bars = diagram.append("g");
        bars.selectAll("rect")
            .data(data.slice(0, numBars), function (d) {return d.label; })
            .enter().append("rect")
            .attr("class", "bar")
             .attr("x", function (d) { return xscale(d.label); })
            .attr("y", function (d) { return yscale(d.value); })
            .attr("width", xscale.rangeBand())
            .attr("height", function (d) { return height - yscale(d.value); })
			.on('mouseover', function(d,i) {
				d3.select(this)
				.attr("class", "bara");  } )

			.on('mouseout', function(d) {
				d3.select(this)
				.attr("class", "bar");
			})
			 
			.append("title")
			.text(function(d) {
			return " La valeur est de :  " + d.value;
			});
			


        var displayed = d3.scale.quantize()
            .domain([0, width])
            .range(d3.range(data.length));

        diagram.append("rect")
            .attr("transform", "translate(0, " + (height + margin.bottom) + ")")
            .attr("class", "mover")
            .attr("x", 0)
            .attr("y", 0)
            .attr("height", selectorHeight)
            .attr("width", Math.round(parseFloat(numBars * width)/data.length))
            .attr("pointer-events", "all")
            .attr("cursor", "ew-resize")
            .call(d3.behavior.drag().on("drag", display));

        function display () {
            var x = parseInt(d3.select(this).attr("x")),
                nx = x + d3.event.dx,
                w = parseInt(d3.select(this).attr("width")),
                f, nf, new_data, rects;

            if ( nx < 0 || nx + w > width ) return;

            d3.select(this).attr("x", nx);

            f = displayed(x);
            nf = displayed(nx);

            if ( f === nf ) return;

            new_data = data.slice(nf, nf + numBars);

            xscale.domain(new_data.map(function (d) { return d.label; }));
            diagram.select(".x.axis").call(xAxis);

            rects = bars.selectAll("rect")
                .data(new_data, function (d) {return d.label; });

            rects.attr("x", function (d) { return xscale(d.label); });

            rects.enter().append("rect")
                .attr("class", "bar")
                .attr("x", function (d) { return xscale(d.label); })
                .attr("y", function (d) { return yscale(d.value); })
                .attr("width", xscale.rangeBand())
                .attr("height", function (d) { return height - yscale(d.value); });


         

        };
		

});
 

 
function donut () {

    radius = Math.min(width, height) / 2;

var color = d3.scale.ordinal()
    .range(["#3366CC", "#FF0000","#FF9900", "#088A08", "#FF0040", "#40FF00", "#990099"]);

var arc = d3.svg.arc()
    .outerRadius(radius - 2)
    .innerRadius(radius - 115);

var pie = d3.layout.pie()

    .value(function(d) { return d.value; });

var svg = d3.select("body").append("svg")
    .attr("width", width)
    .attr("height", height)
  .append("g")
    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

d3.csv("data3.csv", function(error, data) {


  data.forEach(function(d) {
    d.value = +d.value;
  });

   g = svg.selectAll("rect")
      .data(pie(data))
    .enter().append("g")
      .attr("class", "arc");

  g.append("path")
      .attr("d", arc)
	  .on('mouseover', function(d,i) {
				d3.select(this)
				.attr("class", "baro")
				.append("title")
					.text(function(d) {
					return " La valeur est de :  " + d.data.value;
				});				} )
	.on('mouseout', function(d) {
				d3.select(this)
				.attr("class", "bari");
			})
	
	  .transition()
    .ease("bounce")
    .duration(2000)
    .attrTween("d", tweenPie)
      .style("fill", function(d) { return color(d.data.label); });
			
	  
	  function tweenPie(b) {
  b.innerRadius = 0;
  var i = d3.interpolate({startAngle: 0, endAngle: 0}, b);
  return function(t) { return arc(i(t)); };
}

  g.append("text")
      .attr("transform", function(d) { return "translate(" + arc.centroid(d) + ")"; })
      .style("text-anchor", "middle")
      .text(function(d) { return d.data.label; });

});

}
