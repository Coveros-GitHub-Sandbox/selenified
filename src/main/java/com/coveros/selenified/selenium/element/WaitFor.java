package com.coveros.selenified.selenium.element;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.OutputFile.Result;
import com.coveros.selenified.selenium.element.Element;
import com.coveros.selenified.tools.General;

public class WaitFor {

	private static final Logger log = Logger.getLogger(General.class);

	// what element are we trying to interact with on the page
	private Element element;

	// this will be the name of the file we write all commands out to
	private OutputFile file;

	// constants
	private static final String WAITED = "Waited ";
	private static final String UPTO = "Wait up to ";
	private static final String WAITING = "After waiting ";
	private static final String SECONDS_FOR = " seconds for ";

	private static final String PRESENT = " to be present";
	private static final String DISPLAYED = " to be displayed";
	private static final String ENABLED = " to be enabled";

	private double defaultWait = 5.0;

	public WaitFor(Element element, OutputFile file) {
		this.element = element;
		this.file = file;
	}

	public void changeDefaultWait(double seconds) {
		defaultWait = seconds;
	}

	// ///////////////////////////////////////
	// waiting functionality
	// ///////////////////////////////////////

	/**
	 * a method for waiting until an element is present for a maximum of 5
	 * seconds
	 * 
	 * @return Integer: the number of errors encountered while executing these
	 *         steps
	 */
	public int present() {
		return present(defaultWait);
	}

	/**
	 * a method for waiting until an element is no longer present for a maximum
	 * of 5 seconds
	 *
	 * @return Integer: the number of errors encountered while executing these
	 *         steps
	 */
	public int notPresent() {
		return notPresent(defaultWait);
	}

	/**
	 * a method for waiting until an element is displayed for a maximum of 5
	 * seconds
	 *
	 * @return Integer: the number of errors encountered while executing these
	 *         steps
	 */
	public int displayed() {
		return displayed(defaultWait);
	}

	/**
	 * a method for waiting until an element is not displayed for a maximum of 5
	 * seconds
	 *
	 * @return Integer: the number of errors encountered while executing these
	 *         steps
	 */
	public int notDisplayed() {
		return notDisplayed(defaultWait);
	}

	/**
	 * a method for waiting until an element is enabled for a maximum of 5
	 * seconds
	 *
	 * @return Integer: the number of errors encountered while executing these
	 *         steps
	 */
	public int enabled() {
		return enabled(defaultWait);
	}

	/**
	 * a method for waiting until an element is not enabled for a maximum of 5
	 * seconds
	 *
	 * @return Integer: the number of errors encountered while executing these
	 *         steps
	 */
	public int notEnabled() {
		return notEnabled(defaultWait);
	}

	///////////////////////////////////////////////////
	// Our actual full implementation of the above overloaded methods
	///////////////////////////////////////////////////

