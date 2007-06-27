<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="paquet.SqlBean"/>
<jsp:directive.page import="paquet.Tools"/>
<%
		SqlBean sqlbean = new SqlBean();
		
		/* Recherche de toutes les formations */
		sqlbean.connect();
		sqlbean.setWriter(out);
		String query_formations = "Select distinct \"Rating\" from \"AudioCours\" where \"Visible\"=1 AND \"Genre\" IS NULL AND \"Rating\" IS NOT NULL";
		sqlbean.query(query_formations);
		while( sqlbean.next() ) {
			String formation = (String) sqlbean.getColumn("Rating");	//Formation
			
			/* Affichage d'une métadonnée RSS pour chaque formation */
			String nomFichier = Tools.cleanString(Tools.cleanFileName(formation)) + ".xml";
			%>
			<link rel="alternate" type="application/rss+xml" title="Liste des cours de la formation <%= formation %>" href="../rss/rssFormation/<%= nomFichier %>"/>
			<%
		}
		sqlbean.disconnect();
%>