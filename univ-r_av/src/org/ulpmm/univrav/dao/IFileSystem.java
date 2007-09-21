package org.ulpmm.univrav.dao;

import java.util.ArrayList;

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
	
	// + méthodes pour les amphis
	
}
