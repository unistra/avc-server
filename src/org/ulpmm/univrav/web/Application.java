package org.ulpmm.univrav.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;

import javax.naming.InitialContext;
import javax.naming.directory.DirContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import nl.captcha.Captcha;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;
import org.json.XML;
import org.ulpmm.univrav.dao.DaoException;
import org.ulpmm.univrav.dao.DatabaseImpl;
import org.ulpmm.univrav.dao.FileSystemImpl;
import org.ulpmm.univrav.dao.LdapAccessImpl;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Discipline;
import org.ulpmm.univrav.entities.Job;
import org.ulpmm.univrav.entities.LogUserAction;
import org.ulpmm.univrav.entities.Selection;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Tag;
import org.ulpmm.univrav.entities.Teacher;
import org.ulpmm.univrav.entities.User;
import org.ulpmm.univrav.service.ServiceImpl;

import com.sun.jndi.toolkit.url.UrlUtil;


/**
 * The servlet of the application.
 * 
 * @author morgan
 *
 */
public class Application extends HttpServlet {

	/** Serial version */
	private static final long serialVersionUID = 1L;
	
	/** Instance of the service layer */
	private ServiceImpl service;
	
	/** Used to managed user sessions */
	private HttpSession session;
	
	/** The name of the bundle to search the corresponding language properties files */
	private static final String BUNDLE_NAME = "org.ulpmm.univrav.language.messages"; 
			
	/** The Url to access to the course on the Internet */
	private static String coursesUrl;
		
	/** The ftp folder */
	private static String ftpFolder;
	/** The courses folder */
	private static String coursesFolder; 
	/** The live folder */
	private static String liveFolder; 
	
	/** Default MP3 filename in the archive sent by the client */
	private static String defaultMp3File;
	/** Default Flash filename in the archive sent by the client */
	private static String defaultFlashFile;
	/** Default Mp4 filename in the archive sent by the client */
	private static String defaultMp4File;
	/** Default Audio filename 1 in the archive sent by the MAC client */
	private static String defaultAudioMacFile1;
	/** Default Audio filename 2 in the archive sent by the MAC client */
	private static String defaultAudioMacFile2;
		
	/** Copyright comment */
	private static String comment;
	
	/** IP address of the Flash Server for the video live */
	private static String flashServerIp;
			
	/** The RSS title */
	private static String rssTitle;
	/** The RSS name */
	private static String rssName;
	/** The RSS description */
	private static String rssDescription;
	/** The url of the server */
	private static String serverUrl;
	/** The url of the rss image */
	private static String rssImageUrl;
	/** the RSS category */
	private static String rssCategory;
	/** The url of the recorded interface */
	private static String recordedInterfaceUrl;
	/** The language */
	private static String language;
	
	/** The itunes author */
	private static String itunesAuthor;
	/** The itunes subtitle */
	private static String itunesSubtitle;
	/** The itunes summary */
	private static String itunesSummary;
	/** The itunes image */
	private static String itunesImage;
	/** The itunes category */
	private static String itunesCategory;
	/** The itunes keywords */
	private static String itunesKeywords;
	
	/** University name */
	private static String univName;
	/** University acronym */
	private static String univAcronym;
	/** University link */
	private static String univLink;

	/** Publication free */
	private static boolean pubFree;
	/** Publication test */
	private static boolean pubTest;
	
	/** The numbers of last courses to display */
	private static int lastCourseNumber;
	/** The numbers of selection courses to display */
	private static int selectionCourseNumber;
	/** The numbers of collection courses to display */
	private static int collectionCourseNumber;
	/** The numbers of recorded courses to display */
	private static int recordedCourseNumber;
	
	/** The default style */
	private static String defaultStyle;
	
	/** The keyword to identify the tests to delete (genre is equal to this keyword) */
	private static String testKeyWord1;

	/** The keyword to identify the tests to hide (title begins with this keyword) */
	private static String testKeyWord2;
	/** The keyword to identify the tests to hide (title begins with this keyword) */
	private static String testKeyWord3;
	
	/** Admin email for notification */
	private static String adminEmail1;
	/** Admin email for notification */
	private static String adminEmail2;
	/** Admin email for notification */
	private static String adminEmail3;
	
	/** the url for cas logout */
	private static String casLogoutUrl;
	
	/** Additional document formats **/
	private static String addDocFormats;
	
	/** Upload media formats **/
	private static String uploadFormats;
	
	/** Link for support (help page) */
	private static String supportLink;
		
	/** Link for support (help page) */
	private static String helpLink;
	
	/** Link for client download (download thick) */
	private static String clientLink;
	
	/** Link for trac (download thick) */
	private static String tracLink;
	
	/** Link for doc (help page) */
	private static String docLink;
		
	/** Ldap base dn */
	private static String ldapBaseDn;
	/** ldap search filter */
	private static String ldapSearchFilter;
	/** ldap mail */
	private static String ldapMail;
	/** ldap firstname */
	private static String ldapFirstname;
	/** ldap lastname */
	private static String ldapLastname;
	/** ldap profile */
	private static String ldapProfile;
	/** ldap affectation */
	private static String ldapAffectation;
	/** ldap code etp */
	private static String ldapEtpPrimaryCode;
	/** Primary ldap institute code */
	private static String ldapPrimaryInstitute;
	/** Secondary institute code */
	private static String ldapSecondaryInstitute;
		
	/** volume number for filesystem **/
	private static Short volume;	
	
	/** To separate medias encodage **/
	private static boolean sepEnc;
	
	/** default record interface (flash or html5) **/
	private static String defaultRecordInterface;
	
	/** private static boolean log user action stats **/
	private static boolean logstats;
	
	/** google analytics account **/
	private static String googleAnalyticsAccount;
	
	/** show contact us **/
	private static boolean contactUs;
	
	/** Logger log4j */
	private static final Logger logger = Logger.getLogger(Application.class);
	
		
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
				
		/* Initialize Log4j */		
		PropertyConfigurator.configure(getServletContext().getRealPath("/conf") + "/log4j.properties");
				
		logger.info("Univ-R AV : init method called");
		
		
		/* Gets an instance of the service layer */	
		service = new ServiceImpl();
		
		Properties p = new Properties();
		
		try {
			
			/* configuration parameters loading */
			
			p.load(new FileInputStream(getServletContext().getRealPath("/conf") + "/univrav.properties"));
			
			// The Url to access to the course on internet
			coursesUrl = p.getProperty("coursesUrl");
						
			// Folders on the file system
			ftpFolder = p.getProperty("ftpFolder");
			coursesFolder = p.getProperty("coursesFolder");
			liveFolder = p.getProperty("liveFolder");
			
			// Default media filenames in the archive sent by the client
			defaultMp3File = p.getProperty("defaultMp3File");
			defaultFlashFile = p.getProperty("defaultFlashFile");
			defaultMp4File = p.getProperty("defaultMp4File");
			defaultAudioMacFile1 = p.getProperty("defaultAudioMacFile1");
			defaultAudioMacFile2 = p.getProperty("defaultAudioMacFile2");
						
			// Copyright comment
			comment = service.cleanString(p.getProperty("comment"));
			
			// IP address of the Flash Server for the video live
			flashServerIp = p.getProperty("flashServerIp");
						
						
			// The settings of the RSS files, of the permalien (interface flash) and of emails
			rssTitle = p.getProperty("rssTitle");
			rssName = p.getProperty("rssName");
			rssDescription = p.getProperty("rssDescription");
			serverUrl = p.getProperty("serverUrl");
			rssImageUrl = p.getProperty("rssImageUrl");
			rssCategory = p.getProperty("rssCategory");
			recordedInterfaceUrl = p.getProperty("recordedInterfaceUrl");
			language = p.getProperty("language");
			
			// The setting of the RSS files for iTunes
			itunesAuthor = p.getProperty("itunesAuthor");
			itunesSubtitle = p.getProperty("itunesSubtitle");
			itunesSummary = p.getProperty("itunesSummary");
			itunesImage = p.getProperty("itunesImage");
			itunesCategory = p.getProperty("itunesCategory");
			itunesKeywords = p.getProperty("itunesKeywords");
			
			// University parameters
			univName = p.getProperty("univName");
			univAcronym = p.getProperty("univAcronym");
			univLink = p.getProperty("univLink");

			// Publication free
			pubFree = Boolean.parseBoolean(p.getProperty("pubFree"));
			// Publication test
			pubTest = Boolean.parseBoolean(p.getProperty("pubTest"));
			
			// The numbers of courses to display at the same time
			lastCourseNumber = Integer.parseInt(p.getProperty("lastCourseNumber"));
			recordedCourseNumber = Integer.parseInt(p.getProperty("recordedCourseNumber"));
			selectionCourseNumber = Integer.parseInt(p.getProperty("selectionCourseNumber"));
			collectionCourseNumber = Integer.parseInt(p.getProperty("collectionCourseNumber"));
			
			
			// The keyword to identify the tests to delete (genre is equal to this keyword)
			testKeyWord1 = p.getProperty("testKeyWord1");

			// The keyword to identify the tests to hide (title begins with this keyword)
			testKeyWord2 = p.getProperty("testKeyWord2");
			testKeyWord3 = p.getProperty("testKeyWord3");
			
			// The default style
			defaultStyle = p.getProperty("defaultStyle");
						
			// Admin email for notification
			adminEmail1 = p.getProperty("adminEmail1");
			adminEmail2 = p.getProperty("adminEmail2");
			adminEmail3 = p.getProperty("adminEmail3");
			
			//cas logout url
			casLogoutUrl = p.getProperty("casLogoutUrl");
			
			//add doc formats
			addDocFormats = p.getProperty("addDocFormats");
			
			//upload media formats
			uploadFormats = p.getProperty("uploadFormats");
			
			// Link for support (help page)
			supportLink = p.getProperty("supportLink");
			helpLink = p.getProperty("helpLink");
			clientLink = p.getProperty("clientLink");
			tracLink = p.getProperty("tracLink");
			docLink = p.getProperty("docLink");
			
			/* ldap properties */
			ldapBaseDn = p.getProperty("ldapBaseDn");
			ldapSearchFilter = p.getProperty("ldapSearchFilter");
			ldapMail=p.getProperty("ldapMail");
			ldapFirstname = p.getProperty("ldapFirstname");
			ldapLastname = p.getProperty("ldapLastname");
			ldapProfile = p.getProperty("ldapProfile");
			ldapAffectation = p.getProperty("ldapAffectation");
			ldapEtpPrimaryCode = p.getProperty("ldapEtpPrimaryCode");
			ldapPrimaryInstitute = p.getProperty("ldapPrimaryInstitute");
			ldapSecondaryInstitute = p.getProperty("ldapSecondaryInstitute");
			
			List<String> ldapinfos = new ArrayList<String>(6);
			ldapinfos.add(0,ldapMail);
			ldapinfos.add(1,ldapFirstname);
			ldapinfos.add(2,ldapLastname);
			ldapinfos.add(3,ldapProfile);
			ldapinfos.add(4,ldapAffectation);
			ldapinfos.add(5,ldapEtpPrimaryCode);
			ldapinfos.add(6,ldapPrimaryInstitute);
			ldapinfos.add(7,ldapSecondaryInstitute);

						
			/* to separate medias encodage */
			sepEnc = Boolean.parseBoolean(p.getProperty("sepEnc"));
			
			// default record interface (flash or html5)
			defaultRecordInterface = p.getProperty("defaultRecordInterface");
			
			// log stats
			logstats = Boolean.parseBoolean(p.getProperty("logstats"));
			
			// google analytics account
			googleAnalyticsAccount = p.getProperty("googleAnalyticsAccount");
			
			// show contactUs
			contactUs = Boolean.parseBoolean(p.getProperty("contactUs"));
										
			/* Datasource retrieving */
			
			InitialContext cxt = new InitialContext();
			if ( cxt == null ) {
			   throw new Exception("No context found!");
			}

			DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );

			if ( ds == null ) {
			   throw new Exception("Data source not found!");
			}
				
			/* Ldap environment retrieving */
			
			DirContext dc=null;
			Hashtable<?,?> env = null;
			try {
				dc = (DirContext) cxt.lookup("java:comp/env/ldap/ox"); 
				env = dc.getEnvironment();
			}
			catch (Exception e) {
				logger.warn("Ldap not found!",e);
			}
			finally {
				try {
					dc.close();
				}
				catch (Exception e) {
					logger.warn("Close Ldap error",e);
				}	
			}
			
			/* volume number for filesystem from tomcat context */
			try {
				volume = (Short) cxt.lookup("java:/comp/env/volume");
			}
			catch (Exception e) {
				logger.warn("Volume not found! Set 1 by default",e);
				volume = 1;
			}
					
			/* Creates the instances of the data access layer */
			
			DatabaseImpl db = new DatabaseImpl(ds);
			FileSystemImpl fs = new FileSystemImpl(
					getServletContext().getRealPath("/") + "scripts",
					ftpFolder, coursesFolder, liveFolder, coursesUrl,
					defaultMp3File, defaultFlashFile, defaultMp4File, defaultAudioMacFile1, defaultAudioMacFile2, comment, db
			);
			
			LdapAccessImpl ldap = new LdapAccessImpl(
					env,
					ldapBaseDn,
					ldapSearchFilter,
					ldapinfos
			);
		
			
			
			/* Links the data access layer to the service layer */
			service.setDb(db);
			service.setFs(fs);
			service.setLdap(ldap);
			
			
			/* Creation of the RSS files */
			
