package org.ulpmm.univrav.service;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.dao.ILdapAccess;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Discipline;
import org.ulpmm.univrav.entities.Job;
import org.ulpmm.univrav.entities.Level;
import org.ulpmm.univrav.entities.LogUserAction;
import org.ulpmm.univrav.entities.Selection;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Tag;
import org.ulpmm.univrav.entities.Teacher;
import org.ulpmm.univrav.entities.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Service implementation methods.
 * 
 * @author morgan
 *
 */
public class ServiceImpl implements IService {

	/** Database interface */
	private IDatabase db;
	
	/** File system interface */
	private IFileSystem fs;
		
	/** Ldap interface */
	private ILdapAccess ldap;
	
	/** Logger log4j */
	private static final Logger logger = Logger.getLogger(ServiceImpl.class);
	
	/**
	 * Gets the database interface
	 * @return the database interface
	 */
	public IDatabase getDb() {
		return db;
	}

	/**
	 * Sets the database interface
	 * @param db the database interface
	 */
	public void setDb(IDatabase db) {
		this.db = db;
	}
	
	/**
	 * Gets the filesystem interface
	 * @return the filesystem interface
	 */
	public IFileSystem getFs() {
		return fs;
	}

	/**
	 * Sets the the filesystem interface
	 * @param fs the filesystem interface
	 */
	public void setFs(IFileSystem fs) {
		this.fs = fs;
	}
	
	/**
	 * Gets the ldap access interface
	 * @return the ldap access interface
	 */
	public ILdapAccess getLdap() {
		return ldap;
	}

	/**
	 * Sets the ldap access interface
	 * @param ldap the ldap to set
	 */
	public void setLdap(ILdapAccess ldap) {
		this.ldap = ldap;
	}
	
	/**
	 * Get the full name of formation. Matching with level and discipline tables.
	 * @param formation the course formation codes
	 * @return the full name of formation
	 */
	public String getFormationFullName(String formation) {
		return db.getFormationFullName(formation);
	}

	/**
	 * Adds a new course
	 * @param c the course to add
	 * @param courseArchive the archive file of the course to add
	 * @param tags list of tags
	 * @param serverUrl the URL of the application on the server
	 * @param sepEnc true if medias encodage is separated
	 * @param coursesFolder the courses folder
	 */
	public synchronized void addCourse(Course c, String courseArchive, String tags, String serverUrl, boolean sepEnc,String coursesFolder) {
		
		CourseAddition ca = new CourseAddition(db, fs, c, courseArchive, tags, this, serverUrl,sepEnc,coursesFolder);
		ca.start();
	}
		
	/**
	 * Creates a course from an uploaded audio or video media file
	 * @param c the course to create
	 * @param mediaFile the media file of the course to create
	 * @param tags tags list
	 * @param serverUrl the URL of the application on the server
	 * @param sepEnc true if medias encodage is separated
	 * @param coursesFolder the courses folder
	 */
	public synchronized void mediaUpload( Course c, FileItem mediaFile, String tags, String serverUrl, boolean sepEnc,String coursesFolder) {
		MediaUpload mu = new MediaUpload(db, fs, c, mediaFile, tags, this, serverUrl, sepEnc,coursesFolder);
		mu.start();
	}
	
	/**
	 * Gets a list of all the courses
	 * @param onlyvisible true to get only visible courses
	 * @param formationfullname show full name of formation with discipline and level table
	 * @param limit limit number of courses
	 * @return the list of courses
	 */
	public List<Course> getAllCourses(boolean onlyvisible, boolean formationfullname, Integer limit) {
		return db.getAllCourses(onlyvisible,formationfullname,limit);
	}
		
	/**
	 * Gets a list of the n last courses
	 * @param n the number of courses to return
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getNLastCourses(int n, String testKeyWord1, String testKeyWord2) {
		return db.getNLastCourses(n, testKeyWord1, testKeyWord2);
	}
	
	/**
	 * Gets a restricted list of courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getCourses(int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		return db.getCourses(number, start, testKeyWord1, testKeyWord2, testKeyWord3);
	}
	
	/**
	 * Gets a restricted list of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getCourses(HashMap<String, String> params, int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		return db.getCourses(params, number, start, testKeyWord1, testKeyWord2, testKeyWord3);
	}
	
	/**
	 * Gets a course by providing its id
	 * @param courseId the id of the course
	 * @return the course
	 */
	public Course getCourse(int courseId) {
		return db.getCourse(courseId);
	}

	/**
	 * Gets a course by providing its id and genre
	 * @param courseId the id of the course
	 * @param genre the genre of the course
	 * @return the course
	 */
	public Course getCourse(int courseId, String genre) {
		return db.getCourse(courseId, genre);
	}
	
	/**
	 * Gets the total number of courses
	 * @return the number of courses
	 */
	public int getCourseNumber() {
		return db.getCourseNumber();
	}
	
	/**
	 * Gets the total number of courses without test keywords
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		return db.getCourseNumber(testKeyWord1, testKeyWord2, testKeyWord3);
	}
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(HashMap<String, String> params,String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		return db.getCourseNumber(params,testKeyWord1, testKeyWord2, testKeyWord3);
	}
		
	/**
	 * Modifies a course
	 * @param c the course to modify
	 */
	public synchronized void modifyCourse(Course c) {
		db.modifyCourse(c);
		MediaRetag mr = new MediaRetag(fs, c);
		mr.start();
	}
	
	/**
	 * Modifies the mediatype of course
	 * @param courseid the course id 
	 * @param mediatype the mediatype
	 */
	public synchronized void modifyCourseMediatype(int courseid, int mediatype) {
		db.modifyCourseMediatype(courseid, mediatype);
	}
	
	/**
	 * Gets the mediatype of the course
	 * @param courseid the courseid
	 * @return the mediatype of the course
	 */
	public int getMediaType(int courseid) {
		return db.getMediaType(courseid);
	}
	
