package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class SampleTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws IOException {
        // set the base URL for the tests here
        setTestSite("http://172.31.2.65/");
        // set the author of the tests here
        setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion("0.0.1");
    }

    @DataProvider(name = "car list items", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][] { new Object[] { "volvo" }, new Object[] { "saab" }, new Object[] { "mercedes" } };
    }

    @Test(groups = { "sample", "virtual" }, description = "A sample test to check a title")
    public void sampleTest() throws IOException {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform the verification
        asserts.compareTitle("Selenified Test Page");
        // perform the verification
        finish();
    }

    @Test(dataProvider = "car list items", groups = { "sample",
            "virtual" }, description = "A sample test using a data provider to perform searches")
    public void sampleTestWDataProvider(String listItem) throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.select(Locator.ID, "car_list", listItem);
        // close out the test
        finish();
    }

    @Test(groups = { "sample",
            "virtual" }, description = "A sample test to show how to loop through elements with multiple matches")
    public void sampleTestLoopThroughElements() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        Element element = new Element(Locator.XPATH, "//form/input[@type='checkbox']");
        for (int option = 0; option < actions.getElementMatchCount(element); option++) {
            actions.click(element, option);
            asserts.checkElementChecked(element, option);
        }
        // close out the test
        finish();
    }
}
