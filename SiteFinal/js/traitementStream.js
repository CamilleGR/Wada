$(document).ready( function(){

	$('#formulaire').fadeIn();

	
	$('#boutonSubmit').click(function(e){
		$('#formulaire').fadeOut();
		var pathVar = $('#path').val()+"/*/part*";
		//quand on clique on peut afficher les graphes
		
		/********************************************************************************************************************************
		*********************************************************************************************************************************
						Debut du traitement pour l'évolution => Courbes pour l'évolution
		*********************************************************************************************************************************
		*********************************************************************************************************************************/
		$.get( "proxyWebService.php",{action:"evoTweet",path:pathVar,seg:$('#seg').val()}, function( json ) {
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
        
						$('#hashtagButton').fadeIn();	

        
        });
	        					$('#courbes').fadeIn();

	

	});

		
		$('#hashtagButton').click(function(){
			var pathVar = $('#path').val()+"/*/part*";
			console.log("click");
		$.get( "proxyWebService.php",{action:"associatedHashtags",path:pathVar}, function( json ) {
        				 console.log(json);
        				json.hashtags.forEach(function(elem){
        					var tuple = $('<tr></tr>');
        					tuple.append($('<td>'+elem.label+"</td>"));
        				        tuple.append($('<td>'+elem.value+"</td>"));
        				        $('#hashtagTable tbody').append(tuple);
        				});

        				$('#hashContainer').fadeToggle();
        				$('#hashtagButton').fadeToggle();
        				});
        	
		});
        
        /*
        <table class="table">
        	<thead>
        		<tr>
        			<th>hashtags</th><th>Fréquence ( % ) </th></tr>
        	</thead>
        	<tbody>
        		<tr>
        			<td>cell is row 0, column 0</td>
        			<td>cell is row 0, column 1</td>
        		</tr>
        	</tbody>
        </table>
        
        */
        
        
		
		

});
