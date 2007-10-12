<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:directive.page import="org.ulpmm.univrav.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<%
	Locale l = request.getLocale();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Aide"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/help.css">
	
	<!--[if IE]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/help_ie6.css" media="screen" />
	<![endif]-->
	
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>

  </head>
  
  <body>
	    <div class="main">
		    <div class="banner">
		    	<c:import url="include/banner.jsp" />
		    </div>
		    <div class="contents">
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
						<li><a href="#downloads">Téléchargements</a></li>
					</ul>
				</div>
		    	<br>
		    	<div class="columns">
			    	<div class="leftCol">
			    		<h3><a id="browsers"></a>Navigateurs conseill&eacute;s</h3>
						<p>Firefox 2.0 (Linux, MacOSX, Windows), IE7 (Windows)</p>
						
						<h3><a id="live"></a>Cours en direct</h3>
						<h5>Lieux et salles</h5>
						<a href="../files/img/help/Live.png"><img alt="Live" src="../files/img/help/thumbs/Live.jpg"></a>
						<p>Disponible uniquement &agrave; partir des salles et amphith&eacute;atres automatis&eacute;s et sur 
						la volont&eacute; de l'enseignant, le direct est disponible sur le site de façon hi&eacute;rarchis&eacute;e 
						par lieu puis par salle.<br>
						Pour obtenir la liste des salles, il suffit de cliquer sur le lieu.<br>
						Cliquer sur une salle permet d'&eacute;couter et de suivre le cours en direct.</p>
						<h5>Diffusion et &eacute;coute du Live</h5>
						<a href="../files/img/help/Live_audio.png"><img alt="Live_audio" src="../files/img/help/thumbs/Live_audio.jpg"></a>
						<p>Les diapositives sont diffus&eacute;es simultan&eacute;ment avec le flux audio.<br>
						Pour le flux audio, vous avez besoin d'avoir un plugin permettant leur lecture.</p>
						<p><span class="underline">Exemples de plugins :</span><br>
						Mplayerplug-in sous Linux<br>
						Windows Media Player sous Windows<br>
						Quicktime pour MacOSX avec l'ajout du logiciel Flip4Mac</p>
						<p><span class="underline">NB :</span> Firefox dispose d'une extension 'MediaPlayer Connectivity' 
						qui permet de se substituer aux plugins web des players et d'utiliser n'importe quel logiciel audio 
						install&eacute; sur la machine pour lire le flux.</p>
						<p>Le live video n&eacute;cessite la pr&eacute;sence de RealMediaPlayer.</p>
						
						<h3><a id="podcasting"></a>Podcasting et flux RSS</h3>
						<a href="../files/img/help/Podcast.png"><img alt="Podcast" src="../files/img/help/thumbs/Podcast.jpg"></a>
						<p>Tous les cours sont disponibles au t&eacute;l&eacute;chargement via le site, en cliquant sur l'un des 
						m&eacute;dias disponibles ou via l'abonnement à un flux RSS.</p><br>
						<h5>Abonnement &agrave; un flux RSS</h5>
						<a href="../files/img/help/Rss.png"><img alt="rss" src="../files/img/help/thumbs/Rss.png"></a>
						<p>Cliquer sur l'ic&ocirc;ne orange dans la barre d'adresse de Firefox permet de lister les 
						diff&eacute;rents flux disponibles.</p>
						<br><br><br>
						<a href="../files/img/help/Rss_FF.png"><img alt="Rss_FF" src="../files/img/help/thumbs/Rss_FF.jpg"></a>
						<p>En s&eacute;lectionnant un flux dans cette liste, Firefox affiche une page permettant d'avoir le listing 
						de tous les cours.<br>
						Le lien donne un acc&egrave;s direct &agrave; la page de diffusion du cours.</p>
						<br>
						<a href="../files/img/help/Rss_opera.png"><img alt="Rss_opera" src="../files/img/help/thumbs/Rss_opera.jpg"></a>
						<p>Vous pouvez utiliser d'autres logiciels capables de g&eacute;rer les flux Rss comme le navigateur Opera ou 
						les lecteurs audio de type itunes. Il vous suffit de r&eacute;cup&eacute;rer l'adresse du fichier XML et de 
						la mettre dans votre logiciel de gestion des flux. Par exemple, un simple glisser-d&eacute;poser depuis 
						la barre d'adresse de Firefox vers itunes suffit pour s'abonner.</p>
						<a href="../files/img/help/Rss_podcast_amarok.png"><img alt="Amarok_pod" src="../files/img/help/thumbs/Rss_podcast_amarok.jpg"></a>
						<p><span class="underline">Exemples :</span> Opera, Amarok et Itunes</p>
						<p><span class="underline">NB :</span> Les lecteurs audios permettent de g&eacute;rer sa liste de cours et 
						de les &eacute;couter directement apr&egrave;s obtention du m&eacute;dia. Attention, seul le fichier mp3 
						est cependant disponible via les logiciels audio.</p>
						<br>
						<a href="../files/img/help/Rss_itunes.png"><img alt="Rss_itunes" src="../files/img/help/thumbs/Rss_itunes.jpg"></a>
			    	</div>
			    	
			    	<div class="rightCol">
			    		<h3><a id="configuration"></a>Configuration minimale</h3>
			    		<p>Realplayer 10 (Linux, MacOSX, Windows)<br>
						Un plugin pour la lecture des flux audio (Live)</p>
						
						<h3><a id="recorded"></a>Cours en différé</h3>
						<h5>Filtrage de l'Affichage</h5>
						<p>On dispose d'options de filtrage pour l'affichage des cours ainsi que d'une fonction de recherche.</p>
						<h5>Listings des Cours</h5>
						<a href="../files/img/help/Differe.png"><img alt="Differe" src="../files/img/help/thumbs/Differe.jpg"></a>
						<p>Les cours sont disponibles selon diff&eacute;rents modes.</p>	
						<p><b>Clic sur la diapositive :</b><br>
						Mode en ligne. N&eacute;cessite la pr&eacute;sence du plugin RealPlayer sur votre machine. 
						C'est le mode recommand&eacute; pour la visualisation des cours.</p>		
						<p><b>Clic sur le logo 'Smil' :</b><br>
						Ce mode permet d'avoir un acc&egrave;s direct au fichier de synchronisation des m&eacute;dias. 
						Vous pouvez par ce biais utiliser n'importe quel logiciel capable de supporter Smil 
						(MediaPlayerClassic, AmbulantPlayer, RealPlayer, Quicktime,....)</p>			
						<h5>Diffusion et &eacute;coute du diff&eacute;r&eacute; (Mode recommandé)</h5>
						<a href="../files/img/help/Diff_layout.png"><img alt="Diff_layout" src="../files/img/help/thumbs/Diff_layout.jpg"></a>
						<p>Le fichier Smil est lu dans le lecteur Reaplayer qui se charge de diffuser les diapositives et 
						l'enregistrement audio du cours. Il est possible de d&eacute;placer le curseur pour se positionner 
						&agrave; un endroit particulier du cours.<br>
						Les diapositives du Smil sont cliquables et ouvrent un popup contenant la diapositive dans son format 
						d'origine pour une meilleure lecture. Cela met cependant la lecture du Smil en pause.<br>
						Les petites diapositives du cours sont cliquables et permettent de positionner automatiquement le lecteur 
						&agrave; l'endroit de diffusion de la diapo.<br>
						Sur la partie droite, on trouve les informations concernant le cours, un acc&egrave;s direct aux 
						t&eacute;l&eacute;chargement des diff&eacute;rents m&eacute;dias le composant, mais &eacute;galement 
						un acc&egrave;s direct aux autres cours de l'enseignant.</p>	
						<p><span class="underline">NB :</span> Dans le cas d'un cours vid&eacute;o, la vid&eacute;o est 
						diffus&eacute;e dans le coin sup&eacute;rieur droit.</p>
	
						<h3><a id="downloads"></a>T&eacute;l&eacute;chargement des logiciels et plugins</h3>
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
