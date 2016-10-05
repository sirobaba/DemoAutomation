package page;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import common.AutomationControl;
import common.Constant;

public class AbstractPage {

	protected AbstractPage() {
		log = LogFactory.getLog(getClass());
		log.debug("Created page abstraction for " + getClass().getName());
	}

	/**
	 * enter value to dynamic control
	 * 
	 * @param controlName
	 * @param value
	 * @param controlValue
	 *            (value of dynamic control)
	 */
	public static void enter(String controlName, String value, String... controlValue) {
		waitForControl(controlName, timeout, controlValue);
		element = control.findElement(controlName, controlValue);
		element.clear();
		element.sendKeys(value);
	}

	/**
	 * send keyboard
	 * 
	 * @param controlName
	 * @param value
	 * @param controlValue
	 *            (value of dynamic control)
	 */
	public static void sendKey(String controlName, Keys keys, String... controlValue) {
		waitForControl(controlName, timeout, controlValue);
		element = control.findElement(controlName, controlValue);
		element.sendKeys(keys);
	}

	/**
	 * click control
	 * 
	 * @param controlName
	 */
	public static void click(String controlName) {
		waitForControl(controlName);
		element = control.findElement(controlName);
		element.click();
	}

	/**
	 * click to dynamic control
	 * 
	 * @param controlName
	 * @param controlValue
	 *            (value of dynamic control)
	 */
	public static void click(String controlName, String... controlValue) {
		waitForControl(controlName, timeout, controlValue);
		element = control.findElement(controlName, controlValue);
		element.click();
	}

