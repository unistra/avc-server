package org.ulpmm.univrav.serverclasses;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ulpmm.univrav.dao.DatabaseImpl;
import org.ulpmm.univrav.dao.FileSystemImpl;
import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Smil;
import org.ulpmm.univrav.service.IService;
import org.ulpmm.univrav.service.ServiceImpl;

/* Servlet d'upload de cours à partir du client python */
public class ClientUpload extends HttpServlet {

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
		String id, title, description, mediapath, media, timing, name, firstname, formation, login, genre;

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
		mediapath = request.getParameter("mediapath");
		timing = request.getParameter("timing");
		
		description = request.getParameter("description");
		title = request.getParameter("title");
		name = request.getParameter("name");
		firstname = EncodageStr.toUTF8(request.getParameter("firstname"));
		formation = request.getParameter("ue");
		login = request.getParameter("login");
		genre = request.getParameter("genre");
		
		/* Vérification que les paramètres essentiels ont bien été envoyés, annulation de l'upload dans le cas contraire */
		if(id != null && description != null && mediapath != null && title != null && name != null && formation != null) {
		
			/* Traitement des valeurs NULL (paramètres non envoyés par les anciens clients) */
			if( firstname == null )
				firstname = "";
			
			if( genre == null )
				genre = "";
			
			if( timing == null )
				timing = "n-1";
			
			String clientIP = request.getRemoteAddr();
			
			/* Affichage des informations reçues */
			out.println("ID : " + id + "<br/>");
			out.println("Titre : " + title + "<br/>");
			out.println("Description : " + description + "<br/>");
			out.println("Mediapath : " + mediapath + "<br/>");
			//out.println("Date : " + date + "<br/>");
			out.println("Enseignant : " + name + " " + firstname + "<br>");
			out.println("UE : " + formation + "<br>");
			out.println("Login : " + login + "<br>");
			out.println("Genre : " + genre + "<br>");
			out.println("Timing : " + timing + "<br>");
			
			/* Récupération du nom du fichier à partir de son chemin d'accès */
			media = mediapath.substring(mediapath.lastIndexOf("\\") + 1, mediapath.length());
			if( media.length() == mediapath.length() )
				media = mediapath.substring(mediapath.lastIndexOf("/") + 1, mediapath.length());
			
			out.println("Media : " + media + "<br/>");
			
			ServiceImpl service = new ServiceImpl();
			IDatabase db = new DatabaseImpl();
			IFileSystem fs = new FileSystemImpl(getServletContext().getRealPath("/") + "scripts");
			service.setDb(db);
			service.setFs(fs);
			
			Course c = new Course(
					service.getNextCoursId(),
					new Timestamp(new Date().getTime()),
					"", // The type can't be set yet
					title,
					description,
					formation,
					name,
					firstname,
					clientIP,
					0, // The duration can't be set yet
					genre,
					true,
					0,
					timing
			);
			
			service.addCourse(c, media);
			
			// virer le addSlides de la couche service ??
			//service.addSlides(c.getCourseid());
			
			// ça sert à rien en fait la table smil ...?
			//service.addSmil(new Smil(c.getCourseid(), "XXXXXXX"));
			
			
			
		
					
		out.println("Fichier envoye ! <br/>");
					
					
		}
		else
			out.println("Erreur : un ou plusieurs parametres n'ont pas ete renseignes");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
