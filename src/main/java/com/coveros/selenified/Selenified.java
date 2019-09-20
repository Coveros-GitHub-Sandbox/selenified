/*
 * Copyright 2019 Coveros, Inc.
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

package com.coveros.selenified;

import com.coveros.selenified.Browser.BrowserUse;
import com.coveros.selenified.application.App;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.HTTP.ContentType;
import com.coveros.selenified.utilities.*;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

import static com.coveros.selenified.utilities.Property.*;
import static com.coveros.selenified.utilities.Reporter.ENABLED_LOGS;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Selenified contains all of the elements to setup the test suite, and to start
 * and finish your tests. The site under test should either be set in
 * the @BeforeClass, as there is no default site set for testing. If not, this
 * site can be passed in as a system parameter. Before each suite is run, the
 * system variables are gathered, to set the browser, test site, proxy, hub,
 * etc. This class should be extended by each test class to allow for simple
 * execution of tests.
 *
 * By default each test run will launch a selenium browser, and open the defined
 * test site. If no browser is needed for the test, override the startTest
 * method. Similarly, if you don't want a URL to initially load, override the
 * startTest method.
 *
 * @author Max Saperstone
 * @version 3.2.1
 * @lastupdate 8/19/2019
 */
@Listeners({Listener.class, ReportOverview.class, Transformer.class})
public class Selenified {

    private static final Logger log = Logger.getLogger(Selenified.class);
    private static final String SERVICES_USER = "ServicesUser";
    private static final String SERVICES_PASS = "ServicesPass";

    // some passed in system browser capabilities
    private static final List<Capabilities> CAPABILITIES_LIST = new ArrayList<>();
    private static String buildName;

    // for individual tests
    private final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<DesiredCapabilities> desiredCapabilitiesThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Reporter> reporterThreadLocal = new ThreadLocal<>();
    protected final ThreadLocal<App> apps = new ThreadLocal<>();
    protected final ThreadLocal<Call> calls = new ThreadLocal<>();

    // constants
    public static final String SESSION_ID = "SessionId";
    public static final String REPORTER = "reporter";
    private static final String INVOCATION_COUNT = "InvocationCount";
    private static final String ERRORS_CHECK = " errors";
    private static final String DESIRED_CAPABILITIES = "DesiredCapabilities";

    /**
     * Sets the URL of the application under test. If the site was provided as a
     * system property, this method ignores the passed in value, and uses the
     * system property.
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @param siteURL - the URL of the application under test
     */
    protected static void setAppURL(Selenified clazz, ITestContext context, String siteURL) {
        context.setAttribute(clazz.getClass().getName() + APP_URL, siteURL);
    }

    /**
     * Obtains the version of the current test suite being executed. If no
     * version was set, null will be returned
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @return String: the version of the current test being executed
     */
    private String getVersion(String clazz, ITestContext context) {
        return (String) context.getAttribute(clazz + "Version");
    }

    /**
     * Sets the version of the current test suite being executed.
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @param version - the version of the test suite
     */
    protected static void setVersion(Selenified clazz, ITestContext context, String version) {
        context.setAttribute(clazz.getClass().getName() + "Version", version);
    }

    /**
     * Obtains the author of the current test suite being executed. If no author
     * was set, null will be returned
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @return String: the author of the current test being executed
     */
    private String getAuthor(String clazz, ITestContext context) {
        return (String) context.getAttribute(clazz + "Author");
    }

    /**
     * Sets the author of the current test suite being executed.
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @param author  - the author of the test suite
     */
    protected static void setAuthor(Selenified clazz, ITestContext context, String author) {
        context.setAttribute(clazz.getClass().getName() + "Author", author);
    }


    /**
     * Sets the content type for the web services calls for each instance of the test suite being executed.
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @return ContentType - the contentType we want to set for this particular test suite
     */
    protected static ContentType getContentType(String clazz, ITestContext context) {
        return (ContentType) context.getAttribute(clazz + "ContentType");
    }

