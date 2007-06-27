package ajout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import paquet.SqlBean;
import paquet.Tools;

/* Classe contenant des fonctions relatives à l'ajout de cours : encodage, SMIL, base de données ... */
public class Fonctions {

	private String id;
	private String titre;
	private String description;
	private String auteur;
	private String prenomAuteur;
	private String date;
	private String formation;
	private String commentaire;
	private String repertoireCode;
	private String repertoireSmil;
	private String urlSmil = "http://audiovideocours.u-strasbg.fr/coursv2/";
	private String nom_media;
	private SqlBean sqlbean;
	private ArrayList timecodes;
	private int dureeMedia;
	private String extension;
	
	/* Constructeur permettant d'initialiser l'objet avec les paramètres envoyées par la page d'upload (du client ou en ligne) */
	public Fonctions(String id, String titre, String description, String auteur, String prenomAuteur, String date, String formation, String commentaire, String repertoireCode, String serverName) {
		this.id = id;
		this.titre = titre;
		this.description = description;
		this.auteur = auteur;
		this.prenomAuteur = prenomAuteur != null ? prenomAuteur : "";
		this.date = date;
		this.formation = formation;
		this.commentaire = commentaire;
		this.repertoireCode = repertoireCode;
		this.urlSmil = "http://" + serverName + "/coursv2/";
		this.sqlbean = new SqlBean();
		
		/* Débarassement des caractères indésirables */
		this.auteur = Tools.cleanString(this.auteur);
		this.prenomAuteur = Tools.cleanString(this.prenomAuteur);
		this.titre = Tools.cleanString(this.titre);
		this.formation = Tools.cleanString(this.formation);
		this.nom_media = this.titre/*.replace(' ', '_')*/;
		this.nom_media = Tools.cleanFileName(this.nom_media);
		
		/* Test que l'ID est bien sous la forme (id:xxxx) */
		if( this.id.length() > 5 && this.id.substring(0, 4).equals("(id:")) {
			this.id = this.id.substring(4, this.id.length()-1);
			
			/* Si on a un (id:no), il faut calculer l'ID */
			if( this.id.equals("no"))
				this.id = Tools.calculeId();
		}
		else
			this.id = Tools.calculeId();
		
	}
	
