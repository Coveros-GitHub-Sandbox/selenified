package com.coveros.selenified.selenium.element;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.OutputFile.Success;

public class State extends Assert {

	// constants
	private static final String EXPECTED = "Expected to find ";
	private static final String CHECKED = " is checked on the page";
	private static final String NOTCHECKED = " is not checked on the page";
	private static final String IS = " is ";

	public State(Element element, OutputFile file) {
		this.element = element;
		this.file = file;
	}

	// ///////////////////////////////////////
	// assessing functionality
	// ///////////////////////////////////////

	/**
	 * checks to see if an element is visible on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void displayed() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + " visible on the page");
		file.recordActual(element.prettyOutputStart() + " is present and visible on the page", Success.PASS);
	}

	/**
	 * checks to see if an element is not visible on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void notDisplayed() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// outputFile.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + " present, but not visible on the page");
		file.recordActual(element.prettyOutputStart() + " is present and not visible on the page", Success.PASS);
	}

	/**
	 * checks to see if the actual element is editable
	 * 
	 * @param presence
	 *            - what additional attribute is expected from the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	private void checkEditable(String presence) {
		// check for the object to the editable
		if (!element.is().input()) {
			file.recordActual(element.prettyOutputStart() + IS + presence + " but not an input on the page",
					Success.FAIL);
			file.addError();
			return;
		}
		if (!element.is().enabled()) {
			file.recordActual(element.prettyOutputStart() + IS + presence + " but not editable on the page",
					Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + IS + presence + " and editable on the page", Success.PASS);
	}

	/**
	 * checks to see if the actual element is editable
	 * 
	 * @param presence
	 *            - what additional attribute is expected from the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	private void checkNotEditable(String presence) {
		// check for the object to the editable
		boolean isElementEnabled = false;
		if (element.is().input()) {
			isElementEnabled = element.is().enabled();
		}
		if (isElementEnabled) {
			file.recordActual(element.prettyOutputStart() + IS + presence + " but editable on the page", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + IS + presence + " and not editable on the page", Success.PASS);
	}

	/**
	 * checks to see if an object is checked on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void checked() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + CHECKED);
		// check for the object to the visible
		if (!element.is().checked()) {
			file.recordActual(element.prettyOutputStart() + NOTCHECKED, Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + CHECKED, Success.PASS);
	}

	/**
	 * checks to see if an object is not checked on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void notChecked() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// outputFile.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + NOTCHECKED);
		// check for the object to the visible
		if (element.is().checked()) {
			file.recordActual(element.prettyOutputStart() + " checked on the page", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + NOTCHECKED, Success.PASS);
	}

	/**
	 * checks to see if an object is visible and checked on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void displayedAndChecked() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + CHECKED);
		// check for the object to the visible
		if (!element.is().checked()) {
			file.recordActual(element.prettyOutputStart() + NOTCHECKED, Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + " is checked and visible on the page", Success.PASS);
	}

	/**
	 * checks to see if an object is visible and not checked on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void displayedAndUnchecked() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + NOTCHECKED);
		// check for the object to the visible
		if (element.is().checked()) {
			file.recordActual(element.prettyOutputStart() + CHECKED, Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + " is not checked and visible on the page", Success.PASS);
	}

	/**
	 * checks to see if an element is editable on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void editable() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + " editable on the page");
		checkEditable("present");
	}

	/**
	 * checks to see if an element is not editable on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void notEditable() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// outputFile.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + " not editable on the page");
		checkNotEditable("present");
	}

	/**
	 * checks to see if an element is visible and editable on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void displayedAndEditable() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + " visible and editable on the page");
		checkEditable("visable");
	}

	/**
	 * checks to see if an element is visible and not editable on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void displayedAndNotEditable() {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + " visible and not editable on the page");
		checkNotEditable("visible");
	}
}