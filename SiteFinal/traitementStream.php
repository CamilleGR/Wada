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
				<center><h2><a href="accueil.php" style="text-decoration:none">Wada</a> API for Twitter</h2></center>
				<p> <center><h3>  Mesurez l’impact d’un évènement avec Wada !</h3></center> </p>
			</div>
			<center>
				<ul>
					<a href="api1.php"><li class="bouton_gauche btn btn-default" style="cursor: auto;">Accueil</li></a>
					<a href="api2.php"><li class="bouton_gauche btn btn-default" style="cursor: auto;">Stream</li></a>
				</ul>
			</center>
		</div>
		
	<p> Entrez le chemin correspondant à votre enregistrement et l'interval de temps que vous souhaitez pour visualiser l'evolution de votre hashtag </p>
	
	<div id="formulaire" style="display:none;">
		<form  method="GET" action="api2.php" style="z-index:98; position:relative; top:auto;  width:300px; height: 18px; margin-left: 39%;" >
			<div class="bfh-timepicker" runat="server" id="Arrivo" data-time="" style="margin-top:5em;">

				<div class="input-group" style="margin-top:7px;">
					<span class="input-group-addon"><i class="glyphicon glyphicon-comment"></i></span>
			    	<input type="text" name="path" class="form-control" placeholder="chemin vers l'enregistrement ..."  />
			    	<input type="number" name="seg" class="form-control" placeholder="Entrez votre interval de temps ..." />
			    	<a id="boutonSubmit"class="btn btn-default"><span class="glyphicon glyphicon-play"></span> Lancer Stream</a>
			    </div>
			</div>
		</form>
	</div>
	<script src="js/jquery-1.10.2.min.js"></script>
	<script src="js/traitementStream.js">/*C'est dans le fichier qu'on modifiera le script*/</script>
	
	</body>
</html>

