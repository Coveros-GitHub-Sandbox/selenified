package samples;

import static org.testng.Assert.assertTrue;
import java.util.Arrays;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Action.Locators;

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
     // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform our verification
        asserts.compareTitle("Google");
        // perform our verification
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample negative test to check a title")
    public void sampleNegativeTest() throws Exception {
        // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareTitle("Yahoo");
        // perform our verification
        assertTrue(asserts.getOutputFile().getErrors() == 1);
        finishNoAssert();
    }

    @Test(dataProvider = "google search terms", groups = { "sample" },
            description = "A sample test using a data provider to perform searches")
    public void sampleTestWDataProvider(String searchTerm) throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.type(Locators.name, "q", searchTerm);
        actions.click(Locators.name, "btnG");
        actions.waitForElementDisplayed(Locators.id, "resultStats");
        // perform our verification
        asserts.compareTitle(searchTerm + " - Google Search");
        // close out the test
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the goToURL method")
    public void sampleTestGoToURL() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("https://www.yahoo.com/");
        assertTrue(actions.getCurrentUrl().equals("https://www.yahoo.com/"));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the goToURL method")
    public void sampleTestNegativeGoToURL() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("https://www.yahoo.com/");
        assertTrue(!actions.getCurrentUrl().equals("https://www.google.com/"));
        // verify no issues
        finish();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the waitForElementPresent method")
    public void sampleTestWaitForElementPresent() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementPresent(Locators.name, "q");
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the waitForElementPresent method")
    public void sampleTestNegativeWaitForElementPresent() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.waitForElementPresent(Locators.name, "non-existent-name");
        // verify no issues
        assertTrue(asserts.getOutputFile().getErrors() == 1);
        finishNoAssert();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the waitForElementNotPresent method")
    public void sampleTestWaitForElementNotPresent() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementNotPresent(Locators.name, "non-existent-name");
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the waitForElementNotPresent method")
    public void sampleTestNegativeWaitForElementNotPresent() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.waitForElementNotPresent(Locators.name, "q");
        // verify no issues
        assertTrue(asserts.getOutputFile().getErrors() == 1);
        finishNoAssert();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the waitForElementDisplayed method")
    public void sampleTestWaitForElementDisplayed() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementDisplayed(Locators.name, "q");
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the waitForElementDisplayed method")
    public void sampleTestNegativeWaitForElementDisplayed() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.waitForElementDisplayed(Locators.name, "non-existent-name");
        // verify no issues
        assertTrue(asserts.getOutputFile().getErrors() == 1);
        finishNoAssert();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the WaitForElementNotDisplayed method")
    public void sampleTestWaitForElementNotDisplayed() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementNotDisplayed(Locators.name, "site");
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the WaitForElementNotDisplayed method")
    public void sampleTestNegativeWaitForElementNotDisplayed() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.waitForElementNotDisplayed(Locators.name, "q");
        // verify no issues
        assertTrue(asserts.getOutputFile().getErrors() == 1);
        finishNoAssert();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the WaitForElementEnabled method")
    public void sampleTestWaitForElementEnabled() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementEnabled(Locators.name, "q");
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the WaitForElementEnabled method")
    public void sampleTestNegativeWaitForElementEnabled() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.waitForElementEnabled(Locators.id, "gs_taif0");
        // verify no issues
        assertTrue(asserts.getOutputFile().getErrors() == 1);
        finishNoAssert();
    }

    @Test(groups = { "sample" }, description = "A sample test to check the WaitForElementNotEnabled method")
    public void sampleTestWaitForNotElementEnabled() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.waitForElementNotEnabled(Locators.name, "oq");
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the WaitForElementNotEnabled method")
    public void sampleTestNegativeWaitForNotElementEnabled() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.waitForElementNotEnabled(Locators.name, "q");
        // verify no issues
        assertTrue(asserts.getOutputFile().getErrors() == 1);
        finishNoAssert();
    }
    
    @Test(groups = { "sample" }, description = "A sample test to check the getNumOfSelectOptions method")
    public void sampleGetNumOfSelectOptionsTest() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.yahoo.com/");
        assertTrue(actions.getNumOfSelectOptions(Locators.name, "league") == 3);
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the getNumOfSelectOptions method")
    public void sampleNegativeGetNumOfSelectOptionsTest() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.yahoo.com/");
        assertTrue(!(actions.getNumOfSelectOptions(Locators.name, "league") == 0));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample test to check the getSelectOptions method")
    public void sampleGetSelectOptionsTest() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.yahoo.com/");
        assertTrue(Arrays.equals(actions.getSelectOptions(Locators.name, "league"),new String[]{"nba", "nhl", "mlb"}));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample test to check the getTableRows method")
    public void sampleGetTableRows() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.seleniumhq.org/");
        assertTrue(actions.getTableRows(Locators.id, "choice").size() == 1);
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the getTableRows method")
    public void sampleNegativeGetTableRows() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.seleniumhq.org/");
        assertTrue(!(actions.getTableRows(Locators.id, "choice").size() == 0));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample test to check the getNumOfTableRows method")
    public void sampleGetNumOfTableRows() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.seleniumhq.org/");
        assertTrue(actions.getNumOfTableRows(Locators.id, "choice") == 1);
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the getNumOfTableRows method")
    public void sampleNegativeGetNumOfTableRows() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.seleniumhq.org/");
        assertTrue(!(actions.getNumOfTableRows(Locators.id, "choice") == 0));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample1" }, description = "A sample test to check the getTableColumns method")
    public void sampleGetTableColumns() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.seleniumhq.org/");
        assertTrue(actions.getTableColumns(Locators.id, "choice").size() == 2);
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample1" }, description = "A sample negative test to check the getTableColumns method")
    public void sampleNegativeGetTableColumns() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.seleniumhq.org/");
        assertTrue(!(actions.getTableColumns(Locators.id, "choice").size() == 0));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample1" }, description = "A sample test to check the getNumOfTableColumns method")
    public void sampleGetNumOfTableColumns() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.seleniumhq.org/");
        assertTrue(actions.getNumOfTableColumns(Locators.id, "choice") == 2);
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample1" }, description = "A sample negative test to check the getNumOfTableColumns method")
    public void sampleNegativeGetNumOfTableColumns() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.goToURL("http://www.seleniumhq.org/");
        assertTrue(!(actions.getNumOfTableColumns(Locators.id, "choice") == 0));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample test to check the scroll method")
    public void sampleScrollTest() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // perform some actions
        actions.type(Locators.name, "q", "scroll");
        actions.click(Locators.name, "btnG");
        actions.waitForElementDisplayed(Locators.id, "resultStats");
        actions.scroll(50);
        // verify no issues
        finish();
    }
    
    @Test(groups = { "sample" }, description = "A sample negative test to check the scroll method")
    public void sampleNegativeScrollTest() throws Exception {
        // use this object to manipulate our page
        Action actions = this.actions.get();
        // use this object to verify our page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        actions.scroll(50);
        // verify no issues
        assertTrue(asserts.getOutputFile().getErrors() == 1);
        finishNoAssert();
    }
}