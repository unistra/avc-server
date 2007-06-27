<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:useBean id="sqlbean" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String query = "";
	String cible = request.getParameter("cible");
	String tri = request.getParameter("order");
%>
<html>
<head>
<title>AudioVideoCours : Outils de masquage</title>
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
                <td width="45" valign="top"><img src="../_mm/pyramide_bleu.jpg" width="45" height="50"></td>
                <td valign="bottom">
                   <table width="100%" border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23"></td>
                       <td>&nbsp;</td>
                       <td>&nbsp;</td>
                     </tr>
                     <tr>
                       <td class="ArialBlack18">Outils de Masquage</td>
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
                <a href="listeCodes.jsp" class="Arial12bold">Modification des codes d'acc&egrave;s</a>
                <strong>&nbsp;&nbsp;</strong>|&nbsp;&nbsp;
                	<% if( cible.equals("visible") ) { %>
                		<a href="liste.jsp?cible=visible" class="Arial14boldNoir">Tous les cours visibles</a>
                	<% } else { %>
                		<a href="liste.jsp?cible=visible" class="Arial12bold">Tous les cours visibles</a>
                	<% } %>
                    <strong>&nbsp;&nbsp;</strong>|&nbsp;&nbsp;
                	<% if( cible.equals("shadows") ) { %>
                		<a href="liste.jsp?cible=shadows" class="Arial14boldNoir">Tous les cours masqu&eacute;s</a>
                	<% } else { %>
                		<a href="liste.jsp?cible=shadows" class="Arial12bold">Tous les cours masqu&eacute;s</a>
                	<% } %>
                    <strong>&nbsp;&nbsp;</strong>|&nbsp;&nbsp;
                	<% if( cible.equals("all") ) { %>
                		<a href="liste.jsp?cible=all" class="Arial14boldNoir">Tous les cours</a>
                	<% } else { %>
                		<a href="liste.jsp?cible=all" class="Arial12bold">Tous les cours</a>
                	<% } %>
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

<FORM METHOD=GET ACTION="liste.jsp">
	<INPUT TYPE="Hidden" NAME="cible" VALUE="<%= cible %>">
	Tri : 
	<SELECT NAME="order">
		<OPTION VALUE="CreationDate">Date</OPTION>
		<OPTION VALUE="Title">Titre</OPTION>
		<OPTION VALUE="Who">Auteur</OPTION>
		<OPTION VALUE="Rating">Formation</OPTION>
		<OPTION VALUE="Object">Sujet</OPTION>
		<OPTION VALUE="Visible">Visible</OPTION>
	</SELECT>
	<INPUT TYPE="Submit" VALUE="Valider">
</FORM>

<% if( cible.equals("visible") ) { 
		query = "SELECT * from \"AudioCours\" WHERE \"Visible\" = 1 ORDER BY ";
		%>
		<FORM Method=POST Action="shadow.jsp?cible=visible">
<% } else if( cible.equals("shadows") ) { 
		query = "SELECT * from \"AudioCours\" WHERE \"Visible\" = 0 ORDER BY ";
		%>
		<FORM Method=POST Action="shadow.jsp?cible=shadows">
<% } else { 
		query = "SELECT * from \"AudioCours\" ORDER BY ";
		%>
		<FORM Method=POST Action="shadow.jsp?cible=all">
<% } 
	query += ((tri != null && ! tri.equals("CreationDate")) ? "\"" + tri + "\", " : "") + "\"CreationDate\" DESC, \"AudioCoursId\" DESC" ;
%>
<table width="100%">
<tr>
	<td valign="top">
<%
	/* Recherche des cours */

	sqlbean.setWriter(out);
	sqlbean.connect();
	sqlbean.query(query);
	
	while( sqlbean.next() ) {
		long AudioCoursId = ((Long) sqlbean.getColumn("AudioCoursId")).longValue();
		String visibleStatus = (String) sqlbean.getColumn("Visible");
		String titre = (String) sqlbean.getColumn("Title");
		String formation = (String) sqlbean.getColumn("Rating");
		String auteur = (String) sqlbean.getColumn("Who");
		String prenom = (String) sqlbean.getColumn("FirstName");
		String sujet = (String) sqlbean.getColumn("Object");
		String Type = (String) sqlbean.getColumn("Type");
		String Genre = (String) sqlbean.getColumn("Genre");
		
		// Conversion de la date dans le bon format
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
    	Date d = (Date) sqlbean.getColumn("CreationDate"); 
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(d);
		
		if(prenom == null)
			prenom = "";
		if(sujet == null)
			sujet = "";
		if(formation == null)
			formation = "";
%>
		<tr>
   			<td colspan=3><hr></td>
   		</tr>
   		<tr>
			<td valign="top" width="150">
			<% if( visibleStatus.equals("1") ) { %>
				<img src="../_mm/cours/haut_parleurs.jpg" alt="" width="125" height="90"></a>
	      	<% } else { %>
				<img src="../_mm/mask.jpg" alt="" width="125" height="90"></a>
			<% } %>
			</td>
			<td><INPUT TYPE=CHECKBOX NAME=REQUETE VALUE=<%= AudioCoursId%>>&nbsp;</td>
	   		<td class="Arial11" valign="top">
		   		<ul><li><small>
		      		<b>Enseignant </b>: <%=auteur + " " + prenom%><br>
		      		<b>Formation </b>: <%=formation%><br>
		      		<b>Titre </b>: <%=titre%><br>
		     		<b>Sujet </b>: <%=sujet%><br>
		      		<b>Date </b>: <%=date%><br>
		      		<b>Type </b>: <%= Type %><br>
		      		<b>Code </b>: <%= Genre == null ? "-" : Genre %><br>
		      		<%
		      			if( Genre == null) {
		      				String url = "http://" + request.getServerName() + request.getContextPath() + "/differe/interface.jsp?id=" + AudioCoursId;
		      		%>
		      		<b>URL Directe </b>: <a href="<%= url %>"><%= url %></a><br>
		      		<%
		      			}
		      		%>
		      		</small></li>
	    		</ul>
			</td>
		</tr>
<%
	}
	sqlbean.disconnect();
%>
	</td>
</tr>
</table>
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
       <tr>
           <td width="20%">&nbsp;</td>
           <td width="60%" align="center"><INPUT width="100%" type=SUBMIT value="Traitement de la selection"/></td>
           <td width="20%">&nbsp;</td>
       </tr>
       <tr>
           <td width="20%">&nbsp;</td>
           <td width="60%" align="center">&nbsp;</td>
           <td width="20%">&nbsp;</td>
       </tr>
</table>
</FORM>
</body>
</html>