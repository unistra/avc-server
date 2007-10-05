<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="org.ulpmm.univrav.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<p>
  	<%=Messages._("R&eacute;alisation du site par ULP Multim&eacute;dia - 2007", l)%> <br>
  	<a href="."><%=Messages._("Contact", l)%></a> - <a href="."><%=Messages._("Informations l&eacute;gales", l)%></a> - <a href="."><%=Messages._("Liens", l)%></a>
</p>