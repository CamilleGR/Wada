/**
 * @author user
 */


// upload JPEG files
function UploadFile(file) {

	var xhr = new XMLHttpRequest();
	if (xhr.upload && file.type == "image/jpeg" && file.size <= $id("MAX_FILE_SIZE").value) {
	
		// create progress bar
		var o = $id("progress");
		var progress = o.appendChild(document.createElement("p"));
		progress.appendChild(document.createTextNode("upload " + file.name));
		
		// progress bar
		xhr.upload.addEventListener("progress", function(e) {
			var pc = parseInt(100 - (e.loaded / e.total * 100));
			progress.style.backgroundPosition = pc + "% 0";
		}, false);
		// file received/failed
		xhr.onreadystatechange = function(e) {
			if (xhr.readyState == 4) {
				progress.className = (xhr.status == 200 ? "success" : "failure");
			}
		};
		// start upload
		xhr.open("POST", $id("upload").action, true);
		xhr.setRequestHeader("X-FILENAME", file.name);
		xhr.send(file);

	}

}