package org.ulpmm.univrav.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * Provides access to the SQl database
 * @author ULP Multimedia, 2007
 */
public class PgsqlAccess {
	
	/** the connection object */
	private Connection cnt;
	
	/** the datasource object */
	private DataSource ds;
	
	/**
	 * Constructor to initialize datasource
	 * 
	 * @param ds the datasource object
	 */
	public PgsqlAccess(DataSource ds) {
		this.ds = ds;
	}
	
	/**
	 *  Initialization of the connection 
	 */
	public void connect() {
		try {
			if( cnt == null || cnt.isClosed())
				cnt = ds.getConnection();
		}
		catch( SQLException sqle) {
			System.out.println("The connection to the database has failed");
			sqle.printStackTrace();
		}
	}
	
	/**
	 * Gets the connection to the database
	 * @return the connection to the database
	 */
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
	
	/**
	 * Executes a SELECT SQL query without parameters
	 * @param sql the SQL query to execute
	 * @return the Resultset of the query
	 */
	public ResultSet query(String sql) {
		ResultSet rs = null;
		
		try {
			if( cnt == null || cnt.isClosed())
				connect();
			
				Statement stmt = cnt.createStatement();
				rs = stmt.executeQuery(sql);
		}
		catch( SQLException sqle) {
			System.out.println("Error executing the query :" + sql);
			sqle.printStackTrace();
		}
		
		return rs;
	}
	
	/**
	 * Closes the connection to the Database
	 */
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
