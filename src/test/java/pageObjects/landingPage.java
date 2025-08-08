package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class landingPage extends basePage {
	//WebDriver driver;
	public landingPage(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="//img[@class='centerLogoClass']")
	public WebElement dataNexusLogo;
	@FindBy(className="DSsolutions")
	public WebElement dataSolutions;
	@FindBy(className="output-solutions")
	public WebElement solutionsBox;
	@FindBy(xpath="//div[contains(@class,'agenthood-solution')]")
	public WebElement agentHoodIcon;
	public void dataNexusClick() {
		dataNexusLogo.click();
	}
	
}
