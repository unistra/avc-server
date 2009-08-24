package org.ulpmm.univrav.service;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.dao.IUnivrDao;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Selection;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Tag;
import org.ulpmm.univrav.entities.Teacher;
import org.ulpmm.univrav.entities.Univr;
import org.ulpmm.univrav.entities.User;

import sun.misc.BASE64Encoder;

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
	
	/** UnivrDao interface */
	private IUnivrDao ud;
	
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
	 * Gets the univr dao interface
	 * @return the univr dao interface
	 */
	public IUnivrDao getUd() {
		return ud;
	}

	/**
	 * Sets the univr dao interface
	 * @param ud the ud to set
	 */
	public void setUd(IUnivrDao ud) {
		this.ud = ud;
	}

	/**
	 * Adds a new course
	 * @param c the course to add
	 * @param courseArchive the archive file of the course to add
	 * @param tags list of tags
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
	public synchronized void addCourse(Course c, String courseArchive, String tags, String rssFolderPath, 
			String rssName, String rssTitle, String rssDescription, String serverUrl, 
			String rssImageUrl, String recordedInterfaceUrl, String language, String rssCategory, String itunesAuthor,
			String itunesSubtitle, String itunesSummary, String itunesImage, String itunesCategory, String itunesKeywords) {
		
		CourseAddition ca = new CourseAddition(db, fs, c, courseArchive, tags,
				this, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, 
				rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor,
				itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		ca.start();
	}
	
	/**
	 * Adds a new course from Univ-R
	 * @param c the course to add
	 * @param u the Univ-R infos
	 */
	public synchronized void addUnivrCourse(Course c, Univr u) {
		db.addCourse(c);
		db.addUnivr(u);
	}
	
	/**
	 * Completes a Univr course
	 * @param c the course to complete
	 * @param u the Univr course to complete
	 * @param courseArchive the archive file of the course to complete
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
	public synchronized void completeUnivrCourse(Course c, Univr u, String courseArchive , String rssFolderPath, 
			String rssName, String rssTitle, String rssDescription, String serverUrl, 
			String rssImageUrl, String recordedInterfaceUrl, String language, String rssCategory, String itunesAuthor,
			String itunesSubtitle, String itunesSummary, String itunesImage, String itunesCategory, String itunesKeywords) {
		
		UnivrCourseCompletion ucc = new UnivrCourseCompletion(db, fs, ud, c, u, courseArchive,
				this, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, 
				rssImageUrl, recordedInterfaceUrl, language, rssCategory, itunesAuthor,
				itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		ucc.start();
	}
	
	/**
	 * Creates a course from an uploaded audio or video media file
	 * @param c the course to create
	 * @param mediaFile the media file of the course to create
	 * @param tags tags list
	 * @param rssFolderPath the path of the folder to store the RSS files
	 * @param rssName the filename of the general RSS file
	 * @param rssTitle the title of the RSS files
	 * @param rssDescription the description of the RSS files
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image files
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS files
	 * @param hq High Quality
	 * @param rssCategory the category of the RSS file
	 * @param itunesAuthor The itunes author
	 * @param itunesSubtitle The itunes subtitle
	 * @param itunesSummary The itunes summary
	 * @param itunesImage The itunes image
	 * @param itunesCategory The itunes category
	 * @param itunesKeywords The itunes keywords
	 */
	public synchronized void mediaUpload( Course c, FileItem mediaFile, String tags , String rssFolderPath, 
		String rssName, String rssTitle, String rssDescription, String serverUrl, 
		String rssImageUrl, String recordedInterfaceUrl, String language,boolean hq, String rssCategory, String itunesAuthor,
		String itunesSubtitle, String itunesSummary, String itunesImage, String itunesCategory, String itunesKeywords) {
		
		MediaUpload mu = new MediaUpload(db, fs, c, mediaFile, tags,
				this, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, 
				rssImageUrl, recordedInterfaceUrl, language,hq, rssCategory, itunesAuthor,
				itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords);
		mu.start();
	}
	
	/**
	 * Gets a list of all the courses (non-Univr)
	 * @return the list of courses
	 */
	public List<Course> getAllCourses() {
		return db.getAllCourses();
	}
	
	/**
	 * Gets a list of all the Univ-R courses
	 * @return the list of Univ-R courses
	 */
	public List<Course> getUnivrCourses() {
		return db.getUnivrCourses();
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
	 * Gets a Univr course by providing its id
	 * @param courseId the id of the Univr course
	 * @return the Univr object
	 */
	public Univr getUnivr(int courseId) {
		return db.getUnivr(courseId);
	}
	
	/**
	 * Modifies a course
	 * @param c the course to modify
	 */
	public synchronized void modifyCourse(Course c) {
		db.modifyCourse(c);
	}
	
	/**
	 * Deletes a course by providing its id and media Folder
	 * @param courseId the id of the course to delete
	 * @param mediaFolder the folder of the course to delete
	 */
	public synchronized void deleteCourse(int courseId, String mediaFolder) {
		db.deleteCourse(courseId);
		fs.deleteCourse(mediaFolder);
	}
	
	/**
	 * Deletes a univr by providing its id
	 * @param courseId the id of the course to delete
	 */
	public synchronized void deleteUnivr(int courseId) {
		db.deleteUnivr(courseId);
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
	public void deleteTests(String testKeyWord) {
		List<String> mediaFolders = db.getTestsMediaFolders(testKeyWord);
		System.out.println(mediaFolders);
		for( String mediaFolder : mediaFolders) {
			db.deleteTag(db.getCourseByMediafolder(mediaFolder).getCourseid());
		}
		db.deleteTests(testKeyWord);
		for( String mediaFolder : mediaFolders) {
			fs.deleteCourse(mediaFolder);
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
	public void modifyAmphi(Amphi a, String oldAmphiip) {
		db.modifyAmphi(a, oldAmphiip);
	}

	/**
	 * Sets the status of the live in an amphi
	 * @param ip the IP address of the amphi
	 * @param status the status od the live in the amphi
	 */
	public void setAmphiStatus(String ip, boolean status) {
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
		// For all courses 
		List<Course> courses = db.getAllCourses();
		String rssPath = rssFolderPath + "/" + cleanFileName(rssName) + ".xml";
		fs.rssCreation(courses, rssPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
		fs.rssCreation(courses, rssFolderPath + "/" + cleanFileName("univrav") + ".xml", rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
		
		
		// For the teachers
		List<String> teachers = db.getTeachers();
		for( String teacher : teachers) {
			courses = db.getCoursesByAuthor(teacher);
			rssPath = rssFolderPath + "/" + cleanFileName(teacher) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+teacher, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
		}
				
		// For the formation
		List<String> formations = db.getFormations();
		for( String formation : formations) {
			courses = db.getCoursesByFormation(formation);
			rssPath = rssFolderPath + "/" + cleanFileName(formation) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+formation, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
		}
		
		//For the login
		List<User> users = db.getAllUsers();
		for(User u : users) {
			courses = db.getCoursesByUser(u, null, null);	
			rssPath = rssFolderPath + "/" + cleanFileName("lgn_" + u.getLogin()) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+"lgn_"+u.getLogin(), rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
		}
		
		
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
		List<Course> courses = db.getAllCourses();
		String rssPath = rssFolderPath + "/" + cleanFileName(rssName) + ".xml";
		fs.rssCreation(courses, rssPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
		fs.rssCreation(courses, rssFolderPath + "/" + cleanFileName("univrav") + ".xml", rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
				
		// For the teacher
		if( ! (c.getName() == null && c.getFirstname() == null)) {
			String teacher = db.getTeacherFullName(c.getName(), c.getFirstname());
			courses = db.getCoursesByAuthor(teacher);
			rssPath = rssFolderPath + "/" + cleanFileName(teacher) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+teacher, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
		}
		
		//For the formation
		if(c.getFormation()!=null) {
			courses = db.getCoursesByFormation(c.getFormation());
			rssPath = rssFolderPath + "/" + cleanFileName(c.getFormation()) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+c.getFormation(), rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
		}
		
		//For the login
		if(c.getUserid()!=null && c.getUserid()!=0) {
			User u = db.getUser(c.getUserid());
			courses = db.getCoursesByUser(u, null, null);
			rssPath = rssFolderPath + "/" + cleanFileName("lgn_" + u.getLogin()) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle+" - "+"lgn_"+u.getLogin(), rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language, rssCategory,itunesAuthor, itunesSubtitle, itunesSummary, itunesImage, itunesCategory, itunesKeywords, db);
		}
		
	}
	
	/**
	 * Gets the list of RSS files
	 * @param rssTitle the title of the general RSS file
	 * @param rssName the name of the general RSS file
	 * @return the hashMap of the RSS titles and files
	 */
	public HashMap<String, String> getRssFileList( String rssTitle, String rssName) {
		LinkedHashMap<String, String> rss = new LinkedHashMap<String, String>();
		rss.put(rssTitle, "../rss/" + rssName + ".xml");
		
		List<String> teachers = db.getTeachers();
		for( String teacher : teachers) {
			rss.put(
				teacher,
				"../rss/" + cleanFileName(teacher) + ".xml"
			);
		}
		
		List<String> formations = db.getFormations();
		for( String formation : formations) {
			rss.put(
				formation,
				"../rss/" + cleanFileName(formation) + ".xml"
			);
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

			if(c.getFormation()!=null && !c.getFormation().equals(""))
				rss.put(c.getFormation(),"../rss/" + cleanFileName(c.getFormation()) + ".xml");
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
	 * Verifies if a user is logged on Univ-R
	 * @param uid the uid of the user
	 * @param uuid Univ-R session identifier
	 * @param estab the establishment
	 * @return true if the user is logged on Univ-R
	 */
	public boolean isUserAuth(int uid, String uuid, String estab) {
		return ud.isUserAuth(uid, uuid, estab);
	}
	
	/**
	 * Gets information about an user
	 * @param uid the uid of the user
	 * @param estab the establishment
	 * @return the information about the user
	 */
	public HashMap<String, String> getUserInfos(int uid, String estab) {
		return ud.getUserInfos(uid,estab);
	}
	
	/**
	 * Gets information about an user
	 * @param login the login of the user
	 * @param estab the establishment
	 * @return the information about the user
	 */
	public HashMap<String, String> getUserInfos(String login,String estab) {
		return ud.getUserInfos(login,estab);
	}
	
	/**
	 * Gets the group name of a group
	 * @param groupCode the code of the group
	 * @param estab the establishment
	 * @return the group name
	 */
	public String getGroupName(int groupCode,String estab) {
		return ud.getGroupName(groupCode,estab);
	}
	
	/**
	 * Publishes a course on Univ-R
	 * @param courseId the id of the course to publish
	 * @param groupCode the code of the group which will have access to the course
	 * @param estab the establishment
	 */
	public void publishCourse(int courseId, int groupCode,String estab) {
		ud.publishCourse(courseId, groupCode,estab);
	}
	
	/**
	 * Checks if a user has access to a course
	 * @param uid the uid of the user
	 * @param courseId the course
	 * @param estab the establishment
	 * @return true if the user has access to the course
	 */
	public boolean hasAccessToCourse(int uid, int courseId,String estab) {
		return ud.hasAccessToCourse(uid, courseId,estab);
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
	 * @return the list of course
	 */
	public List<Course> getCoursesByUser(User u,Integer number,Integer start) {
		return db.getCoursesByUser(u,number,start);
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
	 * @return the list of users
	 */
	public List<User> getAllUsers() {
		return db.getAllUsers();
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
	 * Gets a restricted list of courses
	 * @param mediafolder the folder of the media
	 * @return the course
	 */
	public Course getCourseByMediafolder(String mediafolder) {
		return db.getCourseByMediafolder(mediafolder);
	}
	
	/**
	 * Get the list of ahref for the tag cloud
	 * @param listTag the list of tag
	 * @return list of ahref
	 */
	public List<String> getTagCloud(List<String> listTag) {
				
		List<String> nouvelleList = new ArrayList<String>();
		
		for(int i=0;i<listTag.size();i++) {
			nouvelleList.add("<a href=\"./tags?tags="+listTag.get(i)+"\" id=\"tag"+(i+1)+"\">"+(listTag.get(i).length()<20 ? listTag.get(i) : listTag.get(i).substring(0, 20)+"...")+"</a>&nbsp;&nbsp;");
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
	public void modifySelection(Selection s) {
		db.modifySelection(s);
	}
	
	/**
	 * Deletes a selection by providing its id
	 * @param position the position of the selection
	 */
	public void deleteSelection(int position) {
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
		
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA"); 
			md.update(plaintext.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		
		return (new BASE64Encoder()).encode(md.digest()); 
	}
	
	/**
	 * Add an additional document to a course
	 * @param c the course
	 * @param docFile the fileitem of the document
	 */
	public void addAdditionalDoc(Course c, FileItem docFile) {
		fs.addAdditionalDoc(c.getMediaFolder(),docFile);
		c.setAdddocname(FilenameUtils.getName(docFile.getName()));		
		db.modifyCourse(c);
	}
	
	/**
	 * Delete an additional document of a course
	 * @param c the course
	 */
	public void deleteAdditionalDoc(Course c) {
		fs.deleteAdditionalDoc(c.getMediaFolder(),c.getAdddocname());
		c.setAdddocname(null);
		db.modifyCourse(c);
	}

}
