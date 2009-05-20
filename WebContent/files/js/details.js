// Function which hides and shows div elements dynamically 
function switchDetails(elem)
{
	var collink = elem + 'collink';
	var coldetails = elem + 'coldetails';
		
	// Retrieves the actual element state
	var state = document.getElementById(collink).style.display;
	
	if(state=="none"){
		document.getElementById(collink).style.display="inline";
		document.getElementById(coldetails).style.display="none";
	}
	else{
		document.getElementById(collink).style.display="none";
		document.getElementById(coldetails).style.display="inline";
	}
}