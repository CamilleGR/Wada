<!doctype html>
<html>
	<head>
    <meta charset="UTF-8">
    <title>Wada - API Twitter</title>

    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-formhelpers.min.css">
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Noto+Sans:400,700,400italic"  id="style-resource-3">
    <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,600,200italic,600italic&subset=latin,vietnamese' rel='stylesheet' type='text/css'>

	
	<link rel="stylesheet" href="css/styletweet.css"  id="style-resource-4">   

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
				<center><h2><a href="accueil.php" style="text-decoration:none">Wada </a>API for Twitter</h2></center>
				<p> <center><h3>  Mesurez l’impact d’un évènement avec Wada !</h3></center> </p>
				<center><a href="api1.php"><li class="bouton_gauche btn btn-default" style="cursor: auto;">Accueil</li></a></center>
			</div>
		</div>
	



	<!-- evolution du tweet -->

		<div class="page-container">	
			<div class="main-content">
				<div class="row">
					<div class="col-md-12">		
						<div class="panel panel-primary">
							
							<table class="table table-bordered">
								<tbody>
				                    <tr>
										<td>
											<p class="text-center"><strong>Evolution de l'hashtag "<?php echo $_GET['hashtags']; ?>" sur une durée de <?php echo $_GET['temps']; ?> heures. </strong></p>
											<div id="morrisline" style="height: 300px"></div>
										</td>
									</tr>
								</tbody>
							</table>
							
						</div>	
					</div>
				</div>
			</div>
		</div>


	<div class="center-block">
	<p><button class="btn btn-success btn-large" onclick="association()">Hashtags associés</button></p><div class="bubbleChart"/>
	</div>





		<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
	  	<script src="js/charts.js"></script> <!-- fait les requetes pour levolution des tweets -->
		<script src="js/raphael-min.js"></script>
		<script src="js/morris.min.js"></script>
	  	<script src="js/indexBulle.js"></script> <!--  fait les requetes pour les hashtags  -->

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
  
	</body>
</html>