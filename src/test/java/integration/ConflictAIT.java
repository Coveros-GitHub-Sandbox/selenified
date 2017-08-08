package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.TestBase;

public class ConflictAIT extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        // set the base URL for the tests here
        setTestSite("http://172.31.2.65/");
        // set the author of the tests here
        setAuthor("Matt Grasberger\n<br/>matthew.grasberger@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion("0.0.1");
    }

    @Test(groups = { "integration", "conflict", "virtual" },
            description = "A sample test to show how to loop through elements with multiple matches")
    public void conflictingTestName() {
        System.setProperty("packageResults", "true");
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
        System.setProperty("packageResults", "false");
    }
}