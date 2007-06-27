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
<!-- ************************************************************************************************************** -->
<br/>

<!-- Formulaire de suppression des cours -->
<FORM Method="POST" Action="suppression.jsp">
<INPUT width="100%" type=SUBMIT value="Suppression des entrées"> <br><br>
<table width="1000" border="1" cellspacing="0" cellpadding="0">
<thead>
    <td width="1%" valign="top" align="center">&nbsp;&nbsp;</td>
    <td width="17%" valign="top" align="center"><a href="nettoyage.jsp?order=Title">Titre</a></td>
    <td width="17%" valign="top" align="center"><a href="nettoyage.jsp?order=Who">Auteur</a></td>
    <td width="17%" valign="top" align="center"><a href="nettoyage.jsp?order=Rating">Formation</a></td>
    <td width="17%" valign="top" align="center"><a href="nettoyage.jsp?order=Object">Sujet</a></td>
    <td width="8%" valign="top" align="center"><a href="nettoyage.jsp?order=CreationDate">Date</a></td>
    <td width="8%" valign="top" align="center"><a href="nettoyage.jsp?order=Duration">Dur&eacute;e</a></td>
    <td width="8%" valign="top" align="center"><a href="nettoyage.jsp?order=Type">Type</a></td>
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
   <td valign="top" align="center"><INPUT TYPE=CHECKBOX NAME=REQUETE VALUE=<%= AudioCoursId %>>&nbsp;</td>
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
</tr>

<%
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
</body>
</html>
