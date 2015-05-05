package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import com.scut.zl.Config;
import com.scut.zl.FileUtils;
import com.scut.zl.bean.DisplayResource;
import com.scut.zl.bean.xmlbean.ResultPassage;
import com.scut.zl.utils.DataConverter;
import com.scut.zl.utils.HttpRequest;

public class DisplayLogic {
	public static DisplayResource getDisplayRes(File file) {
		DisplayResource dr = new DisplayResource();
		// 文件名
		String fileName = file.getName();

		String cachePath = Config.LOCAL_CACHE_PATH + fileName;
		File cacheFile = new File(cachePath);

		// 1.如果有缓存
		if (cacheFile.exists()) {
			try {
				return getFromCache(cacheFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}// 如果没有缓存
		else {
			String fileContent = FileUtils.getAbstractContent(file);
			String params = "type=text&input=";
			params += fileContent;
			String s = HttpRequest.sendPost(
					"http://annotation.dbi.udel.edu/text_mining/bioc/bioc.php?",
					params);
			System.out.println(s);
			ResultPassage passage;
			try {
				passage = DataConverter.xml2ResultPassage(s);
				dr.text = passage.text;
				dr.mEntityMap = DataConverter.getEntitys(passage);
				dr.mRelationList = DataConverter.getRelationList(passage);
				// 把结果存入缓存，方便下次加载
				addCache(fileName, dr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dr;
	}

	// 增加到文档集里面去
	private static void addCache(String fileName, String fileContent)
			throws Exception {
		if (fileName.equals("")) {
			return;
		}
		// 由于java会把"/"当成子目录处理，所以要替换成：进行区别
		fileName = fileName.replace("/", ":");
		File file = new File(new File(Config.LOCAL_CACHE_PATH), fileName);

		OutputStream os = new FileOutputStream(file);
		os.write(fileContent.getBytes());
		os.flush();
		os.close();
	}

	// 增加到文档集里面去
	private static void addCache(String fileName, DisplayResource dr)
			throws Exception {
		if (fileName.equals("")) {
			return;
		}
		// 由于java会把"/"当成子目录处理，所以要替换成：进行区别
		fileName = fileName.replace("/", ":");
		File file = new File(new File(Config.LOCAL_CACHE_PATH), fileName);

		OutputStream os = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(os);
		
		oos.writeObject(dr);
		oos.flush();
		oos.close();
	}

	private static DisplayResource getFromCache(File file) throws Exception {
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
		DisplayResource dr = (DisplayResource) is.readObject();
		return dr;
	}
}
