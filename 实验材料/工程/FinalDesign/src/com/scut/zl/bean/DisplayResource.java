package com.scut.zl.bean;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.scut.zl.Config;
import com.scut.zl.FileUtils;
import com.scut.zl.utils.DataConverter;

public class DisplayResource implements Serializable {
	// 原文
	public String text;
	// 实体
	public HashMap<Integer, List<String>> mEntityMap;
	// 关系
	public List<HashMap<Integer, List<String>>> mRelationList;
}
