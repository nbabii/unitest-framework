package framework.utils.database;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.beanutils.BeanUtils;

public class SQLDataBaseObjectMapper<T> {

	@SuppressWarnings("serial")
	public static class ResultSetMapperException extends Exception {
		ResultSetMapperException(String msg) {
			super(msg);
		}

		ResultSetMapperException(String msg, Throwable throwable) {
			super(msg, throwable);
		}
	}

	/**
	 * maps SQL query result set to List of Java Objects
	 * @param resultSet	sql result set
	 * @param clazz class for mapping
	 * @return	List of mapped objects of passed class
	 * @throws Exception
	 */
	public List<T> sqlQueryResultToList(ResultSet resultSet, Class<T> clazz)
			throws Exception {
		List<T> parsedObjects = new ArrayList<T>();
		if (resultSet != null) {

			if (!clazz.isAnnotationPresent(Entity.class)) {
				throw new ResultSetMapperException(
						"Class missing Entity annotation: "
								+ clazz.getSimpleName());
			}
			try {
				ResultSetMetaData rsmd = resultSet.getMetaData();
				Field[] fields = clazz.getDeclaredFields();
				while (resultSet.next()) {
					T bean = (T) clazz.newInstance();
					// ResultSet indexes are 1-based.
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						String rsColumnName = rsmd.getColumnName(i);
						Object rsColumnValue = resultSet.getObject(i);
						for (Field field : fields) {
							if (field.isAnnotationPresent(Column.class)) {
								Column column = field
										.getAnnotation(Column.class);
								String beanColumnName = field.getName();
								if (!column.name().isEmpty()) {
									beanColumnName = column.name();
								}
								if (beanColumnName.equals(rsColumnName)) {
									BeanUtils.setProperty(bean,
											field.getName(), rsColumnValue);
									break;
								}
							}
						}
					}
					parsedObjects.add(bean);
				}
			} catch (Exception e) {
				throw new ResultSetMapperException(e.getMessage(), e);
			}
			return parsedObjects;

		} else
			throw new RuntimeException("result set is null");
	}
}