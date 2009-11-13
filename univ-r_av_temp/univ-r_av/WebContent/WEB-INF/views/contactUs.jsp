<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="Aide"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/contactus.css">
	
	<!--[if IE]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
	<![endif]-->
	
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
	    <div class="main">
		    <div class="contents">
		    
		    	<div class="banner">
		    		<c:import url="include/banner.jsp" />
		    	</div>
		    			    	
		    	<div class="contactform">
		    		<form method="POST" action="<c:url value="${posturl}" />">
		    				    					    		
			    	<table>
			    		<tr class="tableheader">
							<th colspan="2" class="formTitle"><fmt:message key="contactUs"/></th>
						</tr>
			    		<tr class="odd">
				    		<td><fmt:message key="Sujet :"/></td>
				    		<td><input type="text" name="subject" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td><fmt:message key="Votre e-mail"/></td>
				    		<td><input type="text" name="useremail" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td><fmt:message key="yourmessage"/></td>
				    		<td><textarea name="yourmessage" class="textarea"></textarea></td>
			    		</tr>
			    				    				    	
			    	</table>
			    	<br>
			    	<input type="submit" class="valider" name="valider" value="<fmt:message key="send"/> ">
		    	</form>
		    	
		    	</div>
		    	
		    	
	    	</div>
		    	
		    <div class="footer">
		    	<c:import url="include/footer.jsp" />
		    </div>
	    </div>
  </body>
</html>
