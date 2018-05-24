//package unit;
//
//import com.coveros.selenified.Browser;
//import com.coveros.selenified.Browser.BrowserName;
//import com.coveros.selenified.OutputFile;
//import com.coveros.selenified.application.App;
//import com.coveros.selenified.utilities.Jira;
//import com.coveros.selenified.utilities.Listener;
//import org.mockserver.integration.ClientAndServer;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.testng.*;
//import org.testng.annotations.*;
//import org.testng.internal.ConstructorOrMethod;
//import org.testng.xml.XmlTest;
//
//import java.io.File;
//import java.lang.reflect.Method;
//import java.util.*;
//
//import static org.mockserver.integration.ClientAndServer.startClientAndServer;
//import static org.mockserver.model.HttpRequest.request;
//import static org.mockserver.model.HttpResponse.response;
//
//public class ListenerMockTest extends Listener {
//
//    private String jiraLink;
//    private ClientAndServer mockServer;
//    private OutputFile outputFile;
//    private File directory;
//    private File file;
//
//    @BeforeClass
//    public void setupArrays() {
//        if (System.getProperty("jira.link") != null) {
//            jiraLink = System.getProperty("jira.link");
//        }
//        System.setProperty("jira.link", "http://localhost:1080/jira");
//    }
//
//    @AfterClass
//    public void restoreBrowser() {
//        System.clearProperty("jira.link");
//        if (jiraLink != null) {
//            System.setProperty("jira.link", jiraLink);
//        }
//    }
//
//    @BeforeMethod
//    public void startMockServer() {
//        mockServer = startClientAndServer(1080);
//        outputFile =
//                new OutputFile("directory", "file", new Browser(Browser.BrowserName.ANDROID), null, null, null, null,
//                        null, null);
//        directory = new File("directory");
//        file = new File("directory", "fileANDROID.html");
//    }
//
//    @AfterMethod
//    public void stopMockServer() {
//        mockServer.stop();
//        file.delete();
//        directory.delete();
//    }
//
//    @Test
//    @Jira(project = "HW")
//    public void recordZephyrNoIssueTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 0;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return new File("somefile");
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "FB", issue = "FB-987654")
//    public void recordZephyrBadCycleTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/FB")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/FB-987654")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(406).withBody("{\"projectId\":\"Project Id (projectId) is required.\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 0;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return new File("somefile");
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "HW", issue = "FB-987654")
//    public void recordZephyrInvalidIssueTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/FB-987654")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"id\":\"16471\",\"responseMessage\":\"Cycle 16471 created successfully.\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution/addTestsToCycle"))
//                .respond(response().withStatusCode(200).withBody(
//                        "{\"warn\":\"The following just happened: <br/> Tests added: <strong>-</strong> " +
//                                "<br/> <p>These Tests could not be added <br/> Invalid: <strong>-</strong> <br/> " +
//                                "Belongs to another project: <strong><a href='http://localhost:1080/jira/browse/FB-987654'>" +
//                                "FB-987654</a></strong> <br/> Already present in this Test " +
//                                "Cycle: <strong>-</strong><br/> Not a Test: " + "<strong>-" +
//                                "</strong><br/> Permission Denied: <strong>-</strong><br/>\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 0;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return new File("somefile");
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "HW", issue = "HW-123456")
//    public void recordZephyrInvalidExecutionTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/HW-123456")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"id\":\"16471\",\"responseMessage\":\"Cycle 16471 created successfully.\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution/addTestsToCycle"))
//                .respond(response().withStatusCode(200).withBody(
//                        "{\"warn\":\"The following just happened: <br/> Tests added: " +
//                                "<strong><a href='http://localhost:1080/jira/browse/HW-123456'>HW-123456</a></strong>" +
//                                " <br/> <p>These Tests could not be added <br/> Invalid: <strong>-</strong> <br/> " +
//                                "Belongs to another project: <strong>-</strong> <br/> Already present in this Test " +
//                                "Cycle: <strong>-</strong><br/> Not a Test: " + "<strong>-" +
//                                "</strong><br/> Permission Denied: <strong>-</strong><br/>\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution")).respond(
//                response().withStatusCode(400).withBody("{\"Invalid Project\":\"projectId  1180 is Invalid.\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 0;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return new File("somefile");
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "HW", issue = "HW-123456")
//    public void recordZephyrBadExecutionTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/HW-123456")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"id\":\"16471\",\"responseMessage\":\"Cycle 16471 created successfully.\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution/addTestsToCycle"))
//                .respond(response().withStatusCode(200).withBody(
//                        "{\"warn\":\"The following just happened: <br/> Tests added: " +
//                                "<strong><a href='http://localhost:1080/jira/browse/HW-123456'>HW-123456</a></strong>" +
//                                " <br/> <p>These Tests could not be added <br/> Invalid: <strong>-</strong> <br/> " +
//                                "Belongs to another project: <strong>-</strong> <br/> Already present in this Test " +
//                                "Cycle: <strong>-</strong><br/> Not a Test: " + "<strong>-" +
//                                "</strong><br/> Permission Denied: <strong>-</strong><br/>\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution")).respond(
//                response().withStatusCode(200).withBody("{\"456789\":{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":16471," +
//                        "\"cycleName\":\"Sample\",\"versionId\":-1,\"versionName\":\"Unscheduled\"," +
//                        "\"projectId\":12345,\"createdBy\":\"max.saperstone\",\"modifiedBy\":\"max.saperstone\"," +
//                        "\"issueId\":234567,\"issueKey\":\"HW-123456\",\"summary\":\"Some Sample Test Case\"," +
//                        "\"issueDescription\":\"Some Generic Description\",\"label\":\"\",\"component\":\"\"," +
//                        "\"projectKey\":\"HW\"}}"));
//        mockServer.when(request().withMethod("PUT").withPath("/jira/rest/zapi/latest/execution/456789/execute"))
//                .respond(response().withStatusCode(400).withBody(
//                        "{\"error\":\"Error in creating Execution, please check value of 456789 and retry\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 1;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return new File("somefile");
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "HW", issue = "HW-123456")
//    public void recordZephyrPassTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/HW-123456")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"id\":\"16471\",\"responseMessage\":\"Cycle 16471 created successfully.\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution/addTestsToCycle"))
//                .respond(response().withStatusCode(200).withBody(
//                        "{\"warn\":\"The following just happened: <br/> Tests added: " +
//                                "<strong><a href='http://localhost:1080/jira/browse/HW-123456'>HW-123456</a></strong>" +
//                                " <br/> <p>These Tests could not be added <br/> Invalid: <strong>-</strong> <br/> " +
//                                "Belongs to another project: <strong>-</strong> <br/> Already present in this Test " +
//                                "Cycle: <strong>-</strong><br/> Not a Test: " + "<strong>-" +
//                                "</strong><br/> Permission Denied: <strong>-</strong><br/>\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution")).respond(
//                response().withStatusCode(200).withBody("{\"456789\":{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":16471," +
//                        "\"cycleName\":\"Sample\",\"versionId\":-1,\"versionName\":\"Unscheduled\"," +
//                        "\"projectId\":12345,\"createdBy\":\"max.saperstone\",\"modifiedBy\":\"max.saperstone\"," +
//                        "\"issueId\":234567,\"issueKey\":\"HW-123456\",\"summary\":\"Some Sample Test Case\"," +
//                        "\"issueDescription\":\"Some Generic Description\",\"label\":\"\",\"component\":\"\"," +
//                        "\"projectKey\":\"HW\"}}"));
//        mockServer.when(request().withMethod("PUT").withPath("/jira/rest/zapi/latest/execution/456789/execute"))
//                .respond(response().withStatusCode(200).withBody("{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"1\",\"executedOn\":\"Today 9:54 AM\"," +
//                        "\"executedBy\":\"max.saperstone\",\"executedByDisplay\":\"max saperstone\",\"comment\":\"" +
//                        "\",\"htmlComment\":\"\",\"cycleId\":16471,\"cycleName\":\"Sample\",\"versionId\":-1," +
//                        "\"versionName\":\"Unscheduled\",\"projectId\":12345,\"createdBy\":\"max.saperstone\"," +
//                        "\"modifiedBy\":\"max.saperstone\",\"issueId\":234567,\"issueKey\":\"HW-123456\"," +
//                        "\"summary\":\"Some Sample Test Case\",\"issueDescription\":\"Some Generic " +
//                        "Description\"," + "\"label\":\"\"," + "\"component\":\"\",\"projectKey\":\"HW\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/attachment")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"success\":\"File(s) fileANDROID.html successfully uploaded\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 1;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return outputFile;
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "HW", issue = "HW-123456")
//    public void recordZephyrFailTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/HW-123456")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"id\":\"16471\",\"responseMessage\":\"Cycle 16471 created successfully.\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution/addTestsToCycle"))
//                .respond(response().withStatusCode(200).withBody(
//                        "{\"warn\":\"The following just happened: <br/> Tests added: " +
//                                "<strong><a href='http://localhost:1080/jira/browse/HW-123456'>HW-123456</a></strong>" +
//                                " <br/> <p>These Tests could not be added <br/> Invalid: <strong>-</strong> <br/> " +
//                                "Belongs to another project: <strong>-</strong> <br/> Already present in this Test " +
//                                "Cycle: <strong>-</strong><br/> Not a Test: " + "<strong>-" +
//                                "</strong><br/> Permission Denied: <strong>-</strong><br/>\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution")).respond(
//                response().withStatusCode(200).withBody("{\"456789\":{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":16471," +
//                        "\"cycleName\":\"Sample\",\"versionId\":-1,\"versionName\":\"Unscheduled\"," +
//                        "\"projectId\":12345,\"createdBy\":\"max.saperstone\",\"modifiedBy\":\"max.saperstone\"," +
//                        "\"issueId\":234567,\"issueKey\":\"HW-123456\",\"summary\":\"Some Sample Test Case\"," +
//                        "\"issueDescription\":\"Some Generic Description\",\"label\":\"\",\"component\":\"\"," +
//                        "\"projectKey\":\"HW\"}}"));
//        mockServer.when(request().withMethod("PUT").withPath("/jira/rest/zapi/latest/execution/456789/execute"))
//                .respond(response().withStatusCode(200).withBody("{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"1\",\"executedOn\":\"Today 9:54 AM\"," +
//                        "\"executedBy\":\"max.saperstone\",\"executedByDisplay\":\"max saperstone\",\"comment\":\"" +
//                        "\",\"htmlComment\":\"\",\"cycleId\":16471,\"cycleName\":\"Sample\",\"versionId\":-1," +
//                        "\"versionName\":\"Unscheduled\",\"projectId\":12345,\"createdBy\":\"max.saperstone\"," +
//                        "\"modifiedBy\":\"max.saperstone\",\"issueId\":234567,\"issueKey\":\"HW-123456\"," +
//                        "\"summary\":\"Some Sample Test Case\",\"issueDescription\":\"Some Generic " +
//                        "Description\"," + "\"label\":\"\"," + "\"component\":\"\",\"projectKey\":\"HW\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/attachment")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"success\":\"File(s) fileANDROID.html successfully uploaded\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 2;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return outputFile;
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "HW", issue = "HW-123456")
//    public void recordZephyrSkipTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/HW-123456")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"id\":\"16471\",\"responseMessage\":\"Cycle 16471 created successfully.\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution/addTestsToCycle"))
//                .respond(response().withStatusCode(200).withBody(
//                        "{\"warn\":\"The following just happened: <br/> Tests added: " +
//                                "<strong><a href='http://localhost:1080/jira/browse/HW-123456'>HW-123456</a></strong>" +
//                                " <br/> <p>These Tests could not be added <br/> Invalid: <strong>-</strong> <br/> " +
//                                "Belongs to another project: <strong>-</strong> <br/> Already present in this Test " +
//                                "Cycle: <strong>-</strong><br/> Not a Test: " + "<strong>-" +
//                                "</strong><br/> Permission Denied: <strong>-</strong><br/>\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution")).respond(
//                response().withStatusCode(200).withBody("{\"456789\":{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":16471," +
//                        "\"cycleName\":\"Sample\",\"versionId\":-1,\"versionName\":\"Unscheduled\"," +
//                        "\"projectId\":12345,\"createdBy\":\"max.saperstone\",\"modifiedBy\":\"max.saperstone\"," +
//                        "\"issueId\":234567,\"issueKey\":\"HW-123456\",\"summary\":\"Some Sample Test Case\"," +
//                        "\"issueDescription\":\"Some Generic Description\",\"label\":\"\",\"component\":\"\"," +
//                        "\"projectKey\":\"HW\"}}"));
//        mockServer.when(request().withMethod("PUT").withPath("/jira/rest/zapi/latest/execution/456789/execute"))
//                .respond(response().withStatusCode(200).withBody("{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"1\",\"executedOn\":\"Today 9:54 AM\"," +
//                        "\"executedBy\":\"max.saperstone\",\"executedByDisplay\":\"max saperstone\",\"comment\":\"" +
//                        "\",\"htmlComment\":\"\",\"cycleId\":16471,\"cycleName\":\"Sample\",\"versionId\":-1," +
//                        "\"versionName\":\"Unscheduled\",\"projectId\":12345,\"createdBy\":\"max.saperstone\"," +
//                        "\"modifiedBy\":\"max.saperstone\",\"issueId\":234567,\"issueKey\":\"HW-123456\"," +
//                        "\"summary\":\"Some Sample Test Case\",\"issueDescription\":\"Some Generic " +
//                        "Description\"," + "\"label\":\"\"," + "\"component\":\"\",\"projectKey\":\"HW\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/attachment")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"success\":\"File(s) fileANDROID.html successfully uploaded\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 3;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return outputFile;
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "HW", issue = "HW-123456")
//    public void recordZephyrWarningTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/HW-123456")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"id\":\"16471\",\"responseMessage\":\"Cycle 16471 created successfully.\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution/addTestsToCycle"))
//                .respond(response().withStatusCode(200).withBody(
//                        "{\"warn\":\"The following just happened: <br/> Tests added: " +
//                                "<strong><a href='http://localhost:1080/jira/browse/HW-123456'>HW-123456</a></strong>" +
//                                " <br/> <p>These Tests could not be added <br/> Invalid: <strong>-</strong> <br/> " +
//                                "Belongs to another project: <strong>-</strong> <br/> Already present in this Test " +
//                                "Cycle: <strong>-</strong><br/> Not a Test: " + "<strong>-" +
//                                "</strong><br/> Permission Denied: <strong>-</strong><br/>\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution")).respond(
//                response().withStatusCode(200).withBody("{\"456789\":{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":16471," +
//                        "\"cycleName\":\"Sample\",\"versionId\":-1,\"versionName\":\"Unscheduled\"," +
//                        "\"projectId\":12345,\"createdBy\":\"max.saperstone\",\"modifiedBy\":\"max.saperstone\"," +
//                        "\"issueId\":234567,\"issueKey\":\"HW-123456\",\"summary\":\"Some Sample Test Case\"," +
//                        "\"issueDescription\":\"Some Generic Description\",\"label\":\"\",\"component\":\"\"," +
//                        "\"projectKey\":\"HW\"}}"));
//        mockServer.when(request().withMethod("PUT").withPath("/jira/rest/zapi/latest/execution/456789/execute"))
//                .respond(response().withStatusCode(200).withBody("{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"1\",\"executedOn\":\"Today 9:54 AM\"," +
//                        "\"executedBy\":\"max.saperstone\",\"executedByDisplay\":\"max saperstone\",\"comment\":\"" +
//                        "\",\"htmlComment\":\"\",\"cycleId\":16471,\"cycleName\":\"Sample\",\"versionId\":-1," +
//                        "\"versionName\":\"Unscheduled\",\"projectId\":12345,\"createdBy\":\"max.saperstone\"," +
//                        "\"modifiedBy\":\"max.saperstone\",\"issueId\":234567,\"issueKey\":\"HW-123456\"," +
//                        "\"summary\":\"Some Sample Test Case\",\"issueDescription\":\"Some Generic " +
//                        "Description\"," + "\"label\":\"\"," + "\"component\":\"\",\"projectKey\":\"HW\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/attachment")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"success\":\"File(s) fileANDROID.html successfully uploaded\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 4;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return outputFile;
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "HW", issue = "HW-123456")
//    public void recordZephyrBlockedTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/HW-123456")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"id\":\"16471\",\"responseMessage\":\"Cycle 16471 created successfully.\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution/addTestsToCycle"))
//                .respond(response().withStatusCode(200).withBody(
//                        "{\"warn\":\"The following just happened: <br/> Tests added: " +
//                                "<strong><a href='http://localhost:1080/jira/browse/HW-123456'>HW-123456</a></strong>" +
//                                " <br/> <p>These Tests could not be added <br/> Invalid: <strong>-</strong> <br/> " +
//                                "Belongs to another project: <strong>-</strong> <br/> Already present in this Test " +
//                                "Cycle: <strong>-</strong><br/> Not a Test: " + "<strong>-" +
//                                "</strong><br/> Permission Denied: <strong>-</strong><br/>\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution")).respond(
//                response().withStatusCode(200).withBody("{\"456789\":{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":16471," +
//                        "\"cycleName\":\"Sample\",\"versionId\":-1,\"versionName\":\"Unscheduled\"," +
//                        "\"projectId\":12345,\"createdBy\":\"max.saperstone\",\"modifiedBy\":\"max.saperstone\"," +
//                        "\"issueId\":234567,\"issueKey\":\"HW-123456\",\"summary\":\"Some Sample Test Case\"," +
//                        "\"issueDescription\":\"Some Generic Description\",\"label\":\"\",\"component\":\"\"," +
//                        "\"projectKey\":\"HW\"}}"));
//        mockServer.when(request().withMethod("PUT").withPath("/jira/rest/zapi/latest/execution/456789/execute"))
//                .respond(response().withStatusCode(200).withBody("{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"1\",\"executedOn\":\"Today 9:54 AM\"," +
//                        "\"executedBy\":\"max.saperstone\",\"executedByDisplay\":\"max saperstone\",\"comment\":\"" +
//                        "\",\"htmlComment\":\"\",\"cycleId\":16471,\"cycleName\":\"Sample\",\"versionId\":-1," +
//                        "\"versionName\":\"Unscheduled\",\"projectId\":12345,\"createdBy\":\"max.saperstone\"," +
//                        "\"modifiedBy\":\"max.saperstone\",\"issueId\":234567,\"issueKey\":\"HW-123456\"," +
//                        "\"summary\":\"Some Sample Test Case\",\"issueDescription\":\"Some Generic " +
//                        "Description\"," + "\"label\":\"\"," + "\"component\":\"\",\"projectKey\":\"HW\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/attachment")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"success\":\"File(s) fileANDROID.html successfully uploaded\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 0;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                return outputFile;
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//
//    @Test
//    @Jira(project = "HW", issue = "HW-123456")
//    public void recordZephyrScreenshotsTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/HW-123456")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"id\":\"16471\",\"responseMessage\":\"Cycle 16471 created successfully.\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution/addTestsToCycle"))
//                .respond(response().withStatusCode(200).withBody(
//                        "{\"warn\":\"The following just happened: <br/> Tests added: " +
//                                "<strong><a href='http://localhost:1080/jira/browse/HW-123456'>HW-123456</a></strong>" +
//                                " <br/> <p>These Tests could not be added <br/> Invalid: <strong>-</strong> <br/> " +
//                                "Belongs to another project: <strong>-</strong> <br/> Already present in this Test " +
//                                "Cycle: <strong>-</strong><br/> Not a Test: " + "<strong>-" +
//                                "</strong><br/> Permission Denied: <strong>-</strong><br/>\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/execution")).respond(
//                response().withStatusCode(200).withBody("{\"456789\":{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":16471," +
//                        "\"cycleName\":\"Sample\",\"versionId\":-1,\"versionName\":\"Unscheduled\"," +
//                        "\"projectId\":12345,\"createdBy\":\"max.saperstone\",\"modifiedBy\":\"max.saperstone\"," +
//                        "\"issueId\":234567,\"issueKey\":\"HW-123456\",\"summary\":\"Some Sample Test Case\"," +
//                        "\"issueDescription\":\"Some Generic Description\",\"label\":\"\",\"component\":\"\"," +
//                        "\"projectKey\":\"HW\"}}"));
//        mockServer.when(request().withMethod("PUT").withPath("/jira/rest/zapi/latest/execution/456789/execute"))
//                .respond(response().withStatusCode(200).withBody("{\"id\":456789,\"orderId\":70022," +
//                        "\"executionStatus\":\"1\",\"executedOn\":\"Today 9:54 AM\"," +
//                        "\"executedBy\":\"max.saperstone\",\"executedByDisplay\":\"max saperstone\",\"comment\":\"" +
//                        "\",\"htmlComment\":\"\",\"cycleId\":16471,\"cycleName\":\"Sample\",\"versionId\":-1," +
//                        "\"versionName\":\"Unscheduled\",\"projectId\":12345,\"createdBy\":\"max.saperstone\"," +
//                        "\"modifiedBy\":\"max.saperstone\",\"issueId\":234567,\"issueKey\":\"HW-123456\"," +
//                        "\"summary\":\"Some Sample Test Case\",\"issueDescription\":\"Some Generic " +
//                        "Description\"," + "\"label\":\"\"," + "\"component\":\"\",\"projectKey\":\"HW\"}"));
//        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/attachment")).respond(
//                response().withStatusCode(200)
//                        .withBody("{\"success\":\"File(s) fileANDROID.html successfully uploaded\"}"));
//        ITestResult test = new ITestResult() {
//            @Override
//            public int getStatus() {
//                return 0;
//            }
//
//            @Override
//            public void setStatus(int status) {
//
//            }
//
//            @Override
//            public ITestNGMethod getMethod() {
//                return new ITestNGMethod() {
//                    @Override
//                    public Class getRealClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public ITestClass getTestClass() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setTestClass(ITestClass cls) {
//
//                    }
//
//                    @Override
//                    public Method getMethod() {
//                        return null;
//                    }
//
//                    @Override
//                    public String getMethodName() {
//                        return null;
//                    }
//
//                    @Override
//                    public Object[] getInstances() {
//                        return new Object[0];
//                    }
//
//                    @Override
//                    public Object getInstance() {
//                        return null;
//                    }
//
//                    @Override
//                    public long[] getInstanceHashCodes() {
//                        return new long[0];
//                    }
//
//                    @Override
//                    public String[] getGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getGroupsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String getMissingGroup() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setMissingGroup(String group) {
//
//                    }
//
//                    @Override
//                    public String[] getBeforeGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getAfterGroups() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public String[] getMethodsDependedUpon() {
//                        return new String[0];
//                    }
//
//                    @Override
//                    public void addMethodDependedUpon(String methodName) {
//
//                    }
//
//                    @Override
//                    public boolean isTest() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterMethodConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterClassConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterSuiteConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterTestConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isBeforeGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAfterGroupsConfiguration() {
//                        return false;
//                    }
//
//                    @Override
//                    public long getTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setTimeOut(long timeOut) {
//
//                    }
//
//                    @Override
//                    public int getInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setInvocationCount(int count) {
//
//                    }
//
//                    @Override
//                    public int getTotalInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public int getSuccessPercentage() {
//                        return 0;
//                    }
//
//                    @Override
//                    public String getId() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setId(String id) {
//
//                    }
//
//                    @Override
//                    public long getDate() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setDate(long date) {
//
//                    }
//
//                    @Override
//                    public boolean canRunFromClass(IClass testClass) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean isAlwaysRun() {
//                        return false;
//                    }
//
//                    @Override
//                    public int getThreadPoolSize() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setThreadPoolSize(int threadPoolSize) {
//
//                    }
//
//                    @Override
//                    public boolean getEnabled() {
//                        return false;
//                    }
//
//                    @Override
//                    public String getDescription() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setDescription(String description) {
//
//                    }
//
//                    @Override
//                    public void incrementCurrentInvocationCount() {
//
//                    }
//
//                    @Override
//                    public int getCurrentInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setParameterInvocationCount(int n) {
//
//                    }
//
//                    @Override
//                    public int getParameterInvocationCount() {
//                        return 0;
//                    }
//
//                    @Override
//                    public ITestNGMethod clone() {
//                        return null;
//                    }
//
//                    @Override
//                    public IRetryAnalyzer getRetryAnalyzer() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setRetryAnalyzer(IRetryAnalyzer retryAnalyzer) {
//
//                    }
//
//                    @Override
//                    public boolean skipFailedInvocations() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setSkipFailedInvocations(boolean skip) {
//
//                    }
//
//                    @Override
//                    public long getInvocationTimeOut() {
//                        return 0;
//                    }
//
//                    @Override
//                    public boolean ignoreMissingDependencies() {
//                        return false;
//                    }
//
//                    @Override
//                    public void setIgnoreMissingDependencies(boolean ignore) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public void setInvocationNumbers(List<Integer> numbers) {
//
//                    }
//
//                    @Override
//                    public void addFailedInvocationNumber(int number) {
//
//                    }
//
//                    @Override
//                    public List<Integer> getFailedInvocationNumbers() {
//                        return null;
//                    }
//
//                    @Override
//                    public int getPriority() {
//                        return 0;
//                    }
//
//                    @Override
//                    public void setPriority(int priority) {
//
//                    }
//
//                    @Override
//                    public XmlTest getXmlTest() {
//                        return null;
//                    }
//
//                    @Override
//                    public ConstructorOrMethod getConstructorOrMethod() {
//                        return new ConstructorOrMethod(method);
//                    }
//
//                    @Override
//                    public Map<String, String> findMethodParameters(XmlTest test) {
//                        return null;
//                    }
//
//                    @Override
//                    public int compareTo(Object o) {
//                        return 0;
//                    }
//                };
//            }
//
//            @Override
//            public Object[] getParameters() {
//                return new Object[0];
//            }
//
//            @Override
//            public void setParameters(Object[] parameters) {
//
//            }
//
//            @Override
//            public IClass getTestClass() {
//                return null;
//            }
//
//            @Override
//            public Throwable getThrowable() {
//                return null;
//            }
//
//            @Override
//            public void setThrowable(Throwable throwable) {
//
//            }
//
//            @Override
//            public long getStartMillis() {
//                return 0;
//            }
//
//            @Override
//            public long getEndMillis() {
//                return 0;
//            }
//
//            @Override
//            public void setEndMillis(long millis) {
//
//            }
//
//            @Override
//            public String getName() {
//                return null;
//            }
//
//            @Override
//            public boolean isSuccess() {
//                return false;
//            }
//
//            @Override
//            public String getHost() {
//                return null;
//            }
//
//            @Override
//            public Object getInstance() {
//                return null;
//            }
//
//            @Override
//            public String getTestName() {
//                return null;
//            }
//
//            @Override
//            public String getInstanceName() {
//                return null;
//            }
//
//            @Override
//            public ITestContext getTestContext() {
//                return null;
//            }
//
//            @Override
//            public int compareTo(ITestResult o) {
//                return 0;
//            }
//
//            @Override
//            public Object getAttribute(String name) {
//                OutputFile outputFile =
//                        new OutputFile("directory", "file", new Browser(Browser.BrowserName.ANDROID), null, null, null,
//                                null, null, null);
//                try {
//                    outputFile
//                            .setApp(new App(new Browser(BrowserName.HTMLUNIT), new DesiredCapabilities(), outputFile));
//                } catch (Exception e) {
//                }
//                outputFile.captureEntirePageScreenshot();
//                return outputFile;
//            }
//
//            @Override
//            public void setAttribute(String name, Object value) {
//
//            }
//
//            @Override
//            public Set<String> getAttributeNames() {
//                return new HashSet<>(Arrays.asList("Output"));
//            }
//
//            @Override
//            public Object removeAttribute(String name) {
//                return null;
//            }
//        };
//        recordZephyr(test);
//    }
//}