<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Projet AudioVideoCours</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript" src="../code/js_videocours_.js"></script>
<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#666666" link="#666666" vlink="#666666" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<jsp:include page="./include/banniere_administration.jsp"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><table width="757" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="../_mm/zero.gif" width="10" height="30"></td>
        </tr>
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45" valign="top"><img src="../_mm/pyramide_magenta.jpg" width="45" height="50"></td>
                <td valign="bottom">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td class="ArialBlack18">Mise en ligne de cours
                        AudioCours</td>
                      </tr>
                      <tr>
                        <td height="6" background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                        <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                        <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                      </tr>
                    </table>
                  </td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
            	<!-- Début code spécifique à la page -->
	        	<% 
	        		// récupération de la date du jour pour remplissage auto du champ date
	        		Date d = new Date();
	        		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        		
	        		// Récupération des données envoyées par POST pour préremplir les champs
	        		request.setCharacterEncoding("UTF-8");
	        		String auteur = request.getParameter("auteur");
	        		String ue = request.getParameter("ue");
	        	%>
	        	<td>
	        	<br/>
		        	<form action="upload.jsp" method="post" enctype="multipart/form-data">
						<table>
						<tr>
							<td>Titre : </td>
							<td><input type="text" name="titre"> </td>
						</tr>
						<tr>
							<td>Description : </td>
							<td><input type="text" name="description"></td>
						 </tr>
						<tr>
							<td>Nom auteur : </td>
							<td><input type="text" name="auteur" value="<%= auteur != null ? auteur : "" %>"> </td>
						</tr>
						<tr>
							<td>Prénom auteur : </td>
							<td><input type="text" name="prenomAuteur"> </td>
						</tr>
						<tr>
							<td>Date (jj/mm/aaaa) : </td>
							<td><input type="text" name="date" value="<% out.print(sdf.format(d)); %>"> </td>
						</tr>
						<tr>
							<td>Unité d'enseignement : </td>
							<td><input type="text" name="formation" value="<%= ue != null ? ue : "" %>"> </td>
						</tr>
						<tr>
							<td>Genre : </td>
							<td><input type="password" name="genre"></td>
						</tr>
						<tr>
							<td>Fichier : </td>
							<td><input type="file" name="fichier" value="/"> </td>
						</tr>
						<tr>
							<td><input type="submit" name="valider" value="Envoyer le fichier"> </td>
						</tr>
						</table>
					</form>
				</td>			
				<!-- Fin code spécifique à la page -->  
            </table>
          </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
