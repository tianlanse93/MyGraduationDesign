package com.scut.zl.bean.xmlbean;

import java.io.Serializable;

public class Node implements Serializable{
	public Annotation mAnnotation;
	public String role;
	
	public Node(){
		mAnnotation = new Annotation();
	}
}
