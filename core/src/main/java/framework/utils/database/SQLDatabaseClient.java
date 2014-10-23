package framework.utils.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.dto.IFrameworkDTO;

public class SQLDatabaseClient implements IDatabaseDao {

	@Override
	public <T extends IFrameworkDTO> List<T> mapTestDataToObjects(
			String queryExpression, Class<T> clazz) {
		SQLDataBaseObjectMapper<T> objectMapper = new SQLDataBaseObjectMapper<T>();
		List<T> parsedList = null;
		Statement stmt = createStatement();
		ResultSet resultSet = null;
		try {
			resultSet = stmt.executeQuery(queryExpression);
			parsedList = objectMapper.sqlQueryResultToList(resultSet, clazz);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			SQLDataBaseUtil.close(resultSet);
			SQLDataBaseUtil.close(stmt);
			SQLDataBaseUtil.close(SQLDataBaseConnector.getConnection());
		}

		return parsedList;
	}

	public Map<String, String> getMapFromResultSet(int rowId,
			String queryExpression) {
		Map<String, String> resultMap = new HashMap<String, String>();
		Statement stmt = createStatement();
		ResultSet resultSet = null;
		try {
			resultSet = stmt.executeQuery(queryExpression);
			ResultSetMetaData metaData = resultSet.getMetaData();
			while (resultSet.next()) {
				if (resultSet.getRow() == rowId) {
					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						resultMap.put(metaData.getColumnName(i),
								resultSet.getString(i));
					}
					break;
				}
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			SQLDataBaseUtil.close(resultSet);
			SQLDataBaseUtil.close(stmt);
			SQLDataBaseUtil.close(SQLDataBaseConnector.getConnection());
		}

		return resultMap;
	}

	private Statement createStatement() {
		Statement stmt = null;
		try {
			stmt = SQLDataBaseConnector.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
}
