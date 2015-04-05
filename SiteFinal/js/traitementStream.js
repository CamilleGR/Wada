$(document).ready( function(){

	$('#formulaire').fadeIn();
	
	
	$('#boutonSubmit').click(function(e){
		$('#formulaire').fadeOut();

		//quand on clique on peut afficher les graphes
		
		/********************************************************************************************************************************
		*********************************************************************************************************************************
						Debut du traitement pour l'évolution => Courbes pour l'évolution
		*********************************************************************************************************************************
		*********************************************************************************************************************************/
		$.get( "proxyWebService.php",
		{
			action:"evoTweet",
			path:$('#path').val(),
			seg:$('#seg').val()
		}
		, function( json ) 
        {
        	  console.log(json);
		//console.log(Object.keys(json.line).map(function(key) {return json.line[key]}));
            if (typeof Morris != 'undefined')
            {	
    			//Morris Line chart
                Morris.Line({
                      element: 'morrisline',
                      data: Object.keys(json.evoTweet).map(function(key) {return json.evoTweet[key]}),
                      xkey: 'label',
                      ykeys: ['value'],
                      labels: ['value'],
                      parseTime: false,
                      lineColors: ['#242d3c']
                  });
            }      
        
        /********************************************************************************************************************************
		*********************************************************************************************************************************
						Debut du traitement pour les hashtags => Courbes pour les hashtags
		*********************************************************************************************************************************
		*********************************************************************************************************************************/
		$.get( "proxyWebService.php",
		{
		action:"associatedHashtags",
		path:$('#path').val(),
		}
		, function( json ) 
        	{
        	  console.log(json);
		//console.log(Object.keys(json.line).map(function(key) {return json.line[key]}));
            if (typeof Morris != 'undefined')
            {	
    			//Morris Line chart
                Morris.Line({
                      element: 'hashtagsChart',
                      data: Object.keys(json.hashtags).map(function(key) {return json.hashtags[key]}),
                      xkey: 'label',
                      ykeys: ['value'],
                      labels: ['value'],
                      parseTime: false,
                      lineColors: ['#242d3c']
                  });
            }      
        });
		$('#hashtagsChart').fadeIn();
        
        
        
        });
		$('#courbes').fadeIn();
		
		

	});



});
