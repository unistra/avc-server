var currentSlide = 0; // current slide number displayed in the smil media
var oldSlide = 0; // previous slide number displayed in the smil media
var pageNumber = 1; // current page of the pagination
var paginationUpdateTime = 10000; // after having clicked on the pagination, the time bar is not updated during this time
var update = true; // if the time bar has to be updated or not
var numberPerPage = 10; // number of time points in a page


/* function to call when the page starts */
window.onload = function(){
	// calls the method to update the time bar
	initTimeBar();
	updateTimeBar(0);
};

function sendEvent(swf,typ,prm) { 
	thisMovie(swf).sendEvent(typ,prm); 
};

function thisMovie(movieName) {
	if(navigator.appName.indexOf("Microsoft") != -1) {
	        return window[movieName];
	} else {
	        return document[movieName];
	}
};

function getUpdate(typ,pr1,pr2,swf) {
	if(typ == "time") {  
	    updateTimeBar(pr1);
	}
};

/* Updates the time bar with the position of the video */
/* This function is called every second */
function updateTimeBar(time) {

	// retrieves the current slide number
	currentSlide = getSlideFromTime(time);	

	// if the slide displayed has changed
	if( currentSlide != oldSlide ) {

		// if the time bar must be updated
		if( update || Math.ceil(currentSlide/numberPerPage) == pageNumber) {
		
			// verifies if the page has changed in the pagination
			checkPageChange();
			
			// changes the points images for the current and old slide
			document.getElementById('time' + currentSlide).className="currentThumb";
					
			tabw=new Array(	   800,  1024,  1152,  1280,   1680,  1920);
   			tabh=new Array(	   600,   768,   864,  1024,   1050,  1200);
   			
   			tabwimg=new Array( 620,   620,   620,   840,   1004,  1280);
   			tabhimg=new Array( 472,   472,   472,   584,    748,  1024);

   			// Width and height of the window
   			if(!document.all) { 
   				w=window.innerWidth; 
   				h=window.innerHeight; 
   			}
   			else { 
   				w=document.documentElement.offsetWidth; 
   			    h=document.documentElement.offsetHeight;
   			}
   			
   			for(i=tabw.length-1;i>=0;i--) {
   				if((w>tabw[i]) || (h>tabh[i])) {
   				//	alert("BRK w " + w + " h " + h + " i" + i + " tabw[i] " + tabw[i] +" tabh[i]" + tabh[i] );
   					break;
   				}	
   			}
   			

			if(i<tabw.length-1){
				i++;
			}
   				
			// changes the current slide
			document.getElementById("currentDia").innerHTML = '<a target="external" href="' + slidesurl + 'D' + (currentSlide + timing) + '.jpg"><img class="slide" src="' + slidesurl + 'D' + (currentSlide + timing) + '.jpg" width="'+tabwimg[i]+'" height="'+tabhimg[i]+'"/></a>';
			
			if(oldSlide > 0)
				document.getElementById('time' + oldSlide).className="otherThumb";
			
			oldSlide=currentSlide;
		}
		
		// updates the page and slide numbers
		updatePagination();
                       
	}

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
    sendEvent('flashvideo','scrub', timecodes[slide-1])
	update = true;
}

/* Checks if the current slide is in the same page as the previous one, and modify the time bar and pagination if not */
function checkPageChange() {
	var res = false;
	
	if( Math.ceil(currentSlide/numberPerPage) > pageNumber) { //next page
		pageNumber = Math.ceil(currentSlide/numberPerPage);
		initTimeBar();
		oldSlide = 0;
		res = true;
	}
	else if( Math.ceil(currentSlide/numberPerPage) < pageNumber) { // previous page
		pageNumber = Math.ceil(currentSlide/numberPerPage);
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
	var firstSlide = (pageNumber-1)*numberPerPage + 1;
	
	// adds each time point and corresponding thumb
	for( i=0 ; i< numberPerPage && (firstSlide + i <= timecodes.length) ; i++) {
		document.getElementById("videoLine").innerHTML += '<a href="javascript:setTimeFromSlide(' + (firstSlide + i)  + ')"><img id="time' + (firstSlide + i)  + '" class="otherThumb" src="' + slidesurl + 'D' + (firstSlide + i + timing) + '-thumb.jpg"></a>';
	}
}

/* Updates the page and slide numbers in the HTML code */
function updatePagination() {
	document.getElementById("slideNumber").innerHTML = currentSlide + "/" + timecodes.length;
	document.getElementById("pageNumber").innerHTML = pageNumber + "/" + Math.ceil(timecodes.length/numberPerPage);
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
		//updatePagination();
	}
}

/* Sets the position of the smil media to the next page */
function nextPage() {
	if( pageNumber < Math.ceil(timecodes.length/numberPerPage) ) {
		pageNumber++;
		// disables the time bar update and then enables it again
		update = false;
		setTimeout("update=true", paginationUpdateTime);
		oldSlide = 0;
		initTimeBar();
		// updates the page and slide numbers
		//updatePagination();
	}
}

