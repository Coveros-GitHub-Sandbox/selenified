package com.coveros.selenified.selenium;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.Assert.Success;

public class Helper {

    // constants
    private static final String NOTSELECT = " is not a select on the page";
    private static final String NOTTABLE = " is not a table on the page";

    /**
     * Determines if the element is a select
     * 
     * @param element
     *            - the element to be assessed
     * @return Boolean: whether the element is an select or not
     */
    public static boolean isSelect(Action action, OutputFile file, Element element) {
        if (!action.is().elementSelect(element)) {
            file.recordActual(element.prettyOutputStart() + NOTSELECT, Success.FAIL);
            file.addError();
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a table
     * 
     * @param element
     *            - the element to be assessed
     * @return Boolean: whether the element is an table or not
     */
    public static boolean isTable(Action action, OutputFile file, Element element) {
        if (!action.is().elementTable(element)) {
            file.recordActual(element.prettyOutputStart() + NOTTABLE, Success.FAIL);
            file.addError();
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a present, and if it is, it is a select
     * 
     * @param element
     *            - the element to be assessed
     * @param expected
     *            - the expected outcome
     * @return Boolean: whether the element is a select or not
     */
    public static boolean isPresentSelect(Action action, OutputFile file, Element element, String expected) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return false;
        }
        file.recordExpected(expected);
        // verify this is a select element
        return isSelect(action, file, element);
    }

    /**
     * Determines if the element is a present, and if it is, it is a table
     * 
     * @param element
     *            - the element to be assessed
     * @param expected
     *            - the expected outcome
     * @return Boolean: whether the element is an table or not
     */
    public static boolean isPresentTable(Action action, OutputFile file, Element element, String expected) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return false;
        }
        file.recordExpected(expected);
        // verify this is a select element
        return isTable(action, file, element);
    }
}
