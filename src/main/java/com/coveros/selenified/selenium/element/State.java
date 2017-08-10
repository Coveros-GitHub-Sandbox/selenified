package com.coveros.selenified.selenium.element;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.OutputFile.Success;

public class State {
	
    // this will be the name of the file we write all commands out to
    private OutputFile file;
	
	// what element are we trying to interact with on the page
	private Element element;

    // constants
    private static final String EXPECTED = "Expected to find ";
    private static final String CHECKED = " is checked on the page";
    private static final String NOTCHECKED = " is not checked on the page";
    private static final String IS = " is ";

    public State(Element element, OutputFile file) {
        this.element = element;
        this.file = file;
    }

    public void setOutputFile(OutputFile file) {
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
    public int displayed() {
        // wait for the element
        if (!element.is().displayed() && element.waitFor().displayed() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " visible on the page");
        file.recordActual(element.prettyOutputStart() + " is present and visible on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notDisplayed() {
        // wait for the element
        if (element.is().displayed() && element.waitFor().notDisplayed() == 1) {
            return 1;
        }
        // outputFile.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " present, but not visible on the page");
        file.recordActual(element.prettyOutputStart() + " is present and not visible on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if the actual element is editable
     * 
     * @param presence
     *            - what additional attribute is expected from the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    private int checkEditable( String presence) {
        // check for the object to the editable
        if (!element.is().input()) {
            file.recordActual(element.prettyOutputStart() + IS + presence + " but not an input on the page",
                    Success.FAIL);
            file.addError();
            return 1;
        }
        if (!element.is().enabled()) {
            file.recordActual(element.prettyOutputStart() + IS + presence + " but not editable on the page",
                    Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + IS + presence + " and editable on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if the actual element is editable
     * 
     * @param presence
     *            - what additional attribute is expected from the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    private int checkNotEditable( String presence) {
        // check for the object to the editable
        boolean isElementEnabled = false;
        if (element.is().input()) {
            isElementEnabled = element.is().enabled();
        }
        if (isElementEnabled) {
            file.recordActual(element.prettyOutputStart() + IS + presence + " but editable on the page", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + IS + presence + " and not editable on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is checked on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checked() {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + CHECKED);
        // check for the object to the visible
        if (!element.is().checked()) {
            file.recordActual(element.prettyOutputStart() + NOTCHECKED, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + CHECKED, Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is not checked on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notChecked() {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return 1;
        }
        // outputFile.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + NOTCHECKED);
        // check for the object to the visible
        if (element.is().checked()) {
            file.recordActual(element.prettyOutputStart() + " checked on the page", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + NOTCHECKED, Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is visible and checked on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndChecked() {
        // wait for the element
        if (!element.is().displayed() && element.waitFor().displayed() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + CHECKED);
        // check for the object to the visible
        if (!element.is().checked()) {
            file.recordActual(element.prettyOutputStart() + NOTCHECKED, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + " is checked and visible on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is visible and not checked on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndUnchecked() {
        // wait for the element
        if (!element.is().displayed() && element.waitFor().displayed() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + NOTCHECKED);
        // check for the object to the visible
        if (element.is().checked()) {
            file.recordActual(element.prettyOutputStart() + CHECKED, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + " is not checked and visible on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is editable on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int editable() {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " editable on the page");
        return checkEditable( "present");
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notEditable() {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return 1;
        }
        // outputFile.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " not editable on the page");
        return checkNotEditable( "present");
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndEditable() {
        // wait for the element
        if (!element.is().displayed() && element.waitFor().displayed() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " visible and editable on the page");
        return checkEditable( "visable");
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndNotEditable() {
        // wait for the element
        if (!element.is().displayed() && element.waitFor().displayed() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " visible and not editable on the page");
        return checkNotEditable( "visible");
    }
}