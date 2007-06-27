<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="paquet.EncodageStr"/>
<jsp:directive.page import="paquet.Tools"/>
<jsp:useBean id="sqlbean1" class="paquet.SqlBean" />
<jsp:useBean id="sqlbean2" class="paquet.SqlBean" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	request.setCharacterEncoding("UTF-8");
	String enseignant = EncodageStr.toUTF8(request.getParameter("name"));
	String FirstName = EncodageStr.toUTF8(request.getParameter("firstname"));
		
	/* Nombre de cours à afficher par page */
	int nbrCoursMax = 5;

	/* Récupération du paramètre donnant la pagination à afficher */
	String start = request.getParameter("start");
	
	/* Vérification que le paramètre start est correct */
	if( start == null )
		start =  "0";
	else {
		try {
			Integer.parseInt(start);
		}
		catch(Exception e) {
			start = "0";
		}
	}
%>
<html>
<head>
<title>AudioVideoCours : cours par enseignants</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript" src="../code/js_videocours_.js"></script>
<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">
<%	
	/* Affichage d'une métadonnée RSS pour l'enseignant */
	String nomFichier = Tools.cleanString(Tools.cleanFileName(enseignant + (! FirstName.equals("") ? "_" + FirstName : ""))) + ".xml";
%>
<link rel="alternate" type="application/rss+xml" title="Liste des cours de <%= enseignant + " " + FirstName %>" href="../rss/rssEnseignant/<%= nomFichier %>"/>
</head>

