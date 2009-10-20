<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
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

  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	
	    	<div class="amphitheatre">${building} | ${amphi}</div>  	
	    	<a class="closeButton" href=".${sessionScope.previousPage}"><fmt:message key="Fermer"/> <img src="../files/styles/${sessionScope.style}/img/close.png"></a>
	    		 
	    	<br><br>	 
	    		    	    		    	
			<iframe id="DiaFrame" name="DiaFrame" scrolling="no" src="./iframe_liveslide?ip=${ip}"></iframe>
			
			<br><br>
						
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
    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
