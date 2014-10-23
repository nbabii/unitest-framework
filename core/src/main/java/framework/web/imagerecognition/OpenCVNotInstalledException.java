package framework.web.imagerecognition;

public class OpenCVNotInstalledException extends Exception {

	private static final long serialVersionUID = 1L;

	OpenCVNotInstalledException() {
	}

	public OpenCVNotInstalledException(String message) {
		super(message);
	}

}
