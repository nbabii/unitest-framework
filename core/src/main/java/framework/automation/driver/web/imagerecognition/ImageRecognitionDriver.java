package framework.automation.driver.web.imagerecognition;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;

import framework.automation.page.CriteriaPageFactory;
import framework.automation.page.web.WebPage;
import framework.utils.parsers.FileReader;

/**
 * Class that represents image recognition driver based on Sikuli
 * @author Taras.Lytvyn
 *
 * @param <T>	Web Page
 */
public class ImageRecognitionDriver<T extends WebPage<T>> {

	private Class<T> webPage;
	
	private int imageWaitingTimeout = 7;
	private double recognitionScore = 1.0;
	private ScreenRegion screenRegion;

	public ImageRecognitionDriver(Class<T> webPage) {
		this.screenRegion = new DesktopScreenRegion();
		this.webPage = webPage;
	}

	/**
	 * @return 	image wait timeout for waiting image on screen
	 */
	public int getImageWaitingTimeout() {
		return imageWaitingTimeout;
	}

	/**
	 * @return 	set image wait timeout for waiting image on screen
	 */
	public void setImageWaitingTimeout(int imageWaitingTimeout) {
		this.imageWaitingTimeout = imageWaitingTimeout;
	}

	/**
	 * @return	recognition score in percentage
	 */
	public double getRecognitionScore() {
		return recognitionScore;
	}

	/**
	 * set the recognition score percentage 
	 * @param recognitionScore	percentage coefficient for recognition score
	 */
	public void setRecognitionScore(double recognitionScore) {
		this.recognitionScore = recognitionScore;
	}

	/**
	 * Clicks on image and staying on the same page object that we are situated before click
	 * @param webDriver		webdriver 
	 * @param imageFileLocation		image file location
	 * @return	same Page Object
	 */
	//Here we pass the generic Page Object Type T 
	public T clickAndStayOnSamePageObject(WebDriver webDriver,
			String imageFileLocation) {
		try {
			clickOnImage(imageFileLocation);
		} catch (ImageNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CriteriaPageFactory.criteriaInitWebElements(webDriver, webPage);
	}

	/**
	 * Click on image and return instance of anothe page object
	 * @param webDriver	webdriver
	 * @param imageFileLocation	image file location
	 * @param pageObjectClazz	Page object to return class
	 * @return	Page Object that should be returned
	 */
	public <V extends WebPage<V>> V clickAndMoveToPageObject(WebDriver webDriver,
			String imageFileLocation, Class<V> pageObjectClazz) {
		try {
			clickOnImage(imageFileLocation);
		} catch (ImageNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CriteriaPageFactory.criteriaInitWebElements(webDriver, pageObjectClazz);
	}

	/**
	 * Click on image
	 * @param imageFileLocation	image file location
	 * @throws ImageNotFoundException	exception if there is no image for current path
	 */
	private void clickOnImage(String imageFileLocation)
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

	/**
	 * is image on the screen ?
	 * @param imageFileLocation	image file location
	 * @return true if image is on the screen ?
	 * @throws OpenCVNotInstalledException	if OpenCV is not installed on execution machine
	 */
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
				System.out.println("java.library.path="
						+ System.getProperty("java.library.path"));
				System.out.println(error.getMessage());
				throw new OpenCVNotInstalledException(
						"opencv is not installed on your local machine or appropriate lib opencv-xx-xx.jar is not added to project path."
								+ "Follow steps for configuring opencv installation http://opencvlover.blogspot.in/2012/04/javacv-setup-with-eclipse-on-windows-7.html and "
								+ "https://code.google.com/p/javacv/wiki/Windows7AndOpenCV"
								+ "Follow steps for adding opencv jars hhttp://ganeshtiwaridotcomdotnp.blogspot.com/2011/12/opencv-javacv-eclipse-project.html");
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
