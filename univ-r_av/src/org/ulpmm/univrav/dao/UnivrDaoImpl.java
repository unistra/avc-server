/**
 * 
 */
package org.ulpmm.univrav.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.ulpmm.univr.cmsapi.Cours;
import org.ulpmm.univr.cmsapi.Group;
import org.ulpmm.univr.cmsapi.User;

/**
 * @author laurent
 *
 */
public class UnivrDaoImpl implements IUnivrDao {

	/**
	 * Verifies if a user is logged on Univ-R
	 * @param uid the uid of the user
	 * @param uuid Univ-R session identifier
	 * @return true if the user is logged on Univ-R
	 */
	public boolean isUserAuth(int uid, String uuid) {
		User u = new User(uid);
		return u.isUserAuth(uuid);
	}
	
	/**
	 * Gets information about an user
	 * @param uid the uid of the user
	 * @return the information about the user
	 */
	public HashMap<String, String> getUserInfos(int uid) {
		User u = new User(uid);
		return u.getUserInfos();
	}
	
	/**
	 * Gets the group name of a group
	 * @param groupCode the code of the group
	 * @return the group name
	 */
	public String getGroupName(int groupCode) {
		Group g = new Group(groupCode);
		return g.getName();
	}
	
	/**
	 * Publishes a course on Univ-R
	 * @param courseId the id of the course to publish
	 * @param groupCode the code of the group which will have access to the course
	 */
	public void publishCourse(int courseId, int groupCode) {
		Cours c = new Cours(courseId);
		c.publishAll(groupCode);
	}
	
	/**
	 * Checks if a user has access to a course
	 * @param uid the uid of the user
	 * @param courseId the course
	 * @return true if the user has access to the course
	 */
	public boolean hasAccessToCourse(int uid, int courseId) {
		User u = new User(uid);
		Cours c = new Cours(courseId);
		
		/* Retrieves the user groups */
		List<Group> gl = u.getGroupsByUser();
		
		/* Checks if the user belongs to a group which has access to the course */
		Iterator<Group> i = gl.iterator();
		boolean access = false;
		while (i.hasNext() && access == false) {
			Group g = i.next();

			/* Checks if the course is entirely published */
			if (c.isAllPublished(g.getUnivRId()) == true) 
				access = true;
		}
		
		return access;
	}

}
