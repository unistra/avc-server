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

import javax.sql.DataSource;

import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Selection;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Tag;
import org.ulpmm.univrav.entities.Teacher;
import org.ulpmm.univrav.entities.Univr;
import org.ulpmm.univrav.entities.User;

public class DatabaseImpl implements IDatabase {
	
	private static PgsqlAccess pa;
	
	public DatabaseImpl(DataSource ds) {
		pa = new PgsqlAccess(ds);
	}
	
	/**
	 * Adds a new course
	 * @param c the course to add
	 */
	public void addCourse(Course c) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO course values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
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
			
			pstmt.setBoolean(16, c.isHighquality());
			
			if(c.getUserid() !=null)
				pstmt.setInt(17, c.getUserid());
			else
				pstmt.setNull(17, Types.INTEGER);
			
			if( pstmt.executeUpdate() == 0) {
				System.out.println("The course " + c + " has not been added to the database");
				throw new DaoException("The course " + c + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new Course " + c);
			sqle.printStackTrace();
			throw new DaoException("Error while adding the new Course " + c);
		}
	}
	
	/**
	 * Adds a new Univ-R course
	 * @param u the Univ-R course
	 */
	public void addUnivr(Univr u) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO univr values(?,?,?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, u.getCourseid());
			pstmt.setInt(2, u.getUid());
			pstmt.setInt(3, u.getGroupCode());
			pstmt.setString(4, u.getEstablishment());
			
			if( pstmt.executeUpdate() == 0) {
				System.out.println("The Univr course " + u + " has not been added to the database");
				throw new DaoException("The Univr course " + u + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new Univr course " + u);
			sqle.printStackTrace();
			throw new DaoException("Error while adding the new Univr course " + u);
		}
	}
	
	/**
	 * Gets a list of all the courses (non-Univr)
	 * @return the list of courses
	 */
	public List<Course> getAllCourses() {
		
		ResultSet rs = pa.query("SELECT * From course WHERE courseid NOT IN " +
				"( SELECT courseid FROM univr ) ORDER BY date DESC");
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list");
		}
		
