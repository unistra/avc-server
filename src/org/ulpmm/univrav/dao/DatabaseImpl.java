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
import java.util.StringTokenizer;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.ulpmm.univrav.entities.Amphi;
import org.ulpmm.univrav.entities.Building;
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Discipline;
import org.ulpmm.univrav.entities.Job;
import org.ulpmm.univrav.entities.Level;
import org.ulpmm.univrav.entities.Selection;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Tag;
import org.ulpmm.univrav.entities.Teacher;
import org.ulpmm.univrav.entities.User;

/**
 * Database implementation methods
 * 
 * @author morgan
 *
 */
public class DatabaseImpl implements IDatabase {
	
	/** The datasource for the database connection */
	private DataSource datasrc;
	
	/** Logger log4j */
	private static final Logger logger = Logger.getLogger(DatabaseImpl.class);
	
	/**
	 * Constructor for database connection
	 * @param ds the datasources
	 */
	public DatabaseImpl(DataSource ds) {
		datasrc = ds;
	}
	
	/**
	 * Method to close a query (resultset, statement and connection)
	 * @param rs resultset
	 * @param ps statement
	 * @param cnt connection
	 */
	private void close(ResultSet rs, Statement ps, Connection cnt) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Error while close the resultset",e);
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				logger.error("Error while close the statement",e);
			}
		}
		if( cnt != null) {
			try {
				if(!cnt.isClosed())
					cnt.close();
			}
			catch( SQLException sqle) {
				logger.error("The database disconnection has failed",sqle);
			}
		}
	}
	

	/**
	 * Get the full name of formation. Matching with level and discipline tables.
	 * @param formation the course formation codes
	 * @return the full name of formation
	 */
	public String getFormationFullName(String formation) {
				
		String formFullName = null;
		
		Connection cnt = null;
		Statement stmt=null;
		ResultSet rs = null;		
		String sql = null;
		
		// Get the component name
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			sql = "SELECT namecomp FROM discipline WHERE codecomp='"+(formation!=null && formation.indexOf("-")!=-1 ? formation.substring(0,formation.indexOf("-")) : "")+"'";
			rs = stmt.executeQuery( sql);
			
			if(rs.next())
				formFullName = rs.getString("namecomp");					
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the formation name " + formation,sqle);
			throw new DaoException("Error while retrieving the formation name" + formation);
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		
		// Get the level name
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			sql = "SELECT name FROM level WHERE code='"+(formation!=null && formation.indexOf("-")!=-1 ? formation.substring(formation.indexOf("-")+1) : "")+"'";
			rs = stmt.executeQuery( sql);
			
			if(rs.next())
				formFullName += " - " + rs.getString("name");					
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the formation name " + formation,sqle);
			throw new DaoException("Error while retrieving the formation name" + formation);
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		
		return formFullName;
	}
	
	
	
	
	/**
	 * Adds a new course
	 * @param c the course to add
	 */
	public void addCourse(Course c) {
		
		Connection cnt = null;
		String sql = "INSERT INTO course values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";	
		PreparedStatement pstmt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
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
									
			if(c.getUserid() !=null)
				pstmt.setInt(15, c.getUserid());
			else
				pstmt.setNull(15, Types.INTEGER);
			
			if( c.getAdddocname() != null)
				pstmt.setString(16, c.getAdddocname());
			else
				pstmt.setNull(16, Types.VARCHAR);
			
			pstmt.setBoolean(17, c.isDownload());
			pstmt.setBoolean(18, c.isRestrictionuds());
			pstmt.setInt(19, c.getmediatype());
			pstmt.setShort(20, c.getVolume());
			
			if( pstmt.executeUpdate() == 0) {
				logger.error("The course " + c + " has not been added to the database");
				throw new DaoException("The course " + c + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while adding the new Course " + c,sqle);
			throw new DaoException("Error while adding the new Course " + c);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
		
	/**
	 * Gets a list of all the courses
	 * @param onlyvisible true to get only visible courses
	 * @param formationfullname show full name of formation with discipline and level table
	 * @param limit limit number of courses
	 * @return the list of courses
	 */
	public List<Course> getAllCourses(boolean onlyvisible, boolean formationfullname, Integer limit) {
		
		Connection cnt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Course> l = new ArrayList<Course>();
		String sql = null;
		
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			sql = "SELECT * From course ";
			
			if(onlyvisible)
				sql += "WHERE visible=true ";
				
			sql += "ORDER BY date DESC";		
			
			if(limit!=null)
				sql += " LIMIT "+limit;
			
			rs = stmt.executeQuery(sql);
							
			
			String formation =null;			
			while(rs.next()) {
				formation = formationfullname ? getFormationFullName(rs.getString("formation")) : rs.getString("formation");	
				l.add(new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					formation!=null ? formation : rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list",sqle);
			throw new DaoException("Error while retrieving the courses list");
		}
		finally {
			close(rs,stmt,cnt);
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
		
		Connection cnt = null;
		
		String sql = "SELECT * From course WHERE genre IS NULL AND visible = true AND restrictionuds = false " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord1 + "%' ";
		
		if( testKeyWord2 != null && ! testKeyWord2.equals(""))
			sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
				
		sql += "ORDER BY date DESC, courseid DESC LIMIT " + n;
		
		Statement stmt = null;
		ResultSet rs = null;
		List<Course> l = new ArrayList<Course>();
		
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery(sql);
			
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
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list",sqle);
			throw new DaoException("Error while retrieving the courses list");
		}
		finally {
			close(rs,stmt,cnt);
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
		Connection cnt = null;
		
		String sql = "SELECT * From course WHERE visible = true " +
			"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
			
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
			
		sql += "ORDER BY date DESC, courseid DESC LIMIT " + number + " OFFSET " + start;

		Statement stmt=null;
		ResultSet rs = null;
		List<Course> l = new ArrayList<Course>();
		String formation =null;
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( sql);
			
			while(rs.next()) {
				
				formation = getFormationFullName(rs.getString("formation"));
				
				l.add(new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					formation!=null ? formation : rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list",sqle);
			throw new DaoException("Error while retrieving the courses list");
		}
		finally {
			close(rs,stmt,cnt);
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
		
		Connection cnt = null;
		List<Course> l = new ArrayList<Course>();
		
		if( ! params.isEmpty() ) {
		
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
				else if( param.equals("keyword") ) {
					sql += "AND (UPPER(title) LIKE UPPER(?) OR UPPER(description) LIKE UPPER(?) OR UPPER(name) LIKE UPPER(?) OR UPPER(firstname) LIKE UPPER(?) ";
					sql += "OR courseid IN(SELECT courseid FROM course WHERE SUBSTRING(formation FROM 1 FOR (ABS(POSITION('-' in formation)-1))) IN (SELECT codecomp FROM discipline WHERE UPPER(namecomp) LIKE UPPER(?))) ";
					sql += "OR courseid IN(SELECT courseid FROM tag WHERE UPPER(tag) LIKE UPPER(?))) ";
				}	
				else if (param.equals("discipline"))
					sql += "AND UPPER(formation) LIKE UPPER(?) ";
				else if (param.equals("level"))
					sql += "AND UPPER(formation) LIKE UPPER(?) ";
				else
					sql += "AND " + param + " = ? ";
			}
			sql += "ORDER BY date DESC, courseid DESC LIMIT " + number + " OFFSET " + start;

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				cnt = datasrc.getConnection();
				pstmt = cnt.prepareStatement(sql);

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
					else if (param.equals("discipline")) {
						pstmt.setString(i, params.get(param) + "-%");
					}
					else if (param.equals("level")) {
						pstmt.setString(i, "%-" + params.get(param));
					}
					else
						pstmt.setString(i, params.get(param));
					i++;
				}
				
				rs = pstmt.executeQuery();
				
				/* Retrieves the records */
				String formation =null;
								
				while(rs.next()) {
					formation = getFormationFullName(rs.getString("formation"));
					l.add(new Course(
							rs.getInt("courseid"),
							rs.getTimestamp("date"),
							rs.getString("type"),
							rs.getString("title"),
							rs.getString("description"),
							formation!=null ? formation : rs.getString("formation"),
							rs.getString("name"),
							rs.getString("firstname"),
							rs.getString("ipaddress"),
							rs.getInt("duration"),
							rs.getString("genre"),
							rs.getBoolean("visible"),
							rs.getInt("consultations"),
							rs.getString("timing"),
							rs.getInt("userid"),
							rs.getString("adddocname"),
							rs.getBoolean("download"),
							rs.getBoolean("restrictionuds"),
							rs.getInt("mediatype"),
							rs.getShort("volume")
					));
				}
			}
			catch( SQLException sqle) {
				logger.error("Error while retrieving the courses list",sqle);
				throw new DaoException("Error while retrieving the courses list");
			}
			finally {
				close(rs,pstmt,cnt);
			}
			
		}
		else
			l = getCourses(number, start, testKeyWord1, testKeyWord2, testKeyWord3);
		
		return l;
	}
	

	
	/**
	 * Gets the list of courses for an author
	 * @param author the author
	 * @param limit limit number of courses
	 * @return the list of courses
	 */
	public List<Course> getCoursesByAuthor(String author, Integer limit) {
		
		Connection cnt = null;
		List<Course> l = new ArrayList<Course>();
	
		String sql = "SELECT * FROM course WHERE " +
				"(COALESCE(INITCAP(name),'') || COALESCE(INITCAP(' ' || firstname),'')) = INITCAP(?) " +
				"AND visible = true ORDER BY date DESC";
		
		if(limit!=null)
			sql += " LIMIT "+limit;
			
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, author);
			rs = pstmt.executeQuery();
			
			/* Retrieves the records */
			String formation =null;
			while(rs.next()) {
				formation = getFormationFullName(rs.getString("formation"));	
				l.add(new Course(
						rs.getInt("courseid"),
						rs.getTimestamp("date"),
						rs.getString("type"),
						rs.getString("title"),
						rs.getString("description"),
						formation!=null ? formation : rs.getString("formation"),
						rs.getString("name"),
						rs.getString("firstname"),
						rs.getString("ipaddress"),
						rs.getInt("duration"),
						rs.getString("genre"),
						rs.getBoolean("visible"),
						rs.getInt("consultations"),
						rs.getString("timing"),
						rs.getInt("userid"),
						rs.getString("adddocname"),
						rs.getBoolean("download"),
						rs.getBoolean("restrictionuds"),
						rs.getInt("mediatype"),
						rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list for the author " + author,sqle);
			throw new DaoException("Error while retrieving the courses list for the author " + author);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return l;
	}
	
	/**
	 * Gets the list of courses for a formation
	 * @param formation the formation
	 * @param limit limit number of courses
	 * @return the list of courses
	 */
	public List<Course> getCoursesByFormation(String formation,Integer limit) {
		
		Connection cnt = null;
		List<Course> l = new ArrayList<Course>();
	
		String sql = "SELECT * FROM course WHERE " +
				"formation=?" + "AND visible = true ORDER BY date DESC";
		
		if(limit!=null)
			sql += " LIMIT "+limit;
			
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, formation);
			rs = pstmt.executeQuery();
			
			/* Retrieves the records */
			String showform =null;
			while(rs.next()) {
				showform = getFormationFullName(rs.getString("formation"));
				l.add(new Course(
						rs.getInt("courseid"),
						rs.getTimestamp("date"),
						rs.getString("type"),
						rs.getString("title"),
						rs.getString("description"),
						showform!=null ? showform : rs.getString("formation"),
						rs.getString("name"),
						rs.getString("firstname"),
						rs.getString("ipaddress"),
						rs.getInt("duration"),
						rs.getString("genre"),
						rs.getBoolean("visible"),
						rs.getInt("consultations"),
						rs.getString("timing"),
						rs.getInt("userid"),
						rs.getString("adddocname"),
						rs.getBoolean("download"),
						rs.getBoolean("restrictionuds"),
						rs.getInt("mediatype"),
						rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list for the formation " + formation,sqle);
			throw new DaoException("Error while retrieving the courses list for the formation " + formation);
		}
		finally {
			close(rs,pstmt,cnt);
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
		Connection cnt = null;
		String sql = "SELECT * FROM course WHERE courseid = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
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
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				);
			}
			else
				throw new DaoException("Course " + courseId + " not found");
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the course " + courseId,sqle);
			throw new DaoException("Error while retrieving the course " + courseId);
		}
		finally {
			close(rs,pstmt,cnt);
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
		Connection cnt = null;
		String sql = "SELECT * FROM course WHERE courseid = ? AND GENRE = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			pstmt.setString(2, genre);
			rs = pstmt.executeQuery();
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
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				);
			}
			else
				throw new DaoException("Course " + courseId + " not found with this genre");
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the course " + courseId,sqle);
			throw new DaoException("Error while retrieving the course " + courseId);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return c;
	}
	
	/**
	 * Gets the total number of courses without test keywords
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		Connection cnt = null;
		
		int number = 0;			
		String sql = "SELECT COUNT(*) From course WHERE visible = true " +
		"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
		"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
		
		if( testKeyWord3 != null && ! testKeyWord3.equals(""))
			sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
		
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the course number",sqle);
			throw new DaoException("Error while retrieving the course number");
		}
		finally {
			close(rs,stmt,cnt);
		}

		return number;
	}
	
	/**
	 * Gets the total number of courses
	 * @return the number of courses
	 */
	public int getCourseNumber() {
		Connection cnt = null;
		
		int number = 0;
		String sql = "SELECT COUNT(*) FROM course WHERE visible = true";
		
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the course number",sqle);
			throw new DaoException("Error while retrieving the course number");
		}
		finally {
			close(rs,stmt,cnt);
		}

		return number;
	}
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param params the criteria of the searched courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(HashMap<String, String> params,String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		Connection cnt = null;
		
		int number = 0;
		
		if( ! params.isEmpty() ) {
			
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
				else if( param.equals("keyword") ) {
					sql += "(UPPER(title) LIKE UPPER(?) OR UPPER(description) LIKE UPPER(?) OR UPPER(name) LIKE UPPER(?) OR UPPER(firstname) LIKE UPPER(?) ";
					sql += "OR courseid IN(SELECT courseid FROM course WHERE SUBSTRING(formation FROM 1 FOR (ABS(POSITION('-' in formation)-1))) IN (SELECT codecomp FROM discipline WHERE UPPER(namecomp) LIKE UPPER(?))) ";
					sql += "OR courseid IN(SELECT courseid FROM tag WHERE UPPER(tag) LIKE UPPER(?))) ";
				}
				else if (param.equals("discipline"))
					sql += "UPPER(formation) LIKE UPPER(?) ";
				else if (param.equals("level"))
					sql += "UPPER(formation) LIKE UPPER(?) ";
				else
					sql += param + " = ? ";
			}
			sql += "AND visible = true";
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				cnt = datasrc.getConnection();
				pstmt = cnt.prepareStatement(sql);
				
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
					else if (param.equals("discipline")) {
						pstmt.setString(i, params.get(param) + "-%");
					}
					else if (param.equals("level")) {
						pstmt.setString(i, "%-" + params.get(param));
					}
					else
						pstmt.setString(i, params.get(param));
					i++;
				}
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) 
					number = rs.getInt("count");
			}
			catch( SQLException sqle) {
				logger.error("Error while retrieving the course number",sqle);
				throw new DaoException("Error while retrieving the course number");
			}
			finally {
				close(rs,pstmt,cnt);
			}
		}
		else
			number = getCourseNumber(testKeyWord1, testKeyWord2, testKeyWord3);
		
		return number;
	}
	
	/**
	 * Modifies a course
	 * @param c the course to modify
	 */
	public void modifyCourse(Course c) {
		
		
		/* Creation of the SQL query string */
		String sql = "UPDATE course SET date = ? , type = ? , title = ? , description = ? , ";
		sql += "formation = ? , name = ? , firstname = ? , ipaddress = ? , duration = ? , ";
		sql += "genre = ? , visible = ? , consultations = ? , timing = ?, userid = ?, adddocname = ?, download = ?, restrictionuds = ?, mediatype = ?, volume = ? ";
		sql += "WHERE courseid = ?";
		
		PreparedStatement pstmt = null;
		Connection cnt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
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
									
			if(c.getUserid() !=null && c.getUserid() !=0)
				pstmt.setInt(14, c.getUserid());
			else
				pstmt.setNull(14, Types.INTEGER);
						
			if( c.getAdddocname() != null)
				pstmt.setString(15, c.getAdddocname());
			else
				pstmt.setNull(15, Types.VARCHAR);
			
			pstmt.setBoolean(16, c.isDownload());
			pstmt.setBoolean(17, c.isRestrictionuds());
			pstmt.setInt(18, c.getmediatype());
			pstmt.setShort(19, c.getVolume());
			pstmt.setInt(20, c.getCourseid());
			
			
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The course " + c + " has not been modified");
				throw new DaoException("The course " + c + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while modifying the course " + c,sqle);
			throw new DaoException("Error while modifying the course " + c);
		}
		finally {
			close(null,pstmt,cnt);
		}
		
	}
	
	/**
	 * Modifies the mediatype of course
	 * @param courseid the course id 
	 * @param mediatype the mediatype
	 */
	public void modifyCourseMediatype(int courseid, int mediatype) {
				
		/* Creation of the SQL query string */
		String sql = "UPDATE course SET mediatype = ? ";
		sql += "WHERE courseid = ?";
		
		PreparedStatement pstmt = null;
		Connection cnt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setInt(1, mediatype);
			pstmt.setInt(2, courseid);
			
			
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The mediatype of course " + courseid + " has not been modified");
				throw new DaoException("The mediacourse " + courseid + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while modifying the mediatype of course " + courseid,sqle);
			throw new DaoException("Error while modifying the mediatype course " + courseid);
		}
		finally {
			close(null,pstmt,cnt);
		}
		
	}
	
	
	/**
	 * Gets the mediatype of the course
	 * @param courseid the courseid
	 * @return the mediatype of the course
	 */
	public int getMediaType(int courseid) {
		Connection cnt = null;
		String sql = "SELECT mediatype FROM course WHERE courseid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int mediatype;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseid);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				mediatype = rs.getInt("mediatype");
			}
			else
				throw new DaoException("Mediatype of course " + courseid + " not found");
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the mediatype of course " + courseid,sqle);
			throw new DaoException("Error while retrieving the mediatype of course " + courseid);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return mediatype;
	}
	
	/**
	 * Deletes a course by providing its id
	 * @param courseId the id of the course
	 */
	public void deleteCourse(int courseId) {
		
		Connection cnt = null;
		String sql = "DELETE FROM course WHERE courseid = ?";
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			if( pstmt.executeUpdate() == 0) {
				logger.error("the course " + courseId + " has not been deleted");
				throw new DaoException("the course " + courseId + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while deleting the course " + courseId,sqle);
			throw new DaoException("Error while deleting the course " + courseId);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
		
	/**
	 * Gets the list of the test courses to delete
	 * @param testKeyWord the key word which identifies a test
	 * @return the list of test courses 
	 */
	public List<Course> getTestsToDelete(String testKeyWord) {
		
		Connection cnt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Course> l = new ArrayList<Course>();
		
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery("SELECT * FROM course WHERE initcap(genre) = '" + testKeyWord + "'");
							
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
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				));
			}			
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the media folders list",sqle);
			throw new DaoException("Error while retrieving the media folders list");
		}
		finally {
			close(rs,stmt,cnt);
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
		
		Connection cnt = null;
		
		String sql = "SELECT * From course " +
			"WHERE INITCAP(genre) = '" + testKeyWord1 + "' " +
			"OR INITCAP(title) LIKE '" + testKeyWord2 + "%' ";
			
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "OR INITCAP(title) LIKE '" + testKeyWord3 + "%' ";
			
		sql += "ORDER BY date DESC, courseid DESC LIMIT " + number + " OFFSET " + start;

		ResultSet rs = null;
		Statement stmt = null;
		
		List<Course> l = new ArrayList<Course>();
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( sql);
			
			String formation =null;
			while(rs.next()) {
				formation = getFormationFullName(rs.getString("formation"));
				l.add(new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					formation!=null ? formation : rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list",sqle);
			throw new DaoException("Error while retrieving the courses list");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return l;
	}
	
	/**
	 * Gets the total number of tests with test keywords
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getTestNumber(String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		int number = 0;			
		String sql = "SELECT COUNT(*) From course " +
		"WHERE INITCAP(genre) = '" + testKeyWord1 + "' " +
		"OR INITCAP(title) LIKE '" + testKeyWord2 + "%' ";
		
		if( testKeyWord3 != null && ! testKeyWord3.equals(""))
			sql += "OR INITCAP(title) LIKE '" + testKeyWord3 + "%' ";
		
		ResultSet rs = null;
		Statement stmt = null;
		Connection cnt = null;
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the course number",sqle);
			throw new DaoException("Error while retrieving the course number");
		}
		finally {
			close(rs,stmt,cnt);
		}

		return number;
	}
	
	
	
	/**
	 * Deletes the test courses (ie courses with genre 'Suppression')
	 * @param testKeyWord the key word which identifies a test
	 */
	public void deleteTests(String testKeyWord) {
		if( testKeyWord != null && ! testKeyWord.equals("")) {
			
			String sql = "DELETE FROM course WHERE INITCAP(genre) = '" + testKeyWord + "'";
			Statement stmt = null;
			Connection cnt = null;
			try {
				cnt = datasrc.getConnection();
				stmt = cnt.createStatement();
				stmt.executeUpdate(sql);
			}
			catch( SQLException sqle) {
				logger.error("Error while deleting the tests",sqle);
				throw new DaoException("Error while deleting the tests");
			}
			finally {
				close(null,stmt,cnt);
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
		
			String sql = "UPDATE course SET visible=false " +
					"WHERE INITCAP(title) LIKE '" + testKeyWord1 + "%'";
			
			if( testKeyWord2 != null && ! testKeyWord2.equals(""))
				sql += "OR INITCAP(title) LIKE '" + testKeyWord2 + "%'";
			
			Statement stmt = null;
			Connection cnt = null;
			try {
				cnt = datasrc.getConnection();
				stmt = cnt.createStatement();
				stmt.executeUpdate(sql);
			}
			catch( SQLException sqle) {
				logger.error("Error while hiding the tests",sqle);
				throw new DaoException("Error while hiding the tests");
			}
			finally {
				close(null,stmt,cnt);
			}
		}
	}
	
	/**
	 * Gets the id of the next course which will be uploaded
	 * @return the id of the course
	 */
	public int getNextCoursId() {
		int id = 0 ;
		ResultSet rs = null;
		Statement stmt = null;
		Connection cnt = null;
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( "SELECT nextval('course_courseid_seq')");
			
			if( rs.next() ) 
				id = rs.getInt("nextval");
			else {
				logger.error("The next course Id hasn't been retrieved");
				throw new DaoException("The next course Id hasn't been retrieved");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the next course Id",sqle);
			throw new DaoException("Error while retrieving the next course Id");
		}
		finally {
			close(rs,stmt,cnt);
		}
				
		return id;
	}
	
	/**
	 * Gets the list of the teachers with visible courses
	 * @return the list of teachers
	 */
	public List<String> getTeachers() {
		List<String> l = new ArrayList<String>();
		ResultSet rs = null;
		Statement stmt = null;
		Connection cnt = null;
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( "SELECT DISTINCT (COALESCE(INITCAP(name),'') || COALESCE(INITCAP(' ' || firstname),'')) AS fullname FROM course WHERE visible = true AND title IS NOT NULL AND NOT (name IS NULL AND firstname IS NULL)");
			
			while( rs.next() ) {
				l.add(rs.getString("fullname"));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the teachers list",sqle);
			throw new DaoException("Error while retrieving the teachers list");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return l;
	}
	
	/**
	 * Gets the list of all the teachers
	 * @return the list of teachers
	 */
	public List<Teacher> getAllTeachers() {
		List<Teacher> l = new ArrayList<Teacher>();
		ResultSet rs = null;
		Statement stmt = null;
		Connection cnt = null;
		try {	
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery("SELECT INITCAP(name) AS ic_name, INITCAP(firstname) AS ic_firstname, count(*) FROM course " +
			"WHERE NOT (name IS NULL AND firstname IS NULL) GROUP BY ic_name, ic_firstname ORDER BY ic_name");

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
			logger.error("Error while retrieving the teachers list",sqle);
			throw new DaoException("Error while retrieving the teachers list");
		}
		finally {
			close(rs,stmt,cnt);
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
		
		Connection cnt = null;
		String fullname = "";
		String sql = "SELECT (COALESCE(INITCAP(name),'') || COALESCE(INITCAP(' ' || firstname),'')) AS fullname FROM course WHERE" +
				(name != null ? " INITCAP(name) = INITCAP(?) " : "") +
				( ! (name == null || firstname == null) ? "AND" : "") + 
				(firstname != null ? " INITCAP(firstname) = INITCAP(?) " : "") ;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			if(name != null)
				pstmt.setString(1, name);
			else if( firstname != null)
				pstmt.setString(1, firstname);
			
			if(name != null && firstname != null)
				pstmt.setString(2, firstname);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fullname = rs.getString("fullname");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the teacher full name",sqle);
			throw new DaoException("Error while retrieving the teacher full name");
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return fullname;
	}
		
	/**
	 * Gets the list of all the formations
	 * @return the list of formations
	 */
	public List<String> getFormations() {
		List<String> l = new ArrayList<String>();
		ResultSet rs = null;
		Statement stmt = null;
		Connection cnt = null;
		
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( "SELECT DISTINCT formation from course WHERE visible = true AND formation IS NOT NULL");
						
			while( rs.next() ) {
				l.add(rs.getString("formation"));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the formations list",sqle);
			throw new DaoException("Error while retrieving the formations list");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return l;
	}
	
		
	/**
	 * Increments the number of consultations for a course
	 * @param c the course
	 */
	public void incrementConsultations(Course c) {
		int consultations = c.getConsultations() + 1;
		Connection cnt = null;
		
		String sql = "UPDATE course SET consultations = ? WHERE courseid = ? ";
		PreparedStatement pstmt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, consultations);
			pstmt.setInt(2, c.getCourseid());
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The consultations have not been incremented");
				throw new DaoException("The consultations have not been incremented");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while incrementing the consultations",sqle);
			throw new DaoException("Error while incrementing the consultations");
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Adds a new slide
	 * @param s the slide to add
	 */
	public void addSlide(Slide s) {
		
		Connection cnt = null;
		String sql = "INSERT INTO Slide(courseid, slidetime) values(?,?)";
		PreparedStatement pstmt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, s.getCourseid());
			pstmt.setInt(2, s.getSlidetime());
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The slide has not been added");
				throw new DaoException("The slide has not been added");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while adding the Slide",sqle);
			throw new DaoException("Error while adding the Slide");
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Gets the slides of a course
	 * @param courseId the id of the course
	 * @return the list of slides
	 */
	public List<Slide> getSlides(int courseId) {
		
		Connection cnt = null;
		List<Slide> l = new ArrayList<Slide>();
		String sql = "SELECT * FROM slide WHERE courseid = ? ORDER BY slidetime";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				l.add(new Slide(
					rs.getInt("courseid"),
					rs.getInt("slidetime")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the slides list",sqle);
			throw new DaoException("Error while retrieving the slides list");
		}
		finally {
			close(rs,pstmt,cnt);
		}

		return l;
	}
	
	/**
	 * Adds a new building
	 * @param b the building to add
	 */
	public void addBuilding(Building b) {
	
		Connection cnt = null;
		String sql = "INSERT INTO building(name, imagefile) values(?,?)";
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, b.getName());
			pstmt.setString(2, b.getImageFile());
			
			if( pstmt.executeUpdate() == 0) {
				logger.error("The building " + b + " has not been added to the database");
				throw new DaoException("The building " + b + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while adding the new Building " + b,sqle);
			throw new DaoException("Error while adding the new Building " + b);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Gets the list of the buildings
	 * @return the list of buildings
	 */
	public List<Building> getBuildings() {
		
		Connection cnt = null;
		List<Building> l = new ArrayList<Building>();
		String sql = "SELECT * FROM building ORDER BY buildingid";
		ResultSet rs = null;
		Statement stmt = null;
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Building b = new Building(
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("imagefile")
				);
				b.setAmphis(getAmphis(b.getBuildingid()));
				l.add(b);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the buildings list",sqle);
			throw new DaoException("Error while retrieving the buildings list");
		}
		finally {
			close(rs,stmt,cnt);
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
		Connection cnt = null;
		String sql = "SELECT * FROM building WHERE buildingid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, buildingId);
			rs = pstmt.executeQuery();
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
			logger.error("Error while retrieving the building " + buildingId,sqle);
			throw new DaoException("Error while retrieving the building " + buildingId);
		}
		finally {
			close(rs,pstmt,cnt);
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
		Connection cnt = null;
		String sql = "SELECT name FROM building WHERE buildingid = ( SELECT buildingid FROM amphi WHERE ipaddress = ? )";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, amphiIp);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				name = rs.getString("name");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the building of amphi " + amphiIp,sqle);
			throw new DaoException("Error while retrieving the building of amphi " + amphiIp);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return name;
	}
	
	/**
	 * Modifies a building
	 * @param b the building to modify
	 */
	public void modifyBuilding(Building b) {
		
		Connection cnt = null;
		/* Creation of the SQL query string */
		String sql = "UPDATE building SET name = ? , imagefile = ? ";
		sql += "WHERE buildingid = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setString(1, b.getName());
			pstmt.setString(2, b.getImageFile());
			pstmt.setInt(3, b.getBuildingid());
			
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The building " + b + " has not been modified");
				throw new DaoException("The building " + b + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while modifying the building " + b,sqle);
			throw new DaoException("Error while modifying the building " + b);
		}
		finally {
			close(null,pstmt,cnt);
		}
		
	}
	
	/**
	 * Deletes a building
	 * @param buildingId the id of the building
	 */
	public void deleteBuilding(int buildingId) {
		
		Connection cnt = null;
		String sql = "DELETE FROM building WHERE buildingid = ?";
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, buildingId);
			if( pstmt.executeUpdate() == 0) {
				logger.error("the building " + buildingId + " has not been deleted");
				throw new DaoException("the building " + buildingId + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while deleting the course " + buildingId,sqle);
			throw new DaoException("Error while deleting the course " + buildingId);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Adds a new Amphi
	 * @param a the amphi to add
	 */
	public void addAmphi(Amphi a) {
	
		String sql = "INSERT INTO amphi(buildingid, name, ipaddress, status, gmapurl, version, restrictionuds) values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection cnt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, a.getBuildingid());
			pstmt.setString(2, a.getName());
			pstmt.setString(3, a.getIpAddress());
			pstmt.setBoolean(4, false);
			if( a.getGmapurl() != null)
				pstmt.setString(5, a.getGmapurl());
			else
				pstmt.setNull(5, Types.VARCHAR);
			
			pstmt.setString(6, a.getVersion());
			
			pstmt.setBoolean(7, a.isRestrictionuds());
			
			if( pstmt.executeUpdate() == 0) {
				logger.error("The amphi " + a + " has not been added to the database");
				throw new DaoException("The amphi " + a + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while adding the new amphi " + a,sqle);
			throw new DaoException("Error while adding the new amphi " + a);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Gets a list of all the amphis
	 * @param buildingId the id of the building
	 * @return the list of amphis
	 */
	public List<Amphi> getAmphis(int buildingId) {
		
		Connection cnt = null;
		List<Amphi> l = new ArrayList<Amphi>();
		String sql = "SELECT amphi.*, count(course.ipaddress) FROM amphi LEFT OUTER JOIN course " +
				"ON amphi.ipaddress = course.ipaddress " +
				"WHERE amphi.buildingid = ? " +
				"GROUP BY amphi.amphiid, amphi.buildingid, amphi.name, amphi.ipaddress, amphi.status, amphi.gmapurl, amphi.version, amphi.restrictionuds " +
				"ORDER BY amphi.amphiid";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, buildingId);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Amphi a = new Amphi(
					rs.getInt("amphiid"),
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("ipaddress"),
					rs.getBoolean("status"),
					rs.getString("gmapurl"),
					rs.getString("version"),
					rs.getBoolean("restrictionuds")
				);
				a.setNumber(rs.getInt("count"));
				l.add(a);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the amphis list",sqle);
			throw new DaoException("Error while retrieving the amphis list");
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return l;
	}

	/**
	 * Gets an amphi by providing its id
	 * @param amphiId the id of the amphi
	 * @return the amphi
	 */
	public Amphi getAmphi(int amphiId) {
		Amphi a = null;
		Connection cnt = null;
		String sql = "SELECT * FROM amphi WHERE amphiid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, amphiId);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				a = new Amphi(
					rs.getInt("amphiid"),
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("ipaddress"),
					rs.getBoolean("status"),
					rs.getString("gmapurl"),
					rs.getString("version"),
					rs.getBoolean("restrictionuds")
				);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the amphi " + amphiId,sqle);
		}
		finally {
			close(rs,pstmt,cnt);
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
		Connection cnt = null;
		String sql = "SELECT * FROM amphi WHERE ipaddress = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, ip);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				a = new Amphi(
					rs.getInt("amphiid"),
					rs.getInt("buildingid"),
					rs.getString("name"),
					rs.getString("ipaddress"),
					rs.getBoolean("status"),
					rs.getString("gmapurl"),
					rs.getString("version"),
					rs.getBoolean("restrictionuds")
				);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the amphi " + ip,sqle);
			throw new DaoException("Error while retrieving the amphi " + ip);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return a;
	}
	
	/**
	 * Modifies an amphi
	 * @param a the amphi to modify
	 * @param oldAmphiip the old Ip address of this amphi
	 */
	public void modifyAmphi(Amphi a, String oldAmphiip) {
		
		Connection cnt = null;
		/* Creation of the SQL query string */
		String sql = "UPDATE amphi SET buildingid = ?, name = ?, ";
		sql += "ipaddress = ?, status = ?, gmapurl = ?, version = ?, restrictionuds = ? WHERE amphiid = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
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
			pstmt.setBoolean(7, a.isRestrictionuds());
			pstmt.setInt(8, a.getAmphiid());
			
			
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The amphi " + a + " has not been modified");
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
			logger.error("Error while modifying the amphi " + a,sqle);
			throw new DaoException("Error while modifying the amphi " + a);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}

	/**
	 * Deletes an amphi by providing its id
	 * @param amphiId the id of the amphi
	 */
	public void deleteAmphi(int amphiId) {
	
		Connection cnt = null;
		String sql = "DELETE FROM amphi WHERE amphiid = ?";
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, amphiId);
			if( pstmt.executeUpdate() == 0) {
				logger.error("the amphi " + amphiId + " has not been deleted");
				throw new DaoException("the amphi " + amphiId + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while deleting the amphi " + amphiId,sqle);
			throw new DaoException("Error while deleting the amphi " + amphiId);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Sets the status of the live in an amphi
	 * @param ip the IP address of the amphi
	 * @param status the status od the live in the amphi
	 */
	public void setAmphiStatus(String ip, boolean status) {
	
		Connection cnt = null;
		String sql = "UPDATE amphi SET status = ? WHERE ipaddress = ? ";
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setBoolean(1, status);
			pstmt.setString(2, ip);
			if( pstmt.executeUpdate() == 0) {
				logger.error("The status of the amphi has not been changed");
				throw new DaoException("The status of the amphi has not been changed");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while updating the status of the amphi " + ip,sqle);
			throw new DaoException("Error while updating the status of the amphi " + ip);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Gets user by login (login is UNIQUE)
	 * @param login the login of the user
	 * @return the user
	 */
	public User getUser(String login) {
		User u = null;
		Connection cnt = null;
		String sql = "SELECT * FROM \"user\" WHERE login = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				u = new User(
					rs.getInt("userid"),
					rs.getString("login"),
					rs.getString("email"),
					rs.getString("firstname"),
					rs.getString("lastname"),
					rs.getString("profile"),
					rs.getString("establishment"),
					rs.getString("type"),
					rs.getBoolean("activate"),
					rs.getString("etp")
					
				);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the user " + login,sqle);
			throw new DaoException("Error while retrieving the user " + login);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return u;
	}
	
		
	/**
	 * Get user by id 
	 * @param id the id of the user
	 * @return the user
	 */
	public User getUser(int id) {
		User u = null;
		Connection cnt = null;
		String sql = "SELECT * FROM \"user\" WHERE userid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				u = new User(
						rs.getInt("userid"),
						rs.getString("login"),
						rs.getString("email"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("profile"),
						rs.getString("establishment"),
						rs.getString("type"),
						rs.getBoolean("activate"),
						rs.getString("etp")
				);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the user " + id,sqle);
			throw new DaoException("Error while retrieving the user " + id);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return u;
	}
	
	/**
	 * Gets local user by hash code (hash is unique)
	 * @param hash the hash code
	 * @return the user
	 */
	public User getUserLocalByHash(String hash) {
		User u = null;
		Connection cnt = null;
		String sql = "SELECT * FROM \"user\" WHERE password = ? and type = ?" ;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, hash);
			pstmt.setString(2, User.getTYPELOCAL());
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				u = new User(
					rs.getInt("userid"),
					rs.getString("login"),
					rs.getString("email"),
					rs.getString("firstname"),
					rs.getString("lastname"),
					rs.getString("profile"),
					rs.getString("establishment"),
					rs.getString("type"),
					rs.getBoolean("activate"),
					rs.getString("etp")
					
				);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the user",sqle);
			throw new DaoException("Error while retrieving the user ");
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return u;
	}
	
	/**
	 * Modify a password for a user
	 * @param login the login
	 * @param hash the password
	 * @param hashtype the password type
	 */
	public void modifyUserPassword(String login, String hash, String hashtype) {
		
		Connection cnt = null;
		/* Creation of the SQL query string */
		String sql = "UPDATE \"user\" SET password = ?, passwordtype = ? ";
		sql += "WHERE login = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setString(1, hash);
			pstmt.setString(2, hashtype);
			pstmt.setString(3, login);
						
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The user " + login + " has not been modified");
				throw new DaoException("The user " + login + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while modifying the user " + login,sqle);
			throw new DaoException("Error while modifying the user " + login);
		}
		finally {
			close(null,pstmt,cnt);
		}
		
	}
	
	/**
	 * Gets the id of the next user which will be uploaded
	 * @return the id of the user
	 */
	public int getNextUserId() {
		int id = 0 ;
		ResultSet rs = null;
		Statement stmt = null;
		Connection cnt = null;
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery("SELECT nextval('user_userid_seq')");
			
			if( rs.next() ) 
				id = rs.getInt("nextval");
			else {
				logger.error("The next user Id hasn't been retrieved");
				throw new DaoException("The next user Id hasn't been retrieved");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the next user Id",sqle);
			throw new DaoException("Error while retrieving the next user Id");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return id;
	}
	
	/**
	 * Adds a new user
	 * @param u User
	 */
	public void addUser(User u) {
		
		String sql = "INSERT INTO \"user\"(\"login\",email,firstname,lastname,profile,establishment,type,activate,etp) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection cnt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, u.getLogin());
			pstmt.setString(2, u.getEmail());
			pstmt.setString(3, u.getFirstname());
			pstmt.setString(4, u.getLastname());
			pstmt.setString(5, u.getProfile());
			pstmt.setString(6, u.getEstablishment());
			pstmt.setString(7, u.getType());
			pstmt.setBoolean(8, u.isActivate());
			pstmt.setString(9, u.getEtp());
			
			if( pstmt.executeUpdate() == 0) {
				logger.error("The User " + u + " has not been added to the database");
				throw new DaoException("The User " + u + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while adding the new user " + u,sqle);
			throw new DaoException("Error while adding the new user " + u);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Modify a user
	 * @param u User
	 */
	public void modifyUser(User u) {
		
		Connection cnt = null;
		/* Creation of the SQL query string */
		String sql = "UPDATE \"user\" SET userid = ?, login = ? , email = ?, firstname = ?,lastname = ?," +
				"profile = ?,establishment = ?,type = ?,activate = ?,etp = ? ";
		sql += "WHERE userid = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setInt(1, u.getUserid());
			pstmt.setString(2, u.getLogin());
			pstmt.setString(3, u.getEmail());
			pstmt.setString(4, u.getFirstname());
			pstmt.setString(5, u.getLastname());
			pstmt.setString(6, u.getProfile());
			pstmt.setString(7, u.getEstablishment());
			pstmt.setString(8, u.getType());
			pstmt.setBoolean(9, u.isActivate());
			pstmt.setString(10, u.getEtp());
			pstmt.setInt(11, u.getUserid());
						
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The user " + u + " has not been modified");
				throw new DaoException("The user " + u + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while modifying the user " + u,sqle);
			throw new DaoException("Error while modifying the user " + u);
		}
		finally {
			close(null,pstmt,cnt);
		}
		
	}
	
	/**
	 * Deletes an user by providing its id
	 * @param userid the id of the user
	 */
	public void deleteUser(int userid) {
		
		Connection cnt = null;
		String sql = "DELETE FROM \"user\" WHERE userid = ?";
		PreparedStatement pstmt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, userid);
			if( pstmt.executeUpdate() == 0) {
				logger.error("the user " + userid + " has not been deleted");
				throw new DaoException("the user " + userid + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while deleting the user " + userid,sqle);
			throw new DaoException("Error while deleting the user " + userid);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Gets a list of courses by providing its user
	 * @param u the user of the course
	 * @param number the number of courses
	 * @param start the start number of courses
	 * @param onlyvisible true to get only visible courses
	 * @return the list of course
	 */
	public List<Course> getCoursesByUser(User u, Integer number, Integer start, boolean onlyvisible) {
		
		List<Course> l = new ArrayList<Course>();
		Connection cnt = null;
		String sql = "SELECT * FROM course WHERE userid = ? ";
		
		if(onlyvisible)
			sql+="AND visible=true ";
		
		sql+="ORDER BY date DESC, courseid ";		
		
		if(number!=null && start!=null)
			sql = sql + "DESC LIMIT " + number + " OFFSET " + start;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, u.getUserid());
			rs = pstmt.executeQuery();
			
			String formation =null;
			while(rs.next()) {
				formation = getFormationFullName(rs.getString("formation"));
				l.add( new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					formation!=null ? formation : rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				));
			}
		}	
		catch( SQLException sqle) {
			logger.error("Error while retrieving the course with user " + u.getUserid(),sqle);
			throw new DaoException("Error while retrieving the course with user " + u.getUserid());
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return l;
	}
	
	/**
	 * Gets the total number of courses
	 * @param u the user
	 * @return the number of courses
	 */
	public int getCourseNumber(User u) {
		int number = 0;
		String sql = "SELECT COUNT(*) FROM course WHERE userid="+u.getUserid();
		ResultSet rs = null;
		Statement stmt = null;
		Connection cnt = null;
		
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) 
				number = rs.getInt("count");
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the course number",sqle);
			throw new DaoException("Error while retrieving the course number");
		}
		finally {
			close(rs,stmt,cnt);
		}

		return number;
	}
	
	/**
	 * Gets the list of all the users
	 * @return the list of users
	 */
	public List<User> getAllUsers() {
		
		List<User> l = new ArrayList<User>();
		
		Connection cnt = null;
		String sql = "SELECT * FROM \"user\"";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				l.add( new User(
						rs.getInt("userid"),
						rs.getString("login"),
						rs.getString("email"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("profile"),
						rs.getString("establishment"),
						rs.getString("type"),
						rs.getBoolean("activate"),
						rs.getString("etp")
					
				));
			}
		}	
		catch( SQLException sqle) {
			logger.error("Error while retrieving the list of users",sqle);
			throw new DaoException("Error while retrieving the list of users");
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return l;
	}
	
	
	/**
	 * Add a new tag
	 * @param t the tag
	 */
	public void addTag(Tag t) {
		
		String sql = "INSERT INTO tag(tag,courseid) values(?,?)";
		PreparedStatement pstmt = null;
		Connection cnt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, t.getTag().toLowerCase());
			pstmt.setInt(2, t.getCourseid());
			
			if( pstmt.executeUpdate() == 0) {
				logger.error("The tag " + t + " has not been added to the database");
				throw new DaoException("The tag " + t + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while adding the new tag " + t,sqle);
			throw new DaoException("Error while adding the new tag " + t);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Deletes tags by providing its courseid
	 * @param courseid the id of the course
	 */
	public void deleteTag(int courseid) {
		
		Connection cnt = null;
		String sql = "DELETE FROM tag WHERE courseid = ?";
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseid);
			pstmt.executeUpdate();
		}
		catch( SQLException sqle) {
			logger.error("Error while deleting tags of course " + courseid,sqle);
			throw new DaoException("Error while deleting tags of course " + courseid);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	
	/**
	 * Gets a list of all tags of a course
	 * @param c Course
	 * @return the list of tags
	 */
	public List<Tag> getTagsByCourse(Course c) {
		
		Connection cnt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Tag> l = new ArrayList<Tag>();
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( "SELECT * From tag WHERE courseid="+c.getCourseid()+ "ORDER BY tag");
			
			while(rs.next()) {
				l.add(new Tag(
					rs.getInt("tagid"),
					rs.getString("tag").toLowerCase(),
					rs.getInt("courseid")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the tags list",sqle);
			throw new DaoException("Error while retrieving the tags list");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return l;
	}
	
	
	/**
	 * Gets a list of all tags
	 * @return the list of tags
	 */
	public List<String> getAllTags() {
		
		Connection cnt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> l = new ArrayList<String>();
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( "SELECT distinct(tag) From tag ORDER BY tag");
			
			while(rs.next()) {
				l.add(rs.getString("tag").toLowerCase());
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the tags list",sqle);
			throw new DaoException("Error while retrieving the tags list");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return l;
	}
	
	/**
	 * Gets a list of most popular tags
	 * @return the list of most popular tags
	 */
	public List<String> getMostPopularTags() {
		
		Connection cnt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> l = new ArrayList<String>();
		
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( "select tag, count(*) from tag group by tag order by 2 desc,tag limit 15");
			
			while(rs.next()) {
				l.add(rs.getString("tag").toLowerCase());
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the tags list",sqle);
			throw new DaoException("Error while retrieving the tags list");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return l;
	}
	
	
	/**
	 * Gets a restricted list of courses
	 * @param tags the tags
	 * @param number the number of courses to return
	 * @param start the start number of the courses
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the list of courses
	 */
	public List<Course> getCoursesByTags(List<String> tags, int number, int start, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		Connection cnt = null;
		
		String sql = "SELECT * From course WHERE visible = true " +
			"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
		
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
						
			for(int i=0;i<tags.size();i++) 
				sql += "and courseid in (select courseid from tag where lower(tag)='" + tags.get(i).toLowerCase() + "') "; 
							
		sql += "ORDER BY date DESC, courseid DESC LIMIT " + number + " OFFSET " + start;
		
		ResultSet rs = null;
		Statement stmt = null;
		List<Course> l = new ArrayList<Course>();
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( sql);
			
			String formation =null;
			while(rs.next()) {
				formation = getFormationFullName(rs.getString("formation"));
				l.add(new Course(
					rs.getInt("courseid"),
					rs.getTimestamp("date"),
					rs.getString("type"),
					rs.getString("title"),
					rs.getString("description"),
					formation!=null ? formation : rs.getString("formation"),
					rs.getString("name"),
					rs.getString("firstname"),
					rs.getString("ipaddress"),
					rs.getInt("duration"),
					rs.getString("genre"),
					rs.getBoolean("visible"),
					rs.getInt("consultations"),
					rs.getString("timing"),
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list",sqle);
			throw new DaoException("Error while retrieving the courses list");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return l;
	}
	
	
	/**
	 * Gets the number of courses corresponding to the given criteria
	 * @param tags the tags
	 * @param testKeyWord1 the first key word which identifies a test
	 * @param testKeyWord2 the second key word which identifies a test
	 * @param testKeyWord3 the third key word which identifies a test
	 * @return the number of courses
	 */
	public int getCourseNumber(List<String> tags, String testKeyWord1, String testKeyWord2, String testKeyWord3) {
		int number = 0;
		
		if( tags!=null) {
			
												
			String sql = "SELECT count(*) From course WHERE visible = true " +
			"AND (genre IS NULL OR NOT INITCAP(genre) = '" + testKeyWord1 + "') " +
			"AND INITCAP(title) NOT LIKE '" + testKeyWord2 + "%' ";
			
			for(int i=0;i<tags.size();i++) 
				sql += "and courseid in (select courseid from tag where tag='" + tags.get(i) + "') "; 
			
			if( testKeyWord3 != null && ! testKeyWord3.equals(""))
				sql += "AND INITCAP(title) NOT LIKE '" + testKeyWord3 + "%' ";
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Connection cnt = null;
			
			try {
				cnt = datasrc.getConnection();
				pstmt = cnt.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) 
					number = rs.getInt("count");
			}
			catch( SQLException sqle) {
				logger.error("Error while retrieving the course number",sqle);
				throw new DaoException("Error while retrieving the course number");
			}
			finally {
				close(rs,pstmt,cnt);
			}
		}
		else
			number = getCourseNumber(testKeyWord1, testKeyWord2, testKeyWord3);
		
		return number;
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
				
		Statement stmt = null;
		ResultSet rs = null;
		Connection cnt = null;
		List<Course> l = new ArrayList<Course>();
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( sql);
			
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
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list",sqle);
			throw new DaoException("Error while retrieving the courses list");
		}
		finally {
			close(rs,stmt,cnt);
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
		
		ResultSet rs = null;
		Statement stmt = null;
		List<Course> l = new ArrayList<Course>();
		Connection cnt = null;
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery( sql);
			
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
					rs.getInt("userid"),
					rs.getString("adddocname"),
					rs.getBoolean("download"),
					rs.getBoolean("restrictionuds"),
					rs.getInt("mediatype"),
					rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list",sqle);
			throw new DaoException("Error while retrieving the courses list");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return l;
	}
	
	/**
	 * Gets the list of all the selection
	 * @return the list of users
	 */
	public List<Selection> getAllSelections() {
		
		List<Selection> l = new ArrayList<Selection>();
		Connection cnt = null;
		
		String sql = "SELECT * FROM selection order by position";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				l.add( new Selection(
					rs.getInt("position"),
					rs.getInt("idcourseselection"),
					rs.getString("formationcollection")
					
				));
			}
		}	
		catch( SQLException sqle) {
			logger.error("Error while retrieving the list of selections",sqle);
			throw new DaoException("Error while retrieving the list of selections");
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return l;
	}
	
	/**
	 * Get selection by position 
	 * @param position the position of the selection
	 * @return the selection
	 */
	public Selection getSelection(int position) {
		Selection s = null;
		Connection cnt = null;
		String sql = "SELECT * FROM selection WHERE position = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, position);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				s = new Selection(
					rs.getInt("position"),
					rs.getInt("idcourseselection"),
					rs.getString("formationcollection")
				);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the selection " + position,sqle);
			throw new DaoException("Error while retrieving the selection " + position);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return s;
	}
	
	
	
	/**
	 * Adds a new selection
	 * @param s the selection
	 */
	public void addSelection(Selection s) {
		
		String sql = "INSERT INTO selection(idcourseselection,formationcollection) values(?,?)";
		PreparedStatement pstmt = null;
		Connection cnt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, s.getIdcourseselection());
			pstmt.setString(2, s.getFormationcollection());
			
			if( pstmt.executeUpdate() == 0) {
				logger.error("The Selection " + s + " has not been added to the database");
				throw new DaoException("The Selection " + s + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while adding the new selection " + s,sqle);
			throw new DaoException("Error while adding the new selection " + s);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	/**
	 * Modify a selection
	 * @param s the selection
	 */
	public void modifySelection(Selection s) {
		
		Connection cnt = null;
		/* Creation of the SQL query string */
		String sql = "UPDATE selection SET position = ?, idcourseselection = ? , formationcollection = ? ";
		sql += "WHERE position = ?";
		
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setInt(1, s.getPosition());
			pstmt.setInt(2, s.getIdcourseselection());
			pstmt.setString(3, s.getFormationcollection());
			pstmt.setInt(4, s.getPosition());
						
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The selection " + s + " has not been modified");
				throw new DaoException("The selection " + s + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while modifying the selection " + s,sqle);
			throw new DaoException("Error while modifying the selection " + s);
		}
		finally {
			close(null,pstmt,cnt);
		}
		
	}
	
	/**
	 * Deletes a selection by providing its id
	 * @param position the position of the selection
	 */
	public void deleteSelection(int position) {
		
		Connection cnt = null;
		String sql = "DELETE FROM selection WHERE position = ?";
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, position);
			if( pstmt.executeUpdate() == 0) {
				logger.error("the selection " + position + " has not been deleted");
				throw new DaoException("the selection " + position + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while deleting the selection " + position,sqle);
			throw new DaoException("Error while deleting the selection " + position);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
		
	/**
	 * Gets the list of all jobs
	 * @return the list of jobs
	 */
	public List<Job> getAllJobs() {
		
		List<Job> l = new ArrayList<Job>();
		Connection cnt = null;
		
		String sql = "SELECT * FROM job order by jobid desc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				l.add( new Job(
					rs.getInt("jobid"),
					rs.getInt("courseid"),
					rs.getString("status"),
					rs.getInt("mediatype"),
					rs.getString("coursetype"),
					rs.getString("mediapath"),
					rs.getString("extension")
				));
			}
		}	
		catch( SQLException sqle) {
			logger.error("Error while retrieving the list of jobs",sqle);
			throw new DaoException("Error while retrieving the list of jobs");
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return l;
	}
	
	/**
	 * Adds a new job
	 * @param j the job
	 */
	public void addJob(Job j) {
		
		String sql = "INSERT INTO job(jobid,courseid,status,mediatype,coursetype,mediapath,extension) values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection cnt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, j.getJobid());
			pstmt.setInt(2, j.getCourseid());
			pstmt.setString(3, j.getStatus());
			pstmt.setInt(4, j.getMediatype());
			pstmt.setString(5, j.getCoursetype());
			pstmt.setString(6, j.getMediapath());
			pstmt.setString(7, j.getExtension());
			
			if( pstmt.executeUpdate() == 0) {
				logger.error("The job " + j + " has not been added to the database");
				throw new DaoException("The job " + j + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while adding the new job " + j,sqle);
			throw new DaoException("Error while adding the new job " + j);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	
	/**
	 * Modify a job
	 * @param j the job
	 */
	public void modifyJob(Job j) {
		
		Connection cnt = null;
		/* Creation of the SQL query string */
		String sql = "UPDATE job SET jobid = ?, courseid = ?, status = ?, mediatype = ?, coursetype = ?, mediapath = ?, extension = ? ";
		sql += "WHERE jobid = ?";
		
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setInt(1, j.getJobid());
			pstmt.setInt(2, j.getCourseid());
			pstmt.setString(3, j.getStatus());
			pstmt.setInt(4, j.getMediatype());
			pstmt.setString(5, j.getCoursetype());
			pstmt.setString(6, j.getMediapath());
			pstmt.setString(7, j.getExtension());
			pstmt.setInt(8, j.getJobid());
						
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The job " + j + " has not been modified");
				throw new DaoException("The job " + j + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while modifying the job " + j,sqle);
			throw new DaoException("Error while modifying the job " + j);
		}
		finally {
			close(null,pstmt,cnt);
		}
		
	}
	
	/**
	 * Gets the id of the next job which will be uploaded
	 * @return the id of the job
	 */
	public int getNextJobId() {
		int id = 0 ;
		ResultSet rs = null;
		Statement stmt = null;
		Connection cnt = null;
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery("SELECT nextval('job_jobid_seq')");
			
			if( rs.next() ) 
				id = rs.getInt("nextval");
			else {
				logger.error("The next job Id hasn't been retrieved");
				throw new DaoException("The next job Id hasn't been retrieved");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the next job Id",sqle);
			throw new DaoException("Error while retrieving the job user Id");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return id;
	}
	
	
	/**
	 * Modify the job status
	 * @param courseid course id
	 * @param status job status
	 */
	public void modifyJobStatus(int courseid,String status) {
		
		Connection cnt = null;
		/* Creation of the SQL query string */
		String sql = "UPDATE job SET status = ? ";
		sql += "WHERE courseid = ?";
		
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setString(1, status);
			pstmt.setInt(2, courseid);
			
						
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The job for course " + courseid + " has not been modified");
				throw new DaoException("The job for course " + courseid + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while modifying the job for course " + courseid,sqle);
			throw new DaoException("Error while modifying the job for course " + courseid);
		}
		finally {
			close(null,pstmt,cnt);
		}
		
	}
	
	
	/**
	 * Get job by courseid 
	 * @param courseid the courseid of the job
	 * @return the job
	 */
	public Job getJob(int courseid) {
		Job s = null;
		Connection cnt = null;
		String sql = "SELECT * FROM job WHERE courseid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseid);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				s = new Job(
					rs.getInt("jobid"),
					rs.getInt("courseid"),
					rs.getString("status"),
					rs.getInt("mediatype"),
					rs.getString("coursetype"),
					rs.getString("mediapath"),
					rs.getString("extension")
				);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the job for course " + courseid,sqle);
			throw new DaoException("Error while retrieving the job for course " + courseid);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return s;
	}
		

	/**
	 * Gets the list of all discipline
	 * @return the list of discipline
	 */
	public List<Discipline> getAllDiscipline() {
		
		List<Discipline> l = new ArrayList<Discipline>();
		Connection cnt = null;
		
		String sql = "SELECT * FROM discipline order by namecomp asc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				l.add( new Discipline(
					rs.getInt("disciplineid"),
					rs.getString("codecomp"),
					rs.getString("namecomp"),
					rs.getString("codedom"),
					rs.getString("namedom")
				));
			}
		}	
		catch( SQLException sqle) {
			logger.error("Error while retrieving the list of discipline",sqle);
			throw new DaoException("Error while retrieving the list of discipline");
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return l;
	}
	
	/**
	 * Adds a new discipline
	 * @param d the discipline
	 */
	public void addDiscipline(Discipline d) {
		
		String sql = "INSERT INTO discipline(codecomp,namecomp,codedom,namedom) values(?,?,?,?)";
		PreparedStatement pstmt = null;
		Connection cnt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setString(1, d.getCodecomp());
			pstmt.setString(2, d.getNamecomp());
			pstmt.setString(3, d.getCodedom());
			pstmt.setString(4, d.getNamedom());
			
			if( pstmt.executeUpdate() == 0) {
				logger.error("The discipline " + d + " has not been added to the database");
				throw new DaoException("The discipline " + d + " has not been added to the database");
			}
		}
		catch(SQLException sqle){
			logger.error("Error while adding the new discipline " + d,sqle);
			throw new DaoException("Error while adding the new discipline. The code " + d.getCodecomp() +" must be unique.");
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	
	/**
	 * Modify a discipline
	 * @param d the discipline
	 */
	public void modifyDiscipline(Discipline d) {
		
		Connection cnt = null;
		/* Creation of the SQL query string */
		String sql = "UPDATE discipline SET disciplineid = ?, codecomp = ?, namecomp = ?, codedom = ?, namedom = ? ";
		sql += "WHERE disciplineid = ?";
		
		PreparedStatement pstmt = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			/* Applies the parameters to the query */
			pstmt.setInt(1, d.getDisciplineid());
			pstmt.setString(2, d.getCodecomp());
			pstmt.setString(3, d.getNamecomp());
			pstmt.setString(4, d.getCodedom());
			pstmt.setString(5, d.getNamedom());
			pstmt.setInt(6, d.getDisciplineid());
						
			if( pstmt.executeUpdate() == 0 ) {
				logger.error("The discipline " + d + " has not been modified");
				throw new DaoException("The discipline " + d + " has not been modified");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while modifying the discipline " + d,sqle);
			throw new DaoException("Error while modifying the discipline " + d);
		}
		finally {
			close(null,pstmt,cnt);
		}
		
	}
	
	/**
	 * Gets the id of the next discipline which will be uploaded
	 * @return the id of the discipline
	 */
	public int getNextDisciplineId() {
		int id = 0 ;
		ResultSet rs = null;
		Statement stmt = null;
		Connection cnt = null;
		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery("SELECT nextval('discipline_disciplineid_seq')");
			
			if( rs.next() ) 
				id = rs.getInt("nextval");
			else {
				logger.error("The next discipline Id hasn't been retrieved");
				throw new DaoException("The next discipline Id hasn't been retrieved");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the next discipline Id",sqle);
			throw new DaoException("Error while retrieving the discipline Id");
		}
		finally {
			close(rs,stmt,cnt);
		}
		
		return id;
	}
	
		
	/**
	 * Get discipline by id 
	 * @param id the id of the discipline
	 * @return the discipline
	 */
	public Discipline getDiscipline(int id) {
		Discipline d = null;
		Connection cnt = null;
		String sql = "SELECT * FROM discipline WHERE disciplineid = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				d = new Discipline(
						rs.getInt("disciplineid"),
						rs.getString("codecomp"),
						rs.getString("namecomp"),
						rs.getString("codedom"),
						rs.getString("namedom")
				);
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the discipline " + id,sqle);
			throw new DaoException("Error while retrieving the discipline " + id);
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return d;
	}
	
	
	/**
	 * Deletes a discipline by providing its id
	 * @param disciplineid the id of the discipline
	 */
	public void deleteDiscipline(int disciplineid) {
		
		Connection cnt = null;
		String sql = "DELETE FROM discipline WHERE disciplineid = ?";
		PreparedStatement pstmt = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, disciplineid);
			if( pstmt.executeUpdate() == 0) {
				logger.error("the discipline " + disciplineid + " has not been deleted");
				throw new DaoException("the discipline " + disciplineid + " has not been deleted");
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while deleting the discipline " + disciplineid,sqle);
			throw new DaoException("Error while deleting the discipline " + disciplineid);
		}
		finally {
			close(null,pstmt,cnt);
		}
	}
	
	
	/**
	 * Gets the list of all levels
	 * @return the list of levels
	 */
	public List<Level> getAllLevels() {
		
		List<Level> l = new ArrayList<Level>();
		Connection cnt = null;
		
		String sql = "SELECT * FROM level order by name asc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			cnt = datasrc.getConnection();
			pstmt = cnt.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				l.add( new Level(
					rs.getInt("levelid"),
					rs.getString("code"),
					rs.getString("name")
				));
			}
		}	
		catch( SQLException sqle) {
			logger.error("Error while retrieving the list of level",sqle);
			throw new DaoException("Error while retrieving the list of level");
		}
		finally {
			close(rs,pstmt,cnt);
		}
		
		return l;
	}
	
	
	
	
	/**
	 * Return the result of find tracks function
	 * 
	 * @param params all parameters
	 * @return a list of course
	 */
	public List<Course> getTracks(HashMap<String, String> params) {

		Connection cnt = null;
		List<Course> l = new ArrayList<Course>();

		Set<String> keys = params.keySet();

		/* Creation of the SQL query string */
		String sql = "SELECT * FROM course c WHERE visible = true ";

		if(!params.isEmpty() ) {

			Iterator<String> it = keys.iterator();
			while( it.hasNext()) {				
				String param = it.next();

				if(param.equals("authorName"))
					sql += "AND UPPER(c.name)=UPPER('"+params.get(param)+"') ";

				if(param.equals("authorFirstname"))
					sql += "AND UPPER(c.firstname)=UPPER('"+params.get(param)+"') ";

				if(param.equals("component"))
					sql += "AND UPPER(c.formation) LIKE UPPER('"+params.get(param)+"-%') ";

				if(param.equals("level"))
					sql += "AND UPPER(c.formation) LIKE UPPER('%-"+params.get(param)+"') ";

				if(param.equals("authorLogin"))
					sql += "AND c.userid=(SELECT u.userid FROM \"user\" u WHERE u.login='"+params.get(param)+"') ";

				if(param.equals("idBeg"))
					sql += "AND c.courseid >= "+params.get(param)+" ";

				if(param.equals("idLast"))
					sql += "AND c.courseid <= "+params.get(param)+" ";

				if(param.equals("dateBeg"))
					sql += "AND c.date >= '"+params.get(param)+"' ";

				if(param.equals("dateLast"))
					sql += "AND c.date <= '"+params.get(param)+"' ";

				if(param.equals("tags")) {

					StringTokenizer st = new StringTokenizer(params.get(param));
					while(st.hasMoreTokens()) {
						sql += "AND c.courseid IN (SELECT t.courseid FROM tag t WHERE UPPER(t.tag)=UPPER('"+st.nextToken()+"')) ";
					}

				}
			}

		}
		sql += "ORDER BY date DESC, courseid DESC";

		//System.out.println(sql);

		Statement stmt = null;
		ResultSet rs = null;

		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery(sql);

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
						rs.getInt("userid"),
						rs.getString("adddocname"),
						rs.getBoolean("download"),
						rs.getBoolean("restrictionuds"),
						rs.getInt("mediatype"),
						rs.getShort("volume")
				));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list for find tracks",sqle);
			throw new DaoException("Error while retrieving the courses list for find tracks");
		}
		finally {
			close(rs,stmt,cnt);
		}


		return l;
	}
	
	
	/**
	 * Return the result of find stats function
	 * 
	 * @param params all parameters
	 * @return a list of stats
	 */
	public HashMap<String, Integer> getStats(HashMap<String, String> params) {		
		
		Connection cnt = null;
		HashMap<String, Integer> hm = new HashMap<String, Integer>();

		Set<String> keys = params.keySet();

		/* Creation of the SQL query string */
		String sql = "SELECT count(*) as nbcourses, sum(c.duration) as nbduration, sum(c.consultations) as nbviews, count(DISTINCT (COALESCE(INITCAP(c.name),'') || COALESCE(INITCAP(' ' || c.firstname),''))) AS nbauthors FROM course c WHERE visible = true ";

		if(!params.isEmpty() ) {

			Iterator<String> it = keys.iterator();
			while( it.hasNext()) {				
				String param = it.next();

				if(param.equals("dateBeg"))
					sql += "AND c.date >= '"+params.get(param)+"' ";

				if(param.equals("dateLast"))
					sql += "AND c.date <= '"+params.get(param)+"' ";

				if(param.equals("level"))
					sql += "AND UPPER(c.formation) LIKE UPPER('%-"+params.get(param)+"') ";

				if(param.equals("component"))
					sql += "AND UPPER(c.formation) LIKE UPPER('"+params.get(param)+"-%') ";

				if(param.equals("domain"))
					sql += "AND UPPER(SUBSTRING(formation FROM 1 FOR (ABS(POSITION('-' in formation)-1)))) in (select UPPER(d.codecomp) from discipline d where d.codedom='"+params.get(param)+"') ";

				if(param.equals("account")) {
					if(params.get(param).equals("student"))
						sql += "AND userid in (select userid from \"user\" u where UPPER(u.profile)='"+params.get(param).toUpperCase()+"') ";
					else
						sql += "AND userid in (select userid from \"user\" u where u.type='"+params.get(param)+"') ";
				}
				
				if(param.equals("weekday"))
					sql += "AND to_char(c.date, 'D') in ('"+params.get(param)+"') ";

			}
		}
		//System.out.println(sql);

		Statement stmt = null;
		ResultSet rs = null;

		try {
			cnt = datasrc.getConnection();
			stmt = cnt.createStatement();
			rs = stmt.executeQuery(sql);

			/* Retrieves the records */								
			if(rs.next()) {										
				hm.put("nbcourses", rs.getInt("nbcourses"));
				hm.put("nbduration", rs.getInt("nbduration"));
				hm.put("nbviews", rs.getInt("nbviews"));
				hm.put("nbauthors", rs.getInt("nbauthors"));
			}
		}
		catch( SQLException sqle) {
			logger.error("Error while retrieving the courses list for find tracks",sqle);
			throw new DaoException("Error while retrieving the courses list for find tracks");
		}
		finally {
			close(rs,stmt,cnt);
		}


		return hm;
	}
	
	
}