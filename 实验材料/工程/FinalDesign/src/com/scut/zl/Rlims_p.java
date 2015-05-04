package com.scut.zl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.scut.zl.bean.Annotation;
import com.scut.zl.bean.Node;
import com.scut.zl.bean.Relation;
import com.scut.zl.bean.ResultPassage;
import com.scut.zl.utils.DataConverter;

public class Rlims_p {

	public static final int TAG_PROTEIN = 1;
	public static final int TAG_KINASE = 2;
	public static final int TAG_POSITION = 3;
	public static final int TAG_ACID = 4;
	public static final int TAG_SUBSTRATE = 5;
	public static final int TAG_TRIGGER = 6;

	public static ResultPassage result;
	public static HashMap<Integer, List<String>> getEntitys(String txt) {
		result = request(txt);

		HashMap<Integer, List<String>> mHashMap = new HashMap<Integer, List<String>>();
		List<String> list_protein = new ArrayList<String>();
		List<String> list_kinase = new ArrayList<String>();
		List<String> list_position = new ArrayList<String>();
		List<String> list_acid = new ArrayList<String>();
		List<String> list_substrate = new ArrayList<String>();
		List<String> list_trigger = new ArrayList<String>();
		mHashMap.put(TAG_PROTEIN, list_protein);
		mHashMap.put(TAG_KINASE, list_kinase);
		mHashMap.put(TAG_POSITION, list_position);
		mHashMap.put(TAG_ACID, list_acid);
		mHashMap.put(TAG_SUBSTRATE, list_substrate);
		mHashMap.put(TAG_TRIGGER, list_trigger);

		for (int i = 0; i < result.mRelationList.size(); i++) {
			Relation relation = result.mRelationList.get(i);
			for (int j = 0; j < relation.mNodeList.size(); j++) {
				Node node = relation.mNodeList.get(j);

				Annotation annotation = node.mAnnotation;
				if (node.role.equals("substrate")) {
					if (!list_substrate.contains(annotation.text)) {
						list_substrate.add(annotation.text);
					}
				}
				if (node.role.equals("amino_acid")) {
					if (!list_acid.contains(annotation.text)) {
						list_acid.add(annotation.text);
					}
				}
				if (node.role.equals("position")) {
					if (!list_position.contains(annotation.text)) {
						list_position.add(annotation.text);
					}
				}
				if (node.role.equals("kinase")) {
					if (!list_kinase.contains(annotation.text)) {
						list_kinase.add(annotation.text);
					}
				}
				if (node.role.equals("trigger")) {
					if (!list_trigger.contains(annotation.text)) {
						list_trigger.add(annotation.text);
					}
				}
			}
		}

		return mHashMap;
	}

	public static ResultPassage request(String text) {

		if( null != result ){
			return result;
		}
		String params = "type=text&input=";
		ResultPassage result = null;
		params += text;
		String s = sendPost(
				"http://annotation.dbi.udel.edu/text_mining/bioc/bioc.php?",
				params);
		System.out.println(s);

		try {
			result = DataConverter.xml2ResultPassage(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
