<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="org.apache.commons.fileupload.servlet.ServletFileUpload"/>
<jsp:directive.page import="org.apache.commons.fileupload.servlet.ServletRequestContext"/>
<jsp:directive.page import="org.apache.commons.fileupload.FileItemFactory"/>
<jsp:directive.page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"/>
<jsp:directive.page import="org.apache.commons.fileupload.FileItem"/>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Calendar"/>
<jsp:directive.page import="java.util.GregorianCalendar"/>
<jsp:directive.page import="java.text.ParseException"/>
<jsp:directive.page import="ajout.Fonctions"/>
<jsp:directive.page import="ajout.CreationCours"/>

<!-- Upload du cours audio grâce à l'API FileUpload de Jakarta -->
<!-- http://jakarta.apache.org/commons/fileupload/using.html -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Projet AudioVideoCours</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript" src="../code/js_videocours_.js"></script>
<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#666666" link="#666666" vlink="#666666" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<jsp:include page="./include/banniere_administration.jsp"/>
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
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td class="ArialBlack18">Mise en ligne de cours
                        AudioCours</td>
                      </tr>
                      <tr>
                        <td height="6" background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                        <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                        <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                      </tr>
                    </table>
                  </td>
              </tr>
            </table></td>
        </tr>
        <tr>
        	<!-- Début code spécifique à la page -->
        	
        	<td>
        	<br/>
        				<%
   	        		    		String titre, description, auteur, prenomAuteur, date, formation, genre, media, mediaDir;
   	        		    		String commentaire = "Copyright ULP Multimedia";
   	        		    		String repertoire; // Répertoire de récupération des archives  de cours envoyées
   	        		    		String repertoireCode; // Répertoire de l'application web
   	        		    		String repertoireApache = "/media/coursv2/";
        	        			String repertoireSmil;
   	        		    		Calendar cal = new GregorianCalendar();
   	        		    		Fonctions f;        	
   	        	        	
   	        	        		titre = description = auteur = prenomAuteur = date = formation = genre = "";
   	        	        		repertoire = "/tmp/";
   	        	        		repertoireCode = getServletContext().getRealPath("/");
   	        	        		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   	        	        		Date d = new Date();
   	        	        		boolean continuer=true;
   	        	        	
   	        	        		// Teste si la requête est bien une requête d'envoi de fichier
        	        			if( ! ServletFileUpload.isMultipartContent(new ServletRequestContext(request)) )
        	        				out.println("Erreur : Requête d'envoi de fichier incorrecte");
        	        			else {
        	        				
        	        				// Préparation d'un parse de la requête pour récupérer les différents éléments du POST
        	        				FileItemFactory factory = new DiskFileItemFactory();
        	        				ServletFileUpload upload = new ServletFileUpload(factory);
        	        				List items = upload.parseRequest(request);
        	        				
        	        				// Traitement des différents éléments
        	        				Iterator iter = items.iterator();
        	        				while (iter.hasNext() && continuer) {
        	        				    FileItem item = (FileItem) iter.next();
        	        		
        	        				    // Si l'élément est un élément de formulaire
        	        				    if (item.isFormField()) {
        	        				        
        	        				    	if(item.getFieldName().equals("titre")){
        	        				        	titre = item.getString("UTF8");
        	        				        	if( titre.equals("") ) {
        	        				        		out.println("Vous n'avez pas spécifié de titre pour votre cours");
        	        				        		continuer = false ;
        	        				        	}
        	        				    	}
        	        				    	if(item.getFieldName().equals("description")){
        	        				        	description = item.getString("UTF8");
        	        				        	if( titre.equals("") ) {
        	        				        		out.println("Vous n'avez pas spécifié de description pour votre cours");
        	        				        		continuer = false ;
        	        				        	}
        	        				    	}
        	        				        else if(item.getFieldName().equals("auteur")){
        	        				        	auteur = item.getString("UTF8");
        	        				        	if( auteur.equals("") ) {
        	        				        		out.println("Vous n'avez pas spécifié d'auteur pour votre cours");
        	        				        		continuer = false ;
        	        				        	}
        	        				        }
        	        				        else if(item.getFieldName().equals("date")){
        	        				        	date = item.getString("UTF8");
        	        				        	if( date.equals("") ) {
        	        				        		out.println("Vous n'avez pas spécifié de date pour votre cours");
        	        				        		continuer = false ;
        	        				        	}
        	        				        	else {
		        	        			        	try {
		        	        				        	d = sdf.parse(item.getString("UTF8"));
		        	        				        	cal.setTime(d);
		        	        			        	}
		        	        			        	catch( ParseException e) {
		        	        			        		out.println("Le format de la date de votre cours est incorrect");
		        	        			        		continuer = false ;
		        	        			        	}
        	        				        	}
        	        				        }
        	        				        else if(item.getFieldName().equals("prenomAuteur"))
        	        				        	prenomAuteur = item.getString("UTF8");
        	        				        else if(item.getFieldName().equals("formation"))
        	        				        	formation = item.getString("UTF8");
        	        				        else if(item.getFieldName().equals("genre")) {
        	        				        	genre = item.getString("UTF8");
        	        				        }
        	        				    	
        	        				    } // Sinon, il s'agit d'un fichier
        	        				    else {
        	        		
        	        				    	String extension = item.getName().length() > 3 ? item.getName().substring(item.getName().length()-4,item.getName().length()) : "";

        	        				        // Test du type de fichier
        	        				        if( item.getName().equals("") || ( (! extension.equals(".zip")) && (! extension.equals(".tar")) ) ) { 
        	        				        	out.println("Erreur : Vous devez envoyer une archive au format zip ou tar");
        	        				        }
        	        				        else {
        	        				        	f = new Fonctions("(id:no)",titre, description, auteur, prenomAuteur, date, formation, commentaire, repertoireCode, request.getServerName());
        	        				        	
        	        				        	media = item.getName();
		        	        					mediaDir = media.substring(0,media.lastIndexOf("."));
		        	        					String id = "-" + f.getId();
		        	        					
        	        				        	// Ecriture du fichier zip/tar dans le système de fichiers local
		        	        			        File uploadedFile = new File(repertoire + media);
		        	        					item.write(uploadedFile);
		        	        					out.println("Fichier envoyé (" + id + ")<br/>");
		        	        				        	
		        	        					// Décompression de l'archive en fonction du type de fichier
        	        				        	if( extension.equals(".zip")){
        	        				        		/* Décompression du .zip reçu */
        	        				        		Process p3 = Runtime.getRuntime().exec("unzip " + repertoire + media, null, new File(repertoireApache));
        	        				        		p3.waitFor();
        	        				        		/* Renommage du dossier décompressé pour éviter les doublons */
        	        				        		p3 = Runtime.getRuntime().exec("mv " + mediaDir + " " + mediaDir + id, null, new File(repertoireApache));
        	        				        		p3.waitFor();
        	        				        	}
        	        				        	else if( extension.equals(".tar")){
        	        				        		/* Décompression du .tar reçu */
        	        				        		Process p3 = Runtime.getRuntime().exec("tar xf " + repertoire + media, null, new File(repertoireApache));
        	        				        		p3.waitFor();
        	        				        		/* Renommage du dossier décompressé pour éviter les doublons */
        	        				        		p3 = Runtime.getRuntime().exec("mv " + mediaDir + " " + mediaDir + id, null, new File(repertoireApache));
        	        				        		p3.waitFor();
        	        				        	}
		        	        					
        	        				        	mediaDir += id;
		        	        					repertoireSmil = repertoireApache + mediaDir + "/";
		        	        					f.setRepertoireSmil(repertoireSmil);

        	        				        	if( new File(repertoireSmil).exists() ) {
        	        				        		
        	        				        		int duree = f.typeFichier(cal); 
				        	        				
													// Instanciation et lancement du Thread de création du cours
        	        								Thread t = new CreationCours(f, mediaDir, null, genre);
        	        								t.start();
        	        				        		
				        	        				out.println("Durée de la vidéo : " + duree + " secondes");
        	        				        	}
        	        				        	else
        	        								out.println("Le repertoire du cours n'a pas pu être créé sur le serveur");
        	        				        }
        	        				    }
        	        				}
        	        			}
        	        	%>
			
        	</td>
        	
			<!-- Fin code spécifique à la page -->
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
