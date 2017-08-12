package sample;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.App;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.selenium.element.Element;
import com.coveros.selenified.tools.Selenified;

public class SimpleSampleIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://172.31.2.65/");
        // set the author of the tests here
        setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion("0.0.1");
    }

    @DataProvider(name = "car list items", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][] { new Object[] { "Volvo" }, new Object[] { "Saab" }, new Object[] { "Mercedes" } };
    }

    @Test(groups = { "sample", "virtual" }, description = "A sample test to check a title")
    public void sampleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().titleEquals("Selenified Test Page");
        // perform the verification
        finish();
    }

    @Test(dataProvider = "car list items", groups = { "sample",
            "virtual" }, description = "A sample test using a data provider to perform searches")
    public void sampleTestWDataProvider(String listItem) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").select(listItem);
        // close out the test
        finish();
    }

    @Test(groups = { "sample",
            "virtual" }, description = "A sample test to show how to loop through elements with multiple matches")
    public void sampleTestLoopThroughElements() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element element = app.newElement(Locator.XPATH, "//form/input[@type='checkbox']");
        for (int match = 0; match < element.get().matchCount(); match++) {
            element.setMatch(match);
            element.click();
            element.assertState().checked();
        }
        // close out the test
        finish();
    }
}
