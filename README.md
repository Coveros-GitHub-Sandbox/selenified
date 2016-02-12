SecureCI WebDriver Testing Framework

Writing Tests
	Coming Soon

Configuring within an IDE
	Eclipse
		Coming Soon

Running Tests
	Parameters
		The testing framework take two optional input parameters.
		Browser
			If unspecified the default browser of Firefox will be used. The supported browsers are Firefox, Chrome, InternetExplorer, Android, Ipad (only locally - not on grid), Iphone (only locally, not on grid), Opera, and Safari. To run most other browsers additional drivers may need to be added to the browser install directory. Multiple drivers can be found in the 'lib' directory.
		Hub Address
			If unspecified the tests will run in standalone mode. If a hub address is specified, then tests will run via Selenium Grid on the specified grid address. Just list the IP or DNS, not ports, or protocol should be provided.
	Eclipse
		TestNG
			Open up the Eclipse IDE. Expand the project in the left side navigational panel. Right-click on the Java file containing the tests (for our example it is SampleTests.java) and select the Run As menu item, and click on the TestNG Test sub-item. This will launch the tests.
			Once the tests have completed running, TestNG results will be displayed under the TestNG tab at the lower section of the screen.
			If you want to provide inputs to the tests being run, when right clicking on the Java file containing test, select the Run Configurations... sub-item. On the option screen, select the Arguments tab on the upper left of the screen. In the Program arguments input area, enter in the desired input details to be tested as below:
				-Dbrowser=Chrome -DhubAddress=localhost
		Ant
			Open up the Eclipse IDE. Expand the project in the left side navigational panel. Right-click on the build.xml file and select the Run As menu item, and click on the Ant-Build sub-item. This will launch the tests.
			If you want to provide inputs to the tests being run, when right clicking on the build.xml file select the Ant Build... sub-item. On the option screen, select the Main tab on the upper left of the screen. In the Arguments input area, enter in the feature file to be tested as below:
				-Dbrowser=Chrome -DhubAddress=localhost
	Command Line
		TestNG
			Coming Soon
		Ant
			Open up the command prompt. Navigate to the folder where the Test Automation project is checked out using the cd command. Once at the folder, if these tests have been before, it’s best to clean out the results folder. Run the command:
				ant clean
			Once that completes, run the following command to execute the tests:
				ant
			All test results will be stored in the target-output folder.
			If you want to provide inputs to the tests being run, run the below command:
				ant -Dbrowser=Chrome -DhubAddress=localhost

Viewing Results
	Coming Soon