<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="paquet.Tools"/>
<jsp:useBean id="sqlbean1" class="paquet.SqlBean" />
<jsp:useBean id="sqlbean2" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<%
	String[] requete = request.getParameterValues("REQUETE"); // récupération des numéros des cours à supprimer
	String repertoireSMIL = "/media/coursv2/";
%>
<html>
<head>
<title>AudioVideoCours : Outils de suppression</title>
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
                <td width="45" valign="top"><img src="../_mm/pyramide_jaune.jpg" width="45" height="50"></td>
                <td valign="bottom">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23"></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="ArialBlack18">Outil de suppression des entrées</td>
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
		sqlbean1.setWriter(out);
		sqlbean1.connect();
		sqlbean2.setWriter(out);
		sqlbean2.connect();
		String AudioCoursIds = "";
		
		/* Mise en forme de la liste des numéros de cours dans une chaine de caractère pour la requête */
		for( int i = 0 ; i < requete.length ; i++ ) {
			AudioCoursIds += requete[i];
			if( i != requete.length -1 )
				AudioCoursIds += ", ";
		}
		
		/* Suppression des cours du serveur */
		String query = "SELECT * FROM \"Media\" WHERE \"AudioCoursId\" in (" + AudioCoursIds + ")";
		sqlbean1.query(query);
		while( sqlbean1.next() ) {
			String AudioCoursId = (String) sqlbean1.getColumn("AudioCoursId");
			String cheminCours = (String) sqlbean1.getColumn("MediaURI");
			String repertoire = cheminCours.substring(cheminCours.indexOf("/son.smil")-22, cheminCours.length()-8);
			
			/* Vérification que le cours en question n'est pas en doublon dans la base de données avant de le supprimer */
			sqlbean2.query("SELECT * FROM \"Media\" WHERE \"MediaURI\"='" + cheminCours + "' AND \"AudioCoursId\" <> " + AudioCoursId);
			if( ! sqlbean2.next() ) 
				Runtime.getRuntime().exec("rm -Rf " + repertoire, null, new File(repertoireSMIL));
		}
		
		/* Suppression des cours de la base de données*/
		sqlbean1.update("DELETE FROM \"Slide\" WHERE \"AudioCoursId\" in (" + AudioCoursIds + ")");
		sqlbean1.update("DELETE FROM \"Media\" WHERE \"AudioCoursId\" in (" + AudioCoursIds + ")");
		sqlbean1.update("DELETE FROM \"AudioCours\" WHERE \"AudioCoursId\" in (" + AudioCoursIds + ")");
		
		Tools.createRss(); //lancement de la création des RSS
		
		out.println("Les audiocours " + AudioCoursIds + " ont été supprimés");
	
		sqlbean1.disconnect();
		sqlbean2.disconnect();
		
	}
	else {
		out.println("Pas de cours à supprimer !");
	}
%>
<br><a href="nettoyage.jsp">Retour</a>
</body>
</html>