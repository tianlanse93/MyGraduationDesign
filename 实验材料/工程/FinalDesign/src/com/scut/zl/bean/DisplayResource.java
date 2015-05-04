package com.scut.zl.bean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.scut.zl.Config;
import com.scut.zl.FileUtils;
import com.scut.zl.utils.DataConverter;

public class DisplayResource {
	//实体
	public HashMap<Integer, List<String>> mEntityMap;
	//原文
	public String text;

//	DisplayResource(File file){
//
//		//文件名
//		String fileName = file.getName();
//		
//		String cachePath = Config.LOCAL_CACHE_PATH + fileName;
//		File cacheFile = new File(cachePath);
//		
//		if( cacheFile.exists() ){
//			String fileContent = FileUtils.getAbstractContent(cacheFile);
//			ResultPassage passage = null;
//			try {
//				passage = DataConverter.xml2ResultPassage(fileContent);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			mEntityMap = DataConverter.getEntitys(passage);
//			
//		}else{
//			
//		}
//	}
//	
//	private Runnable runable;
}
