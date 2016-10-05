package page;

import common.Interfaces;

public class HomePage extends AbstractPage {
	public HomePage() {
		control.setPage(this.getClass().getSimpleName());
	}
	
	public boolean isHomePageDisplayed(){
		waitForPageLoaded();
		return isControlDisplayed(Interfaces.HomePage.userIcon);
	}
	
	public static void logout(){
		click(Interfaces.HomePage.userIcon);
		sleep(timeout);
		click(Interfaces.HomePage.logoutButton);
		waitForPageLoaded();
	}
}
