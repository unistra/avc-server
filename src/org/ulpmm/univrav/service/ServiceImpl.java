package org.ulpmm.univrav.service;

import java.util.HashMap;
import java.util.List;

import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;

public class ServiceImpl implements IService {

	private IDatabase db;
	private IFileSystem fs;
	
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
	 * Adds a new course
	 * @param c the course to add
	 */
	public synchronized void addCourse(Course c, String courseArchive) {
		CourseAddition ca = new CourseAddition(db, fs, c, courseArchive);
		ca.start();
	}
	
	/**
	 * Gets a list of all the courses
	 * @return the list of courses
	 */
	public synchronized List<Course> getAllCourses() {
		return db.getAllCourses();
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
	public List<Course> getCourses(int number, int start) {
		return db.getCourses(number, start);
	}
	
	/**
	 * Gets a restricted list of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @return the list of courses
	 */
	public List<Course> getCourses(HashMap<String, String> params, int number, int start) {
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
	public int getCourseNumber() {
		return db.getCourseNumber();
	}
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @return the number of courses
	 */
	public int getCourseNumber(HashMap<String, String> params) {
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
	 * Deletes a course by providing its id
	 * @param courseId the id of the course
	 */
	public synchronized void deleteCourse(int courseId) {
		db.deleteCourse(courseId);
		fs.deleteCourse();
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
	public List<String[]> getTeachers() {
		return db.getTeachers();
	}
	
	/**
	 * Gets the list of all the formations
	 * @return the list of formations
	 */
	public List<String> getFormations() {
		return db.getFormations();
	}
	
	
	/**
	 * Adds the slides of a course
	 * @param s the slide to add
	 */
	/*public void addSlides(int courseid) {
		ArrayList<String> list = fs.getTimecodes();
		for( int i = 0 ; i< list.size() ; i++)
			db.addSlide(new Slide(courseid,"XXXXXXXXXX",(int) Float.parseFloat(list.get(i))));
	}*/
	
	/**
	 * Adds a new slide
	 * @param s the slide to add
	 */
	/*public synchronized void addSlide(Slide s) {
		db.addSlide(s);
	}*/
	
	/**
	 * Gets the slides of a course
	 * @param courseId the id of the course
	 * @return the list of slides
	 */
	public synchronized List<Slide> getSlides(int courseId) {
		return db.getSlides(courseId);
	}
	
	/**
	 * Deletes the slides of a course
	 * @param courseId the id of the course
	 */
	public synchronized void deleteSlide(int courseId) {
		db.deleteSlide(courseId);
	}
	
	
	/**
	 * Gets the list of the buildings
	 * @return the list of buildings
	 */
	public synchronized List<Building> getBuildings() {
		return db.getBuildings();
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
	 * Adds a new Amphi
	 * @param a the amphi to add
	 */
	public synchronized void addAmphi(Amphi a) {
		// TODO Auto-generated method stub
		
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
	public synchronized Amphi getAmphi(String ip) {
		return db.getAmphi(ip);
	}
	
	/**
	 * Modifies an amphi
	 * @param a the amphi to modify
	 */
	public synchronized void modifyAmphi(Amphi a) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Deletes an amphi by providing its id
	 * @param id the id of the amphi
	 */
	public synchronized void deleteAmphi(String ip) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Creates the .ram file used by a live video
	 * @param amphiIp the Ip address of the video amphi
	 * @param helixServerIp the Ip address of the helix server
	 */
	public void createLiveVideo(String amphiIp, String helixServerIp) {
		fs.createLiveVideo(amphiIp, helixServerIp);
	}
	
	/**
	 * Retrieves a list of the website's available themes
	 * @param stylesFolder the folder in which the themes are stored
	 * @return the list of themes
	 */
	public List<String> getStyles(String stylesFolder) {
		return fs.getStyles(stylesFolder);
	}
	
}
