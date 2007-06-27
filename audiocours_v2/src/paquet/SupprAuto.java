package paquet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SupprAuto extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

		/* Suppression automatique des tests */
		
		SqlBean sqlbean = new SqlBean();
		sqlbean.connect();
		
		String query = "DELETE FROM \"AudioCours\" WHERE INITCAP(\"Who\")='Testulpmm'";
		query += " AND INITCAP(\"Title\")='Testulpmm'";
		query += " AND INITCAP(\"Genre\")='Suppression'"; 
		
		sqlbean.update(query);
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();		
		
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>Suppression des tests</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("    <p>Suppression des tests</p>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
