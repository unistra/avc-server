<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Downloads</title>
	</head>
	<body>
		<br>
			<a href="http://ulpmm-projets.u-strasbg.fr/projets/videocours/wiki/DownloadAudiovideocours"><fmt:message key="clientLink"/></a>
			<p><fmt:message key="clientDescription"/></p>
			<br><br>
			<a href="http://ulpmm-projets.u-strasbg.fr/projets/videocours"><fmt:message key="tracLink"/></a>
			<p><fmt:message key="tracDescription"/></p>
	</body>
</html>