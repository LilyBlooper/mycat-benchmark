package io.lloyd.mycat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import io.lloyd.mycat.entity.Foo;
import io.lloyd.mycat.util.DBHandler;
import io.lloyd.mycat.util.JdbcHelper;

/**
 * 批量插入一些测试数据 <br/>
 * Created by LilyBlooper(lilyblooper@163.com) on 2018/5/31.
 */
public class MyCatBatchInsertRunner {

	public static void main(String[] args) throws Exception {
		String from = args[0];
		String size = args[1];
		String type = args[2];
		if (from == null || size == null) {
			throw new IllegalArgumentException("不传参数怎么运行!! :p");
		}
		if (from.length() == 0 || size.length() == 9) {
			throw new IllegalArgumentException("参数为空休想运行!! :p");
		}
		int startFrom = Integer.parseInt(from);
		int batchSize = Integer.parseInt(size);
		if (type.equals("1")) {
			testInsertOneByOne(startFrom, batchSize);
		}
		if (type.equals("2")) {
			testInsertOneByOneBarTable(startFrom, batchSize);
		}
		// testInsertOneByOne(10000, 10000);
		// testInsertBatch(10000, 10000);
	}

	/**
	 * 测试一个一个顺序插入,每次都新建连接
	 * 
	 * @param startFrom
	 * @param size
	 */
	private static void testInsertOneByOne(int startFrom, int size) {
		List<Foo> foos = buildEntity(startFrom, size);
		List<String> logs = new ArrayList<>();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			logs.add(insertOne(foos.get(i)));
		}
		long endTime = System.currentTimeMillis();
		System.out.println("testInsertOneByOne 整体耗费了" + (endTime - startTime) + "毫秒");
		for (String log : logs) {
			System.out.println(log);
		}
	}

	/**
	 * 测试一个一个顺序插入,每次都新建连接
	 *
	 * @param startFrom
	 * @param size
	 */
	private static void testInsertOneByOneBarTable(int startFrom, int size) {
		List<Foo> foos = buildEntity(startFrom, size);
		List<String> logs = new ArrayList<>();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < size; i++) {
			logs.add(insertOneBarTable(foos.get(i)));
		}
		long endTime = System.currentTimeMillis();
		System.out.println("testInsertOneByOneBarTable 整体耗费了" + (endTime - startTime) + "毫秒");
		for (String log : logs) {
			System.out.println(log);
		}
	}

	private static void testInsertBatch(int startFrom, int size) {
		List<Foo> foos = buildEntity(startFrom, size);
		long startTime = System.currentTimeMillis();
		List<String> logs = insertBatch(foos);
		long endTime = System.currentTimeMillis();
		System.out.println("testInsertOneByOne 整体耗费了" + (endTime - startTime) + "毫秒");
		for (String log : logs) {
			System.out.println(log);
		}
	}

	private static String insertOne(Foo foo) {
		long startTIme = System.currentTimeMillis();
		String sql = "insert into mock_table (uid,some_text) values (?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBHandler.getConn();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, foo.getUid());
			ps.setString(2, foo.getText());
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcHelper.closeStatement(ps);
			JdbcHelper.closeConnection(conn);
		}
		String log = "insert one log：db-read-costs:" + (System.currentTimeMillis() - startTIme)
				+ "millis!";
		return log;
	}

	private static String insertOneBarTable(Foo foo) {
		long startTIme = System.currentTimeMillis();
		String sql = "insert into bar_table (user_id ,message,c_date) values (?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBHandler.getConn();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, foo.getUid());
			ps.setString(2, foo.getText());
			ps.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
			ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcHelper.closeStatement(ps);
			JdbcHelper.closeConnection(conn);
		}
		String log = "insertOneBarTable：db-read-costs:" + (System.currentTimeMillis() - startTIme)
				+ "millis!";
		return log;
	}

	private static List<String> insertBatch(List<Foo> fooList) {
		List<String> logList = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBHandler.getConn();
			for (Foo foo : fooList) {
				long startTime = System.currentTimeMillis();
				String sql = "insert into mock_table (uid,some_text) values (?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setLong(1, foo.getUid());
				ps.setString(2, foo.getText());
				ps.executeUpdate();
				logList.add("insert one log：db-read-costs:"
						+ (System.currentTimeMillis() - startTime) + "millis!");
				JdbcHelper.closeStatement(ps);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			JdbcHelper.closeConnection(conn);
		}
		return logList;
	}

	/**
	 * 准备下测试数据
	 * 
	 * @param startFrom
	 * @param size
	 * @return
	 */
	private static List<Foo> buildEntity(int startFrom, int size) {
		List<Foo> entities = new ArrayList<>();
		for (int i = startFrom; i < size + startFrom; i++) {
			entities.add(new Foo(i, "text[" + i + "]"));
		}
		return entities;
	}

}
