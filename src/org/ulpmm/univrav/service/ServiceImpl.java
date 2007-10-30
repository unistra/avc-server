package org.ulpmm.univrav.service;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.dao.IUnivrDao;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Univr;

public class ServiceImpl implements IService {

	private IDatabase db;
	private IFileSystem fs;
	private IUnivrDao ud;
	private static ServiceImpl instance = new ServiceImpl();
	
	public static ServiceImpl getInstance() {
		return instance;
	}
	
	/**
	 * @return the db
	 */
	public IDatabase getDb() {
		return db;
	}

	/**
	 * @param db the db to set
	 */
	public void setDb(IDatabase db) {
		this.db = db;
	}
	
	/**
	 * @return the fs
	 */
	public IFileSystem getFs() {
		return fs;
	}

	/**
	 * @param fs the fs to set
	 */
	public void setFs(IFileSystem fs) {
		this.fs = fs;
	}

	/**
	 * @return the ud
	 */
	public IUnivrDao getUd() {
		return ud;
	}

	/**
	 * @param ud the ud to set
	 */
	public void setUd(IUnivrDao ud) {
		this.ud = ud;
	}

	/**
	 * Adds a new course
	 * @param c the course to add
	 * @param courseArchive the archive file of the course to add
	 * @param rssFolderPath the path of the folder to store the RSS files
	 * @param rssName the filename of the general RSS file
	 * @param rssTitle the title of the RSS files
	 * @param rssDescription the description of the RSS files
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image files
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS files
	 */
	public synchronized void addCourse(Course c, String courseArchive, String rssFolderPath, 
			String rssName, String rssTitle, String rssDescription, String serverUrl, 
			String rssImageUrl, String recordedInterfaceUrl, String language) {
		
		CourseAddition ca = new CourseAddition(db, fs, c, courseArchive, 
				this, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, 
				rssImageUrl, recordedInterfaceUrl, language);
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
	 * @param c the Univr course to complete
	 * @param courseArchive the archive file of the course to complete
	 * @param rssFolderPath the path of the folder to store the RSS files
	 * @param rssName the filename of the general RSS file
	 * @param rssTitle the title of the RSS files
	 * @param rssDescription the description of the RSS files
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image files
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS files
	 */
	public synchronized void completeUnivrCourse(Course c, String courseArchive , String rssFolderPath, 
			String rssName, String rssTitle, String rssDescription, String serverUrl, 
			String rssImageUrl, String recordedInterfaceUrl, String language) {
		
		UnivrCourseCompletion ucc = new UnivrCourseCompletion(db, fs, c, courseArchive,
				this, rssFolderPath, rssName, rssTitle, rssDescription, serverUrl, 
				rssImageUrl, recordedInterfaceUrl, language);
		ucc.start();
	}
	
	/**
	 * Gets a list of all the courses (non-Univr)
	 * @return the list of courses
	 */
	public synchronized List<Course> getAllCourses() {
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
	 * @return the list of courses
	 */
	public synchronized List<Course> getNLastCourses(int n) {
		return db.getNLastCourses(n);
	}
	
	/**
	 * Gets a restricted list of courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @return the list of courses
	 */
	public synchronized List<Course> getCourses(int number, int start) {
		return db.getCourses(number, start);
	}
	
	/**
	 * Gets a restricted list of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @return the list of courses
	 */
	public synchronized List<Course> getCourses(HashMap<String, String> params, int number, int start) {
		return db.getCourses(params, number, start);
	}
	
	/**
	 * Gets a course by providing its id
	 * @param courseId the id of the course
	 * @return the course
	 */
	public synchronized Course getCourse(int courseId) {
		return db.getCourse(courseId);
	}

	/**
	 * Gets a course by providing its id and genre
	 * @param courseId the id of the course
	 * @param genre the genre of the course
	 * @return the course
	 */
	public synchronized Course getCourse(int courseId, String genre) {
		return db.getCourse(courseId, genre);
	}
	
	/**
	 * Gets the total number of courses
	 * @return the number of courses
	 */
	public synchronized int getCourseNumber() {
		return db.getCourseNumber();
	}
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @return the number of courses
	 */
	public synchronized int getCourseNumber(HashMap<String, String> params) {
		return db.getCourseNumber(params);
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
	 * Deletes the test courses (courses with genre 'Suppression')
	 * @param testKeyWord the key word which identifies a test
	 */
	public synchronized void deleteTests(String testKeyWord) {
		List<String> mediaFolders = db.getTestsMediaFolders(testKeyWord);
		System.out.println(mediaFolders);
		db.deleteTests(testKeyWord);
		for( String mediaFolder : mediaFolders)
			fs.deleteCourse(mediaFolder);
	}
	
	/**
	 * Hides the test courses (ie courses beginning with 'test' or 'essai')
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 */
	public synchronized void hideTests(String testKeyWord1, String testKeyWord2) {
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
	 * Gets the list of all the teachers
	 * @return the list of teachers
	 */
	public synchronized List<String> getTeachers() {
		return db.getTeachers();
	}
	
	/**
	 * Gets the list of all the formations
	 * @return the list of formations
	 */
	public synchronized List<String> getFormations() {
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
	public synchronized List<Slide> getSlides(int courseId) {
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
	public synchronized List<Building> getBuildings() {
		return db.getBuildings();
	}
	
	/**
	 * Gets a building by providing its id
	 * @param buildingId the id of the building
	 * @return the building
	 */
	public synchronized Building getBuilding(int buildingId) {
		return db.getBuilding(buildingId);
	}
	
	/**
	 * Gets a building name by providing one of its amphis IP address
	 * @param amphiIp the amphi IP address
	 * @return the building name
	 */
	public synchronized String getBuildingName(String amphiIp) {
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
	 * @return the list of amphis
	 */
	public synchronized List<Amphi> getAmphis(int buildingId) {
		return db.getAmphis(buildingId);
	}

	/**
	 * Gets an amphi by providing its IP address
	 * @param ip the IP address of the amphi
	 * @return the amphi
	 */
	public synchronized Amphi getAmphi(int amphiId) {
		return db.getAmphi(amphiId);
	}
	
	/**
	 * Gets an amphi by providing its IP address
	 * @param ip the IP address of the amphi
	 * @return the amphi
	 */
	public synchronized Amphi getAmphi(String ip) {
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
	 * Checks wether a video amphi is diffusing an audio stream or a video stream
	 * @param amphiIp the Ip address of the video amphi
	 * @param audioLivePort the port used by the audio live
	 * @return the stream type diffused by the amphi
	 */
	public synchronized String getLiveStreamType(String amphiIp, int audioLivePort) {
		return fs.getLiveStreamType(amphiIp, audioLivePort);
	}
	
	/**
	 * Creates the .ram file used by a live video
	 * @param amphiIp the Ip address of the video amphi
	 * @param helixServerIp the Ip address of the helix server
	 */
	public synchronized void createLiveVideo(String amphiIp, String helixServerIp) {
		fs.createLiveVideo(amphiIp, helixServerIp);
	}
	
	/**
	 * Retrieves a list of the website's available themes
	 * @param stylesFolder the folder in which the themes are stored
	 * @return the list of themes
	 */
	public synchronized List<String> getStyles(String stylesFolder) {
		return fs.getStyles(stylesFolder);
	}
	
	/**
	 * Retrieves a list of the website's available languages
	 * @param languagesFolder the folder in which the language property files are stored
	 * @return the list of languages
	 */
	public synchronized List<String> getLanguages(String languagesFolder) {
		return fs.getLanguages(languagesFolder);
	}
	
	/**
	 * Sends a file to the client's browser
	 * @param filename the name of the file to send
	 * @param out the stream in which send the file
	 */
	public synchronized void returnFile(String filename, OutputStream out) {
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
	 */
	public synchronized void generateRss( String rssFolderPath, String rssName, String rssTitle, String rssDescription, 
			String serverUrl, String rssImageUrl, String recordedInterfaceUrl, String language) {
		// For all courses
		List<Course> courses = db.getAllUnlockedCourses();
		String rssPath = rssFolderPath + "/" + cleanFileName(rssName) + ".xml";
		fs.rssCreation(courses, rssPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
		
		// For the teachers
		List<String> teachers = db.getTeachersWithRss();
		for( String teacher : teachers) {
			courses = db.getUnlockedCourses(teacher);
			rssPath = rssFolderPath + "/" + cleanFileName(teacher) + ".xml";
			fs.rssCreation(courses, rssPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
		}
	}
	
	/**
	 * Creates the RSS files for all the courses and the teacher of the course in parameter
	 * @param course c the course which has been modified or added
	 * @param rssFolderPath the path of the folder to store the RSS files
	 * @param rssName the filename of the general RSS file
	 * @param rssTitle the title of the RSS files
	 * @param rssDescription the description of the RSS files
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image files
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS files
	 */
	public synchronized void generateRss( Course c, String rssFolderPath, String rssName, String rssTitle, String rssDescription, 
			String serverUrl, String rssImageUrl, String recordedInterfaceUrl, String language) {
		// For all courses
		List<Course> courses = db.getAllUnlockedCourses();
		String rssPath = rssFolderPath + "/" + cleanFileName(rssName) + ".xml";
		fs.rssCreation(courses, rssPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
		
		// For the teacher
		if( ! (c.getName() == null && c.getFirstname() == null)) {
			String teacher = db.getTeacherFullName(c.getName(), c.getFirstname());
			System.out.println(teacher);
			courses = db.getUnlockedCourses(teacher);
			rssPath = rssFolderPath + "/" + cleanFileName(teacher) + ".xml";
			System.out.println(rssPath);
			fs.rssCreation(courses, rssPath, rssName, rssTitle, rssDescription, serverUrl, rssImageUrl, recordedInterfaceUrl, language);
		}
	}
	
	/**
	 * Gets the list of RSS files
	 * @param rssTitle the title of the general RSS file
	 * @param rssName the name of the general RSS file
	 * @return the hashMap of the RSS titles and files
	 */
	public synchronized HashMap<String, String> getRssFileList( String rssTitle, String rssName) {
		LinkedHashMap<String, String> rss = new LinkedHashMap<String, String>();
		rss.put(rssTitle, "../rss/" + rssName + ".xml");
		
		List<String> teachers = db.getTeachersWithRss();
		for( String teacher : teachers) {
			rss.put(
				teacher,
				"../rss/" + cleanFileName(teacher) + ".xml"
			);
		}
		
		return rss;
	}
	
	/**
	 * Sends a message over a socket to the Univ-R AV client
	 * @param message the message to send
	 * @return the answer of the client
	 */
	public synchronized String sendMessageToClient(String message, String ip, int port) {
		return fs.sendMessageToClient(message, ip, port);
	}
	
	/**
	 * Verifies if a user is logged on Univ-R
	 * @param uid the uid of the user
	 * @param uuid Univ-R session identifier
	 * @return true if the user is logged on Univ-R
	 */
	public synchronized boolean isUserAuth(int uid, String uuid) {
		return ud.isUserAuth(uid, uuid);
	}
	
	/**
	 * Gets information about an user
	 * @param uid the uid of the user
	 * @return the information about the user
	 */
	public synchronized HashMap<String, String> getUserInfos(int uid) {
		return ud.getUserInfos(uid);
	}
	
	/**
	 * Gets the group name of a group
	 * @param groupCode the code of the group
	 * @return the group name
	 */
	public synchronized String getGroupName(int groupCode) {
		return ud.getGroupName(groupCode);
	}
	
	/**
	 * Publishes a course on Univ-R
	 * @param courseId the id of the course to publish
	 * @param groupCode the code of the group which will have access to the course
	 */
	public synchronized void publishCourse(int courseId, int groupCode) {
		ud.publishCourse(courseId, groupCode);
	}
	
	/**
	 * Checks if a user has access to a course
	 * @param uid the uid of the user
	 * @param courseId the course
	 * @return true if the user has access to the course
	 */
	public synchronized boolean hasAccessToCourse(int uid, int courseId) {
		return ud.hasAccessToCourse(uid, courseId);
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
	public static String cleanFileName(String string){
		
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
	
}
