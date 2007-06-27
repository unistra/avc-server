<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="paquet.Tools"/>
<jsp:useBean id="sqlbean" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String cible = request.getParameter("cible");
	String[] requete = request.getParameterValues("REQUETE"); // récupération des numéros des cours à modifier
%>
<html> 
<head>
<title>AudioVideoCours : Outils de masquage</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript" src="../code/js_videocours_.js"></script>
<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#666666" link="#666666" vlink="#666666" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<jsp:include page="include/banniere_administration.jsp"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><table width="757" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="../_mm/zero.gif" width="10" height="30"></td>
        </tr>
        <tr>
          <td>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45" valign="top"><img src="../_mm/pyramide_bleu.jpg" width="45" height="50"></td>
                <td valign="bottom">
                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23"></td>
                       <td>&nbsp;</td>
                       <td>&nbsp;</td>
                     </tr>
                     <tr>
                       <td class="ArialBlack18">Outils de Masquage</td>
                     </tr>
                     <tr>
                       <td height="6" background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                       <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                       <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                     </tr>
                   </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          	<br/>
              <tr>
                <td width="45"><img src="../_mm/zero.gif" width="45" height="10"></td>
                <td class="Arial12bold">
                	<% if( cible.equals("visible") ) { %>
                		<a href="liste.jsp?cible=visible" class="Arial14boldNoir">Tous les cours visibles</a>
                	<% } else { %>
                		<a href="liste.jsp?cible=visible" class="Arial12bold">Tous les cours visibles</a>
                	<% } %>
                    <strong>&nbsp;&nbsp;</strong>|&nbsp;&nbsp;
                	<% if( cible.equals("shadows") ) { %>
                		<a href="liste.jsp?cible=shadows" class="Arial14boldNoir">Tous les cours masqu&eacute;s</a>
                	<% } else { %>
                		<a href="liste.jsp?cible=shadows" class="Arial12bold">Tous les cours masqu&eacute;s</a>
                	<% } %>
                    <strong>&nbsp;&nbsp;</strong>|&nbsp;&nbsp;
                	<% if( cible.equals("all") ) { %>
                		<a href="liste.jsp?cible=all" class="Arial14boldNoir">Tous les cours</a>
                	<% } else { %>
                		<a href="liste.jsp?cible=all" class="Arial12bold">Tous les cours</a>
                	<% } %>
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td class="Arial12">&nbsp;</td>
              </tr>
          </td>
        </tr>
    </td>
  </tr>
</table>
<%
	if( requete != null ) {
		String AudioCoursIds = "";
		String query = "";
		
		sqlbean.setWriter(out);
		sqlbean.connect();
		
		if( ! cible.equals("all") ) {
			
			/* Mise en forme de la liste des numéros de cours dans une chaine de caractère pour la requête */
			for( int i = 0 ; i < requete.length ; i++ ) {
				AudioCoursIds += requete[i];
				if( i != requete.length -1 )
					AudioCoursIds += ", ";
			}
			
			/* Requêtes de modification des cours */
			if( cible.equals("visible") ) {
				query = "UPDATE \"AudioCours\" SET \"Visible\" = 0 WHERE \"AudioCoursId\" in (" + AudioCoursIds + ")";
			}
			else if( cible.equals("shadows") ) {
				query = "UPDATE \"AudioCours\" SET \"Visible\" = 1 WHERE \"AudioCoursId\" in (" + AudioCoursIds + ")";
			}
			sqlbean.update(query);
			out.println("Les audiocours " + AudioCoursIds + " ont été traités");
		}
		else {
			// cible = all, les cours cochés doivent changer de statut visible/masqué
			
			for( int i = 0 ; i < requete.length ; i++ ) {
				
				/* Recherche si le cours est visible ou non */
				query = "SELECT \"Visible\" FROM \"AudioCours\" WHERE \"AudioCoursId\" = ?";
				sqlbean.pquery(query, requete[i]);
				
				String estVisible = "";
				
				if( sqlbean.next() )
					estVisible = (String) sqlbean.getColumn("Visible"); 
				
				/* Changement de statut du cours */
				if( estVisible.equals("1") ) {
					query = "UPDATE \"AudioCours\" SET \"Visible\" = 0 WHERE \"AudioCoursId\" = ?";
				}
				else {
					query = "UPDATE \"AudioCours\" SET \"Visible\" = 1 WHERE \"AudioCoursId\" = ?";
				}
				sqlbean.pupdate(query,requete[i]);
				out.println("L'audiocours " + requete[i] + " a été traité.<br/>");
			}
		}
		
		sqlbean.disconnect();
		Tools.createRss(); //lancement de la création des RSS
	}
	else {
		out.println("Pas de cours à modifier !");
	}
%>

<br><a href="liste.jsp?cible=<%= cible %>">Retour</a>

</body>
</html>
