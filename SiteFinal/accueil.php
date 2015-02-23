<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Ali - Accueil</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
  </head>


  <body> 
    <?php include "menu.html"; ?>




		<!-- Header de la page  -->
		<div class="jumbotron">
			<div class="container">
				<center><h2>Web Application for Data Analysis</h2></center>
				<p> <center><h4>Visualiser vos données selon vos goûts !</h4></center> </p>
			</div>
		</div>



		<!-- texte explicatif --> 
		<center><h4>
			Voila du blabla pour expliquer le projet et comment utiliser la web application. </br>
			D'abord selectionner le type de graphique qu'on veut. On aura par la suite un choix entre plusieur type du graphe qu'on choisira ici. </br>
			Par exemple, si on choisit le diagramme, on nous proposera différents diagrammes (dynamique, statique ...) pour la visualisation.
		</h4></center></br></br>



				
				<!-- Proposition des graphiques de visualisations -->
					<div class="row center-block">

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
										<a href="histo.php"><h4 class="text-thin">Histogramme statique</h4></a> L’histogramme statique est un moyen rapide pour étudier la répartition d’une variable.
									</div>
								</div>
							</div>

							<div class="col-sm-6 col-lg-3">
								<!--Sparkline Line Chart-->
								<div class="panel panel-info panel-colorful text-center">
									<a href="#">
										<div class="panel-body">
											<!--Placeholder-->
											<div id="demo-sparkline-line"><img src="img/diagrStat.png" alt="Preview" width="268"></div>
										</div>
									</a>
									<div class="bg-light pad-ver">
										<a href="#"><h4 class="text-thin">Diagramme</h4></a> Un diagramme est utilisé pour représenter des objets et servant de support du raisonnement.
									</div>
								</div>
							</div>


							<div class="col-sm-6 col-lg-3">
								<!--Sparkline Pie Chart-->
								<div class="panel panel-info panel-colorful text-center">
									<a href="#">
										<div class="panel-body">
											<!-- Placeholder -->
											<div id="demo-sparkline-pie" class="box-inline "><img src="img/courbe2.png" alt="Preview" width="268"></div>
										</div>
									</a>
									<div class="bg-light pad-ver">
										<a href="#"><h4 class="text-thin">Courbe</h4></a> La courbe permt de visualiser l'évolution d'un element à long, moyen ou court terme
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




  </body>
</html>