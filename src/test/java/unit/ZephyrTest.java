package unit;

import com.coveros.selenified.utilities.jira.Zephyr;
import org.mockserver.integration.ClientAndServer;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class ZephyrTest {

    private ClientAndServer mockServer;
    private String jiraLink = null;

    @BeforeClass
    public void saveJiraLink() {
        if (System.getProperty("jira.link") != null) {
            jiraLink = System.getProperty("jira.link");
        }
        System.setProperty("jira.link", "http://localhost:1080/jira");
    }

    @AfterClass
    public void resetJiraLink() {
        System.clearProperty("jira.link");
        if (jiraLink != null) {
            System.setProperty("jira.link", jiraLink);
        }
    }

    @BeforeMethod
    public void startMockServer() {
        mockServer = startClientAndServer(1080);
    }

    @AfterMethod
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void getCycleBadTest(Method method) {
        Zephyr zephyr = new Zephyr(method);
    }

    @Test
    public void createCycleBadTest(Method method) {
        mockServer.when(request().withMethod("POST").withPath("/jira/rest/zapi/latest/cycle")).respond(
                response().withStatusCode(406).withBody(
                        "{\"ProjectBrowsePermissions\":\"Null or invalid projectId. Please provide a valid projectId.\"}"));
        Zephyr zephyr = new Zephyr(method);
        zephyr.createCycle();
        Assert.assertEquals(zephyr.getCycleId(), 0);
    }
}