package org.ulpmm.univrav.service;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Selection;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Tag;
import org.ulpmm.univrav.entities.Teacher;
import org.ulpmm.univrav.entities.Univr;
import org.ulpmm.univrav.entities.User;

/**
 * Interface for service implementation methods
 * 
 * @author morgan
 *
 */
public interface IService {
	
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
	 */
	public void addCourse(Course c, String courseArchive, String tags, String rssFolderPath, 
			String rssName, String rssTitle, String rssDescription, String serverUrl, 
			String rssImageUrl, String recordedInterfaceUrl, String language);
	
	/**
	 * Adds a new course from Univ-R
	 * @param c the course to add
	 * @param u the Univ-R infos
	 */
	public void addUnivrCourse(Course c, Univr u);
	
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
	 */
	public void completeUnivrCourse(Course c, Univr u, String courseArchive , String rssFolderPath, 
			String rssName, String rssTitle, String rssDescription, String serverUrl, 
			String rssImageUrl, String recordedInterfaceUrl, String language);
	
	/**
	 * Creates a course from an uploaded audio or video media file
	 * @param c the course to create
	 * @param mediaFile the media file of the course to create
	 * @param tags list
	 * @param rssFolderPath the path of the folder to store the RSS files
	 * @param rssName the filename of the general RSS file
	 * @param rssTitle the title of the RSS files
	 * @param rssDescription the description of the RSS files
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image files
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS files
	 * @param hq High Quality
	 */
	public void mediaUpload( Course c, FileItem mediaFile, String tags, String rssFolderPath, 
		String rssName, String rssTitle, String rssDescription, String serverUrl, 
		String rssImageUrl, String recordedInterfaceUrl, String language,boolean hq);
	
	/**
	 * Gets a list of all the courses (no-Univr)
	 * @return the list of courses
	 */
	public List<Course> getAllCourses();
	
	/**
	 * Gets a list of all the Univ-R courses
	 * @return the list of Univ-R courses
	 */
	public List<Course> getUnivrCourses();
	
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
	 * Gets a Univr course by providing its id
	 * @param courseId the id of the Univr course
	 * @return the Univr object
	 */
	public Univr getUnivr(int courseId);
	
	/**
	 * Modifies a course
	 * @param c the course to modify
	 */
	public void modifyCourse(Course c);
	
	/**
	 * Deletes a course by providing its id and media Folder
	 * @param courseId the id of the course to delete
	 * @param mediaFolder the folder of the course to delete
	 */
	public void deleteCourse(int courseId, String mediaFolder);
	
	/**
	 * Deletes a unvir by providing its id
	 * @param courseId the id of the course to delete
	 */
	public void deleteUnivr(int courseId);
	
	
	
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
	 * Gets an amphi by providing its amphi id
	 * @param amphiId the amphiId of the amphi
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
	 * Retrieves a list of the website's available themes
	 * @param stylesFolder the folder in which the themes are stored
	 * @return the list of themes
	 */
	public List<String> getStyles(String stylesFolder);
	
	/**
	 * Retrieves a list of the website's available languages
	 * @param languagesFolder the folder in which the language property files are stored
	 * @return the list of languages
	 */
	public List<String> getLanguages(String languagesFolder);
	
	/**
	 * Sends a file to the client's browser
	 * @param filename the name of the file to send
	 * @param out the stream in which send the file
	 */
	public void returnFile(String filename, OutputStream out);
	
	/**
	 * Creates the RSS files for all the courses and teachers
	 * 
	 * @param rssFolderPath the path of the folder to store the RSS files
	 * @param rssFileName the filename of the general RSS file
	 * @param rssTitle the title of the RSS files
	 * @param rssDescription the description of the RSS files
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image files
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS files
	 */
	public void generateRss( String rssFolderPath, String rssFileName, String rssTitle, String rssDescription, 
			String serverUrl, String rssImageUrl, String recordedInterfaceUrl, String language);
	
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
	 */
	public void generateRss( Course c, String rssFolderPath, String rssName, String rssTitle, String rssDescription, 
			String serverUrl, String rssImageUrl, String recordedInterfaceUrl, String language);
	
