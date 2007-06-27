package paquet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.servlet.jsp.JspWriter;

public class SqlBean {
	
	private String driver = "org.postgresql.Driver";
	private String url = "jdbc:postgresql://localhost/audiocours_v2";

	/* Paramètres de connexion à la base de données sur Larsen */
	private String user = "";
	private String password = ""; 

	private Connection cnx;
	private ResultSet rs;
	private JspWriter out;
	
	public SqlBean() {}
	
	/* Connexion à la base de données */
	public void connect() throws IOException {
		try {
			Class.forName(this.driver);
	    	this.cnx = DriverManager.getConnection(this.url, this.user, this.password);
		}
		catch( Exception e){
			if( this.out != null)
				this.out.println(e);
		}
	}
	
	/* Requête d'interrogation simple */
	public void query(String s) throws IOException {
		try{
			if( this.cnx != null ) {
				Statement stmt = this.cnx.createStatement();
		    	this.rs = stmt.executeQuery(s);
			}
		}
		catch( SQLException sqle){
			if( this.out != null) {
				this.out.println(sqle);
				this.out.println("erreur d'exécution de la requête");
			}
		}
	}
	
	/* Requête d'interrogation paramétrée pour 1 paramètre (String) */
	public void pquery(String s, String param) throws IOException {
		try{
			if( this.cnx != null ) {
				PreparedStatement pstmt = this.cnx.prepareStatement(s);
				pstmt.setString(1, param);
		    	this.rs = pstmt.executeQuery();
			}
		}
		catch( SQLException sqle){
			if( this.out != null) {
				this.out.println(sqle);
				this.out.println("erreur d'exécution de la requête");
			}
		}
	}
	
	/* Requête d'interrogation paramétrée pour 1 paramètre (int) */
	public void pquery(String s, int param) throws IOException {
		try{
			if( this.cnx != null ) {
				PreparedStatement pstmt = this.cnx.prepareStatement(s);
				pstmt.setInt(1, param);
		    	this.rs = pstmt.executeQuery();
			}
		}
		catch( SQLException sqle){
			if( this.out != null) {
				this.out.println(sqle);
				this.out.println("erreur d'exécution de la requête");
			}
		}
	}
	
	/* Requête d'interrogation paramétrée pour 2 paramètres */
	public void p2query(String s, String param1, String param2) throws IOException {
		try {
			if( this.cnx != null ) {
				PreparedStatement pstmt = this.cnx.prepareStatement(s);
				pstmt.setString(1, param1);
				pstmt.setString(2, param2);
		    	this.rs = pstmt.executeQuery();
			}
		}
		catch( SQLException sqle){
			if( this.out != null) {
				this.out.println(sqle);
				this.out.println("erreur d'exécution de la requête");
			}
		}
	}
	
	/* Requête d'interrogation paramétrée pour 3 paramètres */
	public void p3query(String s, String param1, String param2, String param3) throws IOException {
		try {
			if( this.cnx != null ) {
				PreparedStatement pstmt = this.cnx.prepareStatement(s);
				pstmt.setString(1, param1);
				pstmt.setString(2, param2);
				pstmt.setString(3, param3);
		    	this.rs = pstmt.executeQuery();
			}
		}
		catch( SQLException sqle){
			if( this.out != null) {
				this.out.println(sqle);
				this.out.println("erreur d'exécution de la requête");
			}
		}
	}
	
	/* Requête d'ajout d'un nouveau cours dans la base de données */
	public int insertAudiocours(String req, String auteur, String prenomAuteur, String formation, String titre, String description, String genre) throws IOException{
		try {
			if( this.cnx != null ) {
				PreparedStatement pstmt = this.cnx.prepareStatement(req);
				pstmt.setString(1, auteur);
				
				if(! prenomAuteur.equals(""))
					pstmt.setString(2, prenomAuteur);
				else
					pstmt.setNull(2, Types.VARCHAR);
					
				if(! formation.equals(""))
					pstmt.setString(3, formation);
				else
					pstmt.setNull(3, Types.VARCHAR);
				
				pstmt.setString(4, titre);
				if( ! description.equals(""))
					pstmt.setString(5, description);
				else
					pstmt.setNull(5, Types.VARCHAR);
				if( ! genre.equals(""))
					pstmt.setString(6, genre);
				else
					pstmt.setNull(6, Types.VARCHAR);
		    	
				return pstmt.executeUpdate();
			}
			else
				return 0;
		}
		catch( SQLException sqle){
			if( this.out != null)
				this.out.println(sqle);
			return 0;
		}
	}
	
	/* Requête de modification et ajout simple */
	public int update(String s) throws IOException {
		try {
			if( this.cnx != null ) {
				Statement stmt = this.cnx.createStatement();
		    	return stmt.executeUpdate(s);
			}
			else
				return 0;
		}
		catch( SQLException sqle){
			if( this.out != null)
				this.out.println(sqle);
			return 0;
		}
	}
	
	/* Requête de modification et ajout paramétrée (String) */
	public int pupdate(String s, String param) throws IOException {
		try {
			if( this.cnx != null ) {
				PreparedStatement pstmt = this.cnx.prepareStatement(s);
				pstmt.setString(1, param);
		    	return pstmt.executeUpdate();
			}
			else
				return 0;
		}
		catch( SQLException sqle){
			if( this.out != null)
				this.out.println(sqle);
			return 0;
		}
	}
	
	/* Requête de modification et ajout paramétrée (int) */
	public int pupdate(String s, int param) throws IOException {
		try {
			if( this.cnx != null ) {
				PreparedStatement pstmt = this.cnx.prepareStatement(s);
				pstmt.setInt(1, param);
		    	return pstmt.executeUpdate();
			}
			else
				return 0;
		}
		catch( SQLException sqle){
			if( this.out != null)
				this.out.println(sqle);
			return 0;
		}
	}
	
	/* Déplacement du curseur à l'enregistrement suivant */
	public boolean next() throws IOException {
		try {
			if( this.rs != null )
				return this.rs.next();
			else
				return false;
		}
		catch( SQLException sqle) {
			if( this.out != null) {
				this.out.println(sqle);
				this.out.println("erreur lors de la récupération de l'enregistrement suivant");
			}
			return false;
		}
	}
	
	/* Récupération de la valeur d'une colonne de la ligne courante */
	public Object getColumn(String col) throws IOException {
		try {
			if( this.rs != null )
				return rs.getObject(col);
			else
				return null;
		}
		catch( SQLException sqle){
			if( this.out != null) {
				this.out.println(sqle);
				this.out.println("erreur lors de la récupération de la valeur de la colonne\n");
			}
			return null;
		}
	}
	
	/* Déconnexion de la base de données */
	public void disconnect() throws IOException {
		try {
			if( this.rs != null )
				this.rs.close();
			if( this.cnx != null)
				this.cnx.close();
		}
		catch( Exception e){
			if( this.out != null)
				this.out.println(e);
		}
	}
	
	/* Récupération du JspWriter de la page JSP afin de pouvoir y écrire les messages d'erreur des Exceptions */
	public void setWriter(JspWriter o){
		this.out = o;
	}
	
}
