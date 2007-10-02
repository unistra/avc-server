package org.ulpmm.univrav.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ulpmm.univrav.dao.DaoException;
import org.ulpmm.univrav.dao.DatabaseImpl;
import org.ulpmm.univrav.dao.FileSystemImpl;
import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.language.Messages;
import org.ulpmm.univrav.serverclasses.EncodageStr;
import org.ulpmm.univrav.service.IService;
import org.ulpmm.univrav.service.ServiceImpl;

public class Application extends HttpServlet {

	private ServiceImpl service;
	
	private String coursesUrl = "http://stagiaire1.u-strasbg.fr/coursv2/";
	private String coursesFolder = "/media/coursv2/";
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		DatabaseImpl db = new DatabaseImpl();
		FileSystemImpl fs = new FileSystemImpl(getServletContext().getRealPath("/") + "scripts");
		service = new ServiceImpl();
		service.setDb(db);
		service.setFs(fs);
	}
	
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String page = request.getPathInfo();
		
		if( page == null )
			page = "/home";
		
		if( page.equals("/home"))
			displayHomePage(request, response);
		else if( page.equals("/live"))
			displayLivePage(request, response);
		else if( page.equals("/recorded"))
			displayRecordedPage(request, response);
		else if( page.equals("/search"))
			displaySearchResults(request, response);
		else if( page.equals("/add"))
			addCourse(request, response);
		else if( page.equals("/codeform")) {
			request.setAttribute("id", request.getParameter("id"));
			request.setAttribute("type", request.getParameter("type"));
			getServletContext().getRequestDispatcher("/WEB-INF/views/codeform.jsp").forward(request, response);
		}
		else if( page.equals("/courseaccess")) {
			courseAccess(request, response);
		}
		else
			displayHomePage(request, response);
	}

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

		doGet(request, response);
	}
	
	private void displayHomePage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// le modèle de la vue [list]
		request.setAttribute("teachers", service.getTeachers());
		request.setAttribute("formations", service.getFormations());
		request.setAttribute("courses", service.getNLastCourses(3));
		// affichage de la vue [list]
		getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}
	
	private void displayLivePage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {  
		// le modèle de la vue [list]  
		request.setAttribute("buildings", service.getBuildings());
		// affichage de la vue [list] 
		getServletContext().getRequestDispatcher("/WEB-INF/views/live.jsp").forward(request, response);
	}
	
	private void displayRecordedPage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		int courseNumber = 5;
		int start = 0;
		
		// le modèle de la vue [list]
		if( request.getParameter("page") != null) {
			int pageNumber = Integer.parseInt( request.getParameter("page"));
			start = courseNumber * (pageNumber - 1) ;
			request.setAttribute("page", pageNumber);
		}
		else
			request.setAttribute("page", 1);
		
		request.setAttribute("teachers", service.getTeachers());
		request.setAttribute("formations", service.getFormations());
		request.setAttribute("courses", service.getCourses(courseNumber, start));
		request.setAttribute("items", service.getCourseNumber());
		request.setAttribute("number", courseNumber);
		request.setAttribute("resultPage", "recorded");
		
		// affichage de la vue [list]
		getServletContext().getRequestDispatcher("/WEB-INF/views/recorded.jsp").forward(request, response);
	}
	
	private void displaySearchResults(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		int courseNumber = 5;
		int start = 0;
		
		HashMap<String, String> params = new HashMap<String, String>();
		System.out.println(request.getParameter("audio"));
		
		if( /*request.getMethod()*/ request.getParameter("page") == null) {
			
			if( request.getParameter("audio") != null && request.getParameter("video") == null ) { 
				params.put("type", "audio");
				request.setAttribute("audio", "checked");
			}
			else if( request.getParameter("audio") == null && request.getParameter("video") != null ) {
				params.put("type", "video");
				request.setAttribute("video", "checked");
			}
			else if( request.getParameter("audio") != null && request.getParameter("video") != null ) {
				request.setAttribute("audio", "checked");
				request.setAttribute("video", "checked");
			}
			else
				params.put("type", "");
			
			if( request.getParameter("name") != null && ! request.getParameter("name").equals("*") ) {
				params.put("name", request.getParameter("name"));
				request.setAttribute("nameSelected", request.getParameter("name"));
			}
			
			if( request.getParameter("formation") != null && ! request.getParameter("formation").equals("*") ) {
				params.put("formation", request.getParameter("formation"));
				request.setAttribute("formationSelected", request.getParameter("formation"));
			}
			
			request.setAttribute("page", 1);
		}
		else {
			int pageNumber = Integer.parseInt( request.getParameter("page"));
			start = courseNumber * (pageNumber - 1) ;
			request.setAttribute("page", pageNumber);
		}
		
		/*Enumeration<String> e = request.getParameterNames();

		while( e.hasMoreElements())
			System.out.println(e.nextElement());
		
		System.out.println(request.getParameter("type"));*/
		
		request.setAttribute("teachers", service.getTeachers());
		request.setAttribute("formations", service.getFormations());
		request.setAttribute("courses", service.getCourses(params, courseNumber, start));
		request.setAttribute("items", service.getCourseNumber(params));
		request.setAttribute("number", courseNumber);
		request.setAttribute("resultPage", "search");
		
		// affichage de la vue [list]
		getServletContext().getRequestDispatcher("/WEB-INF/views/recorded.jsp").forward(request, response);
	}
	
	private void addCourse(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
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
					timing,
					"" // The media folder can't be set yet
			);
			
			service.addCourse(c, media);
			
		out.println("Fichier envoye ! <br/>");
								
		}
		else
			out.println("Erreur : un ou plusieurs parametres n'ont pas ete renseignes");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	private void courseAccess(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		int courseid = Integer.parseInt( (String) request.getParameter("id"));
		Course c = null;
		String genre = (String) request.getParameter("code");
		String type = (String) request.getParameter("type");
		System.out.println("id:" + courseid);
		System.out.println("code:" + genre);
		
		try {
			if( genre == null)
				c = service.getCourse(courseid);
			else
				c = service.getCourse(courseid, genre);
			
			if( type.equals("real")) {
				//redirection interface
				request.setAttribute("courseurl", coursesUrl + c.getMediaFolder() + "/" + c.getMediasFileName() + ".smil");
				List<Slide> slides = service.getSlides(c.getCourseid());
				System.out.println(slides.toString());
				
				// affichage de la vue [list]
				getServletContext().getRequestDispatcher("/WEB-INF/views/visualization.jsp").forward(request, response);
			}
			else {
				String filename = coursesFolder + c.getMediaFolder() + "/" + c.getMediasFileName() + "." + type;
				
				// Initialisation des en-têtes.
				response.setContentType("application/x-download");
				response.setHeader("Content-Disposition", "attachment; filename=" + c.getMediasFileName() + "." + type);

				// Envoi du fichier.
				OutputStream out = response.getOutputStream();
				returnFile(filename, out);
			}
		}
		catch(DaoException de) {
			// redirection message d'erreur
			System.out.println("mauvais code");
		}
	}
	
	public static void returnFile(String filename, OutputStream out)
	    throws FileNotFoundException, IOException {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filename));
			byte[  ] buf = new byte[4 * 1024];  // 4K buffer
			int bytesRead;
			while ((bytesRead = in.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
		}
		finally {
			if (in != null) 
				in.close(  );
		}
	}

}
