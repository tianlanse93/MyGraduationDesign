package com.scut.zl.abner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.scut.zl.config.Config;

import abner.Tagger;

public class EntityRecognizer {

	public static void recognizer(String path) throws Exception {
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		
		
		for (int i = 0; i < fileList.length; i++) {
			System.out.println("**************"+i+"**************");
			recognizerSingleFile(fileList[i]);
			System.out.println(" ");
			System.out.println(" ");
		}
	}

	public static void recognizer() throws Exception {
		recognizer(Config.DOC_SET_PATH);
	}

	public static void recognizerSingleFile(File file) throws Exception {
		String fileContent = getAbstractContent(file);
		Tagger t = new Tagger();
		String protein[] = t.getEntities(fileContent, "PROTEIN");
		for (int i = 0; i < protein.length; i++) {
			System.out.println(protein[i]);
		}
	}

	public static String getAbstractContent(File file) throws Exception {
		InputStream is = new FileInputStream(file);
		byte[] b = new byte[1024];
		String fileContent = "";
		while (is.read(b) != -1) {
			String s1 = new String(b);
			fileContent += s1;
			b = new byte[1024];
		}
		is.close();
		return fileContent;
	}

	public static String getAbstractContent(String path) {
		return null;
	}

}
