package framework.utils.httpclient;

/**
 * Exception that represents incorrect content type for requests executions
 * 
 * @author Taras.Lytvyn
 *
 */
public class IncorrectContentTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public IncorrectContentTypeException() {
	}

	public IncorrectContentTypeException(String message) {
		super(message);
	}

}
