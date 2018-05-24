//package unit;
//
//import com.coveros.selenified.utilities.Jira;
//import org.mockserver.integration.ClientAndServer;
//import org.testng.Assert;
//import org.testng.annotations.*;
//
//import java.lang.reflect.Method;
//
//import static org.mockserver.integration.ClientAndServer.startClientAndServer;
//import static org.mockserver.model.HttpRequest.request;
//import static org.mockserver.model.HttpResponse.response;
//
//
//public class JiraMockTest {
//
//    private ClientAndServer mockServer;
//    private String jiraLink = null;
//
//    @BeforeClass
//    public void saveJiraLink() {
//        if (System.getProperty("jira.link") != null) {
//            jiraLink = System.getProperty("jira.link");
//        }
//        System.setProperty("jira.link", "http://localhost:1080/jira");
//    }
//
//    @AfterClass
//    public void resetJiraLink() {
//        System.clearProperty("jira.link");
//        if (jiraLink != null) {
//            System.setProperty("jira.link", jiraLink);
//        }
//    }
//
//    @BeforeMethod
//    public void startMockServer() {
//        mockServer = startClientAndServer(1080);
//    }
//
//    @AfterMethod
//    public void stopMockServer() {
//        mockServer.stop();
//    }
//
//    @Test
//    @Jira(project = "/")
//    public void getProjectIdIllegalProjectTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project//")).respond(
//                response().withStatusCode(404).withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" " +
//                        "standalone=\"yes\"?><status><status-code>404</status-code><message>null for uri: " +
//                        "http://localhost:1080/jira/rest/api/2/project//</message></status>"));
//        com.coveros.selenified.utilities.jira.Jira jira = new com.coveros.selenified.utilities.jira.Jira(method);
//        Assert.assertEquals(jira.getProjectId(), 0);
//    }
//
//    @Test
//    @Jira(project = "FB")
//    public void getProjectIdBadProjectTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/FB")).respond(
//                response().withStatusCode(404)
//                        .withBody("{\"errorMessages\":[\"No project could be found with key 'FB'.\"],\"errors\":{}}"));
//        com.coveros.selenified.utilities.jira.Jira jira = new com.coveros.selenified.utilities.jira.Jira(method);
//        Assert.assertEquals(jira.getProjectId(), 0);
//    }
//
//    @Test
//    @Jira(project = "HW")
//    public void getProjectIdTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/project/HW")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\":\"description,lead,url,projectKeys\", \"self\":\"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\":\"12345\", \"key\":\"HW\", \"description\":\"some description\", \"lead\":{ \"self\":\"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"key\":\"saperstone\", \"name\":\"max.saperstone\", \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/useravatar?ownerId=saperstone&avatarId=11704\", \"24x24\":\"http://localhost:1080/jira/secure/useravatar?size=small&ownerId=saperstone&avatarId=11704\", \"16x16\":\"http://localhost:1080/jira/secure/useravatar?size=xsmall&ownerId=saperstone&avatarId=11704\", \"32x32\":\"http://localhost:1080/jira/secure/useravatar?size=medium&ownerId=saperstone&avatarId=11704\" }, \"displayName\":\"Max Saperstone\", \"active\":true }, \"components\":[ ], \"issueTypes\":[ { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/1\", \"id\":\"1\", \"description\":\"A problem which impairs or prevents the functions of the product.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10603&avatarType=issuetype\", \"name\":\"Bug\", \"subtask\":false, \"avatarId\":10603 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/3\", \"id\":\"3\", \"description\":\"A task that needs to be done.\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10618&avatarType=issuetype\", \"name\":\"Task\", \"subtask\":false, \"avatarId\":10618 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/5\", \"id\":\"5\", \"description\":\"The sub-task of the issue\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10616&avatarType=issuetype\", \"name\":\"Sub-task\", \"subtask\":true, \"avatarId\":10616 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\":\"10000\", \"description\":\"gh.issue.epic.desc\", \"iconUrl\":\"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\":\"Epic\", \"subtask\":false }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/10001\", \"id\":\"10001\", \"description\":\"gh.issue.story.desc\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10615&avatarType=issuetype\", \"name\":\"Story\", \"subtask\":false, \"avatarId\":10615 }, { \"self\":\"http://localhost:1080/jira/rest/api/2/issuetype/11002\", \"id\":\"11002\", \"description\":\"\", \"iconUrl\":\"http://localhost:1080/jira/secure/viewavatar?size=xsmall&avatarId=10600&avatarType=issuetype\", \"name\":\"Issue\", \"subtask\":false, \"avatarId\":10600 } ], \"assigneeType\":\"UNASSIGNED\", \"versions\":[ ], \"name\":\"Hello World\", \"roles\":{ }, \"avatarUrls\":{ \"48x48\":\"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\":\"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\":\"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\":\"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" }, \"projectTypeKey\":\"software\"}"));
//        com.coveros.selenified.utilities.jira.Jira jira = new com.coveros.selenified.utilities.jira.Jira(method);
//        Assert.assertEquals(jira.getProjectId(), 12345);
//    }
//
//    @Test
//    @Jira(issue = "/")
//    public void getIssueIdIllegalIssueTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue//")).respond(
//                response().withStatusCode(404).withBody("<?xml version=\"1.0\" encoding=\"UTF-8\" " +
//                        "standalone=\"yes\"?><status><status-code>404</status-code><message>null for uri: " +
//                        "http://localhost:1080/jira/rest/api/2/issue//</message></status>"));
//        com.coveros.selenified.utilities.jira.Jira jira = new com.coveros.selenified.utilities.jira.Jira(method);
//        Assert.assertEquals(jira.getIssueId(), 0);
//    }
//
//    @Test
//    @Jira(issue = "FB-987654")
//    public void getIssueIdBadIssueTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/FB-987654")).respond(
//                response().withStatusCode(404)
//                        .withBody("{\"errorMessages\":[\"Issue Does Not Exist\"],\"errors\":{}}"));
//        com.coveros.selenified.utilities.jira.Jira jira = new com.coveros.selenified.utilities.jira.Jira(method);
//        Assert.assertEquals(jira.getIssueId(), 0);
//    }
//
//    @Test
//    @Jira(issue = "HW-123456")
//    public void getIssueIdTest(Method method) {
//        mockServer.when(request().withMethod("GET").withPath("/jira/rest/api/2/issue/HW-123456")).respond(
//                response().withStatusCode(200).withBody(
//                        "{ \"expand\": \"renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations\", \"id\": \"234567\", \"self\": \"http://localhost:1080/jira/rest/api/2/issue/234567\", \"key\": \"HW-12345\", \"fields\": { \"issuetype\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issuetype/10000\", \"id\": \"10000\", \"description\": \"gh.issue.epic.desc\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/ico_epic.png\", \"name\": \"Epic\", \"subtask\": false }, \"timespent\": null, \"project\": { \"self\": \"http://localhost:1080/jira/rest/api/2/project/12345\", \"id\": \"12345\", \"key\": \"HW\", \"name\": \"Hello World\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/projectavatar?avatarId=11203\", \"24x24\": \"http://localhost:1080/jira/secure/projectavatar?size=small&avatarId=11203\", \"16x16\": \"http://localhost:1080/jira/secure/projectavatar?size=xsmall&avatarId=11203\", \"32x32\": \"http://localhost:1080/jira/secure/projectavatar?size=medium&avatarId=11203\" } }, \"fixVersions\": [], \"aggregatetimespent\": null, \"resolution\": null, \"customfield_10500\": \"0|108699:zzv\", \"customfield_10700\": null, \"customfield_10701\": [], \"customfield_10902\": null, \"resolutiondate\": null, \"customfield_10905\": null, \"workratio\": -1, \"customfield_10906\": null, \"lastViewed\": null, \"watches\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/watchers\", \"watchCount\": 1, \"isWatching\": true }, \"created\": \"2017-11-07T22:08:18.000+0530\", \"customfield_12001\": null, \"priority\": { \"self\": \"http://localhost:1080/jira/rest/api/2/priority/10006\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/priorities/major.svg\", \"name\": \"P1\", \"id\": \"10006\" }, \"customfield_10100\": null, \"customfield_10101\": null, \"labels\": [ ], \"timeestimate\": null, \"aggregatetimeoriginalestimate\": null, \"versions\": [], \"issuelinks\": [], \"assignee\": null, \"updated\": \"2018-04-07T00:23:14.000+0530\", \"status\": { \"self\": \"http://localhost:1080/jira/rest/api/2/status/10203\", \"description\": \"\", \"iconUrl\": \"http://localhost:1080/jira/images/icons/subtask.gif\", \"name\": \"Backlog\", \"id\": \"10203\", \"statusCategory\": { \"self\": \"http://localhost:1080/jira/rest/api/2/statuscategory/2\", \"id\": 2, \"key\": \"new\", \"colorName\": \"blue-gray\", \"name\": \"To Do\" } }, \"components\": [], \"timeoriginalestimate\": null, \"description\": null, \"timetracking\": {}, \"customfield_10005\": \"ghx-label-9\", \"customfield_10006\": null, \"customfield_10007\": null, \"customfield_10800\": null, \"attachment\": [], \"customfield_10009\": null, \"aggregatetimeestimate\": null, \"summary\": \"Functional tests for forms\", \"creator\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"subtasks\": [], \"reporter\": { \"self\": \"http://localhost:1080/jira/rest/api/2/user?username=max.saperstone\", \"name\": \"max.saperstone\", \"key\": \"saperstone\", \"emailAddress\": \"max.saperstone@coveros.com\", \"avatarUrls\": { \"48x48\": \"http://localhost:1080/jira/secure/useravatar?avatarId=11222\", \"24x24\": \"http://localhost:1080/jira/secure/useravatar?size=small&avatarId=11222\", \"16x16\": \"http://localhost:1080/jira/secure/useravatar?size=xsmall&avatarId=11222\", \"32x32\": \"http://localhost:1080/jira/secure/useravatar?size=medium&avatarId=11222\" }, \"displayName\": \"Max Saperstone\", \"active\": true, \"timeZone\": \"America/New_York\" }, \"customfield_12101\": null, \"customfield_10000\": null, \"customfield_12100\": \"com.atlassian.servicedesk.plugins.approvals.internal.customfield.ApprovalsCFValue@542cd3d5\", \"aggregateprogress\": { \"progress\": 0, \"total\": 0 }, \"customfield_10001\": null, \"customfield_12103\": null, \"customfield_10002\": \"9223372036854775807\", \"customfield_12102\": null, \"customfield_10003\": { \"self\": \"http://localhost:1080/jira/rest/api/2/customFieldOption/10000\", \"value\": \"To Do\", \"id\": \"10000\" }, \"customfield_10004\": \"Basics\", \"environment\": null, \"customfield_11801\": null, \"customfield_11800\": null, \"customfield_11802\": null, \"duedate\": null, \"progress\": { \"progress\": 0, \"total\": 0 }, \"comment\": { \"comments\": [], \"maxResults\": 0, \"total\": 0, \"startAt\": 0 }, \"votes\": { \"self\": \"http://localhost:1080/jira/rest/api/2/issue/HW-12345/votes\", \"votes\": 0, \"hasVoted\": false }, \"worklog\": { \"startAt\": 0, \"maxResults\": 20, \"total\": 0, \"worklogs\": [] } }}"));
//        com.coveros.selenified.utilities.jira.Jira jira = new com.coveros.selenified.utilities.jira.Jira(method);
//        Assert.assertEquals(jira.getIssueId(), 234567);
//    }
//}