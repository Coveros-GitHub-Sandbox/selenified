package unit;

import com.coveros.selenified.Locator;
import com.coveros.selenified.element.Element;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ElementTest {

    @Test
    public void checkElementTypeTest() {
        Element element = new Element(null, null, Locator.ID, "myId");
        Assert.assertEquals(element.getType(), Locator.ID);
    }

    @Test
    public void checkElementLocatorTest() {
        Element element = new Element(null, null, Locator.ID, "myId");
        Assert.assertEquals(element.getLocator(), "myId");
    }

    @Test
    public void checkElementMatchTest() {
        Element element = new Element(null, null, Locator.ID, "myId");
        Assert.assertEquals(element.getMatch(), 0);
    }

    @Test
    public void checkElementWebDriverTest() {
        Element element = new Element(null, null, Locator.ID, "myId");
        Assert.assertNull(element.getDriver());
    }

    @Test
    public void checkFullElementTypeTest() {
        Element element = new Element(null, null, Locator.ID, "myId", 2);
        Assert.assertEquals(element.getType(), Locator.ID);
    }

    @Test
    public void checkFullElementLocatorTest() {
        Element element = new Element(null, null, Locator.ID, "myId", 2);
        Assert.assertEquals(element.getLocator(), "myId");
    }

    @Test
    public void checkFullElementMatchTest() {
        Element element = new Element(null, null, Locator.ID, "myId", 2);
        Assert.assertEquals(element.getMatch(), 2);
    }

    @Test
    public void checkFullElementWebDriverTest() {
        Element element = new Element(null, null, Locator.ID, "myId", 2);
        Assert.assertNull(element.getDriver());
    }

    @Test
    public void checkParentElementTypeTest() {
        Element element = new Element(null, null, Locator.ID, "myId", null);
        Assert.assertEquals(element.getType(), Locator.ID);
    }

    @Test
    public void checkParentElementLocatorTest() {
        Element element = new Element(null, null, Locator.ID, "myId", null);
        Assert.assertEquals(element.getLocator(), "myId");
    }

    @Test
    public void checkParentElementMatchTest() {
        Element element = new Element(null, null, Locator.ID, "myId", null);
        Assert.assertEquals(element.getMatch(), 0);
    }

    @Test
    public void checkParentElementWebDriverTest() {
        Element element = new Element(null, null, Locator.ID, "myId", null);
        Assert.assertNull(element.getDriver());
    }

    @Test
    public void checkFullParentElementTypeTest() {
        Element element = new Element(null, null, Locator.ID, "myId", 2, null);
        Assert.assertEquals(element.getType(), Locator.ID);
    }

    @Test
    public void checkFullParentElementLocatorTest() {
        Element element = new Element(null, null, Locator.ID, "myId", 2, null);
        Assert.assertEquals(element.getLocator(), "myId");
    }

    @Test
    public void checkFullParentElementMatchTest() {
        Element element = new Element(null, null, Locator.ID, "myId", 2, null);
        Assert.assertEquals(element.getMatch(), 2);
    }

    @Test
    public void checkFullParentElementWebDriverTest() {
        Element element = new Element(null, null, Locator.ID, "myId", 2, null);
        Assert.assertNull(element.getDriver());
    }

    @Test
    public void checkPrettyOutputTest() {
        Element element = new Element(null, null, Locator.ID, "myId");
        Assert.assertEquals(element.prettyOutput(), " element with <i>ID</i> of <i>myId</i> ");
    }

    @Test
    public void checkPrettyOutputStartTest() {
        Element element = new Element(null, null, Locator.ID, "myId");
        Assert.assertEquals(element.prettyOutputStart(), "Element with <i>ID</i> of <i>myId</i>");
    }

    @Test
    public void checkPrettyOutputEndTest() {
        Element element = new Element(null, null, Locator.ID, "myId");
        Assert.assertEquals(element.prettyOutputEnd(), "element with <i>ID</i> of <i>myId</i>.");
    }

    @Test
    public void checkPrettyOutputLowercaseTest() {
        Element element = new Element(null, null, Locator.ID, "myId");
        Assert.assertEquals(element.prettyOutputLowercase(), "element with <i>ID</i> of <i>myId</i>");
    }
}