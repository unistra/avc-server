<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
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
		<!--<script type="text/javascript" src="../files/js/accountrequest.js"></script>-->
			
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
	    		<p><fmt:message key="accountrequestmessage1"/> ${univName} <fmt:message key="accountrequestmessage1b"/></p>
	    		<p><fmt:message key="accountrequestmessage2"/></p>
	    		<p><fmt:message key="accountrequestmessage3"/></p>
	    	</div>
	    	
	    	<br />
		
			<div class="divCenter">
	    		<p class="error" id="error"><c:out value="${message}" /></p>
			</div>
		
			
			<!-- FORM -->
			<form action="<c:url value="./authentication_accountrequestvalid"/>" method="POST">
			
				<table>
					<tr class="odd">
						<td title="<fmt:message key="userlastname"/>"><fmt:message key="userlastname"/><b class="boldStar">*</b></td>
						<td><input type="text" name="lastname" class="field" value="${lastname}"></td>
					</tr>
					<tr class="even">
						<td title="<fmt:message key="userfirstname"/>"><fmt:message key="userfirstname"/><b class="boldStar">*</b></td>
						<td><input type="text" name="firstname" class="field" value="${firstname}"></td>
					</tr>
					<tr class="odd">
						<td title="<fmt:message key="ib_email_id"/>"><fmt:message key="Votre e-mail"/><b class="boldStar">*</b></td> <!-- Login = Email -->
						<td><input type="text" name="email" class="field" value="${email}"></td>
					</tr>
					<tr class="even">
						<td title="<fmt:message key="ib_password"/>"><fmt:message key="password"/><b class="boldStar">*</b></td>
						<td><input type="password" name="password" class="field"></td>
					</tr>
					<tr class="odd">
						<td title="<fmt:message key="ib_password"/>"><fmt:message key="repeatpassword"/><b class="boldStar">*</b></td>
						<td><input type="password" name="repeatpassword" class="field"></td>
					</tr>
					<tr class="even">
						<td title="<fmt:message key="establishment"/>"><fmt:message key="establishment"/><b class="boldStar">*</b></td>
						<td><input type="text" name="establishment" class="field" value="${establishment}"></td>
					</tr>
					<tr class="odd">
						<td title="<fmt:message key="post"/>"><fmt:message key="post"/><b class="boldStar">*</b></td>
						<td><input type="text" name="post" class="field" value="${post}"></td>
					</tr>
					<tr class="even">
						<td title="<fmt:message key="requestcomment"/>"><fmt:message key="requestcomment"/><b class="boldStar">*</b></td>
						<td><textarea name="comment" class="textarea">${comment}</textarea></td>
					</tr>
					<tr class="odd">
						<td title="captcha"><img src="../captcha" /></td>
						<td><input type="text" name="captcha_answer" class="field"></td>
					</tr>
					<tr>
			    		<td class="chpsObl"><b class="boldStar">*</b>: <fmt:message key="requiredField"/></td>
			    	</tr>
					<tr>
						<td><input type="submit" name="valider" value="<fmt:message key="Valider"/>"> </td>
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