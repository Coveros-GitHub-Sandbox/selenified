package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Selenium.Locators;

public class ActionGetIT extends TestBase {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		// set the base URL for the tests here
		setTestSite("http://172.31.2.65/");
		// set the author of the tests here
		setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
		// set the version of the tests or of the software, possibly with a
		// dynamic check
		setVersion("0.0.1");
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getBrowser method")
	public void getBrowserTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		if (actions.getBrowser().equals("NONE"))
			asserts.getOutputFile().addError();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getCapabilities method")
	public void getCapabilitiesTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		if (actions.getCapabilities().equals(null))
			asserts.getOutputFile().addError();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getSelectOptions method")
	public void getSelectOptionsTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getSelectOptions(Locators.NAME, "car_list");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getSelectOptions method")
	public void getSelectOptionsNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getSelectOptions(Locators.NAME, "non-existent-name");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfSelectOptions method")
	public void getNumOfSelectOptionsTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getNumOfSelectOptions(Locators.NAME, "car_list");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfSelectOptions method")
	public void getNumOfSelectOptionsNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getNumOfSelectOptions(Locators.NAME, "non-existent-name");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfTableRows method")
	public void getNumOfTableRowsTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getNumOfTableRows(Locators.ID, "table");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfTableRows method")
	public void getNumOfTableRowsNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getNumOfTableRows(Locators.ID, "non-existent-name");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfTableColumns method")
	public void getNumOfTableColumnsTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getNumOfTableColumns(Locators.ID, "table");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfTableColumns method")
	public void getNumOfTableColumnsNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getNumOfTableColumns(Locators.ID, "non-existent-name");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfTableColumns method")
	public void getTableRowHeaderTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableRowHeader(Locators.ID, "table", "Company");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfTableColumns method")
	public void getTableRowHeaderBadHeaderTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableRowHeader(Locators.ID, "table", "Company1");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfTableColumns method")
	public void getTableRowHeaderNoHeaderTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableRowHeader(Locators.ID, "table_no_header", "Company");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to check the getNumOfTableColumns method")
	public void getTableRowHeaderNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableRowHeader(Locators.ID, "non-existent-name", "Company");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableRow method")
	public void getTableRowTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableRow(Locators.ID, "table", 1);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableRow method")
	public void getTableRowNoRowTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableRow(Locators.ID, "table", 99);
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableRow method")
	public void getTableRowNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableRow(Locators.ID, "non-existent-name", 1);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableColumn method")
	public void getTableColumnTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableColumn(Locators.ID, "table", 1);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableColumn method")
	public void getTableColumnNoColumnTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableColumn(Locators.ID, "table", 99);
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableColumn method")
	public void getTableColumnNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableColumn(Locators.ID, "non-existent-name", 1);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableCell method")
	public void getTableCellTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableCell(Locators.ID, "table", 1, 1);
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableCell method")
	public void getTableCellNoCellTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableCell(Locators.ID, "table", 99, 99);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "virtual" }, description = "An integration test to check the getTableCell method")
	public void getTableCellNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.getTableCell(Locators.ID, "non-existent-name", 1, 1);
		// verify 1 issue
		finish(1);
	}
}