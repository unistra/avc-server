<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:useBean id="sqlbean1" class="paquet.SqlBean" />
<jsp:useBean id="sqlbean2" class="paquet.SqlBean" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	request.setCharacterEncoding("UTF-8");
	String keyword = request.getParameter("word");
	
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
	<style>

		A:hover { color:gray; }
		A:link { color:blue; }
		A {text-decoration:none}

	</style>
  <title>R&eacute;sultat de la recherche sur "<%=keyword%>"</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body alink="#3333FF" vlink="#3333FF" link="#000000" bgcolor="#c0c0c0"
 text="#000000">
<h1><font face="Helvetica, Arial, sans-serif">R&eacute;sultat de la recherche sur "<%=keyword%>" :</font></h1>
<br>
<br>
<table cellpadding="2" cellspacing="2" border="0">
  <tbody>
		<%
			/* Recherche des cours correspondant au mot clé */
		
			sqlbean1.setWriter(out);
			sqlbean1.connect();
			String queryinit = "SELECT * FROM \"AudioCours\" a, \"Media\" m WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" AND \"Visible\" = 1";
			queryinit += " AND (Initcap(\"Title\") LIKE Initcap(?) OR Initcap(\"Object\") LIKE Initcap(?) OR Initcap(\"Who\") LIKE Initcap(?))";
			queryinit += " ORDER BY \"CreationDate\" DESC, a.\"AudioCoursId\" DESC LIMIT " + nbrCoursMax + " OFFSET " + start ;

			sqlbean1.p3query(queryinit,"%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%");
			
			if( sqlbean1.next() ) {
				do {
				String AudioCoursId = (String) sqlbean1.getColumn("AudioCoursId");
				String Who = (String) sqlbean1.getColumn("Who");
				String FirstName = (String) sqlbean1.getColumn("FirstName");
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
				
				/* Recherche de la première diapositive de chaque cours */
				sqlbean2.setWriter(out);
				sqlbean2.connect();
				String queryslide = "SELECT \"SlideURI\" FROM \"Slide\" WHERE \"AudioCoursId\" = " + AudioCoursId + " ORDER BY \"Time\"";
				sqlbean2.query(queryslide);
				
				String SlideURI;
				if( sqlbean2.next() ) {
					SlideURI = (String) sqlbean2.getColumn("SlideURI");
					SlideURI = SlideURI.substring(0,SlideURI.length()-4) + "-thumb.jpg";
				} else
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
		    <td valign="top">
			    <ul><li><font face="Helvetica, Arial, sans-serif">
			    <b>Titre </b>: <%=Title%><br>
			    <b>Enseignant </b>: <%=Who + " " + FirstName%><br>
			    <b>Formation </b>: <%=Rating%><br>
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
			    		<input type="hidden" name="page" value="liste_des_cours">
			    		<input type="submit" name="confirmer" value="Envoyer">
			    	</form>
			    <%
			    	}
			    %>
			    </font></li>
				</ul>
			</td>
		</tr>
			<%
				} while(sqlbean1.next());
			%>
			
				<tr>
					<td colspan="2">Page : &nbsp;
					
				<%
					/* Recherche du nombre d'enregistrements pour la pagination */
					String querycount = "SELECT COUNT(*) FROM \"AudioCours\" a, \"Media\" m WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" AND \"Visible\" = 1";
					querycount += " AND (Initcap(\"Title\") LIKE Initcap(?) OR Initcap(\"Object\") LIKE Initcap(?) OR Initcap(\"Who\") LIKE Initcap(?))";
		
					sqlbean1.p3query(querycount,"%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%");
					
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
					out.println("<a href=\"./find.jsp?word=" + keyword + "&start=0\">&lt;&lt;</a>&nbsp;");
					
					/* Affichage des liens avec les numéros de page*/
					for( int i=debut ; i<= fin ; i++)
						out.println((i != pageCourante ? "<a href=\"./find.jsp?word=" + keyword + "&start=" + ((i-1) * nbrCoursMax) + "\">" : "") + i + (i != pageCourante ? "</a>" : "") +"&nbsp;");
					
					/* Lien dernière page */
					out.println("<a href=\"./find.jsp?word=" + keyword + "&start=" + ((nbrPages-1) * nbrCoursMax) + "\">&gt;&gt;</a>&nbsp;");
					
					sqlbean1.disconnect();
		   		%>
				</td>
			</tr>
			<tr>
				<td colspan="2"><br>Nombre de cours : <%= nbrCours %></td>
			</tr>
			<%
			}
			else
				out.println("<p>La recherche n'a retourn&eacute; aucun r&eacute;sultat</p>");
			%>
  </tbody>
</table>
<br>
<br>
<br>
</body>
</html>
