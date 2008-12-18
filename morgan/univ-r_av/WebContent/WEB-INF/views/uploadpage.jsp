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
	    		<c:import url="include/banner.jsp" />
	    	</div>
	    	
	    	<form action="<c:url value="./mediaupload"/>" method="post" enctype="multipart/form-data">
				<table>
				<tr>
					<td><fmt:message key="title"/> : </td>
					<td><input type="text" name="title"> </td>
				</tr>
				<tr>
					<td><fmt:message key="description"/> : </td>
					<td><input type="text" name="description"></td>
				 </tr>
				<tr>
					<td><fmt:message key="name"/> : </td>
					<!--<td><input type="text" value="${name}" readonly="readonly"> </td>-->
					<td><input type="text" name="name"> </td>
				</tr>
				<tr>
					<td><fmt:message key="firstname"/> : </td>
					<!--<td><input type="text" value="${firstname}" readonly="readonly"> </td>-->
					<td><input type="text" name="firstname"> </td>
				</tr>
				<tr>
					<td><fmt:message key="ue"/> : </td>
					<td><input type="text" name="formation"> </td>
				</tr>
				<tr>
					<td><fmt:message key="password"/> : </td>
					<td><input type="password" name="genre"></td>
				</tr>
				<tr>
					<td><fmt:message key="hd"/> : </td>
					<td><input type="checkbox" name="hd"> </td>
				</tr>
				<tr>
					<td><fmt:message key="file"/> : </td>
					<td><input type="file" name="media"> </td>
				</tr>
				<tr>
					<td><input type="submit" name="valider" onclick="javascript:document.getElementById('process').style.visibility='visible'" value="<fmt:message key="sendFile"/>"> </td>
					<td><img id="process" src="../files/img/squaresCircle.gif" /></td>
				</tr>
				<tr>
					<td><a href="<c:url value="${gobackurl}" />"><fmt:message key="Retour"/></a></td>
		    	</tr>
				
				</table>
			</form>
			   
			
			<br>
			<p><fmt:message key="uploadmessage1"/></p>
			<br>
			<p><fmt:message key="uploadmessage2"/></p>
			<br>
			<p><fmt:message key="uploadmessage3"/></p>

    	</div>
	    	
	    <div class="footer">
	    	<c:import url="include/footer.jsp" />
	    </div>
    </div>
  </body>
</html>
