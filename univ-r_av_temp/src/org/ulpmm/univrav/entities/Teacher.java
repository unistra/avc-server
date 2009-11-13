package org.ulpmm.univrav.entities;

/**
 * Class for the Teacher entity.
 * Teacher is not a table. This entity is used because it's convenient.
 * 
 * @author morgan
 *
 */
public class Teacher {
	
	/** The name of the teacher */
	private String name; 
	
	/** The firstname of the teacher */
	private String firstname;
	
	/** The number of courses */
	private int number;
	
	/**
	 * Teacher's constructor
	 * 
	 * @param name The name of the teacher
	 * @param firstname The firstname of the teacher
	 * @param number The number of courses
	 */
	public Teacher( String name, String firstname, int number) {
		this.name = name;
		this.firstname = firstname;
		this.number = number;
	}

	/**
	 * Gets teacher's name
	 * @return the name
	 */
	public String getname() {
		return name;
	}
	
	/**
	 * Gets teacher's firstname
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Gets the number of courses
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	
}
