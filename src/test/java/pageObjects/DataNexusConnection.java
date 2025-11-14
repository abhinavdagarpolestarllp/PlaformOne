package pageObjects;

import java.io.IOException;
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
	@FindBy(xpath="(//div[@class='Sourcecard '])[2]")
	public WebElement firstConnectionName;
	@FindBy(xpath="(//div[@class='Sourcecard '])[1]")
	public WebElement allFirstConnectionName;
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
	@FindBy(xpath="//p[contains(text(),'Connection Validated Successfully')]")
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
	@FindBy(xpath="//div[contains(@class,'editIcon')]")
	WebElement editConnectionType;
	@FindBy(xpath="//span[text()='This connection already exists']")
	public WebElement duplicateNameMsg;
	@FindBy(xpath="//p[text()='PostgreSQL connection successful']")
	public WebElement postgreMsg;
	public void clickConnectionType(String s) {
		List<WebElement> lis = driver.findElements(By.xpath("//div[@class='ant-tabs-tab']"));
		for(WebElement e:lis) {
			if(e.getText().equals(s)) {
				e.click();
			}
		}
	}public void clickFirstConnection() throws IOException {
		standardClickButton(firstConnectionName,"Connection Type");
	}public void clickAllFirstConnection() throws IOException {
		standardClickButton(allFirstConnectionName,"Connection Type");
	}public void setConnectionName(String s) throws IOException {
		standardEnterTextbox(connectionNameTextbox,s,"Connection Textbox");
	}public void setServer(String s) throws IOException {
		standardEnterTextbox(serverTextbox,s,"Set Server");
	}public void setPort(String s) throws IOException {
		standardEnterTextbox(portTextbox,s,"Port");
		
	}public void setDatabase(String s) throws IOException {
		standardEnterTextbox(databaseTextbox,s,"Database");
	}public void setUserName(String s) throws IOException {
		standardEnterTextbox(userTextbox,s,"User");
	}public void setPassword(String s) throws IOException {
		standardEnterTextbox(passwordTextbox,s,"Password");
	}public void newConnectionSearchbox(String data) throws IOException{
		standardEnterTextbox(newConnectionSearchbox,data,"New Connection Searchbox");
	}public void clickEditConnectionType() throws IOException {
		standardClickButton(editConnectionType,"Edit Connection Type");
	}
	
}