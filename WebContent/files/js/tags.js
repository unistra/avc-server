// Search with tag from "all tags"
function LienAllTags() {
	i = document.formAllTags.selectAllTags.selectedIndex;
	var uriCourant = document.location.pathname;
	
	if (i == 0) return;
	
	//first access
	url = "tags?tags="+encodeURI(document.formAllTags.selectAllTags.options[i].value);
			
	parent.location.href = url;
}


//Search with tag from "all tags"
function LienCumulTags() {
	i = document.formCumulTags.selectCumulTags.selectedIndex;
	var uriCourant = document.location.pathname;
	var nomPage = uriCourant.substring(uriCourant.lastIndexOf('/')+1, uriCourant.length);

	if (i == 0) return;
	
	//first access
	if(nomPage!="tags") {
		url = "tags?tags="+encodeURI(document.formCumulTags.selectCumulTags.options[i].value);
	}
	//add tags
	else {
		url = "tags?tags="+getUrl("tags") + "+"+encodeURI(document.formCumulTags.selectCumulTags.options[i].value);
	}
		
	parent.location.href = url;
}


// To delete a tag from the URL
function closeTag(tag) {
	
	var oldUrl = parent.location.href;
   
    var debTag = oldUrl.lastIndexOf(tag);
    var finTag = oldUrl.lastIndexOf(tag)+tag.length;
   
    
    // Because difference between IE/Mozilla with accents (ex: IE print 'é', Mozilla print '%C3%A9' or '%E9')
    if(debTag == '-1') {
    	oldUrl = unescape(parent.location.href);
        debTag = oldUrl.lastIndexOf(tag);
        finTag = oldUrl.lastIndexOf(tag)+tag.length;
    }
    
    // Because difference between IE/Mozilla with accents (ex: IE print 'é', Mozilla print '%C3%A9' or '%E9')
    if(debTag == '-1') {
    	oldUrl = decodeURI(parent.location.href);
        debTag = oldUrl.lastIndexOf(tag);
        finTag = oldUrl.lastIndexOf(tag)+tag.length;
    }
   
    // To delete the character "+" of the URL
    if(oldUrl.substring(debTag-1,debTag)=='+') {
        debTag = debTag-1;
    }
       
    // Remove the tag from the url
    url = oldUrl.substring(0,debTag) +
    oldUrl.substring(finTag,oldUrl.length);

    parent.location.href = url;
}




function getURLvar() {
	 var url=location.href; 
	 var urlVars=url.split("?"); 
	 var v=new Array(); //
	 v["name"]=new Array();
	 v["val"]=new Array();

	 if (urlVars.length>0)
	 {
		 var vars=urlVars[1].split("&"); 

		 for (var i=0; i<vars.length; i++)
		 {
			 v["name"][i]=vars[i].split("=")[0];
			 v["val"][i]=vars[i].split("=")[1];
		 }
	 }
	 return(v);
}

// To get a parameter of an url
function getUrl(alpha) {
	 var v=getURLvar();
	 var cRet;
	 for (var i=0; i<v["name"].length; i++)
	 {
		 if (v["name"][i]==alpha)
		 {
			 cRet=v["val"][i];
		 }
	 }
	 return cRet;
} 














