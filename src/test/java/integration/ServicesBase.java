package integration;

import com.coveros.selenified.Browser.BrowserUse;
import com.coveros.selenified.Selenified;
import com.google.gson.JsonObject;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Parameter;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class ServicesBase extends Selenified {

    private ClientAndServer mockServer;
    final JsonObject json1 = new JsonObject();
    final JsonObject json2 = new JsonObject();
    final JsonObject json3 = new JsonObject();
    final JsonObject json4 = new JsonObject();
    final JsonObject simJson4 = new JsonObject();
    private int mockPort = 1080;

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setAppURL(this, test, "http://localhost:" + mockPort + "/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a dynamic check
        setVersion(this, test, "3.3.1");

        //test data
        json1.addProperty("userId", 1);
        json1.addProperty("id", 1);
        json1.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json1.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        json2.addProperty("userId", 1);
        json2.addProperty("id", 2);
        json2.addProperty("title", "qui est esse");
        json2.addProperty("body",
                "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla");
        json3.addProperty("userId", 1);
        json3.addProperty("id", 3);
        json3.addProperty("title", "ea molestias quasi exercitationem repellat qui ipsa sit aut");
        json3.addProperty("body",
                "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut");
        json4.addProperty("userId", 1);
        json4.addProperty("id", 4);
        json4.addProperty("title", "eum et est occaecati");
        json4.addProperty("body",
                "ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit");
        simJson4.addProperty("id", 4);
    }

    @BeforeSuite(alwaysRun = true)
    public void startMockServer() throws IOException {
        if (System.getProperty("mockPort") != null) {
            mockPort += Integer.valueOf(System.getProperty("mockPort"));
        }
        mockServer = startClientAndServer(mockPort);
        mockServer.when(request().withPath("/null/"))
                .respond(response().withStatusCode(404).withBody("We encountered an error, no page was found"));
        mockServer.when(request().withMethod("GET").withPath("/sample/")).respond(response().withBody("{}"));
        mockServer.when(request().withMethod("GET").withPath("/posts/")
                .withQueryStringParameter(new Parameter("id", "4"))).respond(response().withBody(
                "{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}"));
        mockServer.when(request().withMethod("GET").withPath("/posts/4")).respond(response().withBody(
                "{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}"));
        mockServer.when(request().withMethod("GET").withPath("/posts/")).respond(response().withBody(
                "[{\"userId\":1,\"id\":1,\"title\":\"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\":\"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"},{\"userId\":1,\"id\":2,\"title\":\"qui est esse\",\"body\":\"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"},{\"userId\":1,\"id\":3,\"title\":\"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\"body\":\"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"},{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}]"));
        mockServer.when(request().withMethod("PUT").withPath("/post/4")).respond(response().withStatusCode(200));
        mockServer.when(request().withMethod("PATCH").withPath("/post/4")).respond(response().withStatusCode(200));
        mockServer.when(request().withPath("/post/4")).respond(response().withBody("{\"id\":4}"));
        mockServer.when(request().withMethod("GET").withPath("/users/")).respond(response().withBody(
                "{\"users\":{\"userId\":1,\"userId\":2,\"userId\":3,\"userId\":4}}"));
        mockServer.when(request().withMethod("GET").withPath("/ids/")).respond(response().withBody(
                "{\"ids\":[{\"userId\":1,\"id\":1,\"title\":\"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\":\"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"},{\"userId\":1,\"id\":2,\"title\":\"qui est esse\",\"body\":\"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"},{\"userId\":1,\"id\":3,\"title\":\"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\"body\":\"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"},{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}]}"));
        mockServer.when(request().withMethod("GET").withPath("/ids/1")).respond(response().withBody(
                "{\"ids\":[{\"userId\":1,\"id\":1,\"title\":\"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\":\"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"}]}"));
        mockServer.when(request().withMethod("GET").withPath("/ids/5")).respond(response().withBody(
                "{\"ids\":[]}"));
        mockServer.when(request().withMethod("GET").withPath("/empty/")).respond(response().withBody(
                "[]"));
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) throws IOException {
        super.startTest(dataProvider, method, test, result, BrowserUse.FALSE);
    }

    @AfterSuite(alwaysRun = true)
    public void stopMockServer() {
        mockServer.stop();
    }
}
