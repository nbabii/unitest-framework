package framework.utils.database;

import java.util.List;
import java.util.Map;

import framework.utils.dto.IFrameworkDTO;

/**
 * DAO interface for framework DB Utilities
 * @author Taras.Lytvyn
 *
 */
public interface IDatabaseDao {

	/**
	 * Get the db result set and map it to List of Java Objects
	 * @param queryExpression	db query expression to retrieve objects
	 * @param clazz	class to map objects
	 * @return List of mapped objects
	 */
	public <T extends IFrameworkDTO> List<T> mapTestDataToObjects(String queryExpression, Class<T> clazz);

	/**
	 * Get the set from result set by if and represent it as map (key=column name, value=column value) 
	 * @param id result set id
	 * @param queryExpression query expression
	 * @return set by id from result set in map representation
	 */
	public Map<String, String> getMapFromResultSet(int id, String queryExpression);

}
