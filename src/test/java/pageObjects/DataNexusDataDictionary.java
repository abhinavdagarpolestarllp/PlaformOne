package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataNexusDataDictionary extends basePage{
	//WebDriver driver;
	public DataNexusDataDictionary(WebDriver driver) {
		super(driver);
		
	}@FindBy(xpath="//div[@role='tab' and text()='Silver Layer']")
	public WebElement silverLayer;
	@FindBy(xpath="//div[@role='tab' and text()='Bronze Layer']")
	public WebElement bronzeLayer;
	@FindBy(xpath="//div[@role='tab' and text()='Gold Layer']")
	public WebElement goldLayer;
}
