package unit;

import com.coveros.selenified.utilities.Listener;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.internal.ConstructorOrMethod;
import org.testng.xml.XmlTest;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

public class ListenerTest extends Listener {

    private String hub;
    private String jiraLink;

    @BeforeClass
    public void setupArrays() {
        if (System.getProperty("hub") != null) {
            hub = System.getProperty("hub");
        }
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
        if (System.getProperty("jira.link") != null) {
            jiraLink = System.getProperty("jira.link");
        }
        System.setProperty("jira.link", "http://localhost:1080/jira");
    }

    @AfterClass
    public void restoreBrowser() {
        System.clearProperty("hub");
        if (hub != null) {
            System.setProperty("hub", hub);
        }
        System.clearProperty("jira.link");
        if (jiraLink != null) {
            System.setProperty("jira.link", jiraLink);
        }
    }

    @Test
    public void recordSauceNotSauceTest() {
        System.clearProperty("hub");
        recordSauce(null);
        System.setProperty("hub", "https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443");
    }

    @Test
    public void recordSauceNullTestTest() {
        recordSauce(null);
    }

    @Test
    public void recordSauceNoAttributesTest() {
        ITestResult test = new ITestResult() {
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
        recordSauce(test);
    }

    @Test
    public void recordSauceNoSessionTest() {
        ITestResult test = new ITestResult() {
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
                return new HashSet<>(Arrays.asList("Hello", "World"));
            }

            @Override
            public Object removeAttribute(String name) {
                return null;
            }
        };
        recordSauce(test);
    }

