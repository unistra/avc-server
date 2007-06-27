package paquet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import rss.CreationRss;

/* Classe contenant un ensemble de méthodes statiques "utilitaires" */
public class Tools {
	
	/* Fonction permettant de récupérer la durée en secondes d'un fichier son au format WAV */
	public static int dureeWav(File f) {
		int res=0;
		
		try {
			AudioFileFormat aff = AudioSystem.getAudioFileFormat(f);
			AudioFormat af = aff.getFormat();
			res = (int) (aff.getFrameLength() / af.getFrameRate()) ;
		}
		catch(IOException ioe) { res = -1; }
		catch( UnsupportedAudioFileException uafe) { res = -1; }
		catch( ArithmeticException ae) { res = -1; }
			
		return res;
	}
	
	/* Fonction permettant de récupérer la durée en secondes d'un fichier son au format MP3 */
	public static int dureeMp3(File f) {

		try {
			Process p = Runtime.getRuntime().exec("mp3info -p %S " + f.getAbsolutePath() );
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			return Integer.parseInt(br.readLine());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		catch (NumberFormatException nfe) {
			// TODO Auto-generated catch block
			nfe.printStackTrace();
			return -1;
		}

	}
	
	/* Fonction qui génère un ID unique en fonction de la date et de l'heure */
	public static String calculeId2() {
		String id = "(id:c";
		Calendar cal = new GregorianCalendar();
		
		/* Jours */
		String jours = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		if( jours.length() == 1) jours = "0" + jours;
		
		/* Mois */
		String mois = Integer.toString(cal.get(Calendar.MONTH) + 1);
		if( mois.length() == 1) mois = "0" + mois;
		
		/* Années */
		String annees = Integer.toString(cal.get(Calendar.YEAR));
		
		/* Heures */
		String heures = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
		if( heures.length() == 1) heures = "0" + heures;
		
		/* Minutes */
		String minutes = Integer.toString(cal.get(Calendar.MINUTE));
		if( minutes.length() == 1) minutes = "0" + minutes;
		
		/* Secondes */
		String secondes = Integer.toString(cal.get(Calendar.SECOND));
		if( secondes.length() == 1) secondes = "0" + secondes;
		
		id += jours + mois + annees + heures + minutes + secondes + ")";
		
		return id;
	}
	
	/* Fonction qui cherche un ID unique dans une séquence de la base de données */
	public static String calculeId() {
		SqlBean sqlb = new SqlBean();
		
		try {
			sqlb.connect();
			sqlb.query("SELECT nextval('id_seq')");
			if( sqlb.next() ) {
				return ((Long) sqlb.getColumn("nextval")).toString();
			}
			else
				return "";
		}
		catch(IOException ioe){
			return "";
		}
	}
	
	/* Fonction qui supprime les caractères spéciaux indésirables en vue de créer un fichier à ce nom */
	/*public static String cleanFileName(String chaine){
		final String carSpeDebut = "+-.";
		final String carSpeTotal = "/\\<>:*?|\"&'`%();#~";
		
		String res = "";
		
		if( (carSpeDebut.indexOf(chaine.charAt(0)) == -1) && (carSpeTotal.indexOf(chaine.charAt(0)) == -1) )
			res += chaine.charAt(0);
		
		for( int i=1 ; i < chaine.length() ; i++ ) {
			if( carSpeTotal.indexOf(chaine.charAt(i)) == -1 )
				res += chaine.charAt(i);
		}
		
		return res;
	}*/
	
	/* Fonction qui supprime les caractères spéciaux indésirables en vue de créer un fichier à ce nom */
	public static String cleanFileName(String chaine){
		
		String res="";
		
		if( chaine != null ) {
			for(int i=0 ; i< chaine.length() ; i++) {
				char car = chaine.charAt(i);
				if( ! ((car >= 'a' && car <='z') | (car >= 'A' && car <='Z') | (car >= '0' && car <='9')))
					car = '_';
				
				res += car;
			}
		}
		
		return res;
	}
	
	/* Fonction qui supprime les caractères spéciaux indésirables des chaînes de caractères relatives à un cours */
	/* (et les espaces inutiles à la fin ) */
	public static String cleanString(String chaine){
		final String carSpeTotal = "&><\"%#+";
		
		String res = "";
		
		if( chaine != null ) {
			for( int i=0 ; i < chaine.length() ; i++ ) {
				if( carSpeTotal.indexOf(chaine.charAt(i)) == -1 )
					res += chaine.charAt(i);
			}
		}

		/* Suppression des espaces à la fin */
		if( res.length() > 0 ) {
			while( res.charAt(res.length()-1) == ' ' )
				res = res.substring(0,res.length()-1);
		}
		
		return res;
	}
	
	/* Fonction qui retourne le nom d'un amphi à partir de son adresse IP */
	public static String getNomAmphi(String IP){
		
		String nomSalle = "inconnu";
		
		SqlBean sqlb = new SqlBean();
		
		try {
			sqlb.connect();
			sqlb.query("SELECT * FROM \"Live\" WHERE \"Chemin\"='" + IP + "'");
			if( sqlb.next() ) {
				nomSalle = (String) sqlb.getColumn("Lieu") + " -- " + (String) sqlb.getColumn("Salle");
			}
			sqlb.disconnect();
		}
		catch( IOException ioe) {
			ioe.printStackTrace();
		}
		
		/*if( IP.equals("130.79.191.134") )
    		nomSalle = "ILB -- Amphi 1";
    	else if( IP.equals("130.79.191.129") )
    		nomSalle = "ILB -- Amphi 2";
    	else if( IP.equals("130.79.191.130") )
    		nomSalle = "ILB -- Amphi 3";
    	else if( IP.equals("130.79.191.131") )
    		nomSalle = "ILB -- Amphi 4";
    	else if( IP.equals("130.79.191.132") )
    		nomSalle = "ILB -- Amphi 5";
    	else if( IP.equals("130.79.191.133") )
    		nomSalle = "ILB -- Amphi 6";
    	else if( IP.equals("130.79.188.130") )
    		nomSalle = "ATRIUM -- Amphi AT 0";
    	else if( IP.equals("130.79.188.131") )
    		nomSalle = "ATRIUM -- Amphi AT 1";
    	else if( IP.equals("130.79.188.132") )
    		nomSalle = "ATRIUM -- Amphi AT 2";
    	else if( IP.equals("130.79.188.133") )
    		nomSalle = "ATRIUM -- Amphi AT 3";
    	else if( IP.equals("130.79.188.134") )
    		nomSalle = "ATRIUM -- Amphi AT 4";
    	else if( IP.equals("130.79.188.114") )
    		nomSalle = "ATRIUM -- Amphi AT 7";
    	else if( IP.equals("130.79.188.113") )
    		nomSalle = "ATRIUM -- Amphi AT 8";
    	else if( IP.equals("130.79.188.109") )
    		nomSalle = "ATRIUM -- Amphi AT 9";
    	else if( IP.equals("130.79.188.11") )
    		nomSalle = "ATRIUM -- Salle Visio";
    	else if( IP.equals("130.79.190.137") )
    		nomSalle = "MATHS -- Salle C 10";
    	else if( IP.equals("130.79.191.135") )
    		nomSalle = "MATHS -- GAM";
    	else if( IP.equals("130.79.191.136") )
    		nomSalle = "MATHS -- PAM";
    	else if( IP.equals("130.79.16.101") )
    		nomSalle = "SCIENCES DE LA VIE -- Amphi Vles";
    	else if( IP.equals("130.79.135.83") )
    		nomSalle = "SCIENCES DE LA VIE -- Amphi Maresquelle";
    	else if( IP.equals("130.79.133.101") )
    		nomSalle = "PHYSIQUE -- Amphi Fresnel";
    	else if( IP.equals("130.79.40.158") )
    		nomSalle = "CHIMIE -- Amphi Forestier";
    	else if( IP.equals("130.79.136.181") )
    		nomSalle = "PYSCHO -- Amphi Viaud";
    	else if( IP.equals("130.79.123.200") )
    		nomSalle = "GEOGRAPHIE -- Salle M";
    	else if( IP.equals("130.79.70.184") )
    		nomSalle = "PHARMA -- Amphi Gerhardt";
    	else if( IP.equals("130.79.70.185") )
    		nomSalle = "PHARMA -- Amphi Pasteur";
    	else if( IP.equals("130.79.70.134") )
    		nomSalle = "PHARMA -- Salle Ledoux";
    	else if( IP.equals("130.79.70.135") )
    		nomSalle = "PHARMA -- Salle A106";
    	else if( IP.equals("130.79.70.160") )
    		nomSalle = "PHARMA -- Amphi Métais";
    	else if( IP.equals("130.79.70.203") )
    		nomSalle = "PHARMA -- Salle A103";
    	else if( IP.equals("130.79.70.204") )
    		nomSalle = "PHARMA -- Salle A105";
    	else if( IP.equals("130.79.70.206") )
    		nomSalle = "PHARMA -- Salle B100";
    	else if( IP.equals("130.79.30.109") )
    		nomSalle = "IUT";
    	else if( IP.equals("130.79.50.24") )
    		nomSalle = "IPST";
    	else if( IP.equals("130.79.130.206") )
    		nomSalle = "SCIENCES DE L'EDUC -- R4";
    	else if( IP.equals("130.79.57.13") )
    		nomSalle = "IUT - Amphi 4";
    	else
    		nomSalle = "inconnu";*/
		
		return nomSalle;
	}
	
	/* Procédure qui lance la régénération des flux RSS quand un cours est ajouté */
	public static void createRss() {
		CreationRss cr = new CreationRss();
    	cr.createAll();
    	cr.createEnseignant();
    	cr.createFormation();
	}
	
}

