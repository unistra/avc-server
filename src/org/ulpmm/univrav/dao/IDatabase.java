package org.ulpmm.univrav.dao;

import java.util.HashMap;
import java.util.List;

import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Teacher;
import org.ulpmm.univrav.entities.Univr;

public interface IDatabase {
	
	/**
	 * Adds a new course
	 * @param c the course to add
	 */
	public void addCourse(Course c);
	
	/**
	 * Adds a new Univ-R course
	 * @param u the Univ-R course
	 */
	public void addUnivr(Univr u);
	
	/**
	 * Gets a list of all the courses (non-Univr)
	 * @return the list of courses
	 */
	public List<Course> getAllCourses();
	
	/**
	 * Gets a list of all the Univ-R courses
	 * @return the list of Univ-R courses
	 */
	public List<Course> getUnivrCourses();
	
	/**
	 * Gets a list of all the courses without an access code
	 * @return the list of courses
	 */
	public List<Course> getAllUnlockedCourses();
	
	/**
	 * Gets a list of the n last courses
	 * @param n the number of courses to return
	 * @return the list of courses
	 */
	public List<Course> getNLastCourses(int n);
	
	/**
	 * Gets a restricted list of courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @return the list of courses
	 */
	public List<Course> getCourses(int number, int start);
	
	/**
	 * Gets a restricted list of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @return the list of courses
	 */
	public List<Course> getCourses(HashMap<String, String> params, int number, int start);
	
	/**
	 * Gets the list of courses without access code for a teacher
	 * @param teacher the teacher
	 * @return the list of courses
	 */
	public List<Course> getUnlockedCourses(String teacher);
	
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
	 * Gets the total number of courses
	 * @return the number of courses
	 */
	public int getCourseNumber();
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @return the number of courses
	 */
	public int getCourseNumber(HashMap<String, String> params);
	
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
	 * Gets the list of the media folders of the test courses
	 * @return the list of media folders
	 * @param testKeyWord the key word which identifies a test
	 */
	public List<String> getTestsMediaFolders(String testKeyWord);
	
	/**
	 * Deletes the test courses (courses with genre 'Suppression')
	 * @param testKeyWord the key word which identifies a test
	 */
	public void deleteTests(String testKeyWord);
	
	/**
	 * Hides the test courses (ie courses beginning with 'test' or 'essai')
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 */
	public void hideTests(String testKeyWord1, String testKeyWord2);
	
	/**
	 * Gets the id of the next course which will be uploaded
	 * @return the id of the course
	 */
	public int getNextCoursId();
	
	/**
	 * Gets the list of the teachers with visible courses
	 * @return the list of teachers
	 */
	public List<String> getTeachers();
	
	/**
	 * Gets the list of all the teachers
	 * @return the list of teachers
	 */
	public List<Teacher> getAllTeachers();
	
	/**
	 * Gets the full name of a teacher with the correct case
	 * @param name the name of the teacher
	 * @param firstname the firstname of the teacher
	 * @return the full name of the teacher 
	 */
	public String getTeacherFullName(String name, String firstname);
	
	/**
	 * Gets the list of all the teachers who have at least one course with no access code
	 * @return the list of teachers
	 */
	public List<String> getTeachersWithRss();
	
	/**
	 * Gets the list of all the formations
	 * @return the list of formations
	 */
	public List<String> getFormations();
	
	/**
	 * Increments the number of consultations for a course
	 * @param c the course
	 */
	public void incrementConsultations(Course c);
	
	
	/**
	 * Adds a new slide
	 * @param s the slide to add
	 */
	public void addSlide(Slide s);
	
	/**
	 * Gets the slides of a course
	 * @param courseId the id of the course
	 * @return the list of slides
	 */
	public List<Slide> getSlides(int courseId);
	
	
	/**
	 * Adds a new building
	 * @param b the building to add
	 */
	public void addBuilding(Building b);
	
	/**
	 * Gets the list of the buildings
	 * @return the list of buildings
	 */
	public List<Building> getBuildings();
	
	/**
	 * Gets a building by providing its id
	 * @param buildingId the id of the building
	 * @return the building
	 */
	public Building getBuilding(int buildingId);
	
	/**
	 * Gets a building name by providing one of its amphis IP address
	 * @param amphiIp the amphi IP address
	 * @return the building name
	 */
	public String getBuildingName(String amphiIp);
	
	/**
	 * Modifies a building
	 * @param b the building to modify
	 */
	public void modifyBuilding(Building b);
	
	/**
	 * Deletes a building
	 * @param buildingId the id of the building
	 */
	public void deleteBuilding(int buildingId);
	
	/**
	 * Adds a new Amphi
	 * @param a the amphi to add
	 */
	public void addAmphi(Amphi a);
	
	/**
	 * Gets a list of all the amphis
	 * @return the list of amphis
	 */
	public List<Amphi> getAmphis(int buildingId);
	
	/**
	 * Gets an amphi by providing its IP address
	 * @param ip the IP address of the amphi
	 * @return the amphi
	 */
	public Amphi getAmphi(int amphiId);
	
	/**
	 * Gets an amphi by providing its IP address
	 * @param ip the IP address of the amphi
	 * @return the amphi
	 */
	public Amphi getAmphi(String ip);
	
	/**
	 * Modifies an amphi
	 * @param a the amphi to modify
	 * @param oldAmphiip the old Ip address of this amphi
	 */
	public void modifyAmphi(Amphi a, String oldAmphiip);
	
	/**
	 * Sets the status of the live in an amphi
	 * @param ip the IP address of the amphi
	 * @param status the status od the live in the amphi
	 */
	public void setAmphiStatus(String ip, boolean status);
	
	/**
	 * Deletes an amphi by providing its id
	 * @param amphiId the id of the amphi
	 */
	public void deleteAmphi(int amphiId);
}
