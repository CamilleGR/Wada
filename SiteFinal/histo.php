<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
    <title>Wada - Histogrammes</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-formhelpers.min.css">
    
    <link href="css/bootstrap.min.css" rel="stylesheet">

	<link href="css/jumbo.css" rel="stylesheet">

	<script src="js/ie-emulation-modes-warning.js"></script>

  </head>


  <body> 
    <?php include "menu.html"; ?>

		<!-- Main jumbotron for a primary marketing message or call to action -->

		<div class="container">
			<div class="row">
				<div class="page-header">
					<h1>Histogramme <small>Sélectionner puis filter vos données.</small></h1>
				</div>
			</div>
			<div class="panel panel-default">

				<div class="panel-heading" role="tab" id="headingOne">

					<div class="container">
						<div class="row">
							<div class="col-md-4">
								<h3 class="panel-title">
									<a class="label label-default" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne"> Cacher </a> 									
								</h3>

							</div>
							<div class="col-md-3">
								
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
							</div>
							<div id="tools" class="col-md-3 col-md-offset-2">
								<button type="button" class="btn btn-default btn-xs">
									<span class="glyphicon glyphicon-option-horizontal"></span>
								</button>
								<a id="dl1" class="btn btn-default btn-xs" download="data.svg">Download <span class="glyphicon glyphicon-send" ></span></a>

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
						<div class="container holder">
							<img class="holder" src="holder.js/92%x400" alt="Mickey" >
							<!-- TRAITEMENT A CE NIVEAU MOAZ ! -->
						</div>

					</div>
				</div>
				<div class="panel-footer">

					<div class="container">
						<div class="row">

							<div class="col-md-4">
								<h4 class="text-center">Csv File</h4>
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
				

				<blockquote>

					<p>
						En statistiques, un histogramme est un graphique permettant de représenter la répartition d'une variable continue.
					</p>
				</blockquote>

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
			
			
			<!-- perso -->
			<script src="js/common.js"></script>
			<script src="js/histo.js"></script>
<!--  <script src="histogram.js"></script>  -->
			<script src="js/upload.js"></script>
			
			<script>
				$(document).ready(function() {
					$("#credits").replaceWith(" { Bootstrap - " + $.fn.tooltip.Constructor.VERSION + ", JQuery - " + $.fn.jquery + ", D3.js - " + d3.version + " } ");

					createViz();

					$(function() {
						$('[data-toggle="popover"]').popover()
					});

					//upload_button("uploader", load_dataset);
				});
			</script>

			<script src="js/bootstrap.min.js"></script>
			<script src="js/holder.js"></script>
			<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
			<script src="js/ie10-viewport-bug-workaround.js"></script>



	</body>
</html>
