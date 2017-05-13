package unit;

import org.testng.Assert;
import org.testng.annotations.Test;

import tools.output.Selenium.Locators;
import tools.output.Element;

public class ElementTest {
	
	@Test
	public void checkElementTypeTest() {
		Element element = new Element(Locators.id, "myId" );
		Assert.assertEquals(element.getType(), Locators.id);
		
		element.setType(Locators.name);
		Assert.assertEquals(element.getType(), Locators.name);
	}
	
	@Test
	public void checkElementLocatorTest() {
		Element element = new Element(Locators.id, "myId" );
		Assert.assertEquals(element.getLocator(), "myId");
		
		element.setLocator("newId");
		Assert.assertEquals(element.getLocator(), "newId");
	}
}
