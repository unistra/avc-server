<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:useBean id="newsbean" class="paquet.SqlBean" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Projet AudioVideoCours</title>
  <script language="JavaScript" src="code/js_videocours_.js"></script>
  <link href="code/style_videocours.css" rel="stylesheet"
 type="text/css">
 <link rel="alternate" type="application/rss+xml" title="Liste de tous les cours" href="./rss/liste_des_cours.xml"/>
</head>
<body leftmargin="0" topmargin="0" alink="#000000" bgcolor="#ffffff"
 link="#666666" marginheight="0" marginwidth="0" text="#666666"
 vlink="#666666">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody>
    <tr>
      <td align="right" background="_mm/top_g_bg.gif" valign="top"><a
 href="http://ulpmultimedia.u-strasbg.fr/" target="_blank"><img
 src="_mm/logo_ulpmm.jpg" border="0" height="70" width="103"></a></td>
      <td width="757">
      <table align="center" border="0" cellpadding="0" cellspacing="0"
 width="757">
        <tbody>
          <tr>
            <td rowspan="2"><img src="_mm/top_g.jpg" height="119"
 width="187"></td>
            <td><img src="_mm/top_h_V2.jpg" height="60" width="570"></td>
          </tr>
          <tr>
            <td>
            <table background="_mm/top_b_bg.gif" border="0"
 cellpadding="0" cellspacing="0" width="100%">
              <tbody>
                <tr>
                  <td width="86"> <a
 onmouseover="SetImg('roll1','_mm/btn1_roll.jpg');return true"
 onmouseout="SetImg('roll1','_mm/btn1.jpg');return true"
 title="Consultation en direct" href="live/index.jsp"><img
 src="_mm/btn1.jpg" alt="Consultation en direct" name="roll1" id="roll1"
 border="0" height="59" width="86"></a></td>
                  <td width="86"> <a
 onmouseover="SetImg('roll2','_mm/btn2_roll.jpg');return true"
 onmouseout="SetImg('roll2','_mm/btn2.jpg');return true"
 title="Consultation en diff&eacute;r&eacute;" href="differe/index.jsp"><img
 src="_mm/btn2.jpg" alt="Consultation en dif&eacute;r&eacute;e" name="roll2"
 id="roll2" border="0" height="59" width="86"></a></td>
                  <!-- <td width="49"> <a
 onmouseover="SetImg('roll3','_mm/btn3_roll.jpg');return true"
 onmouseout="SetImg('roll3','_mm/btn3.jpg');return true"
 title="Mise en ligne de cours offline" href="envoi/index.jsp"><img
 src="_mm/btn3.jpg" name="roll3" border="0" height="59" width="49"></a></td>-->
                  <td width="49"> <a
 onmouseover="SetImg('roll4','_mm/btn_dl_roll.jpg');return true"
 onmouseout="SetImg('roll4','_mm/btn_dl.jpg');return true"
 title="Zone de t&eacute;l&eacute;chargement" href="telechargement/index.jsp"><img
 src="_mm/btn_dl.jpg" name="roll4" border="0" height="59" width="78"></a></td>
                  <td width="72"> <a
 onmouseover="SetImg('roll5','_mm/btn_pod_roll.jpg');return true"
 onmouseout="SetImg('roll5','_mm/btn_pod.jpg');return true"
 title="Zone de Podcasting" href="podcast/index.jsp"><img
 src="_mm/btn_pod.jpg" name="roll5" border="0" height="59" width="72"></a></td>
                  <td align="right"><a href="aide/index.jsp"
 onMouseOver="SetImg('roll6','_mm/btn_aide_roll.jpg');return true"
 onMouseOut="SetImg('roll6','_mm/btn_aide.jpg');return true"><img src="_mm/btn_aide.jpg" alt="Aide en ligne" title="Aide en ligne" name="roll6" width="27" height="59" border="0"></a></td>

                </tr>
              </tbody>
            </table>
            </td>
          </tr>
        </tbody>
      </table>
      </td>
      <td background="_mm/top_d_bg.gif">&nbsp;</td>
    </tr>
  </tbody>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody>
    <tr>
      <td align="center">
      <table border="0" cellpadding="0" cellspacing="0" width="757">
        <tbody>
          <tr>
            <td><img src="_mm/zero.gif" height="5" width="500"></td>
          </tr>
          <tr>
            <td>
            <table border="0" cellpadding="0" cellspacing="0"
 width="100%">
              <tbody>
                <tr>
                  <td><img src="_mm/pyramide_rouge.jpg" height="50"
 width="45"></td>
                  <td valign="bottom">
                  <table border="0" cellpadding="0" cellspacing="0"
 width="100%">
                    <tbody>
                      <tr>
                        <td class="ArialBlack18">Pr&eacute;sentation</td>
                      </tr>
                      <tr>
                        <td><img src="_mm/titre_trait.gif" height="6"  width="550"></td>
                      </tr>
                    </tbody>
                  </table>
                  </td>
                </tr>
              </tbody>
            </table>
            </td>

          </tr>
          
          <tr>
            <td valign="top">
            <table border="0" cellpadding="0" cellspacing="0"  width="100%">
              <tbody>
                <tr>
                  <td width="450">
                  <table border="0" cellpadding="0" cellspacing="0" width="600">
                    <tbody>
                    <br/>
                      <tr>
                        <td class="Arial12">
                           	
							Ce site est encore en cours de développement. Il permet de visionner les cours faits avec la version 2 du client en cours de déploiement.<br>
							<br>
							Actuellement, le différé, l'upload et la récupération d'un fichier podcast sont disponibles et fonctionnels. Nous recommendons RealPlayer
							 version 10 pour la lecture d'un cours en différé sous Windows et MacOS, HelixPlayer 1.0 pour Linux.<br>
							
							<br>
							Ce site va s'enrichir dans l'avenir par de nouvelles fonctionnalités :<br>
							<ul>
							<li> Accès authentifiés</li>
							<li> Choix des licences de diffusion (de type Creative Commons)</li>
							<li> Choix du thème du site</li>
							<li> Statistiques</li>
							<li> Direct</li>
							<li> Vidéocours</li>
							<li> Possibilités d'enrichissement des cours avec des commentaires de l'auteur</li>
							<li> Connexion à partir d'Univ-R</li>
							<li> Flux RSS pour les news et le podcasting</li>
							</ul>
							
							<br><br>
							
							<a href="rss/listeFlux.jsp">Liste des flux RSS du site</a> <a href="rss/listeFlux.jsp"><img src="_mm/Rssicon.gif"></a>

                        </td>
                      </tr>
                    </tbody>
                  </table>
                  </td>
                  <td><img src="_mm/zero.gif" height="10" width="10"></td>
                </tr>
              </tbody>
            </table>
            </td>
          </tr>
        </tbody>
      </table>
      </td>
    </tr>
  </tbody>
</table>
</body>
</html>