			service.generateRss(getServletContext().getRealPath("/rss"), rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
			
		}
		catch( IOException e) {
			logger.fatal("Impossible to find the configuration file",e);
			destroy();
		}
		catch( DaoException de) {
			logger.fatal("Database error",de);
			destroy();
		}
		catch( Exception e) {
			logger.fatal("Servlet exception",e);
			destroy();
		}
	}
	
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
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
		
		/* Sets the character encoding as UTF-8 */
		request.setCharacterEncoding("UTF-8");

		/* Retrieves the current session or creates a new one if no session exists */
		session = request.getSession(true);
		
		String style = null;
		String language = null;
		String recordinterface = null;
		
		/* If the session didn't exist and has just been created */
		if( session.isNew() || session.getAttribute("session")==null) {
			
			//Boolean to know if the session is validate
			session.setAttribute("session", true);
			
			/* Checks if the style and language are stored in the cookies */
			
			Cookie[] cookies = request.getCookies();
			
			// If the browser has not disabled cookies ...
			if( cookies != null ) {
				for(int i=0 ; i<cookies.length ; i++) {
					String cookieName = cookies[i].getName();
					if( cookieName.equals("style") )
						style=cookies[i].getValue();
					else if( cookieName.equals("language") ) 
						language = cookies[i].getValue();
					else if(cookieName.equals("recordinterface"))
						recordinterface = cookies[i].getValue();
				}
			}
			
			/* If not, store the default values in the cookies */
			
			if( style == null || style.equals("style1") || style.equals("style2")) {
				style = defaultStyle;
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
			
			if(recordinterface == null){
				recordinterface = defaultRecordInterface;
				Cookie recordinterfaceCookie = new Cookie("recordinterface", recordinterface);
				recordinterfaceCookie.setMaxAge(31536000);
				response.addCookie(recordinterfaceCookie);
			}
			
			/* Store them in the session */
			session.setAttribute("style", style);
			session.setAttribute("language", language);
			session.setAttribute("recordinterface", recordinterface);
			
			// Button disconnect
			session.setAttribute("btnDeco", false);		
			
			//university link for banner
			session.setAttribute("univLink", univLink);	
			
			//google analytics
			session.setAttribute("googleAnalyticsAccount", googleAnalyticsAccount);
		}
								
		/* Retrieves the path info from the browser's URL */
		String page = request.getPathInfo();
		
		if( page == null )
			response.sendRedirect(response.encodeRedirectURL("./avc/home"));
		else if( page.equals("/home"))
			displayHomePage(request, response);
		else if (page.equals("/authentication_cas")) 
			authenticationCas(request,response);
		else if (page.equals("/authentication_local")) 
			authenticationLocal(request,response);
		else if( page.equals("/authentication_localvalid"))
            validateAuthenticationLocal(request, response);
		else if(page.equals("/authentication_accountrequest"))
			displayAccountRequestForm(request, response);
		else if(page.equals("/authentication_accountrequestvalid"))
			validateAccountRequest(request,response);
		else if(page.equals("/authentication_forgotpass"))
			displayForgotPassForm(request,response);
		else if(page.equals("/authentication_forgotpassvalid"))
			validateForgotPassForm(request,response);	
		else if(page.equals("/authentication_resetpass"))
			displayResetPassForm(request,response);
		else if(page.equals("/authentication_resetpassvalid"))
			validateResetPassForm(request,response);	
		else if (page.equals("/myspace_home")) 
			displayMyspace(request,response);
		else if(page.equals("/logout")) 
			logout(request,response);
		else if(page.equals("/publication")) 
			displayPublicationPage(request,response);
		else if(page.equals("/publication_validatepublication")) 
			validatePublication(request,response);
		else if( page.equals("/live"))
			displayLivePage(request, response);
		else if( page.equals("/recorded"))
			displayRecordedPage(request, response);
		else if( page.equals("/search"))
			displaySearchResults(request, response);
		else if( page.equals("/tags"))
			displayTagsCourses(request, response);
		else if( page.equals("/rss")) 
			displayRssPage(request, response);
		else if (page.equals("/courses"))
			displayCourses(request, response);
		else if (page.equals("/myspace_editmycourse"))
			displayEditMyCourses(request, response);
		else if( page.equals("/myspace_validatemycourse"))
			validateMyCourse(request, response);
		else if( page.equals("/myspace_upload"))
			uploadAccess(request, response);
		else if( page.equals("/myspace_mediaupload"))
			mediaUpload(request, response);
		else if( page.equals("/myspace_adddocupload"))
			myspaceAddDocUpload(request, response);
		else if( page.equals("/myspace_deleteadddoc"))
			myspaceDeleteAddDoc(request, response);
		else if( page.equals("/myspace_addsubtitles"))
			myspaceAddSubtitles(request, response);
		else if( page.equals("/myspace_deletesubtitles"))
			myspaceDeleteSubtitles(request, response);
		else if( page.equals("/myspace_deletecourse"))
			myspaceDeleteCourse(request, response);
		else if(page.equals("/myspace_changepass")) 
			myspaceChangePass(request, response);
		else if(page.equals("/myspace_changepassvalid")) 
			myspaceChangePassValid(request, response);
		else if(page.equals("/myspace_replacemedia"))
			myspaceReplaceMedia(request, response);	
		else if(page.equals("/myspace_deletereplacemedia"))
			myspaceDeleteReplaceMedia(request, response);	
		else if( page.equals("/add") || page.equals("/UploadClient"))
			addCourse(request, response);
		else if(page.equals("/livestate") || page.equals("/LiveState"))
			liveState(request, response);
		else if(page.equals("/encodagestate"))
			encodageState(request, response);
		else if( page.equals("/courseaccess")) 
			courseAccess(request, response);
		else if( page.equals("/liveaccess"))
			liveAccess(request, response);
		else if( page.equals("/contactUs"))
			contactUs(request,response);
		else if( page.equals("/contactUsSendMail"))
			sendMailContactUs(request, response);
		else if( page.equals("/test"))
			displayTestPage(request, response);
		else if( page.equals("/changestyle"))
			changeStyle(request, response);
		else if( page.equals("/changelanguage"))
			changeLanguage(request, response);
		else if( page.equals("/changeinterface"))
			changeInterface(request, response);
		else if( page.equals("/deletetests"))
			deleteTests(request, response);
		else if( page.equals("/thick_styles"))
			thickStyles(request, response);
		else if( page.equals("/thick_languages"))
			thickLanguages(request, response);
		else if( page.equals("/thick_legal"))
			thickLegal(request, response);
		else if( page.equals("/thick_replacemedia")) 
			thickReplacemedia(request, response);
		else if( page.equals("/thick_download"))
			thickDownload(request, response);
		else if( page.equals("/thick_help"))
			thickHelp(request, response);
		else if( page.equals("/thick_myspace"))
			thickMyspace(request, response);
		else if( page.equals("/liveslide"))
			liveSlide(request, response);
		else if( page.equals("/admin_home"))
			adminHome(request, response);
		else if( page.equals("/admin_courses"))
			adminCourse(request, response);
		else if( page.equals("/admin_editcourse"))
			DisplayAdminEditCourse(request,response);
		else if( page.equals("/admin_deletecourse"))
			adminDeletecourse(request, response);
		else if( page.equals("/admin_validatecourse"))
			validateCourse(request, response, "./admin_courses");
		else if( page.equals("/admin_adddocupload"))
			addDocUpload(request, response);
		else if( page.equals("/admin_deleteadddoc"))
			deleteAddDoc(request, response);
		else if( page.equals("/admin_addsubtitles"))
			addSubtitles(request, response);
		else if( page.equals("/admin_deletesubtitles"))
			deleteSubtitles(request, response);
		else if(page.equals("/admin_replacemedia"))
			adminReplacemedia(request, response);
		else if(page.equals("/admin_deletereplacemedia"))
			adminDeleteReplacemedia(request, response);
		else if( page.equals("/admin_buildings"))
			adminBuildings(request, response);
		else if( page.equals("/admin_addbuilding"))
			adminAddBuilding(request, response);
		else if( page.equals("/admin_editbuilding"))
			adminEditBuilding(request, response);
		else if( page.equals("/admin_deletebuilding"))
			adminDeleteBuilding(request, response);
		else if( page.equals("/admin_validatebuilding"))
			validateBuilding(request, response,"./admin_buildings");
		else if( page.equals("/admin_amphis"))
			adminAmphis(request, response);
		else if( page.equals("/admin_addamphi"))
			adminAddAmphi(request, response);
		else if( page.equals("/admin_editamphi"))
			adminEditAmphi(request, response);
		else if( page.equals("/admin_deleteamphi"))
			adminDeleteAmphi(request, response);
		else if( page.equals("/admin_validateamphi"))
			validateAmphi(request, response,"./admin_amphis");
		else if( page.equals("/admin_users"))
			adminUsers(request,response);
		else if( page.equals("/admin_edituser"))
			adminEditUser(request, response);
		else if( page.equals("/admin_validateuser"))
			validateUser(request, response);
		else if( page.equals("/admin_deleteuser"))
			adminDeleteUser(request, response);
		else if( page.equals("/admin_adduser"))
			adminAddUser(request, response);
		else if(page.equals("/admin_useractivate"))
			userActivate(request,response);
		else if( page.equals("/admin_selections"))
			adminSelections(request, response);
		else if( page.equals("/admin_editselection"))
			adminEditSelection(request, response);
		else if( page.equals("/admin_validateselection"))
			validateSelection(request, response);
		else if( page.equals("/admin_deleteselection"))
			adminDeleteSelection(request, response);
		else if( page.equals("/admin_addselection"))
			adminAddSelection(request, response);
		else if( page.equals("/admin_teachers"))
			adminTeachers(request, response);
		else if( page.equals("/admin_stats"))
			adminStats(request, response);
		else if (page.equals("/admin_jobs"))
			adminJobs(request, response);
		else if (page.equals("/admin_disciplines"))
			adminDisciplines(request, response);
		else if( page.equals("/admin_adddiscipline"))
			adminAddDiscipline(request, response);
		else if( page.equals("/admin_validatediscipline"))
			validateDiscipline(request, response);
		else if( page.equals("/admin_editdiscipline"))
			adminEditDiscipline(request, response);
		else if( page.equals("/admin_deletediscipline"))
			adminDeleteDiscipline(request, response);
		else if( page.equals("/gp_home"))
			gpHome(request, response);
		else if( page.equals("/gp_editbuilding"))
			gpEditBuilding(request, response);
		else if( page.equals("/gp_validatebuilding"))
			validateBuilding(request, response,"./gp_home");
		else if( page.equals("/gp_amphis"))
			gpAmphis(request, response);
		else if( page.equals("/gp_editamphi"))
			gpEditAmphi(request, response);
		else if( page.equals("/gp_validateamphi"))
			validateAmphi(request, response,"./gp_amphis");
		else if(page.equals("/findTracks"))
				displayFindTracks(request, response);
		else if(page.equals("/findStats"))
			displayFindStats(request, response);
		else
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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

		/* Calls the DoGet method */
		doGet(request, response);
	}
	
	/**
	 * Method to display the home page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayHomePage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		/* initializes the model */
		request.setAttribute("teachers", service.getTeachers());
		request.setAttribute("disciplines", service.getAllDiscipline());
		request.setAttribute("lastcourses", service.getNLastCourses(lastCourseNumber, testKeyWord2, testKeyWord3));
		request.setAttribute("selectioncourses", service.getNSelectionCourses(selectionCourseNumber, testKeyWord2, testKeyWord3));
		request.setAttribute("collectioncourses", service.getNFormationCourses(collectionCourseNumber, testKeyWord2, testKeyWord3));
		Selection collection = service.getSelection(1);
		String showForm = collection!=null ? service.getFormationFullName(collection.getFormationcollection()) : "";
		request.setAttribute("collectionname", showForm!=null ? showForm : "");
		request.setAttribute("collectioncode", collection!=null ? collection.getFormationcollection() : "");		
		request.setAttribute("rssFileName", rssName + ".xml");
		request.setAttribute("allTags", service.getAllTags());
		request.setAttribute("mostPopularTags", service.getTagCloud(service.getMostPopularTags()));
		request.setAttribute("rssfiles", service.getRssFileList(rssTitle, rssName, true));
		request.setAttribute("levels", service.getAllLevels());
		
		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/home");
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		/* Displays the view */
		getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}
	
	
	/**
	 * Method to authenticate a cas user
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void authenticationCas(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String casUser = (String) session.getAttribute(edu.yale.its.tp.cas.client.filter.CASFilter.CAS_FILTER_USER);		

		// If we have the cas ticket
		if(casUser!=null) {
			// We get the user login from the session
			// or we get the user from the database
				User user = service.getUser(casUser);
				
				// Gets user's infos from the ldap
				List<String> userInfos = null;		
				try { userInfos= service.getLdapUserInfos(casUser);} catch (Exception e) { logger.warn("Ldap user error",e); }
								
				// If the user is null, create an account
				if(user==null) {

					if (userInfos!=null && !userInfos.isEmpty())
						service.addUser(new User(0,casUser,userInfos.get(0),userInfos.get(1),userInfos.get(2),userInfos.get(3),userInfos.get(4),"ldap",true,userInfos.get(5),userInfos.get(6)));
					else 
						service.addUser(new User(0,casUser,null,null,null,null,null,"ldap",true,null,null));
				}
				else {
					if (userInfos!=null && !userInfos.isEmpty()) {
						
						String email = userInfos.get(0);
						String firstname = userInfos.get(1);
						String lastname = userInfos.get(2);
						String profile = userInfos.get(3);
						String establishment = userInfos.get(4);
						String etp = userInfos.get(5);
						
						// Check values between ldap and database. Modify user if necessary.
						if(user.getEmail()==null || !user.getEmail().equals(email) || user.getFirstname()==null || !user.getFirstname().equals(firstname) || 
								user.getLastname()==null || !user.getLastname().equals(lastname) || user.getProfile()==null || !user.getProfile().equals(profile) ||
								user.getEstablishment()==null ||!user.getEstablishment().equals(establishment)||user.getEtp()==null||!user.getEtp().equals(etp)) {
							user.setEmail(email);
							user.setFirstname(firstname);
							user.setLastname(lastname);
							user.setProfile(profile);
							user.setEstablishment(establishment);
							user.setEtp(etp);
							service.modifyUser(user);
						}
					}
				}
				
				// Disconnect user LOCAL if exist
				if(session.getAttribute("$userLocalLogin")!=null) {	
					session.removeAttribute("$userLocalLogin");
				}
							
				session.setAttribute("btnDeco", true);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeLogin, null);
				}	
				
				//REDIRECTION
				// Publication
				if(request.getParameter("returnPage")!=null && request.getParameter("returnPage").equals("publication")) {
					request.setAttribute("publication_type", "serverCas");
					getServletContext().getRequestDispatcher("/avc/publication").forward(request, response);
				}
				//Myspace
				else if(request.getParameter("returnPage")!=null && request.getParameter("returnPage").equals("myspace")) {
					getServletContext().getRequestDispatcher("/avc/myspace_home").forward(request, response);
				}	
				//Course access for medicine
				else if(request.getParameter("returnPage")!=null && request.getParameter("returnPage").equals("courseaccess")) {
					int courseid = Integer.parseInt( (String) request.getParameter("id"));
					String type = (String) request.getParameter("type");
					//getServletContext().getRequestDispatcher("/avc/courseaccess").forward(request, response);
					response.sendRedirect("./courseaccess?id="+courseid+(type != null ? "&type="+ type : ""));
				}
				//Live access for medicine
				else if(request.getParameter("returnPage")!=null && request.getParameter("returnPage").equals("liveaccess")) {
					String ip = request.getParameter("amphi");
					//getServletContext().getRequestDispatcher("/avc/liveaccess").forward(request, response);
					response.sendRedirect("./liveaccess?amphi="+ip);
				}
				// from ENT
				else {
					response.sendRedirect("./home");
				}
				
			}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "You don't have access to this page");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			
		}
		
	}
	
	
	/**
	 * Method to display the myspace page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayMyspace(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
				
		User user = service.getSessionUser(session);
		
		// if the user is not authenticated, redirection to authentication_cas
		if(user==null) {
			response.sendRedirect("./authentication_cas?returnPage=myspace");
		}
		// if user is present and activate
		else if(user.isActivate()) {
			
			int start = 0;
			int pageNumber;

			/* initializes the model */
			if( request.getParameter("page") != null) {
				pageNumber = Integer.parseInt( request.getParameter("page"));
				start = recordedCourseNumber * (pageNumber - 1) ;
			}
			else
				pageNumber = 1;

			request.setAttribute("page", pageNumber);


		String keywords = service.encodeString(request.getParameter("keywords"));
		String paramsUrl = "";
		if(keywords==null || keywords.equals("") ) {
			request.setAttribute("courses", service.getCoursesByUser(user,recordedCourseNumber,start,false));
			request.setAttribute("items", service.getCourseNumber(user));
			session.setAttribute("previousPage", "/myspace_home?page=" + pageNumber);
		}
		else {
			request.setAttribute("courses", service.getCoursesByUser(keywords, user,recordedCourseNumber,start,false));
			request.setAttribute("items", service.getCourseNumber(keywords, user));
			request.setAttribute("keywords", keywords);
			paramsUrl = "?keywords=" + keywords;
			request.setAttribute("paramsUrl", paramsUrl);
			session.setAttribute("previousPage", "/myspace_home" + paramsUrl +"&page=" + pageNumber);
		}

			request.setAttribute("number", recordedCourseNumber);
			request.setAttribute("resultPage", "myspace_home");
			request.setAttribute("rssfiles", service.getRssFileList(rssTitle, rssName, true));
			request.setAttribute("user", user);

			

			// Button disconnect
			session.setAttribute("btnDeco", true);

			// log stats
			if(logstats) {
				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
			}	
			
			/* Displays the view */ 
			getServletContext().getRequestDispatcher("/WEB-INF/views/myspace/myspace_home.jsp").forward(request, response);
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "You don't have access to this page");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}

	}
	
	/**
	 * Method to logout the user
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		// Button disconnect
		session.setAttribute("btnDeco", false);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeLogout, null);
		}	
		
		// Authentication CAS
		if(session.getAttribute(edu.yale.its.tp.cas.client.filter.CASFilter.CAS_FILTER_USER)!=null) {		
			session.removeAttribute(edu.yale.its.tp.cas.client.filter.CASFilter.CAS_FILTER_USER);
			response.sendRedirect(casLogoutUrl);
		}
		
		// Authentication local
		if(session.getAttribute("$userLocalLogin")!=null) {	
			session.removeAttribute("$userLocalLogin");
			response.sendRedirect("./home");
		}
		
	}
	
	/**
	 * Method to display publication page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayPublicationPage(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {  

		if(request.getParameter("publication_type")!=null) {
			request.setAttribute("publication_type", request.getParameter("publication_type"));	
		}

		if(request.getParameter("mediapath")!=null) {
			session.setAttribute("publication_mediapath", request.getParameter("mediapath"));
		}

		// Get the form parameters if error message
		request.setAttribute("title", request.getParameter("title"));
		request.setAttribute("description", request.getParameter("description"));
		request.setAttribute("name", request.getParameter("name"));
		request.setAttribute("firstname", request.getParameter("firstname"));
		request.setAttribute("ue", request.getParameter("ue"));
		request.setAttribute("genre", request.getParameter("genre"));
		request.setAttribute("tags", request.getParameter("tags"));
		request.setAttribute("visible", request.getParameter("visible"));
		request.setAttribute("download", request.getParameter("download"));
		request.setAttribute("restrictionuds", request.getParameter("restrictionuds"));
		request.setAttribute("levelSelected", request.getParameter("level"));
		request.setAttribute("discSelected", request.getParameter("component"));
		//request.setAttribute("permission", request.getParameter("permission"));

		User user = service.getSessionUser(session);

		// if user is present and activate
		if(user!=null && user.isActivate()) {

			request.setAttribute("user", user);

			// Button disconnect
			session.setAttribute("btnDeco", true);
		}

		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/publication");

		/* the discipline box */
		request.setAttribute("disciplines", service.getAllDiscipline());

		/* the levels box */
		request.setAttribute("levels", service.getAllLevels());

		// serveur url for test url
		request.setAttribute("serverUrl", serverUrl);

		// publication free
		request.setAttribute("pubFree", pubFree);
		
		// publication test
		request.setAttribute("pubTest", pubTest);

		// univ acronym
		request.setAttribute("univAcronym", univAcronym);
		// univ name
		request.setAttribute("univName", univName);
				
		// testKeyWord1. Uncapitalize the fist char
		request.setAttribute("testKeyWord1", (testKeyWord1.substring(0, 1).toLowerCase()+testKeyWord1.substring(1)));

		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		/* Displays the view */ 
		getServletContext().getRequestDispatcher("/WEB-INF/views/publication.jsp").forward(request, response);


	}
	
	/**
	 * Method to validate the publication form
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void validatePublication(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
				
		// Test form parameters
		if(request.getParameter("mediapath")==null || request.getParameter("mediapath").equals("")) {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Mediapath is not found!");
			getServletContext().getRequestDispatcher("/avc/publication").forward(request, response);
		}
		else if(request.getParameter("title")==null || request.getParameter("title").equals("")) {
			request.setAttribute("messagetype", "error");
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
			request.setAttribute("message", bundle.getString("err_title"));
			getServletContext().getRequestDispatcher("/avc/publication").forward(request, response);
		}
		else if(request.getParameter("name")==null || request.getParameter("name").equals("")) {
			request.setAttribute("messagetype", "error");
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
			request.setAttribute("message", bundle.getString("err_name"));
			getServletContext().getRequestDispatcher("/avc/publication").forward(request, response);
		}
		else if(request.getParameter("level")==null || request.getParameter("level").equals("")) {
			request.setAttribute("messagetype", "error");
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
			request.setAttribute("message", bundle.getString("err_lvl"));
			getServletContext().getRequestDispatcher("/avc/publication").forward(request, response);
		}
		else if(request.getParameter("component")==null || request.getParameter("component").equals("")) {
			request.setAttribute("messagetype", "error");
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
			request.setAttribute("message", bundle.getString("err_component"));
			getServletContext().getRequestDispatcher("/avc/publication").forward(request, response);
		}
		// If the formulaire is valid
		else {
			
			// log stats
			if(logstats) {
				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
			}	
			
			getServletContext().getRequestDispatcher("/avc/UploadClient").forward(request, response);
		}
	}
	
	
	/**
	 * Method to display the live page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayLivePage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {  
		/* initializes the model */
		request.setAttribute("buildings", service.getBuildings());
		
		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/live");
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		/* Displays the view */ 
		getServletContext().getRequestDispatcher("/WEB-INF/views/live.jsp").forward(request, response);
	}
	
	/**
	 * Method to display the recorded page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayRecordedPage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
				
		int start = 0;
		int pageNumber;
		
		/* initializes the model */
		if( request.getParameter("page") != null) {
			pageNumber = Integer.parseInt( request.getParameter("page"));
			start = recordedCourseNumber * (pageNumber - 1) ;
		}
		else
			pageNumber = 1;
		
		request.setAttribute("page", pageNumber);
		request.setAttribute("teachers", service.getTeachers());
		//request.setAttribute("formations", service.getFormations());
		request.setAttribute("disciplines", service.getAllDiscipline());
		request.setAttribute("courses", service.getCourses(recordedCourseNumber, start, testKeyWord1, testKeyWord2, testKeyWord3));
		request.setAttribute("items", service.getCourseNumber(testKeyWord1, testKeyWord2, testKeyWord3));
		request.setAttribute("number", recordedCourseNumber);
		request.setAttribute("resultPage", "recorded");
		request.setAttribute("allTags", service.getAllTags());
		request.setAttribute("mostPopularTags", service.getTagCloud(service.getMostPopularTags()));
		request.setAttribute("rssfiles", service.getRssFileList(rssTitle, rssName, true));
		request.setAttribute("levels", service.getAllLevels());
		
		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/recorded?page=" + pageNumber);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		/* Displays the view */
		getServletContext().getRequestDispatcher("/WEB-INF/views/recorded.jsp").forward(request, response);
	}
	
	/**
	 * Method to display the rss page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayRssPage(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {  
		
		//Number of Teachers with RSS, for the list in the jsp page
		request.setAttribute("nbrTeachersRss",service.getTeachers().size());
		// Rss Files
		request.setAttribute("rssfiles", service.getRssFileList(rssTitle, rssName, false));
		// Server Url
		request.setAttribute("serverUrl", serverUrl);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
				
		/* Displays the view */ 
		getServletContext().getRequestDispatcher("/WEB-INF/views/rss.jsp").forward(request, response);
	}
	
	/**
	 * Method to display the test page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayTestPage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		int start = 0;
		int pageNumber;
		
		/* initializes the model */
		if( request.getParameter("page") != null) {
			pageNumber = Integer.parseInt( request.getParameter("page"));
			start = recordedCourseNumber * (pageNumber - 1) ;
		}
		else
			pageNumber = 1;
		
		request.setAttribute("page", pageNumber);
		request.setAttribute("teachers", service.getTeachers());
		//request.setAttribute("formations", service.getFormations());
		request.setAttribute("disciplines", service.getAllDiscipline());
		request.setAttribute("courses", service.getTests(recordedCourseNumber, start, testKeyWord1, testKeyWord2, testKeyWord3));
		request.setAttribute("items", service.getTestNumber(testKeyWord1, testKeyWord2, testKeyWord3));
		request.setAttribute("number", recordedCourseNumber);
		request.setAttribute("resultPage", "test");
		request.setAttribute("allTags", service.getAllTags());
		request.setAttribute("mostPopularTags", service.getTagCloud(service.getMostPopularTags()));
		request.setAttribute("rssfiles", service.getRssFileList(rssTitle, rssName, true));
		request.setAttribute("levels", service.getAllLevels());
		
		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/test?page=" + pageNumber);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		/* Displays the view */
		getServletContext().getRequestDispatcher("/WEB-INF/views/recorded.jsp").forward(request, response);
	}
	
	/**
	 * Method to display the results of the search
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displaySearchResults(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String paramsUrl="?search=true";
						
		// to set typevideo and typeaudio nochecked
		if(request.getParameter("typevideo") != null && request.getParameter("typevideo").equals("nochecked")) 
			paramsUrl=paramsUrl+"&typevideo=nochecked";
		if( request.getParameter("typeaudio") != null && request.getParameter("typeaudio").equals("nochecked")) 
			paramsUrl=paramsUrl+"&typeaudio=nochecked";
		
				
		// if UTF8 else ISO
		if(request.getParameter("fullname") != null && ! request.getParameter("fullname").equals("*"))
			paramsUrl=paramsUrl+"&fullname="+UrlUtil.encode(service.encodeString(request.getParameter("fullname")), "UTF-8");			
		if( request.getParameter("discipline") != null && ! request.getParameter("discipline").equals("*") ) 
			paramsUrl=paramsUrl+"&discipline="+UrlUtil.encode(service.encodeString(request.getParameter("discipline")), "UTF-8");
		if( request.getParameter("level") != null && ! request.getParameter("level").equals("*"))
			paramsUrl=paramsUrl+"&level="+UrlUtil.encode(service.encodeString(request.getParameter("level")), "UTF-8");
		if( request.getParameter("keyword") != null && ! request.getParameter("keyword").equals(""))
			paramsUrl=paramsUrl+"&keyword="+UrlUtil.encode(service.encodeString(request.getParameter("keyword")), "UTF-8");
				
						
		// Redirect if the form posted
		if( request.getMethod().equals("POST") ) { 
			
			
			// to set typevideo and typeaudio nochecked when form post
			if(request.getParameter("video") == null) 
				paramsUrl=paramsUrl+"&typevideo=nochecked";
			if( request.getParameter("audio") == null) 
				paramsUrl=paramsUrl+"&typeaudio=nochecked";
			
						
			String redirect = "./search"+paramsUrl;
			response.sendRedirect(redirect);
		}

		// Print the results
		else {

			int start = 0;
			int pageNumber = 1;

			HashMap<String, String> params = new HashMap<String, String>();

			// The user has clicked on a pagination link
			if(request.getParameter("page")!=null ) {
				pageNumber = request.getParameter("page")!=null ? Integer.parseInt(request.getParameter("page")) : 1;
				start = recordedCourseNumber * (pageNumber - 1) ;
			}

			
			
			/* Puts the search paramaters in a HashMap object */
			if( request.getParameter("typeaudio") != null && request.getParameter("typeaudio").equals("nochecked") && request.getParameter("typevideo") != null && request.getParameter("typevideo").equals("nochecked")) {
				params.put("type", "");
			}
			else if( request.getParameter("typeaudio") != null && request.getParameter("typeaudio").equals("nochecked") && request.getParameter("typevideo") == null) {
				params.put("type", "video");
				request.setAttribute("video", "checked");
			}
			
			else if( request.getParameter("typevideo") != null && request.getParameter("typevideo").equals("nochecked") && request.getParameter("typeaudio") == null) {
				params.put("type", "audio");
				request.setAttribute("audio", "checked");
			}
			else {
				request.setAttribute("audio", "checked");
				request.setAttribute("video", "checked");
			}
		
			
			if( request.getParameter("fullname") != null && ! request.getParameter("fullname").equals("*") ) {
				// if UTF8 else ISO
				params.put("fullname", service.encodeString(WordUtils.capitalizeFully(request.getParameter("fullname"))));
				request.setAttribute("nameSelected", params.get("fullname"));
			}

			if( request.getParameter("discipline") != null && ! request.getParameter("discipline").equals("*") ) {
				// if UTF8 else ISO
				params.put("discipline", service.encodeString(request.getParameter("discipline")));				
				request.setAttribute("discSelected", params.get("discipline"));	
			}

			if( request.getParameter("level") != null && ! request.getParameter("level").equals("*") ) {
				// if UTF8 else ISO
				params.put("level", service.encodeString(request.getParameter("level")));
				request.setAttribute("levelSelected", params.get("level"));	
			}

			if( request.getParameter("keyword") != null && ! request.getParameter("keyword").equals("") ) {
				// if UTF8 else ISO
				params.put("keyword", service.encodeString(request.getParameter("keyword")));
				request.setAttribute("keyword", params.get("keyword"));
			}

			request.setAttribute("page", pageNumber);
			request.setAttribute("teachers", service.getTeachers());
			request.setAttribute("disciplines", service.getAllDiscipline());
			request.setAttribute("courses", service.getCourses(params, recordedCourseNumber, start, testKeyWord1, testKeyWord2, testKeyWord3));
			request.setAttribute("items", service.getCourseNumber(params,testKeyWord1, testKeyWord2, testKeyWord3));
			request.setAttribute("number", recordedCourseNumber);
			request.setAttribute("resultPage", "search");
			request.setAttribute("allTags", service.getAllTags());
			request.setAttribute("mostPopularTags", service.getTagCloud(service.getMostPopularTags()));
			request.setAttribute("rssfiles", service.getRssFileList(rssTitle, rssName, true));
			request.setAttribute("levels", service.getAllLevels());

			/* Saves the page for the style selection thickbox return */
			session.setAttribute("previousPage", "/search" + paramsUrl +"&page=" + pageNumber);

			
			// params url
			request.setAttribute("paramsUrl", paramsUrl);
			
			// log stats
			if(logstats) {
				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
			}	

			/* Displays the view */
			getServletContext().getRequestDispatcher("/WEB-INF/views/recorded.jsp").forward(request, response);
		}
	}
	
	/**
	 * Method to display the courses page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayCourses(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String paramsUrl="?search=true";
		
		if( request.getParameter("author") != null) {
			paramsUrl = paramsUrl + "&fullname=" + WordUtils.capitalizeFully(request.getParameter("author"));
		}
		
		if( request.getParameter("formation") != null) {
			
			String formation = new String(request.getParameter("formation"));
			paramsUrl = paramsUrl + "&level=" + service.getLevelCodeByFormation(formation) + "&discipline=" +  service.getComponentCodeByFormation(formation);
			
		}
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
				
		String redirect = "./search"+paramsUrl;
		response.sendRedirect(redirect);
		
	}
	
	/**
	 * Method to display courses by tags
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayTagsCourses(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
					
		if( request.getParameter("tags") != null)  {
						
			int pageNumber = request.getParameter("page")!=null ? Integer.parseInt( request.getParameter("page")):1;
			int start = recordedCourseNumber * (pageNumber - 1) ;
				
			//if UTF8 else ISO
			String tags = service.encodeString(request.getParameter("tags"));
		
			
			List<String> listTags = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(tags);
			while(st.hasMoreTokens()) {
				listTags.add(st.nextToken());
			}
															
			String rssNamePar=rssName;
			
			request.setAttribute("audio", "checked");
			request.setAttribute("video", "checked");
													
			request.setAttribute("page", pageNumber);
			request.setAttribute("teachers", service.getTeachers());
			//request.setAttribute("formations", service.getFormations());	
			request.setAttribute("disciplines", service.getAllDiscipline());
			request.setAttribute("courses", service.getCoursesByTags(listTags, recordedCourseNumber, start, testKeyWord1, testKeyWord2, testKeyWord3));
			request.setAttribute("items", service.getCourseNumber(listTags,testKeyWord1, testKeyWord2, testKeyWord3));
			request.setAttribute("number", recordedCourseNumber);
			request.setAttribute("resultPage", "tags"); 
			request.setAttribute("rssfiles", service.getRssFileList(rssTitle, rssNamePar, true));
			request.setAttribute("allTags", service.getAllTags());
			request.setAttribute("mostPopularTags", service.getTagCloud(service.getMostPopularTags()));
			request.setAttribute("levels", service.getAllLevels());
			request.setAttribute("listTags",listTags);
			request.setAttribute("listTagsSize",listTags.size());
			request.setAttribute("tags",tags);
			
			/* Saves the page for the style selection thickbox return */
			session.setAttribute("previousPage", "/tags?tags="+tags+"&page=" + pageNumber);
			
			listTags=null; //set list null for memory
			st = null;
			
			// log stats
			if(logstats) {
				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
			}	
						
			/* Displays the view */
			getServletContext().getRequestDispatcher("/WEB-INF/views/recorded.jsp").forward(request, response);
		}
		else { // if the session is not valid anymore
			response.sendRedirect("./recorded");
		}
	}
	
	/**
	 * Method to validate my course form
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void validateMyCourse(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {	
						
		User user = service.getSessionUser(session);
		
		if(user!=null && user.isActivate()) {

			// Get the course
			Course c = service.getCourse(Integer.parseInt(request.getParameter("courseid")));

			// Check the user id
			if(c.getUserid()==user.getUserid()) {

				// Button disconnect
				session.setAttribute("btnDeco", true);
				
				boolean formValid = true;
				String message = "";
				String returnUrl = request.getParameter("returnUrl");
				ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
				
				// Check the form
				if(request.getParameter("slidesoffset")!=null && !request.getParameter("slidesoffset").equals("")) {
									
					try {
						Integer.valueOf(request.getParameter("slidesoffset"));				
					}
					catch(NumberFormatException e) {
						message = bundle.getString("error_offset");
						formValid = false;
					}	
					
				}	
					
				// validate course
				if (formValid) {
					String redirectUrl = session.getAttribute("previousPage")!=null ?
						"."+session.getAttribute("previousPage") : "./myspace_home";
					validateCourse(request,response,redirectUrl);
				}
				else {
					request.setAttribute("messagetype", "error");
					request.setAttribute("message", message);
					getServletContext().getRequestDispatcher(returnUrl).forward(request, response);			
				}
			}
			else {
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "You don't have access to this page");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			
			}
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "You don't have access to this page");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}
	}
	
	/**
	 * Method to edit my course
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayEditMyCourses(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
				
		User user = service.getSessionUser(session);
		
		if(user!=null && user.isActivate()) {

			// Get the course
			Course c = service.getCourse(Integer.parseInt(request.getParameter("id")));
			
			// Check user id
			if(c.getUserid()==user.getUserid()) {

				// Getting tags of the course
				String tags="";
				List<Tag> listTags = service.getTagsByCourse(c);
				for(int i=0;i<listTags.size();i++) {
					if(i==0)
						tags=listTags.get(i).getTag();
					else
						tags=tags + " " + listTags.get(i).getTag();
				}

				request.setAttribute("tags", tags);
				request.setAttribute("course", c);
				request.setAttribute("posturl", "./myspace_validatemycourse");
				request.setAttribute("gobackurl", session.getAttribute("previousPage")!=null ?
					"."+session.getAttribute("previousPage") : "./myspace_home");

				// Button disconnect
				session.setAttribute("btnDeco", true);

				// the discipline box
				request.setAttribute("disciplines", service.getAllDiscipline());
				
				// the discSelected
				request.setAttribute("discSelected", service.getComponentCodeByFormation(c.getFormation()));
				
				// the levels box
				request.setAttribute("levels", service.getAllLevels());

				// the levelSelected
				request.setAttribute("levelSelected", service.getLevelCodeByFormation(c.getFormation()));
				
				// univ acronym
				request.setAttribute("univAcronym", univAcronym);
				// univ name
				request.setAttribute("univName", univName);
				
				request.setAttribute("mediaLst", c.getMedias());
				
				// check if we have an additional video job in process
				Job j = service.getJob(c.getCourseid(), "ADDV");
				boolean addvinprocess = (j!=null && !j.getStatus().equals("done"));
				request.setAttribute("addvinprocess", addvinprocess);
				
				// show additional video block if the record is from the client
				request.setAttribute("showAddVidBlock", c.isAudioClient() || c.isVideoClient());
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeAccess, null);
				}	
				
				/* Displays the view */
				getServletContext().getRequestDispatcher("/WEB-INF/views/myspace/myspace_editmycourse.jsp").forward(request, response);


			}
			else {
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "You don't have access to this page");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			
			}
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "You don't have access to this page");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			
		}
	}
		
	
	
	/**
	 * Method to add a course
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void addCourse(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
						
		String title, description, mediapath, media, timing, name, firstname, formation, genre, login, email,tags,level,component;
		boolean visible,restrictionuds, download;
		String message, message2, ahref;
		message=message2=ahref="";
		String messageType = "information";
		
		/* The client sends parameters in the ISO8859-15 encoding */
		request.setCharacterEncoding("ISO8859-15");
		
		if(request.getParameter("publication_type")!=null) {
			request.setCharacterEncoding("UTF-8");
		}
		
		/* Retrieves the parameters sent by the client */
		mediapath = request.getParameter("mediapath");
		timing = request.getParameter("timing");
		description = request.getParameter("description")!=null ? service.cleanString(request.getParameter("description")) : "";
		title = request.getParameter("title")!=null ? service.cleanString(request.getParameter("title")) : "";
		name = request.getParameter("name")!=null ? service.cleanString(request.getParameter("name")) : "";
		firstname = request.getParameter("firstname")!=null ? service.cleanString(request.getParameter("firstname")) : "";
		//formation = request.getParameter("ue")!=null ? service.cleanString(request.getParameter("ue")) : "";
		genre = request.getParameter("genre")!=null ? request.getParameter("genre") : "";
		login = request.getParameter("login")!=null ? request.getParameter("login") : "";
		email = request.getParameter("email")!=null ? request.getParameter("email") : "";
		tags = service.cleanString(request.getParameter("tags"));
		level = request.getParameter("level")!=null ? request.getParameter("level") : "";
		component = request.getParameter("component")!=null ? request.getParameter("component") : "";
				
		
		if(request.getParameter("publication_type")!=null) {
			visible = request.getParameter("visible") != null ? true : false;
			download = request.getParameter("download") != null ? true : false;
			restrictionuds = request.getParameter("restrictionuds") != null ? true : false;
		}
		// for old client
		else {
			visible = true;
			download = true;
			restrictionuds = false;
		}
						
		/* Verifies that all essential parameters are sent, cancels the upload if not */
		if(mediapath != null && !mediapath.equals("")) {
			
			if( timing == null )
				timing = "n-1";
			
			String clientIP = request.getRemoteAddr();
						
			/* Retrieves the filename from the media path */
			media = mediapath.substring(mediapath.lastIndexOf("\\") + 1, mediapath.length());
			if( media.length() == mediapath.length() )
				media = mediapath.substring(mediapath.lastIndexOf("/") + 1, mediapath.length());
			
			message += "Media : " + media + "<br/>";
			
			
			// Generate pseudo code etp for formation
			formation = service.getFormationByCodes(level, component);
			
			Course c;
			User user=null; // user audiovideocours
			
			try {
				// Login with 1.12 client
				if(!login.equals("")) {	
					// getting the user
					user = service.getUser(login);
				}
							
				// Create the course course										
				c = new Course(
						service.getNextCoursId(),
						new Timestamp(new Date().getTime()),
						null, // The type can't be set yet
						title.equals("") ? null : title,
						description.equals("") ? null : description,
						formation.equals("") ? null : formation,
						name.equals("") ? null : name,
						(firstname == null || firstname.equals("")) ? null : firstname,
						clientIP,
						0, // The duration can't be set yet
						(genre == null || genre.equals("")) ? null : genre,
						visible,
						0,
						timing,
						user!=null ? user.getUserid() : null,
						null,
						download,
						restrictionuds,
						0, // mediatype can't be set yet
						volume,
						null, // The date recorded can't be set yet
						null
				);
					
				service.addCourse(c, media, tags, serverUrl, sepEnc, coursesFolder);
				
				ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
				
				String access_url = recordedInterfaceUrl + "?id="+c.getCourseid();
				String formation_fullname = !formation.equals("") ? service.getFormationFullName(formation) : "";
				
				String emailUserSubject = bundle.getString("email_addcourse_user_subject");
				String emailUserMessage = MessageFormat.format(bundle.getString("email_addcourse_user_message"), 
						access_url, title, description, name, firstname, formation_fullname, genre, supportLink, docLink, 
						access_url, title, description, name, firstname, formation_fullname, genre, supportLink, docLink);
				
	
				//if the email from av client is present, send an email
				if(email!=null && !email.equals("")) {
					service.sendMail(emailUserSubject,emailUserMessage,email);
				}
								
				// If course is present in the recorded page
				if(c.isVisible() && (c.getGenre()!=null ? !c.getGenre().toUpperCase().equals(testKeyWord1.toUpperCase()) : true) && (c.getTitle()!=null ? !c.getTitle().toUpperCase().startsWith(testKeyWord2.toUpperCase()) : false)) {
					// Sending email for admins
					String emailAdminSubject = "a new course on AudioVideoCast";
					String emailAdminMessage = "Dear Admin,\n\nA course named \"" + c.getTitle() +"\" will be published on "+ recordedInterfaceUrl + "?id="+c.getCourseid() + (c.getName()!=null ? "\n\nAuthor:"+c.getName() + (c.getFirstname()!=null ? " " + c.getFirstname() : "") : "") + (email!=null ? "\n\nEmail:"+email : "") + (c.getGenre()!=null ? "\n\nPassword:"+c.getGenre() : "") + "\n\nBest Regards,\n\nAudioVideoCast Administrator" ;
					if(adminEmail1!=null && !adminEmail1.equals(""))
						service.sendMail(emailAdminSubject,emailAdminMessage,adminEmail1);
					if(adminEmail2!=null && !adminEmail2.equals(""))
						service.sendMail(emailAdminSubject,emailAdminMessage,adminEmail2);
					if(adminEmail3!=null && !adminEmail3.equals(""))
						service.sendMail(emailAdminSubject,emailAdminMessage,adminEmail3);
				}
												
				message = bundle.getString("addcourse_message1a")+" \"" + c.getTitle() +"\" "+bundle.getString("addcourse_message1b")+": ";
				ahref = recordedInterfaceUrl + "?id="+c.getCourseid();
				message2 = bundle.getString("addcourse_message2");
			
				
				request.setAttribute("messagetype2", messageType);
				request.setAttribute("message2", message2);
				request.setAttribute("ahref", ahref);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeAdd, media);
				}	

			}
			catch( DaoException de) {
				messageType = "error";
				message = "Error : Database error";
			}
								
		}
		else {
			messageType = "error";
			message = "Error: One ore more parameters have not been given";
		}
		
		request.setAttribute("messagetype", messageType);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
	}
	
	/**
	 * Method to display the upload access page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void uploadAccess(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		String forwardUrl = "/WEB-INF/views/myspace/myspace_uploadpage.jsp";
		
		User user = service.getSessionUser(session);


		// Gets the user's login from the session		
		if(user!=null && user.isActivate()) {
			
			if(request.getParameter("publication_type")!=null) {
				request.setAttribute("publication_type", request.getParameter("publication_type"));	
			}
			
			request.setAttribute("gobackurl", "./myspace_home");
			request.setAttribute("user", user);		
			session.setAttribute("btnDeco", true);
			session.setAttribute("previousPage", "/myspace_upload");
			
			/* the discipline box */
			request.setAttribute("disciplines", service.getAllDiscipline());
			
			/* the levels box */
			request.setAttribute("levels", service.getAllLevels());
			
			// univ acronym
			request.setAttribute("univAcronym", univAcronym);
			// univ name
			request.setAttribute("univName", univName);
			// serveur url for test url
			request.setAttribute("serverUrl", serverUrl);
			// publication test
			request.setAttribute("pubTest", pubTest);
			// testKeyWord1. Uncapitalize the fist char
			request.setAttribute("testKeyWord1", (testKeyWord1.substring(0, 1).toLowerCase()+testKeyWord1.substring(1)));

								
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
			request.setAttribute("err_title", bundle.getString("err_title"));
			request.setAttribute("err_name", bundle.getString("err_name"));
			request.setAttribute("err_lvl", bundle.getString("err_lvl"));
			request.setAttribute("err_component", bundle.getString("err_component"));
			request.setAttribute("err_file", bundle.getString("err_file"));
			request.setAttribute("err_fileformat", bundle.getString("err_fileformat"));
			
			request.setAttribute("uploadFormats", uploadFormats);
			
			// log stats
			if(logstats) {
				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
			}	
			
			getServletContext().getRequestDispatcher(forwardUrl).forward(request, response);
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "You don't have access to this page");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			
		}
	}
	
	/**
	 * Method to upload a media
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@SuppressWarnings("unchecked")
	private void mediaUpload(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		// Attribute
		String title, description, name, firstname, date, formation, genre,tags, fileName, level, component;
		title = description = name = firstname = date = formation = genre = tags = fileName = level = component = "";
		//boolean hq=false;
		boolean visible, restrictionuds, download;
		//boolean permission = false;
		visible=restrictionuds=download=false;
		String message = "";
		String messageType = "information";
		String requestDispatcher = "/WEB-INF/views/message.jsp";
		Calendar cal = new GregorianCalendar();        	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = new Date();
		boolean continuer=true;

		User user = service.getSessionUser(session);

		if(user!=null && user.isActivate()) {

			if( ServletFileUpload.isMultipartContent(new ServletRequestContext(request)) ) {

				try {
					/* Prepares to parse the request to get the different elements of the POST */
					FileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload upload = new ServletFileUpload(factory);
					List<FileItem> items = upload.parseRequest(request);

					/* Processes the different elements */
					Iterator<FileItem> iter = items.iterator();
					while (iter.hasNext() && continuer) {
						FileItem item = (FileItem) iter.next();

						/* If the element is a form field, retrieves the info */
						if (item.isFormField()) {

							if(item.getFieldName().equals("title"))
								title = item.getString("UTF8");
							else if(item.getFieldName().equals("description"))
								description = item.getString("UTF8");
							else if(item.getFieldName().equals("date")){
								date = item.getString("UTF8");
								try {
									d = sdf.parse(date);					        	
								}
								catch( ParseException pe) {
									logger.error("Parse date error",pe);
								}
								finally {
									cal.setTime(d);
								}
							}
							/*else if(item.getFieldName().equals("formation"))
								formation = item.getString("UTF8");*/
							else if(item.getFieldName().equals("level"))
								level = item.getString("UTF8");
							else if(item.getFieldName().equals("component"))
								component = item.getString("UTF8");
							else if(item.getFieldName().equals("genre")) {
								genre = item.getString("UTF8");
							}
							else if(item.getFieldName().equals("name")) {
								name = item.getString("UTF8");
							}
							else if(item.getFieldName().equals("firstname")) {
								firstname = item.getString("UTF8");
							}
							/*else if(item.getFieldName().equals("hd")) {
								hq=true;
							}*/
							else if(item.getFieldName().equals("visible")) {
								visible=true;
							}
							else if(item.getFieldName().equals("download")) {
								download=true;
							}
							else if(item.getFieldName().equals("tags")) {
								tags = item.getString("UTF8");
							}
							else if(item.getFieldName().equals("restrictionuds")) {
								restrictionuds=true;
							}
							/*else if(item.getFieldName().equals("permission")) {
								permission=true;
							}*/

						} /* If the element is a file (the last element */
						else {
							fileName = item.getName();
							String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.') + 1,fileName.length()).toLowerCase() : "";
							
							/* Checks the extension of the item to have a supported file format */				
							StringTokenizer tokenMediaFormats = new StringTokenizer(uploadFormats);
							boolean isExtVal = false;
							while(tokenMediaFormats.hasMoreTokens() && !isExtVal) {
								isExtVal = extension.equals(tokenMediaFormats.nextToken());
							}
							
							// Test the form
							if(fileName==null || fileName.equals("")) {
								messageType = "error";
								ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
								message = bundle.getString("err_file");
								requestDispatcher="/avc/myspace_upload";								
							}
							/* Checks the extension of the item to have a supported file format */
							else if( !isExtVal ) {

								messageType = "error";
								ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
								message = bundle.getString("err_fileformat")+" : " + extension;
								requestDispatcher="/avc/myspace_upload";
							}
							else if(title==null || title.equals("")) {
								messageType = "error";
								ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
								message = bundle.getString("err_title");
								requestDispatcher="/avc/myspace_upload";
							}
							else if(name==null || name.equals("")) {
								messageType = "error";
								ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
								message = bundle.getString("err_name");
								requestDispatcher="/avc/myspace_upload";
							}
							else if(level==null || level.equals("")) {
								messageType = "error";
								ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
								message = bundle.getString("err_lvl");
								requestDispatcher="/avc/myspace_upload";
							}
							else if(component==null || component.equals("")) {
								messageType = "error";
								ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
								message = bundle.getString("err_component");
								requestDispatcher="/avc/myspace_upload";
							}
							/*else if(!permission) {
								messageType = "error";
								ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
								message = bundle.getString("err_permission");
								requestDispatcher="/avc/myspace_upload";
							}*/
							else {
								
								String clientIP = request.getRemoteAddr();
								String timing = "n-1";
								String type = (extension.equals("mp3") || extension.equals("ogg") || extension.equals("wav") || extension.equals("wma")) ? "audio" : "video";
								
								// Generate pseudo code etp for formation
								if(!level.equals("") && !component.equals("")) {
									formation=component+"-"+level;
								}
								// for old client
								else {
									//FORMATION DANS AUTRE AUTRE
									formation="00-O0";
								}
								
								Course c = new Course(
										service.getNextCoursId(),
										new Timestamp(new Date().getTime()),
										type,
										title.equals("") ? null : title,
										description.equals("") ? null : description,
										formation.equals("") ? null : formation,
										(name == null || name.equals("")) ? null : name,
										(firstname == null || firstname.equals("")) ? null : firstname,
										clientIP,
										0, // The duration can't be set yet
										(genre == null || genre.equals("")) ? null : genre,
										visible,
										0,
										timing,
										user.getUserid(),
										null,
										download,
										restrictionuds,
										0,
										volume,
										new Timestamp(new Date().getTime()),
										null
								);

								/* Sends the creation of the course to the service layer */
								service.mediaUpload(c, item, tags, serverUrl,sepEnc,coursesFolder);

								// Sending email for the user
								String emailUserSubject = "Votre nouvel enregistrement sur AudioVideoCast / Your new course on AudioVideoCast";
								
								String emailUserMessageFr = "Bonjour,\n\nVotre enregistrement intitulé \"" + c.getTitle()
								+"\" sera publié sur la plateforme AudioVideoCast à l'adresse : "+ recordedInterfaceUrl + "?id="+c.getCourseid()
								+ "\nMerci de bien vouloir patienter quelques minutes avant la mise en ligne définitive du document, le processus de conversion durant environ 30 minutes pour chaque heure de vidéo."
								+"\n\nPour toute question sur l'usage de la plateforme AudioVideoCast,"
								+"\n- contactez le support : " + supportLink		
								+"\n- ou consultez la documentation : " + docLink
								+ "\n\nBien cordialement,\n\nL'équipe AudioVideoCast";
								
								String emailUserMessageEn = "Hello,\n\nYour recording entitled \"" + c.getTitle()
								+"\" will be published on : "+ recordedInterfaceUrl + "?id="+c.getCourseid()
								+"\nPlease note that the conversion process of your document will take about 30 minutes for every hour of video."
								+"\n\nFor any question regarding AudioVideoCast,"
								+"\n- contact support team : " + supportLink
								+"\n- or read the documentation : " + docLink
								+ "\n\nBest Regards,\n\nAudioVideoCast team";
								
								String emailUserMessage = emailUserMessageFr + "\n\n\n********************\n\n\n" + emailUserMessageEn;
								
								// If the user is not anonymous and his email is present
								if(user!=null && user.getEmail()!=null && !user.getEmail().equals("")) {
									service.sendMail(emailUserSubject,emailUserMessage,user.getEmail());
								}

								// If course is present in the recorded page
								if(c.isVisible() && (c.getGenre()!=null ? !c.getGenre().toUpperCase().equals(testKeyWord1.toUpperCase()) : true) && (c.getTitle()!=null ? !c.getTitle().toUpperCase().startsWith(testKeyWord2.toUpperCase()) : false)) {
									// Sending email for admins
									String emailAdminSubject = "a new file on AudioVideoCast";
									String emailAdminMessage = "Dear Admin,\n\nA file named \"" + c.getTitle() +"\" will be published on "+ recordedInterfaceUrl + "?id="+c.getCourseid() + (c.getName()!=null ? "\n\nAuthor:"+c.getName() + (c.getFirstname()!=null ? " " + c.getFirstname() : "") : "") + (user!=null && user.getEmail()!=null ? "\n\nEmail:"+user.getEmail() : "") + (c.getGenre()!=null ? "\n\nPassword:"+c.getGenre() : "") + "\n\nBest Regards,\n\nAudioVideoCast Administrator" ;
									if(!adminEmail1.equals(""))
										service.sendMail(emailAdminSubject,emailAdminMessage,adminEmail1);
									if(!adminEmail2.equals(""))
										service.sendMail(emailAdminSubject,emailAdminMessage,adminEmail2);
									if(!adminEmail3.equals(""))
										service.sendMail(emailAdminSubject,emailAdminMessage,adminEmail3);
								}

								ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
																
								message = bundle.getString("upload_valmsg1");
								message += bundle.getString("upload_valmsg2");
								
								// log stats
								if(logstats) {
									service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeAdd, fileName);
								}	
							}
						}
					}
				}
				catch( FileUploadException fue) {
					messageType = "error";
					message = "Error : File upload error";
				}
			}
			else {
				messageType = "error";
				message = "Error: Incorrect file upload request";
			}
		}
		else { 
			messageType = "error";
			message = "Error: You don't have access to this page";
		}

		
		if(requestDispatcher.equals("/avc/myspace_upload")) {
			request.setAttribute("title", title);
			request.setAttribute("description", description);
			request.setAttribute("name", name);
			request.setAttribute("firstname", firstname);
			request.setAttribute("ue", formation);
			request.setAttribute("genre", genre);
			request.setAttribute("tags", tags);
			request.setAttribute("visible", visible!=false ? visible : null);
			request.setAttribute("download", download!=false ? download : null);
			//request.setAttribute("hd", hq!=false ? hq : null);
			request.setAttribute("restrictionuds", restrictionuds!=false ? restrictionuds : null);
			request.setAttribute("levelSelected", level);
			request.setAttribute("discSelected", component);
			//request.setAttribute("permission", permission!=false ? permission : null);
		}
				
		/* Displays the result of the upload process */
		request.setAttribute("messagetype", messageType);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher(requestDispatcher).forward(request, response);
	}

	/**
	 * Method to change the live status
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void liveState(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		String message = "";
		String messageType = "information";
		
		String recordingPlace = request.getParameter("recordingPlace");
		String status = request.getParameter("status");
		
		/* Verifies that all parameters are sent and are not empty */
		if( recordingPlace != null && status != null && ! recordingPlace.equals("") && ! status.equals("")) {
			
			/* Verifies that the "status" variable contains one of the two authorized strings */
			if( status.equals("begin") || status.equals("end")) {
				recordingPlace = recordingPlace.replace('_', '.');
				
				try {
					// Check with IP adress
					service.setAmphiStatus(recordingPlace, status.equals("begin"));
				}
				catch (Exception e) {
					// If error with IP adress, check with host name
					recordingPlace=InetAddress.getByAddress(InetAddress.getByName(recordingPlace).getAddress()).getHostName();
					service.setAmphiStatus(recordingPlace, status.equals("begin"));
				}
				message = "Amphi : " + recordingPlace + " : " + status;
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeEdit, recordingPlace + " : " + status);
				}	
			}
			else {
				messageType = "error";
				message = "Error: possible status values for status : {begin ; end}";
			}
		} 
		else {
			messageType = "error";
			message = "Error: missing parameters: recordingPlace and status must be sent";
		}
		
		request.setAttribute("messagetype", messageType);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
	}
	
	
	/**
	 * Method to change the encodage status of a course
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void encodageState(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		String message = "";
		String messageType = "information";
				
			/* Verifies that all parameters are sent and are not empty */
			if( request.getParameter("courseid") != null && request.getParameter("mediatype") != null && request.getParameter("jobtype") != null && ! request.getParameter("courseid").equals("") && ! request.getParameter("mediatype").equals("") && ! request.getParameter("jobtype").equals("")) {

				int courseid = Integer.parseInt(request.getParameter("courseid"));
				int mediatype = Integer.parseInt(request.getParameter("mediatype"));		
				int currentmt = service.getMediaType(courseid);
				String jobtype = request.getParameter("jobtype");
				
				Job j = service.getJob(courseid, jobtype);
				
			    // If job not already done				
				if (j!=null && !j.getStatus().equals("done")) {
					
					//update the job status
					service.modifyJobStatus(courseid, "done", jobtype);

					// Update the mediatype
					service.modifyCourseMediatype(courseid, currentmt+mediatype);		

					Course c = service.getCourse(courseid);
					/* Generation of the RSS files */
					if( c.getGenre() == null)
						service.generateRss(c, getServletContext().getRealPath("/rss"), rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);

					// log stats
					if(logstats) {
						service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeEdit, null);
					}	
				}
				else {
					messageType = "error";
					message = "Error: job already done";
				}
			} 
			else {
				messageType = "error";
				message = "Error: missing parameters";
			}
		
		
		request.setAttribute("messagetype", messageType);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
	}
	
	/**
	 * Method to get or display a course (flash, mp3, pdf ...)
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void courseAccess(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// Parameters
		int courseid = Integer.parseInt( (String) request.getParameter("id"));			
		String type = (String) request.getParameter("type");
		String courseAccessUrl = service.getCleanCoursesUrl(coursesUrl);
		Course c = null;
		String genre = null;
		
		if(request.getParameter("code")!=null) // if code in url or form
			genre = request.getParameter("code");
		else if(session.getAttribute("code_"+courseid)!=null) // if code already in session
			genre = (String) session.getAttribute("code_"+courseid);
		
		try {
			if( genre == null) {
				c = service.getCourse(courseid);
			}
			else {
				c = service.getCourse(courseid, genre);
				// if the code is correct, set the code in the session
				if (c!=null && session.getAttribute("code_"+courseid)==null)
					session.setAttribute("code_"+courseid, genre);
			}
			

			//To lock access of course with UDS account
			User user = null;
			if(c.isRestrictionuds()) {
				String casUser = (String) session.getAttribute(edu.yale.its.tp.cas.client.filter.CASFilter.CAS_FILTER_USER);	
				String localUser = session.getAttribute("$userLocalLogin")!=null ? (String) session.getAttribute("$userLocalLogin") : null;
				if(casUser!=null) // Authentification CAS	
					user=service.getUser(casUser); // Gets the user from the session	
				// Access authorized if the user is the course user
				else if(localUser!=null) {
					User userTmp = service.getUser(localUser);
					if (userTmp.getUserid() == c.getUserid())
						user = userTmp;
				}
			}
					
			// if the user is not authenticated, redirection to authentication_cas
			if(c.isRestrictionuds() && user==null) {
				response.sendRedirect("./authentication_cas?returnPage=courseaccess&id="+courseid+(type != null ? "&type="+ type : "")+(genre !=null ? "&code="+genre:""));
			}
			else {

				if( genre == null && c.getGenre() != null) { // The user tries to access to the protected course directly via the URL

					/* Redirects the client to the access code typing form */
					request.setAttribute("id", courseid);
					if(type != null && !type.equals(""))
						request.setAttribute("type", type);
					getServletContext().getRequestDispatcher("/WEB-INF/views/codeform.jsp").forward(request, response);
				}
				else {

					// To fix a bug with SSL and IE
					String slidesurl = null;
					if(request.getRequestURL().toString().substring(0, 5).equals("https") && courseAccessUrl.substring(0, 4).equals("http"))
						slidesurl = courseAccessUrl.replace("http", "https") + c.getMediafolder() + "/screenshots/";
					else 
						slidesurl = courseAccessUrl + c.getMediafolder() + "/screenshots/";

					service.incrementConsultations(c);

					// default access : set html5 or flash
					if(type == null || type.equals("")) {
						// if audio with mp3 and ogg, or video with webm-mp4, go to html5 page, else, go to flash
						String recordinterface = (String) session.getAttribute("recordinterface");
						if(recordinterface.equals("html5") && ((c.getType().equals("audio") && c.isAvailable("ogg") && c.isAvailable("mp3")) || (c.getType().equals("video") && c.isAvailable("webm")))) {
							type = "html5";
						}
						else {
							type = "flash";
						}
					}
										
					// Check the media availability
					if(type != null && !type.equals("") && c.isAvailable(type)) {
						
						// log stats
						if(logstats) {
							String logUserActionType = type.equals("html5") || type.equals("flash") || type.equals("hq") ? LogUserAction.typeRead : LogUserAction.typeDownload;
							service.addLogUserAction(request, service.getSessionUser(session), c, logUserActionType, type);
						}	
						
						if( type.equals("flash")) {
							/* redirection to the FlvPlay JS interface */
							String courseExtension = "";
							if( c.getType().equals("audio"))
								courseExtension = ".mp3";
							else if( c.getType().equals("video"))
								courseExtension = ".flv";

							String courseurl = "";
							
							//if the course have an additional video, change the url of the main media
							if(c.isAvailable("addvideo")) {
								courseurl = courseAccessUrl + c.getMediafolder() + "/additional_video/addvideo_" + c.getMediasFileName() + ".mp4";
							}
							else {
								courseurl = courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + courseExtension;
							}
							
							List<Slide> slides = service.getSlides(c.getCourseid());
							// if offset, change time slide "à la volée"
							if(c.getSlidesoffset() != null && c.getSlidesoffset()!=0) {
								for(Slide s : slides) {
									int t = s.getSlidetime() + c.getSlidesoffset();
									s.setSlidetime(t);
								}					
							}
							
							request.setAttribute("courseurl", courseurl);
							request.setAttribute("courseurlfolder", courseAccessUrl + c.getMediafolder());
							request.setAttribute("slidesurl", slidesurl);
							request.setAttribute("course", c);
							request.setAttribute("slides", slides);
							String showForm = service.getFormationFullName(c.getFormation());
							request.setAttribute("formationfullname", showForm!=null ? showForm : c.getFormation());														
							Amphi a = service.getAmphi(c.getIpaddress());
							String amphi = a != null ? a.getName() : "";
							String building = service.getBuildingName(c.getIpaddress());
							request.setAttribute("amphi", amphi);
							request.setAttribute("building", building);
							if( c.getTiming().equals("n-1"))
								request.setAttribute("timing", 1);
							else
								request.setAttribute("timing", 0);

							request.setAttribute("serverUrl",serverUrl);
							request.setAttribute("tags", service.getTagsByCourse(c));
							request.setAttribute("rssfiles", service.getRssCourseFileList(c));
							request.setAttribute("mediaLst", c.getMedias());
							
							// Sets embed objects
							if(c.isDownload() && !c.isRestrictionuds() && c.getGenre()==null) {
								if(c.isAvailable("videoslide"))
									request.setAttribute("embedvs", "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='640' height='480' id='player1' name='player1'><param name='movie' value='"+serverUrl+"/files/jwflvplayer/player.swf'><param name='allowfullscreen' value='true'><param name='allowscriptaccess' value='always'><param name='flashvars' value='file="+courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName()+"_videoslide.mp4&type=lighttpd'><embed id='player1' name='player1' src='"+serverUrl+"/files/jwflvplayer/player.swf' width='640' height='480' allowscriptaccess='always' allowfullscreen='true' flashvars='file="+courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName()+"_videoslide.mp4&type=lighttpd' /></object>");
								if(c.isAvailable("mp3"))
									request.setAttribute("embedaudio", "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='320' height='20' id='player1' name='player1'><param name='movie' value='"+serverUrl+"/files/jwflvplayer/player.swf'><param name='allowfullscreen' value='true'><param name='allowscriptaccess' value='always'><param name='flashvars' value='file="+courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName()+".mp3'><embed id='player1' name='player1' src='"+serverUrl+"/files/jwflvplayer/player.swf' width='320' height='20' allowscriptaccess='always' allowfullscreen='true' flashvars='file="+courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName()+".mp3' /></object>");
								if(c.getType().equals("video"))
									request.setAttribute("embedvideo", "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='320' height='240' id='player1' name='player1'><param name='movie' value='"+serverUrl+"/files/jwflvplayer/player.swf'><param name='allowfullscreen' value='true'><param name='allowscriptaccess' value='always'><param name='flashvars' value='file="+courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName()+".flv&type=lighttpd'><embed id='player1' name='player1' src='"+serverUrl+"/files/jwflvplayer/player.swf' width='320' height='240' allowscriptaccess='always' allowfullscreen='true' flashvars='file="+courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName()+".flv&type=lighttpd' /></object>");
								if(c.isAvailable("hq"))
									request.setAttribute("embedhq", "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='1024' height='576' id='player1' name='player1'><param name='movie' value='"+serverUrl+"/files/jwflvplayer/player.swf'><param name='allowfullscreen' value='true'><param name='allowscriptaccess' value='always'><param name='flashvars' value='file="+courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName()+".mp4&type=lighttpd'><embed id='player1' name='player1' src='"+serverUrl+"/files/jwflvplayer/player.swf' width='1024' height='576' allowscriptaccess='always' allowfullscreen='true' flashvars='file="+courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName()+".mp4&type=lighttpd' /></object>");
							}
							
							/* displays the .jsp view */
							getServletContext().getRequestDispatcher("/WEB-INF/views/recordinterface_flash.jsp").forward(request, response);
						}
						// High Quality
						else if( type.equals("hq")) {
							
							String courseurl = "";
							//if the course have an additional video, change the url of the main media
							if(c.isAvailable("addvideo"))
								courseurl = courseAccessUrl + c.getMediafolder() + "/additional_video/addvideo_" + c.getMediasFileName() + ".mp4";
							else
								courseurl = courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + ".mp4";
								
							request.setAttribute("courseurl", courseurl);
							request.setAttribute("courseurlfolder", courseAccessUrl + c.getMediafolder());
							request.setAttribute("course", c);					
							Amphi a = service.getAmphi(c.getIpaddress());
							String amphi = a != null ? a.getName() : "";
							String building = service.getBuildingName(c.getIpaddress());
							request.setAttribute("amphi", amphi);
							request.setAttribute("building", building);
							request.setAttribute("serverUrl",serverUrl);

							/* Displays the view */
							getServletContext().getRequestDispatcher("/WEB-INF/views/recordinterface_hq.jsp").forward(request, response);

						}
						
						else if(c.isDownload() & (type.equals("mp3") || type.equals("ogg") || type.equals("zip") || type.equals("pdf"))) {
							
							String filename = courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + "." + type;
							
							/* Initializes the headers */
							response.setContentType("application/x-download");
							response.setHeader("Content-Disposition", "attachment; filename=" + c.getMediasFileName() + "." + type);

							/* Sends the file */
							OutputStream out = response.getOutputStream();
							service.returnFile(filename, out);
						}
						else if(c.isDownload() & (type.equals("videoslide"))) {
							String filename = courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + "_videoslide.mp4";

							/* Initializes the headers */
							response.setContentType("application/x-download");
							response.setHeader("Content-Disposition", "attachment; filename=" + c.getMediasFileName() + "_videoslide.mp4");

							/* Sends the file */
							OutputStream out = response.getOutputStream();
							service.returnFile(filename, out);
						}
						else if(c.isDownload() & (type.equals("videoslideipod"))) {
							String filename = courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + "_videoslide_ipod.mp4";

							/* Initializes the headers */
							response.setContentType("application/x-download");
							response.setHeader("Content-Disposition", "attachment; filename=" + c.getMediasFileName() + "_videoslide_ipod.mp4");

							/* Sends the file */
							OutputStream out = response.getOutputStream();
							service.returnFile(filename, out);
						}
						else if(c.isDownload() & (type.equals("video"))) {
							String filename = courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + ".mp4";

							/* Initializes the headers */
							response.setContentType("application/x-download");
							response.setHeader("Content-Disposition", "attachment; filename=" + c.getMediasFileName() + ".mp4");

							/* Sends the file */
							OutputStream out = response.getOutputStream();
							service.returnFile(filename, out);
						}
						else if(type.equals("adddoc")) {
							//String filename = coursesFolder + c.getMediafolder() +"/additional_docs/" + c.getAdddocname();
							String filename = courseAccessUrl + c.getMediafolder() +"/additional_docs/" + c.getAdddocname();
							
							/* Initializes the headers */
							response.setContentType("application/x-download");
							response.setHeader("Content-Disposition", "attachment; filename=\"" + c.getAdddocname()+"\"");

							/* Sends the file */
							OutputStream out = response.getOutputStream();
							service.returnFile(filename, out);
						}					
						// Html 5 page
						else if( type.equals("html5")) {

							String courseurl = "";

							//if the course have an additional video, change the url of the main media
							if(c.isAvailable("addvideo")) {
								courseurl = courseAccessUrl + c.getMediafolder() + "/additional_video/addvideo_" + c.getMediasFileName();
							}
							else {
								courseurl = courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName();
							}

							List<Slide> slides = service.getSlides(c.getCourseid());
							// if offset change time slide "à la volée"
							if(c.getSlidesoffset() != null && c.getSlidesoffset()!=null) {
								for(Slide s : slides) {
									int t = s.getSlidetime() + c.getSlidesoffset();
									s.setSlidetime(t);
								}					
							}

							request.setAttribute("courseurlnoext", courseurl);
							request.setAttribute("courseurlfolder", courseAccessUrl + c.getMediafolder());
							request.setAttribute("slidesurl", slidesurl);
							request.setAttribute("course", c);
							request.setAttribute("slides", slides);
							String showForm = service.getFormationFullName(c.getFormation());
							request.setAttribute("formationfullname", showForm!=null ? showForm : c.getFormation());													
							Amphi a = service.getAmphi(c.getIpaddress());
							String amphi = a != null ? a.getName() : "";
							String building = service.getBuildingName(c.getIpaddress());
							request.setAttribute("amphi", amphi);
							request.setAttribute("building", building);
							if( c.getTiming().equals("n-1"))
								request.setAttribute("timing", 1);
							else
								request.setAttribute("timing", 0);

							request.setAttribute("serverUrl",serverUrl);
							request.setAttribute("tags", service.getTagsByCourse(c));
							request.setAttribute("rssfiles", service.getRssCourseFileList(c));
							request.setAttribute("mediaLst", c.getMedias());

							// Sets embed objects
							if(c.isDownload() && !c.isRestrictionuds() && c.getGenre()==null) {
								if(c.isAvailable("videoslide"))
									request.setAttribute("embedvs", "<video width='640 height='480' controls poster='" + serverUrl + "/files/img/logo_audio640.png'><source src='" + courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + "_videoslide.mp4" + "' type='video/mp4'><source src='" + courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + "_videoslide.webm" + "' type='video/webm'><p class='nohtml5'>Warning! Your browser doesn't support html5.</p></video>");
								if(c.isAvailable("mp3"))
									request.setAttribute("embedaudio", "<audio width='320 height='20' controls poster='" + serverUrl + "/files/img/logo_audio640.png'><source src='" + courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + ".mp3" + "' type='audio/mpeg'><source src='" + courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + ".ogg" + "' type='audio/ogg'><p class='nohtml5'>Warning! Your browser doesn't support html5.</p></audio>");
								if(c.getType().equals("video"))
									request.setAttribute("embedvideo", "<video width='640 height='480' controls poster='" + serverUrl + "/files/img/logo_audio640.png'><source src='" + courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + ".mp4" + "' type='video/mp4'><source src='" + courseAccessUrl + c.getMediafolder() + "/" + c.getMediasFileName() + ".webm" + "' type='video/webm'><p class='nohtml5'>Warning! Your browser doesn't support html5.</p></video>");
							}


							/* displays the .jsp view */
							getServletContext().getRequestDispatcher("/WEB-INF/views/recordinterface_html5.jsp").forward(request, response);

						}
					}
					else {
						// For media upload during process
						if(c.getmediatype()==0) {
							ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
							request.setAttribute("messagetype", "information");
							request.setAttribute("message", bundle.getString("processing"));
							getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
						}
						else {
							ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
							request.setAttribute("messagetype", "error");
							request.setAttribute("message", bundle.getString("wrongAccessCode"));
							getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
						}
					}
				}
			}
		}
		catch(DaoException de) {
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", bundle.getString("wrongAccessCode"));
			if(session.getAttribute("code_"+courseid)!=null)
				session.removeAttribute("code_"+courseid);
			
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}
	}
	
	/**
	 * Method to display the live interface
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void liveAccess(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String ip = request.getParameter("amphi");
		Amphi a = service.getAmphi(ip);
		String amphi = a != null ? a.getName() : ip;
		String building = service.getBuildingName(ip);
				
		ip=InetAddress.getByName(ip).getHostAddress();
		
		String streamerUrl = "rtmp://" + flashServerIp + "/live";
		String fileUrl = ip.replace('.', '_');
				
		//To lock access of course with UDS account
		User user = null;		
		if(a!=null && a.isRestrictionuds()) {
			String casUser = (String) session.getAttribute(edu.yale.its.tp.cas.client.filter.CASFilter.CAS_FILTER_USER);		
			if(casUser!=null) // Authentification CAS	
				user=service.getUser(casUser); // Gets the user from the session		
		}
				
		// if the user is not authenticated, redirection to authentication_cas
		if(a!=null && a.isRestrictionuds() && user==null) {
			response.sendRedirect("./authentication_cas?returnPage=liveaccess&amphi="+ip);
		}
		else {
			
			request.setAttribute("amphi", amphi);
			request.setAttribute("building", building);
			request.setAttribute("ip", ip);
			request.setAttribute("streamerUrl", streamerUrl);
			request.setAttribute("fileUrl", fileUrl);
			
			// log stats
			if(logstats) {
				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeRead, ip);
			}	

			getServletContext().getRequestDispatcher("/WEB-INF/views/liveinterface.jsp").forward(request, response);

		}
	}
	
	/**
	 * Method to update the live slide
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void liveSlide(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		//don't log this method because it's used by javascript
		
		String ip = request.getParameter("ip");
		String url = "../../live/" + ip.replace('.','_') + ".jpg";
		
		long checksum = 0;
		FileInputStream fis = null;
		CheckedInputStream cis = null;
		boolean nodia = false;
		
		try {
			    fis = new FileInputStream(liveFolder+"/"+ip.replace('.','_') + ".jpg");
		        cis = new CheckedInputStream(fis, new Adler32());
		        byte[] tempBuf = new byte[128];
		        while (cis.read(tempBuf) >= 0) {}
		        
		        checksum = cis.getChecksum().getValue();
		        
		        cis.close();
		    	fis.close();
		        
	    } catch (IOException e) {
	    	nodia = true;
	    }
			    
		request.setAttribute("urlimg", url);
		request.setAttribute("checksum", checksum);
		request.setAttribute("nodia", nodia);
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/include/liveslide.jsp").forward(request, response);
	}
	
	/**
	 * Method to validate a course form
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void validateCourse(HttpServletRequest request, HttpServletResponse response, String redirectUrl) 
		throws ServletException, IOException {
		
		String formation = service.getFormationByCodes(request.getParameter("level"), request.getParameter("component"));
				
		Course c= new Course(
			Integer.parseInt(request.getParameter("courseid")),
			new Timestamp(Long.parseLong(request.getParameter("date"))),
			! request.getParameter("type").equals("") ? request.getParameter("type") : null,
			! request.getParameter("title").equals("") ? request.getParameter("title") : null,
			! request.getParameter("description").equals("") ? request.getParameter("description") : null,
			formation,
			! request.getParameter("name").equals("") ? request.getParameter("name") : null,
			! request.getParameter("firstname").equals("") ? request.getParameter("firstname") : null,
			! request.getParameter("ipaddress").equals("") ? request.getParameter("ipaddress") : null,
			Integer.parseInt(request.getParameter("duration")),
			! request.getParameter("genre").equals("") ? request.getParameter("genre") : null,
			request.getParameter("visible") != null ? true : false,
			Integer.parseInt(request.getParameter("consultations")),
			! request.getParameter("timing").equals("") ? request.getParameter("timing") : null,
			! request.getParameter("userid").equals("0") ? Integer.parseInt(request.getParameter("userid")) : null,
			! request.getParameter("adddocname").equals("") ? request.getParameter("adddocname") : null,
			request.getParameter("download") != null ? true : false,
			request.getParameter("restrictionuds") != null ? true : false,
			Integer.parseInt(request.getParameter("mediatype")),
			Short.parseShort(request.getParameter("volume")),
			! request.getParameter("recorddate").equals("") ? new Timestamp(Long.parseLong(request.getParameter("recorddate"))) : null,
			! request.getParameter("slidesoffset").equals("") ? Integer.parseInt(request.getParameter("slidesoffset")) : null
		);
		service.modifyCourse(c);
		
		// Delete TAGS
		service.deleteTag(Integer.parseInt(request.getParameter("courseid")));
		
		// ADD TAGS		
		List<String> listTmp=new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(request.getParameter("tags")," ,;");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if(!listTmp.contains(token)) {
				service.addTag(new Tag(0, //is not used
					token, // the tag
					Integer.parseInt(request.getParameter("courseid"))) // the course
				);
				listTmp.add(token);
			}
		}
		
		/* Generation of the RSS files */
		service.generateRss(c, getServletContext().getRealPath("/rss"), rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		
		// Remove access code in session (because user maybe change the code access 
		if(session.getAttribute("code_"+c.getCourseid())!=null)
			session.removeAttribute("code_"+c.getCourseid());
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeEdit, null);
		}	
		
		response.sendRedirect(response.encodeRedirectURL(redirectUrl));
	}
	
	/**
	 * Method to validate a building form
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @param responseredirect redirection when the form validated
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void validateBuilding(HttpServletRequest request, HttpServletResponse response, String responseredirect) 
		throws ServletException, IOException {
		
		if( ! (request.getParameter("name").equals("") || request.getParameter("imagefile").equals(""))) {
		
			int buildingId =  ! request.getParameter("buildingid").equals("") ? Integer.parseInt(request.getParameter("buildingid")) : 0;
			
			Building b = new Building(
				buildingId,
				request.getParameter("name"),
				request.getParameter("imagefile")
			);
			
			if(request.getParameter("action").equals("edit")) {
				service.modifyBuilding(b);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeEdit, "Building : " + buildingId);
				}	
			}
			else {
				service.addBuilding(b);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAdd, "Building : " + buildingId);
				}	
			}
			
			response.sendRedirect(response.encodeRedirectURL(responseredirect));
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "All fields must be completed");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}
	}
	
	
	/**
	 * Method to validate an amphi form
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @param responseredirect redirection when the form validated
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void validateAmphi(HttpServletRequest request, HttpServletResponse response, String responseredirect) 
		throws ServletException, IOException {
		
		if( ! (request.getParameter("name").equals("") || request.getParameter("ipaddress").equals(""))) {
		
			int amphiId =  ! request.getParameter("amphiid").equals("") ? Integer.parseInt(request.getParameter("amphiid")) : 0;
			
			Amphi a = new Amphi(
				amphiId,
				Integer.parseInt(request.getParameter("buildingid")),
				request.getParameter("name"),
				request.getParameter("ipaddress"),
				Boolean.parseBoolean(request.getParameter("status")),
				request.getParameter("gmapurl")!=null && request.getParameter("gmapurl").equals("") ? null : request.getParameter("gmapurl"),
				request.getParameter("version"),
				request.getParameter("restrictionuds") != null && request.getParameter("restrictionuds") != "" ? true : false
			);
			
			String oldAmphiip = request.getParameter("oldAmphiip");
			
			if(request.getParameter("action").equals("edit")) {
				service.modifyAmphi(a, oldAmphiip);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeEdit, "Amphi : " + amphiId);
				}	
			}
			else {
				service.addAmphi(a);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAdd, "Amphi : " + amphiId);
				}	
			}
			
			response.sendRedirect(response.encodeRedirectURL(responseredirect+"?buildingId=" + request.getParameter("buildingid")));
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "All fields must be completed");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}
	}
	
	/**
	 * Method to validate an user form
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void validateUser(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
			
		if( ! (request.getParameter("login").equals("") || request.getParameter("email").equals("") || request.getParameter("firstname").equals("") || request.getParameter("lastname").equals("") || request.getParameter("type").equals(""))) {
			
			int userid =  ! request.getParameter("userid").equals("") ? Integer.parseInt(request.getParameter("userid")) : 0;
		
			User u = new User(
					userid,
					request.getParameter("login"),
					request.getParameter("email"),
					request.getParameter("firstname"),
					request.getParameter("lastname"),
					request.getParameter("profile"),
					request.getParameter("establishment"),
					request.getParameter("type"),
					request.getParameter("activate") != null ? true : false,
					request.getParameter("etp"),
					request.getParameter("institute")
					
			);
			
			try {
						
				if(request.getParameter("action").equals("edit")) {
					service.modifyUser(u);
					
					// log stats
					if(logstats) {
						service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeEdit, "User : " + userid);
					}	
				}
				else {
					service.addUser(u);
					
					// log stats
					if(logstats) {
						service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAdd, "User : " + userid );
					}	
				}
				
				response.sendRedirect(response.encodeRedirectURL("./admin_users"));
			}
			catch(DaoException e){
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "Can't add/modify user. Maybe another user already exists in the system with the same login name.");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
			}
			
			
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "All required fields must be completed");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}
	}
	
	/**
	 * Method to change the style of the website
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void changeStyle(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String style = request.getParameter("style");
		/* Stores the style in the session */
		session.setAttribute("style", style);
		/* Stores the style in the cookies */
		Cookie styleCookie = new Cookie("style", style);
		styleCookie.setMaxAge(31536000);
		response.addCookie(styleCookie);
		
		/* Returns to the page before the thickbox call (stored in the session) */
		String previousPage = (String) session.getAttribute("previousPage");
		if( previousPage == null)
			previousPage = "/home";
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		response.sendRedirect(response.encodeRedirectURL("." + previousPage));
	}
	
	/**
	 * Method to change the language of the website
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void changeLanguage(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String language = request.getParameter("language");
		/* Stores the language in the session */
		session.setAttribute("language", language);
		/* Stores the language in the cookies */
		Cookie languageCookie = new Cookie("language", language);
		languageCookie.setMaxAge(31536000);
		response.addCookie(languageCookie);
		
		/* Returns to the page before the thickbox call (stored in the session) */
		String previousPage = (String) session.getAttribute("previousPage");
		if( previousPage == null)
			previousPage = "/home";
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		response.sendRedirect(response.encodeRedirectURL("." + previousPage));
	}
		
	
	/**
	 * Method to display the admin edit course page
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void DisplayAdminEditCourse(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		// Get the course
		Course c = service.getCourse(Integer.parseInt(request.getParameter("id")));
		
		// Getting tags of the course
		String tags="";
		List<Tag> listTags = service.getTagsByCourse(c);
		for(int i=0;i<listTags.size();i++) {
			if(i==0)
				tags=listTags.get(i).getTag();
			else
				tags=tags + " " + listTags.get(i).getTag();
		}
					
		request.setAttribute("tags", tags);
		request.setAttribute("course", c);
		request.setAttribute("posturl", "./admin_validatecourse");
		request.setAttribute("gobackurl", "./admin_courses");
		
		// the discipline box
		request.setAttribute("disciplines", service.getAllDiscipline());
		
		// the discSelected
		request.setAttribute("discSelected", service.getComponentCodeByFormation(c.getFormation()));
		
		// the levels box
		request.setAttribute("levels", service.getAllLevels());

		// the levelSelected
		request.setAttribute("levelSelected", service.getLevelCodeByFormation(c.getFormation()));
		
		// univ acronym
		request.setAttribute("univAcronym", univAcronym);
		
		request.setAttribute("mediaLst", c.getMedias());
		
		// check if we have an additional video job in process
		Job j = service.getJob(c.getCourseid(), "ADDV");
		boolean addvinprocess = (j!=null && !j.getStatus().equals("done"));
		request.setAttribute("addvinprocess", addvinprocess);
		
		// show additional video block if the record is from the client
		request.setAttribute("showAddVidBlock", c.isAudioClient() || c.isVideoClient());
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeAccess, null);
		}	
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_editcourse.jsp").forward(request, response);
		
	
	}
	
	/**
	 * Methode to validate a selection form
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void validateSelection(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
						
		int position =  ! request.getParameter("position").equals("") ? Integer.parseInt(request.getParameter("position")) : 0;
		
			Selection s = new Selection(
					position,
					!request.getParameter("idcourseselection").equals("") ? Integer.parseInt(request.getParameter("idcourseselection")) : 0,
					request.getParameter("formationcollection")
			);
						
			if(request.getParameter("action").equals("edit")) {
				service.modifySelection(s);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeEdit, "Selection : " + position);
				}	
			}
			else {
				service.addSelection(s);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAdd, "Selection : " + position);
				}	
			}
			
			response.sendRedirect(response.encodeRedirectURL("./admin_selections"));	
	}
		
	
	/**
	 * Method to upload a document from myspace
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void myspaceAddDocUpload(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		User user = service.getSessionUser(session);

		if(user!=null && user.isActivate()) {

			String courseid = request.getParameter("courseid");

			if(courseid!=null) {
				// Get the course
				Course c = service.getCourse(Integer.parseInt(courseid));

				// Check user id
				if(c.getUserid()==user.getUserid()) {
					addDocUpload(request,response);
				}
				else { 
					request.setAttribute("messagetype", "error");
					request.setAttribute("message", "Error: You don't have access to this page");
					getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
				}		

			}
			else { 
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "Error: You don't have access to this page");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
			}	

		}
		else { 
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Error: Wrong parameters");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}	
	}
	
	
	/**
	 * Methode to delete an additional document from myspace
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void myspaceDeleteAddDoc(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	
		User user = service.getSessionUser(session);

		if(user!=null && user.isActivate()) {
			
			// Get the course
			Course c = service.getCourse(Integer.parseInt(request.getParameter("courseid")));
			
			// Check user id
			if(c.getUserid()==user.getUserid()) {
				deleteAddDoc(request, response);
			}
			else { 
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "Error: You don't have access to this page");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
			}		
		}
		else { 
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Error: You don't have access to this page");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}	
	}
	
	
	/**
	 * Method to upload a document
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@SuppressWarnings("unchecked")
	private void addDocUpload(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		// Attribute
		String courseid, fileName, returnUrl;
		courseid = fileName = returnUrl = "";
		String message = "";
		String messageType = "information";
		String requestDispatcher = "/WEB-INF/views/message.jsp";
		
		if( ServletFileUpload.isMultipartContent(new ServletRequestContext(request)) ) {

			try {
				/* Prepares to parse the request to get the different elements of the POST */
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);

				/* Processes the different elements */
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();

					/* If the element is a form field, retrieves the info */
					if (item.isFormField()) {

						if(item.getFieldName().equals("courseid"))
							courseid = item.getString("UTF8");
						else if(item.getFieldName().equals("returnUrl"))
							returnUrl = item.getString("UTF8");

					} /* If the element is a file (the last element */
					else {
						fileName = item.getName();
						String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.') + 1,fileName.length()) : "";

						/* Checks the extension of the item to have a supported file format */				
						StringTokenizer stokadf = new StringTokenizer(addDocFormats);
						boolean isExtVal = false;
						while(stokadf.hasMoreTokens() && !isExtVal) {
							isExtVal = extension.equals(stokadf.nextToken());
						}
																		
						// Test the form
						if(fileName==null || fileName.equals("")) {
							messageType = "error";
							message = "File must be completed";							
						}
						/* Checks the extension of the item to have a supported file format */
						else if(!isExtVal) {
							messageType = "error";
							message = "Error: Not supported file format : " + extension;
						}
						else {
							// Get the course
							Course c = service.getCourse(Integer.parseInt(courseid));

							// Add the document to the course									
							service.addAdditionalDoc(c,item);
							requestDispatcher=returnUrl;
							
							// log stats
							if(logstats) {
								service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeAdd, fileName);
							}	
						}
					}
				}
			}
			catch( FileUploadException fue) {
				messageType = "error";
				message = "Error : File upload error";
			}
		}
		else {
			messageType = "error";
			message = "Error: Incorrect file upload request";
		}

		/* Displays the result of the upload process */
		request.setAttribute("messagetype", messageType);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher(requestDispatcher).forward(request, response);
	}
	
	
	/**
	 * Methode to delete an additional document
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void deleteAddDoc(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
				
		// Get the course
		Course c = service.getCourse(Integer.parseInt(request.getParameter("courseid")));

		// Delete the document to the course									
		service.deleteAdditionalDoc(c);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeRemove, null);
		}	
		
		/* Displays the result of the upload process */
		getServletContext().getRequestDispatcher(request.getParameter("returnUrl")).forward(request, response);
	}
	
	
	/**
	 * Send mail from the contact us form
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void sendMailContactUs(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String subject = request.getParameter("subject");
		String useremail = request.getParameter("useremail");
		String yourmessage = request.getParameter("yourmessage");
		
		if(!adminEmail1.equals(""))
			service.sendMail(subject,"from: " + useremail + "\n\n" + yourmessage,adminEmail1);
		if(!adminEmail2.equals(""))
			service.sendMail(subject,"from: " + useremail + "\n\n" + yourmessage,adminEmail2);
		if(!adminEmail3.equals(""))
			service.sendMail(subject,"from: " + useremail + "\n\n" + yourmessage,adminEmail3);
		request.setAttribute("messagetype", "info");
		request.setAttribute("message", "E-mail successfully sent !");
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);

	
	}
	
	
	/**
	 * Method to validate an discipline form
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void validateDiscipline(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
			
		if( ! (request.getParameter("codecomp").equals(""))) {
			
			int disciplineid =  ! request.getParameter("disciplineid").equals("") ? Integer.parseInt(request.getParameter("disciplineid")) : 0;
		
			Discipline d = new Discipline(
					disciplineid,
					request.getParameter("codecomp"),
					request.getParameter("namecomp"),
					request.getParameter("codedom"),
					request.getParameter("namedom")
			);
						
			if(request.getParameter("action").equals("edit")) {
				service.modifyDiscipline(d);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeEdit, "Discipline : " + disciplineid);
				}	
			}
			else {
				service.addDiscipline(d);
				
				// log stats
				if(logstats) {
					service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAdd, "Discipline : " + disciplineid);
				}	
			}
			
			response.sendRedirect(response.encodeRedirectURL("./admin_disciplines"));
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Component code field must be completed");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}
	}

	
	/**
	 * Method to return tracks
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayFindTracks(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		String datatype = null;
				
		//Check and set parameters
		// error msg if parameters list is empty
		boolean showErrorMsg = !request.getParameterNames().hasMoreElements();
		
		// For each parameter
		for (Enumeration<?> e = request.getParameterNames() ; e.hasMoreElements() ;) {
			String nameParam = (String) e.nextElement();
					
			// Get the datatype (xml by default,csv,json)
			if( nameParam.equals("datatype")  && ! request.getParameter("datatype").equals("") ) {
				datatype = request.getParameter("datatype");
			}
			// Check and set idRange parameter.
			else if( nameParam.equals("idRange") && ! request.getParameter("idRange").equals("") ) {		
				String idBeg = request.getParameter("idRange").indexOf("-")!=-1 ? request.getParameter("idRange").substring(0,request.getParameter("idRange").indexOf("-")) : request.getParameter("idRange");
				String idLast = request.getParameter("idRange").indexOf("-")!=-1 ? request.getParameter("idRange").substring(request.getParameter("idRange").indexOf("-")+1) : request.getParameter("idRange");
				
				try {
					//Check idBeg and idLast (must be integer)
					Integer.parseInt(idBeg);
					Integer.parseInt(idLast);
					params.put("idBeg", idBeg);
					params.put("idLast", idLast);
				}
				catch(Exception e1) {
					showErrorMsg=true;
				}
			}
			// Check and set dateRange parameter.
			else if( nameParam.equals("dateRange") && ! request.getParameter("dateRange").equals("") ) {
				
				if(!request.getParameter("dateRange").equals("all")) {

					String dateBeg = request.getParameter("dateRange").indexOf("-")!=-1 ? request.getParameter("dateRange").substring(0,request.getParameter("dateRange").indexOf("-")) : request.getParameter("dateRange");
					String dateLast = request.getParameter("dateRange").indexOf("-")!=-1 ? request.getParameter("dateRange").substring(request.getParameter("dateRange").indexOf("-")+1) : request.getParameter("dateRange");

					try {
						//Check and set the date format for dateBeg and dateLast
						SimpleDateFormat sdf_in = new SimpleDateFormat("yyyyMMdd");
						SimpleDateFormat sdf_out = new SimpleDateFormat("yyyy-MM-dd");
						params.put("dateBeg", sdf_out.format(sdf_in.parse(dateBeg)));
						params.put("dateLast", sdf_out.format(sdf_in.parse(dateLast)));
					}
					catch(ParseException exception) {
						showErrorMsg=true;
					}
				}
			}
			// Check and set authorName parameter.
			else if( nameParam.equals("authorName") && ! request.getParameter("authorName").equals("") ) 
				params.put("authorName", service.encodeString(request.getParameter("authorName")));
			// Check and set authorFirstname parameter.
			else if( nameParam.equals("authorFirstname") && ! request.getParameter("authorFirstname").equals("") ) 
				params.put("authorFirstname", service.encodeString(request.getParameter("authorFirstname")));
			// Check and set authorLogin parameter.
			else if( nameParam.equals("authorLogin") && ! request.getParameter("authorLogin").equals("") ) 
				params.put("authorLogin", request.getParameter("authorLogin"));
			// Check and set component parameter.
			else if( nameParam.equals("component") && ! request.getParameter("component").equals("") ) 
				params.put("component", request.getParameter("component"));
			// Check and set level parameter.
			else if( nameParam.equals("level") && ! request.getParameter("level").equals("") ) 
				params.put("level", request.getParameter("level"));
			// Check and set tags parameter.
			else if( nameParam.equals("tags") && ! request.getParameter("tags").equals("") ) {
				params.put("tags", service.encodeString(request.getParameter("tags")));								
			}
			// show error msg if parameter unknown
			else {
				showErrorMsg=true;
			}
		}

		//System.out.println(params);
		
		String results = null;
		//Find the list of courses and generate xml if no error 
		if(!showErrorMsg) {
			try {
				// Find the list of courses 
				List<Course> lstCrs = service.getTracks(params);
				results = service.generateXmlTracks(coursesUrl,lstCrs,serverUrl,false);
			}
			catch(Exception e1) {
				showErrorMsg = true;
			}
		}
		
		//if error, show xml error
		if(showErrorMsg) {
			results = service.generateXmlTracks(null,null,null,true);
		}
				
		// Return results
		if(datatype!=null && datatype.toUpperCase().equals("JSON")) {
			//Convert Xml to Json
			JSONObject jsonObj = XML.toJSONObject(results);
			String json = jsonObj.toString();
			response.setContentType("application/json;charset=UTF-8");
			response.getOutputStream().print(new String(json.getBytes("UTF8"),"8859_1"));		
		}
		//into XML by default
		else {
			response.setContentType("text/xml;charset=UTF-8");
			response.getOutputStream().print(new String(results.getBytes("UTF8"),"8859_1"));
		}				
	}
	
	
	
	/**
	 * Method to return stats
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayFindStats(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		
		HashMap<String, String> params = new HashMap<String, String>();
		String datatype = null;
		
		//Check and set parameters
		// error msg if parameters list is empty
		boolean showErrorMsg = !request.getParameterNames().hasMoreElements();
		
		// For each parameter
		for (Enumeration<?> e = request.getParameterNames() ; e.hasMoreElements() ;) {
			String nameParam = (String) e.nextElement();
					
			// Get the datatype (xml by default,csv,json)
			if( nameParam.equals("datatype")  && ! request.getParameter("datatype").equals("") ) {
				datatype = request.getParameter("datatype");
			}
			// Check and set dateRange parameter.
			else if( nameParam.equals("dateRange") && ! request.getParameter("dateRange").equals("") ) {
				
				if(!request.getParameter("dateRange").equals("all")) {

					String dateBeg = request.getParameter("dateRange").indexOf("-")!=-1 ? request.getParameter("dateRange").substring(0,request.getParameter("dateRange").indexOf("-")) : request.getParameter("dateRange");
					String dateLast = request.getParameter("dateRange").indexOf("-")!=-1 ? request.getParameter("dateRange").substring(request.getParameter("dateRange").indexOf("-")+1) : request.getParameter("dateRange");

					try {
						//Check and set the date format for dateBeg and dateLast
						SimpleDateFormat sdf_in = new SimpleDateFormat("yyyyMMdd");
						SimpleDateFormat sdf_out = new SimpleDateFormat("yyyy-MM-dd");
						params.put("dateBeg", sdf_out.format(sdf_in.parse(dateBeg)));
						params.put("dateLast", sdf_out.format(sdf_in.parse(dateLast)));
					}
					catch(ParseException exception) {
						showErrorMsg=true;
					}
				}
			}
			// Check and set account parameter.
			else if( nameParam.equals("account") && ! request.getParameter("account").equals("") ) { 
				if(request.getParameter("account").toUpperCase().equals("STUDENT"))
					params.put("account", "student");
				else if(request.getParameter("account").toUpperCase().equals("UDS"))
					params.put("account", "ldap");
				else if(request.getParameter("account").toUpperCase().equals("FREE"))
					params.put("account", "local");
				else
					showErrorMsg=true;
			}
			// Check and set component parameter.
			else if( nameParam.equals("component") && ! request.getParameter("component").equals("") ) 
				params.put("component", request.getParameter("component"));
			// Check and set level parameter.
			else if( nameParam.equals("level") && ! request.getParameter("level").equals("") ) 
				params.put("level", request.getParameter("level"));
			// Check and set domain parameter.
			else if( nameParam.equals("domain") && ! request.getParameter("domain").equals("") ) {
				params.put("domain", request.getParameter("domain"));								
			}
			else if( nameParam.equals("weekday") && ! request.getParameter("weekday").equals("") ) {
				
				try {
					//Check idBeg and idLast (must be integer)
					int i = Integer.parseInt(request.getParameter("weekday"));
					if(i>=1 & i<=7)
						params.put("weekday", request.getParameter("weekday"));
					else
						showErrorMsg=true;
				}
				catch(Exception e1) {
					showErrorMsg=true;
				}
				
				
				params.put("weekday", request.getParameter("weekday"));								
			}
			// show error msg if parameter unknown
			else {
				showErrorMsg=true;
			}
		}

		//System.out.println(params);
		
		String results = null;
		//Find the list of courses and generate xml if no error 
		if(!showErrorMsg) {
			try {
				// Find the list of courses 
				HashMap<String,Integer> lstStats = service.getStats(params);
				results = service.generateXmlStats(lstStats,serverUrl,false);
			}
			catch(Exception e1) {
				showErrorMsg = true;
			}
		}
		
		//if error, show xml error
		if(showErrorMsg) {
			results = service.generateXmlStats(null,null,true);
		}
				
		// Return results
		if(datatype!=null && datatype.toUpperCase().equals("JSON")) {
			//Convert Xml to Json
			JSONObject jsonObj = XML.toJSONObject(results);
			String json = jsonObj.toString();
			response.setContentType("application/json;charset=UTF-8");
			response.getOutputStream().print(new String(json.getBytes("UTF8"),"8859_1"));		
		}
		//into XML by default
		else {
			response.setContentType("text/xml;charset=UTF-8");
			response.getOutputStream().print(new String(results.getBytes("UTF8"),"8859_1"));
		}			
	
	}
	
	/**
	 * Method to upload a subtitles xml from myspace
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void myspaceAddSubtitles(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		User user = service.getSessionUser(session);

		if(user!=null && user.isActivate()) {

			String courseid = request.getParameter("courseid");

			if(courseid!=null) {
				// Get the course
				Course c = service.getCourse(Integer.parseInt(courseid));

				// Check user id
				if(c.getUserid()==user.getUserid()) {
					addSubtitles(request,response);
				}
				else { 
					request.setAttribute("messagetype", "error");
					request.setAttribute("message", "Error: You don't have access to this page");
					getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
				}		

			}
			else { 
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "Error: You don't have access to this page");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
			}	

		}
		else { 
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Error: Wrong parameters");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}	
	}
	
	
	/**
	 * Methode to delete a subtitles xml from myspace
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void myspaceDeleteSubtitles(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	
		User user = service.getSessionUser(session);

		if(user!=null && user.isActivate()) {
			
			// Get the course
			Course c = service.getCourse(Integer.parseInt(request.getParameter("courseid")));
			
			// Check user id
			if(c.getUserid()==user.getUserid()) {
				deleteSubtitles(request, response);
			}
			else { 
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "Error: You don't have access to this page");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
			}		
		}
		else { 
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Error: You don't have access to this page");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}	
	}
	
	/**
	 * Methode to delete a course from myspace
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void myspaceDeleteCourse(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
	    int courseid = Integer.parseInt(request.getParameter("id"));
	    Course c = service.getCourse(courseid);
	    service.deleteTag(courseid);
	    service.deleteCourse(courseid, c.getMediafolder(),false);
	    /* Generation of the RSS files */
		service.generateRss(c, getServletContext().getRealPath("/rss"), rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
	    
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeRemove, null);
		}	
		
		response.sendRedirect(response.encodeRedirectURL(session.getAttribute("previousPage")!=null ?
						"."+session.getAttribute("previousPage") : "./myspace_home"));
	}
	
	/**
	 * Method to upload a subtitles xml
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@SuppressWarnings("unchecked")
	private void addSubtitles(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		// Attribute
		String courseid, fileName, returnUrl;
		courseid = fileName = returnUrl = "";
		String message = "";
		String messageType = "information";
		String requestDispatcher = "/WEB-INF/views/message.jsp";
		
		if( ServletFileUpload.isMultipartContent(new ServletRequestContext(request)) ) {

			try {
				/* Prepares to parse the request to get the different elements of the POST */
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);

				/* Processes the different elements */
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();

					/* If the element is a form field, retrieves the info */
					if (item.isFormField()) {

						if(item.getFieldName().equals("courseid"))
							courseid = item.getString("UTF8");
						else if(item.getFieldName().equals("returnUrl"))
							returnUrl = item.getString("UTF8");

					} /* If the element is a file (the last element */
					else {
						fileName = item.getName();
						String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.') + 1,fileName.length()) : "";

						/* Checks the extension of the item to have a supported file format */				
						boolean isExtVal = extension.equals("xml");
																								
						// Test the form
						if(fileName==null || fileName.equals("")) {
							messageType = "error";
							message = "File must be completed";							
						}
						/* Checks the extension of the item to have a supported file format */
						else if(!isExtVal) {
							messageType = "error";
							message = "Error: Not supported file format. Only xml format.";
						}
						else {
							// Get the course
							Course c = service.getCourse(Integer.parseInt(courseid));

							// Add the document to the course									
							service.addSubtitles(c,item);
							requestDispatcher=returnUrl;
							
							// log stats
							if(logstats) {
								service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeAdd, fileName);
							}	
						}
					}
				}
			}
			catch( FileUploadException fue) {
				messageType = "error";
				message = "Error : File upload error";
			}
		}
		else {
			messageType = "error";
			message = "Error: Incorrect file upload request";
		}

		/* Displays the result of the upload process */
		request.setAttribute("messagetype", messageType);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher(requestDispatcher).forward(request, response);
	}
	
	
	/**
	 * Methode to delete a subtitles xml
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void deleteSubtitles(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
				
		// Get the course
		Course c = service.getCourse(Integer.parseInt(request.getParameter("courseid")));

		// Delete the document to the course									
		service.deleteSubtitles(c);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeRemove, null);
		}	
		
		/* Displays the result of the upload process */
		getServletContext().getRequestDispatcher(request.getParameter("returnUrl")).forward(request, response);
	}
	
	
	/**
	 * Method to request a local account 
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void displayAccountRequestForm(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
	
		String forwardUrl = "/WEB-INF/views/authentication/accountrequestform.jsp";		
		//request.setAttribute("gobackurl", "./authentication_local");
		request.setAttribute("univName", univName);
		session.setAttribute("previousPage", "/authentication_accountrequest");
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		getServletContext().getRequestDispatcher(forwardUrl).forward(request, response);
	}
	
	
	/**
	 * 
	 * Method to validate the request of a local account
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void validateAccountRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		//form valid
		boolean formValid = true;
		String err_message ="";
		
		// parameters
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String repeatpassword = request.getParameter("repeatpassword");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String comment = request.getParameter("comment");
		String establishment = request.getParameter("establishment");
		String post = request.getParameter("post");
		String captcha_answer = request.getParameter("captcha_answer");
		
		// email pattern
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		//captcha
		Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
		// password pattern (The password must be at least 8 characters, 1 digit and 1 lowercase)
		Pattern p_pwd = Pattern.compile("^(?=.*[0-9])(?=.*[a-z]).{8,}$");
				
		// Check the form
		if(email==null || email.equals("") || !p.matcher(email).matches()) {
			formValid = false;
			err_message = "err_email";
		}
		else if(password==null || password.equals("") || repeatpassword==null || repeatpassword.equals("") || !repeatpassword.equals(password) || !p_pwd.matcher(password).matches()) {
			formValid = false;
			err_message = "err_password";
		}
		else if(firstname==null || firstname.equals("")) {
			formValid = false;
			err_message = "err_firstname_local";
		}
		else if(lastname==null || lastname.equals("")) {
			formValid = false;
			err_message = "err_lastname_local";
		}
		else if(comment==null || comment.equals("")) {
			formValid = false;
			err_message = "err_comment";
		}
		else if(establishment==null || establishment.equals("")) {
			formValid = false;
			err_message = "err_establishment";
		}
		else if(post==null || post.equals("")) {
			formValid = false;
			err_message = "err_post";
		}
		else if(captcha_answer==null || captcha_answer.equals("") || !captcha.isCorrect(captcha_answer)) {
			formValid = false;
			err_message = "err_captcha";
		}
		// vérification si l'user existe deja	
		else if(service.getUser(email)!=null) {
			formValid = false;
			err_message = "err_user_exist";
		}
		
		
		// If the form is not valid
		if(!formValid) {		
			request.setAttribute("email", email);
			request.setAttribute("firstname", firstname);
			request.setAttribute("lastname", lastname);
			request.setAttribute("comment", comment);		
			request.setAttribute("establishment", establishment);		
			request.setAttribute("post", post);	
			
			request.setAttribute("messagetype", "error");
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
			request.setAttribute("message", bundle.getString(err_message));
			getServletContext().getRequestDispatcher("/avc/authentication_accountrequest").forward(request, response);
		}
		// If the formulaire is valid
		else {
			
			// Add user to database
			String hash = service.encrypt(password);
			service.addUser(new User(0, email, email, firstname, lastname, null, null, User.getTYPELOCAL(), false, null, null));
			service.modifyUserPassword(email, hash, "sha");
						
			// send email to admin
			String emailAdminSubject = "a new account request on AudioVideoCast";
			String emailAdminMessage = "Dear Admin,\n\nA new account request has been sent to AudioVideoCast:"
			+ "\n\n--------------------"	
			+ "\n\nEmail: " + email
			+ "\nFirstname: " + firstname
			+ "\nLastname: " + lastname
			+ "\nEstablishment: " + establishment
			+ "\nPost: " + post
			+ "\nReason for request: " + comment	
			+ "\n\n--------------------"
			+ "\n\nTo activate the account: " + serverUrl + "/avc/admin_users?login=" + email
			+ "\n\nBest Regards,\n\nAudioVideoCast Administrator";
			
			if(adminEmail1!=null && !adminEmail1.equals(""))
				service.sendMail(emailAdminSubject,emailAdminMessage,adminEmail1);
			if(adminEmail2!=null && !adminEmail2.equals(""))
				service.sendMail(emailAdminSubject,emailAdminMessage,adminEmail2);
			if(adminEmail3!=null && !adminEmail3.equals(""))
				service.sendMail(emailAdminSubject,emailAdminMessage,adminEmail3);
			
			// log stats
			if(logstats) {
				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
			}	
			
			/* Done message */
			String requestDispatcher = "/WEB-INF/views/message.jsp";
			request.setAttribute("messagetype", "information");
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
			request.setAttribute("message", bundle.getString("valid_accountrequest"));
			getServletContext().getRequestDispatcher(requestDispatcher).forward(request, response);
			
			
		}
		
		
	}
	
	
	/**
     * Method to authenticate a local user with a jsp page
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
	private void authenticationLocal(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		String returnPage = request.getParameter("returnPage");

		// If the local user is already authenticated
		if(session.getAttribute("$userLocalLogin")!=null) {
			
			// redirect to return page (publication)
			if(returnPage!=null && returnPage.equals("publication")) {
				response.sendRedirect("./publication?publication_type=serverLocal");
			}
			// return home by default
			else {
				response.sendRedirect("./myspace_home");
				
			}
		}
		// Show authentication form
		else {

			request.setAttribute("createaccounturl", "./authentication_accountrequest");
			request.setAttribute("forgotpassurl", "./authentication_forgotpass");
			session.setAttribute("previousPage", "/authentication_local");

			// add return page (publication)
			if(returnPage!=null && returnPage.equals("publication")) {
				request.setAttribute("gobackurl", "./publication");
				request.setAttribute("posturl", "./authentication_localvalid?returnPage="+returnPage);
			}
			//by default, valid the form to go to myspace
			else {				
				request.setAttribute("gobackurl", "./home");
				request.setAttribute("posturl", "./authentication_localvalid");
			}
			
			// log stats
			if(logstats) {
				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
			}	

			// Displays the view 
			getServletContext().getRequestDispatcher("/WEB-INF/views/authentication/login.jsp").forward(request, response);
		}
	}

	
	
	/**
     * Method to validate authentication form
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    private void validateAuthenticationLocal(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

    	ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
    	boolean captcha_valid = true;

    	// If user cas not connected
    	if(session.getAttribute(edu.yale.its.tp.cas.client.filter.CASFilter.CAS_FILTER_USER)==null) {

    		// Test form paramaters
    		if( ! (request.getParameter("login").equals("") || request.getParameter("password").equals(""))) {

    			// Test captcha
    			if(session.getAttribute("loginAttempt")!=null && (Integer)session.getAttribute("loginAttempt") > 3) {
    				//captcha
    				Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
    				String captcha_answer = request.getParameter("captcha_answer");

    				if(captcha_answer==null || captcha_answer.equals("") || !captcha.isCorrect(captcha_answer)) {
    					captcha_valid = false;
    				}

    			}

    			if(captcha_valid) {

    				//encrypt the password into SHA1        
    				String passSha = service.encrypt(request.getParameter("password"));
    				//Gets the user
    				User user = service.getUserLocal(request.getParameter("login"),passSha);

    				//if the user is authenticate and activate
    				if(user!=null) {
    					if(user.isActivate()) {
    						// Connection LOCAL
    						// Disconnect user LOCAL if exist
    						if(session.getAttribute("$userLocalLogin")!=null) {	
    							session.removeAttribute("$userLocalLogin");
    						}

    						session.setAttribute("$userLocalLogin", user.getLogin());
    						session.setAttribute("btnDeco", true);
    						session.removeAttribute("loginAttempt");
    						
    						// log stats
    						if(logstats) {
    							service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeLogin, null);
    						}	
    						
    						String returnPage = request.getParameter("returnPage");
    						// publication redirect
    						if(returnPage!=null && returnPage.equals("publication")) {
    							response.sendRedirect("./publication?publication_type=serverLocal");
    						}
    						// by default
    						else {
    							response.sendRedirect("./myspace_home");
    							
    						}
    						
    						
    					}
    					else {
    						request.setAttribute("messagetype", "error");
    						request.setAttribute("message", bundle.getString("err_account_not_activated"));
    						request.setAttribute("login", request.getParameter("login"));
    						getServletContext().getRequestDispatcher("/avc/authentication_local").forward(request, response);			
    					}
    				}
    				else {

    					Integer loginAttempt = session.getAttribute("loginAttempt")!=null ? (Integer) session.getAttribute("loginAttempt") : 0;
    					session.setAttribute("loginAttempt",(loginAttempt+1));

    					request.setAttribute("messagetype", "error");
    					request.setAttribute("message", bundle.getString("err_login_pass"));
    					request.setAttribute("login", request.getParameter("login"));
    					getServletContext().getRequestDispatcher("/avc/authentication_local").forward(request, response);			
    				}
    			}
    			else {
    				request.setAttribute("messagetype", "error");
    				request.setAttribute("message", bundle.getString("err_captcha"));
    				request.setAttribute("login", request.getParameter("login"));
    				getServletContext().getRequestDispatcher("/avc/authentication_local").forward(request, response);
    			}

    		}
    		else {
    			request.setAttribute("messagetype", "error");
    			request.setAttribute("message", bundle.getString("err_all_fields"));
    			request.setAttribute("login", request.getParameter("login"));
    			getServletContext().getRequestDispatcher("/avc/authentication_local").forward(request, response);
    		}


    	}
    	else {
    		request.setAttribute("messagetype", "error");
    		request.setAttribute("message", bundle.getString("err_disconnect_cas"));
    		request.setAttribute("login", request.getParameter("login"));
    		getServletContext().getRequestDispatcher("/avc/authentication_local").forward(request, response);			
    	}
    }

	/**
     * Method display change password form
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    private void myspaceChangePass(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
    	
    	User user =null;
    	ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
        
    	// Only local user can change his password
    	if(session.getAttribute("$userLocalLogin")!=null) {
			user=service.getUser(session.getAttribute("$userLocalLogin").toString());
		}
    	    	
    	// Gets the user's login from the session		
		if(user!=null && user.isActivate()) {

			request.setAttribute("posturl", "./myspace_changepassvalid");
	        request.setAttribute("gobackurl", "./myspace_home");
	        session.setAttribute("previousPage", "/myspace_changepass");   
	        
	        // log stats
	        if(logstats) {
	        	service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
	        }	
	        
	        // Displays the view 
	        getServletContext().getRequestDispatcher("/WEB-INF/views/myspace/myspace_changepass.jsp").forward(request, response);
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", bundle.getString("err_acces"));
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			
		}
    	
    	
    }
    
    
	/**
     * Method to valid the change password form
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    private void myspaceChangePassValid(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
    	
    	User user =null;
    	ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
        
    	// Only local user can change his password
    	if(session.getAttribute("$userLocalLogin")!=null) {
			user=service.getUser(session.getAttribute("$userLocalLogin").toString());
		}
    	    	
    	// Gets the user's login from the session		
		if(user!=null && user.isActivate()) {
			
			String currentPassword = request.getParameter("currentPassword");
			String newPassword = request.getParameter("newPassword");
			String confirmNewPass = request.getParameter("confirmNewPass");
			// password pattern (The password must be at least 8 characters, 1 digit and 1 lowercase)
			Pattern p_pwd = Pattern.compile("^(?=.*[0-9])(?=.*[a-z]).{8,}$");
						
			if(currentPassword==null || currentPassword.equals("") || newPassword==null || newPassword.equals("") || confirmNewPass==null || confirmNewPass.equals("")) {	
				request.setAttribute("messagetype", "error");
    			request.setAttribute("message", bundle.getString("err_all_fields"));
    			getServletContext().getRequestDispatcher("/avc/myspace_changepass").forward(request, response);			
			}
			else {
				// check current password
				//encrypt the password into SHA1        
				String currentPasswordSha = service.encrypt(currentPassword);
				User user_currentpass = service.getUserLocal(user.getLogin(),currentPasswordSha);
				
				// if current password is ok
				if(user_currentpass!=null && newPassword.equals(confirmNewPass) && p_pwd.matcher(newPassword).matches()) {
					String newPasswordSha = service.encrypt(newPassword);
					service.modifyUserPassword(user.getLogin(), newPasswordSha, "sha");
					
					// log stats
					if(logstats) {
						service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
					}	
					
					request.setAttribute("messagetype", "information");
					request.setAttribute("message", bundle.getString("passchanged"));
					getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
				}
				else {
					request.setAttribute("messagetype", "error");
	    			request.setAttribute("message", bundle.getString("err_password"));
	    			getServletContext().getRequestDispatcher("/avc/myspace_changepass").forward(request, response);		
				}
    		}
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", bundle.getString("err_acces"));
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			
		}
    }
    
    
    /**
     * Method to display forgot password form
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    private void displayForgotPassForm(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
    	
    	// log stats
    	if(logstats) {
    		service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
    	}	
    	
		getServletContext().getRequestDispatcher("/WEB-INF/views/authentication/forgotpass.jsp").forward(request, response);	
    	
    }
    
    
    
    /**
     * Method to valid the forgot password form
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    private void validateForgotPassForm(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
    	    	
    	String email = request.getParameter("email");
     	ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
     	Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
     	
    	if(email==null || email.equals("") || !p.matcher(email).matches()) {
    		request.setAttribute("messagetype", "error");
			request.setAttribute("message", bundle.getString("err_email"));
			getServletContext().getRequestDispatcher("/avc/authentication_forgotpass").forward(request, response);

    	}
    	else {
    		    		
    		User user = service.getUser(email);    		
    		if(user==null) {
    			request.setAttribute("messagetype", "error");
    			request.setAttribute("message", bundle.getString("err_forgotpass"));
    			getServletContext().getRequestDispatcher("/avc/authentication_forgotpass").forward(request, response);

    		}
    		else {
    			// reinitialisation mot de pass VALIDATION 1 HEURE
    			
    			
    			String pass = service.generatePassword(16);
    			String hash = service.encrypt(email + pass);		
    			service.modifyUserResetCode(email, hash, "sha", new Timestamp(new Date().getTime()));
    	
    			// Sending email for the user
				String emailUserSubject = "AudioVideoCast: Réinitialisation du mot de passe / Reset password";
						
						String emailUserMessageFr = "Bonjour,\n\nVous pouvez réinitialiser votre mot de passe avec l'url suivante. Cette url est valide une heure.\n"
							+ serverUrl + "/avc/authentication_resetpass?hash=" + hash
							+"\n\nPour toute question sur l'usage de la plateforme AudioVideoCast,"
							+"\n- contactez le support : " + supportLink
							+"\n- ou consultez la documentation : " + docLink
							+ "\n\nBien cordialement,\n\nL'équipe AudioVideoCast";
												
						String emailUserMessageEn = "Hello,\n\nYou can reset your password using the following url. This url is valid one hour.\n"
						+ serverUrl + "/avc/authentication_resetpass?hash=" + hash
						+"\n\nFor any question,"
						+"\n- contact the support : " + supportLink
						+"\n- or read the documentation : " + docLink
						+ "\n\nBest Regards,\n\nAudioVideoCast team";
												
						
						String emailUserMessage = emailUserMessageFr + "\n\n\n********************\n\n\n" + emailUserMessageEn;
						
							
						service.sendMail(emailUserSubject,emailUserMessage,email);
						
						// log stats
						if(logstats) {
							service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
						}	
						
						request.setAttribute("messagetype", "information");
						request.setAttribute("message", bundle.getString("forgotpassmessage2"));
						getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			
				
    		}
    		
    	}
    	
    }
    
    
    
    
    /**
     * Method to display reset password form
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    private void displayResetPassForm(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
    	
    	String hash = request.getParameter("hash");
    	ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
        	    	
    	if(hash!=null && !hash.equals("")) {
    		User user = service.getUserLocalByResetCode(hash);
    		if(user !=null) {
    			
    			// log stats
    			if(logstats) {
    				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
    			}	
    			
    			request.setAttribute("hash", hash);
    			getServletContext().getRequestDispatcher("/WEB-INF/views/authentication/resetpass.jsp").forward(request, response);	
    		}
    		else {
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", bundle.getString("wrongresetcode"));
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			

    		}
    		
    	
    	}
    		
    }
    
    
    
    /**
     * Method to valid the reset password form
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    private void validateResetPassForm(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {

    	String hash = request.getParameter("hash");
    	ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));

    	if(hash!=null && !hash.equals("")) {
    		User user = service.getUserLocalByResetCode(hash);

    		// Gets the user's login from the session		
    		if(user!=null && user.isActivate()) {

    			String newPassword = request.getParameter("newPassword");
    			String confirmNewPass = request.getParameter("confirmNewPass");
    			// password pattern (The password must be at least 8 characters, 1 digit and 1 lowercase)
    			Pattern p_pwd = Pattern.compile("^(?=.*[0-9])(?=.*[a-z]).{8,}$");

    			if(newPassword==null || newPassword.equals("") || confirmNewPass==null || confirmNewPass.equals("") || !newPassword.equals(confirmNewPass) || !p_pwd.matcher(newPassword).matches()) {	
    				request.setAttribute("messagetype", "error");
    				request.setAttribute("message", bundle.getString("err_password"));
    				getServletContext().getRequestDispatcher("/avc/authentication_resetpass").forward(request, response);			
    			}
    			else {
    				//change password
    				String newPasswordSha = service.encrypt(newPassword);
					service.modifyUserPassword(user.getLogin(), newPasswordSha, "sha");
					//remove reset code
					service.modifyUserResetCode(user.getLogin(), null, null, null);
					
					// log stats
					if(logstats) {
						service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
					}	
										
					request.setAttribute("messagetype", "information");
					request.setAttribute("message", bundle.getString("passchanged"));
					getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);	
		
    			}
    		}
    		else {
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", bundle.getString("wrongresetcode"));
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			

    		}


    	}

    }
    
    
    
    
    
    /**
	 * Method to change the user activation
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void userActivate(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
				
		Integer userid = Integer.valueOf(request.getParameter("userid"));
		String activate = request.getParameter("activate");
		
		/* Verifies that all parameters are sent and are not empty */
		if( userid != null && activate != null && ! userid.equals("") && ! activate.equals("")) {
			
			/* Verifies that the "status" variable contains one of the two authorized strings */
			if( activate.equals("true") || activate.equals("false")) {
				
				try {
					
					// log stats
					if(logstats) {
						service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "User : " + userid);
					}	
					
					User u = service.getUser(userid);
					u.setActivate(Boolean.valueOf(activate));
					service.modifyUser(u);	
										
					request.setAttribute("messagetype", "information");
					if(u.isActivate())
						request.setAttribute("message", "Account of user " + u.getLogin() + " activated !");
					else
						request.setAttribute("message", "Account of user " + u.getLogin() + " desactivated !");
					
					request.setAttribute("message2", "Return to users list :");
					request.setAttribute("ahref2", serverUrl + "/avc/admin_users");
					getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
					
				}
				catch (Exception e) {
					request.setAttribute("messagetype", "error");
					request.setAttribute("message", "Error: can't change the user activation :" + e);
					getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
				}
				
			}
			else {
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "Error: possible status values for activate : {true ; false}");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
			}
		} 
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Error: missing parameters: userid and activate must be sent");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}
		
		
	}
	
	 /**
	 * Method admin users list
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminUsers(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		request.setAttribute("viewurl", "./admin_users");	
		
		int start = 0;
		int pageNumber;
		int usersnumber = 100;
		
		/* initializes the model */
		if( request.getParameter("page") != null) {
			pageNumber = Integer.parseInt( request.getParameter("page"));
			start = usersnumber * (pageNumber - 1) ;
		}
		else
			pageNumber = 1;
		
		String login = request.getParameter("login");
		List<User> users = new ArrayList<User>();
		if(login==null || login.equals("") ) {
			users = service.getAllUsers(usersnumber,start);
			request.setAttribute("items", service.getUsersNumber());
		}
		else if(login!=null && !login.equals("")) {
			User u = service.getUser(login);
			if(u!=null)
				users.add(u);
				request.setAttribute("items", 1);
				request.setAttribute("login", login);
		}
		
		request.setAttribute("page", pageNumber);
		request.setAttribute("users", users );
		request.setAttribute("resultPage", "admin_users");
		request.setAttribute("number", usersnumber);
		request.setAttribute("editurl", "./admin_edituser");
		request.setAttribute("deleteurl", "./admin_deleteuser");
		
		session.setAttribute("previousPage", "./admin_users?page=" + pageNumber);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_users.jsp").forward(request, response);

	}
	
	
	/**
	 * Method to replace the main media of a course by a video
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void myspaceReplaceMedia(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		User user = service.getSessionUser(session);

		if(user!=null && user.isActivate()) {

			String courseid = request.getParameter("courseid");

			if(courseid!=null) {
				// Get the course
				Course c = service.getCourse(Integer.parseInt(courseid));

				// Check user id
				if(c.getUserid()==user.getUserid()) {
					
					// If the course already have an additional video or if a job already running, don't lauch replace media
					Job j = service.getJob(c.getCourseid(), "ADDV");
					boolean notReplaceMedia =  c.isAvailable("addvideo") || (j!=null && !j.getStatus().equals("done"));
					if (!notReplaceMedia) {
						replaceMedia(request,response);
					}
					else {
						request.setAttribute("messagetype", "error");
						request.setAttribute("message", "Error: Add video job already in process");
						getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
					}
				}
				else { 
					request.setAttribute("messagetype", "error");
					request.setAttribute("message", "Error: You don't have access to this page");
					getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
				}		

			}
			else { 
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "Error: You don't have access to this page");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
			}	

		}
		else { 
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Error: Wrong parameters");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}	
	}
	
	
	
	/**
	 * Method to replace media by a video
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@SuppressWarnings("unchecked")
	private void replaceMedia(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		// Attribute
		String courseid, fileName, slidesoffset, returnUrl;
		courseid = fileName = slidesoffset = returnUrl = "";
		String message = "";
		String messageType = "information";
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) session.getAttribute("language")));
				
		if( ServletFileUpload.isMultipartContent(new ServletRequestContext(request)) ) {

			try {
				/* Prepares to parse the request to get the different elements of the POST */
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);

				/* Processes the different elements */
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();

					/* If the element is a form field, retrieves the info */
					if (item.isFormField()) {

						if(item.getFieldName().equals("courseid"))
							courseid = item.getString("UTF8");
						else if(item.getFieldName().equals("slidesoffset"))
							slidesoffset = item.getString("UTF8");
						else if(item.getFieldName().equals("returnUrl"))
							returnUrl = item.getString("UTF8");
						
					} /* If the element is a file (the last element */
					else {
						fileName = item.getName();
						String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf('.') + 1,fileName.length()) : "";

						/* Checks the extension of the item to have a supported file format */							
						StringTokenizer tokenMediaFormats = new StringTokenizer(uploadFormats);
						boolean isExtVal = false;
						while(tokenMediaFormats.hasMoreTokens() && !isExtVal) {
							isExtVal = extension.equals(tokenMediaFormats.nextToken());
						}
																								
						// Test the form
						if(fileName==null || fileName.equals("")) {
							messageType = "error";
							message = bundle.getString("err_file");							
						}
						/* Checks the extension of the item to have a supported file format */
						else if(!isExtVal) {
							messageType = "error";
							message = bundle.getString("err_fileformat")+ " : " + extension;
						}
						else {
							// Get the course
							Course c = service.getCourse(Integer.parseInt(courseid));
							Integer offsetInt = 0;
							
							try {
								offsetInt = (slidesoffset==null || slidesoffset.equals("")) ? 0 : Integer.valueOf(slidesoffset);
														
								// Replace media	
								service.addVideo(c,offsetInt,item,serverUrl, sepEnc, coursesFolder);
																		
								messageType = "information";
								message = bundle.getString("uploadaddvideo");
								
								// log stats
								if(logstats) {
									service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeAdd, fileName);
								}	
															
							}
							catch(NumberFormatException e) {
								messageType = "error";
								message = bundle.getString("error_offset");
							}						
						}
					}
				}
			}
			catch( FileUploadException fue) {
				messageType = "error";
				message = "Error : File upload error";
			}
		}
		else {
			messageType = "error";
			message = "Error: Incorrect file upload request";
		}

		/* Displays the result of the upload process */
		request.setAttribute("messagetype", messageType);
		request.setAttribute("message", message);
		getServletContext().getRequestDispatcher(returnUrl).forward(request, response);
	}
	
	
	
	/**
	 * Methode to delete the additional video
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void myspaceDeleteReplaceMedia(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	
		User user = service.getSessionUser(session);

		if(user!=null && user.isActivate()) {
			
			// Get the course
			Course c = service.getCourse(Integer.parseInt(request.getParameter("courseid")));
			
			// Check user id
			if(c.getUserid()==user.getUserid()) {
				// If the course don't have an additional video or if a job already running, don't lauch delete replace media
				Job j = service.getJob(c.getCourseid(), "ADDV");
				boolean notdeleteReplaceMedia =  !c.isAvailable("addvideo") || (j!=null && !j.getStatus().equals("done"));
				if (!notdeleteReplaceMedia) {
					deleteReplaceMedia(request, response);
				}
				else {
					request.setAttribute("messagetype", "error");
					request.setAttribute("message", "Error: Add video job already in process");
					getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
				}
			}
			else { 
				request.setAttribute("messagetype", "error");
				request.setAttribute("message", "Error: You don't have access to this page");
				getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
			}		
		}
		else { 
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Error: You don't have access to this page");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}	
		
	}
	
	
	/**
	 * Methode to delete the additional video
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void deleteReplaceMedia(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
				
		// Get the course
		Course c = service.getCourse(Integer.parseInt(request.getParameter("courseid")));

		// Delete the document to the course									
		service.deleteReplaceMedia(c);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeRemove, null);
		}	
		
		/* Displays the result of the upload process */
		getServletContext().getRequestDispatcher(request.getParameter("returnUrl")).forward(request, response);
	}
	
	/**
	 * Method to change the interface (flash or html5) of the website
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void changeInterface(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String recordinterface = request.getParameter("recordinterface");
		/* Stores the language in the session */
		session.setAttribute("recordinterface", recordinterface);
		/* Stores the language in the cookies */
		Cookie recordinterfaceCookie = new Cookie("recordinterface", recordinterface);
		recordinterfaceCookie.setMaxAge(31536000);
		response.addCookie(recordinterfaceCookie);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, recordinterface);
		}	
				
		// return to the course access page
		String courseid = request.getParameter("courseid");
		response.sendRedirect("./courseaccess?id="+courseid);
	}
	
	/**
	 * Method to contact us
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void contactUs(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/contactUs");
		request.setAttribute("posturl", "/avc/contactUsSendMail");
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/contactUs.jsp").forward(request, response);
	}
	
	/**
	 * Method to delete tests
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void deleteTests(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		service.deleteTests(testKeyWord1);
		service.hideTests(testKeyWord2, testKeyWord3);
		/* Regeneration of the RSS files */
		service.generateRss(getServletContext().getRealPath("/rss"), rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);

		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
	}
	
	/**
	 * Thick style
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void thickStyles(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		List<String> styles = service.getStyles(getServletContext().getRealPath("/") + "files/styles/");
		request.setAttribute("styles", styles );
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}			
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_styles.jsp").forward(request, response);
	}
	
	
	/**
	 * Thick languages
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void thickLanguages(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		List<String> languages = service.getLanguages(getServletContext().getRealPath("/") + "WEB-INF/classes/org/ulpmm/univrav/language");
		request.setAttribute("languages", languages );
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_languages.jsp").forward(request, response);
		
	}
	
	/**
	 * Thick legal
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void thickLegal(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_legal.jsp").forward(request, response);
	}
      
	/**
	 * Thick Replacemedia
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void thickReplacemedia(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_replacemedia.jsp").forward(request, response);

	}
	
	/**
	 * Thick download
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void thickDownload(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("clientLink", clientLink);
		request.setAttribute("tracLink", tracLink);
		getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_download.jsp").forward(request, response);

	}


	/**
	 * Thick help
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void thickHelp(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("supportLink", supportLink);
		request.setAttribute("helpLink", helpLink);
		request.setAttribute("docLink",docLink);
		request.setAttribute("contactUs", contactUs);
		getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_help.jsp").forward(request, response);
	}

	/**
	 * Thick myspace
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void thickMyspace(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("univAcronym", univAcronym);	
		request.setAttribute("univName", univName);
		getServletContext().getRequestDispatcher("/WEB-INF/views/include/thick_myspace.jsp").forward(request, response);
	}
	
	/**
	 * Admin home
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminHome(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}
		
		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/admin_home");
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_home.jsp").forward(request, response);

	}
	
	/**
	 * Admin course
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminCourse(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		List<Course> courses = service.getAllCourses(false,false,null);
		request.setAttribute("courses",courses);
		request.setAttribute("number", courses.size());
		request.setAttribute("viewurl", "./admin_courses");
		request.setAttribute("editurl", "./admin_editcourse");
		request.setAttribute("deleteurl", "./admin_deletecourse");
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_courses.jsp").forward(request, response);

	}
	
	/**
	 * Admin delete course
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminDeletecourse(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		int courseid = Integer.parseInt(request.getParameter("id"));
		Course c = service.getCourse(courseid);
		service.deleteTag(courseid);
		service.deleteCourse(courseid, c.getMediafolder(),true);
		/* Regeneration of the RSS files */
		service.generateRss(c, getServletContext().getRealPath("/rss"), rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), c, LogUserAction.typeRemove, null);
		}	
		
		response.sendRedirect(response.encodeRedirectURL("./admin_courses"));

	}

	/**
	 * Admin replace media
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminReplacemedia(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String courseid = request.getParameter("courseid");
		Course c = service.getCourse(Integer.parseInt(courseid));
		// If the course already have an additional video or if a job already running, don't lauch replace media
		Job j = service.getJob(c.getCourseid(), "ADDV");
		boolean notReplaceMedia =  c.isAvailable("addvideo") || (j!=null && !j.getStatus().equals("done"));
		if (!notReplaceMedia) {
			replaceMedia(request,response);
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Error: Add video job already in process");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}

	}

	/**
	 * Admin delete replace media
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminDeleteReplacemedia(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String courseid = request.getParameter("courseid");
		Course c = service.getCourse(Integer.parseInt(courseid));
		// If the course don't have an additional video or if a job already running, don't lauch delete replace media
		Job j = service.getJob(c.getCourseid(), "ADDV");
		boolean notdeleteReplaceMedia =  !c.isAvailable("addvideo") || (j!=null && !j.getStatus().equals("done"));
		if (!notdeleteReplaceMedia) {
			deleteReplaceMedia(request, response);
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "Error: Add video job already in process");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);
		}

	}
	
	/**
	 * Admin buildings
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminBuildings(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("buildings", service.getBuildings());
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_buildings.jsp").forward(request, response);

	}

	/**
	 * Admin add building
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminAddBuilding(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("action","add"); 
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_editbuilding.jsp").forward(request, response);

	}
	
	/**
	 * Admin edit building
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminEditBuilding(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "Building : " + request.getParameter("id"));
		}	
		
		request.setAttribute("action","edit"); 
		request.setAttribute("building", service.getBuilding(Integer.parseInt(request.getParameter("id"))));
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_editbuilding.jsp").forward(request, response);

	}
	
	/**
	 * Admin delete building
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminDeleteBuilding(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeRemove, "Building : " + request.getParameter("id"));
		}	
		
		int buildingid = Integer.parseInt(request.getParameter("id"));
		service.deleteBuilding(buildingid);
		response.sendRedirect(response.encodeRedirectURL("./admin_buildings"));

	}

	/**
	 * Admin amphis
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminAmphis(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "Building : " + request.getParameter("buildingId"));
		}	
		
		request.setAttribute("buildingId", request.getParameter("buildingId"));
		request.setAttribute("buildingName", service.getBuilding(Integer.parseInt(request.getParameter("buildingId"))).getName());
		request.setAttribute("amphis", service.getAmphis(Integer.parseInt(request.getParameter("buildingId"))));
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_amphis.jsp").forward(request, response);

	}

	/**
	 * Admin add amphi
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminAddAmphi(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "Building : " + request.getParameter("buildingId"));
		}	
		
		request.setAttribute("buildingId", request.getParameter("buildingId"));
		request.setAttribute("action","add"); 
		// univ acronym
		request.setAttribute("univAcronym", univAcronym);
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_editamphi.jsp").forward(request, response);

	}

	/**
	 * Admin edit amphi
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminEditAmphi(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "Building : " + request.getParameter("buildingId"));
		}	
		
		request.setAttribute("buildingId", request.getParameter("buildingId"));
		request.setAttribute("buildingName", service.getBuilding(Integer.parseInt(request.getParameter("buildingId"))).getName());
		request.setAttribute("action","edit"); 
		request.setAttribute("amphi", service.getAmphi(Integer.parseInt(request.getParameter("id"))));
		// univ acronym
		request.setAttribute("univAcronym", univAcronym);
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_editamphi.jsp").forward(request, response);

	}

	/**
	 * Admin delete amphi
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminDeleteAmphi(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeRemove, "Amphi : " + request.getParameter("id"));
		}	
		
		int amphiid = Integer.parseInt(request.getParameter("id"));
		service.deleteAmphi(amphiid);
		response.sendRedirect(response.encodeRedirectURL("./admin_amphis?buildingId=" + request.getParameter("buildingId")));

	}

	/**
	 * Admin edit user
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminEditUser(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "User : " + request.getParameter("id"));
		}	
		
		List<String> types = Arrays.asList("","ldap","local");
		request.setAttribute("types",types);
		request.setAttribute("action","edit"); 
		request.setAttribute("user", service.getUser(Integer.parseInt(request.getParameter("id"))));
		request.setAttribute("posturl", "./admin_validateuser");
		request.setAttribute("gobackurl", "./admin_users");
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_edituser.jsp").forward(request, response);

	}

	/**
	 * Admin delete user
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminDeleteUser(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		Integer userid = request.getParameter("id")!=null ? Integer.parseInt(request.getParameter("id")) : null;
		if(service.getCourseNumber(service.getUser(userid))==0) {
			service.deleteUser(userid);
			
			// log stats
			if(logstats) {
				service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeRemove, "User : " + userid);
			}	
			
			response.sendRedirect(response.encodeRedirectURL("./admin_users"));
		}
		else {
			request.setAttribute("messagetype", "error");
			request.setAttribute("message", "You cannot delete this user because he have courses");
			getServletContext().getRequestDispatcher("/WEB-INF/views/message.jsp").forward(request, response);			
		}

	}

	/**
	 * Admin add user
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminAddUser(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		List<String> types = Arrays.asList("","ldap","local");
		request.setAttribute("types",types);
		request.setAttribute("action","add"); 
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_edituser.jsp").forward(request, response);

		
	}

	/**
	 * Admin selections
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminSelections(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("viewurl", "./admin_selections");
		List<Selection> selections = service.getAllSelections();
		request.setAttribute("selections", selections );
		request.setAttribute("number", selections.size());
		request.setAttribute("editurl", "./admin_editselection");
		request.setAttribute("deleteurl", "./admin_deleteselection");
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_selections.jsp").forward(request, response);

	}
	
	/**
	 * Admin edit selection
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminEditSelection(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "Selection : " + request.getParameter("id"));
		}	
		
		request.setAttribute("action","edit"); 
		request.setAttribute("selection", service.getSelection(Integer.parseInt(request.getParameter("id"))));
		request.setAttribute("posturl", "./admin_validateselection");
		request.setAttribute("gobackurl", "./admin_selections");
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_editselection.jsp").forward(request, response);

	}

	/**
	 * Admin delete selection
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminDeleteSelection(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
					
		Integer selectionid = request.getParameter("id")!=null ? Integer.parseInt(request.getParameter("id")) : null;
		service.deleteSelection(selectionid);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeRemove, "Selection : " + selectionid);
		}	
				
		response.sendRedirect(response.encodeRedirectURL("./admin_selections"));

	}

	/**
	 * Admin add selection
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminAddSelection(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("action","add"); 
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_editselection.jsp").forward(request, response);

	}
	
	/**
	 * Admin teachers
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminTeachers(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("viewurl", "./admin_teachers");
		List<Teacher> teachers = service.getAllTeachers();
		request.setAttribute("teachers", teachers );
		request.setAttribute("number", teachers.size());
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_teachers.jsp").forward(request, response);

	}

	/**
	 * Admin stats
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminStats(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("diskspaceinfo", service.getDiskSpaceInfo());
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_stats.jsp").forward(request, response);

	}
	
	/**
	 * Admin jobs
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminJobs(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("viewurl", "./admin_jobs");
		List<Job> jobs = service.getAllJobs();
		request.setAttribute("jobs", jobs );
		request.setAttribute("number", jobs.size());
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_jobs.jsp").forward(request, response);

	}

	/**
	 * Admin jobs
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminDisciplines(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("viewurl", "./admin_disciplines");
		List<Discipline> disciplines = service.getAllDiscipline();
		request.setAttribute("disciplines", disciplines );
		request.setAttribute("number", disciplines.size());
		request.setAttribute("editurl", "./admin_editdiscipline");
		request.setAttribute("deleteurl", "./admin_deletediscipline");
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_disciplines.jsp").forward(request, response);

	}

	/**
	 * Admin add discipline
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminAddDiscipline(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		request.setAttribute("action","add"); 
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_editdiscipline.jsp").forward(request, response);

	}

	/**
	 * Admin edit discipline
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminEditDiscipline(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "Discipline : " + request.getParameter("id"));
		}	
		
		request.setAttribute("action","edit"); 
		request.setAttribute("discipline", service.getDiscipline(Integer.parseInt(request.getParameter("id"))));
		request.setAttribute("posturl", "./admin_validatediscipline");
		request.setAttribute("gobackurl", "./admin_disciplines");
		getServletContext().getRequestDispatcher("/WEB-INF/views/admin/admin_editdiscipline.jsp").forward(request, response);

	}

	/**
	 * Admin delete discipline
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void adminDeleteDiscipline(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		Integer disciplineid = request.getParameter("id")!=null ? Integer.parseInt(request.getParameter("id")) : null;
		service.deleteDiscipline(disciplineid);
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeRemove, "Discipline : " + disciplineid);
		}	
		
		response.sendRedirect(response.encodeRedirectURL("./admin_disciplines"));

	}
	
	
	/**
	 * gp home
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void gpHome(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, null);
		}	
		
		/* Saves the page for the style selection thickbox return */
		session.setAttribute("previousPage", "/gp_home");
		request.setAttribute("buildings", service.getBuildings());
		getServletContext().getRequestDispatcher("/WEB-INF/views/gp/gp_home.jsp").forward(request, response);

	}
	
	/**
	 * gp edit building
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void gpEditBuilding(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "Building : " + request.getParameter("id"));
		}	
		
		request.setAttribute("action","edit"); 
		request.setAttribute("building", service.getBuilding(Integer.parseInt(request.getParameter("id"))));
		getServletContext().getRequestDispatcher("/WEB-INF/views/gp/gp_editbuilding.jsp").forward(request, response);

	}

	/**
	 * gp amphis
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void gpAmphis(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "Building : " + request.getParameter("buildingId"));
		}	
		
		request.setAttribute("buildingId", request.getParameter("buildingId"));
		request.setAttribute("buildingName", service.getBuilding(Integer.parseInt(request.getParameter("buildingId"))).getName());
		request.setAttribute("amphis", service.getAmphis(Integer.parseInt(request.getParameter("buildingId"))));
		getServletContext().getRequestDispatcher("/WEB-INF/views/gp/gp_amphis.jsp").forward(request, response);

	}
	
	/**
	 * gp edit amphi
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private void gpEditAmphi(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		// log stats
		if(logstats) {
			service.addLogUserAction(request, service.getSessionUser(session), null, LogUserAction.typeAccess, "Building : " + request.getParameter("buildingId"));
		}	
		
		request.setAttribute("buildingId", request.getParameter("buildingId"));
		request.setAttribute("buildingName", service.getBuilding(Integer.parseInt(request.getParameter("buildingId"))).getName());
		request.setAttribute("action","edit"); 
		request.setAttribute("amphi", service.getAmphi(Integer.parseInt(request.getParameter("id"))));
		// univ acronym
		request.setAttribute("univAcronym", univAcronym);
		getServletContext().getRequestDispatcher("/WEB-INF/views/gp/gp_editamphi.jsp").forward(request, response);

	}

	
	

}
