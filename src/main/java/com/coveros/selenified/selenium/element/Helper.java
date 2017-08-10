package com.coveros.selenified.selenium.element;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.OutputFile.Success;

public class Helper {

    // what element are we trying to interact with on the page
    private Element element;

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // constants
    private static final String NOTSELECT = " is not a select on the page";
    private static final String NOTTABLE = " is not a table on the page";

    public Helper(Element element, OutputFile file) {
        this.element = element;
        this.file = file;
    }

    /**
     * Determines if the element is a select
     * 
     * @return Boolean: whether the element is an select or not
     */
    public boolean isSelect() {
        if (!element.is().select()) {
            file.recordActual(element.prettyOutputStart() + NOTSELECT, Success.FAIL);
            file.addError();
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a table
     * 
     * @return Boolean: whether the element is an table or not
     */
    public boolean isTable() {
        if (!element.is().table()) {
            file.recordActual(element.prettyOutputStart() + NOTTABLE, Success.FAIL);
            file.addError();
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a present, and if it is, it is a select
     * 
     * @param expected
     *            - the expected outcome
     * @return Boolean: whether the element is a select or not
     */
    public boolean isPresentSelect(String expected) {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return false;
        }
        file.recordExpected(expected);
        // verify this is a select element
        return isSelect();
    }

    /**
     * Determines if the element is a present, and if it is, it is a table
     * 
     * @param expected
     *            - the expected outcome
     * @return Boolean: whether the element is an table or not
     */
    public boolean isPresentTable(String expected) {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return false;
        }
        file.recordExpected(expected);
        // verify this is a select element
        return isTable();
    }
}