<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:useBean id="sqlbean" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<%
	String AudioCoursId = request.getParameter("AudioCoursId");
	String Code = request.getParameter("Genre");
%>
<html>
<head>
<title>AudioVideoCours : Modification des codes d'acc&egrave;s</title>
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
                      <td class="ArialBlack18">Modification des codes d'acc&egrave;s</td>
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
	

if( AudioCoursId != null ) {
		sqlbean.setWriter(out);
		sqlbean.connect();
		
		if( Code == null || Code.equals(""))
			Code = "NULL";
		else
			Code = "'" + Code + "'";
		
		/* Suppression des cours du serveur */
		String query = "UPDATE \"AudioCours\" SET \"Genre\" = " + Code + " WHERE \"AudioCoursId\"= " + AudioCoursId ;
		sqlbean.update(query);

		out.println("Le code d'accès du cours " + AudioCoursId + " a été modifié");
	
		sqlbean.disconnect();
		
	}
	else {
		out.println("Pas de cours à modifier !");
	}
%>

<br><a href="listeCodes.jsp">Retour</a>

</body>
</html>
