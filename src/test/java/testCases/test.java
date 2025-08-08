package testCases;
import java.io.IOException;

import java.util.Map;



import Utilities.excelUtility;
public class test {

	public static void main(String[] args) throws IOException {
		Map<String, String> testRunMap =excelUtility.getTestExecutionMap(".\\testData\\Driver.xlsx");
		for(String s : testRunMap.keySet()) {
			System.out.println(s+","+testRunMap.get(s));
		}
	}
	
}
;