package pageObjects;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Utilities.ExtentReportManager;

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
	@FindBy(xpath="//p[text()='Role Created successfully']")
	public WebElement roleCreatedMsg;
	@FindBy(xpath="//input[@id='first_name']")
	WebElement firstName;
	@FindBy(xpath="//input[@id='last_name']")
	WebElement lastName;
	@FindBy(xpath="//input[@id='email']")
	WebElement email;
	@FindBy(xpath="//input[@id='password']")
	WebElement password;
	@FindBy(xpath="//input[@id='confirmPassword']")
	WebElement confirmPassword;
	public WebElement roleUpdatedMsg;
	@FindBy(xpath="//p[text()='Role deactivated successfully']")
	public WebElement roleDeactivateMsg;
	@FindBy(xpath="//p[text()='Role activated successfully']")
	public WebElement roleActivateMsg;
	
	public void clickEditUserButton() {
		standardClickButton(userEditButton,"Edit User");
	}public void clickPreviewUserButton() {
		standardClickButton(userViewButton,"Preview User");
	}public void enterFirstName(String name) throws IOException {
		standardEnterTextbox(firstName,name,"First Name");
	}public void enterLastName(String name) throws IOException{
		standardEnterTextbox(lastName,name,"Last Name");
	}public void enterEmail(String mail) throws IOException{
		standardEnterTextbox(email,mail,"Email");
	}public void enterPassword(String pass) throws IOException{
		standardEnterTextbox(password,pass,"Password");
	}public void enterconfirmPassword(String pass) throws IOException{
		standardEnterTextbox(confirmPassword,pass,"Confirm Password");
	}public void selectModuleUserPrivileges(String moduleName,String acessType) {
		Map<String, String> actionMap = Map.of(
				"All","2",
		        "Create", "3",
		        "Read", "4",
		        "Update", "5",
		        "Delete", "6"
		    );
		String index=actionMap.get(acessType);
		String obj ="//td[text()='"+moduleName+"']//parent::tr//td["+index+"]//input[@class='ant-checkbox-input']";
		try{
			driver.findElement(By.xpath(obj)).click();
			ExtentReportManager.getTest().pass("User acess checkbox '"+acessType+"' is checked for module "+moduleName);
		}catch(Exception checkbox) {
			ExtentReportManager.getTest().fail("User acess checkbox '"+acessType+"' was not checked for module "+moduleName+" due to "+checkbox.getMessage());
		}
	}public void enterRoleName(String name) throws IOException {
		standardEnterTextbox(roleName,name,"Role Name");
	}public void enterRoleDescription(String description) throws IOException {
		standardEnterTextbox(roleDescription,description,"Description");
	}
}
