package org.ulpmm.univrav.service;

import java.util.HashMap;
import java.util.List;

import org.ulpmm.univrav.dao.IDao;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Smil;

public class ServiceImpl implements IService {

	private IDao dao;
	
	/**
	 * @return the dao
	 */
	public IDao getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(IDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Adds a new course
	 * @param c the course to add
	 */
	public synchronized void addCourse(Course c) {
		dao.addCourse(c);
	}
	
	/**
	 * Gets a list of all the courses
	 * @return the list of courses
	 */
	public synchronized List<Course> getAllCourses() {
		return dao.getAllCourses();
	}
	
	/**
	 * Gets the courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @return the list of courses
	 */
	public synchronized List<Course> getCourses(HashMap<String, String> params) {
		return dao.getCourses(params);
	}
	
	/**
	 * Gets a course by providing its id
	 * @param courseId the id of the course
	 * @return the course
	 */
	public synchronized Course getCourse(int courseId) {
		return dao.getCourse(courseId);
	}

	/**
	 * Gets a course by providing its id and genre
	 * @param courseId the id of the course
	 * @param genre the genre of the course
	 * @return the course
	 */
	public synchronized Course getCourse(int courseId, String genre) {
		return dao.getCourse(courseId, genre);
	}
	
	/**
	 * Modifies a course
	 * @param c the course to modify
	 */
	public synchronized void modifyCourse(Course c) {
		dao.modifyCourse(c);
	}
	
	/**
	 * Deletes a course by providing its id
	 * @param courseId the id of the course
	 */
	public synchronized void deleteCourse(int courseId) {
		dao.deleteCourse(courseId);
	}
	
	/**
	 * Adds a new slide
	 * @param s the slide to add
	 */
	public synchronized void addSlide(Slide s) {
		dao.addSlide(s);
	}
	
	/**
	 * Gets the slides of a course
	 * @param courseId the id of the course
	 * @return the list of slides
	 */
	public synchronized List<Slide> getSlides(int courseId) {
		return dao.getSlides(courseId);
	}
	
	/**
	 * Deletes the slides of a course
	 * @param courseId the id of the course
	 */
	public synchronized void deleteSlide(int courseId) {
		dao.deleteSlide(courseId);
	}
	
	/**
	 * Adds a new smil
	 * @param s the smil to add
	 */
	public synchronized void addSmil(Smil s) {
		dao.addSmil(s);
	}
	
	/**
	 * Gets the smil of a course
	 * @param courseId the id of the course
	 * @return the smil
	 */
	public synchronized Smil getSmil(int courseId) {
		return dao.getSmil(courseId);
	}
	
	/**
	 * Deletes the smil of a course
	 * @param courseId the id of the course
	 */
	public synchronized void deleteSmil(int courseId) {
		dao.deleteSmil(courseId);
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
