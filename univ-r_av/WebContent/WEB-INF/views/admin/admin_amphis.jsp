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
	    
	    	<div class="links">
		    	<c:import url="./links.jsp" />
	    	</div>
	    	
	    	<p id="nbr"><c:out value="${buildingName}"/></p>
	    	<br>
	    	
	    	<display:table id="amphis" name="amphis" requestURI="./admin_amphis" class="displaytag">
	    		<display:column property="amphiid" sortable="true"/>
	    		<display:column property="buildingid" />
				<display:column property="name" title="Name" sortable="true" />
				<display:column property="ipAddress" sortable="true" />
				<display:column property="version" sortable="true" />
				<display:column property="number" title="Courses" sortable="true" />
				<display:column title="status live">
					<input type="checkbox" disabled="disabled" ${amphis.status == true ? 'checked' : '' } />
					<a href="<c:url value="./livestate?recordingPlace=${amphis.ipAddress}&status=begin" />">On</a> /
					<a href="<c:url value="./livestate?recordingPlace=${amphis.ipAddress}&status=end" />">Off</a>
				</display:column>
				<display:column title="client">
					<a href="<c:url value="./admin_versionclient?ip=${amphis.ipAddress}"/>">version</a>
				</display:column>
				<display:column>
					<a href="<c:url value="./admin_editamphi?id=${amphis.amphiid}&buildingId=${buildingId}" />">Edit</a>
				</display:column>
				<display:column>
					<a href="javascript:confirmation('Delete the amphi named ${fn:replace(amphis.name,'\'',' ')}?','./admin_deleteamphi?id=${amphis.amphiid}&buildingId=${buildingId}')">Delete</a>
				</display:column>
				<display:column>
					<a href="<c:url value="http://${amphis.ipAddress }" />">Access client</a>
				</display:column>
	    	</display:table>
	    	<br>
	    	<div class="add">
	    		<a href="<c:url value="./admin_addamphi?buildingId=${buildingId}" />">Add</a>		
	  		</div>
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
