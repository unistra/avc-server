// flash player
var player;
// initialize variables
var playerWidth = '320';
var playerHeight = '260';
// size min and max
var playerMinWidth = '320';
var playerMinHeight = '260';
var playerMaxWidth = '640';
var playerMaxHeight = '480';


/* function to call when the player is ready */
function playerReady(obj) {
	player = document.getElementById(obj['id']);
	
	playerHeight = player.offsetHeight;
	playerWidth = player.offsetWidth;
};


/*
 * function to modify the player size
 */
function modifyPlayerSize() {
				
	// when player 320 260
	if((playerWidth==playerMinWidth) && (playerHeight==playerMinHeight)) {
		player.style.width = playerMaxWidth;
		player.style.height = playerMaxHeight;
		playerWidth=playerMaxWidth;
		playerHeight=playerMaxHeight;
		document.getElementById("linkplayersize").style.display="none";
		document.getElementById("linkplayersize2").style.display="block";
	}
	// when player 640 480
	else {
		player.style.width = playerMinWidth;
		player.style.height = playerMinHeight;
		playerWidth=playerMinWidth;
		playerHeight=playerMinHeight;
		document.getElementById("linkplayersize").style.display="block";
		document.getElementById("linkplayersize2").style.display="none";
	}
	
	resizeLiveside();
}


/*!
 * smartresize: debounced resize event for jQuery
 *
 * Copyright (c) 2009 Louis-Rémi Babé
 * Licensed under the GPL license.
 * http://docs.jquery.com/License
 *
 */
(function($) {

var event = $.event,
	resizeTimeout;

event.special[ "smartresize" ] = {
	setup: function() {
		$( this ).bind( "resize", event.special.smartresize.handler );
	},
	teardown: function() {
		$( this ).unbind( "resize", event.special.smartresize.handler );
	},
	handler: function( event, execAsap ) {
		// Save the context
		var context = this,
			args = arguments;

		// set correct event type
        event.type = "smartresize";

		if(resizeTimeout)
			clearTimeout(resizeTimeout);
		resizeTimeout = setTimeout(function() {
			jQuery.event.handle.apply( context, args );
		}, execAsap === "execAsap"? 0 : 100);
	}
}

$.fn.smartresize = function( fn ) {
	return fn ? this.bind( "smartresize", fn ) : this.trigger( "smartresize", ["execAsap"] );
};

})(jQuery);


/*
 * resize live slide div
 */
function resizeLiveside(divname) {
		
	var tabw=new Array(	800,  1024,  1152,  1280,   1680,  1920);
	var tabh=new Array(	600,   768,   864,  1024,   1050,  1200);
	
	var tabwimg;
	var tabhimg;
	
	// when player 320 260
	if((playerWidth==playerMinWidth) && (playerHeight==playerMinHeight)) {
		tabwimg=new Array( 620,   620,   620,   840,   1004,  1280);
		tabhimg=new Array( 472,   472,   472,   584,    748,  1024);
	}
	// when player 640 480
	else {
		tabwimg=new Array( 300,   300,   300,   520,   684,  960);
		tabhimg=new Array( 252,   252,   252,   364,    528,  804);
	}
	
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
			break;
		}	
	}
	
	if(i<tabw.length-1){
		i++;
	}

	document.getElementById('liveslideimg').style.height = tabhimg[i]+'px';
	document.getElementById('liveslideimg').style.width = tabwimg[i]+'px';
}
