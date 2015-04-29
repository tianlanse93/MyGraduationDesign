package com.scut.zl.bean;


//<annotation id="t2"> 
//<infon key="rlims_internal_representation">G protein-coupled receptors ( GPCRs )</infon>
//<location offset="158" length="56"/>
//<text>
//agonist -activated G protein-coupled receptors ( GPCRs )
//</text>
//</annotation>

public class Annotation {

	// 唯一标示符
	public String id;
	// 标注信息
	public String info;
	// 被标注所在位置
	public int[] location;
	// 被标注的文本
	public String text;

	public Annotation() {
		location = new int[2];
	}
}
