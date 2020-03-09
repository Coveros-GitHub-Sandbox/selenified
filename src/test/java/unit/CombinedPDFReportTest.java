package unit;

import com.coveros.selenified.utilities.CombinedPDFReport;
import com.google.inject.Injector;
import org.testng.*;
import org.testng.annotations.Test;
import org.testng.internal.ConstructorOrMethod;
import org.testng.internal.annotations.IAnnotationFinder;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;

import static org.testng.Assert.*;

public class CombinedPDFReportTest extends CombinedPDFReport {

    @Test
    public void getTestSuiteMapTest() {
        Map<String, List<String>> tests = new HashMap<>();
        List<String> methodNames = new ArrayList<>();
        methodNames.add("methodName");
        tests.put("TestClass", methodNames);
        List<ITestNGMethod> iTestNGMethods = getiTestNGMethodsTestObject(tests);
        ISuite iSuite1 = getiSuiteTestObject(null, "dir1");
        ISuite iSuite2 = getiSuiteTestObject(iTestNGMethods, "dir2");
        List<ISuite> iSuiteList = new ArrayList<>();
        iSuiteList.add(iSuite1);
        iSuiteList.add(iSuite2);
        Map<String, List<ITestNGMethod>> map = new HashMap<>();
        map.put("dir1", null);
        map.put("dir2", iTestNGMethods);
        assertEquals(map, getTestSuiteMap(iSuiteList));
    }

    @Test
    public void getTestSuiteMapTest2() {
        Map<String, List<String>> tests = new HashMap<>();
        List<String> methodNames = new ArrayList<>();
        methodNames.add("methodName");
        tests.put("TestClass", methodNames);
        List<ITestNGMethod> iTestNGMethods = getiTestNGMethodsTestObject(tests);
        ISuite iSuite1 = getiSuiteTestObject(iTestNGMethods, "dir1");
        List<ISuite> iSuiteList = new ArrayList<>();
        iSuiteList.add(iSuite1);
        Map<String, List<ITestNGMethod>> map = new HashMap<>();
        map.put("dir1", iTestNGMethods);
        assertEquals(map, getTestSuiteMap(iSuiteList));
    }

    @Test
    public void getAllTestReportFilesTest() throws IOException {
        Map<String, List<String>> tests = new HashMap<>();
        List<String> methodNames = new ArrayList<>();
        methodNames.add("methodName");
        tests.put("TestClass", methodNames);
        List<ITestNGMethod> iTestNGMethods = getiTestNGMethodsTestObject(tests);
        ISuite iSuite1 = getiSuiteTestObject(iTestNGMethods, "dir1");
        List<ISuite> iSuiteList = new ArrayList<>();
        iSuiteList.add(iSuite1);
        Map<String, List<ITestNGMethod>> map = getTestSuiteMap(iSuiteList);
        List<File> expectedPDFList = new ArrayList<>();
        expectedPDFList.add(new File("dir1/TestClass.methodName.pdf"));
        createTestFileHelper(map);
        assertEquals(expectedPDFList, getAllTestReportFiles(map));
        cleanupFileHelper(map);
    }

    @Test
    public void getAllTestReportFilesTest2() throws IOException {
        Map<String, List<String>> tests = new HashMap<>();
        List<String> methodNames = new ArrayList<>();
        methodNames.add("methodName");
        methodNames.add("anotherMethodName");
        tests.put("TestClass", methodNames);
        List<ITestNGMethod> iTestNGMethods = getiTestNGMethodsTestObject(tests);
        ISuite iSuite1 = getiSuiteTestObject(iTestNGMethods, "dir1");
        List<ISuite> iSuiteList = new ArrayList<>();
        iSuiteList.add(iSuite1);
        Map<String, List<ITestNGMethod>> map = getTestSuiteMap(iSuiteList);
        List<File> expectedPDFList = new ArrayList<>();
        expectedPDFList.add(new File("dir1/TestClass.methodName.pdf"));
        expectedPDFList.add(new File("dir1/TestClass.anotherMethodName.pdf"));
        createTestFileHelper(map);
        assertEquals(expectedPDFList, getAllTestReportFiles(map));
        cleanupFileHelper(map);
    }