	// Procédure détectant le type de fichier son envoyé et appelant la méthode qu'il faut
	public int typeFichier(Calendar cal) {
		String nom_wav = "enregistrement-micro.wav";
		String nom_mp3 = "enregistrement-micro.mp3";
		String nom_rm = "enregistrement-video.rm";
		
		File fichier_wav = new File(this.repertoireSmil + nom_wav);
		File fichier_mp3 = new File(this.repertoireSmil + nom_mp3);
		File fichier_rm = new File(this.repertoireSmil + nom_rm);
		
		if( fichier_mp3.exists() ) {
			renommeMp3();
			tag(cal);
			conversion2ogg();
			this.dureeMedia = Tools.dureeMp3(new File(this.repertoireSmil + this.nom_media + ".mp3"));
			this.extension = ".mp3";
			return this.dureeMedia;
		}
		else if( fichier_rm.exists() ) {
			convertRm2Mp3();
			conversion2ogg();
			this.dureeMedia = Tools.dureeMp3(new File(this.repertoireSmil + this.nom_media + ".mp3"));
			this.extension = ".rm";
			return this.dureeMedia;
		}
		else if( fichier_wav.exists() ) {
			encodage(cal);
			this.dureeMedia = Tools.dureeWav(fichier_wav);
			this.extension = ".wav";
			return this.dureeMedia;
		}
		else {
			return -1;
		}
	}
	
	
	// Procédure d'encodage du fichier audio brut en formats compressés (MP3 et OGG)
	public void encodage(Calendar cal) {
		String fichier_son = "enregistrement-micro.wav";
		
		// Encodage du fichier en OGG avec les tags
		String[] commande = new String[8];
		commande[0] = "oggenc";
		commande[1] = "-t" + this.titre;
		commande[2] = "-a" + this.auteur + (this.prenomAuteur.equals("") ? "" : " " + this.prenomAuteur);
		commande[3] = "-d" + this.date;
		commande[4] = ! this.formation.equals("") ? "-l" + this.formation : "";
		commande[5] = "-c" + "comment=" + this.commentaire;
		commande[6] = "-o" + this.nom_media + ".ogg";
		commande[7] = fichier_son;
		try {
			Runtime.getRuntime().exec(commande, null, new File(this.repertoireSmil)); //lancement de la commande d'encodage OGG
		}
		catch(IOException ioe) {ioe.printStackTrace();}					
		
		// Encodage du fichier en MP3 avec les tags
		commande = new String[13];
		commande[0] = "lame";
		commande[1] = "--tt";
		commande[2] = this.titre;
		commande[3] = ! this.auteur.equals("") ? "--ta" : "";
		commande[4] = ! this.auteur.equals("") ? this.auteur  + (this.prenomAuteur.equals("") ? "" : " " + this.prenomAuteur) : "";
		commande[5] = "--ty";
		commande[6] = Integer.toString(cal.get(Calendar.YEAR));
		commande[7] = ! this.formation.equals("") ? "--tl" : "";
		commande[8] = ! this.formation.equals("") ? this.formation : "";
		commande[9] = "--tc";
		commande[10] = this.commentaire;
		commande[11] = fichier_son;
		commande[12] = "enregistrement-micro.mp3";
		try {
			Process p = Runtime.getRuntime().exec(commande, null, new File(this.repertoireSmil)); //lancement de la commande d'encodage MP3
			p.waitFor();
			Runtime.getRuntime().exec("cp " + "enregistrement-micro.mp3" + " " + this.nom_media + ".mp3", null, new File(this.repertoireSmil)); //lancement de la commande d'encodage MP3
		}
		catch( IOException ioe) {ioe.printStackTrace();}
		catch( InterruptedException ie) {ie.printStackTrace();}
	}
	
	/* Procédure qui permet de recréer le fichier MP3 pour qu'il soit lisible avec RealPlayer */
	/* Et qui le renomme avec le titre du cours */
	public void renommeMp3(){
		String fichier_son = "enregistrement-micro.mp3";
		
		try {

			Process p1 = Runtime.getRuntime().exec("bash ./mp3cl.sh " + repertoireSmil + " " + fichier_son, null, new File(repertoireCode + "code")); //lancement du script de conversion
			p1.waitFor();
			
			Process p2 = Runtime.getRuntime().exec("mv clean_enregistrement-micro.mp3 enregistrement-micro.mp3", null, new File(this.repertoireSmil)); //lancement de la commande d'encodage MP3
			p2.waitFor();
			
			Process p3 = Runtime.getRuntime().exec("cp " + fichier_son + " " + this.nom_media + ".mp3", null, new File(this.repertoireSmil)); //lancement de la commande d'encodage MP3
			p3.waitFor();
		}
		catch( IOException ioe) {ioe.printStackTrace();}
		catch( InterruptedException ie) {ie.printStackTrace();}
	}
	
	/* Procédure qui permet d'ajouter les tags MP3 au fichier son du cours */
	public void tag(Calendar cal){
		String[] commande;
		
		commande = new String[12];
		commande[0] = "mp3info";
		commande[1] = "-t";
		commande[2] = this.titre;
		commande[3] = ! this.auteur.equals("") ? "-a" : "";
		commande[4] = ! this.auteur.equals("") ? this.auteur  + (this.prenomAuteur.equals("") ? "" : " " + this.prenomAuteur) : "";
		commande[5] = "-y";
		commande[6] = Integer.toString(cal.get(Calendar.YEAR));
		commande[7] = ! this.formation.equals("") ? "-l" : "";
		commande[8] = ! this.formation.equals("") ? this.formation : "";
		commande[9] = "-c";
		commande[10] = this.commentaire;
		commande[11] = this.nom_media + ".mp3";
		try {
			Runtime.getRuntime().exec(commande, null, new File(this.repertoireSmil)); //lancement de la commande d'encodage MP3
		}
		catch( IOException ioe) {ioe.printStackTrace();}
	}
	
