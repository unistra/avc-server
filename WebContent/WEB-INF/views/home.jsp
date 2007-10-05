<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="org.ulpmm.univrav.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><%=Messages._("Univ-R AV Audiovid&eacute;cours", l)%></title>

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

	<script type="text/javascript" src="../files/js/details.js"></script>
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
    <div class="main">
	    ${session.id }
	    <div class="banner">
	    	<c:import url="include/banner.jsp" />
	    </div>
	    <div class="contents">
	    	<div class="search">
	    		<c:import url="include/searchform.jsp" />
	    	</div>
	    	
			<div class="links">
		    	<div class="downloadImage">
		    		<span class="linkText"><a href="."><%=Messages._("T&eacute;l&eacute;chargements", l)%></a></span>
		    		<span class="linkDescription"><%=Messages._("Acc&eacute;dez aux T&eacute;l&eacute;chargements du site", l)%></span>
		    	</div>
		    	<div class="rssImage">
		    		<span class="linkText"><a href="."><%=Messages._("Fil d'abonnement", l)%></a></span>
		    		<span class="linkDescription"><%=Messages._("Abonnez-vous au fil d'abonnement", l)%></span>
		    	</div>
		    	<div class="helpImage">
		    		<span class="linkText"><a href="<c:url value="./help" />"><%=Messages._("Aide", l)%></a></span>
		    		<span class="linkDescription"><%=Messages._("Utilisation d'Univ-R AV", l)%></span>
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
