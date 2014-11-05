<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Myspace</title>
	</head>
	<body>
		<br>
			<a href="./myspace_home"><fmt:message key="udsAccount"/> ${univAcronym}</a>
			<p><fmt:message key="authCasDescription"/> ${univName}</p>
						
			<br><br>
			<a href="./authentication_local"><fmt:message key="authLocalLink"/></a>
			<p><fmt:message key="authLocalDescription"/></p>
	</body>
</html>