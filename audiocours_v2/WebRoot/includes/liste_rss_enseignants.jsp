<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="paquet.SqlBean"/>
<jsp:directive.page import="paquet.Tools"/>
<%
		SqlBean sqlbean = new SqlBean();
		
		/* Recherche de tous les enseignants */
		sqlbean.connect();
		sqlbean.setWriter(out);
		String query_enseignants = "Select distinct \"Who\",\"FirstName\" from \"AudioCours\" where \"Visible\"=1 AND \"Genre\" IS NULL";
		sqlbean.query(query_enseignants);
		while( sqlbean.next() ) {
			String name = (String) sqlbean.getColumn("Who");	//Nom de l'enseignant du cours
			String firstname = (String) sqlbean.getColumn("FirstName"); //Prénom de l'enseignant du cours
			
			if( firstname == null)
				firstname = "";
			
			/* Affichage d'une métadonnée RSS pour chaque enseignant */
			String nomFichier = Tools.cleanString(Tools.cleanFileName(name + (! firstname.equals("") ? "_" + firstname : ""))) + ".xml";
			%>
			<link rel="alternate" type="application/rss+xml" title="Liste des cours de <%= name + " " + firstname %>" href="../rss/rssEnseignant/<%= nomFichier %>"/>
			<%
		}
		sqlbean.disconnect();
%>