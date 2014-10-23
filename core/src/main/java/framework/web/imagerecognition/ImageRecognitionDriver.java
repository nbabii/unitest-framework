package framework.web.imagerecognition;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;

import framework.utils.parsers.FileReader;
import framework.web.pages.WebPage;

public class ImageRecognitionDriver<P extends PageFactory> {

	private int imageWaitingTimeout = 7;
	private double recognitionScore = 1.0;
	private ScreenRegion screenRegion;

	public ImageRecognitionDriver() {
		this.screenRegion = new DesktopScreenRegion();
	}

	public int getImageWaitingTimeout() {
		return imageWaitingTimeout;
	}

	public void setImageWaitingTimeout(int imageWaitingTimeout) {
		this.imageWaitingTimeout = imageWaitingTimeout;
	}

	public double getRecognitionScore() {
		return recognitionScore;
	}

	public void setRecognitionScore(double recognitionScore) {
		this.recognitionScore = recognitionScore;
	}

	public <T extends WebPage> T clickAndStayOnSamePageObject(
			String imageFileLocation, T webPage) {
		try {
			clickOnImage(imageFileLocation);
		} catch (ImageNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return webPage;
	}

	public <T extends WebPage> T clickAndMoveToPageObject(WebDriver webDriver,
			String imageFileLocation, Class<T> pageObjectClazz) {
		try {
			clickOnImage(imageFileLocation);
		} catch (ImageNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return P.initElements(webDriver, pageObjectClazz);
	}

	public void clickOnImage(String imageFileLocation)
			throws ImageNotFoundException {

		try {
			if (isImageOnScreen(imageFileLocation)) {
				Mouse mouse = new DesktopMouse();
				mouse.click(screenRegion.getCenter());
			} else
				throw new ImageNotFoundException(
						imageFileLocation
								+ " - image was not found on the screen after timeout: "
								+ imageWaitingTimeout + " ms");
		} catch (OpenCVNotInstalledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isImageOnScreen(String imageFileLocation)
			throws OpenCVNotInstalledException {
		BufferedImage ininputBufferImage = null;
		Target imageTarget = null;
		try {
			ininputBufferImage = ImageIO.read(FileReader.class
					.getResourceAsStream("/test-images/" + imageFileLocation));

			imageTarget = new ImageTarget(ininputBufferImage);

			screenRegion = screenRegion.wait(imageTarget,
					imageWaitingTimeout * 1000);
		} catch (UnsatisfiedLinkError error) {
			if (error.getMessage().contains("opencv")) {
				throw new OpenCVNotInstalledException(
						"opencv is not installed on your machine or not added to system path.");
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"no file in resources/test-images/ folder with name:"
							+ imageFileLocation + " located", e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			screenRegion = (screenRegion.find(imageTarget));
		} catch (NullPointerException npe) {
			return false;
		}
		return true;
	}
}
