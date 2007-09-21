package org.ulpmm.univrav.service;

import java.util.HashMap;
import java.util.List;

import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Smil;

public interface IService {
	
	/**
	 * Adds a new course
	 * @param c the course to add
	 */
	public void addCourse(Course c, String courseArchive);
	
	/**
	 * Gets a list of all the courses
	 * @return the list of courses
	 */
	public List<Course> getAllCourses();
	
	/**
	 * Gets a list of the n last courses
	 * @param n the number of courses to return
	 * @return the list of courses
	 */
	public List<Course> getNLastCourses(int n);
	
	/**
	 * Gets the courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @return the list of courses
	 */
	public List<Course> getCourses(HashMap<String, String> params);
	
	/**
	 * Gets a course by providing its id
	 * @param courseId the id of the course
	 * @return the course
	 */
	public Course getCourse(int courseId);
	
	/**
	 * Gets a course by providing its id and genre
	 * @param courseId the id of the course
	 * @param genre the genre of the course
	 * @return the course
	 */
	public Course getCourse(int courseId, String genre);
	
	/**
	 * Modifies a course
	 * @param c the course to modify
	 */
	public void modifyCourse(Course c);
	
	/**
	 * Deletes a course by providing its id
	 * @param courseId the id of the course
	 */
	public void deleteCourse(int courseId);
	
	/**
	 * Gets the id of the next course which will be uploaded
	 * @return the id of the course
	 */
	public int getNextCoursId();
	
	
	/**
	 * Adds the slides of a course
	 * @param s the slide to add
	 */
	//public void addSlides(int courseid);
	
	/**
	 * Gets the slides of a course
	 * @param courseId the id of the course
	 * @return the list of slides
	 */
	public List<Slide> getSlides(int courseId);
	
	/**
	 * Deletes the slides of a course
	 * @param courseId the id of the course
	 */
	public void deleteSlide(int courseId);
	
	
	/**
	 * Adds a new smil
	 * @param s the smil to add
	 */
	//public void addSmil(Smil s);
	
	/**
	 * Gets the smil of a course
	 * @param courseId the id of the course
	 * @return the smil
	 */
	public Smil getSmil(int courseId);
	
	/**
	 * Deletes the smil of a course
	 * @param courseId the id of the course
	 */
	public void deleteSmil(int courseId);
	
	
	/**
	 * Adds a new Amphi
	 * @param a the amphi to add
	 */
	public void addAmphi(Amphi a);
	
	/**
	 * Gets a list of all the amphis
	 * @return the list of amphis
	 */
	public List<Amphi> getAmphis();
	
	/**
	 * Gets an amphi by providing its IP address
	 * @param ip the IP address of the amphi
	 * @return the amphi
	 */
	public Amphi getAmphi(String ip);
	
	/**
	 * Modifies an amphi
	 * @param a the amphi to modify
	 */
	public void modifyAmphi(Amphi a);
	
	/**
	 * Deletes an amphi by providing its id
	 * @param id the id of the amphi
	 */
	public void deleteAmphi(String id);
}
