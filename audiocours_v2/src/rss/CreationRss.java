package rss;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import paquet.SqlBean;
import paquet.Tools;

/* Classe permettant de créer les flux RSS du site */
public class CreationRss {
	
	/* Requête SQL de sélection des informations à inclure dans les flux RSS */
	private String query;
	
	/* Paramètres éventuels de la requête */
	private String param1, param2;
	
	/* Procédure de lancement de la création des flux RSS de tous les cours */
	public void createAll() {
		this.query = "SELECT * FROM \"AudioCours\" a, \"Media\" m WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\"";
		this.query += " AND a.\"Visible\" = 1 AND \"Genre\" IS NULL ORDER BY \"CreationDate\" DESC, a.\"AudioCoursId\" DESC" ;
		try {
			createRss("Liste de tous les cours","liste_des_cours.xml");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/* Procédure de lancement de la création des flux RSS des enseignants */
	public void createEnseignant() {
		
		SqlBean sqlbean = new SqlBean();
		try {
			/* Recherche de tous les enseignants */
			sqlbean.connect();
			String query_enseignants = "Select distinct \"Who\",\"FirstName\" from \"AudioCours\" where \"Visible\"=1";
			sqlbean.query(query_enseignants);
			while( sqlbean.next() ) {
				String name = (String) sqlbean.getColumn("Who");	//Nom de l'enseignant du cours
				String firstname = (String) sqlbean.getColumn("FirstName"); //Prénom de l'enseignant du cours
				
				if( firstname == null)
					firstname = "";
				
				this.query = "SELECT * FROM \"AudioCours\" a, \"Media\" m WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" ";
				this.query += "AND \"Visible\" = 1 AND \"Genre\" IS NULL AND Initcap(\"Who\") = Initcap(?) ";
				this.query += "AND (Initcap(\"FirstName\") = Initcap(?) OR \"FirstName\" IS NULL ) ORDER BY \"CreationDate\" DESC, a.\"AudioCoursId\" DESC" ;
				this.param1 = name;
				this.param2 = firstname;
				
				/* Création du RSS pour chaque enseignant */
				try {
					String nomFichier = Tools.cleanString(Tools.cleanFileName(name + (! firstname.equals("") ? "_" + firstname : ""))) + ".xml";
					createRss("Liste des cours de " + name + " " + firstname, "rssEnseignant/" + nomFichier);
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				}
			}
			
			sqlbean.disconnect();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/* Procédure de lancement de la création des flux RSS des formations */
	public void createFormation() {
		
		SqlBean sqlbean = new SqlBean();
		try {
			/* Recherche de toutes les formations */
			sqlbean.connect();
			String query_formations = "Select distinct \"Rating\" from \"AudioCours\" where \"Visible\"=1 AND \"Rating\" IS NOT NULL";
			sqlbean.query(query_formations);
			while( sqlbean.next() ) {
				String formation = (String) sqlbean.getColumn("Rating");	// Formation
				if( formation != null ) {
					this.query = "SELECT * FROM \"AudioCours\" a, \"Media\" m WHERE a.\"AudioCoursId\" = m.\"AudioCoursId\" ";
					this.query += "AND \"Visible\" = 1 AND \"Genre\" IS NULL AND Initcap(\"Rating\") = Initcap(?) ";
					this.query += "ORDER BY \"CreationDate\" DESC, a.\"AudioCoursId\" DESC" ;
					this.param1 = formation;
					this.param2 = null;
					
					/* Création du RSS pour chaque formation */
					try {
						String nomFichier = Tools.cleanString(Tools.cleanFileName(formation)) + ".xml";
						createRss("Liste des cours de la formation " + formation, "rssFormation/" + nomFichier);
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					}
				}
			}
			
			sqlbean.disconnect();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/* Procédure qui créer un Document XML à partir d'un nom de flux RSS et d'un nom de fichier, et de la requête sauvegardée dans l'attribut query */
	public void createRss(String rssName, String fileName) throws ParserConfigurationException {
		
		SqlBean sqlbean = new SqlBean();
		
		// Création d'un nouveau DOM
        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructeur = fabrique.newDocumentBuilder();
        Document document = constructeur.newDocument();
        
        // Propriétés du DOM
        document.setXmlVersion("1.0");
        document.setXmlStandalone(true);
        
        // Création de l'arborescence du DOM
        Element racine = document.createElement("rss");
        racine.setAttribute("version", "2.0");
        //racine.appendChild(document.createComment("Commentaire sous la racine"));
        
        Element channel = document.createElement("channel");
        racine.appendChild(channel);
        
        // Ajout des informations sur le flux
        
        Element title = document.createElement("title");
        title.setTextContent("AudioVideoCours");
        channel.appendChild(title);
        
        Element link = document.createElement("link");
        link.setTextContent("http://audiovideocours.u-strasbg.fr/");
        channel.appendChild(link);
        
        Element description = document.createElement("description");
        description.setTextContent(rssName);
        channel.appendChild(description);
        
        Element language = document.createElement("language");
        language.setTextContent("fr");
        channel.appendChild(language);
        
        Element copyright = document.createElement("copyright");
        copyright.setTextContent("ULP Multimedia");
        channel.appendChild(copyright);
        
        /*Element webmaster = document.createElement("webmaster");
        webmaster.setTextContent("christophe.debeire@ulpmm.u-strasbg.fr");
        channel.appendChild(webmaster);*/
        
        // Ajout d'une image au flux RSS
        Element image = document.createElement("image");
        channel.appendChild(image);
        
        Element imageTitle = document.createElement("title");
        imageTitle.setTextContent("AudioVideoCours");
        image.appendChild(imageTitle);
        
        Element urlLink = document.createElement("url");
        urlLink.setTextContent("http://audiovideocours.u-strasbg.fr/_mm/top_h_V2.jpg");
        image.appendChild(urlLink);
        
        Element imageLink = document.createElement("link");
        imageLink.setTextContent("http://audiovideocours.u-strasbg.fr/");
        image.appendChild(imageLink);
        
        // Recherche de cours et création d'un item pour chaque cours
        
        try {
	        sqlbean.connect();
			
	        if( this.param1 == null )
	        	sqlbean.query(this.query);
	        else if( this.param2 == null)
	        	sqlbean.pquery(this.query, this.param1);
	        else
	        	sqlbean.p2query(this.query, this.param1, this.param2);
			
			while( sqlbean.next() ) {
				String AudioCoursId = (String) sqlbean.getColumn("AudioCoursId");
				String Who = (String) sqlbean.getColumn("Who");
				String FirstName = (String) sqlbean.getColumn("FirstName");
				String Rating = (String) sqlbean.getColumn("Rating");
				String Title = (String) sqlbean.getColumn("Title");
				String Object = (String) sqlbean.getColumn("Object");
				String Type = (String) sqlbean.getColumn("Type");
				String urlPod= (String) sqlbean.getColumn("MediaURI");
				
				if(FirstName == null)
					FirstName = "";
				if(Object == null)
					Object = "";
				if(Rating == null)
					Rating = "";
				
				// Création de l'URL des fichiers Podcast
		    	String url_media = Tools.cleanFileName(Title);
				url_media = urlPod.substring(0,urlPod.lastIndexOf("/") + 1 ) 
					+ URLEncoder.encode(url_media,"ISO8859-1");
				
				// Conversion de la date dans le bon format
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		    	Date d = (Date) sqlbean.getColumn("CreationDate"); 
				sdf = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
	        
		        Element item = document.createElement("item");
		        channel.appendChild(item);
		        
		        Element coursGuid = document.createElement("guid");
		        coursGuid.setTextContent(AudioCoursId);
		        coursGuid.setAttribute("isPermaLink","false");
		        item.appendChild(coursGuid);
		        
		        Element coursTitle = document.createElement("title");
		        coursTitle.setTextContent(Title);
		        item.appendChild(coursTitle);
		        
		        Element coursDescription = document.createElement("description");
		        coursDescription.setTextContent("Cours de " + Who + " " + FirstName + ( ! Rating.equals("") ? " dans la formation " + Rating : "") + ": " + Object + ".");
		        item.appendChild(coursDescription);
		        
		        Element coursCategory = document.createElement("category");
		        coursCategory.setTextContent(Type);
		        item.appendChild(coursCategory);
		        
		        Element coursLink = document.createElement("link");
		        coursLink.setTextContent("http://audiovideocours.u-strasbg.fr/differe/interface.jsp?id=" + AudioCoursId + "&player=real");
		        item.appendChild(coursLink);
		        
		        Element coursPubDate = document.createElement("pubDate");
		        coursPubDate.setTextContent(sdf.format(d));
		        item.appendChild(coursPubDate);
		        
		        Element coursEnclosure = document.createElement("enclosure");
		        coursEnclosure.setAttribute("url",url_media + ".mp3");
		        coursEnclosure.setAttribute("type","audio/mpeg");
		        //coursEnclosure.setAttribute("length","");
		        item.appendChild(coursEnclosure);
		        
		        Element coursEnclosure2 = document.createElement("enclosure");
		        coursEnclosure2.setAttribute("url",url_media + ".ogg");
		        coursEnclosure2.setAttribute("type","application/ogg");
		        //coursEnclosure2.setAttribute("length","");
		        item.appendChild(coursEnclosure2);
		        
		        Element coursEnclosure3 = document.createElement("enclosure");
		        coursEnclosure3.setAttribute("url",url_media + ".pdf");
		        coursEnclosure3.setAttribute("type","application/pdf");
		        //coursEnclosure3.setAttribute("length","");
		        item.appendChild(coursEnclosure3);
		        
		        Element coursEnclosure4 = document.createElement("enclosure");
		        coursEnclosure4.setAttribute("url",url_media + ".zip");
		        coursEnclosure4.setAttribute("type","application/zip");
		        //coursEnclosure4.setAttribute("length","");
		        item.appendChild(coursEnclosure4);
		        
			}
			
			sqlbean.disconnect();
		}
		catch( IOException ioe) {
			ioe.printStackTrace();
		}
		
		document.appendChild(racine);
        
		svgXml(document, fileName);
		
	}
	
	/* Procédure permettant de sauvegarder le fichier XML du flux RSS */
	public static void svgXml(Document document, String fichier) {
        try {
	        // Création de la source DOM
	        Source source = new DOMSource(document);
	        
	        // Création du fichier de sortie
	        File file = new File("/var/lib/tomcat5.5/webapps/audiocours_v2/rss/" + fichier);
	        //File file = new File("../../../rss/" + fichier);
	        Result resultat = new StreamResult(file);
	        
	        // Configuration du transformer
	        TransformerFactory fabrique = TransformerFactory.newInstance();
	        Transformer transformer = fabrique.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        
	        // Transformation en fichier XML
	        transformer.transform(source, resultat);
	        
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	
}
