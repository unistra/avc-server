package org.ulpmm.univrav.dao;

import java.util.HashMap;

public interface IUnivrDao {
	
	/**
	 * Verifies if a user is logged on Univ-R
	 * @param uid the uid of the user
	 * @param uuid Univ-R session identifier
	 * @return true if the user is logged on Univ-R
	 */
	public boolean isUserAuth(int uid, String uuid);
	
	/**
	 * Gets information about an user
	 * @param uid the uid of the user
	 * @return the information about the user
	 */
	public HashMap<String, String> getUserInfos(int uid);
	
	/**
	 * Gets information about an user
	 * @param login the login of the user
	 * @return the information about the user
	 */
	public HashMap<String, String> getUserInfos(String login);
	
	/**
	 * Gets the group name of a group
	 * @param groupCode the code of the group
	 * @return the group name
	 */
	public String getGroupName(int groupCode);
	
	/**
	 * Publishes a course on Univ-R
	 * @param courseId the id of the course to publish
	 * @param groupCode the code of the group which will have access to the course
	 */
	public void publishCourse(int courseId, int groupCode);
	
	/**
	 * Checks if a user has access to a course
	 * @param uid the uid of the user
	 * @param courseId the course
	 * @return true if the user has access to the course
	 */
	public boolean hasAccessToCourse(int uid, int courseId);
	
}
