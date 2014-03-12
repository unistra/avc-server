<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	   		<div class="banner">
	    		<c:import url="../include/banner.jsp" />
	    	</div>
	    		    	
	    	<c:choose>
				<c:when test="${building.imageFile == '' || building.imageFile == null}">
					<c:set var="imageFile" value="generique-thumb.png" />
				</c:when>
				<c:when test="${building.imageFile != '' && building.imageFile != null}">
					<c:set var="imageFile" value="${building.imageFile}" />
				</c:when>
			</c:choose>
	    	    	
	    	<div class="editform">
		    	<form method="POST" action="<c:url value="./gp_validatebuilding" />">
			    	<table>
			    		<tr class="odd">
				    		<td>BuildingId</td>
				    		<td><input type="hidden" name="buildingid" value="${building.buildingid}">${building.buildingid}</td>
			    		</tr>
			    		<tr class="even">
				    		<td>Name</td>
				    		<td><input type="text" name="name" value="${building.name}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>ImageFile</td>
				    		<td><input type="text" name="imagefile" value="${imageFile}" class="field"></td>
			    		</tr>
			    	</table>
			    	<br>
			    	<input type="hidden" name="action" value="${action}">
			    	<input type="submit" value="Validate">
			    	<br><br>
			    	<a href="<c:url value="./gp_home" />">Go back</a>
		    	</form>
		    </div>
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
