<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <title>Slide Frame</title>
     <meta http-equiv="Refresh" content="10; URL=./iframe_liveslide?ip=${ip}&amp;url=${url}">
     <link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/liveslide.css">
  </head>
  <body style="padding:0">
  	<a href="${url}" target="external"><img src="${url}" width="966" height="724"></a>
  </body>
</html>
