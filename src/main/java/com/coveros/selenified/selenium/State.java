package com.coveros.selenified.selenium;

import com.coveros.selenified.output.Assert.Success;
import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Selenium.Locator;

public class State {
    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private Action action;

    // constants
    private static final String EXPECTED = "Expected to find ";
    private static final String CHECKED = " is checked on the page";
    private static final String NOTCHECKED = " is not checked on the page";
    private static final String IS = " is ";

    public State(Action action, OutputFile file) {
        this.action = action;
        this.file = file;
    }

    public void setAction(Action action) {
        this.action = action;
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
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayed(Locator type, String locator) {
        return displayed(new Element(type, locator));
    }

    /**
     * checks to see if an element is visible on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayed(Locator type, String locator, int elementMatch) {
        return displayed(new Element(type, locator, elementMatch));
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notDisplayed(Locator type, String locator) {
        return notDisplayed(new Element(type, locator));
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param element
     *            - the element to be waited for
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notDisplayed(Element element, int elementMatch) {
        return notDisplayed(element.getType(), element.getLocator(), elementMatch);
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notDisplayed(Locator type, String locator, int elementMatch) {
        return notDisplayed(new Element(type, locator, elementMatch));
    }

    /**
     * checks to see if an object is checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checked(Locator type, String locator) {
        return checked(new Element(type, locator));
    }

    /**
     * checks to see if an object is checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checked(Locator type, String locator, int elementMatch) {
        return checked(new Element(type, locator, elementMatch));
    }

    /**
     * checks to see if an object is not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notChecked(Locator type, String locator) {
        return notChecked(new Element(type, locator));
    }

    /**
     * checks to see if an object is not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notChecked(Locator type, String locator, int elementMatch) {
        return notChecked(new Element(type, locator, elementMatch));
    }

    /**
     * checks to see if an object is visible and checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndChecked(Locator type, String locator) {
        return displayedAndChecked(new Element(type, locator));
    }

    /**
     * checks to see if an object is visible and checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndChecked(Locator type, String locator, int elementMatch) {
        return displayedAndChecked(new Element(type, locator, elementMatch));
    }

    /**
     * checks to see if an object is visible and not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndUnchecked(Locator type, String locator) {
        return displayedAndUnchecked(new Element(type, locator));
    }

    /**
     * checks to see if an object is visible and not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndUnchecked(Locator type, String locator, int elementMatch) {
        return displayedAndUnchecked(new Element(type, locator, elementMatch));
    }

    /**
     * checks to see if an element is editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int editable(Locator type, String locator) {
        return editable(new Element(type, locator));
    }

    /**
     * checks to see if an element is editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int editable(Locator type, String locator, int elementMatch) {
        return editable(new Element(type, locator, elementMatch));
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notEditable(Locator type, String locator) {
        return notEditable(new Element(type, locator));
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notEditable(Locator type, String locator, int elementMatch) {
        return notEditable(new Element(type, locator, elementMatch));
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndEditable(Locator type, String locator) {
        return displayedAndEditable(new Element(type, locator));
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndEditable(Locator type, String locator, int elementMatch) {
        return displayedAndEditable(new Element(type, locator, elementMatch));
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndNotEditable(Locator type, String locator) {
        return displayedAndNotEditable(new Element(type, locator));
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndNotEditable(Locator type, String locator, int elementMatch) {
        return displayedAndNotEditable(new Element(type, locator, elementMatch));
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * checks to see if an element is visible on the page
     *
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayed(Element element) {
        // wait for the element
        if (!action.is().elementDisplayed(element) && action.waitFor().elementDisplayed(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " visible on the page");
        file.recordActual(element.prettyOutputStart() + " is present and visible on the page", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notDisplayed(Element element) {
        // wait for the element
        if (action.is().elementDisplayed(element) && action.waitFor().elementNotDisplayed(element) == 1) {
            return 1;
        }
        // outputFile.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " present, but not visible on the page");
        file.recordActual(element.prettyOutputStart() + " is present and not visible on the page", Success.PASS);
        return 0;
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * checks to see if the actual element is editable
     * 
     * @param element
     *            - the element to be assessed
     * @param presence
     *            - what additional attribute is expected from the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    private int checkEditable(Element element, String presence) {
        // check for the object to the editable
        if (!action.is().elementInput(element)) {
            file.recordActual(element.prettyOutputStart() + IS + presence + " but not an input on the page",
                    Success.FAIL);
            file.addError();
            return 1;
        }
        if (!action.is().elementEnabled(element)) {
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
     * @param element
     *            - the element to be assessed
     * @param presence
     *            - what additional attribute is expected from the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    private int checkNotEditable(Element element, String presence) {
        // check for the object to the editable
        boolean isElementEnabled = false;
        if (action.is().elementInput(element)) {
            isElementEnabled = action.is().elementEnabled(element);
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
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int checked(Element element) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + CHECKED);
        // check for the object to the visible
        if (!action.is().elementChecked(element)) {
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
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notChecked(Element element) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // outputFile.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + NOTCHECKED);
        // check for the object to the visible
        if (action.is().elementChecked(element)) {
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
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndChecked(Element element) {
        // wait for the element
        if (!action.is().elementDisplayed(element) && action.waitFor().elementDisplayed(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + CHECKED);
        // check for the object to the visible
        if (!action.is().elementChecked(element)) {
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
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndUnchecked(Element element) {
        // wait for the element
        if (!action.is().elementDisplayed(element) && action.waitFor().elementDisplayed(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + NOTCHECKED);
        // check for the object to the visible
        if (action.is().elementChecked(element)) {
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
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int editable(Element element) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " editable on the page");
        return checkEditable(element, "present");
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int notEditable(Element element) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // outputFile.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " not editable on the page");
        return checkNotEditable(element, "present");
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndEditable(Element element) {
        // wait for the element
        if (!action.is().elementDisplayed(element) && action.waitFor().elementDisplayed(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " visible and editable on the page");
        return checkEditable(element, "visable");
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @param element
     *            - the element to be assessed
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int displayedAndNotEditable(Element element) {
        // wait for the element
        if (!action.is().elementDisplayed(element) && action.waitFor().elementDisplayed(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " visible and not editable on the page");
        return checkNotEditable(element, "visible");
    }
}