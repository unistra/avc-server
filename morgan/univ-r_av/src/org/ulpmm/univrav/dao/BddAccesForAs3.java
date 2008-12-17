package org.ulpmm.univrav.dao;
/*
 * Connection à une base de données sur postgresql
 * et requête pour obtenir des éléments.
 */

/* Classe représente une connexion à une base de données. */
import java.sql.Connection;
/* 
 * Classe Elle prend en charge le chargement des pilotes
 * et permet de créer de nouvelles connexions à des bases de données.
 * Elle tient à jour, la liste principale des pilotes JDBC recensés du système.
*/
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.Hashtable;
import java.io.FileInputStream;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.*;


public class BddAccesForAs3
{
	  private Hashtable bdd;
	  private String tag;
	  private String idCours;
	  
	  private Connection conn;
	  
	  public BddAccesForAs3() 
	  {
		  bdd=new Hashtable();
		  tag = "test2";
		  conn = null;
		  // connect to the database
		  conn = connectToDatabaseOrDie();
		  // get the data
		  //populateListOfTopics(conn);
	  }

	  private Connection connectToDatabaseOrDie()
	  {
		  Connection conn = null;
		  try
		  {
			  Class.forName("org.postgresql.Driver");
			  String url = "jdbc:postgresql://audiovideocours.u-strasbg.fr:5432/univrav";
			  conn = DriverManager.getConnection(url,"sqluser", "$ulpmmeric1");
		  }
		  catch (ClassNotFoundException e)
		  {
			  e.printStackTrace();
			  System.exit(1);
		  }
		  catch (SQLException e)
		  {
			  e.printStackTrace();
			  System.exit(2);
		  }
		  return conn;
	  }

	  public String getXml(String var, String id)
	  {
		  tag = var;
		  idCours = id;
		  
		  try 
		  {
			  Statement st = conn.createStatement();
			  ResultSet rs = st.executeQuery("SELECT name, firstname, formation, title, description, date, type, duration, consultations, mediafolder FROM course WHERE courseid=" + idCours);
			  rs.next();
			  if(rs.getString("name")!=null)
			  {
				  bdd.put("name", rs.getString("name"));
			  }
			  if(rs.getString("firstname")!=null)
			  {
				  bdd.put("firstname", rs.getString("firstname"));
			  }
			  if(rs.getString("formation")!=null)
			  {
				  bdd.put("formation", rs.getString("formation"));
			  }
			  if(rs.getString("title")!=null)
			  {
				  bdd.put("atitle", rs.getString("title"));
			  }
			  if(rs.getString("title")!=null)
			  {
				  bdd.put("videolink", rs.getString("title"));
			  }
			  if(rs.getString("description")!=null)
			  {
				  bdd.put("description", rs.getString("description"));
			  }
			  if(rs.getString("date")!=null)
			  {
				  bdd.put("date", rs.getString("date"));
			  }
			  if(rs.getString("type")!=null)
			  {
				  bdd.put("type", rs.getString("type"));
			  }
			  if(rs.getString("duration")!=null)
			  {
				  bdd.put("duration", rs.getString("duration"));
			  }
			  if(rs.getString("consultations")!=null)
			  {
				  bdd.put("consultations", rs.getString("consultations"));
			  }
			  if(rs.getString("mediafolder")!=null)
			  {
				  bdd.put("mediafolder", rs.getString("mediafolder"));
			  }
			  rs.close();
			  st.close();
		  }
		  catch (SQLException se) {
			  System.err.println(se.getMessage());
		  }
		  
		  String temp = "";
		  
		  if(tag=="videolink")
		  {
			  //temp = getDataMediafolder();
			  temp = replaceChar();
		  }
		  //else if(tag=="timecode")
		  //{
			  //temp = getTimeCode();
		  //}
		  else
		  {
			  temp = getData();
		  }

		  if(temp==null)
		  {
			  temp = " ";
		  }
		  
		  temp.replace("B", "_");
		  return temp;

	  }
	  
	  public String getTimeCode(String var, String id)
	  {
		  tag = var;
		  idCours = id;
		  
		  try 
		  {
			  Statement st = conn.createStatement();
			  ResultSet rs = st.executeQuery("SELECT mediafolder FROM course WHERE courseid=" + idCours);
			  rs.next();
			  if(rs.getString("mediafolder")!=null)
			  {
				  bdd.put("mediafolder", rs.getString("mediafolder"));
			  }
			  rs.close();
			  st.close();
		  }
		  catch (SQLException se) {
			  System.err.println(se.getMessage());
		  }
		  
		  String adresseCoursSmil = "http://univ-rav.u-strasbg.fr/coursv2/" + (String)bdd.get("mediafolder") + "/timecode.csv";
		  String tablo[] = new String[200];
		  
		  URL u;
		  InputStream is;
		  DataInputStream dis;
		  String ligne;
		  int i=1;
		  try
		  {
			  // Définir l'URL pointant sur le fichier
			  u = new URL (adresseCoursSmil);
	
			  // Lire le flux d'entrée (InputStream) lié au fichier
			  is = u.openStream();
			
			  // En faire un flux de données (DataInputStream)
			  dis = new DataInputStream(is);
			
			  // Lire le fichier jusqu'à la fin
			  ligne = dis.readLine();
			  while (ligne != null)
			  {
				  tablo[i] = ligne;
				  ligne = dis.readLine();
				  i++;
			  }
		  }
		  catch(Exception e)
		  {}

		  String stringTimeCode = "";
		  
		  if(var=="timecode0")
		  {
			  stringTimeCode = String.valueOf(i-1);
		  }
		  else
		  {
			  for(int j=1;j<i;j++)
			  {
				  stringTimeCode = stringTimeCode + tablo[j] + "-";
			  }
		  }
		  return stringTimeCode;
	  }

	  
	  public String getData()
	  {	  
		  return (String)bdd.get(tag);
	  }
	  
