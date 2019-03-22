package unit;

import com.coveros.selenified.utilities.Property;
import org.testng.annotations.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.TestCase.assertNull;
import static org.testng.Assert.*;

public class PropertyTest {

    private static final String SELENIFIED = "src/test/resources/selenified.properties";

    static final String GENERATE_PDF = "generatePDF";
    static final String PACKAGE_RESULTS = "packageResults";
    static final String HUB = "hub";
    static final String PROXY = "proxy";

    private String setGeneratePDF = null;
    private String setPackageResults = null;
    private String setHub = null;
    private String setProxy = null;

    @BeforeClass(alwaysRun = true)
    public void saveProperties() {
        if (System.getProperty(GENERATE_PDF) != null) {
            setGeneratePDF = System.getProperty(GENERATE_PDF);
        }
        if (System.getProperty(PACKAGE_RESULTS) != null) {
            setPackageResults = System.getProperty(PACKAGE_RESULTS);
        }
        if (System.getProperty(HUB) != null) {
            setHub = System.getProperty(HUB);
        }
        if (System.getProperty(PROXY) != null) {
            setProxy = System.getProperty(PROXY);
        }
    }

    @AfterClass(alwaysRun = true)
    public void restoreProperties() {
        if (setGeneratePDF != null) {
            System.setProperty(GENERATE_PDF, setGeneratePDF);
        }
        if (setPackageResults != null) {
            System.setProperty(PACKAGE_RESULTS, setPackageResults);
        }
        if (setHub != null) {
            System.setProperty(HUB, setHub);
        }
        if (setProxy != null) {
            System.setProperty(PROXY, setProxy);
        }
    }

    @BeforeMethod(alwaysRun = true)
    @AfterMethod(alwaysRun = true)
    public void clearProperties() {
        System.clearProperty(GENERATE_PDF);
        System.clearProperty(PACKAGE_RESULTS);
        System.clearProperty(HUB);
        System.clearProperty(PROXY);

        if (new File(SELENIFIED).exists()) {
            new File(SELENIFIED).delete();
        }
    }

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

    @Test
    public void defaultGetHubTest() {
        assertNull(Property.getHub());
    }

    @Test
    public void defaultGetHubSystemEmptyTest() {
        System.setProperty(HUB, "");
        assertNull(Property.getHub());
    }

    @Test
    public void defaultGetHubSystemTest() {
        System.setProperty(HUB, "somehub");
        assertEquals(Property.getHub(), "somehub");
    }

    @Test
    public void defaultGetHubFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertNull(Property.getHub());
    }

    @Test
    public void defaultGetHubFilePartialTest() throws IOException {
        createPropertiesFile(HUB);
        assertNull(Property.getHub());
    }

    @Test
    public void defaultGetHubFileUnsetTest() throws IOException {
        createPropertiesFile(HUB + "=");
        assertNull(Property.getHub());
    }

    @Test
    public void defaultGetHubFileTrueTest() throws IOException {
        createPropertiesFile(HUB + "=somehub");
        assertEquals(Property.getHub(), "somehub");
    }

    @Test
    public void defaultGetHubOverrideEmptyTest() throws IOException {
        System.setProperty(HUB, "");
        createPropertiesFile(HUB + "=somehub");
        assertNull(Property.getHub());
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

    @Test
    public void defaultGetProxyTest() {
        assertNull(Property.getProxy());
    }

    @Test
    public void defaultGetProxySystemEmptyTest() {
        System.setProperty(PROXY, "");
        assertNull(Property.getProxy());
    }

    @Test
    public void defaultGetProxySystemTest() {
        System.setProperty(PROXY, "someproxy");
        assertEquals(Property.getProxy(), "someproxy");
    }

    @Test
    public void defaultGetProxyFileEmptyTest() throws IOException {
        createPropertiesFile("");
        assertNull(Property.getProxy());
    }

    @Test
    public void defaultGetProxyFilePartialTest() throws IOException {
        createPropertiesFile(PROXY);
        assertNull(Property.getProxy());
    }

    @Test
    public void defaultGetProxyFileUnsetTest() throws IOException {
        createPropertiesFile(PROXY + "=");
        assertNull(Property.getProxy());
    }

    @Test
    public void defaultGetProxyFileTrueTest() throws IOException {
        createPropertiesFile(PROXY + "=someproxy");
        assertEquals(Property.getProxy(), "someproxy");
    }

    @Test
    public void defaultGetProxyOverrideEmptyTest() throws IOException {
        System.setProperty(PROXY, "");
        createPropertiesFile(PROXY + "=someproxy");
        assertNull(Property.getProxy());
    }

    @Test
    public void defaultGetProxyOverrideTrueTest() throws IOException {
        System.setProperty(PROXY, "someproxy");
        createPropertiesFile(PROXY + "=");
        assertEquals(Property.getProxy(), "someproxy");
    }


    private void createPropertiesFile(String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(SELENIFIED));
        writer.write(content);
        writer.close();
    }
}