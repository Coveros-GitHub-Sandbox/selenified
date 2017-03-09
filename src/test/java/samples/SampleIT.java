package samples;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.logging.TestOutput;
import tools.selenium.SeleniumHelper.Locators;
import tools.selenium.SeleniumHelper;

public class SampleIT extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws Exception {
        // set the base URL for the tests here
        testSite = "http://www.google.com/";
        // set the author of the tests here
        author = "Max Saperstone\n<br/>max.saperstone@coveros.com";
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        version = "0.0.1";
    }

    @DataProvider(name = "google search terms", parallel = true)
    public Object[][] DataSetOptions(Method method, ITestContext test) {
        return new Object[][] { new Object[] { "python", method, test }, new Object[] { "perl", method, test },
                new Object[] { "bash", method, test }, };
    }

    @Test(groups = { "sample" }, description = "A sample test to check a title")
    public void sampleTest(Object[] dataProvider, Method method, ITestContext test) throws Exception {
        TestOutput output = getTestOutput(method, test, dataProvider);
        // perform some actions
        output.compareTitle("Google");
        // verify no issues
        finalize(output);
    }

    @Test(dataProvider = "google search terms", groups = {
            "sample" }, description = "A sample test using a data provider to perform searches")
    public void sampleTestWDataProvider(String searchTerm, Method method, ITestContext test) throws Exception {
        SeleniumHelper selHelper = getSelHelper(method, test, searchTerm);
        TestOutput output = getTestOutput(method, test, searchTerm);
        // perform some actions
        selHelper.type(Locators.name, "q", searchTerm);
        selHelper.click(Locators.name, "btnG");
        selHelper.waitForElementDisplayed(Locators.id, "resultStats");
        output.compareTitle(searchTerm + " - Google Search");
        // verify no issues
        finalize(output);
    }
}