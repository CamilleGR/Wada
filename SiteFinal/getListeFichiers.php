<?php
	// exemple de listes de Fichiers dans l'HDFS
	$chaine = "sondage;retard;blabla";

	echo '<html>
			<body onLoad="document.form1.submit();"> <!-- pr lauto submit -->
				<form name="form1" method="GET" action="accueil.php">
					<input type="hidden" name="listeFichiers" value="'.$chaine.'"/><input type="submit"/>
				</form>
			</body>
		</html>';
?>
