<!doctype html>
<html>
	<head>
    <meta charset="UTF-8">
    <title>Wada - API Twitter</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-formhelpers.min.css">
    <!-- Style particulier pour les titre h2  -->
	<style type="text/css">
		@font-face {  font-family: 'quadranta';		src: url('fonts/aspace_demo.otf') format('truetype');	font-weight: normal;	font-style: normal; }	 
		h2 {  font-family:quadranta, sans-serif;	font-size:3em; }
		h3{	font-family:quadranta, sans-serif;	font-size:2em; }
	</style>
  </head>


  <body>
    <?php include "menu.html"; ?>



    <!-- Header de la page  -->
		<div class="jumbotron">
			<div class="container">
				<center><h2>Wada API for Twitter</h2></center>
				<p> <center><h3>  Mesurez l’impact d’un évènement avec Wada !</h3></center> </p>
				<center><a href="api1.php"><li class="bouton_gauche btn btn-default" style="cursor: auto;">Accueil</li></a></center>
			</div>
		</div>
		

			



	<?php

		$mot = $_GET['hashtags'];
		$temps = $_GET['temps']*60;
		echo " stream sur $mot d'une durée de $temps minutes";


	?>
	
	</body>
</html>