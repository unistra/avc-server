<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Language selection</title>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/thick.css">
	</head>
	<body>
		<br>
		<c:forEach var="language" varStatus="status" items="${languages}">
			<c:url var="changelanguage" scope="page" value="./changelanguage">
				<c:param name="language" value="${language}"/>
			</c:url>
			<a href="${changelanguage}"> ${fn:toUpperCase(language)}</a> <br><br>
		</c:forEach>
	</body>
</html>