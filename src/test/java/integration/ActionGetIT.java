package integration;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.tools.TestBase;

public class ActionGetIT extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        // set the base URL for the tests here
        setTestSite("http://172.31.2.65/");
        // set the author of the tests here
        setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion("0.0.1");
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getBrowser method")
    public void getBrowserTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        com.coveros.selenified.output.Assert asserts = this.asserts.get();
        // perform some actions
        if (actions.getBrowser().equals("NONE")) {
            asserts.getOutputFile().addError();
        }
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getCapabilities method")
    public void getCapabilitiesTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // use this object to verify the page looks as expected
        com.coveros.selenified.output.Assert asserts = this.asserts.get();
        // perform some actions
        if (actions.getCapabilities().equals(null)) {
            asserts.getOutputFile().addError();
        }
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration test to check the getCookie method")
    public void getCookieTest() throws IOException, ParseException {
        // the cookie date
        String dateval = "2019-12-18T12:00:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Cookie cookie = actions.get().cookie("cookie");
        Assert.assertEquals(cookie, new Cookie("cookie", "cookietest", "/", df.parse(dateval)));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookie method")
    public void negativeGetCookieTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Cookie cookie = actions.get().cookie("badcookie");
        Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration test to check the getCookieValue method")
    public void getCookieValueTest() throws IOException, ParseException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.get().cookieValue("cookie");
        Assert.assertEquals(cookie, "cookietest");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookieValue method")
    public void negativeGetCookieValueTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.get().cookieValue("badcookie");
        Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration test to check the getCookiePath method")
    public void getCookiePathTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.get().cookiePath("cookie");
        Assert.assertEquals(cookie, "/");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookiePath method")
    public void negativeGetCookiePathTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.get().cookiePath("badcookie");
        Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration test to check the getCookieDomain method")
    public void getCookieDomainTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.get().cookieDomain("cookie");
        Assert.assertEquals(cookie, getTestSite().split("/")[2].split(":")[0]);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookieDomain method")
    public void negativeGetCookieDomainTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.get().cookieDomain("badcookie");
        Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration test to check the getCookieExpiration method")
    public void getCookieExpirationTest() throws IOException, ParseException {
        // the cookie date
        String dateval = "2019-12-18T12:00:00";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Date cookie = actions.get().cookieExpiration("cookie");
        Assert.assertEquals(cookie, df.parse(dateval));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookieExpiration method")
    public void negativeGetCookieExpirationTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Date cookie = actions.get().cookieExpiration("badcookie");
        Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectOptions method")
    public void getSelectOptionsTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] options = actions.get().selectOptions(Locator.NAME, "car_list");
        Assert.assertEquals(options, new String[] { "Volvo", "Saab", "Mercedes", "Audi" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectOptions method")
    public void getSelectOptionsNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] options = actions.get().selectOptions(new Element(Locator.NAME, "non-existent-name"));
        Assert.assertEquals(options, new String[] {});
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfSelectOptions method")
    public void getNumOfSelectOptionsTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int options = actions.get().numOfSelectOptions(Locator.NAME, "car_list");
        Assert.assertEquals(options, 4);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfSelectOptions method")
    public void getNumOfSelectOptionsNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int options = actions.get().numOfSelectOptions(new Element(Locator.NAME, "non-existent-name"));
        Assert.assertEquals(options, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfTableRows method")
    public void getNumOfTableRowsTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int rows = actions.get().numOfTableRows(Locator.ID, "table");
        Assert.assertEquals(rows, 7);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfTableRows method")
    public void getNumOfTableRowsNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int rows = actions.get().numOfTableRows(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(rows, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfTableColumns method")
    public void getNumOfTableColumnsTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int columns = actions.get().numOfTableColumns(Locator.ID, "table");
        Assert.assertEquals(columns, 4);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfTableColumns method")
    public void getNumOfTableColumnsNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int columns = actions.get().numOfTableColumns(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(columns, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableRow method")
    public void getTableRowTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> row = actions.get().tableRow(Locator.ID, "table", 1);
        Assert.assertEquals(row.size(), 4);
        Assert.assertEquals(row.get(0).getText(), "President");
        Assert.assertEquals(row.get(1).getText(), "Alfreds Futterkiste");
        Assert.assertEquals(row.get(2).getText(), "Maria Anders");
        Assert.assertEquals(row.get(3).getText(), "Germany");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableRow method")
    public void getTableRowNoRowTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> row = actions.get().tableRow(Locator.ID, "table", 99);
        Assert.assertEquals(row, new ArrayList<>());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableRow method")
    public void getTableRowNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> row = actions.get().tableRow(new Element(Locator.ID, "non-existent-name"), 1);
        Assert.assertEquals(row, new ArrayList<>());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableColumn method")
    public void getTableColumnTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> column = actions.get().tableColumn(Locator.ID, "table", 1);
        Assert.assertEquals(column.size(), 7);
        Assert.assertEquals(column.get(0).getText(), "Company");
        Assert.assertEquals(column.get(1).getText(), "Alfreds Futterkiste");
        Assert.assertEquals(column.get(2).getText(), "Centro comercial Moctezuma");
        Assert.assertEquals(column.get(3).getText(), "Ernst Handel");
        Assert.assertEquals(column.get(4).getText(), "Island Trading");
        Assert.assertEquals(column.get(5).getText(), "Laughing Bacchus Winecellars");
        Assert.assertEquals(column.get(6).getText(), "Magazzini Alimentari Riuniti");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableColumn method")
    public void getTableColumnNoColumnTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> column = actions.get().tableColumn(new Element(Locator.ID, "table"), 99);
        Assert.assertEquals(column, new ArrayList<>());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableColumn method")
    public void getTableColumnNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> column = actions.get().tableColumn(Locator.ID, "non-existent-name", 1);
        Assert.assertEquals(column, new ArrayList<>());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.get().tableCell(Locator.ID, "table", 1, 1);
        Assert.assertEquals(cell.getText(), "Alfreds Futterkiste");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellNoCellWideTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.get().tableCell(Locator.ID, "table", 1, 99);
        Assert.assertNull(cell);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellNoCellLongTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.get().tableCell(Locator.ID, "table", 99, 1);
        Assert.assertNull(cell);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellNoCellTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.get().tableCell(Locator.ID, "table", 99, 99);
        Assert.assertNull(cell);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.get().tableCell(new Element(Locator.ID, "non-existent-name"), 1, 1);
        Assert.assertNull(cell);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedText method")
    public void getSelectedTextTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.get().selectedOption(Locator.ID, "car_list");
        Assert.assertEquals(text, "Volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedText method")
    public void getSelectedTextNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.get().selectedOption(new Element(Locator.ID, "non-existent-name"));
        Assert.assertNull(text);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedText method")
    public void getSelectedTextNotSelectTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.get().selectedOption(Locator.ID, "table");
        Assert.assertNull(text);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedTexts method")
    public void getSelectedTextsTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] text = actions.get().selectedOptions(Locator.ID, "car_list");
        Assert.assertEquals(text, new String[] { "Volvo" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedTexts method")
    public void getSelectedTextsNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] text = actions.get().selectedOptions(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(text, new String[0]);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedTexts method")
    public void getSelectedTextsNotSelectTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] text = actions.get().selectedOptions(Locator.ID, "table");
        Assert.assertEquals(text, new String[0]);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValue method")
    public void getSelectedValueTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String value = actions.get().selectedValue(Locator.ID, "car_list");
        Assert.assertEquals(value, "volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValue method")
    public void getSelectedValueNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String value = actions.get().selectedValue(new Element(Locator.ID, "non-existent-name"));
        Assert.assertNull(value);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValue method")
    public void getSelectedValueNotSelectTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String value = actions.get().selectedValue(Locator.ID, "table");
        Assert.assertNull(value);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValues method")
    public void getSelectedValuesTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] value = actions.get().selectedValues(Locator.ID, "car_list");
        Assert.assertEquals(value, new String[] { "volvo" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValues method")
    public void getSelectedValuesNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] value = actions.get().selectedValues(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(value, new String[0]);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValues method")
    public void getSelectedValuesNotSelectTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] value = actions.get().selectedValues(Locator.ID, "table");
        Assert.assertEquals(value, new String[0]);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getText method")
    public void getTextTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.get().text(Locator.ID, "disable_click");
        Assert.assertEquals(text, "Click me to Disable/Enable a html button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getText method")
    public void getTextNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.get().text(new Element(Locator.ID, "non-existent-name"));
        Assert.assertNull(text);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getValue method")
    public void getValueTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.get().value(Locator.ID, "input_box");
        Assert.assertEquals(text, "");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getValue method")
    public void getValueNotInputTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.get().value(Locator.ID, "disable_click");
        Assert.assertNull(text);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getValue method")
    public void getValueNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.get().value(new Element(Locator.ID, "non-existent-name"));
        Assert.assertNull(text);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getCss method")
    public void getCssTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String css = actions.get().css(Locator.ID, "disable_click", "display");
        Assert.assertEquals(css, "block");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getCss method")
    public void getCssWonkyTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String css = actions.get().css(Locator.ID, "disable_click", "some-bad-css-attribute");
        Assert.assertEquals(css, "");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getCss method")
    public void getCssNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String css = actions.get().css(new Element(Locator.ID, "non-existent-name"), "display");
        Assert.assertNull(css);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getAttribute method")
    public void getAttributeTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String attribute = actions.get().attribute(Locator.ID, "disable_click", "class");
        Assert.assertEquals(attribute, "click");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getAttribute method")
    public void getAttributeWonkyTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String attribute = actions.get().attribute(Locator.ID, "disable_click", "some-bad-attribute");
        Assert.assertNull(attribute);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getAttribute method")
    public void getAttributeNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String attribute = actions.get().attribute(new Element(Locator.ID, "non-existent-name"), "display");
        Assert.assertNull(attribute);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getAllAttribute method")
    public void getAllAttributeTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Map<String, String> attributes = actions.get().allAttributes(Locator.ID, "disable_click");
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "disable_click");
        expected.put("class", "click");
        Assert.assertEquals(attributes, expected);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "A negative integration test to check the getAllAttribute method")
    public void getAllAttributeNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Map<String, String> attributes = actions.get().allAttributes(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(attributes, new HashMap<>());
        // verify 0 issue
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getEval method")
    public void getEvalTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String location = (String) actions.get().eval("document.location");
        Assert.assertNull(location);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getEval method")
    public void getElementEvalTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String location = (String) actions.get().eval(new Element(Locator.ID, "disable_click"), "document.location");
        Assert.assertNull(location);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "A negative integration test to check the getEval method")
    public void getElementEvalNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String location = (String) actions.get().eval(Locator.ID, "non-existent-name", "document.location");
        Assert.assertNull(location);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getPrompt method")
    public void getPromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "prompt_button");
        String prompt = actions.get().prompt();
        Assert.assertEquals(prompt, "What do you think?");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "A integration negative test to check the getPrompt method")
    public void negativeGetPromptTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.get().prompt();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getConfirmation method")
    public void getConfirmationTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "confirm_button");
        String confirm = actions.get().confirmation();
        Assert.assertEquals(confirm, "Is this not great?");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration negative test to check the getConfirmation method")
    public void negativeGetConfirmationTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.get().confirmation();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getAlert method")
    public void getAlertTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        String alert = actions.get().alert();
        Assert.assertEquals(alert, "Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration negative test to check the getAlert method")
    public void negativeGetAlertTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.get().alert();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getHtmlSource method")
    public void getHtmlSourceTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "submit_button", 0));
        String source = actions.get().htmlSource();
        Assert.assertTrue(source.contains("You're on the next page"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getElementMatchCount method")
    public void getElementMatchCountTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertEquals(actions.get().matchCount(new Element(Locator.ID, "submit_button")), 1);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getElementMatchCount method")
    public void getElementMatchCountMultipleTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertEquals(actions.get().matchCount(new Element(Locator.CLASSNAME, "overlay")), 3);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration negative test to check the getElementMatchCount method")
    public void getElementMatchCountNotExistTest() {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Assert.assertEquals(actions.get().matchCount(new Element(Locator.ID, "non-existent-name")), 0);
        // verify no issues
        finish();
    }
}