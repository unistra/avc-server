package org.ulpmm.univrav.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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

import org.apache.commons.fileupload.FileItem;
import org.ulpmm.univrav.entities.Course;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileSystemImpl implements IFileSystem {

	private static Runtime r;
	
	private static File scriptsFolder; //folder which contains the scripts
	private static String ftpFolder; // folder in which the courses are uploaded via FTP
	private static String coursesFolder; // folder which contains all the media folders
	private static String coursesUrl;
	private static String defaultMp3File;
	private static String defaultRmFile;
	private static String defaultFlashFile;
	private static String defaultScreenshotsFolder;
	private static String comment;
	
	@SuppressWarnings("static-access")
	public FileSystemImpl(String scriptsFolder, String ftpFolder, String coursesFolder, 
		String liveFolder, String coursesUrl, String defaultMp3File, String defaultRmFile, 
		String defaultSmilFile, String defaultFlashFile, String defaultScreenshotsFolder, 
		String comment) {
		
		r = Runtime.getRuntime();
		this.scriptsFolder = new File(scriptsFolder);
		this.ftpFolder = ftpFolder;
		this.coursesFolder = coursesFolder;
		this.coursesUrl = coursesUrl;
		this.defaultMp3File = defaultMp3File;
		this.defaultRmFile = defaultRmFile;
		this.defaultFlashFile = defaultFlashFile;
		this.defaultScreenshotsFolder = defaultScreenshotsFolder;
		this.comment = comment;
	}
	
	/**
	 * Creates a course with all its media files on the file system
	 * @param c the course to create
	 * @param courseArchive the name of the archive file of the course to create
	 */
	public void addCourse(Course c, String courseArchive) {

		archiveExtraction(c, courseArchive);
		setCourseType(c);
		thumbCheck(c.getMediaFolder());
		
		if( c.getType().equals("audio")) {
			mp3Modif(c.getMediaFolder(), defaultMp3File, c.getMediasFileName() + ".mp3");
			mp3Tag(c, c.getMediaFolder(), c.getMediasFileName());
			setCourseDuration(c, c.getMediaFolder(), c.getMediasFileName());
			pdfCreation(c.getMediaFolder(), c.getMediasFileName());
			smilCreation(c, c.getMediaFolder(), c.getMediasFileName());
			mp3ToOgg(c.getMediaFolder(), c.getMediasFileName());
			courseZip(c, c.getMediaFolder(), c.getMediasFileName());
		}
		else if( c.getType().equals("video")) {
			renameFile(c.getMediaFolder(), defaultRmFile, c.getMediasFileName() + ".rm");
			rmFlvToMp3(c.getMediaFolder(), c.getMediasFileName(), "rm");
			mp3Tag(c, c.getMediaFolder(), c.getMediasFileName());
			setCourseDuration(c, c.getMediaFolder(), c.getMediasFileName());
			pdfCreation(c.getMediaFolder(), c.getMediasFileName());
			smilCreation(c, c.getMediaFolder(), c.getMediasFileName());
			mp3ToOgg(c.getMediaFolder(), c.getMediasFileName());
			courseZip(c, c.getMediaFolder(), c.getMediasFileName());
			rmMkvConvert(c.getMediaFolder(), c.getMediasFileName() + ".rm", c.getMediasFileName());
		}
		else if( c.getType().equals("flash")) {
			renameFile(c.getMediaFolder(), defaultFlashFile, c.getMediasFileName() + ".flv");
			rmFlvToMp3(c.getMediaFolder(), c.getMediasFileName(), "flv");
			mp3Tag(c, c.getMediaFolder(), c.getMediasFileName());
			setCourseDuration(c, c.getMediaFolder(), c.getMediasFileName());
			pdfCreation(c.getMediaFolder(), c.getMediasFileName());
			smilCreation(c, c.getMediaFolder(), c.getMediasFileName());
			mp3ToOgg(c.getMediaFolder(), c.getMediasFileName());
			courseZip(c, c.getMediaFolder(), c.getMediasFileName());
			
			c.setType("video");
		}
	}
	
	/**
	 * Creates a course from an uploaded audio or video media file
	 * @param c the course to create
	 * @param mediaFile the media file of the course to create
	 * @param hq High Quality
	 */
	public void mediaUpload(Course c, FileItem mediaFile,boolean hq) {
		String fileName = mediaFile.getName();
		
		/* Used to fix full path problem with IE */
		if( fileName.indexOf("\\") != -1 ) {
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1,fileName.length());
			mediaFile.setFieldName(fileName);
		}
		
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1,fileName.length());
		
		mediaFolderCreation(c, mediaFile, fileName);
		
		if( extension.equals("mp3") || extension.equals("ogg")) { // audio files
			c.setType("audio");
			if( extension.equals("mp3")) {
				mp3Modif(c.getMediaFolder(), fileName, c.getMediasFileName() + ".mp3");
				mp3Tag(c, c.getMediaFolder(), c.getMediasFileName());
				setCourseDuration(c, c.getMediaFolder(), c.getMediasFileName());
				emptyPdfCopy(c.getMediaFolder(), c.getMediasFileName());
				smilCreation(c, c.getMediaFolder(), c.getMediasFileName());
				mp3ToOgg(c.getMediaFolder(), c.getMediasFileName());
				courseZip(c, c.getMediaFolder(), c.getMediasFileName());
			}
			else if( extension.equals("ogg")) {
				renameFile(c.getMediaFolder(), fileName, c.getMediasFileName() + ".ogg");
				oggTag(c, c.getMediaFolder(), c.getMediasFileName());
				oggToMp3(c.getMediaFolder(), c.getMediasFileName());
				setCourseDuration(c, c.getMediaFolder(), c.getMediasFileName());
				emptyPdfCopy(c.getMediaFolder(), c.getMediasFileName());
				smilCreation(c, c.getMediaFolder(), c.getMediasFileName());
				courseZip(c, c.getMediaFolder(), c.getMediasFileName());
			}
		}
		else if( extension.equals("avi") || extension.equals("divx") || extension.equals("rm") 
				|| extension.equals("rv") || extension.equals("mp4") || extension.equals("mpg") 
    			|| extension.equals("mpeg") || extension.equals("mov") || extension.equals("wmv")
    			|| extension.equals("mkv") || extension.equals("flv")) { // video files
			
			c.setType("flash");
			
			if( extension.equals("flv") )
				renameFile(c.getMediaFolder(), fileName, c.getMediasFileName() + ".flv");
			else
				videoConvert(c.getMediaFolder(), fileName, c.getMediasFileName());
			
			rmFlvToMp3(c.getMediaFolder(), c.getMediasFileName(), "flv");
			mp3Tag(c, c.getMediaFolder(), c.getMediasFileName());
			setCourseDuration(c, c.getMediaFolder(), c.getMediasFileName());
			emptyPdfCopy(c.getMediaFolder(), c.getMediasFileName());
			smilCreation(c, c.getMediaFolder(), c.getMediasFileName());
			mp3ToOgg(c.getMediaFolder(), c.getMediasFileName());
			courseZip(c, c.getMediaFolder(), c.getMediasFileName());
			
			if(hq)  // for High-Quality video
				videoHighQualityConvert(c.getMediaFolder(), fileName, c.getMediasFileName());
				
			c.setType("video");
		}

	}
	
	/**
	 * Reads the timecodes csv file and creates the timecodes list
	 * @param mediaFolder the folder where the timecode list is stored
	 * @return the timecodes list
	 */
	public ArrayList<String> getTimecodes(String mediaFolder) {
		String s;
		ArrayList<String> timecodes = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(coursesFolder + mediaFolder + "/timecode.csv"));
			while( (s = in.readLine()) != null) {
				timecodes.add(s);
			}
			in.close();
		}
		catch(IOException ioe) {
			System.out.println("Impossible to load the timecodes file");
			ioe.printStackTrace();
		}
		return timecodes;
	}
	
	/**
	 * Removes the media folder of a course on the file system
	 * @param mediaFolder the media folder of the course
	 */
	public void deleteCourse(String mediaFolder) {
		if( mediaFolder != null && ! mediaFolder.equals("")) {
			try {
				Process p = r.exec("rm -Rf " + coursesFolder + mediaFolder);
				if( p.waitFor() != 0)
					System.out.println("the course folder " + mediaFolder + " mediaFolder has not been deleted");
			}
			catch(IOException ioe) {
				System.out.println("Impossible to delete the course folder");
				ioe.printStackTrace();
			}
			catch(InterruptedException ie) {
				System.out.println("Impossible to delete the course folder");
				ie.printStackTrace();
			}
		}
	}
	
	/**
	 * Checks wether a video amphi is diffusing an audio stream or a video stream
	 * @param amphiIp the Ip address of the video amphi
	 * @param audioLivePort the port used by the audio live
	 * @return the stream type diffused by the amphi
	 */
	public String getLiveStreamType(String amphiIp, int audioLivePort) {
		
		String result="";
		
		try {
			String command = "wget -o live_" + amphiIp + ".log --timeout=2 -t2 --spider -S http://" + amphiIp + ":" + audioLivePort;
			Process p = r.exec(command, null, new File("/tmp"));
			p.waitFor();
			
			FileInputStream fis = new FileInputStream("/tmp/live_" + amphiIp + ".log");
			BufferedReader entree = new BufferedReader(new InputStreamReader(fis));
			String text="";
			while( (text = entree.readLine()) != null )
				result+= text;
		}
		catch( IOException ioe) {
			System.out.println("Error while checking the Stream type for the amphi " + amphiIp);
			ioe.printStackTrace();
		}
		catch( InterruptedException ie) {
			System.out.println("Error while checking the Stream type for the amphi " + amphiIp);
			ie.printStackTrace();
		}
		
		if( result.indexOf("200 OK") != -1)
			return "audio";
		else
			return "video";
	}
	
	/**
	 * Retrieves a list of the website's available themes
	 * @param stylesFolder the folder in which the themes are stored
	 * @return the list of themes
	 */
	public List<String> getStyles(String stylesFolder) {
		File f = new File(stylesFolder);
		return Arrays.asList(f.list());
	}
	
	/**
	 * Retrieves a list of the website's available languages
	 * @param languagesFolder the folder in which the language property files are stored
	 * @return the list of languages
	 */
	public List<String> getLanguages(String languagesFolder) {
		File f = new File(languagesFolder);
		String[] files = f.list();
		ArrayList<String> languages = new ArrayList<String>();
		for( int i=0 ; i<files.length ; i++ ) {
			if(files[i].endsWith(".properties")) {
				String language = files[i].substring(files[i].indexOf('_') + 1, files[i].indexOf('.'));
				languages.add(language);
			}
		}
		return languages;
	}
	
	/**
	 * Sends a file to the client's browser
	 * @param filename the name of the file to send
	 * @param out the stream in which send the file
	 */
	public void returnFile(String filename, OutputStream out) {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filename));
			byte[  ] buf = new byte[4 * 1024];  // 4K buffer
			int bytesRead;
			while ((bytesRead = in.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
		}
		catch( Exception e) {
			System.out.println("Error while sending the file " + filename + " to the client");
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				try {
					in.close(  );
				}
				catch( IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Sends a message over a socket to the Univ-R AV client
	 * @param message the message to send
	 * @return the answer of the client
	 */
	public String sendMessageToClient(String message, String ip, int port) {
		
		String answer = "";
		
		try {
			/* Sends the message to the client */
			Socket client = new Socket(ip, port);
			PrintWriter output = new PrintWriter(client.getOutputStream());
			output.println(message);
			output.flush();
			/* Reception of the answer */
			BufferedReader entree = new BufferedReader(new InputStreamReader(client.getInputStream()));
			answer = entree.readLine();
			client.close();
		}
		catch( IOException ioe) {
			System.out.println("Error while sending the message to the client");
			ioe.printStackTrace();
		}
		
		return answer;
	}
	
	/**
	 * Creates a RSS files for a list of courses
	 * @param courses the list of courses
	 * @param filePath the full path of the RSS file to create
	 * @param rssName the name of the RSS files
	 * @param rssTitle the title of the RSS file
	 * @param rssDescription the description of the RSS file
	 * @param serverUrl the URL of the application on the server
	 * @param rssImageUrl the URL of the RSS image file
	 * @param recordedInterfaceUrl the URL of the recorded interface
	 * @param language the language of the RSS file
	 * @throws ParserConfigurationException
	 */
	public void rssCreation( List<Course> courses, String filePath, String rssName, 
			String rssTitle, String rssDescription, String serverUrl, String rssImageUrl, 
			String recordedInterfaceUrl, String language ) {
		
		try {		
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
	        
	        Element channel = document.createElement("channel");
	        racine.appendChild(channel);
	        
	        // Ajout des informations sur le flux
	        
	        Element title = document.createElement("title");
	        title.setTextContent(rssTitle);
	        channel.appendChild(title);
	        
	        Element link = document.createElement("link");
	        link.setTextContent(serverUrl);
	        channel.appendChild(link);
	        
	        Element description = document.createElement("description");
	        description.setTextContent(rssDescription);
	        channel.appendChild(description);
	        
	        Element lang = document.createElement("language");
	        lang.setTextContent(language);
	        channel.appendChild(lang);
	        
	        Element cr = document.createElement("copyright");
	        cr.setTextContent(comment);
	        channel.appendChild(cr);
	        
	        // Ajout d'une image au flux RSS
	        Element image = document.createElement("image");
	        channel.appendChild(image);
	        
	        Element imageTitle = document.createElement("title");
	        imageTitle.setTextContent(rssTitle);
	        image.appendChild(imageTitle);
	        
	        Element urlLink = document.createElement("url");
	        urlLink.setTextContent(rssImageUrl);
	        image.appendChild(urlLink);
	        
	        Element imageLink = document.createElement("link");
	        imageLink.setTextContent(serverUrl);
	        image.appendChild(imageLink);
	        
	        // Recherche de cours et création d'un item pour chaque cours
			for( Course course : courses) {
				
				if( course.getTitle() != null) {
					// Conversion de la date dans le bon format	
			    	Date d = course.getDate();
			    	SimpleDateFormat sdf = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
		        
			        Element item = document.createElement("item");
			        channel.appendChild(item);
			        
			        Element coursGuid = document.createElement("guid");
			        coursGuid.setTextContent(rssName + "_" + course.getCourseid());
			        coursGuid.setAttribute("isPermaLink","false");
			        item.appendChild(coursGuid);
			        
			        Element coursTitle = document.createElement("title");
			        coursTitle.setTextContent(course.getTitle());
			        item.appendChild(coursTitle);
			        
			        Element coursDescription = document.createElement("description");
			        coursDescription.setTextContent(
			        		(course.getName() != null ? course.getName() : "") 
			        		+ (! (course.getName() == null || course.getFirstname() == null) ? " " : "")
			        		+ (course.getFirstname() != null ? course.getFirstname() : "")
			        		+ (! ((course.getName() == null && course.getFirstname() == null) || course.getFormation() == null) ? " - " : "")
			        		+ (course.getFormation() != null ? course.getFormation() : "")
			        		+ (! ((course.getName() == null && course.getFirstname() == null && course.getFormation() == null) || course.getDescription() == null) ? " : " : "")
			        		+ (course.getDescription() != null ? course.getDescription() : ""));
			        item.appendChild(coursDescription);
			        
			        Element coursCategory = document.createElement("category");
			        coursCategory.setTextContent(course.getType());
			        item.appendChild(coursCategory);
			        
			        Element coursLink = document.createElement("link");
			        coursLink.setTextContent(recordedInterfaceUrl + "?id=" + course.getCourseid() + "&type=real");
			        item.appendChild(coursLink);
			        
			        Element coursPubDate = document.createElement("pubDate");
			        coursPubDate.setTextContent(sdf.format(d));
			        item.appendChild(coursPubDate);
			        
			        String courseMediaUrl = coursesUrl + course.getMediaFolder() + "/" + course.getMediasFileName();
			        
			        Element coursEnclosure = document.createElement("enclosure");
			        coursEnclosure.setAttribute("url",courseMediaUrl + ".mp3");
			        coursEnclosure.setAttribute("type","audio/mpeg");
			        coursEnclosure.setAttribute("length", Long.toString(getContentLength(coursesFolder + course.getMediaFolder() + "/" + course.getMediasFileName() + ".mp3")));
			        item.appendChild(coursEnclosure);
			        
			        Element coursEnclosure2 = document.createElement("enclosure");
			        coursEnclosure2.setAttribute("url",courseMediaUrl + ".ogg");
			        coursEnclosure2.setAttribute("type","application/ogg");
			        coursEnclosure2.setAttribute("length", Long.toString(getContentLength(coursesFolder + course.getMediaFolder() + "/" + course.getMediasFileName() + ".ogg")));
			        item.appendChild(coursEnclosure2);
			        
			        Element coursEnclosure3 = document.createElement("enclosure");
			        coursEnclosure3.setAttribute("url",courseMediaUrl + ".pdf");
			        coursEnclosure3.setAttribute("type","application/pdf");
			        coursEnclosure3.setAttribute("length", Long.toString(getContentLength(coursesFolder + course.getMediaFolder() + "/" + course.getMediasFileName() + ".pdf")));
			        item.appendChild(coursEnclosure3);
			        
			        Element coursEnclosure4 = document.createElement("enclosure");
			        coursEnclosure4.setAttribute("url",courseMediaUrl + ".zip");
			        coursEnclosure4.setAttribute("type","application/zip");
			        coursEnclosure4.setAttribute("length", Long.toString(getContentLength(coursesFolder + course.getMediaFolder() + "/" + course.getMediasFileName() + ".zip")));
			        item.appendChild(coursEnclosure4);
				}
			}
		        
			document.appendChild(racine);
	        
			svgXml(document, filePath);
		}
		catch( ParserConfigurationException pce) {
			System.out.println("Error while creating the RSS file " + filePath);
			pce.printStackTrace();
		}
	}
	
	/**
	 * Retrieves information about used and free disk space on the server
	 * @return the string containing the info
	 */
	public String getDiskSpaceInfo() {
		
		String result = "";
		
		try {
			/* Execution of the df program */
			Process p = r.exec("df -m");
			if( p.waitFor() != 0) {
				System.out.println("Error while getting disk space info");
				throw new DaoException("Error while getting disk space info");
			}
			
			InputStream is = p.getInputStream();
			BufferedReader entree = new BufferedReader(new InputStreamReader(is));
			String text="";
			while( (text = entree.readLine()) != null )
				result+= "\n" + text ;
		}
		catch( Exception e ) {
			System.out.println("Error while getting disk space info");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Extracts the course archive file to the courses folder and renames it
	 * @param c the course corresponding to the archive
	 * @param courseArchive the course archive file
	 */
	private static void archiveExtraction(Course c, String courseArchive) {
		String extension;
		
		if( courseArchive.length() > 3) {
			extension = courseArchive.substring(courseArchive.length()-4, courseArchive.length());
			String tmpMediaFolder = courseArchive.substring(0,courseArchive.lastIndexOf(".")); //uncompressed media folder
			String mediaFolder = tmpMediaFolder + "-" + c.getCourseid(); // new (renamed) media folder
			c.setMediaFolder(mediaFolder);
			
			if( ! new File(coursesFolder + mediaFolder).exists()) {	
				try {
					/* Archive extraction according to its type to the courses folder */
					if( extension.equals(".zip")){
					
						/* Zip extracting */
						Process p = r.exec("unzip " + ftpFolder + courseArchive, null, new File(coursesFolder));
						if( p.waitFor() != 0) {
							System.out.println("The course archive " + courseArchive + " has not been extracted");
		        			throw new DaoException("The course archive " + courseArchive + " has not been extracted");
						}
		        		/* Renaming of the extracted folder to have a unique one */
		        		p = r.exec("mv " + tmpMediaFolder + " " + mediaFolder, null, new File(coursesFolder));
		        		if( p.waitFor() != 0) {
		        			System.out.println("The course archive " + courseArchive + " has not been renamed");
		        			throw new DaoException("The course archive " + courseArchive + " has not been renamed");
		        		}
					}
					else {			
						/* Tar extracting */
						Process p = r.exec("tar xf " + ftpFolder + courseArchive, null, new File(coursesFolder));
						if( p.waitFor() != 0) {
							System.out.println("The course archive " + courseArchive + " has not been extracted");
		        			throw new DaoException("The course archive " + courseArchive + " has not been extracted");
						}
						/* Renaming of the extracted folder to have a unique one */
						p = r.exec("mv " + tmpMediaFolder + " " + mediaFolder, null, new File(coursesFolder));
						if( p.waitFor() != 0) {
							System.out.println("The course archive " + courseArchive + " has not been renamed");
		        			throw new DaoException("The course archive " + courseArchive + " has not been renamed");
						}
					}
				}
				catch (IOException ioe) {
					System.out.println("Error while extracting the course archive");
					ioe.printStackTrace();
				}
				catch (InterruptedException ie) {
					System.out.println("Error while extracting the course archive");
					ie.printStackTrace();
				}
			}
			else {
				System.out.println("The course folder " + mediaFolder + " already exists");
				throw new DaoException("The course folder " + mediaFolder + " already exists" );
			}
		}
		else {
			System.out.println("Incorrect archive file to extract: " + courseArchive );
			throw new DaoException("Incorrect archive file to extract: " + courseArchive );
		}
	}
	
	/**
	 * Sets the "type" attribute of the Course object by identifying the media file
	 * @param c the course
	 */
	private static void setCourseType(Course c) {
		
		if( new File(coursesFolder + c.getMediaFolder() + "/" + defaultMp3File).exists()) 
			c.setType("audio");
		else if(new File(coursesFolder + c.getMediaFolder() + "/" + defaultRmFile).exists())
			c.setType("video");
		else if(new File(coursesFolder + c.getMediaFolder() + "/" + defaultFlashFile).exists())
			c.setType("flash");
		else {
			System.out.println("No course media file found in the " + coursesFolder + c.getMediaFolder() + " folder");
			throw new DaoException("No course media file found in the " + coursesFolder + c.getMediaFolder() + " folder");
		}
	}
	
	/**
	 * Launches a python script which creates the missing screenshots thumbs
	 * @param mediaFolder the folder which contains the media files of a course
	 */
	private static void thumbCheck(String mediaFolder) {
		try {
			Process p = r.exec("python2.5 thumbCheck.py " + coursesFolder + mediaFolder + "/" + defaultScreenshotsFolder, null, scriptsFolder); 
			if( p.waitFor() != 0 ) {
				System.out.println("Error while checking the course thumbs in the folder " + coursesFolder + mediaFolder + "/" + defaultScreenshotsFolder);
				throw new DaoException("Error while checking the course thumbs in the folder " + coursesFolder + mediaFolder + "/" + defaultScreenshotsFolder);
			}
		}
		catch(IOException ioe) {
			System.out.println("Error while checking the course thumbs in the folder " + coursesFolder + mediaFolder + "/" + defaultScreenshotsFolder);
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while checking the course thumbs in the folder " + coursesFolder + mediaFolder + "/" + defaultScreenshotsFolder);
			ie.printStackTrace();
		}
	}
	
	/**
	 * Regenerates the mp3 file to fix the problems with RealPlayer and renames it
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param oldFileName the old name of the rm/rv file
	 * @param newFileName the new name of the rm/rv file
	 */
	private static void mp3Modif(String mediaFolder, String oldFileName, String newFileName) {
		
		try {
			/* Regeneration of the mp3 file to fix the play problems with RealPlayer */
			Process p = r.exec("bash ./mp3cl.sh " + coursesFolder + mediaFolder + " " + oldFileName, null, scriptsFolder);
			if( p.waitFor() != 0 ) {
				System.out.println("Error while cleaning the mp3 file " + oldFileName);
				throw new DaoException("Error while cleaning the mp3 file " + oldFileName);
			}
			
			/* Renames the mp3 file */
			p = r.exec("mv clean_" + oldFileName + " " + newFileName, null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 ) {
				System.out.println("Error while renaming the mp3 file clean_" + oldFileName);
				throw new DaoException("Error while renaming the mp3 file clean_" + oldFileName);
			}
			
			/* Removes the default media file to free disk space */
			p = r.exec("rm " + oldFileName, null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 ) {
				System.out.println("Error while deleting the media file " + oldFileName);
				throw new DaoException("Error while deleting the media file " + oldFileName);
			}
		}
		catch( IOException ioe) {
			System.out.println("Error while modifying the mp3 file");
			ioe.printStackTrace();
		}
		catch( InterruptedException ie) {
			System.out.println("Error while modifying the mp3 file");
			ie.printStackTrace();
		}
	}
	
	/**
	 * Adds the id3 tags to the mp3 file
	 * @param c the course
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */ 
	private static void mp3Tag(Course c, String mediaFolder, String mediaFileName) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			ArrayList<String> command = new ArrayList<String>();
			
			command.add("mp3info");
			if( c.getTitle() != null) {
				command.add("-t");
				command.add(c.getTitle());
			}
			if( c.getName() != null) {
				command.add("-a");
				command.add(c.getName() + (c.getFirstname() == null ? "" : " " + c.getFirstname()));
			}
			command.add("-y");
			command.add(sdf.format(c.getDate()));
			if( c.getFormation() != null) {
				command.add("-l");
				command.add(c.getFormation());
			}
			if( comment != null) {
				command.add("-c");
				command.add(comment);
			}
			command.add(mediaFileName + ".mp3");

			Process p = r.exec(command.toArray(new String[command.size()]), null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 ) {
				System.out.println("Error while adding the tags to the mp3 file " + mediaFileName + ".mp3");
				throw new DaoException("Error while adding the tags to the mp3 file " + mediaFileName + ".mp3");
			}
		}
		catch( IOException ioe) {
			System.out.println("Error while adding the tags to the mp3 file " + mediaFileName + ".mp3");
			ioe.printStackTrace();
		}
		catch( InterruptedException ie) {
			System.out.println("Error while adding the tags to the mp3 file " + mediaFileName + ".mp3");
			ie.printStackTrace();
		}
	}
	
	/**
	 * Adds the tags to the ogg file
	 * @param c the course
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */ 
	private static void oggTag(Course c, String mediaFolder, String mediaFileName) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			ArrayList<String> command = new ArrayList<String>();
			
			command.add("lltag");
			command.add("--clear");
			command.add("--yes");
			if( c.getTitle() != null) {
				command.add("-t");
				command.add(c.getTitle());
			}
			if( c.getName() != null) {
				command.add("-a");
				command.add(c.getName() + (c.getFirstname() == null ? "" : " " + c.getFirstname()));
			}
			command.add("-d");
			command.add(sdf.format(c.getDate()));
			if( c.getFormation() != null) {
				command.add("-A");
				command.add(c.getFormation());
			}
			if( comment != null) {
				command.add("-c");
				command.add(comment);
			}
			command.add(mediaFileName + ".ogg");

			Process p = r.exec(command.toArray(new String[command.size()]), null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 ) {
				System.out.println("Error while adding the tags to the ogg file " + mediaFileName + ".ogg");
				throw new DaoException("Error while adding the tags to the ogg file " + mediaFileName + ".ogg");
			}
		}
		catch( IOException ioe) {
			System.out.println("Error while adding the tags to the ogg file " + mediaFileName + ".ogg");
			ioe.printStackTrace();
		}
		catch( InterruptedException ie) {
			System.out.println("Error while adding the tags to the ogg file " + mediaFileName + ".ogg");
			ie.printStackTrace();
		}
	}
	
	/** 
	 * Renames a file
	 * @param oldFileName the old name of the file
	 * @param newFileName the new name of the file
	 */ 
	private static void renameFile( String mediaFolder, String oldFileName, String newFileName) {
		try {
			Process p = r.exec("mv " + oldFileName + " " + newFileName, null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 ) {
				System.out.println("Error while renaming the file " + oldFileName);
				throw new DaoException("Error while renaming the file " + oldFileName);
			}
		} catch (IOException ioe) {
			System.out.println("Error while renaming the file " + oldFileName);
			ioe.printStackTrace();
		}
		catch( InterruptedException ie) {
			System.out.println("Error while renaming the file " + oldFileName);
			ie.printStackTrace();
		}
	}
	
	/** 
	 * Launches a bash script which converts a rm or flv media file to mp3
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */
	private static void rmFlvToMp3( String mediaFolder, String mediaFileName, String mediaFileExtension) {
		try {
			Process p = r.exec("bash ExRmvFlv2mp3.sh "  + coursesFolder + mediaFolder + " " + mediaFileName + " " + mediaFileExtension, null, scriptsFolder);
			if( p.waitFor() != 0 ) {
				System.out.println("Error while converting the file " + mediaFileName + "." + mediaFileExtension + " to mp3");
				throw new DaoException("Error while converting file " + mediaFileName + "." + mediaFileExtension + " to mp3");
			}
		}
		catch(IOException ioe) {
			System.out.println("Error while converting file " + mediaFileName + "." + mediaFileExtension + " to mp3");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while converting file " + mediaFileName + "." + mediaFileExtension + " to mp3");
			ie.printStackTrace();
		}
	}
	
	/** 
	 * Sets the "duration" attribute of the Course object by identifying the media file
	 * @param c the course
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */
	private static void setCourseDuration(Course c, String mediaFolder, String mediaFileName) {
		int duration;
		
		try {
			Process p = r.exec("mp3info -p %S " +  mediaFileName + ".mp3", null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 ) {
				System.out.println("Error while getting the length of the file " + mediaFileName + ".mp3");
				throw new DaoException("Error while getting the length of the file " + mediaFileName + ".mp3");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			duration = Integer.parseInt(br.readLine());
			c.setDuration(duration);
			
		} catch (IOException e) {
			System.out.println("Error while getting the length of the file " + mediaFileName + ".mp3");
			e.printStackTrace();
		}
		catch (NumberFormatException nfe) {
			System.out.println("Error while getting the length of the file " + mediaFileName + ".mp3");
			nfe.printStackTrace();
		}
		catch( InterruptedException ie) {
			System.out.println("Error while getting the length of the file " + mediaFileName + ".mp3");
			ie.printStackTrace();
		}
	}
	
	/** 
	 * Converts the mp3 file to a ogg file using the mp32ogg command
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */
	private static void mp3ToOgg(String mediaFolder, String mediaFileName) {
		try {
			Process p = r.exec("mp32ogg " + mediaFileName + ".mp3", null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 ) {
				System.out.println("Error while converting the mp3 file " + mediaFileName + ".mp3 to ogg");
				throw new DaoException("Error while converting the mp3 file " + mediaFileName + ".mp3 to ogg");
			}
		} catch (IOException ioe) {
			System.out.println("Error while converting the mp3 file " + mediaFileName + ".mp3 to ogg");
			ioe.printStackTrace();
		}
		catch( InterruptedException ie) {
			System.out.println("Error while converting the mp3 file " + mediaFileName + ".mp3 to ogg");
			ie.printStackTrace();
		}
	}
	
	/** 
	 * Converts an ogg file to a mp3 file using the ogg2mp3 command
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */
	private static void oggToMp3(String mediaFolder, String mediaFileName) {
		try {
			Process p = r.exec("perl " + scriptsFolder + "/ogg2mp3 " + mediaFileName + ".ogg", null, new File(coursesFolder + mediaFolder));
			
			if( p.waitFor() != 0 )
				throw new DaoException("Error while converting the ogg file " + mediaFileName + ".ogg to mp3");
		} catch (IOException ioe) {
			System.out.println("Error while converting the ogg file " + mediaFileName + ".ogg to mp3");
			ioe.printStackTrace();
		}
		catch( InterruptedException ie) {
			System.out.println("Error while converting the ogg file " + mediaFileName + ".ogg to mp3");
			ie.printStackTrace();
		}
	}

	/**
	 * Launches a python script which creates a pdf file with the screenshots
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */
	private static void pdfCreation(String mediaFolder, String mediaFileName) {
		try {
			Process p = r.exec("python2.5 CreatePDF.py " + coursesFolder + mediaFolder + " " + mediaFileName, null, scriptsFolder); 
			if( p.waitFor() != 0 ) {
				System.out.println("Error while creating the pdf file " + mediaFileName + ".pdf");
				throw new DaoException("Error while creating the pdf file " + mediaFileName + ".pdf");
			}
		}
		catch(IOException ioe) {
			System.out.println("Error while creating the pdf file " + mediaFileName + ".pdf");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while creating the pdf file " + mediaFileName + ".pdf");
			ie.printStackTrace();
		}
	}
	
	/**
	 * Copies an "empty" pdf file for the courses with no slides
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */
	private static void emptyPdfCopy(String mediaFolder, String mediaFileName) {
		try {
			Process p = r.exec("cp " + coursesFolder + "model/empty.pdf " + coursesFolder + mediaFolder + "/" + mediaFileName + ".pdf"); 
			
			if( p.waitFor() != 0 ) {
				System.out.println("Error while copying the empty pdf file");
				throw new DaoException("Error while copying the empty pdf file");
			}
		}
		catch(IOException ioe) {
			System.out.println("Error while copying the empty pdf file");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while copying the empty pdf file");
			ie.printStackTrace();
		}
	}
	
	/**
	 * Creates the smil file
	 * @param c the course
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */
	private void smilCreation(Course c, String mediaFolder, String mediaFileName) {
		ISmil smil1, smil2;
		
		if( c.getType().equals("audio")) {
			smil1 = new RemoteAudioSmil1(c, coursesFolder + mediaFolder + "/", mediaFolder, mediaFileName, coursesUrl, comment, getTimecodes(c.getMediaFolder()));
			smil1.smilCreation();
			smil2 = new LocalAudioSmil1(c, coursesFolder + mediaFolder + "/", mediaFolder, mediaFileName, coursesUrl, comment, getTimecodes(c.getMediaFolder()));
			smil2.smilCreation();
		}
		else if( c.getType().equals("video") || c.getType().equals("flash") ) {
			
			String mediaFileExtension = "";
			if( c.getType().equals("video") )
				mediaFileExtension = ".rm";
			else if( c.getType().equals("flash") )
				mediaFileExtension = ".flv";
			
			smil1 = new RemoteVideoSmil1(c, coursesFolder + mediaFolder + "/", mediaFolder, mediaFileName, mediaFileExtension, coursesUrl, comment, getTimecodes(c.getMediaFolder()));
			smil1.smilCreation();
			smil2 = new LocalVideoSmil1(c, coursesFolder + mediaFolder + "/", mediaFolder, mediaFileName, mediaFileExtension, coursesUrl, comment, getTimecodes(c.getMediaFolder()));
			smil2.smilCreation();
		}
	}
	
	/**
	 * Creates a zip file containing the mp3, rm, or flv file, the smil file, and the pdf file
	 * @param c the course
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param mediaFileName the name used by all the media files of a course
	 */
	private static void courseZip(Course c, String mediaFolder, String mediaFileName) {
		String mediaFileExtension = "";
		if( c.getType().equals("audio") )
			mediaFileExtension = ".mp3";
		else if( c.getType().equals("video") )
			mediaFileExtension = ".rm";
		else if( c.getType().equals("flash") )
			mediaFileExtension = ".flv";
		
		String command = "zip -r " + mediaFileName + ".zip description.txt local_" + mediaFileName + ".smil" + " " + defaultScreenshotsFolder + " " + mediaFileName + mediaFileExtension + " " + mediaFileName + ".pdf";
		
		try {
			Process p = r.exec(command, null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 ) {
				System.out.println("Error while creating the zip file " + mediaFileName + ".zip");
				throw new DaoException("Error while creating the zip file " + mediaFileName + ".zip");
			}
			
		} catch (IOException ioe) {
			System.out.println("Error while creating the zip file " + mediaFileName + ".zip");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while creating the zip file " + mediaFileName + ".zip");
			ie.printStackTrace();
		}
	}
	
	/* Procédure permettant de sauvegarder le fichier XML du flux RSS */
	private static void svgXml(Document document, String fichier) {
        try {
	        // Création de la source DOM
	        Source source = new DOMSource(document);
	        
	        // Création du fichier de sortie
	        File file = new File(fichier);
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
	
	/**
	 * Returns the content length of a file
	 * @param filePath the path of the file
	 * @return the content length
	 */
	private static long getContentLength(String filePath) {
		File f = new File(filePath);
		return f.length();
	}
	
	/**
	 * Creates the media folder of a course created from a media file
	 * @param c the course
	 * @param mediaFile the media file
	 */
	private static void mediaFolderCreation( Course c, FileItem mediaFile, String fileName) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(c.getDate().getTime());
		String mediaFolderName = "mu-" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) 
			+ "-" + cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.HOUR_OF_DAY) 
			+ "h-" + cal.get(Calendar.MINUTE) + "m-" + cal.get(Calendar.SECOND) + "s-" + c.getCourseid();
		
		File mediaFolder = new File(coursesFolder, mediaFolderName);
		mediaFolder.mkdir();
		c.setMediaFolder(mediaFolderName);
		
		try {
			mediaFile.write(new File(mediaFolder, fileName));
		}
		catch( Exception e) {
			System.out.println("Error while writing the media file " + fileName);
			e.printStackTrace();
		}
	}
	
	/** 
	 * converts a video format into .flv format
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param inputFileName the input video filename (with extension)
	 * @param outputName the output video name (without extension)
	 */
	private static void videoConvert( String mediaFolder, String inputFileName, String outputName) {
		
		try {						
			String[] cmd = new String[] {"bash" , "convertAll2Flv.sh" , coursesFolder + mediaFolder , inputFileName , outputName};
			Process p = r.exec(cmd, null, scriptsFolder);
			
			/* Used to avoid ffmpeg crash ... */
			InputStream in=p.getInputStream();
			BufferedReader entree = new BufferedReader(new InputStreamReader(in));

			while( entree.readLine() != null );
			
			if( p.waitFor() != 0 ) {
				System.out.println("Error while converting the file " + inputFileName + " to flv");
				throw new DaoException("Error while converting the file " + inputFileName + " to flv");
			}
		}
		catch(IOException ioe) {
			System.out.println("Error while converting the file " + inputFileName + " to flv");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while converting the file " + inputFileName + " to flv");
			ie.printStackTrace();
		}
	}
	
	/** 
	 * converts a video format into .mp4 format in High Quality
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param inputFileName the input video filename (with extension)
	 * @param outputName the output video name (without extension)
	 */
	private static void videoHighQualityConvert( String mediaFolder, String inputFileName, String outputName) {
				
		try {						
			String[] cmd = new String[] {"bash" , "convertAll2Mp4.sh" , coursesFolder + mediaFolder , inputFileName , outputName};
			Process p = r.exec(cmd, null, scriptsFolder);
			
			// Used to avoid ffmpeg crash ... 
			InputStream in=p.getInputStream();
			BufferedReader entree = new BufferedReader(new InputStreamReader(in));

			while( entree.readLine() != null );
			
			if( p.waitFor() != 0 ) {
				System.out.println("Error while converting the file " + inputFileName + " to mp4");
				throw new DaoException("Error while converting the file " + inputFileName + " to mp4");
			}
		}
		catch(IOException ioe) {
			System.out.println("Error while converting the file " + inputFileName + " to mp4");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while converting the file " + inputFileName + " to mp4");
			ie.printStackTrace();
		}
	}
	
	/** 
	 * converts a video in .rm/.rv/.mkv format into .flv format
	 * @param mediaFolder the folder which contains the media files of a course
	 * @param inputFileName the input video filename (with extension)
	 * @param outputName the output video name (without extension)
	 */
	private static void rmMkvConvert( String mediaFolder, String inputFileName, String outputName) {
		
		try {
			Process p = r.exec("bash convertRmMkv2Flv.sh "  + coursesFolder + mediaFolder + " " + inputFileName + " " + outputName, null, scriptsFolder);
			
			/* Used to avoid mencoder crash ... */
			/*InputStream in=p.getInputStream();
			BufferedReader entree = new BufferedReader(new InputStreamReader(in));
			while( entree.readLine() != null );*/
			
			if( p.waitFor() != 0 ) {
				System.out.println("Error while converting the file " + inputFileName + " to flv");
				throw new DaoException("Error while converting the file " + inputFileName + " to flv");
			}
		}
		catch(IOException ioe) {
			System.out.println("Error while converting the file " + inputFileName + " to flv");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while converting the file " + inputFileName + " to flv");
			ie.printStackTrace();
		}
	}
}
