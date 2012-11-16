package org.ulpmm.univrav.entities;

/**
 * Class for the Slide entity
 * 
 * @author morgan
 *
 */
public class Slide {
	
	/** the course's id */
	private int courseid;
	
	/** the slide time */
	private int slidetime;
	
	/**
	 * Default constructor
	 */
	public Slide() {
	}
	
	/**
	 * Slide's constructor
	 * 
	 * @param courseid the course's id
	 * @param slidetime the slide time
	 */
	public Slide(int courseid, int slidetime) {
		this.courseid = courseid;
		this.slidetime = slidetime;
	}
	
	/**
	 * Returns a string representation of the object
	 * @return a string representation of the object
	 */
	public String toString() {
		return Integer.toString(this.slidetime);
	}

	/**
	 * Gets course's id
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}

	/**
	 * Gets the slide time
	 * @return the slide time
	 */
	public int getSlidetime() {
		return slidetime;
	}

	/**
	 * Set the course's id
	 * @param courseid the course id
	 */
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	/**
	 * Set the slide's time
	 * @param slidetime the slidetime
	 */
	public void setSlidetime(int slidetime) {
		this.slidetime = slidetime;
	}
	
	
}
