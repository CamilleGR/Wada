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

    <!-- Style particulier pour les titre h2  -->
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
				<center><h2><a href="accueil.php" style="text-decoration:none">Wada</a> API for Twitter</h2></center>
				<p> <center><h3>  Mesurez l’impact d’un évènement avec Wada !</h3></center> </p>
			</div>
			<center>
				<ul>
					<a href="api1.php"><li class="bouton_gauche btn btn-default" style="cursor: auto;">Accueil</li></a>
					<a href="api2.php"><li class="bouton_gauche btn btn-default" style="cursor: auto;">Stream</li></a>
				</ul>
			</center>
		</div>
		

	
	<div id="formulaire" style="display:none;">
		<form  method="GET" style="z-index:98; position:relative; top:auto;  width:300px; height: 18px; margin-left: 39%;" >
			<div class="bfh-timepicker" runat="server" id="Arrivo" data-time="" style="margin-top:5em;">

				<div class="input-group" style="margin-top:7px;">
					<span class="input-group-addon"><i class="glyphicon glyphicon-comment"></i></span>
			    	<input type="text" id="path" class="form-control" placeholder="chemin vers l'enregistrement ..."  />
			    	<input type="number" id="seg" class="form-control" placeholder="Entrez votre interval de temps ..." />
			    	<a id="boutonSubmit"class="btn btn-default"><span class="glyphicon glyphicon-play"></span> Lancer Stream</a>
			    </div>
			</div>
		</form>
	</div>





	<!-- evolution du tweet -->
	<div id="courbes" style="display:none;">
		<div class="page-container" >	
			<div class="main-content">
				<div class="row">
					<div class="col-md-12">		
						<div class="panel panel-primary">
							
							<table class="table table-bordered">
								<tbody>
				                    <tr>
										<td>
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
	</div>




		<div class="center-block" >
			<!--<p><button class="btn btn-success btn-large" onclick="association()">Hashtags associés</button></p>
			<div class="bubbleChart"></div>-->
			<center><div id="hashtagsChart"></div></center>
		</div>




  	<!-- pour le traitement des graphes -->
	<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
	<!--<script src="js/charts.js"></script> -->
	<script src="js/raphael-min.js"></script>
	<script src="js/morris.min.js"></script>
  	<script src="js/indexBulle.js"></script> <!--  fait les requetes pour les hashtags  -->

	<!-- pour les bibliotheques -->
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

  	<!-- pour le traitement du stream -->
	<script src="js/traitementStream.js">/*C'est dans le fichier qu'on modifiera le script*/</script>



	
	</body>
</html>
