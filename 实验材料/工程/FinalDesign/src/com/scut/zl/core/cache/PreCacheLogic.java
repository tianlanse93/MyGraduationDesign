package com.scut.zl.core.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.scut.zl.config.Config;
import com.scut.zl.core.concurrence.TaskRunnable;
import com.scut.zl.core.concurrence.ThreadPool;

public class PreCacheLogic {

	// 开始预处理
	private static void startPreCacheMachine(int threadSize) {
		File[] list = new File(Config.DOC_SET_PATH).listFiles();
		// 每一个线程处理多少文件
		int countPerThread = list.length / threadSize + 1;
		//HashMap 每个线程对应一组文件，因为每个线程要处理一组文件
		HashMap<Integer, List<File>> hashMap = new HashMap<Integer, List<File>>();
		for (int i = 0; i < threadSize; i++) {
			ArrayList<File> fileList = new ArrayList<File>();
			hashMap.put(i,fileList);
		}
		
		//把文件装载进不同的线程
		for (int i = 1; i < list.length; i++) {
			int whichThread = i/countPerThread;
			System.out.println(i+"");
			hashMap.get(whichThread).add(list[i]);
		}
		
		for (int i = 0; i < threadSize; i++) {
			TaskRunnable tr = new TaskRunnable( hashMap.get(i) );
			ThreadPool.getThreadPool().execute(tr);
		}
	}

	public static void preCache() {
		startPreCacheMachine(20);
	}

	public static void main(String args[]) {
		preCache();
	}
}
