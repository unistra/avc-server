<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!doctype html>
<html>
  <head>

    <meta charset="utf-8">
      
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="Visualisation du cours"/></title>

	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualizationLive.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualizationLive_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->
	
	<script type="text/javascript" src="../files/js/ieupdate.js"></script>
	<script type="text/javascript" src="../files/jwflvplayer/swfobject.js"></script>
	<script type="text/javascript" src="../files/js/jquery.min.js"></script>
	<script type="text/javascript" src="../files/js/liveinterface.js"></script>
		
	<script type="text/javascript">
		$.ajaxSetup({cache:false}) 
		function loadLiveslide() { $('#liveslidediv').load('./liveslide?ip=${ip}'); }
		var auto_refresh = setInterval("loadLiveslide()", 5000);  // every 5 sec


		// When browser resolution changed
		$(window).smartresize(function(){
			resizeLiveside('liveslideimg');
		});


		
	</script>
	
	<!-- google analytics -->
	<c:import url="include/google_analytics.jsp" />
	
  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	
	    	<div class="firstline">
	    		<div class="amphitheatre">${building} | ${amphi}</div>  	
	    		<div class="closeButton">
	    			<a class="closeButton" href=".${sessionScope.previousPage}"><fmt:message key="Fermer"/> <img src="../files/styles/${sessionScope.style}/img/close.png" alt="close"></a>
	    		</div>
	    	</div>	
	    	
	    	
	    	<div class="visumain"> 
	    	
	    		<div class="visuleft">
						
		    	<script type="text/javascript">
     				 var flashvars =
     				 {
     					file:					'${fileUrl}', 
            			id:						'flashvideo', 
           				autostart:				'true',
           				image:					'../files/img/logo_audio.png',
           				streamer:				'${streamerUrl}',
           				type:					'rtmp' 	  
     				 };

     				 var params =
     				 {
       			     	allowfullscreen:		'true', 
       					allowscriptaccess:		'always'
     				 };

      				var attributes =
     				 {
     				 	id:						'flashvideo', 
       					name:					'flashvideo'
     				 };

    				  swfobject.embedSWF('../files/jwflvplayer/player.swf', 'flashvideo', '320', '260', '9.0.124', false, flashvars, params, attributes);
    				  
   				 </script>
   				 <p id="flash"><a id="flashvideo" href="http://www.adobe.com/go/getflashplayer">Get flash to see this player </a></p>
   				 
   				 
   				 <a id="linkplayersize" class="linkplayersize" href="javascript:modifyPlayerSize();"><img src="../files/styles/${sessionScope.style}/img/btn_enl.png" alt="enlarge"></a>
   				 
   				 
   				 <a id="linkplayersize2" class="linkplayersize2" href="javascript:modifyPlayerSize();" style="display:none;"><img src="../files/styles/${sessionScope.style}/img/btn_red.png" alt="reduce"></a>
   				 
   			</div>
   			
   			<div class="visuright"> 		    		    	    	
	    	
	    	<!-- dia div -->	    	    	
	    	<div class="liveslidediv" id="liveslidediv"></div>
	    	
	    	<!-- load live slide first time -->
	    	<script type="text/javascript">
	    		loadLiveslide();
	    	</script>
	    	
	    	</div>
	    	 				 
   				 
    	</div>
    	
    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
