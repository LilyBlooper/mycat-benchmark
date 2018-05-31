package io.lloyd.mycat.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBHandler {
	private static HikariDataSource ds;
	static {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://172.18.10.202:8066/TESTDB");
		config.setUsername("root");
		config.setPassword("123456");
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);
	}

	public static Connection getConn() throws SQLException {
		return ds.getConnection();
	}

}