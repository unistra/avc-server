<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!doctype html>
<html>
  <head>

  	<meta charset="utf-8">
    
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

	<script type="text/javascript" src="../files/js/confirmation.js"></script>
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
	    	
	    		
	    	<display:table id="disciplines" name="disciplines" requestURI="${viewurl}" class="displaytag">
	    		<display:column property="disciplineid" title="Discipline id" sortable="true"/>
	    		<display:column property="codecomp" title="Component code" sortable="true"/>
	    		<display:column property="namecomp" title="Component name" sortable="true"/>
	    		<display:column property="codedom" title="Domain code" sortable="true"/>
	    		<display:column property="namedom" title="Domain name" sortable="true"/>
	    		<display:column>
					<a href="<c:url value="${editurl}?id=${disciplines.disciplineid}" />">Edit</a>
				</display:column>
				<display:column>
					<a href="javascript:confirmation('Delete the discipline named ${disciplines.disciplineid}?','${deleteurl}?id=${disciplines.disciplineid}')">Delete</a>
				</display:column>
	
	    	</display:table>
	    	
	    	<br>
	    	<div class="add">
	    		<a href="<c:url value="./admin_adddiscipline"/>">Add</a>	
	    	</div>
	    	<br>
	    	<p class="nbr">${number} disciplines</p>
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
