<!doctype html>  
<html>
  <head>
    <meta charset="UTF-8">
    <title>Wada - Graphiques</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-formhelpers.min.css">
	  <link rel="stylesheet" type="text/css" href='css/graphe.css'>
    <script src="scripts/d3.js" charset="utf-8"></script>
		<style type="text/css">
		@font-face {  font-family: 'quadranta';		src: url('fonts/aspace_demo.otf') format('truetype');	font-weight: normal;	font-style: normal; }	 
		h2 {  font-family:quadranta, sans-serif;	font-size:3em; }
		h3{	font-family:quadranta, sans-serif;	font-size:2em; }
		.divCam{border-radius: 10px};
	</style>
  </head>

 <body>
    <?php 	include "menu.html";    ?>
	
		<div class="jumbotron">
			<div class="container">
				<center><h2>Web Application for Data Analysis</h2></center>
				<p> <center><h3>Liste des visualisations pour vos donn&eacutees !</h3></center> </p>
			</div>
		</div>


<div class="divCam">
<div class="bussinesscard">
      <div class="flip">
        <div class="front">       
			<div class="top"></div>       
				<div class="nametroduction">
					<div class="name">wada</div>
					<div class="introduction">Camembert</div>
				</div>  
				<div class="contact">           
					<div class="website">
						<img src="img/images.jpg" width="200" height="190"/>
					</div>
				</div>            
        </div>
        <div class="back"></div> <!--FIXES FONT RENDERING -->
	</div>
 </div>                       
<div class="button"><a href="#" class="btn btn-primary btn-lg active" role="button">Visualiser</a></div>
</div>

<div class="divGraph">
<div class="bussinesscard">
    <div class="flip">
        <div class="front">       
			<div class="top"></div>      
				<div class="nametroduction">
					<div class="name">wada</div>
					<div class="introduction">Courbe</div>
				</div>  
				<div class="contact">           
				<div class="website">
					<img src="img/graph.jpg" width="170" height="160"/>
				</div>
			</div>            
        </div>
        <div class="back"></div> <!--FIXES FONT RENDERING -->
    </div>
 </div>                        
<div class="button"><a href="#" class="btn btn-primary btn-lg active" role="button">Visualiser</a></div>
</div>

<div class="divHisto">
	<div class="bussinesscard">
		<div class="flip">
			<div class="front">       
				<div class="top"></div>       
                <div class="nametroduction">
					<div class="name">wada</div>
					<div class="introduction">Histogramme</div>
				</div>  
				<div class="contact">           
					<div class="website">
						<img src="img/histo.jpg" width="230" height="190"/>
					</div>
				</div>            
			</div>
        <div class="back"></div> <!--FIXES FONT RENDERING -->
     </div>
 </div>                      
<div class="button"><a href="#" class="btn btn-primary btn-lg active" role="button">Visualiser</a></div>
</div>
	</body>
</html>