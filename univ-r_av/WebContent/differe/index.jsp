<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Page de diff&eacute;r&eacute;</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="../files/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/css/differe.css">
	
	<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href="../files/css/styles_ie6.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="../files/css/differe_ie6.css" media="screen" />
	<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

	<script type="text/javascript" src="../files/js/differe.js"></script>

  </head>
  
  <body>
    <div class="main">
	    <div class="banner">
	    	<a class="zoneAccueil" href="."></a>
	    	<a class="zoneUlpmm" href="http://ulpmultimedia.u-strasbg.fr/" target="external"></a>
	    	<a class="zoneUlp" href="http://www-ulp.u-strasbg.fr/" target="external"></a>
	    	<div class="direct">
	    		<a class="zoneDirect" href=".">Direct</a>
	    	</div>
	    	<div class="differe">
	    		<a class="zoneDiffere" href=".">Différé</a>
	    	</div>
	    </div>
	    <div class="contenu">
	    	<div class="recherche">
	    		<div class="imgRecherche">
	    			<span class="txtRecherche">Recherche</span>
	    			<span class="txtAvance">avanc&eacute;e</span>
	    		</div>
	    		<form method="post" action=".">
		    		<div class="criteres">
		    			<fieldset>
			    			<legend>Type de cours</legend>
			    			<input type="checkbox" name="type" checked>Audio
			    			&nbsp;&nbsp;&nbsp;
			    			<input type="checkbox" name="type">Video
		    			</fieldset>
		    			<!-- <br>
		    			<input type="checkbox" class="cb" name="tous">Tous les cours-->
		    			<br><br>
		    			<img src="../files/img/images/arrowsearch.png" alt=""><input type="submit" name="valider" class="submit" value="Lancez la recherche">
		    		</div>
		    		<div class="criteres">
		    			<label>Enseignant</label>
		    			<select class="champ"></select>
		    			<br>
		    			<label>Formation</label>
		    			<select class="champ"></select>
		    			<br>
		    			<label>Code ETAP</label>
		    			<input type="text" class="champ">
		    			<br>
		    		</div>
		    		<hr>
	    		</form>
	    	</div>
	    	<div class="cours">
	    		<table cellspacing="0">
	    			<tr>
	    				<th colspan="3" id="cours">Les cours</th>
	    				<th colspan="2">Visualisez</th>
	    				<th colspan="4">T&eacute;l&eacute;chargez</th>
	    			</tr>
	    			<tr class="row2">
	    				<td colspan="3">&nbsp;</td>
	    				<td class="tdalign">
		    				<img src="../files/img/links/smile.png" alt="smile"><br>
		    				smil
	    				</td>
	    				<td class="tdalign">
		    				<img src="../files/img/links/real_v2.png" alt="real"><br>
		    				realplayer
	    				</td>
	    				<td class="tdalign">
		    				<img src="../files/img/links/ogg_v2.png" alt="ogg"><br>
		    				ogg
	    				</td>
	    				<td class="tdalign">
		    				<img src="../files/img/links/mp3_v2.png" alt="mp3"><br>
		    				mp3
	    				</td>
	    				<td class="tdalign">
		    				<img src="../files/img/links/winzip3.png" alt="zip"><br>
		    				zip
	    				</td>
	    				<td class="tdalign">
		    				<img src="../files/img/links/acrobat.png" alt="pdf"><br>
		    				pdf
	    				</td>
	    			</tr>
	    			<tr class="row1">
	    				<td><img src="../files/img/images/video.png" alt="video"></td>
	    				<td>	    				
		    				<b>Titre : </b>Cours0607LS6_070411_Leçon8<br>
		    				<b>Enseignant : </b>René de Quenaudon (LS6)<br>
		    				<div id="row1col1link">
		    					<a href="#" onclick= "switchDetails('row1')">[+] plus de détails</a>
		    				</div>	    				
		    				<div id="row1col1details" class="hidden">
			    				<b>Formation : </b>Droit des escrocs<br>
			    				<b>Sujet : </b>Comment contourner la loi<br>
			    				<a href="#" onclick= "switchDetails('row1')">[-] moins de détails</a>
		    				</div>	    				
	    				</td>
	    				<td>
		    				<b>Date : </b>11/04/2007 <br>
		    				<b>Consultations : </b>50 <br>
		    				<div id="row1col2link">
		    					<a href="#" onclick= "switchDetails('row1')">[+] plus de détails</a>
		    				</div>
		    				<div id="row1col2details" class="hidden">
			    				<b>Dur&eacute;e : </b>3h40m50s<br>
			    				<b>Type : </b>audio<br>
			    				<a href="#" onclick= "switchDetails('row1')">[-] moins de détails</a>
		    				</div>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    			</tr>
	    			<tr class="row2">
	    				<td><img src="../files/img/images/video.png" alt="video"></td>
	    				<td>	    				
		    				<b>Titre : </b>Cours0607LS6_070411_Leçon8<br>
		    				<b>Enseignant : </b>René de Quenaudon (LS6)<br>
		    				<div id="row2col1link">
		    					<a href="#" onclick= "switchDetails('row2')">[+] plus de détails</a>
		    				</div>	    				
		    				<div id="row2col1details" class="hidden">
			    				<b>Formation : </b>Droit des escrocs<br>
			    				<b>Sujet : </b>Comment contourner la loi<br>
			    				<a href="#" onclick= "switchDetails('row2')">[-] moins de détails</a>
		    				</div>	    				
	    				</td>
	    				<td>
		    				<b>Date : </b>11/04/2007 <br>
		    				<b>Consultations : </b>50 <br>
		    				<div id="row2col2link">
		    					<a href="#" onclick= "switchDetails('row2')">[+] plus de détails</a>
		    				</div>
		    				<div id="row2col2details" class="hidden">
			    				<b>Dur&eacute;e : </b>3h40m50s<br>
			    				<b>Type : </b>audio<br>
			    				<a href="#" onclick= "switchDetails('row2')">[-] moins de détails</a>
		    				</div>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    			</tr>
	    			<tr class="row1">
	    				<td><img src="../files/img/images/video.png" alt="video"></td>
	    				<td>	    				
		    				<b>Titre : </b>Cours0607LS6_070411_Leçon8<br>
		    				<b>Enseignant : </b>René de Quenaudon (LS6)<br>
		    				<div id="row3col1link">
		    					<a href="#" onclick= "switchDetails('row3')">[+] plus de détails</a>
		    				</div>	    				
		    				<div id="row3col1details" class="hidden">
			    				<b>Formation : </b>Droit des escrocs<br>
			    				<b>Sujet : </b>Comment contourner la loi<br>
			    				<a href="#" onclick= "switchDetails('row3')">[-] moins de détails</a>
		    				</div>	    				
	    				</td>
	    				<td>
		    				<b>Date : </b>11/04/2007 <br>
		    				<b>Consultations : </b>50 <br>
		    				<div id="row3col2link">
		    					<a href="#" onclick= "switchDetails('row3')">[+] plus de détails</a>
		    				</div>
		    				<div id="row3col2details" class="hidden">
			    				<b>Dur&eacute;e : </b>3h40m50s<br>
			    				<b>Type : </b>audio<br>
			    				<a href="#" onclick= "switchDetails('row3')">[-] moins de détails</a>
		    				</div>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    			</tr>
	    			<tr class="row2">
	    				<td><img src="../files/img/images/video.png" alt="video"></td>
	    				<td>	    				
		    				<b>Titre : </b>Cours0607LS6_070411_Leçon8<br>
		    				<b>Enseignant : </b>René de Quenaudon (LS6)<br>
		    				<div id="row4col1link">
		    					<a href="#" onclick= "switchDetails('row4')">[+] plus de détails</a>
		    				</div>	    				
		    				<div id="row4col1details" class="hidden">
			    				<b>Formation : </b>Droit des escrocs<br>
			    				<b>Sujet : </b>Comment contourner la loi<br>
			    				<a href="#" onclick= "switchDetails('row4')">[-] moins de détails</a>
		    				</div>	    				
	    				</td>
	    				<td>
		    				<b>Date : </b>11/04/2007 <br>
		    				<b>Consultations : </b>50 <br>
		    				<div id="row4col2link">
		    					<a href="#" onclick= "switchDetails('row4')">[+] plus de détails</a>
		    				</div>
		    				<div id="row4col2details" class="hidden">
			    				<b>Dur&eacute;e : </b>3h40m50s<br>
			    				<b>Type : </b>audio<br>
			    				<a href="#" onclick= "switchDetails('row4')">[-] moins de détails</a>
		    				</div>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    			</tr>
	    			<tr class="row1">
	    				<td><img src="../files/img/images/video.png" alt="video"></td>
	    				<td>	    				
		    				<b>Titre : </b>Cours0607LS6_070411_Leçon8<br>
		    				<b>Enseignant : </b>René de Quenaudon (LS6)<br>
		    				<div id="row5col1link">
		    					<a href="#" onclick= "switchDetails('row5')">[+] plus de détails</a>
		    				</div>	    				
		    				<div id="row5col1details" class="hidden">
			    				<b>Formation : </b>Droit des escrocs<br>
			    				<b>Sujet : </b>Comment contourner la loi<br>
			    				<a href="#" onclick= "switchDetails('row5')">[-] moins de détails</a>
		    				</div>	    				
	    				</td>
	    				<td>
		    				<b>Date : </b>11/04/2007 <br>
		    				<b>Consultations : </b>50 <br>
		    				<div id="row5col2link">
		    					<a href="#" onclick= "switchDetails('row5')">[+] plus de détails</a>
		    				</div>
		    				<div id="row5col2details" class="hidden">
			    				<b>Dur&eacute;e : </b>3h40m50s<br>
			    				<b>Type : </b>audio<br>
			    				<a href="#" onclick= "switchDetails('row5')">[-] moins de détails</a>
		    				</div>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    			</tr>
	    			<tr class="row2">
	    				<td><img src="../files/img/images/video.png" alt="video"></td>
	    				<td>	    				
		    				<b>Titre : </b>Cours0607LS6_070411_Leçon8<br>
		    				<b>Enseignant : </b>René de Quenaudon (LS6)<br>
		    				<div id="row6col1link">
		    					<a href="#" onclick= "switchDetails('row6')">[+] plus de détails</a>
		    				</div>	    				
		    				<div id="row6col1details" class="hidden">
			    				<b>Formation : </b>Droit des escrocs<br>
			    				<b>Sujet : </b>Comment contourner la loi<br>
			    				<a href="#" onclick= "switchDetails('row6')">[-] moins de détails</a>
		    				</div>	    				
	    				</td>
	    				<td>
		    				<b>Date : </b>11/04/2007 <br>
		    				<b>Consultations : </b>50 <br>
		    				<div id="row6col2link">
		    					<a href="#" onclick= "switchDetails('row6')">[+] plus de détails</a>
		    				</div>
		    				<div id="row6col2details" class="hidden">
			    				<b>Dur&eacute;e : </b>3h40m50s<br>
			    				<b>Type : </b>audio<br>
			    				<a href="#" onclick= "switchDetails('row6')">[-] moins de détails</a>
		    				</div>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    				<td class="tdalign">
	    					<a href="."><img src="../files/img/images/chip.png" alt="chip"></a>
	    				</td>
	    			</tr>
	    		</table>
	    		<div class="pagination">
	    			<a href=".">&lt;&lt;</a>
	    			&nbsp;
					<b>1</b>
					&nbsp;
					<a href=".">2</a>&nbsp;
					<a href=".">3</a>&nbsp;
					<a href=".">4</a>&nbsp;
					<a href=".">5</a>&nbsp;
					<a href=".">6</a>&nbsp;
					<a href=".">7</a>&nbsp;
					<a href=".">8</a>&nbsp;
					<a href=".">9</a>&nbsp;
					<a href=".">10</a>&nbsp;
					<a href=".">11</a>&nbsp;
					<a href=".">12</a>&nbsp;
					<a href=".">13</a>&nbsp;					
					<a href=".">14</a>&nbsp;
					<a href=".">15</a>&nbsp;
					<a href=".">16</a>&nbsp;
					<a href=".">17</a>&nbsp;
					<a href=".">18</a>&nbsp;
					<a href=".">19</a>&nbsp;
					<a href=".">20</a>&nbsp;
					<a href=".">&gt;&gt;</a>&nbsp;
				</div>
	    	</div>
    	</div>
	    	
	    <div class="footer">
	    	<p>
		    	Réalisation du site par ULP Multim&eacute;dia - 2007 <br>
		    	<a href=".">Contact</a> - <a href=".">Informations l&eacute;gales</a> - <a href=".">Liens</a>
	    	</p>
	    </div>
    </div>
  </body>
</html>
