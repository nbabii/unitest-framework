package framework.automation.driver.web.imagerecognition;

/**
 * Exception that will be thrown whether image is not found on the screen
 * @author Taras.Lytvyn
 *
 */
public class ImageNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	ImageNotFoundException() {
	}

	public ImageNotFoundException(String message) {
		super(message);
	}

}
