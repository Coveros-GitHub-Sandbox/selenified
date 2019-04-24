package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.utilities.Listener;
import com.coveros.selenified.utilities.Reporter;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.testng.*;
import org.testng.annotations.Test;
import org.testng.internal.ConstructorOrMethod;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;

import static com.coveros.selenified.Selenified.REPORTER;
import static com.coveros.selenified.Selenified.SESSION_ID;
import static com.coveros.selenified.utilities.Property.BROWSER;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ListenerTest extends SaveProperties {

    Reporter reporterPass = new Reporter("directory", "fileP", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
            null);

    Reporter reporterFail = new Reporter("directory", "fileF", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
            null);

    Reporter reporterCheck = new Reporter("directory", "fileC", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
            null);

    ITestResult resultPass = new ITestResult() {
        @Override
        public int getStatus() {
            return 1;
        }

        @Override
        public void setStatus(int i) {

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
        public void setParameters(Object[] objects) {

        }

        @Override
        public IClass getTestClass() {
            return new IClass() {
                @Override
                public String getName() {
                    return "My Odd Sample Test Case";
                }

                @Override
                public XmlTest getXmlTest() {
                    return null;
                }

                @Override
                public XmlClass getXmlClass() {
                    return null;
                }

                @Override
                public String getTestName() {
                    return null;
                }

                @Override
                public Class<?> getRealClass() {
                    return null;
                }

                @Override
                public Object[] getInstances(boolean b) {
                    return new Object[0];
                }

                @Override
                public int getInstanceCount() {
                    return 0;
                }

                @Override
                public long[] getInstanceHashCodes() {
                    return new long[0];
                }

                @Override
                public void addInstance(Object o) {

                }
            };
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
        public void setEndMillis(long l) {

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
        public Object getAttribute(String s) {
            return null;
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String s) {
            return null;
        }
    };

    ITestResult resultP = new ITestResult() {
        @Override
        public int getStatus() {
            return 1;
        }

        @Override
        public void setStatus(int i) {

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
                public void setTestClass(ITestClass iTestClass) {

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
                public void setMissingGroup(String s) {

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
                public void addMethodDependedUpon(String s) {

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
                public void setTimeOut(long l) {

                }

                @Override
                public int getInvocationCount() {
                    return 0;
                }

                @Override
                public void setInvocationCount(int i) {

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
                public void setId(String s) {

                }

                @Override
                public long getDate() {
                    return 0;
                }

                @Override
                public void setDate(long l) {

                }

                @Override
                public boolean canRunFromClass(IClass iClass) {
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
                public void setThreadPoolSize(int i) {

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
                public void setDescription(String s) {

                }

                @Override
                public void incrementCurrentInvocationCount() {

                }

                @Override
                public int getCurrentInvocationCount() {
                    return 0;
                }

                @Override
                public void setParameterInvocationCount(int i) {

                }

                @Override
                public int getParameterInvocationCount() {
                    return 0;
                }

                @Override
                public void setMoreInvocationChecker(Callable<Boolean> callable) {

                }

                @Override
                public boolean hasMoreInvocation() {
                    return false;
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
                public void setRetryAnalyzer(IRetryAnalyzer iRetryAnalyzer) {

                }

                @Override
                public boolean skipFailedInvocations() {
                    return false;
                }

                @Override
                public void setSkipFailedInvocations(boolean b) {

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
                public void setIgnoreMissingDependencies(boolean b) {

                }

                @Override
                public List<Integer> getInvocationNumbers() {
                    return null;
                }

                @Override
                public void setInvocationNumbers(List<Integer> list) {

                }

                @Override
                public void addFailedInvocationNumber(int i) {

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
                public void setPriority(int i) {

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
                public Map<String, String> findMethodParameters(XmlTest xmlTest) {
                    return null;
                }

                @Override
                public String getQualifiedName() {
                    return null;
                }
            };
        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public void setParameters(Object[] objects) {

        }

        @Override
        public IClass getTestClass() {
            return new IClass() {
                @Override
                public String getName() {
                    return "My Odd Sample Test Case";
                }

                @Override
                public XmlTest getXmlTest() {
                    return null;
                }

                @Override
                public XmlClass getXmlClass() {
                    return null;
                }

                @Override
                public String getTestName() {
                    return null;
                }

                @Override
                public Class<?> getRealClass() {
                    return null;
                }

                @Override
                public Object[] getInstances(boolean b) {
                    return new Object[0];
                }

                @Override
                public int getInstanceCount() {
                    return 0;
                }

                @Override
                public long[] getInstanceHashCodes() {
                    return new long[0];
                }

                @Override
                public void addInstance(Object o) {

                }
            };
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
        public void setEndMillis(long l) {

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
                public List<com.google.inject.Module> getGuiceModules(Class<? extends com.google.inject.Module> aClass) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(List<com.google.inject.Module> list) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(IClass iClass) {
                    return null;
                }

                @Override
                public void addInjector(List<com.google.inject.Module> list, com.google.inject.Injector injector) {

                }

                @Override
                public Object getAttribute(String s) {
                    return null;
                }

                @Override
                public void setAttribute(String s, Object o) {

                }

                @Override
                public Set<String> getAttributeNames() {
                    return null;
                }

                @Override
                public Object removeAttribute(String s) {
                    return null;
                }
            };
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String s) {
            return null;
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String s) {
            return null;
        }
    };

    ITestResult resultPassChrome = new ITestResult() {
        @Override
        public int getStatus() {
            return 1;
        }

        @Override
        public void setStatus(int i) {

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
                public void setTestClass(ITestClass iTestClass) {

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
                public void setMissingGroup(String s) {

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
                public void addMethodDependedUpon(String s) {

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
                public void setTimeOut(long l) {

                }

                @Override
                public int getInvocationCount() {
                    return 0;
                }

                @Override
                public void setInvocationCount(int i) {

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
                public void setId(String s) {

                }

                @Override
                public long getDate() {
                    return 0;
                }

                @Override
                public void setDate(long l) {

                }

                @Override
                public boolean canRunFromClass(IClass iClass) {
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
                public void setThreadPoolSize(int i) {

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
                public void setDescription(String s) {

                }

                @Override
                public void incrementCurrentInvocationCount() {

                }

                @Override
                public int getCurrentInvocationCount() {
                    return 0;
                }

                @Override
                public void setParameterInvocationCount(int i) {

                }

                @Override
                public int getParameterInvocationCount() {
                    return 0;
                }

                @Override
                public void setMoreInvocationChecker(Callable<Boolean> callable) {

                }

                @Override
                public boolean hasMoreInvocation() {
                    return false;
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
                public void setRetryAnalyzer(IRetryAnalyzer iRetryAnalyzer) {

                }

                @Override
                public boolean skipFailedInvocations() {
                    return false;
                }

                @Override
                public void setSkipFailedInvocations(boolean b) {

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
                public void setIgnoreMissingDependencies(boolean b) {

                }

                @Override
                public List<Integer> getInvocationNumbers() {
                    return null;
                }

                @Override
                public void setInvocationNumbers(List<Integer> list) {

                }

                @Override
                public void addFailedInvocationNumber(int i) {

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
                public void setPriority(int i) {

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
                public Map<String, String> findMethodParameters(XmlTest xmlTest) {
                    return null;
                }

                @Override
                public String getQualifiedName() {
                    return null;
                }
            };
        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public void setParameters(Object[] objects) {

        }

        @Override
        public IClass getTestClass() {
            return new IClass() {
                @Override
                public String getName() {
                    return "My Odd Sample Test Case";
                }

                @Override
                public XmlTest getXmlTest() {
                    return null;
                }

                @Override
                public XmlClass getXmlClass() {
                    return null;
                }

                @Override
                public String getTestName() {
                    return null;
                }

                @Override
                public Class<?> getRealClass() {
                    return null;
                }

                @Override
                public Object[] getInstances(boolean b) {
                    return new Object[0];
                }

                @Override
                public int getInstanceCount() {
                    return 0;
                }

                @Override
                public long[] getInstanceHashCodes() {
                    return new long[0];
                }

                @Override
                public void addInstance(Object o) {

                }
            };
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
        public void setEndMillis(long l) {

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
                public List<com.google.inject.Module> getGuiceModules(Class<? extends com.google.inject.Module> aClass) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(List<com.google.inject.Module> list) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(IClass iClass) {
                    return null;
                }

                @Override
                public void addInjector(List<com.google.inject.Module> list, com.google.inject.Injector injector) {

                }

                @Override
                public Object getAttribute(String s) {
                    return null;
                }

                @Override
                public void setAttribute(String s, Object o) {

                }

                @Override
                public Set<String> getAttributeNames() {
                    return null;
                }

                @Override
                public Object removeAttribute(String s) {
                    return null;
                }
            };
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String s) {
            try {
                if (s.equals(BROWSER)) {
                    return new Browser("Chrome");
                }
                if (s.equals(REPORTER)) {
                    return reporterPass;
                }
            } catch (InvalidBrowserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public Set<String> getAttributeNames() {
            Set<String> set = new HashSet();
            set.add(BROWSER);
            set.add(REPORTER);
            return set;
        }

        @Override
        public Object removeAttribute(String s) {
            return null;
        }
    };

    ITestResult resultFailChrome = new ITestResult() {
        @Override
        public int getStatus() {
            return 2;
        }

        @Override
        public void setStatus(int i) {

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
                public void setTestClass(ITestClass iTestClass) {

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
                public void setMissingGroup(String s) {

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
                public void addMethodDependedUpon(String s) {

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
                public void setTimeOut(long l) {

                }

                @Override
                public int getInvocationCount() {
                    return 0;
                }

                @Override
                public void setInvocationCount(int i) {

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
                public void setId(String s) {

                }

                @Override
                public long getDate() {
                    return 0;
                }

                @Override
                public void setDate(long l) {

                }

                @Override
                public boolean canRunFromClass(IClass iClass) {
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
                public void setThreadPoolSize(int i) {

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
                public void setDescription(String s) {

                }

                @Override
                public void incrementCurrentInvocationCount() {

                }

                @Override
                public int getCurrentInvocationCount() {
                    return 0;
                }

                @Override
                public void setParameterInvocationCount(int i) {

                }

                @Override
                public int getParameterInvocationCount() {
                    return 0;
                }

                @Override
                public void setMoreInvocationChecker(Callable<Boolean> callable) {

                }

                @Override
                public boolean hasMoreInvocation() {
                    return false;
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
                public void setRetryAnalyzer(IRetryAnalyzer iRetryAnalyzer) {

                }

                @Override
                public boolean skipFailedInvocations() {
                    return false;
                }

                @Override
                public void setSkipFailedInvocations(boolean b) {

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
                public void setIgnoreMissingDependencies(boolean b) {

                }

                @Override
                public List<Integer> getInvocationNumbers() {
                    return null;
                }

                @Override
                public void setInvocationNumbers(List<Integer> list) {

                }

                @Override
                public void addFailedInvocationNumber(int i) {

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
                public void setPriority(int i) {

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
                public Map<String, String> findMethodParameters(XmlTest xmlTest) {
                    return null;
                }

                @Override
                public String getQualifiedName() {
                    return null;
                }
            };
        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public void setParameters(Object[] objects) {

        }

        @Override
        public IClass getTestClass() {
            return new IClass() {
                @Override
                public String getName() {
                    return "My Odd Sample Test Case";
                }

                @Override
                public XmlTest getXmlTest() {
                    return null;
                }

                @Override
                public XmlClass getXmlClass() {
                    return null;
                }

                @Override
                public String getTestName() {
                    return null;
                }

                @Override
                public Class<?> getRealClass() {
                    return null;
                }

                @Override
                public Object[] getInstances(boolean b) {
                    return new Object[0];
                }

                @Override
                public int getInstanceCount() {
                    return 0;
                }

                @Override
                public long[] getInstanceHashCodes() {
                    return new long[0];
                }

                @Override
                public void addInstance(Object o) {

                }
            };
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
        public void setEndMillis(long l) {

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
                public List<com.google.inject.Module> getGuiceModules(Class<? extends com.google.inject.Module> aClass) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(List<com.google.inject.Module> list) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(IClass iClass) {
                    return null;
                }

                @Override
                public void addInjector(List<com.google.inject.Module> list, com.google.inject.Injector injector) {

                }

                @Override
                public Object getAttribute(String s) {
                    return null;
                }

                @Override
                public void setAttribute(String s, Object o) {

                }

                @Override
                public Set<String> getAttributeNames() {
                    return null;
                }

                @Override
                public Object removeAttribute(String s) {
                    return null;
                }
            };
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String s) {
            try {
                if (s.equals(BROWSER)) {
                    return new Browser("Chrome");
                }
                if (s.equals(REPORTER)) {
                    reporterFail.fail(null, null, null);
                    return reporterFail;
                }
            } catch (InvalidBrowserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String s) {
            return null;
        }
    };

    ITestResult resultCheckChrome = new ITestResult() {
        @Override
        public int getStatus() {
            return 3;
        }

        @Override
        public void setStatus(int i) {

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
                public void setTestClass(ITestClass iTestClass) {

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
                public void setMissingGroup(String s) {

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
                public void addMethodDependedUpon(String s) {

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
                public void setTimeOut(long l) {

                }

                @Override
                public int getInvocationCount() {
                    return 0;
                }

                @Override
                public void setInvocationCount(int i) {

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
                public void setId(String s) {

                }

                @Override
                public long getDate() {
                    return 0;
                }

                @Override
                public void setDate(long l) {

                }

                @Override
                public boolean canRunFromClass(IClass iClass) {
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
                public void setThreadPoolSize(int i) {

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
                public void setDescription(String s) {

                }

                @Override
                public void incrementCurrentInvocationCount() {

                }

                @Override
                public int getCurrentInvocationCount() {
                    return 0;
                }

                @Override
                public void setParameterInvocationCount(int i) {

                }

                @Override
                public int getParameterInvocationCount() {
                    return 0;
                }

                @Override
                public void setMoreInvocationChecker(Callable<Boolean> callable) {

                }

                @Override
                public boolean hasMoreInvocation() {
                    return false;
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
                public void setRetryAnalyzer(IRetryAnalyzer iRetryAnalyzer) {

                }

                @Override
                public boolean skipFailedInvocations() {
                    return false;
                }

                @Override
                public void setSkipFailedInvocations(boolean b) {

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
                public void setIgnoreMissingDependencies(boolean b) {

                }

                @Override
                public List<Integer> getInvocationNumbers() {
                    return null;
                }

                @Override
                public void setInvocationNumbers(List<Integer> list) {

                }

                @Override
                public void addFailedInvocationNumber(int i) {

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
                public void setPriority(int i) {

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
                public Map<String, String> findMethodParameters(XmlTest xmlTest) {
                    return null;
                }

                @Override
                public String getQualifiedName() {
                    return null;
                }
            };
        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public void setParameters(Object[] objects) {

        }

        @Override
        public IClass getTestClass() {
            return new IClass() {
                @Override
                public String getName() {
                    return "My Odd Sample Test Case";
                }

                @Override
                public XmlTest getXmlTest() {
                    return null;
                }

                @Override
                public XmlClass getXmlClass() {
                    return null;
                }

                @Override
                public String getTestName() {
                    return null;
                }

                @Override
                public Class<?> getRealClass() {
                    return null;
                }

                @Override
                public Object[] getInstances(boolean b) {
                    return new Object[0];
                }

                @Override
                public int getInstanceCount() {
                    return 0;
                }

                @Override
                public long[] getInstanceHashCodes() {
                    return new long[0];
                }

                @Override
                public void addInstance(Object o) {

                }
            };
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
        public void setEndMillis(long l) {

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
                public List<com.google.inject.Module> getGuiceModules(Class<? extends com.google.inject.Module> aClass) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(List<com.google.inject.Module> list) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(IClass iClass) {
                    return null;
                }

                @Override
                public void addInjector(List<com.google.inject.Module> list, com.google.inject.Injector injector) {

                }

                @Override
                public Object getAttribute(String s) {
                    return null;
                }

                @Override
                public void setAttribute(String s, Object o) {

                }

                @Override
                public Set<String> getAttributeNames() {
                    return null;
                }

                @Override
                public Object removeAttribute(String s) {
                    return null;
                }
            };
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String s) {
            try {
                if (s.equals(BROWSER)) {
                    return new Browser("Chrome");
                }
                if (s.equals(REPORTER)) {
                    return reporterCheck;
                }
            } catch (InvalidBrowserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String s) {
            return null;
        }
    };

    ITestResult resultChromeSkip = new ITestResult() {
        @Override
        public int getStatus() {
            return 1;
        }

        @Override
        public void setStatus(int i) {

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
                public void setTestClass(ITestClass iTestClass) {

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
                    return new String[]{"no-chrome"};
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
                public void setMissingGroup(String s) {

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
                public void addMethodDependedUpon(String s) {

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
                public void setTimeOut(long l) {

                }

                @Override
                public int getInvocationCount() {
                    return 0;
                }

                @Override
                public void setInvocationCount(int i) {

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
                public void setId(String s) {

                }

                @Override
                public long getDate() {
                    return 0;
                }

                @Override
                public void setDate(long l) {

                }

                @Override
                public boolean canRunFromClass(IClass iClass) {
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
                public void setThreadPoolSize(int i) {

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
                public void setDescription(String s) {

                }

                @Override
                public void incrementCurrentInvocationCount() {

                }

                @Override
                public int getCurrentInvocationCount() {
                    return 0;
                }

                @Override
                public void setParameterInvocationCount(int i) {

                }

                @Override
                public int getParameterInvocationCount() {
                    return 0;
                }

                @Override
                public void setMoreInvocationChecker(Callable<Boolean> callable) {

                }

                @Override
                public boolean hasMoreInvocation() {
                    return false;
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
                public void setRetryAnalyzer(IRetryAnalyzer iRetryAnalyzer) {

                }

                @Override
                public boolean skipFailedInvocations() {
                    return false;
                }

                @Override
                public void setSkipFailedInvocations(boolean b) {

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
                public void setIgnoreMissingDependencies(boolean b) {

                }

                @Override
                public List<Integer> getInvocationNumbers() {
                    return null;
                }

                @Override
                public void setInvocationNumbers(List<Integer> list) {

                }

                @Override
                public void addFailedInvocationNumber(int i) {

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
                public void setPriority(int i) {

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
                public Map<String, String> findMethodParameters(XmlTest xmlTest) {
                    return null;
                }

                @Override
                public String getQualifiedName() {
                    return null;
                }
            };
        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public void setParameters(Object[] objects) {

        }

        @Override
        public IClass getTestClass() {
            return new IClass() {
                @Override
                public String getName() {
                    return "[TestClass name=class myTestCase]";
                }

                @Override
                public XmlTest getXmlTest() {
                    return null;
                }

                @Override
                public XmlClass getXmlClass() {
                    return null;
                }

                @Override
                public String getTestName() {
                    return "[TestClass name=class myTestCase]";
                }

                @Override
                public Class<?> getRealClass() {
                    return null;
                }

                @Override
                public Object[] getInstances(boolean b) {
                    return new Object[0];
                }

                @Override
                public int getInstanceCount() {
                    return 0;
                }

                @Override
                public long[] getInstanceHashCodes() {
                    return new long[0];
                }

                @Override
                public void addInstance(Object o) {

                }
            };
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
        public void setEndMillis(long l) {

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
        public Object getAttribute(String s) {
            try {
                return new Browser("Chrome");
            } catch (InvalidBrowserException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String s) {
            return null;
        }
    };

    ITestResult resultChromeNoSkip = new ITestResult() {
        @Override
        public int getStatus() {
            return 1;
        }

        @Override
        public void setStatus(int i) {

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
                public void setTestClass(ITestClass iTestClass) {

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
                    return new String[]{"no-safari"};
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
                public void setMissingGroup(String s) {

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
                public void addMethodDependedUpon(String s) {

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
                public void setTimeOut(long l) {

                }

                @Override
                public int getInvocationCount() {
                    return 0;
                }

                @Override
                public void setInvocationCount(int i) {

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
                public void setId(String s) {

                }

                @Override
                public long getDate() {
                    return 0;
                }

                @Override
                public void setDate(long l) {

                }

                @Override
                public boolean canRunFromClass(IClass iClass) {
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
                public void setThreadPoolSize(int i) {

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
                public void setDescription(String s) {

                }

                @Override
                public void incrementCurrentInvocationCount() {

                }

                @Override
                public int getCurrentInvocationCount() {
                    return 0;
                }

                @Override
                public void setParameterInvocationCount(int i) {

                }

                @Override
                public int getParameterInvocationCount() {
                    return 0;
                }

                @Override
                public void setMoreInvocationChecker(Callable<Boolean> callable) {

                }

                @Override
                public boolean hasMoreInvocation() {
                    return false;
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
                public void setRetryAnalyzer(IRetryAnalyzer iRetryAnalyzer) {

                }

                @Override
                public boolean skipFailedInvocations() {
                    return false;
                }

                @Override
                public void setSkipFailedInvocations(boolean b) {

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
                public void setIgnoreMissingDependencies(boolean b) {

                }

                @Override
                public List<Integer> getInvocationNumbers() {
                    return null;
                }

                @Override
                public void setInvocationNumbers(List<Integer> list) {

                }

                @Override
                public void addFailedInvocationNumber(int i) {

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
                public void setPriority(int i) {

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
                public Map<String, String> findMethodParameters(XmlTest xmlTest) {
                    return null;
                }

                @Override
                public String getQualifiedName() {
                    return null;
                }
            };
        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public void setParameters(Object[] objects) {

        }

        @Override
        public IClass getTestClass() {
            return new IClass() {
                @Override
                public String getName() {
                    return "[TestClass name=class myTestCase]";
                }

                @Override
                public XmlTest getXmlTest() {
                    return null;
                }

                @Override
                public XmlClass getXmlClass() {
                    return null;
                }

                @Override
                public String getTestName() {
                    return "[TestClass name=class myTestCase]";
                }

                @Override
                public Class<?> getRealClass() {
                    return null;
                }

                @Override
                public Object[] getInstances(boolean b) {
                    return new Object[0];
                }

                @Override
                public int getInstanceCount() {
                    return 0;
                }

                @Override
                public long[] getInstanceHashCodes() {
                    return new long[0];
                }

                @Override
                public void addInstance(Object o) {

                }
            };
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
        public void setEndMillis(long l) {

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
        public Object getAttribute(String s) {
            try {
                return new Browser("Chrome");
            } catch (InvalidBrowserException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public Set<String> getAttributeNames() {
            return null;
        }

        @Override
        public Object removeAttribute(String s) {
            return null;
        }
    };

    ITestResult resultPassSauce = new ITestResult() {
        @Override
        public int getStatus() {
            return 1;
        }

        @Override
        public void setStatus(int i) {

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
                public void setTestClass(ITestClass iTestClass) {

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
                public void setMissingGroup(String s) {

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
                public void addMethodDependedUpon(String s) {

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
                public void setTimeOut(long l) {

                }

                @Override
                public int getInvocationCount() {
                    return 0;
                }

                @Override
                public void setInvocationCount(int i) {

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
                public void setId(String s) {

                }

                @Override
                public long getDate() {
                    return 0;
                }

                @Override
                public void setDate(long l) {

                }

                @Override
                public boolean canRunFromClass(IClass iClass) {
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
                public void setThreadPoolSize(int i) {

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
                public void setDescription(String s) {

                }

                @Override
                public void incrementCurrentInvocationCount() {

                }

                @Override
                public int getCurrentInvocationCount() {
                    return 0;
                }

                @Override
                public void setParameterInvocationCount(int i) {

                }

                @Override
                public int getParameterInvocationCount() {
                    return 0;
                }

                @Override
                public void setMoreInvocationChecker(Callable<Boolean> callable) {

                }

                @Override
                public boolean hasMoreInvocation() {
                    return false;
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
                public void setRetryAnalyzer(IRetryAnalyzer iRetryAnalyzer) {

                }

                @Override
                public boolean skipFailedInvocations() {
                    return false;
                }

                @Override
                public void setSkipFailedInvocations(boolean b) {

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
                public void setIgnoreMissingDependencies(boolean b) {

                }

                @Override
                public List<Integer> getInvocationNumbers() {
                    return null;
                }

                @Override
                public void setInvocationNumbers(List<Integer> list) {

                }

                @Override
                public void addFailedInvocationNumber(int i) {

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
                public void setPriority(int i) {

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
                public Map<String, String> findMethodParameters(XmlTest xmlTest) {
                    return null;
                }

                @Override
                public String getQualifiedName() {
                    return null;
                }
            };
        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public void setParameters(Object[] objects) {

        }

        @Override
        public IClass getTestClass() {
            return new IClass() {
                @Override
                public String getName() {
                    return "My Odd Sample Test Case";
                }

                @Override
                public XmlTest getXmlTest() {
                    return null;
                }

                @Override
                public XmlClass getXmlClass() {
                    return null;
                }

                @Override
                public String getTestName() {
                    return null;
                }

                @Override
                public Class<?> getRealClass() {
                    return null;
                }

                @Override
                public Object[] getInstances(boolean b) {
                    return new Object[0];
                }

                @Override
                public int getInstanceCount() {
                    return 0;
                }

                @Override
                public long[] getInstanceHashCodes() {
                    return new long[0];
                }

                @Override
                public void addInstance(Object o) {

                }
            };
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
        public void setEndMillis(long l) {

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
                public List<com.google.inject.Module> getGuiceModules(Class<? extends com.google.inject.Module> aClass) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(List<com.google.inject.Module> list) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(IClass iClass) {
                    return null;
                }

                @Override
                public void addInjector(List<com.google.inject.Module> list, com.google.inject.Injector injector) {

                }

                @Override
                public Object getAttribute(String s) {
                    return null;
                }

                @Override
                public void setAttribute(String s, Object o) {

                }

                @Override
                public Set<String> getAttributeNames() {
                    return null;
                }

                @Override
                public Object removeAttribute(String s) {
                    return null;
                }
            };
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String s) {
            try {
                if (s.equals(BROWSER)) {
                    return new Browser("Chrome");
                }
                if (s.equals(REPORTER)) {
                    return reporterPass;
                }
                if (s.equals(SESSION_ID)) {
                    return "12345";
                }
            } catch (InvalidBrowserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public Set<String> getAttributeNames() {
            Set<String> set = new HashSet();
            set.add(BROWSER);
            set.add(REPORTER);
            set.add(SESSION_ID);
            return set;
        }

        @Override
        public Object removeAttribute(String s) {
            return null;
        }
    };

    ITestResult resultFailSauce = new ITestResult() {
        @Override
        public int getStatus() {
            return 2;
        }

        @Override
        public void setStatus(int i) {

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
                public void setTestClass(ITestClass iTestClass) {

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
                    return new String[]{"group"};
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
                public void setMissingGroup(String s) {

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
                public void addMethodDependedUpon(String s) {

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
                public void setTimeOut(long l) {

                }

                @Override
                public int getInvocationCount() {
                    return 0;
                }

                @Override
                public void setInvocationCount(int i) {

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
                public void setId(String s) {

                }

                @Override
                public long getDate() {
                    return 0;
                }

                @Override
                public void setDate(long l) {

                }

                @Override
                public boolean canRunFromClass(IClass iClass) {
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
                public void setThreadPoolSize(int i) {

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
                public void setDescription(String s) {

                }

                @Override
                public void incrementCurrentInvocationCount() {

                }

                @Override
                public int getCurrentInvocationCount() {
                    return 0;
                }

                @Override
                public void setParameterInvocationCount(int i) {

                }

                @Override
                public int getParameterInvocationCount() {
                    return 0;
                }

                @Override
                public void setMoreInvocationChecker(Callable<Boolean> callable) {

                }

                @Override
                public boolean hasMoreInvocation() {
                    return false;
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
                public void setRetryAnalyzer(IRetryAnalyzer iRetryAnalyzer) {

                }

                @Override
                public boolean skipFailedInvocations() {
                    return false;
                }

                @Override
                public void setSkipFailedInvocations(boolean b) {

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
                public void setIgnoreMissingDependencies(boolean b) {

                }

                @Override
                public List<Integer> getInvocationNumbers() {
                    return null;
                }

                @Override
                public void setInvocationNumbers(List<Integer> list) {

                }

                @Override
                public void addFailedInvocationNumber(int i) {

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
                public void setPriority(int i) {

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
                public Map<String, String> findMethodParameters(XmlTest xmlTest) {
                    return null;
                }

                @Override
                public String getQualifiedName() {
                    return null;
                }
            };
        }

        @Override
        public Object[] getParameters() {
            return new Object[0];
        }

        @Override
        public void setParameters(Object[] objects) {

        }

        @Override
        public IClass getTestClass() {
            return new IClass() {
                @Override
                public String getName() {
                    return "My Odd Sample Test Case";
                }

                @Override
                public XmlTest getXmlTest() {
                    return null;
                }

                @Override
                public XmlClass getXmlClass() {
                    return null;
                }

                @Override
                public String getTestName() {
                    return null;
                }

                @Override
                public Class<?> getRealClass() {
                    return null;
                }

                @Override
                public Object[] getInstances(boolean b) {
                    return new Object[0];
                }

                @Override
                public int getInstanceCount() {
                    return 0;
                }

                @Override
                public long[] getInstanceHashCodes() {
                    return new long[0];
                }

                @Override
                public void addInstance(Object o) {

                }
            };
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
        public void setEndMillis(long l) {

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
                public List<com.google.inject.Module> getGuiceModules(Class<? extends com.google.inject.Module> aClass) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(List<com.google.inject.Module> list) {
                    return null;
                }

                @Override
                public com.google.inject.Injector getInjector(IClass iClass) {
                    return null;
                }

                @Override
                public void addInjector(List<com.google.inject.Module> list, com.google.inject.Injector injector) {

                }

                @Override
                public Object getAttribute(String s) {
                    return null;
                }

                @Override
                public void setAttribute(String s, Object o) {

                }

                @Override
                public Set<String> getAttributeNames() {
                    return null;
                }

                @Override
                public Object removeAttribute(String s) {
                    return null;
                }
            };
        }

        @Override
        public int compareTo(ITestResult o) {
            return 0;
        }

        @Override
        public Object getAttribute(String s) {
            try {
                if (s.equals(BROWSER)) {
                    return new Browser("Chrome");
                }
                if (s.equals(REPORTER)) {
                    return reporterFail;
                }
                if (s.equals(SESSION_ID)) {
                    return "12345";
                }
            } catch (InvalidBrowserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public Set<String> getAttributeNames() {
            Set<String> set = new HashSet();
            set.add(BROWSER);
            set.add(REPORTER);
            set.add(SESSION_ID);
            return set;
        }

        @Override
        public Object removeAttribute(String s) {
            return null;
        }
    };

    public ListenerTest() throws InvalidBrowserException, InvalidProxyException {
    }

    @Test
    public void onTestStartNullBrowserTest() {
        Listener listener = new Listener();
        listener.onTestStart(resultPass);
    }

    @Test
    public void onTestStartDefaultTest() {
        Listener listener = new Listener();
        listener.onTestStart(resultPassChrome);
    }

    @Test
    public void onTestStartNoSkipTest() {
        Listener listener = new Listener();
        listener.onTestStart(resultChromeNoSkip);
    }

    @Test
    public void onTestStartSkipTest() {
        Listener listener = new Listener();
        listener.onTestStart(resultChromeSkip);
    }

    @Test
    public void onTestSuccessNullTest() throws IOException {
        Listener listener = new Listener();
        listener.onTestSuccess(resultP);
        File file = new File("directory", "fileP.html");
        assertFalse(file.exists());
    }

    @Test
    public void onTestSkippedDefaultTest() throws IOException {
        Listener listener = new Listener();
        listener.onTestSkipped(resultCheckChrome);
        File file = new File("directory", "fileC.html");
        assertFalse(file.exists());
    }

    @Test
    public void onTestFailureDefaultTest() throws IOException {
        Listener listener = new Listener();
        listener.onTestFailure(resultFailChrome);
        File file = new File("directory", "fileF.html");
        assertTrue(file.exists());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<b>FAIL</b>"));
        file.delete();
        new File("directory").delete();
    }

    @Test
    public void onTestSuccessDefaultTest() throws IOException {
        Listener listener = new Listener();
        listener.onTestSuccess(resultPassChrome);
        File file = new File("directory", "fileP.html");
        assertTrue(file.exists());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<b>PASS</b>"));
        file.delete();
        new File("directory").delete();
    }

    @Test
    public void onTestSuccessPdfTest() {
        System.setProperty("generatePDF", "true");
        Listener listener = new Listener();
        listener.onTestSuccess(resultPassChrome);
        File file = new File("directory", "fileP.pdf");
        assertTrue(file.exists());
        file.delete();
        new File("directory").delete();
    }

    @Test
    public void onTestSuccessSauceTest() {
        System.setProperty("hub", "ondemand.saucelabs.com");
        Listener listener = new Listener();
        listener.onTestSuccess(resultPassChrome);
        File file = new File("directory", "fileP.html");
        assertTrue(file.exists());
        file.delete();
        new File("directory").delete();
    }

    @Test
    public void onTestSuccessSauceSessionTest() {
        System.setProperty("hub", "https://sauceusername:saucekey@ondemand.saucelabs.com");
        Listener listener = new Listener();
        listener.onTestSuccess(resultPassSauce);
        File file = new File("directory", "fileP.html");
        assertTrue(file.exists());
        file.delete();
        new File("directory").delete();
    }

    @Test
    public void onTestFailureSauceSessionTest() {
        System.setProperty("hub", "ondemand.saucelabs.com");
        Listener listener = new Listener();
        listener.onTestFailure(resultFailSauce);
        File file = new File("directory", "fileF.html");
        assertTrue(file.exists());
        file.delete();
        new File("directory").delete();
    }
}
