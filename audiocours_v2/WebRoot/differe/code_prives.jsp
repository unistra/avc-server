<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Projet AudioVideoCours</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript" src="../code/js_videocours_.js"></script>
<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#666666" link="#666666" vlink="#666666" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<jsp:include page="../includes/banniere.jsp"/>
<!--suite-->
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
                  <FORM method="post" action="find.jsp">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td class="ArialBlack18">Listes des cours</td>
                        <td width="140"> <INPUT name="word" type="text" class="inputTxt" size="20"></td>
                        <td width="80"> <input type="image" src="../_mm/btn_rechercher.jpg" alt="Rechercher"></td>
                      </tr>
                      <tr>
                        <td height="6" background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                        <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                        <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                      </tr>
                    </table>
                  </FORM></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45"><img src="../_mm/zero.gif" width="45" height="10"></td>
                <td class="Arial12bold">
                	<a href="liste_enseignants.jsp" class="Arial12bold">Par&nbsp;enseignants</a> &nbsp;&nbsp;|&nbsp;&nbsp;
                	<a href="liste_formations.jsp" class="Arial12bold">Par&nbsp;formations</a> &nbsp;&nbsp;|&nbsp;&nbsp;
                	<a href="liste_des_cours.jsp" class="Arial12bold">Tous les cours</a> &nbsp;&nbsp;|&nbsp;&nbsp;
                	<a href="code_prives.jsp" class="Arial14boldNoir">Acc&egrave;s par code</a>
                </td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td class="Arial12">&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td valign="top" class="Arial12">
                	<br><br>
                	<form action="cours_prives.jsp" method="post">
                		Code d'acc&egrave;s : 
                		<input type="text" name="genre"> &nbsp;&nbsp;&nbsp;
                		<input type="submit" value="Valider" name="envoi">
                	</form>
                </td>
                </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
