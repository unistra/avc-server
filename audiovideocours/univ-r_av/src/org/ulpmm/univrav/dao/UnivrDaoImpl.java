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
 * Univr implementation methods
 * 
 * @author morgan
 *
 */
public class UnivrDaoImpl implements IUnivrDao {

	/**
	 * Verifies if a user is logged on Univ-R
	 * @param uid the uid of the user
	 * @param uuid Univ-R session identifier
	 * @param estab the establishment
	 * @return true if the user is logged on Univ-R
	 */
	public boolean isUserAuth(int uid, String uuid, String estab) {
		User u = new User(uid,estab);
		return u.isUserAuth(uuid);
	}
	
	/**
	 * Gets information about an user
	 * @param uid the uid of the user
	 * @param estab the establishment
	 * @return the information about the user
	 */
	public HashMap<String, String> getUserInfos(int uid, String estab) {
		User u = new User(uid,estab);
		return u.getUserInfos();
	}
	
	/**
	 * Gets information about an user
	 * @param login the login of the user
	 * @param estab the establishment
	 * @return the information about the user
	 */
	public HashMap<String, String> getUserInfos(String login, String estab) {
		HashMap<String, String> infos = null;
		
		try {
			User u = new User(login,estab);
			infos = u.getUserInfos();
		}
		catch( Exception e) {
			System.out.println("Error while retrieving the information about the user");
			e.printStackTrace();
		}
		
		return infos;
	}
	
	/**
	 * Gets the group name of a group
	 * @param groupCode the code of the group
	 * @param estab the establishment
	 * @return the group name
	 */
	public String getGroupName(int groupCode, String estab) {
		
		Group g=null;
		
		try {
			g = new Group(groupCode,estab);
		}
		catch(Exception e) {
			System.out.println("Error while retrieving the group");
			e.printStackTrace();
		}
		
		return g.getGroupName();
	}
	
	/**
	 * Publishes a course on Univ-R
	 * @param courseId the id of the course to publish
	 * @param groupCode the code of the group which will have access to the course
	 * @param estab the establishment
	 */
	public void publishCourse(int courseId, int groupCode, String estab) {
		Cours c = new Cours(courseId,estab);
		c.publishAll(groupCode);
	}
	
	/**
	 * Checks if a user has access to a course
	 * @param uid the uid of the user
	 * @param courseId the course
	 * @param estab the establishment
	 * @return true if the user has access to the course
	 */
	public boolean hasAccessToCourse(int uid, int courseId, String estab) {
		User u = new User(uid,estab);
		Cours c = new Cours(courseId, estab);
				
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