	public static void clickByJS(String controlName) {
		element = control.findElement(controlName);
		JavascriptExecutor executor = (JavascriptExecutor) Constant.PathConfig.driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public static void waitForControl(final String controlName) {
		try {
			By by = control.getBy(controlName);
			WebDriverWait wait = new WebDriverWait(Constant.PathConfig.driver, 3);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
		}
	}

	public static void waitForControl(final String controlName, long timeWait) {
		try {
			By by = control.getBy(controlName);
			WebDriverWait wait = new WebDriverWait(Constant.PathConfig.driver, timeWait);
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
		}
	}

	/*
	 * dynamic control
	 */
	public static void waitForControl(final String controlName, long timeWait, String... value) {
		try {
			By by = control.getBy(controlName, value);
			WebDriverWait wait = new WebDriverWait(Constant.PathConfig.driver, timeWait);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
		}
	}
	

	public void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void sleep(long timeout) {
		try {
			Thread.sleep(timeout * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
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

	/**
	 * adding Check control display
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return true/false
	 */
	public boolean isControlDisplayed(String controlName, String... value) {
		waitForControl(controlName, timeout, value);
		try {
			element = control.findElement(controlName, value);
			// Focus element when found
			new Actions(Constant.PathConfig.driver).moveToElement(element).perform();
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * adding Check control display
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return true/false
	 */
	public boolean isControlDisplayed(String controlName) {
		waitForPageLoaded();
		waitForControl(controlName, timeout);
		try {
			element = control.findElement(controlName);
			// Focus element when found
			new Actions(Constant.PathConfig.driver).moveToElement(element).perform();
			return element.isDisplayed();
		}

		catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * adding check control is selected
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return true/false
	 */
	public boolean isControlSelected(String controlName, String... value) {
		waitForControl(controlName, timeout, value);
		try {
			element = control.findElement(controlName, value);
			return element.isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * adding Check an item exists in combobox or not
	 * 
	 * @param driver
	 * @param controlName
	 * @param itemName
	 * @return true/ false
	 */
	public boolean isItemExistInCombobox(String controlName, String itemName, String... value) {
		waitForControl(controlName, timeout, value);
		element = control.findElement(controlName, value);
		List<WebElement> itemsList = element.findElements(By.tagName("option"));
		for (WebElement item : itemsList) {
			if (item.getText().trim().equals(itemName.trim()))
				return true;
		}
		Reporter.log("<p style=\"color: red;font-weight: bold;padding-top: 10px;font-size: 14px;\">".concat(itemName).concat("</p>"));
		return false;
	}

	/**
	 * adding Check control enabled
	 * 
	 * @param controlName
	 * @param value
	 * @return true/false
	 */
	public boolean isControlEnabled(String controlName, String... value) {
		waitForControl(controlName, timeout, value);
		try {
			element = control.findElement(controlName, value);
			return element.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * adding get control attribute
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @param attribute
	 * @return attribute value
	 */
	public String getAttributeValue(String controlName, String attribute, String... value) {
		waitForControl(controlName, timeout, value);
		element = control.findElement(controlName, value);
		return element.getAttribute(attribute);
	}

	/**
	 * adding Force Click on control
	 * 
	 * @param driver
	 * @param controlName
	 */
	public void forceClick(String controlName, String... value) {
		waitForControl(controlName, timeout, value);
		WebElement element = control.findElement(controlName, value);
		JavascriptExecutor executor = (JavascriptExecutor) Constant.PathConfig.driver;
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * adding clear text in control
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 */
	public static void clear(String controlName, String... value) {
		waitForControl(controlName, timeout, value);
		element = control.findElement(controlName, value);
		element.clear();
	}

	/**
	 * adding enter value into control
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @param valueinput
	 */
	public void type(String controlName, String valueinput, String... value) {
		waitForControl(controlName, timeout, value);
		element = control.findElement(controlName, value);
		element.clear();
		element.sendKeys(valueinput);
	}

	/**
	 * adding get text for a control
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return text of control
	 */
	public String getText(String controlName, String... value) {
		waitForControl(controlName, timeout, value);
		WebElement element = control.findElement(controlName, value);
		return element.getText();
	}

	public String getTextAndRemoveAllSpaces(String controlName, String value) {
		waitForControl(controlName, timeout, value);
		WebElement element = control.findElement(controlName, value);
		return element.getText().replaceAll("\\s", "");
	}

	/**
	 * adding select item in Combobox
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @param item
	 */
	public void selectComboxboxItem(String controlName, String item, String... controlValue) {
		waitForControl(controlName, timeout, controlValue);
		element = control.findElement(controlName, controlValue);
		Select select = new Select(element);
		select.selectByVisibleText(item);
	}

	/**
	 * adding select item in Combobox by index
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @param item
	 */
	public void selectComboxboxItemByIndex(String controlName, int index, String... controlValue) {
		waitForControl(controlName, timeout, controlValue);
		element = control.findElement(controlName, controlValue);
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	/**
	 * adding Select Combobox item by Value
	 * 
	 * @param driver
	 * @param controlName
	 * @param optionValue
	 * @param value
	 */
	public void selectComboboxItemByValue(String controlName, String optionValue, String... controlValue) {
		waitForControl(controlName, timeout, controlValue);
		element = control.findElement(controlName, controlValue);
		Select select = new Select(element);
		select.selectByValue(optionValue);
	}

	/**
	 * adding get selected item in combobox
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return selectedItem
	 */
	public String getSelectedComboboxItem(String controlName) {
		waitForControl(controlName, timeout);
		element = control.findElement(controlName);
		Select select = new Select(element);
		String itemSelected = select.getFirstSelectedOption().getText();
		return itemSelected;
	}

	/**
	 * adding check a checkbox
	 */
	public void checkACheckbox(String controlName, String... controlValue) {
		waitForControl(controlName, timeout);
		element = control.findElement(controlName, controlValue);
		if (!element.isSelected()) {
			forceClick(controlName, controlValue);
		}
	}

	/**
	 * adding Click on a checkbox with javascript
	 */
	public void checkOneCheckbox(String controlName, String contain) {
		waitForControl(controlName, timeout);
		((JavascriptExecutor) Constant.PathConfig.driver).executeScript("$('label:contains(" + contain + ") > input').click();");
	}

	/**
	 * adding uncheck a checkbox
	 */
	public void uncheckACheckbox(String controlName, String... controlValue) {
		waitForControl(controlName, timeout);
		element = control.findElement(controlName, controlValue);
		if (element.isSelected()) {
			click(controlName, controlValue);
		}
	}

	/**
	 * adding Be unique for name
	 * 
	 * @param filename
	 * @param filepath
	 * @return string htmlScript
	 */

	public String getUniqueName(String name) {
		return name + DateTime.now().toString("MMMddyyHHmmss");
	}

	public boolean reportFailed(boolean result, String field, String expectedResult) {
		if (result == false) {
			Reporter.log("<p style=\"color: red;font-weight: bold;padding-top: 10px;font-size: 14px;\">".concat("Expected ").concat(field).concat(": ").concat(expectedResult).concat("</p>"));
			return false;
		}
		return result;
	}

	public boolean reportFailed(boolean result, String errorMessage) {
		if (result == false) {
			Reporter.log("<p style=\"color: red;font-weight: bold;padding-top: 10px;font-size: 14px;\">".concat(errorMessage).concat("</p>"));
			return false;
		}
		return result;
	}

	public boolean isItemExistInList(String item, List<String> list) {
		for (String str : list) {
			if (str.trim().contains(item))
				return true;
		}
		return false;
	}

	/**
	 * count element
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return number of elements
	 * 
	 */

	public int countElement(String controlName, String... value) {
		waitForControl(controlName, timeout, value);
		return control.findElements(controlName, value).size();
	}

	/**
	 * get element
	 * 
	 * @param driver
	 * @param controlName
	 * @return web element
	 */
	public WebElement getElement(String controlName, String... value) {
		waitForControl(controlName, timeout, value);
		element = control.findElement(controlName, value);
		return element;
	}

	/**
	 * get list of element
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return list of elements
	 */
	public List<WebElement> getElements(String controlName, String... value) {
		return control.findElements(controlName, value);
	}

	public void scrollingToElement(String controlName, String... controlValue) {
		waitForControl(controlName, timeout, controlValue);
		element = control.findElement(controlName, controlValue);
		((JavascriptExecutor) Constant.PathConfig.driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	/**
	 * @Quoc Le: Refresh page
	 * 
	 *       return void
	 * @Param none
	 */
	public void refreshPage() {
		Constant.PathConfig.driver.navigate().refresh();
		waitForPageLoaded();
	}

	public static void copy(String text) {
		
		Clipboard clipboard = getSystemClipboard();
		clipboard.setContents(new StringSelection(text), null);
	}

	public static void paste() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		robot.keyPress(KeyEvent.VK_CONTROL);
		sleep(1);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
	}

	private static Clipboard getSystemClipboard() {
		
		Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
		Clipboard systemClipboard = defaultToolkit.getSystemClipboard();

		return systemClipboard;
	}

	public static void typeValueByCopyPaste(String control, String value) {
		clear(control);
		copy(value);
		paste();
	}
	
	protected final Log log;
	protected static WebElement element;
	protected static AutomationControl control = new AutomationControl();
	protected static int timeout = 2;

}
