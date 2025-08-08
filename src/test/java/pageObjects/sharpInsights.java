package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class sharpInsights extends basePage{
	WebDriver driver;
	public sharpInsights(WebDriver driver){
		super(driver);
	}@FindBy(xpath="//img[@alt='chat-icon']")
	public WebElement chatBot;
	@FindBy(xpath="//div[@class='chat-right']")
	public WebElement expandChat;
	@FindBy(xpath="(//textarea[@placeholder='Ask a Query'])[2]")
	public WebElement askQuery;
	@FindBy(xpath="//button[@class='MuiButtonBase-root MuiIconButton-root MuiIconButton-sizeMedium css-15bum57']")
	public WebElement sendQuery;
	@FindBy(xpath="//h6[text()='Recent']/..")
	public WebElement collapse;
	@FindBy(xpath="(//p[contains(text(),'I’m sorry, I couldn’t process that question at the moment.')])[2]")
	public WebElement questionProcessErrorMessage;
	public void clickChatBot() {
		chatBot.click();
	}public void expandChat() {
		expandChat.click();
	}public void askQuery(String s) {
		askQuery.sendKeys(s);
	}public void sendQuery() {
		sendQuery.click();
	}public String getQueryResultText(String q) {
		String obj ="(//p[text()='"+q+"'])[2]//following::div[@class='MuiTypography-root MuiTypography-body1 css-1yj7rn3']";
		String ans=driver.findElement(By.xpath(obj)).getText();
		return ans;
	}public void collapseRecent() {
		collapse.click();
	}
}
