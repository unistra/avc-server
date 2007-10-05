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

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/live.css">
	
	<!--[if IE]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/live_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
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
					    			<c:url var="liveaccess" scope="page" value="./liveaccess">
										<c:param name="amphi" value="${amphi.ipAddress}"/>
									</c:url>
					    			<c:choose>
					    				<c:when test="${amphi.status == true}">
											<td> <img src="../files/styles/${sessionScope.style}/img/amphi_icon.png" alt="amphi_icon"> <a href="${liveaccess}"> <c:out value="${amphi.name}"/> </a> </td>
											<td> <img src="../files/styles/${sessionScope.style}/img/chip_direct_on.png" alt="chip_direct_on"> <%=Messages._("En cours", l)%> </td>
										</c:when>
										<c:otherwise>
											<td> <img src="../files/styles/${sessionScope.style}/img/amphi_icon.png" alt="amphi_icon"> <c:out value="${amphi.name}" /> </td>
											<td> <img src="../files/styles/${sessionScope.style}/img/chip_direct.png" alt="chip_direct"> <%=Messages._("En attente", l)%> </td>
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
		    	<c:import url="include/footer.jsp" />
		    </div>
	    </div>
  </body>
</html>
