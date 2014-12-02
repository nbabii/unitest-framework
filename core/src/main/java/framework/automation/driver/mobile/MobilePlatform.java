package framework.automation.driver.mobile;

import java.util.HashMap;
import java.util.Map;

import framework.automation.driver.ExecutionEnvironment;

/**
 * Class that represents the Mobile platform
 * @author Taras.Lytvyn
 *
 */
public class MobilePlatform extends ExecutionEnvironment {

	private static MobilePlatform mobilePlatform = null;

	private String mobileDeviceName;
	private String appType;

	private MobilePlatform() {

	}

	private MobilePlatform(String osType, String appType,
			String mobileDeviceName) {
		this.currentExecutionEnvironmentName = osType;
		this.appType = appType;
		this.mobileDeviceName = mobileDeviceName;
	}

	/**
	 * signleton to get Mobile platform object
	 * @param osType	os type (android , ios)
	 * @param appType	application type (native, hybrid)
	 * @param mobileDeviceName	mobile device name
	 * @return
	 */
	public static MobilePlatform getInstance(String osType, String appType,
			String mobileDeviceName) {
		if (mobilePlatform == null) {
			mobilePlatform = new MobilePlatform(osType, appType,
					mobileDeviceName);
		}
		return mobilePlatform;
	}

	/**
	 * reset the mobile platform object
	 */
	public void resetMobilePlatform() {
		mobilePlatform = null;
	}

	/**
	 * @return pre-created Mobile platform object
	 */
	public static MobilePlatform getSetMobilePlatform() {
		if (mobilePlatform == null) {
			throw new RuntimeException(
					"Mobile platform configurations are not set");
		}
		return mobilePlatform;
	}

	/**
	 * @return mobile application type
	 */
	public String getMobileApplicationType() {
		return appType;
	}

	/**
	 * @return mobile device name
	 */
	public String getMobileDeviceName() {
		return mobileDeviceName;
	}

	/**
	 * Enum to represent Mobile OS type
	 * @author Taras.Lytvyn
	 *
	 */
	public enum MobileOsType {
		ANDROID("android"), IOS("ios");

		private String browserKey;
		private static Map<String, MobileOsType> mobileOsMap = new HashMap<String, MobileOsType>();

		static {
			for (MobileOsType mot : MobileOsType.values()) {
				mobileOsMap.put(mot.key(), mot);
			}
		}

		private MobileOsType(String key) {
			browserKey = key;
		}

		private String key() {
			return this.browserKey;
		}

		public static MobileOsType get(String key) {
			if (mobileOsMap.containsKey(key)) {
				return mobileOsMap.get(key);
			}
			return ANDROID;
		}

	}

	/**
	 * Enum to represent Mobile Applicaiton type
	 * @author Taras.Lytvyn
	 *
	 */
	public enum MobileAppType {
		HYBRID("hybrid"), NATIVE("native");

		private String browserKey;
		private static Map<String, MobileAppType> mobileAppMap = new HashMap<String, MobileAppType>();

		static {
			for (MobileAppType mat : MobileAppType.values()) {
				mobileAppMap.put(mat.key(), mat);
			}
		}

		private MobileAppType(String key) {
			browserKey = key;
		}

		private String key() {
			return this.browserKey;
		}

		public static MobileAppType get(String key) {
			if (mobileAppMap.containsKey(key)) {
				return mobileAppMap.get(key);
			}
			return HYBRID;
		}

	}

}
