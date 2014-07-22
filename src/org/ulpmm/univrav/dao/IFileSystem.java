package org.ulpmm.univrav.dao;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.ulpmm.univrav.entities.Course;

/**
 * Interface of file system implementation methods
 * 
 * @author morgan
 *
 */
public interface IFileSystem {

	/**
	 * Creates a course with all its media files on the file system
	 * @param c the course to create
	 * @param courseArchive the name of the archive file of the course to create
	 */
	public void addCourse(Course c, String courseArchive);
	
	
	/**
	 * Create the file description.txt
	 * @param c the course
	 */
	public void createDescriptionFile(Course c);
	
		
	/**
	 * Creates a course from an uploaded audio or video media file
	 * @param c the course to create
	 * @param mediaFile the media file of the course to create
	 * @param fileName the filename
	 * @param extension the extension
	 */
	public void mediaUpload(Course c, FileItem mediaFile, String fileName, String extension);
	
		
	/**
	 * Reads the timecodes csv file and creates the timecodes list
	 * @param mediaFolder the folder where the timecode list is stored
	 * @return the timecodes list
	 */
	public ArrayList<String> getTimecodes(String mediaFolder);
	
	/**
	 * Removes the media folder of a course on the file system
	 * @param mediaFolder the media folder of the course
	 */
	public void deleteCourse(String mediaFolder);
	
	/**
	 * Move the media folder of a course on the file system with "_DELETE"
	 * @param mediaFolder the media folder of the course
	 */
	public void deleteMoveCourse(String mediaFolder);
	
	/**
	 * Creates a RSS files for a list of courses
	 * @param courses the list of courses
	 * @param filePath the full path of the RSS file to create
	 * @param rssName the name of the RSS files
	 * @param rssTitle the title of the RSS file
	 * @param rssDescription the description of the RSS file
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image file
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS file
	 * @param rssCategory the category of the RSS file
	 * @param itunesAuthor The itunes author
	 * @param itunesSubtitle The itunes subtitle
	 * @param itunesSummary The itunes summary
	 * @param itunesImage The itunes image
	 * @param itunesCategory The itunes category
	 * @param itunesKeywords The itunes keywords
	 */
	public void rssCreation( List<Course> courses, String filePath, String rssName, 
			String rssTitle, String rssDescription, String serverUrl, String rssImageUrl, 
			String recordedInterfaceUrl, String language, String rssCategory, String itunesAuthor,
			String itunesSubtitle, String itunesSummary, String itunesImage, String itunesCategory, String itunesKeywords);
	
		
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
	 * Sends a message over a socket to the Univ-R AV client
	 * @param message the message to send
	 * @param ip the ip to contact the client
	 * @param port the port to contact the client
	 * @param timeout the timeout to contact the client
	 * @return the answer of the client
	 */
	public String sendMessageToClient(String message, String ip, int port, int timeout);
	
	/**
	 * Retrieves information about used and free disk space on the server
	 * @return the string containing the info
	 */
	public String getDiskSpaceInfo();
	
	/**
	 * Send an email to confirm the add of the new course 
	 * @param subject the subject of the mail
	 * @param message the message of the mail
	 * @param email the email address
	 */
	public void sendMail(String subject, String message, String email);
	
	/**
	 * Add an additional document to a course
	 * @param mediafolder the mediafolder
	 * @param docFile the fileitem of the document
	 */
	public void addAdditionalDoc(String mediafolder, FileItem docFile);
	
	/**
	 * Delete an additional document of a course
	 * @param mediafolder the mediafolder
	 * @param addDocName the name of the additional document
	 */
	public void deleteAdditionalDoc(String mediafolder, String addDocName);
	
	
	/**
	 * Script to launch medias encodage
	 * @param serverUrl the server url
	 * @param job_line the job line
	 */
	public void launchJob(String serverUrl, String job_line);
		
	/**
	 * Return the clean courses url (check if RAND[?-?] exist)
	 * @param coursesUrl : the coursesurl from univrav.properties
	 * @return the clean courses url
	 */
	public String getCleanCoursesUrl(String coursesUrl) ;
	

	/**
	 * Add a subtitles xml to a course
	 * @param mediafolder the mediafolder
	 * @param docFile the fileitem of the document
	 * @param courseid courseid
	 */
	public void addSubtitles(String mediafolder, FileItem docFile, int courseid);
	
	/**
	 * Delete a subtitles xml of a course
	 * @param mediafolder the mediafolder
	 * @param courseid courseid
	 */
	public void deleteSubtitles(String mediafolder, int courseid);
	
	
	/**
	 * Retag media function. Re-generate description.txt and re-tag medias 
	 * @param c the course
	 */
	public void mediaRetag(Course c);
	
	/**
	 * Move the add video file
	 * @param c the course to create
	 * @param mediaFile the media file of the course to create
	 * @param fileName the filename
	 * @param extension the extension
	 */
	public void moveAddVideo(Course c, FileItem mediaFile, String fileName, String extension);
	
	/**
	 * Delete the additional video of a course
	 * @param mediafolder the mediafolder
	 * @param courseid courseid
	 */
	public void deleteReplaceMedia(String mediafolder, int courseid);
	
	/**
	 * Check if the zip file contains a not empty audio or video file 
	 * @param zip the name of the zip file
	 * @return true if the zip file is ok
	 */
	public boolean checkZipFile(String zip);
}

