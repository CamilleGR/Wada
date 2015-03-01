$(document).ready( function(){

				console.log("Mise en place des variables ...");

				var host = "localhost"
				
				var port = 50070;
				
				var path = "/webhdfs/v1/"
				
				var dir = "default/";
				
				var user = "user";
				
				var url = host+":"+port+path;
				
				//mettez le lien navigateur pour acceder au fichier curl.php
				
				var curl = "http://localhost/curl.php?adr=";
				
				var res;
				
				
				$('.nav').click(function(){ 
				
					console.log("Parcourir dossier actuel");
					op="?op=LISTSTATUS";
					$.get( curl+url+op, function(data){ 
						//synchronisation ...
						$(data).each(function( i , val)
							{ res+=val }); 
						});
					
					console.log(res);
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