	/**
	 * Sends a message over a socket to the Univ-R AV client
	 * @param message the message to send
	 * @param ip the ip to contact the client
	 * @param port the port to contact the client
	 * @param timeout the timeout to contact the client
	 * @return the answer of the client
	 */
	public String sendMessageToClient(String message, String ip, int port,int timeout);
	
	/**
	 * Retrieves information about used and free disk space on the server
	 * @return the string containing the info
	 */
	public String getDiskSpaceInfo();
	
	/**
	 * Verifies if a user is logged on Univ-R
	 * @param uid the uid of the user
	 * @param uuid Univ-R session identifier
	 * @param estab the establishment
	 * @return true if the user is logged on Univ-R
	 */
	public boolean isUserAuth(int uid, String uuid, String estab);
	
	/**
	 * Gets information about an user
	 * @param uid the uid of the user
	 * @param estab the establishment
	 * @return the information about the user
	 */
	public HashMap<String, String> getUserInfos(int uid, String estab);
	
	/**
	 * Gets information about an user
	 * @param login the login of the user
	 * @param estab the establishment
	 * @return the information about the user
	 */
	public HashMap<String, String> getUserInfos(String login, String estab);
	
	/**
	 * Gets the group name of a group
	 * @param groupCode the code of the group
	 * @param estab the establishment
	 * @return the group name
	 */
	public String getGroupName(int groupCode, String estab);
	
	/**
	 * Publishes a course on Univ-R
	 * @param courseId the id of the course to publish
	 * @param groupCode the code of the group which will have access to the course
	 * @param estab the establishment
	 */
	public void publishCourse(int courseId, int groupCode, String estab);
	
	/**
	 * Checks if a user has access to a course
	 * @param uid the uid of the user
	 * @param courseId the course
	 * @param estab the establishment
	 * @return true if the user has access to the course
	 */
	public boolean hasAccessToCourse(int uid, int courseId,String estab);
	
	/**
	 * Function which removes the undesirable characters of a String and the useless spaces at the end
	 * @param string the string to clean
	 * @return the cleaned string
	 */
	public String cleanString(String string);
	
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
	 * @return the list of course
	 */
	public List<Course> getCourses(User u, int number,int start);
	
	/**
	 * Gets the total number of courses
	 * @param u the user
	 * @return the number of courses
	 */
	public int getCourseNumber(User u);
	
	/**
	 * Gets the list of all the users
	 * @return the list of users
	 */
	public List<User> getAllUsers();
	
	/**
	 * Send an email to confirm the add of the new course 
	 * @param subject the subject of the mail
	 * @param message the message of the mail
	 * @param email the email address
	 */
	public void sendMail(String subject, String message, String email);
	
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
	 * @param tags the tags
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(List<String> tags,String testKeyWord1, String testKeyWord2, String testKeyWord3);
	
	/**
	 * Gets a restricted list of courses
	 * @param mediafolder the folder of the media
	 * @return the course
	 */
	public Course getCourseByMediafolder(String mediafolder);
	
	/**
	 * Get the list of ahref for the tag cloud
	 * @param listTag the list of tag
	 * @return list of ahref
	 */
	public List<String> getTagCloud(List<String> listTag);
	
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
	 * Encrypt a text
	 * @param plaintext
	 * @return string encrypted
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public String encrypt(String plaintext) throws NoSuchAlgorithmException, UnsupportedEncodingException;
	
	/**
	 * Add an additional document to a course
	 * @param c the course
	 * @param docFile the fileitem of the document
	 */
	public void addAdditionalDoc(Course c, FileItem docFile);
	
	/**
	 * Delete an additional document of a course
	 * @param c the course
	 */
	public void deleteAdditionalDoc(Course c);


}