	  public String getDataMediafolder()
	  {
		  String temp = (String)bdd.get(tag);
		  int s = 0;
		  int e = 0;
			
			StringBuffer newText = new StringBuffer();
			
			while (((e = temp.indexOf(" ", s)) >= 0)||((e = temp.indexOf("«", s)) >= 0)||((e = temp.indexOf("»", s)) >= 0)) {	
				newText.append(temp.substring(s, e));
			    newText.append("_");
			    s = e + " ".length();
			}
		 
			newText.append(temp.substring(s));
			return newText.toString();
			
	  }
	  
	  public String replaceChar()
	  {
		  String temp = (String)bdd.get(tag);
		  String temp_valide = "";
		  char caractere[] = {32, 33, 34, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 58,
				  59, 60, 61, 62, 63, 64, 91, 92, 93, 94, 96, 123, 124, 125, 126, 128,
				  129, 130, 145, 146, 147, 148, 171, 180, 184, 187, 224, 225, 226, 231,
				  232, 233, 234, 235, 244, 249, 250, 251};
		  char caractereTitre[] = temp.toCharArray();
		  
		  for(int i=0;i<caractereTitre.length;i++)
		  {
			  for(int j=0;j<caractere.length;j++)
			  {
				  if(caractereTitre[i]==caractere[j])
				  {
					  temp_valide = temp_valide + "_";
					  break;
				  }
		   
				  // caractere correct
				  else if(j==caractere.length-1)
				  {
		    	  	String s = String.valueOf(caractereTitre[+i]);  
		    	  	temp_valide = temp_valide + s;
				  }
			  }
		    }
		    return temp_valide;
	  }
	  
	  public String[] getLienMediafolder(String var, String id, boolean boolThumb)
	  {
		  tag = var;
		  idCours = id;
		  
		  URL u;
		  InputStream is;
		  DataInputStream dis;
		  String ligne;
		  int i=0;
			
		  try 
		  {
			  Statement st = conn.createStatement();
			  ResultSet rs = st.executeQuery("SELECT mediafolder FROM course WHERE courseid=" + idCours);
		  rs.next();
		  if(rs.getString("mediafolder")!=null)
		  {
			  bdd.put("mediafolder", rs.getString("mediafolder"));
			  }
			  rs.close();
			  st.close();
		  }
		  catch (SQLException se) {
			  System.err.println(se.getMessage());
		  }
		  
		  String temp = "";
		  temp = "http://univ-rav.u-strasbg.fr/coursv2/";
		  temp = temp + ((String)bdd.get("mediafolder"));
		  temp = temp + "/screenshots/";
		  
		  int nombreImage = 0;
		  
		  for(i=2;i<100;i++){
			  try
			  {
				  // Définir l'URL pointant sur le fichier
				  u = new URL (temp + "/D" + i + ".jpg");
				  // Lire le flux d'entrée (InputStream) lié au fichier
				  is = u.openStream();
				  nombreImage += 1;
			  }
			  catch(Exception e)
			  {
				  break;
			  }
		  }
		  
		  String temp2[] = new String[100];
		  String thumb = "";
		  
		  if(boolThumb==true)
		  {
			  thumb = "-thumb";
		  }	  
  
		  for(i=2;i<nombreImage;i++)
		  {
			  temp2[i] = temp + "D" + i + thumb + ".jpg";
		  }
	  
		  //http://univ-rav.u-strasbg.fr/coursv2/2008-05-29-14h-47m-40s-2474/screenshots/D3-thumb.jpg
		  //String temp = "http://univ-rav.u-strasbg.fr/coursv2/" + (String)bdd.get("mediafolder") + "/screenshots/D3-thumb.jpg";
			return temp2;
		
	  }

	  public String getLienMediafolderTest(String var, String id)
	  {
		  tag = var;
		  idCours = id;
		  File f = new File("C:\\"); 
		  String[] noms = f.list();
		  String temp = "";
		  
		  
		  for (int i = 0; noms != null && i < noms.length; i++)
		  {
			  temp = noms[0] + "-";
		  } 
		  

  
	  //http://univ-rav.u-strasbg.fr/coursv2/2008-05-29-14h-47m-40s-2474/screenshots/D3-thumb.jpg
	  //String temp = "http://univ-rav.u-strasbg.fr/coursv2/" + (String)bdd.get("mediafolder") + "/screenshots/D3-thumb.jpg";
		return temp;
		
	  }
	  
}
