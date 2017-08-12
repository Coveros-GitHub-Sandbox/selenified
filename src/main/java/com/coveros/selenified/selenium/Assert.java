package com.coveros.selenified.selenium;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.OutputFile.Success;

public class Assert {

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private App app;

    // constants
    private static final String ONPAGE = "</b> on the page";
    private static final String NOALERT = "No alert is present on the page";
    private static final String ALERTTEXT = "An alert with text <b>";
    private static final String NOCONFIRMATION = "No confirmation is present on the page";
    private static final String CONFIRMATIONTEXT = "A confirmation with text <b>";
    private static final String NOPROMPT = "No prompt is present on the page";
    private static final String PROMPTTEXT = "A prompt with text <b>";

    private static final String STORED = "</b> is stored for the page";
    private static final String VALUE = "</b> and a value of <b>";
    private static final String COOKIE = "A cookie with the name <b>";
    private static final String NOCOOKIE = "No cookie with the name <b>";

    private static final String TEXT = "The text <b>";
    private static final String PRESENT = "</b> is present on the page";

    public Assert(App app, OutputFile file) {
        this.app = app;
        this.file = file;
    }

    ///////////////////////////////////////////////////////
    // assertions about the page in general
    ///////////////////////////////////////////////////////

    /**
     * compares the actual URL a page is on to the expected URL
     *
     * @param expectedURL
     *            the URL of the page
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int urlEquals(String expectedURL) {
        // file.record the action
        file.recordExpected("Expected to be on page with the URL of <i>" + expectedURL + "</i>");
        String actualURL = app.get().location();
        if (!actualURL.equalsIgnoreCase(expectedURL)) {
            file.recordActual("The page URL  reads <b>" + actualURL + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual("The page URL reads <b>" + actualURL + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the actual title a page is on to the expected title
     *
     * @param expectedTitle
     *            the friendly name of the page
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int titleEquals(String expectedTitle) {
        // file.record the action
        file.recordExpected("Expected to be on page with the title of <i>" + expectedTitle + "</i>");
        String actualTitle = app.get().title();
        if (!actualTitle.equalsIgnoreCase(expectedTitle)) {
            file.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if text is visible on the page
     *
     * @param expectedTexts
     *            the expected text to be visible
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int textPresent(String... expectedTexts) {
        // file.record the action
        int errors = 0;
        for (String expectedText : expectedTexts) {
            file.recordExpected("Expected to find text <b>" + expectedText + "</b> visible on the page");
            // check for the object to the visible
            boolean isPresent = app.is().textPresent(expectedText);
            if (!isPresent) {
                file.recordActual(TEXT + expectedText + "</b> is not present on the page", Success.FAIL);
                file.addError();
                errors++;
            } else {
                file.recordActual(TEXT + expectedText + PRESENT, Success.PASS);
            }
        }
        return errors;
    }

    /**
     * checks to see if text is not visible on the page
     *
     * @param expectedTexts
     *            the expected text to be invisible
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int textNotPresent(String... expectedTexts) {
        // file.record the action
        int errors = 0;
        for (String expectedText : expectedTexts) {
            file.recordExpected("Expected not to find text <b>" + expectedText + "</b> visible on the page");
            // check for the object to the visible
            boolean isPresent = app.is().textPresent(expectedText);
            if (isPresent) {
                file.recordActual(TEXT + expectedText + PRESENT, Success.FAIL);
                file.addError();
                errors++;
            } else {
                file.recordActual(TEXT + expectedText + "</b> is not present on the page", Success.PASS);
            }
        }
        return errors;
    }

    ///////////////////////////////////////////////////////
    // assertions about pop-ups
    ///////////////////////////////////////////////////////

    /**
     * checks to see if an alert is present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int alertPresent() {
        // file.record the action
        file.recordExpected("Expected to find an alert on the page");
        // check for the object to the visible
        String alert = "";
        boolean isAlertPresent = app.is().alertPresent();
        if (isAlertPresent) {
            alert = app.get().alert();
        }
        if (!isAlertPresent) {
            file.recordActual(NOALERT, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if an alert is not present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int alertNotPresent() {
        // file.record the action
        file.recordExpected("Expected not to find an alert on the page");
        // check for the object to the visible
        boolean isAlertPresent = app.is().alertPresent();
        if (isAlertPresent) {
            file.recordActual("An alert is present on the page", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(NOALERT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if an alert is correct on the page
     *
     * @param expectedAlertText
     *            the expected text of the alert
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int alertPresent(String expectedAlertText) {
        // file.record the action
        file.recordExpected("Expected to find alert with the text <b>" + expectedAlertText + ONPAGE);
        // check for the object to the visible
        String alert = "";
        boolean isAlertPresent = app.is().alertPresent();
        if (isAlertPresent) {
            alert = app.get().alert();
        }
        if (!isAlertPresent) {
            file.recordActual(NOALERT, Success.FAIL);
            file.addError();
            return 1;
        }
        Pattern patt = Pattern.compile(expectedAlertText);
        Matcher m = patt.matcher(alert);
        boolean isCorrect;
        if (expectedAlertText.contains("\\")) {
            isCorrect = m.matches();
        } else {
            isCorrect = alert.equals(expectedAlertText);
        }
        if (!isCorrect) {
            file.recordActual(ALERTTEXT + alert + PRESENT, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a confirmation is present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int confirmationPresent() {
        // file.record the action
        file.recordExpected("Expected to find a confirmation on the page");
        // check for the object to the visible
        String confirmation = "";
        boolean isConfirmationPresent = app.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = app.get().confirmation();
        }
        if (!isConfirmationPresent) {
            file.recordActual(NOCONFIRMATION, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a confirmation is not present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int confirmationNotPresent() {
        // file.record the action
        file.recordExpected("Expected to find a confirmation on the page");
        // check for the object to the visible
        boolean isConfirmationPresent = app.is().confirmationPresent();
        if (isConfirmationPresent) {
            file.recordActual("A confirmation is present on the page", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(NOCONFIRMATION, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a confirmation is correct on the page
     *
     * @param expectedConfirmationText
     *            the expected text of the confirmation
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int confirmationPresent(String expectedConfirmationText) {
        // file.record the action
        file.recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmationText + ONPAGE);
        // check for the object to the visible
        String confirmation = "";
        boolean isConfirmationPresent = app.is().confirmationPresent();
        if (isConfirmationPresent) {
            confirmation = app.get().confirmation();
        }
        if (!isConfirmationPresent) {
            file.recordActual(NOCONFIRMATION, Success.FAIL);
            file.addError();
            return 1;
        }
        if (!expectedConfirmationText.equals(confirmation)) {
            file.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a prompt is present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int promptPresent() {
        // file.record the action
        file.recordExpected("Expected to find prompt on the page");
        // check for the object to the visible
        String prompt = "";
        boolean isPromptPresent = app.is().promptPresent();
        if (isPromptPresent) {
            prompt = app.get().prompt();
        }
        if (!isPromptPresent) {
            file.recordActual(NOPROMPT, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a prompt is not present on the page
     *
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int promptNotPresent() {
        // file.record the action
        file.recordExpected("Expected not to find prompt on the page");
        // check for the object to the visible
        boolean isPromptPresent = app.is().promptPresent();
        if (isPromptPresent) {
            file.recordActual("A prompt is present on the page", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(NOPROMPT, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a prompt is correct on the page
     *
     * @param expectedPromptText
     *            the expected text of the confirmation
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int promptPresent(String expectedPromptText) {
        // file.record the action
        file.recordExpected("Expected to find prompt with the text <b>" + expectedPromptText + ONPAGE);
        // check for the object to the visible
        String prompt = "";
        boolean isPromptPresent = app.is().promptPresent();
        if (isPromptPresent) {
            prompt = app.get().prompt();
        }
        if (!isPromptPresent) {
            file.recordActual(NOPROMPT, Success.FAIL);
            file.addError();
            return 1;
        }
        if (!expectedPromptText.equals(prompt)) {
            file.recordActual(PROMPTTEXT + prompt + PRESENT, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
        return 0;
    }

    ///////////////////////////////////////////////////////
    // assertions about cookies
    ///////////////////////////////////////////////////////

    /**
     * checks to see if a cookie is present on the page
     *
     * @param expectedCookieName
     *            the name of the cookie
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int cookieExists(String expectedCookieName) {
        // file.record the action
        file.recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + STORED);
        // check for the object to the visible
        String cookieValue = "";
        boolean isCookiePresent = app.is().cookiePresent(expectedCookieName);
        if (isCookiePresent) {
            cookieValue = app.get().cookieValue(expectedCookieName);
        }
        if (!isCookiePresent) {
            file.recordActual(NOCOOKIE + expectedCookieName + STORED, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(COOKIE + expectedCookieName + VALUE + cookieValue + STORED, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a cookie is not present on the page
     *
     * @param unexpectedCookieName
     *            the name of the cookie
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int cookieNotExists(String unexpectedCookieName) {
        // file.record the action
        file.recordExpected("Expected to find no cookie with the name <b>" + unexpectedCookieName + STORED);
        // check for the object to the visible
        boolean isCookiePresent = app.is().cookiePresent(unexpectedCookieName);
        if (isCookiePresent) {
            file.recordActual(COOKIE + unexpectedCookieName + STORED, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(NOCOOKIE + unexpectedCookieName + STORED, Success.PASS);
        return 0;
    }

    /**
     * checks to see if a cookie is correct for the page
     *
     * @param cookieName
     *            the name of the cookie
     * @param expectedCookieValue
     *            the expected value of the cookie
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int cookieExists(String cookieName, String expectedCookieValue) {
        // file.record the action
        file.recordExpected(
                "Expected to find cookie with the name <b>" + cookieName + VALUE + expectedCookieValue + STORED);
        // check for the object to the visible
        String cookieValue = "";
        boolean isCookiePresent = app.is().cookiePresent(cookieName);
        if (isCookiePresent) {
            cookieValue = app.get().cookieValue(cookieName);
        }
        if (!isCookiePresent) {
            file.recordActual(NOCOOKIE + cookieName + STORED, Success.FAIL);
            file.addError();
            return 1;
        }
        if (!cookieValue.equals(expectedCookieValue)) {
            file.recordActual(COOKIE + cookieName + "</b> is stored for the page, but the value " + "of the cookie is "
                    + cookieValue, Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(COOKIE + cookieName + VALUE + cookieValue + STORED, Success.PASS);
        return 0;
    }
}