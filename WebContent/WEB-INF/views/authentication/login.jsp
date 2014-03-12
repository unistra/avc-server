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
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/login.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->
		
	<script type="text/javascript" src="../files/js/details.js"></script>
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

	
  </head>
  
  <body>
  
    <div class="main">
      <div class="contents">
	    
	    	<div class="banner">
	    		<c:import url="../include/banner.jsp" />
	    	</div>
	    	
	    	<div class="loginForm">
	    		<p><fmt:message key="loginmessage1"/></p>
	    		<br>
				<form method="POST" action="<c:url value="${posturl}" />">
			    	<table>
			    		<tr class="odd">
				    		<td title="<fmt:message key="ib_email"/>"><fmt:message key="Votre e-mail"/></td>
				    		<td><input type="text" name="login" class="field" value="${login }"></td>
			    		</tr>
			    		<tr class="even">
				    		<td title="<fmt:message key="password"/>"><fmt:message key="password"/></td>
				    		<td><input type="password" name="password" class="field"></td>
			    		</tr>
			    		
			    		<c:if test="${loginAttempt > 3}">
			    			<tr class="odd">
								<td title="captcha"><img src="../captcha" /></td>
								<td><input type="text" name="captcha_answer" class="field"></td>
							</tr>
			    		</c:if>
			    		
			    	</table>
			    	<ul>
			    		<li><a href="<c:url value="${createaccounturl}" />"><fmt:message key="loginmessage2"/></a></li>
			    		<li><a href="<c:url value="${forgotpassurl}" />"><fmt:message key="loginmessage3"/></a></li>
			    	</ul>
			    	
			    	<div class="message">
	    				<p class="${messagetype}"><c:out value="${message}" /></p>
	    			</div>
	    			
			    	<input type="submit" value="<fmt:message key="Valider"/>">
			    	
			    	<br /><br />
			    	
			    	<a href="<c:url value="${gobackurl}" />"><fmt:message key="Retour"/></a>
			    	
		    	</form>
	    	</div>
	    	 
    	</div>
    	
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
  
  
</html>
