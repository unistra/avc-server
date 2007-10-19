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
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/admin.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/home_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
    <div class="main">
	    <div class="banner">
	    	<c:import url="../include/banner.jsp" />
	    </div>
	    <div class="contents">
	    	<form method="POST" action="./admin_validateamphi">
		    	<table>
		    		<tr>
			    		<td>AmphiId</td>
			    		<td><input type="hidden" name="amphiid" value="${amphi.amphiid}">${amphi.amphiid}</td>
		    		</tr>
		    		<tr>
			    		<td>Building</td>
			    		<td><input type="hidden" name="buildingid" value="${buildingId}">${amphi.buildingid}</td>
		    		</tr>
		    		<tr>
			    		<td>Name</td>
			    		<td><input type="text" name="name" value="${amphi.name}"></td>
		    		</tr>
		    		<tr>
			    		<td>Type</td>
			    		<td>
			    			<select name="type" title="Type">
			    				<option value="audio">Audio</option>
			    				<option value="video">Video</option>
			    			</select>
			    		</td>
		    		</tr>
		    		<tr>
			    		<td>IpAddress</td>
			    		<td><input type="text" name="ipaddress" value="${amphi.ipAddress}"></td>
		    		</tr>
		    		<tr>
			    		<td>Status</td>
			    		<td><input type="hidden" name="status" value="${amphi.status}">${amphi.status}</td>
		    		</tr>
		    	</table>
		    	<input type="hidden" name="action" value="${action}">
		    	<input type="submit" value="Validate">
	    	</form>
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