<body bgcolor="#FFFFFF" text="#666666" link="#666666" vlink="#666666" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<jsp:include page="../includes/banniere.jsp"/>
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
          <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45"><img src="../_mm/zero.gif" width="45" height="10"></td>
                <td class="Arial12bold">
                	<a href="liste_enseignants.jsp" class="Arial14boldNoir">Par&nbsp;enseignants</a> &nbsp;&nbsp;|&nbsp;&nbsp;
                	<a href="liste_formations.jsp" class="Arial12bold">Par&nbsp;formations</a> &nbsp;&nbsp;|&nbsp;&nbsp;
                	<a href="liste_des_cours.jsp" class="Arial12bold">Tous les cours</a> &nbsp;&nbsp;|&nbsp;&nbsp;
                	<a href="code_prives.jsp" class="Arial12bold">Acc&egrave;s par code</a>
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
     
     <table width="100%">
			  <tr>
          		<td valign="top">
              

	   			<%
					/* Sélection et affichage de tous les cours d'un enseignant */
	   			
	   				sqlbean1.setWriter(out);
					sqlbean1.connect();
					String queryinit = "SELECT * FROM \"AudioCours\" a, \"Media\" m WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\"";
					queryinit += " AND \"Visible\" = 1 AND Initcap(\"Who\") = Initcap(?) ";
					queryinit += ( ! FirstName.equals("")) ? " AND Initcap(\"FirstName\") = Initcap(?) " : "";
					queryinit += "ORDER BY \"CreationDate\" DESC, a.\"AudioCoursId\" DESC LIMIT " + nbrCoursMax + " OFFSET " + start ;
					if( FirstName.equals("") )
						sqlbean1.pquery(queryinit,enseignant);
					else
						sqlbean1.p2query(queryinit,enseignant,FirstName);

					while( sqlbean1.next() ) {
						String AudioCoursId = (String) sqlbean1.getColumn("AudioCoursId");
						String Who = (String) sqlbean1.getColumn("Who");
						FirstName = (String) sqlbean1.getColumn("FirstName");
						String Rating = (String) sqlbean1.getColumn("Rating");
						String Title = (String) sqlbean1.getColumn("Title");
						String Object = (String) sqlbean1.getColumn("Object");
						String Type = (String) sqlbean1.getColumn("Type");
						String Genre = (String) sqlbean1.getColumn("Genre");
						String MediaURI = (String) sqlbean1.getColumn("MediaURI");
						int Duration = ((Integer) sqlbean1.getColumn("Duration")).intValue();
						int DurationHour = Duration / 3600;
						int DurationMinute = (Duration % 3600) / 60 ;
						int DurationSecond = ((Duration % 3600) % 60) ;
						long consults = ((Long) sqlbean1.getColumn("Consultations")).longValue();
						
						if(FirstName == null)
							FirstName = "";
						if(Object == null)
							Object = "";
						if(Rating == null)
							Rating = "";
						
						/* Recherche de la première diapo de chaque cours */
						sqlbean2.setWriter(out);
						sqlbean2.connect();
						String queryslide = "SELECT \"SlideURI\" FROM \"Slide\" WHERE \"AudioCoursId\" = " + AudioCoursId + " ORDER BY \"Time\"";
						sqlbean2.query(queryslide);
						
						String SlideURI;
						if( sqlbean2.next() ) {
							SlideURI = (String) sqlbean2.getColumn("SlideURI");
							SlideURI = SlideURI.substring(0,SlideURI.length()-4) + "-thumb.jpg";
						}
						else
							SlideURI = "../_mm/audio_icon.gif";
						
						sqlbean2.disconnect();
				%>
                <tr>
       	 			<td colspan=2>
       					<hr>
        			</td>
    			</tr>

				<tr>
   					<td valign="top" width="150">
		   			    <% if( Genre == null ) { %>
					    <a href="interface.jsp?id=<%=AudioCoursId%>&player=real">
					    <% } %>
					    	<img src="<%=SlideURI%>" alt="" width="140" height="105">
					    <% if( Genre == null ) { %>
					    </a>
					    <% } %>
		   			</td>

		   			<td class="Arial11" valign="top">
		   			   <ul><li><small>
		   				    <b>Enseignant </b>: <a><%=Who + " " + FirstName%></a><br>
		   				    <b>Formation </b>: <%=Rating%><br>
		   				    <b>Titre </b>: <%=Title%><br>
		   				    <b>Sujet </b>: <%=Object%><br>
		   				    <b>Date </b>: 
						    <%
								// Conversion de la date dans le bon format
						    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
						    	Date d = (Date) sqlbean1.getColumn("CreationDate"); 
								sdf = new SimpleDateFormat("dd/MM/yyyy");
								out.print(sdf.format(d));
							%>
						    <br> 
						    <b>Type </b>: <%=Type%><br>
						    <b>Dur&eacute;e</b>: 
						    <%
						    	// Affichage de la durée
						    	out.print(DurationHour > 0 ? DurationHour + "h" : "");
						    	out.print(DurationHour > 0 || DurationMinute > 0 ? DurationMinute + "min" : "");
						    	out.print(DurationSecond + "sec");
						    	
								// Affichage du nombre de consultations
						    	out.println("<br><b>Consultations </b>: " + consults);
								
						    	if( Genre == null  || Genre.equals("")) {
						    %>
						    <br/><br/>
							Visualisation externe : <a href="<%= MediaURI.substring(0, MediaURI.length()-4) + "smil" %>" target="_self"><img src="../_mm/smil.gif" alt="Visualisation externe"/></a>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%
								if( Type.equals("audio") ) {
							%>
							Quicktime (web) : <a href="interface.jsp?id=<%=AudioCoursId%>&player=quicktime"><img src="../_mm/logo_quicktime.jpg" alt="Quicktime (web)"/></a>
							<%
								}
						    	}
						    	else {
							%>
						    	<br><br>
						    	
						    	COURS PRIVE, entrez le code d'accès:
						    	
						    	<form method="post" action="verif_code.jsp">
						    		<input type="text" name="genre">
						    		<input type="hidden" name="id" value="<%= AudioCoursId %>">
						    		<input type="hidden" name="player" value="real">
						    		<input type="hidden" name="page" value="cours_par_enseignant">
						    		<input type="submit" name="confirmer" value="Envoyer">
						    	</form>
						    <%
						    	}
						    %>
						    </small></li><br>
		   				</ul>
		   			</td>
				</tr>
				<%
					}
				%>
				<tr>
					<td colspan="2">Page : &nbsp;
					
				<%
					/* Recherche du nombre d'enregistrements pour la pagination */
					String querycount = "SELECT COUNT(*) FROM \"AudioCours\" a, \"Media\" m WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\"";
					querycount += " AND \"Visible\" = 1 AND Initcap(\"Who\") = Initcap(?) ";
					querycount += ( ! FirstName.equals("")) ? " AND Initcap(\"FirstName\") = Initcap(?) " : "";
					
					if( FirstName.equals("") )
						sqlbean1.pquery(querycount,enseignant);
					else
						sqlbean1.p2query(querycount,enseignant,FirstName);
					
					int nbrCours = 0;
					if( sqlbean1.next() )
						nbrCours = Integer.parseInt(sqlbean1.getColumn("count").toString());
					
					/* Calcul du nombre de pages à afficher */
					double nbr = ((double) nbrCours / (double) nbrCoursMax);
					int nbrPages = (int) Math.ceil(nbr);
					
					int pageCourante = (Integer.parseInt(start) / nbrCoursMax) + 1 ;
					
					/* Affichage des liens vers les différentes pages */
					
					int debut, fin;
					
					/* Calcul des liens de pages à afficher */
					if( nbrPages <= 20 ) {
						debut = 1 ;
						fin = nbrPages;
					}
					else {
						
						if( pageCourante <= 9 ) {
							// Affichage des premières pages
							debut = 1 ;
							fin = 20;
						}
						else if( nbrPages - pageCourante <= 10) {
							// Affichage des dernières pages
							debut = nbrPages - 19;
							fin = nbrPages;
						}
						else {
							// Affichage de 9 pages avant, 10 pages après
							debut = pageCourante -9;
							fin = pageCourante + 10;
						}
						
					}
					
					/* Lien première page */
					out.println("<a href=\"./cours_par_enseignant.jsp?name=" + enseignant + "&firstname=" + FirstName + "&start=0\">&lt;&lt;</a>&nbsp;");
					
					/* Affichage des liens avec les numéros de page*/
					for( int i=debut ; i<= fin ; i++)
						out.println((i != pageCourante ? "<a href=\"./cours_par_enseignant.jsp?name=" + enseignant + "&firstname=" + FirstName + "&start=" + ((i-1) * nbrCoursMax) + "\">" : "") + i + (i != pageCourante ? "</a>" : "") +"&nbsp;");
					
					/* Lien dernière page */
					out.println("<a href=\"./cours_par_enseignant.jsp?name=" + enseignant + "&firstname=" + FirstName + "&start=" + ((nbrPages-1) * nbrCoursMax) + "\">&gt;&gt;</a>&nbsp;");
					
					sqlbean1.disconnect();
		   		%>
				</td>
			</tr>
			<tr>
				<td colspan="2"><br>Nombre de cours : <%= nbrCours %></td>
			</tr>
   			</table>
   			</td>
   			</tr>
   			</table>
  </body>
</html>
