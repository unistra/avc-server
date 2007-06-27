<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="java.io.InputStream"/>
<jsp:directive.page import="java.io.BufferedReader"/>
<jsp:directive.page import="java.io.InputStreamReader"/>
<jsp:directive.page import="java.io.FileInputStream"/>
<jsp:directive.page import="java.io.PrintWriter"/>
<jsp:directive.page import="java.io.OutputStreamWriter"/>
<jsp:directive.page import="java.io.FileOutputStream"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	request.setCharacterEncoding("UTF-8");
	String ip = request.getParameter("ip");
	
	// IP du serveur Helix
	String ipHelixServer = "130.79.188.5";
	
	if( ip == null)
		ip = "130.79.188.21";
	
	String type = request.getParameter("type");
	
	// Vérification qu'une diffusion est en cours
	String commande;
	boolean liveOn = false;
	
	if(type.equals("audio")) {
		
		commande = "wget -o live_" + ip + ".log --timeout=2 -t2 --spider -S http://" + ip + ":8080";
		Process p = Runtime.getRuntime().exec(commande, null, new File("/tmp"));
		p.waitFor();
		
		FileInputStream fis = new FileInputStream("/tmp/live_" + ip + ".log");
		BufferedReader entree = new BufferedReader(new InputStreamReader(fis));
		String texte="";
		String result="";
		while( (texte = entree.readLine()) != null )
			result+= texte;
		liveOn = result.indexOf("200 OK") != -1;
	}
	else if(type.equals("video")) {
		commande = "mplayer -frames 0 rtsp://" + ipHelixServer + "/broadcast/" + ip.replace(".","_") + ".rm";
		Process p = Runtime.getRuntime().exec(commande, null, new File("/tmp"));
		p.waitFor();
		
		InputStream in=p.getInputStream();
		BufferedReader entree = new BufferedReader(new InputStreamReader(in));
		String texte="";
		String result="";
		while( (texte = entree.readLine()) != null )
			result+= texte;
		liveOn = result.indexOf("REAL file format detected") != -1;
	}
%>
<html>
<head>
<title>Projet AudioVideoCours</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript" src="../code/js_videocours_.js"></script>
<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">

</head>

<body bgcolor="#FFFFFF" text="#666666" link="#666666" vlink="#666666" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<jsp:include page="../includes/banniere.jsp"/>
<!--suite-->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><table width="757" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="../_mm/zero.gif" width="10" height="30"></td>
        </tr>
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45" valign="top"><img src="../_mm/pyramide_gris.jpg" width="45" height="50"></td>
                <td valign="bottom">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="ArialBlack18">Cours en Direct</td>
                    </tr>
                    <tr>
                      <td height="6" background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="712" height="6"></td>
                    </tr>
                  </table>
                  </td>
              </tr>
            </table></td>
        </tr>
		</table>
		
		<br>
		
		<%
			if( type.equals("audio") ) { 
		%>
				<iframe id="DiaFrame" name="DiaFrame" WIDTH="660" HEIGHT="500" scrolling="no" src="diapo.jsp?ip=<%= ip %>&liveOn=<%= liveOn %>"></iframe>
				<br>
				<br>
		<%
				if( liveOn ) {		
		%>
				<embed name="live" pluginspage="http://www.microsoft.com/Windows/MediaPlayer/" src="http://<%= ip %>:8080" type="application/x-mplayer2" autostart="true" showstatusbar="1" width="300" height="65"></embed>
		<% 
				}
			} else if (type.equals("video") ) { 
		%>
				<table>
				<tr>
					<td>
		<%
				if( liveOn ) {
					
					// Test que le fichier RAM existe
					File fichierRam = new File(getServletContext().getRealPath("/") + "live/livevideo_" + ip.replace('.','_') + ".ram");
					
					if( ! fichierRam.exists() ) {
						fichierRam.createNewFile();
						PrintWriter pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream(fichierRam),"UTF8"));
						pw.println("rtsp://" + ipHelixServer + "/broadcast/" + ip.replace(".","_") + ".rm");
						pw.close();
					}
		%>
						<object id="video" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="320" height="240">
							<param name="src" value="./<%= fichierRam.getName() %>">
							<param name="controls" value="ImageWindow">
							<param name="console" value="console">
							<param name="autostart" value="true">
							<embed type="audio/x-pn-realaudio-plugin" name="video" src="./<%= fichierRam.getName() %>" width="320" height="240" align="middle" controls="ImageWindow" console="console" autostart="true">
						</object>
							<br />
						<object classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" width="320" height="40">
							<param name="controls" value="ControlPanel">
							<param name="console" value="console">
							<embed type="audio/x-pn-realaudio-plugin" width="320" height="40" align="middle" controls="ControlPanel" console="console">
						</object>
		<%
				}
		%>
					</td>
					<td>
						<iframe id="DiaFrame" name="DiaFrame" WIDTH="660" HEIGHT="500" scrolling="no" src="diapo.jsp?ip=<%= ip %>&liveOn=<%= liveOn %>"></iframe>
					</td>
				</tr>
				</table>
		<% 
			} 
		%>
	</td>
</tr>
</table>
</body>
</html>