package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ServicesAssertMatchesTest {

    private Reporter reporter;
    private File directory;
    private File file;

    @BeforeMethod(alwaysRun = true)
    public void createFile() throws InvalidBrowserException, InvalidProxyException {
        reporter =
                new Reporter("directory", "file", new Capabilities(new Browser("None")), null, null, null, null, null, null);
        directory = new File("directory");
        file = new File("directory", "file.html");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteFile() {
        file.delete();
        directory.delete();
    }

    @Test
    public void confirmEqualsCodePassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertMatches().code("(\\d)");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCodeFailTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertMatches().code("([0-3])");
    }

    @Test
    public void confirmEqualsMessagePassTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.assertMatches().message("([\\w\\s]*)");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsMessageFailTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "SOME MESSAGE");
        response.assertMatches().message("([a-z]*)");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsMessageNullTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.assertMatches().message("");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmEqualsCrumbsNothing() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.assertMatches().nestedValue(null, "name");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsNoJsonObject() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.assertMatches().nestedValue(new ArrayList<>(), "name");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmEqualsCrumbsNoCrumbs() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.assertMatches().nestedValue(null, "name");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsCrumbsNotExist() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        List<String> list = new ArrayList<>();
        list.add("name");
        response.assertMatches().nestedValue(list, "john");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsCrumbsNotJsonObject() {
        JsonArray array = new JsonArray();
        array.add("john");
        array.add("jon");
        JsonObject json = new JsonObject();
        json.add("first", array);
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        list.add("last");
        response.assertMatches().nestedValue(list, "john");
    }

    @Test
    public void confirmEqualsCrumbsMatch() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        response.assertMatches().nestedValue(list, "john");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsNoMatch() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        response.assertMatches().nestedValue(list, "janice");
    }
}