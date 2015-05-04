package core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	public static ExecutorService pool;
	
	public static ExecutorService getThreadPool(){
		if( null == pool ){
			synchronized (ThreadPool.class) {
				if( null == pool ){
					pool = Executors.newCachedThreadPool();
				}
			}
		}
		return pool;
	}

}
