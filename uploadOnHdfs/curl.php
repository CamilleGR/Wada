<?php

	header("Access-Control-Allow-Origin:http://localhost:50070");  
	header("Access-Control-Allow-Methods : POST, GET, OPTIONS, PUT, PATCH, DELETE");
	header("Access-Control-Allow-Headers : Content-Type,api_key,Authorization,X-Auth-Token");
	header('Content-Type: text/html; charset=utf-8');	
	//header('Content-type: application/json');

	if($_GET)
	{	
		$url = $_GET['host'].$_GET['port'].$_GET['path'].$_GET['dir'].$_GET['op'];
		
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_HEADER, false);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_URL, $url);
		
		switch ($_GET['method']) {
			case 'put':
				curl_setopt($ch, CURLOPT_PUT, true);
				if($_GET['hdfs'])
				{
					$fileName = $_GET['file'];
					$url= $url.$_GET['datanode'];
					$file = fopen($fileName, "rb");
					if(!$file)
					{
						echo "fichier introuvable sur le serveur";
						return;
					}					
					print($url);
					curl_setopt($ch, CURLOPT_HEADER, true);							
					curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);						
					curl_setopt($ch, CURLOPT_URL, trim($url));
					curl_setopt($ch, CURLOPT_INFILESIZE, filesize($fileName));
					curl_setopt($ch, CURLOPT_INFILE, $file);		
				
																			
					echo curl_exec($ch);
					
					fclose($file);
					
					
				}
				else
				{
					echo curl_exec($ch);
				}
				break;
			
			case 'get':		
				echo curl_exec($ch);			
				break;
			case 'header-put' :
				curl_setopt($ch, CURLOPT_HEADER, true);
				curl_setopt($ch, CURLOPT_PUT, true);
				curl_setopt($ch, CURLOPT_FOLLOWLOCATION, false);
				echo curl_exec($ch);
				break;
			case 'post':
				curl_setopt($ch, CURLOPT_POST, true);
				
				echo curl_exec($ch);				
				break;		
				
			case 'delete':
				curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "delete");
				echo curl_exec($ch);
				break;
			
		
			default:
			
			echo json_encode(array( false));
			curl_close($ch);
			break;
		}
		
		curl_close($ch);
		
	}
	
	if(!empty($_FILES))
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
				echo "Upload done";
				
			}
			else 
			{
				echo  json_encode(array("success"=> false, "name" =>""));
			}
	}
?>