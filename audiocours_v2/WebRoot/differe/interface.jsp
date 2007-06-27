<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="paquet.Tools"/>
<jsp:directive.page import="java.net.URLEncoder"/>
<jsp:useBean id="sqlbean1" class="paquet.SqlBean" />
<jsp:useBean id="sqlbean2" class="paquet.SqlBean" />
<%
	String AudioCoursId = request.getParameter("id");
	String player = request.getParameter("player");
	if( player == null)
		player = "real";
	
	String genre = request.getParameter("genre");
	
	String urlMov = "http://audiovideocours.u-strasbg.fr/coursv2/modele/UNeedQT41.mov";  // URL du fichier Mov permettant à Quicktime de lire du SMIL
//	String urlMov = "http://stagiaire1.u-strasbg.fr/coursv2/modele/UNeedQT41.mov";  // URL du fichier Mov permettant à Quicktime de lire du SMIL
//	String urlMov = "http://sys2000.u-strasbg.fr/coursv2/modele/UNeedQT41.mov";  // URL du fichier Mov permettant à Quicktime de lire du SMIL
	String mediaURI = "";
	String nom_media = "";
	long consults = 0; // Nombre de consultations du cours
	String Type = "";
	String FirstName = "";
	boolean authorized = false;
	
	/* Recherche des informations sur le cours */
 	
 	sqlbean1.setWriter(out);
	sqlbean1.connect();
	String query = "SELECT * FROM \"AudioCours\" a, \"Media\" m ";
	query += "WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" " ;
	query += "AND a.\"AudioCoursId\"= ?";
	query += genre != null ? " AND a.\"Genre\"= '" + genre + "'" : " AND a.\"Genre\" IS NULL";
	sqlbean1.pquery(query,AudioCoursId);
	
	if( sqlbean1.next() ) {
		authorized = true;
		mediaURI = (String) sqlbean1.getColumn("MediaURI");
		String Who = (String) sqlbean1.getColumn("Who");
		FirstName = (String) sqlbean1.getColumn("FirstName");
		String Rating = (String) sqlbean1.getColumn("Rating");
		String Title = (String) sqlbean1.getColumn("Title");
		String Object = (String) sqlbean1.getColumn("Object");
		int Duration = ((Integer) sqlbean1.getColumn("Duration")).intValue();
		int DurationHour = Duration / 3600;
		int DurationMinute = (Duration % 3600) / 60 ;
		int DurationSecond = ((Duration % 3600) % 60) ;
		
		String urlPod= (String) sqlbean1.getColumn("MediaURI");
		Type = (String) sqlbean1.getColumn("Type");
		consults = ((Long) sqlbean1.getColumn("Consultations")).longValue() + 1;
		
		if(FirstName == null)
			FirstName = "";
		if(Object == null)
			Object = "";
		if(Rating == null)
			Rating = "";
		
		String width, height;
		if( Type.equals("audio") ) {
			width="95%";
			height="90%";
		}
		else {
			width="95%";
			height="95%";
		}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>   
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>AudioVideoCours : interface de viusalisation</title>
	 	<% if( Type.equals("audio") ) { %>
 		<script type="text/javascript">
			if (screen.width / screen.height < 1.5)
				document.writeln("<link rel=\"stylesheet\" type=\"text/css\" href=\"../code/style_visu_audio_4_3.css\" />");
			else
				document.writeln("<link rel=\"stylesheet\" type=\"text/css\" href=\"../code/style_visu_audio_16_10.css\" />");
		</script>
	 	<% } else { %>
		<script type="text/javascript">
			if (screen.width / screen.height < 1.5)
				document.writeln("<link rel=\"stylesheet\" type=\"text/css\" href=\"../code/style_visu_video_4_3.css\" />");
			else
				document.writeln("<link rel=\"stylesheet\" type=\"text/css\" href=\"../code/style_visu_video_16_10.css\" />");
		</script>
		<% } %>
		
		<script type="text/javascript">
	
			function setPos(temps)
			{
				<% if( player.equals("real") ) { %>
					document.video.SetPosition(temps*1000);
				<% } else if( player.equals("quicktime") ) { %>
					document.video.Step(temps*1000);
					document.video.Play();
				<% } %>
			}
			
			function redim()
			{
				if(navigator.platform == "Win32")
				{
					setTimeout("self.resizeBy(0,-1)", 5000)
					setTimeout("self.resizeBy(0,1)", 5500)
				}
			}
		
		</script>
	</head>
  
  	<body style="background-image:url(../_mm/Fond.jpg)">
  	
  		<!-- Affichage du lecteur SMIL -->
  		<div id="diapo_courante">
  		
		<%		
			if( player != null ) {
				
				if( player.equals("quicktime") ) {
		%>
			
			<object id="video" classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab" height="<%= height %>" width="<%=width %>">
			 <param name="src" value="<%= mediaURI %>" />
			 <param name="controller" value="true" />
			 <param name="autostart" value="true" />
			 <param name="autosize" value="true" />
			 <param name="scale" value="tofit"> 
			 <embed name="video" src="<%= urlMov %>" height="<%= height %>" width="<%=width %>"
			 autoplay="true"
			 controller="true"
			 scale="tofit" 
			 AutoSize="true"
			 qtsrc="<%= mediaURI %>">
			</object>
			
		<%
				} else if( player.equals("ambulant") ) {
		%>
		
			<!-- <embed align="middle" type="application/smil" --> 
			<!-- src="/usr/apache-tomcat-5.5.17/webapps/coursv2/2006-10-31-12h-03m-28s/son.smil" -->
			<!-- width="90%" height="90%"> -->
		
		<%
				} else if( player.equals("real") ) {
		%>
		
			<object id="video" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="<%= width %>" height="<%= height %>">
			<param name="src" value="<%= mediaURI.substring(0,mediaURI.lastIndexOf(".") + 1 ) + "ram" %>">
			<param name="controls" value="ImageWindow">
			<param name="console" value="console">
			<param name="autostart" value="true">
			<embed type="audio/x-pn-realaudio-plugin" name="video" src="<%= mediaURI.substring(0,mediaURI.lastIndexOf(".") + 1 ) + "ram" %>" width="<%= width %>" height="<%= height %>" align="middle" controls="ImageWindow" console="console" autostart="true">
			</object>
			<br />
			<object classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="<%=width %>" height="40">
			<param name="controls" value="ControlPanel">
			<param name="console" value="console">
			<embed type="audio/x-pn-realaudio-plugin" width="<%=width %>" height="40" align="middle" controls="ControlPanel" console="console">
			</object>
			<script type="text/javascript" src="../code/ieupdate.js"></script>
		<%
				}
			}
		%>
		
		</div>

		<!-- Affichage des informations sur le cours et de la liste des autres cours -->
		<div id="cours">
		    <small>
			    <b>Enseignant </b>: <%=Who + " " + FirstName%><br>
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
			    <b>Dur&eacute;e</b>: 
			    
			    <%
			    	// Affichage de la durée
			    	out.print(DurationHour > 0 ? DurationHour + "h" : "");
			    	out.print(DurationHour > 0 || DurationMinute > 0 ? DurationMinute + "min" : "");
			    	out.print(DurationSecond + "sec");
			    	
					// Affichage du nom de la salle
			    	String IP = (String) sqlbean1.getColumn("IPaddress");	
			    	if( IP !=null && (! IP.equals("")) ){
			    		String nomSalle = Tools.getNomAmphi(IP);
			    		if( ! nomSalle.equals("inconnu") )
			    			out.println("<br><b>Amphi </b>: " + nomSalle);
			    		else
			    			out.println("<br><b>IP Salle </b>: " + IP);
			    	}
			    	
			    	// Affichage du nombre de consultations
			    	out.println("<br><b>Consultations </b>: " + consults);
		
			    	// Création de l'URL des fichiers Podcast
			    	nom_media = Tools.cleanFileName(Title);
					nom_media = urlPod.substring(0,urlPod.lastIndexOf("/") + 1 ) 
						+ URLEncoder.encode(nom_media,"ISO8859-1");
			    %>
			    
	   		</small>
			
			<br><br>
			
			<b>Fichier audio MP3</b>:&nbsp;&nbsp;<a href="<%= nom_media + ".mp3" %>"><img src="../_mm/mp3.png" border="0" align="middle" alt="mp3"/></a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
			<b>Fichier audio Ogg</b>:&nbsp;&nbsp;<a href="<%= nom_media + ".ogg" %>"><img src="../_mm/ogg.png" border="0" align="middle" alt="ogg"/></a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
			
			<%
				// Affichage du lien PDF seulement s'il existe, cad si le cours comporte bien des slides
				sqlbean2.setWriter(out);
				sqlbean2.connect();
				String querypdf = "SELECT \"SlideURI\" FROM \"Slide\" WHERE \"AudioCoursId\" = " + AudioCoursId;
				sqlbean2.query(querypdf);
				
				if( sqlbean2.next() ) {
			%>
					<b>Fichier image PDF</b>:&nbsp;&nbsp;<a href="<%= nom_media + ".pdf" %>"><img src="../_mm/pdf.png" border="0" align="middle" alt="pdf"/></a><br>
			<%
				}
				sqlbean2.disconnect();
			}
			%>
		 	
		 	<b>Cours complet</b>:&nbsp;&nbsp;<a href="<%= nom_media + ".zip" %>"><img src="../_mm/zip.gif" border="0" align="middle" alt="zip"/></a>
		 	
		 	<hr/>
		 	
		 	<p class="Arial11">Note : Si les images sont coupées avec RealPlayer, vous pouvez redimensionner la fenêtre de votre navigateur pour résoudre le problème.</p>
		 	<a href="index.jsp"><img src="../_mm/cours/retour.gif" width="19" height="13" border="0" align="middle" alt="retour"></a>
			&nbsp;
			<a class="retour" href="index.jsp">Retour</a>
		  
		  	<h3>Autres cours de cet enseignant :</h3>
		  
	  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	
	   			<%
					/* Sélection et affichage de tous les cours d'un enseignant */
	   			
					String queryinit = "SELECT * FROM \"AudioCours\" WHERE \"Visible\" = 1 AND \"AudioCoursId\" != ? AND \"Genre\" IS NULL ";
	   				queryinit += "AND Initcap(\"Who\") = ( SELECT Initcap(\"Who\") FROM \"AudioCours\" WHERE \"AudioCoursId\" = ? ) ";
					queryinit += ( ! FirstName.equals("")) ? "AND Initcap(\"FirstName\") = ( SELECT Initcap(\"FirstName\") FROM \"AudioCours\" WHERE \"AudioCoursId\" = ? ) " : "";
					if( FirstName.equals("") )
						sqlbean1.p2query(queryinit,AudioCoursId,AudioCoursId);
					else
						sqlbean1.p3query(queryinit,AudioCoursId,AudioCoursId,AudioCoursId);
					
					if( sqlbean1.next() ) {
						do {
							long CoursId = ((Long) sqlbean1.getColumn("AudioCoursId")).longValue();
							String Who = (String) sqlbean1.getColumn("Who");
							FirstName = (String) sqlbean1.getColumn("FirstName");
							String Rating = (String) sqlbean1.getColumn("Rating");
							String Title = (String) sqlbean1.getColumn("Title");
							String Object = (String) sqlbean1.getColumn("Object");
							int Duration = ((Integer) sqlbean1.getColumn("Duration")).intValue();
							int DurationHour = Duration / 3600;
							int DurationMinute = (Duration % 3600) / 60 ;
							int DurationSecond = ((Duration % 3600) % 60) ;
							
							if(FirstName == null)
								FirstName = "";
							if(Object == null)
								Object = "";
							if(Rating == null)
								Rating = "";
							
							/* Recherche de la première diapo de chaque cours */
							sqlbean2.connect();
							String queryslide = "SELECT \"SlideURI\" FROM \"Slide\" WHERE \"AudioCoursId\" = " + CoursId + " ORDER BY \"Time\"";
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
		   			    <a href="interface.jsp?id=<%=CoursId%>&amp;player=<%= player %>"><img src="<%=SlideURI%>" alt="<%=Title%>" width="140" height="105"></a>
		   			</td>

		   			<td class="Arial11" valign="top">
		   			   <small>
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
						    <b>Dur&eacute;e</b>: 
						    
						    <%
						    	// Affichage de la durée
						    	out.print(DurationHour > 0 ? DurationHour + "h" : "");
						    	out.print(DurationHour > 0 || DurationMinute > 0 ? DurationMinute + "min" : "");
						    	out.print(DurationSecond + "sec");
						    %>
						    
					    </small><br>
		   			</td>
				</tr>
				<%
						} while( sqlbean1.next() );
					}
					else
						out.println("<i>(Aucun autre cours)</i>");
					sqlbean1.disconnect();
		   		%>
         	</table>
		</div>
         
		<!-- Affichage des miniatures de cours -->
		<div id="diaThumb">
		
			<% if( Type.equals("audio")) { %>
				<script language="javascript">
				if (screen.width / screen.height < 1.5)
					document.writeln("<nobr>");
				</script>
			<% } %>
			
			<%
				int count = 1;
				String SlideURI;
				String SlideTitle;
				int Time;
				
				if( authorized ) {
					sqlbean1.connect();
					queryinit = "SELECT * FROM \"Slide\" WHERE \"AudioCoursId\" = '" + AudioCoursId + "' ORDER BY \"Time\"" ;
					sqlbean1.query(queryinit);
					
					while( sqlbean1.next() ) {
						SlideURI = (String) sqlbean1.getColumn("SlideURI");
						SlideTitle = (String) sqlbean1.getColumn("SlideTitle");
						Time = ((Integer) sqlbean1.getColumn("Time")).intValue();
						count++;
			%>
			
			<a href="javascript:setPos(<%= Time %>);">
				
				<% if( Type.equals("video")) { %>
					<img src="<%= SlideURI.substring(0,SlideURI.length()-4) + "-thumb.jpg" %>" alt="<%= SlideTitle %>" border="0" width="95%">
				<% } else { %>
				<script language="javascript">
				if (screen.width / screen.height < 1.5)
					document.writeln("<img src=\"<%= SlideURI.substring(0,SlideURI.length()-4) + "-thumb.jpg" %>\" alt=\"<%= SlideTitle %>\" border=\"0\" height=\"80%\">");
				else
					document.writeln("<img src=\"<%= SlideURI.substring(0,SlideURI.length()-4) + "-thumb.jpg" %>\" alt=\"<%= SlideTitle %>\" border=\"0\" width=\"95%\">");
				</script>
				<% } %>
			</a>
			
			<%
				} // fin boucle while
			} // fin if
			%>
			
			<% if( Type.equals("audio")) { %>
				<script language="javascript">
				if (screen.width / screen.height < 1.5)
					document.writeln("</nobr>");
				</script>
			<% } %>
					
		</div>
		
		<%
			if( authorized ) {
				// Mise à jour du nombre de consultations du cours (+1)
				String queryupdate = "UPDATE \"AudioCours\" SET \"Consultations\"=" + consults;
				queryupdate += " WHERE \"AudioCoursId\"='" + AudioCoursId + "'";
				sqlbean1.update(queryupdate);
				sqlbean1.disconnect();
			}
		%>
		
		<script type="text/javascript">
			redim();
		</script>		
	</body>
</html>
