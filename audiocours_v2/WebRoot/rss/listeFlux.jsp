<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="paquet.SqlBean"/>
<jsp:directive.page import="paquet.Tools"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>Projet AudioVideoCours</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	
	<script language="JavaScript" src="../code/js_videocours_.js"></script>
	<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" style="color: rgb(102, 102, 102); background-color: rgb(255, 255, 255);" alink="#000000" link="#666666" marginheight="0" marginwidth="0" vlink="#666666">
<body leftmargin="0" topmargin="0" style="color: rgb(102, 102, 102); background-color: rgb(255, 255, 255);" alink="#000000" link="#666666" marginheight="0" marginwidth="0" vlink="#666666">

<jsp:include page="../includes/banniere.jsp"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
    	<table width="757" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="../_mm/zero.gif" width="10" height="30"></td>
        </tr>
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45" valign="top"><img src="../_mm/pyramide_bleu.jpg" width="45" height="50"></td>
                <td valign="bottom">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="ArialBlack18">Liste des flux RSS</td>
                    </tr>
                    <tr>
                      <td height="6" background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="712" height="6"></td>
                    </tr>
                  </table>
                  </td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td valign="top">
            <br><br> Flux RSS général : <br><br>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
              	<td><a href="../rss/liste_des_cours.xml">Liste de tous les cours</a></td>
              	<td align="right"><a href="../rss/liste_des_cours.xml"><img src="../_mm/Rssicon.gif"></a></td>
              </tr>
            </table>
            
            <br><br> Flux Rss des cours par enseignant : <br><br>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">            
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
					
					/* Affichage d'un lien vers les flux de chaque enseignant */
					String nomFichier = Tools.cleanString(Tools.cleanFileName(name + (! firstname.equals("") ? "_" + firstname : ""))) + ".xml";
					%>
					<tr>
              			<td><a href="../rss/rssEnseignant/<%= nomFichier %>">Liste des cours de <%= name + " " + firstname %></a></td>
              			<td align="right"><a href="../rss/rssEnseignant/<%= nomFichier %>"><img src="../_mm/Rssicon.gif"></a></td>
              		</tr>
					<%
				}
				sqlbean.disconnect();
			  %>
            </table>
            
            <br><br> Flux Rss des cours par formation : <br><br>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <%	
					/* Recherche de toutes les formations */
					sqlbean.connect();
					String query_formations = "Select distinct \"Rating\" from \"AudioCours\" where \"Visible\"=1 AND \"Genre\" IS NULL AND \"Rating\" IS NOT NULL";
					sqlbean.query(query_formations);
					while( sqlbean.next() ) {
						String formation = (String) sqlbean.getColumn("Rating");	//Formation
						
						/* Affichage d'une métadonnée RSS pour chaque formation */
						String nomFichier = Tools.cleanString(Tools.cleanFileName(formation)) + ".xml";
						%>
						<tr>
			            	<td><a href="../rss/rssFormation/<%= nomFichier %>">Liste des cours de la formation <%= formation %></a></td>
			            	<td align="right"><a href="../rss/rssFormation/<%= nomFichier %>"><img src="../_mm/Rssicon.gif"></a></td>
			            </tr>
						<%
					}
					sqlbean.disconnect();
			  %>
            </table>
            
          </td>
        </tr>
	</table>	
	
	</td>
</tr>
</table>

</body>
</html>