package org.ulpmm.univrav.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.ulpmm.univrav.entities.Course;

public class FileSystemImpl implements IFileSystem {

	private Runtime r;
	private Course c;
	
	private String mediaFolder; //folder which contains the media files of a course
	private String mediaFileName; //name used by all the media files of a course
	private File scriptsFolder; //folder which contain
	
	private String ftpFolder; // folder in which the courses are uploaded via FTP
	private String coursesFolder; // folder which contains all the media folders
	private String defaultMp3File;
	private String defaultRmFile;
	private String comment;
	
	public FileSystemImpl(String scriptsFolder) {
		this.scriptsFolder = new File(scriptsFolder);
		r = Runtime.getRuntime();
		
		ftpFolder = "/media/ftp/";
		coursesFolder = "/media/coursv2/";
		defaultMp3File = "enregistrement-micro.mp3";
		defaultRmFile = "enregistrement-video.rm";
		comment = "Copyright ULP Multimedia";
	}
	
	/**
	 * Creates a course with all its media files on the file system
	 * @param courseArchive the name of the archive file of the course to create
	 */
	public void addCourse(Course c, String courseArchive) {
		this.c = c;
		mediaFileName = c.getMediasFileName();
		archiveExtraction(courseArchive);
		setCourseType();
		thumbCheck();
		if( c.getType().equals("audio")) {
			mp3Modif();
			mp3Tag();
			setCourseDuration();
			mp3ToOgg();
			pdfCreation();
			smilCreation();
			courseZip();
		}
		else if( c.getType().equals("video")) {
			rmModif();
			rmToMp3();
			mp3Tag();
			setCourseDuration();
			mp3ToOgg();
			pdfCreation();
			smilCreation();
			courseZip();
		}
	}
	
