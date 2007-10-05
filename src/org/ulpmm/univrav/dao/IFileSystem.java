package org.ulpmm.univrav.dao;

import java.util.ArrayList;
import java.util.List;

import org.ulpmm.univrav.entities.Course;

public interface IFileSystem {

	/**
	 * Creates a course with all its media files on the file system
	 * @param courseArchive the name of the archive file of the course to create
	 */
	public void addCourse(Course c, String courseArchive);
	
	/**
	 * Reads the timecodes csv file and creates the timecodes list
	 * @return the timecodes list
	 */
	public ArrayList<String> getTimecodes();
	
	public void deleteCourse();
	
	public void rssCreation();
	
	/**
	 * Creates the .ram file used by a live video
	 * @param amphiIp the Ip address of the video amphi
	 * @param helixServerIp the Ip address of the helix server
	 */
	public void createLiveVideo(String amphiIp, String helixServerIp);
	
	/**
	 * Retrieves a list of the website's available themes
	 * @param stylesFolder the folder in which the themes are stored
	 * @return the list of themes
	 */
	public List<String> getStyles(String stylesFolder);
	
	// + méthodes pour les amphis
	
}
