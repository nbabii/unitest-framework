package framework.web.imagerecognition;

import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;



public class Demo {
	
	private static final FrameworkLogger LOG = LogFactory.getLogger(Demo.class); 
	
	public static void main(String... args) throws ImageNotFoundException{
		ImageRecognitionDriver driver = new ImageRecognitionDriver();

				driver.setImageWaitingTimeout(2);
				driver.clickOnImage("button2.png");
			
		
		LOG.info("here is the first log mzf !!!!!");
	}

}
