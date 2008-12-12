<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<a class="bannerPageZone" href="<c:url value="./home" />"></a>
<a class="ulpmmZone" href="http://ulpmultimedia.u-strasbg.fr/" target="external"></a>
<a class="ulpZone" href="http://www-ulp.u-strasbg.fr/" target="external"></a>
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
	<c:param name="width" value="150"/>
	<c:param name="height" value="300"/>
</c:url>
<c:url var="thick_download" scope="page" value="./thick_download">
	<c:param name="width" value="370"/>
	<c:param name="height" value="220"/>
</c:url>
<div class="myspace">
	<a href="<c:url value="./myspace" />" title="<fmt:message key="myspace"/>"><fmt:message key="myspace"/></a>
</div>
<div class="styles">
	<a href="<c:out value="${thick_styles}" />" title="<fmt:message key="S&eacute;l&eacute;ction du th&egrave;me"/>" class="thickbox"><fmt:message key="style"/></a>
</div>
<div class="languages">
	<a href="<c:out value="${thick_languages}" />" title="<fmt:message key="S&eacute;l&eacute;ction de la langue"/>" class="thickbox"><fmt:message key="language"/></a>
</div>
<div class="help">
	<a href="<c:url value="./help" />" title="<fmt:message key="Utilisation d'Univ-R AV"/>" ><fmt:message key="Aide"/></a>
</div>
<div class="download">
	<a href="<c:out value="${thick_download}" />" title="<fmt:message key="T&eacute;l&eacute;chargements"/>" class="thickbox"><fmt:message key="T&eacute;l&eacute;chargements"/></a>
</div>