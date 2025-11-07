package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class testLogin extends basePageAllure{
	//public WebDriver driver;
	public testLogin(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="(//span[text()='Maxlength is 100'])[2]")
	public WebElement maxlength;
	@FindBy(id = "username")
	public WebElement username_textbox;
	@FindBy(xpath="//button[contains(@class,'icon-close')]")
	WebElement messageClose;
	@FindBy(id="password")
	public WebElement password_textbox;
	public void enterUsername(String username) {
		standardEnterTextbox(driver.findElement(By.xpath("//button[contains(@class,'icon-close')]")),username,"Username");
	}
	public void enterPassword(String password) {
		standardEnterTextbox(password_textbox,password,"Password");
	}

}
