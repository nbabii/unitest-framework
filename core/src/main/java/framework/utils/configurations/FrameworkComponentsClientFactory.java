package framework.utils.configurations;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

/**
 * Factory that creates and returns FramewrkPropertiesUtil bean to various components
 * It reads environment.configuration properties files from classpath
 */
public class FrameworkComponentsClientFactory {
	
	private static final FrameworkLogger LOG = LogFactory
			.getLogger(FrameworkComponentsClientFactory.class);

	private static final String ENV_CTX = "classpath:environment-spring-config.xml";

	private static volatile FrameworkPropertiesUtil frameworkPropertiesUtil;

	@SuppressWarnings("resource")
	public synchronized static FrameworkPropertiesUtil getPropertiesUtil() {
		if (frameworkPropertiesUtil == null) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(ENV_CTX);
			frameworkPropertiesUtil = (FrameworkPropertiesUtil) ctx
					.getBean("frameworkPropertiesUtil");
			LOG.info("Prepared Framework Properties Util");
		}

		return frameworkPropertiesUtil;
	}

}