    @Test
    public void getAllTestReportFilesTest3() throws IOException {
        Map<String, List<String>> tests = new HashMap<>();
        List<String> methodNames = new ArrayList<>();
        methodNames.add("methodName");
        methodNames.add("anotherMethodName");
        tests.put("TestClass", methodNames);
        tests.put("AnotherTestClass", methodNames);
        List<ITestNGMethod> iTestNGMethods = getiTestNGMethodsTestObject(tests);
        ISuite iSuite1 = getiSuiteTestObject(iTestNGMethods, "dir1");
        List<ISuite> iSuiteList = new ArrayList<>();
        iSuiteList.add(iSuite1);
        Map<String, List<ITestNGMethod>> map = getTestSuiteMap(iSuiteList);
        List<File> expectedPDFList = new ArrayList<>();
        expectedPDFList.add(new File("dir1/AnotherTestClass.methodName.pdf"));
        expectedPDFList.add(new File("dir1/AnotherTestClass.anotherMethodName.pdf"));
        expectedPDFList.add(new File("dir1/TestClass.methodName.pdf"));
        expectedPDFList.add(new File("dir1/TestClass.anotherMethodName.pdf"));
        createTestFileHelper(map);
        assertEquals(expectedPDFList, getAllTestReportFiles(map));
        cleanupFileHelper(map);
    }

    @Test
    public void getAllTestReportFilesTest4() throws IOException {
        Map<String, List<String>> tests = new HashMap<>();
        List<String> methodNames = new ArrayList<>();
        methodNames.add("methodName");
        methodNames.add("anotherMethodName");
        tests.put("TestClass", methodNames);
        tests.put("AnotherTestClass", methodNames);
        List<ITestNGMethod> iTestNGMethods = getiTestNGMethodsTestObject(tests);
        ISuite iSuite1 = getiSuiteTestObject(iTestNGMethods, "dir1");
        ISuite iSuite2 = getiSuiteTestObject(iTestNGMethods, "dir2");
        List<ISuite> iSuiteList = new ArrayList<>();
        iSuiteList.add(iSuite1);
        iSuiteList.add(iSuite2);
        Map<String, List<ITestNGMethod>> map = getTestSuiteMap(iSuiteList);
        List<File> expectedPDFList = new ArrayList<>();
        expectedPDFList.add(new File("dir2/AnotherTestClass.methodName.pdf"));
        expectedPDFList.add(new File("dir2/AnotherTestClass.anotherMethodName.pdf"));
        expectedPDFList.add(new File("dir2/TestClass.methodName.pdf"));
        expectedPDFList.add(new File("dir2/TestClass.anotherMethodName.pdf"));
        expectedPDFList.add(new File("dir1/AnotherTestClass.methodName.pdf"));
        expectedPDFList.add(new File("dir1/AnotherTestClass.anotherMethodName.pdf"));
        expectedPDFList.add(new File("dir1/TestClass.methodName.pdf"));
        expectedPDFList.add(new File("dir1/TestClass.anotherMethodName.pdf"));
        createTestFileHelper(map);
        assertEquals(expectedPDFList, getAllTestReportFiles(map));
        cleanupFileHelper(map);
    }

    @Test
    public void getAllTestReportFilesTestWithoutCreatingIndivPDF() throws IOException {
        Map<String, List<String>> tests = new HashMap<>();
        List<String> methodNames = new ArrayList<>();
        methodNames.add("methodName");
        tests.put("TestClass", methodNames);
        List<ITestNGMethod> iTestNGMethods = getiTestNGMethodsTestObject(tests);
        ISuite iSuite1 = getiSuiteTestObject(iTestNGMethods, "dir1");
        List<ISuite> iSuiteList = new ArrayList<>();
        iSuiteList.add(iSuite1);
        Map<String, List<ITestNGMethod>> map = getTestSuiteMap(iSuiteList);
        assertEquals(new ArrayList<>(), getAllTestReportFiles(map));
    }

