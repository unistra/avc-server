<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/thick_styles.css">
	</head>
	<body>
		<c:forEach var="language" varStatus="status" items="${languages}">
			<c:if test="${status.count % 2 == 1}">
				<c:out value='<div class="line">' escapeXml="false" />
			</c:if>
			<c:url var="changelanguage" scope="page" value="./changelanguage">
				<c:param name="language" value="${language}"/>
			</c:url>
			<a href="${changelanguage}"><!--<img src="../files/styles/${style}/img/screenshot.png" alt="${style}">--> ${language}</a>
			<c:if test="${status.count % 2 == 0}">
				<c:out value='</div>' escapeXml="false" />
			</c:if>
		</c:forEach>
	</body>
</html>