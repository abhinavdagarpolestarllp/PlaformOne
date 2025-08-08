package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class workspacePage extends basePage{
	WebDriver driver;
	public workspacePage(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="//input[contains(@placeholder,'search')]")
	public WebElement searchBox;
	@FindBy(className="anticon anticon-plusa")
	public WebElement addNewWorkspace;
	
}
