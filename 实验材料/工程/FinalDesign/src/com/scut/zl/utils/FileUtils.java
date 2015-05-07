package com.scut.zl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
	public static String getAbstractContent(File file){
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] b = new byte[1024];
		String fileContent = "";
		try {
			while (is.read(b) != -1) {
				String s1 = new String(b);
				fileContent += s1;
				b = new byte[1024];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
}
