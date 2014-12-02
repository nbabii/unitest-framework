package framework.automation.driver.web.imagerecognition;

/**
 * Exception that will be thrown whether opencv is not installed on execution machine
 * @author Taras.Lytvyn
 *
 */
public class OpenCVNotInstalledException extends Exception {

	private static final long serialVersionUID = 1L;

	OpenCVNotInstalledException() {
	}

	public OpenCVNotInstalledException(String message) {
		super(message);
	}

}
