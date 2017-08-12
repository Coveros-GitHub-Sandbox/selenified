package com.coveros.selenified.selenium.element;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.OutputFile.Success;

public class Excludes extends Assert {

	public Excludes(Element element, OutputFile file) {
		this.element = element;
		this.file = file;
	}

	// ///////////////////////////////////////
	// assessing functionality
	// ///////////////////////////////////////

	/**
	 * checks to see if an element does not contain a particular class
	 *
	 * @param unexpectedClass
	 *            - the unexpected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void clazz(String unexpectedClass) {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		file.recordExpected(EXPECTED + element.prettyOutput() + " without class <b>" + unexpectedClass + "</b>");
		// check our classes
		String actualClass = element.get().attribute(CLASS);
		// file.record the element
		if (actualClass.contains(unexpectedClass)) {
			file.recordActual(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>, which contains <b>"
					+ unexpectedClass + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(
				element.prettyOutputStart() + " does not contain a class value of <b>" + unexpectedClass + "</b>",
				Success.PASS);
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 *
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void attribute(String attribute) {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		file.recordExpected(EXPECTED + element.prettyOutput() + " without attribute <b>" + attribute + "</b>");
		// check our attributes
		Map<String, String> attributes = element.get().allAttributes();
		if (attributes == null) {
			file.recordActual("Unable to assess the attributes of " + element.prettyOutput(), Success.FAIL);
			file.addError();
			return;
		}
		Set<String> keys = attributes.keySet();
		String[] allAttributes = keys.toArray(new String[keys.size()]);
		// file.record the element
		if (Arrays.asList(allAttributes).contains(attribute)) {
			file.recordActual(element.prettyOutputStart() + " contains the attribute of <b>" + attribute + "</b>",
					Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + " does not contain the attribute of <b>" + attribute + "</b>"
				+ ONLYVALUE + Arrays.toString(allAttributes) + "</b>", Success.PASS);
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void text(String expectedValue) {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + HASTEXT + expectedValue + "</b>");
		// check for the object to the present on the page
		String elementValue = element.get().text();
		if (elementValue.contains(expectedValue)) {
			file.recordActual(element.prettyOutputStart() + TEXT + elementValue + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + TEXT + elementValue + "</b>", Success.PASS);
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void value(String expectedValue) {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + HASVALUE + expectedValue + "</b>");
		// verify this is an input element
		if (!element.is().input()) {
			file.recordActual(element.prettyOutputStart() + NOTINPUT, Success.FAIL);
			file.addError();
			return;
		}
		// check for the object to the present on the page
		String elementValue = element.get().value();
		if (elementValue.contains(expectedValue)) {
			file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
	}

	/**
	 * checks to see if an option is not available to be selected on the page
	 *
	 * @param option
	 *            the option not expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void selectOption(String option) {
		// wait for the select
		if (!isPresentSelect(EXPECTED + element.prettyOutput() + " without the option <b>" + option
				+ "</b> available to be selected on the page")) {
			return;
		}
		// check for the object to the editable
		String[] allOptions = element.get().selectOptions();
		if (Arrays.asList(allOptions).contains(option)) {
			file.recordActual(element.prettyOutputStart() + " is editable and present and contains the option " + "<b>"
					+ option + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + " is editable and present but does not contain the option "
				+ "<b>" + option + "</b>", Success.PASS);
	}

	/**
	 * checks to see if an element select value does not exist
	 *
	 * @param selectValue
	 *            the unexpected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void selectValue(String selectValue) {
		// wait for the select
		if (!isPresentSelect(EXPECTED + element.prettyOutput() + " without a select value of <b>" + selectValue
				+ "</b> available to be selected on the page")) {
			return;
		}
		// check for the object to the present on the page
		String[] elementValues = element.get().selectValues();
		if (Arrays.asList(elementValues).contains(selectValue)) {
			file.recordActual(element.prettyOutputStart() + HASVALUE + selectValue + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + " does not contain the value of <b>" + selectValue
				+ "</b>, only the values <b>" + Arrays.toString(elementValues) + "</b>", Success.PASS);
	}
}