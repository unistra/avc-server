<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<script type="text/javascript" src="../files/js/tags.js"></script>

<div class="tagImage">
	<span class="searchText">Tags</span>
</div>

<div class="criteria">

	<label><fmt:message key="popularTags"/></label>
	<br/>
	
	<div id="tagcloud">
		<c:forEach var="mostPopularTags" items="${mostPopularTags}" varStatus="status">
		${mostPopularTags}
		</c:forEach>
	</div>

	
	<form name="formAllTags">
	<label><fmt:message key="allTags"/></label>
	<select class ="field" name="selectAllTags" onChange="LienAllTags()">
	<option value=" "> </option>
	<c:forEach var="allTags" items="${allTags}">
				<c:if test="${nameSelected == allTags}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${allTags}" ${selected}>${allTags}</option>
				<c:remove var="selected"/>
			</c:forEach>
	</select>
	</form>
	
	<form name="formCumulTags">
	<label><fmt:message key="cumulTags"/></label>
	<select class ="field" name="selectCumulTags" onChange="LienCumulTags()">
	<option value=" "> </option>
	<c:forEach var="allTags" items="${allTags}">
				<c:if test="${nameSelected == allTags}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${allTags}" ${selected}>+ ${allTags}</option>
				<c:remove var="selected"/>
			</c:forEach>
	</select>
	</form>
	
</div>
