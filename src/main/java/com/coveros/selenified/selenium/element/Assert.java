package com.coveros.selenified.selenium.element;

import com.coveros.selenified.tools.OutputFile;
import com.coveros.selenified.tools.OutputFile.Success;

public class Assert {

    // this will be the name of the file we write all commands out to
    protected OutputFile file;

    // what element are we trying to interact with on the page
    protected Element element;

    // constants
    protected static final String EXPECTED = "Expected to find ";
    protected static final String CLASS = "class";

    protected static final String NOTINPUT = " is not an input on the page";

    protected static final String VALUE = " has the value of <b>";
    protected static final String TEXT = " has the text of <b>";
    protected static final String HASVALUE = " contains the value of <b>";
    protected static final String HASTEXT = " contains the text of <b>";
    protected static final String ONLYVALUE = ", only the values <b>";
    protected static final String CLASSVALUE = " has a class value of <b>";

    protected static final String NOTSELECT = " is not a select on the page";
    protected static final String NOTTABLE = " is not a table on the page";

    protected boolean isPresent() {
        if (!element.is().present()) {
            element.waitFor().present();
            if (!element.is().present()) {
                return false;
            }
        }
        return true;
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
        if (!isPresent()) {
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
        if (!isPresent()) {
            return false;
        }
        file.recordExpected(expected);
        // verify this is a select element
        return isTable();
    }
}