package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MDMMasters extends basePage{
	//WebDriver driver;
	public MDMMasters(WebDriver driver) {
		super(driver);
	}@FindBy(xpath="(//div[@class='edit'])[1]")
	WebElement edit;
	@FindBy(xpath="(//div[@class='configure'])[1]")
	WebElement configure;
	@FindBy(xpath="(//div[@class='schedule'])[1]")
	WebElement schedule;
	@FindBy(xpath="(//div[@class='trigger'])[1]")
	WebElement trigger;
	@FindBy(xpath="(//div[@class='deduplogs'])[1]")
	WebElement viewDetails;
	public void clickEdit() {
		standardClickButton(edit,"Edit");
	}public void clickConfigure() {
		standardClickButton(configure,"Configure");
	}public void clickSchedule() {
		standardClickButton(schedule,"Schedule");
	}public void clickTrigger() {
		standardClickButton(trigger,"Trigger");
	}public void clickViewDetais() {
		standardClickButton(viewDetails,"View Details");
	}

}
