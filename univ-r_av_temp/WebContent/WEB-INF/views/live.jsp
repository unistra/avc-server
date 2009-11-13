<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="org.ulpmm.univrav.language.messages"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><fmt:message key="Univ-R AV Audiovid&eacute;cours"/> - <fmt:message key="Cours en direct"/></title>

	<link rel="stylesheet" type="text/css" href="../files/thickbox/thickbox.css" media="screen">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/live.css">
	
	<!--[if IE]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie.css" media="screen" />
	<![endif]-->
	<!--[if lte IE 6]>
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/styles_ie6.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../files/styles/${sessionScope.style}/css/live_ie6.css" media="screen" />
		<script defer type="text/javascript" src="../files/js/pngfix.js"></script>
	<![endif]-->
	
	<script type="text/javascript" src="../files/thickbox/jquery.js"></script>
	<script type="text/javascript" src="../files/thickbox/thickbox.js"></script>
	<script type="text/javascript">window.setInterval("location.reload(true)",60000);</script>

  </head>
  
  <body>
    
	    <div class="main">
		    <div class="contents">
		    
		    	<div class="banner">
		    		<c:import url="include/banner.jsp" />
		   		</div>
		    	
		    	<c:forEach var="building" varStatus="status" items="${buildings}">
		    		
		    		<c:if test="${status.count % 4 == 1}">
						<c:out value='<div class="line">' escapeXml="false" />
					</c:if>
		    		
			    		<div class="building">
				    	<img src="../files/img/buildings/${building.imageFile}" alt="<c:out value="${building.name}" />">
				    	<p class="buildingName"><c:out value="${building.name}" /></p>
				    	<table>
				    		<tr class="tableheader">
				    			<th><fmt:message key="Salle"/></th>
				    			<th><fmt:message key="Enregistrement"/></th>
				    		</tr>
				    		
				    		<c:set var="class" value="row1" />
				    		<c:forEach var="amphi" varStatus="status2" items="${building.amphis}">
					    		<tr class="${class}">
					    			<c:url var="liveaccess" scope="page" value="./liveaccess">
										<c:param name="amphi" value="${amphi.ipAddress}"/>
									</c:url>
					    			
					    			<c:choose>
					    				<c:when test="${amphi.status == true}">
											<td> 
												<c:choose>
													<c:when test="${amphi.gmapurl == null}">
														<img src="../files/styles/${sessionScope.style}/img/amphi_icon.png" alt="amphi_icon">
													</c:when>
													<c:otherwise>
														<a href="<c:url value="${amphi.gmapurl}" />" target="_blank"> <img src="../files/styles/${sessionScope.style}/img/amphi_icon_g.png" alt="Google Maps Url"> </a>
													</c:otherwise>
												</c:choose>
												<a href="${liveaccess}"> <c:out value="${amphi.name}"/> </a> 
											</td>
											<td> <img src="../files/styles/${sessionScope.style}/img/chip_direct_on.png" alt="chip_direct_on"><fmt:message key="En cours"/> </td>
										</c:when>
										<c:otherwise>
											<td> 
												<c:choose>
													<c:when test="${amphi.gmapurl == null}">
														<img src="../files/styles/${sessionScope.style}/img/amphi_icon.png" alt="amphi_icon">
													</c:when>
													<c:otherwise>
														<a href="<c:url value="${amphi.gmapurl}" />" target="_blank"> <img src="../files/styles/${sessionScope.style}/img/amphi_icon_g.png" alt="Google Maps Url"> </a>
													</c:otherwise>
												</c:choose>
												<c:out value="${amphi.name}" /> 
											</td>
											<td> <img src="../files/styles/${sessionScope.style}/img/chip_direct.png" alt="chip_direct"><fmt:message key="En attente"/> </td>
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
			    	
			    	<c:if test="${status.count % 4 == 0 || status.last }">
						<c:out value='</div>' escapeXml="false" />
					</c:if>
					
		    	</c:forEach>
		    	${status.count }
		    		
	    	</div>
		    	
		    <div class="footer">
		    	<c:import url="include/footer.jsp" />
		    </div>
	    </div>
  </body>
</html>
