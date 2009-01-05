<?xml version="1.0" encoding="iso8859-1"?>
<%@page import="org.ulpmm.univrav.dao.BddAccesForAs3;"%>

<%	//utf-8
	//Pour éviter la mise en cache par le navigateur de la réponse
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
	//#
	String idCours = request.getParameter("idcours");
%>

<info>
  <idcours><%= idCours %></idcours>
  <name><%= (new BddAccesForAs3().getXml("name",idCours)) %></name>
  <firstname><%= (new BddAccesForAs3().getXml("firstname",idCours)) %></firstname>
  <formation><%= (new BddAccesForAs3().getXml("formation",idCours)) %></formation>
  <atitle><%= (new BddAccesForAs3().getXml("atitle",idCours)) %></atitle>
  <videolink><%= (new BddAccesForAs3().getXml("videolink",idCours)) %></videolink>
  <description><%= (new BddAccesForAs3().getXml("description",idCours)) %></description>
  <date><%= (new BddAccesForAs3().getXml("date",idCours)) %></date>
  <type><%= (new BddAccesForAs3().getXml("type",idCours)) %></type>
  <duration><%= (new BddAccesForAs3().getXml("duration",idCours)) %></duration>
  <consultations><%= (new BddAccesForAs3().getXml("consultations",idCours)) %></consultations>
  <timecode0><%= (new BddAccesForAs3().getTimeCode("timecode0",idCours)) %></timecode0>
  <timecode><%= (new BddAccesForAs3().getTimeCode("timecode",idCours)) %></timecode>
  <mediafolder><%= (new BddAccesForAs3().getXml("mediafolder",idCours)) %></mediafolder>
</info>
