package com.scut.zl.core.concurrence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.scut.zl.bean.DisplayResource;
import com.scut.zl.bean.xmlbean.ResultPassage;
import com.scut.zl.config.Config;
import com.scut.zl.rlims.Rlims_p;
import com.scut.zl.utils.DataConverter;
import com.scut.zl.utils.FileUtils;
import com.scut.zl.utils.HttpRequest;

public class TaskRunnable implements Runnable {

	private List<File> mFileList;

	
	public TaskRunnable(List<File> file) {
		this.mFileList = file;
	}

	@Override
	public void run() {

		for (File mFile : mFileList) {
			String cachePath = Config.LOCAL_CACHE_PATH + mFile.getName();
			File cacheFile = new File(cachePath);
			//如果文件存在就不用请求了
			if( cacheFile.exists() ){
				continue;
			}
			System.out.println(Thread.currentThread().getName() + " : deal "
					+ mFile.getName());
			
			String fileContent = FileUtils.getAbstractContent(mFile);
			String params = "type=text&input=";
			params += fileContent;
			String s = HttpRequest
					.sendPost(
							"http://annotation.dbi.udel.edu/text_mining/bioc/bioc.php?",
							params);
			ResultPassage passage;
			DisplayResource dr = new DisplayResource();
			try {
				passage = DataConverter.xml2ResultPassage(s);
				dr.text = passage.text;
				dr.mEntityMap = DataConverter.getEntitys(passage);
				dr.mRelationList = DataConverter.getRelationList(passage);
				// 把结果存入缓存，方便下次加载
				addCache(mFile.getName(), dr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 增加到文档集里面去
	private void addCache(String fileName, DisplayResource dr) throws Exception {
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
}
