package org.ulpmm.univrav.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.ulpmm.univrav.entities.Course;

/**
 * Class to create a video smil file
 * @author Laurent Kieffer
 *
 */
public class VideoSmil1 implements ISmil {

	private Course c;
	private String absoluteMediaFolder;
	private String mediaFolder;
	private String mediaFileName;
	private String coursesUrl;
	private String comment;
	private ArrayList<String> timecodes;
	
	/**
	 * @param c the course
	 * @param absoluteMediaFolder the absolute media folder of the course in the file system
	 * @param mediaFolder the media folder name of the course in the file system
	 * @param mediaFileName the name of all the media files
	 */
	public VideoSmil1(Course c, String absoluteMediaFolder, String mediaFolder, String mediaFileName, 
			String coursesUrl, String comment, ArrayList<String> timecodes) {
		this.c = c;
		this.absoluteMediaFolder = absoluteMediaFolder;
		this.mediaFolder = mediaFolder;
		this.mediaFileName = mediaFileName;
		this.coursesUrl = coursesUrl;
		this.comment = comment;
		this.timecodes = timecodes;
	}

	/**
	 * Creates and writes the smil file on the file system
	 */
	public void smilCreation() {
		
		try {
			/* creates the .smil file */
			File smilFile = new File(absoluteMediaFolder + mediaFileName + ".smil");
			smilFile.createNewFile();
			PrintWriter pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream( smilFile)));
			
			/* Smil head */
			pw.println("<?xml version=\"1.0\"?>");
			pw.println("<!DOCTYPE smil PUBLIC \"-//W3C//DTD SMIL 2.0//EN\" \"http://www.w3.org/2001/SMIL20/SMIL20.dtd\">");
			pw.println("<smil xmlns=\"http://www.w3.org/2001/SMIL20/Language\">");
			pw.println("<head>");
			if( ! c.getTitle().equals(""))
				pw.println("<meta name=\"title\" content=\"" + cleanString(c.getTitle()) + "\"/>");
			if( ! c.getName().equals(""))
				pw.println("<meta name=\"author\" content=\"" + cleanString(c.getName())  + (c.getFirstname().equals("") ? "" : " " + cleanString(c.getFirstname())) + "\"/>");
			if( ! comment.equals(""))
				pw.println("<meta name=\"copyright\" content=\"" + cleanString(comment) + "\"/>");
			
			/* Regions definition */
			pw.println("<layout>");
			pw.println("<root-layout width=\"800\" height=\"780\"/>");
			pw.println("<region id=\"Bg\" left=\"0\" width=\"800\" height=\"780\" fit=\"meet\" />");
			pw.println("<region id=\"Images\" left=\"0\" top=\"180\" width=\"800\" height=\"600\" fit=\"meet\" />");
			pw.println("<region id=\"Video\" left=\"0\" width=\"240\" height=\"180\" fit=\"meet\"/>");
			pw.println("</layout>");
			pw.println("</head>\n<body>\n<par>");
			
			/* Media display */
			pw.println("<img region=\"Bg\" src=\"" + coursesUrl + "model/bgsmi.jpg\"/>");
			pw.println("<video src=\"" + coursesUrl + mediaFolder + "/" + mediaFileName + ".rm" + "\" region=\"Video\" />");
			
			int time;
			/* Determines if the times of the slides must be changed or not */
			if( c.getTiming().equals("n") ) {
				time = 1;
			}
			else if( c.getTiming().equals("n-1") ){
				time = 2;
			}
			else
				time = 2;
			
			/* Slides display */
			for(int i=0 ; i< timecodes.size() - (time-1)  ; i++){
				float currentSlide = Float.parseFloat(timecodes.get(i)) ;
				
				pw.println("<a href=\""+ coursesUrl + mediaFolder + "/screenshots/D" + (i+time) + ".jpg\"  external=\"true\">" );
				pw.print("<img begin=\"" + Math.round(currentSlide) + "\"");
				
				/* Doesn't write the length for the last slide */
				if( i != timecodes.size() - 1) {
					float nextSlide = Float.parseFloat(timecodes.get(i+1)) ;
					pw.print(" dur=\"" + (int) Math.ceil(nextSlide - currentSlide) + "\"");
				}
				
				pw.println(" region=\"Images\" src=\"" + coursesUrl + mediaFolder + "/screenshots/D" + (i+time) + ".jpg\"/>");
				pw.println("</a>");
			}
			
			/* End of the Smil file */
			pw.println("</par>\n</body>\n</smil>");
			pw.close();
		}
		catch( IOException ioe) {
			System.out.println("Error while writing the smil file");
			ioe.printStackTrace();
		}	
	}
	
	/**
	 * Function which removes the characters of a string which make a general error in RealPlayer
	 * @param str the string to clean
	 * @return the string cleaned
	 */
	private static String cleanString(String str){
		final String carSpeTotal = "&><\"%#+";
		
		String res = "";
		
		if( str != null ) {
			for( int i=0 ; i < str.length() ; i++ ) {
				if( carSpeTotal.indexOf(str.charAt(i)) == -1 )
					res += str.charAt(i);
			}
		}
		
		return res;
	}
	
}
