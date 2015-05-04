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
	String fileContent = FileUtils.getAbstractContent(new File(
			Config.DOC_SET_PATH).listFiles()[1]);
	System.out.println(fileContent);
	HashMap<Integer, List<String>> entityList = Rlims_p
			.getEntitys(fileContent);
	fileContent = Rlims_p.result.text;
	HashMap<String,Annotation> hashMap = Rlims_p.result.mAnnotationMap;
	System.out.println(hashMap);
	List<Annotation> annotationList = new ArrayList<Annotation>();
	
	for(  Iterator k = hashMap.keySet().iterator(); k.hasNext();){
		String id = (String)k.next();
		Annotation annotation = hashMap.get(id);
		annotationList.add(annotation);
	}
	
	Collections.sort(annotationList);
	System.out.println(annotationList);	
	int gap = 0;
	fileContent = "                                                             "+fileContent;
	for( Annotation a : annotationList){
		
		fileContent = DataConverter.insertTag(fileContent,"span","span",a.location[0]+gap,a.location[0]+a.location[1]+gap);
		gap += DataConverter.insertItemHead.length();
		gap += DataConverter.insertItemTail.length();
	}
	
	
%>

<body>
	<h2>原文</h2>
	<p width=100%>
		<%=fileContent%>
	</p>
	<br></br>
	<br></br>

	<a>文中实体</a>
	<table width=1000 border=1px>
		<tr>
			<td>蛋白质</td>
			<%
				String string_substrate = "";
				for (int i = 0; i < ((List<String>) entityList.get(Rlims_p.TAG_SUBSTRATE)).size(); i++) {
					string_substrate += ((List<String>) entityList.get(Rlims_p.TAG_SUBSTRATE)).get(i) + "  ";
				}
			%>
			<td><%=string_substrate%></td>
		</tr>
		
		<tr>
			<td>氨基酸</td>
			<%
				String string_acid = "";
				for (int i = 0; i < ((List<String>) entityList.get(Rlims_p.TAG_ACID)).size(); i++) {
					string_acid += ((List<String>) entityList.get(Rlims_p.TAG_ACID)).get(i) + "  ";
				}
			%>
			<td><%=string_acid%></td>
		</tr>
		
		
		<tr>
			<td>激酶</td>
			<%
				String string_kinase = "";
				for (int i = 0; i < ((List<String>) entityList.get(Rlims_p.TAG_KINASE)).size(); i++) {
					string_kinase += ((List<String>) entityList.get(Rlims_p.TAG_KINASE)).get(i) + "  ";
				}
			%>
			<td><%=string_kinase%></td>
		</tr>
		
		
		<tr>
			<td>位点</td>
			<%
				String string_position = "";
				for (int i = 0; i < ((List<String>) entityList.get(Rlims_p.TAG_POSITION)).size(); i++) {
					string_position += ((List<String>) entityList.get(Rlims_p.TAG_POSITION)).get(i) + "  " ;
				}
			%>
			<td><%=string_position%></td>
		</tr>
		

		<tr>

		</tr>



	</table>
</body>
</html>