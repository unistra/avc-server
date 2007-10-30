package org.ulpmm.univrav.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.ulpmm.univrav.entities.Course;

/**
 * Class to create an audio smil file
 * @author Laurent Kieffer
 *
 */
public class AudioSmil2 implements ISmil {

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
	public AudioSmil2(Course c, String absoluteMediaFolder, String mediaFolder, String mediaFileName, 
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
			if( c.getTitle() != null)
				pw.println("<meta name=\"title\" content=\"" + c.getTitle() + "\"/>");
			if( c.getName() != null)
				pw.println("<meta name=\"author\" content=\"" + c.getName()  + (c.getFirstname() == null ? "" : " " + c.getFirstname()) + "\"/>");
			if( ! comment.equals(""))
				pw.println("<meta name=\"copyright\" content=\"" + comment + "\"/>");
			
			/* Regions definition */
			pw.println("<layout>");
			pw.println("<root-layout width=\"970\" height=\"550\" />");
			pw.println("<region id=\"Bg\" left=\"0\" width=\"970\" height=\"550\" fit=\"meet\" />");
			pw.println("<region id=\"Images\" left=\"0\" top=\"0\" bottom=\"0\" right=\"0\" width=\"733\" height=\"550\" fit=\"meet\" />");
			pw.println("<region id=\"Texte\" left=\"740\" top=\"20\" width=\"220\" height=\"160\" fit=\"meet\" />");
			pw.println("<region id=\"mp3\" left=\"770\" top=\"260\" width=\"58\" height=\"45\" fit=\"meet\" />");
			pw.println("<region id=\"ogg\" left=\"770\" top=\"340\" width=\"58\" height=\"45\" fit=\"meet\" />");
			pw.println("<region id=\"pdf\" left=\"880\" top=\"260\" width=\"58\" height=\"45\" fit=\"meet\" />");
			pw.println("<region id=\"zip\" left=\"880\" top=\"340\" width=\"58\" height=\"45\" fit=\"meet\" />");
			pw.println("</layout>");
			pw.println("</head>\n<body>\n<par>");
			
			/* Media display */
			pw.println("<img region=\"Bg\" src=\"" + coursesUrl + "model/bgsmil.jpg\"/>");
			pw.println("<audio src=\"" + coursesUrl + mediaFolder + "/" + mediaFileName + ".mp3\"/>");
			pw.println("<text region=\"Texte\" src=\"" + coursesUrl + mediaFolder + "/description.txt\">");
			pw.println("<param name=\"fontFace\" value=\"Arial\"/>");
			pw.println("<param name=\"fontColor\" value=\"#ffffff\"/>");
			pw.println("<param name=\"backgroundColor\" value=\"#999999\"/>");
			//pw.println("<param name=\"charset\" value=\"utf-8\"/>");
			pw.println("</text>");
				
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
			
			/* Downloads links display */
			pw.println("<a href=\"" + coursesUrl + mediaFolder + "/" + mediaFileName + ".mp3\" external=\"true\"> <img region=\"mp3\" src=\"" + coursesUrl + "model/mp3_v2.png\"/> </a>");
			pw.println("<a href=\"" + coursesUrl + mediaFolder + "/" + mediaFileName + ".ogg\" external=\"true\"> <img region=\"ogg\" src=\"" + coursesUrl + "model/ogg_v2.png\"/> </a>");
			pw.println("<a href=\"" + coursesUrl + mediaFolder + "/" + mediaFileName + ".pdf\" external=\"true\"> <img region=\"pdf\" src=\"" + coursesUrl + "model/acrobat.png\"/> </a>");
			pw.println("<a href=\"" + coursesUrl + mediaFolder + "/" + mediaFileName + ".zip\" external=\"true\"> <img region=\"zip\" src=\"" + coursesUrl + "model/winzip3.png\"/> </a>");
			
			/* End of the Smil file */
			pw.println("</par>\n</body>\n</smil>");
			pw.close();
			
			/* creates the .txt description file */
			File descriptionFile = new File(absoluteMediaFolder + "description.txt");
			descriptionFile.createNewFile();
			pw = new PrintWriter( new OutputStreamWriter( new FileOutputStream( descriptionFile), "ISO8859-15"));
			pw.println("Author: " +  (c.getName() != null ? c.getName() + ( c.getFirstname() != null ? " " + c.getFirstname() : "") : "-"));
			pw.println("Formation: " +  (c.getFormation() != null ? c.getFormation() : "-"));
			pw.println("Title: " +  (c.getTitle() != null ? c.getTitle() : "-"));
			pw.println("Subject: " +  (c.getDescription() != null ? c.getDescription() : "-"));
			pw.println("Date: " + c.getDateString());
			pw.println("Type: " + c.getType());
			pw.println("Duration: " + c.getDurationString());
			pw.close();
			
		}
		catch( IOException ioe) {
			System.out.println("Error while writing the smil file");
			ioe.printStackTrace();
		}	
	}
	
}