	/* Procédure permettant de convertir un fichier de cours MP3 en OGG */
	public void conversion2ogg() {
		
		try {
			Runtime.getRuntime().exec("mp32ogg " + this.nom_media + ".mp3", null, new File(this.repertoireSmil));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/* 
	public void convertWmv2Rm() {
		
		try {						
			Process p = Runtime.getRuntime().exec("bash code/convertWmv2Rm.sh "  + repertoireSmil, null, new File(this.repertoireCode)); //lancement du script de conversion
			p.waitFor();
		}
		catch(IOException ioe) {ioe.printStackTrace();}
		catch(InterruptedException ie) {ie.printStackTrace();}
	}*/
	
	/* Procédure qui permet de créer un podcast en MP3 d'un cours Real */
	public void convertRm2Mp3() {
		
		try {						
			Process p = Runtime.getRuntime().exec("bash code/ExRmv2mp3.sh "  + repertoireSmil + " " + this.nom_media, null, new File(this.repertoireCode)); //lancement du script de conversion
			p.waitFor();
		}
		catch(IOException ioe) {ioe.printStackTrace();}
		catch(InterruptedException ie) {ie.printStackTrace();}
	}
	
	/* Procédure de création des fichiers SMIL */
	public void creation_smil(String mediaDir) {
		try {
			/*long taille = 0;
			long bitrate;
			
			// Calcul de la taille totale des diapos en octets
			File f = new File(this.repertoireSmil + "screenshots/");
			File[] diapos = f.listFiles();
			for(int i = 0 ; i < diapos.length ; i++){
				if( diapos[i].getName().indexOf("thumb") == -1)
					taille += diapos[i].length();
			}
			//Conversion en kilobits
			taille = (taille / 1024) * 8;
			
			//Calcul du bitrate en bits/secondes
			bitrate = (taille / this.dureeMedia) * 1000;*/
			
			// Lecture du fichier des timecodes et création de la liste des timecodes
			String s;
			this.timecodes = new ArrayList();
			BufferedReader in = new BufferedReader(new FileReader(this.repertoireSmil + "timecode.csv"));
			while( (s = in.readLine()) != null) {
				this.timecodes.add(s); // Remplissage de la liste des timecodes pour ne pas avoir à rouvrir le fichier par la suite
			}
			in.close();
			
			// Lecture du fichier des timecodes et création de la liste des durées
			ArrayList lengths = new ArrayList();
			
			
			// Creation du fichier .smil
			File fichier_smil = new File(this.repertoireSmil + "son.smil");
			fichier_smil.createNewFile();
			PrintWriter pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream( fichier_smil)));
			
			// Début écriture Smil
			pw.println("<?xml version=\"1.0\"?>");
			pw.println("<!DOCTYPE smil PUBLIC \"-//W3C//DTD SMIL 2.0//EN\" \"http://www.w3.org/2001/SMIL20/SMIL20.dtd\">");
			pw.println("<smil xmlns=\"http://www.w3.org/2001/SMIL20/Language\">");
			pw.println("<head>");
			pw.println(! this.titre.equals("") ? "<meta name=\"title\" content=\"" + this.titre + "\"/>" : "");
			pw.println(! this.auteur.equals("") ? "<meta name=\"author\" content=\"" + this.auteur  + (this.prenomAuteur.equals("") ? "" : " " + this.prenomAuteur) + "\"/>" : "");
			pw.println(! this.commentaire.equals("") ? "<meta name=\"copyright\" content=\"" + this.commentaire + "\"/>" : "");
			pw.println("<layout>");
			
			/* Définition des régions en fonction du type de fichier (audio/video) */
			if( ! extension.equals(".rm")) {
				pw.println("<root-layout width=\"1024\" height=\"768\"/>");
				pw.println("<region id=\"Bg\" width=\"1024\" height=\"768\" fit=\"meet\" />");
				pw.println("<region id=\"Images\" width=\"1024\" height=\"768\" fit=\"meet\" />");
			}
			else {
				/*pw.println("<root-layout width=\"400\" height=\"560\"/>");
				pw.println("<region id=\"Video\" top=\"0\" width=\"320\" height=\"240\" fit=\"meet\" />");
				pw.println("<region id=\"Images\" top=\"260\" width=\"400\" height=\"300\" fit=\"meet\" />");*/
				
				/*pw.println("<root-layout width=\"700\" height=\"700\"/>");
				pw.println("<region id=\"Images\" left=\"0\" top=\"240\" width=\"700\" height=\"450\" fit=\"meet\" />");
				pw.println("<region id=\"Video\" left=\"0\" width=\"320\" height=\"240\" fit=\"meet\"/>");*/
				
				pw.println("<root-layout width=\"800\" height=\"780\"/>");
				pw.println("<region id=\"Bg\" left=\"0\" width=\"800\" height=\"780\" fit=\"meet\" />");
				pw.println("<region id=\"Images\" left=\"0\" top=\"180\" width=\"800\" height=\"600\" fit=\"meet\" />");
				pw.println("<region id=\"Video\" left=\"0\" width=\"240\" height=\"180\" fit=\"meet\"/>");
			}
			
			pw.println("</layout>");
			//pw.println("</head>\n<body>\n<par>\n<seq begin=\"" + ((String) this.timecodes.get(0)).replace('.', ',') + "\">\n");
			pw.println("</head>\n<body>\n<par>");
			
			/* Affichage du média audio ou vidéo */
			if( ! extension.equals(".rm")) {
				pw.println("<img region=\"Bg\" src=\"" + this.urlSmil + "modele/bgsmia.jpg\"/>");
				pw.println("<audio src=\"" + this.urlSmil + mediaDir + "/enregistrement-micro.mp3" + "\"/>");
			} else {
				pw.println("<img region=\"Bg\" src=\"" + this.urlSmil + "modele/bgsmi.jpg\"/>");
				pw.println("<video src=\"" + this.urlSmil + mediaDir + "/enregistrement-video.rm" + "\" region=\"Video\" />");
			}
				
			// Première diapo, définir le timing de début et la durée
			if( this.timecodes.size() > 1) {
				Float duree = new Float(Float.parseFloat((String) this.timecodes.get(1)) - Float.parseFloat((String) this.timecodes.get(0)));
				//pw.println("<par><img begin=\"" + this.timecodes.get(0) + "\" dur=\"" + duree + "\" src=\"screenshots/D1.jpg\"/></par>");
				pw.println("<a href=\""+ this.urlSmil + mediaDir + "/screenshots/D2.jpg\"  external=\"true\">" );
				pw.print("<img begin=\"" + Math.round(Float.parseFloat((String) this.timecodes.get(0))));
				pw.println("\" dur=\"" + (int) Math.ceil(duree.floatValue()) + "\" region=\"Images\" src=\"" + this.urlSmil + mediaDir + "/screenshots/D2.jpg\"/>");
				pw.println("</a>");
			}
			
			// Diapos suivantes, définir la durée
			for(int i=1 ; i< this.timecodes.size() -1  ; i++){
				Float duree = new Float(Float.parseFloat((String) this.timecodes.get(i+1)) - Float.parseFloat((String) this.timecodes.get(i)));
				//pw.println("<par><img dur=\"" + duree.toString().replace('.', ',') + "\" region=\"Images\" src=\"screenshots/D" + (i+1) + ".jpg\"/></par>");
				pw.println("<a href=\""+ this.urlSmil + mediaDir + "/screenshots/D" + (i+2) + ".jpg\"  external=\"true\">" );
				pw.print("<img begin=\"" + Math.round(Float.parseFloat((String) this.timecodes.get(i))));
				pw.println("\" dur=\"" + (int) Math.ceil(duree.floatValue()) + "\" region=\"Images\" src=\"" + this.urlSmil + mediaDir + "/screenshots/D" + (i+2) + ".jpg\"/>");
				pw.println("</a>");
			}
			
			// Dernière diapo, pas de durée
			//pw.println("<par><img src=\"screenshots/D" + this.timecodes.size() + ".jpg\"/></par>");
			/*pw.println("<a href=\""+ this.urlSmil + mediaDir + "/screenshots/D" + this.timecodes.size() + ".jpg\"  external=\"true\">" );
			pw.print("<img begin=\"" + Math.round(Float.parseFloat((String) this.timecodes.get(this.timecodes.size()-2))));
			pw.println("\" region=\"Images\" src=\"" + this.urlSmil + mediaDir + "/screenshots/D" + this.timecodes.size() + ".jpg\"/>");
			pw.println("</a>");*/
			
			/*for( int i=0 ; i<this.timecodes.size()-1 ; i++) {
				pw.println("<a href=\""+ this.urlSmil + mediaDir + "/screenshots/D" + (i+2) + ".jpg\"  external=\"true\">" );
				pw.print("<img begin=\"" + Math.round(Float.parseFloat((String) this.timecodes.get(i))));
				pw.println("\" region=\"Images\" src=\"" + this.urlSmil + mediaDir + "/screenshots/D" + (i+2) + ".jpg\"/>");
				pw.println("</a>");
			}*/
			
			// Fin écriture Smil
			pw.println("</par>\n</body>\n</smil>");
			pw.close();
			
			File fichier_ram = new File(this.repertoireSmil + "/son.ram");
			pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream(fichier_ram),"UTF8"));
			pw.println(this.urlSmil + mediaDir + "/son.smil");
			pw.close();
		}
		catch( IOException ioe) {ioe.printStackTrace();}
	}
	
	/* Procédure de lancement du script python de vérification et création des thumbs manquants */
	public void thumbCheck() {
		
		try {
			// Lancement du script python de vérification des thumbs manquants
			Process p = Runtime.getRuntime().exec("python2.4 ./code/thumbCheck.py " + repertoireSmil + "/screenshots", null, new File(this.repertoireCode)); 
			p.waitFor();
		}
		catch(IOException ioe) {ioe.printStackTrace();}
		catch(InterruptedException ie) {ie.printStackTrace();}
	}
	
	/* Procédure qui lance le script python qui créé le PDF à partir des diapos du cours */
	public void creation_pdf() {
		
		try {
			// Lancement de la commande de création du fichier PDF
			Process p = Runtime.getRuntime().exec("python2.5 ./code/CreatePDF.py " + repertoireSmil + " " + this.nom_media, null, new File(this.repertoireCode)); 
			p.waitFor();
		}
		catch(IOException ioe) {ioe.printStackTrace();}
		catch(InterruptedException ie) {ie.printStackTrace();}
	}
	
	/* Procédure réalisant l'ajout du cours dans la base de données */
	public void ajoutBDD(String mediaDir, String IP, String genre){
		String repertoireCours = this.urlSmil + mediaDir + "/";
		String query = "";
		String date2;
		
		try {
			/* Transformation de la date au format de la base de données */
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
	    	Date d = sdf.parse(this.date);
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			date2 = sdf.format(d);
		}
		catch( ParseException pe) {
			date2 = "";
		}
		
		/* Définition du type de cours (audio/video) */
		String type;
		if( ! extension.equals(".rm"))
			type = "audio";
		else
			type = "video";
		
		/* Requêtes d'insertion dans la base de données */
		
		try {
			sqlbean.connect();
			
			if(IP != null)
				IP = "'" + IP + "'";
					
			// Table AudioCours
			query = "INSERT INTO \"AudioCours\"(\"AudioCoursId\",\"Who\",\"FirstName\",\"Rating\",\"Title\",\"Object\",\"Genre\",\"CreationDate\",\"Type\",\"Duration\",\"Podcast\",\"Visible\",\"IPaddress\",\"Consultations\")";
			query += " VALUES(" + this.id + ",?,?,?,?,?,?,'" + date2 + "','" + type + "'," + this.dureeMedia + ",1,1," + IP + ",0)";			
			sqlbean.insertAudiocours(query, this.auteur, this.prenomAuteur, this.formation, this.titre, this.description, genre);
			
			// Table Media
			query = "INSERT INTO \"Media\" VALUES('" + this.id + "','" + repertoireCours + "son.smil','smil',128,'mp3')";
			sqlbean.update(query);
			
			// Table Slide
			for( int i = 0 ; i < this.timecodes.size()-1 ; i++) {
				query = "INSERT INTO \"Slide\" VALUES('" + this.id + "','";
				query += repertoireCours + "screenshots/D" + (i + 2) + ".jpg','Diapositive " + (i + 2) + "'," + this.timecodes.get(i) + ")"; 
				sqlbean.update(query);
			}
			
			sqlbean.disconnect();
		}
		catch( Exception e) { e.printStackTrace(); }
	}
	
	/* Procédure qui créé une archive zip contenant les fichiers SMIL pour téléchargement */
	public void zipsmil() {
		
		String fichier_media = "";
		if( this.extension.equals(".mp3") )
			fichier_media = "enregistrement-micro.mp3";
		else if( this.extension.equals(".rm") )
			fichier_media = "enregistrement-video.rm";
		else if( this.extension.equals(".wav") )
			fichier_media = "enregistrement-micro.wav";
		
		String commande = "zip -r " + this.nom_media + ".zip screenshots son.smil " + fichier_media + " " + this.nom_media + ".pdf";
		
		try {
			Runtime.getRuntime().exec(commande, null, new File(this.repertoireSmil));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/* procédure qui envoie une confirmation d'ajout de cours vers Univ-R */
	public void confirmationUnivr(String login) {

		try {
			/* Vérification dans la base de données */
			sqlbean.connect();
			String query = "SELECT * from \"Univr\" WHERE \"coursId\"=" + this.id + " AND login='" + login + "'";
			sqlbean.query(query);
			
			if( sqlbean.next() ){
				StringBuffer sb = new StringBuffer(  );
			    sb.append( URLEncoder.encode("idaudiocours","ISO8859_15") + "=" );
			    sb.append( URLEncoder.encode( ((Long) sqlbean.getColumn("coursId")).toString(),"ISO8859_15"));
			    sb.append( "&" + URLEncoder.encode("login","ISO8859_15") + "=" );
			    sb.append( URLEncoder.encode(login,"ISO8859_15"));
			    sb.append( "&" + URLEncoder.encode("groupe","ISO8859_15") + "=" );
			    sb.append( URLEncoder.encode( (String) sqlbean.getColumn("codeGroupe"),"ISO8859_15"));
			    sb.append( "&" + URLEncoder.encode("titre","ISO8859_15") + "=" );
			    sb.append( URLEncoder.encode(this.titre,"ISO8859_15"));
			    String postData = sb.toString();
			    
				URL url = new URL( (String) sqlbean.getColumn("url") );
				HttpURLConnection urlcon = (HttpURLConnection) url.openConnection(  );
				urlcon.setRequestMethod("POST");
				urlcon.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
				urlcon.setDoOutput(true);
				urlcon.setDoInput(true);
				PrintWriter pout = new PrintWriter( new OutputStreamWriter(urlcon.getOutputStream()), true );
				pout.print( postData );
				pout.flush();
				
				urlcon.getResponseCode();
	
				  // Affichage du résultat du POST
				  //if ( urlcon.getResponseCode() == HttpURLConnection.HTTP_OK )
					//  res = "POST OK";
				//out.println("POST effectué !");
				  //else {
				  //out.println("Problème lors du POST");
				  //}
			}

	    } catch( UnsupportedEncodingException uee) {
	    	// ...
	    } catch (MalformedURLException e) {
	      //out.println(e);     // mauvaise URL de POST
	    } catch (IOException e2) {
	      //out.println(e2);
	    }
	}
	
	/* Getters et Setters */
	
	public String getId() {
		return this.id;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setRepertoireSmil(String repertoireSmil) {
		this.repertoireSmil = repertoireSmil;
	}
	
}