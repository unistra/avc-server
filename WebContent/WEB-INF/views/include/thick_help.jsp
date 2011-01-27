<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Help</title>
	</head>
	<body>
		<br>
		
		<a href="${helpLink}"><fmt:message key="helpLink"/></a>
		<br><br>
		<a href="${supportLink}"><fmt:message key="supportLink"/></a>
		<br><br>
		<a href="./contactUs"><fmt:message key="contactUs"/></a>
		
	</body>
</html>