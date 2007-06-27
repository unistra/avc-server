package paquet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Servlet permettant de modifier l'état d'un amphi (live en cours ou non) */
public class LiveState extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		
		String recordingPlace = request.getParameter("recordingPlace");
		String status = request.getParameter("status");
		
		/* Vérification que les paramètres sont envoyés et non vides */
		if( recordingPlace != null && status != null && ! recordingPlace.equals("") && ! status.equals("")) {
			
			/* Vérification que status contient une des deux chaînes attendues */
			if( status.equals("begin") || status.equals("end")) {
				SqlBean sqlbean = new SqlBean();
				sqlbean.connect();
				
				/* Requête de modification */
				String query = "UPDATE \"Live\" SET \"Status\"= " + (status.equals("end") ? "FALSE" : "TRUE");
				query += " WHERE \"Chemin\" = ?";
				sqlbean.pupdate(query, recordingPlace);
				
				out.println("Amphi d'IP " + recordingPlace + ": " + status);
				
				sqlbean.disconnect();
			}
			else
				out.println("Erreur: valeurs de status attendues: {begin ; end}");
		} 
		else
			out.println("Erreur: param&egrave;tres attendus: recordingPlace, status");
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
