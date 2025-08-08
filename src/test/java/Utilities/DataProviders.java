package Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "ChatBotQuestions")
    public Object[][] chatBotDataProvider(ITestContext context) throws IOException {
        // Get Excel file path from TestNG parameter
        String excelPath = context.getCurrentXmlTest().getParameter("excelFile");

        if (excelPath == null || excelPath.isEmpty()) {
            excelPath = ".\\TestData\\ChatBot.xlsx"; // Default path
        }

        excelUtility xlutil = new excelUtility(excelPath);
        int totalRows = xlutil.getRowCount("Sheet1");

        List<Object[]> data = new ArrayList<>();
        int index = 1;

        for (int i = 1; i <= totalRows; i++) {
            String flag = xlutil.getCellData("Sheet1", i, 0);
            if ("Yes".equalsIgnoreCase(flag.trim())) {
                String question = xlutil.getCellData("Sheet1", i, 1);
                String expectedStatus = xlutil.getCellData("Sheet1", i, 2);
                String expectedSQLText = xlutil.getCellData("Sheet1", i, 3);
                data.add(new Object[]{question, index++, expectedStatus, expectedSQLText});
            }
        }

        return data.toArray(new Object[0][0]);
    }
}
