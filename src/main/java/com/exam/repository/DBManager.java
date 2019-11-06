package com.exam.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBManager {
	
	private static SqlSessionFactory sqlSessionFactory;
	
	static {
		// 자바7이상부터 try with resources 문법 제공. 자동으로 close() 호출해서 자원 닫아줌.
		try (InputStream is = Resources.getResourceAsStream("mybatis-config.xml")) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // static 초기화 블록
	
	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

} // DBManager class