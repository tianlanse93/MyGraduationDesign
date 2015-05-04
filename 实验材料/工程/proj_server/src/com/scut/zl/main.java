package com.scut.zl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

import abner.Tagger;

public class main {
	public static void main(String args[]){
//		try {
//			XmlParser.parseXml(Config.PATH_XML_FILE+"1.xml");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		EntityRecognizer.recognizer(Config.DOC_SET_PATH);
		
//		Trainner.train(Config.TRAIN_PATH, Config.MODEL_PATH);
//		
//		Tagger t = new Tagger(new File(Config.MODEL_PATH));
//		
//		String res[] = t.getEntities("GRK6 phosphorylates at Ser32 and enhances TNF-α-induced inflammation.", "POSITION");
//		
//		for( int i = 0; i < res.length;i++ ){
//			System.out.println(res[i]);
//		}
		
		
		
		String fileContent = FileUtils.getAbstractContent(new File(Config.DOC_SET_PATH).listFiles()[0]);
		System.out.println(fileContent);
		
		
		Rlims_p.request("text",fileContent);
		
		
	}
}
