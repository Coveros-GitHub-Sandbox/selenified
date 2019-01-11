# Selenified Testing Framework
The Selenified Test Framework provides mechanisms for simply testing applications at multiple tiers 
while easily integrating into DevOps build environments. Selenified provides traceable reporting for 
both web and API testing, wraps and extends Selenium calls to more appropriately handle testing errors, 
and supports testing over multiple browsers locally, or in the cloud (Selenium Grid or SauceLabs) in 
parallel. It can be a great starting point for building or improving test automation in your organization.

## Test Examples
### Getting Started
One of Selenified's goals is to be a framework that is easy to drop in to an existing project. You can 
easily have Selenified running within minutes using only a Maven POM, Java test class and a TestNG XML Suite.

### Adding the Selenified Dependency
It’s very simple to get started using Selenified. Just add selenified.jar to your project, and you can start 
writing your test cases. If you’re using a build tool, simply add the jar as a dependency.

#### Maven
Update your pom.xml file to include
```xml
    <dependency>
    <groupId>com.coveros</groupId>
    <artifactId>selenified</artifactId>
    <version>3.0.3</version>
    <scope>test</scope>
    </dependency>
```

#### Ant
Update your ivy.xml file to include
```xml
    <ivy-module>
        <dependencies>
            <dependency org="com.coveros" name="selenified" rev="3.0.3"/>
        </dependencies>
    </ivy-module>
```

#### Gradle
Update your build.gradle file to include
```groovy
    dependencies {
        testCompile 'com.coveros:selenified:3.0.3'
    }
```

Have a look at this example test class to get an idea of what you'll actually be adding into your codebase.

```java
    public class SampleTests extends Selenified {

        @DataProvider(name = "google search terms", parallel = true)
        public Object[][] DataSetOptions() {
            return new Object[][] { new Object[] { "python" }, 
                new Object[] { "perl" }, new Object[] { "bash" }, };
        }

        @Test(groups = { "sample" }, description = "A sample selenium test to check a title")
        public void sampleTest() {
            // use this object to manipulate the app
            App app = this.apps.get();
            // verify the correct page title
            app.azzert().titleEquals("Google");
            // verify no issues
            finish();
        }

         @Test(dataProvider = "google search terms", groups = { "sample"},
                         description = "A sample selenium test using a data provider to perform a google search")
         public void sampleTestWDataProvider(String searchTerm) {
             // use this object to manipulate the app
             App app = this.apps.get();
             // find the search box element and create the object
             Element searchBox = app.newElement(Locator.NAME, "q");
             //perform the search and submit
             searchBox.type(searchTerm);
             searchBox.submit();
             //wait for the page to return the results
             app.newElement(Locator.ID, "resultStats").waitFor().present();
             // verify the correct page title
             app.azzert().titleEquals(searchTerm + " - Google Search");
             // verify no issues
             finish();
         }

         @Test(groups = { "sampleServices" }, description = "A sample web services test to verify the response code")
             public void sampleServicesCityTest() {
             Map<String, String> params = new HashMap<>();
             params.put("address", "chicago");
             // use this object to verify the app looks as expected
             Call call = this.calls.get();
             // retrieve the zip code and verify the return code
             call.get("", new Request(params)).assertEquals(200);
             // verify no issues
             finish();
         }

    }
```

