//Exemple d'utilisation du webhdfs ...
$(document).ready( function()
{

	console.log("Mise en place des variables ...");

	var host = "localhost:"
	var port = 50070;
	var path = "/webhdfs/v1/"
	var curl = "curl.php";	
	var permission = 755;
	//Initialisation du répertoire pour Wada
	var dir = "wada/";
	
	
	$.getJSON( curl, 
	{ host : host,
			port : port,
			path : path,
			dir : dir,
			method : 'put',
			op : "?op=MKDIRS&permission="+permission
			},
	function(reponse)
	{
	
			if(reponse.boolean)
			{
				console.log("dossier crée");
			}
	});
	var fileName, dataNode;

	//mettez le lien navigateur pour acceder au fichier curl.php
	var op;

	//avec JsTreeFile AngularJs utilisez ces fonctions ...
	
	Lister();
	
	$('.nav').click(function(){
	
		 Lister();
	
	
	});

	$('.info').click(function(){
		
		op="GETFILECHECKSUM";
		$.get( curl ,
				{
			host : host,
			port : port,
			path : path,
			dir : dir+"Avatar.png",
			op : "?op="+op,		
			method : 'get'
				}, function(reponse)
				{
					if(reponse)
					{
						console.log(reponse);	
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

	$('#file1').change(function(){
		uploadFile();
		op="CREATE";
		fileName = $("#file1").val();
		dataNode = $.get( curl, {
			host : host,
			port : port,
			path : path,
			dir : dir,
			op : "?op="+op+"&overwrite=true&permission="+permission,							
			method : 'header-put'
			}, function( reponse ){
			{
				
				
				dataNode = reponse.split("\n");
				dataNode = dataNode[11].replace("Location: ","");
				var prefixe = "http://sandbox.hortonworks.com:"+50075+path+dir+"?op="+op;
				console.log(prefixe);
				dataNode = dataNode.replace(prefixe,"");				
				console.log(dataNode);
				$('#transfer').removeAttr("disabled");
				return dataNode;
				
			}
		});
		
		
		
		
	});
	
	$("#transfer").click(function()
	{
		console.log("upload de : " + fileName + " vers : " + dataNode);
		
		$.get( curl ,{
			host : host,
			port : 50075,
			path : path,
			dir : dir+fileName, 
			op : "?op=CREATE&overwrite=true&permission="+permission,
			datanode : dataNode,
			method : 'put', 
			hdfs : true  ,
			file : fileName}, 
			function(reponse){
			console.log(reponse);
			fileName ="";
		});
		
		$(this).prop("disabled",true);
		Lister();
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
					host : host,
					port : port,
					path : path,
					dir : dir,
					op : "?op="+op,					
					method : 'get'
		
				}, function(reponse)
				{
					
					//console.log(JSON.stringify(reponse.FileStatuses));
					/*$('div').text( JSON.stringify(reponse));								
					$('div').show();
					$('div').hide(1000);*/
					console.log("Fichiers : ");
					
					if( reponse !=null )
					{
						
						var objet = reponse.FileStatuses.FileStatus;
						
						for ( i = 0; i< objet.length; i++)
						{
							var lignes =(objet[i].pathSuffix);
							
							$('#fichiers ul').append('<li>'+lignes+'</li>');
						}
					}
				}
		);
	}
	
});


	
