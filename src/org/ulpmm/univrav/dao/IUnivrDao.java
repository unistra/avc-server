package org.ulpmm.univrav.dao;

import java.util.HashMap;

/**
 * Interface for the univr implementation methods
 * 
 * @author morgan
 *
 */
public interface IUnivrDao {
	
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
	public boolean hasAccessToCourse(int uid, int courseId, String estab);
	
}
