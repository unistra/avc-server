var currentSlide = 0; // current slide number displayed in the smil media
var oldSlide = 0; // previous slide number displayed in the smil media
var pageNumber = 1; // current page of the pagination
var paginationUpdateTime = 10000; // after having clicked on the pagination, the time bar is not updated during this time
var update = true; // if the time bar has to be updated or not
var numberPerPage = 10; // number of time points in a page
var player;
var html5=false;

/* function to call when the player is ready */
function playerReady(obj) {
	player = document.getElementById(obj['id']);
	// init the time bar	
	initTimeBar();
	updateTimeBar(0);
	// add time listener
	player.addModelListener('TIME', 'timeMonitor');
};

//html5
function initPlayerHtml5() {
	//init player html5 exist (no flash)
	if(document.getElementById("playerhtml5").currentTime == 0) {
		html5 = true;
		//block right click
		$('#playerhtml5').bind('contextmenu',function() { return false; });
		//add listeners
		player = document.getElementById("playerhtml5");
		player.addEventListener("seeked", function() { player.play(); }, true);  
		player.addEventListener("timeupdate",function () { updateTimeBar(player.currentTime); },true);	
		// init the time bar	
		initTimeBar();
	}
}

/* Time listener fonction */
function timeMonitor(obj) {
	updateTimeBar(obj.position);
}

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
			data = '<img class="slide" src="' + slidesurl + 'D' + (currentSlide + timing) + '.jpg" width="'+tabwimg[i]+'" height="'+tabhimg[i]+'"/>';
			
			// change the front slide
			$('#frontslide').fadeOut(10,function() { 
	            $(this).html(data).fadeIn(500, function() { 
	            	// add the current front slide to the back slide
	            	$('#backslide').html(data);
				});
			});
			
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
	
	if(html5==true) {
		player.currentTime = timecodes[slide-1];  
	}
	else {
		player.sendEvent('SEEK', timecodes[slide-1]);
	}
	
	update = true;
}

/* Checks if the current slide is in the same page as the previous one, and modify the time bar and pagination if not */
function checkPageChange() {
	var res = false;
	
	// correct a bug when no current slide
	if(currentSlide==0) {
		pageNumber = 1;
		initTimeBar();
		oldSlide = 0;
		res = true;
	}
	else if( Math.ceil(currentSlide/numberPerPage) > pageNumber) { //next page
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
		// preload full image
		(new Image()).src = slidesurl + 'D' + (firstSlide + i + timing) + ".jpg";
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
	}
}

//fullscreen on dia click
function fullScreen() {	
	$('#currentDia').click(function() {
		$("#currentDia").colorbox({
			inline:true, 
			href:"#currentDia",
			scrolling:true,
			width:"100%",
			height:"100%"
		}); 
	});
}

$(function() {
	fullScreen();
});