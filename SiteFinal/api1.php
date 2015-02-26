<!doctype html>
<html>
	<head>
    <meta charset="UTF-8">
    <title>Wada - Accueil</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-formhelpers.min.css">
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
				<center><h2>Wada API fot Twitter</h2></center>
				<p> <center><h3>  Mesurez l’impact d’un évènement avec Wada !</h3></center> </p>
			</div>
		</div>


		<!-- texte explicatif --> 
		<div class="text" style=" position:relative; left:100px; width:700px; text-align=center; font-size:17px;">
			<p>Le principe est simple : Vous saisissez un hashtag ainsi que la durée du stream voulu et 
				Wada vous affiche l’évolution de l’utilisation d’un hashtag et les mots qui y sont associés. </p>
		</br></br>
		</div>


		<!-- Formulaire de saisie -->
		<form  method="POST" action="http://localhost:8080/stream" style="z-index:98; position:relative; top:auto; left:100px; width:300px; height: 18px">
			<div class="bfh-timepicker" runat="server" id="Arrivo" data-time="" >
			        <input type="text" name="hashtags" class="form-control" placeholder="Mot clés à rechercher ..."  />		</br>    
				<div class="input-group bfh-timepicker-toggle" data-toggle="bfh-timepicker">
					<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
			        <input type="number" name="temps" class="form-control" placeholder="Durée du stream en minute"  />
			    </div>

			    <button name="butt"> valider </button> <!-- a faire  -->

			</div>
		</form>
		<!-- <label for="minute">Temps(min):</label>  -->
	

	<br><br><br><br><br>
	<div class="lien" style="position:relative; left:100px">
		<a href="api2.php">Page 2 de l'API Twitter</a> </br>
		<a href="api3.php">Page 3 de l'API Twitter</a>
	</div>


	</body>
</html>