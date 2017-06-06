package unit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Locators;

public class ElementTest {
	
	@Test
	public void checkElementTypeTest() {
		Element element = new Element(Locators.ID, "myId" );
		Assert.assertEquals(element.getType(), Locators.ID);
		
		element.setType(Locators.NAME);
		Assert.assertEquals(element.getType(), Locators.NAME);
	}
	
	@Test
	public void checkElementLocatorTest() {
		Element element = new Element(Locators.ID, "myId" );
		Assert.assertEquals(element.getLocator(), "myId");
		
		element.setLocator("newId");
		Assert.assertEquals(element.getLocator(), "newId");
	}
}
