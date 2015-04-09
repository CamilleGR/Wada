<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
    <title>Wada - Kmeans, Nuage de points</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-formhelpers.min.css">
    
    <link href="css/bootstrap.min.css" rel="stylesheet">

	<link href="css/jumbo.css" rel="stylesheet">

	<script src="js/ie-emulation-modes-warning.js"></script>

  </head>


  <body> 
    <?php include "menu.html"; ?>
        <?php
        	$fp = fopen('listefichiers.txt', 'r');
	?>

		<!-- Main jumbotron for a primary marketing message or call to action -->

		<div class="container">
			<div class="row">
				<div class="page-header">
					<h1>Kmeans, Nuage de points</h1>
				</div>
			</div>
			<div id="error"></div>
			<div id="form" class="row">
				<div class="col-md-6">
				<div id="formFichier">
					<center><div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
							<span id="textbtn">Choix fichier</span> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
			<?php 
				while (!feof($fp)){
					$line = trim(fgets($fp));
					echo '<li value="'.$line.'"><a>'.$line.'</a></option>';
				}
			?>
						</ul>
					</div></center>
				</div>
				<div id="formAttr"style="display:none">
					<p>
						<center><div class="input-group">
							<!--<div><center><label>Attributs <select class="form-control attribut" name="attribut"></select></label></center></div>-->
							<div><center><label>Nombre de clusters :</label></div>
							<div><center><ul class="clust pagination">
								<li class="active"><a>2</a></li>
								<li><a>3</a></li>
								<li><a>4</a></li>
								<li><a>5</a></li>
								<li><a>6</a></li>
								<li><a>7</a></li>
								<li><a>8</a></li>
								<li><a>9</a></li>
								<li><a>10</a></li>
							</ul></center></div>
							<!--<div><center><label>Filtres : <input type="text" class="form-control filtres" name="filtre"/></label></center></div>-->
							<div><center><button id="buttonStats" type="button" class="btn btn-primary">Lancer</button></center></div>
						</div></center>
					</p>
				</div>
				</div>
				<div class="panelFiltres col-md-6" style="display:none">
					<div><center><button id="addFilter" type="button" class="btn btn-primary">Ajouter filtre</button></center></div>
					<div class="filtres">
					</div>
				</div>
			</div>
			<div id="loading" class="alert alert-info" style="display:none; margin-top:5px"><img src="img/load.gif"/> <strong>Patientez</strong></div>
			<div id="vizu" class="panel panel-primary" style="display:none">

				<div class="panel-heading" role="tab" id="headingOne">

					<div class="container">
						<div class="row">
							<div class="col-md-4">

								<h3 class="panel-title"><span></span>  									
								</h3>

							</div>
							<!--<div class="col-md-3">
								
										<a id="open2" class="btn btn-default btn-xs" data-placement="bottom" data-toggle="popover" data-title="Use client side csv files" data-container="body" type="button" data-html="true" href="#"  >Selection de données</a>
									
									<div id="popover-content" class="hide">
										<form class="form-inline" role="form">
											<div class="form-group">
												<select class="form-control">
													<option>NA</option>
													<option>RU</option>
													<option>EU</option>
													<option>SEA</option>
												</select>
												<input placeholder="Name" class="form-control" maxlength="5" type="text">
												<button type="submit" class="btn btn-primary">
													Go To Login »
												</button>
											</div>
										</form>
									</div>
								
								<div class="btn-group" role="group">
									<button id="title1" type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false" >

										<span class="caret"></span>
									</button>
									<ul id="opt1" class="dropdown-menu" role="menu">

									</ul>
								</div>
							</div>-->
							<div id="tools" class="col-md-2 col-md-offset-6">
								<!--<button type="button" class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon-option-horizontal"></span>
								</button>
								<a id="dl1" class="btn btn-default btn-xs" download="data.svg">Download <span class="glyphicon glyphicon-send" ></span></a>-->
								<a class="btn btn-success btn-xs" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne"> Cacher </a>
							</div>
						</div>
					</div>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
					<div id="out1" class="panel-body">
						<div class="progress holder">
							<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
								<span class="sr-only">60% Complete</span>
							</div>
						</div>
						<!--<div class="container holder">
							<img class="holder" src="holder.js/92%x400" alt="Mickey" >
							 TRAITEMENT A CE NIVEAU MOAZ !
						</div> -->

					</div>
				</div>
				<div class="panel-footer">

					<div class="container">
						<div class="row">

							<div id="statsPanel" class="col-md-4">
								<h4 class="text-center">Statistiques</h4>
								<h5 id="stats" class="blockquote"></h5>

							</div>
							<div class="col-md-7">
								<table id="table" class="table">

								</table>
							</div>

						</div>
					</div>
				</div>

			</div>

			<div class="row">
				

<!--				<blockquote>

					<p>
						En statistiques, un histogramme est un graphique permettant de représenter la répartition d'une variable continue.
					</p>
				</blockquote>
