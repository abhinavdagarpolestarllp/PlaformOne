package pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MDMEntities extends basePage{
	//WebDriver driver;
	public MDMEntities(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="//div[@class='togle-buttons']")
	WebElement toggleButton;
	@FindBy(xpath="(//div[@class='ellipsis-custom'])[1]")
	WebElement actionButton;
	@FindBy(xpath="//span[text()='GR Details']")
	WebElement grDetails;
	@FindBy(xpath="//button[text()='Add New Variant']")
	WebElement addNewVariant;
	@FindBy(xpath="//input[@id='customer_id']")
	WebElement customerID;
	@FindBy(xpath="//input[@id='name']")
	WebElement name;
	@FindBy(xpath="//input[@id='email']")
	WebElement email;
	@FindBy(xpath="//input[@id='city']")
	public WebElement city;
	@FindBy(xpath="//input[@id='phone']")
	WebElement phone;
	@FindBy(xpath="(//div[contains(@class,'ant-card ant-card-bordered')])[1]")
	public WebElement entityCard;
	@FindBy(xpath="//div[contains(@class,'AddNewTaxonomy_select')]")
	public WebElement createTaxonomyFor;
	@FindBy(xpath="//input[@id='basic_taxonomyName']")
	WebElement taxonomyName;
	@FindBy(xpath="(//span[text()='Entities']//following::div[contains(@class,'sideChildGreyCircle')]//following-sibling::span)[1]")
	public WebElement firstSideNavEntity;
	public void selectEntity(String s) {
		List<WebElement> list=driver.findElements(By.xpath("//span[contains(@class,'SideNav_sideNotModulename')]"));
		for(WebElement e:list) {
			if(e.getText().equals(s)) {
				standardClickButton(e,"Entity Value"+s);
			}
		}
	}public void selectCreatedTaxonomyFor(String s) {
		dropdownElement(createTaxonomyFor,s,"Created Taxonomy");
	}public void enterTaxonomyName(String name) throws IOException{
		standardEnterTextbox(taxonomyName,name,"Taxonomy Name");
	}public void clickFirstSideNavEntity() {
		standardClickButton(firstSideNavEntity,firstSideNavEntity.getText());
	}
	public void clickEntityCard() throws InterruptedException {
		By goldenRecordLoc = By.xpath("//span[text()='Golden Record Details']");
	    while (true) {
	        try {
	        	By loc = By.xpath("(//div[contains(@class,'ant-card ant-card-bordered')])[1]");
	            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));

	            // wait max 2 sec for element to be visible
	            WebElement entityCard = shortWait.until(
	                ExpectedConditions.visibilityOfElementLocated(loc));
	            if (!entityCard.isDisplayed()) {
	                System.out.println("Break - element not visible anymore");
	                break;
	            }

	            // click the element
	            standardClickButton(entityCard, entityCard.getText());

	            // wait 2 sec after clicking
	            Thread.sleep(2000);
	            if (!driver.findElements(goldenRecordLoc).isEmpty()) {
	                System.out.println("Break - Golden Record Details appeared");
	                break;
	            }

	        } catch (Exception e) {
	            System.out.println("Break - element not visible or stale");
	            break;
	        }
	    }
	}
}
