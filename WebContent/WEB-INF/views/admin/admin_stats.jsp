<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/admin.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/menus_off_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
    <div class="main">
	    
	    <div class="contents">
	    	<div class="banner">
	    		<c:import url="../include/banner.jsp" />
	    	</div>
	    	
	    	<div class="links">
		    	<c:import url="./links.jsp" />
	    	</div>
	    	
	    	<div class="diskspace">
	    	
	    		<div class="infos">
	    			<h2>Disk space information (MB)</h2>
	    			<pre id="pre">
	    				<c:out value="${diskspaceinfo}" />
	    			</pre>
	    		
	    		</div>	    
	    			
	    		<br>
	    		
	    		<a href="<c:url value="${statsUrl}" />">Univ-R AV access stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="${statsUrl}adminfms/fms_adminConsole.htm" />">FMS stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="./versionclient?ip=all"/>">Clients version stats</a>
	    		<br>
	    		<br>
	    		<a href="<c:url value="${oldStatsUrl}xml.html#Requested pages" />">XML stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="${oldStatsUrl}mp3.html#Requested pages" />">MP3 stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="${oldStatsUrl}pdf.html#Requested pages" />">PDF stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="${oldStatsUrl}flv.html#Requested pages" />">FLV stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="${oldStatsUrl}ogg.html#Requested pages" />">OGG stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="${oldStatsUrl}rm.html#Requested pages" />">RM stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="${oldStatsUrl}smil.html#Requested pages" />">SMIL stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="${oldStatsUrl}zip.html#Requested pages" />">ZIP stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    		<a href="<c:url value="${oldStatsUrl}mp4.html#Requested pages" />">MP4 stats</a>
	    		&nbsp;&nbsp;&nbsp;
	    			    			
	    	</div>
	    	
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
