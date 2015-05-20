package com.scut.zl.bean;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.scut.zl.config.Config;
import com.scut.zl.utils.DataConverter;
import com.scut.zl.utils.FileUtils;

public class DisplayResource implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 725061072956556274L;
	//pmid
	public String pmid;
	//文献标题
	public String title;
	// 原文
	public String text;
	// 实体
	public HashMap<Integer, List<String>> mEntityMap;
	// 关系
	public List<HashMap<Integer, List<String>>> mRelationList;
}
