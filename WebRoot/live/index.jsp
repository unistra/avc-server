<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="org.ulpmm.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><%=Messages._("Cours en direct", l)%></title>

	<link rel="stylesheet" type="text/css" href="../files/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/css/direct.css">
	
	<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href="../files/css/styles_ie6.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="../files/css/direct_ie6.css" media="screen" />
	<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

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
		    	<div class="line">
			    	
			    	<div class="building">
			    	<img src="../files/img/images/lebel-thumb.png" alt="Institut Le Bel">
			    	<p class="buildingName">Institut Le Bel</p>
			    	<table>
			    		<tr>
			    			<th><%=Messages._("Salle", l)%></th>
			    			<th><%=Messages._("Enregistrement", l)%></th>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi1</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> <%=Messages._("En attente", l)%></td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi2</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> <%=Messages._("En cours", l)%></td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi3</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi4</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi5</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi6</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    	</table>
			    	</div>
			    	
			    	<div class="building">
			    	<img src="../files/img/images/lebel-thumb.png" alt="Institut Le Bel">
			    	<p class="buildingName">Institut Le Bel</p>
			    	<table>
			    		<tr>
			    			<th>Salle</th>
			    			<th>Enregistrement</th>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi1</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi2</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi3</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi4</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi5</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi6</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi7</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi8</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    	</table>
			    	</div>
			    	
			    	<div class="building">
			    	<img src="../files/img/images/lebel-thumb.png" alt="Institut Le Bel">
			    	<p class="buildingName">Institut Le Bel</p>
			    	<table>
			    		<tr>
			    			<th>Salle</th>
			    			<th>Enregistrement</th>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi1</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi2</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi3</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    	</table>
			    	</div>	 
			    	   	
		    	</div>
		    	
		    	<div class="line">
			    	
			    	<div class="building">
			    	<img src="../files/img/images/lebel-thumb.png" alt="Institut Le Bel">
			    	<p class="buildingName">Institut Le Bel</p>
			    	<table>
			    		<tr>
			    			<th>Salle</th>
			    			<th>Enregistrement</th>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi1</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi2</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi3</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi4</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi5</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi6</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    	</table>
			    	</div>
			    	
			    	<div class="building">
			    	<img src="../files/img/images/lebel-thumb.png" alt="Institut Le Bel">
			    	<p class="buildingName">Institut Le Bel</p>
			    	<table>
			    		<tr>
			    			<th>Salle</th>
			    			<th>Enregistrement</th>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi1</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi2</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi3</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi4</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi5</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi6</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi7</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi8</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    	</table>
			    	</div>
			    	
			    	<div class="building">
			    	<img src="../files/img/images/lebel-thumb.png" alt="Institut Le Bel">
			    	<p class="buildingName">Institut Le Bel</p>
			    	<table>
			    		<tr>
			    			<th>Salle</th>
			    			<th>Enregistrement</th>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi1</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi2</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi3</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    	</table>
			    	</div>	 
			    	   	
		    	</div>
		    	
		    	<div class="line">
			    	
			    	<div class="building">
			    	<img src="../files/img/images/lebel-thumb.png" alt="Institut Le Bel">
			    	<p class="buildingName">Institut Le Bel</p>
			    	<table>
			    		<tr>
			    			<th>Salle</th>
			    			<th>Enregistrement</th>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi1</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi2</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi3</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi4</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi5</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi6</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    	</table>
			    	</div>
			    	
			    	<div class="building">
			    	<img src="../files/img/images/lebel-thumb.png" alt="Institut Le Bel">
			    	<p class="buildingName">Institut Le Bel</p>
			    	<table>
			    		<tr>
			    			<th>Salle</th>
			    			<th>Enregistrement</th>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi1</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi2</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi3</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi4</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi5</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi6</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi7</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi8</td>
			    			<td> <img src="../files/img/links/chip_direct.png" alt="chip_direct"> En attente</td>
			    		</tr>
			    	</table>
			    	</div>
			    	
			    	<div class="building">
			    	<img src="../files/img/images/lebel-thumb.png" alt="Institut Le Bel">
			    	<p class="buildingName">Institut Le Bel</p>
			    	<table>
			    		<tr>
			    			<th>Salle</th>
			    			<th>Enregistrement</th>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi1</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row2">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi2</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    		<tr class="row1">
			    			<td><img src="../files/img/links/amphi_icon.png" alt="amphi_icon">Amphi3</td>
			    			<td> <img src="../files/img/links/chip_direct_on.png" alt="chip_direct_on"> En attente</td>
			    		</tr>
			    	</table>
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
