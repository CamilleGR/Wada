<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
	<META http-equiv=Content-Type content="text/html; charset=windows-1252">
	<link rel="stylesheet" type="text/css" href="css/diagram.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    </head>
    <body>
        <h1> Formulaire de test </h1>
        <?php
        	$fp = fopen('listefichiers.txt', 'r');
	?>
	<p id="fichier">
		<strong>Fichiers :</strong><br />
		 <select name="nomFichier">
			<?php 
				while (!feof($fp)){
					$line = trim(fgets($fp));
					echo '<option value="'.$line.'">'.$line.'</option>';
				}
			?>
		</select>
		<input class="button" type="submit" />
	</p>
	<p id="stats" style="display:none">
		<strong>Stats :</strong><br />
		<label>Attributs <select class="attribut" name="attribut"></select></label><br />
		<label>Segments : <input type="text" class="segment" name="segment" value="2"/></label><br />
		<label>Filtres : <input type="text" class="filtres" name="filtre"/></label><br />
		<input class="button" type="submit" />
	</p>
	<p id="courbe" style="display:none">
		<strong>Courbe :</strong><br />
		<label>Attributs1 <select class="attribut1" name="attribut1"></select></label><br />
		<label>Attributs2 <select class="attribut2" name="attribut2"></select></label><br />
		<label>Filtres : <input type="text" class="filtres" name="filtre"/></label><br />
		<input class="button" type="submit" />
	</p>
	<p id="kmeans" style="display:none">
		<strong>Kmeans :</strong><br />
		<label>Nombre de clusters <input type="text" class="nbClusters" name="nbClusters" value="2"/></label><br />
		<label>Filtres : <input type="text" class="filtres" name="filtre"/></label><br />
		<input class="button" type="submit" />
	</p>
	<p id="kmeans_Stats" style="display:none">
		<strong>Stats sur un attributs par clusters :</strong><br />
		<label>Attributs <select class="attribut" name="attribut"></select></label><br />
		<label>Segments : <input type="text" class="segment" name="segment" value="2"/></label><br />
		<label>Nombre de clusters <input type="text" class="nbClusters" name="nbClusters" value="2"/></label><br />
		<label>Filtres : <input type="text" class="filtres" name="filtre"/></label><br />
		<input class="button" type="submit" />
	</p>
	<?php fclose($fp); ?>
	<script>
		var attrFichiers;
		var nomFichier;

		function MajStats() {
			for (var i = 0; i<attrFichiers.attributs.length; i++) {
				$('.attribut').append('<option value="' + attrFichiers.attributs[i].label + '">' + attrFichiers.attributs[i].label + '</option>')
			}
			$('#stats').show();
			$('#kmeans').show();
			$('#kmeans_Stats').show();
		}

		function MajCourbe() {
			var attr = attrFichiers.attributs
			for (var i = 0; i<attr.length; i++) {
				if (attr[i].numerique) {
					$('#courbe .attribut1').append('<option value="' + attr[i].label + '">' + attr[i].label + '</option>')
					$('#courbe .attribut2').append('<option value="' + attr[i].label + '">' + attr[i].label + '</option>')
				}
			}
			$('#courbe').show();
		}

		$('#fichier .button').click(function(){
			console.log("click fichier");
			var fichier = $("#fichier select").val();
			nomFichier = fichier;
			$.get("http://localhost/BD/proxyWebService.php",
				{"action":"attributs",
				"nomFichier":fichier},
				function(reponse) {
					attrFichiers = reponse
					console.log(reponse);
					MajStats()
					MajCourbe()
				});
		});

		$('#stats .button').click(function(){
			console.log("click stats");
			var attr = $("#stats .attribut").val();
			var seg = $("#stats .segment").val();
			var filtre = $("#stats .filtres").val();
			$.get("http://localhost/BD/proxyWebService.php",
				{"action":"stats",
				"nomFichier":nomFichier,
				"attribut":attr,
				"segment":seg,
				"filtre":filtre},
				function(reponse) {
					console.log(reponse);
				});
		});

		$('#courbe .button').click(function(){
			console.log("click courbe");
			var attr1 = $("#courbe .attribut1").val();
			var attr2 = $("#courbe .attribut2").val();
			var filtre = $("#courbe .filtres").val();
			$.get("http://localhost/BD/proxyWebService.php",
				{"action":"courbe",
				"nomFichier":nomFichier,
				"attribut1":attr1,
				"attribut2":attr2,
				"filtre":filtre},
				function(reponse) {
					console.log(reponse);
				});
		});

		$('#kmeans .button').click(function(){
			console.log("click kmeans");
			var clust = $("#kmeans .nbClusters").val();
			var filtre = $("#kmeans .filtres").val();
			$.get("http://localhost/BD/proxyWebService.php",
				{"action":"kmeans",
				"nomFichier":nomFichier,
				"nbClusters":clust,
				"filtre":filtre},
				function(reponse) {
					console.log(reponse);
				});
		});
		$('#kmeans_Stats .button').click(function(){
			console.log("click kmeans_Stats");
			var attr = $("#kmeans_Stats .attribut").val();
			var seg = $("#kmeans_Stats .segment").val();
			var clust = $("#kmeans_Stats .nbClusters").val();
			var filtre = $("#kmeans_Stats .filtres").val();
			$.get("http://localhost/BD/proxyWebService.php",
				{"action":"kmeans_Stats",
				"nomFichier":nomFichier,
				"attribut":attr,
				"segment":seg,
				"nbClusters":clust,
				"filtre":filtre},
				function(reponse) {
					console.log(reponse);
				});
		});
	</script>
    </body>
</html>
