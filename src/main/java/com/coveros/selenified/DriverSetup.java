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

package com.coveros.selenified;

/**
 * determining how to launch/start the browser. Do we even want a browser, and
 * if so do we wait for the initial page to load, or do we need to perform other
 * activities first
 *
 * @author Max Saperstone
 * @version 3.0.4
 * @lastupdate 8/29/2018
 */
public enum DriverSetup {
    FALSE, OPEN, LOAD;

    private Boolean browser;
    private Boolean load;

    static {
        FALSE.browser = false;
        OPEN.browser = true;
        LOAD.browser = true;
    }

    static {
        FALSE.load = false;
        OPEN.load = false;
        LOAD.load = true;
    }

    public Boolean useBrowser() {
        return this.browser;
    }

    public Boolean loadPage() {
        return this.load;
    }
}