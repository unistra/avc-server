<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pt" uri="/WEB-INF/pagination-taglib.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!doctype html>
<html>
  <head>
    
    <meta charset="utf-8">

    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="Cours enregistr&eacute;s"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/recorded.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/searchform.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/courselist.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/myspace.css">
	
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

	<script type="text/javascript" src="../files/js/confirmation.js"></script>
	<script type="text/javascript" src="../files/js/details.js"></script>
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>
	
	<!-- google analytics -->
	<c:import url="../include/google_analytics.jsp" />
	
  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	<div class="banner">
	    		<c:import url="../include/banner.jsp" />
	     	</div>
	    	
	    	<div class="welcome">
	    		<c:choose>
					<c:when test="${user.lastname!=null}">	
	    				<p><fmt:message key="Bienvenue"/> ${user.firstname} ${user.lastname}</p>
					</c:when>
					<c:otherwise>
						<p><fmt:message key="Bienvenue"/> ${user.login}</p>
					</c:otherwise>
				</c:choose>		
	    	</div>
	    	<br>
	    	<div class=btnMyspace>

	    		<div class=btnMyspace>
 
                <!-- check if publisher ldap profile can publish -->
                <c:if test="${(fn:contains(publisherLdapProfiles,user.profile)) and (fn:length(user.profile)>0)}">
		    		<div class="btnUpload">
		    			<a href="<c:url value="./myspace_upload" />" title="<fmt:message key="upload"/>" ><fmt:message key="upload"/></a>
		    		</div>
		    	</c:if>
	    		
	    		<!-- change mdp for local user -->
	    		<c:if test="${$userLocalLogin != null}">
	    			<div class="btnChangePass">
	    				<a href="<c:url value="./myspace_changepass" />" title="<fmt:message key="changePass"/>" ><fmt:message key="changePass"/></a>
	    			</div>
	    		</c:if>
	    	</div>

	    	<br />
	    	<div class="divLeft">	    	
	    		<form name="input" action="./myspace_home" method="post">
					<p><fmt:message key="Recherche"/>: <input type="text" name="keywords" value="${keywords}" />
					<input type="submit" value="<fmt:message key="Valider"/>" />
				</form>
			</div>
	    	
	    	<c:choose>
			<c:when test="${!empty courses}">	
	    		<div class="course">
					<table>
						<tr class="tableheader" id="tableheader">
							<th colspan="5" id="courses"><fmt:message key="Mes cours"/></th>
							<th colspan="1"><fmt:message key="Editer"/></th>
							<th colspan="1" ><fmt:message key="Supprimer"/></th>
						</tr>
						<c:import url="./mycourselist.jsp" />
					</table>
	    			<div class="pagination">
	    				<pt:PaginationTag currentPage="${page}" itemsNumber="${items}" numberPerPage="${number}" resultPageName="${resultPage}" paramsUrl="${paramsUrl}"/>
					</div>
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
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
