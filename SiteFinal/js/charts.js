
$(function ($, window, undefined) {
    $(document).ready(function ()
    {

		$.getJSON( "data/exempleJSON.json", function( json ) 
        {
		//console.log(Object.keys(json.line).map(function(key) {return json.line[key]}));
            if (typeof Morris != 'undefined')
            {	
    			//Morris Line chart
                Morris.Line({
                      element: 'morrisline',
                      data: Object.keys(json.EvoTweet).map(function(key) {return json.EvoTweet[key]}),
                      xkey: 'label',
                      ykeys: ['value'],
                      labels: ['value'],
                      parseTime: false,
                      lineColors: ['#242d3c']
                  });
            }      
        });
	});
});