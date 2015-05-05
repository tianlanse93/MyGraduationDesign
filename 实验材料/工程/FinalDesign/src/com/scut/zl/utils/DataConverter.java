package com.scut.zl.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.scut.zl.Rlims_p;
import com.scut.zl.bean.xmlbean.Annotation;
import com.scut.zl.bean.xmlbean.Node;
import com.scut.zl.bean.xmlbean.Relation;
import com.scut.zl.bean.xmlbean.ResultPassage;

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
		String s = "0000PMID - 0000 TI - Title not 0000 provided . AB - In recen 0000 t years , bone marrow-derived mesenchymal stem cells ( BMSCs ) have been demonstrated to exert extensive therapeutic effects on acute liver injury ; however , the underlying mechanisms of these effects have remained to be elucidated . The present study focused on the potential anti‑apoptotic and pro‑regenerative effects of BMSCs in D‑galactosamine ( D‑Gal ) and lipopolysaccharide ( LPS ) ‑induced acute liver injury in rats . An experimental rat acute liver injury model was established by intraperitoneal injection of D‑Gal ( 400 mg/kg ) and LPS ( 80 µg/kg ) . BMSCs and an identical volume of saline were administered via the caudal vein 2 h after the D‑Gal and LPS challenge . Subsequently , the serum samples were collected to detect the levels of alanine aminotransferase and aspartate aminotransferase . Hematoxylin and eosin staining , terminal deoxynucleotidyl transferase‑mediated nick‑end labeling assay and immunohistochemical staining were performed to determine apoptosis , regeneration and histological changes of liver sections . Western blotting and reverse transcription‑quantitative polymerase chain reaction were performed to detect the protein and mRNA expression levels of fibrinogen‑like‑protein 1 ( FGL1 ) , phosphorylated signal transducer and activator of transcription 3 ( p‑STAT3 ) , STAT3 and B‑cell lymphoma 2 ( Bcl‑2 ) and Bcl‑2 associated X protein ( Bax ) in liver tissue samples . The results indicated that intravenous transplantation of BMSCs significantly decreased the levels of alanine aminotransferase and aspartate aminotransferase , and reduced hepatocellular necrosis and inflammatory cell infiltration . Additionally , a terminal deoxynucleotidyl transferase‑mediated nick‑end labeling assay and immunohistochemical staining revealed that BMSC treatment reduced hepatocyte apoptosis and enhanced liver regeneration . Furthermore , Bcl‑2 expression was increased , whilst the protein expression of Bax was reduced . The expression of FGL1 and p‑STAT3 were elevated concurrently with the improvement of liver function . These results demonstrated that BMSCs may provide a promising potential agent for the prevention of acute liver injury via inhibition of hepatocyte apoptosis and acceleration of liver regeneration . The mechanism may be , a least in part , a consequence of the upregulation of FGL1 expression and the induction of STAT3 phosphorylation . NORM=stat 30000";

		System.out.println(insert(s, "0000","integer"));
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
}
