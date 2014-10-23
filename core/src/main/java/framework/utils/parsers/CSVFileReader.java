package framework.utils.parsers;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.dto.IFrameworkDTO;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class CSVFileReader extends FileReader {

	private CSVReader csvReader;

	public CSVFileReader(String fileLocation) {
		super(fileLocation);
		try {
			parsedInput = getStringFromInputStream(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// OpenCSV is lazy - stream is closed after reading csv
		closeStream();
	}

	public <T extends IFrameworkDTO> List<T> getObjectFromCSV(Class<T> clazz) {
		csvReader = new CSVReader(new StringReader(parsedInput));
		HeaderColumnNameTranslateMappingStrategy<T> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<T>();
		beanStrategy.setType(clazz);

		Map<String, String> columnMapping = new HashMap<String, String>();
		for (Field field : clazz.getDeclaredFields()) {
			columnMapping.put(field.getName(), field.getName());
		}

		beanStrategy.setColumnMapping(columnMapping);
		CsvToBean<T> csvToBean = new CsvToBean<T>();

		List<T> parsedObjects = csvToBean.parse(beanStrategy, csvReader);
		return parsedObjects;
	}

	public List<Map<String, String>> getListOfMapsFromCSV() {
		csvReader = new CSVReader(new StringReader(parsedInput));
		List<Map<String, String>> resultParsedList = new ArrayList<Map<String, String>>();
		try {
			String[] columnKeys = csvReader.readNext();
			if (columnKeys != null) {
				String[] csvRow;
				Map<String, String> rowMap;
				while ((csvRow = csvReader.readNext()) != null) {
					rowMap = new HashMap<String, String>();
					for (String key : columnKeys) {
						rowMap.put(key, csvRow[Arrays.asList(columnKeys)
								.indexOf(key)]);
					}
					resultParsedList.add(rowMap);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultParsedList;
	}

}
