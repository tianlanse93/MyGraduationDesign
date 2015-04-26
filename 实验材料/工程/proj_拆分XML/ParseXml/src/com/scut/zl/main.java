package com.scut.zl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

public class main {
	public static void main(String args[]) {
		try {
			XmlParser.parseXml("/Users/minghao_zl/Documents/bishe/MyGraduationDesign/实验材料/文献文本库/已拆分的小文件/1.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
