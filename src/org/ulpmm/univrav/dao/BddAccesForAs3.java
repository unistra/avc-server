package org.ulpmm.univrav.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Database access for AS3 (Full flash)
 * Not used for the moment
 * @author morgan
 *
 */
public class BddAccesForAs3 {
	 
	  /** The pgsql access for the database connection */
	  private DataSource datasrc;
	  
	  /** Logger log4j */
	  private static final Logger logger = Logger.getLogger(BddAccesForAs3.class);
		  
	  /**
	   * Constructor for database connection
	   */
	  public BddAccesForAs3() {
		 		  
		  try {
			  // Datasource retrieving 
			  InitialContext cxt = new InitialContext();
			  if ( cxt == null ) {
				  throw new Exception("No context found!");
			  }
			  
			  datasrc = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );

			  if ( datasrc == null ) {
				  throw new Exception("Data source not found!");
			  }
			  
		  }
		  catch (ClassNotFoundException e) {
			  logger.error("Data source AS3 error",e);
		  }
		  catch (SQLException e) {
			  logger.error("Data source AS3 error",e);
		  } catch (Exception e) {
			  logger.error("Data source AS3 error",e);
		}
	  }
	 
	  /**
	   * Get informations for AS3
	   * @param var Name of the parametre
	   * @param id Course Id
	   * @return a information String
	   */
	  public String getXml(String var, String id) {
		  
		  Hashtable<String,String> bdd=new Hashtable<String,String>();
		  Connection cnt = null;
		  try {		
			  cnt = datasrc.getConnection();
			  Statement st = cnt.createStatement();
			  ResultSet rs = st.executeQuery("SELECT name, firstname, formation, title, description, date, type, duration, consultations, mediafolder FROM course WHERE courseid=" + id);
			  rs.next();
			  if(rs.getString("name")!=null) {
				  bdd.put("name", rs.getString("name"));
			  }
			  if(rs.getString("firstname")!=null) {
				  bdd.put("firstname", rs.getString("firstname"));
			  }
			  if(rs.getString("formation")!=null) {
				  bdd.put("formation", rs.getString("formation"));
			  }
			  if(rs.getString("title")!=null) {
				  bdd.put("atitle", rs.getString("title"));
			  }
			  if(rs.getString("title")!=null) {
				  bdd.put("videolink", rs.getString("title"));
			  }
			  if(rs.getString("description")!=null) {
				  bdd.put("description", rs.getString("description"));
			  }
			  if(rs.getString("date")!=null) {
				  bdd.put("date", rs.getString("date"));
			  }
			  if(rs.getString("type")!=null) {
				  bdd.put("type", rs.getString("type"));
			  }
			  if(rs.getString("duration")!=null) {
				  bdd.put("duration", rs.getString("duration"));
			  }
			  if(rs.getString("consultations")!=null) {
				  bdd.put("consultations", rs.getString("consultations"));
			  }
			  if(rs.getString("mediafolder")!=null) {
				  bdd.put("mediafolder", rs.getString("mediafolder"));
			  }
			  rs.close();
			  st.close();
			  cnt.close();
		  }
		  catch (SQLException se) {
			  logger.error("Xml AS3 error",se);
		  }
		  
		  
		  return bdd.get(var)!=null ? bdd.get(var) : "_";
	  }
	  
	  /**
	   * Get TimeCode for AS3
	   * @param var
	   * @param id
	   * @return Timecode String
	   */
	  public String getTimeCode(String var, String id) {  
		  
		  String stringTimeCode = "";
		  Connection cnt = null;
		  try {
			  cnt = datasrc.getConnection();
			  String mediafolder=null;
			  Statement st = cnt.createStatement();
			  ResultSet rs = st.executeQuery("SELECT mediafolder FROM course WHERE courseid=" + id);
			  rs.next();
			  if(rs.getString("mediafolder")!=null) {
				  mediafolder = rs.getString("mediafolder");
			  }
			  rs.close();
			  st.close();
			  cnt.close();
			  	  
			  String urltimecode = "http://univ-rav.u-strasbg.fr/coursv2/" + mediafolder + "/timecode.csv";
			  String tablo[] = new String[200];
			
			  // Définir l'URL pointant sur le fichier
			  URL u = new URL(urltimecode);
			  // Lire le flux d'entrée (InputStream) lié au fichier
			  InputStream is = u.openStream();
			  // En faire un flux de données 
			  BufferedReader dis = new BufferedReader(new InputStreamReader(is));
			  			 
			  // Lire le fichier jusqu'à la fin
			  int i=1;
			  String ligne = dis.readLine();
			  while (ligne != null) {
				  tablo[i] = ligne;
				  ligne = dis.readLine();
				  i++;
			  }			  
			  
			  if(var=="timecode0") {
				  stringTimeCode = String.valueOf(i-1);
			  }
			  else {
				  for(int j=1;j<i;j++) {
					  stringTimeCode = stringTimeCode + tablo[j] + "-";
				  }
			  }
		  }
		  catch(Exception e) {
			  logger.error("Timecode AS3 error",e);
		  }  
		  return stringTimeCode;
	  }
}
