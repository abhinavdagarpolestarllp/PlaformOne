package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataNexusTenants extends basePage{
	public DataNexusTenants(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="//input[@placeholder='Enter Client ID']")
	WebElement clientId;
	@FindBy(xpath="//input[@placeholder='Enter Client Secret']")
	WebElement clientSecret;
	@FindBy(xpath="//input[@placeholder='Enter Tenant ID']")
	WebElement tenantId;
}
