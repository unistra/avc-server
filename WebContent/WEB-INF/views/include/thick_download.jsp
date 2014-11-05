<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Downloads</title>
	</head>
	<body>
		<br>
			<a href="${clientLink}"><fmt:message key="clientLink"/></a>
			<p><fmt:message key="clientDescription"/></p>
			<br><br>
			<a href="${tracLink}"><fmt:message key="tracLink"/></a>
			<p><fmt:message key="tracDescription"/></p>
	</body>
</html>