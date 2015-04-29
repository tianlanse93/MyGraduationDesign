package com.scut.zl.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.scut.zl.bean.Annotation;
import com.scut.zl.bean.Relation;
import com.scut.zl.bean.ResultPassage;

public class DataConverter {
	public static ResultPassage xml2ResultPassage(String xml) throws Exception{
		ResultPassage passageResult = new ResultPassage();
		
		SAXReader reader = new SAXReader();
		
		Document source = DocumentHelper.parseText(xml);
		
		Element root = source.getRootElement();
		
		Element document = root.element("document");
		
		Element passage = document.element("passage");
		
		if( null != passage ){
			//原文本
			passageResult.text = passage.element("text").getText();
		}
		
		if( null != passage ){
			List annotationList = passage.elements("annotation");
			
			for (Iterator i = annotationList.iterator(); i.hasNext();) {
				Element annotation = (Element)i.next();
				Element infon = annotation.element("infon");
				Element location = annotation.element("location");
				Element text = annotation.element("text");
				Annotation a = new Annotation();
				a.id = annotation.attributeValue("id");
				a.info = infon.getText();
				a.location[0] = Integer.parseInt(location.attributeValue("offset"));
				a.location[1] = Integer.parseInt(location.attributeValue("length"));
				a.text = text.getText();
				passageResult.mAnnotationMap.put(a.id, a);
			}
			
			List relationList = passage.elements("relation");
			
			for (Iterator i = relationList.iterator(); i.hasNext();) {
				Element relation = (Element)i.next();
				Element infon = relation.element("infon");
				List nodeList = relation.elements("node");
				Relation r = new Relation();
				r.id = relation.attributeValue("id");
				r.info = infon.getText();
				
				for( Iterator j = nodeList.iterator(); j.hasNext(); ){
					Element node = (Element)j.next();
					String annotationId = node.attributeValue("refid");
					String role = node.attributeValue("role");
					r.mAnnotationSet.put(passageResult.mAnnotationMap.get(annotationId), role);
				}
				
				passageResult.mRelationList.add(r);
			}
		}
		
		
		
		
		
		return passageResult;
	}
}
