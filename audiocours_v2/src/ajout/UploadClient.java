package ajout;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/* Servlet d'upload de cours à partir du client python */
public class UploadClient extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String date;
		String id, titre, description, mediapath, media, mediaDir, name, firstname, ue, login, genre;
		String commentaire = "Copyright ULP Multimedia";
		String repertoireFTP = "/media/ftp/";
		//String repertoireFTP = "/home/ftp/";
		String repertoireApache = "/media/coursv2/";
		String repertoireSmil;
		String repertoireCode = getServletContext().getRealPath("/"); // Répertoire de l'application web sur le système de fichiers local
		Calendar cal = new GregorianCalendar();
		Fonctions f;

		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>Servlet d'envoi de cours</TITLE></HEAD>");
		out.println("  <BODY>");
		
		/* Le client envoie les informations en ISO8859-15 */
		request.setCharacterEncoding("ISO8859-15");
		
		/* récupération des paramètres du client */
		id = request.getParameter("id");
		description = request.getParameter("description");
		mediapath = request.getParameter("mediapath");
		titre = request.getParameter("title");
		name = request.getParameter("name");
		firstname = request.getParameter("firstname");
		ue = request.getParameter("ue");
		login = request.getParameter("login");
		genre = request.getParameter("genre");
		
		/* Traitement des valeurs NULL (paramètres non envoyés par les anciens clients) */
		if( firstname == null )
			firstname = "";
		
		if( genre == null )
			genre = "";
		
		/* Vérification que les paramètres essentiels ont bien été envoyés, annulation de l'upload dans le cas contraire */
		if(id != null && description != null && mediapath != null && titre != null && name != null && ue != null) {
			
			// Initialisation de la date du cours avec la date du jour
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");;
			Date d = new Date();
			date = sdf.format(d);
			
			/* Affichage des informations reçues */
			out.println("ID : " + id + "<br/>");
			out.println("Titre : " + titre + "<br/>");
			out.println("Description : " + description + "<br/>");
			out.println("Mediapath : " + mediapath + "<br/>");
			out.println("Date : " + date + "<br/>");
			out.println("Enseignant : " + name + " " + firstname + "<br>");
			out.println("UE : " + ue + "<br>");
			out.println("Login : " + login + "<br>");
			out.println("Genre : " + genre + "<br>");
			
			/* Récupération du nom du fichier à partir de son chemin d'accès */
			media = mediapath.substring(mediapath.lastIndexOf("\\") + 1, mediapath.length());
			if( media.length() == mediapath.length() )
				media = mediapath.substring(mediapath.lastIndexOf("/") + 1, mediapath.length());
			
			out.println("Media : " + media + "<br/>");
			
			f = new Fonctions(id, titre, description, name, firstname, date, ue, commentaire, repertoireCode, request.getServerName());
			
			/* Nom du répertoire qui stocke le cours sur le serveur à partir du nom de fichier, et en rajoutant l'ID */
			mediaDir = media.substring(0,media.lastIndexOf("."));
			id = "-" + f.getId();
			
			/* Vérification que le répertoire de cours n'existe pas déjà */
			if( ! new File(repertoireApache + mediaDir + id).exists() ) {
				
				try {
					String extension = media.length() > 3 ? media.substring(media.length()-4, media.length()) : "";
					
					/* Décompression de l'archive en fonction de son type vers le répertoire de cours */
					
					if( extension.equals(".zip")){
					
						/* Décompression du .zip reçu */
						Process p = Runtime.getRuntime().exec("unzip " + repertoireFTP + media, null, new File(repertoireApache));
		        		p.waitFor();
		        		/* Renommage du dossier décompressé pour éviter les doublons */
		        		p = Runtime.getRuntime().exec("mv " + mediaDir + " " + mediaDir + id, null, new File(repertoireApache));
		        		p.waitFor();
					}
					else {
					
						/* Décompression du .tar reçu */
						Process p = Runtime.getRuntime().exec("tar xf " + repertoireFTP + media, null, new File(repertoireApache));
						p.waitFor();
						/* Renommage du dossier décompressé pour éviter les doublons */
						p = Runtime.getRuntime().exec("mv " + mediaDir + " " + mediaDir + id, null, new File(repertoireApache));
						p.waitFor();
					}
				} catch (InterruptedException e) {
					out.println(e);
					e.printStackTrace();
					}
				
				out.println("Fichier envoye ! <br/>");
				
				/* Répertoire de cours sur le système de fichiers local */
				mediaDir += id;
				repertoireSmil = repertoireApache + mediaDir + "/";
				f.setRepertoireSmil(repertoireSmil);
				
				/* Vérification que l'archive a bien été décompressée en vérifiant la présence du répertoire du cours */
				if( new File(repertoireSmil).exists() ) {
					
					int duree = f.typeFichier(cal);
					String ipClient = request.getRemoteAddr();
					
					// Instanciation et lancement du Thread de création du cours en tâche de fond
					Thread t = new CreationCours(f, mediaDir, ipClient, genre);
					t.start();
					
					out.println("Duree de la video : " + duree + " secondes");
					
					/* Si le cours a été lancé à partir d'Univ-R, il faut lui envoyer un message de confirmation */
					if( ! id.equals("(id:no")) {
						f.confirmationUnivr(login);
					}
				}
				else
					out.println("Le repertoire du cours n'a pas pu être créé sur le serveur");
			}
			else
				out.println("Ce cours existe déjà sur le serveur ! Upload annulé.");
		}
		else
			out.println("Erreur : un ou plusieurs parametres n'ont pas ete renseignes");

		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
