package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser {
	public WebDriver launch(String browser) {
		try {
			if (browser.toLowerCase().equals("firefox")) {
				driver = new FirefoxDriver();
			} else if (browser.toLowerCase().equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", "src/main/resource/chromedriver.exe");
				driver = new ChromeDriver();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return driver;
	}

	private WebDriver driver = null;
}
