$(document).ready( function()
	{

				console.log("Mise en place des variables ...");

				var host = "localhost"
				
				var port = 50070;
				
				var path = "/webhdfs/v1/"
				
				var dir = "default/";
				
				var user = "user";
				
				var url = host+":"+port+path;
				
				//mettez le lien navigateur pour acceder au fichier curl.php
				
				var curl = "curl.php";
				
		
				
				$('.nav').click(function() 
				{
					console.log("Parcourir dossier actuel");
					op="?op=LISTSTATUS";
					$.get( curl ,
					{
						adr : url+op
					}, function(reponse)
						{
							console.log("curl exécuté");
							$("div").html(reponse);								
							$('div').show();
							$('div').hide(1000);
						}
					);
				});
						
			
				
				$('.open').click(function(){
				
					console.log("Ouverture fichier et obtenir le path webhdfs ...");

				});
				
				
				$('.upload').click(function(){
				
				
				});
				
				$('.delete').click(function(){
				
				});
				
				console.log("fin mise en place ...");
				
				
				console.log("En attente ...");
				
	}
);


	


