package com.scut.zl.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.scut.zl.bean.DisplayResource;
import com.scut.zl.bean.xmlbean.Annotation;
import com.scut.zl.bean.xmlbean.Node;
import com.scut.zl.bean.xmlbean.Relation;
import com.scut.zl.bean.xmlbean.ResultPassage;
import com.scut.zl.core.db.InsertBean;
import com.scut.zl.rlims.Rlims_p;

public class DataConverter {
	public static ResultPassage xml2ResultPassage(String xml) throws Exception {
		ResultPassage passageResult = new ResultPassage();

		Document source = DocumentHelper.parseText(xml);

		Element root = source.getRootElement();

		Element document = root.element("document");

		Element passage = document.element("passage");

		if (null != passage) {
			// 原文本
			passageResult.text = passage.element("text").getText();
		}

		if (null != passage) {
			List annotationList = passage.elements("annotation");

			for (Iterator i = annotationList.iterator(); i.hasNext();) {
				Element annotation = (Element) i.next();
				Element infon = annotation.element("infon");
				Element location = annotation.element("location");
				Element text = annotation.element("text");
				Annotation a = new Annotation();
				a.id = annotation.attributeValue("id");
				a.info = infon.getText();
				a.location[0] = Integer.parseInt(location
						.attributeValue("offset"));
				a.location[1] = Integer.parseInt(location
						.attributeValue("length"));
				a.text = text.getText();
				passageResult.mAnnotationMap.put(a.id, a);
			}

			List relationList = passage.elements("relation");

			for (Iterator i = relationList.iterator(); i.hasNext();) {
				Element relation = (Element) i.next();
				Element infon = relation.element("infon");
				List nodeList = relation.elements("node");
				Relation r = new Relation();
				r.id = relation.attributeValue("id");
				r.info = infon.getText();

				for (Iterator j = nodeList.iterator(); j.hasNext();) {
					Element node = (Element) j.next();
					String annotationId = node.attributeValue("refid");
					String role = node.attributeValue("role");

					Node _node = new Node();
					_node.role = role;
					passageResult.mAnnotationMap.get(annotationId).role = role;
					_node.mAnnotation = passageResult.mAnnotationMap
							.get(annotationId);
					r.mNodeList.add(_node);
				}

				passageResult.mRelationList.add(r);
			}
		}

		return passageResult;
	}

