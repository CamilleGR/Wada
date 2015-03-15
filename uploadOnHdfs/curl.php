<?php
	header("Access-Control-Allow-Origin:http://localhost:50070"); 
	header("Access-Control-Allow-Methods : POST, GET, OPTIONS, PUT, PATCH, DELETE");
	header("Access-Control-Allow-Headers : Content-Type,api_key,Authorization,X-Auth-Token");
	header('Content-type: application/json');

	if(isset($_GET['adr']))
	{
		//recupere le contenu de la page, entete y comrpris et renvoie sous JSON
		$result = file_get_contents( 'http://'.$_GET['adr']);
		if($result)
		{
			echo json_encode($result);
		}else
		{
			$result = array( 'ok' => false);
			echo json_encode($result);
		}
	}
?>