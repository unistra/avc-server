<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<a class="bannerPageZone" href="<c:url value="./home" />"></a>
<a class="udsZone" href="${univLink}" target="external"></a>

<div class="recorded">
	<a class="recordedZone" href="<c:url value="./recorded" />"><fmt:message key="Enregistr&eacute;"/></a>
</div>
<div class="live">
	<a class="liveZone" href="<c:url value="./live" />"><fmt:message key="Direct"/></a>
</div>
<c:url var="thick_styles" scope="page" value="./thick_styles">
	<c:param name="width" value="510"/>
	<c:param name="height" value="500"/>
</c:url>
<c:url var="thick_languages" scope="page" value="./thick_languages">
	<c:param name="width" value="250"/>
	<c:param name="height" value="150"/>
</c:url>
<c:url var="thick_download" scope="page" value="./thick_download">
	<c:param name="width" value="370"/>
	<c:param name="height" value="220"/>
</c:url>
<c:url var="thick_help" scope="page" value="./thick_help">
	<c:param name="width" value="250"/>
	<c:param name="height" value="150"/>
</c:url>
<c:url var="thick_myspace" scope="page" value="./thick_myspace">
	<c:param name="width" value="370"/>
	<c:param name="height" value="220"/>
</c:url>

<c:if test="${btnDeco}">
	<div class="logout">
		<a href="<c:url value="./logout" />" title="<fmt:message key="Logout"/>"><fmt:message key="Logout"/></a>
	</div>
</c:if>

<c:choose>
	<c:when test="${btnDeco}">
		<div class="logout">
			<a href="<c:url value="./logout" />" title="<fmt:message key="Logout"/>"><fmt:message key="Logout"/></a>
		</div>
		<div class="myspace">
			<a href="<c:url value="./myspace_home" />" title="<fmt:message key="myspace"/>"><fmt:message key="myspace"/></a>
		</div>

	</c:when>
	<c:otherwise>
		<div class="myspace">
			<a href="<c:out value="${thick_myspace}" />" title="<fmt:message key="myspace"/>" class="thickbox"><fmt:message key="myspace"/></a>
		</div>
	</c:otherwise>
</c:choose>


<div class="styles">
	<a href="<c:out value="${thick_styles}" />" title="<fmt:message key="S&eacute;l&eacute;ction du th&egrave;me"/>" class="thickbox"><fmt:message key="style"/></a>
</div>
<div class="languages">
	<a href="<c:out value="${thick_languages}" />" title="<fmt:message key="S&eacute;l&eacute;ction de la langue"/>" class="thickbox"><fmt:message key="language"/></a>
</div>
<div class="help">
	<a href="<c:out value="${thick_help}" />" title="<fmt:message key="Utilisation d'Univ-R AV"/>" class="thickbox"><fmt:message key="Aide"/></a>
</div>
<div class="download">
	<a href="<c:out value="${thick_download}" />" title="<fmt:message key="T&eacute;l&eacute;chargements"/>" class="thickbox"><fmt:message key="T&eacute;l&eacute;chargements"/></a>
</div>