package testcase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.Constant;
import page.LoginPage;
import page.HomePage;
import page.PageFactory;
import test.AbstractTest;

public class LoginFunction extends AbstractTest {

	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void setup(String browser) {
		navigateToTestSite(browser);
	}
	
	@Test(priority = 1, description = "Verify that login to home page successfully")
	public void LoginToHomePage(){
		
		log.info("Login to Home Page");
		loginPage = PageFactory.getLoginPage();
		loginPage.login(userName, password);
		
		homePage = PageFactory.getHomePage();
		verifyPoint = verifyTrue(homePage.isHomePageDisplayed());
		log.info("VP01: " + verifyPoint + ": Verify that Login to Home page successfully with valid account");
		
		log.info("Log out");
		HomePage.logout();
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		closeBrowser();
	}
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	private String userName = Constant.LoginInfo.userName;
	private String password = Constant.LoginInfo.password;
	public String verifyPoint;
	
	
}
