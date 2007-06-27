<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.net.URLEncoder"/>
<jsp:directive.page import="paquet.Tools"/>
<jsp:directive.page import="paquet.EncodageStr"/>
<jsp:useBean id="sqlbean1" class="paquet.SqlBean" />
<jsp:useBean id="sqlbean2" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	request.setCharacterEncoding("UTF-8");
	String What = EncodageStr.toUTF8(request.getParameter("What"));
	String Who = EncodageStr.toUTF8(request.getParameter("Who"));
	String Genre = request.getParameter("Genre");
	String FirstName = EncodageStr.toUTF8(request.getParameter("FirstName"));
	String AudioCoursId = "";
	long DlMp3 = 0;
	long DlOgg = 0;
	
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
  <title>AudioVideoCours : Podcasting</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <script language="JavaScript" src="../code/js_videocours_.js" type="text/javascript"></script>
  <link href="../code/style_videocours.css" rel="stylesheet" type="text/css">

  <%
  
  /* Affichage dynamique du flux RSS */
  
  if( Who != null ) {
		if( What.equals("ens") ) {
			/* RSS des cours d'un enseignant */		
			String nomFichier = Tools.cleanString(Tools.cleanFileName(Who + (! FirstName.equals("") ? "_" + FirstName : ""))) + ".xml";
			out.println("<link rel=\"alternate\" type=\"application/rss+xml\" title=\"Liste des cours de " + Who + " " + FirstName +"\" href=\"../rss/rssEnseignant/" + nomFichier + "\"/>");
		}
		else if( What.equals("rat") ){
			/* RSS des cours d'une formation */		
			String nomFichier = Tools.cleanString(Tools.cleanFileName(Who)) + ".xml";
			out.println("<link rel=\"alternate\" type=\"application/rss+xml\" title=\"Liste des cours de la formation " + Who + "\" href=\"../rss/rssFormation/" + nomFichier + "\"/>");
		}
  }
  else {
		if( What.equals("all") ) {
			/* RSS de tous les cours */
			out.println("<link rel=\"alternate\" type=\"application/rss+xml\" title=\"Liste de tous les cours\" href=\"../rss/liste_des_cours.xml\"/>");
		}
		else if( What.equals("ens") ){
			/* RSS de tous les enseignants */
			sqlbean1.connect();
			sqlbean1.setWriter(out);
			String query_enseignants = "Select distinct \"Who\",\"FirstName\" from \"AudioCours\" where \"Visible\"=1 AND \"Genre\" IS NULL";
			sqlbean1.query(query_enseignants);
			while( sqlbean1.next() ) {
				String name = (String) sqlbean1.getColumn("Who");	//Nom de l'enseignant du cours
				String firstname = (String) sqlbean1.getColumn("FirstName"); //Prénom de l'enseignant du cours
				
				if( firstname == null)
					firstname = "";
				
				/* Affichage d'une métadonnée RSS pour chaque enseignant */
				String nomFichier = Tools.cleanString(Tools.cleanFileName(name + (! firstname.equals("") ? "_" + firstname : ""))) + ".xml";
				%>
				<link rel="alternate" type="application/rss+xml" title="Liste des cours de <%= name + " " + firstname %>" href="../rss/rssEnseignant/<%= nomFichier %>"/>
				<%
			}
			sqlbean1.disconnect();
		}
		else if( What.equals("rat") ){
			/* RSS de toutes les formations */
			sqlbean1.connect();
			sqlbean1.setWriter(out);
			String query_formations = "Select distinct \"Rating\" from \"AudioCours\" where \"Visible\"=1 AND \"Genre\" IS NULL AND \"Rating\" IS NOT NULL";
			sqlbean1.query(query_formations);
			while( sqlbean1.next() ) {
				String formation = (String) sqlbean1.getColumn("Rating");	//Formation
				
				/* Affichage d'une métadonnée RSS pour chaque formation */
				String nomFichier = Tools.cleanString(Tools.cleanFileName(formation)) + ".xml";
				%>
				<link rel="alternate" type="application/rss+xml" title="Liste des cours de la formation <%= formation %>" href="../rss/rssFormation/<%= nomFichier %>"/>
				<%
			}
			sqlbean1.disconnect();
		}
  }
  %>
