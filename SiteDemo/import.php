<!doctype html>  
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Ali - importation</title>
	    <link rel="stylesheet" type="text/css" href="styles.css"/>
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

		<form action="#">
		  <p class="file">
		    <input type="file" name="file" id="file" />
		    <label for="file">Importer</label>
		  </p>
		</form>
		
	</body>
</html>