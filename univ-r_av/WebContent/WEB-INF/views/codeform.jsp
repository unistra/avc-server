<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<div id="codeForm">
	<form method="post" action="./courseaccess">
		<br>
		<legend>Code d'acc&egrave;s</legend>
		<input type="text" name="code">
		<input type="hidden" name="id" value="${id}">
		<input type="hidden" name="type" value="${type}">
		<br><br>
		<input type="submit" value="Valider">
	</form>
</div>