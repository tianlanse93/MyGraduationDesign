package com.scut.zl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlParser {
	public static void parseXml(String fileName) throws Exception {
		SAXReader reader = new SAXReader();

		Document document = reader.read(new File(fileName));
		Element root = document.getRootElement();
		// 将解析出来的Data下的PubmedArticle放在list中
		List list = root.elements();
		int k = 0;
		for (Iterator i = list.iterator(); i.hasNext();) {
			k++;
			//输入的文件名称
			String outFileName = "";
			//输入的文件内容
			String fileContent = "";
			//PubmedArticle标签
			Element PubmedArticle = (Element) i.next();
			//MedlineCitation标签
			Element MedlineCitation = PubmedArticle.element("MedlineCitation");
			//！！！最关键的文献标签
			Element Article = MedlineCitation.element("Article");
			//最关键的文献的标题
			Element ArticleTitle = Article.element("ArticleTitle");
			//文献主体内容
			Element Abstract = Article.element("Abstract");
			
			//输入文件名称设置为序列号+文献的标题
			outFileName = k + ". " + ArticleTitle.getText();
			System.out.println(outFileName);
			
			//因为linux下面文件名字的最大的长度为256，所以超过了的话要进行裁剪
			if (outFileName.length() > 250) {
				outFileName = outFileName.substring(0, 250);
			}
			
			//Abstract有可能为空
			if( null == Abstract ){
				continue;
			}
			
			//Abstract里面有很多段落，段落的列表
			List eList = Abstract.elements();

			for (Iterator j = eList.iterator(); j.hasNext();) {
				Element AbstractText = (Element) j.next();
				fileContent += AbstractText.getText() + "\n";
			}
			addDocSet(outFileName, fileContent);
		}
	}

	//文档集目录
	private static String DOC_SET_PATH = "/Users/minghao_zl/Documents/bishe/MyGraduationDesign/实验材料/文献文本库/文档集/";
	
	//增加到文档集里面去
	private static void addDocSet(String fileName, String fileContent)
			throws Exception {
		if (fileName.equals("")) {
			return;
		}
		//由于java会把"/"当成子目录处理，所以要替换成：进行区别
		fileName = fileName.replace("/", ":");
		File file = new File(
				new File("DOC_SET_PATH"),
				fileName + ".txt");

		OutputStream os = new FileOutputStream(file);
		os.write(fileContent.getBytes());
		os.flush();
		os.close();
	}

}
