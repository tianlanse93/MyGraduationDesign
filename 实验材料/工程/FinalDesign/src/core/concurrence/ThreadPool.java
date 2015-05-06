package core.concurrence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	private static ExecutorService pool;
	
	private ThreadPool(){
		
	}
	
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
