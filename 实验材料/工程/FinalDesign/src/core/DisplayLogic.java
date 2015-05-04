package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import com.scut.zl.Config;
import com.scut.zl.FileUtils;
import com.scut.zl.bean.DisplayResource;
import com.scut.zl.bean.ResultPassage;
import com.scut.zl.utils.DataConverter;

public class DisplayLogic {
	public static DisplayResource getDisplayRes(File file){
		DisplayResource dr = new DisplayResource();
		dr.text = FileUtils.getAbstractContent(file);
		//文件名
		String fileName = file.getName();
		
		String cachePath = Config.LOCAL_CACHE_PATH + fileName;
		File cacheFile = new File(cachePath);
		
		//1.如果有缓存
		if( cacheFile.exists() ){
			String fileContent = FileUtils.getAbstractContent(cacheFile);
			ResultPassage passage = null;
			try {
				passage = DataConverter.xml2ResultPassage(fileContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(passage.toString());
			dr.mEntityMap = DataConverter.getEntitys(passage);
			
		}//如果没有缓存
		else{
			String fileContent = FileUtils.getAbstractContent(file);
			String params = "type=text&input=";
			params += fileContent;
			String s = sendPost(
					"http://annotation.dbi.udel.edu/text_mining/bioc/bioc.php?",
					params);
			System.out.println(s);
			ResultPassage passage;
			try {
				passage = DataConverter.xml2ResultPassage(s);
				dr.mEntityMap = DataConverter.getEntitys(passage);
				//把结果存入缓存，方便下次加载
				addCache(fileName, s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dr;
	}
	
	private static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	//增加到文档集里面去
	private static void addCache(String fileName, String fileContent)
			throws Exception {
		if (fileName.equals("")) {
			return;
		}
		//由于java会把"/"当成子目录处理，所以要替换成：进行区别
		fileName = fileName.replace("/", ":");
		File file = new File(
				new File(Config.LOCAL_CACHE_PATH),
				fileName);

		OutputStream os = new FileOutputStream(file);
		os.write(fileContent.getBytes());
		os.flush();
		os.close();
	}
}
