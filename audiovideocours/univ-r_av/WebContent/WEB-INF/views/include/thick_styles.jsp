<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Style selection</title>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/thick.css">
	</head>
	<body>
		<c:forEach var="style" varStatus="status" items="${styles}">
			<c:if test="${status.count % 2 == 1}">
				<c:out value='<div class="thick_line">' escapeXml="false" />
			</c:if>
			<c:url var="changestyle" scope="page" value="./changestyle">
				<c:param name="style" value="${style}"/>
			</c:url>
			<a href="${changestyle}"><img class="thick_img" src="../files/styles/${style}/img/screenshot.png" alt="${style}"></a>
			<c:if test="${status.count % 2 == 0 || status.last}">
				<c:out value='</div>' escapeXml="false" />
			</c:if>
		</c:forEach>
	</body>
</html>