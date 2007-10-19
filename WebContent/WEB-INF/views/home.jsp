<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/home.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/searchform.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/courselist.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/home_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->
	
	<c:forEach var="rssfile" items="${rssfiles}">
		<link rel="alternate" type="application/rss+xml" title="${rssfile.key}" href="${rssfile.value}"/>
	</c:forEach>

	<script type="text/javascript" src="../files/js/details.js"></script>
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
    <div class="main">
	    <div class="banner">
	    	<c:import url="include/banner.jsp" />
	    </div>
	    <div class="contents">
	    	<div class="search">
	    		<c:import url="include/searchform.jsp" />
	    	</div>
	    	
			<div class="links">
		    	<div class="downloadImage">
		    		<c:url var="thick_download" scope="page" value="./thick_download">
						<c:param name="width" value="250"/>
						<c:param name="height" value="100"/>
					</c:url>
		    		<span class="linkText"><a href="<c:out value="${thick_download}" />" title="<fmt:message key="T&eacute;l&eacute;chargements"/>" class="thickbox"><fmt:message key="T&eacute;l&eacute;chargements"/></a></span>
		    		<span class="linkDescription"><fmt:message key="Acc&eacute;dez aux T&eacute;l&eacute;chargements du site"/></span>
		    	</div>
		    	<div class="rssImage">
		    		<span class="linkText"><a href="../rss/${rssFileName}"><fmt:message key="Fil d'abonnement"/></a></span>
		    		<span class="linkDescription"><fmt:message key="Abonnez-vous au fil d'abonnement"/></span>
		    	</div>
		    	<div class="helpImage">
		    		<span class="linkText"><a href="<c:url value="./help" />"><fmt:message key="Aide"/></a></span>
		    		<span class="linkDescription"><fmt:message key="Utilisation d'Univ-R AV"/></span>
		    	</div>
	    	</div>
	    	
	    	<div class="course">
	    		<c:import url="include/courselist.jsp" />
	    	</div>
    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
