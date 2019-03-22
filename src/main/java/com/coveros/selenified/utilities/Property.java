package com.coveros.selenified.utilities;

import org.testng.ITestContext;
import org.testng.log4testng.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {

    private static final Logger log = Logger.getLogger(Property.class);
    //TODO - make all of these private, and copy over to UT when we're done
    private static final String SELENIFIED = "src/test/resources/selenified.properties";

    private static final String GENERATE_PDF = "generatePDF";
    private static final String PACKAGE_RESULTS = "packageResults";
    private static final String HUB = "hub";
    private static final String PROXY = "proxy";
    private static final String APP_URL = "appURL";

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
        if( !isHubSet()) {
            return null;
        }
        return getProgramProperty(HUB);
    }

    public static boolean isProxySet() {
        String proxy = getProgramProperty(PROXY);
        return proxy != null && !"".equals(proxy);
    }

    public static String getProxy() {
        if( !isProxySet()) {
            return null;
        }
        return getProgramProperty(PROXY);
    }

    // TODO - we should align nomenclature. Either AppUrl (from CMD) or TestSite (from java code)
    public static String getAppURL(String clazz, ITestContext context) {
        String appURL = getProgramProperty(APP_URL);
        if (appURL == null) {
            appURL = (String) context.getAttribute(clazz + APP_URL);
        }
        if( appURL == null ) {
//            throw new Invalid
        }
        return appURL;
    }
}
