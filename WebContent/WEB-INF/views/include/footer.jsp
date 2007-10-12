<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<p>
  	<fmt:message key="R&eacute;alisation du site par ULP Multim&eacute;dia - 2007"/> <br>
  	<c:url var="thick_legal" scope="page" value="./thick_legal">
		<c:param name="width" value="200"/>
		<c:param name="height" value="100"/>
	</c:url>
  	<a href="mailto:christophe.debeire@ulpmm.u-strasbg.fr"><fmt:message key="Contact"/></a> - <a href="${thick_legal}" title="<fmt:message key="Informations l&eacute;gales"/>" class="thickbox"><fmt:message key="Informations l&eacute;gales"/></a>
</p>