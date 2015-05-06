package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import com.scut.zl.Config;
import com.scut.zl.bean.DisplayResource;

public class DatabaseHelper {
	//pmid
	public static final String KEY_PMID = "pmid";
	//文献标题
	public static final String KEY_ABSTRACT_TITLE = "title";
	//文献内容
	public static final String KEY_ABSTRACT_TEXT = "text";
	//蛋白质实体
	public static final String KEY_SUBSTARTE = "substrate";
	//激酶实体
	public static final String KEY_KINASE = "kinase";
	//位点实体
	public static final String KEY_POSITION = "position";
	//氨基酸实体
	public static final String KEY_ACID = "acid";
	//磷酸化修饰
	public static final String KEY_PHOSPHORYLATION = "phosphorylation";
	//实体关系
	public static final String KEY_RELATION = "relation";
	
	
	public static void createDataBase(){
		
	}
	
	public static void putCacheInDatabase() throws Exception{
		File[] cacheList = new File(Config.LOCAL_CACHE_PATH).listFiles();
		for( File cache : cacheList ){
			InputStream is = new FileInputStream(cache);
			ObjectInputStream ois = new ObjectInputStream(is);
			DisplayResource dr = (DisplayResource) ois.readObject();
		}
	}
}
