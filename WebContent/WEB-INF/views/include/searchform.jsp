<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<div class="searchImage">
	<span class="searchText"><fmt:message key="Recherche"/></span>
</div>
<form method="post" action="<c:url value="./search" />">
	<div class="criteria">
		<fieldset>
			<legend><fmt:message key="Type de cours"/></legend>
			<c:if test="${resultPage == null || resultPage == 'recorded'}">
				<c:set var="audio" value="checked" />
				<c:set var="video" value="checked" />
			</c:if>
			<input type="checkbox" name="audio" value="checked" ${audio}><fmt:message key="Audio"/>
			&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="video" value="checked" ${video}><fmt:message key="Video"/>
		</fieldset>
		<!-- <br>
		<input type="checkbox" class="cb" name="tous">Tous les cours-->
		<br><br>
		<img src="../files/styles/${sessionScope.style}/img/arrowsearch.png" alt="arrowsearch"><input type="submit" class="submit" value="<fmt:message key="Lancez la recherche"/>">
	</div>
	<div class="criteria">
		<label><fmt:message key="Enseignant"/></label>
		<select class="field" name="name" title="test">
			<option value="*"><fmt:message key="Tous"/></option>
			<c:forEach var="teacher" items="${teachers}">
				<c:if test="${nameSelected == teacher[0]}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${teacher[0]}" ${selected}>${teacher[0]} ${teacher[1]}</option>
				<c:remove var="selected"/>
			</c:forEach>
		</select>
		<br>
		<label><fmt:message key="Formation"/></label>
		<select class="field" name="formation">
			<option value="*"><fmt:message key="Tous"/></option>
			<c:forEach var="formation" items="${formations}">
				<c:if test="${formationSelected == formation}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${formation}" ${selected} >${formation}</option>
				<c:remove var="selected"/>
			</c:forEach>
		</select>
		<br>
		<label><fmt:message key="Code ETAP"/></label>
		<input type="text" class="field">
		<br>
	</div>
	<hr>
</form>