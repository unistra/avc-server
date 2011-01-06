<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pt" uri="/WEB-INF/pagination-taglib.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="Cours enregistr&eacute;s"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/recorded.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/searchform.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/courselist.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
		<style type="text/css">.row1,.row2{ behavior: url('../files/js/IEHoverFixes.htc');}</style>
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/recorded_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->
	
	<c:forEach var="rssfile" items="${rssfiles}" begin="0" end="0">
		<link rel="alternate" type="application/rss+xml" title="${rssfile.key}" href="${rssfile.value}"/>
	</c:forEach>

	<script type="text/javascript" src="../files/js/details.js"></script>
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>
	<script type="text/javascript" src="../files/js/tags.js"></script>
	
	<!-- google analytics -->
	<c:import url="include/google_analytics.jsp" />
		
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
	    	<div class="course">
				
				<div class=displayTags>
					<c:forEach var="tag" items="${listTags}" varStatus="status"><a class="pTag"><c:out value="${tag}"/></a><c:if test="${status.index!=listTagsSize-1}"><a class="closeDualTag" href="javascript:closeTag('${tag}')"/><img class="imgTag "src="../files/styles/${sessionScope.style}/img/transp.png"></a></c:if><c:if test="${status.index==listTagsSize-1}"><a class="closeTag" href="javascript:closeTag('${tag}')"/><img class="imgTag "src="../files/styles/${sessionScope.style}/img/transp.png"></a></c:if></c:forEach>
				</div>
					
				<c:choose>
					<c:when test="${!empty courses}">	
						<table cellspacing="0">
							<tr class="tableheader" id="tableheader">
								<th colspan="5" id="courses"><fmt:message key="Les cours"/></th>
							</tr>
							<c:import url="include/courselist.jsp" />
						</table>
								
	    				<div class="pagination">
	    					<pt:PaginationTag currentPage="${page}" itemsNumber="${items}" numberPerPage="${number}" resultPageName="${resultPage}" tags="${tags}" paramsUrl="${paramsUrl}" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="divCenter">
							<br>
							<p><fmt:message key="nocourses"/></p>
						</div>
					</c:otherwise>
				</c:choose>
								
	    	</div>
    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>