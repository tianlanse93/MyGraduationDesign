package com.scut.zl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileUtils {
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
}