	/**
	 * a method for waiting until an element is present
	 * 
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer: the number of errors encountered while executing these
	 *         steps
	 */
	public int present(double seconds) {
		String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + PRESENT;
		String expected = element.prettyOutputStart() + " is present";
		// wait for up to XX seconds for the error message
		double end = System.currentTimeMillis() + (seconds * 1000);
		while (System.currentTimeMillis() < end) {
			try { // If results have been returned, the results are displayed in
					// a drop down.
				element.getWebElement().getText();
				break;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				log.error(e);
			}
		}
		double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
		timetook = timetook / 1000;
		if (!element.is().present(false)) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is not present", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + PRESENT,
				Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is no longer present
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int notPresent(double seconds) {
		String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + " to not be present";
		String expected = element.prettyOutputStart() + " is not present";
		// wait for up to XX seconds for the error message
		double end = System.currentTimeMillis() + (seconds * 1000);
		while (System.currentTimeMillis() < end) {
			if (!element.is().present(false)) {
				break;
			}
		}
		double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
		timetook = timetook / 1000;
		if (element.is().present(false)) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still present", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected,
				WAITED + timetook + SECONDS_FOR + element.prettyOutput() + " to not be present", Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is displayed
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int displayed(double seconds) {
		String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + DISPLAYED;
		String expected = element.prettyOutputStart() + " is displayed";
		double start = System.currentTimeMillis();
		if (!element.is().present(false)) {
			int success = present(seconds);
			if (success == 1) {
				return success;
			}
		}
		WebElement webElement = element.getWebElement();
		if (!webElement.isDisplayed()) {
			// wait for up to XX seconds
			double end = System.currentTimeMillis() + (seconds * 1000);
			while (System.currentTimeMillis() < end) {
				if (webElement.isDisplayed()) {
					break;
				}
			}
		}
		double timetook = (System.currentTimeMillis() - start) / 1000;
		if (!webElement.isDisplayed()) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is not displayed", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + DISPLAYED,
				Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is not displayed
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int notDisplayed(double seconds) {
		// this might fail if the element disappears completely
		String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + " to not be displayed";
		String expected = element.prettyOutputStart() + " is not displayed";
		if (!element.is().present(false)) {
			file.recordAction(action, expected, element.prettyOutput() + " is not present, and therefore not displayed",
					Result.SUCCESS);
			return 0;
		}
		double start = System.currentTimeMillis();
		WebElement webElement = element.getWebElement();
		// wait for up to XX seconds
		double end = System.currentTimeMillis() + (seconds * 1000);
		try {
			while (webElement.isDisplayed() && System.currentTimeMillis() < end)
				;
		} catch (StaleElementReferenceException e) {
			log.error(e);
			file.recordAction(action, expected,
					element.prettyOutput() + " has been removed from the page, and therefore not displayed",
					Result.SUCCESS);
			return 0;
		}
		double timetook = (System.currentTimeMillis() - start) / 1000;
		if (webElement.isDisplayed()) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still displayed", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected,
				WAITED + timetook + SECONDS_FOR + element.prettyOutput() + " to not be displayed", Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is enabled
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int enabled(double seconds) {
		String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + ENABLED;
		String expected = element.prettyOutputStart() + " is enabled";
		double start = System.currentTimeMillis();
		if (!element.is().present(false)) {
			present(seconds);
		}
		if (!element.is().enabled(false)) {
			WebElement webElement = element.getWebElement();
			// wait for up to XX seconds for the error message
			double end = System.currentTimeMillis() + (seconds * 1000);
			while (System.currentTimeMillis() < end) {
				// If results have been returned, the results are displayed
				// in a drop down.
				if (webElement.isEnabled()) {
					break;
				}
			}
		}
		double timetook = (System.currentTimeMillis() - start) / 1000;
		if (!element.is().enabled(false)) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is not enabled", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + ENABLED,
				Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is not enabled
	 *
	 * @param seconds
	 *            - the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int notEnabled(double seconds) {
		// this might fail if the element is no longer present
		String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + " to not be enabled";
		String expected = element.prettyOutputStart() + " is not enabled";
		if (!element.is().present(false)) {
			file.recordAction(action, expected, element.prettyOutput() + " is not present, and therefore not enabled",
					Result.SUCCESS);
			return 0;
		}
		double start = System.currentTimeMillis();
		WebElement webElement = element.getWebElement();
		// wait for up to XX seconds
		double end = System.currentTimeMillis() + (seconds * 1000);
		try {
			while (webElement.isEnabled() && System.currentTimeMillis() < end)
				;
		} catch (StaleElementReferenceException e) {
			log.error(e);
			file.recordAction(action, expected,
					element.prettyOutput() + " has been removed from the page, and therefore not displayed",
					Result.SUCCESS);
			return 0;
		}
		double timetook = (System.currentTimeMillis() - start) / 1000;
		if (webElement.isEnabled()) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still enabled", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected,
				WAITED + timetook + SECONDS_FOR + element.prettyOutput() + " to not be enabled", Result.SUCCESS);
		return 0;
	}
}