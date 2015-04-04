<!doctype html>  
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Wada - importation</title>
    	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
		<link href="css/jumbo.css" rel="stylesheet">
		<link href="css/styles.css" rel="stylesheet">
   		<link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
    	<link rel="stylesheet" type="text/css" href="css/bootstrap-formhelpers.min.css">
		<script src="js/ie-emulation-modes-warning.js"></script>
	</head>

	<style>
	/* STYLE pour le bouton importation */
	.file {
	  position: relative;
	}
	.file label { /* affichage du css */
	  background: #333333;
	  padding: 5px 20px;
	  color: #6ACD79;
	  font-weight: bold;
	  font-size: .9em;
	  transition: all .4s;
	}
	.file input { /* cache le bouton avec le css */
	  position: absolute;
	  display: inline-block;
	  left: 0;
	  top: 0;
	  opacity: 0.01;
	  cursor: pointer;
	}
	.file input:hover + label,
	.file input:focus + label {
	  background: #6ACD79;
	  color: #333333;
	}	
	</style>

	<body> 
	
	    <?php include "menu.html"; ?>
				<div class="container">
			<div class="row">
				<div class="page-header">
					<h1>Aperçu <small>utilisation generale</small></h1>
				</div>
			</div>
			<div class="row">

				<div class="panel panel-default">
					<div class="container">
					<ul class="nav nav-pills">
						<li role="presentation">
							<!-- <span id="uploaderIco" class="glyphicon glyphicon-open-file"></span> -->
							<!-- <img id="uploaderIco" class="glyphicon glyphicon-open-file" src="img/upload.png" alt="Preview" width="50"> -->
						</li>
						<li role="presentation">
							<input type="file" class="btn btn-success btn-large" id="uploader" hidden>
						</li>
					</ul>
					</div>

				</div>
			</div>

			<div class="row">
				<div class="panel panel-default">

					<div class="panel-heading" role="tab" id="headingOne">
						<ul id="tools" class="nav nav-pills">
							<li role="presentation">
								<a id="tll1" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne"><span class="glyphicon glyphicon-minus"></span> </a> 
								<!--<img src="img/diagrStat.png" alt="Preview" width="50">-->
							</li>
						</ul>
					</div>

					<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
						<div id="out1" class="panel-body">
							<div class="progress holder">

								<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
									<span class="sr-only">60% Complete</span>
								</div>
							</div>
							<div class="container holder">
								<img id="imgHolder" class="holder" src="holder.js/92%x400" alt="Mickey" >
							</div>

						</div>
					</div>
					<div class="panel-footer">

						<div class="container">
							<div class="row">

								<div class="col-md-4">
									<h4 id="statsTitle" class="text-center"></h4>
									<h5 id="stats" class="blockquote"></h5>

								</div>
								<div class="col-md-7">
									<blockquote>
										<table id="table" class="table">

										</table>
									</blockquote>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
			

				<blockquote>

					<p>
						Selectionne un fichier csv.
					</p>
				</blockquote>

			</div>
	
			<hr>
			
			
</div>

		<!--<form action="#">
		  <p class="file">
		    <input type="file" name="file" id="file" />
		    <label for="file">Importer</label>
		  </p>
		</form>-->
		
		<script src="js/jquery.min.js"></script>
			<script src="js/d3.min.js"></script>

			<script src="js/d3.tip.js"></script>

			<script src="js/bootstrap.min.js"></script>
			<script src="js/holder.js"></script>

			<!-- perso -->
			<script src="js/common.js"></script>
			<script src="js/pie.js"></script>
			<script src="js/histo.js"></script>
			<script src="js/upload.js"></script>
			
			<script>
				$(document).ready(function() {
					$("#credits").replaceWith(" { Bootstrap - " + $.fn.tooltip.Constructor.VERSION + ", JQuery - " + $.fn.jquery + ", D3.js - " + d3.version + " } ");

					d3.select("#imgHolder").remove();
					progressUp(".progress-bar", 0);

					upload_button("uploader", load_dataset);
				});
			</script>

			<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
			<script src="js/ie10-viewport-bug-workaround.js"></script>
		
		
		
	</body>
	
	<footer>
				<p id="credits">
					&copy; Either JQuery or bootstrap or Javascript is not running or your browser.
				</p>

			</footer>
</html>