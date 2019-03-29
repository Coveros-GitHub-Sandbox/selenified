package unit;

import com.coveros.selenified.exceptions.InvalidBrowserOptionsException;
import com.coveros.selenified.exceptions.InvalidHTTPException;
import com.coveros.selenified.exceptions.InvalidHubException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.utilities.Property;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static com.coveros.selenified.utilities.Property.*;
import static org.testng.Assert.*;

public class PropertyTest extends SaveProperties {

    @Test
    public void defaultGeneratePdfTest() {
        assertFalse(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfSystemTrueTest() {
        System.setProperty(GENERATE_PDF, "true");
        assertTrue(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfSystemFalseTest() {
        System.setProperty(GENERATE_PDF, "false");
        assertFalse(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfSystemOtherTest() {
        System.setProperty(GENERATE_PDF, "hello");
        assertFalse(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertFalse(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfFilePartialTest() throws IOException {
        createPropertiesFile(GENERATE_PDF);
        assertTrue(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfFileUnsetTest() throws IOException {
        createPropertiesFile(GENERATE_PDF + "=");
        assertTrue(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfFileTrueTest() throws IOException {
        createPropertiesFile(GENERATE_PDF + "=true");
        assertTrue(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfFileFalseTest() throws IOException {
        createPropertiesFile(GENERATE_PDF + "=false");
        assertFalse(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfFileOtherTest() throws IOException {
        createPropertiesFile(GENERATE_PDF + "=hello");
        assertFalse(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfOverrideEmptyTest() throws IOException {
        System.setProperty(GENERATE_PDF, "true");
        createPropertiesFile("");
        assertTrue(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfOverridePartialTest() throws IOException {
        System.setProperty(GENERATE_PDF, "false");
        createPropertiesFile(GENERATE_PDF);
        assertFalse(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfOverrideUnsetTest() throws IOException {
        System.setProperty(GENERATE_PDF, "false");
        createPropertiesFile(GENERATE_PDF + "=");
        assertFalse(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfOverrideTrueTest() throws IOException {
        System.setProperty(GENERATE_PDF, "false");
        createPropertiesFile(GENERATE_PDF + "=true");
        assertFalse(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfOverrideFalseTest() throws IOException {
        System.setProperty(GENERATE_PDF, "true");
        createPropertiesFile(GENERATE_PDF + "=false");
        assertTrue(Property.generatePDF());
    }

    @Test
    public void defaultGeneratePdfOverrideOtherTest() throws IOException {
        System.setProperty(GENERATE_PDF, "true");
        createPropertiesFile(GENERATE_PDF + "=hello");
        assertTrue(Property.generatePDF());
    }

    @Test
    public void defaultPackageResultsTest() {
        assertFalse(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsSystemTrueTest() {
        System.setProperty(PACKAGE_RESULTS, "true");
        assertTrue(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsSystemFalseTest() {
        System.setProperty(PACKAGE_RESULTS, "false");
        assertFalse(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsSystemOtherTest() {
        System.setProperty(PACKAGE_RESULTS, "hello");
        assertFalse(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertFalse(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsFilePartialTest() throws IOException {
        createPropertiesFile(PACKAGE_RESULTS);
        assertTrue(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsFileUnsetTest() throws IOException {
        createPropertiesFile(PACKAGE_RESULTS + "=");
        assertTrue(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsFileTrueTest() throws IOException {
        createPropertiesFile(PACKAGE_RESULTS + "=true");
        assertTrue(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsFileFalseTest() throws IOException {
        createPropertiesFile(PACKAGE_RESULTS + "=false");
        assertFalse(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsFileOtherTest() throws IOException {
        createPropertiesFile(PACKAGE_RESULTS + "=hello");
        assertFalse(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsOverrideEmptyTest() throws IOException {
        System.setProperty(PACKAGE_RESULTS, "true");
        createPropertiesFile("");
        assertTrue(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsOverridePartialTest() throws IOException {
        System.setProperty(PACKAGE_RESULTS, "false");
        createPropertiesFile(PACKAGE_RESULTS);
        assertFalse(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsOverrideUnsetTest() throws IOException {
        System.setProperty(PACKAGE_RESULTS, "false");
        createPropertiesFile(PACKAGE_RESULTS + "=");
        assertFalse(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsOverrideTrueTest() throws IOException {
        System.setProperty(PACKAGE_RESULTS, "false");
        createPropertiesFile(PACKAGE_RESULTS + "=true");
        assertFalse(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsOverrideFalseTest() throws IOException {
        System.setProperty(PACKAGE_RESULTS, "true");
        createPropertiesFile(PACKAGE_RESULTS + "=false");
        assertTrue(Property.packageResults());
    }

    @Test
    public void defaultPackageResultsOverrideOtherTest() throws IOException {
        System.setProperty(PACKAGE_RESULTS, "true");
        createPropertiesFile(PACKAGE_RESULTS + "=hello");
        assertTrue(Property.packageResults());
    }

    @Test
    public void defaultIsHubTest() {
        assertFalse(Property.isHubSet());
    }

    @Test
    public void defaultIsHubSystemEmptyTest() {
        System.setProperty(HUB, "");
        assertFalse(Property.isHubSet());
    }

    @Test
    public void defaultIsHubSystemTest() {
        System.setProperty(HUB, "somehub");
        assertTrue(Property.isHubSet());
    }

    @Test
    public void defaultIsHubFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertFalse(Property.isHubSet());
    }

    @Test
    public void defaultIsHubFilePartialTest() throws IOException {
        createPropertiesFile(HUB);
        assertFalse(Property.isHubSet());
    }

    @Test
    public void defaultIsHubFileUnsetTest() throws IOException {
        createPropertiesFile(HUB + "=");
        assertFalse(Property.isHubSet());
    }

    @Test
    public void defaultIsHubFileTrueTest() throws IOException {
        createPropertiesFile(HUB + "=somehub");
        assertTrue(Property.isHubSet());
    }

    @Test
    public void defaultIsHubOverrideEmptyTest() throws IOException {
        System.setProperty(HUB, "");
        createPropertiesFile(HUB + "=somehub");
        assertFalse(Property.isHubSet());
    }

    @Test
    public void defaultIsHubOverrideTrueTest() throws IOException {
        System.setProperty(HUB, "somehub");
        createPropertiesFile(HUB + "=");
        assertTrue(Property.isHubSet());
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubTest() throws InvalidHubException {
        Property.getHub();
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubSystemEmptyTest() throws InvalidHubException {
        System.setProperty(HUB, "");
        Property.getHub();
    }

    @Test
    public void defaultGetHubSystemTest() throws InvalidHubException {
        System.setProperty(HUB, "somehub");
        assertEquals(Property.getHub(),"somehub");
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubFileEmptyTest() throws IOException {
        createPropertiesFile("");
        Property.getHub();
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubFilePartialTest() throws IOException {
        createPropertiesFile(HUB);
        Property.getHub();
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubFileUnsetTest() throws IOException {
        createPropertiesFile(HUB + "=");
        Property.getHub();
    }

    @Test
    public void defaultGetHubFileTrueTest() throws IOException {
        createPropertiesFile(HUB + "=somehub");
        assertEquals(Property.getHub(), "somehub");
    }

    @Test(expectedExceptions = InvalidHubException.class)
    public void defaultGetHubOverrideEmptyTest() throws IOException {
        System.setProperty(HUB, "");
        createPropertiesFile(HUB + "=somehub");
        Property.getHub();
    }

    @Test
    public void defaultGetHubOverrideTrueTest() throws IOException {
        System.setProperty(HUB, "somehub");
        createPropertiesFile(HUB + "=");
        assertEquals(Property.getHub(), "somehub");
    }

    @Test
    public void defaultIsProxyTest() {
        assertFalse(Property.isProxySet());
    }

    @Test
    public void defaultIsProxySystemEmptyTest() {
        System.setProperty(PROXY, "");
        assertFalse(Property.isProxySet());
    }

    @Test
    public void defaultIsProxySystemTest() {
        System.setProperty(PROXY, "someproxy");
        assertTrue(Property.isProxySet());
    }

    @Test
    public void defaultIsProxyFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertFalse(Property.isProxySet());
    }

    @Test
    public void defaultIsProxyFilePartialTest() throws IOException {
        createPropertiesFile(PROXY);
        assertFalse(Property.isProxySet());
    }

    @Test
    public void defaultIsProxyFileUnsetTest() throws IOException {
        createPropertiesFile(PROXY + "=");
        assertFalse(Property.isProxySet());
    }

    @Test
    public void defaultIsProxyFileTrueTest() throws IOException {
        createPropertiesFile(PROXY + "=someproxy");
        assertTrue(Property.isProxySet());
    }

    @Test
    public void defaultIsProxyOverrideEmptyTest() throws IOException {
        System.setProperty(PROXY, "");
        createPropertiesFile(PROXY + "=someproxy");
        assertFalse(Property.isProxySet());
    }

    @Test
    public void defaultIsProxyOverrideTrueTest() throws IOException {
        System.setProperty(PROXY, "someproxy");
        createPropertiesFile(PROXY + "=");
        assertTrue(Property.isProxySet());
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyTest() throws InvalidProxyException {
        Property.getProxy();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxySystemEmptyTest() throws InvalidProxyException {
        System.setProperty(PROXY, "");
        Property.getProxy();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxySystemNoPortTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy");
        Property.getProxy();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxySystemWithProtocolTest() throws InvalidProxyException {
        System.setProperty(PROXY, "http://someproxy:1234");
        Property.getProxy();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxySystemBadPortTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy:twelve");
        Property.getProxy();
    }

    @Test
    public void defaultGetProxySystemTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy:1234");
        assertEquals(Property.getProxy(), "someproxy:1234");
    }

    @Test
    public void defaultGetProxySystemSpaceTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy:1234 ");
        assertEquals(Property.getProxy(), "someproxy:1234");
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyHostTest() throws InvalidProxyException {
        Property.getProxyHost();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyHostSystemEmptyTest() throws InvalidProxyException {
        System.setProperty(PROXY, "");
        Property.getProxyHost();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyHostSystemNoPortTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy");
        Property.getProxyHost();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyHostSystemWithProtocolTest() throws InvalidProxyException {
        System.setProperty(PROXY, "http://someproxy:1234");
        Property.getProxyHost();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyHostSystemBadPortTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy:twelve");
        Property.getProxyHost();
    }

    @Test
    public void defaultGetProxyHostSystemTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy:1234");
        assertEquals(Property.getProxyHost(), "someproxy");
    }

    @Test
    public void defaultGetProxyHostSystemSpaceTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy:1234 ");
        assertEquals(Property.getProxyHost(), "someproxy");
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyPortTest() throws InvalidProxyException {
        Property.getProxyPort();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyPortSystemEmptyTest() throws InvalidProxyException {
        System.setProperty(PROXY, "");
        Property.getProxyPort();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyPortSystemNoPortTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy");
        Property.getProxyPort();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyPortSystemWithProtocolTest() throws InvalidProxyException {
        System.setProperty(PROXY, "http://someproxy:1234");
        Property.getProxyPort();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyPortSystemBadPortTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy:twelve");
        Property.getProxyPort();
    }

    @Test
    public void defaultGetProxyPortSystemTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy:1234");
        assertEquals(Property.getProxyPort(), 1234);
    }

    @Test
    public void defaultGetProxyPortSystemSpaceTest() throws InvalidProxyException {
        System.setProperty(PROXY, "someproxy:1234 ");
        assertEquals(Property.getProxyPort(), 1234);
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyFileEmptyTest() throws IOException {
        createPropertiesFile("");
        Property.getProxy();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyFilePartialTest() throws IOException {
        createPropertiesFile(PROXY);
        Property.getProxy();
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyFileUnsetTest() throws IOException {
        createPropertiesFile(PROXY + "=");
        Property.getProxy();
    }

    @Test
    public void defaultGetProxyFileTrueTest() throws IOException {
        createPropertiesFile(PROXY + "=someproxy:1234");
        assertEquals(Property.getProxy(), "someproxy:1234");
    }

    @Test
    public void defaultGetProxyFileTrueSpaceTest() throws IOException {
        createPropertiesFile(PROXY + "=someproxy:1234 ");
        assertEquals(Property.getProxy(), "someproxy:1234");
    }

    @Test(expectedExceptions = InvalidProxyException.class)
    public void defaultGetProxyOverrideEmptyTest() throws IOException {
        System.setProperty(PROXY, "");
        createPropertiesFile(PROXY + "=someproxy");
        Property.getProxy();
    }

    @Test
    public void defaultGetProxyOverrideTrueTest() throws IOException {
        System.setProperty(PROXY, "someproxy:1234");
        createPropertiesFile(PROXY + "=");
        assertEquals(Property.getProxy(), "someproxy:1234");
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void defaultGetAppUrlTest(ITestContext context) throws InvalidHTTPException {
        Property.getAppURL(this.getClass().getName(), context);
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void defaultGetAppUrlSystemEmptyTest(ITestContext context) throws InvalidHTTPException {
        System.setProperty(APP_URL, "");
        Property.getAppURL(this.getClass().getName(), context);
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void defaultGetAppUrlSystemInvalidTest(ITestContext context) throws InvalidHTTPException {
        System.setProperty(APP_URL, "httpsomeurl");
        Property.getAppURL(this.getClass().getName(), context);
    }

    @Test
    public void defaultGetAppUrlSystemTest(ITestContext context) throws InvalidHTTPException {
        System.setProperty(APP_URL, "http://www.google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://www.google.com");
        System.setProperty(APP_URL, "https://google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "https://google.com");
        System.setProperty(APP_URL, "www.example.org");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://www.example.org");
        System.setProperty(APP_URL, "google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://google.com");
        System.setProperty(APP_URL, "https://www.google.com?s=4");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "https://www.google.com?s=4");
        System.setProperty(APP_URL, "file:///path/to/file.html");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "file:///path/to/file.html");
        System.setProperty(APP_URL, "192.168.1.1");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://192.168.1.1");
        System.setProperty(APP_URL, "http://192.168.1.1");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://192.168.1.1");
        System.setProperty(APP_URL, "mydomain");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://mydomain");
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void defaultGetAppUrlFileEmptyTest(ITestContext context) throws IOException {
        createPropertiesFile(APP_URL);
        Property.getAppURL(this.getClass().getName(), context);
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void defaultGetAppUrlFileEmpty2Test(ITestContext context) throws IOException {
        createPropertiesFile(APP_URL + "=");
        Property.getAppURL(this.getClass().getName(), context);
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void defaultGetAppUrlFileInvalidTest(ITestContext context) throws IOException {
        createPropertiesFile(APP_URL + "=httpsomeurl");
        Property.getAppURL(this.getClass().getName(), context);
    }

    @Test
    public void defaultGetAppUrlFileTest(ITestContext context) throws IOException {
        createPropertiesFile(APP_URL + "=http://www.google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://www.google.com");
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void defaultGetAppUrlContextEmptyTest(ITestContext context) throws IOException {
        context.setAttribute(this.getClass().getName() + APP_URL, "");
        Property.getAppURL(this.getClass().getName(), context);
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void defaultGetAppUrlContextInvalidTest(ITestContext context) throws IOException {
        context.setAttribute(this.getClass().getName() + APP_URL, "httpsomeurl");
        Property.getAppURL(this.getClass().getName(), context);
    }

    @Test
    public void defaultGetAppUrlContextTest(ITestContext context) throws IOException {
        context.setAttribute(this.getClass().getName() + APP_URL, "http://www.google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://www.google.com");
    }

    @Test
    public void defaultGetAppUrlFileOverrideInvalidTest(ITestContext context) throws IOException {
        createPropertiesFile(APP_URL + "=httpsomeurl");
        context.setAttribute(this.getClass().getName() + APP_URL, "http://www.google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://www.google.com");
    }

    @Test
    public void defaultGetAppUrlFileOverrideTest(ITestContext context) throws IOException {
        createPropertiesFile(APP_URL + "=yahoo.com");
        context.setAttribute(this.getClass().getName() + APP_URL, "http://www.google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://yahoo.com");
    }

    @Test
    public void defaultGetAppUrlSystemOverrideInvalidTest(ITestContext context) throws IOException {
        System.setProperty(APP_URL, "httpsomeurl");
        context.setAttribute(this.getClass().getName() + APP_URL, "http://www.google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://www.google.com");
    }

    @Test
    public void defaultGetAppUrlSystemOverrideTest(ITestContext context) throws IOException {
        System.setProperty(APP_URL, "yahoo.com");
        context.setAttribute(this.getClass().getName() + APP_URL, "http://www.google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://yahoo.com");
    }

    @Test
    public void defaultGetAppUrlAllOverrideInvalidTest(ITestContext context) throws IOException {
        System.setProperty(APP_URL, "httpsomeurl");
        createPropertiesFile(APP_URL + "=httpsomeurl");
        context.setAttribute(this.getClass().getName() + APP_URL, "http://www.google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://www.google.com");
    }

    @Test
    public void defaultGetAppUrlAllOverrideTest(ITestContext context) throws IOException {
        System.setProperty(APP_URL, "yahoo.com");
        createPropertiesFile(APP_URL + "=bing.com");
        context.setAttribute(this.getClass().getName() + APP_URL, "http://www.google.com");
        assertEquals(Property.getAppURL(this.getClass().getName(), context), "http://yahoo.com");
    }

    @Test
    public void defaultGetBrowserTest() {
        assertEquals(Property.getBrowser(), "HTMLUNIT");
    }

    @Test
    public void defaultGetBrowserSystemEmptyTest() {
        System.setProperty(BROWSER, "");
        assertEquals(Property.getBrowser(), "HTMLUNIT");
    }

    @Test
    public void defaultGetBrowserSystemTest() {
        System.setProperty(BROWSER, "somebrowser");
        assertEquals(Property.getBrowser(), "somebrowser");
    }

    @Test
    public void defaultGetBrowserFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertEquals(Property.getBrowser(), "HTMLUNIT");
    }

    @Test
    public void defaultGetBrowserFilePartialTest() throws IOException {
        createPropertiesFile(BROWSER);
        assertEquals(Property.getBrowser(), "HTMLUNIT");
    }

    @Test
    public void defaultGetBrowserFileUnsetTest() throws IOException {
        createPropertiesFile(BROWSER + "=");
        assertEquals(Property.getBrowser(), "HTMLUNIT");
    }

    @Test
    public void defaultGetBrowserFileTrueTest() throws IOException {
        createPropertiesFile(BROWSER + "=somebrowser");
        assertEquals(Property.getBrowser(), "somebrowser");
    }

    @Test
    public void defaultGetBrowserOverrideEmptyTest() throws IOException {
        System.setProperty(BROWSER, "");
        createPropertiesFile(BROWSER + "=somebrowser");
        assertEquals(Property.getBrowser(), "HTMLUNIT");
    }

    @Test
    public void defaultGetBrowserOverrideTrueTest() throws IOException {
        System.setProperty(BROWSER, "somebrowser");
        createPropertiesFile(BROWSER + "=");
        assertEquals(Property.getBrowser(), "somebrowser");
    }


    @Test
    public void defaultHeadlessTest() {
        assertFalse(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessSystemTrueTest() {
        System.setProperty(HEADLESS, "true");
        assertTrue(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessSystemFalseTest() {
        System.setProperty(HEADLESS, "false");
        assertFalse(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessSystemOtherTest() {
        System.setProperty(HEADLESS, "hello");
        assertFalse(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertFalse(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessFilePartialTest() throws IOException {
        createPropertiesFile(HEADLESS);
        assertTrue(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessFileUnsetTest() throws IOException {
        createPropertiesFile(HEADLESS + "=");
        assertTrue(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessFileTrueTest() throws IOException {
        createPropertiesFile(HEADLESS + "=true");
        assertTrue(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessFileFalseTest() throws IOException {
        createPropertiesFile(HEADLESS + "=false");
        assertFalse(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessFileOtherTest() throws IOException {
        createPropertiesFile(HEADLESS + "=hello");
        assertFalse(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessOverrideEmptyTest() throws IOException {
        System.setProperty(HEADLESS, "true");
        createPropertiesFile("");
        assertTrue(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessOverridePartialTest() throws IOException {
        System.setProperty(HEADLESS, "false");
        createPropertiesFile(HEADLESS);
        assertFalse(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessOverrideUnsetTest() throws IOException {
        System.setProperty(HEADLESS, "false");
        createPropertiesFile(HEADLESS + "=");
        assertFalse(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessOverrideTrueTest() throws IOException {
        System.setProperty(HEADLESS, "false");
        createPropertiesFile(HEADLESS + "=true");
        assertFalse(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessOverrideFalseTest() throws IOException {
        System.setProperty(HEADLESS, "true");
        createPropertiesFile(HEADLESS + "=false");
        assertTrue(Property.runHeadless());
    }

    @Test
    public void defaultHeadlessOverrideOtherTest() throws IOException {
        System.setProperty(HEADLESS, "true");
        createPropertiesFile(HEADLESS + "=hello");
        assertTrue(Property.runHeadless());
    }

    @Test
    public void defaultIsOptionsTest() {
        assertFalse(Property.areOptionsSet());
    }

    @Test
    public void defaultIsOptionsSystemEmptyTest() {
        System.setProperty(OPTIONS, "");
        assertFalse(Property.areOptionsSet());
    }

    @Test
    public void defaultIsOptionsSystemTest() {
        System.setProperty(OPTIONS, "someoptions");
        assertTrue(Property.areOptionsSet());
    }

    @Test
    public void defaultIsOptionsFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertFalse(Property.areOptionsSet());
    }

    @Test
    public void defaultIsOptionsFilePartialTest() throws IOException {
        createPropertiesFile(OPTIONS);
        assertFalse(Property.areOptionsSet());
    }

    @Test
    public void defaultIsOptionsFileUnsetTest() throws IOException {
        createPropertiesFile(OPTIONS + "=");
        assertFalse(Property.areOptionsSet());
    }

    @Test
    public void defaultIsOptionsFileTrueTest() throws IOException {
        createPropertiesFile(OPTIONS + "=someoptions");
        assertTrue(Property.areOptionsSet());
    }

    @Test
    public void defaultIsOptionsOverrideEmptyTest() throws IOException {
        System.setProperty(OPTIONS, "");
        createPropertiesFile(OPTIONS + "=someoptions");
        assertFalse(Property.areOptionsSet());
    }

    @Test
    public void defaultIsOptionsOverrideTrueTest() throws IOException {
        System.setProperty(OPTIONS, "someoptions");
        createPropertiesFile(OPTIONS + "=");
        assertTrue(Property.areOptionsSet());
    }

    @Test(expectedExceptions = InvalidBrowserOptionsException.class)
    public void defaultGetOptionsTest() throws InvalidBrowserOptionsException {
        Property.getOptions();
    }

    @Test(expectedExceptions = InvalidBrowserOptionsException.class)
    public void defaultGetOptionsSystemEmptyTest() throws InvalidBrowserOptionsException {
        System.setProperty(OPTIONS, "");
        Property.getOptions();
    }

    @Test
    public void defaultGetOptionsSystemTest() throws InvalidBrowserOptionsException {
        System.setProperty(OPTIONS, "someoptions");
        assertEquals(Property.getOptions(), "someoptions");
    }

    @Test(expectedExceptions = InvalidBrowserOptionsException.class)
    public void defaultGetOptionsFileEmptyTest() throws IOException {
        createPropertiesFile("");
        Property.getOptions();
    }

    @Test(expectedExceptions = InvalidBrowserOptionsException.class)
    public void defaultGetOptionsFilePartialTest() throws IOException {
        createPropertiesFile(OPTIONS);
        Property.getOptions();
    }

    @Test(expectedExceptions = InvalidBrowserOptionsException.class)
    public void defaultGetOptionsFileUnsetTest() throws IOException {
        createPropertiesFile(OPTIONS + "=");
        Property.getOptions();
    }

    @Test
    public void defaultGetOptionsFileTrueTest() throws IOException {
        createPropertiesFile(OPTIONS + "=someoptions");
        assertEquals(Property.getOptions(), "someoptions");
    }

    @Test(expectedExceptions = InvalidBrowserOptionsException.class)
    public void defaultGetOptionsOverrideEmptyTest() throws IOException {
        System.setProperty(OPTIONS, "");
        createPropertiesFile(OPTIONS + "=someoptions");
        Property.getOptions();
    }

    @Test
    public void defaultGetOptionsOverrideTrueTest() throws IOException {
        System.setProperty(OPTIONS, "someoptions");
        createPropertiesFile(OPTIONS + "=");
        assertEquals(Property.getOptions(), "someoptions");
    }


    private void createPropertiesFile(String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(SELENIFIED));
        writer.write(content);
        writer.close();
    }
}