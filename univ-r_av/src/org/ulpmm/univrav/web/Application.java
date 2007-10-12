package org.ulpmm.univrav.web;

import java.io.BufferedInputStream;
import java.io.File;
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
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ulpmm.univrav.dao.DaoException;
import org.ulpmm.univrav.dao.DatabaseImpl;
import org.ulpmm.univrav.dao.FileSystemImpl;
import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.language.Messages;
import org.ulpmm.univrav.serverclasses.EncodageStr;
import org.ulpmm.univrav.service.IService;
import org.ulpmm.univrav.service.ServiceImpl;

public class Application extends HttpServlet {

	private ServiceImpl service;
	private HttpSession session;
	
	/* Configuration parameters */
	private static String ftpFolder; // folder in which the courses are uploaded via FTP
	private static String coursesFolder; // folder which contains all the media folders
	private static String liveFolder; // folder which contains the media files for a live
	private static String coursesUrl;
	private static String defaultMp3File;
	private static String defaultRmFile;
	private static String comment;
	private static String helixServerIp;
	
	private static String host;
	private static String port;
	private static String database;
	private static String user;
	private static String password;
	
	private static String rssTitle;
	private static String rssFileName;
	private static String rssDescription;
	private static String serverUrl;
	private static String rssImageUrl;
	private static String recordedInterfaceUrl;
	private static String language;
	
	private static int homeCourseNumber;
	private static int recordedCourseNumber;
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		
		System.out.println("Univ-R AV : init method called");
		
