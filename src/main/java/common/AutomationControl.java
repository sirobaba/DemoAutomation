package common;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AutomationControl {
	public void loadControlInfo(String controlName) {
		try {
			
			NodeList nList = readXLMFile(Constant.PathConfig.interfacesXMLPath, getPage(), "control");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				Element control = (Element) nNode;
					
				if (control.getElementsByTagName("name").item(0).getTextContent().equals(controlName)) 
					{
						setControlType(control.getElementsByTagName("type").item(0).getTextContent());
						setControlValue(control.getElementsByTagName("value").item(0).getTextContent());
					}
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public NodeList readXLMFile(String XMLFolderPath, String xmlFileName, String tagName)
	{	
		NodeList nList = null;
		try {
			File fXmlFile = new File(XMLFolderPath + xmlFileName + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
	
			doc.getDocumentElement().normalize();
			nList = doc.getElementsByTagName(tagName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return nList;
	}

	public WebElement findElement(String controlName) {
		// load control information
		loadControlInfo(controlName);
		WebElement element = null;
		String type = getControlType();
		String value = getControlValue();

		// find element by xpath
		if (type.equals("xpath")) {
			element = Constant.PathConfig.driver.findElement(By.xpath(value));
		}
		// find element by id
		if (type.equals("id")) {
			element = Constant.PathConfig.driver.findElement(By.id(value));
		}
		// find element by name
		if (type.equals("name")) {
			element = Constant.PathConfig.driver.findElement(By.name(value));
		}
		return element;
	}

	public WebElement findElement(String controlName, String... dynamicValue) {
		// load control information
		loadControlInfo(controlName);
		WebElement element = null;
		String type = getControlType();
		String value = getControlValue();
		value = String.format(value, dynamicValue);

		// find element by xpath
		if (type.equals("xpath")) {
			element = Constant.PathConfig.driver.findElement(By.xpath(value));
		}
		// find element by id
		if (type.equals("id")) {
			element = Constant.PathConfig.driver.findElement(By.id(value));
		}
		// find element by name
		if (type.equals("name")) {
			element = Constant.PathConfig.driver.findElement(By.name(value));
		}
		return element;
	}
	
	/**
	 * find list of dynamic element
	 * 
	 * @param driver
	 * @param controlName
	 * @param inputValue
	 * @return list of dynamic element 
	 */
	 
	public List<WebElement> findElements(String controlName, String... inputValue) {
		List<WebElement> lstElement = Constant.PathConfig.driver.findElements(getBy(controlName, inputValue));
		return lstElement;
	}
	
	
	public By getBy(String controlName) {
		//load control information
		loadControlInfo(controlName);
		By by = null;
		String type = getControlType();
		String value = getControlValue();
		//get element by xpath value
		if (type.equals("xpath")) {
			by = By.xpath(value);
		}
		//get element by id value
		if (type.equals("id")) {
			by = By.id(value);
		}
		//get element by name value
		if (type.equals("name")) {
			by = By.name(value);
		}
		return by;
	}
	
	public By getBy(String controlName, String... dynamicValue) {
		//load control information
		loadControlInfo(controlName);
		By by = null;
		String type = getControlType();
		String value = getControlValue();
		//value = String.format(value, dynamicValue);
		
		//get element by xpath value
		if (type.equals("xpath")) {
			by = By.xpath(value);
		}
		//get element by id value
		if (type.equals("id")) {
			by = By.id(value);
		}
		//get element by name value
		if (type.equals("name")) {
			by = By.name(value);
		}
		return by;
	}
	
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlValue(String controlValue) {
		this.controlValue = controlValue;
	}

	public String getControlValue() {
		return controlValue;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	private String controlValue;
	private String controlType;
	private String page;
}
