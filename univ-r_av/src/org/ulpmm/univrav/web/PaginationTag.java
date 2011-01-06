package org.ulpmm.univrav.web;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * Custom Tag to display a list with a pagination bar in a jsp page
 * @author Laurent Kieffer
 *
 */
public class PaginationTag extends TagSupport {

	/** Serial version */
	private static final long serialVersionUID = -2966587645073072851L;
	
	/** number of items */
	private int itemsNumber;
	
	/** Number per page */
	private int numberPerPage;
	
	/** the current page */
	private int currentPage;
	
	/** Name of the result page */
	private String resultPageName;
	
	/** tags */
	private String tags;
	
	/** paramsUrl */
	private String paramsUrl;
	
	/** The name of the bundle to search the corresponding language properties files */
	private static final String BUNDLE_NAME = "org.ulpmm.univrav.language.messages";
	
	/** Logger log4j */
	private static final Logger logger = Logger.getLogger(PaginationTag.class);

	/**
	 * Displays the pagination bar
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		
		/* Calculates the number of pages to display */
		double nbr = ((double) itemsNumber / (double) numberPerPage);
		int pagesNumber = (int) Math.ceil(nbr);
				
		/* Calculates the numbers of the first and the last pages */
		int begin, end;
		
		if( pagesNumber <= 10 ) {
			begin = 1 ;
			end = pagesNumber;
		}
		else {
			
			if( currentPage <= 4 ) {
				/* The first pages will be displayed */
				begin = 1 ;
				end = 10;
			}
			else if( pagesNumber - currentPage <= 5) {
				/* The last pages will be displayed */
				begin = pagesNumber - 9;
				end = pagesNumber;
			}
			else {
				/* 4 pages before and 5 pages after the current page will be displayed */
				begin = currentPage -4;
				end = currentPage + 5;
			}
			
		}
		
		/* Sets url rewriting to keep the session if the browser has disabled cookies */
		String sessionId = "";
		sessionId = ";jsessionid=" + pageContext.getSession().getId();
		

		/* Displays the links to the different pages */
		try {
			JspWriter out = pageContext.getOut();
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale( (String) pageContext.getSession().getAttribute("language")));
			
			/* Link to the previous page */
			out.println("<a href=\"" + resultPageName + sessionId + (paramsUrl!=null && !paramsUrl.equals("") ? paramsUrl+"&page=" : "?page=") + ((currentPage-1) > 0 ? currentPage-1 : 1)  + (resultPageName.equals("tags")? "&tags="+tags :"") + "#tableheader\">" + bundle.getString("Precedent") + "<img src=\"../files/img/R.png\"></a>&nbsp;");

			/* Links with page numbers to the pages */
			for( int i=begin ; i<= end ; i++)
				out.println((i != currentPage ? "<a class=\"numpage\" href=\"" + resultPageName + sessionId + (paramsUrl!=null && !paramsUrl.equals("") ? paramsUrl+"&page=" : "?page=") + i + (resultPageName.equals("tags")? "&tags="+tags :"") + "#tableheader\">" : "<b class=\"currentnumpage\">") + i + (i != currentPage ? "</a>" : "</b>") +"&nbsp;");
		
			
			// Combobox
			int combosize=10;
			
			out.println("<select name=combopages onchange=\"javascript:document.location.href='" + resultPageName + sessionId + (paramsUrl!=null && !paramsUrl.equals("") ? paramsUrl+"&page=" : "?page=") + "'+this.value+'#tableheader'\" />");
			out.println("<option value=''>page</option>");
			for(int i=1;i<=pagesNumber;i++) {
								
				//the first and the other
				if(i==1 || (i%combosize)==0) {
					out.println("<option value=" + i /*+ (i == currentPage ? " selected" : "")*/ + ">" + i + " ... " +"</option>");
				}	
							
			}
			out.println("</select>");
						
			
			/* Link to the next page */
			out.println("<a href=\"" + resultPageName + sessionId + (paramsUrl!=null && !paramsUrl.equals("") ? paramsUrl+"&page=" : "?page=") + ((currentPage+1) <= pagesNumber ? currentPage+1 : pagesNumber) + (resultPageName.equals("tags")? "&tags="+tags :"") + "#tableheader\"><img src=\"../files/img/F.png\">"+ bundle.getString("Suivant") + "</a>&nbsp;");

			
		}
		catch(IOException ioe){
			logger.error("Error IO",ioe);
		}
		
		return SKIP_BODY;
	}

	/**
	 * Gets items number
	 * @return the total number of items of the list
	 */
	public int getItemsNumber() {
		return itemsNumber;
	}

	/**
	 * Sets items number
	 * @param itemsNumber total number of items of the list to set
	 */
	public void setItemsNumber(int itemsNumber) {
		this.itemsNumber = itemsNumber;
	}

	/**
	 * Gets number per page
	 * @return the items number to display per page
	 */
	public int getNumberPerPage() {
		return numberPerPage;
	}

	/**
	 * Sets number per page
	 * @param numberPerPage the items number to display per page to set
	 */
	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}

	/**
	 * Gets current page
	 * @return the current page number
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * Sets current page
	 * @param currentPage the current page number to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * Gets result page name
	 * @return the resultPageName
	 */
	public String getResultPageName() {
		return resultPageName;
	}

	/**
	 * Sets result page name
	 * @param resultPageName the resultPageName to set
	 */
	public void setResultPageName(String resultPageName) {
		this.resultPageName = resultPageName;
	}

	/**
	 * Gets tags
	 * @return tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * Sets tags
	 * @param tags tags string
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * Gets paramsurl
	 * @return paramsurl
	 */
	public String getParamsUrl() {
		return paramsUrl;
	}

	/**
	 * Sets paramsurl
	 * @param paramsUrl
	 */
	public void setParamsUrl(String paramsUrl) {
		this.paramsUrl = paramsUrl;
	}
	
	

}