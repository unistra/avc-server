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
	    	
			<div class="smil">
				<object width="970" height="550">
					<param name="movie" value="${courseurl}">
					<embed src="${courseurl}" width="970" height="550"/>
				</object>
			</div>

    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
