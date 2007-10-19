package org.ulpmm.univrav.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Univr;

public class DatabaseImpl implements IDatabase {
	
	private static PgsqlAccess pa;
	
	public DatabaseImpl(String host, String port, String database, String user, String password) {
		pa = new PgsqlAccess(host, port, database, user, password);
	}
	
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
			
			if( c.getType() != null)
				pstmt.setString(3, c.getType());
			else
				pstmt.setNull(3, Types.VARCHAR);
			
			if( c.getTitle() != null)
				pstmt.setString(4, c.getTitle());
			else
				pstmt.setNull(4, Types.VARCHAR);
			
			if( c.getDescription() != null)
				pstmt.setString(5, c.getDescription());
			else
				pstmt.setNull(5, Types.VARCHAR);
			
			if( c.getFormation() != null)
				pstmt.setString(6, c.getFormation());
			else
				pstmt.setNull(6, Types.VARCHAR);
			
			if( c.getName() != null)
				pstmt.setString(7, c.getName());
			else
				pstmt.setNull(7, Types.VARCHAR);
			
			if( c.getFirstname() != null)
				pstmt.setString(8, c.getFirstname());
			else
				pstmt.setNull(8, Types.VARCHAR);
			
			pstmt.setString(9, c.getIpaddress());
			pstmt.setInt(10, c.getDuration());
			
			if( c.getGenre() != null)
				pstmt.setString(11, c.getGenre());
			else
				pstmt.setNull(11, Types.VARCHAR);
			
			pstmt.setBoolean(12, c.isVisible());
			pstmt.setInt(13, c.getConsultations());
			
			if( c.getTiming() != null)
				pstmt.setString(14, c.getTiming());
			else
				pstmt.setNull(14, Types.VARCHAR);
			
			if( c.getMediaFolder() != null)
				pstmt.setString(15, c.getMediaFolder());
			else
				pstmt.setNull(15, Types.VARCHAR);
				
			if( pstmt.executeUpdate() == 0)
				throw new DaoException("The course " + c + " has not been added to the database");
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new Course " + c);
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Adds a new Univ-R course
	 * @param u the Univ-R course
	 */
	public void addUnivr(Univr u) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO univr values(?,?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, u.getCourseid());
			pstmt.setInt(2, u.getUid());
			pstmt.setInt(3, u.getGroupCode());
			
