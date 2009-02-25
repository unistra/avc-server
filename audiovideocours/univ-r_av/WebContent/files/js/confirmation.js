/* Function which displays a confirmation message */
function confirmation( message, urlOK) {
	if (confirm(message))
		window.location = urlOK;
}