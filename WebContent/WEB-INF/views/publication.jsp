<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="uploadPage"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/upload.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/upload_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
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
	    	
	    	<div class="divCenter">
	    		<p><fmt:message key="publiMsg"/></p>
	    	</div>
	    	
	    	<br>
	    	
	    	<!-- Choose if you have an account or not -->
	    	<div class="pubLinks">
	    		<div class="divPubCas">
	    			<a class="linkPubCas" href="./authentication_cas?returnPage=publication"><fmt:message key="udsAccount"/></a>
	    		</div>
	    		<div class="divPubFree">
	    			<a class="linkPubFree" href="./publication?publication_type=serverFree"><fmt:message key="free"/></a>
	    		</div>
	    	</div>
	    	
	    	<br>
	    	
	    	<div class="divCenter">
	    		<p class="${messagetype}"><c:out value="${message}" /></p>
	  	 	</div>
	    		    		    	
	    	<!-- VARS FOR THE FORM -->
	  	  	<c:choose>
				<c:when test="${publication_type != 'serverFree' and publication_type != 'serverCas'}">
					<c:set var="classField" value="txtDisabled" />
					<c:set var="disabledField" value="disabled" />
				</c:when>
				<c:otherwise>
					<c:set var="classField" value="field" />
					<c:set var="disabledField" value="" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${message!=null and visible==null}">
					<c:set var="checkedVisbleField" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedVisbleField" value="checked" />
				</c:otherwise>
			</c:choose>
		
			<!-- FORMULAIRE -->
	    	<form action="<c:url value="./publication_validatepublication"/>" method="POST">
	    		
	    		<input type="hidden" name="id" value="${publication_id}">
	    		<input type="hidden" name="mediapath" value="${publication_mediapath}">
	    		<input type="hidden" name="publication_type" value="${publication_type}">
	    								
				<table>
					 <c:if test="${publication_type == 'serverCas'}">
	   				 <tr class="odd">
						<td><fmt:message key="login"/> : </td>
						<c:choose>
							<c:when test="${user != null and publication_type == 'serverCas'}">
								<td><input type="text" name="login" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly" value="${user.login }" > </td>
							</c:when>
							<c:otherwise>
								<td><input type="text" name="login" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly"> </td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr class="even">
						<td>E-mail : </td>
						<c:choose>
							<c:when test="${user != null and publication_type == 'serverCas'}">
								<td><input type="text" name="email" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly" value="${user.email }" > </td>
							</c:when>
							<c:otherwise>
								<td><input type="text" name="email" class="txtDisabled" <c:out value="${disabledField}"/> readonly="readonly"> </td>
							</c:otherwise>
						</c:choose>
					</tr>
					</c:if>
					<tr class="odd">
						<td><fmt:message key="title"/><b class="boldStar">*</b> : </td>
						<td><input type="text" name="title" class="${classField}" <c:out value="${disabledField}"/> value="${title}"> </td>
					</tr>
					<tr class="even">
						<td><fmt:message key="description"/> : </td>
						<td><input type="text" name="description" class="${classField}" <c:out value="${disabledField}"/> value="${description}"></td>
					 </tr>
					<tr class="odd"> 
						<td><fmt:message key="name"/><b class="boldStar">*</b> : </td>
						<c:choose>
							<c:when test="${user != null and publication_type == 'serverCas' and message==null}">
								<td><input type="text" name="name" class="${classField}" <c:out value="${disabledField}"/> value="${user.lastname }" > </td>
							</c:when>
							<c:otherwise>
								<td><input type="text" name="name" class="${classField}" <c:out value="${disabledField}"/> value="${name}"> </td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr class="even">
						<td><fmt:message key="firstname"/> : </td>
						<c:choose>
							<c:when test="${user!=null and publication_type == 'serverCas' and message==null}">
								<td><input type="text" name="firstname" class="${classField}" <c:out value="${disabledField}"/> value="${user.firstname }" > </td>
							</c:when>
							<c:otherwise>
								<td><input type="text" name="firstname" class="${classField}" <c:out value="${disabledField}"/> value="${firstname }"> </td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr class="odd">
						<td><fmt:message key="ue"/> : </td>
						<td><input type="text" name="ue" class="${classField}" <c:out value="${disabledField}"/> value="${ue}"> </td>
					</tr>
					<tr class="even">
						<td><fmt:message key="Code d'acc&egrave;s"/> : </td>
						<td><input type="password" name="genre" class="${classField}" <c:out value="${disabledField}"/> value="${genre }"></td>
					</tr>				
					<tr class="odd">
				    	<td>Tags</td>
				  	  <td><input type="text" name="tags" class="${classField}" <c:out value="${disabledField}"/> value="${tags }"></td>
			    	</tr>
			    	<tr class="even">
					    <td>Visible</td>
					    <td><input type="checkbox" name="visible" <c:out value="${checkedVisbleField}"/> <c:out value="${disabledField}"/>></td>
			   		</tr>
			    	<tr>
			    		<td class="chpsObl"><b class="boldStar">*</b>: <fmt:message key="requiredField"/></td>
			    	</tr>
					<tr>
						<td><input type="submit" name="valider" onclick="javascript:document.getElementById('process').style.visibility='visible'" value="<fmt:message key="publier"/>" <c:out value="${disabledField}"/>> </td>
						<td><img id="process" src="../files/img/squaresCircle.gif" /></td>
					</tr>
				
				</table>
			</form>
			
    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
