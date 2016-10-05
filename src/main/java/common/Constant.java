package common;

import org.openqa.selenium.WebDriver;

public class Constant {

	public static class PathConfig {
		public static final String AUTOMATION_CONFIG_XML = "src/main/resource/automation.config.xml";
		public static final String DATA_TEST_XML = "src/main/resource/TestData/";
		public static final String CAPTURE_SCREENSHOT = "\\test-output\\screenshots";
		public static final String interfacesXMLPath = "src/main/resource/interfaces/";
		public static final String HOME_URL = "http://www.doubletvn.byethost31.com/aut";
		public static final String ADMINSTRATOR_URL = "http://www.doubletvn.byethost31.com/aut/administrator/";
		
		public static WebDriver driver;
	}
	
	public static class LoginInfo {
		public static final String userName = "demo";
		public static final String password = "demo123";
	}

}
