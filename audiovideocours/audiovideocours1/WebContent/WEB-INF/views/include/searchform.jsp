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
			<input type="checkbox" class="checkbox" name="audio" value="checked" ${audio}><fmt:message key="Audio"/>
			&nbsp;&nbsp;&nbsp;
			<input type="checkbox" class="checkbox" name="video" value="checked" ${video}><fmt:message key="Video"/>
		</fieldset>
		<br><br>
		<img src="../files/styles/${sessionScope.style}/img/arrowsearch.png" alt="arrowsearch"><input type="submit" class="submit" value="<fmt:message key="Lancez la recherche"/>">
	</div>
	<div class="criteria">
		<label><fmt:message key="Auteur"/></label>
		<select class="field" name="fullname" title="<fmt:message key="Auteur"/>">
			<option value="*"><fmt:message key="Tous"/></option>
			<c:forEach var="teacher" items="${teachers}">
				<c:if test="${nameSelected == teacher}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${teacher}" ${selected}>${teacher}</option>
				<c:remove var="selected"/>
			</c:forEach>
		</select>
		<br>
		<label><fmt:message key="Formation"/></label>
		<select class="field" name="formation" title="<fmt:message key="Formation"/>">
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
		<label><fmt:message key="keyword"/></label>
		<input type="text" name="keyword" value="${keyword}" class="field">
		<br>
	</div>
	<hr/>
</form>