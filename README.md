# SecureCI™ Testing Framework

## Writing Tests
###Create A New Test Suite
Tests should be organized into suites. To create a new test suite, simply create a folder, or nested set of folders within the src directory. Within each folder, then create one or more Java classes. Name the class something descriptive following the test suites purposes.

###Structuring the Test Suite
Have each class extend the SeleniumTestBase class which is contained within the WebDriverWrapper.jar. Each suite can optionally contain a method setting up some details to be used in each test. The URL the selenium tests should connect to, the author of the tests, and the version of tests or software under test. See below for an example:

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

Each suite should also contain a method to setup each test. This method will just call the super method of startTest and initialize everything needed. If we need a Selenium driver, the last value passed into startTest should be set to true, and false otherwise. See below for an example:

```java
    @BeforeMethod (alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test) throws Exception {
        super.startTest(dataProvider, method, test, true);
    }
```

With this in mind, it may be helpful to structure tests based on functionality and attributes under test. If a suite has multiple tests that will be running using similar tests, consider setting up some suite specific methods to run these tests. This will reduce the amount of code being written, and make updates quicker and cleaner.

###Write the Tests
Adding a new test, is simply adding a new method to an existing test suite (class). Each method should be named something descriptive following the tests functionality. Each method should have a public modifier and have a void return type. They should always contain the below parameters:

 * Object[] dataProvider
 * Method method
 * ITestContext test
 
Additionally, each method should be prepared to catch a general exception, resulting from either an assert at the end of each test, or from other errors arising during runtime. Each method should have an annotation before it. Putting in the below information in each annotation will ensure that the data is available in the custom test reporting.

 * a group - based on the test suite and the extended test suite
 * a description - something useful/descriptive to be displayed on the results and test detailed results
 * a dependency (optional) - based on either another group or test, or multiples
 * a data provider (optional) - if this test takes multiple inputs, allowing the test to run multiple times

The method body should start with three to four lines, in order to obtain the passed in Method and ITestContext. The below three lines should start each test, to ensure the custom test report is properly generated

```java
    String testName = getTestName( method, dataProvider );
    TestOutput output = (TestOutput) test.getAttribute( testName + "Output" );
    int errors = (Integer) test.getAttribute( testName + "Errors" );
```

If any Selenium commands will be run, you’ll want to pull the SeleniumHelper object as well

```java
    SeleniumHelper selHelper = (SeleniumHelper) test.getAttribute( testName + "SelHelper" );
```

All Selenium commands to be executed can be found within the selHelper class. Functionality from clicking and typing, to hovering and changing orientation are all contained within this class. Using Eclipse will help you auto-complete desired commands, and the JavaDocs provided will outline each piece of functionality. For performing checks and verifications, the output class should be used. Functionality from confirming an alert and element are present, to checking the css and page source are all contained within this class. Tests can be build directly from combining these method calls into a test, or alternatively to create an overall stronger set of tests, a separate class or classes can be created to form workflows using these actions. Test steps can then reference calls to workflows, instead of direct actions.

Finally, in order to track errors within the tests, the last step of each test is comparing the value within errors to the number 0. This will then throw an error if any issues occurred during the test. All previous errors are caught and handled, to allow the test to run to completion if possible. This last line should read as follow:

```java
    genFun.stopTest( output, errors );
```

If a class has multiple tests that are similar, but simply require one or two different inputs, a dataProvider should be used. Instead of writing multiple tests, one test can be written instead. This will reduce the amount of code being written, and make updates quicker and cleaner. A full example test can be seen in the included SampleTests.java class in the framework.

###Update testng.xml
When tests are executed via Ant, the build file testng.xml dictates which tests to execute. When a new package, class or method is added to the test suite, they need to be included in the XML file if you want them to run. More details on how to update this file can be found on the ​TestNG Documentation site.

## Configuring within an IDE
### Eclipse
Coming Soon

