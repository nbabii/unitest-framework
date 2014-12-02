package framework.automation.driver;

/**
 * Abstract class for execution environment, parent for all environment classes:
 * ex. Browser, Mobile Platform
 * 
 * @author Taras.Lytvyn
 */
public abstract class ExecutionEnvironment {

	protected String currentExecutionEnvironmentName;

	/**
	 * @return execution environment name : ex. browser name or mobile platform
	 *         name
	 */
	public String getCurrentExecutionEnvironmentName() {
		return currentExecutionEnvironmentName;
	}

}
