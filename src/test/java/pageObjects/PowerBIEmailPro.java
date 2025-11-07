package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PowerBIEmailPro extends basePage{
	//WebDriver driver;
	public PowerBIEmailPro(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="(//*[text()='Select Campaign']//following::div[@class='slicer-dropdown-menu'])[1]")
	WebElement selectCampaign;
	@FindBy(xpath="//span[text()='Email Pro']")
	WebElement emailPro;
	@FindBy(xpath="//div[@class='searchHeader show']//input")
	WebElement selectCampagnSearchbox;
	@FindBy(xpath="//div[text()='Use another account']")
	WebElement userAnotherAccount;
	public void clickChecbox(String s) {
		By xpath = By.xpath("//div[@title='"+s+"']//div");
		try {
			WebDriverWait twait = new WebDriverWait(driver, Duration.ofSeconds(8));
			WebElement checkbox = twait.until(ExpectedConditions.presenceOfElementLocated(xpath));
			checkbox.click();
		}catch(Exception e){
			
		}
	}public void clickSelectAllChecbox() {
		By xpath = By.xpath("//div[@title='Select all']//div[@class='slicerCheckbox selected']");
		By xpath1 = By.xpath("//div[@title='Select all']//div");
		try {
			WebDriverWait twait = new WebDriverWait(driver, Duration.ofSeconds(8));
			WebElement checkbox = twait.until(ExpectedConditions.presenceOfElementLocated(xpath));
			checkbox.click();
		}catch(Exception e){
			WebDriverWait twait = new WebDriverWait(driver, Duration.ofSeconds(8));
			WebElement checkbox1 = twait.until(ExpectedConditions.presenceOfElementLocated(xpath1));
			checkbox1.click();
		}
	}
	public void selectCampaign(String value) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(selectCampaign));
			selectCampaign.click();
			clickSelectAllChecbox();
			selectCampagnSearchbox.sendKeys(value);
			clickChecbox(value);
			driver.findElement(By.xpath("//div[@aria-label='Successful deliveries ']")).click();
		}catch(Exception dropdown) {
			System.out.print("Dropdown selection failed for selectCampaign");
		}
	}public String getContentValue(String value) {
		By loc = By.xpath("(//h3[text()='"+value+"']//following::div[@data-testid='visual-content-desc'])[1]");
		try {
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(loc));
			return element.getText();
		}catch(Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}public void loginIntoPowerBi() {
		userAnotherAccount.click();
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("data@omniamedcommunications.com");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Hippoc-fawdy4-mekceh");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.findElement(By.xpath("//input[@id='idBtn_Back']")).click();
		}
	@FindBy(xpath = "//input[contains(@id,'email')]")
	private WebElement EnterUsername;
	
	@FindBy(xpath = "//div/button[contains(@id,'submitBtn')]")
	private WebElement ClickOnSubmit;
	
	@FindBy(xpath = "//input[contains(@id,'i0118')]")
	private WebElement EnterPassword;
 
	@FindBy(xpath = "//input[contains(@id,'idSIButton9')]")
	private WebElement ClickOnLogin;
	
	@FindBy(xpath = "//input[contains(@id,'idSIButton')]")
	private WebElement ClickOnStaySignedIn;
	public void login(String username, String password) {
    	EnterUsername.sendKeys(username);
    	ClickOnSubmit.click();
    	EnterPassword.sendKeys(password);
    	ClickOnLogin.click();
    	ClickOnStaySignedIn.click();
    	
    }public void clickEmailPro() {
    	By xpath = By.xpath("//span[text()='Email Pro']");
		try {
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
			element.click();
		}catch(Exception e){
			
		}
	}@FindBy(xpath="//button[@data-testid='reset-to-default-btn']")
	WebElement resetButton;
	public void clickResetButton() {
		By xpath = By.xpath("//button[text()='Reset']");
		try {
			resetButton.click();
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
			element.click();
		}catch(Exception e){
			
		}
	}
	
}
