package io.lloyd.mycat.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Created by LilyBlooper(lilyblooper@163.com) on 2017/8/14.
 */
public class JdbcHelper {
	private static final Logger logger = Logger.getLogger(JdbcHelper.class);

	private JdbcHelper() {
	}

	/**
	 * Close the given JDBC Connection and ignore any thrown exception. This is
	 * useful for typical finally blocks in manual JDBC code.
	 * 
	 * @param con
	 *            the JDBC Connection to close (may be {@code null})
	 */
	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				logger.debug("Could not close JDBC Connection", ex);
			} catch (Throwable ex) {
				// We don't trust the JDBC driver: It might throw
				// RuntimeException or Error.
				logger.debug("Unexpected exception on closing JDBC Connection", ex);
			}
		}
	}

	/**
	 * Close the given JDBC Statement and ignore any thrown exception. This is
	 * useful for typical finally blocks in manual JDBC code.
	 * 
	 * @param stmt
	 *            the JDBC Statement to close (may be {@code null})
	 */
	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				logger.trace("Could not close JDBC Statement", ex);
			} catch (Throwable ex) {
				// We don't trust the JDBC driver: It might throw
				// RuntimeException or Error.
				logger.trace("Unexpected exception on closing JDBC Statement", ex);
			}
		}
	}

	/**
	 * Close the given JDBC ResultSet and ignore any thrown exception. This is
	 * useful for typical finally blocks in manual JDBC code.
	 * 
	 * @param rs
	 *            the JDBC ResultSet to close (may be {@code null})
	 */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				logger.trace("Could not close JDBC ResultSet", ex);
			} catch (Throwable ex) {
				// We don't trust the JDBC driver: It might throw
				// RuntimeException or Error.
				logger.trace("Unexpected exception on closing JDBC ResultSet", ex);
			}
		}
	}
}
