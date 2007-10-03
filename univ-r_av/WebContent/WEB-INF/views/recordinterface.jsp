<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="org.ulpmm.univrav.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><%=Messages._("Visualisation du cours", l)%></title>

	<link rel="stylesheet" type="text/css" href="../files/styles/${style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${style}/css/visualization.css">
	
	<!--[if IE]>
   		<link rel="stylesheet" type="text/css" href="../files/styles/${style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${style}/css/visualization_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/js/recordinterface.js"></script>
	<script type="text/javascript">
		var timecodes = ${slides};
	</script>

  </head>
  
  <body>
    <div class="main">
	    <div class="contents">
	    	
	    	<div class="amphitheatre">${building} | ${amphi}</div>
	    	
	    	<a class="closeButton" href="."><%=Messages._("Fermer", l)%> <img src="../files/styles/${style}/img/close.png"></a>
	    	
			<div class="smil">
				<object name="video" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="970" height="550">
					<param name="src" value="${courseurl}">
					<param name="controls" value="ImageWindow">
					<param name="console" value="console">
					<param name="autostart" value="true">
					<param name="wmode" value="transparent">
					<embed type="audio/x-pn-realaudio-plugin" id="video" src="${courseurl}" width="970" height="550" align="middle" controls="ImageWindow" console="console" autostart="true" wmode="transparent">
				</object>
				<br>
				<object classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="970" height="40">
					<param name="controls" value="ControlPanel">
					<param name="console" value="console">
					<param name="wmode" value="transparent">
					<embed type="audio/x-pn-realaudio-plugin" width="970" height="40" align="middle" controls="ControlPanel" console="console" wmode="transparent">
				</object>
			</div>
			
			<br>
			
			<div id="thumbLine">
			</div>
			
			<div id="videoLine">
			</div>

			<br>
			
			<div id="pagination">
				<a class="leftPagination" href="javascript:previousPage()">&lt;&lt;</a>
				<div class="rightPagination">
				<%=Messages._("Page", l)%> <span id="pageNumber"></span>
				<a href="javascript:nextPage()">&gt;&gt;</a>
				| <%=Messages._("diapositive", l)%> <span id="slideNumber"></span>
				</div>
			</div>

    	</div>
	    	
	    <div class="footer">
	    	<p>
		    	<%=Messages._("R&eacute;alisation du site par ULP Multim&eacute;dia - 2007", l)%> <br>
		    	<a href="."><%=Messages._("Contact", l)%></a> - <a href="."><%=Messages._("Informations l&eacute;gales", l)%></a> - <a href="."><%=Messages._("Liens", l)%></a>
	    	</p>
	    </div>
    </div>
  </body>
</html>
