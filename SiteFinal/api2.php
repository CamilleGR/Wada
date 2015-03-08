<!doctype html>
<html>
	<head>
    <meta charset="UTF-8">
    <title>Wada - API Twitter</title>

    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-formhelpers.min.css">


    <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,600,200italic,600italic&subset=latin,vietnamese' rel='stylesheet' type='text/css'>
    <script src="http://phuonghuynh.github.io/js/bower_components/jquery/dist/jquery.min.js"></script>
  	<script src="http://phuonghuynh.github.io/js/bower_components/d3/d3.min.js"></script>
  	<script src="http://phuonghuynh.github.io/js/bower_components/d3-transform/src/d3-transform.js"></script>
  	<script src="http://phuonghuynh.github.io/js/bower_components/cafej/src/extarray.js"></script>
  	<script src="http://phuonghuynh.github.io/js/bower_components/cafej/src/misc.js"></script>
  	<script src="http://phuonghuynh.github.io/js/bower_components/cafej/src/micro-observer.js"></script>
  	<script src="http://phuonghuynh.github.io/js/bower_components/microplugin/src/microplugin.js"></script>
  	<script src="http://phuonghuynh.github.io/js/bower_components/bubble-chart/src/bubble-chart.js"></script>
  	<script src="http://phuonghuynh.github.io/js/bower_components/bubble-chart/src/plugins/central-click/central-click.js"></script>
  	<script src="http://phuonghuynh.github.io/js/bower_components/bubble-chart/src/plugins/lines/lines.js"></script>
  	<script src="js/indexBulle.js"></script>

    
	<style type="text/css">
		@font-face {  font-family: 'quadranta';		src: url('fonts/aspace_demo.otf') format('truetype');	font-weight: normal;	font-style: normal; }	 
		h2 {  font-family:quadranta, sans-serif;	font-size:3em; }
		h3{	font-family:quadranta, sans-serif;	font-size:2em; }

		.bubbleChart {
	      min-width: 100px;
	      max-width: 500px;
	      height: 500px;
	      margin: 0 auto;
	    }
	    .bubbleChart svg{
	      background: #fff;
	    }
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

		
		<p><button class="btn btn-success btn-large" onclick="association()">Hashtags associés</button></p>		
		<div class="bubbleChart"/>
	</body>
</html>