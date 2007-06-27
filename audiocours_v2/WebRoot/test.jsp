<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.IOException"/>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="java.io.InputStream"/>
<jsp:directive.page import="java.io.BufferedInputStream"/>
<jsp:directive.page import="java.io.BufferedReader"/>
<jsp:directive.page import="java.io.InputStreamReader"/>
<jsp:directive.page import="java.awt.Dimension"/>
<jsp:directive.page import="paquet.Tools"/>
<jsp:directive.page import="paquet.SqlBean"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <script language="javascript">
			document.writeln("<title>Page jsp de test</title>");
			</script>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   	<form method="post" action="../audiocours_v2/servlet/LiveState">
   		<input type="text" name="recordingPlace" value="130.79.188.11"></input>
   		<input type="text" name="status" value="begin"></input>
   		<input type="submit" value="OK"></input>
   	</form>
   	<%= Tools.getNomAmphi("130.79.149.15") %>
  </body>
</html>
