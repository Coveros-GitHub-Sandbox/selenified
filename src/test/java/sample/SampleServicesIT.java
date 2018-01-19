package sample;

import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Request;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SampleServicesIT extends Selenified{


    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://maps.googleapis.com/maps/api/geocode/json");
        // set the author of the tests here
        setAuthor(this, test, "Hammid Funsho\n<br/>hammid.funsho@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }


    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
    }


    @Test(groups = { "sampleServices" }, description = "A sample services test to verify the response code")
    public void sampleServicesAddressTest() {
        // the parameters to pass to google
        Map<String, String> params = new HashMap<>();
        params.put("address", "123+main+street");
        // use this object to make web service calls
        Call call = this.calls.get();
        // make a get call, and confirm we get a 200 response code
        Request request = new Request(params);
        call.get("", request).assertEquals(200);
        // verify no issues
        finish();
    }


    @Test(groups = { "sampleServices" }, description = "A sample services test to verify the response code")
    public void sampleServicesZipCountryTest() {
        // the parameters to pass to google
        Map<String, String> params = new HashMap<>();
        params.put("components=postal_code", "78748|");
        params.put("country", "US");
        // use this object to make web service calls
        Call call = this.calls.get();
        // make a get call, and confirm we get a 200 response code
        //Request request = new Request(params);
        call.get("", new Request(params)).assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = { "sampleServices" }, description = "A sample services test to verify the response code")
    public void sampleServicesCityTest() {
        Map<String, String> params = new HashMap<>();
        params.put("address", "chicago");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("", new Request(params)).assertEquals(200);
        // verify no issues
        finish();
    }
}
