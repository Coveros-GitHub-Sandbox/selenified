# Selenified Testing Framework™

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

The method body should start with one or two lines, in order to obtain the passed in Method and ITestContext. The below line should start each test, to ensure the custom test report is properly generated

```java
    TestOutput output = getTestOutput(method, test, dataProvider);
```

If any Selenium commands will be run, you’ll want to pull the SeleniumHelper object as well

```java
    SeleniumHelper selHelper = getSelHelper(method, test, dataProvider);
```

If you are defining your dataProvider parameters, be sure to update the varaible name of ```dataProvider``` to your variable(s) name(s)

All Selenium commands to be executed can be found within the selHelper class. Functionality from clicking and typing, to hovering and changing orientation are all contained within this class. Using Eclipse will help you auto-complete desired commands, and the JavaDocs provided will outline each piece of functionality. For performing checks and verifications, the output class should be used. Functionality from confirming an alert and element are present, to checking the css and page source are all contained within this class. Tests can be build directly from combining these method calls into a test, or alternatively to create an overall stronger set of tests, a separate class or classes can be created to form workflows using these actions. Test steps can then reference calls to workflows, instead of direct actions.

Finally, in order to track errors within the tests, the last step of each test is comparing the value within errors to the number 0. This will then throw an error if any issues occurred during the test. All previous errors are caught and handled, to allow the test to run to completion if possible. This last line should read as follow:

```java
    finalize( output );
```

If a class has multiple tests that are similar, but simply require one or two different inputs, a dataProvider should be used. Instead of writing multiple tests, one test can be written instead. This will reduce the amount of code being written, and make updates quicker and cleaner. A full example test can be seen in the included SampleTests.java class in the framework.

###Update testng build file
When tests are executed from the commandline, the build file dictates which tests to execute. When a new package, class or method is added to the test suite, they need to be included in the XML file if you want them to run. More details on how to update this file can be found on the [​TestNG Documentation site](http://testng.org/doc/documentation-main.html#testng-xml).

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
As the Selenified Testing Framework is built on top of TestNG, it follows the testng testing suite example for determining which tests to execute. An XML file must exist following the guidelines outlined [here](http://testng.org/doc/documentation-main.html#testng-xml). If this file is not specified, the default file specified in the build tool will be used. If that file does not exist, no tests will be specified, and so nothing will run.   
```
-Dtest-suite=my-test-suite.xml
```
There is a sample testng build file included named `sample.xml` which points to the included sample tests
#### Application URL
This is the default URL that all tests should run against. This value can be overridden in each test, class, or even suite (see below).
```
-DappURL=www.example.org
-DappURL=192.168.1.1
-DappURL=https://192.168.1.1:6443
```
#### Browser
If unspecified the default browser of HTMLUnit will be used. Other supported browsers are Firefox (specified via Firefox or Marionette), Chrome, InternetExplorer, Edge, Android (not on grid), Ipad (not on grid), Iphone (not on grid), Opera, and Safari, PhantomJS (not on grid). To run most other browsers additional drivers may need to be added to the browser install directory. These drivers are all managed via the webDriverManager jar. Browsers can be specified in two ways, either just noting the browser, or indicating the browser name, and additional details, following [grid](http://www.seleniumhq.org/docs/07_selenium_grid.jsp#node-configuration) notation.
```
-Dbrowser=Chrome
-Dbrowser=Edge
-Dbrowser="browserName=InternetExplorer&browserVersion=50.1&devicePlatform=Windows 10"
-Dbrowser="browserName=Firefox&browserVersion=47.0&devicePlatform=Linux"
```
#### Hub
If unspecified the tests will run in standalone mode. If a hub address is specified, then tests will run on a remove hub, either via Selenium Grid or SauceLabs. Specify the protocol, IP or DNS, and include the ports if needed.
```
-Dhub=http://localhost:4444
-Dhub=https://sauceusername:sauceaccesskey@ondemand.saucelabs.com:443
``` 
#### Proxy
If this is specified, then the tests will be run through a proxy server at the specified address. Include the address and port in the parameter
```
-Dproxy=localhost:5013
```

### Eclipse
Expand the project in the left side navigational panel. Right-click on the Java package, class, or method containing the test(s) you want to run (for our example it is SampleTests.java), and select the Run As menu item, and click on the TestNG Test sub-item. This will launch the tests.

Once the tests have completed running, TestNG results will be displayed under the TestNG tab at the lower section of the screen.

If you want to provide inputs to the tests being run, when right clicking on the Java file containing test, select the Run Configurations... sub-item. On the option screen, select the Arguments tab on the upper left of the screen. In the Program arguments input area, enter in the desired input details to be tested as below:
```
-Dtest-suite=smoke.xml -appURL=www.google.com -Dbrowser=Chrome -Dhub=localhost -Dproxy=localhost:8082
```
### IntelliJ
Right-click on the Java package, class, or method containing the test(s) you want to run (for our example it is SampleTests.java), and select the Run (package, class, or method) menu item. This will launch the tests.

If you want to provide inputs to the tests being run, select Run -> Edit Configurations... from the top menu. On the option menu, under JDK Settings tab, add your options into the VM options field as below:
```
-Dtest-suite=./suites/regression.xml -appURL=google.com -Dbrowser=InternetExplorer -Dhub=192.168.1.10
```
You can enter these values under either your already created tests, or as the default, if you want all tests to use them.

### Command Line
#### TestNG
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the cd command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
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
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the cd command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
ant clean
```
Once that completes, run the following command to execute the tests:
```
ant -Dtest-suite=../acceptance.xml -DappURL=google.com -Dbrowser=Firefox -Dhub=http://localhost -Dproxy=localhost:8080
```
The default task is 'test', which can alternatively be executed, or could be chained with other commands.
```
ant clean test -Dtest-suite=./suites/all.xml -DappURL=google.com -Dbrowser=Android -Dproxy=172.16.3.12:8080
```
#### Maven
Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the cd command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
```
mvn clean
```
Once that completes, run the following command to execute the tests:
```
mvn verify -Dtest-suite=../acceptance.xml -DappURL=https://amazon.com -Dbrowser=Edge -Dhub=https://172.16.3.12:6443
```
#### Gradle
TBD

## Viewing Results
To view test results, navigate to the newly created target-output folder within the framework directory. Within SecureCI™ you can view these results through the browser. Open the index.html file: this is a file generated by TestNG, and will list all of the test suites run. Each test suite will contain a link on the left side of the table. Clicking each of these links will bring up a page showing the individual suite listed on a framed page. Click the results link. The right frame will then show each test run within the suite. Red highlights indicate failure, yellow a skip, and green a pass. Each test will have a description and other information about the test run. Along with the general results listed under the suite, navigating to the ”reporter output link” will provide links to detailed results for each test run. Clicking on each of these links will display a step by step procedure about what was run, in addition to details about the test. These steps and information are also very useful for debugging. They will alert if elements are missing, locators are bad, or anything else. Both locators and associated IDs are listed to make fixing tests or the app easier.

If running within SecureCI™ and Jenkins, TestNG produces a JUnit XML results file. This is great for storing results/metrics within Jenkins, and tracking trends. Additionally, consider archiving testing results to go along with these trending results.