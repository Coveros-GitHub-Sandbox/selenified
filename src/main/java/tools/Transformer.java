package tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

public class Transformer implements IAnnotationTransformer {

	/**
	 * overrides the basic TestNG transform function to provide dynamic access
	 * to an invocation count
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setInvocationCount(StringUtils.countMatches(System.getProperty("browser"), ",") + 1);
	}
}
