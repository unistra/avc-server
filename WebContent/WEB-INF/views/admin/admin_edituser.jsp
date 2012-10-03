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
	    	
	    	<div class="divCenter">
	    		<p>For local user, login field must be an email adress (the same as the email field).</p>
	    	</div>
	    	
	    	<br />
	    	
	    	<div class="editform">
		    	<form method="POST" action="<c:url value="./admin_validateuser" />">
			    	<table>
			    		<tr class="odd">
				    		<td>UserId</td>
				    		<td><input type="hidden" name="userid" value="${user.userid}">${user.userid}</td>
			    		</tr>
			    		<tr class="even">
				    		<td>Login<b class="boldStar">*</b></td>
				    		<td><input type="text" name="login" value="${user.login}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Email<b class="boldStar">*</b></td>
				    		<td><input type="text" name="email" value="${user.email}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Firstname<b class="boldStar">*</b></td>
				    		<td><input type="text" name="firstname" value="${user.firstname}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Lastname<b class="boldStar">*</b></td>
				    		<td><input type="text" name="lastname" value="${user.lastname}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Profile</td>
				    		<td><input type="text" name="profile" value="${user.profile}" class="field"></td>
			    		</tr>
			    		<tr class="odd">
				    		<td>Establishment</td>
				    		<td><input type="text" name="establishment" value="${user.establishment}" class="field"></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Type<b class="boldStar">*</b></td>
				    		<td>			    			
				    			<select name="type">
								<c:forEach var="t" items="${types}">
									<c:if test="${user.type == t}">
										<c:set var="selected" value="selected" />
									</c:if>
									<option value="${t}" title="${t}" ${selected}>${t}</option>
									<c:remove var="selected"/>
								</c:forEach>
							</select>
				    		</td>
				    		
			    		</tr>
			    		<tr class="odd">
				    		<td>Activate</td>
				    		<td><input type="checkbox" name="activate" ${user.activate == true ? 'checked' : ''} ></td>
			    		</tr>
			    		<tr class="even">
				    		<td>Etp</td>
				    		<td><input type="text" name="etp" value="${user.etp}" class="field"></td>
			    		</tr>
			    		<tr>
			    			<td class="chpsObl"><b class="boldStar">*</b>: required fields</td>
			    		</tr>
			    	</table>
			    	<br>
			    	<input type="hidden" name="action" value="${action}">
			    	<input type="submit" value="Validate">
			    	<br><br>
			    	<a href="<c:url value="./admin_users" />">Go back</a>
		    	</form>
		    </div>
	    </div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
