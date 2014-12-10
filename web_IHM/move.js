function initD3() {
	console.log("intro");

	var drag = d3.behavior.drag().on("dragstart", function() {

	}).on("drag", function() {

		d3.select(this).style("top", d3.event.y + "px").style("left", d3.event.x + "px");

	}).on('dragend', function() {

	});
	var container = d3.select("#mainWrap");
	var data = [{
		id : "#frameb",
		x : "400px",
		y : "200px"
	}];
	var closer1 = function(id) {
		container.select(id).remove();
	}
	var noteFrame = function() {
		var idF = "#f" + Math.random();
		var frame1 = container.selectAll().data([{
			id : idF,
			x : "500px",
			y : "100px"
		}]).enter().append("div").style("position", "absolute").style("left", function(d) {
			return d.x;
		}).style("top", function(d) {
			return d.y;
		}).attr("class", "frame").call(drag);

		var barTop = frame1.selectAll().data([{
			id : idF,
			x : "500px",
			y : "100px"
		}]).enter().append("div").attr("class", "borderTop");
		var borderTop = frame1.select(".borderTop");
		var buttonData = [{
			icon : "fi-zoom-in",
			funct : 0
		}, {
			icon : "fi-zoom-in",
			funct : 0
		}, {
			icon : "fi-refresh",
			funct : 0
		}, {
			icon : "fi-filter",
			funct : 0
		}, {
			icon : "fi-graph-bar",
			funct : 0
		}, {
			icon : "fi-graph-pie",
			funct : 0
		}, {
			icon : "fi-graph-trend",
			funct : 0
		}, {
			icon : "fi-list",
			funct : 0
		}, {
			icon : "fi-x",
			funct : 0
		}];
		borderTop.selectAll().data(buttonData).enter().append("em").attr("class", function(d) {
			return "barTool " + d.icon;
		});
		/*
		 * .call(closer1(function(d) {return d.id;}));
		 */

	};
	var bulleMaker = function() {
		var data = [{
			x : 100,
			y : 200
		}];
		var circle = svgBack.selectAll("circle").data(data);
		circle.exit().remove();
		circle.enter().append("circle").attr("cx", function(d) {
			return d.x;
		}).attr("cy", function(d) {
			return d.y;
		}).attr("r", function(d) {
			return d.r;
		}).style("fill", "green").call(drag);
	};

	var newButton = d3.select("#bt1");
	newButton.on("click", function() {
		noteFrame();

		console.log("e");

	});
}

