package testCases;

import java.util.Collections;

import org.testng.TestNG;

public class TestNGExecutionMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String suitePath = (args != null && args.length > 0) ? args[0] : "SanityTest.xml";
	        TestNG testng = new TestNG();
	        testng.setTestSuites(Collections.singletonList(suitePath));
	        testng.run();

	}

}
