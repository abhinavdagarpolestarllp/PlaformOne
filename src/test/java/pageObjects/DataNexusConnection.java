package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DataNexusConnection extends basePage{
	//WebDriver driver;
	public DataNexusConnection(WebDriver driver) {
		super(driver);
	}public final String connectionname = "Conn"+dateNTime;
	@FindBy(xpath="(//span[contains(@class,'SourceSetting_f')])[1]")
	public WebElement firstConnection;
	@FindBy(id="basic_source_name")
	public WebElement connectionNameTextbox;
	@FindBy(xpath="(//div[@class='Sourcecard '])[1]")
	public WebElement firstConnectionName;
	@FindBy(xpath="//input[@id='basic_server']")
	public WebElement serverTextbox;
	@FindBy(xpath="//input[@id='basic_port']")
	public WebElement portTextbox;
	@FindBy(xpath="//input[@id='basic_database']")
	public WebElement databaseTextbox;
	@FindBy(xpath="//input[@id='basic_user']")
	public WebElement userTextbox;
	@FindBy(xpath="//input[@id='basic_password']")
	public WebElement passwordTextbox;
	@FindBy (xpath="(//input[@class='searchInput'])[2]")
	public WebElement newConnectionSearchbox;
	@FindBy(xpath="//p[text()='Connection Validated Successfully']")
	public WebElement connectionTestSuccessMsg;
	@FindBy(xpath="//p[text()='connection added successfully']")
	public WebElement connectionAddedMsg;
	@FindBy(xpath="//p[text()='connection updated successfully']")
	public WebElement connectionUpdatedMsg;
	@FindBy(xpath="//p[text()='Invalid Credentials']")
	public WebElement invalidCredentials_Message;
	@FindBy(xpath="//div[text()='Please input your Connection name!']")
	public WebElement mandatoryConnectionName;
	@FindBy(xpath="//p[text()='Connection name is required!']")
	public WebElement mandatoryConnectionMessage;
	public void clickConnectionType(String s) {
		List<WebElement> lis = driver.findElements(By.xpath("//div[@class='ant-tabs-tab']"));
		for(WebElement e:lis) {
			if(e.getText().equals(s)) {
				e.click();
			}
		}
	}public void clickFirstConnection() {
		firstConnectionName.click();
	}public void setConnectionName(String s) {
		connectionNameTextbox.sendKeys(s);
	}public void setServer(String s) {
		serverTextbox.sendKeys(s);
	}public void setPort(String s) {
		portTextbox.sendKeys(s);
	}public void setDatabase(String s) {
		databaseTextbox.sendKeys(s);
	}public void setUserName(String s) {
		userTextbox.sendKeys(s);
	}public void setPassword(String s) {
		passwordTextbox.sendKeys(s);
	}public void selectConnectionType() {
		firstConnectionName.click();
	}public void newConnectionSearchbox(String data) {
		newConnectionSearchbox.sendKeys(data);
	}
	
}