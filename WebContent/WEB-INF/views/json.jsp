<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/></title>
	<script type="text/javascript" src="../files/js/xml2json.js"></script>		
  </head>
  
	<body>
		<textarea id="xmlstring" style="display:none;">${xmlstring}</textarea>
  
		<div id="jsonresult"></div>
  		
		<script type="text/javascript">
			document.getElementById("jsonresult").innerHTML = xml2json.parser(document.getElementById("xmlstring").value,'','html');
		</script>
   
  </body>
</html>
