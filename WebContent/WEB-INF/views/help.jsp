<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="Aide"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/help.css">
	
	<!--[if IE]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/help_ie6.css" media="screen" />
	<![endif]-->
	
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
	    <div class="main">
		    <div class="contents">
		    
		    	<div class="banner">
		    		<c:import url="include/banner.jsp" />
		    	</div>
		    	
		    	<div class="helpmenu">
		    	
		    		<div class="helpImage">
		    				<span class="helpText"><fmt:message key="Aide"/></span>
	    					<span class="useText"><fmt:message key="Utilisation d'Univ-R AV"/></span>
		    		</div>
		    		
		    		<div class="helpLinks">
						<ul>
							<li><a href="#browsers">Navigateurs conseillés</a></li>
							<li><a href="#configuration">Configuration minimale</a></li>
							<li><a href="#live">Cours en direct</a></li>
							<li><a href="#recorded">Cours en différé</a></li>
							<li><a href="#podcasting">Podcasting et flux RSS</a></li>
							<li><a href="#myspace">Mon espace</a></li>
							<li><a href="#downloads">Téléchargements</a></li>
						</ul>
					</div>
					
				</div>
		    	
		    	
		    	<div class="columns">
		    	
			    	<div class="leftCol">
			    		
			    		<h3><a id="browsers"></a>Navigateurs conseill&eacute;s</h3>
						<p>Firefox 3.0 (Linux, MacOSX, Windows), IE7 (Windows)</p>
						
						<h3><a id="live"></a>Cours en direct</h3>
						<h5>Lieux et salles</h5>
						<p>Disponible uniquement &agrave; partir des salles et amphith&eacute;atres automatis&eacute;s et sur 
						la volont&eacute; de l'enseignant, le direct est disponible sur le site de façon hi&eacute;rarchis&eacute;e 
						par b&acirc;timent puis par salle.<br>
						Cliquer sur une salle allum&eacute;e (Test Live sur l'image) permet d'&eacute;couter et de suivre le cours en direct.</p>
						<a class="link" href="../files/img/help/Live.png">Screenshot</a>
						<h5>Diffusion et &eacute;coute du Live</h5>
						<p>Les diapositives sont diffus&eacute;es simultan&eacute;ment avec le flux audio et/ou vid&eacute;o.<br>
						Pour lire le flux audio et/ou vid&eacute;o, vous avez besoin d'installer flash player sur votre machine.</p>
						<a class="link" href="../files/img/help/Live_audio.png">Screenshot</a>
						
						<h3><a id="podcasting"></a>Podcasting et flux RSS</h3>
						<p>Tous les cours sont disponibles au t&eacute;l&eacute;chargement via le site, en cliquant sur l'un des 
						m&eacute;dias disponibles ou via l'abonnement à un flux RSS.</p>
						<a class="link" href="../files/img/help/Podcast.png">Screenshot</a>
						<h5>Abonnement &agrave; un flux RSS</h5>
						<p>Cliquer sur l'ic&ocirc;ne orange dans la barre d'adresse de Firefox permet de s'abonner à l'ensemble du site Audiovid&eacute;cours. 
						Cliquer sur le lien "abonnement" de l'accueil pour lister les diff&eacute;rents flux disponibles.</p>
						<a class="link" href="../files/img/help/Rss.png">Screenshot</a>
						<p>En s&eacute;lectionnant un flux dans cette liste, Firefox affiche une page permettant d'avoir le listing 
						de tous les cours.<br>
						Le lien donne un acc&egrave;s direct &agrave; la page de diffusion du cours.</p>
						<a class="link" href="../files/img/help/Rss_FF.png">Screenshot</a>
						<p>Vous pouvez utiliser d'autres logiciels capables de g&eacute;rer les flux Rss comme le navigateur Opera ou 
						les lecteurs audio de type itunes. Il vous suffit de r&eacute;cup&eacute;rer l'adresse du fichier XML et de 
						la mettre dans votre logiciel de gestion des flux. Par exemple, un simple glisser-d&eacute;poser depuis 
						la barre d'adresse de Firefox vers itunes suffit pour s'abonner.</p>
						<p><span class="underline">Exemples :</span> Opera, Amarok et Itunes</p>
						<a class="link" href="../files/img/help/Rss_opera.png">Screenshot</a>
						<a class="link" href="../files/img/help/Rss_podcast_amarok.png">Screenshot</a>
						<a class="link" href="../files/img/help/Rss_itunes.png">Screenshot</a>
						
						<p><span class="underline">NB :</span> Les lecteurs audio permettent de g&eacute;rer sa liste de cours et 
						de les &eacute;couter directement apr&egrave;s obtention du m&eacute;dia. Attention, seul le fichier mp3 
						est cependant disponible via les logiciels audio.</p>
												
						<h3><a id="myspace"></a>Mon espace</h3>
						<p>Pour acc&eacute;der &agrave; votre espace personnel, cliquez sur "Mon espace" et entrez votre login et mot de passe ENT.</p>
			    		<p>Vous aurez alors la possibilit&eacute; :<br>
			    		- d'enregistrer ou de modifier votre e-mail pour recevoir les notifications de r&eacute;ception de vos cours (bouton "modifier mon e-mail")<br>
			    		- de visualiser vos cours qui sont ratach&eacute;s &agrave; votre login ENT (voir la liste)<br>
			    		- d'&eacute;diter vos cours afin de modifier le titre, la description, la visibilité, etc... (bouton "editer")<br>
			    		- de d&eacute;poser directement un fichier audio ou vid&eacute;o (bouton "d&eacute;poser un fichier")</p>
			    		<p>Enfin, pour quitter votre espace, il vous suffit d'appuyer sur le bouton "D&eacute;connexion".</p>
			    		<a class="link" href="../files/img/help/myspace.png">Screenshot</a>
			    		
			    	</div>
			    	
			    	<div class="rightCol">
			    				    	
			    		<h3><a id="configuration"></a>Configuration minimale</h3>
			    		<p>Realplayer 10 ou Flash Player (Linux, MacOSX, Windows)</p>
						
						<h3><a id="recorded"></a>Cours en différé</h3>
						<h5>Filtrage de l'Affichage</h5>
						<p>On dispose d'une fonction de recherche (Launch Search) avec des options de filtrage pour l'affichage des cours.<br>
						Vous pouvez &eacute;galement acc&eacute;der directement aux cours d'un auteur et/ou d'une formation grâce à l'url:<br> 
						http://audiovideocours.u-strasbg.fr/avc/courses?author=...&formation=...</p>
						<h5>Listings des Cours</h5>
						<p>Les cours sont disponibles selon diff&eacute;rents modes.</p>	
						<p><b>Clic sur l'ic&ocirc;ne à gauche ou sur l'ic&ocirc;ne sous l'intitul&eacute; 'flash' :</b><br>
						Mode en ligne. N&eacute;cessite la pr&eacute;sence de Flash Player sur votre machine. 
						C'est le mode recommand&eacute; pour la visualisation des cours.</p>
						<p><b>Clic sur l'ic&ocirc;ne sous l'intitul&eacute; 'realplayer' :</b><br>
						Mode en ligne. N&eacute;cessite la pr&eacute;sence du plugin RealPlayer sur votre machine. 
						</p>		
						<a class="link" href="../files/img/help/Differe.png">Screenshot</a>			
						<h5>Diffusion et &eacute;coute du diff&eacute;r&eacute; (Mode recommandé)</h5>
						<p> En flash, le cours est lu dans le lecteur flash qui se charge de diffuser les diapositives et 
						l'enregistrement audio/vid&eacute;o du cours. Il est possible de d&eacute;placer le curseur pour se positionner 
						&agrave; un endroit particulier du cours. </p>
						<a class="link" href="../files/img/help/Diff_layout_flash.png">Screenshot</a>
						<p>Le fichier Smil est lu dans le lecteur Reaplayer qui se charge de diffuser les diapositives et 
						l'enregistrement audio du cours. Il est également possible de d&eacute;placer le curseur pour se positionner 
						&agrave; un endroit particulier du cours.</p>
						<a class="link" href="../files/img/help/Diff_layout_smil.png">Screenshot</a>
						<p>Les diapositives miniatures sont cliquables et permettent de positionner automatiquement le lecteur &agrave; l'endroit de diffusion de la diapo.<br>
						Les diapositives sont cliquables et ouvrent un popup contenant la diapositive dans son format 
						d'origine pour une meilleure lecture. Cela met cependant la lecture du Smil en pause.<br>
						On y trouve les informations concernant le cours, un acc&egrave;s direct aux 
						t&eacute;l&eacute;chargement des diff&eacute;rents m&eacute;dias le composant.</p>	
						<p><span class="underline">NB :</span> Dans le cas d'un cours vid&eacute;o smil, la vid&eacute;o est 
						diffus&eacute;e dans la partie droite. Cette vid&eacute;o est cliquable pour &ecirc;tre diffus&eacute;e dans son format d'origine. La lecture du smil est mise en pause.</p>
						<p><b>Clic sur les ic&ocirc;nes sous les intitul&eacute;s 'smil', 'ogg', 'mp3', 'zip', ou 'pdf':</b><br>
						Ces modes permettent d'avoir un acc&egrave;s direct aux fichiers des m&eacute;dias. 
						Dans le cas du choix du m&eacute;dia Smil, Vous pouvez utiliser n'importe quel logiciel capable de supporter Smil 
						(MediaPlayerClassic, AmbulantPlayer, RealPlayer, Quicktime,....)</p>
						
					
						<h3><a id="downloads"></a>T&eacute;l&eacute;chargement des logiciels et plugins</h3>
						<h5>Adobe Flash Player</h5>
						<p><a href="http://get.adobe.com/fr/flashplayer/">Flash_allplatforms</a></p>
						<h5>Realplayer</h5>
						<p><b>Linux</b> :<br>
						Utilisez votre gestionnaire de paquets.<br>
						S'il n'est pas pr&eacute;sent, vous pouvez l'obtenir ici :&nbsp;<a href="http://www.real.com/linux/">RealPlayer_Linux</a></p>
						<p><b>MacOSX :</b><br>
						<a href="http://www.apple.com/downloads/macosx/internet_utilities/realplayer.html">RealPlayer_MacOSX</a></p>
						<p><b>Windows :</b><br>
						<a href="http://france.real.com/freeplayer_r1p.html">RealPlayer_Windows</a><br>
						<a href="http://www.free-codecs.com/download/Real_Alternative.htm">RealAlternative</a></p>
						<h5>QuickTime</h5>
						<p><b>MacOSX :</b><br>
						<a href="http://www.apple.com/fr/quicktime/download/">Quicktime</a></p>
						<p><b>Windows :</b><br>
						<a href="http://www.apple.com/fr/quicktime/download/">Quicktime</a><br>
						<a href="http://www.free-codecs.com/download/QuickTime_Alternative.htm">QuicktimeAlternative</a></p>
						<h5>MediaPlayerConnectivity</h5>
						<p><a href="https://addons.mozilla.org/fr/firefox/addon/446">Extension Firefox, Seamonkey (Tout OS)</a></p>
						<h5>Autres</h5>   
						<p><a href="http://www.flip4mac.com/wmv_download.htm">Flip4Mac -MacOSX</a><br>
						<a href="http://www.clubic.com/telecharger-fiche11019-media-player-classic.html">MediaPlayerClassic - Windows</a><br>
						<a href="https://helixcommunity.org/frs/?group_id=154">HelixPlayer- Linux</a></p>
			    	</div>
		    	</div>
		    	
	    	</div>
		    	
		    <div class="footer">
		    	<c:import url="include/footer.jsp" />
		    </div>
	    </div>
  </body>
</html>
