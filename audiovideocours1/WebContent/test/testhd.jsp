<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="Visualisation du cours"/>&nbsp;${course.title}</title>

	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->
	
	<script type="text/javascript" src="../files/jwflvplayer/swfobject.js"></script>
	<!-- <script type="text/javascript" src="../files/js/ieupdate.js"></script> -->
	<script type="text/javascript" src="../files/js/recordinterface_flash.js"></script>
	<script type="text/javascript">
		var timecodes = ${slides};
		var style = "${sessionScope.style}";
		var slidesurl = "${slidesurl}";
		var timing = ${timing};
	</script>
	
	<meta name="keywords" content="${course.name},${course.title},${course.formation}">
	

  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	
	    	<div class="firstline">
		    	<div class="amphitheatre">${building} | ${amphi}</div>
		    	<a class="closeButton" href=".${sessionScope.previousPage}"><fmt:message key="Fermer"/> <img src="../files/styles/${sessionScope.style}/img/close.png"></a>
	    	</div>
	    			
	        <div class="flashslide">
	        	           			
	         	<p id="flash">Please install flash player</p>
	            <script type="text/javascript">
		        var so = new SWFObject('../files/jwflvplayer/player_flv_maxi.swf','flashvideo','1224','776','8');
		        so.addParam('movie','../files/jwflvplayer/player_flv_maxi.swf');
		        so.addParam('allowFullScreen','true');
		        so.addVariable('flv','${courseurl}');
		        so.addVariable('autoplay','0');
		        so.addVariable('autoload','1');
		        so.addVariable('showfullscreen','1');
		        so.addVariable('showvolume','1');
		        so.addVariable('showstop','1');
		        so.addVariable('showtime','1');
		        so.addVariable('showloading','always');
		        so.addVariable('showplayer','autohide');
				so.addVariable('title', '<fmt:message key="Auteur :"/>  ${course.name}\n'+
										'<fmt:message key="Formation :"/>  ${course.formation}\n' +
										'<fmt:message key="Titre :"/> ${course.title}\n' +
										'<fmt:message key="Sujet :"/> ${course.description}\n' +
										'<fmt:message key="Date :"/> <dt:format pattern="${dateFormat}">${course.date.time}</dt:format>\n' +
										'<fmt:message key="Type :"/> ${course.type}\n' +
										'<fmt:message key="Dur&eacute;e :"/> ${course.durationString}\n' +
										'<fmt:message key="Consultations :"/> ${course.consultations}\n'
									);

		        so.write('flash');
		           
	            </script>

 
			</div>
									
    	</div>
		    
    </div>
        
  </body>
</html>