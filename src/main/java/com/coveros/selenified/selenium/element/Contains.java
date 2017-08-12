package com.coveros.selenified.selenium.element;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.coveros.selenified.output.OutputFile.Success;
import com.coveros.selenified.output.OutputFile;

public class Contains extends Assert {

	public Contains(Element element, OutputFile file) {
		this.element = element;
		this.file = file;
	}

	// ///////////////////////////////////////
	// assessing functionality
	// ///////////////////////////////////////

	/**
	 * checks to see if an element contains a particular class
	 *
	 * @param expectedClass
	 *            - the expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void clazz(String expectedClass) {
		// wait for the element
		if (!isPresent()) {
			return;
		}
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + " containing class <b>" + expectedClass + "</b>");
		// get the class
		String actualClass = element.get().attribute(CLASS);
		// file.record the element
		if (!actualClass.contains(expectedClass)) {
			file.recordActual(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>, which contains <b>"
				+ expectedClass + "</b>", Success.PASS);
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
		// file.record the element
		file.recordExpected(EXPECTED + element.prettyOutput() + " with attribute <b>" + attribute + "</b>");
		// get all attributes
		Map<String, String> attributes = element.get().allAttributes();
		if (attributes == null) {
			file.recordActual("Unable to assess the attributes of " + element.prettyOutput(), Success.FAIL);
			file.addError();
			return;
		}
		Set<String> keys = attributes.keySet();
		String[] allAttributes = keys.toArray(new String[keys.size()]);
		// file.record the element
		if (!Arrays.asList(allAttributes).contains(attribute)) {
			file.recordActual(element.prettyOutputStart() + " does not contain the attribute of <b>" + attribute
					+ "</b>" + ONLYVALUE + Arrays.toString(allAttributes) + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + " contains the attribute of <b>" + attribute + "</b>",
				Success.PASS);
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
		if (!elementValue.contains(expectedValue)) {
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
		if (!elementValue.contains(expectedValue)) {
			file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
	}

	/**
	 * checks to see if an option is available to be selected on the page
	 *
	 * @param option
	 *            the option expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void selectOption(String option) {
		// wait for the select
		if (!isPresentSelect(EXPECTED + element.prettyOutput() + " with the option <b>" + option
				+ "</b> available to be selected on the page")) {
			return;
		}
		// check for the object to the editable
		String[] allOptions = element.get().selectOptions();
		if (!Arrays.asList(allOptions).contains(option)) {
			file.recordActual(element.prettyOutputStart() + " is present but does not contain the option " + "<b>"
					+ option + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(
				element.prettyOutputStart() + " is present and contains the option " + "<b>" + option + "</b>",
				Success.PASS);
	}

	/**
	 * checks to see if an element select value exists
	 *
	 * @param selectValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void selectValue(String selectValue) {
		// wait for the select
		if (!isPresentSelect(EXPECTED + element.prettyOutput() + " having a select value of <b>" + selectValue
				+ "</b> available to be selected on the page")) {
			return;
		}
		// check for the object to the present on the page
		String[] elementValues = element.get().selectValues();
		if (!Arrays.asList(elementValues).contains(selectValue)) {
			file.recordActual(element.prettyOutputStart() + " does not contain the value of <b>" + selectValue + "</b>"
					+ ONLYVALUE + Arrays.toString(elementValues) + "</b>", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + HASVALUE + selectValue + "</b>", Success.PASS);
	}

	/**
	 * compares the number of expected attributes from a select value with the
	 * actual number of attributes from the element
	 *
	 * @param numOfOptions
	 *            the expected number of options in the select element
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void selectOptions(int numOfOptions) {
		// wait for the select
		if (!isPresentSelect(EXPECTED + element.prettyOutput() + " with number of select values equal to <b>"
				+ numOfOptions + "</b>")) {
			return;
		}
		// check for the object to the present on the page
		int elementValues = element.get().numOfSelectOptions();
		if (elementValues != numOfOptions) {
			file.recordActual(element.prettyOutputStart() + " has <b>" + numOfOptions + "</b>" + " select options",
					Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + " has <b>" + numOfOptions + "</b>" + " select options",
				Success.PASS);
	}

	/**
	 * compares the number of expected columns with the actual number of columns
	 * of a table with from a table element
	 *
	 * @param numOfColumns
	 *            the expected number of column elements of a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void columns(int numOfColumns) {
		// wait for the table
		if (!isPresentTable(EXPECTED + element.prettyOutput() + " with the number of table columns equal to <b>"
				+ numOfColumns + "</b>")) {
			return;
		}
		int actualNumOfCols = element.get().numOfTableColumns();
		if (actualNumOfCols != numOfColumns) {
			file.recordActual(element.prettyOutputStart() + " does not have the number of columns <b>" + numOfColumns
					+ "</b>. Instead, " + actualNumOfCols + " columns were found", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + "has " + actualNumOfCols + "</b> columns", Success.PASS);
	}

	/**
	 * compares the number of expected rows with the actual number of rows of a
	 * table with from a table element
	 *
	 * @param numOfRows
	 *            the expected number of row elements of a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public void rows(int numOfRows) {
		// wait for the table
		if (!isPresentTable(EXPECTED + element.prettyOutput() + " with the number of table rows equal to <b>"
				+ numOfRows + "</b>")) {
			return;
		}
		int actualNumOfRows = element.get().numOfTableRows();
		if (actualNumOfRows != numOfRows) {
			file.recordActual(element.prettyOutputStart() + " does not have the number of rows <b>" + numOfRows
					+ "</b>. Instead, " + actualNumOfRows + " rows were found", Success.FAIL);
			file.addError();
			return;
		}
		file.recordActual(element.prettyOutputStart() + "has " + actualNumOfRows + "</b> rows", Success.PASS);
	}
}