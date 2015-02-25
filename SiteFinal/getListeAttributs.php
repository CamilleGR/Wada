<?php

	$chaine = "age;profession;sexe;salaire";

	echo '<html>
			<body onLoad="document.form2.submit();"> <!-- pr lauto submit -->
				<form name="form2" method="GET" action="sondage.php">
					<input type="hidden" name="listeAttributs" value="'.$chaine.'"/><input type="submit"/>
				</form>
			</body>
		</html>';
?>
