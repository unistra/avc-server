package org.ulpmm.univrav.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ulpmm.univrav.dao.DatabaseImpl;
import org.ulpmm.univrav.dao.IDatabase;
import org.ulpmm.univrav.service.IService;
import org.ulpmm.univrav.service.ServiceImpl;

public class Application extends HttpServlet {

	private ServiceImpl service;
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		DatabaseImpl dao = new DatabaseImpl();
		service = new ServiceImpl();
		service.setDb(dao);
	}
	
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String page = request.getPathInfo();
		
		if( page == null )
			page = "home";
		
		if( page.equals("home"))
			displayHomePage(request, response);
		else
			displayHomePage(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	private void displayHomePage(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		  // le modèle de la vue [list]
		  request.setAttribute("courses", service.getNLastCourses(3));
		  // affichage de la vue [list]
		  getServletContext()
		      .getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
