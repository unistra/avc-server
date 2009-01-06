package org.ulpmm.univrav.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Accès base de donnée AS3
 * @author morgan
 *
 */
public class BddAccesForAs3 {
	 
	  private static PgsqlAccess pa;
		  
	  /**
	   * Constructeur qui initialise la connection à la bdd
	   */
	  public BddAccesForAs3() {
		 		  
		  try {
			  // Datasource retrieving 
				
				InitialContext cxt = new InitialContext();
				if ( cxt == null ) {
				   throw new Exception("No context found!");
				}
			  
			  DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );

				if ( ds == null ) {
				   throw new Exception("Data source not found!");
				}
			  
			   pa = new PgsqlAccess(ds);
		  }
		  catch (ClassNotFoundException e) {
			  e.printStackTrace();
		  }
		  catch (SQLException e) {
			  e.printStackTrace();
		  } catch (Exception e) {
			e.printStackTrace();
		}
	  }
	 
	  /**
	   * Récupération des infos pour AS3
	   * @param var Name of the parametre
	   * @param id Course Id
	   * @return
	   */
	  public String getXml(String var, String id) {
		  
		  Hashtable<String,String> bdd=new Hashtable<String,String>();
			  
		  try {
			 			  
			  Statement st = pa.getConnection().createStatement();
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
		  }
		  catch (SQLException se) {
			  System.err.println(se.getMessage());
		  }
		  
		  return bdd.get(var)!=null ? bdd.get(var) : "_";
	  }
	  
	  /**
	   * Récupération TimeCode pour AS3
	   * @param var
	   * @param id
	   * @return
	   */
	  public String getTimeCode(String var, String id) {  
		  
		  String stringTimeCode = "";
		  
		  try 
		  {
			  
			  String mediafolder=null;
			  
			  Statement st = pa.getConnection().createStatement();
			  ResultSet rs = st.executeQuery("SELECT mediafolder FROM course WHERE courseid=" + id);
			  rs.next();
			  if(rs.getString("mediafolder")!=null) {
				  mediafolder = rs.getString("mediafolder");
			  }
			  rs.close();
			  st.close();
			  
			  String adresseCoursSmil = "http://univ-rav.u-strasbg.fr/coursv2/" + mediafolder + "/timecode.csv";
			  String tablo[] = new String[200];
			
			  // Définir l'URL pointant sur le fichier
			  URL u = new URL(adresseCoursSmil);
			  // Lire le flux d'entrée (InputStream) lié au fichier
			  InputStream is = u.openStream();
			  // En faire un flux de données 
			  BufferedReader dis = new BufferedReader(new InputStreamReader(is));
			  			 
			  // Lire le fichier jusqu'à la fin
			  int i=1;
			  String ligne = dis.readLine();
			  while (ligne != null)
			  {
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
			  System.err.println(e.getMessage());
		  }
		  
		  return stringTimeCode;
	  }
}
