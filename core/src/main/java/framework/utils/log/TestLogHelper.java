package framework.utils.log;

import org.slf4j.MDC;

/**
 * Class to handle setting/removing MDC on per test case basis. This helps
 * us log each test case into it's own log file. See
 * {@link http://logback.qos.ch/manual/appenders.html#SiftingAppender}
 * and {@link http://logback.qos.ch/manual/mdc.html}
 *
 */
public class TestLogHelper {

    public static final String TEST_METHOD_NAME = "testMethodName";

    /**
     * Adds the test name to MDC so that sift appender can use it and log the new
     * log events to a different file
     *
     * @param name name of the new log file
     * @throws Exception
     */
    
    public static void startTestLogging(String methodName) throws Exception {
    	MDC.put(TEST_METHOD_NAME, methodName);
    }
    
    /**
     * Removes the key (log file name) from MDC
     *
     * @return name of the log file, if one existed in MDC
     */
    public static String stopTestLoggingSeparateMethod() {
        String name = MDC.get(TEST_METHOD_NAME);
        MDC.remove(TEST_METHOD_NAME);
        return name;
    }
}