    @Test
    public void recordSauceFailedTest() {
        ITestResult test = new ITestResult() {
            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public void setStatus(int status) {

            }

            @Override
            public ITestNGMethod getMethod() {
                return new ITestNGMethod() {
                    @Override
                    public Class getRealClass() {
                        return null;
                    }

                    @Override
                    public ITestClass getTestClass() {
                        return null;
                    }

                    @Override
                    public void setTestClass(ITestClass cls) {

                    }

                    @Override
                    public Method getMethod() {
                        return null;
                    }

                    @Override
                    public String getMethodName() {
                        return null;
                    }

                    @Override
                    public Object[] getInstances() {
                        return new Object[0];
                    }

                    @Override
                    public Object getInstance() {
                        return null;
                    }

                    @Override
                    public long[] getInstanceHashCodes() {
                        return new long[0];
                    }

                    @Override
                    public String[] getGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getGroupsDependedUpon() {
                        return new String[0];
                    }

                    @Override
                    public String getMissingGroup() {
                        return null;
                    }

                    @Override
                    public void setMissingGroup(String group) {

                    }

                    @Override
                    public String[] getBeforeGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getAfterGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getMethodsDependedUpon() {
                        return new String[0];
                    }

                    @Override
                    public void addMethodDependedUpon(String methodName) {

                    }

                    @Override
                    public boolean isTest() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeMethodConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterMethodConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeClassConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterClassConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeSuiteConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterSuiteConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeTestConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterTestConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeGroupsConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterGroupsConfiguration() {
                        return false;
                    }

                    @Override
                    public long getTimeOut() {
                        return 0;
                    }

                    @Override
                    public void setTimeOut(long timeOut) {

                    }

                    @Override
                    public int getInvocationCount() {
                        return 0;
                    }

                    @Override
                    public void setInvocationCount(int count) {

                    }

                    @Override
                    public int getTotalInvocationCount() {
                        return 0;
                    }

                    @Override
                    public int getSuccessPercentage() {
                        return 0;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public void setId(String id) {

                    }

                    @Override
                    public long getDate() {
                        return 0;
                    }

                    @Override
                    public void setDate(long date) {

                    }

                    @Override
                    public boolean canRunFromClass(IClass testClass) {
                        return false;
                    }

                    @Override
                    public boolean isAlwaysRun() {
                        return false;
                    }

                    @Override
                    public int getThreadPoolSize() {
                        return 0;
                    }

                    @Override
                    public void setThreadPoolSize(int threadPoolSize) {

                    }

                    @Override
                    public boolean getEnabled() {
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }

                    @Override
                    public void setDescription(String description) {

                    }

                    @Override
                    public void incrementCurrentInvocationCount() {

                    }

                    @Override
                    public int getCurrentInvocationCount() {
                        return 0;
                    }

                    @Override
                    public void setParameterInvocationCount(int n) {

                    }

                    @Override
                    public int getParameterInvocationCount() {
                        return 0;
                    }

                    @Override
                    public ITestNGMethod clone() {
                        return null;
                    }

                    @Override
                    public IRetryAnalyzer getRetryAnalyzer() {
                        return null;
                    }

                    @Override
                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {

                    }

                    @Override
                    public boolean skipFailedInvocations() {
                        return false;
                    }

                    @Override
                    public void setSkipFailedInvocations(boolean skip) {

                    }

                    @Override
                    public long getInvocationTimeOut() {
                        return 0;
                    }

                    @Override
                    public boolean ignoreMissingDependencies() {
                        return false;
                    }

                    @Override
                    public void setIgnoreMissingDependencies(boolean ignore) {

                    }

                    @Override
                    public List<Integer> getInvocationNumbers() {
                        return null;
                    }

                    @Override
                    public void setInvocationNumbers(List<Integer> numbers) {

                    }

                    @Override
                    public void addFailedInvocationNumber(int number) {

                    }

                    @Override
                    public List<Integer> getFailedInvocationNumbers() {
                        return null;
                    }

                    @Override
                    public int getPriority() {
                        return 0;
                    }

                    @Override
                    public void setPriority(int priority) {

                    }

                    @Override
                    public XmlTest getXmlTest() {
                        return null;
                    }

                    @Override
                    public ConstructorOrMethod getConstructorOrMethod() {
                        return null;
                    }

                    @Override
                    public Map<String, String> findMethodParameters(XmlTest test) {
                        return null;
                    }

                    @Override
                    public int compareTo(Object o) {
                        return 0;
                    }
                };
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
                return 10;
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public Set<String> getAttributeNames() {
                return new HashSet<>(Arrays.asList("SessionId"));
            }

            @Override
            public Object removeAttribute(String name) {
                return null;
            }
        };
        recordSauce(test);
    }

    @Test
    public void recordSaucePassedTest() {
        ITestResult test = new ITestResult() {
            @Override
            public int getStatus() {
                return 1;
            }

            @Override
            public void setStatus(int status) {

            }

            @Override
            public ITestNGMethod getMethod() {
                return new ITestNGMethod() {
                    @Override
                    public Class getRealClass() {
                        return null;
                    }

                    @Override
                    public ITestClass getTestClass() {
                        return null;
                    }

                    @Override
                    public void setTestClass(ITestClass cls) {

                    }

                    @Override
                    public Method getMethod() {
                        return null;
                    }

                    @Override
                    public String getMethodName() {
                        return null;
                    }

                    @Override
                    public Object[] getInstances() {
                        return new Object[0];
                    }

                    @Override
                    public Object getInstance() {
                        return null;
                    }

                    @Override
                    public long[] getInstanceHashCodes() {
                        return new long[0];
                    }

                    @Override
                    public String[] getGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getGroupsDependedUpon() {
                        return new String[0];
                    }

                    @Override
                    public String getMissingGroup() {
                        return null;
                    }

                    @Override
                    public void setMissingGroup(String group) {

                    }

                    @Override
                    public String[] getBeforeGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getAfterGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getMethodsDependedUpon() {
                        return new String[0];
                    }

                    @Override
                    public void addMethodDependedUpon(String methodName) {

                    }

                    @Override
                    public boolean isTest() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeMethodConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterMethodConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeClassConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterClassConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeSuiteConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterSuiteConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeTestConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterTestConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeGroupsConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterGroupsConfiguration() {
                        return false;
                    }

                    @Override
                    public long getTimeOut() {
                        return 0;
                    }

                    @Override
                    public void setTimeOut(long timeOut) {

                    }

                    @Override
                    public int getInvocationCount() {
                        return 0;
                    }

                    @Override
                    public void setInvocationCount(int count) {

                    }

                    @Override
                    public int getTotalInvocationCount() {
                        return 0;
                    }

                    @Override
                    public int getSuccessPercentage() {
                        return 0;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public void setId(String id) {

                    }

                    @Override
                    public long getDate() {
                        return 0;
                    }

                    @Override
                    public void setDate(long date) {

                    }

                    @Override
                    public boolean canRunFromClass(IClass testClass) {
                        return false;
                    }

                    @Override
                    public boolean isAlwaysRun() {
                        return false;
                    }

                    @Override
                    public int getThreadPoolSize() {
                        return 0;
                    }

                    @Override
                    public void setThreadPoolSize(int threadPoolSize) {

                    }

                    @Override
                    public boolean getEnabled() {
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }

                    @Override
                    public void setDescription(String description) {

                    }

                    @Override
                    public void incrementCurrentInvocationCount() {

                    }

                    @Override
                    public int getCurrentInvocationCount() {
                        return 0;
                    }

                    @Override
                    public void setParameterInvocationCount(int n) {

                    }

                    @Override
                    public int getParameterInvocationCount() {
                        return 0;
                    }

                    @Override
                    public ITestNGMethod clone() {
                        return null;
                    }

                    @Override
                    public IRetryAnalyzer getRetryAnalyzer() {
                        return null;
                    }

                    @Override
                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {

                    }

                    @Override
                    public boolean skipFailedInvocations() {
                        return false;
                    }

                    @Override
                    public void setSkipFailedInvocations(boolean skip) {

                    }

                    @Override
                    public long getInvocationTimeOut() {
                        return 0;
                    }

                    @Override
                    public boolean ignoreMissingDependencies() {
                        return false;
                    }

                    @Override
                    public void setIgnoreMissingDependencies(boolean ignore) {

                    }

                    @Override
                    public List<Integer> getInvocationNumbers() {
                        return null;
                    }

                    @Override
                    public void setInvocationNumbers(List<Integer> numbers) {

                    }

                    @Override
                    public void addFailedInvocationNumber(int number) {

                    }

                    @Override
                    public List<Integer> getFailedInvocationNumbers() {
                        return null;
                    }

                    @Override
                    public int getPriority() {
                        return 0;
                    }

                    @Override
                    public void setPriority(int priority) {

                    }

                    @Override
                    public XmlTest getXmlTest() {
                        return null;
                    }

                    @Override
                    public ConstructorOrMethod getConstructorOrMethod() {
                        return null;
                    }

                    @Override
                    public Map<String, String> findMethodParameters(XmlTest test) {
                        return null;
                    }

                    @Override
                    public int compareTo(Object o) {
                        return 0;
                    }
                };
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
                return 10;
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public Set<String> getAttributeNames() {
                return new HashSet<>(Arrays.asList("SessionId"));
            }

            @Override
            public Object removeAttribute(String name) {
                return null;
            }
        };
        recordSauce(test);
    }

    @Test
    public void recordSauceTagsTest() {
        ITestResult test = new ITestResult() {
            @Override
            public int getStatus() {
                return 1;
            }

            @Override
            public void setStatus(int status) {

            }

            @Override
            public ITestNGMethod getMethod() {
                return new ITestNGMethod() {
                    @Override
                    public Class getRealClass() {
                        return null;
                    }

                    @Override
                    public ITestClass getTestClass() {
                        return null;
                    }

                    @Override
                    public void setTestClass(ITestClass cls) {

                    }

                    @Override
                    public Method getMethod() {
                        return null;
                    }

                    @Override
                    public String getMethodName() {
                        return null;
                    }

                    @Override
                    public Object[] getInstances() {
                        return new Object[0];
                    }

                    @Override
                    public Object getInstance() {
                        return null;
                    }

                    @Override
                    public long[] getInstanceHashCodes() {
                        return new long[0];
                    }

                    @Override
                    public String[] getGroups() {
                        return new String[]{"Hello", "World"};
                    }

                    @Override
                    public String[] getGroupsDependedUpon() {
                        return new String[0];
                    }

                    @Override
                    public String getMissingGroup() {
                        return null;
                    }

                    @Override
                    public void setMissingGroup(String group) {

                    }

                    @Override
                    public String[] getBeforeGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getAfterGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getMethodsDependedUpon() {
                        return new String[0];
                    }

                    @Override
                    public void addMethodDependedUpon(String methodName) {

                    }

                    @Override
                    public boolean isTest() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeMethodConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterMethodConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeClassConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterClassConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeSuiteConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterSuiteConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeTestConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterTestConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeGroupsConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterGroupsConfiguration() {
                        return false;
                    }

                    @Override
                    public long getTimeOut() {
                        return 0;
                    }

                    @Override
                    public void setTimeOut(long timeOut) {

                    }

                    @Override
                    public int getInvocationCount() {
                        return 0;
                    }

                    @Override
                    public void setInvocationCount(int count) {

                    }

                    @Override
                    public int getTotalInvocationCount() {
                        return 0;
                    }

                    @Override
                    public int getSuccessPercentage() {
                        return 0;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public void setId(String id) {

                    }

                    @Override
                    public long getDate() {
                        return 0;
                    }

                    @Override
                    public void setDate(long date) {

                    }

                    @Override
                    public boolean canRunFromClass(IClass testClass) {
                        return false;
                    }

                    @Override
                    public boolean isAlwaysRun() {
                        return false;
                    }

                    @Override
                    public int getThreadPoolSize() {
                        return 0;
                    }

                    @Override
                    public void setThreadPoolSize(int threadPoolSize) {

                    }

                    @Override
                    public boolean getEnabled() {
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }

                    @Override
                    public void setDescription(String description) {

                    }

                    @Override
                    public void incrementCurrentInvocationCount() {

                    }

                    @Override
                    public int getCurrentInvocationCount() {
                        return 0;
                    }

                    @Override
                    public void setParameterInvocationCount(int n) {

                    }

                    @Override
                    public int getParameterInvocationCount() {
                        return 0;
                    }

                    @Override
                    public ITestNGMethod clone() {
                        return null;
                    }

                    @Override
                    public IRetryAnalyzer getRetryAnalyzer() {
                        return null;
                    }

                    @Override
                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {

                    }

                    @Override
                    public boolean skipFailedInvocations() {
                        return false;
                    }

                    @Override
                    public void setSkipFailedInvocations(boolean skip) {

                    }

                    @Override
                    public long getInvocationTimeOut() {
                        return 0;
                    }

                    @Override
                    public boolean ignoreMissingDependencies() {
                        return false;
                    }

                    @Override
                    public void setIgnoreMissingDependencies(boolean ignore) {

                    }

                    @Override
                    public List<Integer> getInvocationNumbers() {
                        return null;
                    }

                    @Override
                    public void setInvocationNumbers(List<Integer> numbers) {

                    }

                    @Override
                    public void addFailedInvocationNumber(int number) {

                    }

                    @Override
                    public List<Integer> getFailedInvocationNumbers() {
                        return null;
                    }

                    @Override
                    public int getPriority() {
                        return 0;
                    }

                    @Override
                    public void setPriority(int priority) {

                    }

                    @Override
                    public XmlTest getXmlTest() {
                        return null;
                    }

                    @Override
                    public ConstructorOrMethod getConstructorOrMethod() {
                        return null;
                    }

                    @Override
                    public Map<String, String> findMethodParameters(XmlTest test) {
                        return null;
                    }

                    @Override
                    public int compareTo(Object o) {
                        return 0;
                    }
                };
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
                return 10;
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public Set<String> getAttributeNames() {
                return new HashSet<>(Arrays.asList("SessionId"));
            }

            @Override
            public Object removeAttribute(String name) {
                return null;
            }
        };
        recordSauce(test);
    }

    @Test
    public void recordZephyrNotJiraTest() {
        System.clearProperty("jira.link");
        recordZephyr(null);
        System.setProperty("jira.link", "http://localhost:1080/jira");
    }

    @Test
    public void recordZephyrNullTestTest() {
        recordSauce(null);
    }

    @Test
    public void recordZephyrNoAttributesTest() {
        ITestResult test = new ITestResult() {
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
        recordZephyr(test);
    }

    @Test
    public void recordZephyrNoOutputTest() {
        ITestResult test = new ITestResult() {
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
                return new HashSet<>(Arrays.asList("Hello", "World"));
            }

            @Override
            public Object removeAttribute(String name) {
                return null;
            }
        };
        recordZephyr(test);
    }

    @Test
    public void recordZephyrNoProjectTest(Method method) {
        ITestResult test = new ITestResult() {
            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public void setStatus(int status) {

            }

            @Override
            public ITestNGMethod getMethod() {
                return new ITestNGMethod() {
                    @Override
                    public Class getRealClass() {
                        return null;
                    }

                    @Override
                    public ITestClass getTestClass() {
                        return null;
                    }

                    @Override
                    public void setTestClass(ITestClass cls) {

                    }

                    @Override
                    public Method getMethod() {
                        return null;
                    }

                    @Override
                    public String getMethodName() {
                        return null;
                    }

                    @Override
                    public Object[] getInstances() {
                        return new Object[0];
                    }

                    @Override
                    public Object getInstance() {
                        return null;
                    }

                    @Override
                    public long[] getInstanceHashCodes() {
                        return new long[0];
                    }

                    @Override
                    public String[] getGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getGroupsDependedUpon() {
                        return new String[0];
                    }

                    @Override
                    public String getMissingGroup() {
                        return null;
                    }

                    @Override
                    public void setMissingGroup(String group) {

                    }

                    @Override
                    public String[] getBeforeGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getAfterGroups() {
                        return new String[0];
                    }

                    @Override
                    public String[] getMethodsDependedUpon() {
                        return new String[0];
                    }

                    @Override
                    public void addMethodDependedUpon(String methodName) {

                    }

                    @Override
                    public boolean isTest() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeMethodConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterMethodConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeClassConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterClassConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeSuiteConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterSuiteConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeTestConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterTestConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeGroupsConfiguration() {
                        return false;
                    }

                    @Override
                    public boolean isAfterGroupsConfiguration() {
                        return false;
                    }

                    @Override
                    public long getTimeOut() {
                        return 0;
                    }

                    @Override
                    public void setTimeOut(long timeOut) {

                    }

                    @Override
                    public int getInvocationCount() {
                        return 0;
                    }

                    @Override
                    public void setInvocationCount(int count) {

                    }

                    @Override
                    public int getTotalInvocationCount() {
                        return 0;
                    }

                    @Override
                    public int getSuccessPercentage() {
                        return 0;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public void setId(String id) {

                    }

                    @Override
                    public long getDate() {
                        return 0;
                    }

                    @Override
                    public void setDate(long date) {

                    }

                    @Override
                    public boolean canRunFromClass(IClass testClass) {
                        return false;
                    }

                    @Override
                    public boolean isAlwaysRun() {
                        return false;
                    }

                    @Override
                    public int getThreadPoolSize() {
                        return 0;
                    }

                    @Override
                    public void setThreadPoolSize(int threadPoolSize) {

                    }

                    @Override
                    public boolean getEnabled() {
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }

                    @Override
                    public void setDescription(String description) {

                    }

                    @Override
                    public void incrementCurrentInvocationCount() {

                    }

                    @Override
                    public int getCurrentInvocationCount() {
                        return 0;
                    }

                    @Override
                    public void setParameterInvocationCount(int n) {

                    }

                    @Override
                    public int getParameterInvocationCount() {
                        return 0;
                    }

                    @Override
                    public ITestNGMethod clone() {
                        return null;
                    }

                    @Override
                    public IRetryAnalyzer getRetryAnalyzer() {
                        return null;
                    }

                    @Override
                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {

                    }

                    @Override
                    public boolean skipFailedInvocations() {
                        return false;
                    }

                    @Override
                    public void setSkipFailedInvocations(boolean skip) {

                    }

                    @Override
                    public long getInvocationTimeOut() {
                        return 0;
                    }

                    @Override
                    public boolean ignoreMissingDependencies() {
                        return false;
                    }

                    @Override
                    public void setIgnoreMissingDependencies(boolean ignore) {

                    }

                    @Override
                    public List<Integer> getInvocationNumbers() {
                        return null;
                    }

                    @Override
                    public void setInvocationNumbers(List<Integer> numbers) {

                    }

                    @Override
                    public void addFailedInvocationNumber(int number) {

                    }

                    @Override
                    public List<Integer> getFailedInvocationNumbers() {
                        return null;
                    }

                    @Override
                    public int getPriority() {
                        return 0;
                    }

                    @Override
                    public void setPriority(int priority) {

                    }

                    @Override
                    public XmlTest getXmlTest() {
                        return null;
                    }

                    @Override
                    public ConstructorOrMethod getConstructorOrMethod() {
                        return new ConstructorOrMethod(method);
                    }

                    @Override
                    public Map<String, String> findMethodParameters(XmlTest test) {
                        return null;
                    }

                    @Override
                    public int compareTo(Object o) {
                        return 0;
                    }
                };
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
                return new File("somefile");
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public Set<String> getAttributeNames() {
                return new HashSet<>(Arrays.asList("Output"));
            }

            @Override
            public Object removeAttribute(String name) {
                return null;
            }
        };
        recordZephyr(test);
    }
}