	/**
	 * Deletes a course by providing its id and media Folder
	 * @param courseId the id of the course to delete
	 * @param mediaFolder the folder of the course to delete
	 * @param permanent true really delete the course, false move the course with the name folder_DELETE
	 */
	public synchronized void deleteCourse(int courseId, String mediaFolder, boolean permanent) {
		db.deleteCourse(courseId);
		
		if(permanent)
			fs.deleteCourse(mediaFolder);
		else
			fs.deleteMoveCourse(mediaFolder);
			
	}
	
	
	/**
	 * Gets a restricted list of test courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getTests(int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		return db.getTests(number, start, testKeyWord1, testKeyWord2, testKeyWord3);
	}
	
	/**
	 * Gets the total number of tests with test keywords
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getTestNumber(String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		return db.getTestNumber(testKeyWord1, testKeyWord2, testKeyWord3);
	}
	
	/**
	 * Deletes the test courses (courses with genre 'Suppression')
	 * @param testKeyWord the key word which identifies a test
	 */
	public synchronized void deleteTests(String testKeyWord) {
		// Gets tests to delete
		List<Course> tests = db.getTestsToDelete(testKeyWord);
		
		// delete tags
		for( Course c : tests) {
			db.deleteTag(c.getCourseid());
		}
				
		// delete tests in database
		db.deleteTests(testKeyWord);
					
		// delete course in file system
		for( Course c : tests) {
			fs.deleteCourse(c.getMediafolder());
		}
	}
	
	/**
	 * Hides the test courses (ie courses beginning with 'test' or 'essai')
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 */
	public void hideTests(String testKeyWord1, String testKeyWord2) {
		db.hideTests(testKeyWord1, testKeyWord2);
	}
	
	/**
	 * Gets the id of the next course which will be uploaded
	 * @return the id of the course
	 */
	public synchronized int getNextCoursId() {
		return db.getNextCoursId();
	}
	
	/**
	 * Gets the list of the teachers with visible courses
	 * @return the list of teachers
	 */
	public List<String> getTeachers() {
		return db.getTeachers();
	}
	
		
	/**
	 * Gets the list of all the teachers
	 * @return the list of teachers
	 */
	public List<Teacher> getAllTeachers() {
		return db.getAllTeachers();
	}
	
	/**
	 * Gets the list of all the formations
	 * @return the list of formations
	 */
	public List<String> getFormations() {
		return db.getFormations();
	}

	/**
	 * Increments the number of consultations for a course
	 * @param c the course
	 */
	public synchronized void incrementConsultations(Course c) {
		db.incrementConsultations(c);
	}
	
	
	/**
	 * Gets the slides of a course
	 * @param courseId the id of the course
	 * @return the list of slides
	 */
	public List<Slide> getSlides(int courseId) {
		return db.getSlides(courseId);
	}
	
	/**
	 * Adds a new building
	 * @param b the building to add
	 */
	public synchronized void addBuilding(Building b) {
		db.addBuilding(b);
	}
	
	/**
	 * Gets the list of the buildings
	 * @return the list of buildings
	 */
	public List<Building> getBuildings() {
		return db.getBuildings();
	}
	
	/**
	 * Gets a building by providing its id
	 * @param buildingId the id of the building
	 * @return the building
	 */
	public Building getBuilding(int buildingId) {
		return db.getBuilding(buildingId);
	}
	
	/**
	 * Gets a building name by providing one of its amphis IP address
	 * @param amphiIp the amphi IP address
	 * @return the building name
	 */
	public String getBuildingName(String amphiIp) {
		return db.getBuildingName(amphiIp);
	}
	
	/**
	 * Modifies a building
	 * @param b the building to modify
	 */
	public synchronized void modifyBuilding(Building b) {
		db.modifyBuilding(b);
	}
	
	/**
	 * Deletes a building
	 * @param buildingId the id of the building
	 */
	public synchronized void deleteBuilding(int buildingId) {
		db.deleteBuilding(buildingId);
	}
	
	/**
	 * Adds a new Amphi
	 * @param a the amphi to add
	 */
	public synchronized void addAmphi(Amphi a) {
		db.addAmphi(a);
	}
	
	/**
	 * Gets a list of all the amphis
	 * @param buildingId the id of the building
	 * @return the list of amphis
	 */
	public List<Amphi> getAmphis(int buildingId) {
		return db.getAmphis(buildingId);
	}

	/**
	 * Gets an amphi by providing its amphiId
	 * @param amphiId the ids of the amphi
	 * @return the amphi
	 */
	public Amphi getAmphi(int amphiId) {
		return db.getAmphi(amphiId);
	}
	
	/**
	 * Gets an amphi by providing its IP address
	 * @param ip the IP address of the amphi
	 * @return the amphi
	 */
	public Amphi getAmphi(String ip) {
		return db.getAmphi(ip);
	}
	
	/**
	 * Modifies an amphi
	 * @param a the amphi to modify
	 * @param oldAmphiip the old Ip address of this amphi
	 */
	public synchronized void modifyAmphi(Amphi a, String oldAmphiip) {
		db.modifyAmphi(a, oldAmphiip);
	}

	/**
	 * Sets the status of the live in an amphi
	 * @param ip the IP address of the amphi
	 * @param status the status od the live in the amphi
	 */
	public synchronized void setAmphiStatus(String ip, boolean status) {
		db.setAmphiStatus(ip, status);
	}
	
	/**
	 * Deletes an amphi by providing its id
	 * @param amphiId the id of the amphi
	 */
	public synchronized void deleteAmphi(int amphiId) {
		db.deleteAmphi(amphiId);
	}
	
		
	/**
	 * Retrieves a list of the website's available themes
	 * @param stylesFolder the folder in which the themes are stored
	 * @return the list of themes
	 */
	public List<String> getStyles(String stylesFolder) {
		return fs.getStyles(stylesFolder);
	}
	
	/**
	 * Retrieves a list of the website's available languages
	 * @param languagesFolder the folder in which the language property files are stored
	 * @return the list of languages
	 */
	public List<String> getLanguages(String languagesFolder) {
		return fs.getLanguages(languagesFolder);
	}
	
	/**
	 * Sends a file to the client's browser
	 * @param filename the name of the file to send
	 * @param out the stream in which send the file
	 */
	public void returnFile(String filename, OutputStream out) {
		fs.returnFile(filename, out);
	}
	
