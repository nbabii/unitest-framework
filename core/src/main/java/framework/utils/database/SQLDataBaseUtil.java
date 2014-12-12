package framework.utils.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL DB Util class
 * @author Taras.Lytvyn
 *
 */
public class SQLDataBaseUtil {

	/**
	 * close connection
	 * @param connection
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				/* log or print or ignore */
			}
		}
	}

	/**
	 * close statement
	 * @param statement
	 */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				/* log or print or ignore */
			}
		}
	}

	/**
	 * close the result set
	 * @param resultSet
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				/* log or print or ignore */
			}
		}
	}

}