		/* configuration parameters loading */
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(getServletContext().getRealPath("/conf") + "/univrav.properties"));
			
			ftpFolder = p.getProperty("ftpFolder");
			coursesFolder = p.getProperty("coursesFolder");
			liveFolder = p.getProperty("liveFolder");
			coursesUrl = p.getProperty("coursesUrl");
			defaultMp3File = p.getProperty("defaultMp3File");
			defaultRmFile = p.getProperty("defaultRmFile");
			comment = p.getProperty("comment");
			helixServerIp = p.getProperty("helixServerIp");
			
			/* Loading of the settings to connect to the database */
			host = p.getProperty("host");
			port = p.getProperty("port");
			database = p.getProperty("database");
			user = p.getProperty("user");
			password = p.getProperty("password");
			
			/* Loading of the settings of the RSS files */
			rssTitle = p.getProperty("rssTitle");
			rssFileName = p.getProperty("rssFileName");
			rssDescription = p.getProperty("rssDescription");
			serverUrl = p.getProperty("serverUrl");
			rssImageUrl = p.getProperty("rssImageUrl");
			recordedInterfaceUrl = p.getProperty("recordedInterfaceUrl");
			language = p.getProperty("language");
			
			/* the numbers of courses to display at the same time */
			homeCourseNumber = Integer.parseInt(p.getProperty("homeCourseNumber"));
			recordedCourseNumber = Integer.parseInt(p.getProperty("recordedCourseNumber"));
		}
		catch( Exception e) {
			System.out.println("Impossible to find the configuration file");
			e.printStackTrace();
			destroy();
		}
		
		DatabaseImpl db = new DatabaseImpl(host, port, database, user, password);
		FileSystemImpl fs = new FileSystemImpl(
				getServletContext().getRealPath("/") + "scripts",
				ftpFolder, coursesFolder, liveFolder, coursesUrl,
				defaultMp3File, defaultRmFile, comment
		);
		service = ServiceImpl.getInstance();
		service.setDb(db);
		service.setFs(fs);
		
		/* Creation of the RSS files */
		
		// For all courses
		List<Course> courses = service.getAllUnlockedCourses();
		/*** !!!!!! ***/
		String rssPath = getServletContext().getRealPath("/rss") + "/" + rssFileName;
		service.rssCreation(courses, rssPath, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
		
		// For the teachers
		List<String[]> teachers = service.getTeachersWithRss();
		for( String[] teacher : teachers) {
			courses = service.getUnlockedCourses(teacher);
			rssPath = getServletContext().getRealPath("/rss") + "/" 
				+ teacher[0] +  (! teacher[1].equals("") ? "_" + teacher[1] : "") + ".xml";
			service.rssCreation(courses, rssPath, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
		}
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
		
		/* Retrieves the current session or creates a new one if no session exists */
		session = request.getSession(true);
		System.out.println("new session : " + session.isNew());
		
		String style = null;
		String language = null;
		
		/* If the session didn't exist and has just been created */
		if( session.isNew()) {
			/* Checks if the style and language are stored in the cookies */
			Cookie[] cookies = request.getCookies();
			
			/* If the browser has not disabled cookies */
			if( cookies != null ) {
				for(int i=0 ; i<cookies.length ; i++) {
					String cookieName = cookies[i].getName();
					if( cookieName.equals("style") )
						style=cookies[i].getValue();
					else if( cookieName.equals("language") ) 
						language = cookies[i].getValue();
				}
			}
			
			/* If not, store the default values in the cookies */
			if( style == null) {
				style = "style1";
				Cookie styleCookie = new Cookie("style", style);
				styleCookie.setMaxAge(31536000);
				response.addCookie(styleCookie);
			}
			
			if( language == null ){
				language = request.getLocale().getLanguage();
				Cookie languageCookie = new Cookie("language", language);
				languageCookie.setMaxAge(31536000);
				response.addCookie(languageCookie);
			}
			
			/* Store them in the session */
			session.setAttribute("style", style);
			session.setAttribute("language", language);
		}
		
		String page = request.getPathInfo();
		
		if( page == null )
			page = "/home";
		
		if( page.equals("/home")){
			displayHomePage(request, response);
		}
		else if( page.equals("/live"))
			displayLivePage(request, response);
		else if( page.equals("/recorded"))
			displayRecordedPage(request, response);
		else if( page.equals("/search")) {
			try {
			displaySearchResults(request, response);
			}
			catch( Exception e) {
				e.printStackTrace();
			}
		}
		else if( page.equals("/add"))
			addCourse(request, response);
		else if(page.equals("/livestate"))
			liveState(request, response);
		else if( page.equals("/courseaccess")) {
			courseAccess(request, response);
		}
		else if( page.equals("/liveaccess")) {
			liveAccess(request, response);
		}
		else if( page.equals("/help")) {
			/* Saves the page for the style selection thickbox return */
			session.setAttribute("previousPage", "/help");
			getServletContext().getRequestDispatcher("/WEB-INF/views/help.jsp").forward(request, response);
		}
		else if( page.equals("/changestyle")) {
			changeStyle(request, response);
		}
		else if( page.equals("/changelanguage")) {
			changeLanguage(request, response);
		}
		else if( page.equals("/thick_codeform")) {
			request.setAttribute("id", request.getParameter("id"));
			request.setAttribute("type", request.getParameter("type"));
			getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_codeform.jsp").forward(request, response);
		}
		else if( page.equals("/thick_styles")) {
			List<String> styles = service.getStyles(getServletContext().getRealPath("/") + "files/styles/");
			request.setAttribute("styles", styles );
			getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_styles.jsp").forward(request, response);
		}
		else if( page.equals("/thick_languages")) {
			List<String> languages = service.getLanguages(getServletContext().getRealPath("/") + "WEB-INF/classes/org/ulpmm/univrav/language");
			request.setAttribute("languages", languages );
			getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_languages.jsp").forward(request, response);
		}
		else if( page.equals("/thick_legal")) {
			getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_legal.jsp").forward(request, response);
		}
		else if( page.equals("/thick_download")) {
			getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_download.jsp").forward(request, response);
		}
		else if( page.equals("/iframe_liveslide")) {
			liveSlide(request, response);
		}
		else if( page.equals("/admin_courses")) {
			request.setAttribute("courses", service.getAllCourses());
			getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_courses.jsp").forward(request, response);
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
		request.setAttribute("courses", service.getNLastCourses(homeCourseNumber));
		request.setAttribute("rssFileName", rssFileName);
		
		request.setAttribute("rssfiles", getRssFileList());
		
		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/home");
		
		// affichage de la vue [list]
		getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}
	
	private void displayLivePage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {  
		// le modèle de la vue [list]  
		request.setAttribute("buildings", service.getBuildings());
		
		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/live");
		
		// affichage de la vue [list] 
		getServletContext().getRequestDispatcher("/WEB-INF/views/live.jsp").forward(request, response);
	}
	
	private void displayRecordedPage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		int start = 0;
		int pageNumber;
		
		// le modèle de la vue [list]
		if( request.getParameter("page") != null) {
			pageNumber = Integer.parseInt( request.getParameter("page"));
			start = recordedCourseNumber * (pageNumber - 1) ;
		}
		else
			pageNumber = 1;
		
		request.setAttribute("page", pageNumber);
		request.setAttribute("teachers", service.getTeachers());
		request.setAttribute("formations", service.getFormations());
		request.setAttribute("courses", service.getCourses(recordedCourseNumber, start));
		request.setAttribute("items", service.getCourseNumber());
		request.setAttribute("number", recordedCourseNumber);
		request.setAttribute("resultPage", "recorded");
		
		request.setAttribute("rssfiles", getRssFileList());
		
		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/recorded?page=" + pageNumber);
		
		// affichage de la vue [list]
		getServletContext().getRequestDispatcher("/WEB-INF/views/recorded.jsp").forward(request, response);
	}
	
	private void displaySearchResults(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		int start = 0;
		int pageNumber;
		
		HashMap<String, String> params = new HashMap<String, String>();
		
		if( request.getMethod().equals("POST")) { // The form has just been posted
			
			pageNumber = 1;
			System.out.println("enregistrement parametres");
			
			/* Puts the search paramaters in a HashMap object */
			if( request.getParameter("audio") != null && request.getParameter("video") == null ) 
				params.put("type", "audio");
			else if( request.getParameter("audio") == null && request.getParameter("video") != null ) 
				params.put("type", "video");
			else if( request.getParameter("audio") == null && request.getParameter("video") == null ) 
				params.put("type", "");
			
			if( request.getParameter("fullname") != null && ! request.getParameter("fullname").equals("*") ) 
				params.put("fullname", request.getParameter("fullname"));
			
			if( request.getParameter("formation") != null && ! request.getParameter("formation").equals("*") ) 
				params.put("formation", request.getParameter("formation"));
			
			if( request.getParameter("keyword") != null && ! request.getParameter("keyword").equals("") ) 
				params.put("keyword", request.getParameter("keyword"));
			
			/* Saves the hashmap in the session */
			session.setAttribute("params", params);
		}
		else { // The user has clicked on a pagination link
			
			pageNumber = Integer.parseInt( request.getParameter("page"));
			start = recordedCourseNumber * (pageNumber - 1) ;
			
			params = (HashMap<String, String>) session.getAttribute("params");
		}
		
		if( params != null) {
			/* Saves the parameters of the form */
			if( params.get("type") == null ) { 
				request.setAttribute("audio", "checked");
				request.setAttribute("video", "checked");
			}
			else if( (params.get("type")).equals("audio") ) {
				request.setAttribute("audio", "checked");
			}
			else if( (params.get("type")).equals("video") ) {
				request.setAttribute("video", "checked");
			}
			
			if( params.get("fullname") != null && ! params.get("fullname").equals("*") ) {
				request.setAttribute("nameSelected", params.get("fullname"));
			}
			
			if( params.get("formation") != null && ! params.get("formation").equals("*") ) {
				request.setAttribute("formationSelected", params.get("formation"));
			}
			
			if( params.get("keyword") != null && ! params.get("keyword").equals("") ) {
				request.setAttribute("keyword", params.get("keyword"));
			}
			
			request.setAttribute("page", pageNumber);
			request.setAttribute("teachers", service.getTeachers());
			request.setAttribute("formations", service.getFormations());
			request.setAttribute("courses", service.getCourses(params, recordedCourseNumber, start));
			request.setAttribute("items", service.getCourseNumber(params));
			request.setAttribute("number", recordedCourseNumber);
			request.setAttribute("resultPage", "search");
			
			request.setAttribute("rssfiles", getRssFileList());
			
			/* Saves the page for the style selection thickbox return */
			session.setAttribute("previousPage", "/search?page=" + pageNumber);
			
			// affichage de la vue [list]
			getServletContext().getRequestDispatcher("/WEB-INF/views/recorded.jsp").forward(request, response);
		}
		else { // if the session is not valid anymore
			response.sendRedirect("./recorded");
		}
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
			
			/* Generation of the RSS files */
			if( c.getGenre().equals("")) {
				// Regeneration for all courses
				List<Course> courses = service.getAllUnlockedCourses();
				/*** !!!!!! ***/
				String rssPath = getServletContext().getRealPath("/rss") + "/" + rssFileName;
				service.rssCreation(courses, rssPath, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
				
				// For the teacher
				courses = service.getUnlockedCourses(new String[]{c.getName(), c.getFirstname()});
				rssPath = getServletContext().getRealPath("/rss") + "/" 
					+ c.getName() +  (! c.getFirstname().equals("") ? "_" + c.getFirstname() : "") + ".xml";
				service.rssCreation(courses, rssPath, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
			}
			
		out.println("Fichier envoye ! <br/>");
								
		}
		else
			out.println("Erreur : un ou plusieurs parametres n'ont pas ete renseignes");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	private void liveState(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		
		String recordingPlace = request.getParameter("recordingPlace");
		String status = request.getParameter("status");
		
		/* Vérification que les paramètres sont envoyés et non vides */
		if( recordingPlace != null && status != null && ! recordingPlace.equals("") && ! status.equals("")) {
			
			/* Vérification que status contient une des deux chaînes attendues */
			if( status.equals("begin") || status.equals("end"))
				service.setAmphiStatus(recordingPlace, status.equals("begin"));
			else
				out.println("Erreur: valeurs de status attendues: {begin ; end}");
		} 
		else
			out.println("Erreur: param&egrave;tres attendus: recordingPlace, status");
		
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
		
		try {
			if( genre == null)
				c = service.getCourse(courseid);
			else
				c = service.getCourse(courseid, genre);
			
			service.incrementConsultations(c);
			
			if( type.equals("real")) {
				//redirection interface
				request.setAttribute("courseurl", coursesUrl + c.getMediaFolder() + "/" + c.getMediasFileName() + ".smil");
				request.setAttribute("slidesurl", coursesUrl + c.getMediaFolder() + "/screenshots/");
				List<Slide> slides = service.getSlides(c.getCourseid());
				request.setAttribute("slides", slides);
				Amphi a = service.getAmphi(c.getIpaddress());
				String amphi = a != null ? a.getName() : c.getIpaddress();
				String building = service.getBuildingName(c.getIpaddress());
				request.setAttribute("amphi", amphi);
				request.setAttribute("building", building);
				
				// affichage de la vue [list]
				getServletContext().getRequestDispatcher("/WEB-INF/views/recordinterface_smil.jsp").forward(request, response);
			}
			else if( type.equals("flash")) {
				//redirection interface
				request.setAttribute("courseurl", coursesUrl + c.getMediaFolder() + "/" + c.getMediasFileName() + ".swf");
				Amphi a = service.getAmphi(c.getIpaddress());
				String amphi = a != null ? a.getName() : c.getIpaddress();
				String building = service.getBuildingName(c.getIpaddress());
				request.setAttribute("amphi", amphi);
				request.setAttribute("building", building);
				
				// affichage de la vue [list]
				getServletContext().getRequestDispatcher("/WEB-INF/views/recordinterface_flash.jsp").forward(request, response);
			}
			else {
				String filename = coursesFolder + c.getMediaFolder() + "/" + c.getMediasFileName() + "." + type;
				
				// Initialisation des en-têtes.
				response.setContentType("application/x-download");
				response.setHeader("Content-Disposition", "attachment; filename=" + c.getMediasFileName() + "." + type);

				// Envoi du fichier.
				OutputStream out = response.getOutputStream();
				service.returnFile(filename, out);
				
				/*String previousPage = (String) request.getSession().getAttribute("previousPage");
				if( previousPage == null)
					previousPage = "/home";
				response.sendRedirect("." + previousPage);*/
			}
		}
		catch(DaoException de) {
			// redirection message d'erreur
			de.printStackTrace();
			System.out.println("mauvais code");
		}
	}
	
	private void liveAccess(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String ip = request.getParameter("amphi");
		Amphi a = service.getAmphi(ip);
		String amphi = a != null ? a.getName() : ip;
		String building = service.getBuildingName(ip);
		String url = "";
		if( a.getType().equals("audio")) {
			url = "http://" + ip + ":8080";
		}
		else if( a.getType().equals("video")) {
			service.createLiveVideo(ip, helixServerIp);
			url = coursesUrl + "live/livevideo_" + ip.replace('.','_') + ".ram";
		}
		
		request.setAttribute("amphi", amphi);
		request.setAttribute("building", building);
		request.setAttribute("type", a.getType());
		request.setAttribute("ip", ip);
		request.setAttribute("url", url);
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/liveinterface.jsp").forward(request, response);
	}
	
	private void liveSlide(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String ip = request.getParameter("ip");
		String url = "../../live/" + ip.replace('.','_') + ".jpg";
		request.setAttribute("ip", ip);
		request.setAttribute("url", url);
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/include/iframe_liveslide.jsp").forward(request, response);
	}
	
	private void changeStyle(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String style = request.getParameter("style");
		/* Store the style in the session */
		session.setAttribute("style", style);
		/* Store the style in the cookies */
		Cookie styleCookie = new Cookie("style", style);
		styleCookie.setMaxAge(31536000);
		response.addCookie(styleCookie);
		
		/* Returns to the page before the thickbox call (stored in the session) */
		String previousPage = (String) session.getAttribute("previousPage");
		if( previousPage == null)
			previousPage = "/home";
		response.sendRedirect(response.encodeRedirectURL("." + previousPage));
	}
	
	private void changeLanguage(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String language = request.getParameter("language");
		/* Store the style in the session */
		session.setAttribute("language", language);
		/* Store the style in the cookies */
		Cookie languageCookie = new Cookie("language", language);
		languageCookie.setMaxAge(31536000);
		response.addCookie(languageCookie);
		
		/* Returns to the page before the thickbox call (stored in the session) */
		String previousPage = (String) session.getAttribute("previousPage");
		if( previousPage == null)
			previousPage = "/home";
		response.sendRedirect(response.encodeRedirectURL("." + previousPage));
	}
	
	public HashMap<String, String> getRssFileList() {
		HashMap<String, String> rss = new HashMap<String, String>();
		rss.put(rssTitle, "../rss/" + rssFileName);
		
		List<String[]> teachers = service.getTeachersWithRss();
		for( String[] teacher : teachers) {
			rss.put(
				teacher[0] +  (! teacher[1].equals("") ? " " + teacher[1] : ""), 
				"../rss/" + teacher[0] +  (! teacher[1].equals("") ? "_" + teacher[1] : "") + ".xml"
			);
		}
		
		return rss;
	}

}
