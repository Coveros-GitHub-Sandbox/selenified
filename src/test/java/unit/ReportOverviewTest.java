package unit;

import com.coveros.selenified.utilities.ReportOverview;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.testng.*;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import java.io.File;
import java.util.*;

import static org.testng.Assert.assertEquals;

public class ReportOverviewTest extends ReportOverview {

    @Test
    public void getReportDirNullSetTest() {
        generateReport(null, new ArrayList<ISuite>(), "directory");
        assertEquals(getReportDir(null), "directory");
    }

    @Test
    public void getReportDirEmptyTest() {
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
                return null;
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
        generateReport(null, new ArrayList<ISuite>(), "directory");
        assertEquals(getReportDir(iTestResult), "directory");
    }

    @Test
    public void getReportDirContextEmptyTest() {
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
                return null;
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
        generateReport(null, new ArrayList<ISuite>(), "directory");
        assertEquals(getReportDir(iTestResult), "directory");
    }

    @Test
    public void getReportDirContextOutputEmptyTest() {
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
                        return null;
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
        generateReport(null, new ArrayList<ISuite>(), "directory");
        assertEquals(getReportDir(iTestResult), "directory");
    }

    @Test
    public void getReportDirContextOutputShortTest() {
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
                        return "directory";
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
        generateReport(null, new ArrayList<ISuite>(), "directory");
        assertEquals(getReportDir(iTestResult), System.getProperty("user.dir") + File.separator + "directory");
    }


    @Test
    public void getReportDirContextOutputTest() {
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
                        return "directory/otherDir";
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
        generateReport(null, new ArrayList<ISuite>(), "directory");
        assertEquals(getReportDir(iTestResult), "otherDir");
    }

    @Test
    public void getReportDirContextOutputFullTest() {
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
                        return "directory/otherDir";
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
        generateReport(null, new ArrayList<ISuite>(), System.getProperty("user.dir") + File.separator + "directory");
        assertEquals(getReportDir(iTestResult), "otherDir");
    }
}
