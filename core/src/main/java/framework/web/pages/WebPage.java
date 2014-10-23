package framework.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import framework.web.imagerecognition.ImageRecognitionDriver;

public class WebPage {

	private WebDriver webDriver;
	public ImageRecognitionDriver<PageFactory> imageRecognitionDriver = 
			new ImageRecognitionDriver<PageFactory>();

}
