<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:useBean id="sqlbean" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String tri = request.getParameter("order");
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
        <tr>
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <br/>
              <tr>
                <td width="45"><img src="../_mm/zero.gif" width="45" height="10"></td>
                <td class="Arial12bold">
                    <a href="listeCodes.jsp" class="Arial14boldNoir">Modification des codes d'acc&egrave;s</a>
                    <strong>&nbsp;&nbsp;</strong>|&nbsp;&nbsp;
                    <a href="liste.jsp?cible=visible" class="Arial12bold">Tous les cours visibles</a>
                    <strong>&nbsp;&nbsp;</strong>|&nbsp;&nbsp;
                    <a href="liste.jsp?cible=shadows" class="Arial12bold">Tous les cours masqu&eacute;s</a>
                    <strong>&nbsp;&nbsp;</strong>|&nbsp;&nbsp;
                    <a href="liste.jsp?cible=all" class="Arial12bold">Tous les cours</a>
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
<!-- ************************************************************************************************************** -->
<br/>

<table width="1000" border="1" cellspacing="0" cellpadding="0">
<thead>
    <td width="17%" valign="top" align="center"><a href="nettoyage.jsp?order=Title">Titre</a></td>
    <td width="17%" valign="top" align="center"><a href="nettoyage.jsp?order=Who">Auteur</a></td>
    <td width="17%" valign="top" align="center"><a href="nettoyage.jsp?order=Rating">Formation</a></td>
    <td width="17%" valign="top" align="center"><a href="nettoyage.jsp?order=Object">Sujet</a></td>
    <td width="8%" valign="top" align="center"><a href="nettoyage.jsp?order=CreationDate">Date</a></td>
    <td width="8%" valign="top" align="center"><a href="nettoyage.jsp?order=Duration">Dur&eacute;e</a></td>
    <td width="8%" valign="top" align="center"><a href="nettoyage.jsp?order=Type">Type</a></td>
    <td width="8%" valign="top" align="center"><a href="nettoyage.jsp?order=Genre">Code d'acc&egrave;s</a></td>
</thead>
<!-- requête pour obtenir tous les fichiers de la base -->
<tbody>
<%
	sqlbean.setWriter(out);
	sqlbean.connect();
	String query = "SELECT * from \"AudioCours\" ORDER BY ";
	query += ((tri != null && ! tri.equals("CreationDate")) ? "\"" + tri + "\", " : "") + "\"CreationDate\" DESC, \"AudioCoursId\" DESC" ;
	sqlbean.query(query);
	
	int i=0;
	
	while( sqlbean.next() ) {
		long AudioCoursId = ((Long) sqlbean.getColumn("AudioCoursId")).longValue();
		String Who = (String) sqlbean.getColumn("Who");
		String FirstName = (String) sqlbean.getColumn("FirstName");
		String Rating = (String) sqlbean.getColumn("Rating");
		String Title = (String) sqlbean.getColumn("Title");
		String Object = (String) sqlbean.getColumn("Object");
		String Type = (String) sqlbean.getColumn("Type");
		String Genre = (String) sqlbean.getColumn("Genre");
		int Duration = ((Integer) sqlbean.getColumn("Duration")).intValue();
		int DurationHour = Duration / 3600;
		int DurationMinute = (Duration % 3600) / 60 ;
		int DurationSecond = ((Duration % 3600) % 60) ;

		if(Object == null)
			Object = "";
		if(Rating == null)
			Rating = "";
		
		i++;
%>
<tr>
   <td valign="top" align="center"><%= Title %></td>
   <td valign="top" align="center"><%= Who + (FirstName != null ? " " + FirstName : "") %></td>
   <td valign="top" align="center"><%= Rating %></td>
   <td valign="top" align="center"><%= Object %></td>
   <td valign="top" align="center"> 
    <%
		// Conversion de la date dans le bon format
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
    	Date d = (Date) sqlbean.getColumn("CreationDate"); 
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		out.print(sdf.format(d));
	%>
   </td>
   <td valign="top" align="center">
   <%
	   	// Affichage de la durée
	   	out.print(DurationHour > 0 ? DurationHour + "h" : "");
	   	out.print(DurationHour > 0 || DurationMinute > 0 ? DurationMinute + "min" : "");
	   	out.print(DurationSecond + "sec");
   %>
   </td>
   <td valign="top" align="center"><%= Type %></td>
   <td valign="top" align="center">
   	<!-- Formulaire de modification du code d'acces -->
	<FORM name="modifCode<%=i %>" method="POST" action="modifCode.jsp">
		<INPUT type="hidden" name="AudioCoursId" value="<%= AudioCoursId %>"/>
		<INPUT type="text" name="Genre" value="<%= Genre != null ? Genre : "" %>"/> <br>
		<INPUT type="submit" name="Valider" value="Modifier" />
	</FORM>
   </td>
</tr>

<%
	}
	sqlbean.disconnect();
%>
</tbody>
</table>
<br>
</body>
</html>
