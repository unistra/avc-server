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
	    	<div class="links">
		    	<c:import url="./links.jsp" />
	    	</div>
	    	
	    	<div class="editform">
		    	<form method="POST" action="<c:url value="./admin_validatediscipline" />">
			    	<table>
			    		<tr class="odd">
				    		<td>DisciplineId</td>
				    		<td><input type="hidden" name="disciplineid" value="${discipline.disciplineid}">${discipline.disciplineid}</td>
			    		</tr>
			    		<tr class="even">
				    		<td>Component code</td>
				    		<td><input type="text" name="codecomp" value="${discipline.codecomp}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Component name</td>
				    		<td><input type="text" name="namecomp" value="${discipline.namecomp}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Domain code</td>
				    		<td><input type="text" name="codedom" value="${discipline.codedom}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Domain name</td>
				    		<td><input type="text" name="namedom" value="${discipline.namedom}" class="field"></td>
			    		</tr>
			    	</table>
			    	<br>
			    	<input type="hidden" name="action" value="${action}">
			    	<input type="submit" value="Validate">
			    	<br><br>
			    	<a href="<c:url value="./admin_disciplines" />">Go back</a>
		    	</form>
		    </div>
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
