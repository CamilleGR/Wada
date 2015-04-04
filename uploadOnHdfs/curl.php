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
			
			echo json_encode(array( false));
			curl_close($ch);
			break;
		}
		echo curl_exec($ch);
		curl_close($ch);
		
	}
	
	if(isset($_POST['target'])&& $_FILES!=null)
	{
		
		$fileName = $_FILES["file1"]["name"]; 
		// The file name
		$fileTmpLoc = $_FILES["file1"]["tmp_name"]; 
		// File in the PHP tmp folder
		$fileType = $_FILES["file1"]["type"];
		// The type of file it is
		$fileSize = $_FILES["file1"]["size"];
		// File size in bytes
		$fileErrorMsg = $_FILES["file1"]["error"];
		// 0 for false... and 1 for true
		
		if (!$fileTmpLoc) { // if file not chosen
			echo "ERROR: Please browse for a file before clicking the upload button."; 
			exit(); 
			} 
			if(move_uploaded_file($fileTmpLoc, "$fileName"))
			{
				
				$dataNode = $_POST['target'];
				
				$ch = curl_init();				
				curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
				curl_setopt($ch, CURLOPT_POST, true);
				curl_setopt($ch, CURLOPT_HEADER, true);
				curl_setopt($ch, CURLOPT_URL, $dataNode);
				$args['file'] = new CurlFile($fileTmpLoc);
				curl_setopt($ch, CURLOPT_POSTFIELDS, $args); 
				echo curl_exec($ch);
				unlink($fileName);
				
				curl_close($ch);
				//echo "$fileName upload is complete";
			
				
			}
			else 
			{
				echo "move_uploaded_file function failed"; 
			}
	}
?>