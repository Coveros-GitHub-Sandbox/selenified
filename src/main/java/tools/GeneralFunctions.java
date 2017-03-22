/*
 * Copyright 2017 Coveros, Inc.
 * 
 * This file is part of Selenified.
 * 
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy 
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on 
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY 
 * KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations 
 * under the License.
 */

package tools;

import static org.testng.AssertJUnit.assertEquals;

import java.io.IOException;

import tools.logging.TestOutput;

public class GeneralFunctions {

	/**
	 * Closes out the output file and checks the error count to see how the
	 * tests went, overall passed or failed
	 * 
	 * @param output
	 *            - the TestOutput class that is instantiated
	 * @throws IOException
	 *             - an IOException
	 */
	public void stopTest(TestOutput output) throws IOException {
		stopTest(output, output.getErrors());
	}

	/**
	 * Closes out the output file and checks the error count to see how the
	 * tests went, overall passed or failed
	 * 
	 * @param output
	 *            - the TestOutput class that is instantiated
	 * @param errors
	 *            - the error count from all performed functions
	 * @throws IOException
	 *             - an IOException
	 * @deprecated use {@link #finalize()} instead
	 */
	@Deprecated
	public void stopTest(TestOutput output, int errors) throws IOException {
		output.endTestTemplateOutputFile();
		// new PDFOutput( output.getOutputFileName() );
		assertEquals("Detailed results found at: " + output.getFileName(), "0 errors",
				Integer.toString(errors) + " errors");
	}
}