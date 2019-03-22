package com.coveros.selenified.utilities;

import com.coveros.selenified.Browser;
import com.coveros.selenified.exceptions.InvalidHTTPException;
import org.testng.ITestContext;
import org.testng.log4testng.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Property {

    private static final Logger log = Logger.getLogger(Property.class);
    private static final String SELENIFIED = "src/test/resources/selenified.properties";

    private static final String GENERATE_PDF = "generatePDF";
    private static final String PACKAGE_RESULTS = "packageResults";
    private static final String HUB = "hub";
    private static final String PROXY = "proxy";
    public static final String APP_URL = "appURL";
    public static final String BROWSER = "browser";
    public static final String HEADLESS = "headless";
    public static final String OPTIONS = "options";


    private static String getProgramProperty(String property) {
        if (System.getProperty(property) != null) {
            return System.getProperty(property);
        }
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(SELENIFIED)) {
            prop.load(input);
        } catch (IOException e) {
            log.info(e);
        }
        return prop.getProperty(property);
    }

    public static boolean generatePDF() {
        String generatePDF = getProgramProperty(GENERATE_PDF);
        if (generatePDF == null) {
            return false;
        }
        if ("".equals(generatePDF)) {
            return true;
        }
        return "true".equalsIgnoreCase(generatePDF);
    }

    public static boolean packageResults() {
        String packageResults = getProgramProperty(PACKAGE_RESULTS);
        if (packageResults == null) {
            return false;
        }
        if ("".equals(packageResults)) {
            return true;
        }
        return "true".equalsIgnoreCase(packageResults);
    }

    public static boolean isHubSet() {
        String hub = getProgramProperty(HUB);
        return hub != null && !"".equals(hub);
    }

    public static String getHub() {
        if (!isHubSet()) {
            return null;
        }
        return getProgramProperty(HUB);
    }

    public static boolean isProxySet() {
        String proxy = getProgramProperty(PROXY);
        return proxy != null && !"".equals(proxy);
    }

    public static String getProxy() {
        if (!isProxySet()) {
            return null;
        }
        return getProgramProperty(PROXY);
    }


    /**
     * Obtains the application under test, as a URL. If the site was provided as
     * a system property, that value will override whatever was set in the
     * particular test suite. If no site was set, null will be returned, which
     * will causes the tests to error out
     *
     * @param clazz   - the test suite class, used for making threadsafe storage of
     *                application, allowing suites to have independent applications
     *                under test, run at the same time
     * @param context - the TestNG context associated with the test suite, used for
     *                storing app url information
     * @return String: the URL of the application under test
     */
    // TODO - we should align nomenclature. Either AppUrl (from CMD) or TestSite (from java code)
    public static String getAppURL(String clazz, ITestContext context) throws InvalidHTTPException {
        String appURL = null;
        appURL = checkAppUrl(appURL, (String) context.getAttribute(clazz + APP_URL), "The provided app via test case setup '");
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(SELENIFIED)) {
            prop.load(input);
        } catch (IOException e) {
            log.info(e);
        }
        appURL = checkAppUrl(appURL, prop.getProperty(APP_URL), "The provided app via Properties file '");
        appURL = checkAppUrl(appURL, System.getProperty(APP_URL), "The provided app via System Properties '");
        if (appURL != null && !"http://".equals(appURL)) {
            return appURL;
        }
        throw new InvalidHTTPException("There was not a valid app provided to test. Please properly set the 'appURL'");
    }

    private static String checkAppUrl(String originalAppURL, String newAppURL, String s) {
        if (newAppURL != null) {
            if (!newAppURL.toLowerCase().startsWith("http") && !newAppURL.toLowerCase().startsWith("file")) {
                newAppURL = "http://" + newAppURL;
            }
            try {
                new URL(newAppURL);
                return newAppURL;
            } catch (MalformedURLException e) {
                log.error(s + newAppURL + "' is not a valud URL.");
            }
        }
        return originalAppURL;
    }

    public static String getBrowser() {
        String browser = getProgramProperty(BROWSER);
        if (browser == null || "".equals(browser)) {
            browser = Browser.BrowserName.HTMLUNIT.toString();
        }
        return browser;
    }

    public static boolean headless() {
        String headless = getProgramProperty(HEADLESS);
        if (headless == null) {
            return false;
        }
        if ("".equals(headless)) {
            return true;
        }
        return "true".equalsIgnoreCase(headless);
    }

    public static boolean areOptionsSet() {
        String options = getProgramProperty(OPTIONS);
        return options != null && !"".equals(options);
    }

    public static String getOptions() {
        if (!areOptionsSet()) {
            return null;
        }
        return getProgramProperty(OPTIONS);
    }
}
