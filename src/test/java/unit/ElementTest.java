package unit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locator;

public class ElementTest {
	
	@Test
	public void checkElementTypeTest() {
		Element element = new Element(Locator.ID, "myId" );
		Assert.assertEquals(element.getType(), Locator.ID);
		
		element.setType(Locator.NAME);
		Assert.assertEquals(element.getType(), Locator.NAME);
	}
	
	@Test
	public void checkElementLocatorTest() {
		Element element = new Element(Locator.ID, "myId" );
		Assert.assertEquals(element.getLocator(), "myId");
		
		element.setLocator("newId");
		Assert.assertEquals(element.getLocator(), "newId");
	}
}
