<!-- A simple project by http://jqueryajaxphp.com -->

$(function ($, window, undefined) {
    "use strict";
    $(document).ready(function () {
		$.getJSON( "assets/js/data.json", function( json ) {
		//console.log(Object.keys(json.line).map(function(key) {return json.line[key]}));
 
        if (typeof Morris != 'undefined') {
			
			//Bar chart
           Morris.Bar({
                element: 'barchart',
                axes: true,
                data: Object.keys(json.bar).map(function(key) {return json.bar[key]}),
                xkey: 'x',
                ykeys: ['y', 'z', 'a'],
                labels: ['Facebook', 'LinkedIn', 'Google+'],
                barColors: ['#707f9b', '#455064', '#242d3c']
            });
            Morris.Bar({
                element: 'barstacked',
                data: Object.keys(json.barstack).map(function(key) {return json.barstack[key]}),
                xkey: 'x',
                ykeys: ['y', 'z', 'a'],
                labels: ['Facebook', 'LinkedIn', 'Google+'],
                stacked: true,
                barColors: ['#ffaaab', '#ff6264', '#d13c3e']
            });
			
			
			//Donut Chart
            Morris.Donut({
                element: 'donutbasic',
                data : Object.keys(json.donut).map(function(key) {return json.donut[key]}),
                colors: ['#707f9b', '#455064', '#242d3c']
            });
            Morris.Donut({
                element: 'donutcolored',
                data: Object.keys(json.donutcol).map(function(key) {return json.donutcol[key]}),
                labelColor: '#303641',
                colors: ['#f26c4f', '#00a651', '#00bff3', '#0072bc']
            });
            Morris.Donut({
                element: 'donutformatted',
                data: Object.keys(json.donutfor).map(function(key) {return json.donutfor[key]}),
                formatter: function (x, data) {
                    return data.formatted;
                },
                colors: ['#b92527', '#d13c3e', '#ff6264', '#ffaaab']
            });
			
			
			//Morris Line chart
           
            Morris.Line({
                element: 'morrisline',
                data: Object.keys(json.line).map(function(key) {return json.line[key]}),
                xkey: 'elapsed',
                ykeys: ['value'],
                labels: ['value'],
                parseTime: false,
                lineColors: ['#242d3c']
            });
			
			
			// Line chart
            var decimal_data = [];
            for (var x = 0; x <= 360; x += 10) {
                decimal_data.push({
                    x: x,
                    y: Math.sin(Math.PI * x / 180).toFixed(4)
                });
            }
            Morris.Line({
                element: 'sinechart',
                data: decimal_data,
                xkey: 'x',
                ykeys: ['y'],
                labels: ['sin(x)'],
                parseTime: false,
                goals: [-1, 0, 1],
                lineColors: ['#d13c3e']
            });
			
			//Area char
            Morris.Area({
                element: 'morrisbar',
                data:Object.keys(json.area).map(function(key) {return json.area[key]}),
                xkey: 'y',
                ykeys: ['a', 'b', 'c'],
                labels: ['Series A', 'Series B', 'Series C']
            });
        }      
    });
	});
})(jQuery, window);

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}