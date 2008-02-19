package org.ulpmm.univrav.entities;

public class Teacher {
	
	private String name;
	private String firstname;
	private int number;
	
	/**
	 * 
	 * @param name
	 * @param number
	 */
	public Teacher( String name, String firstname, int number) {
		this.name = name;
		this.firstname = firstname;
		this.number = number;
	}

	/**
	 * @return the name
	 */
	public String getname() {
		return name;
	}
	
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	
}
