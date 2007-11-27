<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Course access</title>
	</head>
	<body>
		<div id="codeForm">
			<form method="post" action="./courseaccess?id=${id}">
				<br>
				<legend><fmt:message key="Code d'acc&egrave;s"/></legend>
				<input type="text" name="code">
				<input type="hidden" name="type" value="${type}">
				<br><br>
				<input type="submit" value="<fmt:message key="Valider"/>">
			</form>
		</div>
	</body>
</html>