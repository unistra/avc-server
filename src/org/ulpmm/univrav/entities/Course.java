package org.ulpmm.univrav.entities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Course {

	private int courseid;
	private Timestamp date;
	private String type;
	private String title;
	private String description;
	private String formation;
	private String name;
	private String firstname;
	private String ipaddress;
	private int duration;
	private String genre;
	private boolean visible;
	private int consultations;
	private String timing;
	
	/**
	 * Default constructor
	 */
	public Course() {
	}
	
	/**
	 * constructor : creates a course by providing all the information
	 * 
	 * @param courseid
	 * @param date
	 * @param type
	 * @param title
	 * @param description
	 * @param formation
	 * @param name
	 * @param firstname
	 * @param ipaddress
	 * @param duration
	 * @param genre
	 * @param visible
	 * @param consultations
	 * @param timing
	 */
	public Course(int courseid, Timestamp date, String type, String title, String description, String formation, String name, String firstname, String ipaddress, int duration, String genre, boolean visible, int consultations, String timing) {
		this.courseid = courseid;
		this.date = date;
		this.type = type;
		this.title = title;
		this.description = description;
		this.formation = formation;
		this.name = name;
		this.firstname = firstname;
		this.ipaddress = ipaddress;
		this.duration = duration;
		this.genre = genre;
		this.visible = visible;
		this.consultations = consultations;
		this.timing = timing;
	}
	
	/**
	 * Creates a copy of the object passed in parameter
	 * @param c
	 */
	/*public Course(Course c) {
		this.courseid = c.getCourseid();
		this.date = c.getDate();
		this.type = c.getType();
		this.title = c.getTitle();
		this.description = c.getDescription();
		this.formation = c.getFormation();
		this.name = c.getName();
		this.firstname = c.getFirstname();
		this.ipaddress = c.getIpaddress();
		this.duration = c.getDuration();
		this.genre = c.getGenre();
		this.visible = c.isVisible();
		this.consultations = c.getConsultations();
		this.timing = c.getTiming();
	}*/
	
	/**
	 * Returns a string representation of the object
	 */
	public String toString() {
		return "[" + courseid + "," + getDateString() + "," + type + "," + title + ","
			+ description + "," + formation + "," + name + "," + firstname + ","
			+ ipaddress + "," + getDurationString() + "," + genre + "," + visible + ","
			+ consultations + "," + timing + "]";
	}

	/*public List<Slide> getSlides() {
		return new ArrayList<Slide>();
	}
	
	public Smil getSmil() {
		return new Smil();
	}*/
	
	/**
	 * @return the consultations
	 */
	public int getConsultations() {
		return consultations;
	}
	
	/**
	 * Increments the number of consultations
	 */
	/*public void addConsultation() {
		consultations++;
	}*/

	/**
	 * @return the courseid
	 */
	public int getCourseid() {
		return courseid;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}
	
	/**
	 * @return the date in the convenient String format
	 */
	public String getDateString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
		return sdf.format(date);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * @return the duration in a String
	 */
	public String getDurationString() {
		String res = "";
		int durationHour = duration / 3600;
		int durationMinute = (duration % 3600) / 60 ;
		int durationSecond = ((duration % 3600) % 60) ;
		res += durationHour > 0 ? durationHour + "h" : "";
    	res += durationHour > 0 || durationMinute > 0 ? durationMinute + "min" : "";
    	res += durationSecond + "sec";
		return res;
	}
	
	/**
	 * @return the formation
	 */
	public String getFormation() {
		return formation;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @return the ipaddress
	 */
	public String getIpaddress() {
		return ipaddress;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the timing
	 */
	public String getTiming() {
		return timing;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}
	
	
}
