package testCases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import Utilities.excelUtility;
import pageObjects.PowerBIEmailPro;
import testBase.baseClass;
import testBase.baseClassTesting;

public class PowerBi extends baseClass{
	PowerBIEmailPro power;
	@Test(priority=1)
	public void loginPowerBI() {
		power = new PowerBIEmailPro(getDriver());
		power.login("data@omniamedcommunications.com", "Hippoc-fawdy4-mekceh");
	}@Test(priority=2)
	public void getEmailDetails() throws InterruptedException, FileNotFoundException, IOException, CsvValidationException {
		power.clickEmailPro();
		Thread.sleep(4000);
		power.clickResetButton();
		power.selectCampaign("A000016_IE_OMC-Services/Internal_Diabetes-emails_DPC-Journal-email_OMC_OMC - DPC 13 Dec");
		Thread.sleep(10000);
		String Delivery=power.getContentValue("Successful deliveries");
		String Opens = power.getContentValue("Total opens");
		String Clicks=power.getContentValue("Total clicks");
		String SheetName ="";
		String filePath = "C:\\Users\\abhinav.dagar\\Downloads\\Test_Hub.csv";

        HashSet<String> openSet = new HashSet<>();     // unique
        HashSet<String> clickSet = new HashSet<>();    // unique
        List<String> deliveredList = new ArrayList<>(); // allows duplicates

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            int row = 0;
            while ((nextLine = reader.readNext()) != null) {
                // Skip header row
                if (row > 0 && nextLine.length > 10) {
                    String status = nextLine[10].trim(); // Column 11 (index 10)
                    String id = nextLine[0];            // Column 1 (index 0)

                    if ("Open".equalsIgnoreCase(status)) {
                        openSet.add(id);
                    } else if ("Delivered".equalsIgnoreCase(status)) {
                        deliveredList.add(id); // keep duplicates
                    } else if ("Click".equalsIgnoreCase(status)) {
                        clickSet.add(id);
                    }
                }
                row++;
            }
        }
        excelUtility excel = new excelUtility(filePath,SheetName);
        for(int i=1;i<excel.getRowCount();i++) {
        	String status = excel.getCellData( i, 10);
        	String id = excel.getCellData( i, 0);
        	if ("Open".equalsIgnoreCase(status)) {
                openSet.add(id);
            } else if ("Delivered".equalsIgnoreCase(status)) {
                deliveredList.add(id); // keep duplicates
            } else if ("Click".equalsIgnoreCase(status)) {
                clickSet.add(id);
            }
        	
        }
        System.out.println("Expected Delivery: "+deliveredList.size()+"Actual Delivery: "+Delivery);
        System.out.println("Expected Click: "+clickSet.size()+"Actual Click: "+Clicks);
        System.out.println("Expected Opens: "+openSet.size()+"Actual Opens: "+Opens);
	}
}
