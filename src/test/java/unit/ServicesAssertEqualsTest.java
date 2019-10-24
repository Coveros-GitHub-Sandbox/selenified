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

public class ServicesAssertEqualsTest {

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

    @AfterMethod
    public void deleteFile() {
        file.delete();
        directory.delete();
    }

    @Test
    public void confirmEqualsCodePassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertEquals().code(5);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCodeFailTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertEquals().code(6);
    }

    @Test
    public void confirmEqualsObjectPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertEquals().objectData(json);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsObjectFailTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertEquals().objectData(new JsonObject());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsObjectNullTest() {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.assertEquals().objectData(new JsonObject());
    }

    @Test
    public void confirmEqualsArrayPassTest() {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.assertEquals().arrayData(json);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsArrayFailTest() {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.assertEquals().arrayData(new JsonArray());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsArrayNullTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertEquals().arrayData(new JsonArray());
    }

    @Test
    public void confirmEqualsMessagePassTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.assertEquals().message("Some message");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsMessageFailTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "SOME MESSAGE");
        response.assertEquals().message("Some message");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsMessageNullTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.assertEquals().message("");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmEqualsCrumbsNothing() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.assertEquals().nestedValue(null, "name");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsNoJsonObject() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.assertEquals().nestedValue(new ArrayList<>(), "name");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmEqualsCrumbsNoCrumbs() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.assertEquals().nestedValue(null, "name");
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
        response.assertEquals().nestedValue(list, "john");
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
        response.assertEquals().nestedValue(list, "john");
    }

    @Test
    public void confirmEqualsCrumbsMatch() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        response.assertEquals().nestedValue(list, "john");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsNoMatch() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        response.assertEquals().nestedValue(list, "janice");
    }

    @Test
    public void confirmEqualsArraySizeNotArray() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.assertEquals().arraySize(-1);
    }

    @Test
    public void confirmEqualsArraySizeEmptyMatch() {
        Response response = new Response(reporter, null, 5, null, new JsonArray(), null);
        response.assertEquals().arraySize(0);
    }

    @Test
    public void confirmEqualsArraySizeMatch() {
        JsonArray array = new JsonArray();
        array.add("5");
        Response response = new Response(reporter, null, 5, null, array, null);
        response.assertEquals().arraySize(1);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsArraySizeMisMatch() {
        JsonArray array = new JsonArray();
        array.add("5");
        Response response = new Response(reporter, null, 5, null, array, null);
        response.assertEquals().arraySize(2);
    }

    @Test
    public void confirmEqualsCrumbsNotObject() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.assertEquals().nestedArraySize(new ArrayList<>(), -1);
    }

    @Test
    public void confirmEqualsCrumbsEmptyCrumbs() {
        JsonArray array = new JsonArray();
        array.add("5");
        Response response = new Response(reporter, null, 5, null, array, null);
        response.assertEquals().nestedArraySize(new ArrayList<>(), -1);
    }

    @Test
    public void confirmEqualsCrumbsOneCrumb() {
        JsonArray array = new JsonArray();
        JsonObject john = new JsonObject();
        john.addProperty("first", "john");
        john.addProperty("last", "doe");
        array.add(john);
        JsonObject jon = new JsonObject();
        jon.addProperty("first", "jon");
        jon.addProperty("last", "doe");
        array.add(jon);
        JsonObject json = new JsonObject();
        json.add("name", array);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertEquals().nestedArraySize(crumbs, 2);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsTwoCrumbs() {
        JsonArray array = new JsonArray();
        JsonObject john = new JsonObject();
        john.addProperty("first", "john");
        john.addProperty("last", "doe");
        array.add(john);
        JsonObject jon = new JsonObject();
        jon.addProperty("first", "jon");
        jon.addProperty("last", "doe");
        array.add(jon);
        JsonObject json = new JsonObject();
        json.add("name", array);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        crumbs.add("first");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertEquals().nestedArraySize(crumbs, 2);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsArraySizeMisMatch() {
        JsonArray array = new JsonArray();
        JsonObject john = new JsonObject();
        john.addProperty("first", "john");
        john.addProperty("last", "doe");
        array.add(john);
        JsonObject jon = new JsonObject();
        jon.addProperty("first", "jon");
        jon.addProperty("last", "doe");
        array.add(jon);
        JsonObject json = new JsonObject();
        json.add("name", array);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        crumbs.add("first");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertEquals().nestedArraySize(crumbs, 3);
    }
}