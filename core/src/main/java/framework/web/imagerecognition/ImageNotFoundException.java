package framework.web.imagerecognition;

public class ImageNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	ImageNotFoundException() {
	}

	public ImageNotFoundException(String message) {
		super(message);
	}

}
