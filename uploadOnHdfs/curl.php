<?php
	header("Access-Control-Allow-Origin:*"); 
	if($_GET == null)
	{
		echo '<html><body>Parametres vides</body></html>';
	}
	
	else
	{
		$result = file_get_contents('http://'.$_GET['adr']);
		
		echo $result;
		
	}