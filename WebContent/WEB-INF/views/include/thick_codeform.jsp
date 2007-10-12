<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<div id="codeForm">
	<form method="post" action="./courseaccess">
		<br>
		<legend><fmt:message key="Code d'acc&egrave;s"/></legend>
		<input type="text" name="code">
		<input type="hidden" name="id" value="${id}">
		<input type="hidden" name="type" value="${type}">
		<br><br>
		<input type="submit" value="<fmt:message key="Valider"/>">
	</form>
</div>