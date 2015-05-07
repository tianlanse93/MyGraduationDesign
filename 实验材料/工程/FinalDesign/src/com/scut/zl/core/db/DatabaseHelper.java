package com.scut.zl.core.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.scut.zl.bean.DisplayResource;
import com.scut.zl.config.Config;
import com.scut.zl.utils.DataConverter;

public class DatabaseHelper {

	public static final String driver = "com.mysql.jdbc.Driver";
	public static final String url = "jdbc:mysql://127.0.0.1:3306/";
	public static final String user = "root";
	public static final String password = "";

	// 数据库名
	public static final String DATABASE_NAME = "ArticleData";
	// 表名
	public static final String TABLE_NAME = "CoreData";
	// id
	public static final String KEY_ID = "id";
	// pmid
	public static final String KEY_PMID = "pmid";
	// 文献标题
	public static final String KEY_ABSTRACT_TITLE = "title";
	// 文献内容
	public static final String KEY_ABSTRACT_TEXT = "text";
	// 蛋白质实体
	public static final String KEY_SUBSTARTE = "substrate";
	// 激酶实体
	public static final String KEY_KINASE = "kinase";
	// 位点实体
	public static final String KEY_POSITION = "position";
	// 氨基酸实体
	public static final String KEY_ACID = "acid";
	// 磷酸化修饰
	public static final String KEY_PHOSPHORYLATION = "phosphorylation";
	// 实体关系
	public static final String KEY_RELATION = "relation";
	
	public static void insert(InsertBean bean) throws Exception {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url + DATABASE_NAME, user, password);
		if (!conn.isClosed()) {
			Statement statement = conn.createStatement();
			// 要执行的SQL语句
			String sql = "insert into "+TABLE_NAME+"("
					+ KEY_PMID + ","
					+ KEY_ABSTRACT_TITLE + ","
					+ KEY_ABSTRACT_TEXT + ","
					+ KEY_SUBSTARTE + ","
					+ KEY_KINASE + ","
					+ KEY_POSITION + ","
					+ KEY_ACID + ","
					+ KEY_PHOSPHORYLATION + ","
					+ KEY_RELATION+")" +  
					" values("
					+ "\"" + bean.pmid + "\","
					+ "\"" + bean.title + "\","
					+ "\"" + bean.text + "\","
					+ "\"" + bean.substrate + "\","
					+ "\"" + bean.kinase + "\","
					+ "\"" + bean.position + "\","
					+ "\"" + bean.acid + "\","
					+ "\"" + bean.phosphorylation + "\","
					+ "\"" + bean.relation + "\");";
			System.out.println(sql);
			statement.execute(sql);
		}
		conn.close();
	}
	
	private static void createTable(String tableName) throws Exception {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url + DATABASE_NAME,
				user, password);
		if (!conn.isClosed()) {
			// statement用来执行SQL语句
			Statement statement = conn.createStatement();
			// 要执行的SQL语句
			String sql = "create table " + tableName + "(" + KEY_ID
					+ " int(32) not null primary key auto_increment,"
					+ KEY_PMID + " varchar(100)," + KEY_ABSTRACT_TITLE
					+ " varchar(300)," + KEY_ABSTRACT_TEXT + " varchar(8000),"
					+ KEY_SUBSTARTE + " varchar(2000)," + KEY_KINASE
					+ " varchar(2000)," + KEY_POSITION + " varchar(2000),"
					+ KEY_ACID + " varchar(2000)," + KEY_PHOSPHORYLATION
					+ " varchar(2000)," + KEY_RELATION + " varchar(3000) );";
			System.out.println(sql);
			try {
				statement.execute(sql);
				System.out.println("Succeeded creating table " + tableName);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(tableName + "is already created");
			}
		}
	}

	public static void createDataBase(String databaseName) throws Exception {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		if (!conn.isClosed()) {
			// statement用来执行SQL语句
			Statement statement = conn.createStatement();
			// 要执行的SQL语句
			String sql = "create database " + databaseName + ";";

			try {
				statement.execute(sql);
				System.out.println("Succeeded creating database "
						+ databaseName);
			} catch (SQLException e) {
				System.out.println(databaseName + " is already created");
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws Exception {
		// createDataBase(DATABASE_NAME);
		createTable(TABLE_NAME);
		putCacheInDatabase();
	}

	public static void putCacheInDatabase() throws Exception {
		File[] cacheList = new File(Config.LOCAL_CACHE_PATH).listFiles();
		for ( int i = 0; i < cacheList.length;i++) {
			//过滤 DS.store文件
			if( i == 0 ){
				continue;
			}
			InputStream is = new FileInputStream(cacheList[i]);
			ObjectInputStream ois = new ObjectInputStream(is);
			DisplayResource dr = (DisplayResource) ois.readObject();
			insert(DataConverter.dr2InsertBean(dr));
		}
	}
}
