package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import common.Browser;
import common.Constant;

public class AbstractTest {
	protected AbstractTest() {
		log = LogFactory.getLog(getClass());

	}

	protected boolean verifyTrue(boolean condition, boolean halt) {
		boolean pass = true;
		if (halt == false) {
			try {
				Assert.assertTrue(condition);
			} catch (Throwable e) {
				pass = false;
				VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			}
		} else {
			Assert.assertTrue(condition);
		}
		return pass;
	}

	protected String verifyTrue(boolean condition) {

		if (verifyTrue(condition, false)) {
			return "Passed";
		} else {
			return "Failed";
		}

	}

	protected boolean verifyFalse(boolean condition, boolean halt) {
		boolean pass = true;
		if (halt == false) {
			try {
				Assert.assertFalse(condition);
			} catch (Throwable e) {
				pass = false;
				VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			}
		} else {
			Assert.assertFalse(condition);
		}
		return pass;

	}

	protected String verifyFalse(boolean condition) {
		if (verifyFalse(condition, false)) {
			return "Passed";
		} else {
			return "Failed";
		}
	}

	public static void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		Wait<WebDriver> wait = new WebDriverWait(Constant.PathConfig.driver, 60);
		try {
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println("Timed out!!!");
		}
	}

	public static void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void navigateToTestSite(String browser) {
		Browser br = new Browser();
		Constant.PathConfig.driver = br.launch(browser);
		Constant.PathConfig.driver.get(Constant.PathConfig.ADMINSTRATOR_URL);
		Constant.PathConfig.driver.manage().deleteAllCookies();
		maximize();
		waitForPageLoaded();
	}

	public static void maximize() {
		Constant.PathConfig.driver.manage().window().maximize();
	}

	public static void closeBrowser() {
		try {
			Constant.PathConfig.driver.quit();
			System.gc();
			if (Constant.PathConfig.driver.toString().toLowerCase().contains("chrome")) {
				String cmd = "taskkill /f /t /im chromedriver.exe";
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	protected final Log log;
}
