package org.ulpmm.univrav.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.dao.IFileSystem;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Smil;

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
	public List<Course> getNLastCourses(int n) {
		return db.getNLastCourses(n);
	}
	
	/**
	 * Gets the courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @return the list of courses
	 */
	public synchronized List<Course> getCourses(HashMap<String, String> params) {
		return db.getCourses(params);
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
	public int getNextCoursId() {
		return db.getNextCoursId();
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
	 * Adds a new smil
	 * @param s the smil to add
	 */
	/*public synchronized void addSmil(Smil s) {
		db.addSmil(s);
	}*/
	
	/**
	 * Gets the smil of a course
	 * @param courseId the id of the course
	 * @return the smil
	 */
	public synchronized Smil getSmil(int courseId) {
		return db.getSmil(courseId);
	}
	
	/**
	 * Deletes the smil of a course
	 * @param courseId the id of the course
	 */
	public synchronized void deleteSmil(int courseId) {
		db.deleteSmil(courseId);
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
	public synchronized List<Amphi> getAmphis() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets an amphi by providing its IP address
	 * @param ip the IP address of the amphi
	 * @return the amphi
	 */
	public synchronized Amphi getAmphi(String ip) {
		// TODO Auto-generated method stub
		return null;
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
	
}