In the first test, sampleTest, the App class is used to check the title of the page. 
In the next test, sampleTestWDataProvider, the App class is used to generate elements we want
to interact with; type a search term, submit the search term and the wait for the page to load. 
We then use that same element in order to verify the title contains the same search term. The 
'google search terms' dataProvider provides a search term to the test. In the third test, a call 
is made to the google maps api to retrieve the GPS coordinates of the city of Chicago, then verify
the response code.
For more information on the App and Call class plus all the other classes used by Selenified, check out the
documentation [here](https://coveros.github.io/selenified).

## Writing Tests
### Create A New Test Suite
Tests should be organized into suites. To create a new test suite, simply create a folder, 
or nested set of folders within the src directory. Within each folder, then create one or 
more Java classes. Name the class something descriptive following the test suites purposes.

### Structuring the Test Suite
Have each class extend the Selenified class which is contained within the 
selenified.jar. Each should contain a method setting up some details to 
be used in each test, Only the testSite is required, if the URL is passed in from
the commandline, even this can be excluded. Additional optional parameters are 
the author of the tests, and the version of tests or software under test. 
See below for an example:
```java
    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://172.31.2.65/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "3.0.4");
    }
```

If the suite of tests do not require a selenium instance to run the test (e.g. API test), the suite
should also contain a method to override the default startTest method. This needs to be annotated 
with @BeforeMethod. This method will just call the super method of startTest and initialize everything needed. 
Since we do not need a Selenium driver, the last value passed into startTest should be set to 'FALSE'. 
See below for an example:

```java
    @BeforeMethod (alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
    }
```

Sometimes you may end up running some tests that require some browser configurations or changes to be made before
the page is initially loaded. In order to launch the browser, but not load (and wait for the successful load) of 
a page, you also will want to override the default startTest method. In this case, the last value passed into 
startTest should be set to 'OPEN'.
See below for an example:

```java
    @BeforeMethod (alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.OPEN);
    }
```

While proxy and remote running capability configuration settings are handled by the framework (see below
sections on parameters), sometimes additional custom capabilities need to be setup for the test execution.
If needed additional capabilites can be set by overriding the default beforeSuite method. Add whatever desired
capabilities are required to the `extraCapabilities` object, and then call the parent method. An example is
shown below

```java
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws InvalidBrowserException {
        extraCapabilities = new DesiredCapabilities();
        extraCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        extraCapabilities.setCapability("ignoreProtectedModeSettings", true);
        super.beforeSuite();
    }
```

With this in mind, it may be helpful to structure tests based on functionality and attributes under test. 
If a suite has multiple tests that will be running using similar tests, consider setting up some suite 
specific methods to run these tests. This will reduce the amount of code being written, and make updates 
quicker and cleaner.

### Write the Tests
Adding a new test, is simply adding a new method to an existing test suite (class). Each method should be 
named something descriptive following the tests functionality. Each method should have a public modifier and 
have a void return type. Each method should have an @Test annotation before it. Putting in the below information 
in each annotation will ensure that the data is available in the custom test reporting.

 * a group - based on the test suite and the extended test suite
 * a description - something useful/descriptive to be displayed on the results and test detailed results
 * a dependency (optional) - based on either another group or test, or multiples
 * a data provider (optional) - if this test takes multiple inputs, allowing the test to run multiple times

If a class has multiple tests that are similar, but simply require one or two different inputs, a dataProvider 
should be used. Instead of writing multiple tests, one test can be written instead. This will reduce the amount 
of code being written, and make updates quicker and cleaner. A full example test can be seen in the included 
SampleIT.java class in the framework.

If you are defining your dataProvider parameters, be sure to include the variables defined in your declaring method.

Tests should always start in a known state, be sure you use your @BeforeMethod annotation to set this up if needed. 

#### Simple Test Cases
The method body should start with one line, dependent on the type of test you plan to perform.

##### Browser
If you plan on running a browser based test, the first thing you should do is retrieve your app class.
```java
    App app = this.apps.get();
```
This object will give you access to perform any required actions on the application, such as accepting an
alert, asserting an alert is present, or reloading the page.
```java
    app.acceptAlert();
    app.is().alertPresent();
    app.reloadPage();
```

If you want to interact with particular elements on a page, you can create them from this app object.
```java
    Element element = app.newElement(Locator.XPATH, "//form/input");
```
Similar to the `app` object, any actions you want to perform on the element, can be done on this object, such
as clicking on it, entering text, or selecting a value that it is available.
```java
    element.click();
    element.type("hello world");
    element.select(1);
    element.select("value");
```

Sub functionality exists for both pages and elements, to make writing tests simpler. Objects exist for getting,
checking, and waiting for things on the page. Additionally, objects exist for getting, checking, and waiting.
```java
    app.is().alertPresent();
    app.get().alert();
    app.waitFor().alert();
    
    element.is().table();
    element.get().numOfSelectOptions();
    element.waitFor().displayed();
```

There are also custom assertions associated with both the page and element objects. These asserts are custom
to the framework, and in addition to providing easy object oriented capabilities, they take screenshots with
each verification to provide additional traceability, and assist in troubleshooting and debugging failing tests.
```java
    app.azzert().alertPresent();
    app.azzert().urlEquals();
    
    element.assertContains().text("hello");
    element.assertExcludes().value("world");
    element.assertEquals().rows(7);
    element.assertState().enabled();
```

##### Web Services
If you plan on running a web services based test, the first thing you should do is retrieve your call class.
```java
    Call call = this.calls.get();
```
This object will give you access to make any required method calls on your web service, such as a get or post.
```java
    call.get("post/");
    call.post("post/", request);
```

These calls return a response, which contain their own custom assertions. Similar to the browser based testing
these asserts provide additional traceability and debugging assistance. both the response, and code can be
verified
```java
    call.get("post/").assertEquals(404);
    call.post("posts/", request).assertContains(response);
```

###### Authentication
Any of the above calls would occur without any authentication; they just are direct gets, posts, etc. Some 
basic authentication capabilities are built into Selenified. If you have simple user/password authentication 
for your services, Selenified makes it easy to provide those. Simply set the username and password as 
environment variables, and Selenified will automatically pick them up, and pass them along with your call. 
Don’t worry, they’re not passed in clear text, but encoded, and passed as header authorization information.
```shell
export SERVICES_USER=myusername
export SERVICES_PASS=mypassword
```
Instead of passing in credentials globally for your tests, you can set them at the suite or test level as well.
Note that globally passed in credentials take precedence over any set in the test cases themselves. You can 
provide default credentials for an entire class, in the `@BeforeMethod`, just call the static `setCredentials` method.
```java
setCredentials(this, test, "hello", "world");
```
These, credentials can be overridden on a test by test basis by providing them directly in the test case.
```java
Call call = this.calls.get();
call.addCredentials("hello", "world");
```

You may have some more complex authentication scheme. That is not atypical. Unfortunately, in order to set 
this up, you’ll actually need to modify the source code a bit. Because authentication is performed in so many 
different ways, we don’t have a standard setup for oauth, csrf tokens, or others. Checkout the 
[wiki](https://github.com/Coveros/selenified/wiki/Service-Authentication) for more information on authentication 
schemes.

###### Custom Headers
Custom headers can be added to web-services calls, for whatever purpose. They can add user-agents, custom
required headers for sites, or even override the default provided headers. By default, all web services calls
are made with `Content-Type` set to `application/json; charset=UTF-8`. This can be changed by overridding this
header. Headers can be added on a per test basis, or can be added for all tests in a suite. Headers should be 
set as key-value pairs, in a HashMap.
```java
Map<String, String> headers = new HashMap<>();
headers.put("X-Atlassian-Token", "no-check");
```
To set this on an individual basis, simply retrieve the `Call` object, and add the headers
```java
Call call = this.calls.get();
call.addHeaders(headers);
``` 
To set the headers for an entire class, in the `@BeforeMethod`, just call the static `addHeader` method.
```java
addHeaders(this, test, headers);
```
Finally, if you want to reset the headers, on a test by test basis (maybe you want to set up headers for all 
tests instead of one), you can call the `resetHeaders` method.
```java
Call call = this.calls.get();
call.resetHeaders();
```

##### Finalizing your tests
Finally, in order to track errors within the tests, the last step of each test is comparing the value within 
errors to the number 0. This will then throw an error if any issues occurred during the test. All previous
errors are caught and handled, to allow the test to run to completion if possible. This last line should 
read as follow:
```java
    finish();
```

Using and IDE such as Eclipse will help you auto-complete desired commands, and the 
[JavaDocs](https://coveros.github.io/selenified) provided will outline each piece of functionality.

#### Using Page Object Model
Selenified supports allowing tests to be written following the Page Object Model (POM). Each page or module
you want to use in this model, simply needs a constructor taking in the `app` object, where the desired page
elements are defined. Then any workflows on the page can be easily written utilizing those elements.
```java
public final class MainPage {

    // our page elements
    private Element click;
    private Element alert;
    private Element carList;
    public Element checkbox;

    public MainPage(App app) {
        click = app.newElement(Locator.CLASSNAME, "click");
        alert = app.newElement(Locator.CSS, "input#alert_button");
        carList = app.newElement(Locator.ID, "car_list");
        checkbox = app.newElement(Locator.XPATH, "//form/input[@type='checkbox']");
    }

    public void selectCar(String car) {
        carList.select(car);
    }

    public void assertCar(String car) {
        carList.assertEquals().selectedOption(car);
    }

    public void generateAlert() {
        click.click();
        alert.click();
    }
}
```

In order to use these models, your tests only need to instantiate these pages before running. This is done
most simply in a @BeforeMethod call.
```java
    ThreadLocal<MainPage> main = new ThreadLocal<>();;

    @BeforeMethod(alwaysRun = true)
    public void setupApp() {
        main.set(new MainPage(this.apps.get()));
    }

    @Test(groups = { "sample", "pom" }, description = "A sample test to perform searches")
    public void sampleTestWDataProvider() {
        // our test actions - use our threadsafe main object
        main.get().selectCar("volvo");
        main.get().assertCar("volvo");
        // close out the test
        finish();
    }
```

#### Locators
Selenified uses locators to find different elements on a webpage during testing. There are 8 different 
types of locators supported: xpath, id, name, classname, css, partial link text, link text, and tagname. 
Locators are used to navigate the HTML Document Object Model, returning a single web element or a list of 
web elements with common locator attributes. For example, you may create an element with the locator 
type 'tagname' and the locator 'h3' to then call the getWebElements method with the locator. This returns 
a list of all HTML elements on the webpage with the 'h3' tag. To be more specific, locators like id are 
often used to return a single web element, being that the id attribute of a web element is supposed to be unique. An elementMatch can also be provided when using locators that match more than one element. For example, calling the method click() on an element with the locator type 'classname', the locator 'filter-button', and the elementMatch 3 will return the third element with the class attribute equal to 'filter-button'.

##### Identifying Locators
The easiest way to identify locators for elements you want to test is to use a web browser. There are 
many tools to assist in finding locators, but you may not always need a tool. Start by right-clicking 
on an element and then click 'Inspect' (Chrome)  or 'Inspect Element' (Firefox). From here you are then 
presented with the HTML source of the page with the selected element highlighted. Look at the element's 
attributes for a unique locator such as an id or class name. If your element has an id or class name, 
search through the rest of the webpage source to ensure that your element's id or class name attribute 
is unique. Command or Ctrl + F is the quickest way to do this. Other attributes can be used if unique as 
well, such as name, link text (if the element is a link), or tagname. If an element does not have a unique 
locator, you can use an xpath locator or provide an index to specify a single element out of all the elements 
that match the locator. To copy an element's xpath in Chrome, right click on the element after inspecting 
it and mouseover 'Copy', then click on 'Copy Xpath'. In Firefox, you'll have to use an Add-on like 
'Xpath Checker'. Xpaths are powerful and can always provide a unique selector, but because of the way they 
often traverse the DOM specifically, they can be very brittle and possibly break with any change to your 
webpage layout.  

##### Examples
###### Xpath
```java
Element carList = page.newElement( Locators.XPATH, "//*[@id='align_table']/tbody/tr[1]/td[1]/select[1]");
```
###### Id
```java
Element carList = page.newElement( Locators.ID, "car_list");
```
###### Name
```java
Element carList = page.newElement( Locators.NAME, "car_list");
```
###### Classname
```java
Element carList = page.newElement( Locators.CLASSNAME, "dropdown-default");
```
###### CSS
```java
Element carList = page.newElement( Locators.CSS, "#car_list");
```
###### Partial link text
```java
Element nextImageLink = page.newElement( Locators.PARTIALLINKTEXT, "next");
```
###### Link text
```java
Element nextImageLink = page.newElement( Locators.LINKTEXT, "next image");
```
###### Tag name
```java
Element carList = page.newElement( Locators.TAGNAME, "select");
```
##### Duplicate locators
If you have a non-unique locator, something that would match multiple elements, you can tell Selenified
which element to match. By default, the first matching element is used if not provided. 
```java
Element carList = page.newElement(Locators.TAGNAME, "select");
```
In this example, the first `select` element on the page is chosen to interact with.
```java
Element carList = page.newElement(Locators.TAGNAME, "select", 4);
```
In this example, the fourth `select` element on the page is chosen to interact with. Note that element 
identification starts at 0. You can also dynamically change the element match during your test, which 
is useful when looping through tasks.
```java
Element element = app.newElement(Locators.TAGNAME, "select");
for (int match = 0; match < element.get().matchCount(); match++) {
    element.setMatch(match);
    element.select(2);
}
```

#### Custom Actions
Sometimes, the action you want to perform isn't available via Selenified. Luckily this doesn't mean you need 
to abandon Selenified.

If you need to perform the custom action, use the `app` object to retrieve the driver.
```java
    WebDriver driver = app.getDriver();
```
Then perform the action that you need to. You'll want to ensure this action is recorded in the Selenified
reports. To do this, retrieve the `outputfile` object from the `app` object, and call the recordAction
method on. If you need to record custom verifications, you can use recordExpected and recordActual.
```java
    OutputFile file = app.getOutputFile();
    file.recordAction(action, expectedResult, actualResult, result);
    file.recordExpected(expectedOutcome);
    file.recordActual(actualOutcome, result);
```

Of course, if this is something that you believe others can benefit from, feel free to 
[open an issue](https://github.com/Coveros/selenified/issues), or fork the repo, and submit a PR once it's
implemented.

## Running Tests
### Parameters
The testing framework requires no parameters, but takes several optional input parameters.
#### Application URL
This is the default URL that all tests should run against. This value can be overridden in each test, class, or 
even suite (see below).
```
-DappURL=www.example.org
-DappURL=192.168.1.1
-DappURL=https://192.168.1.1:6443
```
#### Browser
If unspecified the default browser of HTMLUnit will be used. Other supported browsers are Firefox (specified via 
Firefox or Marionette), Chrome, InternetExplorer, Edge, Android (not on grid), Ipad (not on grid), Iphone (not on 
grid), Opera, and Safari, PhantomJS (not on grid). To run most other browsers additional drivers may need to be added 
to the browser install directory. These drivers are all managed via the selenified jar. Browsers can be specified 
in two ways, either just noting the browser, or indicating the browser name, and additional details, 
following [grid](http://www.seleniumhq.org/docs/07_selenium_grid.jsp#node-configuration) notation.
```
-Dbrowser=Chrome
-Dbrowser=Edge
-Dbrowser="browserName=InternetExplorer&browserVersion=50.1&devicePlatform=Windows 10"
-Dbrowser="browserName=Firefox&browserVersion=47.0&devicePlatform=Linux"
```
#### Hub
If unspecified the tests will run in standalone mode. If a hub address is specified, then tests will run on a remove 
hub, either via Selenium Grid or SauceLabs. Specify the protocol, IP or DNS, and include the ports if needed.
```
-Dhub=http://localhost:4444
-Dhub=https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443
``` 
#### Proxy
If this is specified, then the tests will be run through a proxy server at the specified address. Include the 
address and port in the parameter
```
-Dproxy=localhost:5013
```

#### Screensize
If specified, then the launched browsers, will be at the specified size. This can be provided in one of two ways,
either as `width`x`height` or by specifying `maximum` implying you want the browser to be maximized. These make use 
of the Selenified calls `resize` and `maximize` respectively on browser start-up, before even loading the initial 
URL.
```
-Dscreensize=600x400
-Dscreensize=maximum
```

#### Headless
Currently, only Chrome and Firefox supports running in headless mode. To achieve this, simply pass in the parameter 
`headless`
```
-Dheadless
```
#### Options
Currently, only Chrome and Firefox supports adding additional options to launch the browser. To achieve this, simply 
pass in the parameter `options` with whatever browser specific options are desired. The headless parameter can either
be passed in via the above `headless` parameter, or in this method. For multiple options, make them comma separated.
```
-Doptions='--disable-gpu,--headless'
```
#### Failsafe
The pom included in this project works as an example for specifying which tests to run, and how to execute them. Tests 
should be executed using the failsafe plugin, if using Maven, following standard Java practices. Several variables can 
be easily set to specify which tests to run, and how to run them, all from the failsafe plugin itself.
```xml
<maven.failsafe.plugin.version>2.21.0</maven.failsafe.plugin.version>
<!-- Test run information -->
<failsafe.threads>5</failsafe.threads>
<failsafe.verbosity>0</failsafe.verbosity>
<failsafe.groups.include>integration</failsafe.groups.include>
<failsafe.groups.exclude>browser</failsafe.groups.exclude>
<failsafe.files.include>**/*IT.java</failsafe.files.include>
<failsafe.files.exclude></failsafe.files.exclude>

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-failsafe-plugin</artifactId>
    <version>${maven.failsafe.plugin.version}</version>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>${failsafe.threads}</threadCount>
        <properties>
            <property>
                <name>surefire.testng.verbose</name>
                <value>${failsafe.verbosity}</value>
            </property>
            <property>
                <name>listener</name>
                <value>com.coveros.selenified.utilities.Transformer</value>
            </property>
        </properties>
        <groups>${failsafe.groups.include}</groups>
        <excludedGroups>${failsafe.groups.exclude}</excludedGroups>
        <includes>
            <include>${failsafe.files.include}</include>
        </includes>
        <excludes>
            <exclude>${failsafe.files.exclude}</exclude>
        </excludes>
    </configuration>
    <executions>
        <execution>
            <id>verify</id>
            <goals>
                <goal>verify</goal>
            </goals>
        </execution>
    </executions>
</plugin>
``` 
Several options exist to change how your tests are run, including `threads` (how many to run in parallel), `verbosity` 
(how much logging is desired), `groups` (both included and excluded), and `files` (both included and excluded). Be sure 
to set some standard values like in the above, and these can then be simply overridden from the commandline. More 
options can he found [here](https://maven.apache.org/surefire/maven-failsafe-plugin/examples/testng.html)

### Eclipse
Expand the project in the left side navigational panel. Right-click on the Java package, class, or method containing 
the test(s) you want to run (for our example it is SampleTests.java), and select the Run As menu item, and click on 
the TestNG Test sub-item. This will launch the tests.

Once the tests have completed running, TestNG results will be displayed under the TestNG tab at the lower section of 
the screen.

If you want to provide inputs to the tests being run, when right clicking on the Java file containing test, select the 
Run Configurations... sub-item. On the option screen, select the Arguments tab on the upper left of the screen. In the 
Program arguments input area, enter in the desired input details to be tested as below:
```
-appURL=www.google.com -Dbrowser=Chrome -Dhub=localhost -Dproxy=localhost:8082
```
### IntelliJ
Right-click on the Java package, class, or method containing the test(s) you want to run (for our example it is 
SampleIT.java), and select the Run (package, class, or method) menu item. This will launch the tests.

If you want to provide inputs to the tests being run, select Run -> Edit Configurations... from the top menu. 
On the option menu, under JDK Settings tab, add your options into the VM options field as below:
```
-appURL=google.com -Dbrowser=InternetExplorer -Dhub=192.168.1.10
```
You can enter these values under either your already created tests, or as the default, if you want all tests to use them.

### Command Line
#### Ant
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the `cd` 
command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
ant clean
```
Once that completes, run the following command to execute the tests:
```
ant -DappURL=http://google.com -Dbrowser=Firefox -Dhub=http://localhost -Dproxy=localhost:8080
```
The default task is 'test', which can alternatively be executed, or could be chained with other commands.
```
ant clean test -DappURL=http://google.com -Dbrowser=Android -Dproxy=172.16.3.12:8080
```
#### Maven
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the `cd` 
command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
mvn clean
```
Once that completes, run the following command to execute the tests:
```
mvn verify -DappURL=https://amazon.com -Dbrowser=Edge -Dhub=https://172.16.3.12:6443
```
To specify different groups of tests to run, instead of manipulating the TestNG xml file, you can provide an 
additional parameter, `failsafe.groups.include` with the desired group to test
```
mvn verify -Dfailsafe.groups.include=smoke
```
#### Gradle
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the `cd` 
command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
gradle clean
```
Once that completes, run the following command to execute the tests:
```
gradle seleniumTest -DappURL=google.com -Dbrowser=Firefox
```
To specify different groups of tests to run, instead of manipulating the TestNG xml file, you can provide an 
additional parameter, groups with the desired group to test
```
gradle seleniumTest -Pgroups=virtual
```
## Viewing Results
To view test results, navigate to the newly created target-output folder within the framework directory. Within 
SecureCI™ you can view these results through the browser. Open the index.html file: this is a file generated by 
TestNG, and will list all of the test suites run. Each test suite will contain a link on the left side of the 
table. Clicking each of these links will bring up a page showing the individual suite listed on a framed page. 
Click the results link. The right frame will then show each test run within the suite. Red highlights indicate 
failure, yellow a skip, and green a pass. Each test will have a description and other information about the test run. 
Along with the general results listed under the suite, navigating to the ”reporter output link” will provide links 
to detailed results for each test run. Clicking on each of these links will display a step by step procedure about 
what was run, in addition to details about the test. These steps and information are also very useful for debugging. 
They will alert if elements are missing, locators are bad, or anything else. Both locators and associated IDs are 
listed to make fixing tests or the app easier.

If running within SecureCI™ and Jenkins, TestNG produces a JUnit XML results file. This is great for storing 
results/metrics within Jenkins, and tracking trends. Additionally, consider archiving testing results to go along 
with these trending results.

## Installation
### Building the jar
If you want to compile the jar from the source code, use maven. Maven can be used to run unit tests, run
integration tests, build javadocs, and build the executable jar. To simply execute the unit tests, run the
below command
```
mvn clean test
```
To also build the jars, run the below commands
```
mvn clean package
```
To run the integration tests, use the verify goal. The integration tests currently point at a private server
hosting the file found in this base directory called `index.html`. In order to properly execute these tests,
host this file, and set the testSite to point to the hosted file's location. This can be done dynamically through
the command line, as outlined below in the Application URL section.

Some of the integration tests require a physical browser to run, and so they can be run two different ways, the 
entire set with a browser, or a subset using HtmlUnit
```
mvn clean verify -Dbrowser=Firefox
mvn clean verify -Dfailsafe.groups.include=virtual
```

### Adding the jar to your project
See the below sections on executing tests to see the proper way to source the jar, and add them to your 
classpath

### Packaging Results
If you'd like to zip up your test reports along with screenshots, include the 'packageResults' system property
and set it to true
```
mvn clean verify -Dbrowser=Firefox -DpackageResults=true
```
The zipped results will be placed in the same directory as the test results

## Known Issues
* Safari 8 doesn't properly handle alerts. These exceptions are caught and handled in the code, but will
cause tests to fail. This is an Apple/Selenium issue, not specific to Selenified.
https://github.com/SeleniumHQ/selenium-google-code-issue-archive/issues/3862
* Safari can't navigate using forward or backward history functionality. These exceptions are caught and handled 
in the code, but will cause tests to fail. This is an Apple/Selenium issue, not specific to Selenified.
https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/3771
* Unable to access, edit, or clear cookies in Edge 18. These exceptions are caught and handled in the code, 
but may cause tests to fail, as cookies present are always returned as false. This is a Microsoft Edge
and EdgeDriver issue, not specific to Selenified.
https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14838528/

### Skipping Tests
To handle some of these known issues, the ability to skip a test, based on the browser is provided. For example
if a particular test includes html alerts, you might want to indicate to never run this test in Safari, as it will
always fail. In this case, a standard group can be added to this test case, so that it is always skipped if 
Safari is the specified browser, but otherwise, always run.
```java
    @Test(groups = { "no-safari" }, description = "Verified a pop-up alert is present - won't run properly in Safari")
```
All browsers are supported for this feature, simply prepend the browser name with `no-` in the group name.