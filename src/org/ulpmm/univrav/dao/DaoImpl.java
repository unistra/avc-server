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
import org.ulpmm.univrav.entities.Course;
import org.ulpmm.univrav.entities.Slide;
import org.ulpmm.univrav.entities.Smil;

public class DaoImpl implements IDao {

	private static PgsqlAccess pa = PgsqlAccess.getInstance();
	
	private static DaoImpl instance = new DaoImpl();
	
	public static DaoImpl getInstance() {
		return instance;
	}
	
	public void addCourse(Course c) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO course values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
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
			pstmt.executeUpdate();
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the new Course");
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
	
	public List<Course> getAllCourses() {
		pa.connect();
		ResultSet rs = pa.query("SELECT * From course;");
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
					rs.getString("timing")
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
	
	public void deleteCourse(int courseId) {
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM course WHERE courseid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			pstmt.executeUpdate();
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the course");
			sqle.printStackTrace();
		}
		pa.disconnect();
	}

	public Amphi getAmphi(String ip) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Amphi> getAmphis() {
		// TODO Auto-generated method stub
		return null;
	}

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
					rs.getString("timing")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course");
			sqle.printStackTrace();
		}
		pa.disconnect();
		
		return c;
	}

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
					rs.getString("timing")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the course");
			sqle.printStackTrace();
		}
		pa.disconnect();
		
		return c;
	}

	public List<Course> getCourses(HashMap<String, String> params) {
		
		Connection cnt = pa.getConnection();	
		List<Course> l = new ArrayList<Course>();
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
						rs.getString("timing")
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

	public List<Slide> getSlides(int courseId) {
		Connection cnt = pa.getConnection();
		List<Slide> l = new ArrayList<Slide>();
		String sql = "SELECT * FROM slide WHERE courseid = ?";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				l.add(new Slide(
					rs.getInt("slideid"),
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

	public Smil getSmil(int courseId) {
		Smil s = null;
		Connection cnt = pa.getConnection();
		String sql = "SELECT * FROM smil WHERE courseid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) {
				s = new Smil(
					rs.getInt("smilid"),
					rs.getInt("courseid"),
					rs.getString("smilpath")
				);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the smil");
			sqle.printStackTrace();
		}
		pa.disconnect();
		
		return s;
	}

	public void deleteAmphi(String ip) {
		// TODO Auto-generated method stub
		
	}

	public void deleteSlide(int courseId) {
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM slide WHERE courseid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			pstmt.executeUpdate();
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the slide");
			sqle.printStackTrace();
		}
		pa.disconnect();
	}

	public void deleteSmil(int courseId) {
		Connection cnt = pa.getConnection();
		String sql = "DELETE FROM smil WHERE courseid = ?";
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, courseId);
			pstmt.executeUpdate();
		}
		catch( SQLException sqle) {
			System.out.println("Error while deleting the smil");
			sqle.printStackTrace();
		}
		pa.disconnect();
	}

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
			
			pstmt.executeUpdate();
		}
		catch( SQLException sqle) {
			System.out.println("Error while modifying the course");
			sqle.printStackTrace();
		}
		
		pa.disconnect();
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

	public void addAmphi(Amphi a) {
		// TODO Auto-generated method stub
		
	}

	public void addSlide(Slide s) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO Slide values(?,?,?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, s.getSlideid());
			pstmt.setInt(2, s.getCourseid());
			pstmt.setString(3, s.getSlideuri());
			pstmt.setInt(4, s.getSlidetime());
			pstmt.executeUpdate();
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the Slide");
			sqle.printStackTrace();
		}
	}

	public void addSmil(Smil s) {
		Connection cnt = pa.getConnection();
		String sql = "INSERT INTO Smil values(?,?,?)";
		
		try {
			PreparedStatement pstmt = cnt.prepareStatement(sql);
			pstmt.setInt(1, s.getSmilid());
			pstmt.setInt(2, s.getCourseid());
			pstmt.setString(3, s.getSmilpath());
			pstmt.executeUpdate();
			pa.disconnect();
		}
		catch(SQLException sqle){
			System.out.println("Error while adding the Smil");
			sqle.printStackTrace();
		}
	}

	public void modifyAmphi(Amphi a) {
		// TODO Auto-generated method stub
		
	}

}
