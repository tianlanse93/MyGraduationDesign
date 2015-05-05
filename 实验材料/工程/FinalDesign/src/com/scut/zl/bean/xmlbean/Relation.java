package com.scut.zl.bean.xmlbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//<relation id="r0">
//<infon key="PTM_type">phosphorylation</infon>
//<node refid="t1" role="trigger"/>
//<node refid="t2" role="substrate"/>
//<node refid="t3" role="amino_acid"/>
//<node refid="t3" role="amino_acid"/>
//</relation>

public class Relation implements Serializable{
	//id
	public String id;
	//信息
	public String info;
	//标注，和标注的类型
	public List<Node> mNodeList;
	
	public Relation(){
		mNodeList = new ArrayList<Node>();
	}
}
