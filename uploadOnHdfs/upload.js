		/* Script written by Adam Khoury @ DevelopPHP.com */ 
		function _(el){ 
			return document.getElementById(el); 
		} 
		function uploadFile(dataNode){ 
			
			var file = _("file1").files[0]; 
			// alert(file.name+" | "+file.size+" | "+file.type);
			
			var formdata = new FormData(); 
			console.log("uploadFile en action ... " +dataNode);
			formdata.append("file1", file); 
			formdata.append("target",dataNode);
			var ajax = new XMLHttpRequest(); 
			ajax.upload.addEventListener("progress", progressHandler, false); 
			ajax.addEventListener("load", completeHandler, false);
			ajax.addEventListener("error", errorHandler, false); 
			ajax.addEventListener("abort", abortHandler, false); 
			ajax.addEventListener("success", sucessHandler,false);
			ajax.open("post", "curl.php"); 
			
			ajax.send(formdata); 
		} 
		function progressHandler(event){ 
			_("loaded_n_total").innerHTML = "Uploaded "+event.loaded+" bytes of "+event.total; 
			var percent = (event.loaded / event.total) * 100; _("progressBar").value = Math.round(percent); 
			_("status").innerHTML = Math.round(percent)+"% uploaded... please wait"; 
		} 
		function completeHandler(event){
			_("status").innerHTML = event.target.responseText; _("progressBar").value = 0; 
		} 
		
		function errorHandler(event){ 
			_("status").innerHTML = "Upload Failed"; 
		} 
		function abortHandler(event){ 
			_("status").innerHTML = "Upload Aborted"; 
		} 
		function sucessHandler(event){
			_("status").innerHTML = "Horton is blind ...";
		}