package org.ulpmm.univrav.serverclasses;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Custom Tag to display a list with a pagination bar in a jsp page
 * @author Laurent Kieffer
 *
 */
public class PaginationTag extends TagSupport {

	private static final long serialVersionUID = -2966587645073072851L;
	private int itemsNumber;
	private int numberPerPage;
	private int currentPage;
	private String resultPageName;

	/**
	 * Displays the pagination bar
	 */
	public int doStartTag() throws JspException {
		
		/* Calculates the number of pages to display */
		double nbr = ((double) itemsNumber / (double) numberPerPage);
		int pagesNumber = (int) Math.ceil(nbr);
		
		/* Calculates the numbers of the first and the last pages */
		int begin, end;
		
		if( pagesNumber <= 20 ) {
			begin = 1 ;
			end = pagesNumber;
		}
		else {
			
			if( currentPage <= 9 ) {
				/* The first pages will be displayed */
				begin = 1 ;
				end = 20;
			}
			else if( pagesNumber - currentPage <= 10) {
				/* The last pages will be displayed */
				begin = pagesNumber - 19;
				end = pagesNumber;
			}
			else {
				/* 9 pages before and 10 pages after the current page will be displayed */
				begin = currentPage -9;
				end = currentPage + 10;
			}
			
		}
		
		/* Sets the parameters */
		Map<String, String[]> parameters = pageContext.getRequest().getParameterMap();
		String params = "";
		for(Entry<String, String[]> entry : parameters.entrySet()) {
		    params += "&" + entry.getKey() + "=" + entry.getValue()[0];
		}

		/* Displays the links to the different pages */
		try {
			JspWriter out = pageContext.getOut();
			
			/* Link to the first page */
			out.println("<a href=\"" + resultPageName + "?page=1" + params + "\">&lt;&lt;</a>&nbsp;");
			
			/* Links with page numbers to the pages */
			for( int i=begin ; i<= end ; i++)
				out.println((i != currentPage ? "<a href=\"" + resultPageName + "?page=" + i + params + "\">" : "<b>") + i + (i != currentPage ? "</a>" : "</b>") +"&nbsp;");
			
			/* Link to the last page */
			out.println("<a href=\"" + resultPageName + "?page=" + pagesNumber + params + "\">&gt;&gt;</a>&nbsp;");
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	/**
	 * @return the total number of items of the list
	 */
	public int getItemsNumber() {
		return itemsNumber;
	}

	/**
	 * @param itemsNumber total number of items of the list to set
	 */
	public void setItemsNumber(int itemsNumber) {
		this.itemsNumber = itemsNumber;
	}

	/**
	 * @return the items number to display per page
	 */
	public int getNumberPerPage() {
		return numberPerPage;
	}

	/**
	 * @param numberPerPage the items number to display per page to set
	 */
	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}

	/**
	 * @return the current page number
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the current page number to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the resultPageName
	 */
	public String getResultPageName() {
		return resultPageName;
	}

	/**
	 * @param resultPageName the resultPageName to set
	 */
	public void setResultPageName(String resultPageName) {
		this.resultPageName = resultPageName;
	}

}
