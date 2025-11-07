package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.google.common.collect.Table.Cell;
import com.microsoft.schemas.office.visio.x2012.main.CellType;
import com.opencsv.CSVReader;

import Utilities.excelUtility;
import pageObjects.basePage;
import pageObjects.testLogin;
import testBase.baseClass;

 public class test {
	int rowIndex=0;
	//String filePath = "C:\\Users\\abhinav.dagar\\Downloads\\Test_Hub.xlsx";
	@Test
    
	public void validateCounts() throws Exception {
		String Email="Testing Campaign";
        String filePath = "C:\\Users\\abhinav.dagar\\Downloads\\Test_Hub.xlsx";
        String SheetName="Test1";
        String dateNTime=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String resultFilePath="C:\\Users\\abhinav.dagar\\Downloads\\Email Campaigns\\EmailCampaignResult"+dateNTime+".xlsx";
        String resultSheetName = "Email_Campaign_Result";
        HashSet<String> uniqueOpenSet = new HashSet<>();     // unique
        HashSet<String> uniqueClickSet = new HashSet<>();    // unique
        List<String> deliveredList = new ArrayList<>();
        List<String> openSet = new ArrayList<>();
        List<String> clickSet = new ArrayList<>();
        // allows duplicates

        excelUtility excel = new excelUtility(filePath,SheetName);
        
     // Load sheet once
     int rowCount = excel.getRowCount();
      
     for (int i = 1; i < rowCount; i++) {
         // Fetch entire row once
         String status = excel.getCellData( i, 10);
         String id = excel.getCellData( i, 0);
      
         if ("Open".equalsIgnoreCase(status)) {
             openSet.add(id);
             uniqueOpenSet.add(id);
         } else if ("Delivered".equalsIgnoreCase(status)) {
             deliveredList.add(id); // allow duplicates
         } else if ("Click".equalsIgnoreCase(status)) {
             clickSet.add(id);
             uniqueClickSet.add(id);
         }
     }excel.close();
     excel.createExcelFile(resultFilePath,resultSheetName);
     excelUtility excel1 = new excelUtility(resultFilePath,resultSheetName);
     excel1.setCellData(0, 0, "Campaign");
     excel1.setCellData(0, 1, "KPI");
     excel1.setCellData(0, 2, "Hubspot");
     excel1.setCellData(0, 3, "PBI");
     excel1.setCellData(0, 4, "Difference");
     excel1.setCellData(0, 5, "% Diff");
     rowIndex++;
     excel1.setCellData(rowIndex, 0, Email);
     excel1.setCellData(rowIndex, 1, "Delivered");
     excel1.setCellData(rowIndex, 2, "");
     excel1.setCellData(rowIndex, 3, String.valueOf(deliveredList.size()));
     excel1.setCellData(rowIndex, 4, "");
     excel1.setCellData(rowIndex, 5, "");
     rowIndex++;
     excel1.setCellData(rowIndex, 0, Email);
     excel1.setCellData(rowIndex, 1, "Open Clicks");
     excel1.setCellData(rowIndex, 2, "");
     excel1.setCellData(rowIndex, 3, String.valueOf(openSet.size()));
     excel1.setCellData(rowIndex, 4, "");
     excel1.setCellData(rowIndex, 5, "");
     rowIndex++;
     excel1.setCellData(rowIndex, 0, Email);
     excel1.setCellData(rowIndex, 1, "Clicks");
     excel1.setCellData(rowIndex, 2, "");
     excel1.setCellData(rowIndex, 3, String.valueOf(clickSet.size()));
     excel1.setCellData(rowIndex, 4, "");
     excel1.setCellData(rowIndex, 5, "");
     rowIndex++;
     excel1.setCellData(rowIndex, 0, Email);
     excel1.setCellData(rowIndex, 1, "Unique Opens");
     excel1.setCellData(rowIndex, 2, "");
     excel1.setCellData(rowIndex, 3, String.valueOf(uniqueOpenSet.size()));
     excel1.setCellData(rowIndex, 4, "");
     excel1.setCellData(rowIndex, 5, "");
     rowIndex++;
     excel1.setCellData(rowIndex, 0, Email);
     excel1.setCellData(rowIndex, 1, "Unique Clicks");
     excel1.setCellData(rowIndex, 2, "");
     excel1.setCellData(rowIndex, 3, String.valueOf(uniqueClickSet.size()));
     excel1.setCellData(rowIndex, 4, "");
     excel1.setCellData(rowIndex, 5, "");
     rowIndex++;
     
     sendMailViaOutlook("anirudh.shekhawat@polestarllp.com","Automation Result","I am sharing the automation result excel file",resultFilePath);
	}
     
     
	public static void sendMailViaOutlook(String to, String subject, String body, String attachmentPath) {
        try {
            // Build PowerShell script
            String psScript =
                "$Outlook = New-Object -ComObject Outlook.Application;" +
                "$Mail = $Outlook.CreateItem(0);" +
                "$Mail.To = '" + to + "';" +
                "$Mail.Subject = '" + subject + "';" +
                "$Mail.Body = '" + body + "';" +
                (attachmentPath != null && !attachmentPath.isEmpty()
                        ? "$Mail.Attachments.Add('" + attachmentPath.replace("\\", "\\\\") + "');"
                        : "") +
                "$Mail.Send();";

            // Save PowerShell script
            File tempScript = File.createTempFile("outlookMail", ".ps1");
            try (FileWriter writer = new FileWriter(tempScript)) {
                writer.write(psScript);
            }

            // Use ProcessBuilder to run PowerShell
            ProcessBuilder pb = new ProcessBuilder(
                "powershell", "-ExecutionPolicy", "Bypass", "-File", tempScript.getAbsolutePath()
            );
            pb.inheritIO(); // (optional) show PowerShell output/errors in console
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("✅ Email sent successfully via Outlook");
            } else {
                System.err.println("❌ Failed to send email. Exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
     
     
     
     
     
     
     
     
     
    }
}