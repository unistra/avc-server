<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
<c:when test="${nodia}">
	<a href="${urlimg}" target="external"><img id ="liveslideimg" class="liveslideimg" src="../files/img/DiaVide.png"></a>
</c:when>
<c:otherwise>
	<a href="${urlimg}" target="external"><img id="liveslideimg" class="liveslideimg" src="${urlimg}?${checksum}"></a>
</c:otherwise>
</c:choose>


<script type="text/javascript">
	resizeLiveside();
</script>

