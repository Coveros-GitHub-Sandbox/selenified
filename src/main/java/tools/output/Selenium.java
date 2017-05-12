package tools.output;

public class Selenium {

	/**
	 * Select a Locator for the element we are interacting with Available
	 * options are: xpath, id, name, classname, paritallinktext, linktext,
	 * tagname
	 */
	public enum Locators {
		xpath, id, name, classname, paritallinktext, linktext, tagname
	}

	/**
	 * Select a browser to run Available options are: HtmlUnit (only locally -
	 * not on grid), Firefox, Chrome, InternetExplorer, Android, Ipad (only
	 * locally - not on grid), Iphone (only locally, not on grid, Opera, Safari
	 */
	public enum Browsers {
		None, HtmlUnit, Firefox, Marionette, Chrome, InternetExplorer, Edge, Android, Ipad, Iphone, Opera, Safari, PhantomJS
	}

	public class Element {
		private Locators type;
		private String locator;

		public Element(Locators type, String locator) {
			this.setType(type);
			this.setLocator(locator);
		}

		public Locators getType() {
			return type;
		}
		
		public String getLocator() {
			return locator;
		}

		public void setType(Locators type) {
			this.type = type;
		}

		public void setLocator(String locator) {
			this.locator = locator;
		}
	}
}