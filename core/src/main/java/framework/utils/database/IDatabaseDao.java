package framework.utils.database;

import java.util.List;
import java.util.Map;

import framework.utils.dto.IFrameworkDTO;

public interface IDatabaseDao {

	public <T extends IFrameworkDTO> List<T> mapTestDataToObjects(String queryExpression, Class<T> clazz);

	public Map<String, String> getMapFromResultSet(int id, String queryExpression);

}
