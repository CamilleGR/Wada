<?php

	header("Access-Control-Allow-Origin:http://localhost:50070"); 
	header("Access-Control-Allow-Methods : POST, GET, OPTIONS, PUT, PATCH, DELETE");
	header("Access-Control-Allow-Headers : Content-Type,api_key,Authorization,X-Auth-Token");
	header('Content-Type: text/html; charset=utf-8');
	//header('Content-type: application/json');

	if(isset($_GET['adr'])&&isset($_GET['method']))
	{
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_HEADER, false);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_URL, $_GET['adr']);
		
		switch ($_GET['method']) {
			case 'put':
				curl_setopt($ch, CURLOPT_PUT, true);						
				break;
			
			case 'get':									
				break;
			
			case 'post':
				curl_setopt($ch, CURLOPT_POST, true);
				curl_setopt($ch, CURLOPT_HEADER, true);				
				break;		
				
			case 'delete':
				curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "delete");
				break;
			
			case 'header' :	
				curl_setopt($ch, CURLOPT_HEADER, true);
				break;
			
			default:
			
			echo json_encode(array( 'ok' => false));
			curl_close($ch);
			break;
		}
		echo curl_exec($ch);
		curl_close($ch);
		
	}
?>