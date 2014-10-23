package framework.utils.httpclient;

public class IncorrectContentTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public IncorrectContentTypeException() {
	}

	public IncorrectContentTypeException(String message) {
		super(message);
	}

}