## Running Tests
### Parameters
The testing framework requires one parameter, and takes several optional input parameters.
#### Test Suite
As the SecureCI™ Testing Framework is built on top of TestNG, it follows the testng testing suite example for determining which tests to execute. An XML file must exist following the guidelines outlined [here](http://testng.org/doc/documentation-main.html#testng-xml). The path to this file must be provided, or no tests will be executed.  
#### Application URL
This is the default URL that all tests should run against. This value can be overridden in each test, class, or even suite (see below).
#### Browser
If unspecified the default browser of HTMLUnit will be used. Other supported browsers are Firefox, Chrome, InternetExplorer, Android, Ipad (only locally - not on grid), Iphone (only locally, not on grid), Opera, and Safari. To run most other browsers additional drivers may need to be added to the browser install directory. Multiple drivers can be found in the 'lib' directory.
#### Hub Address
If unspecified the tests will run in standalone mode. If a hub address is specified, then tests will run via Selenium Grid on the specified grid address. Just list the IP or DNS, not ports, or protocol should be provided.
#### Proxy Server
If this is specified AND the proxy port is specified, then the tests will be run through a proxy server at the specified address
#### Proxy Port
If this is specified AND the proxy server is specified, then the tests will be run through a proxy server on the specified port

### Eclipse
#### TestNG
Open up the Eclipse IDE. Expand the project in the left side navigational panel. Right-click on the Java file containing the tests (for our example it is SampleTests.java) and select the Run As menu item, and click on the TestNG Test sub-item. This will launch the tests.

Once the tests have completed running, TestNG results will be displayed under the TestNG tab at the lower section of the screen.

If you want to provide inputs to the tests being run, when right clicking on the Java file containing test, select the Run Configurations... sub-item. On the option screen, select the Arguments tab on the upper left of the screen. In the Program arguments input area, enter in the desired input details to be tested as below:
```
-Dtest-suite=smoke.xml -appURL=google.com -Dbrowser=Chrome -DhubAddress=localhost -DproxyServer=localhost -DproxyPort=8082
```
#### Ant
Open up the Eclipse IDE. Expand the project in the left side navigational panel. Right-click on the build.xml file and select the Run As menu item, and click on the Ant-Build sub-item. This will launch the tests.

If you want to provide inputs to the tests being run, when right clicking on the build.xml file select the Ant Build... sub-item. On the option screen, select the Main tab on the upper left of the screen. In the Arguments input area, enter in the feature file to be tested as below:
```
-Dtest-suite=./suites/regression.xml -appURL=google.com -Dbrowser=InternetExplorer -DhubAddress=192.168.1.10
```

### Command Line
#### TestNG
Coming Soon
#### Ant
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the cd command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
ant clean
```
Once that completes, run the following command to execute the tests:
```
ant -Dtest-suite=../acceptance.xml -appURL=google.com -Dbrowser=Firefox -DhubAddress=localhost -DproxyServer=localhost -DproxyPort=8080
```
The default task is 'run,' which can alternatively be executed, or could be chained with other commands.
```
ant clean run -Dtest-suite=./suites/all.xml -appURL=google.com -Dbrowser=Android -DhubAddress=172.16.3.12 -DproxyServer=172.16.3.12 -DproxyPort=8080
```
All test results will be stored in the target-output folder.

A replace task also exists to provide easy links in the test reports. This should be executed after the tests are completed.
```
ant clean run replace -k -Dtest-suite=./suites/non-destructive.xml -appURL=google.com -Dbrowser=IPhone
```
The -k option ensure this task is always run, even if some of the tests fail.

## Viewing Results
To view test results, navigate to the newly created target-output folder within the framework directory. Within SecureCI™ you can view these results through the browser. Open the index.html file: this is a file generated by TestNG, and will list all of the test suites run. Each test suite will contain a link on the left side of the table. Clicking each of these links will bring up a page showing the individual suite listed on a framed page. Click the results link. The right frame will then show each test run within the suite. Red highlights indicate failure, yellow a skip, and green a pass. Each test will have a description and other information about the test run. Along with the general results listed under the suite, navigating to the ”reporter output link” will provide links to detailed results for each test run. Clicking on each of these links will display a step by step procedure about what was run, in addition to details about the test. These steps and information are also very useful for debugging. They will alert if elements are missing, locators are bad, or anything else. Both locators and associated IDs are listed to make fixing tests or the app easier.

If running within SecureCI™ and Jenkins, TestNG produces a JUnit XML results file. This is great for storing results/metrics within Jenkins, and tracking trends. Additionally, consider archiving testing results to go along with these trending results.