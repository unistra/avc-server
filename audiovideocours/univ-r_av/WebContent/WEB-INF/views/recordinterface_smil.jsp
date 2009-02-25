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
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/visualization_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/js/ieupdate.js"></script>
	<script type="text/javascript" src="../files/js/recordinterface.js"></script>
	<script type="text/javascript">
		var timecodes = ${slides};
		var style = "${sessionScope.style}";
		var slidesurl = "${slidesurl}";
		var timing = ${timing};
	</script>

  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	
	    	<div class="amphitheatre">${building} ${building != '' ? '|' : ''} ${amphi}</div>
	    		    	
	    	<a class="closeButton" href=".${sessionScope.previousPage}"><fmt:message key="Fermer"/> <img src="../files/styles/${sessionScope.style}/img/close.png"></a>
	    	
	    	<br><br>
	    	
			<div class="smil">
				<object name="video" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="970" height="550">
					<param name="src" value="${courseurl}">
					<param name="controls" value="ImageWindow">
					<param name="console" value="console">
					<param name="autostart" value="true">
					<param name="wmode" value="transparent">
					<embed type="audio/x-pn-realaudio-plugin" id="video" src="${courseurl}" width="970" height="550" align="middle" controls="ImageWindow" console="console" autostart="true" wmode="transparent">
				</object>
				<br>
				<object classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="970" height="40">
					<param name="controls" value="ControlPanel">
					<param name="console" value="console">
					<param name="wmode" value="transparent">
					<embed type="audio/x-pn-realaudio-plugin" width="970" height="40" align="middle" controls="ControlPanel" console="console" wmode="transparent">
				</object>
			</div>
			
			<br>
			
			<div id="videoLine">
			</div>
			
			<div class="leftPagination">
				<a href="javascript:previousPage()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="Precedent"/></a>
			</div>
			
			<div class="rightPagination">
			<fmt:message key="Page"/> <span id="pageNumber"></span>
			<a href="javascript:nextPage()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="Suivant"/></a>
			| <fmt:message key="diapositive"/> <span id="slideNumber"></span>
			</div>

    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