    /**
     * Sets the content type for the web services calls for each instance of the test suite being executed.
     *
     * @param clazz       - the test suite class, used for making threadsafe storage of
     *                    application, allowing suites to have independent applications
     *                    under test, run at the same time
     * @param context     - the TestNG context associated with the test suite, used for
     *                    storing app url information
     * @param contentType - what contentType do we want to set for this particular test suite
     */
    protected static void setContentType(Selenified clazz, ITestContext context, ContentType contentType) {
        context.setAttribute(clazz.getClass().getName() + "ContentType", contentType);
    }

    /**
     * Obtains the additional headers of the current test suite being executed. If no additional headers
     * were set, null will be returned
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @return Map: the key-pair values of the headers of the current test being executed
     */
    private static Map<String, Object> getExtraHeaders(String clazz, ITestContext context) {
        return (Map<String, Object>) context.getAttribute(clazz + "Headers");
    }

    /**
     * Sets any additional headers for the web services calls for each instance of the test suite being executed.
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @param headers - what headers do we want to set for this particular test suite
     */
    protected static void addHeaders(Selenified clazz, ITestContext context, Map<String, Object> headers) {
        context.setAttribute(clazz.getClass().getName() + "Headers", headers);
    }

    /**
     * Sets any additional capabilities desired for the browsers. Things like enabling javascript, accepting insecure certs. etc
     * can all be added here on a per test class basis.
     *
     * @param clazz           - the test suite class, used for making threadsafe storage of
     *                        application, allowing suites to have independent applications
     *                        under test, run at the same time
     * @param context         - the TestNG context associated with the test suite, used for
     *                        storing app url information
     * @param capabilityName  - the capability name to be added
     * @param capabilityValue - the capability value to be set
     */
    protected static void addAdditionalDesiredCapabilities(Selenified clazz, ITestContext context, String capabilityName, Object capabilityValue) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        if (context.getAttributeNames().contains(clazz.getClass().getName() + DESIRED_CAPABILITIES)) {
            desiredCapabilities = (DesiredCapabilities) context.getAttribute(clazz.getClass().getName() + DESIRED_CAPABILITIES);
        }
        desiredCapabilities.setCapability(capabilityName, capabilityValue);
        context.setAttribute(clazz.getClass().getName() + DESIRED_CAPABILITIES, desiredCapabilities);
    }

    /**
     * Retrieves the additional desired capabilities set by the current class for the browsers
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @return DesiredCapabilities
     */
    private static DesiredCapabilities getAdditionalDesiredCapabilities(String clazz, ITestContext context) {
        return (DesiredCapabilities) context.getAttribute(clazz + DESIRED_CAPABILITIES);
    }

    /**
     * Obtains the web services username provided for the current test suite being executed. Anything passed in from
     * the command line will first be taken, to override any other values. Next, values being set in the classes will
     * be checked for. If neither of these are set, an empty string will be returned
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @return String: the web services username to use for authentication
     */
    private static String getServiceUserCredential(String clazz, ITestContext context) {
        if (System.getenv("SERVICES_USER") != null) {
            return System.getenv("SERVICES_USER");
        }
        if (context.getAttribute(clazz + SERVICES_USER) != null) {
            return (String) context.getAttribute(clazz + SERVICES_USER);
        } else {
            return "";
        }
    }

    /**
     * Obtains the web services password provided for the current test suite being executed. Anything passed in from
     * the command line will first be taken, to override any other values. Next, values being set in the classes will
     * be checked for. If neither of these are set, an empty string will be returned
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @return String: the web services password to use for authentication
     */
    private static String getServicePassCredential(String clazz, ITestContext context) {
        if (System.getenv("SERVICES_PASS") != null) {
            return System.getenv("SERVICES_PASS");
        }
        if (context.getAttribute(clazz + SERVICES_PASS) != null) {
            return (String) context.getAttribute(clazz + SERVICES_PASS);
        } else {
            return "";
        }
    }

    /**
     * Sets any additional headers for the web services calls for each instance of the test suite being executed.
     *
     * @param clazz        - the test suite class, used for making threadsafe storage of
     *                     application, allowing suites to have independent applications
     *                     under test, run at the same time
     * @param context      - the TestNG context associated with the test suite, used for
     *                     storing app url information
     * @param servicesUser - the username required for authentication
     * @param servicesPass - the password required for authentication
     */
    protected static void setCredentials(Selenified clazz, ITestContext context, String servicesUser,
                                         String servicesPass) {
        context.setAttribute(clazz.getClass().getName() + SERVICES_USER, servicesUser);
        context.setAttribute(clazz.getClass().getName() + SERVICES_PASS, servicesPass);
    }

    /**
     * Runs once before any of the tests run, to parse and setup the static
     * passed information such as browsers, proxy, hub, etc
     *
     * @throws InvalidBrowserException If a browser that is not one specified in the
     *                                 Selenium.Browser class is used, this exception will be thrown
     */
    @BeforeSuite(alwaysRun = true)
    protected void beforeSuite() throws InvalidBrowserException, InvalidProxyException {
        MasterSuiteSetupConfigurator.getInstance().doSetup();
    }

    /**
     * Before any tests run, setup the logging and test details. If a selenium
     * test is being run, it sets up the driver as well
     *
     * @param dataProvider - any objects that are being passed to the tests to loop
     *                     through as variables
     * @param method       - what is the method that is being run. the test name will be
     *                     extracted from this
     * @param test         - was the is context associated with this test suite. suite
     *                     information will be extracted from this
     * @param result       - where are the test results stored. browser information will
     *                     be kept here
     */
    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) throws IOException {
        startTest(dataProvider, method, test, result, BrowserUse.LOAD);
    }

    /**
     * Gathers all of the testing information, and setup up the logging. If a
     * selenium test is running, also sets up the webdriver object
     *
     * @param dataProvider - any objects that are being passed to the tests to loop
     *                     through as variables
     * @param method       - what is the method that is being run. the test name will be
     *                     extracted from this
     * @param test         - was the is context associated with this test suite. suite
     *                     information will be extracted from this
     * @param result       - where are the test results stored. browser information will
     *                     be kept here
     * @param selenium     - is this a selenium test. if so, the webdriver content will
     *                     be setup
     */
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result,
                             BrowserUse selenium) throws IOException {
        String testName = TestCase.getTestName(method, dataProvider);
        String outputDir = test.getOutputDirectory();
        String extClass = method.getDeclaringClass().getName();
        // get annotation information
        Test annotation = method.getAnnotation(Test.class);
        String description = annotation.description();

        while (test.getAttribute(testName + INVOCATION_COUNT) == null) {
            test.setAttribute(testName + INVOCATION_COUNT, 0);
        }
        int invocationCount = (int) test.getAttribute(testName + INVOCATION_COUNT);
        Capabilities capabilities = Selenified.CAPABILITIES_LIST.get(invocationCount);
        // setup our browser instance
        if (!selenium.useBrowser()) {
            capabilities = new Capabilities(new Browser("None"));
        } else if (getAdditionalDesiredCapabilities(extClass, test) != null) {
            capabilities = new Capabilities(capabilities.getBrowser());
            capabilities.addExtraCapabilities(getAdditionalDesiredCapabilities(extClass, test));
        }
        Browser browser = capabilities.getBrowser();
        this.browserThreadLocal.set(browser);
        result.setAttribute(BROWSER, browser);
        // if a group indicates an invalid browser, skip the test
        if (Listener.skipTest(browser, result)) {
            return;
        }
        // setup the rest of the browser details
        capabilities.setInstance(invocationCount);
        DesiredCapabilities desiredCapabilities = capabilities.getDesiredCapabilities();
        desiredCapabilities.setCapability("name", testName);
        desiredCapabilities.setCapability("tags", Arrays.asList(result.getMethod().getGroups()));
        desiredCapabilities.setCapability("build", buildName);
        this.desiredCapabilitiesThreadLocal.set(desiredCapabilities);
        // setup the reporter
        Reporter reporter =
                new Reporter(outputDir, testName, capabilities, Property.getAppURL(extClass, test),
                        test.getName(), Arrays.asList(result.getMethod().getGroups()),
                        getAuthor(extClass, test), getVersion(extClass, test), description);
        this.reporterThreadLocal.set(reporter);
        result.setAttribute(REPORTER, reporter);
        // start creating instances of our app to use for testing
        if (selenium.useBrowser()) {
            App app = new App(capabilities, reporter);
            this.apps.set(app);
            reporter.setApp(app);
            setupScreenSize(app);
            if (selenium.loadPage()) {
                loadInitialPage(app, Property.getAppURL(extClass, test), reporter);
            }
            if (Sauce.isSauce()) {
                result.setAttribute(SESSION_ID, ((RemoteWebDriver) app.getDriver()).getSessionId());
            }
        } else {
            this.apps.set(null);
        }
        // start creating instance of our api to use for testing
        HTTP http = new HTTP(reporter, Property.getAppURL(extClass, test), getServiceUserCredential(extClass, test),
                getServicePassCredential(extClass, test));
        Call call = new Call(http, getExtraHeaders(extClass, test));
        if (getContentType(extClass, test) != null) {
            call.setContentType(getContentType(extClass, test));
        }
        this.calls.set(call);
    }

    /**
     * Loads the initial app specified by the url, and ensures the app loads
     * successfully
     *
     * @param app      - the application to be tested, contains all control elements
     * @param url      - the initial url to load
     * @param reporter - the output file to write everything to
     */
    private void loadInitialPage(App app, String url, Reporter reporter) {
        String startingPage = "The initial url of <i>";
        String act = "Opening new browser and loading up initial url";
        String expected = startingPage + url + "</i> will successfully load";

        if (app != null) {
            try {
                app.getDriver().get(url);
                if (!app.get().url().contains(url)) {
                    reporter.fail(act, expected,
                            startingPage + app.get().url() + "</i> loaded instead of <i>" + url + "</i>");
                    return;
                }
                reporter.pass(act, expected, startingPage + url + "</i> loaded successfully");
            } catch (Exception e) {
                log.warn(e);
                reporter.fail(act, expected, startingPage + url + "</i> did not load successfully");
            }
            app.acceptCertificate();
        }
    }

    /**
     * After each test is completed, the test is closed out, and the test
     * counter is incremented
     *
     * @param dataProvider - any objects that are being passed to the tests to loop
     *                     through as variables
     * @param method       - what is the method that is being run. the test name will be
     *                     extracted from this
     * @param test         - was the is context associated with this test suite. suite
     *                     information will be extracted from this
     * @param result       - where are the test results stored. browser information will
     *                     be kept here
     */
    @AfterMethod(alwaysRun = true)
    protected void endTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        String testName = TestCase.getTestName(method, dataProvider);
        int invocationCount = 0;
        if (test.getAttributeNames().contains(testName + INVOCATION_COUNT)) {
            invocationCount = (int) test.getAttribute(testName + INVOCATION_COUNT);
        }
        if (this.apps.get() != null) {
            for (String logType : ENABLED_LOGS) {
                getLog(logType);
            }
            this.apps.get().killDriver();
        }
        test.setAttribute(testName + INVOCATION_COUNT, invocationCount + 1);
    }

    private void getLog(String logType) {
        try {
            LogEntries logEntries = this.apps.get().getDriver().manage().logs().get(logType);
            if (logEntries != null && !logEntries.getAll().isEmpty()) {
                this.reporterThreadLocal.get().addLogs(logType, logEntries);
            }
        } catch (Exception e) {
            log.debug(e);
        }
    }

    /**
     * Concludes each test case. This should be run as the last time of
     * each @Test. It will close out the output logging file, and count any
     * errors that were encountered during the test, and fail the test if any
     * errors were encountered
     */
    protected void finish() {
        finish(0);
    }

    /**
     * Concludes each test case. This should be run as the last time of
     * each @Test. It will close out the output logging file, and count any
     * errors that were encountered during the test, and assert that the number
     * of errors that occurred equals the provided number of errors.
     *
     * @param errors - number of expected errors from the test
     */
    protected void finish(int errors) {
        Reporter reporter = this.reporterThreadLocal.get();
        assertEquals("Test Step Errors Mismatch", errors + ERRORS_CHECK,
                Integer.toString(reporter.getFails()) + ERRORS_CHECK);
    }

    /**
     * Sets up the initial size of the browser. Checks for the passed in parameter of screensize. If set to width
     * x height, sets the browser to that size; if set to maximum, maximizes the browser.
     *
     * @param app - the application to be tested, contains all control elements
     */
    private void setupScreenSize(App app) {
        String screensize = app.getBrowser().getScreensize();
        if (screensize != null) {
            if (screensize.matches("(\\d+)x(\\d+)")) {
                int width = Integer.parseInt(screensize.split("x")[0]);
                int height = Integer.parseInt(screensize.split("x")[1]);
                app.resize(width, height);
            } else if ("maximum".equalsIgnoreCase(screensize)) {
                app.maximize();
            } else {
                log.error("Provided screensize does not match expected pattern");
            }
        }
    }

    /**
     * Setups up the initial system for test. As a singleton, this is only done
     * once per test suite.
     *
     * @author max
     */
    private static class MasterSuiteSetupConfigurator {
        private static MasterSuiteSetupConfigurator instance;
        private boolean wasInvoked = false;

        private MasterSuiteSetupConfigurator() {
        }

        /**
         * Runs once before any of the tests run, to parse and setup the static
         * passed information such as browsers, proxy, hub, etc
         *
         * @return null
         */
        static MasterSuiteSetupConfigurator getInstance() {
            if (instance != null) {
                return instance;
            }
            instance = new MasterSuiteSetupConfigurator();
            return instance;
        }

        /**
         * Runs once before any of the tests run, to parse and setup the static
         * passed information such as browsers, proxy, hub, etc
         *
         * @throws InvalidBrowserException If a browser that is not one specified in the
         *                                 Selenium.Browser class is used, this exception will be
         *                                 thrown
         */
        void doSetup() throws InvalidBrowserException, InvalidProxyException {
            if (wasInvoked) {
                return;
            }
            setupTestParameters();
            wasInvoked = true;
            // downgrade our logging
            java.util.logging.Logger.getLogger("io.github").setLevel(Level.SEVERE);
        }

        /**
         * Obtains passed in browser information, and sets up the required
         * capabilities
         *
         * @throws InvalidBrowserException If a browser that is not one specified in the
         *                                 Selenium.Browser class is used, this exception will be
         *                                 thrown
         */
        private static void setupTestParameters() throws InvalidBrowserException, InvalidProxyException {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss - ");
            Date date = new Date();
            StringBuilder buildNameSB = new StringBuilder(dateFormat.format(date));
            List<Browser> browsers = getBrowserInput();
            for (Browser browser : browsers) {
                Selenified.CAPABILITIES_LIST.add(new Capabilities(browser));
                buildNameSB.append(browser.getDetails()).append(", ");
            }
            if (isBuildNameSet()) {
                buildName = getBuildName();
            } else {
                buildName = buildNameSB.toString().substring(0, buildNameSB.length() - 2);
            }
        }

        /**
         * looks at the browser information passed in, and loads that data into a
         * list
         *
         * @return List: a list of all browser
         * @throws InvalidBrowserException If a browser that is not one specified in the
         *                                 Selenium.Browser class is used, this exception will be thrown
         */
        private static List<Browser> getBrowserInput() throws InvalidBrowserException {
            List<Browser> browsers = new ArrayList<>();
            String[] browserInput = Property.getBrowser().split(",");
            for (String singleBrowserInput : browserInput) {
                browsers.add(new Browser(singleBrowserInput));
            }
            return browsers;
        }
    }
}