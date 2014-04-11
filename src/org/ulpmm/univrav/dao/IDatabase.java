package org.ulpmm.univrav.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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

/**
 * Interface for database implementation methods
 * 
 * @author morgan
 *
 */
public interface IDatabase {
	
	/**
	 * Get the full name of formation. Matching with level and discipline tables.
	 * @param formation the course formation codes
	 * @return the full name of formation
	 */
	public String getFormationFullName(String formation);
	
	/**
	 * Adds a new course
	 * @param c the course to add
	 */
	public void addCourse(Course c);
		
	/**
	 * Gets a list of all the courses
	 * @param onlyvisible true to get only visible courses
	 * @param formationfullname show full name of formation with discipline and level table
	 * @param limit limit number of courses
	 * @return the list of courses
	 */
	public List<Course> getAllCourses(boolean onlyvisible, boolean formationfullname, Integer limit);
		
		
	/**
	 * Gets a list of the n last courses
	 * @param n the number of courses to return
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getNLastCourses(int n, String testKeyWord1, String testKeyWord2);
	
	/**
	 * Gets a restricted list of courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getCourses(int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3);
	
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
	public List<Course> getCourses(HashMap<String, String> params, int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3);
	
	/**
	 * Gets the list of courses for an author
	 * @param author the author
	 * @param limit limit number of courses
	 * @return the list of courses
	 */
	public List<Course> getCoursesByAuthor(String author,Integer limit);
	
	/**
	 * Gets the list of courses without access code for a teacher
	 * @param formation the formation
	 * @param limit limit number of courses
	 * @return the list of courses
	 */
	public List<Course> getCoursesByFormation(String formation,Integer limit);
	
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
	public Course getCourse(int courseId, String genre) ;
	
	/**
	 * Gets the total number of courses
	 * @return the number of courses
	 */
	public int getCourseNumber();
	
	/**
	 * Gets the total number of courses without test keywords
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(String testKeyWord1, String testKeyWord2, String testKeyWord3);
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(HashMap<String, String> params,String testKeyWord1, String testKeyWord2, String testKeyWord3);
		
	/**
	 * Modifies a course
	 * @param c the course to modify
	 */
	public void modifyCourse(Course c);
	
	/**
	 * Modifies the mediatype of course
	 * @param courseid the course id 
	 * @param mediatype the mediatype
	 */
	public void modifyCourseMediatype(int courseid, int mediatype);
	
	/**
	 * Gets the mediatype of the course
	 * @param courseid the courseid
	 * @return the mediatype of the course
	 */
	public int getMediaType(int courseid);
	
	/**
	 * Deletes a course by providing its id
	 * @param courseId the id of the course
	 */
	public void deleteCourse(int courseId);
		
	/**
	 * Gets the list of the test courses to delete
	 * @param testKeyWord the key word which identifies a test
	 * @return the list of test courses 
	 */
	public List<Course> getTestsToDelete(String testKeyWord);
	
	/**
	 * Gets a restricted list of test courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getTests(int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3);
	
	/**
	 * Gets the total number of tests with test keywords
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getTestNumber(String testKeyWord1, String testKeyWord2, String testKeyWord3);
	
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
	 * @param buildingId the id of the building
	 * @return the list of amphis
	 */
	public List<Amphi> getAmphis(int buildingId);
	
	/**
	 * Gets an amphi by providing its id
	 * @param amphiId the id of the amphi
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
	
	/**
	 * Gets user by login (login is UNIQUE)
	 * @param login the login of the user
	 * @return the user
	 */
	public User getUser(String login);
	
	/**
	 * Get user by id 
	 * @param id the id of the user
	 * @return the user
	 */
	public User getUser(int id);
	
	/**
	 * Gets local user by hash code (hash is unique)
	 * @param hash the hash code
	 * @return the user
	 */
	//public User getUserLocalByHash(String hash);
	
	/**
	 * Gets user by login and hash(login is UNIQUE)
	 * @param login the login of the user
	 * @param hash hash code
	 * @return the user
	 */
	public User getUserLocal(String login, String hash);
	
	/**
	 * Modify a password for a user
	 * @param login the login
	 * @param hash the password
	 * @param hashtype the password type
	 */
	public void modifyUserPassword(String login, String hash, String hashtype);
	
	/**
	 * Modify reset code for a user
	 * @param login the login
	 * @param hash the reset code
	 * @param hashtype the reset code type
	 * @param dateResetCode the reset code date
	 */
	public void modifyUserResetCode(String login, String hash, String hashtype, Timestamp dateResetCode);
	
	/**
	 * Gets user by reset code
	 * @param hash reset code
	 * @return the user
	 */
	public User getUserLocalByResetCode(String hash);
	
	/**
	 * Gets the id of the next user which will be uploaded
	 * @return the id of the user
	 */
	public int getNextUserId();
	
	/**
	 * Adds a new user
	 * @param u User
	 */
	public void addUser(User u);
	
	/**
	 * Modify a user
	 * @param u User
	 */
	public void modifyUser(User u);
	
	/**
	 * Deletes an user by providing its id
	 * @param userid the id of the user
	 */
	public void deleteUser(int userid);
	
	/**
	 * Gets a list of courses by providing its user
	 * @param u the user of the course
	 * @param number the number of courses
	 * @param start the start number of courses
	 * @param onlyvisible true to get only visible courses
	 * @return the list of course
	 */
	public List<Course> getCoursesByUser(User u, Integer number, Integer start, boolean onlyvisible);

