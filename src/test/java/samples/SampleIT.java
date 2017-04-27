package samples;

import static org.testng.Assert.assertTrue;

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
    public Object[][] DataSetOptions() {
        return new Object[][] { new Object[] { "python" }, new Object[] { "perl" }, new Object[] { "bash" } };
    }

    @Test(groups = { "sample" }, description = "A sample test to check a title")
    public void sampleTest() throws Exception {
        // obtain our logger
        TestOutput output = this.output.get();
        // perform some actions
        output.compareTitle("Google");
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check a title")
    public void sampleNegativeTest() throws Exception {
        // obtain our logger
        TestOutput output = this.output.get();
        // perform some actions
        output.compareTitle("Yahoo");
        // verify no issues
        assertTrue(output.getErrors() == 1);
        output.endTestTemplateOutputFile();
    }

    @Test(dataProvider = "google search terms", groups = { "sample" },
            description = "A sample test using a data provider to perform searches")
    public void sampleTestWDataProvider(String searchTerm) throws Exception {
        // obtain our browser instance
        SeleniumHelper selHelper = this.selHelper.get();
        // obtain our logger
        TestOutput output = this.output.get();
        // perform some actions
        selHelper.type(Locators.name, "q", searchTerm);
        selHelper.click(Locators.name, "btnG");
        selHelper.waitForElementDisplayed(Locators.id, "resultStats");
        output.compareTitle(searchTerm + " - Google Search");
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check a the goToURL method")
    public void sampleTestGoToURL() throws Exception {
        SeleniumHelper selHelper = this.selHelper.get();
        // perform some actions
        selHelper.goToURL("https://www.yahoo.com/");
        assertTrue(selHelper.getCurrentUrl().equals("https://www.yahoo.com/"));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample test to check a the goToURL method")
    public void sampleTestNegativeGoToURL() throws Exception {
        SeleniumHelper selHelper = this.selHelper.get();
        // perform some actions
        selHelper.goToURL("https://www.yahoo.com/");
        assertTrue(!selHelper.getCurrentUrl().equals("https://www.google.com/"));
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the waitForElementPresent method")
    public void sampleTestWaitForElementPresent() throws Exception {
        SeleniumHelper selHelper = this.selHelper.get();
        // perform some actions
        selHelper.waitForElementPresent(Locators.name, "q");
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the waitForElementNotPresent method")
    public void sampleTestWaitForElementNotPresent() throws Exception {
        SeleniumHelper selHelper = this.selHelper.get();
        // perform some actions
        selHelper.waitForElementNotPresent(Locators.name, "non-existent-name");
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the waitForElementDisplayed method")
    public void sampleTestWaitForElementDisplayed() throws Exception {
        SeleniumHelper selHelper = this.selHelper.get();
        // perform some actions
        selHelper.waitForElementDisplayed(Locators.name, "q");
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check a title")
    public void sampleTestWaitForElementNotDisplayed() throws Exception {
        // obtain our browser instance
        SeleniumHelper selHelper = this.selHelper.get();
        // perform some actions
        selHelper.waitForElementNotDisplayed(Locators.name, "site");
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check a title")
    public void sampleTestWaitForElementEnabled() throws Exception {
        // obtain our browser instance
        SeleniumHelper selHelper = this.selHelper.get();
        // perform some actions
        selHelper.waitForElementEnabled(Locators.name, "q");
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check a title")
    public void sampleTestWaitForNotElementEnabled() throws Exception {
        // obtain our browser instance
        SeleniumHelper selHelper = this.selHelper.get();
        // perform some actions
        selHelper.waitForElementNotEnabled(Locators.name, "oq");
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check a title")
    public void sampleScrollTest() throws Exception {
        // obtain our browser instance
        SeleniumHelper selHelper = this.selHelper.get();
        // perform some actions
        selHelper.type(Locators.name, "q", "scroll");
        selHelper.click(Locators.name, "btnG");
        selHelper.waitForElementDisplayed(Locators.id, "resultStats");
        selHelper.scroll(50);
        // verify no issues
        finish();
    }
}