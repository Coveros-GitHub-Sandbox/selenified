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
    public void beforeClass() throws IOException {
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
    public void getBrowserTest() throws IOException {
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
    public void getCapabilitiesTest() throws IOException {
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
        Cookie cookie = actions.getCookie("cookie");
        Assert.assertEquals(cookie, new Cookie("cookie", "cookietest", "/", df.parse(dateval)));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookie method")
    public void negativeGetCookieTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Cookie cookie = actions.getCookie("badcookie");
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
        String cookie = actions.getCookieValue("cookie");
        Assert.assertEquals(cookie, "cookietest");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookieValue method")
    public void negativeGetCookieValueTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.getCookieValue("badcookie");
        Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration test to check the getCookiePath method")
    public void getCookiePathTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.getCookiePath("cookie");
        Assert.assertEquals(cookie, "/");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookiePath method")
    public void negativeGetCookiePathTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.getCookiePath("badcookie");
        Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration test to check the getCookieDomain method")
    public void getCookieDomainTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.getCookieDomain("cookie");
        Assert.assertEquals(cookie, getTestSite().split("/")[2].split(":")[0]);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookieDomain method")
    public void negativeGetCookieDomainTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String cookie = actions.getCookieDomain("badcookie");
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
        Date cookie = actions.getCookieExpiration("cookie");
        Assert.assertEquals(cookie, df.parse(dateval));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get", "cookie",
            "virtual" }, description = "An integration negative test to check the getCookieExpiration method")
    public void negativeGetCookieExpirationTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Date cookie = actions.getCookieExpiration("badcookie");
        Assert.assertNull(cookie);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectOptions method")
    public void getSelectOptionsTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] options = actions.getSelectOptions(Locator.NAME, "car_list");
        Assert.assertEquals(options, new String[] { "volvo", "saab", "mercedes", "audi" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectOptions method")
    public void getSelectOptionsNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] options = actions.getSelectOptions(new Element(Locator.NAME, "non-existent-name"));
        Assert.assertEquals(options, new String[] {});
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfSelectOptions method")
    public void getNumOfSelectOptionsTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int options = actions.getNumOfSelectOptions(Locator.NAME, "car_list");
        Assert.assertEquals(options, 4);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfSelectOptions method")
    public void getNumOfSelectOptionsNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int options = actions.getNumOfSelectOptions(new Element(Locator.NAME, "non-existent-name"));
        Assert.assertEquals(options, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfTableRows method")
    public void getNumOfTableRowsTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int rows = actions.getNumOfTableRows(Locator.ID, "table");
        Assert.assertEquals(rows, 7);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfTableRows method")
    public void getNumOfTableRowsNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int rows = actions.getNumOfTableRows(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(rows, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfTableColumns method")
    public void getNumOfTableColumnsTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int columns = actions.getNumOfTableColumns(Locator.ID, "table");
        Assert.assertEquals(columns, 4);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getNumOfTableColumns method")
    public void getNumOfTableColumnsNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        int columns = actions.getNumOfTableColumns(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(columns, 0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableRow method")
    public void getTableRowTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> row = actions.getTableRow(Locator.ID, "table", 1);
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
    public void getTableRowNoRowTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> row = actions.getTableRow(Locator.ID, "table", 99);
        Assert.assertEquals(row, new ArrayList<>());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableRow method")
    public void getTableRowNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> row = actions.getTableRow(new Element(Locator.ID, "non-existent-name"), 1);
        Assert.assertEquals(row, new ArrayList<>());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableColumn method")
    public void getTableColumnTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> column = actions.getTableColumn(Locator.ID, "table", 1);
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
    public void getTableColumnNoColumnTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> column = actions.getTableColumn(new Element(Locator.ID, "table"), 99);
        Assert.assertEquals(column, new ArrayList<>());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableColumn method")
    public void getTableColumnNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        List<WebElement> column = actions.getTableColumn(Locator.ID, "non-existent-name", 1);
        Assert.assertEquals(column, new ArrayList<>());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.getTableCell(Locator.ID, "table", 1, 1);
        Assert.assertEquals(cell.getText(), "Alfreds Futterkiste");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellNoCellWideTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.getTableCell(Locator.ID, "table", 1, 99);
        Assert.assertNull(cell);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellNoCellLongTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.getTableCell(Locator.ID, "table", 99, 1);
        Assert.assertNull(cell);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellNoCellTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.getTableCell(Locator.ID, "table", 99, 99);
        Assert.assertNull(cell);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getTableCell method")
    public void getTableCellNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        WebElement cell = actions.getTableCell(new Element(Locator.ID, "non-existent-name"), 1, 1);
        Assert.assertNull(cell);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedText method")
    public void getSelectedTextTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.getSelectedText(Locator.ID, "car_list");
        Assert.assertEquals(text, "Volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedText method")
    public void getSelectedTextNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.getSelectedText(new Element(Locator.ID, "non-existent-name"));
        Assert.assertNull(text);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedText method")
    public void getSelectedTextNotSelectTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.getSelectedText(Locator.ID, "table");
        Assert.assertNull(text);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedTexts method")
    public void getSelectedTextsTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] text = actions.getSelectedTexts(Locator.ID, "car_list");
        Assert.assertEquals(text, new String[] { "Volvo" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedTexts method")
    public void getSelectedTextsNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] text = actions.getSelectedTexts(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(text, new String[0]);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedTexts method")
    public void getSelectedTextsNotSelectTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] text = actions.getSelectedTexts(Locator.ID, "table");
        Assert.assertEquals(text, new String[0]);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValue method")
    public void getSelectedValueTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String value = actions.getSelectedValue(Locator.ID, "car_list");
        Assert.assertEquals(value, "volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValue method")
    public void getSelectedValueNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String value = actions.getSelectedValue(new Element(Locator.ID, "non-existent-name"));
        Assert.assertNull(value);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValue method")
    public void getSelectedValueNotSelectTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String value = actions.getSelectedValue(Locator.ID, "table");
        Assert.assertNull(value);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValues method")
    public void getSelectedValuesTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] value = actions.getSelectedValues(Locator.ID, "car_list");
        Assert.assertEquals(value, new String[] { "volvo" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValues method")
    public void getSelectedValuesNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] value = actions.getSelectedValues(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(value, new String[0]);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getSelectedValues method")
    public void getSelectedValuesNotSelectTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String[] value = actions.getSelectedValues(Locator.ID, "table");
        Assert.assertEquals(value, new String[0]);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getText method")
    public void getTextTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.getText(Locator.ID, "disable_click");
        Assert.assertEquals(text, "Click me to Disable/Enable a html button");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getText method")
    public void getTextNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.getText(new Element(Locator.ID, "non-existent-name"));
        Assert.assertNull(text);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getValue method")
    public void getValueTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.getValue(Locator.ID, "input_box");
        Assert.assertEquals(text, "");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getValue method")
    public void getValueNotInputTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.getValue(Locator.ID, "disable_click");
        Assert.assertNull(text);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getValue method")
    public void getValueNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String text = actions.getValue(new Element(Locator.ID, "non-existent-name"));
        Assert.assertNull(text);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getCss method")
    public void getCssTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String css = actions.getCss(Locator.ID, "disable_click", "display");
        Assert.assertEquals(css, "block");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getCss method")
    public void getCssWonkyTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String css = actions.getCss(Locator.ID, "disable_click", "some-bad-css-attribute");
        Assert.assertEquals(css, "");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getCss method")
    public void getCssNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String css = actions.getCss(new Element(Locator.ID, "non-existent-name"), "display");
        Assert.assertNull(css);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getAttribute method")
    public void getAttributeTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String attribute = actions.getAttribute(Locator.ID, "disable_click", "class");
        Assert.assertEquals(attribute, "click");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getAttribute method")
    public void getAttributeWonkyTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String attribute = actions.getAttribute(Locator.ID, "disable_click", "some-bad-attribute");
        Assert.assertNull(attribute);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getAttribute method")
    public void getAttributeNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String attribute = actions.getAttribute(new Element(Locator.ID, "non-existent-name"), "display");
        Assert.assertNull(attribute);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getAllAttribute method")
    public void getAllAttributeTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Map<String, String> attributes = actions.getAllAttributes(Locator.ID, "disable_click");
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "disable_click");
        expected.put("class", "click");
        Assert.assertEquals(attributes, expected);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get",
            "virtual" }, description = "An integration test to check the getAllAttribute method")
    public void getAllAttributeNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        Map<String, String> attributes = actions.getAllAttributes(new Element(Locator.ID, "non-existent-name"));
        Assert.assertEquals(attributes, new HashMap<>());
        // verify 0 issue
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getEval method")
    public void getEvalTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String location = (String) actions.getEval("document.location");
        Assert.assertNull(location);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getEval method")
    public void getElementEvalTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String location = (String) actions.getEval(new Element(Locator.ID, "disable_click"), "document.location");
        Assert.assertNull(location);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "get" }, description = "An integration test to check the getEval method")
    public void getElementEvalNotExistTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        String location = (String) actions.getEval(Locator.ID, "non-existent-name", "document.location");
        Assert.assertNull(location);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getPrompt method")
    public void getPromptTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "prompt_button");
        String prompt = actions.getPrompt();
        Assert.assertEquals(prompt, "What do you think?");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "A integration negative test to check the getPrompt method")
    public void negativeGetPromptTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.getPrompt();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getConfirmation method")
    public void getConfirmationTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "confirm_button");
        String confirm = actions.getConfirmation();
        Assert.assertEquals(confirm, "Is this not great?");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration negative test to check the getConfirmation method")
    public void negativeGetConfirmationTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.getConfirmation();
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration test to check the getAlert method")
    public void getAlertTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(Locator.ID, "disable_click");
        actions.click(Locator.ID, "alert_button");
        String alert = actions.getAlert();
        Assert.assertEquals(alert, "Enabled!");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "get" }, description = "An integration negative test to check the getAlert method")
    public void negativeGetAlertTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.getAlert();
        // verify 1 issue
        finish(1);
    }
    
    @Test(groups = { "integration", "actions",
    "get" }, description = "An integration test to check the getHtmlSthece method")
    public void getHtmlStheceTest() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // perform some actions
        actions.click(new Element(Locator.ID, "submit_button"), 0);
        String source = actions.getHtmlSource();
        Assert.assertTrue(source.contains( "You're on the next page"));
        // verify no issues
        finish();
    }
}