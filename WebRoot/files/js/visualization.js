var xmlHttp = null; // xmlHttpRequest object used to contact the server
var timecodes = null; // array containing a list of timecodes
var currentSlide = 0; // current slide number displayed in the smil media
var oldSlide = 0; // previous slide number displayed in the smil media
var pageNumber = 1; // current page of the pagination
var paginationUpdateTime = 10000; // after having clicked on the pagination, the time bar is not updated during this time
var update = true; // if the time bar has to be updated or not

/* function to call when the page starts */
window.onload = function(){

	xmlHttp=getXMLHTTP(); //retrieves the xmlHttpRequest object
	
	if(xmlHttp) {
		// gets the list of timecodes from the server
		xmlHttp.open("GET",'timecodes.jsp',true);
		xmlHttp.onreadystatechange=function() {
			if (xmlHttp.readyState == 4 && xmlHttp.status == 200) { //Complete state and HTTP OK response
				timecodes = xmlHttp.responseText.split(",");
				
				// calls the method to update the time bar
				initTimeBar();
				updatePagination();
				updateTimeBar();
			}
		};
		
		xmlHttp.send(null);
	}
};

/* Returns a xmlHttpRequest object */
function getXMLHTTP(){
	var xhr=null;
	
	if(window.XMLHttpRequest) // Firefox and others
		xhr = new XMLHttpRequest();
	else if(window.ActiveXObject) { // Internet Explorer
		try {
			xhr = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e1) {
				xhr = null;
			}
		}
	}
	
	return xhr;
}

/* Gets the corresponding slide number from a given time */
function getSlideFromTime(time) {
	var i=0;
	while( time >= timecodes[i] && i < timecodes.length)
		i++;
	
	return i;
}

/* Sets the corresponding slide number from a given time */
function setTimeFromSlide(slide) {
	try {
		setPos(timecodes[slide-1]);
		update = true;
	}
	catch( realJavasriptError ) { // error in the setPos function with Opera/Safari ...
	}
}

/* Checks if the current slide is in the same page as the previous one, and modify the time bar and pagination if not */
function checkPageChange() {
	var res = false;
	
	if( Math.ceil((currentSlide)/15) > pageNumber) { //next page
		pageNumber = Math.ceil((currentSlide)/15);
		initTimeBar();
		oldSlide = 0;
		res = true;
	}
	else if( Math.ceil((currentSlide)/15) < pageNumber) { // previous page
		pageNumber = Math.ceil((currentSlide)/15);
		initTimeBar();
		oldSlide = 0;
		res = true;
	}
	
	return res;
}

/* Builds the time bar and thumb line of the current page */
function initTimeBar() {
	
	// erases the previous time bar and thumb line
	document.getElementById("videoLine").innerHTML = "";
	document.getElementById("thumbLine").innerHTML = "";
	var firstSlide = (pageNumber-1)*15 + 1;
	
	// adds each time point and corresponding thumb
	for( i=0 ; i< 15 && (firstSlide + i <= timecodes.length) ; i++) {
		document.getElementById("videoLine").innerHTML += '<a href="javascript:setTimeFromSlide(' + (firstSlide + i)  + ')" onmouseover="showThumb(' + (firstSlide + i)  + ')" onmouseout="hideThumb(' + (firstSlide + i)  + ')" id="time' + (firstSlide + i)  + '" class="videoTime"><img src="../files/img/video_time_off.gif"></a>';
		document.getElementById("thumbLine").innerHTML += '<img class="thumb" id="thumb' + (firstSlide + i) + '" src="http://stagiaire1.u-strasbg.fr/coursv2/smiltest/screenshots/D3-thumb.jpg">';
	}
}

/* Updates the time bar with the position of the video */
/* This function is called every second */
function updateTimeBar() {

	try {
		// retrieves the current slide number
		currentSlide = getSlideFromTime(getPos());	
	
			// if the slide displayed has changed
			if( currentSlide != oldSlide ) {
		
				// if the time bar must be updated
				if( update || Math.ceil((currentSlide)/15) == pageNumber) {
				
					// verifies if the page has changed in the pagination
					checkPageChange();
					
					// changes the points images for the current and old slide
					document.getElementById('time' + currentSlide).innerHTML =  "<img src=\"../files/img/video_time_on.gif\">";
					
					if(oldSlide > 0)
						document.getElementById('time' + oldSlide).innerHTML =  "<img src=\"../files/img/video_time_off.gif\">";
						
					oldSlide=currentSlide;
				}
				
				// updates the page and slide numbers
				updatePagination();
			}
	
		setTimeout("updateTimeBar()", 1000); // The function will be called again in 1 sec.
	}
	catch( realJavasriptError ) { // error in the getPos function with Opera/Safari ...
	}
}

/* Updates the page and slide numbers in the HTML code */
function updatePagination() {
	document.getElementById("slideNumber").innerHTML = currentSlide + "/" + timecodes.length;
	document.getElementById("pageNumber").innerHTML = pageNumber + "/" + Math.ceil(timecodes.length/15);
}

/* Sets the position of the smil media to the previous slide */
function previousSlide() {
	if( currentSlide > 0 ) {
		setPos(timecodes[currentSlide-1]);
		currentSlide--;
		checkPageChange();
	}
}

/* Sets the position of the smil media to the next slide */
function nextSlide() {
	if( currentSlide < timecodes.length ) {
		setPos(timecodes[currentSlide-1]);
		currentSlide++;
		checkPageChange()
	}
}

/* Sets the position of the smil media to the previous page */
function previousPage() {
	if( pageNumber > 1) {
		pageNumber--;
		// disables the time bar update and then reactivate it
		update = false;
		setTimeout("update=true", paginationUpdateTime);
		oldSlide = 0;
		initTimeBar();
		// updates the page and slide numbers
		updatePagination();
	}
}

/* Sets the position of the smil media to the next page */
function nextPage() {
	if( pageNumber < Math.ceil(timecodes.length/15) ) {
		pageNumber++;
		// disables the time bar update and then enables it again
		update = false;
		setTimeout("update=true", paginationUpdateTime);
		oldSlide = 0;
		initTimeBar();
		// updates the page and slide numbers
		updatePagination();
	}
}
/* Sets the position of the smil media in seconds */
function setPos(temps) {
	document.getElementById('video').SetPosition(temps*1000);
}

/* Returns the position of the smil media in seconds */
function getPos() {
	return document.getElementById('video').GetPosition()/1000;
}

/* Shows the div element corresponding to a thumb by specifying its number */
function showThumb(elem) {
	var thumb = "thumb" + elem;
	document.getElementById("pagination").style.visibility="hidden";	
	document.getElementById(thumb).style.visibility="visible";
}

/* Hides the div element corresponding to a thumb by specifying its number */
function hideThumb(elem) {
	var thumb = "thumb" + elem;
	document.getElementById(thumb).style.visibility="hidden";
	document.getElementById("pagination").style.visibility="visible";	
}