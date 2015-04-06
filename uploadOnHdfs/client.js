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
	

	
	
	$('.delete').click(function(){
		op="DELETE";
		console.log("Suppression requÃªte delete ...");
		if(fileName=="")
		{
			return;
		}
		$.get( curl ,
				{
					host : host,
					port : port,
					path : path,
					dir : dir+fileName,
					op : "?op="+op,					
					method : 'delete'
				}, function(reponse)
				{
					$('#dl').empty();
					console.log(reponse);
					Lister();
				}
		);

	});

	console.log("fin mise en place ...");


	console.log("En attente ...");
	
	//selection fichiers
	
	$('#fichiers').on( 'click','li',function(){
	
	
		fileName = $(this).text();
		
		
		
		console.log('fichier selectionné : ' +fileName);		
		
		if(fileName=="")
		{
			return;
		}
		op="OPEN";  
		console.log("Ouverture fichier et obtenir le path webhdfs ...");
		$.get( curl ,
				{
					host : host,
					port : port,
					path : path,
					dir : dir+fileName, 
					method : 'header',
					op : "?op="+op
				}, function(reponse)
				{
					//console.log("DataNode : " +reponse);
					var tab = reponse.split('\n');
					//console.log(tab[9]);					
					destination = tab[8].split("Location:").join("");
					destination = destination.replace("sandbox.hortonworks.com","localhost");
					console.log(destination);			
					$('.delete').prop("disabled",false);
					$('#fichiers h3').replaceWith('<h3>Fichiers :<a  href="'+destination+'" id="dl" >'+'télécharger'+'</a> </h3>');
				}
			);
		
	});
	
	$('#fichiers').on( "click", "a", function(){
		$(this).empty();
	});
	
	
		
	
	
	function Lister()
	{
		$('#fichiers ul').empty();
		fileName ="";
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


	
