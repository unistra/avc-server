<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:useBean id="sqlbean" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<%
	String[] requete = request.getParameterValues("REQUETE"); // récupération des numéros des cours à supprimer
%>
<html>
<head>
<title>AudioVideoCours : Outil de gestion des news</title>
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
                <td width="45" valign="top"><img src="../_mm/pyramide_vert.jpg" width="45" height="50"></td>
                <td valign="bottom">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23"></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="ArialBlack18">Outil de gestion des news</td>
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
    </td>
  </tr>
</table>
<br/>
<%
	if( requete != null ) {
		sqlbean.setWriter(out);
		sqlbean.connect();
		String WebNewsIds = "";
		
		/* Mise en forme de la liste des numéros de cours dans une chaine de caractère pour la requête */
		for( int i = 0 ; i < requete.length ; i++ ) {
			WebNewsIds += "'" + requete[i] + "'";
			if( i != requete.length -1 )
				WebNewsIds += ", ";
		}
		
		/* Suppression des cours */
		sqlbean.update("DELETE FROM \"WebNews\" WHERE \"WebNewsId\" in (" + WebNewsIds + ")");
		
		out.println("Les news " + WebNewsIds + " ont été supprimés");
	
		sqlbean.disconnect();
	}
	else {
		out.println("Pas de news à supprimer !");
	}
%>
</body>
</html>
