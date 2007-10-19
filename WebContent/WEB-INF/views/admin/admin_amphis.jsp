<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/home_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/js/confirmation.js"></script>
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
    <div class="main">
	    <div class="banner">
	    	<c:import url="../include/banner.jsp" />
	    </div>
	    <div class="contents">
	    	<display:table id="amphis" name="amphis" requestURI="./admin_amphis">
	    		<display:column property="amphiid" sortable="true"/>
	    		<display:column property="buildingid" />
				<display:column property="name" sortable="true" />
				<display:column property="type" sortable="true" />
				<display:column property="ipAddress" sortable="true" />
				<display:column title="status">
					<input type="checkbox" disabled="disabled" ${amphi.status == true ? 'checked' : '' } />
				</display:column>
				<display:column>
					<a href="./admin_editamphi?id=${amphis.amphiid}&buildingId=${buildingId}">Edit</a>
				</display:column>
				<display:column>
					<a href="javascript:confirmation('Delete the amphi ?','./admin_deleteamphi?id=${amphis.amphiid}&buildingId=${buildingId}')">Delete</a>
				</display:column>
	    	</display:table>
	    	<br>
	    	<a href="./admin_addamphi?buildingId=${buildingId}">Add</a>		
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
