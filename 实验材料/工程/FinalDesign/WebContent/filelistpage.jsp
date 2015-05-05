<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="com.scut.zl.Config"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%
	File[] docList = new File(Config.DOC_SET_PATH).listFiles();
	List<String> docName = new LinkedList<String>();

	for (File doc : docList) {
		docName.add(doc.getName());
	}
%>
<body>

	<%
		for (int index = 0; index < docName.size(); index++) {
			String s = docName.get(index);
	%>
	<a width=100% href="homepage.jsp?index=<%=index%>"><%=s%></a>
	<br></br>
	<%
		}
	%>




</body>
</html>