</head>

<body bgcolor="#FFFFFF" text="#666666" link="#666666" vlink="#666666" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<jsp:include page="../includes/banniere.jsp"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><table width="757" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="../_mm/zero.gif" width="10" height="30" alt="zero"/></td>
        </tr>
        <tr>
          <td>
          	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45" valign="top"><img src="../_mm/pyramide_jaune.jpg" width="45" height="50" alt="logo"/></td>
                <td valign="bottom">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23" alt="zero"/></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td class="ArialBlack18">Podcasting : Listes des cours disponibles</td>
                        <td width="140">&nbsp;</td>
                        <td width="80">&nbsp;</td>
                      </tr>
                      <tr>
                        <td height="6" background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6" alt="zero"/></td>
                        <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6" alt="zero"/></td>
                        <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6" alt="zero"/></td>
                      </tr>
                    </table>
                  </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td valign="top">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="65">&nbsp;</td>
                <td class="Arial12bold">
					<% if( What.equals("ens") ) {%>
						<a href="liste.jsp?What=ens" class="Arial14boldNoir">Par&nbsp;enseignants</a>
					<% }else {%>
						<a href="liste.jsp?What=ens" class="Arial12bold"at>Par&nbsp;enseignants</a>
					<% }%>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<% if( What.equals("rat") ) {%>
						<a href="liste.jsp?What=rat" class="Arial14boldNoir">Par&nbsp;formations</a>
					<% }else {%>
						<a href="liste.jsp?What=rat" class="Arial12bold">Par&nbsp;formations</a>
					<% }%>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<% if( What.equals("all") ) {%>
						<a href="liste.jsp?What=all" class="Arial14boldNoir">Tous&nbsp;les&nbsp;cours</a>
					<% }else {%>
						<a href="liste.jsp?What=all" class="Arial12bold">Tous&nbsp;les&nbsp;cours</a>
					<% }%>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<% if( What.equals("cod") ) {%>
						<a href="liste.jsp?What=cod" class="Arial14boldNoir">Acc&egrave;s par code</a>
					<% }else {%>
						<a href="liste.jsp?What=cod" class="Arial12bold">Acc&egrave;s par code</a>
					<% }%>
                </td>
              </tr>
              </table>
           </td>
          </tr>
          <tr>
          	<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          		<tr><td colspan=2>&nbsp;</td></tr>
          		<tr><td colspan=2>&nbsp;</td></tr>

				<%
				String queryinit;
				sqlbean1.setWriter(out);
				sqlbean1.connect();
				
				if( Who != null || What.equals("all") || Genre != null ) {
					if( What.equals("all") ) {
						/* Podcasts de tous les cours */
						queryinit = "SELECT * FROM \"AudioCours\" a, \"Media\" m ";
						queryinit += "WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" " ;
						queryinit += "AND \"Visible\" = 1 AND \"Podcast\" = 1 AND \"Genre\" IS NULL ORDER BY \"CreationDate\" DESC, a.\"AudioCoursId\" DESC LIMIT " + nbrCoursMax + " OFFSET " + start ;
						sqlbean1.query(queryinit);
					}
					if( What.equals("ens") ) {
						/* Podcasts des cours d'un enseignant */		
						queryinit = "SELECT * FROM \"AudioCours\" a, \"Media\" m ";
						queryinit += "WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" " ;
						queryinit += "AND \"Visible\" = 1 AND \"Podcast\" = 1 AND Initcap(\"Who\") = Initcap(?) ";
						queryinit += ( ! FirstName.equals("")) ? "AND Initcap(\"FirstName\") = Initcap(?) " : "";
						queryinit += "AND \"Genre\" IS NULL ORDER BY \"CreationDate\" DESC, a.\"AudioCoursId\" DESC LIMIT " + nbrCoursMax + " OFFSET " + start ;
						if( FirstName.equals("") )
							sqlbean1.pquery(queryinit,Who);
						else
							sqlbean1.p2query(queryinit,Who, FirstName);
					}
					else if( What.equals("rat") ){
						/* Podcasts des cours d'une formation */		
						queryinit = "SELECT * FROM \"AudioCours\" a, \"Media\" m ";
						queryinit += "WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" " ;
						queryinit += "AND \"Visible\" = 1 AND \"Podcast\" = 1 AND Initcap(\"Rating\") = Initcap(?) AND \"Genre\" IS NULL ORDER BY \"CreationDate\" DESC, a.\"AudioCoursId\" DESC LIMIT " + nbrCoursMax + " OFFSET " + start ;
						sqlbean1.pquery(queryinit,Who);
					}
					else if( What.equals("cod") ){
						/* Podcasts des cours d'une formation */		
						queryinit = "SELECT * FROM \"AudioCours\" a, \"Media\" m ";
						queryinit += "WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" " ;
						queryinit += "AND \"Visible\" = 1 AND \"Podcast\" = 1 AND \"Genre\" = ? AND \"Genre\" <> '' AND \"Genre\" IS NOT NULL ORDER BY \"CreationDate\" DESC, a.\"AudioCoursId\" DESC";
						sqlbean1.pquery(queryinit,Genre);
					}
					
					/* Affichage des résultats */
					if( sqlbean1.next() ) {
						do {
							AudioCoursId = (String) sqlbean1.getColumn("AudioCoursId");
							String Teacher = (String) sqlbean1.getColumn("Who");
							String TeacherFirstName = (String) sqlbean1.getColumn("FirstName");
							String Rating = (String) sqlbean1.getColumn("Rating");
							String Title = (String) sqlbean1.getColumn("Title");
							String Object = (String) sqlbean1.getColumn("Object");
							int Duration = ((Integer) sqlbean1.getColumn("Duration")).intValue();
							int DurationHour = Duration / 3600;
							int DurationMinute = (Duration % 3600) / 60 ;
							int DurationSecond = ((Duration % 3600) % 60) ;
							DlMp3 = ((Long) sqlbean1.getColumn("DlMp3")).longValue();
							DlOgg = ((Long) sqlbean1.getColumn("DlOgg")).longValue();
							
							String urlPod= (String) sqlbean1.getColumn("MediaURI");
							
							if(TeacherFirstName == null)
								TeacherFirstName = "";
							if(Object == null)
								Object = "";
							if(Rating == null)
								Rating = "";
						%>
						
						<tr><td colspan=2><hr></td></tr>
						<tr>
						<td valign="top" width="140"><img src="../_mm/ipod.jpg" border="0" alt="ipod"/></td>
						<td class="Arial11" valign="top">
						<ul>
						<li>
						<small>
						<b>Enseignant </b>: <%= Teacher + " " + TeacherFirstName%><br/>
						<b>Formation </b>: <%= Rating %><br/>
					    <b>Titre </b>: <%= Title %><br/>
					    <b>Sujet </b>: <%= Object %><br/>
					    <b>Date </b>: 
					    <%
							// Conversion de la date dans le bon format
					    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
					    	Date d = (Date) sqlbean1.getColumn("CreationDate"); 
							sdf = new SimpleDateFormat("dd/MM/yyyy");
							out.print(sdf.format(d));
						%>
					    <br> 
					    <b>Dur&eacute;e</b>: 
					    <%
					    	// Affichage de la durée
					    	out.print(DurationHour > 0 ? DurationHour + "h" : "");
					    	out.print(DurationHour > 0 || DurationMinute > 0 ? DurationMinute + "min" : "");
					    	out.print(DurationSecond + "sec");
					    	
					    	// Création de l'URL des fichiers Podcast
					    	String nom_media = Tools.cleanFileName(Title);
							nom_media = urlPod.substring(0,urlPod.lastIndexOf("/") + 1 ) 
								+ URLEncoder.encode(nom_media,"ISO8859-1");
					    %>
					    <br/><br/>
						<b>Format MP3</b>:&nbsp;&nbsp;<a href="<%= nom_media + ".mp3" %>" target="_self"><img src="../_mm/mp3.jpg" border="0" align="middle" alt="mp3"/></a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<b>Format Ogg Vorbis</b>:&nbsp;&nbsp;<a href="<%= nom_media + ".ogg" %>" target="_self"><img src="../_mm/ogg.jpg" border="0" align="middle" alt="ogg"/></a> 
						<br><br>
						
						<%
							// Affichage du lien PDF seulement s'il existe, cad si le cours comporte bien des slides
							sqlbean2.setWriter(out);
							sqlbean2.connect();
							String querypdf = "SELECT \"SlideURI\" FROM \"Slide\" WHERE \"AudioCoursId\" = " + AudioCoursId;
							sqlbean2.query(querypdf);
							
							if( sqlbean2.next() ) {
						%>
								<b>Capture PDF</b>:&nbsp;&nbsp;<a href="<%= nom_media + ".pdf" %>"><img src="../_mm/pdf.gif" border="0" align="middle" alt="pdf"/></a>
						<%
							}
							sqlbean2.disconnect();
						%>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<b>Cours complet</b>:&nbsp;&nbsp;<a href="<%= nom_media + ".zip" %>"><img src="../_mm/zip.gif" border="0" align="middle" alt="zip"/></a>
						
						</small>
						</li>
					  	</ul>
					  	</td>
					  	</tr>
					  	
					  	<%
						} while(sqlbean1.next());
						
						if( ! What.equals("cod")) {
						%>
						<tr>
							<td colspan="2">Page : &nbsp;
					
				<%
					}
				
					/* Recherche du nombre d'enregistrements pour la pagination */
					if( What.equals("all") ) {
						/* Podcasts de tous les cours */
						String querycount = "SELECT COUNT(*) FROM \"AudioCours\" a, \"Media\" m ";
						querycount += "WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" " ;
						querycount += "AND \"Visible\" = 1 AND \"Podcast\" = 1 AND \"Genre\" IS NULL " ;
						sqlbean1.query(querycount);
					}
					if( What.equals("ens") ) {
						/* Podcasts des cours d'un enseignant */		
						String querycount = "SELECT COUNT(*) FROM \"AudioCours\" a, \"Media\" m ";
						querycount += "WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" " ;
						querycount += "AND \"Visible\" = 1 AND \"Podcast\" = 1 AND Initcap(\"Who\") = Initcap(?) ";
						querycount += ( ! FirstName.equals("")) ? "AND Initcap(\"FirstName\") = Initcap(?) " : "";
						querycount += "AND \"Genre\" IS NULL " ;
						if( FirstName.equals("") )
							sqlbean1.pquery(querycount,Who);
						else
							sqlbean1.p2query(querycount,Who, FirstName);
					}
					else if( What.equals("rat") ){
						/* Podcasts des cours d'une formation */		
						String querycount = "SELECT COUNT(*) FROM \"AudioCours\" a, \"Media\" m ";
						querycount += "WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" " ;
						querycount += "AND \"Visible\" = 1 AND \"Podcast\" = 1 AND Initcap(\"Rating\") = Initcap(?) AND \"Genre\" IS NULL " ;
						sqlbean1.pquery(querycount,Who);
					}
					
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
					
					String params = "";
					
					if( What.equals("all") ) {
						params = "?What=all";
					}
					if( What.equals("ens") ) {
						params = "?What=ens&Who=" + Who + "&FirstName=" + FirstName;
					}
					else if( What.equals("rat") ){
						params = "?What=rat&Who=" + Who;
					}
					else {
						params = "?What=all";
					}
					
					if( ! What.equals("cod") ) {
					
					/* Lien première page */
					out.println("<a href=\"./liste.jsp" + params + "&start=0\">&lt;&lt;</a>&nbsp;");
					
					/* Affichage des liens avec les numéros de page*/
					for( int i=debut ; i<= fin ; i++)
						out.println((i != pageCourante ? "<a href=\"./liste.jsp" + params + "&start=" + ((i-1) * nbrCoursMax) + "\">" : "") + i + (i != pageCourante ? "</a>" : "") +"&nbsp;");
					
					/* Lien dernière page */
					out.println("<a href=\"./liste.jsp" + params + "&start=" + ((nbrPages-1) * nbrCoursMax) + "\">&gt;&gt;</a>&nbsp;");
					
					sqlbean1.disconnect();
		   		%>
						</td>
					</tr>
					<tr>
						<td colspan="2"><br>Nombre de cours : <%= nbrCours %></td>
					</tr>
				<%
					}
					}
					else
						out.println("<br><p>Aucun cours correspondant</p>");
				}
				else{ // Who est null
					
					if( What.equals("ens") ){
						/* Affichage de la liste des enseignants */
						
						queryinit = "SELECT DISTINCT \"Who\",\"FirstName\" FROM \"AudioCours\" WHERE \"Podcast\" = 1 AND \"Visible\" = 1 AND \"Genre\" IS NULL";
						sqlbean1.query(queryinit);
						
						while( sqlbean1.next() ) {
							String auteur = (String) sqlbean1.getColumn("Who");
							String prenomAuteur = (String) sqlbean1.getColumn("FirstName"); //Prénom de l'enseignant du cours
							
							if(prenomAuteur == null)
								prenomAuteur = "";
							%>
							<tr>
							<td valign="top" align="right" width="140">&nbsp;</td>
							<td class="Arial12" valign="top"><a href="liste.jsp?What=ens&amp;Who=<%= auteur %>&FirstName=<%= prenomAuteur %>"><%= auteur + " " + prenomAuteur %></a></td>
							</tr>
							<%
						}
					}
					else if( What.equals("rat") ){
						/* Affichage de la liste des formations */
						
						queryinit = "SELECT DISTINCT \"Rating\" FROM \"AudioCours\" WHERE \"Podcast\" = 1 AND \"Visible\" = 1 AND \"Genre\" IS NULL AND \"Rating\" IS NOT NULL";
						sqlbean1.query(queryinit);
						
						while( sqlbean1.next() ) {
							String formation = (String) sqlbean1.getColumn("Rating");
							%>
							<tr>
							<td valign="top" align="right" width="140">&nbsp;</td>
							<td class="Arial12" valign="top"><a href="liste.jsp?What=rat&amp;Who=<%= formation %>"><%= formation %></a></td>
							</tr>
							<%
						}
					}
					else if( What.equals("cod") ) {
						/* Affichage du champ de demande du code d'accès */
						%>
							<br><br>
		                	<form action="liste.jsp" method="post">
		                		Code d'acc&egrave;s : 
		                		<input type="hidden" name="What" value="cod"> 
		                		<input type="text" name="Genre"> &nbsp;&nbsp;&nbsp;
		                		<input type="submit" value="Valider" name="envoi">
		                	</form>
						<%
					}
				}
				%>
				
				</table>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>

<script language="javascript">
	function IncDlMP3()
	{
		alert('Ceci est un test');
		<%
			//	Mise à jour du nombre de téléchargement du podcast Mp3 du cours (+1)
			//String queryupdate = "UPDATE \"AudioCours\" SET \"DlMp3\"=" + (DlMp3 + 1);
			//queryupdate += " WHERE \"AudioCoursId\"='" + AudioCoursId + "'";
			//sqlbean1.update(queryupdate);
			//sqlbean1.disconnect();
		%>
	}
	
	function IncDlOgg()
	{
		alert('Ceci est un test');
		<%
			//  Mise à jour du nombre de téléchargement du podcast Mp3 du cours (+1)
			//queryupdate = "UPDATE \"AudioCours\" SET \"DlOgg\"=" + (DlOgg + 1);
			//queryupdate += " WHERE \"AudioCoursId\"='" + AudioCoursId + "'";
			//sqlbean1.update(queryupdate);
			//sqlbean1.disconnect();
		%>
	}
</script>