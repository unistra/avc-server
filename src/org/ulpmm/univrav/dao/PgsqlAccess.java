package org.ulpmm.univrav.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author laurent
 *
 */
public class PgsqlAccess {
	
	private final static String DRIVER = "org.postgresql.Driver";
	
	private static String host;
	
	private static String port;
	
	private static String database;
	
	private static String user;
	
	private static String password;
	
	private Connection cnt;
	
	private static PgsqlAccess instance = new PgsqlAccess();
	
	
	public PgsqlAccess() {
		/* Loading of the driver */
		try {
			Class.forName(DRIVER);
		}
		catch( ClassNotFoundException cnfe) {
			System.out.println("Error loading the driver : " + DRIVER);
			cnfe.printStackTrace();
		}
		
		/* Loading of the settings to connect to the database */
		host="stagiaire1.u-strasbg.fr";
		port="5432";
		database="univrav";
		user="sqluser";
		password="$ulpmmeric1";
	}
	
	public static PgsqlAccess getInstance() {
		return instance;
	}
	
	/**
	 *  Initialisation of the connection 
	 *  
	 */
	public void connect() {
		try {
			if( cnt == null || cnt.isClosed())
				cnt = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);
		}
		catch( SQLException sqle) {
			System.out.println("The connection to the database " + database + " has failed");
			sqle.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		try {
			if( cnt == null || cnt.isClosed())
				connect();
		}
		catch( SQLException sqle) {
			System.out.println("Error while retrieving the connection");
			sqle.printStackTrace();
		}
		
		return cnt;
	}
	
	public ResultSet query(String sql) {
		ResultSet rs = null;
		
		try {
			if( cnt != null && ! cnt.isClosed()) {
				Statement stmt = cnt.createStatement();
				rs = stmt.executeQuery(sql);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error executing the query :" + sql);
			sqle.printStackTrace();
		}
		
		return rs;
	}
	
	/*public boolean update(String sql) {
		boolean result = false;
		
		try {
			if( cnt != null && ! cnt.isClosed()) {
				Statement stmt = cnt.createStatement();
				result = stmt.execute(sql);
			}
		}
		catch( SQLException sqle) {
			System.out.println("Error executing the query");
			sqle.printStackTrace();
		}
		
		return result;
	}*/
	
	public void disconnect() {
		try {
			if( cnt != null && ! cnt.isClosed())
				cnt.close();
		}
		catch( SQLException sqle) {
			System.out.println("The database disconnection has failed");
			sqle.printStackTrace();
		}
	}
}
