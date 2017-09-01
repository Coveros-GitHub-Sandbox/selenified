package integration;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.Selenified;
import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;

public class ElementIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://172.31.2.65/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @Test(groups = { "integration",
            "element" }, description = "An integration test to check that a child element is properly located")
    public void checkChildTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        Element cell = table.findChild(app.newElement(Locator.TAGNAME, "th"));
        cell.assertEquals().text("");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration",
            "element" }, description = "An integration test to check that a child element is properly located")
    public void checkMultipleChildTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        Element body = table.findChild(app.newElement(Locator.TAGNAME, "tbody"));
        Element row = body.findChild(app.newElement(Locator.TAGNAME, "tr"));
        Element cell1 = row.findChild(app.newElement(Locator.TAGNAME, "th"));
        cell1.assertEquals().text("President");
        Element cell2 = row.findChild(app.newElement(Locator.TAGNAME, "td"));
        cell2.assertEquals().text("Alfreds Futterkiste");
        // verify no issues
        finish();
    }
}