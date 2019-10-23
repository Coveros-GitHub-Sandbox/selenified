/*
 * Copyright 2019 Coveros, Inc.
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

package com.coveros.selenified.services.check;

import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Reporter;

/**
 * Verify will handle all verifications performed on the actual web services
 * calls themselves. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.3.0
 * @lastupdate 6/25/2019
 */
public class VerifyExcludes extends Excludes {

    /**
     * The default constructor passing in the app and output file
     *
     * @param response - the response from the web services call
     * @param reporter - the file to write all logging out to
     */
    public VerifyExcludes(Response response, Reporter reporter) {
        this.response = response;
        this.reporter = reporter;
    }

    ///////////////////////////////////////////////////////
    // assertions about the page in general
    ///////////////////////////////////////////////////////

}