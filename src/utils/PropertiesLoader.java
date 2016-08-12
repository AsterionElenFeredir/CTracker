package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Loader for the properties of the application.
 * 
 * @author Bruno Marcon.
 *
 */
public class PropertiesLoader {
	private static final Properties props = new Properties();
	private static final String CONFIGURATION_FILE_NAME = "resources/configuration.properties";

	static {
		loadProperties();
	}
	
	/**
	 * Load Properties.
	 */
	public static void loadProperties() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(CONFIGURATION_FILE_NAME));
			props.load(fis);
		} catch (Exception e) {

		} finally {
			if (null != fis)
				try {
					fis.close();
				} catch (Exception e2) {
					// NOTHING TO DO
				}
		}
	}
	
	/**
	 * Get Integer value.
	 * 
	 * @param propertyName
	 * @return
	 */
	public static synchronized int getValue(String propertyName) {
		String value = props.getProperty(propertyName);
		return Integer.parseInt(value);
	}
	
	/**
	 * Get label.
	 * 
	 * @param propertyName
	 * @return
	 */
	public static synchronized String getLabel(String propertyName) {
		return (String)props.getProperty(propertyName);
	}
	
}
