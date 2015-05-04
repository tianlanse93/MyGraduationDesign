<%@page import="com.scut.zl.bean.DisplayResource"%>
<%@page import="core.DisplayLogic"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.scut.zl.utils.DataConverter"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.scut.zl.bean.ResultPassage"%>
<%@page import="com.scut.zl.Rlims_p"%>
<%@page import="java.io.File"%>
<%@page import="com.scut.zl.Config"%>
<%@page import="com.scut.zl.FileUtils"%>
<%@page import="com.scut.zl.bean.Annotation"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<%
	//String a = request.getParameter("HaHa");
/* 	String fileContent = FileUtils.getAbstractContent(new File(Config.DOC_SET_PATH).listFiles()[1]);
	System.out.println(fileContent);
	ResultPassage result = Rlims_p.request(fileContent); */
	
	DisplayResource resource = DisplayLogic.getDisplayRes(new File(Config.DOC_SET_PATH).listFiles()[1]);
%>
<script type="text/javascript">
	function myFunction() {
		document.getElementById("protein_list").innerHTML="ASD";
	}
</script>

<body>
	<h2>原文</h2>
	<p width=100%>
		<%=resource.text%>
	</p>

	<br></br>
	<br></br>

	<a>文中实体</a>
	<table width=1000 border=1px>
		<tr>
			<td>蛋白质</td>
			<%
				String string_substrate = DataConverter.getEntitys(Rlims_p.TAG_SUBSTRATE, resource.mEntityMap);
			%>
			<td id="protein_list"><%=string_substrate%></td>
		</tr>

		<tr>
			<td>氨基酸</td>
			<%
				String string_acid = DataConverter.getEntitys(Rlims_p.TAG_ACID,resource.mEntityMap);
			%>
			<td><%=string_acid%></td>
		</tr>


		<tr>
			<td>激酶</td>
			<%
				String string_kinase = DataConverter.getEntitys(Rlims_p.TAG_KINASE,resource.mEntityMap);
			%>
			<td><%=string_kinase%></td>
		</tr>


		<tr>
			<td>位点</td>
			<%
				String string_position = DataConverter.getEntitys(Rlims_p.TAG_POSITION, resource.mEntityMap);
			%>
			<td><%=string_position%></td>
		</tr>


		<tr>

		</tr>



	</table>

	<br></br>
	<br></br>

	<a>实体关系</a>
	<table width=1000 border=1px>
		<tr>

		</tr>
	</table>
	
	<button onclick="myFunction()">点击这里</button>
</body>
</html>