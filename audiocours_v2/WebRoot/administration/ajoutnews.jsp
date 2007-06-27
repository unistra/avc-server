<%@ page language="java" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<jsp:directive.page import="java.util.Date"/>
<jsp:directive.page import="java.text.ParseException"/>
<jsp:directive.page import="java.io.IOException"/>
<jsp:directive.page import="javax.xml.transform.Source"/>
<jsp:directive.page import="javax.xml.transform.dom.DOMSource"/>
<jsp:directive.page import="javax.xml.parsers.DocumentBuilderFactory"/>
<jsp:directive.page import="javax.xml.parsers.DocumentBuilder"/>
<jsp:directive.page import="java.io.File"/>
<jsp:directive.page import="org.w3c.dom.Document"/>
<jsp:directive.page import="javax.xml.transform.Result"/>
<jsp:directive.page import="javax.xml.transform.stream.StreamResult"/>
<jsp:directive.page import="javax.xml.transform.TransformerFactory"/>
<jsp:directive.page import="javax.xml.transform.stream.StreamSource"/>
<jsp:directive.page import="javax.xml.transform.Transformer"/>
<jsp:directive.page import="javax.xml.transform.OutputKeys"/>
<jsp:useBean id="sqlbean" class="paquet.SqlBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<%
	request.setCharacterEncoding("UTF8");
	String date = request.getParameter("Date");
	String news = request.getParameter("News");
%>
<html>
<head>
<title>AudioVideoCours : Outil de gestion des news</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript" src="../code/js_videocours_.js"></script>
<link href="../code/style_videocours.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF" text="#666666" link="#666666" vlink="#666666" alink="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<jsp:include page="include/banniere_administration.jsp"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"><table width="757" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="../_mm/zero.gif" width="10" height="30"></td>
        </tr>
        <tr>
          <td>
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45" valign="top"><img src="../_mm/pyramide_vert.jpg" width="45" height="50"></td>
                <td valign="bottom">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class="ArialBlack18"><img src="../_mm/zero.gif" width="10" height="23"></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td class="ArialBlack18">Outil de gestion des news</td>
                    </tr>
                    <tr>
                      <td height="6" background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                      <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                      <td background="../_mm/titre_trait.gif"><img src="../_mm/zero.gif" width="10" height="6"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
    </td>
  </tr>
</table>
<br/>
<%
//Création de la source DOM
DocumentBuilderFactory fabriqueD = DocumentBuilderFactory.newInstance();
DocumentBuilder constructeur = fabriqueD.newDocumentBuilder();
File fileXml = new File("/var/lib/tomcat5.5/webapps/audiocours_v2/rss/rssNews/news.xml");
Document document = constructeur.parse(fileXml);
Source source = new DOMSource(document);

// Création du fichier de sortie
File file2 = new File("/var/lib/tomcat5.5/webapps/audiocours_v2/rss/rssNews/news2.xml");
Result resultat = new StreamResult(file2);

// Configuration du transformer
TransformerFactory fabriqueT = TransformerFactory.newInstance();
StreamSource stylesource = new StreamSource("/var/lib/tomcat5.5/webapps/audiocours_v2/rss/rssNews/news.xsl");
Transformer transformer = fabriqueT.newTransformer(stylesource);
transformer.setOutputProperty(OutputKeys.METHOD, "xml");
transformer.setOutputProperty(OutputKeys.INDENT, "yes");
transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

// Transformation
transformer.transform(source, resultat);

//On crée un fichier xml corespondant au résultat
//XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
//outputter.output(resultat, new FileOutputStream("resultat.xml"));

%>
</body>
</html>

