package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataNexusUserManagement extends basePage{
	public DataNexusUserManagement(WebDriver driver){
		super(driver);
	}@FindBy(xpath="//div[@role='tab' and text()='Users']")
	public WebElement usersTab;
	@FindBy(xpath="//div[@role='tab' and text()='Users']")
	public WebElement rolesTab;
}
