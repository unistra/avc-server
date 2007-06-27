<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.sql.Date"/>
<jsp:useBean id="sqlbean" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
<!-- ************************************************************************************************************** -->
<br/>
<FORM Method=POST Action="suppnews.jsp">
<table width="1000" border="1" cellspacing="0" cellpadding="0">
<thead>
    <td width="1%" valign="top" align="center">&nbsp;&nbsp;</td>
    <td width="10%" valign="top" align="center">Date</td>
    <td width="40%" valign="top" align="center">News</td>
</thead>
<!-- requête pour obtenir tous les fichiers de la base -->
<tbody>
<%
	sqlbean.setWriter(out);
	sqlbean.connect();
	String query = "SELECT * from \"WebNews\" ORDER BY \"NewsDate\" DESC, \"WebNewsId\" DESC";
	sqlbean.query(query);
	
	if( ! sqlbean.next() )
		out.println("pas de résulats");
	else {
		do {
			int WebNewsId = ((Integer) sqlbean.getColumn("WebNewsId")).intValue();
			String NewsText = (String) sqlbean.getColumn("NewsText");
%>
<tr>
   <td valign="top" align="center"><INPUT TYPE=CHECKBOX NAME=REQUETE VALUE=<%= WebNewsId %>>&nbsp;</td>
   <td valign="top" align="center"> 
	    <%
			// Conversion de la date dans le bon format	
	    	Date d = (Date) sqlbean.getColumn("NewsDate"); 
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			out.print(sdf.format(d));
		%>
   </td>
   <td valign="top" align="center"><%= NewsText%></td>
</tr>
<%
		} while( sqlbean.next() );
	}
	
	sqlbean.disconnect();
%>
</tbody>
</table>
<br>
<table width="1000" border="0" cellspacing="0" cellpadding="0">
       <tr>
           <td width="20%">&nbsp;</td>
           <td width="60%" align="center"><INPUT width="100%" type=SUBMIT value="Suppression des entrées"></td>
           <td width="20%">&nbsp;</td>
       </tr>
</table>
</FORM>
<br><br>
<FORM Method=POST Action="ajoutnews.jsp">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
<tbody>
	<tr>
		<td width="25%">Date : <INPUT TYPE="TEXT" NAME="Date" VALUE="<%= (new SimpleDateFormat("dd/MM/yyyy")).format(new java.util.Date()) %>"></td>
		<td width="50%">News :<TEXTAREA COLS="50" NAME="News" ROWS="1"></TEXTAREA></td>
		<td><INPUT type=SUBMIT value="Ajout de la news"></td>
	</tr>
</tbody>
</FORM>
</body>
</html>
