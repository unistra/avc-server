<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="java.io.InputStream"/>
<jsp:directive.page import="java.io.BufferedReader"/>
<jsp:directive.page import="java.io.InputStreamReader"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>T&eacute;l&eacute;chargement</title>
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
                <td width="45" valign="top"><img src="../_mm/pyramide_vert.jpg" width="45" height="50"></td>
                <td valign="bottom">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="ArialBlack18">T&eacute;l&eacute;chargement</td>
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
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <%
              	
              	//String repertoireFTP = "/media/ftp";
              
              	/* Listing du répertoire contenant les fichiers à télécharger */
              
              	//String commande = "ls";
	      		//Process p = Runtime.getRuntime().exec(commande, null, new File(repertoireFTP + "/releases"));
	      		//p.waitFor();
	      		
	      		//InputStream in=p.getInputStream();
	      		//BufferedReader entree = new BufferedReader(new InputStreamReader(in));
	      		//String texte="";
	      		//while( (texte = entree.readLine()) != null ) {
	      		//	out.println("<tr><td width=\"65\">&nbsp;</td>");
	      		//	out.println("<td><br><a href=\"../../releases/" + texte + "\">" + texte + "</a></td></tr>");
	      		//}
              %>
              <tr>
              	<td width=\"65\">&nbsp;</td>
              	<td><br><a href="../../releases/AudioVideoCours.exe">Client AudioVideoCours StandAlone</a></td>
              </tr>
              <tr>
                <td><img src="../_mm/zero.gif" width="65" height="10"></td>
                <td><img src="../_mm/zero.gif" width="690" height="10"></td>
              </tr>
            </table>
          </td>
        </tr>
	</table>
	
	
	
	</td>
</tr>
</table>

</body>
</html>