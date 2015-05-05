package com.scut.zl.bean.xmlbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultPassage implements Serializable{

	//原文
	public String text;
	//标注实体List
	public HashMap<String,Annotation> mAnnotationMap;
	//实体关系List
	public List<Relation> mRelationList;
	
	public ResultPassage(){
		mAnnotationMap = new HashMap<String,Annotation>();
		mRelationList = new ArrayList<Relation>();
	}
}
