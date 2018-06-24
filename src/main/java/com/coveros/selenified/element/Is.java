/*
 * Copyright 2018 Coveros, Inc.
 * 
 * This file is part of Selenified.
 * 
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy 
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on 
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY 
 * KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations 
 * under the License.
 */

package com.coveros.selenified.element;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

/**
 * Is retrieves information about a particular element. A boolean is always
 * returning, indicating if an object is present or not
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Is {

    private static final Logger log = Logger.getLogger(Is.class);

    // what element are we trying to interact with on the page
    private final Element element;

    // constants
    private static final String SELECT = "select";

    public Is(Element element) {
        this.element = element;
    }

    // ////////////////////////////////////
    // checking element availability
    // ////////////////////////////////////

    /**
     * Determines whether the element is present or not.
     *
     * @return Boolean: whether the element is present or not
     */
    public boolean present() {
        boolean isPresent = false;
        try {
            element.getWebElement().getText();
            isPresent = true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            log.info(e);
        }
        return isPresent;
    }

    /**
     * Determines whether the element is an input or not. An input could be an
     * input element, a textarea, or a select
     *
     * @return Boolean: whether the element is an input or not
     */
    public boolean input() {
        boolean isInput = false;
        try {
            WebElement webElement = element.getWebElement();
            if ("input".equalsIgnoreCase(webElement.getTagName()) ||
                    "textarea".equalsIgnoreCase(webElement.getTagName()) ||
                    SELECT.equalsIgnoreCase(webElement.getTagName())) {
                isInput = true;
            }
        } catch (NoSuchElementException e) {
            log.info(e);
        }
        return isInput;
    }

    /**
     * Determines whether the element is a select or not.
     *
     * @return Boolean: whether the element is an input or not
     */
    public boolean select() {
        boolean isSelect = false;
        try {
            WebElement webElement = element.getWebElement();
            if (SELECT.equalsIgnoreCase(webElement.getTagName())) {
                isSelect = true;
            }
        } catch (NoSuchElementException e) {
            log.info(e);
        }
        return isSelect;
    }

    /**
     * Determines whether the element is a table or not.
     *
     * @return Boolean: whether the element is an input or not
     */
    public boolean table() {
        boolean isTable = false;
        try {
            WebElement webElement = element.getWebElement();
            if ("table".equalsIgnoreCase(webElement.getTagName())) {
                isTable = true;
            }
        } catch (NoSuchElementException e) {
            log.info(e);
        }
        return isTable;
    }

    /**
     * Determines whether the element is enabled or not.
     *
     * @return Boolean: whether the element is present or not
     */
    public boolean enabled() {
        boolean isEnabled = false;
        try {
            isEnabled = element.getWebElement().isEnabled();
        } catch (NoSuchElementException e) {
            log.info(e);
        }
        return isEnabled;
    }

    /**
     * Determines whether the element is checked or not.
     *
     * @return Boolean: whether the element is checked or not
     */
    public boolean checked() {
        boolean isChecked = false;
        try {
            isChecked = element.getWebElement().isSelected();
        } catch (NoSuchElementException e) {
            log.info(e);
        }
        return isChecked;
    }

    /**
     * Determines whether the element is displayed or not.
     *
     * @return Boolean: whether the element is displayed or not
     */
    public boolean displayed() {
        boolean isDisplayed = false;
        try {
            isDisplayed = element.getWebElement().isDisplayed();
        } catch (NoSuchElementException e) {
            log.info(e);
        }
        return isDisplayed;
    }

    /**
     * Determines whether the element has something selected or not. Checkboxes,
     * radio buttons, and selects could all have something selected. Other
     * elements will default to false.
     *
     * @return Boolean: is something selected or not
     */
    public boolean somethingSelected() {
        boolean isSelected = false;
        if (input()) {
            WebElement webElement = element.getWebElement();
            if ("input".equalsIgnoreCase(webElement.getTagName())) {
                isSelected = webElement.isSelected();
            } else if (SELECT.equalsIgnoreCase(webElement.getTagName())) {
                Select dropdown = new Select(webElement);
                isSelected = !dropdown.getAllSelectedOptions().isEmpty();
            }
        }
        return isSelected;
    }
}