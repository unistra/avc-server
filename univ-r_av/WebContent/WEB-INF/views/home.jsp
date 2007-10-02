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
	<link rel="stylesheet" type="text/css" href="../files/styles/style1/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/style1/css/home.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/style1/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/style1/css/home_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/js/details.js"></script>
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
    <div class="main">
	    <div class="banner">
	    	<a class="bannerPageZone" href=".."></a>
	    	<a class="ulpmmZone" href="http://ulpmultimedia.u-strasbg.fr/" target="external"></a>
	    	<a class="ulpZone" href="http://www-ulp.u-strasbg.fr/" target="external"></a>
	    	<div class="live">
	    		<a class="liveZone" href="./live"><%=Messages._("Direct", l)%></a>
	    	</div>
	    	<div class="recorded">
	    		<a class="recordedZone" href="./recorded"><%=Messages._("Enregistr&eacute;", l)%></a>
	    	</div>
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
		    		<span class="linkText"><a href="./help/index.jsp"><%=Messages._("Aide", l)%></a></span>
		    		<span class="linkDescription"><%=Messages._("Utilisation d'Univ-R AV", l)%></span>
		    	</div>
	    	</div>
	    	
	    	<div class="course">
	    		<c:import url="include/courselist.jsp" />
	    	</div>
    	</div>
	    	
	    <div class="footer">
	    	<p>
		    	<%=Messages._("R&eacute;alisation du site par ULP Multim&eacute;dia - 2007", l)%> <br>
		    	<a href="."><%=Messages._("Contact", l)%></a> - <a href="."><%=Messages._("Informations l&eacute;gales", l)%></a> - <a href="."><%=Messages._("Liens", l)%></a>
	    	</p>
	    </div>
    </div>
  </body>
</html>
