package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ElementIT extends WebBase {

    @Test(groups = {"integration", "element"}, description = "A negative integration test to try to crash defineByElement",
            expectedExceptions = NullPointerException.class)
    public void checkNullLocatorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(null, "table");
        table.getWebElement();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that a child element is properly located")
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

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that a child element is properly located")
    public void checkSingleChildTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.TAGNAME, "body");
        Element cell = table.findChild(app.newElement(Locator.TAGNAME, "button", 3));
        cell.assertEquals().text("");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that a child element is properly located")
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
        Element cell3 = row.findChild(app.newElement(Locator.TAGNAME, "td", 2));
        cell3.assertEquals().text("Germany");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be specified with a parent element")
    public void checkParentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        Element cell = app.newElement(Locator.TAGNAME, "th", table);
        cell.assertEquals().text("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be specified with a parent element")
    public void checkMultipleParentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        Element body = app.newElement(Locator.TAGNAME, "tbody", table);
        Element row = app.newElement(Locator.TAGNAME, "tr", body);
        Element cell1 = app.newElement(Locator.TAGNAME, "th", row);
        cell1.assertEquals().text("President");
        Element cell2 = app.newElement(Locator.TAGNAME, "td", row);
        cell2.assertEquals().text("Alfreds Futterkiste");
        Element cell3 = app.newElement(Locator.TAGNAME, "td", 2, row);
        cell3.assertEquals().text("Germany");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved")
    public void getWebElementsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        List<WebElement> webElements = table.getWebElements();
        assertEquals(webElements.size(), 1);
        assertTrue(webElements.get(0).isDisplayed());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved")
    public void getWebElementsNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "no-such-table");
        List<WebElement> webElements = table.getWebElements();
        assertTrue(webElements.isEmpty());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved")
    public void getMultipleWebElementsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.TAGNAME, "th");
        List<WebElement> webElements = table.getWebElements();
        assertEquals(webElements.size(), 12);
        assertEquals(webElements.get(0).getText(), "");
        assertEquals(webElements.get(1).getText(), "Company");
        assertEquals(webElements.get(2).getText(), "Contact");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved")
    public void getMultipleWebElementsParentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        Element cell = app.newElement(Locator.TAGNAME, "th", table);
        List<WebElement> webElements = cell.getWebElements();
        assertEquals(webElements.size(), 10);
        assertEquals(webElements.get(0).getText(), "");
        assertEquals(webElements.get(1).getText(), "Company");
        assertEquals(webElements.get(2).getText(), "Contact");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved")
    public void getWebElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        WebElement webElement = table.getWebElement();
        assertTrue(webElement.isDisplayed());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved",
            expectedExceptions = NoSuchElementException.class)
    public void getWebElementNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "no-such-table");
        table.getWebElement();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved")
    public void getMultipleWebElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.TAGNAME, "th");
        WebElement webElement = table.getWebElement();
        assertEquals(webElement.getText(), "");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved")
    public void getMultipleWebElementParentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        Element cell = app.newElement(Locator.TAGNAME, "th", table);
        WebElement webElement = cell.getWebElement();
        assertEquals(webElement.getText(), "");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved",
            expectedExceptions = NoSuchElementException.class)
    public void getWebElementParentNotExistTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "no-such-table");
        Element cell = app.newElement(Locator.TAGNAME, "th", table);
        cell.getWebElement();
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "element"},
            description = "An integration test to check that an element can be retrieved",
            expectedExceptions = NoSuchElementException.class)
    public void getWebElementNotExistParentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        Element cell = app.newElement(Locator.TAGNAME, "tw", table);
        cell.getWebElement();
        // verify no issues
        finish();
    }
}