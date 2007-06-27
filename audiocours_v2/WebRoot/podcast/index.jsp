<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>AudioVideoCours : Podcasting</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript" src="../code/js_videocours_.js"></script>
<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">
<link rel="alternate" type="application/rss+xml" title="Liste de tous les cours" href="../rss/liste_des_cours.xml"/>
<jsp:include page="../includes/liste_rss_enseignants.jsp"/>
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
                <td width="45" valign="top"><img src="../_mm/pyramide_jaune.jpg" width="45" height="50"></td>
                <td valign="bottom">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td class="ArialBlack18">Podcasting : Listes des cours disponibles</td>
                        <td width="140">&nbsp;</td>
                        <td width="80">&nbsp;</td>
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
              <tr>
                <td width="65">&nbsp;</td>
                <td class="Arial12bold">
                	<a href="liste.jsp?What=ens" class="Arial12bold">Par&nbsp;enseignants</a>
                	<strong>&nbsp;&nbsp;</strong>|&nbsp;&nbsp;
                	<a href="liste.jsp?What=rat" class="Arial12bold">Par&nbsp;formations</a>
                	&nbsp;&nbsp;|&nbsp;&nbsp;
                	<a href="liste.jsp?What=all" class="Arial12bold">Tous&nbsp;les&nbsp;cours</a>
                	&nbsp;&nbsp;|&nbsp;&nbsp;
                	<a href="liste.jsp?What=cod" class="Arial12bold">Acc&egrave;s par code</a>
                </td>
              </tr>
              <tr>
                <td><img src="../_mm/zero.gif" width="65" height="10"></td>
                <td><img src="../_mm/zero.gif" width="690" height="10"></td>
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
