package univr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import paquet.SqlBean;
import paquet.Tools;

public class Univrinfo extends HttpServlet {


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
doPost(request, response); 
		/*response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>Interaction Univ-R</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    Page de simulation de la récupération des infos d'Univ-R ");
		
		String idCours = Tools.calculeId();
		String ipClient = request.getRemoteAddr();
		
		out.println("<br> ID du cours : " + idCours + "<br>");
		out.println("<br> IP du client : " + ipClient + "<br>");

			
			/* Connexion au client de capture Audiocours par Socket */ 
			/*String host = "130.79.188.11";
			int port = 3737;
			
			try {
				Socket client = new Socket(host, port);
				// Envoi de l'ID au client par Socket
				PrintWriter sortie = new PrintWriter(client.getOutputStream());
				sortie.println(idCours);
				sortie.flush();
				// Réception
				BufferedReader entree = new BufferedReader(new InputStreamReader(client.getInputStream()));
				out.println("<h3>Connexion au client réalisée</h3>");
				out.println("<h3>Réponse du client : " + entree.readLine() + "</h3>");
				client.close();
			}
			catch( Exception e) {
				out.println("<h2>Impossible de se connecter au client !</h2>");
				out.println("<h2>" + e.toString() + "</h2>");
			}
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();*/
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
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>Interaction Univ-R</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    Page de récupération des infos d'Univ-R ");

		String idCours = Tools.calculeId();
		
		/* Récupération des paramètres envoyés par Univers */
		/* Le client envoie les informations en ISO8859-15 */
		request.setCharacterEncoding("ISO8859-15");
		
		String login = request.getParameter("login");
		String codeGroupe = request.getParameter("codegroupe");
		String nature = request.getParameter("nature");
		String url = request.getParameter("url");
		
		out.println("<br> ID du cours : " + idCours + "<br>");
		out.println("<br> Login : " + login + "<br>");
		out.println("<br> Code groupe : " + codeGroupe + "<br>");
		out.println("<br> Nature groupe : " + nature + "<br>");
		out.println("<br> URL Univ-R : " + url + "<br>");
		
		/* Connexion au client de capture Audiocours par Socket */ 
		String host = "130.79.188.11";
		int port = 3737;
		
		try {
			/* Si le transfert de l'ID au client s'est bien déroulé, ajout dans la base de données */
			/*SqlBean sqlb = new SqlBean();
			sqlb.connect();
			String query = "INSERT INTO \"Univr\" VALUES(" + idCours + ",'" + login + "','" + codeGroupe ;
			query += "','" + nature + "','" + url + "')";
			sqlb.query(query);
			sqlb.disconnect();*/
			Socket client = new Socket(host, port);
			// Envoi de l'ID au client par Socket
			PrintWriter sortie = new PrintWriter(client.getOutputStream());
			sortie.println("(id:" + idCours + ")");
			sortie.flush();
			// Réception
			BufferedReader entree = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out.println("<h3>Connexion au client réalisée</h3>");
			out.println("<h3>Réponse du client : " + entree.readLine() + "</h3>");
			client.close();
			
			/* Si le transfert de l'ID au client s'est bien déroulé, ajout dans la base de données */
			SqlBean sqlb = new SqlBean();
			sqlb.connect();
			String query = "INSERT INTO \"Univr\" VALUES(" + idCours + ",'" + login + "','" + codeGroupe ;
			query += "','" + nature + "','" + url + "')";
			sqlb.query(query);
			sqlb.disconnect();
			
		}
		catch( ConnectException ce ) {
			out.println("<h2>Impossible de se connecter au client !</h2>");
			out.println("<h2>" + ce.toString() + "</h2>");
		}
		catch( NoRouteToHostException nrthe ) {
			out.println("<h2>Impossible de se connecter au client !</h2>");
			out.println("<h2>" + nrthe.toString() + "</h2>");
		}
		catch( IOException ioe ) {
			out.println("<h2>Impossible d'ajouter les informations à la base de données !</h2>");
			out.println("<h2>" + ioe.toString() + "</h2>");
		}
		catch( Exception e ) {
			out.println("<h2>Problème lors de la connexion au client !</h2>");
			out.println("<h2>" + e.toString() + "</h2>");
		}
		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}
}
