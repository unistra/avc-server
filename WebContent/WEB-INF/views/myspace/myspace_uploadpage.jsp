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
	    	
	    	<div class="divCenter">
	    		<p class="${messagetype}"><c:out value="${message}" /></p>
	  	 	</div>
	  	 	
	  	 	
	  	 	<c:choose>
				<c:when test="${message!=null and visible==null}">
					<c:set var="checkedVisbleField" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedVisbleField" value="checked" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${hd==null}">
					<c:set var="checkedHdField" value="" />
				</c:when>
				<c:otherwise>
					<c:set var="checkedHdField" value="checked" />
				</c:otherwise>
			</c:choose>
			
	    	
	    	<form action="<c:url value="./myspace_mediaupload"/>" method="post" enctype="multipart/form-data">
				<table>
				<tr class="odd"> 
					<td><fmt:message key="login"/> : </td>
					<td><input type="text" value="${user.login}" readonly="readonly" class="txtDisabled"> </td>
				</tr>
				<tr class="even"> 
					<td>E-mail : </td>
					<td><input type="text" name="email" class="txtDisabled" readonly="readonly" value="${user.email }" > </td>
				</tr>
				<tr class="odd">
					<td><fmt:message key="title"/><b class="boldStar">*</b> : </td>
					<td><input type="text" name="title" class="field" value="${title}"> </td>
				</tr>
				<tr class="even">
					<td><fmt:message key="description"/> : </td>
					<td><input type="text" name="description" class="field" value="${description}"></td>
				 </tr>
				<tr class="odd">
					<td><fmt:message key="name"/><b class="boldStar">*</b> : </td>
				 	<c:choose>
						<c:when test="${message==null}">
							<td><input type="text" name="name" value="${user.lastname}" class="field"> </td>
						</c:when>
						<c:otherwise>
							<td><input type="text" name="name" value="${name}" class="field"> </td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr class="even">
					<td><fmt:message key="firstname"/> : </td>
				 	<c:choose>
						<c:when test="${message==null}">
							<td><input type="text" name="firstname" value="${user.firstname}" class="field"> </td>
						</c:when>
						<c:otherwise>
							<td><input type="text" name="firstname" value="${firstname}" class="field"> </td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr class="odd">
					<td><fmt:message key="ue"/> : </td>
					<td><input type="text" name="formation" class="field" value="${ue}"> </td>
				</tr>
				<tr class="even">
					<td><fmt:message key="Code d'acc&egrave;s"/> : </td>
					<td><input type="password" name="genre" class="field" value="${genre}"></td>
				</tr>
				<tr class="odd">
				   	<td>Tags</td>
					<td><input type="text" name="tags" class="field" value="${tags}"></td>
			    </tr>
			    <tr class="even">
				    <td>Visible</td>
				    <td><input type="checkbox" name="visible" <c:out value="${checkedVisbleField}"/>></td>
			   	</tr>
				<tr class="odd">
					<td><fmt:message key="hd"/> : </td>
					<td><input type="checkbox" name="hd" <c:out value="${checkedHdField}"/>> <font class="chpsHd"><fmt:message key="uploadmessage4"/></font> </td>
				</tr>
				<tr class="even">
					<td><fmt:message key="file"/><b class="boldStar">*</b> : </td>
					<td><input type="file" name="media" class="field"> </td>
				</tr>
				<tr>
			    	<td class="chpsObl"><b class="boldStar">*</b>: <fmt:message key="requiredField"/></td>
			    </tr>
				<tr>
					<td><input type="submit" name="valider" onclick="javascript:document.getElementById('process').style.visibility='visible';" value="<fmt:message key="sendFile"/>"> </td>
					<td><img id="process" src="../files/img/squaresCircle.gif" /></td>
				</tr>
				<tr>
					<td><a href="<c:url value="${gobackurl}" />"><fmt:message key="Retour"/></a></td>
		    	</tr>
				
				</table>
			</form>
			   
			
			<br>
			<p class="message"><fmt:message key="uploadmessage1"/></p>
			<br>
			<p class="message"><fmt:message key="uploadmessage2"/></p>
			<br>
			<p class="message"><fmt:message key="uploadmessage3"/></p>
			<br>
			<p class="message"><fmt:message key="uploadmessage5"/></p>

    	</div>
	    	
	    <div class="footer">
	    	<c:import url="../include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
