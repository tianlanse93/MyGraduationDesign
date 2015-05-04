package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import com.scut.zl.FileUtils;
import com.scut.zl.Rlims_p;
import com.scut.zl.bean.DisplayResource;
import com.scut.zl.bean.ResultPassage;
import com.scut.zl.utils.DataConverter;

public class TaskRunnable implements Runnable {

	private File mFile;
	private DisplayResource mResource;

	public TaskRunnable(File file, DisplayResource mResource) {
		this.mFile = file;
	}

	@Override
	public void run() {
		String fileContent = FileUtils.getAbstractContent(mFile);

		String params = "type=text&input=";
		params += fileContent;
		String s = sendPost(
				"http://annotation.dbi.udel.edu/text_mining/bioc/bioc.php?",
				params);
		System.out.println(s);
		ResultPassage result;
		try {
			result = DataConverter.xml2ResultPassage(s);
			mResource.mEntityMap = DataConverter.getEntitys(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

}
