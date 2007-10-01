package org.ulpmm.univrav.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;

public class DatabaseImpl implements IDatabase {

	// Singleton à virer dans cette classe ????
	
	private static PgsqlAccess pa = PgsqlAccess.getInstance();
	
	/**
	 * Adds a new course
	 * @param c the course to add
	 */
	public void addCourse(Course c) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO course values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, c.getCourseid());
			pstmt.setTimestamp(2, c.getDate());
			pstmt.setString(3, c.getType());
			pstmt.setString(4, c.getTitle());
			pstmt.setString(5, c.getDescription());
			pstmt.setString(6, c.getFormation());
			pstmt.setString(7, c.getName());
			pstmt.setString(8, c.getFirstname());
			pstmt.setString(9, c.getIpaddress());
			pstmt.setInt(10, c.getDuration());
			pstmt.setString(11, c.getGenre());
			pstmt.setBoolean(12, c.isVisible());
			pstmt.setInt(13, c.getConsultations());
			pstmt.setString(14, c.getTiming());
			pstmt.setString(15, c.getMediaFolder());
			if( pstmt.executeUpdate() == 0)
				throw new DaoException("The course " + c + " has not been added to the database");
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new Course " + c);
			sqle.printStackTrace();
		}
		
		/*pa.connect();
		String query = "INSERT INTO course values(";
		query+= c.getCourseid() + ",'";
		query+= c.getDate() + "','";
		query+= c.getType() + "','";
		query+= c.getTitle() + "','";
		query+= c.getDescription() + "','";
		query+= c.getFormation() + "','";
		query+= c.getName() + "','";
		query+= c.getFirstname() + "','";
		query+= c.getIpaddress() + "',";
		query+= c.getDuration() + ",'";
		query+= c.getGenre() + "',";
		query+= c.isVisible() + ",";
		query+= c.getConsultations() + ",'";
		query+= c.getTiming() + "')";
		if( pa.update(query) )
			throw new RuntimeException("Error while adding the new course");
		pa.disconnect();*/
	}
	
	/**
	 * Gets a list of all the courses
	 * @return the list of courses
	 */
	public List<Course> getAllCourses() {
		pa.connect();
		ResultSet rs = pa.query("SELECT * From course ORDER BY date DESC");
		List<Course> l = new ArrayList<Course>();
		try {
			while(rs.next()) {
				l.add(new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getString("mediaFolder")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
		}
		pa.disconnect();
		return l;
	}
	
	/**
	 * Gets a list of the n last courses
	 * @param n the number of courses to return
	 * @return the list of courses
	 */
	public List<Course> getNLastCourses(int n) {
		pa.connect();
		ResultSet rs = pa.query("SELECT * From course ORDER BY date DESC LIMIT " + n);
		List<Course> l = new ArrayList<Course>();
		try {
			while(rs.next()) {
				l.add(new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getString("mediaFolder")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
		}
		pa.disconnect();
		return l;
	}
	
	/**
	 * Gets a restricted list of courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @return the list of courses
	 */
	public List<Course> getCourses(int number, int start) {
		pa.connect();
		ResultSet rs = pa.query("SELECT * From course ORDER BY date DESC LIMIT " + number + " OFFSET " + start);
		List<Course> l = new ArrayList<Course>();
		try {
			while(rs.next()) {
				l.add(new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getString("mediaFolder")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
		}
		pa.disconnect();
		return l;
	}
	
	/**
	 * Gets a restricted list of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @return the list of courses
	 */
	public List<Course> getCourses(HashMap<String, String> params, int number, int start) {
			
		List<Course> l = new ArrayList<Course>();
		
		if( ! params.isEmpty() ) {
		
			Connection cnt = pa.getConnection();
			Set<String> keys = params.keySet();
			Collection<String> values = params.values();
			
			/* Creation of the SQL query string */
			String sql = "SELECT * FROM course WHERE ";
			Iterator<String> it = keys.iterator();
			while( it.hasNext()) {
				if( ! sql.endsWith("WHERE "))
					sql += "AND ";
				sql += it.next() + " = ? ";
			}
			sql += "ORDER BY date DESC LIMIT " + number + " OFFSET " + start;
			System.out.println(sql);
			try {
				PreparedStatement pstmt = cnt.prepareStatement(sql);
				
				/* Applies the parameters to the query */
				it = values.iterator();
				int i = 1;
				while( it.hasNext()) {
					pstmt.setString(i, it.next());
					i++;
				}
				
				ResultSet rs = pstmt.executeQuery();
				
				/* Retrieves the records */
				while(rs.next()) {
					l.add(new Course(
							rs.getInt("courseid"),
							rs.getTimestamp("date"),
							rs.getString("type"),
							rs.getString("title"),
							rs.getString("description"),
							rs.getString("formation"),
							rs.getString("name"),
							rs.getString("firstname"),
							rs.getString("ipaddress"),
							rs.getInt("duration"),
							rs.getString("genre"),
							rs.getBoolean("visible"),
							rs.getInt("consultations"),
							rs.getString("timing"),
							rs.getString("mediaFolder")
					));
				}
				rs.close();
			}
			catch( SQLException sqle) {
				System.out.println("Error while retrieving the courses list");
				sqle.printStackTrace();
			}
			
			pa.disconnect();
		}
		else
			l = getCourses(number, start);
		
		return l;
	}
	
	/**
	 * Gets a course by providing its id
	 * @param courseId the id of the course
	 * @return the course
	 */
	public Course getCourse(int courseId) {
		Course c = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM course WHERE courseid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				c = new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getString("mediaFolder")
				);
			}
			else
				throw new DaoException("Course " + courseId + " not found");
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course " + courseId);
			sqle.printStackTrace();
		}
		pa.disconnect();
		
		return c;
	}

	/**
	 * Gets a course by providing its id and genre
	 * @param courseId the id of the course
	 * @param genre the genre of the course
	 * @return the course
	 */
	public Course getCourse(int courseId, String genre) {
		Course c = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM course WHERE courseid = ? AND GENRE = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			pstmt.setString(2, genre);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				c = new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getString("mediaFolder")
				);
			}
			else
				throw new DaoException("Course " + courseId + " not found with this genre");
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course " + courseId);
			sqle.printStackTrace();
		}
		pa.disconnect();
		
		return c;
	}
	
	/**
	 * Gets the total number of courses
	 * @return the number of courses
	 */
	public int getCourseNumber() {
		int number = 0;
		String sql = "SELECT COUNT(*) FROM course";
		pa.connect();
		try {
			ResultSet rs = pa.query(sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the buildings list");
			sqle.printStackTrace();
		}
		pa.disconnect();
		return number;
	}
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @return the number of courses
	 */
	public int getCourseNumber(HashMap<String, String> params) {
		int number = 0;
		
		if( ! params.isEmpty() ) {
			Connection cnt = pa.getConnection();
			Set<String> keys = params.keySet();
			Collection<String> values = params.values();
			
			
			String sql = "SELECT COUNT(*) FROM course WHERE ";
			Iterator<String> it = keys.iterator();
			while( it.hasNext()) {
				if( ! sql.endsWith("WHERE "))
					sql += "AND ";
				sql += it.next() + " = ? ";
			}
			
			try {
				PreparedStatement pstmt = cnt.prepareStatement(sql);
				
				/* Applies the parameters to the query */
				it = values.iterator();
				int i = 1;
				while( it.hasNext()) {
					pstmt.setString(i, it.next());
					i++;
				}
				
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) 
					number = rs.getInt("count");
				rs.close();
			}
			catch( SQLException sqle) {
				System.out.println("Error while retrieving the buildings list");
				sqle.printStackTrace();
			}
			pa.disconnect();
		}
		else
			number = getCourseNumber();
		
		return number;
	}
	
	/**
	 * Modifies a course
	 * @param c the course to modify
	 */
	public void modifyCourse(Course c) {
		Connection cnt = pa.getConnection();
		
		/* Creation of the SQL query string */
		String sql = "UPDATE course SET date = ? , type = ? , title = ? , description = ? , ";
		sql += "formation = ? , name = ? , firstname = ? , ipaddress = ? , duration = ? , ";
		sql += "genre = ? , visible = ? , consultations = ? , timing = ? WHERE courseid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setTimestamp(1, c.getDate());
			pstmt.setString(2, c.getType());
			pstmt.setString(3, c.getTitle());
			pstmt.setString(4, c.getDescription());
			pstmt.setString(5, c.getFormation());
			pstmt.setString(6, c.getName());
			pstmt.setString(7, c.getFirstname());
			pstmt.setString(8, c.getIpaddress());
			pstmt.setInt(9, c.getDuration());
			pstmt.setString(10, c.getGenre());
			pstmt.setBoolean(11, c.isVisible());
			pstmt.setInt(12, c.getConsultations());
			pstmt.setString(13, c.getTiming());
			pstmt.setInt(14, c.getCourseid());
			
			if( pstmt.executeUpdate() == 0 )
				throw new DaoException("The course " + c + " has not been modified");
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the course " + c);
			sqle.printStackTrace();
		}
		
		pa.disconnect();
	}
	
	/**
	 * Deletes a course by providing its id
	 * @param courseId the id of the course
	 */
	public void deleteCourse(int courseId) {
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM course WHERE courseid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			if( pstmt.executeUpdate() == 0)
				throw new DaoException("the course " + courseId + " has not been deleted");
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the course " + courseId);
			sqle.printStackTrace();
		}
		pa.disconnect();
	}
	
	/**
	 * Gets the id of the next course which will be uploaded
	 * @return the id of the course
	 */
	public int getNextCoursId() {
		int id = 0 ;
		
		pa.connect();
		ResultSet rs = pa.query("SELECT nextval('course_courseid_seq')");
		try {
			if( rs.next() ) 
				id = rs.getInt("nextval");
			else {
				throw new DaoException("The next course Id hasn't been retrieved");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the next course Id");
			sqle.printStackTrace();
		}
		
		return id;
	}
	
	/**
	 * Gets the list of all the teachers
	 * @return the list of teachers
	 */
	public List<String[]> getTeachers() {
		List<String[]> l = new ArrayList<String[]>();
		
		pa.connect();
		ResultSet rs = pa.query("select distinct name, firstname from course");
		try {
			while( rs.next() ) {
				String[] t = new String[2];
				t[0]= rs.getString("name");
				t[1]= rs.getString("firstname");
				l.add(t);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the teachers list");
			sqle.printStackTrace();
		}
		
		return l;
	}
	
	/**
	 * Gets the list of all the formations
	 * @return the list of formations
	 */
	public List<String> getFormations() {
		List<String> l = new ArrayList<String>();
		
		pa.connect();
		ResultSet rs = pa.query("select distinct formation from course");
		try {
			while( rs.next() ) {
				l.add(rs.getString("formation"));
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the formations list");
			sqle.printStackTrace();
		}
		
		return l;
	}
	
	
	/**
	 * Adds a new slide
	 * @param s the slide to add
	 */
	public void addSlide(Slide s) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO Slide(courseid, slideuri, slidetime) values(?,?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, s.getCourseid());
			pstmt.setString(2, s.getSlideuri());
			pstmt.setInt(3, s.getSlidetime());
			if( pstmt.executeUpdate() == 0 )
				throw new DaoException("The slide " + s + " has not been added");
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the Slide " + s);
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the slides of a course
	 * @param courseId the id of the course
	 * @return the list of slides
	 */
	public List<Slide> getSlides(int courseId) {
		Connection cnt = pa.getConnection();
		List<Slide> l = new ArrayList<Slide>();
		String sql = "SELECT * FROM slide WHERE courseid = ? ORDER BY slidetime";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				l.add(new Slide(
					rs.getInt("courseid"),
					rs.getString("slideuri"),
					rs.getInt("slidetime")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the slides list");
			sqle.printStackTrace();
		}
		pa.disconnect();
		return l;
	}
	
	/**
	 * Deletes the slides of a course
	 * @param courseId the id of the course
	 */
	public void deleteSlide(int courseId) {
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM slide WHERE courseid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			if( pstmt.executeUpdate() == 0 )
				throw new DaoException("The Slides of the course " + courseId + " have not been deleted");
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the slide of the course " + courseId);
			sqle.printStackTrace();
		}
		pa.disconnect();
	}
	
	
	/**
	 * Gets the list of the buildings
	 * @return the list of buildings
	 */
	public List<Building> getBuildings() {
		pa.connect();
		List<Building> l = new ArrayList<Building>();
		String sql = "SELECT * FROM building";
		
		try {
			ResultSet rs = pa.query(sql);
			
			while(rs.next()) {
				Building b = new Building(
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("imagefile")
				);
				b.setAmphis(getAmphis(b.getBuildingid()));
				l.add(b);
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the buildings list");
			sqle.printStackTrace();
		}
		pa.disconnect();
		return l;
	}
	
	/**
	 * Adds a new Amphi
	 * @param a the amphi to add
	 */
	public void addAmphi(Amphi a) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Gets a list of all the amphis
	 * @return the list of amphis
	 */
	public List<Amphi> getAmphis(int buildingId) {
		Connection cnt = pa.getConnection();
		List<Amphi> l = new ArrayList<Amphi>();
		String sql = "SELECT * FROM amphi WHERE buildingid = ? ORDER BY name";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, buildingId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				l.add(new Amphi(
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("type"),
					rs.getString("ipaddress"),
					rs.getBoolean("status")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the amphis list");
			sqle.printStackTrace();
		}
		pa.disconnect();
		return l;
	}

	/**
	 * Gets an amphi by providing its IP address
	 * @param ip the IP address of the amphi
	 * @return the amphi
	 */
	public Amphi getAmphi(String ip) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Modifies an amphi
	 * @param a the amphi to modify
	 */
	public void modifyAmphi(Amphi a) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Deletes an amphi by providing its id
	 * @param id the id of the amphi
	 */
	public void deleteAmphi(String ip) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	/* A supprimer si plus besoin !!!!!!! */
	public void modifyCourse(int courseId, HashMap<String, String> params) {
		Connection cnt = pa.getConnection();	
		Set<String> keys = params.keySet();
		Collection<String> values = params.values();
		
		/* Creation of the SQL query string */
		String sql = "UPDATE course SET ";
		Iterator<String> it = keys.iterator();
		while( it.hasNext()) {
			if( ! sql.endsWith("SET "))
				sql += ", ";
			sql += it.next() + " = ? ";
		}
		sql += "WHERE courseid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			it = values.iterator();
			int i = 1;
			while( it.hasNext()) {
				pstmt.setString(i, it.next());
				i++;
			}
			
			pstmt.setInt(i, courseId);
			pstmt.executeUpdate();
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the course");
			sqle.printStackTrace();
		}
		
		pa.disconnect();
	}
}