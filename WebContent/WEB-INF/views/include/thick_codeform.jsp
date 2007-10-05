<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="org.ulpmm.univrav.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<div id="codeForm">
	<form method="post" action="./courseaccess">
		<br>
		<legend><%=Messages._("Code d'acc&egrave;s", l)%></legend>
		<input type="text" name="code">
		<input type="hidden" name="id" value="${id}">
		<input type="hidden" name="type" value="${type}">
		<br><br>
		<input type="submit" value="<%=Messages._("Valider", l)%>">
	</form>
</div>