		return l;
	}
	
	/**
	 * Gets a list of all the Univ-R courses
	 * @return the list of Univ-R courses
	 */
	public List<Course> getUnivrCourses() {
		
		ResultSet rs = pa.query("SELECT * From course WHERE courseid IN " +
				"( SELECT courseid FROM univr ) ORDER BY date DESC");
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list");
		}
		
		return l;
	}
	
	/**
	 * Gets a list of all the courses without access code
	 * @return the list of courses
	 */
	public List<Course> getAllUnlockedCourses() {
		
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list");
		}

		return l;
	}
	
	/**
	 * Gets a list of the n last courses
	 * @param n the number of courses to return
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getNLastCourses(int n, String testKeyWord1, String testKeyWord2) {
		
		String sql = "SELECT * From course WHERE genre IS NULL AND visible = true " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord1 + "%' ";
		
		if( testKeyWord2 != null && ! testKeyWord2.equals(""))
			sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
		
		sql += "ORDER BY date DESC, courseid DESC LIMIT " + n;
		
		ResultSet rs = pa.query(sql);
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list");
		}
		
		return l;
	}
	
	/**
	 * Gets a restricted list of courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getCourses(int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		
		String sql = "SELECT * From course WHERE visible = true " +
			"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
			
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
			
		sql += "ORDER BY date DESC, courseid DESC LIMIT " + number + " OFFSET " + start;

		ResultSet rs = pa.query(sql);
		
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list");
		}
		
		return l;
	}
	
	/**
	 * Gets a restricted list of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getCourses(HashMap<String, String> params, int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
			
		List<Course> l = new ArrayList<Course>();
		
		if( ! params.isEmpty() ) {
		
			Connection cnt = pa.getConnection();
			Set<String> keys = params.keySet();
			
			/* Creation of the SQL query string */
			String sql = "SELECT * FROM course WHERE visible = true " +
				"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
				"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
			
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
			
			Iterator<String> it = keys.iterator();
			while( it.hasNext()) {
				if( ! sql.endsWith("WHERE "))
					sql += "";
				
				String param = it.next();
				if( param.equals("fullname") )
					sql += "AND (COALESCE(INITCAP(name),'') || COALESCE(INITCAP(' ' || firstname),'')) = ? ";
				else if( param.equals("keyword") )
					sql += "AND (INITCAP(title) LIKE INITCAP(?) OR INITCAP(description) LIKE INITCAP(?) OR INITCAP(name) LIKE INITCAP(?) OR INITCAP(firstname) LIKE INITCAP(?) OR INITCAP(formation) LIKE INITCAP(?) OR courseid IN (select courseid from tag where INITCAP(tag) LIKE INITCAP(?))) ";
				else
					sql += "AND " + param + " = ? ";
			}
			sql += "ORDER BY date DESC, courseid DESC LIMIT " + number + " OFFSET " + start;

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
						i++;
						pstmt.setString(i, "%" + params.get(param) + "%");
						i++;
						pstmt.setString(i, "%" + params.get(param) + "%");
						i++;
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
							rs.getString("mediafolder"),
							rs.getBoolean("highquality"),
							rs.getInt("userid")
					));
				}
				rs.close();
			}
			catch( SQLException sqle) {
				System.out.println("Error while retrieving the courses list");
				sqle.printStackTrace();
				throw new DaoException("Error while retrieving the courses list");
			}
			
		}
		else
			l = getCourses(number, start, testKeyWord1, testKeyWord2, testKeyWord3);
		
		return l;
	}
	

	
	/**
	 * Gets the list of courses for an author
	 * @param author the author
	 * @return the list of courses
	 */
	public List<Course> getCoursesByAuthor(String author) {
		
		List<Course> l = new ArrayList<Course>();
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM course WHERE " +
				"(COALESCE(INITCAP(name),'') || COALESCE(INITCAP(' ' || firstname),'')) = INITCAP(?) " +
				"AND visible = true ORDER BY date DESC";
			
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			pstmt.setString(1, author);
			
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
						rs.getString("mediafolder"),
						rs.getBoolean("highquality"),
						rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list for the author " + author);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list for the author " + author);
		}
		
		return l;
	}
	
	/**
	 * Gets the list of courses for a formation
	 * @param formation the formation
	 * @return the list of courses
	 */
	public List<Course> getCoursesByFormation(String formation) {
		
		List<Course> l = new ArrayList<Course>();
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM course WHERE " +
				"formation=?" + "AND visible = true ORDER BY date DESC";
					
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			pstmt.setString(1, formation);
			
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
						rs.getString("mediafolder"),
						rs.getBoolean("highquality"),
						rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list for the formation " + formation);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list for the formation " + formation);
		}
		
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				);
			}
			else
				throw new DaoException("Course " + courseId + " not found");
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course " + courseId);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the course " + courseId);
		}
		
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				);
			}
			else
				throw new DaoException("Course " + courseId + " not found with this genre");
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course " + courseId);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the course " + courseId);
		}
		
		return c;
	}
	
	/**
	 * Gets the total number of courses without test keywords
	 * @param testkeywords
	 * @return the number of courses
	 */
	public int getCourseNumber(String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		int number = 0;			
		String sql = "SELECT COUNT(*) From course WHERE visible = true " +
		"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
		"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
		
		if( testKeyWord3 != null && ! testKeyWord3.equals(""))
			sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
		
		
		try {
			ResultSet rs = pa.query(sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course number");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the course number");
		}

		return number;
	}
	
	/**
	 * Gets the total number of courses
	 * @return the number of courses
	 */
	public int getCourseNumber() {
		int number = 0;
		String sql = "SELECT COUNT(*) FROM course WHERE visible = true";
		
		try {
			ResultSet rs = pa.query(sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course number");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the course number");
		}

		return number;
	}
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @return the number of courses
	 */
	public int getCourseNumber(HashMap<String, String> params,String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		int number = 0;
		
		if( ! params.isEmpty() ) {
			Connection cnt = pa.getConnection();
			Set<String> keys = params.keySet();
									
			String sql = "SELECT COUNT(*) From course WHERE visible = true " +
			"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
			
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
			
						
			Iterator<String> it = keys.iterator();
			while( it.hasNext()) {
				if( ! sql.endsWith("WHERE "))
					sql += "AND ";
				
				String param = it.next();
				if( param.equals("fullname") )
					sql += "(COALESCE(INITCAP(name),'') || COALESCE(INITCAP(' ' || firstname),'')) = ? ";
				else if( param.equals("keyword") )
					sql += "(INITCAP(title) LIKE INITCAP(?) OR INITCAP(description) LIKE INITCAP(?) OR INITCAP(name) LIKE INITCAP(?) OR INITCAP(firstname) LIKE INITCAP(?)OR INITCAP(formation) LIKE INITCAP(?) OR courseid IN (select courseid from tag where INITCAP(tag) LIKE INITCAP(?))) ";
				else
					sql += param + " = ? ";
			}
			sql += "AND visible = true";
			
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
						i++;
						pstmt.setString(i, "%" + params.get(param) + "%");
						i++;
						pstmt.setString(i, "%" + params.get(param) + "%");
						i++;
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
				throw new DaoException("Error while retrieving the buildings list");
			}
		}
		else
			number = getCourseNumber(testKeyWord1, testKeyWord2, testKeyWord3);
		
		return number;
	}
	
	/**
	 * Gets a Univr course by providing its id
	 * @param courseId the id of the Univr course
	 * @return the Univr object
	 */
	public Univr getUnivr(int courseId) {
		Univr u = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM univr WHERE courseid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				u = new Univr(
					rs.getInt("courseid"),
					rs.getInt("uid"),
					rs.getInt("groupcode"),
					rs.getString("establishment")
				);
			}
			else
				throw new DaoException("Univr course " + courseId + " not found");
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the Univr course " + courseId);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the Univr course " + courseId);
		}
		
		return u;
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
		sql += "genre = ? , visible = ? , consultations = ? , timing = ?, mediafolder = ?, highquality = ?, userid = ? ";
		sql += "WHERE courseid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setTimestamp(1, c.getDate());
			
			if( c.getType() != null)
				pstmt.setString(2, c.getType());
			else
				pstmt.setNull(2, Types.VARCHAR);
			
			if( c.getTitle() != null)
				pstmt.setString(3, c.getTitle());
			else
				pstmt.setNull(3, Types.VARCHAR);
			
			if( c.getDescription() != null)
				pstmt.setString(4, c.getDescription());
			else
				pstmt.setNull(4, Types.VARCHAR);
			
			if( c.getFormation() != null)
				pstmt.setString(5, c.getFormation());
			else
				pstmt.setNull(5, Types.VARCHAR);
			
			if( c.getName() != null)
				pstmt.setString(6, c.getName());
			else
				pstmt.setNull(6, Types.VARCHAR);
			
			if( c.getFirstname() != null)
				pstmt.setString(7, c.getFirstname());
			else
				pstmt.setNull(7, Types.VARCHAR);
			
			pstmt.setString(8, c.getIpaddress());
			pstmt.setInt(9, c.getDuration());
			
			if( c.getGenre() != null)
				pstmt.setString(10, c.getGenre());
			else
				pstmt.setNull(10, Types.VARCHAR);
			
			pstmt.setBoolean(11, c.isVisible());
			pstmt.setInt(12, c.getConsultations());
			
			if( c.getTiming() != null)
				pstmt.setString(13, c.getTiming());
			else
				pstmt.setNull(13, Types.VARCHAR);
			
			if( c.getMediaFolder() != null)
				pstmt.setString(14, c.getMediaFolder());
			else
				pstmt.setNull(14, Types.VARCHAR);
			
			pstmt.setBoolean(15, c.isHighquality());
			
			if(c.getUserid() !=null)
				pstmt.setInt(16, c.getUserid());
			else
				pstmt.setNull(16, Types.INTEGER);
						
			pstmt.setInt(17, c.getCourseid());
			
			if( pstmt.executeUpdate() == 0 ) {
				System.out.println("The course " + c + " has not been modified");
				throw new DaoException("The course " + c + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the course " + c);
			sqle.printStackTrace();
			throw new DaoException("Error while modifying the course " + c);
		}
		
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
			if( pstmt.executeUpdate() == 0) {
				System.out.println("the course " + courseId + " has not been deleted");
				throw new DaoException("the course " + courseId + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the course " + courseId);
			sqle.printStackTrace();
			throw new DaoException("Error while deleting the course " + courseId);
		}
	}
	
	/**
	 * Deletes a univr by providing its id
	 * @param courseId the id of the course
	 */
	public void deleteUnivr(int courseId) {
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM univr WHERE courseid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			if( pstmt.executeUpdate() == 0) {
				System.out.println("the course " + courseId + " has not been deleted");
				throw new DaoException("the course " + courseId + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the course " + courseId);
			sqle.printStackTrace();
			throw new DaoException("Error while deleting the course " + courseId);
		}
	}
	
	/**
	 * Gets the list of the media folders of the test courses
	 * @return the list of media folders
	 * @param testKeyWord the key word which identifies a test
	 */
	public List<String> getTestsMediaFolders(String testKeyWord) {
		
		ResultSet rs = pa.query("SELECT mediafolder FROM course WHERE initcap(genre) = '" + testKeyWord + "'");
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
			throw new DaoException("Error while retrieving the media folders list");
		}
		
		return l;
	}
	
	/**
	 * Gets a restricted list of test courses
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getTests(int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		
		String sql = "SELECT * From course " +
			"WHERE INITCAP(genre) = '" + testKeyWord1 + "' " +
			"OR INITCAP(title) LIKE '" + testKeyWord2 + "%' ";
			
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "OR INITCAP(title) LIKE '" + testKeyWord3 + "%' ";
			
		sql += "ORDER BY date DESC, courseid DESC LIMIT " + number + " OFFSET " + start;

		ResultSet rs = pa.query(sql);
		
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list");
		}
		
		return l;
	}
	
	/**
	 * Gets the total number of tests with test keywords
	 * @param testkeywords
	 * @return the number of courses
	 */
	public int getTestNumber(String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		int number = 0;			
		String sql = "SELECT COUNT(*) From course " +
		"WHERE INITCAP(genre) = '" + testKeyWord1 + "' " +
		"OR INITCAP(title) LIKE '" + testKeyWord2 + "%' ";
		
		if( testKeyWord3 != null && ! testKeyWord3.equals(""))
			sql += "OR INITCAP(title) LIKE '" + testKeyWord3 + "%' ";
		
		
		try {
			ResultSet rs = pa.query(sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course number");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the course number");
		}

		return number;
	}
	
	
	
	/**
	 * Deletes the test courses (ie courses with genre 'Suppression')
	 * @param testKeyWord the key word which identifies a test
	 */
	public void deleteTests(String testKeyWord) {
		if( testKeyWord != null && ! testKeyWord.equals("")) {
			Connection cnt = pa.getConnection();
			String sql = "DELETE FROM course WHERE INITCAP(genre) = '" + testKeyWord + "'";
			try {
				Statement stmt = cnt.createStatement();
				stmt.executeUpdate(sql);
			}
			catch( SQLException sqle) {
				System.out.println("Error while deleting the tests");
				sqle.printStackTrace();
				throw new DaoException("Error while deleting the tests");
			}
		}
	}
	
	/**
	 * Hides the test courses (ie courses beginning with 'test' or 'essai')
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 */
	public void hideTests(String testKeyWord1, String testKeyWord2) {
		if( testKeyWord1 != null && ! testKeyWord1.equals("")) {
			Connection cnt = pa.getConnection();
			String sql = "UPDATE course SET visible=false " +
					"WHERE INITCAP(title) LIKE '" + testKeyWord1 + "%'";
			
			if( testKeyWord2 != null && ! testKeyWord2.equals(""))
				sql += "OR INITCAP(title) LIKE '" + testKeyWord2 + "%'";
			
			try {
				Statement stmt = cnt.createStatement();
				stmt.executeUpdate(sql);
			}
			catch( SQLException sqle) {
				System.out.println("Error while hiding the tests");
				sqle.printStackTrace();
				throw new DaoException("Error while hiding the tests");
			}
		}
	}
	
	/**
	 * Gets the id of the next course which will be uploaded
	 * @return the id of the course
	 */
	public int getNextCoursId() {
		int id = 0 ;
		
		ResultSet rs = pa.query("SELECT nextval('course_courseid_seq')");
		try {
			if( rs.next() ) 
				id = rs.getInt("nextval");
			else {
				System.out.println("The next course Id hasn't been retrieved");
				throw new DaoException("The next course Id hasn't been retrieved");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the next course Id");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the next course Id");
		}
		
		return id;
	}
	
	/**
	 * Gets the list of the teachers with visible courses
	 * @return the list of teachers
	 */
	public List<String> getTeachers() {
		List<String> l = new ArrayList<String>();
		
		ResultSet rs = pa.query("SELECT DISTINCT (COALESCE(INITCAP(name),'') || COALESCE(INITCAP(' ' || firstname),'')) AS fullname FROM course WHERE visible = true AND title IS NOT NULL AND NOT (name IS NULL AND firstname IS NULL)");
		try {
			while( rs.next() ) {
				l.add(rs.getString("fullname"));
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the teachers list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the teachers list");
		}
		
		return l;
	}
	
	/**
	 * Gets the list of all the teachers
	 * @return the list of teachers
	 */
	public List<Teacher> getAllTeachers() {
		List<Teacher> l = new ArrayList<Teacher>();
		
		ResultSet rs = pa.query("SELECT INITCAP(name) AS ic_name, INITCAP(firstname) AS ic_firstname, count(*) FROM course " +
				"WHERE NOT (name IS NULL AND firstname IS NULL) GROUP BY ic_name, ic_firstname ORDER BY ic_name");
		
		try {			
			while( rs.next() ) {
				l.add( 
					new Teacher(
						rs.getString("ic_name"),
						rs.getString("ic_firstname"),
						rs.getInt("count"))
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the teachers list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the teachers list");
		}
		
		return l;
	}
	
	/**
	 * Gets the full name of a teacher with the correct case
	 * @param name the name of the teacher
	 * @param firstname the firstname of the teacher
	 * @return the full name of the teacher 
	 */
	public String getTeacherFullName(String name, String firstname) {
		Connection cnt = pa.getConnection();
		String fullname = "";
		String sql = "SELECT (COALESCE(INITCAP(name),'') || COALESCE(INITCAP(' ' || firstname),'')) AS fullname FROM course WHERE" +
				(name != null ? " INITCAP(name) = INITCAP(?) " : "") +
				( ! (name == null || firstname == null) ? "AND" : "") + 
				(firstname != null ? " INITCAP(firstname) = INITCAP(?) " : "") ;
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			if(name != null)
				pstmt.setString(1, name);
			else if( firstname != null)
				pstmt.setString(1, firstname);
			
			if(name != null && firstname != null)
				pstmt.setString(2, firstname);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fullname = rs.getString("fullname");
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the teacher full name");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the teacher full name");
		}
		
		return fullname;
	}
		
	/**
	 * Gets the list of all the formations
	 * @return the list of formations
	 */
	public List<String> getFormations() {
		List<String> l = new ArrayList<String>();

		ResultSet rs = pa.query("SELECT DISTINCT formation from course WHERE visible = true AND formation IS NOT NULL");
		try {
			while( rs.next() ) {
				l.add(rs.getString("formation"));
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the formations list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the formations list");
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
			if( pstmt.executeUpdate() == 0 ) {
				System.out.println("The consultations have not been incremented");
				throw new DaoException("The consultations have not been incremented");
			}
		}
		catch(SQLException sqle){
			System.out.println("Error while incrementing the consultations");
			sqle.printStackTrace();
			throw new DaoException("Error while incrementing the consultations");
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
			if( pstmt.executeUpdate() == 0 ) {
				System.out.println("The slide has not been added");
				throw new DaoException("The slide has not been added");
			}
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the Slide");
			sqle.printStackTrace();
			throw new DaoException("Error while adding the Slide");
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
			throw new DaoException("Error while retrieving the slides list");
		}

		return l;
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
			
			if( pstmt.executeUpdate() == 0) {
				System.out.println("The building " + b + " has not been added to the database");
				throw new DaoException("The building " + b + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new Building " + b);
			sqle.printStackTrace();
			throw new DaoException("Error while adding the new Building " + b);
		}
	}
	
	/**
	 * Gets the list of the buildings
	 * @return the list of buildings
	 */
	public List<Building> getBuildings() {
		
		List<Building> l = new ArrayList<Building>();
		String sql = "SELECT * FROM building ORDER BY buildingid";
		
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
			throw new DaoException("Error while retrieving the buildings list");
		}
		
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
			throw new DaoException("Error while retrieving the building " + buildingId);
		}
		
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
			throw new DaoException("Error while retrieving the building of amphi " + amphiIp);
		}
		
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
			
			if( pstmt.executeUpdate() == 0 ) {
				System.out.println("The building " + b + " has not been modified");
				throw new DaoException("The building " + b + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the building " + b);
			sqle.printStackTrace();
			throw new DaoException("Error while modifying the building " + b);
		}
		
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
			if( pstmt.executeUpdate() == 0) {
				System.out.println("the building " + buildingId + " has not been deleted");
				throw new DaoException("the building " + buildingId + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the course " + buildingId);
			sqle.printStackTrace();
			throw new DaoException("Error while deleting the course " + buildingId);
		}
	}
	
	/**
	 * Adds a new Amphi
	 * @param a the amphi to add
	 */
	public void addAmphi(Amphi a) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO amphi(buildingid, name, ipaddress, status, gmapurl, version) values(?,?,?,?,?,?,?)";

		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, a.getBuildingid());
			pstmt.setString(2, a.getName());
			pstmt.setString(3, a.getIpAddress());
			pstmt.setBoolean(4, false);
			if( a.getGmapurl() != null)
				pstmt.setString(5, a.getGmapurl());
			else
				pstmt.setNull(5, Types.VARCHAR);
			
			pstmt.setString(6, a.getVersion());
			
			if( pstmt.executeUpdate() == 0) {
				System.out.println("The amphi " + a + " has not been added to the database");
				throw new DaoException("The amphi " + a + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new amphi " + a);
			sqle.printStackTrace();
			throw new DaoException("Error while adding the new amphi " + a);
		}
	}
	
	/**
	 * Gets a list of all the amphis
	 * @return the list of amphis
	 */
	public List<Amphi> getAmphis(int buildingId) {
		Connection cnt = pa.getConnection();
		List<Amphi> l = new ArrayList<Amphi>();
		String sql = "SELECT amphi.*, count(course.ipaddress) FROM amphi LEFT OUTER JOIN course " +
				"ON amphi.ipaddress = course.ipaddress " +
				"WHERE amphi.buildingid = ? " +
				"GROUP BY amphi.amphiid, amphi.buildingid, amphi.name, amphi.ipaddress, amphi.status, amphi.gmapurl, amphi.version " +
				"ORDER BY amphi.amphiid";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, buildingId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Amphi a = new Amphi(
					rs.getInt("amphiid"),
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("ipaddress"),
					rs.getBoolean("status"),
					rs.getString("gmapurl"),
					rs.getString("version")
				);
				a.setNumber(rs.getInt("count"));
				l.add(a);
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the amphis list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the amphis list");
		}
		
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
					rs.getString("ipaddress"),
					rs.getBoolean("status"),
					rs.getString("gmapurl"),
					rs.getString("version")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the amphi " + amphiId);
			sqle.printStackTrace();
		}
		
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
					rs.getString("ipaddress"),
					rs.getBoolean("status"),
					rs.getString("gmapurl"),
					rs.getString("version")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the amphi " + ip);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the amphi " + ip);
		}
		
		return a;
	}
	
	/**
	 * Modifies an amphi
	 * @param a the amphi to modify
	 * @param oldAmphiip the old Ip address of this amphi
	 */
	public void modifyAmphi(Amphi a, String oldAmphiip) {
		Connection cnt = pa.getConnection();
		
		/* Creation of the SQL query string */
		String sql = "UPDATE amphi SET buildingid = ?, name = ?, ";
		sql += "ipaddress = ?, status = ?, gmapurl = ?, version = ? WHERE amphiid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setInt(1, a.getBuildingid());
			pstmt.setString(2, a.getName());
			pstmt.setString(3, a.getIpAddress());
			pstmt.setBoolean(4, a.isStatus());
			if( a.getGmapurl() != null)
				pstmt.setString(5, a.getGmapurl());
			else
				pstmt.setNull(5, Types.VARCHAR);
			
			pstmt.setString(6, a.getVersion());
			pstmt.setInt(7, a.getAmphiid());
			
			if( pstmt.executeUpdate() == 0 ) {
				System.out.println("The amphi " + a + " has not been modified");
				throw new DaoException("The amphi " + a + " has not been modified");
			}
			
			/* If the Ip address has changed, updates the references in the course table */
			if( ! oldAmphiip.equals(a.getIpAddress())) {
				sql = "UPDATE course SET ipaddress='"+ a.getIpAddress() + 
					"' WHERE ipaddress='" + oldAmphiip + "'";
				pstmt = cnt.prepareStatement(sql);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the amphi " + a);
			sqle.printStackTrace();
			throw new DaoException("Error while modifying the amphi " + a);
		}
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
			if( pstmt.executeUpdate() == 0) {
				System.out.println("the amphi " + amphiId + " has not been deleted");
				throw new DaoException("the amphi " + amphiId + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the amphi " + amphiId);
			sqle.printStackTrace();
			throw new DaoException("Error while deleting the amphi " + amphiId);
		}
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
			if( pstmt.executeUpdate() == 0) {
				System.out.println("The status of the amphi has not been changed");
				throw new DaoException("The status of the amphi has not been changed");
			}
			
		}
		catch( SQLException sqle) {
			System.out.println("Error while updating the status of the amphi " + ip);
			sqle.printStackTrace();
			throw new DaoException("Error while updating the status of the amphi " + ip);
		}
	}
	
	/**
	 * Get user by login (login is UNIQUE)
	 * @param login
	 * @return the user
	 */
	public User getUser(String login) {
		User u = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM \"user\" WHERE login = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				u = new User(
					rs.getInt("userid"),
					rs.getString("login"),
					rs.getString("email")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the user " + login);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the user " + login);
		}
		
		return u;
	}
	
	/**
	 * Get user by id 
	 * @param id
	 * @return the user
	 */
	public User getUser(int id) {
		User u = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM \"user\" WHERE userid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				u = new User(
					rs.getInt("userid"),
					rs.getString("login"),
					rs.getString("email")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the user " + id);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the user " + id);
		}
		
		return u;
	}
	
	/**
	 * Gets the id of the next user which will be uploaded
	 * @return the id of the user
	 */
	public int getNextUserId() {
		int id = 0 ;
		
		ResultSet rs = pa.query("SELECT nextval('user_userid_seq')");
		try {
			if( rs.next() ) 
				id = rs.getInt("nextval");
			else {
				System.out.println("The next user Id hasn't been retrieved");
				throw new DaoException("The next user Id hasn't been retrieved");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the next user Id");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the next user Id");
		}
		
		return id;
	}
	
	/**
	 * Adds a new user
	 * @param u User
	 */
	public void addUser(User u) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO \"user\"(\"login\",email) values(?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, u.getLogin());
			pstmt.setString(2, u.getEmail());
			
			if( pstmt.executeUpdate() == 0) {
				System.out.println("The User " + u + " has not been added to the database");
				throw new DaoException("The User " + u + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new user " + u);
			sqle.printStackTrace();
			throw new DaoException("Error while adding the new user " + u);
		}
	}
	
	/**
	 * Modify a user
	 * @param u User
	 */
	public void modifyUser(User u) {
		Connection cnt = pa.getConnection();
		
		/* Creation of the SQL query string */
		String sql = "UPDATE \"user\" SET userid = ?, login = ? , email = ? ";
		sql += "WHERE userid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setInt(1, u.getUserid());
			pstmt.setString(2, u.getLogin());
			pstmt.setString(3, u.getEmail());
			pstmt.setInt(4, u.getUserid());
						
			if( pstmt.executeUpdate() == 0 ) {
				System.out.println("The user " + u + " has not been modified");
				throw new DaoException("The user " + u + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the user " + u);
			sqle.printStackTrace();
			throw new DaoException("Error while modifying the user " + u);
		}
		
	}
	
	/**
	 * Deletes an user by providing its id
	 * @param userid the id of the user
	 */
	public void deleteUser(int userid) {
		
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM \"user\" WHERE userid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, userid);
			if( pstmt.executeUpdate() == 0) {
				System.out.println("the user " + userid + " has not been deleted");
				throw new DaoException("the user " + userid + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the user " + userid);
			sqle.printStackTrace();
			throw new DaoException("Error while deleting the user " + userid);
		}
	}
	
	/**
	 * Gets a list of course by providing its user
	 * @param user the user of the course
	 * @param number
	 * @param start
	 * @return the list of course
	 */
	public List<Course> getCourses(User u, int number, int start) {
		
		List<Course> l = new ArrayList<Course>();
		
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM course WHERE userid = ? ORDER BY date DESC, courseid DESC LIMIT " + number + " OFFSET " + start;
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, u.getUserid());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				l.add( new Course(
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
		}	
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course with user " + u.getUserid());
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the course with user " + u.getUserid());
		}
		
		return l;
	}
	
	/**
	 * Gets the total number of courses
	 * @param user
	 * @return the number of courses
	 */
	public int getCourseNumber(User u) {
		int number = 0;
		String sql = "SELECT COUNT(*) FROM course WHERE userid="+u.getUserid();
		
		try {
			ResultSet rs = pa.query(sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course number");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the course number");
		}

		return number;
	}
	
	/**
	 * Gets the list of all the users
	 * @return the list of users
	 */
	public List<User> getAllUsers() {
		
		List<User> l = new ArrayList<User>();
		
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM \"user\"";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				l.add( new User(
					rs.getInt("userid"),
					rs.getString("login"),
					rs.getString("email")
					
				));
			}
		}	
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the list of users");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the list of users");
		}
		
		return l;
	}
	
	
	/**
	 * Add a new tag
	 * @param t Tag
	 */
	public void addTag(Tag t) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO tag(tag,courseid) values(?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, t.getTag());
			pstmt.setInt(2, t.getCourseid());
			
			if( pstmt.executeUpdate() == 0) {
				System.out.println("The tag " + t + " has not been added to the database");
				throw new DaoException("The tag " + t + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new tag " + t);
			sqle.printStackTrace();
			throw new DaoException("Error while adding the new tag " + t);
		}
	}
	
	/**
	 * Deletes tags by providing its courseid
	 * @param courseid the id of the course
	 */
	public void deleteTag(int courseid) {
		
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM tag WHERE courseid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseid);
			pstmt.executeUpdate();
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting tags of course " + courseid);
			sqle.printStackTrace();
			throw new DaoException("Error while deleting tags of course " + courseid);
		}
	}
	
	
	/**
	 * Gets a list of all tags of a course
	 * @param c Course
	 * @return the list of tags
	 */
	public List<Tag> getTagsByCourse(Course c) {
		
		ResultSet rs = pa.query("SELECT * From tag WHERE courseid="+c.getCourseid()+ "ORDER BY tag");
		List<Tag> l = new ArrayList<Tag>();
		try {
			while(rs.next()) {
				l.add(new Tag(
					rs.getInt("tagid"),
					rs.getString("tag"),
					rs.getInt("courseid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the tags list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the tags list");
		}
		
		return l;
	}
	
	
	/**
	 * Gets a list of all tags
	 * @return the list of tags
	 */
	public List<String> getAllTags() {
		
		ResultSet rs = pa.query("SELECT distinct(tag) From tag ORDER BY tag");
		List<String> l = new ArrayList<String>();
		try {
			while(rs.next()) {
				l.add(rs.getString("tag"));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the tags list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the tags list");
		}
		
		return l;
	}
	
	/**
	 * Gets a list of most popular tags
	 * @return the list of most popular tags
	 */
	public List<String> getMostPopularTags() {
		
		ResultSet rs = pa.query("select tag, count(*) from tag group by tag order by 2 desc,tag limit 20");
		List<String> l = new ArrayList<String>();
		try {
			while(rs.next()) {
				l.add(rs.getString("tag"));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the tags list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the tags list");
		}
		
		return l;
	}
	
	
	/**
	 * Gets a restricted list of courses
	 * @param tag
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getCoursesByTags(List<String> tags, int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
			
		String sql = "SELECT * From course WHERE visible = true " +
			"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
		
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
						
			for(int i=0;i<tags.size();i++) 
				sql += "and courseid in (select courseid from tag where tag='" + tags.get(i) + "') "; 
							
		sql += "ORDER BY date DESC, courseid DESC LIMIT " + number + " OFFSET " + start;
		
		ResultSet rs = pa.query(sql);
		
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list");
		}
		
		return l;
	}
	
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @return the number of courses
	 */
	public int getCourseNumber(List<String> tags, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		int number = 0;
		
		if( tags!=null) {
			Connection cnt = pa.getConnection();
												
			String sql = "SELECT count(*) From course WHERE visible = true " +
			"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
			
			for(int i=0;i<tags.size();i++) 
				sql += "and courseid in (select courseid from tag where tag='" + tags.get(i) + "') "; 
			
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
			
			try {
				PreparedStatement pstmt = cnt.prepareStatement(sql);
				
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) 
					number = rs.getInt("count");
				rs.close();
			}
			catch( SQLException sqle) {
				System.out.println("Error while retrieving the course number");
				sqle.printStackTrace();
				throw new DaoException("Error while retrieving the course number");
			}
		}
		else
			number = getCourseNumber(testKeyWord1, testKeyWord2, testKeyWord3);
		
		return number;
	}
	
	
	/**
	 * Gets a restricted list of courses
	 * @param mediafolder
	 * @return course
	 */
	public Course getCourseByMediafolder(String mediafolder) {
								
		Course c = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * From course WHERE mediafolder= '" + mediafolder + "'";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				);
			}
			else
				throw new DaoException("Course " + mediafolder + " not found");
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course " + mediafolder);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the course " + mediafolder);
		}
		
		return c;
	}
	
	/**
	 * Gets a list of the n selection courses
	 * @param n the number of courses to return
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getNSelectionCourses(int n, String testKeyWord1, String testKeyWord2) {
		
		String sql = "SELECT * From course,selection WHERE courseid=idcourseselection AND genre IS NULL AND visible = true " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord1 + "%' ";
		
		if( testKeyWord2 != null && ! testKeyWord2.equals(""))
			sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
		
		sql += "ORDER BY position ASC, date DESC, courseid DESC LIMIT " + n;
				
		ResultSet rs = pa.query(sql);
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list");
		}
		
		return l;
	}
	
	/**
	 * Gets a list of the n collection courses
	 * @param n the number of courses to return
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getNFormationCourses(int n, String testKeyWord1, String testKeyWord2) {
		
		String sql = "SELECT * From course WHERE genre IS NULL AND visible = true " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord1 + "%' AND formation=(select formationcollection from selection s where position=1) ";
		
		if( testKeyWord2 != null && ! testKeyWord2.equals(""))
			sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
		
		sql += "ORDER BY date DESC, courseid DESC LIMIT " + n;
		
		ResultSet rs = pa.query(sql);
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
					rs.getString("mediafolder"),
					rs.getBoolean("highquality"),
					rs.getInt("userid")
				));
			}
			rs.close();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the courses list");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the courses list");
		}
		
		return l;
	}
	
	/**
	 * Gets the list of all the selection
	 * @return the list of users
	 */
	public List<Selection> getAllSelections() {
		
		List<Selection> l = new ArrayList<Selection>();
		
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM selection order by position";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				l.add( new Selection(
					rs.getInt("position"),
					rs.getInt("idcourseselection"),
					rs.getString("formationcollection")
					
				));
			}
		}	
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the list of selections");
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the list of selections");
		}
		
		return l;
	}
	
	/**
	 * Get selection by position 
	 * @param position
	 * @return the selection
	 */
	public Selection getSelection(int position) {
		Selection s = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM selection WHERE position = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, position);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				s = new Selection(
					rs.getInt("position"),
					rs.getInt("idcourseselection"),
					rs.getString("formationcollection")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the selection " + position);
			sqle.printStackTrace();
			throw new DaoException("Error while retrieving the selection " + position);
		}
		
		return s;
	}
	
	
	
	/**
	 * Adds a new selection
	 * @param s Selection
	 */
	public void addSelection(Selection s) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO selection(idcourseselection,formationcollection) values(?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, s.getIdcourseselection());
			pstmt.setString(2, s.getFormationcollection());
			
			if( pstmt.executeUpdate() == 0) {
				System.out.println("The Selection " + s + " has not been added to the database");
				throw new DaoException("The Selection " + s + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new selection " + s);
			sqle.printStackTrace();
			throw new DaoException("Error while adding the new selection " + s);
		}
	}
	
	/**
	 * Modify a selection
	 * @param s Selection
	 */
	public void modifySelection(Selection s) {
		Connection cnt = pa.getConnection();
		
		/* Creation of the SQL query string */
		String sql = "UPDATE selection SET position = ?, idcourseselection = ? , formationcollection = ? ";
		sql += "WHERE position = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setInt(1, s.getPosition());
			pstmt.setInt(2, s.getIdcourseselection());
			pstmt.setString(3, s.getFormationcollection());
			pstmt.setInt(4, s.getPosition());
						
			if( pstmt.executeUpdate() == 0 ) {
				System.out.println("The selection " + s + " has not been modified");
				throw new DaoException("The selection " + s + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the selection " + s);
			sqle.printStackTrace();
			throw new DaoException("Error while modifying the selection " + s);
		}
		
	}
	
	/**
	 * Deletes a selection by providing its id
	 * @param position the position of the selection
	 */
	public void deleteSelection(int position) {
		
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM selection WHERE position = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, position);
			if( pstmt.executeUpdate() == 0) {
				System.out.println("the selection " + position + " has not been deleted");
				throw new DaoException("the selection " + position + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the selection " + position);
			sqle.printStackTrace();
			throw new DaoException("Error while deleting the selection " + position);
		}
	}
	
}