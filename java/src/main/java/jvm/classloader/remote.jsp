<%@ page import="java.lang.*" %>
<%@ page import="java.io.*" %>
<%@ page import="org.jvm.*" %>

<%
InputStream is = new FileInputStream("c:/TestClass.class");
byte[] b = new byte[is.available()];
is.read(b);
is.close();

out.println("<textarea style='width:1000;heigth=800'>");
	out.println(JavaClassExecuter.execute(b));
	out.println("</textarea>");
%>