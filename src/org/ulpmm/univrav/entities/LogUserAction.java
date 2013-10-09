package org.ulpmm.univrav.entities;

import java.sql.Timestamp;

/**
 * Class for the log user action entity
 * 
 * @author morgan
 *
 */

public class LogUserAction {
	
	/** date of the action */
	private Timestamp date;
	/** the user id (null for anonymous user) */
	private Integer userid;
	/** the courseid of the action (null for non-course actions) */
	private Integer courseid;
	/** short action description */
	private String action;
	/** url of the action */
	private String url;
	/** type of the action */
	private String type;
	/** more information */
	private String information;
	
	/** types */
	public static final String typeLogin = "LOGIN"; // "authentication" actions
	public static final String typeLogout = "LOGOUT"; // "disconnection" actions
	public static final String typeAccess = "ACCESS"; // "simple" actions like home, recorded, search ...
	public static final String typeAdd = "ADD"; // "add" actions (add course, add subtitles ...)
	public static final String typeRemove = "REMOVE"; // "remove" actions (remove course, remove subtitles ...)
	public static final String typeEdit = "EDIT"; // "edit" actions (edit course, ...)
	public static final String typeDownload = "DOWNLOAD"; // "download" actions (download mp3, zip ...)
	public static final String typeRead = "READ"; // "read" actions (read flash course, html5 course, live course)
	
	/** default constructor */	
	public LogUserAction() {}


	/**
	 * constructor with param
	 * 
	 * @param date date of the action
	 * @param userid the user id (null for anonymous user)
	 * @param courseid the courseid of the action (null for non-course actions)
	 * @param action short action description
	 * @param url url of the action
	 * @param type type of the action
	 * @param information more information
	 */
	public LogUserAction(Timestamp date, Integer userid, Integer courseid,
			String action, String url, String type, String information) {
		super();
		this.date = date;
		this.userid = userid;
		this.courseid = courseid;
		this.action = action;
		this.url = url;
		this.type = type;
		this.information = information;
	}

	@Override
	public String toString() {
		return "LogUserAction [date=" + date + ", userid=" + userid
				+ ", courseid=" + courseid + ", action=" + action + ", url="
				+ url + ", type=" + type + ", information=" + information + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result
				+ ((courseid == null) ? 0 : courseid.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((information == null) ? 0 : information.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogUserAction other = (LogUserAction) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (courseid == null) {
			if (other.courseid != null)
				return false;
		} else if (!courseid.equals(other.courseid))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (information == null) {
			if (other.information != null)
				return false;
		} else if (!information.equals(other.information))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

	/**
	 * get date
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * set date
	 * @param date the date
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * get user id
	 * @return the user id
	 */
	public Integer getUserid() {
		return userid;
	}

	/**
	 * set the user id
	 * @param userid set the user id
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	/**
	 * get the course id
	 * @return the course id
	 */
	public Integer getCourseid() {
		return courseid;
	}

	/**
	 * set the course id
	 * @param courseid set the course id
	 */
	public void setCourseid(Integer courseid) {
		this.courseid = courseid;
	}

	/**
	 * get the action
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * set the action
	 * @param action the action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/** 
	 * get the url
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * set the url
	 * @param url the url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * get the type
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * set the type
	 * @param type the type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * get information
	 * @return information
	 */
	public String getInformation() {
		return information;
	}

	/**
	 * set information
	 * @param information the information
	 */
	public void setInformation(String information) {
		this.information = information;
	}
	
	
	
}