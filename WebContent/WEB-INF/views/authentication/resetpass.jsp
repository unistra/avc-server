<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!doctype html>
<html>
	<head>

		<meta charset="utf-8">

		<title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="accountrequestform"/></title>
		
		<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/accountrequest.css">
	
		<!--[if IE]>
   			<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
		<![endif]-->
		<!--[if lte IE 6]>
			<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
			<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
		<![endif]-->

		<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
		<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>
		
			
		<!-- google analytics -->
		<c:import url="../include/google_analytics.jsp" />
	
	</head>
	
	<body>
		<div class="main">
			<div class="contents">
			<!-- all contents here -->
			<div class="banner">
				<c:import url="../include/banner.jsp" />
			</div>
			
			<div class="divCenter">
	    		<p><fmt:message key="forgotpassmessage1"/></p>
	    	</div>
	    	
	    	<br />
		
			<div class="divCenter">
	    		<p class="error" id="error"><c:out value="${message}" /></p>
			</div>
		
			
			<!-- FORM -->
			<form action="<c:url value="./authentication_resetpassvalid"/>" method="POST">
			
				<input type="hidden" name="hash" value="${hash}">
			
				<table>
			    		<tr class="odd">
				    		<td title="<fmt:message key="newPassword"/>"><fmt:message key="newPassword"/><b class="boldStar">*</b></td>
				    		<td><input type="password" name="newPassword" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td title="<fmt:message key="confirmNewPass"/>"><fmt:message key="confirmNewPass"/><b class="boldStar">*</b></td>
				    		<td><input type="password" name="confirmNewPass" class="field"></td>
			    		</tr>
					
					<tr>
			    		<td class="chpsObl"><b class="boldStar">*</b>: <fmt:message key="requiredField"/></td>
			    		<td></td>
			    	</tr>
					<tr>
						<td><input type="submit" name="valider" value="<fmt:message key="Valider"/>"> </td>
						<td></td>
					</tr>
					
			
				</table>
			
			
			</form>
		
		
		
			</div>
				
			<div class="footer">
	    		<c:import url="../include/footer.jsp" />
	    	</div>
		</div>
	</body>
	
</html>