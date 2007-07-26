<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="org.ulpmm.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><%=Messages._("Cours enregistr&eacute;s", l)%></title>

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
	    	<a class="bannerPageZone" href="."></a>
	    	<a class="ulpmmZone" href="http://ulpmultimedia.u-strasbg.fr/" target="external"></a>
	    	<a class="ulpZone" href="http://www-ulp.u-strasbg.fr/" target="external"></a>
	    	<div class="live">
	    		<a class="liveZone" href="."><%=Messages._("Direct", l)%></a>
	    	</div>
	    	<div class="recorded">
	    		<a class="recordedZone" href="."><%=Messages._("Enregistr&eacute;", l)%></a>
	    	</div>
	    </div>
	    <div class="contents">
	    	<div class="search">
	    		<div class="searchImage">
	    			<span class="searchText"><%=Messages._("Recherche", l)%></span>
	    			<span class="advancedText"><%=Messages._("avanc&eacute;e", l)%></span>
	    		</div>
	    		<form method="post" action=".">
		    		<div class="criteria">
		    			<fieldset>
			    			<legend><%=Messages._("Type de cours", l)%></legend>
			    			<input type="checkbox" name="type" checked><%=Messages._("Audio", l)%>
			    			&nbsp;&nbsp;&nbsp;
			    			<input type="checkbox" name="type"><%=Messages._("Video", l)%>
		    			</fieldset>
		    			<!-- <br>
		    			<input type="checkbox" class="cb" name="tous">Tous les cours-->
		    			<br><br>
		    			<img src="../files/img/images/arrowsearch.png" alt=""><input type="submit" name="valider" class="submit" value=<%= "\"" + Messages._("Lancez la recherche", l) + "\""%>>
		    		</div>
		    		<div class="criteria">
		    			<label><%=Messages._("Enseignant", l)%></label>
		    			<select class="field"></select>
		    			<br>
		    			<label><%=Messages._("Formation", l)%></label>
		    			<select class="field"></select>
		    			<br>
		    			<label><%=Messages._("Code ETAP", l)%></label>
		    			<input type="text" class="field">
		    			<br>
		    		</div>
		    		<hr>
	    		</form>
	    	</div>
	    	<div class="course">
	    		<table cellspacing="0">
	    			<tr>
	    				<th colspan="3" id="cours"><%=Messages._("Les cours", l)%></th>
	    				<th colspan="2"><%=Messages._("Visualisez", l)%></th>
	    				<th colspan="4"><%=Messages._("T&eacute;l&eacute;chargez", l)%></th>
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
		    				<b><%=Messages._("Titre :", l)%> </b>Cours0607LS6_070411_Leçon8<br>
		    				<b><%=Messages._("Enseignant :", l)%> </b>René de Quenaudon (LS6)<br>
		    				<div id="row1col1link">
		    					<a href="#" onclick= "switchDetails('row1')"><%=Messages._("[+] plus de détails", l)%></a>
		    				</div>	    				
		    				<div id="row1col1details" class="hidden">
			    				<b><%=Messages._("Formation :", l)%> </b>Droit des escrocs<br>
			    				<b><%=Messages._("Sujet :", l)%> </b>Comment contourner la loi<br>
			    				<a href="#" onclick= "switchDetails('row1')"><%=Messages._("[-] moins de détails", l)%></a>
		    				</div>	    				
	    				</td>
	    				<td>
		    				<b><%=Messages._("Date :", l)%> </b>11/04/2007 <br>
		    				<b><%=Messages._("Consultations :", l)%> </b>50 <br>
		    				<div id="row1col2link">
		    					<a href="#" onclick= "switchDetails('row1')"><%=Messages._("[+] plus de détails", l)%></a>
		    				</div>
		    				<div id="row1col2details" class="hidden">
			    				<b><%=Messages._("Dur&eacute;e :", l)%> </b>3h40m50s<br>
			    				<b><%=Messages._("Type :", l)%> </b>audio<br>
			    				<a href="#" onclick= "switchDetails('row1')"><%=Messages._("[-] moins de détails", l)%></a>
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
		    	<%=Messages._("R&eacute;alisation du site par ULP Multim&eacute;dia - 2007", l)%> <br>
		    	<a href="."><%=Messages._("Contact", l)%></a> - <a href="."><%=Messages._("Informations l&eacute;gales", l)%></a> - <a href="."><%=Messages._("Liens", l)%></a>
	    	</p>
	    </div>
    </div>
  </body>
</html>
