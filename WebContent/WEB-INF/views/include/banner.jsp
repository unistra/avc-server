<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="org.ulpmm.univrav.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<a class="bannerPageZone" href="<c:url value="./home" />"></a>
<a class="ulpmmZone" href="http://ulpmultimedia.u-strasbg.fr/" target="external"></a>
<a class="ulpZone" href="http://www-ulp.u-strasbg.fr/" target="external"></a>
<div class="live">
	<a class="liveZone" href="<c:url value="./live" />"><%=Messages._("Direct", l)%></a>
</div>
<div class="recorded">
	<a class="recordedZone" href="<c:url value="./recorded" />"><%=Messages._("Enregistr&eacute;", l)%></a>
</div>
<c:url var="thick_styles" scope="page" value="./thick_styles">
	<c:param name="width" value="500"/>
	<c:param name="height" value="500"/>
</c:url>
<a href="<c:url value="${thick_styles}" />" title="<%=Messages._("S&eacute;l&eacute;ction du th&egrave;me", l)%>" class="thickbox">Style</a>
<a></a>