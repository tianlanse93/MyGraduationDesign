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

import abner.Tagger;

public class main {
	public static void main(String args[]) throws Exception {
//		try {
//			XmlParser.parseXml(Config.PATH_XML_FILE+"1.xml");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		EntityRecognizer.recognizer(Config.DOC_SET_PATH);
		
		Trainner.train(Config.TRAIN_PATH, Config.MODEL_PATH);

	}
}
