//Exemple d'utilisation du webhdfs ...
$(document).ready( function()
{

	console.log("Mise en place des variables ...");

	var host = "localhost"

	var port = 50070, permission=755;

	var path = "/webhdfs/v1/"

	//répertoire ou fichier courant lorsque le fichier est cliqué
	var dir = "tmp"+"?op=";

	var user = "user";

	var url = host+":"+port+path;

	//mettez le lien navigateur pour acceder au fichier curl.php

	var curl = "curl.php";

	var op, destination;

	//avec JsTreeFile AngularJs utilisez ces fonctions ...
	
	
	$('.nav').click(function(){
	
		 Lister();
	
	
	});

	$('.info').click(function(){

		op="GETFILESTATUS";
		$.get( curl ,
				{
			adr : url+dir,
			op : op
				}, function(reponse)
				{
					if(reponse.FileStatuses)
					{
						console.log("tableau trouve !");	
					}

				}
		);


	});

	$('.open').click(function(){

		op="OPEN";  
		console.log("Ouverture fichier et obtenir le path webhdfs ...");
		$.get( curl ,
				{
					adr : url+op,
					method : 'header'
				}, function(reponse)
				{
					//console.log("DataNode : " +reponse);
					var tab = reponse.split('\n');
					//console.log(tab[9]);
					
					destination = tab[9].split("Location:").join("");
					
					console.log(destination);
					
				}
			);
	
	});


	$('#file').click(function(){
		op="APPEND&buffersize=512";
		
		$.get( curl ,
				{
			adr : url+dir+op,
			method : 'post'
				}, function(reponse)
				{
					console.log("reponse  : " +reponse);
					//on peut recuperer le data node et y ajouter le fichier
					var tab = reponse.split('\n');
					destination = tab[11].split("Location:").join("");
					uploadFile(destination);
			
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
						console.log("curl exÃ©cutÃ©");
						$("div").html(reponse);								
						$('div').show();
						$('div').hide(1000);
					}
			);


		});
	$('.delete').click(function(){
		op="DELETE";
		console.log("Suppression requÃªte delete ...");
		$.get( curl ,
				{
			adr : url+op,
			method : 'delete'
				}, function(reponse)
				{
					console.log("curl exÃ©cutÃ©");
					$("div").html(reponse);								
					$('div').show();

				}
		);

	});

	console.log("fin mise en place ...");


	console.log("En attente ...");
	



	function currentUser(){
	op="GETHOMEDIRECTORY";
		
		$.getJSON( curl,
		{
		adr : url+dir+op,
		method : 'get'
		}, function(reponse)
			{
				console.log(reponse);
		
			}
		);
	}

	function Lister()
	{
		console.log("Parcourir dossier actuel");
		op="LISTSTATUS";
		$.getJSON( curl ,
				{
					adr : url+dir+op,
					method : 'get'
		
				}, function(reponse)
				{
					return reponse.FileStatus;
					/*console.log(JSON.stringify(reponse.FileStatuses));
					$('div').text( JSON.stringify(reponse));								
					$('div').show();
					$('div').hide(1000);
					console.log("Fichiers : ");
					var objet = reponse.FileStatuses.FileStatus;
					for ( i = 0; i< objet.length; i++)
					{
						console.log(objet[i]);
					}*/
				}
		);
	}
	
});


	
