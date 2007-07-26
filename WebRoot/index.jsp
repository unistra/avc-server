
<jsp:directive.page import="org.ulpmm.language.Messages"/>
<jsp:directive.page import="java.util.Locale"/>


<%=Messages._("Page of direct", request.getLocale())%>
<% Locale l = Locale.getDefault(); 
	out.println("locale du client :" + request.getLocale());
%>
