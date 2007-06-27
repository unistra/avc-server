<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="paquet.Tools"/>
<jsp:directive.page import="java.net.URLEncoder"/>
<jsp:useBean id="sqlbean1" class="paquet.SqlBean" />
<jsp:useBean id="sqlbean2" class="paquet.SqlBean" />
<%
	String AudioCoursId = request.getParameter("id");
	String player = request.getParameter("player");
	
//	String urlMov = "http://audiovideocours.u-strasbg.fr/coursv2/modele/UNeedQT41.mov";  // URL du fichier Mov permettant à Quicktime de lire du SMIL
	String urlMov = "http://stagiaire1.u-strasbg.fr/coursv2/modele/UNeedQT41.mov";  // URL du fichier Mov permettant à Quicktime de lire du SMIL
	String mediaURI = "";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>   
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>AudioVideoCours : cours de l'enseignant</title>
	 	<style type="text/css">
	    	
	    	body {
	    		color:white;
	    		background-image:url(../_mm/Fond.jpg);
	    	}
	    	
	    	.retour {color:yellow}
	    	
	    	div#diapo_courante {
	    		text-align: center;
	    		position: absolute;
	    		width: 100%;
	    		height: 77%;
	    		left: 0%;
	    	}
	    	
	    	div#diaThumb {
	    		position:absolute;
	    		width: 100%;
	    		height: 22%;
	    		left: 0%;
	    		top: 77%;
	    		overflow: auto;
	    	} 
	    		
		</style>
	</head>
  
	<script language="javascript">
	function setPos(temps)
	{
		<% if( player.equals("real") ) { %>
			document.video.SetPosition(temps*1000);
		<% } else if( player.equals("quicktime") ) { %>
			document.video.Step(temps*1000);
			document.video.Play();
		<% } %>
	}
	</script>
  
  	<body background-image="Fond.jpg">
  	
  		<!-- Affichage du lecteur SMIL -->
  		<div id="diapo_courante">
  		
		<%
		 	/* Recherche des informations sur le cours */
		 	
		 	sqlbean1.setWriter(out);
			sqlbean1.connect();
			String query = "SELECT * FROM \"AudioCours\" a, \"Media\" m ";
			query += "WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" " ;
			query += "AND a.\"AudioCoursId\"= ?";
			sqlbean1.pquery(query,AudioCoursId);
			
			if( sqlbean1.next() ) {
				mediaURI = (String) sqlbean1.getColumn("MediaURI");
				String Who = (String) sqlbean1.getColumn("Who");
				String FirstName = (String) sqlbean1.getColumn("FirstName");
				String Rating = (String) sqlbean1.getColumn("Rating");
				String Title = (String) sqlbean1.getColumn("Title");
				String Object = (String) sqlbean1.getColumn("Object");
				int Duration = ((Integer) sqlbean1.getColumn("Duration")).intValue();
				int DurationHour = Duration / 3600;
				int DurationMinute = (Duration % 3600) / 60 ;
				int DurationSecond = ((Duration % 3600) % 60) ;
				
				String urlPod= (String) sqlbean1.getColumn("MediaURI");
			
			if( player != null ) {
				
				if( player.equals("quicktime") ) {
		%>
		
			<embed id="video" src="<%= urlMov %>" height="90%" width="90%"
			autoplay="true"
			controller="true"
			qtsrc="<%= mediaURI %>">
			
		<%
				} else if( player.equals("ambulant") ) {
		%>
		
			<!-- <embed align="middle" type="application/smil" --> 
			<!-- src="/usr/apache-tomcat-5.5.17/webapps/coursv2/2006-10-31-12h-03m-28s/son.smil" -->
			<!-- width="90%" height="90%"> -->
		
		<%
				} else if( player.equals("real") ) {
		%>
		
			<object id="video" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="60%" height="90%">
			<param name="src" value="<%= mediaURI.substring(0,mediaURI.lastIndexOf(".") + 1 ) + "ram" %>">
			<param name="controls" value="ImageWindow">
			<param name="console" value="console">
			<param name="autostart" value="true">
			<embed type="audio/x-pn-realaudio-plugin" name="video" src="<%= mediaURI.substring(0,mediaURI.lastIndexOf(".") + 1 ) + "ram" %>" width="60%" height="90%" align="middle" controls="ImageWindow" console="console" autostart="true">
			</object>
			<br />
			<object classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="60%" height="40">
			<param name="controls" value="ControlPanel">
			<param name="console" value="console">
			<embed type="audio/x-pn-realaudio-plugin" width="60%" height="40" align="middle" controls="ControlPanel" console="console">
			</object>
			
		<%
				}
			}
			}
		%>
		
		</div>
         
		<!-- Affichage des miniatures de cours -->
		<div id="diaThumb">
		
			<nobr>
			
			<%
				int count = 1;
				String SlideURI;
				String SlideTitle;
				int Time;
				
				sqlbean1.connect();
				String queryinit = "SELECT * FROM \"Slide\" WHERE \"AudioCoursId\" = '" + AudioCoursId + "' ORDER BY \"Time\"" ;
				sqlbean1.query(queryinit);
				
				while( sqlbean1.next() ) {
					SlideURI = (String) sqlbean1.getColumn("SlideURI");
					SlideTitle = (String) sqlbean1.getColumn("SlideTitle");
					Time = ((Integer) sqlbean1.getColumn("Time")).intValue();
					count++;
			%>
			
			<a href="javascript:setPos(<%= Time %>);">
				<img src="<%= SlideURI.substring(0,SlideURI.length()-4) + "-thumb.jpg" %>" alt="<%= SlideTitle %>" border="0" height="80%">
			</a>
			
			<%
				}
			%>
			
			</nobr>
					
		</div>
		
	</body>
</html>
