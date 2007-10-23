package org.ulpmm.univrav.service;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

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
	 */
	public synchronized void addCourse(Course c, String courseArchive) {
		CourseAddition ca = new CourseAddition(db, fs, c, courseArchive);
		ca.start();
	}
	
	/**
	 * Adds a new course from Univ-R
	 * @param c the course to add
	 * @param u the Univ-R infos
	 */
	public void addUnivrCourse(Course c, Univr u) {
		db.addCourse(c);
		db.addUnivr(u);
	}
	
	/**
	 * Completes a Univr course
	 * @param c the Univr course to complete
	 * @param courseArchive the archive file of the course to complete
	 */
	public void completeUnivrCourse(Course c, String courseArchive) {
		UnivrCourseCompletion ucc = new UnivrCourseCompletion(db, fs, c, courseArchive);
		ucc.start();
	}
	
	/**
	 * Gets a list of all the courses
	 * @return the list of courses
	 */
	public synchronized List<Course> getAllCourses() {
		return db.getAllCourses();
	}
	
	/**
	 * Gets a list of all the courses without an access code
	 * @return the list of courses
	 */
	public synchronized List<Course> getAllUnlockedCourses() {
		return db.getAllUnlockedCourses();
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
	 * Gets the list of courses without access code for a teacher
	 * @param teacher the teacher
	 * @return the list of courses
	 */
	public synchronized List<Course> getUnlockedCourses(String[] teacher) {
		return db.getUnlockedCourses(teacher);
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
	 * Deletes the test courses
	 */
	public void deleteTests() {
		List<String> mediaFolders = db.getTestsMediaFolders();
		db.deleteTests();
		for( String mediaFolder : mediaFolders)
			fs.deleteCourse(mediaFolder);
	}
	
	/**
	 * Hides the test courses (courses beginning with 'test' or 'essai')
	 */
	public void hideTests() {
		db.hideTests();
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
	public synchronized List<String[]> getTeachers() {
		return db.getTeachers();
	}
	
	/**
	 * Gets the list of all the teachers who have at least one course with no access code
	 * @return the list of teachers
	 */
	public synchronized List<String[]> getTeachersWithRss() {
		return db.getTeachersWithRss();
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
	public Amphi getAmphi(String ip) {
		return db.getAmphi(ip);
	}
	
	/**
	 * Modifies an amphi
	 * @param a the amphi to modify
	 */
	public synchronized void modifyAmphi(Amphi a) {
		db.modifyAmphi(a);
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
	public void deleteAmphi(int amphiId) {
		db.deleteAmphi(amphiId);
	}
	
	/**
	 * Creates a RSS files for a list of courses
	 * @param courses the list of courses
	 * @param filePath the full path of the RSS file to create
	 * @param rssTitle the title of the RSS file
	 * @param rssDescription the description of the RSS file
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image file
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS file
	 * @throws ParserConfigurationException
	 */
	public synchronized void rssCreation( List<Course> courses, String filePath, String rssTitle, 
			String rssDescription, String serverUrl, String rssImageUrl, 
			String recordedInterfaceUrl, String language ) {
		
		fs.rssCreation(courses, filePath, rssTitle, rssDescription, serverUrl, 
				rssImageUrl, recordedInterfaceUrl, language);
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
	 * Sends a message over a socket to the Univ-R AV client
	 * @param message the message to send
	 * @return the answer of the client
	 */
	public String sendMessageToClient(String message, String ip, int port) {
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
	public boolean hasAccessToCourse(int uid, int courseId) {
		return ud.hasAccessToCourse(uid, courseId);
	}
	
}
