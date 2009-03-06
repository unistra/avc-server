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
	<!--
	<form name="formMostPopularTags">
	<select size="4" class="field" name="selectMostPopularTags" onChange="LienMostPopularTags()">
			<c:forEach var="mostPopularTags" items="${mostPopularTags}">
				<c:if test="${nameSelected == mostPopularTags}">
					<c:set var="selected" value="selected" />
				</c:if>
				<option value="${mostPopularTags}" ${selected}>${mostPopularTags}</option>
				<c:remove var="selected"/>
			</c:forEach>
	</select>
	</form>
	-->
	
	
	<div id="tagcloud">
		<c:forEach var="mostPopularTags" items="${mostPopularTags}" varStatus="status">
		<!--		<a href="javascript:LienMostPopularTags('${mostPopularTags}');" id="tag${status.count}">${mostPopularTags}</a>&nbsp;&nbsp;
		-->
		${mostPopularTags}
		</c:forEach>
	</div>

	<br/>
	
	<label><fmt:message key="allTags"/></label>
	<form name="formAllTags">
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
	
</div>
