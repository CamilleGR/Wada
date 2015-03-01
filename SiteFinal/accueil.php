<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Wada - Accueil</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <!-- Style particulier pour les titre h2  -->
	<style type="text/css">
		@font-face {  font-family: 'quadranta';		src: url('data/aspace_demo.otf') format('truetype');	font-weight: normal;	font-style: normal; }	 
		h2 {  font-family:quadranta, sans-serif;	font-size:3em; }
		h3{	font-family:quadranta, sans-serif;	font-size:2em; }
	</style>
  </head>


  <body>
 
    <?php include "menu.html"; ?>




		<!-- Header de la page  -->
		<div class="jumbotron">
			<div class="container">
				<center><h2>Web Application for Data Analysis</h2></center>
				<p> <center><h3>Visualisez vos données selon vos goûts !</h3></center> </p>
			</div>
		</div>


				
				<!-- Proposition des graphiques de visualisations -->
				<div class="row center-block">

					<!-- texte explicatif --> 
					<center><h4><em>
						Avec quels graphiques visualiser vos données ?
					</h4></center></em>
					</br></br>

							<div class="col-sm-6 col-lg-3">
								<!--Sparkline Area Chart-->
								<div class="panel panel-info panel-colorful text-center">
									<a href="histo.php">
										<div class="panel-body">
											<!--Placeholder-->
											<div id="demo-sparkline-area"><img src="img/histo.png" alt="Preview" width="268"></div>
										</div>
									</a>
									<div class="bg-light pad-ver">
										<a href="histo.php"><h4 class="text-thin">Histogramme</h4></a> L’histogramme statique est un moyen rapide pour étudier la répartition d’une variable.
									</div>
								</div>
							</div>

							<div class="col-sm-6 col-lg-3">
								<!--Sparkline Line Chart-->
								<div class="panel panel-info panel-colorful text-center">
									<a href="diagr.php">
										<div class="panel-body">
											<!--Placeholder-->
											<div id="demo-sparkline-line"><img src="img/diagrStat.png" alt="Preview" width="268"></div>
										</div>
									</a>
									<div class="bg-light pad-ver">
										<a href="diagr.php"><h4 class="text-thin">Diagramme</h4></a> Un diagramme est utilisé pour représenter des objets et servant de support du raisonnement.
									</div>
								</div>
							</div>


							<div class="col-sm-6 col-lg-3">
								<!--Sparkline Pie Chart-->
								<div class="panel panel-info panel-colorful text-center">
									<a href="courbe.php">
										<div class="panel-body">
											<!-- Placeholder -->
											<div id="demo-sparkline-pie" class="box-inline "><img src="img/courbe2.png" alt="Preview" width="268"></div>
										</div>
									</a>
									<div class="bg-light pad-ver">
										<a href="courbe.php"><h4 class="text-thin">Courbe</h4></a> La courbe permt de visualiser l'évolution d'un element à long, moyen ou court terme
									</div>
								</div>
							</div>


							<div class="col-sm-6 col-lg-3">
								<!--Sparkline Pie Chart-->
								<div class="panel panel-info panel-colorful text-center">
									<a href="import.php">
										<div class="panel-body">
											<!-- Placeholder -->
											<div id="demo-sparkline-pie" class="box-inline "><img src="img/importe.png" alt="Preview" width="268" height="130"></div>
										</div>
									</a>
									<div class="bg-light pad-ver">
										<a href="import.php"><h4 class="text-thin">Importer</h4></a> Importez et visualisez vos propore fichier CSV
									</div>
								</div>
							</div>

							

				</div>



<!-- <p>{ Bootstrap - 3.3.2, JQuery - 1.11.2, D3.js - 3.4.13 }</p> -->
	</body>
</html>
