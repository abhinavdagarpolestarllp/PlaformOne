package pageObjects;

import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SharpInsightsTemporary {
    WebDriver driver;
    public WebDriverWait wait;

    public SharpInsightsTemporary(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        
    }

    @FindBy(xpath = "//img[@alt='chat-icon']")
    public WebElement chatBot;

    @FindBy(xpath = "//div[@class='chat-right']")
    public WebElement expandChat;

    @FindBy(xpath = "(//textarea[@placeholder='Ask a Query'])[2]")
    public WebElement askQuery;

    @FindBy(xpath = "//button[@class='MuiButtonBase-root MuiIconButton-root MuiIconButton-sizeMedium css-15bum57']")
    public WebElement sendQuery;

    @FindBy(xpath = "//h6[text()='Recent']/..")
    public WebElement collapse;

    //@FindBy(xpath = "(//p[contains(text(),'I’m sorry, I couldn’t process that question at the moment.')])[2]")
    //public WebElement questionProcessErrorMessage;

    public void clickChatBot() {
        chatBot.click();
    }

    public void expandChat() {
        expandChat.click();
    }

    public void askQuery(String s) {
    	wait.until(ExpectedConditions.visibilityOf(askQuery));	
        askQuery.sendKeys(s);
    }

    public void sendQuery() {
        sendQuery.click();
    }public String sQLQuery(String s){
    	String obj ="(//p[text()='"+s+"'])[2]//following::button[text()='Show SQL Query']";
    	return obj;
    }public String getSQLQuery(String s) {
    	String obj ="(//p[text()='"+s+"'])[2]//following::pre[@class='sql-query']/code";
    	return driver.findElement(By.xpath(obj)).getText();
    }

    public String getQueryResultText(String q) {
        String obj = "(//p[text()='" + q + "'])[2]//following::div[@class='MuiTypography-root MuiTypography-body1 css-1yj7rn3']";
        WebElement ans = driver.findElement(By.xpath(obj));
        return ans.getText();
    }

    public void collapseRecent() {
        collapse.click();
    }public String validateQueryResult(String q) {
		String obj ="(//p[text()='"+q+"'])[2]//following::p[contains(text(),'I’m sorry, I couldn’t process that question at the moment.')]";
		return obj;
    }public String validateQueryResult1(String s) {
    	String obj ="(//p[text()='"+s+"'])[2]//following::p[contains(text(),'Hmm, I couldn’t find an answer for that just yet')]";
		return obj;
    }

    public int isObjectExist(WebElement e) {
        try {
            
            return e.isDisplayed() ? 1 : 0;
        } catch (Exception ex) {
            return 0;
        }
    }

    public int isObjectNotExist(By e) {
    	  try {
    	        // Wait for the element to be present in the DOM
    		  WebDriverWait shortwait = new WebDriverWait(driver, Duration.ofSeconds(3));
    	       // shortwait.until(ExpectedConditions.presenceOfElementLocated(e));
    		  WebElement element = shortwait.until(ExpectedConditions.visibilityOfElementLocated(e));
    		  //element.wait();
    	       return 0;
    	    } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException ex) {
    	        // If element is NOT present within the wait time — return 1 (not exist)
    	        return 1;
    	    } catch(Exception d) {
    	    	return 1;
    	    }
    	}
    	
    
}
