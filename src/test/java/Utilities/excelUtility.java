package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelUtility {

	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook wb;
	public XSSFSheet ws;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;
	
	public excelUtility(String path) 
	{
		this.path=path;
	}
	
	public int getRowCount(String sheetName) throws IOException{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		int rowcount=ws.getLastRowNum();
		wb.close();
		fi.close();
		return rowcount;
	}
	
	public int getCellCount(String sheetName, int rownum) throws IOException{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		row=ws.getRow(rownum);
		int cellcount=row.getLastCellNum();
		wb.close();
		fi.close();
		return cellcount;
	}
	
	public String getCellData(String sheetName, int rownum,int colnum) throws IOException{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		
		String data;
		try {
			//data=cell.toString();
			DataFormatter formatter=new DataFormatter();  //from poi
			data=formatter.formatCellValue(cell);
		}catch(Exception e) {
			data="";
		}
		wb.close();
		fi.close();
		return data;
	}
	
	public void setCellData(String sheetName, int rownum,int colnum,String data) throws IOException{
		File xlFile=new File(path);
		if(!xlFile.exists()) {
			wb=new XSSFWorkbook();
			fo=new FileOutputStream(path);
			wb.write(fo);
		}
		
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);

		if(wb.getSheetIndex(sheetName)==-1)
			wb.createSheet(sheetName);
		ws=wb.getSheet(sheetName);
		
		if(ws.getRow(rownum)==null)
			ws.createRow(rownum);
		row=ws.getRow(rownum);
		
		cell=row.createCell(colnum);
		cell.setCellValue(data);
		fo=new FileOutputStream(path);
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
	
	//Optional method
	public void fillGreenColor(String sheetName, int rownum,int colnum) throws IOException{
		fi=new FileInputStream(path);
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet(sheetName);
		row=ws.getRow(rownum);
		cell=row.getCell(colnum);
		
		style=wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
	
	//Optional method
	public void fillRedColor(String sheetName, int rownum,int colnum) throws IOException{
		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheetName);

		// Ensure row exists
		row = ws.getRow(rownum);
		if (row == null) {
		    row = ws.createRow(rownum);
		}

		// Ensure cell exists
		cell = row.getCell(colnum);
		if (cell == null) {
		    cell = row.createCell(colnum);
		}

		// Apply style
		style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);

		// Save and close
		fo = new FileOutputStream(path); // Make sure fo is initialized here
		wb.write(fo);
		wb.close();
		fi.close();
		fo.close();
	}
    public static HashMap<String, String> getTestExecutionMap(String path) {
        HashMap<String, String> executionMap = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            for (int i = 1; i <= rows; i++) {
                String testCase = sheet.getRow(i).getCell(0).getStringCellValue();
                String execute = sheet.getRow(i).getCell(1).getStringCellValue();
                executionMap.put(testCase.trim(), execute.trim().toLowerCase());
            }
            wb.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executionMap;
    }
}