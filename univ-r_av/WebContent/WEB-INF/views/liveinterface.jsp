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
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->
	
	<script type="text/javascript" src="../files/js/ieupdate.js"></script>

  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	
	    	<div class="amphitheatre">${building} | ${amphi}</div>  	
	    	<a class="closeButton" href=".${sessionScope.previousPage}"><fmt:message key="Fermer"/> <img src="../files/styles/${sessionScope.style}/img/close.png"></a>
	    	
			<iframe id="DiaFrame" name="DiaFrame" width="966" height="724" scrolling="no" src="./iframe_liveslide?ip=${ip}"></iframe>
			<br><br>
			<c:choose>
				<c:when test="${type == 'audio'}">
					<embed name="live" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/" src="${url}" type="application/x-mplayer2" autostart="true" showstatusbar="1" width="300" height="65" />
				</c:when>
				<c:when test="${type == 'video'}">
					<object id="video" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="320" height="240">
						<param name="src" value="${url}">
						<param name="controls" value="ImageWindow">
						<param name="console" value="console">
						<param name="autostart" value="true">
						<embed type="audio/x-pn-realaudio-plugin" name="video" src="${url}" width="320" height="240" align="middle" controls="ImageWindow" console="console" autostart="true">
					</object>
						<br />
					<object classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="320" height="40">
						<param name="controls" value="ControlPanel">
						<param name="console" value="console">
						<embed type="audio/x-pn-realaudio-plugin" width="320" height="40" align="middle" controls="ControlPanel" console="console">
					</object>
				</c:when>
			</c:choose>

    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
