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
<title>文献详情页面</title>
<link rel="stylesheet" href="bootstrap.min.css">
<script type="text/javascript" src="bootstrap.min.js"></script>
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

div.titlebar {
	width: 100%;
	height: 80px;
	background-color: #7AD03A;
	padding-top: 20px;
	padding-left: 20px;
}

.label-protein {
  background-color: #f0ad4e;
}

.label-kinase {
  background-color: #b94a48;
  margin-left: 10px;
}

.label-position{
  background-color: #5cb85c;
  margin-left: 10px;
}

.label-acid {
  background-color: #5bc0de;
  margin-left: 10px;
}

.label-trigger{
  background-color: #333;
  margin-left: 10px;
}

</style>

<%
	int index = Integer.parseInt(request.getParameter("index"));
	//加载要展示的资源要内存
	DisplayResource resource = DisplayLogic.getDisplayRes(new File(
			Config.DOC_SET_PATH).listFiles()[index]);

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
	<div class="titlebar">
		<%
			String pmid = resource.title.split("\\.")[0];
			resource.title = resource.title.substring(pmid.length() + 1,
					resource.title.length());
			if (resource.title.length() > 100) {
				resource.title = resource.title.substring(0, 100) + "...";
			}
		%>
		<font size="6px" color="#ffffff">标题：<%=resource.title%></font>
	</div>
	<div class="container">
		<%
			if (taggedText
					.contains("PMID - 0000TI - Title not provided .AB - ")) {
				taggedText = taggedText.substring(
						"PMID - 0000TI - Title not provided .AB - ".length(),
						taggedText.length());
			}
		%>
		<br></br>
		<br></br>
		<h4>一、文献</h4>
		<table class="table table-bordered" width=100%>
			<tr>
				<td>标题：<%=resource.title%></td>
			</tr>
			<tr>
				<td>PMID：<%=pmid%></td>
			</tr>
			<tr>
				<td>内容：<%=taggedText%></td>
			</tr>
		</table>

		<div>
			<span class="label label-protein">protein</span>
			<span class="label label-kinase">kinase</span>
			<span class="label label-position">position</span>
			<span class="label label-acid">acid</span>
			<span class="label label-trigger">trigger</span>
		</div>
		
		<br></br>
		<h4>二、文中实体</h4>
		<table class="table table-striped table-bordered" width=1000>
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

		<h4>三、实体关系</h4>
		<table class="table table-striped table-bordered" width=100%>
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

	</div>
</body>
</html>