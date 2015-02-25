<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Wada - Base de données</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="bootstrap-table.css">
	<style>
		ul#menu_horizontal li { display : inline; padding : 0 0.5em; /* Pour espacer les boutons entre eux */ }
		ul#menu_horizontal { list-style-type : none; /* Car sinon les puces se placent n'importe où */}
	</style>
  </head>


  <body> 
    <?php include "menu.html"; ?>

	<p> C'est notre bdd </p>
	
	<div style="overflow:auto;width:900px; height:600px; border:#000000 1px solid; margin:auto; margin-left:auto; margin-right:auto; margin-top:auto; magin-bottom:auto; ">
	
	<p> C'est notre bdd <p>
	
	<table data-url="data/data1.json" data-toggle="table" >
	    <thead>
		    <tr>
		        <th data-field="id" data-halign="center" data-align="center" >Item ID</th>
		        <th data-field="name" data-halign="center" data-align="center">Item Name</th>
		        <th data-field="price" data-halign="center" data-align="center">Item Price</th>
		    </tr>
	    </thead>
	</table>  
	</div>


	<?php
	$nb_fichier = 0;
	echo '<ul>';
	if($dossier = opendir('./data')){ //  le point indique dossier actuel | si l'ouverture s'est bien passé
		while(false !== ($fichier = readdir($dossier))){
			if($fichier != '.' && $fichier != '..') // on compte pas le dossier actuel et le prcedent !
			{ 
				$nb_fichier++; 
				echo '<li><a href="./mondossier/' . $fichier . '">' . $fichier . '</a></li>';
			} 
		} 
		echo '</ul><br />';
		echo 'Il y a <strong>' . $nb_fichier .'</strong> fichier(s) dans le dossier';
		closedir($dossier);
	}
	else
    	echo 'Le dossier n\' a pas pu être ouvert';

	?>


    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-table.js"></script>
	<script type="text/javascript">
		$('#table').bootstrapTable({
		    url: 'data/data1.json'
		});
	</script>
  </body>
</html>

