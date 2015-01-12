<?php
echo '
<!DOCTYPE HTML5>
<html>
	<head>
		<title> Test webHDFS </title>
		<link rel="stylesheet" type="text/css" href="style.css" media="all"/>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	</head>
	
	<body>
		
	<form action ="index.php">
		<p> IP------- <input type="text" name="ip" value="192.168.1.32"/></p>
		<p> Port----- <input type="text" name="port"  value="50070"/></p>
		<p>Operation : <select name="op">
				<option value ="" selected ="selected">choisissez l\'operateur</option>
				<option>GET</option>
				<option>PUT</option>
				<option>POST</option>
				<option>DELETE</option>
			</select>
		</p>
		
		
	';
	/* Modifier le script php avec le jscript proposé
	
	<script>;
	
	/*	Marche pour un GETHOMEDIRECTORY / LISTSTATUS, il faut modifier le code pour pouvoir charger les fichiers sur le HDFS
		Selon r�mi, on doit pouvoir envoyer une requ�te POST avec un $.post() de ajax, puis il faudra faire un 2e formulaire ( � envoyer
		avec la m�thode PUT et le nom du fichier � l'int�rieur.
		Bon courage ;)
		
		Pour upload un fichier :
		- On utilise curl -i -X PUT "http://192.168.1.32:50070/webhdfs/v1/chemin.../?op=CREATE
		- On re�oit un petit texte avec HTTP REDIRECTION, copier la l'adresse location ( il faut quand m�me modifier l'ip de la machine ) 			et la mettre � la place de la requ�te qu'on avait pr�c�dement.
		- On obtient quelque chose comme �a 
		curl -i -X PUT "http://192.168.1.32:50075/webhdfs/v1/testWeb/newFile?op=CREATE&namenoderpcaddress=192.168.1.32:8020&overwrite=false"
		-T test.csv 

	*/
	
	/*
	*-function sendForm(){
		var xml = new XMLHttpRequest();
	    var op= 	document.getElementById("op").value;
	    var ip= 	document.getElementById("ip").value;
	    var port= 	document.getElementById("port").value;
	    var method= document.getElementById("method").value;
	    var path= 	document.getElementById("path").value;
	   
	    var requete="http://"+ip+":"+port+"/webhdfs/v1/"+path+"?op="+op;
	//document.getElementById("form").action=requete;
	//document.getElementById("form").method=method;	CELA NE MARCHE PAS AVEC LE FORMULAIRE, AUCUNE ID�E DE POURQUOI !!!! 
	//document.forms["form"].submit();
		
	document.location.href=requete; // Redirection vers la requete qui a �t� construite
	//alert(requete);
	
	//Traiter les requ�tes en php

	
	}
	

	</script>
	
	*/
	echo'
</body>


</html>';

//Gestion de requ�tes 

if($_POST)
	{
	$r = new HttpRequest('index.html', HttpRequest::METH_POST);


    try {
        echo $r->send()->getBody();
    } catch (HttpException $ex) {
        echo $ex;
    }
    catch(HttpException $erreur) {
    	echo $erreur;
    }
if($_GET)
{

}