			if( pstmt.executeUpdate() == 0)
				throw new DaoException("The Univr course " + u + " has not been added to the database");
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new Univr course " + u);
			sqle.printStackTrace();
		}
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
					rs.getString("mediafolder")
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
	 * Gets a list of all the courses without an access code
	 * @return the list of courses
	 */
	public List<Course> getAllUnlockedCourses() {
		pa.connect();
		ResultSet rs = pa.query("SELECT * From course WHERE genre IS NULL AND visible = true ORDER BY date DESC");
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
					rs.getString("mediafolder")
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
		ResultSet rs = pa.query("SELECT * From course WHERE genre IS NULL AND visible = true ORDER BY date DESC LIMIT " + n);
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
					rs.getString("mediafolder")
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
		ResultSet rs = pa.query("SELECT * From course WHERE visible = true ORDER BY date DESC LIMIT " + number + " OFFSET " + start);
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
					rs.getString("mediafolder")
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
			
			/* Creation of the SQL query string */
			String sql = "SELECT * FROM course WHERE ";
			Iterator<String> it = keys.iterator();
			while( it.hasNext()) {
				if( ! sql.endsWith("WHERE "))
					sql += "AND ";
				
				String param = it.next();
				if( param.equals("fullname") )
					sql += "name || ' ' || firstname = ? ";
				else if( param.equals("keyword") )
					sql += "(INITCAP(title) LIKE INITCAP(?) OR INITCAP(description) LIKE INITCAP(?)) ";
				else
					sql += param + " = ? ";
			}
			sql += "AND visible = true ORDER BY date DESC LIMIT " + number + " OFFSET " + start;
			System.out.println(sql);
			try {
				PreparedStatement pstmt = cnt.prepareStatement(sql);

				/* Applies the parameters to the query */
				it = keys.iterator();
				int i = 1;
				while( it.hasNext()) {
					String param = it.next();
					if( param.equals("keyword") ) {
						pstmt.setString(i, "%" + params.get(param) + "%");
						i++;
						pstmt.setString(i, "%" + params.get(param) + "%");
					}
					else
						pstmt.setString(i, params.get(param));
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
							rs.getString("mediafolder")
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
	 * Gets the list of courses without access code for a teacher
	 * @param teacher the teacher
	 * @return the list of courses
	 */
	public List<Course> getUnlockedCourses(String[] teacher) {
		
		List<Course> l = new ArrayList<Course>();
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM course WHERE" +
				(teacher[0] != null ? " name = ?" : "") +
				(teacher[0] != null && teacher[1] != null ? "AND" : "") + 
				(teacher[1] != null ? " firstname = ? " : "") +
				"AND genre IS NULL AND visible = true";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			if( teacher[0] != null)
				pstmt.setString(1, teacher[0]);
			else if( teacher[1] != null)
				pstmt.setString(1, teacher[1]);
			if( teacher[0] != null && teacher[1] != null)
				pstmt.setString(2, teacher[1]);
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
						rs.getString("mediafolder")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list for the teacher " 
					+ teacher[0] + " " + teacher[1]);
			sqle.printStackTrace();
		}
		
		pa.disconnect();
		
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
					rs.getString("mediafolder")
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
		String sql = "SELECT * FROM course WHERE courseid = ? AND GENRE = ? AND visible = true";
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
					rs.getString("mediafolder")
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
		String sql = "SELECT COUNT(*) FROM course WHERE visible = true";
		pa.connect();
		try {
			ResultSet rs = pa.query(sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course number");
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
			
			
			String sql = "SELECT COUNT(*) FROM course WHERE ";
			Iterator<String> it = keys.iterator();
			while( it.hasNext()) {
				if( ! sql.endsWith("WHERE "))
					sql += "AND ";
				
				String param = it.next();
				if( param.equals("fullname") )
					sql += "name || ' ' || firstname = ? ";
				else if( param.equals("keyword") )
					sql += "(INITCAP(title) LIKE INITCAP(?) OR INITCAP(description) LIKE INITCAP(?)) ";
				else
					sql += param + " = ? ";
			}
			
			try {
				PreparedStatement pstmt = cnt.prepareStatement(sql);
				
				/* Applies the parameters to the query */
				it = keys.iterator();
				int i = 1;
				while( it.hasNext()) {
					String param = it.next();
					if( param.equals("keyword") ) {
						pstmt.setString(i, "%" + params.get(param) + "%");
						i++;
						pstmt.setString(i, "%" + params.get(param) + "%");
					}
					else
						pstmt.setString(i, params.get(param));
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
		sql += "genre = ? , visible = ? , consultations = ? , timing = ?, mediafolder = ? ";
		sql += "WHERE courseid = ?";
		
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
			pstmt.setString(14, c.getMediaFolder());
			pstmt.setInt(15, c.getCourseid());
			
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
	 * Gets the list of the media folders of the test courses
	 * @return the list of media folders
	 */
	public List<String> getTestsMediaFolders() {
		pa.connect();
		ResultSet rs = pa.query("SELECT mediafolder FROM course WHERE initcap(genre) = 'Suppression'");
		List<String> l = new ArrayList<String>();
		try {
			while(rs.next()) {
				l.add(rs.getString("mediafolder"));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the media folders list");
			sqle.printStackTrace();
		}
		pa.disconnect();
		return l;
	}
	
	/**
	 * Deletes the test courses (courses with genre 'Suppression')
	 */
	public void deleteTests() {
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM course WHERE initcap(genre) = 'Suppression'";
		try {
			Statement stmt = cnt.createStatement();
			stmt.executeUpdate(sql);
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the tests");
			sqle.printStackTrace();
		}
		pa.disconnect();
	}
	
	/**
	 * Hides the test courses (courses beginning with 'test' or 'essai')
	 */
	public void hideTests() {
		Connection cnt = pa.getConnection();
		String sql = "UPDATE course SET visible=false " +
				"WHERE INITCAP(title) LIKE 'Test%'" +
				"OR INITCAP(title) LIKE 'Essai%'";
		try {
			Statement stmt = cnt.createStatement();
			stmt.executeUpdate(sql);
		}
		catch( SQLException sqle) {
			System.out.println("Error while hiding the tests");
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
		ResultSet rs = pa.query("SELECT DISTINCT name, firstname FROM course WHERE visible = true AND NOT (name IS NULL AND firstname IS NULL)");
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
	 * Gets the list of all the teachers who have at least one course with no access code
	 * @return the list of teachers
	 */
	public List<String[]> getTeachersWithRss() {
		List<String[]> l = new ArrayList<String[]>();
		
		pa.connect();
		ResultSet rs = pa.query("SELECT DISTINCT name, firstname FROM course WHERE genre IS NULL AND title IS NOT NULL AND visible = true AND NOT (name IS NULL AND firstname IS NULL)");
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
		ResultSet rs = pa.query("SELECT DISTINCT formation from course WHERE visible = true AND formation IS NOT NULL");
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
	 * Increments the number of consultations for a course
	 * @param c the course
	 */
	public void incrementConsultations(Course c) {
		int consultations = c.getConsultations() + 1;
		
		Connection cnt = pa.getConnection();
		String sql = "UPDATE course SET consultations = ? WHERE courseid = ? ";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, consultations);
			pstmt.setInt(2, c.getCourseid());
			if( pstmt.executeUpdate() == 0 )
				throw new DaoException("The consultations have not been incremented");
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while incrementing the consultations");
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Adds a new slide
	 * @param s the slide to add
	 */
	public void addSlide(Slide s) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO Slide(courseid, slidetime) values(?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, s.getCourseid());
			pstmt.setInt(2, s.getSlidetime());
			if( pstmt.executeUpdate() == 0 )
				throw new DaoException("The slide has not been added");
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the Slide");
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
	 * Adds a new building
	 * @param b the building to add
	 */
	public void addBuilding(Building b) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO building(name, imagefile) values(?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, b.getName());
			pstmt.setString(2, b.getImageFile());
			
			if( pstmt.executeUpdate() == 0)
				throw new DaoException("The building " + b + " has not been added to the database");
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new Building " + b);
			sqle.printStackTrace();
		}
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
	 * Gets a building by providing its id
	 * @param buildingId the id of the building
	 * @return the building
	 */
	public Building getBuilding(int buildingId) {
		Building b = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM building WHERE buildingid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, buildingId);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				b = new Building(
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("imagefile")
				);
			}
			else
				throw new DaoException("Building " + buildingId + " not found");
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the building " + buildingId);
			sqle.printStackTrace();
		}
		pa.disconnect();
		
		return b;
	}
	
	/**
	 * Gets a building name by providing one of its amphis IP address
	 * @param amphiIp the amphi IP address
	 * @return the building name
	 */
	public String getBuildingName(String amphiIp) {
		String name = "";
		Connection cnt = pa.getConnection();
		String sql = "SELECT name FROM building WHERE buildingid = ( SELECT buildingid FROM amphi WHERE ipaddress = ? )";

		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, amphiIp);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				name = rs.getString("name");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the building of amphi " + amphiIp);
			sqle.printStackTrace();
		}
		pa.disconnect();
		
		return name;
	}
	
	/**
	 * Modifies a building
	 * @param b the building to modify
	 */
	public void modifyBuilding(Building b) {
		Connection cnt = pa.getConnection();
		
		/* Creation of the SQL query string */
		String sql = "UPDATE building SET name = ? , imagefile = ? ";
		sql += "WHERE buildingid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setString(1, b.getName());
			pstmt.setString(2, b.getImageFile());
			pstmt.setInt(3, b.getBuildingid());
			
			if( pstmt.executeUpdate() == 0 )
				throw new DaoException("The building " + b + " has not been modified");
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the building " + b);
			sqle.printStackTrace();
		}
		
		pa.disconnect();
	}
	
	/**
	 * Deletes a building
	 * @param buildingId the id of the building
	 */
	public void deleteBuilding(int buildingId) {
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM building WHERE buildingid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, buildingId);
			if( pstmt.executeUpdate() == 0)
				throw new DaoException("the building " + buildingId + " has not been deleted");
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the course " + buildingId);
			sqle.printStackTrace();
		}
		pa.disconnect();
	}
	
	/**
	 * Adds a new Amphi
	 * @param a the amphi to add
	 */
	public void addAmphi(Amphi a) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO amphi(buildingid, name, type, ipaddress, status) values(?,?,?,?,?)";
		System.out.println(sql);
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, a.getBuildingid());
			pstmt.setString(2, a.getName());
			pstmt.setString(3, a.getType());
			pstmt.setString(4, a.getIpAddress());
			pstmt.setBoolean(5, false);
			
			if( pstmt.executeUpdate() == 0)
				throw new DaoException("The amphi " + a + " has not been added to the database");
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new amphi " + a);
			sqle.printStackTrace();
		}
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
					rs.getInt("amphiid"),
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
	public Amphi getAmphi(int amphiId) {
		Amphi a = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM amphi WHERE amphiid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, amphiId);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				a = new Amphi(
					rs.getInt("amphiid"),
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("type"),
					rs.getString("ipaddress"),
					rs.getBoolean("status")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the amphi " + amphiId);
			sqle.printStackTrace();
		}
		pa.disconnect();
		
		return a;
	}	
	
	/**
	 * Gets an amphi by providing its IP address
	 * @param ip the IP address of the amphi
	 * @return the amphi
	 */
	public Amphi getAmphi(String ip) {
		Amphi a = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM amphi WHERE ipaddress = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, ip);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				a = new Amphi(
					rs.getInt("amphiid"),
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("type"),
					rs.getString("ipaddress"),
					rs.getBoolean("status")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the amphi " + ip);
			sqle.printStackTrace();
		}
		pa.disconnect();
		
		return a;
	}
	
	/**
	 * Modifies an amphi
	 * @param a the amphi to modify
	 */
	public void modifyAmphi(Amphi a) {
		Connection cnt = pa.getConnection();
		
		/* Creation of the SQL query string */
		String sql = "UPDATE amphi SET buildingid = ?, name = ? , type = ?, ";
		sql += "ipaddress = ?, status = ? WHERE amphiid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setInt(1, a.getBuildingid());
			pstmt.setString(2, a.getName());
			pstmt.setString(3, a.getType());
			pstmt.setString(4, a.getIpAddress());
			pstmt.setBoolean(5, a.isStatus());
			pstmt.setInt(6, a.getAmphiid());
			
			if( pstmt.executeUpdate() == 0 )
				throw new DaoException("The amphi " + a + " has not been modified");
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the amphi " + a);
			sqle.printStackTrace();
		}
		
		pa.disconnect();
	}

	/**
	 * Deletes an amphi by providing its id
	 * @param amphiId the id of the amphi
	 */
	public void deleteAmphi(int amphiId) {
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM amphi WHERE amphiid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, amphiId);
			if( pstmt.executeUpdate() == 0)
				throw new DaoException("the amphi " + amphiId + " has not been deleted");
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the amphi " + amphiId);
			sqle.printStackTrace();
		}
		pa.disconnect();
	}
	
	/**
	 * Sets the status of the live in an amphi
	 * @param ip the IP address of the amphi
	 * @param status the status od the live in the amphi
	 */
	public void setAmphiStatus(String ip, boolean status) {
		Connection cnt = pa.getConnection();
		String sql = "UPDATE amphi SET status = ? WHERE ipaddress = ? ";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setBoolean(1, status);
			pstmt.setString(2, ip);
			if( pstmt.executeUpdate() == 0)
				throw new DaoException("The status of the amphi has not been changed");
			
		}
		catch( SQLException sqle) {
			System.out.println("Error while updating the status of the amphi " + ip);
			sqle.printStackTrace();
		}
		pa.disconnect();
	}
	
}