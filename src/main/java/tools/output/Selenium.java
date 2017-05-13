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
}