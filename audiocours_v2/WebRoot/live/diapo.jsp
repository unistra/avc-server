<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	request.setCharacterEncoding("UTF-8");
	String ip = request.getParameter("ip");
	String liveOn = request.getParameter("liveOn");
		
	/* Recherche de la diapo dans le répertoire live avec l'adresse IP comme nom (avec les '.' remplacés par '_') */
	String url;
	if( liveOn.equals("true"))
		url = "../../live/" + ip.replace('.','_') + ".jpg";
	else
		url = "../../live/default.jpg";
%>
<html>
  <head>
    <title>Diapositive live</title>
     <% if( liveOn.equals("true")) { %>
     <meta http-equiv="Refresh" content="10; URL=diapo.jsp?ip=<%= ip %>&liveOn=<%= liveOn %>">
     <% } %>
  </head>
  
  <body>
  	<a href="<%= url %>" target="external"><img src="<%= url %>" width="640" height="480"></a>
  </body>
</html>