	/**
	 * Creates the RSS files for all the courses and teachers
	 * @param rssFolderPath the path of the folder to store the RSS files
	 * @param rssName the filename of the general RSS file
	 * @param rssTitle the title of the RSS files
	 * @param rssDescription the description of the RSS files
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image files
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS files
	 * @param rssCategory the category of the RSS file
	 * @param itunesAuthor The itunes author
	 * @param itunesSubtitle The itunes subtitle
	 * @param itunesSummary The itunes summary
	 * @param itunesImage The itunes image
	 * @param itunesCategory The itunes category
	 * @param itunesKeywords The itunes keywords
	 */
	public void generateRss( String rssFolderPath, String rssName, String rssTitle, String rssDescription, 
			String serverUrl, String rssImageUrl, String recordedInterfaceUrl, String language,String rssCategory, String itunesAuthor,
			String itunesSubtitle, String itunesSummary, String itunesImage, String itunesCategory, String itunesKeywords) {
				
		List<Course> courses = null;
		String rssPath = null;
		
		// For the teachers
		List<String> teachers = db.getTeachers();
		for( String teacher : teachers) {
			courses = db.getCoursesByAuthor(teacher,20);
			rssPath = rssFolderPath + "/" + cleanFileName(teacher) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+teacher, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		}
				
		// For the formation
		List<String> formations = db.getFormations();
		String formFullName = null;
		for( String formation : formations) {
			courses = db.getCoursesByFormation(formation,20);
			rssPath = rssFolderPath + "/" + cleanFileName(formation) + ".xml";
			formFullName = getFormationFullName(formation);
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+(formFullName!=null ? formFullName : formation), rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		}
		
		//For the login
		List<User> users = db.getAllUsers(null,null);
		for(User u : users) {
			courses = db.getCoursesByUser(u, null, null, true);	
			rssPath = rssFolderPath + "/" + cleanFileName("lgn_" + u.getLogin()) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+"lgn_"+u.getLogin(), rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		}
		
		// For all courses 
		courses = db.getAllCourses(true,true,20);
		rssPath = rssFolderPath + "/" + cleanFileName(rssName) + ".xml";
		fs.rssCreation(courses, rssPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		fs.rssCreation(courses, rssFolderPath + "/" + cleanFileName("univrav") + ".xml", rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);		
		
	}
	
	/**
	 * Creates the RSS files for all the courses and the teacher of the course in parameter
	 * @param c the course which has been modified or added
	 * @param rssFolderPath the path of the folder to store the RSS files
	 * @param rssName the filename of the general RSS file
	 * @param rssTitle the title of the RSS files
	 * @param rssDescription the description of the RSS files
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image files
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS files
	 * @param rssCategory the category of the RSS file
	 * @param itunesAuthor The itunes author
	 * @param itunesSubtitle The itunes subtitle
	 * @param itunesSummary The itunes summary
	 * @param itunesImage The itunes image
	 * @param itunesCategory The itunes category
	 * @param itunesKeywords The itunes keywords
	 */
	public void generateRss( Course c, String rssFolderPath, String rssName, String rssTitle, String rssDescription, 
			String serverUrl, String rssImageUrl, String recordedInterfaceUrl, String language,String rssCategory, String itunesAuthor,
			String itunesSubtitle, String itunesSummary, String itunesImage, String itunesCategory, String itunesKeywords) {
		// For all courses
		List<Course> courses = db.getAllCourses(true,true,20);
		String rssPath = rssFolderPath + "/" + cleanFileName(rssName) + ".xml";
		fs.rssCreation(courses, rssPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		fs.rssCreation(courses, rssFolderPath + "/" + cleanFileName("univrav") + ".xml", rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
				
		// For the teacher
		if( ! (c.getName() == null && c.getFirstname() == null)) {
			String teacher = db.getTeacherFullName(c.getName(), c.getFirstname());
			courses = db.getCoursesByAuthor(teacher,20);
			rssPath = rssFolderPath + "/" + cleanFileName(teacher) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+teacher, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		}
		
		//For the formation
		if(c.getFormation()!=null) {
			courses = db.getCoursesByFormation(c.getFormation(),20);
			rssPath = rssFolderPath + "/" + cleanFileName(c.getFormation()) + ".xml";
			String formFullName = getFormationFullName(c.getFormation());
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+(formFullName!=null ? formFullName : c.getFormation()), rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		}
		
		//For the login
		if(c.getUserid()!=null && c.getUserid()!=0) {
			User u = db.getUser(c.getUserid());
			courses = db.getCoursesByUser(u, null, null, true);
			rssPath = rssFolderPath + "/" + cleanFileName("lgn_" + u.getLogin()) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+"lgn_"+u.getLogin(), rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		}
		
	}
	
	/**
	 * Gets the list of RSS files
	 * @param rssTitle the title of the general RSS file
	 * @param rssName the name of the general RSS file
	 * @param onlygeneral to get only the general RSS
	 * @return the hashMap of the RSS titles and files
	 */
	public HashMap<String, String> getRssFileList( String rssTitle, String rssName, boolean onlygeneral) {
		LinkedHashMap<String, String> rss = new LinkedHashMap<String, String>();
		rss.put(rssTitle, "../rss/" + rssName + ".xml");
		
		if(!onlygeneral) {

			List<String> teachers = db.getTeachers();
			for( String teacher : teachers) {
				rss.put(
						teacher,
						"../rss/" + cleanFileName(teacher) + ".xml"
				);
			}

			List<String> formations = db.getFormations();
			String formFullName=null;
			for( String formation : formations) {
				formFullName = getFormationFullName(formation);
				rss.put(
						formFullName!=null ? formFullName : formation,
								"../rss/" + cleanFileName(formation) + ".xml"
				);
			}
		}
		
		return rss;
	}
	
	/**
	 * Gets the list of RSS files of a course
	 * @param c the course
	 * @return the hashMap of the RSS titles and files
	 */
	public HashMap<String, String> getRssCourseFileList(Course c) {
		LinkedHashMap<String, String> rss = new LinkedHashMap<String, String>();
		
		if(c!=null) {
			
			if((c.getName()!=null && !c.getName().equals("")) || (c.getFirstname()!=null && !c.getFirstname().equals(""))) {
				String teacher = db.getTeacherFullName(c.getName(), c.getFirstname());
				rss.put(teacher,"../rss/" + cleanFileName(teacher) + ".xml");
			}

			String showform=null;
			if(c.getFormation()!=null && !c.getFormation().equals(""))
				showform = getFormationFullName(c.getFormation());
				rss.put(showform!=null ? showform : c.getFormation(),"../rss/" + cleanFileName(c.getFormation()) + ".xml");
		}
		return rss;
	}
	
	/**
	 * Sends a message over a socket to the Univ-R AV client
	 * @param message the message to send
	 * @param ip the ip to contact the client
	 * @param port the port to contact the client
	 * @param timeout the timeout to contact the client
	 * @return the answer of the client
	 */
	public String sendMessageToClient(String message, String ip, int port,int timeout) {
		return fs.sendMessageToClient(message, ip, port,timeout);
	}
	
	/**
	 * Retrieves information about used and free disk space on the server
	 * @return the string containing the info
	 */
	public String getDiskSpaceInfo() {
		return fs.getDiskSpaceInfo();
	}
		
	/**
	 * Function which removes the undesirable characters of a String and the useless spaces at the end
	 * @param string the string to clean
	 * @return the cleaned string
	 */
	public String cleanString(String string) {
		final String carSpeTotal = "&><\"%#+";
		
		String res = "";
		
		/* Removes the undesirable characters */
		if( string != null ) {
			for( int i=0 ; i < string.length() ; i++ ) {
				if( carSpeTotal.indexOf(string.charAt(i)) == -1 )
					res += string.charAt(i);
			}
		}

		/* Removes the spaces at the end */
		if( res.length() > 0 ) {
			while( res.charAt(res.length()-1) == ' ' )
				res = res.substring(0,res.length()-1);
		}
		
		return res;
	}
	
	/**
	 * Function which removes the undesirable characters of a String (accents & company) to create a file
	 * @param string the string to clean
	 * @return the cleaned string
	 */
	public String cleanFileName(String string){
		
		String res="";
		
		if( string != null ) {
			for(int i=0 ; i< string.length() ; i++) {
				char car = string.charAt(i);
				if( ! ((car >= 'a' && car <='z') | (car >= 'A' && car <='Z') | (car >= '0' && car <='9')))
					car = '_';
				
				res += car;
			}
		}
		
		return res;
	}
	
	/**
	 * Gets user by login (login is UNIQUE)
	 * @param login the login of the user
	 * @return the user
	 */
	public User getUser(String login) {
		return db.getUser(login);
	}
		
	/**
	 * Get user by id 
	 * @param id the id of the user
	 * @return the user
	 */
	public User getUser(int id) {
		return db.getUser(id);
	}
	
	/**
	 * Gets local user by hash code (hash is unique)
	 * @param hash the hash code
	 * @return the user
	 */
	/*public User getUserLocalByHash(String hash) {
		return db.getUserLocalByHash(hash);
	}*/
	
	/**
	 * Gets user by login and hash(login is UNIQUE)
	 * @param login the login of the user
	 * @param hash hash code
	 * @return the user
	 */
	public User getUserLocal(String login, String hash) {
		return db.getUserLocal(login,hash);
	}
	
	/**
	 * Modify a password for a user
	 * @param login the login
	 * @param hash the password
	 * @param hashtype the password type
	 */
	public synchronized void modifyUserPassword(String login, String hash, String hashtype) {
		db.modifyUserPassword(login, hash, hashtype);
	}
	
	/**
	 * Modify reset code for a user
	 * @param login the login
	 * @param hash the reset code
	 * @param hashtype the reset code type
	 * @param dateResetCode the reset code date
	 */
	public void modifyUserResetCode(String login, String hash, String hashtype, Timestamp dateResetCode) {
		db.modifyUserResetCode(login, hash, hashtype, dateResetCode);
	}
	
	/**
	 * Gets user by reset code
	 * @param hash reset code
	 * @return the user
	 */
	public User getUserLocalByResetCode(String hash) {
		return db.getUserLocalByResetCode(hash);
	}
	
	/**
	 * Gets the id of the next user which will be uploaded
	 * @return the id of the user
	 */
	public synchronized int getNextUserId() {
		return db.getNextUserId();
	}
	
	/**
	 * Adds a new user
	 * @param u User
	 */
	public synchronized void addUser(User u) {
		db.addUser(u);
	}
	
	/**
	 * Modify a user
	 * @param u User
	 */
	public synchronized void modifyUser(User u) {
		db.modifyUser(u);
	}
	
	/**
	 * Deletes an user by providing its id
	 * @param userid the id of the user
	 */
	public synchronized void deleteUser(int userid) {
		db.deleteUser(userid);
	}
	
	/**
	 * Gets a list of courses by providing its user
	 * @param u the user of the course
	 * @param number the number of courses
	 * @param start the start number of courses
	 * @param onlyvisible true to get only visible courses
	 * @return the list of course
	 */
	public List<Course> getCoursesByUser(User u, Integer number, Integer start, boolean onlyvisible) {
		return db.getCoursesByUser(u,number,start,onlyvisible);
	}
	
	/**
	 * Gets the total number of courses
	 * @param u
	 * @return the number of courses
	 */
	public int getCourseNumber(User u) {
		return db.getCourseNumber(u);
	}
	
	/**
	 * Gets the list of all the users
	 * @param number limit
	 * @param start offset
	 * @return the list of users
	 */
	public List<User> getAllUsers(Integer number, Integer start) {
		return db.getAllUsers(number, start);
	}
	
	/**
	 * Gets the number of all the users
	 * @return the list of users
	 */
	public int getUsersNumber() {
		return db.getUsersNumber();
	}
	
	/**
	 * Send an email to confirm the add of the new course 
	 * @param subject the subject of the mail
	 * @param message the message of the mail
	 * @param email the email address
	 */
	public synchronized void sendMail(String subject, String message, String email) {
		fs.sendMail(subject,message,email);
	}
	
	/**
	 * Add a new tag
	 * @param t the tag
	 */
	public void addTag(Tag t) {
		db.addTag(t);
	}
	
	/**
	 * Deletes tags by providing its courseid
	 * @param courseid the id of the course
	 */
	public void deleteTag(int courseid) {
		db.deleteTag(courseid);
	}
	
	/**
	 * Gets a list of all tags of a course
	 * @param c Course
	 * @return the list of tags
	 */
	public List<Tag> getTagsByCourse(Course c) {
		return db.getTagsByCourse(c);
	}
	
	/**
	 * Gets a list of all tags
	 * @return the list of tags
	 */
	public List<String> getAllTags() {
		return db.getAllTags();
	}
	
	/**
	 * Gets a list of most popular tags
	 * @return the list of most popular tags
	 */
	public List<String> getMostPopularTags() {
		return db.getMostPopularTags();
	}
	
	/**
	 * Gets a restricted list of courses
	 * @param tag
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getCoursesByTags(List<String> tag, int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		return db.getCoursesByTags(tag, number, start, testKeyWord1, testKeyWord2, testKeyWord3);
	}
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param tags the tags
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(List<String> tags,String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		return db.getCourseNumber(tags, testKeyWord1, testKeyWord2, testKeyWord3);
	}
		
	/**
	 * Get the list of ahref for the tag cloud
	 * @param listTag the list of tag
	 * @return list of ahref
	 */
	public List<String> getTagCloud(List<String> listTag) {
				
		List<String> nouvelleList = new ArrayList<String>();
		
		for(int i=0;i<listTag.size();i++) {
			nouvelleList.add("<a href=\"./tags?tags="+listTag.get(i)+"\" id=\"tag"+(i+1)+"\">"+(listTag.get(i).length()<=12 ? listTag.get(i) : listTag.get(i).substring(0, 12)+"...")+"</a>&nbsp;&nbsp;");
		}
		Collections.sort(nouvelleList);
		
		return nouvelleList;
	}
	
	/**
	 * Gets a list of the n selection courses
	 * @param n the number of courses to return
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getNSelectionCourses(int n, String testKeyWord1, String testKeyWord2) {
		return db.getNSelectionCourses(n, testKeyWord1, testKeyWord2);
	}

	/**
	 * Gets a list of the n selection courses
	 * @param n the number of courses to return
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getNFormationCourses(int n, String testKeyWord1, String testKeyWord2) {
		return db.getNFormationCourses(n, testKeyWord1, testKeyWord2);
	}
	
	/**
	 * Gets the list of all the selection
	 * @return the list of users
	 */
	public List<Selection> getAllSelections() {
		return db.getAllSelections();
	}
	
	/**
	 * Get selection by position 
	 * @param position the position of the selection
	 * @return the selection
	 */
	public Selection getSelection(int position) {
		return db.getSelection(position);
	}
	
	/**
	 * Adds a new selection
	 * @param s Selection
	 */
	public void addSelection(Selection s) {
		db.addSelection(s);
	}
	
	/**
	 * Modify a selection
	 * @param s Selection
	 */
	public synchronized void modifySelection(Selection s) {
		db.modifySelection(s);
	}
	
	/**
	 * Deletes a selection by providing its id
	 * @param position the position of the selection
	 */
	public synchronized void deleteSelection(int position) {
		db.deleteSelection(position);
	}
	
	/**
	 * Encrypt a text
	 * @param plaintext
	 * @return string encrypted
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public synchronized String encrypt(String plaintext) {
		
		final char[] hexChars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    	StringBuilder sb = new StringBuilder();
   
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(plaintext.getBytes(), 0, plaintext.getBytes().length);
            byte[] hash = md.digest();
            int msb;
            int lsb = 0;
            for (int i = 0; i < hash.length; i++) {
                msb = ((int)hash[i] & 0x000000FF) / 16;
                lsb = ((int)hash[i] & 0x000000FF) % 16;
                sb.append(hexChars[msb]);
                sb.append(hexChars[lsb]);
            }
            
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        
        return sb.toString();
	}
	
	/**
	 * Generate a random password
	 * @param length the length of the password
	 * @return a password
	 */
	public synchronized String generatePassword(int length)
	{
		Random rand = new Random();
		StringBuffer buffer = new StringBuffer();
		length = Math.abs( length );
		int c = 0;
		
		while( length > 0 ){
			--length;
			//minuscule + majuscule + chiffre
			c = rand.nextInt( 26 * 2 + 10 + 1 );
			if( c < 26 ) {
				buffer.append( (char)( 'a' + c ) );
			}
			else if( c < 26 * 2 ) {
				buffer.append( (char)( 'A' + c - 26 ) );
			}
			else {
				buffer.append( (char)( '0' + c - 26 * 2 ) );
			}
		}
		
		return buffer.toString();
	}
	
	/**
	 * Add an additional document to a course
	 * @param c the course
	 * @param docFile the fileitem of the document
	 */
	public synchronized void addAdditionalDoc(Course c, FileItem docFile) {
		fs.addAdditionalDoc(c.getMediafolder(),docFile);
		c.setAdddocname(FilenameUtils.getName(docFile.getName()));	
		if(!c.isAvailable("adddoc"))
			c.setmediatype(c.getmediatype()+Course.typeAdddoc);
		db.modifyCourse(c);
	}
	
	/**
	 * Delete an additional document of a course
	 * @param c the course
	 */
	public synchronized void deleteAdditionalDoc(Course c) {
		fs.deleteAdditionalDoc(c.getMediafolder(),c.getAdddocname());
		c.setAdddocname(null);
		if(c.isAvailable("adddoc"))
			c.setmediatype(c.getmediatype()-Course.typeAdddoc);
		db.modifyCourse(c);
	}
	
	/**
	 * Gets user's parameters from the ldap
	 * @param login user's login
	 * @throws Exception 
	 */
	public List<String> getLdapUserInfos(String login) throws Exception {
		return ldap.getLdapUserInfos(login);
	}
	
	/**
	 * Modify the job status
	 * @param courseid course id
	 * @param status job status
	 * @param coursetype coursetype
	 */
	public void modifyJobStatus(int courseid,String status,String coursetype) {
		db.modifyJobStatus(courseid, status, coursetype);
	}
	
	/**
	 * Modify the job
	 * @param j the job
	 */
	public synchronized void modifyJob(Job j) {
		db.modifyJob(j);
	}
	
	/**
	 * Get job by courseid 
	 * @param courseid the courseid of the job
	 * @param coursetype the job type
	 * @return the job
	 */
	public Job getJob(int courseid, String coursetype) {
		return db.getJob(courseid, coursetype);
	}
	
	/**
	 * Gets the list of all jobs
	 * @return the list of jobs
	 */
	public List<Job> getAllJobs() {
		return db.getAllJobs();
	}
	
	/**
	 * Create a job
	 * @param c the course
	 */
	public synchronized void createJob(Course c, int mediatype, String type, String extension, String coursesFolder) {
		
		Job j = new Job(
				db.getNextJobId(),
				c.getCourseid(),
				"waiting",
				mediatype,
				type,
				c.getMediafolder(),
				extension
				
		);
		
		db.addJob(j);
	}
	
	/**
	 * Gets the list of all discipline
	 * @return the list of discipline
	 */
	public List<Discipline> getAllDiscipline() {
		return db.getAllDiscipline();
	}
	
	/**
	 * Adds a new discipline
	 * @param d the discipline
	 */
	public synchronized void addDiscipline(Discipline d) {
		db.addDiscipline(d);
	}
	
	/**
	 * Modify a discipline
	 * @param d the discipline
	 */
	public synchronized void modifyDiscipline(Discipline d) {
		db.modifyDiscipline(d);
	}
	
	/**
	 * Get discipline by id 
	 * @param id the id of the discipline
	 * @return the discipline
	 */
	public Discipline getDiscipline(int id) {
		return db.getDiscipline(id);
	}
	
	/**
	 * Deletes a discipline by providing its id
	 * @param disciplineid the id of the discipline
	 */
	public void deleteDiscipline(int disciplineid) {
		db.deleteDiscipline(disciplineid);
	}
	
	/**
	 * Gets the list of all levels
	 * @return the list of levels
	 */
	public List<Level> getAllLevels() {
		return db.getAllLevels();
	}
	
	/**
	 * Gets the level code by formation
	 * @param formation the formation
	 * @return the level code
	 */
	public String getLevelCodeByFormation(String formation) {
		if(formation!=null && formation.indexOf("-")!=-1)
			return formation.substring(formation.indexOf("-")+1);
		else
			return "";
	}
	
	
	/**
	 * Gets the component code by formation
	 * @param formation the formation
	 * @return the component code
	 */
	public String getComponentCodeByFormation(String formation) {
		if(formation!=null && formation.indexOf("-")!=-1)
			return formation.substring(0,formation.indexOf("-"));
		else 
			return "";
	}
	
	
	/**
	 * Gets the formation by level code and component code
	 * @param levelcode the level code
	 * @param compcode the component code
	 * @return the formation
	 */
	public String getFormationByCodes(String levelcode, String compcode) {

		String formation;

		// Generate pseudo code etp for formation
		if(levelcode!=null && !levelcode.equals("") && compcode!=null && !compcode.equals("")) {
			formation=compcode+"-"+levelcode;
		}
		// for old client
		else {
			//FORMATION DANS AUTRE AUTRE
			formation="00-O0";
		}
		
		return formation;

	}
	
	/**
	 * Return the result of find tracks function
	 * 
	 * @param params all parameters
	 * @return a list of course
	 */
	public List<Course> getTracks(HashMap<String, String> params) {
		return db.getTracks(params);
	}
	
	/**
	 * Create xml for find tracks function
	 * @param courses list of courses
	 * @param serverUrl the url server for url course access
	 * @param showErrorMsg true if show error xml msg
	 * @param coursesUrl the courses url
	 * @return results
	 */
	public String generateXmlTracks( String coursesUrl, List<Course> courses, String serverUrl, boolean showErrorMsg) {
		
		String results=null;
		
		try {		
			// Création d'un nouveau DOM
	        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
	        DocumentBuilder constructeur = fabrique.newDocumentBuilder();
	        Document document = constructeur.newDocument();
	        
	        // Propriétés du DOM
	        document.setXmlVersion("1.0");
	        document.setXmlStandalone(false);
	        
	        // Création de l'arborescence du DOM
	        Element elRacine = document.createElement("ask");
	        
	        if(showErrorMsg) {
	        	elRacine.setAttribute("track", "fail");
	        	
	        	Element elTotal = document.createElement("err");
	        	elTotal.setTextContent("Parameters must be: dateRange=yyyymmdd-yyyymmdd or all, idRange=x-y, authorLogin=login, authorName=name, authorFirstname=firstname, component=CODE, level=CODE, tags=tag1+tag2+...,datatype=xml or json");
	        	elRacine.appendChild(elTotal);

	        }
	        else {
	        	elRacine.setAttribute("track", "ok");

	        	Element elTotal = document.createElement("totalResultsCount");
	        	elTotal.setTextContent(String.valueOf(courses.size()));
	        	elRacine.appendChild(elTotal);

	        	Element elCourses = document.createElement("courses");
	        	elRacine.appendChild(elCourses);

	        	// Listing des cours
	        	for( Course course : courses) {

	        		if( course.getTitle() != null) {
	        			// Conversion de la date dans le bon format	
	        			SimpleDateFormat sdf = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);

	        			// item
	        			Element item = document.createElement("course");
	        			elCourses.appendChild(item);

	        			// Course param
	        			Element elId = document.createElement("id");
	        			elId.setTextContent(String.valueOf(course.getCourseid()));
	        			item.appendChild(elId);
	        			
	        			Element elUrl = document.createElement("url");
	        			elUrl.setTextContent(serverUrl+"/avc/courseaccess?id="+course.getCourseid());
	        			item.appendChild(elUrl);

	        			Element elDate = document.createElement("date");
	        			elDate.setTextContent(sdf.format(course.getDate()));
	        			item.appendChild(elDate);
	        			
	        			Element elRecordDate = document.createElement("recorddate");
	        			elRecordDate.setTextContent(course.getRecorddate()!=null ? sdf.format(course.getRecorddate()) : "" );
	        			item.appendChild(elRecordDate);

	        			Element elTitle = document.createElement("title");
	        			elTitle.setTextContent(course.getTitle());
	        			item.appendChild(elTitle);
	        			
	        			Element elDesc = document.createElement("description");
	        			elDesc.setTextContent(course.getDescription());
	        			item.appendChild(elDesc);

	        			Element elForm = document.createElement("formation");
	        			elForm.setTextContent(course.getFormation());
	        			item.appendChild(elForm);
	        			
	        			Element elName= document.createElement("name");
	        			elName.setTextContent(course.getName());
	        			item.appendChild(elName);
	        			
	        			Element elFirstname= document.createElement("firstname");
	        			elFirstname.setTextContent(course.getFirstname());
	        			item.appendChild(elFirstname);
	        			        			
	        			Element elUser= document.createElement("login");
	        			User user = db.getUser(course.getUserid());
	        			elUser.setTextContent(user!=null ? user.getLogin() : null);
	        			item.appendChild(elUser);
	        				        			
	        			Element elResCode= document.createElement("resCode");
	        			elResCode.setTextContent(course.getGenre()!=null ? "true" : "false");
	        			item.appendChild(elResCode);

	        			Element elResUds= document.createElement("resUds");
	        			elResUds.setTextContent(course.isRestrictionuds() ? "true" : "false");
	        			item.appendChild(elResUds);
	        			
	        			Element elResDownload= document.createElement("download");
	        			elResDownload.setTextContent(course.isDownload() ? "true" : "false");
	        			item.appendChild(elResDownload);
	        			
	        			Element elDuration= document.createElement("duration");
	        			elDuration.setTextContent(course.getDurationString());
	        			item.appendChild(elDuration);
	        			
	        			Element elConsults= document.createElement("consultations");
	        			elConsults.setTextContent(String.valueOf(course.getConsultations()));
	        			item.appendChild(elConsults);
	        				        			
	        			Element elType = document.createElement("type");
	        			elType.setTextContent(course.getType());
	        			item.appendChild(elType);
	        			
	        			Element elMediatype= document.createElement("mediatype");
	        			elMediatype.setTextContent(String.valueOf(course.getmediatype()));
	        			item.appendChild(elMediatype);
	        			
	        			Element elVisible= document.createElement("visible");
	        			elVisible.setTextContent(course.isVisible() ? "true" : "false");
	        			item.appendChild(elVisible);
	        			
	        			Element elTags= document.createElement("tags");
	        			String tags="";
	        			List<Tag> listTags = db.getTagsByCourse(course);
	        			for(int i=0;i<listTags.size();i++) 
	        					tags=i==0 ? listTags.get(i).getTag() : tags + "+" + listTags.get(i).getTag();
	        			elTags.setTextContent(tags);
	        			item.appendChild(elTags);	  
	        			
	        			Element elUrlFolder= document.createElement("urlfolder");
	        			elUrlFolder.setTextContent(String.valueOf(getCleanCoursesUrl(coursesUrl)+course.getMediafolder()));
	        			item.appendChild(elUrlFolder);
	        		}
	        	}
	        }
		        
			document.appendChild(elRacine);
						
			  // Create DOM source
	        Source source = new DOMSource(document);       
	        
	        StringWriter xmlout = new StringWriter();
	        Result resultat = new StreamResult(xmlout);
	        
	        // Transformer configuration
	        TransformerFactory transFac = TransformerFactory.newInstance();
	        Transformer transformer = transFac.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        
	        // Transformation into xml file
	        transformer.transform(source, resultat);
	        	        
	        results = xmlout.toString();	        
		}
		catch( ParserConfigurationException pce) {
			logger.error("Error while creating the XML",pce);
		}
		 catch (TransformerConfigurationException e) {
			 logger.error("Error while creating the XML",e);
		} catch (TransformerException e) {
			logger.error("Error while creating the XML",e);
		} 
				
		return results;
	}
	
	/**
	 * Return the result of find stats function
	 * 
	 * @param params all parameters
	 * @return a list of stats
	 */
	public HashMap<String, Integer> getStats(HashMap<String, String> params) {
		return db.getStats(params);
	}
	
	/**
	 * Create xml for find stats function
	 * @param stats list of stats
	 * @param serverUrl the url server for url course access
	 * @param showErrorMsg true if show error xml msg
	 * @return results
	 */
	public String generateXmlStats( HashMap<String, Integer> stats, String serverUrl, boolean showErrorMsg) {
		
		String results=null;
		
		try {		
			// Création d'un nouveau DOM
	        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
	        DocumentBuilder constructeur = fabrique.newDocumentBuilder();
	        Document document = constructeur.newDocument();
	        
	        // Propriétés du DOM
	        document.setXmlVersion("1.0");
	        document.setXmlStandalone(false);
	        
	        // Création de l'arborescence du DOM
	        Element elRacine = document.createElement("ask");
	        
	        if(showErrorMsg) {
	        	elRacine.setAttribute("stat", "fail");
	        	
	        	Element elTotal = document.createElement("err");
	        	elTotal.setTextContent("Parameters must be: dateRange=yyyymmdd-yyyymmdd or all, component=CODE, level=CODE, domain=CODE, account=UDS, FREE or STUDENT, weekday=1-7(Sunday is 1), datatype=xml or json");
	        	elRacine.appendChild(elTotal);

	        }
	        else {
	        	elRacine.setAttribute("stat", "ok");

	        	// item
	        	Element elStats = document.createElement("stat");
	        	elRacine.appendChild(elStats);
	        	
	        	        	
	        	for (Entry<String, Integer> currentEntry : stats.entrySet()) {
	        		String id = currentEntry.getKey();
	        		Integer value = currentEntry.getValue();
	        			        		
	        		if(id.equals("nbduration")) {
	        			String res = "";
	        			int durationHour = value / 3600;
	        			int durationMinute = (value % 3600) / 60 ;
	        			int durationSecond = ((value % 3600) % 60) ;
	        			res += durationHour > 0 ? durationHour + "h" : "";
	        	    	res += durationHour > 0 || durationMinute > 0 ? durationMinute + "min" : "";
	        	    	res += durationSecond + "sec";
	        	    	Element el = document.createElement(id);
	        	    	el.setTextContent(res);
	        	    	elStats.appendChild(el);
	        		}
	        		else {        		
	        			Element el = document.createElement(id);
	        			el.setTextContent(String.valueOf(value));
	        			elStats.appendChild(el);
	        		}
        			
	        	}
	        	
	        }
		        
			document.appendChild(elRacine);
						
			  // Create DOM source
	        Source source = new DOMSource(document);       
	        
	        StringWriter xmlout = new StringWriter();
	        Result resultat = new StreamResult(xmlout);
	        
	        // Transformer configuration
	        TransformerFactory transFac = TransformerFactory.newInstance();
	        Transformer transformer = transFac.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        
	        // Transformation into xml file
	        transformer.transform(source, resultat);
	        	        
	        results = xmlout.toString();	        
		}
		catch( ParserConfigurationException pce) {
			logger.error("Error while creating the XML",pce);
		}
		 catch (TransformerConfigurationException e) {
			 logger.error("Error while creating the XML",e);
		} catch (TransformerException e) {
			logger.error("Error while creating the XML",e);
		} 
				
		return results;
	}

		
	/**
	 * Script to launch medias encodage
	 * @param serverUrl the server url
	 * @param job_line the job line
	 */

	public void launchJob(String serverUrl, String job_line) {
		
		fs.launchJob(serverUrl, job_line);
	}
	
	
	/**
	 * Return the clean courses url (check if RAND[?-?] exist)
	 * @param coursesUrl : the coursesurl from univrav.properties
	 * @return the clean courses url
	 */
	public String getCleanCoursesUrl(String coursesUrl) {
		return fs.getCleanCoursesUrl(coursesUrl);
	}

	
	/**
	 * Add a subtitles xml to a course
	 * @param c the course
	 * @param docFile the fileitem of the document
	 */
	public synchronized void addSubtitles(Course c, FileItem docFile) {
		fs.addSubtitles(c.getMediafolder(),docFile,c.getCourseid());
		
		if(!c.isAvailable("subtitles"))
			c.setmediatype(c.getmediatype()+Course.typeSubtitles);
		db.modifyCourse(c);
	}
	
	/**
	 * Delete a subtitles xml of a course
	 * @param c the course
	 */
	public synchronized void deleteSubtitles(Course c) {
		fs.deleteSubtitles(c.getMediafolder(),c.getCourseid());
		
		if(c.isAvailable("subtitles"))
			c.setmediatype(c.getmediatype()-Course.typeSubtitles);
		db.modifyCourse(c);
	}
	
	
	/**
	 * encode string if iso or utf
	 * @param s the string
	 * @return encoded string
	 */
	public String encodeString(String s) {

		String r = "";
		
		try {

			if(s != null && !s.equals("")) {

				//convert string to utf8
				String st = new String(s.getBytes("8859_1"),"UTF8");
				boolean toConvert = true;

				for(int i=0;i<st.getBytes().length-2;i++) {
					//if convertion to utf8 fail
					if((st.getBytes()[i] & 0xff) == 239 && (st.getBytes()[i+1] & 0xff) == 191 && (st.getBytes()[i+2] & 0xff) == 189) {
						toConvert = false;
					}

				}				
				
				// if UTF8 else ISO
				if(toConvert)
					r = st;				
				else	
					r = s;
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("Error while decode string",e);
		}

		return r;
	}
	
	/**
	 * Add a complementary video
	 * @param c the course
	 * @param slidesoffset offset
	 * @param docFile the fileitem of the document
	 * @param serverUrl the URL of the application on the server
	 * @param sepEnc true if medias encodage is separated
	 * @param coursesFolder the courses folder
	 */
	public synchronized void addVideo(Course c, Integer slidesoffset, FileItem docFile,String serverUrl, boolean sepEnc,String coursesFolder) {
		AddVideo rm = new AddVideo(fs,c,slidesoffset,docFile,this,serverUrl,sepEnc,coursesFolder);
		rm.start();		
	}
	
	
	/**
	 * Delete the additional video of a course
	 * @param c the course
	 */
	public synchronized void deleteReplaceMedia(Course c) {
		
		if(c.isAvailable("addvideo")) {
			fs.deleteReplaceMedia(c.getMediafolder(),c.getCourseid());
			c.setmediatype(c.getmediatype()-Course.typeAddVideo);
			db.modifyCourse(c);
		}
	}

	/**
	 * Add a log user action
	 * @param request the request
	 * @param user the user
	 * @param course the course
	 * @param logtype the logtype
	 * @param information the information
	 */
	public void addLogUserAction(HttpServletRequest request, User user, Course course, String logtype, String information ) {
		
		LogUserAction log = new LogUserAction(
				new Timestamp(new Date().getTime()), 
				user!=null ? user.getUserid() : null, 
				course!=null ? course.getCourseid() : null, 
				request.getRequestURI(), 
				request.getRequestURL() + (request.getQueryString()!=null ? "?" + request.getQueryString() : ""), 
				logtype, 
				information
		);
		
		db.addLogUserAction(log);
	}
	
	/**
	 * get log user actions by user
	 * @param userid the user
	 * @return list of log
	 */
	public List<LogUserAction> getLogUserActionByUser(Integer userid) {
		return db.getLogUserActionByUser(userid);
	}
	
	/**
	 * Get the user from the session
	 * @param session the current session
	 * @return the session user
	 */
	public User getSessionUser(HttpSession session) {
		
		User user = null;
		
		String casUser = (String) session.getAttribute(edu.yale.its.tp.cas.client.filter.CASFilter.CAS_FILTER_USER);		
		// Authentification CAS	
		if(casUser!=null) {
			// Gets the user from the session
			user=this.getUser(casUser);
		}
		// Authentification local
		else if(session.getAttribute("$userLocalLogin")!=null) {
			user=this.getUser(session.getAttribute("$userLocalLogin").toString());
		}
		
		return user;
	}
}
