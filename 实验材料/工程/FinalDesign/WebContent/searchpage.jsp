<%@page import="com.scut.zl.utils.FileUtils"%>
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
<title>搜索列表</title>
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
	boolean isAll = true;
	int currentPage = Integer.parseInt(request
			.getParameter("currentPage"));
	String keyWord = request.getParameter("keyWord");
	if (null == keyWord || keyWord.equals("")) {
		isAll = true;
		keyWord = "";
	} else {
		isAll = false;
	}
	File[] docList = new File(Config.DOC_SET_PATH).listFiles();
	List<String> docName = new LinkedList<String>();
	for (File doc : docList) {
		if (!isAll) {
			if (doc.getName().contains(keyWord)) {
				docName.add(doc.getName());
			}
		}else{
			docName.add(doc.getName());
		}
	}
%>
<body>
	<div class="titlebar">
		<font size="6px" color="#ffffff">Pubmed Article List</font>
	</div>
	<section id="countdown">
	<form action="searchpage.jsp?currentPage=1" method="post">
		<div class="col-md-3 col-sm-3"></div>
		<div class="col-md-4 col-sm-4">
			<input name="keyWord" type="pmid" class="form-control"
				placeholder="请输入Pmid或者关键字" value="<%=keyWord%>">
		</div>
		<div class="col-md-2 col-sm-2">
			<input type="submit" class="form-control" value="搜索">
		</div>
	</form>
	</section>

	<br></br>
	<br></br>
	<br></br>
	<br></br>
	<br></br>

	<dir>
		<%
			if (docName.size() <= 0) {
		%>
		<h3 class="center">Sorry,there is no article found!</h3>
		<%
			} else {
				if (isAll) {
		%>
		<h3 class="center">All articles (<%=docName.size()%>篇)</h3>
		<%
			} else {
		%>
		<h3 class="center">
			There are
			<%=docName.size()%>
			articles about keyword "<%=keyWord%>"
		</h3>
		<%
			}
			}
		%>

		<div class="center">
			<%
				for (int index = (currentPage - 1) * 20; index < docName.size()
						&& index < currentPage * 20; index++) {
					//跳过第一个文件ds_store
					if (docName.get(index).equals(".DS_Store")) {
						continue;
					}
					String s = docName.get(index);
					String fileContent = FileUtils.getAbstractContent(new File(
							Config.DOC_SET_PATH + s));
					String pmid = s.split("\\.")[0];
					s = s.substring(pmid.length() + 1, s.length() - 4);
					if (s.length() > 100) {
						s = s.substring(0, 100) + "...";
					}
					if (fileContent.length() > 300) {
						fileContent = fileContent.substring(0, 300) + "...";
					}
			%>
			<a href="homepage.jsp?index=<%=index%>" class="list-group-item">
				<h4 class="list-group-item-heading"><%=s%></h4>
				<p class="list-group-item-text"><%=fileContent%>
				</p>
			</a>
			<%
				}
			%>
		</div>
		<nav>
		<ul class="pagination center">
			<%
				int tempPage = currentPage;
				int totalPage = docName.size() / 20 + 1;
				if (currentPage <= 3) {
					tempPage = 3;
				} else if (currentPage >= totalPage - 2) {
					tempPage = totalPage - 2;
				}
			%>
			<%
				if(docName.size()<=0){
					return;
				}
				if (totalPage <= 5) {
					for (int i = 1; i <= totalPage; i++) {
			%>
			<li><a href="?currentPage=<%=currentPage%>&keyWord=<%=keyWord%>"><%=currentPage%></a></li>
			<%
				}
				} else {
			%>
			<li><a href="#" aria-label="Previous"> <span
					aria-hidden="true">上一页</span>
			</a></li>
			<li><a
				href="?currentPage=<%=tempPage - 2%>&keyWord=<%=keyWord%>"><%=tempPage - 2%></a></li>
			<li><a
				href="?currentPage=<%=tempPage - 1%>&keyWord=<%=keyWord%>"><%=tempPage - 1%></a></li>
			<li><a href="?currentPage=<%=tempPage%>&keyWord=<%=keyWord%>"><%=tempPage%></a></li>
			<li><a
				href="?currentPage=<%=tempPage + 1%>&keyWord=<%=keyWord%>"><%=tempPage + 1%></a></li>
			<li><a
				href="?currentPage=<%=tempPage + 2%>&keyWord=<%=keyWord%>"><%=tempPage + 2%></a></li>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">下一页</span>
			</a></li>
			<%
				}
			%>
		</ul>
		</nav>
	</dir>
</body>
</html>