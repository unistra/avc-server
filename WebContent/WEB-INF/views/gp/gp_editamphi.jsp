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
	    		    	
	    	<div class="editform">
		    	<form method="POST" action="<c:url value="./gp_validateamphi" />">
			    	<table>
			    		<tr class="odd">
				    		<td>AmphiId</td>
				    		<td><input type="hidden" name="amphiid" value="${amphi.amphiid}">${amphi.amphiid}</td>
			    		</tr>
			    		<tr class="even">
				    		<td>Building</td>
				    		<td><input type="text" name="buildingid" value="${buildingId}"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Name</td>
				    		<td><input type="text" name="name" value="${amphi.name}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>IpAddress</td>
				    		<td><input type="text" name="ipaddress" value="${amphi.ipAddress}" class="field"></td>
			    		</tr>
			    					    		
			    	</table>
			    	<br>
			    				    	
			    	<input type="hidden" name="restrictionuds" value="${amphi.restrictionuds == true ? 'checked' : ''}">
			    	<input type="hidden" name="status" value="${amphi.status}">
			    	<input type="hidden" name="gmapurl" value="${amphi.gmapurl}">
			    	<input type="hidden" name="version" value="${amphi.version}">
			    	<input type="hidden" name="oldAmphiip" value="${amphi.ipAddress}">
			    	<input type="hidden" name="action" value="${action}">
			    	<input type="submit" value="Validate">
			    	<br><br>
			    	<a href="<c:url value="./gp_amphis?buildingId=${buildingId}" />">Go back</a>
		    	</form>
		    </div>
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
