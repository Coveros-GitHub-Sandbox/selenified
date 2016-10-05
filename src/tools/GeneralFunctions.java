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
		assertEquals("ZZZFIRSTPARTZZZa href='" + output.getFileName() + "'ZZZSECONDPARTZZZ", "0 errors",
				Integer.toString(errors) + " errors");
	}

	public String getMethodName(StackTraceElement e[]) {
		boolean doNext = false;
		for (StackTraceElement s : e) {
			if (doNext) {
				return s.getMethodName();
			}
			doNext = s.getMethodName().equals("getStackTrace");
		}
		return "UNKOWN";
	}

	public String getClassName(StackTraceElement e[]) {
		boolean doNext = false;
		for (StackTraceElement s : e) {
			if (doNext) {
				return s.getClassName();
			}
			doNext = s.getClassName().equals("getStackTrace");
		}
		return "UNKOWN";
	}
}