-->
			</div>

			<hr>

			<footer>
				

			</footer>

			<!-- /container -->

			<!-- Bootstrap core JavaScript
			================================================== -->
			<!-- Placed at the end of the document so the pages load faster -->
			<script src="js/jquery.min.js"></script>
			<script src="js/d3.min.js"></script>
			<script src="js/d3.tip.js"></script>
			
			
			<!-- perso -->
			<script src="js/common.js"></script>
			<script src="js/stats/graphPoints.js"></script>
<!--  <script src="histogram.js"></script>  -->
			<script src="js/stats/upload.js"></script>
			
			<script>
				$(document).ready(function() {
					/*function MajStats() {
						$('.attribut').html('');
						$('div.filtres').html('');
						for (var i = 0; i<attrFichiers.attributs.length; i++) {
							$('.attribut').append('<option value="' + attrFichiers.attributs[i].label + '">' + attrFichiers.attributs[i].label + '</option>')
						}
					}*/
					function stringFiltres(liste) {
						console.log(liste.length);
						var s = "";
						for (i = 0; i < liste.length; i++) {
							if (i > 0) s += ";";
							s+=liste.find('.attrFiltre').val();
							s+=liste.find('.signFiltre').val();
							s+=liste.find('input').val();
						}
						console.log(s);
						return s;
					}
					var nomFichier;
					var attrFichiers;
					$('#addFilter').click(function(e){
						var line = $('<div class="form-group"></div>');
						var selectAttr = "";
						for (var i = 0; i<attrFichiers.attributs.length; i++) {
							selectAttr += '<option value="' + attrFichiers.attributs[i].label + '">' + attrFichiers.attributs[i].label + '</option>'
						}
						line.append('<select class="attrFiltre col-xs-3">' + selectAttr + '</select>');
						line.append('<select class="signFiltre col-xs-3"><option value=">">&gt;</option><option value=">=">&ge;</option><option value="<">&lt;</option><option value="<=">&le;</option><option value="!=">!=</option><option value="=">=</option></select><input type="text" class="col-xs-3 form-control filtres" name="filtre"/\>');
						line.append('<button class="col-xs-3 btn btn-danger suppr">Retirer</button>');
						console.log(line);
						$('div.filtres').append(line);
					});
					$('div.filtres').on('click','.suppr', function(){
						console.log("remove");
						$(this).parent().remove();
					});
					$('#formFichier li').on('click','a',function() {
						$('#loading').show();
						nomFichier = $(this).text();
						console.log("click sur " + nomFichier);
						$('#textbtn').text(nomFichier);
						$.get("proxyWebService.php",
							{"action":"attributs",
							"nomFichier":nomFichier},
							function(reponse) {
								if(reponse.hasOwnProperty('erreur')) {
									erreur(reponse.erreur);
								}
								else {
									$('#formAttr').show();
									$('.panelFiltres').show();
									attrFichiers = reponse
									console.log(reponse);
								}
								//MajStats();
								$('#loading').hide();
							}
						);
						$('div.filtres').html('');
					
					});
					$('.clust li').click(function(e) {
						$('.clust .active').removeClass('active');
						$(this).addClass('active');
						console.log($(this).find('a').text());
					});
					$('#buttonStats').click(function() {
						$('#loading').show();
						//var attr = $("#formAttr .attribut").val();
						var clust = $("#formAttr .clust .active a").text();
						console.log(clust);
						//var filtre = $("#formAttr .filtres").val();
						var filtre = stringFiltres($('div.filtres div'));
						$.get("proxyWebService.php",
							{"action":"kmeans",
							"nomFichier":nomFichier,
							"nbClusters":clust,
							"filtre":filtre},
							function(reponse) {
								console.log("test" + reponse);
								console.log(reponse);
								if(reponse.hasOwnProperty('erreur')) {
									erreur(reponse.erreur);
								}
								else {
									$('#vizu').show();
									$("#credits").replaceWith(" { Bootstrap - " + $.fn.tooltip.Constructor.VERSION + ", JQuery - " + $.fn.jquery + ", D3.js - " + d3.version + " } ");

									myGraph(reponse);

									$(function() {
										$('[data-toggle="popover"]').popover();
									});	
									$('.panel-title span').text('kmeans - ' + nomFichier);
								}
								$('#loading').hide();
							}
						);
					});
					function erreur(erreur_message) {
						var div = $('<div class="alert alert-danger"></danger>');
						div.html("<strong>ERREUR : </strong>" + erreur_message + '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>');
						$('#error').append(div);
					}
					//upload_button("uploader", load_dataset);
				});
			</script>

			<script src="js/bootstrap.min.js"></script>
			<script src="js/holder.js"></script>
			<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
			<script src="js/ie10-viewport-bug-workaround.js"></script>



	</body>
</html>
