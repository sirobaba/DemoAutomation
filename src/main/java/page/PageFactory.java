package page;

import page.LoginPage;

public class PageFactory {

	/**
	 * Get login page
	 * 
	 * @param none
	 * @return Login page
	 */
	public static LoginPage getLoginPage() {
		return new LoginPage();
	}
	
	/**
	 * Get login page
	 * 
	 * @param none
	 * @return Login page
	 */
	public static HomePage getHomePage() {
		return new HomePage();
	}

}
