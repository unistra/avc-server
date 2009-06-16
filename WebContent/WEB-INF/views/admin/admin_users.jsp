<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	    	
	    	<display:table id="users" name="users" requestURI="${viewurl}" class="displaytag">
	    		<display:column property="userid" title="N°" sortable="true"/>
	    		<display:column property="login" title="Login" sortable="true"/>
	    		<display:column property="email" title="E-mail" sortable="true"/>
	    		<display:column property="firstname" title="Firstname" sortable="true"/>
	    		<display:column property="lastname" title="Lastname" sortable="true"/>
	    		<display:column property="profile" title="Profile" sortable="true"/>
	    		<display:column property="establishment" title="Establishment" sortable="true"/>
	    		<display:column property="type" title="Type" sortable="true"/>
	    		<display:column title="Activate" sortable="true">
					<input type="checkbox" disabled="disabled" ${users.activate == true ? 'checked' : '' } />
				</display:column>
	    		<display:column>
					<a href="<c:url value="${editurl}?id=${users.userid}" />">Edit</a>
				</display:column>
				<display:column>
					<a href="javascript:confirmation('Delete the user named ${fn:replace(users.login,'\'',' ')}?','${deleteurl}?id=${users.userid}')">Delete</a>
				</display:column>
	    	</display:table>
	    	
	    	<br>
	    	<div class="add">
	    		<a href="<c:url value="./admin_adduser"/>">Add</a>	
	    	</div>
	    	<br>
	    	<p id="nbr">${number} users</p>
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
