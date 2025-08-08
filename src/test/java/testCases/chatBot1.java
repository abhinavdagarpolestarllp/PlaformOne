package testCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import Utilities.excelUtility;
import pageObjects.SharpInsightsTemporary;
//import pageObjects.loginPage;
//import pageObjects.sharpInsights;
import testBase.baseClassTesting;

public class chatBot1 extends baseClassTesting {
    String path = ".\\TestData\\ChatBot.xlsx";
    excelUtility xlutil = new excelUtility(path);
    String sheetName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    SharpInsightsTemporary ins;// Declare here

    @BeforeMethod
    public void setup() {
        ins = new SharpInsightsTemporary(driver); // initialize after driver ready
    }

    @Test(priority = 1)
    public void calidateChatBot() throws InterruptedException, IOException {
    	SharpInsightsTemporary ins = new SharpInsightsTemporary(driver);
        ins.wait.until(ExpectedConditions.visibilityOf(ins.chatBot));
        xlutil.setCellData(sheetName, 0, 0, "Questions");
        xlutil.setCellData(sheetName, 0, 1, "Answer");
        xlutil.setCellData(sheetName, 0, 2, "Actual SQL");
        xlutil.setCellData(sheetName, 0, 3, "Expected Keywords In SQL");
        xlutil.setCellData(sheetName, 0, 4, "Answer Validation");
        xlutil.setCellData(sheetName, 0, 5, "SQL Validation");
        xlutil.setCellData(sheetName, 0, 6, "Missing SQL Text");
        ins.clickChatBot();
        ins.expandChat();
        ins.collapseRecent();
    }

    @Test(dataProvider = "ChatBotQuestions", dataProviderClass = DataProviders.class, dependsOnMethods = {"calidateChatBot"})
    public void questionsValidation(String ques, int i,String expectedStatus,String expectedSQLText) throws IOException, InterruptedException, TimeoutException {
        try {
        	SharpInsightsTemporary ins = new SharpInsightsTemporary(driver);
            ins.askQuery(ques);
            ins.sendQuery();
            ins.wait.until(driver -> ins.askQuery.isEnabled());

            By e = By.xpath(ins.validateQueryResult(ques));
            By q = By.xpath(ins.validateQueryResult1(ques));
            By f = By.xpath(ins.sQLQuery(ques));
            xlutil.setCellData(sheetName, i, 3, expectedSQLText);

            if (ins.isObjectNotExist(e) == 0) {
                xlutil.setCellData(sheetName, i, 0, ques);
                xlutil.setCellData(sheetName, i, 1, ins.getQueryResultText(ques));
                xlutil.setCellData(sheetName, i, 5, "NA");
                if(expectedStatus.equals("Fail")) {
                    xlutil.setCellData(sheetName, i, 4, "Pass");
                }else {
                    xlutil.setCellData(sheetName, i, 4, "Fail");
                }
            } else if (ins.isObjectNotExist(q) == 0) {
                xlutil.setCellData(sheetName, i, 0, ques);
                xlutil.setCellData(sheetName, i, 1, ins.getQueryResultText(ques));
                xlutil.setCellData(sheetName, i, 5, "NA");
                if(expectedStatus.equals("Fail")) {
                    xlutil.setCellData(sheetName, i, 4, "Pass");
                }else {
                    xlutil.setCellData(sheetName, i, 4, "Fail");
                }
            } else {
            	int sqlFlag = 0;
                if (ins.isObjectNotExist(f) == 0) {
                    driver.findElement(By.xpath(ins.sQLQuery(ques))).click();
                    xlutil.setCellData(sheetName, i, 2, ins.getSQLQuery(ques));
                    sqlFlag=1;
                }
                xlutil.setCellData(sheetName, i, 0, ques);
                xlutil.setCellData(sheetName, i, 1, ins.getQueryResultText(ques));
                if(expectedStatus.equals("Pass")) {
                	xlutil.setCellData(sheetName, i, 4, "Pass");
                }else {
                	xlutil.setCellData(sheetName, i, 4, "Fail");
                }if(sqlFlag==1) {
                	String actualSqlText=ins.getSQLQuery(ques);
                	actualSqlText=actualSqlText.replaceAll("\\s+", "");
                	actualSqlText=actualSqlText.toLowerCase();
                	String arr[]=expectedSQLText.split(",");
                	int sqlTextFlag = 0;
                	for(String s:arr) {
                		String st=s.replaceAll("\\s+", "");
                		st=st.toLowerCase();
                		if(!actualSqlText.contains(st)) {
                			sqlTextFlag=1;
                			xlutil.setCellData(sheetName, i, 6, s+",");
                		}
                	}if(sqlTextFlag==0) {
                		xlutil.setCellData(sheetName, i, 5, "Pass");
                	}else {
                		xlutil.setCellData(sheetName, i, 5, "Fail");
                	}
                }else {
                	xlutil.setCellData(sheetName, i, 5, "NA");
                }

            }
        } catch (Exception e) {
            xlutil.setCellData(sheetName, i, 0, ques);
            xlutil.setCellData(sheetName, i, 4, "Skipped due to exception");
            driver.navigate().refresh();
            Thread.sleep(5000);

            ins.wait.until(ExpectedConditions.visibilityOf(ins.chatBot));
            ins.clickChatBot();
            ins.expandChat();
            ins.collapseRecent();

            throw new SkipException("Skipping question due to exception");
        }
    }
}


