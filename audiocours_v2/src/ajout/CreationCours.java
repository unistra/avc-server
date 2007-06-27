package ajout;

import paquet.Tools;

/* Classe permettant de lancer une création de cours dans un thread (non bloquant) */
public class CreationCours extends Thread {
	
	private Fonctions f;
	private String mediaDir;
	private String ipClient;
	private String genre;
	
	public CreationCours(Fonctions f, String mediaDir, String ipClient, String genre) {
		this.f = f;
		this.mediaDir = mediaDir;
		this.ipClient = ipClient;
		this.genre = genre;
	}
	
	public void run() {
		f.creation_smil(mediaDir); // lancement de la procédure de création de fichier SMIL
		f.thumbCheck(); // vérification de la présence des thumbs et re-création si besoin
		f.creation_pdf();  // création du PDF du cours
		f.ajoutBDD(mediaDir, ipClient, genre); //lancement de la procédure qui ajoute le cours dans la base de données
		f.zipsmil(); //lancement de la procédure de zip du répertoire SMIL pour mise en téléchargement
		Tools.createRss(); //lancement de la création des RSS
	}
	
}
