package unit;

import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.utilities.jira.Jira;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class JiraTest {

    private String jiraLink = null;
    private String jiraUser = null;
    private String jiraPass = null;

    @BeforeClass
    public void storeJiraProperties() {
        if (System.getProperty("jira.link") != null) {
            jiraLink = System.getProperty("jira.link");
            System.clearProperty("jira.link");
        }
        if (System.getProperty("jira.username") != null) {
            jiraUser = System.getProperty("jira.username");
            System.clearProperty("jira.username");
        }
        if (System.getProperty("jira.password") != null) {
            jiraPass = System.getProperty("jira.password");
            System.clearProperty("jira.password");
        }
    }

    @AfterClass
    public void restoreJiraProperties() {
        if (jiraLink != null) {
            System.setProperty("jira.link", jiraLink);
        }
        if (jiraUser != null) {
            System.setProperty("jira.username", jiraUser);
        }
        if (jiraPass != null) {
            System.setProperty("jira.password", jiraPass);
        }
    }

    @AfterMethod
    public void clearJiraProperties() {
        System.clearProperty("jira.link");
        System.clearProperty("jira.username");
        System.clearProperty("jira.password");
    }

    @Test
    public void uploadToJiraDefaultTest(Method method) {
        Jira jira = new Jira(method);
        Assert.assertFalse(Jira.uploadToJira());
    }

    @Test
    public void uploadToJiraEmptyTest(Method method) {
        System.setProperty("jira.link", "");
        Jira jira = new Jira(method);
        Assert.assertFalse(Jira.uploadToJira());
    }

    @Test
    public void uploadToJiraTrueTest(Method method) {
        System.setProperty("jira.link", "true");
        Jira jira = new Jira(method);
        Assert.assertTrue(Jira.uploadToJira());
    }

    @Test
    public void uploadToJiraAlternateTest(Method method) {
        System.setProperty("jira.link", "hello world");
        Jira jira = new Jira(method);
        Assert.assertTrue(Jira.uploadToJira());
    }

    @Test
    public void getHttpDefaultTest(Method method) {
        Jira jira = new Jira(method);
        Assert.assertNull(jira.getHTTP());
    }

    @Test
    public void getJiraHttpDefaultTest(Method method) {
        Jira jira = new Jira(method);
        Assert.assertNull(jira.getJiraHttp());
    }

    @Test
    public void getHttpMyLinkTest(Method method) {
        System.setProperty("jira.link", "myLink");
        Jira jira = new Jira(method);
        HTTP http = jira.getHTTP();
        Assert.assertEquals(http.getServiceBaseUrl(), "myLink");
        Assert.assertEquals(http.getUser(), "");
        Assert.assertEquals(http.getPass(), "");
    }

    @Test
    public void getJiraHttpMyLinkTest(Method method) {
        System.setProperty("jira.link", "myLink");
        Jira jira = new Jira(method);
        HTTP http = jira.getJiraHttp();
        Assert.assertEquals(http.getServiceBaseUrl(), "myLink");
        Assert.assertEquals(http.getUser(), "");
        Assert.assertEquals(http.getPass(), "");
    }

    @Test
    public void getJiraHttpUserTest(Method method) {
        System.setProperty("jira.link", "myLink");
        System.setProperty("jira.username", "username");
        Jira jira = new Jira(method);
        HTTP http = jira.getHTTP();
        Assert.assertEquals(http.getServiceBaseUrl(), "myLink");
        Assert.assertEquals(http.getUser(), "username");
        Assert.assertEquals(http.getPass(), "");
    }

    @Test
    public void getJiraHttpPassTest(Method method) {
        System.setProperty("jira.link", "myLink");
        System.setProperty("jira.password", "password");
        Jira jira = new Jira(method);
        HTTP http = jira.getHTTP();
        Assert.assertEquals(http.getServiceBaseUrl(), "myLink");
        Assert.assertEquals(http.getUser(), "");
        Assert.assertEquals(http.getPass(), "password");
    }

    @Test
    public void getProjectIdDefaultTest(Method method) {
        Jira jira = new Jira(method);
        Assert.assertEquals(jira.getProjectId(), 0);
    }

    @Test
    public void getIssueIdDefaultTest(Method method) {
        Jira jira = new Jira(method);
        Assert.assertEquals(jira.getIssueId(), 0);
    }
}