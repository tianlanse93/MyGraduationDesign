<%@page import="com.scut.zl.bean.DisplayResource"%>
<%@page import="com.scut.zl.core.display.DisplayLogic"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.scut.zl.utils.DataConverter"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.scut.zl.bean.xmlbean.ResultPassage"%>
<%@page import="com.scut.zl.rlims.Rlims_p"%>
<%@page import="java.io.File"%>
<%@page import="com.scut.zl.config.Config"%>
<%@page import="com.scut.zl.utils.FileUtils"%>
<%@page import="com.scut.zl.bean.xmlbean.Annotation"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<style type="text/css">
span.substrate {
	background-color: yellow
}

span.acid {
	background-color: green
}

span.kinase {
	background-color: blue
}

span.position {
	background-color: red
}

span.trigger {
	background-color: gray;
}
</style>

<%
	int index = Integer.parseInt(request.getParameter("index"));
	//加载要展示的资源要内存
	DisplayResource resource = DisplayLogic.getDisplayRes(new File(Config.DOC_SET_PATH).listFiles()[index]);

	//给原文中的实体标注颜色
	String taggedText = DataConverter.getTaggedText(resource.text,
			resource.mEntityMap);
	System.out.println(resource.mRelationList.toString());
%>

<script type="text/javascript">
	function myFunction() {
		document.getElementById("protein_list").innerHTML = taggedText;
	}
</script>

<body>
	<h2>原文</h2>
	<h4>标题：<%=resource.title%></h4>
	<b>内容：</b><p100%> <%=taggedText%>
	</p>

	<br></br>
	<br></br>

	<a>文中实体</a>
	<table width=1000 border=1px>
		<tr>
			<td>蛋白质</td>
			<%
				String string_substrate = DataConverter.getEntitys(
						Rlims_p.TAG_SUBSTRATE, resource.mEntityMap);
			%>
			<td id="protein_list"><%=string_substrate%></td>
		</tr>

		<tr>
			<td>氨基酸</td>
			<%
				String string_acid = DataConverter.getEntitys(Rlims_p.TAG_ACID,
						resource.mEntityMap);
			%>
			<td><%=string_acid%></td>
		</tr>


		<tr>
			<td>激酶</td>
			<%
				String string_kinase = DataConverter.getEntitys(Rlims_p.TAG_KINASE,
						resource.mEntityMap);
			%>
			<td><%=string_kinase%></td>
		</tr>


		<tr>
			<td>位点</td>
			<%
				String string_position = DataConverter.getEntitys(
						Rlims_p.TAG_POSITION, resource.mEntityMap);
			%>
			<td><%=string_position%></td>
		</tr>


		<tr>

		</tr>



	</table>

	<br></br>
	<br></br>

	<a>实体关系</a>
	<table width=100% border=1px>
		<tr>
			<td>关系</td>
			<td>被修饰的蛋白质</td>
			<td>修饰激酶</td>
			<td>修饰位点</td>
			<td>修饰词(phosphorylate)</td>
			<td>氨基酸</td>
		</tr>
		<%
			for (int i = 0; i < resource.mRelationList.size(); i++) {
		%>
		<tr>
			<td>关系<%=i + 1%></td>
			<td><%=DataConverter.getRelation(i, Rlims_p.TAG_SUBSTRATE,
						resource.mRelationList)%></td>
			<td><%=DataConverter.getRelation(i, Rlims_p.TAG_KINASE,
						resource.mRelationList)%></td>
			<td><%=DataConverter.getRelation(i, Rlims_p.TAG_POSITION,
						resource.mRelationList)%></td>
			<td><%=DataConverter.getRelation(i, Rlims_p.TAG_TRIGGER,
						resource.mRelationList)%></td>
			<td><%=DataConverter.getRelation(i, Rlims_p.TAG_ACID,
						resource.mRelationList)%></td>
		</tr>
		<%
			}
		%>
	</table>

	<button onclick="myFunction()">点击这里</button>
</body>
</html>