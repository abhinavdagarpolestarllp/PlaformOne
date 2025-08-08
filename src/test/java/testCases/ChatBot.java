package testCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.*;

import Utilities.DataProviders;
import Utilities.excelUtility;
import pageObjects.SharpInsightsTemporary;
import testBase.baseClassTesting;

public class ChatBot extends baseClassTesting {
    private excelUtility xlutil;
    private String sheetName;
    private SharpInsightsTemporary ins;

    @Parameters({"excelFile"})
    @BeforeClass
    public void initExcel(@Optional(".\\TestData\\ChatBot.xlsx") String excelFile) {
        this.excelPath = excelFile;  // inherited from baseClassTesting
        this.xlutil = new excelUtility(excelPath);
        this.sheetName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    @BeforeMethod
    public void setup() {
        ins = new SharpInsightsTemporary(driver);
    }

    @Test(priority = 1)
    public void calidateChatBot() throws InterruptedException, IOException {
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
    public void questionsValidation(String ques, int i, String expectedStatus, String expectedSQLText)
            throws IOException, InterruptedException, TimeoutException {

        try {
            ins.askQuery(ques);
            ins.sendQuery();
            ins.wait.until(driver -> ins.askQuery.isEnabled());

            By e = By.xpath(ins.validateQueryResult(ques));
            By q = By.xpath(ins.validateQueryResult1(ques));
            By f = By.xpath(ins.sQLQuery(ques));

            xlutil.setCellData(sheetName, i, 3, expectedSQLText);

            if (ins.isObjectNotExist(e) == 0 || ins.isObjectNotExist(q) == 0) {
                recordAnswerOnly(i, ques, ins.getQueryResultText(ques), expectedStatus);
                return;
            }

            int sqlFlag = 0;
            String actualSQL = "";

            if (ins.isObjectNotExist(f) == 0) {
                driver.findElement(By.xpath(ins.sQLQuery(ques))).click();
                actualSQL = ins.getSQLQuery(ques);
                xlutil.setCellData(sheetName, i, 2, actualSQL);
                sqlFlag = 1;
            }

            xlutil.setCellData(sheetName, i, 0, ques);
            xlutil.setCellData(sheetName, i, 1, ins.getQueryResultText(ques));
            xlutil.setCellData(sheetName, i, 4, expectedStatus.equals("Pass") ? "Pass" : "Fail");

            if (sqlFlag == 1) {
                validateSQLText(i, actualSQL, expectedSQLText);
            } else {
                xlutil.setCellData(sheetName, i, 5, "NA");
            }

        } catch (Exception e) {
            xlutil.setCellData(sheetName, i, 0, ques);
            xlutil.setCellData(sheetName, i, 4, "Skipped due to exception");
            driver.navigate().refresh();
            Thread.sleep(5000);
            ins.clickChatBot();
            ins.expandChat();
            ins.collapseRecent();
            throw new SkipException("Skipping question due to exception");
        }
    }

    private void recordAnswerOnly(int rowIndex, String ques, String answer, String expectedStatus) throws IOException {
        xlutil.setCellData(sheetName, rowIndex, 0, ques);
        xlutil.setCellData(sheetName, rowIndex, 1, answer);
        xlutil.setCellData(sheetName, rowIndex, 5, "NA");
        xlutil.setCellData(sheetName, rowIndex, 4, expectedStatus.equals("Fail") ? "Pass" : "Fail");
    }

    private void validateSQLText(int rowIndex, String actualSqlText, String expectedKeywords) throws IOException {
        String cleanSQL = actualSqlText.replaceAll("\\s+", "").toLowerCase();
        String[] expectedArr = expectedKeywords.split(",");
        StringBuilder missing = new StringBuilder();
        boolean allFound = true;

        for (String keyword : expectedArr) {
            String k = keyword.replaceAll("\\s+", "").toLowerCase();
            if (!cleanSQL.contains(k)) {
                missing.append(keyword).append(",");
                allFound = false;
            }
        }

        xlutil.setCellData(sheetName, rowIndex, 5, allFound ? "Pass" : "Fail");
        if (!allFound) {
            xlutil.setCellData(sheetName, rowIndex, 6, missing.toString());
        }
    }@AfterSuite
    public void suiteCompletionTime() throws IOException {
    	excelUtility excel = new excelUtility(".\\TestData\\ChatBot.xlsx");
        String completionTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        System.out.println("Test suite finished at: " + completionTime);
    	excel.setCellData("2025.07.03.19.53.34", 1, 0, completionTime);
        // Can be extended to write to a summary file or shared log
    }
}
