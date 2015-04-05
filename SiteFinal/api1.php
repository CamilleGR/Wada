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
		
		<!-- texte explicatif --> 
		<div class="text">
			<center><p>Le principe est simple : Vous saisissez un hashtag ainsi que la durée du stream voulu et 
				Wada vous affiche l’évolution de l’utilisation d’un hashtag et les mots qui y sont associés. </p></center>
		</div>




	<!-- Formulaire de saisie -->
	<!-- http://localhost:8080/stream">  -->
	<center>
		<input id="commencer" type="submit" name="butt" class="btn btn-success" value=" Commencer un enregistrement " style="margin-top:22px;" >
		<input id="arreter" type="submit" name="butt" class="btn btn-success" value=" arreter un enregistrement " style="margin-top:22px;" >
		<input id="traiter" type="submit" name="butt" class="btn btn-success" value=" traiter un stream " style="margin-top:22px;" >
	</center>
		</br></br></br>
	<div id="formulaire" style="display:none";>
		<form  method="GET" action="api2.php" style="z-index:98; position:relative; top:auto;  width:300px; height: 18px; margin-left: 39%;" >
			<div class="bfh-timepicker" runat="server" id="Arrivo" data-time="" style="margin-top:5em;">

				<div class="input-group" style="margin-top:7px;">
					<span class="input-group-addon"><i class="glyphicon glyphicon-comment"></i></span>
			    	<input type="text" name="hashtags" class="form-control" placeholder="Mot clés à rechercher ..."  />
			    	<a href="proxyWebService.php" class="btn btn-default"><span class="glyphicon glyphicon-play"></span> Lancer Stream</a>
			    </div>
			</div>
		</form>
	</div>



	
	<script src="js/jquery-1.10.2-min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="bootstrap-formhelpers-min.js"></script>
	<script src="bootstrap-formhelpers-datepicker.js"></script>
	

	<script>
		$(document).ready(function(){
			console.log('test');
			$('input#commencer').click(function(){
				$('#formulaire').show();
			});

			$('input#arreter').click(function(){
				$.get('proxyWebService.php',{action:"stopstream"},function(reponse){
					alert("reponse");
				});
			});

			$('input#traiter').click(function(){
				document.location.href="traitementStream.php";
			});
		});
	</script>





<!--
	<script>
		$.get('proxyWebService.php',{action:"evoTweet",seg:$('#input_seg').val(),path:$('#input_path').val()},function(reponse){

		});
	</script>-->
	
	</body>
</html>

