package framework.utils.parsers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * abstract class for all readers
 * @author Taras.Lytvyn
 *
 */
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

	/**
	 * close input stream
	 */
	protected void closeStream() {
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * gets String from inputstream
	 * @param is	input stream
	 * @return	string represantation of input stream
	 * @throws IOException
	 */
	public static String getStringFromInputStream(InputStream is)
			throws IOException {
		String inputStreamString = IOUtils.toString(is);
		return inputStreamString;
	}

}
