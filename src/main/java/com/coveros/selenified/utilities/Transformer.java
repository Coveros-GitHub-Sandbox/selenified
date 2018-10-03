/*
 * Copyright 2018 Coveros, Inc.
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

package com.coveros.selenified.utilities;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Programmatically sets an invocation count for each test, based on the number
 * of browsers desired to test on. This allows for simple looping of the same
 * test multiple times each run on a different browser, which is handled by the
 * Selenified class. This class should be specified as a listener for the main
 * Selenified class, and/or in the TestNG xml file.
 *
 * @author Max Saperstone
 * @version 3.0.3
 * @lastupdate 8/29/2018
 */
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
