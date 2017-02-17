package secureci.tests;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.logging.TestOutput;
import tools.selenium.SeleniumHelper.Locators;
import tools.selenium.SeleniumHelper;

public class SampleTests extends TestBase {
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		//set the base URL for the tests here
		testSite = "http://www.google.com/";
		//set the author of the tests here
		author = "Max Saperstone\n<br/>max.saperstone@coveros.com";
		//set the version of the tests or of the software, possibly with a dynamic check
		version = "0.0.1";
	}
	
	@BeforeMethod (alwaysRun = true)
	protected void startTest(Object[] dataProvider, Method method, ITestContext test) throws Exception {
		super.startTest(dataProvider, method, test, true);
	}
	
	@DataProvider( name = "google search terms", parallel = true )
	public Object[][] DataSetOptions(Method method, ITestContext test) {
	        return new Object[][] {
	                new Object[] { "python", method, test },
	                new Object[] { "perl", method, test },
	                new Object[] { "bash", method, test },
	        };
	}

	@Test( groups = { "sample" }, description = "A sample test to check a title" )
	public void sampleTest(Object[] dataProvider, Method method, ITestContext test) throws Exception {
	        String testName = getTestName( method, dataProvider );
	        TestOutput output = (TestOutput) test.getAttribute( testName + "Output" );
	        int errors = (Integer) test.getAttribute( testName + "Errors" );
	        // perform some actions
	        errors += output.compareTitle( "Google" );
	        // finalize our tests
	        genFun.stopTest( output, errors );
	}

	@Test( dataProvider = "google search terms", groups = { "sample" }, 
	                description = "A sample test using a data provider to perform searches" )
	public void sampleTestWDataProvider(String searchTerm, Method method, ITestContext test) throws Exception {
	        String testName = getTestName( method, searchTerm );
	        SeleniumHelper selHelper = (SeleniumHelper) test.getAttribute( testName + "SelHelper" );
	        TestOutput output = (TestOutput) test.getAttribute( testName + "Output" );
	        int errors = (Integer) test.getAttribute( testName + "Errors" );
	        // perform some actions
	        errors += selHelper.type( Locators.name, "q", searchTerm );
	        errors += selHelper.submit( Locators.name, "q" );
	        errors += output.compareTitle( searchTerm + " - Google Search" );
	        // finalize our tests
	        genFun.stopTest( output, errors );
	}
}