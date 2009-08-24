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
		<style type="text/css">.row1,.row2{ behavior: url('../files/js/IEHoverFixes.htc');}</style>
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/home_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->
	
	<c:forEach var="rssfile" items="${rssfiles}" begin="0" end="0">
		<link rel="alternate" type="application/rss+xml" title="${rssfile.key}" href="${rssfile.value}"/>
	</c:forEach>

	<script type="text/javascript" src="../files/js/details.js"></script>
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>
	<script type='text/javascript' src="../files/js/flexcroll.js"></script>
		
	<meta name="keywords" content="SMIL, cours audio, cours video, cours live, cours en direct, synchronisation de médias, audiocours, videocours">
	
  </head>
  
  <body>
  
    <div class="main">
      <div class="contents">
	    
	    	<div class="banner">
	    		<c:import url="include/banner.jsp" />
	    	</div>
	    
	    	<div class="search">
	    		<div class="searchform">
	    			<c:import url="include/searchform.jsp" />
	    		</div>
	    		<div class="tagsform">
	    			<c:import url="include/tagsform.jsp" />
	    		</div>
	    	</div>	
	    	
	    	<div class="message">
	    		<!-- <p>Type an information message here</p> -->
	    		<!--[if lte IE 6]>
	    			<p>You cannot access the whole functionalities of the website with IE6. Please use an other browser.</p>
	    		<![endif]-->
	    	</div>
	    	
			<div class="links">
				<div class="recordedImage">
		    		<span class="linkText"><a href="<c:url value="./recorded" />"><fmt:message key="Enregistr&eacute;"/></a></span>
		    		<span class="linkDescription"><fmt:message key="Consultez les cours enregistr&eacute;s"/></span>
		    	</div>
		    	<div class="liveImage">
		    		<span class="linkText"><a href="<c:url value="./live" />"><fmt:message key="Direct"/></a></span>
		    		<span class="linkDescription"><fmt:message key="Consultez les cours en direct"/></span>
		    	</div>
		    	<div class="rssImage">
		    		<span class="linkText"><a href="./rss"><fmt:message key="Fil d'abonnement"/></a></span>
		    		<span class="linkDescription"><fmt:message key="Abonnez-vous au fil d'abonnement"/></span>
		    	</div>
	    	</div>
	    	
	    	<div class="divCourses">
	    		
	    	
	    		<div class="flexcroll">
	    		<table cellspacing="0" class="tableHome">
					<tr class="tableheader">
						<th colspan="3" id="courses"><fmt:message key="Les derniers cours"/></th>
					</tr>
					<c:import url="include/homecourselist.jsp">
						<c:param name="courses" value="lastcourses"/>
					</c:import>	
				</table>
				</div>
				
				<div class="flexcroll">
	    		<table cellspacing="0" class="tableHome">
					<tr class="tableheader">
						<th colspan="2" id="courses"><fmt:message key="selection"/></th>
					</tr>
					<c:import url="include/homecourselist.jsp">
						<c:param name="courses" value="selectioncourses"/>
					</c:import>	
				</table>
	    		</div>
				
				<div class="flexcroll">	
				<table cellspacing="0" class="tableHome">
					<tr class="tableheader">
						<th colspan="3" id="courses"><fmt:message key="collection"/>: <b class="titleColl">${collectionname}</b></th>
					</tr>
					<c:import url="include/homecourselist.jsp">
						<c:param name="courses" value="collectioncourses"/>
					</c:import>	
				</table>
	    		</div>
	    	</div>
	    	    	
    	</div>
    	
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