	/**
	 * Gets a list of courses by providing its user with search keywords
	 * @param keywords the keywords
	 * @param u the user of the course
	 * @param number the number of courses
	 * @param start the start number of courses
	 * @param onlyvisible true to get only visible courses
	 * @return the list of course
	 */
	public List<Course> getCoursesByUser(String keywords, User u, Integer number, Integer start, boolean onlyvisible);
	
	/**
	 * Gets the total number of courses
	 * @param u the user
	 * @return the number of courses
	 */
	public int getCourseNumber(User u);

	/**
	 * Gets the total number of courses
	 * @param keywords the keywords
	 * @param u the user
	 * @return the number of courses
	 */
	public int getCourseNumber(String keywords, User u);
	
	/**
	 * Gets the list of all the users
	 * @param number limit
	 * @param start offset
	 * @return the list of users
	 */
	public List<User> getAllUsers(Integer number, Integer start);
	
	/**
	 * Gets the number of all the users
	 * @return the list of users
	 */
	public int getUsersNumber();
	
	/**
	 * Add a new tag
	 * @param t the tag
	 */
	public void addTag(Tag t);
	
	/**
	 * Deletes tags by providing its courseid
	 * @param courseid the id of the course
	 */
	public void deleteTag(int courseid);
	
	/**
	 * Gets a list of all tags of a course
	 * @param c Course
	 * @return the list of tags
	 */
	public List<Tag> getTagsByCourse(Course c);
	
	/**
	 * Gets a list of all tags
	 * @return the list of tags
	 */
	public List<String> getAllTags();
	
	/**
	 * Gets a list of most popular tags
	 * @return the list of most popular tags
	 */
	public List<String> getMostPopularTags();
	
	/**
	 * Gets a restricted list of courses
	 * @param tag the tag
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getCoursesByTags(List<String> tag, int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3);

	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param tag the tag
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(List<String> tag,String testKeyWord1, String testKeyWord2, String testKeyWord3);
		
	/**
	 * Gets a list of the n selection courses
	 * @param n the number of courses to return
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getNSelectionCourses(int n, String testKeyWord1, String testKeyWord2);

	/**
	 * Gets a list of the n selection courses
	 * @param n the number of courses to return
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getNFormationCourses(int n, String testKeyWord1, String testKeyWord2);
	
	/**
	 * Gets the list of all the selection
	 * @return the list of users
	 */
	public List<Selection> getAllSelections();
	
	/**
	 * Get selection by position 
	 * @param position the position of the selection
	 * @return the selection
	 */
	public Selection getSelection(int position);
	
	/**
	 * Adds a new selection
	 * @param s the selection
	 */
	public void addSelection(Selection s);
	
	/**
	 * Modify a selection
	 * @param s the selection
	 */
	public void modifySelection(Selection s);
	
	/**
	 * Deletes a selection by providing its id
	 * @param position the position of the selection
	 */
	public void deleteSelection(int position);
	
	/**
	 * Gets the list of all jobs
	 * @return the list of jobs
	 */
	public List<Job> getAllJobs();
	
	/**
	 * Adds a new job
	 * @param j the job
	 */
	public void addJob(Job j);
	
	/**
	 * Modify a job
	 * @param j the job
	 */
	public void modifyJob(Job j);
	
	/**
	 * Gets the id of the next job which will be uploaded
	 * @return the id of the job
	 */
	public int getNextJobId();
	
	/**
	 * Modify the job status
	 * @param courseid course id
	 * @param status job status
	 * @param coursetype coursetype
	 */
	public void modifyJobStatus(int courseid,String status,String coursetype);
	
	/**
	 * Get job by courseid 
	 * @param courseid the courseid of the job
	 * @param coursetype the job type
	 * @return the job
	 */
	public Job getJob(int courseid, String coursetype);
	
	/**
	 * Gets the list of all discipline
	 * @return the list of discipline
	 */
	public List<Discipline> getAllDiscipline();
	
	/**
	 * Adds a new discipline
	 * @param d the discipline
	 */
	public void addDiscipline(Discipline d);
	
	/**
	 * Modify a discipline
	 * @param d the discipline
	 */
	public void modifyDiscipline(Discipline d);
	
	/**
	 * Gets the id of the next discipline which will be uploaded
	 * @return the id of the discipline
	 */
	public int getNextDisciplineId();
	
	/**
	 * Get discipline by id 
	 * @param id the id of the discipline
	 * @return the discipline
	 */
	public Discipline getDiscipline(int id);
	
	/**
	 * Deletes a discipline by providing its id
	 * @param disciplineid the id of the discipline
	 */
	public void deleteDiscipline(int disciplineid);
	
	/**
	 * Gets the list of all levels
	 * @return the list of levels
	 */
	public List<Level> getAllLevels();
	
	/**
	 * Get level by code 
	 * @param code the code of the level
	 * @return the level
	 */
	public Level getLevelByCode(String code);
	
	/**
	 * Return the result of find tracks function
	 * 
	 * @param params all parameters
	 * @return a list of course
	 */
	public List<Course> getTracks(HashMap<String, String> params);
	
	/**
	 * Return the result of find stats function
	 * 
	 * @param params all parameters
	 * @return a list of stats
	 */
	public HashMap<String, Integer> getStats(HashMap<String, String> params);
	
	/**
	 * Add a log user action
	 * @param log the log
	 */
	public void addLogUserAction(LogUserAction log);
	
	/**
	 * get log user actions by user
	 * @param userid the user
	 * @return list of log
	 */
	public List<LogUserAction> getLogUserActionByUser(Integer userid);

}
