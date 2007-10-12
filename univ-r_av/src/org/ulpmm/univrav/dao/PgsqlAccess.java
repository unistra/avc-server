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
	
	private String host;
	private String port;
	private String database;
	private String user;
	private String password;
	
	private Connection cnt;
	
	public PgsqlAccess(String host, String port, String database, String user, String password) {
		
		/* Loading of the driver */
		try {
			Class.forName(DRIVER);
		}
		catch( ClassNotFoundException cnfe) {
			System.out.println("Error loading the driver : " + DRIVER);
			cnfe.printStackTrace();
		}
		
		/* Loading of the settings to connect to the database */
		this.host = host;
		this.port = port;
		this.database = database;
		this.user = user;
		this.password = password;
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