	public static HashMap<Integer, List<String>> getEntitys(ResultPassage result) {
		HashMap<Integer, List<String>> mHashMap = new HashMap<Integer, List<String>>();
		List<String> list_protein = new ArrayList<String>();
		List<String> list_kinase = new ArrayList<String>();
		List<String> list_position = new ArrayList<String>();
		List<String> list_acid = new ArrayList<String>();
		List<String> list_substrate = new ArrayList<String>();
		List<String> list_trigger = new ArrayList<String>();
		mHashMap.put(Rlims_p.TAG_PROTEIN, list_protein);
		mHashMap.put(Rlims_p.TAG_KINASE, list_kinase);
		mHashMap.put(Rlims_p.TAG_POSITION, list_position);
		mHashMap.put(Rlims_p.TAG_ACID, list_acid);
		mHashMap.put(Rlims_p.TAG_SUBSTRATE, list_substrate);
		mHashMap.put(Rlims_p.TAG_TRIGGER, list_trigger);

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

	public static List<HashMap<Integer, List<String>>> getRelationList(
			ResultPassage result) {
		List<Relation> relationList = result.mRelationList;
		List<HashMap<Integer, List<String>>> displayRelationList = new ArrayList<HashMap<Integer, List<String>>>();

		for (Relation relation : relationList) {
			List<Node> nodeList = relation.mNodeList;
			HashMap<Integer, List<String>> relationMap = new HashMap<Integer, List<String>>();
			List<String> list_protein = new ArrayList<String>();
			List<String> list_kinase = new ArrayList<String>();
			List<String> list_position = new ArrayList<String>();
			List<String> list_acid = new ArrayList<String>();
			List<String> list_substrate = new ArrayList<String>();
			List<String> list_trigger = new ArrayList<String>();
			relationMap.put(Rlims_p.TAG_PROTEIN, list_protein);
			relationMap.put(Rlims_p.TAG_KINASE, list_kinase);
			relationMap.put(Rlims_p.TAG_POSITION, list_position);
			relationMap.put(Rlims_p.TAG_ACID, list_acid);
			relationMap.put(Rlims_p.TAG_SUBSTRATE, list_substrate);
			relationMap.put(Rlims_p.TAG_TRIGGER, list_trigger);

			for (Node node : nodeList) {
				if (node.role.equals("substrate")) {
					if (!list_substrate.contains(node.mAnnotation.text)) {
						list_substrate.add(node.mAnnotation.text);
					}
				}
				if (node.role.equals("amino_acid")) {
					if (!list_acid.contains(node.mAnnotation.text)) {
						list_acid.add(node.mAnnotation.text);
					}
				}
				if (node.role.equals("position")) {
					if (!list_position.contains(node.mAnnotation.text)) {
						list_position.add(node.mAnnotation.text);
					}
				}
				if (node.role.equals("kinase")) {
					if (!list_kinase.contains(node.mAnnotation.text)) {
						list_kinase.add(node.mAnnotation.text);
					}
				}
				if (node.role.equals("trigger")) {
					if (!list_trigger.contains(node.mAnnotation.text)) {
						list_trigger.add(node.mAnnotation.text);
					}
				}

			}
			displayRelationList.add(relationMap);
		}

		return displayRelationList;
	}

	public static String getEntitys(int tag, ResultPassage result) {
		HashMap<Integer, List<String>> entityMap = getEntitys(result);
		List<String> stringList = entityMap.get(tag);
		String entitys = "";
		for (String s : stringList) {
			entitys += s + ",   ";
		}
		return entitys;
	}

	public static String getEntitys(int tag,
			HashMap<Integer, List<String>> entityMap) {
		List<String> stringList = entityMap.get(tag);
		String entitys = "";
		for (String s : stringList) {
			entitys += s + ",   ";
		}
		return entitys;
	}

	public static String getRelation(int index, int tag,
			List<HashMap<Integer, List<String>>> list) {
		return getEntitys(tag, list.get(index));
	}

	/**
	 * 
	 * @param oldString
	 * @param insertItem
	 * @param position
	 * @return
	 */
	public static String insertString(String oldString, String insertItem,
			int position) {
		String newString = "";
		newString = oldString.substring(0, position) + insertItem
				+ oldString.substring(position, oldString.length());
		return newString;
	}

	public static String insertItemHead;
	public static String insertItemTail;

	public static String insertTag(String oldString, String tagName,
			String className, int start, int end) {
		insertItemHead = "<" + tagName + " class=\"" + className + "\">";
		insertItemTail = "</" + tagName + ">";

		String newString = insertString(oldString, insertItemHead, start);

		newString = insertString(newString, insertItemTail, end
				+ insertItemHead.length());

		return newString;
	}

	public static void main(String args[]) {
		String s = "string \" dsdsds";
		
		s = s.replace("\"", "\\\"");
		
		System.out.println(s);
	}

	private static String insert(String text, String item, String classname) {
		List<Integer> offsetList = new ArrayList<Integer>();

		int next = 0;
		int index = 0;
		do {
			index = text.indexOf(item, next);
			offsetList.add(index);
			next = index + item.length();
		} while (index != -1 && next < text.length());

		int gap = 0;
		for (int i : offsetList) {
			if(i == -1){
				continue;
			}
			text = insertTag(text, "span", classname,i + gap,
					i + item.length() + gap);
			gap += insertItemHead.length() + insertItemTail.length();
		}
		return text;
	}
	
	public static String getTaggedText(String text,HashMap<Integer, List<String>> entityMap){
		String taggedText = text;
		List<String> list = entityMap.get(Rlims_p.TAG_SUBSTRATE);
		for( String s : list ){
			taggedText = insert(taggedText,s,"substrate");
		}
		list = entityMap.get(Rlims_p.TAG_KINASE);
		for( String s : list ){
			taggedText = insert(taggedText,s,"kinase");
		}
		list = entityMap.get(Rlims_p.TAG_ACID);
		for( String s : list ){
			taggedText = insert(taggedText,s,"acid");
		}
		list = entityMap.get(Rlims_p.TAG_POSITION);
		for( String s : list ){
			taggedText = insert(taggedText,s,"position");
		}
		list = entityMap.get(Rlims_p.TAG_TRIGGER);
		for( String s : list ){
			taggedText = insert(taggedText,s,"trigger");
		}
		return taggedText;
	}
	
	public static String relation2String(List<HashMap<Integer, List<String>>> mRelationList){
		String relationString = "";
		relationString = mRelationList.toString();
		return relationString;
	}
	public static InsertBean dr2InsertBean(DisplayResource dr){
		InsertBean bean = new InsertBean();
		bean.text = dr.text;
		bean.substrate = getEntitys(Rlims_p.TAG_SUBSTRATE,dr.mEntityMap);
		bean.kinase = getEntitys(Rlims_p.TAG_KINASE,dr.mEntityMap);
		bean.position = getEntitys(Rlims_p.TAG_POSITION,dr.mEntityMap);
		bean.acid = getEntitys(Rlims_p.TAG_ACID,dr.mEntityMap);
		bean.phosphorylation = getEntitys(Rlims_p.TAG_TRIGGER,dr.mEntityMap);
		bean.relation = relation2String(dr.mRelationList);
		
		bean.text = bean.text.replace("'","\'");
		bean.substrate = bean.substrate.replace("'","\'");
		bean.kinase = bean.kinase.replace("'","\'");
		bean.position = bean.position.replace("'","\'");
		bean.acid = bean.acid.replace("'","\'");
		bean.phosphorylation = bean.phosphorylation.replace("'","\'");
		bean.relation = bean.relation.replace("'","\'");
		
		bean.text = bean.text.replace("\"", "\\\"");
		bean.substrate = bean.substrate.replace("\"", "\\\"");
		bean.kinase = bean.kinase.replace("\"", "\\\"");
		bean.position = bean.position.replace("\"", "\\\"");
		bean.acid = bean.acid.replace("\"", "\\\"");
		bean.phosphorylation = bean.phosphorylation.replace("\"", "\\\"");
		bean.relation = bean.relation.replace("\"", "\\\"");
		return bean;
	}
}
