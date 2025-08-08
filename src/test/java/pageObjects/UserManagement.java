package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserManagement extends basePage{
	//WebDriver driver;
	public UserManagement(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="//span[text()='Role Name']//following::p[1]")
	public WebElement firstRoleName;
	@FindBy(xpath="(//*[@data-icon='edit'])[1]")
	public WebElement userEditButton;
	@FindBy(xpath="(//img[contains(@class,'UserManagement_actionImage')])[1]")
	public WebElement userViewButton;
	@FindBy(xpath="//input[@name='role_name']")
	public WebElement roleName;
	@FindBy(xpath="//textarea[@name='description']")
	public WebElement roleDescription;
	public void clickEditUserButton() {
		standardClickButton(userEditButton,"Edit User");
	}public void clickPreviewUserButton() {
		standardClickButton(userViewButton,"Preview User");
	}
}
