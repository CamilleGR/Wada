//Exemple d'utilisation du webhdfs ...
$(document).ready( function()
		{

	console.log("Mise en place des variables ...");

	var host = "localhost"

		var port = 50070, permission=755;

	var path = "/webhdfs/v1/"

		//répertoire ou fichier courant lorsque le fichier est cliqué
		var dir = "";

	var user = "user";

	var url = host+":"+port+path;

	//mettez le lien navigateur pour acceder au fichier curl.php

	var curl = "curl.php";

	var op, destination;

	//avec JsTreeFile utilisez ces fonctions ...
	$('.nav').click(function() 
			{
		console.log("Parcourir dossier actuel");
		op="LISTSTATUS";
		$.get( curl ,
				{
			adr : url+dir,
			op : op
				}, function(reponse)
				{
					if(!reponse.ok)
					{

						$("div").text(reponse);								
						$('div').show();
						$('div').hide(1000);
					}
				}
		);
	});

	$('.info').click(function(){

		op="GETFILESTATUS";
		$.get( curl ,
				{
			adr : url+dir,
			op : op
				}, function(reponse)
				{
					if(reponse.FileSatuses ==='[object Array]')
					{
						console.log("tableau trouve !");	
					}

				}
		);


	});

	$('.open').click(function(){

		op="";
		console.log("Ouverture fichier et obtenir le path webhdfs ...");
		$.get( curl ,
				{
			adr : url+op
				}, function(reponse)
				{
					var dataNode = reponse.getResponseHeader('Location');
					if( dataNode!="")
					{
						$.get( curl, 
								{
							file : dataNode

								}, function( reponse ){
									//genere le lien de DL
									$('div').append('<a href=>"'+dataNode+'">Telecharger</a>');

								});

					}
				}
				);
	
	});


	$('.upload').click(function(){
		op="APPEND";
		console.log("Envoie de fichier, requête POST obvious ...");
		$.post( curl ,
				{
			adr : url+op
				}, function(reponse)
				{
					if(!reponse)
					{
						$("div").text(reponse);								
						$('div').show();
						$('div').hide(1000);

						//si append fonctionne on peut recuperer le data node et y ajouter
						request.getResponseHeader
						$.post( curl, 
								{
							adr : reponse[1]
								}, function(reponse)
								{
									$("div").html(reponse);								
									$('div').show();
									$('div').hide(1000);
								}
						);
					}
				}
			);
	});


		$('.rename').click(function(){
			op="RENAME";
			console.log("Demande au serveur de changer ...");
			$.post( curl ,
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
	$('.delete').click(function(){
		op="DELETE";
		console.log("Suppression requête delete ...");
		$.post( curl ,
				{
			adr : url+op
				}, function(reponse)
				{
					console.log("curl exécuté");
					$("div").html(reponse);								
					$('div').show();

				}
		);

	});

	console.log("fin mise en place ...");


	console.log("En attente ...");

		}
);





