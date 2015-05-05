package com.scut.zl.bean.xmlbean;

import java.io.Serializable;


//<annotation id="t2"> 
//<infon key="rlims_internal_representation">G protein-coupled receptors ( GPCRs )</infon>
//<location offset="158" length="56"/>
//<text>
//agonist -activated G protein-coupled receptors ( GPCRs )
//</text>
//</annotation>

public class Annotation implements Comparable<Annotation>,Serializable {

	// 唯一标示符
	public String id;
	// 标注信息
	public String info;
	// 被标注所在位置
	public int[] location;
	// 被标注的文本
	public String text;
	//标签
	public String role;

	public Annotation() {
		location = new int[2];
	}
	
	@Override
    public int compareTo(Annotation arg0) {
        return this.location[0] - arg0.location[0];
    }
	
	@Override
	public String toString(){
		return location[0]+"";
	}
}
