//Exemple d'utilisation du webhdfs ...
$(document).ready( function()
{

	console.log("Mise en place des variables ...");

	var host = "localhost"

	var port = 50070;

	var path = "/webhdfs/v1/"

	var curl = "curl.php";
	
	var url = host+":"+port+path;
	//répertoire pour Wada
	var dir = "user/wada";
	$.getJSON( curl, 
	{ adr : url+dir+"?op=MKDIRS&permission=777", 
		method : "put" },
	function(reponse)
	{
	
			if(reponse.boolean)
			{
				console.log("dossier crée");
			}
	});
	var user = "user";

	//mettez le lien navigateur pour acceder au fichier curl.php


	var op;

	//avec JsTreeFile AngularJs utilisez ces fonctions ...
	
	
	$('.nav').click(function(){
	
		 Lister();
	
	
	});

	$('.info').click(function(){

		op="GETFILECHECKSUM";
		$.get( curl ,
				{
			adr : url+"?op="+op,
			method : 'get'
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


	$('#upload_form').click(function(){
		op="CREATE&overwrite=true&permission=777";
		
		$.get( curl ,
				{
			adr : url+"?op="+op,
			method : 'header-put'
				}, function(reponse)
				{
					destination = reponse.split('\n')[11];
					destination = destination.split("Location: http://").join("");
				}
			);
	});
	
	$('#transfer').click(function(){
		
		uploadFile(destination);
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
		console.log("Parcourir " +dir);
		op="LISTSTATUS";
		$.getJSON( curl ,
				{
					adr : url+dir+"?op="+op,
					method : 'get'
		
				}, function(reponse)
				{
					
					//console.log(JSON.stringify(reponse.FileStatuses));
					/*$('div').text( JSON.stringify(reponse));								
					$('div').show();
					$('div').hide(1000);*/
					console.log("Fichiers : ");
					var objet = reponse.FileStatuses.FileStatus;
					for ( i = 0; i< objet.length; i++)
					{
						console.log(objet[i]);
					}
				}
		);
	}
	
});


	
