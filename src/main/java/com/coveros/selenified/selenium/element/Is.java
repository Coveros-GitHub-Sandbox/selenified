package com.coveros.selenified.selenium.element;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import com.coveros.selenified.selenium.element.Element;
import com.coveros.selenified.tools.General;
import com.coveros.selenified.tools.OutputFile;

public class Is {

    private static final Logger log = Logger.getLogger(General.class);

    // what element are we trying to interact with on the page
    private Element element;

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // constants
    private static final String SELECT = "select";
    private static final String CHECKING = "Checking for ";
    private static final String PRESENT = " to be present";
    private static final String DISPLAYED = " to be displayed";
    private static final String ENABLED = " to be enabled";
    private static final String SELECTED = " to have something selected";

    public Is(Element element, OutputFile file) {
        this.element = element;
        this.file = file;
    }

    // ////////////////////////////////////
    // checking element availability
    // ////////////////////////////////////

    /**
     * a method for checking if an element is present
     *
     * @return boolean: whether the element is present or not
     */
    public boolean present() {
        return present(false);
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @return boolean: whether the element is an input or not
     */
    public boolean input() {
        return input(false);
    }

    /**
     * a method for checking if an element is a select element
     *
     * @return boolean: whether the element is an input or not
     */
    public boolean select() {
        return select(false);
    }

    /**
     * a method for checking if an element is a table element
     *
     * @return boolean: whether the element is an input or not
     */
    public boolean table() {
        return table(false);
    }

    /**
     * a method for checking if an element is enabled
     *
     * @return boolean: whether the element is present or not
     */
    public boolean enabled() {
        return enabled(false);
    }

    /**
     * a method for checking if an element is checked
     *
     * @return boolean: whether the element is checked or not
     */
    public boolean checked() {
        return checked(false);
    }

    /**
     * a method for checking if an element is displayed
     *
     * @return boolean: whether the element is displayed or not
     */
    public boolean displayed() {
        return displayed(false);
    }

    /**
     * determine if something is selected in a drop down
     * 
     * @return boolean: is something selected or not
     */
    public boolean somethingSelected() {
        return somethingSelected(false);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * a method for checking if an element is present
     *
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean present(boolean print) {
        boolean isPresent = false;
        try {
            element.getWebElement().getText();
            isPresent = true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected(CHECKING + element.prettyOutput() + PRESENT);
        }
        return isPresent;
    }

    /**
     * a method for checking if an element is an input; it needs to be an input,
     * select, or textarea
     *
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean input(boolean print) {
        boolean isInput = false;
        try {
            WebElement webElement = element.getWebElement();
            if ("input".equalsIgnoreCase(webElement.getTagName())
                    || "textarea".equalsIgnoreCase(webElement.getTagName())
                    || SELECT.equalsIgnoreCase(webElement.getTagName())) {
                isInput = true;
            }
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected(CHECKING + element.prettyOutput() + " to be an input element");
        }
        return isInput;
    }

    /**
     * a method for checking if an element is a select element
     *
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean select(boolean print) {
        boolean isInput = false;
        try {
            WebElement webElement = element.getWebElement();
            if (SELECT.equalsIgnoreCase(webElement.getTagName())) {
                isInput = true;
            }
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected(CHECKING + element.prettyOutput() + " to be a select element");
        }
        return isInput;
    }

    /**
     * a method for checking if an element is a table element
     *
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean table(boolean print) {
        boolean isInput = false;
        try {
            WebElement webElement = element.getWebElement();
            if ("table".equalsIgnoreCase(webElement.getTagName())) {
                isInput = true;
            }
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected(CHECKING + element.prettyOutput() + " to be a table element");
        }
        return isInput;
    }

    /**
     * a method for checking if an element is enabled
     *
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is present or not
     */
    public boolean enabled(boolean print) {
        boolean isEnabled = false;
        try {
            isEnabled = element.getWebElement().isEnabled();
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected(CHECKING + element.prettyOutput() + ENABLED);
        }
        return isEnabled;
    }

    /**
     * a method for checking if an element is checked
     *
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is checked or not
     */
    public boolean checked(boolean print) {
        boolean isChecked = false;
        try {
            isChecked = element.getWebElement().isSelected();
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected(CHECKING + element.prettyOutput() + " to be checked");
        }
        return isChecked;
    }

    /**
     * a method for checking if an element is displayed
     *
     * @param print
     *            - whether or not to printout the action
     * @return boolean: whether the element is displayed or not
     */
    public boolean displayed(boolean print) {
        boolean isDisplayed = false;
        try {
            isDisplayed = element.getWebElement().isDisplayed();
        } catch (NoSuchElementException e) {
            log.error(e);
        }
        if (print) {
            file.recordExpected(CHECKING + element.prettyOutput() + DISPLAYED);
        }
        return isDisplayed;
    }

    /**
     * determine if something is selected from a drop down menu
     * 
     * @param print
     *            - whether or not to printout the action
     * @return Boolean: was something selected in the drop down
     */
    public boolean somethingSelected(boolean print) {
        boolean isSelected = false;
        if (input(false)) {
            WebElement webElement = element.getWebElement();
            if ("input".equalsIgnoreCase(webElement.getTagName())) {
                isSelected = webElement.isSelected();
            } else if (SELECT.equalsIgnoreCase(webElement.getTagName())) {
                Select dropdown = new Select(webElement);
                isSelected = dropdown.getAllSelectedOptions().size() > 0;
            }
        }
        if (print) {
            file.recordExpected(CHECKING + element.prettyOutput() + SELECTED);
        }
        return isSelected;
    }
}