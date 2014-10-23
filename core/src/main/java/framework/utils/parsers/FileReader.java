package framework.utils.parsers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public abstract class FileReader {

	protected InputStream input;
	protected String parsedInput;

	protected FileReader() {
	}

	protected FileReader(String fileLocation) {
		try {
			input = FileReader.class.getResourceAsStream(fileLocation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void closeStream() {
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public static String getStringFromInputStream(InputStream is)
			throws IOException {
		String inputStreamString = IOUtils.toString(is);
		return inputStreamString;
	}

}
