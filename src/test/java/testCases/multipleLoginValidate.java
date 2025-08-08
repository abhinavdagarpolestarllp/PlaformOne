package testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import Utilities.excelUtility;
import pageObjects.landingPage;
import pageObjects.loginPage;
import testBase.baseClass;

public class multipleLoginValidate extends baseClass{
	@Test(dataProvider="ChatBotQuestions",dataProviderClass=DataProviders.class)
	public void verifyUsers(int rowIndex, String user, String pass) {
	    /*try {
	        loginPage login = new loginPage(driver);
	        landingPage landing = new landingPage(driver);
	        excelUtility excel = new excelUtility(".\\testData\\LoginData.xlsx");

	        login.enterUsername(user);
	        login.enterPassword(pass);
	        login.clickLogin();

	        if (login.isObjectNotExist(landing.dataNexusLogo,"") == 0) {
	            excel.fillGreenColor("Sheet1", rowIndex, 3);
	            landing.dataNexusClick();
	            login.clickProfileDropdown();
	            //login.logout();
	            login.isObjectExist(login.username_textbox);
	        } else {
	            excel.fillRedColor("Sheet1", rowIndex, 3);
	            login.clearTextbox(login.username_textbox);
	            login.clearTextbox(login.password_textbox);
	        }
	    } catch (IOException e) {
	        Assert.fail("IO Exception: " + e.getMessage());
	    }*/
	}
	
}