    private ISuite getiSuiteTestObject(List<ITestNGMethod> iTestNGMethods, String outputDirectory) {
        return new ISuite() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public Map<String, ISuiteResult> getResults() {
                return null;
            }

            @Override
            public IObjectFactory getObjectFactory() {
                return null;
            }

            @Override
            public IObjectFactory2 getObjectFactory2() {
                return null;
            }

            @Override
            public String getOutputDirectory() {
                return outputDirectory;
            }

            @Override
            public String getParallel() {
                return null;
            }

            @Override
            public String getParentModule() {
                return null;
            }

            @Override
            public String getGuiceStage() {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Map<String, Collection<ITestNGMethod>> getMethodsByGroups() {
                return null;
            }

            @Override
            public List<IInvokedMethod> getAllInvokedMethods() {
                return null;
            }

            @Override
            public Collection<ITestNGMethod> getExcludedMethods() {
                return null;
            }

            @Override
            public void run() {

            }

            @Override
            public String getHost() {
                return null;
            }

            @Override
            public SuiteRunState getSuiteState() {
                return null;
            }

            @Override
            public IAnnotationFinder getAnnotationFinder() {
                return null;
            }

            @Override
            public XmlSuite getXmlSuite() {
                return null;
            }

            @Override
            public void addListener(ITestNGListener iTestNGListener) {

            }

            @Override
            public Injector getParentInjector() {
                return null;
            }

            @Override
            public void setParentInjector(Injector injector) {

            }

            @Override
            public List<ITestNGMethod> getAllMethods() {
                return iTestNGMethods;
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

    private List<ITestNGMethod> getiTestNGMethodsTestObject(Map<String, List<String>> tests) {
        List<ITestNGMethod> list = new ArrayList<>();
        for (Map.Entry<String, List<String>> testsEntry : tests.entrySet()) {
            for (String methodName : testsEntry.getValue()) {
                ITestNGMethod iTestNGMethod = new ITestNGMethod() {
                    @Override
                    public Class getRealClass() {
                        return null;
                    }

                    @Override
                    public ITestClass getTestClass() {
                        return new ITestClass() {
                            @Override
                            public ITestNGMethod[] getTestMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getBeforeTestMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getAfterTestMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getBeforeClassMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getAfterClassMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getBeforeSuiteMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getAfterSuiteMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getBeforeTestConfigurationMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getAfterTestConfigurationMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getBeforeGroupsMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public ITestNGMethod[] getAfterGroupsMethods() {
                                return new ITestNGMethod[0];
                            }

                            @Override
                            public String getName() {
                                return testsEntry.getKey();
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
                            public long[] getInstanceHashCodes() {
                                return new long[0];
                            }

                            @Override
                            public void addInstance(Object o) {

                            }
                        };
                    }

                    @Override
                    public void setTestClass(ITestClass iTestClass) {

                    }

                    @Override
                    public String getMethodName() {
                        return methodName;
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
                    public int getInterceptedPriority() {
                        return 0;
                    }

                    @Override
                    public void setInterceptedPriority(int i) {

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
                list.add(iTestNGMethod);
            }
        }
        return list;
    }

    /**
     * To facilitate testing, create the test file from the test reports map
     * @param testReportsMap map of test reports
     */
    protected void createTestFileHelper(Map<String, List<ITestNGMethod>> testReportsMap) throws IOException {
        for (Map.Entry<String, List<ITestNGMethod>> testReportsMapEntry : testReportsMap.entrySet()) {
            String testReportDirectory = testReportsMapEntry.getKey();
            for (ITestNGMethod iTestNGMethod : testReportsMapEntry.getValue()) {
                File testReport = new File(testReportDirectory,
                        iTestNGMethod.getTestClass().getName() + "." + iTestNGMethod.getMethodName() + ".pdf");
                testReport.getParentFile().mkdirs();
                testReport.createNewFile();
            }
        }
    }

    /**
     * To facilitate testing, delete the test files from the test reports map
     * @param testReportsMap map of test reports
     */
    protected void cleanupFileHelper(Map<String, List<ITestNGMethod>> testReportsMap) throws IOException {
        for (Map.Entry<String, List<ITestNGMethod>> testReportsMapEntry : testReportsMap.entrySet()) {
            String testReportDirectory = testReportsMapEntry.getKey();
            for (ITestNGMethod iTestNGMethod : testReportsMapEntry.getValue()) {
                File testReport = new File(testReportDirectory,
                        iTestNGMethod.getTestClass().getName() + "." + iTestNGMethod.getMethodName() + ".pdf");
                testReport.delete();
            }
            new File(testReportDirectory).delete();
        }
    }
}
