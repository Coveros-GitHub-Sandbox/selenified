# Selenified Testing Framework

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
mvn clean verify -Dfailsafe.groups=virtual
```

### Adding the jar to your project
See the below sections on executing tests to see the proper way to source the jar, and add them to your 
classpath

## Writing Tests
### Create A New Test Suite
Tests should be organized into suites. To create a new test suite, simply create a folder, 
or nested set of folders within the src directory. Within each folder, then create one or 
more Java classes. Name the class something descriptive following the test suites purposes.

### Structuring the Test Suite
Have each class extend the TestBase class which is contained within the 
selenified.jar. Each suite can optionally contain a method setting up some details to 
be used in each test. The URL the selenium tests should connect to (which can be overridden), 
the author of the tests, and the version of tests or software under test. 
See below for an example:

```java
    @BeforeClass (alwaysRun = true)
    public void beforeClass() throws Exception {
        //set the base URL for the tests here
        testSite = "http://www.google.com/";
        //set the author of the tests here
        author = "Max Saperstone\n<br />max.saperstone@coveros.com";
        //set the version of the tests or of the software, possibly with a dynamic check
        version = "0.0.0";
    }
```

If the suite of tests do not require a selenium instance to run the test (e.g. API test), the suite
should also contain a method to override the default startTest method. This needs to be annotated 
with @BeforeMethod. This method will just call the super method of startTest and initialize everything needed. 
Since we do not need a Selenium driver, the last value passed into startTest should be set to 'FALSE'. 
See below for an example:

```java
    @BeforeMethod (alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) throws IOException {
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
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) throws IOException {
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
have a void return type. Additionally, each method should be prepared to catch an IOException, resulting from 
either a failed assert at the end of each test, or from other errors arising during runtime. Each method should 
have an @Test annotation before it. Putting in the below information in each annotation will ensure that the 
data is available in the custom test reporting.

 * a group - based on the test suite and the extended test suite
 * a description - something useful/descriptive to be displayed on the results and test detailed results
 * a dependency (optional) - based on either another group or test, or multiples
 * a data provider (optional) - if this test takes multiple inputs, allowing the test to run multiple times

The method body should start with one or two lines, based on the test steps you plan to perform. Tests should always 
start in a known state, be sure you use your @BeforeMethod annotation to set this up if needed. Next, tests should
perform some action (this may or may not be necessary, depending on the test). To perform any selenium actions, first
obtain the selenium `action` object automatically created for this test.

```java
    Action actions = this.actions.get();
```

Then perform any actions you desire, using this object. All Selenium commands to be executed can be found within 
the Action class. Functionality from clicking and typing, to hovering and changing orientation are all contained 
within this class. Using and IDE such as Eclipse will help you auto-complete desired commands, and the JavaDocs 
provided will outline each piece of functionality. Tests can be built directly from combining these method calls 
into a test, or alternatively to create an overall stronger set of tests, a separate class or classes can be created 
to form workflows using these actions. Test steps can then reference calls to workflows, instead of direct actions.

You should end each test case performing a verification of your actions. The `Assert` object is automatically created
for any and all selenium verifications.

```java
    Assert asserts = this.asserts.get();
```

Similar to the first class, functionality from confirming an alert and element are present, to checking the css and 
page source are all contained within this class. 

Finally, in order to track errors within the tests, the last step of each test is comparing the value within errors
to the number 0. This will then throw an error if any issues occurred during the test. All previous errors are caught
and handled, to allow the test to run to completion if possible. This last line should read as follow:

```java
    finish();
```

If a class has multiple tests that are similar, but simply require one or two different inputs, a dataProvider 
should be used. Instead of writing multiple tests, one test can be written instead. This will reduce the amount 
of code being written, and make updates quicker and cleaner. A full example test can be seen in the included 
SampleIT.java class in the framework.

If you are defining your dataProvider parameters, be sure to include the variables defined in your declaring method.


#### POM
Selenified supports allowing tests to be written following the Page Object Model (POM). In addition to ensuring your 
test workflows are appropriately structured, in order to ensure the POM is followed, when creating classes for each
of your pages, use the provided `Element` object. After creating an `Element` object for each element you want to 
interact with, these objects can be passed directly to the Action and Assert methods interacting with the page.
This means instead of having an action looking like
```java
	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the waitForElementPresent method")
	public void sampleTestWaitForElementPresent() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementPresent(Locators.name, "car_list");
		// verify no issues
		finish();
	}
```
It could instead look like this
```java
    Element carList = new Element( Locators.name, "car_list");
```
```java
	@Test(groups = { "sample", "virtual" }, description = "A sample test to check the waitForElementPresent method")
	public void sampleTestWaitForElementPresent() throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.waitForElementPresent(carList);
		// verify no issues
		finish();
	}
```

### Update testng build file
When tests are executed from the commandline, the build file dictates which tests to execute. When a new package, 
class or method is added to the test suite, they need to be included in the XML file if you want them to run. 
More details on how to update this file can be found on the 
[​TestNG Documentation site](http://testng.org/doc/documentation-main.html#testng-xml).

## Configuring within an IDE
### Eclipse
Coming Soon
### IntelliJ
#### Clone from GitHub
Select `File -> New -> Project From Version Control -> GitHub`, then log in to your github account.
Enter `https://github.com/msaperst/selenified-testing-framework.git` as the project location.
#### With code already cloned locally
Select ```File -> New -> Project From Existing Sources```.
Navigate to wherever you cloned the project, and select OK

Click OK to open the project.
Right click on `pom.xml`
Select `Add as Maven Project`

#### You might need to select a Module SDK
Right click on the project
Select `Open Module Settings` or click F4
Click the dependencies tab
Select 1.8 as the Module SDK

## Running Tests
### Parameters
The testing framework requires no parameters, but takes several optional input parameters.
#### Test Suite
As the Selenified Testing Framework is built on top of TestNG, it follows the testng testing suite example 
for determining which tests to execute. An XML file must exist following the guidelines outlined 
[here](http://testng.org/doc/documentation-main.html#testng-xml). If this file is not specified, the default 
file specified in the build tool will be used. If that file does not exist, no tests will be specified, and 
so nothing will run.   
```
-DsuiteXmlFile=my-test-suite.xml
```
There is a sample testng build file included named `integration.xml` which points to the included integration tests
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
-DsuiteXmlFile=smoke.xml -appURL=www.google.com -Dbrowser=Chrome -Dhub=localhost -Dproxy=localhost:8082
```
### IntelliJ
Right-click on the Java package, class, or method containing the test(s) you want to run (for our example it is 
SampleIT.java), and select the Run (package, class, or method) menu item. This will launch the tests.

If you want to provide inputs to the tests being run, select Run -> Edit Configurations... from the top menu. 
On the option menu, under JDK Settings tab, add your options into the VM options field as below:
```
-DsuiteXmlFile=./suites/regression.xml -appURL=google.com -Dbrowser=InternetExplorer -Dhub=192.168.1.10
```
You can enter these values under either your already created tests, or as the default, if you want all tests to use them.

### Command Line
#### TestNG
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the `cd` 
command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
rm -rf bin/*
```
Create a directory for your dependencies, and download all of the required dependencies.
```
mkdir target/dependency
cd target/dependency
wget http://central.maven.org/maven2/org/seleniumhq/selenium/selenium-java/2.53.1/selenium-java-2.53.1.jar
wget http://central.maven.org/maven2/org/seleniumhq/selenium/selenium-server/2.53.1/selenium-server-2.53.1.jar
wget http://central.maven.org/maven2/org/codehaus/jackson/jackson-mapper-asl/1.9.13/jackson-mapper-asl-1.9.13.jar
wget http://central.maven.org/maven2/org/testng/testng/6.10/testng-6.10.jar
wget http://central.maven.org/maven2/org/seleniumhq/selenium/selenium-htmlunit-driver/2.52.0/selenium-htmlunit-driver-2.52.0.jar
wget http://central.maven.org/maven2/org/slf4j/slf4j-simple/1.7.23/slf4j-simple-1.7.23.jar
```
Create the folder to hold our compiled classes
```
mkdir bin
```
Compile your tests
```
javac  -cp "target/dependency/*" -d bin test/*.java
```
Finally, launch your tests
```
java -cp "bin;target/dependency/*" -DappURL=http://localhost/ org.testng.TestNG sample.xml
```
#### Ant
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the `cd` 
command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
ant clean
```
Once that completes, run the following command to execute the tests:
```
ant -DsuiteXmlFile=../acceptance.xml -DappURL=http://google.com -Dbrowser=Firefox -Dhub=http://localhost -Dproxy=localhost:8080
```
The default task is 'test', which can alternatively be executed, or could be chained with other commands.
```
ant clean test -DsuiteXmlFile=./suites/all.xml -DappURL=http://google.com -Dbrowser=Android -Dproxy=172.16.3.12:8080
```
#### Maven
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the `cd` 
command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
mvn clean
```
Once that completes, run the following command to execute the tests:
```
mvn verify -DsuiteXmlFile=../acceptance.xml -DappURL=https://amazon.com -Dbrowser=Edge -Dhub=https://172.16.3.12:6443
```
To specify different groups of tests to run, instead of manipulating the TestNG xml file, you can provide an 
additional parameter, failsafe.groups with the desired group to test
```
mvn verify -Dfailsafe.groups=smoke
```
#### Gradle
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the `cd` 
command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
gradle clean
```
Once that completes, run the following command to execute the tests:
```
gradle seleniumTest -PsuiteXmlFile=../acceptance.xml -DappURL=google.com -Dbrowser=Firefox
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
