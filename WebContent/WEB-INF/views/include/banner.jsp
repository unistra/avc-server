<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<a class="bannerPageZone" href="<c:url value="./home" />"></a>
<a class="ulpmmZone" href="http://ulpmultimedia.u-strasbg.fr/" target="external"></a>
<a class="ulpZone" href="http://www-ulp.u-strasbg.fr/" target="external"></a>
<div class="live">
	<a class="liveZone" href="<c:url value="./live" />"><fmt:message key="Direct"/></a>
</div>
<div class="recorded">
	<a class="recordedZone" href="<c:url value="./recorded" />"><fmt:message key="Enregistr&eacute;"/></a>
</div>
<c:url var="thick_styles" scope="page" value="./thick_styles">
	<c:param name="width" value="500"/>
	<c:param name="height" value="500"/>
</c:url>
<c:url var="thick_languages" scope="page" value="./thick_languages">
	<c:param name="width" value="500"/>
	<c:param name="height" value="500"/>
</c:url>
<a href="<c:url value="${thick_styles}" />" title="<fmt:message key="S&eacute;l&eacute;ction du th&egrave;me"/>" class="thickbox">Style</a>
<a href="<c:url value="${thick_languages}" />" title="<fmt:message key="S&eacute;l&eacute;ction de la langue"/>" class="thickbox">Langue</a>
<a></a>