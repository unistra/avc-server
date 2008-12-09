// Function which hides and shows div elements dynamically 
function switchDetails(elem)
{
	var col1link = elem + 'col1link';
	var col2link = elem + 'col2link';
	var col1details = elem + 'col1details';
	var col2details = elem + 'col2details';
	
	// Retrieves the actual element state
	var state = document.getElementById(col1link).style.display;
	
	if(state=="none"){
		document.getElementById(col1link).style.display="inline";
		document.getElementById(col2link).style.display="inline";
		document.getElementById(col1details).style.display="none";
		document.getElementById(col2details).style.display="none";
	}
	else{
		document.getElementById(col1link).style.display="none";
		document.getElementById(col2link).style.display="none";
		document.getElementById(col1details).style.display="inline";
		document.getElementById(col2details).style.display="inline";
	}
}