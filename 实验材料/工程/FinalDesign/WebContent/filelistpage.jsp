<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="com.scut.zl.config.Config"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件列表</title>
<link rel="stylesheet" href="bootstrap.min.css">
</head>
<script type="text/javascript">
	function myFunction() {
		window.location.href = "www.baidu.com";
	}
</script>
<script type="text/javascript" src="bootstrap.min.js"></script>

<style type="text/css">
body {
	background: #F7F7F7;
	font-family: 'Open Sans', sans-serif;
	font-weight: 300;
	font-size: 13px;
	margin: 0px;
	overflow-x: hidden;
}

div.titlebar {
	width: 100%;
	height: 80px;
	background-color: #7AD03A;
	padding-top: 20px;
	padding-left: 20px;
}

.col-md-2, .col-md-4 {
	padding: 0;
	margin: 0;
}

#countdown .form-control {
	border: none;
	border-radius: 0px;
	box-shadow: none;
	height: 50px;
	margin-top: 60px;
	transition: all 0.4s ease-in-out;
}

#countdown input[type="submit"] {
	width: 100px;
	background: #7AD03A;
	color: #ffffff;
	transition: all 0.3s ease;
}

#countdown input[type="submit"]:hover {
	background: #417F12;
}

a.list-group-item {
	width: 100%;
	border-radius: 0px;
	box-shadow: none;
	height: auto;
	transition: all 0.4s ease-in-out;
}

.center {
	width: 50%;
	display: table;
	margin-left: 20%;
	margin-right: auto;
}

p.list-group-item-text {
	height: 40px;
}
</style>

<%
	File[] docList = new File(Config.DOC_SET_PATH).listFiles();
	List<String> docName = new LinkedList<String>();
	for (File doc : docList) {
		docName.add(doc.getName());
	}
	int currentPage = Integer.parseInt(request
			.getParameter("currentPage"));
%>
<body>
	<div class="titlebar">
		<font size="6px" color="#ffffff">Pubmed Article</font>
	</div>
	<section id="countdown">
	<form action="#" method="post">
		<div class="col-md-3 col-sm-3"></div>
		<div class="col-md-4 col-sm-4">
			<input type="pmid" class="form-control" placeholder="Your Pmid">
		</div>
		<div class="col-md-2 col-sm-2">
			<input type="submit" class="form-control" value="提交">
		</div>
	</form>
	</section>



	<br></br>
	<br></br>
	<br></br>
	<br></br>
	<br></br>

	<dir>
		<h3 class="center">All Articles</h3>

		<div class="center">
			<%
				for (int index = currentPage*20; index < docName.size() && index < (currentPage+1)*20; index++) {
					//跳过第一个文件ds_store
					if (index == 0) {
						continue;
					}
					String s = docName.get(index);
			%>
			<a href="homepage.jsp?index=<%=index%>" class="list-group-item">
				<h4 class="list-group-item-heading"><%=s%></h4>
				<p class="list-group-item-text">List group item headingList
					group item headingList group item List group item headingList group
				</p>
			</a>
			<%
				}
			%>
		</div>
		<nav>
		<ul class="pagination center">
			<li><a href="#" aria-label="Previous"> <span
					aria-hidden="true">上一页</span>
			</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#">6</a></li>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">下一页</span>
			</a></li>
		</ul>
		</nav>
	</dir>


</body>
</html>