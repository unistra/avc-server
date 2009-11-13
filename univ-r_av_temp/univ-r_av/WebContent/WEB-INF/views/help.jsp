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
							<li><a href="#browsers">Navigateurs conseill&eacute;s</a></li>
							<li><a href="#configuration">Configuration minimale</a></li>
							<li><a href="#accueil">Accueil</a></li>
							<li><a href="#recorded">Cours enregistr&eacute;s</a></li>
							<li><a href="#live">Cours en direct</a></li>
							<li><a href="#podcasting">Podcasting et flux RSS</a></li>
							<li><a href="#myspace">Mon espace</a></li>
							<li><a href="#publication">Publication</a></li>
							<li><a href="#downloads">T&eacute;l&eacute;chargements</a></li>
						</ul>
					</div>
					
					<div class="helpLinks2">
						<ul>
							<li><a href="http://support.unistra.fr"><fmt:message key="supportLink"/></a></li>
							<li><a href="./contactUs"><fmt:message key="contactUs"/></a></li>
						</ul>
					</div>
									
					
				</div>
		    	
		    	
		    	<div class="columns">
		    	
			    	<div class="leftCol">
			    		
			    		<h3><a id="browsers"></a>Navigateurs conseill&eacute;s</h3>
						<p>Firefox 3.0 (Linux, MacOSX, Windows), IE7 (Windows), with Javascript</p>
						
						<h3><a id="accueil"></a>Accueil</h3>
						<h5>Pr&eacute;sentation</h5>
						<p>La page d'accueil donne un aper&ccedil;u du site et permet d'acc&eacute;der &agrave; l'ensemble des fonctionnalit&eacute;s. On y trouve les derni&egrave;res publications, 
						une s&eacute;lection de publications que l'on a souhait&eacute; mettre en avant, ainsi qu'une collection de publications int&eacute;ressantes.</p>
						<a class="link" href="../files/img/help/home.png">Screenshot</a>
						<h5>Changer de style</h5>
						<p>Pour changer le style du site, vous pouvez cliquer sur le lien « Style ». Une fen&ecirc;tre appara&icirc;tra et vous proposera les diff&eacute;rents styles disponibles.</p>
						<a class="link" href="../files/img/help/boxstyle.png">Screenshot</a>
						<h5>Changer de langue</h5>
						<p>Pour changer la langue du site, vous pouvez cliquer sur le lien « Langue ». Une fen&ecirc;tre appara&icirc;tra et vous proposera les diff&eacute;rentes langues disponibles (francais et anglais).</p>
						<a class="link" href="../files/img/help/boxlangue.png">Screenshot</a>
						<h5>T&eacute;l&eacute;chargements</h5>
						<p>Pour t&eacute;l&eacute;charger le client ou les sources du site, vous pouvez cliquer sur le lien « T&eacute;l&eacute;chargements ». Une fen&ecirc;tre appara&icirc;tra et vous proposera les diff&eacute;rents t&eacute;l&eacute;chargements disponibles.</p>
						<a class="link" href="../files/img/help/boxdl.png">Screenshot</a>
										
						
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
						<p>Les diapositives sont cliquables et ouvrent un popup contenant la diapositive dans son format d'origine pour une meilleure lecture.</p>
						<a class="link" href="../files/img/help/Live_audio.png">Screenshot</a>
														
						<p><span class="underline">NB :</span> Les lecteurs audio permettent de g&eacute;rer sa liste de cours et 
						de les &eacute;couter directement apr&egrave;s obtention du m&eacute;dia. Attention, seul le fichier mp3 
						est cependant disponible via les logiciels audio.</p>
												
						<h3><a id="myspace"></a>Mon espace</h3>
						<h5>Pr&eacute;sentation</h5>
						<p>Pour acc&eacute;der &agrave; votre espace personnel, cliquez sur "Mon espace" et entrez votre login et mot de passe ENT.</p>
			    		<p>Vous aurez alors la possibilit&eacute; :<br>
			    		- d'enregistrer ou de modifier votre e-mail pour recevoir les notifications de r&eacute;ception de vos cours (bouton "modifier mon e-mail")<br>
			    		- de visualiser vos cours qui sont ratach&eacute;s &agrave; votre login ENT (voir la liste)<br>
			    		- d'&eacute;diter vos cours afin de modifier le titre, la description, la visibilit&eacute;, etc... (lien "Editer")<br>
			    		- de d&eacute;poser directement un fichier audio ou vid&eacute;o (bouton "d&eacute;poser un fichier")</p>
			    		<p>Enfin, pour quitter votre espace, il vous suffit d'appuyer sur "D&eacute;connexion".</p>
			    		<a class="link" href="../files/img/help/myspace.png">Screenshot</a>
			    		<h5>Editer un cours</h5>
			    		<p>Pour &eacute;diter un cours, il vous suffit de cliquer sur « Editer », d'effectuer vos modifications dans le formulaire, et de valider.<br>
			    		Vous avez ainsi la possibilit&eacute; de modifier le titre du cours, sa description, la discipline concern&eacute;e, le nom et le pr&eacute;nom de l'auteur, le code d'acc&egrave;s, la visibilit&eacute; et les tags.</p>
			    		<p>Il est &eacute;galement possible de d&eacute;poser un fichier compl&eacute;mentaire &agrave; l'enregistrement. Celui-ci sera ainsi t&eacute;l&eacute;chargeable dans l'interface de visualisation flash du cours. Les formats suivants sont pris en charge: Pdf, Ppt, Pptx, Odp, Docx, Doc, Odt, Archive Zip. Pour ajouter plusieurs documents compl&eacute;mentaires &agrave; l'enregistrement, veuillez les mettre dans une archive zip.</p>
			    		<a class="link" href="../files/img/help/editcourse.png">Screenshot</a>
			    		<h5>D&eacute;poser un fichier</h5>
			    		<p>Pour d&eacute;poser un fichier, il vous suffit de cliquer sur le bouton « D&eacute;poser un fichier », de remplir le formulaire et de valider.</p>
			    		<p>Les formats vid&eacute;o suivants sont pris en charge: Avi, Mov, Mp4, Rm, Rv, Mkv,Divx, Mpg, Wmv, Flv.<br>
			    		Les formats audio suivants sont pris en charge: Mp3, Ogg.</p>
			    		<p>L'upload d&eacute;pend de la taille de votre fichier et de votre vitesse d'envoi. Cela peut &ecirc;tre assez long. Soyez patient, ne fermez pas la page "envoi en cours" avant d'avoir re&ccedil;u le message de succ&egrave;s ou d'&eacute;chec.</p>
			    		<p>L'option Haute-D&eacute;finition vous permet d'uploader une vid&eacute;o haute-d&eacute;finition. Attention, cette option augmente consid&eacute;rablement le temps de traitement de votre vid&eacute;o. En effet, le traitement d'une vid&eacute;o en mode Haute-D&eacute;finition dure 3 heures pour 1 heure de film.</p>
			    		<a class="link" href="../files/img/help/upload.png">Screenshot</a>
			    		
			    		<h3><a id="publication"></a>Publication</h3>
			    		<h5>Depuis un client v1.13 ou sup&eacute;rieur</h5>
			    		<p>Apr&egrave;s avoir effectu&eacute; un enregistrement via le client AVC (voir doc correspondante) et cliqu&eacute; sur le  bouton « Publier », vous serez rediriger sur une page du site permettant la saisie des informations et la publication finale de l'enregistrement:</p>
			    		<a class="link" href="../files/img/help/pub1.png">Screenshot</a>
			    		
			    		<p>Deux possibilit&eacute;s s'offrent &agrave; vous:</p>
			    		<p>1) Compte UDS: En vous connectant avec votre compte ENT, votre enregistrement sera associ&eacute; &agrave; votre compte. Ainsi, vous aurez par la suite acc&egrave;s aux fonctionnalit&eacute;s de « mon espace » pour &eacute;diter votre enregistrement par exemple (modifier le titre, la visibilit&eacute;, la description, les tags, ...)</p>
			    		<p>2) Libre: En choisissant l'option libre, vous pouvez envoyer un enregistrement sans avoir de compte UDS. N&eacute;anmoins, vous n'aurez pas acc&egrave;s aux fonctionnalit&eacute;s de « mon espace ». Vous ne pourrez donc plus &eacute;diter votre cours par exemple.</p>
			    		<p>Apr&egrave;s avoir choisi l'une des deux options, vous devez remplir le formulaire. Les champs « Titre » et « Nom Auteur » sont obligatoires.</p>
			    		<a class="link" href="../files/img/help/pub2.png">Screenshot</a>
			    					    		
			    		<p>Validez l'enregistrement en cliquant sur « Publier l'enregistrement ». Un message vous indiquant que le fichier a &eacute;t&eacute; correctement envoy&eacute; appara&icirc;tra &agrave; l'&eacute;cran.</p>
			    		
			    	</div>
			    	
			    	<div class="rightCol">
			    				    	
			    		<h3><a id="configuration"></a>Configuration minimale</h3>
			    		<p>Flash Player (Linux, MacOSX, Windows)</p>
						
						<h3><a id="recorded"></a>Cours enregistr&eacute;s</h3>
						<h5>Recherche</h5>
						<p>On dispose d'une fonction de recherche (Launch Search) avec des options de filtrage pour l'affichage des cours.<br>
						Vous pouvez &eacute;galement acc&eacute;der directement aux cours d'un auteur et/ou d'une formation gr&acirc;ce &agrave; l'url:<br> 
						http://audiovideocours.u-strasbg.fr/avc/courses?author=...&formation=...</p>
						<a class="link" href="../files/img/help/Search.png">Screenshot</a>
						<h5>Tags</h5>
						<p>Certains cours sont associ&eacute;s &agrave; des mots cl&eacute;s que l'on appelle « tag ». Vous pouvez ainsi afficher l'ensemble des cours correspondant &agrave; un ou plusieurs tags en les s&eacute;lectionnant.<br>
						Vous pouvez &eacute;galement acc&eacute;der directement aux cours d'un ou plusieurs tags gr&acirc;ce &agrave; l'url: http://audiovideocours.u-strasbg.fr/avc/tags?tags=Alsace+Alg&eacute;rie+...</p>
						<a class="link" href="../files/img/help/Tags.png">Screenshot</a>
						<h5>Listings des Cours</h5>
						<p>Les cours sont disponibles en mode flash en cliquant sur l'onglet « Enregistr&eacute; ».</p>	
						<p><b>Clic sur l'ic&ocirc;ne &agrave; gauche ou sur l'ic&ocirc;ne sous l'intitul&eacute; 'flash' :</b><br>
						Mode en ligne. N&eacute;cessite la pr&eacute;sence de Flash Player sur votre machine. 
						C'est le mode recommand&eacute; pour la visualisation des cours.</p>		
						<a class="link" href="../files/img/help/Differe.png">Screenshot</a>			
						<h5>Diffusion et &eacute;coute du diff&eacute;r&eacute;</h5>
						<p> En flash, le cours est lu dans le lecteur flash qui se charge de diffuser les diapositives et 
						l'enregistrement audio/vid&eacute;o du cours. Il est possible de d&eacute;placer le curseur pour se positionner 
						&agrave; un endroit particulier du cours. </p>
						<a class="link" href="../files/img/help/Diff_layout_flash.png">Screenshot</a>
						<p>Les miniatures sont cliquables et permettent de positionner automatiquement le lecteur &agrave; l'endroit de diffusion de la diapositive.<br>
						Les diapositives sont cliquables et ouvrent un popup contenant la diapositive dans son format 
						d'origine pour une meilleure lecture. Celles-ci s'adaptent &eacute;galement &agrave; la taille de la fen&ecirc;tre de votre navigateur.<br>
						Les tags associ&eacute;s au cours sont &eacute;galement cliquables et permettent d'afficher l'ensemble des cours correspondant au tag s&eacute;lectionn&eacute;.<br>
						On y trouve les informations concernant le cours, un acc&egrave;s direct aux 
						t&eacute;l&eacute;chargement des diff&eacute;rents m&eacute;dias le composant, et un permalien.</p>	
						<p><b>Clic sur les ic&ocirc;nes sous les intitul&eacute;s 'smil', 'ogg', 'mp3', 'zip', ou 'pdf':</b><br>
						Ces modes permettent d'avoir un acc&egrave;s direct aux fichiers des m&eacute;dias. 
						Dans le cas du choix du m&eacute;dia Smil, Vous pouvez utiliser n'importe quel logiciel capable de supporter Smil tel que RealPlayer.</p>
						<p>Le fichier Smil est lu dans le lecteur Reaplayer qui se charge de diffuser les diapositives et 
						l'enregistrement audio du cours. Il est &eacute;galement possible de d&eacute;placer le curseur pour se positionner 
						&agrave; un endroit particulier du cours.</p>
						<a class="link" href="../files/img/help/Diff_layout_smil.png">Screenshot</a>
						<p>Les miniatures sont cliquables et permettent de positionner automatiquement le lecteur &agrave; l'endroit de diffusion de la diapo.<br>
						Les diapositives sont cliquables et ouvrent un popup contenant la diapositive dans son format 
						d'origine pour une meilleure lecture. Cela met cependant la lecture du Smil en pause.<br>
						On y trouve les informations concernant le cours, un acc&egrave;s direct aux 
						t&eacute;l&eacute;chargement des diff&eacute;rents m&eacute;dias le composant.</p>	
						<p><span class="underline">NB :</span> Dans le cas d'un cours vid&eacute;o smil, la vid&eacute;o est 
						diffus&eacute;e dans la partie droite. Cette vid&eacute;o est cliquable pour &ecirc;tre diffus&eacute;e dans son format d'origine. La lecture du smil est mise en pause.</p>
					
					    <h3><a id="podcasting"></a>Podcasting et flux RSS</h3>
						<h5>Abonnement &agrave; un flux RSS</h5>
						<p>Tous les cours sont disponibles au t&eacute;l&eacute;chargement via le site, en cliquant sur l'un des 
						m&eacute;dias disponibles ou via l'abonnement &agrave; un flux RSS.</p>						
						<p>Cliquer sur l'ic&ocirc;ne orange dans la barre d'adresse de Firefox permet de s'abonner &agrave; l'ensemble du site Audiovid&eacute;cours. 
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
			    	</div>
		    	</div>
		    	
	    	</div>
		    	
		    <div class="footer">
		    	<c:import url="include/footer.jsp" />
		    </div>
	    </div>
  </body>
</html>
