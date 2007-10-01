<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="org.ulpmm.univrav.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>

<%
	Locale l = request.getLocale();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><%=Messages._("Cours en direct", l)%></title>

	<link rel="stylesheet" type="text/css" href="../files/styles/style1/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/style1/css/live.css">
	
	<!--[if IE]>
		<link rel="stylesheet" type="text/css" href="../files/styles/style1/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/style1/css/live_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->

  </head>
  
  <body>
    
	    <div class="main">
		    <div class="banner">
		    	<a class="bannerPageZone" href=".."></a>
		    	<a class="ulpmmZone" href="http://ulpmultimedia.u-strasbg.fr/" target="external"></a>
		    	<a class="ulpZone" href="http://www-ulp.u-strasbg.fr/" target="external"></a>
		    	<div class="live">
		    		<a class="liveZone" href="./live"><%=Messages._("Direct", l)%></a>
		    	</div>
		    	<div class="recorded">
		    		<a class="recordedZone" href="./recorded"><%=Messages._("Enregistr&eacute;", l)%></a>
		    	</div>
		    </div>
		    <div class="contents">
		    	
		    	<c:forEach var="building" varStatus="status" items="${buildings}">
		    		
		    		<c:if test="${status.count % 3 == 1}">
						<c:out value='<div class="line">' escapeXml="false" />
					</c:if>
		    		
			    		<div class="building">
				    	<img src="../files/img/buildings/${building.imageFile}" alt="Institut Le Bel">
				    	<p class="buildingName"><c:out value="${building.name}" /></p>
				    	<table>
				    		<tr>
				    			<th><%=Messages._("Salle", l)%></th>
				    			<th><%=Messages._("Enregistrement", l)%></th>
				    		</tr>
				    		
				    		<c:set var="class" value="row1" />
				    		<c:forEach var="amphi" varStatus="status2" items="${building.amphis}">
					    		<tr class="${class}">
					    			<td><img src="../files/img/amphi_icon.png" alt="amphi_icon"> <c:out value="${amphi.name}" /></td>
					    			<c:choose>
					    				<c:when test="${amphi.status == true}">
											<td> <img src="../files/img/chip_direct_on.png" alt="chip_direct_on"> <%=Messages._("En cours", l)%></td>
										</c:when>
										<c:otherwise>
											<td> <img src="../files/img/chip_direct.png" alt="chip_direct"> <%=Messages._("En attente", l)%></td>
										</c:otherwise>
					    			</c:choose>
					    		</tr>
					    		<c:choose>
				    				<c:when test="${status2.count % 2 == 0}">
										<c:set var="class" value="row1" />
									</c:when>
									<c:otherwise>
										<c:set var="class" value="row2" />
									</c:otherwise>
				    			</c:choose>
				    		</c:forEach>
				    	</table>
				    	</div>
			    	
			    	<c:if test="${status.count % 3 == 0}">
						<c:out value='</div>' escapeXml="false" />
					</c:if>
					
		    	</c:forEach>
		    		
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
