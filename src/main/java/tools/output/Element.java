package tools.output;

import tools.output.Selenium.Locators;

public class Element {

	private Locators type;
	private String locator;

	public Element(Locators type, String locator) {
		this.setType(type);
		this.setLocator(locator);
	}

	public void setType(Locators type) {
		this.type = type;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	public Locators getType() {
		return type;
	}

	public String getLocator() {
		return locator;
	}
}