<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dt" uri="/WEB-INF/taglibs-datetime.tld" %>
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
	
	<script type="text/javascript" src="../files/jwflvplayer/swfobject.js"></script>
	<!-- <script type="text/javascript" src="../files/js/ieupdate.js"></script> -->
	<script type="text/javascript" src="../files/js/recordinterface_flash.js"></script>
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
	    	
	    	<div class="firstline">
		    	<div class="amphitheatre">${building} | ${amphi}</div>
		    	<a class="closeButton" href=".${sessionScope.previousPage}"><fmt:message key="Fermer"/> <img src="../files/styles/${sessionScope.style}/img/close.png"></a>
	    	</div>
	    	
			<div class="flashslide">
				<p id="flash">This text will be replaced</p>
	            <script type="text/javascript">
		            var so = new SWFObject('../files/jwflvplayer/mediaplayer.swf','flashvideo','320','260','8');
		            so.addParam('allowscriptaccess','always');
		            so.addParam('allowfullscreen','true');
		            so.addVariable('width','320');
		            so.addVariable('height','260');
		            so.addVariable('file','${courseurl}');
		            so.addVariable('autostart','true');
		            so.addVariable('javascriptid','flashvideo');
		            so.addVariable('enablejs','true');
		            so.write('flash');
	            </script>
	            
	            <div id="currentDia">		
				</div>
				
				<p id="info">
					<b><fmt:message key="Enseignant :"/> </b> <c:out value="${course.name}" /> <c:out value="${course.firstname}" /> <br>
					<b><fmt:message key="Formation :"/> </b> <c:out value="${course.formation}" /> <br>
					<b><fmt:message key="Titre :"/> </b> <c:out value="${course.title}" /> <br>
					<b><fmt:message key="Sujet :"/> </b> <c:out value="${course.description}" /> <br>
					<fmt:message key="dateFormat" var="dateFormat" />
					<b><fmt:message key="Date :"/> </b> <dt:format pattern="${dateFormat}">${course.date.time}</dt:format> <br>
					<b><fmt:message key="Type :"/> </b> <c:out value="${course.type}" /> <br>
					<b><fmt:message key="Dur&eacute;e :"/> </b> <c:out value="${course.durationString}" /> <br>
					<b><fmt:message key="Consultations :"/> </b> <c:out value="${course.consultations}" /> <br>
				</p>
			</div>
			
			<br>
			
			<div id="videoLine">
			</div>
			
			<a class="leftPagination" href="javascript:previousPage()">&lt;&lt;</a>
			<div class="rightPagination">
			Page <span id="pageNumber"></span>
			<a href="javascript:nextPage()">&gt;&gt;</a>
			| Diapo <span id="slideNumber"></span>
			</div>

    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
