// to validate upload form
	function verif_before_valid_form(err_title,err_name,err_lvl,err_component,err_file,err_fileformat) {
	    var erreur = 0;

	    if(document.uploadform.title.value=="") {
	    	document.getElementById('error').innerHTML = err_title;
			erreur = 1;
	    }
	    else if(document.uploadform.name.value=="") {
	    	document.getElementById('error').innerHTML = err_name;
			erreur = 1;
	    }
	    else if(document.uploadform.level.value=="") {
	    	document.getElementById('error').innerHTML = err_lvl;
			erreur = 1;
	    }
	    else if(document.uploadform.component.value=="") {
	    	document.getElementById('error').innerHTML = err_component;
			erreur = 1;
	    }
	    else if(document.uploadform.media.value=="") {
			document.getElementById('error').innerHTML = err_file;
			erreur = 1;
			
		}
		else {
			fichier = document.uploadform.media.value;
			ext = fichier.substring(fichier.lastIndexOf("."),fichier.length).toLowerCase();
	               
			if(ext==".avi"||ext==".mov"||ext==".mp4"||ext==".mkv"||ext==".divx"||ext==".mpg"||ext==".mpeg"||ext==".wmv"||ext==".flv"||ext==".mp3"||ext==".wma"||ext==".wav"||ext==".ogg"){} // si extension = a une des extension suivante alors tout est ok donc ... pas d'erreur
			else {
				document.getElementById('error').innerHTML = err_fileformat;
				erreur = 1;
			}
		}

		if(erreur==1) {
			javascript:document.getElementById('process').style.visibility='hidden';
			return (false);
		}
		else {
			return (true);
		}
	}