<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Wada - Base de données</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="bootstrap-table.css">
	<style type="text/css">
		body{ background-image: url(img/co.jpg);}
		@font-face {  font-family: 'quadranta';		src: url('data/aspace_demo.otf') format('truetype');	font-weight: normal;	font-style: normal; }	 
		h2 {  font-family:quadranta, sans-serif;	font-size:3em; }
		h3{	font-family:quadranta, sans-serif;	font-size:2em; }
	</style>
  </head>


  <body> 
    <?php include "menu.html"; ?>

	
	<script type="text/javascript">
		$('#table').bootstrapTable({
		    url: 'data/data1.json'
		});
	</script>
	


	
	
<?php
	$nb_fichier = 0;
	echo '<ul>';
	if($dossier = opendir('./data')){ //  le point indique dossier actuel | si l'ouverture s'est bien passé
	
		$liste = fopen('liste.json', 'w+'); // Creer un fichier qui va lister le contenu du repertoire et qui sera lu dans la table
		$i=1;
		$json = array();
		while(false !== ($fichier = readdir($dossier))){
			if($fichier != '.' && $fichier != '..') // on compte pas le dossier actuel et le precedent !
			{ 
				$nb_fichier++; 
				//echo '<li><a href="./mondossier/' . $fichier . '">' . $fichier . '</a></li>';
				
				$liste = fopen('liste.json', 'a+');
				$tmp = $fichier;
				$mavariabe= "<a href=" . '.' .  "/data/$tmp >$tmp</a>";
				$arr = array('id' => $i , 'name' => $mavariabe, 'price' => '| |'); // Petite precision on est oblige de laisser les cles id, name et price sinon la colonne se sera pa lu
				array_push($json,$arr);
				//print_r($fichier);
				
				$i++;
			} 
		} 
		echo '</ul><br />';
		//echo 'Il y a <strong>' . $nb_fichier .'</strong> fichier(s) dans le dossier';
		$liste = fopen('liste.json', 'a+');
		fwrite($liste,json_encode($json));
		
		closedir($dossier);
	}
	else
    	echo 'Le dossier n\' a pas pu être ouvert';
		
	?>


	<p><center><h3> Base de données WADA </h3> <?php	echo '<p style="font-size:17px;"> ' . $nb_fichier .' tables </p>';	?></center></p>
		

	
	<div style="overflow:auto;width:900px; height:400px; border:#000000 1px solid; margin:auto; margin-left:auto; margin-right:auto; margin-top:auto; magin-bottom:auto; ">
	

		
		<table data-url="liste.json" data-toggle="table" >
			<thead>
				<tr>
					<th data-field="id" data-halign="center" data-align="center" >Num Table</th>
					<th data-field="name" data-halign="center" data-align="center">Table</th>
					<!--<th data-field="price" data-halign="center" data-align="center">La troisème colonne</th>-->
				</tr>
			</thead>
		</table>  
	
	</div>
	

	
	
	


    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-table.js"></script>
	
				<!-- perso -->
			<script src="js/common.js"></script>
			

			<script src="js/upload.js"></script>
			
			<script>
				$(document).ready(function() {
					$("#credits").replaceWith(" { Bootstrap - " + $.fn.tooltip.Constructor.VERSION + ", JQuery - " + $.fn.jquery + ", D3.js - " + d3.version + " } ");
					
					d3.select("#imgHolder").remove();
					progressUp(".progress-bar", 0);
					
					upload_button("uploader", load_dataset);
				});
			</script>

			<script src="js/bootstrap.min.js"></script>
			<script src="js/holder.js"></script>
			<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
			<script src="js/ie10-viewport-bug-workaround.js"></script>
	
  </body>
</html>

<style>
	ul#menu_horizontal li { 

	display : inline;

	padding : 0 0.5em; /* Pour espacer les boutons entre eux */

	}

	ul#menu_horizontal {

	list-style-type : none; /* Car sinon les puces se placent n'importe où */

	}
</style>

