<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
	    	
	    	<display:table id="buildings" name="buildings" requestURI="./gp_home" class="displaytag">
	    		<display:column property="buildingid" sortable="true"/>
	    		<display:column property="name" title="Name" sortable="true" />
				<display:column property="imageFile" />
				<display:column>
					<a href="<c:url value="./gp_editbuilding?id=${buildings.buildingid}" />">Edit</a>
				</display:column>
				<display:column>
					<a href="<c:url value="./gp_amphis?buildingId=${buildings.buildingid}" />">Amphis</a>
				</display:column>
	    	</display:table> 
	    	<br>
	    	
	    	
	    	<div class="displaytag">   	
		    	<form method="POST" action="<c:url value="./gp_validatebuilding" />">
		    		<input type="hidden" name="buildingid" value="${building.buildingid}">
		    		<input type="hidden" name="action" value="${action}">
			    	<table cellspacing="0">
			    		<tr class="tableheader">
							<td class="littleFont">Name</td>
							<td class="littleFont">ImageFile</td>
							<td class="littleFont">Add</td>
						</tr>
			    		<tr class="odd">	    		
				    		<td><input type="text" name="name" class="field"></td>
			  				<td><input type="text" name="imagefile" value="generique-thumb.png" class="field"></td>
			  				<td><input type="submit" value="Validate"></td>
			    		</tr>
			    	</table>
		    	</form>
		    </div>
	    	
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
