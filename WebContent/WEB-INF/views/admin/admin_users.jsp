<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pt" uri="/WEB-INF/pagination-taglib.tld" %>

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
	    	
	    	<div class="divCenter">	    	
	    		<form name="input" action="./admin_users" method="post">
					Login: <input type="text" name="login" value="${login}" />
					<input type="submit" value="Submit" />
				</form>
			</div>
			
			<div class="divCenter">
	    		<p class="message" id="message"><c:out value="${message}" /></p>
			</div>
			
			<br />
			
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
					<a href="<c:url value="./admin_useractivate?userid=${users.userid}&activate=true" />">On</a> /
					<a href="<c:url value="./admin_useractivate?userid=${users.userid}&activate=false" />">Off</a>
				</display:column>
				<display:column property="etp" title="Etp" sortable="true"/>
				<display:column property="institute" title="Institute" sortable="true"/>
				<display:column title="Rss" sortable="false">
					<a href="../rss/lgn_${fn:replace(users.login,'.','_')}.xml">Link</a>
				</display:column>
	    		<display:column>
					<a href="<c:url value="${editurl}?id=${users.userid}" />">Edit</a>
				</display:column>
				<display:column>
					<a href="javascript:confirmation('Delete the user named ${fn:replace(users.login,'\'',' ')}?','${deleteurl}?id=${users.userid}')">Delete</a>
				</display:column>
	    	</display:table>
	    	
	    	<c:if test="${items > 1}">
	    	
	    		<div class="pagination">
	    			<pt:PaginationTag currentPage="${page}" itemsNumber="${items}" numberPerPage="${number}" resultPageName="${resultPage}" />
				</div>
	    	</c:if>
	    	
	    	<br>
	    	<div class="add">
	    		<a href="<c:url value="./admin_adduser"/>">Add</a>	
	    	</div>
	    	<br>
	    	<p id="nbr">${items} users</p>
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
