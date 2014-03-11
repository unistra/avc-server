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
	
	<label><fmt:message key="Type de cours"/>&nbsp;&nbsp;&nbsp;</label>
		<c:if test="${resultPage == null || resultPage == 'recorded'}">
			<c:set var="audio" value="checked" />
			<c:set var="video" value="checked" />
		</c:if>
		<input type="checkbox" class="checkbox" name="audio" value="checked" ${audio}><fmt:message key="Audio"/>
		&nbsp;&nbsp;&nbsp;
		<input type="checkbox" class="checkbox" name="video" value="checked" ${video}><fmt:message key="Video"/>
		<br><br>
	
		<label><fmt:message key="Auteur"/></label>
		<select class="field" name="fullname" title="<fmt:message key="Auteur"/>">
			<option value="*"><fmt:message key="Tous"/></option>
			<c:forEach var="teacher" items="${teachers}">
				<c:if test="${nameSelected == teacher}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${teacher}" title="${teacher}" ${selected}>${teacher}</option>
				<c:remove var="selected"/>
			</c:forEach>
		</select>
		<br>
		<label><fmt:message key="level"/></label>
		<select class="field" name="level" title="<fmt:message key="level"/>">
			<option value="*"><fmt:message key="Tous"/></option>
			<c:forEach var="levels" items="${levels}">
				<c:if test="${levelSelected == levels.code}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${levels.code}" title="${levels.name}" ${selected}>${levels.name}</option>
				<c:remove var="selected"/>
			</c:forEach>
		</select>
		<br>
		<label><fmt:message key="component"/></label>
		<select class="field" name="discipline" title="<fmt:message key="component"/>">
			<option value="*"><fmt:message key="Tous"/></option>
			<c:forEach var="discipline" items="${disciplines}">
				<c:if test="${discSelected == discipline.codecomp}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${discipline.codecomp}" title="${discipline.namecomp}" ${selected} >${discipline.namecomp}</option>
				<c:remove var="selected"/>
			</c:forEach>
		</select>
		<br>
		<label><fmt:message key="keyword"/></label>
		<input type="text" name="keyword" value="${keyword}" class="field">
		<br>
		<img src="../files/styles/${sessionScope.style}/img/arrowsearch.png" alt="arrowsearch"><input type="submit" class="submit" value="<fmt:message key="Lancez la recherche"/>">
	</div>
</form>
