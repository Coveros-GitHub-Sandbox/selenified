package unit;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.utilities.CombinedPDFReport;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.testng.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import static org.testng.Assert.*;

import java.io.File;
import java.util.*;

public class CombinedPDFReportTest extends CombinedPDFReport {

    File directory;

    @BeforeMethod(alwaysRun = true)
    public void createFile() throws InvalidBrowserException, InvalidProxyException {
        directory = new File("directory");
        directory.mkdirs();
    }

    @AfterMethod
    public void deleteFile() {
        for(File file: directory.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    @Test
    public void generateCombinedReportTest() {
        ITestResult iTestResult = new ITestResult() {
            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public void setStatus(int status) {

            }

            @Override
            public ITestNGMethod getMethod() {
                return null;
            }

            @Override
            public Object[] getParameters() {
                return new Object[0];
            }

            @Override
            public void setParameters(Object[] parameters) {

            }

            @Override
            public IClass getTestClass() {
                return null;
            }

            @Override
            public Throwable getThrowable() {
                return null;
            }

            @Override
            public void setThrowable(Throwable throwable) {

            }

            @Override
            public long getStartMillis() {
                return 0;
            }

            @Override
            public long getEndMillis() {
                return 0;
            }

            @Override
            public void setEndMillis(long millis) {

            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean isSuccess() {
                return false;
            }

            @Override
            public String getHost() {
                return null;
            }

            @Override
            public Object getInstance() {
                return null;
            }

            @Override
            public String getTestName() {
                return null;
            }

            @Override
            public String getInstanceName() {
                return null;
            }

            @Override
            public ITestContext getTestContext() {
                return new ITestContext() {
                    @Override
                    public String getName() {
                        return null;
                    }

                    @Override
                    public Date getStartDate() {
                        return null;
                    }

                    @Override
                    public Date getEndDate() {
                        return null;
                    }

                    @Override
                    public IResultMap getPassedTests() {
                        return null;
                    }

                    @Override
                    public IResultMap getSkippedTests() {
                        return null;
                    }

                    @Override
                    public IResultMap getFailedButWithinSuccessPercentageTests() {
                        return null;
                    }

                    @Override
                    public IResultMap getFailedTests() {
                        return null;
                    }

                    @Override
                    public String[] getIncludedGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getExcludedGroups() {
                        return new String[0];
                    }

                    @Override
                    public String getOutputDirectory() {
                        return "myDir/otherDir";
                    }

                    @Override
                    public ISuite getSuite() {
                        return null;
                    }

                    @Override
                    public ITestNGMethod[] getAllTestMethods() {
                        return new ITestNGMethod[0];
                    }

                    @Override
                    public String getHost() {
                        return null;
                    }

                    @Override
                    public Collection<ITestNGMethod> getExcludedMethods() {
                        return null;
                    }

                    @Override
                    public IResultMap getPassedConfigurations() {
                        return null;
                    }

                    @Override
                    public IResultMap getSkippedConfigurations() {
                        return null;
                    }

                    @Override
                    public IResultMap getFailedConfigurations() {
                        return null;
                    }

                    @Override
                    public XmlTest getCurrentXmlTest() {
                        return null;
                    }

                    @Override
                    public List<Module> getGuiceModules(Class<? extends Module> cls) {
                        return null;
                    }

                    @Override
                    public Injector getInjector(List<Module> moduleInstances) {
                        return null;
                    }

                    @Override
                    public Injector getInjector(IClass iClass) {
                        return null;
                    }

                    @Override
                    public void addInjector(List<Module> moduleInstances, Injector injector) {

                    }

                    @Override
                    public Object getAttribute(String name) {
                        return null;
                    }

                    @Override
                    public void setAttribute(String name, Object value) {

                    }

                    @Override
                    public Set<String> getAttributeNames() {
                        return null;
                    }

                    @Override
                    public Object removeAttribute(String name) {
                        return null;
                    }
                };
            }

            @Override
            public int compareTo(ITestResult o) {
                return 0;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public Set<String> getAttributeNames() {
                return null;
            }

            @Override
            public Object removeAttribute(String name) {
                return null;
            }
        };
        generateReport(null, new ArrayList<>(), "myDir");
    }
}
