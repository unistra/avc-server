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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + number;
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
		Teacher other = (Teacher) obj;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number != other.number)
			return false;
		return true;
	}
	
}
