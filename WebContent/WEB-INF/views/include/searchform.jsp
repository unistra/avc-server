<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="org.ulpmm.univrav.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<div class="searchImage">
	<span class="searchText"><%=Messages._("Recherche", l)%></span>
</div>
<form method="post" action="./search">
	<div class="criteria">
		<fieldset>
			<legend><%=Messages._("Type de cours", l)%></legend>
			<c:if test="${resultPage == null || resultPage == 'recorded'}">
				<c:set var="audio" value="checked" />
				<c:set var="video" value="checked" />
			</c:if>
			<input type="checkbox" name="audio" value="checked" ${audio}><%=Messages._("Audio", l)%>
			&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="video" value="checked" ${video}><%=Messages._("Video", l)%>
		</fieldset>
		<!-- <br>
		<input type="checkbox" class="cb" name="tous">Tous les cours-->
		<br><br>
		<img src="../files/styles/${style}/img/arrowsearch.png" alt="arrowsearch"><input type="submit" class="submit" value=<%= "\"" + Messages._("Lancez la recherche", l) + "\""%>>
	</div>
	<div class="criteria">
		<label><%=Messages._("Enseignant", l)%></label>
		<select class="field" name="name" title="test">
			<option value="*"><%=Messages._("Tous", l)%></option>
			<c:forEach var="teacher" items="${teachers}">
				<c:if test="${nameSelected == teacher[0]}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${teacher[0]}" ${selected} }>${teacher[0]} ${teacher[1]}</option>
				<c:remove var="selected"/>
			</c:forEach>
		</select>
		<br>
		<label><%=Messages._("Formation", l)%></label>
		<select class="field" name="formation">
			<option value="*"><%=Messages._("Tous", l)%></option>
			<c:forEach var="formation" items="${formations}">
				<c:if test="${formationSelected == formation}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${formation}" ${selected} >${formation}</option>
				<c:remove var="selected"/>
			</c:forEach>
		</select>
		<br>
		<label><%=Messages._("Code ETAP", l)%></label>
		<input type="text" class="field">
		<br>
	</div>
	<hr>
</form>