	/**
	 * Reads the timecodes csv file and creates the timecodes list
	 * @return the timecodes list
	 */
	public ArrayList<String> getTimecodes() {
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
	
	public void rssCreation() {
		
	}
	
	public void deleteCourse() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Extracts the course archive file to the courses folder and renames it
	 * @param courseArchive the course archive file
	 */
	private void archiveExtraction(String courseArchive) {
		String extension;
		
		if( courseArchive.length() > 3) {
			extension = courseArchive.substring(courseArchive.length()-4, courseArchive.length());
			String tmpMediaFolder = courseArchive.substring(0,courseArchive.lastIndexOf(".")); //uncompressed media folder
			mediaFolder = tmpMediaFolder + "-" + c.getCourseid(); // new (renamed) media folder
			
			if( ! new File(coursesFolder + mediaFolder).exists()) {	
				try {
					/* Archive extraction according to its type to the courses folder */
					if( extension.equals(".zip")){
					
						/* Zip extracting */
						Process p = r.exec("unzip " + ftpFolder + courseArchive, null, new File(coursesFolder));
						if( p.waitFor() != 0)
		        			throw new DaoException("The course archive " + courseArchive + " has not been extracted");
		        		/* Renaming of the extracted folder to have a unique one */
		        		p = r.exec("mv " + tmpMediaFolder + " " + mediaFolder, null, new File(coursesFolder));
		        		if( p.waitFor() != 0)
		        			throw new DaoException("The course archive " + courseArchive + " has not been renamed");
					}
					else {			
						/* Tar extracting */
						Process p = r.exec("tar xf " + ftpFolder + courseArchive, null, new File(coursesFolder));
						if( p.waitFor() != 0)
		        			throw new DaoException("The course archive " + courseArchive + " has not been extracted");
						/* Renaming of the extracted folder to have a unique one */
						p = r.exec("mv " + tmpMediaFolder + " " + mediaFolder, null, new File(coursesFolder));
						if( p.waitFor() != 0)
		        			throw new DaoException("The course archive " + courseArchive + " has not been renamed");
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
			else
				throw new DaoException("The course folder " + mediaFolder + " already exists" );
		}
		else
			throw new DaoException("Incorrect archive file to extract: " + courseArchive );
	}
	
	/**
	 * Sets the "type" attribute of the Course object by identifying the media file
	 */
	private void setCourseType() {
		
		if( new File(coursesFolder + mediaFolder + "/" + defaultMp3File).exists()) 
			c.setType("audio");
		else if(new File(coursesFolder + mediaFolder + "/" + defaultRmFile).exists())
			c.setType("video");
		else
			throw new DaoException("No course media file found in the " + coursesFolder + mediaFolder + " folder");
	}
	
	/**
	 * Launches a python script which creates the missing screenshots thumbs
	 */
	private void thumbCheck() {
		try {
			Process p = r.exec("python2.5 thumbCheck.py " + coursesFolder + mediaFolder + "/screenshots", null, scriptsFolder); 
			if( p.waitFor() != 0 )
				throw new DaoException("Error while checking the course thumbs in the folder " + coursesFolder + mediaFolder + "/screenshots");
		}
		catch(IOException ioe) {
			System.out.println("Error while checking the course thumbs in the folder " + coursesFolder + mediaFolder + "/screenshots");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while checking the course thumbs in the folder " + coursesFolder + mediaFolder + "/screenshots");
			ie.printStackTrace();
		}
	}
	
	/**
	 * Regenerates the mp3 file to fix the problems with RealPlayer and renames it
	 */
	private void mp3Modif() {
		
		try {
			/* Regeneration of the mp3 file to fix the play problems with RealPlayer */
			Process p = r.exec("mp3cleaner " + coursesFolder + mediaFolder + " " + defaultMp3File, null, scriptsFolder);
			if( p.waitFor() != 0 )
				throw new DaoException("Error while cleaning the mp3 file " + defaultMp3File);
			
			/* Renames the mp3 file */
			p = r.exec("mv clean_" + defaultMp3File + " " + mediaFileName + ".mp3", null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 )
				throw new DaoException("Error while renaming the mp3 file clean_" + defaultMp3File);
			
			/* Removes the default mp3 file */
			p = r.exec("rm " + defaultMp3File, null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 )
				throw new DaoException("Error while deleting the mp3 file " + defaultMp3File);
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
	 */ 
	private void mp3Tag() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			ArrayList<String> command = new ArrayList<String>();
			
			command.add("mp3info");
			if( ! c.getTitle().equals("")) {
				command.add("-t");
				command.add(c.getTitle());
			}
			if( ! c.getName().equals("")) {
				command.add("-a");
				command.add(c.getName() + (c.getFirstname().equals("") ? "" : " " + c.getFirstname()));
			}
			command.add("-y");
			command.add(sdf.format(c.getDate()));
			if( ! c.getFormation().equals("")) {
				command.add("-l");
				command.add(c.getFormation());
			}
			if( ! comment.equals("")) {
				command.add("-c");
				command.add(comment);
			}
			command.add(mediaFileName + ".mp3");

			Process p = r.exec(command.toArray(new String[command.size()]), null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 )
				throw new DaoException("Error while adding the tags to the mp3 file " + mediaFileName + ".mp3");
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
	 * Renames the rm file 
	 */ 
	private void rmModif() {
		try {
			Process p = r.exec("mv " + defaultRmFile + " " + mediaFileName + ".rm", null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 )
				throw new DaoException("Error while renaming the rm file " + defaultRmFile);
		} catch (IOException ioe) {
			System.out.println("Error while renaming the rm file " + defaultRmFile);
			ioe.printStackTrace();
		}
		catch( InterruptedException ie) {
			System.out.println("Error while renaming the rm file " + defaultRmFile);
			ie.printStackTrace();
		}
	}
	
	/** 
	 * Launches a bash script which converts a rm media file to mp3
	 */
	private void rmToMp3() {
		try {						
			Process p = r.exec("bash ExRmv2mp3.sh "  + coursesFolder + mediaFolder + " " + mediaFileName, null, scriptsFolder);
			if( p.waitFor() != 0 )
				throw new DaoException("Error while converting the rm file " + mediaFileName + ".rm to mp3");
		}
		catch(IOException ioe) {
			System.out.println("Error while converting the rm file " + mediaFileName + ".rm to mp3");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while converting the rm file " + mediaFileName + ".rm to mp3");
			ie.printStackTrace();
		}
	}
	
	/** 
	 * Sets the "duration" attribute of the Course object by identifying the media file
	 */
	private void setCourseDuration() {
		int duration;
		
		try {
			Process p = r.exec("mp3info -p %S " +  mediaFileName + ".mp3", null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 )
				throw new DaoException("Error while getting the length of the file " + mediaFileName + ".mp3");
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
	 */
	private void mp3ToOgg() {
		try {
			Process p = r.exec("mp32ogg " + mediaFileName + ".mp3", null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 )
				throw new DaoException("Error while converting the mp3 file " + mediaFileName + ".mp3 to ogg");
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
	 * Launches a python script which creates a pdf file with the screenshots
	 */
	private void pdfCreation() {
		try {
			Process p = r.exec("python2.5 CreatePDF.py " + coursesFolder + mediaFolder + " " + mediaFileName, null, scriptsFolder); 
			if( p.waitFor() != 0 )
				throw new DaoException("Error while creating the pdf file " + mediaFileName + ".ogg");
		}
		catch(IOException ioe) {
			System.out.println("Error while creating the pdf file " + mediaFileName + ".ogg");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while creating the pdf file " + mediaFileName + ".ogg");
			ie.printStackTrace();
		}
	}
	
	/**
	 * Creates the smil file
	 */
	private void smilCreation() {
		ISmil smil;
		if( c.getType().equals("audio")) {
			smil = new AudioSmil1(c, coursesFolder + mediaFolder + "/", mediaFolder, mediaFileName, getTimecodes());
			smil.smilCreation();
		}
		else if( c.getType().equals("video")) {
			smil = new VideoSmil1(c, coursesFolder + mediaFolder + "/", mediaFolder, mediaFileName, getTimecodes());
			smil.smilCreation();
		}
	}
	
	/**
	 * Creates a zip file containing the mp3 or rm file, the smil file, and the pdf file
	 */
	private void courseZip() {
		String mediaFileExtension = "";
		if( c.getType().equals("audio") )
			mediaFileExtension = ".mp3";
		else if( c.getType().equals("video") )
			mediaFileExtension = ".rm";
		
		String command = "zip -r " + mediaFileName + ".zip " + mediaFileName + ".smil " + mediaFileName + mediaFileExtension + " " + mediaFileName + ".pdf";
		
		try {
			Process p = r.exec(command, null, new File(coursesFolder + mediaFolder));
			if( p.waitFor() != 0 )
				throw new DaoException("Error while creating the zip file " + mediaFileName + ".zip");
		} catch (IOException ioe) {
			System.out.println("Error while creating the zip file " + mediaFileName + ".zip");
			ioe.printStackTrace();
		}
		catch(InterruptedException ie) {
			System.out.println("Error while creating the zip file " + mediaFileName + ".zip");
			ie.printStackTrace();
		}
	}

}
