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

package com.coveros.selenified.utilities;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.logging.Level;

/**
 * Overrides the default HtmlUnitDriver. This class specifically overrides the
 * modifyWebClient method to ignore Javascript errors found by the HtmlUnit
 * driver.
 *
 * @author Matthew Grasberger
 */
public class CustomHtmlUnitDriver extends HtmlUnitDriver {
    public CustomHtmlUnitDriver(BrowserVersion version) {
        super(version);
    }

    public CustomHtmlUnitDriver() {
        super();
    }

    public CustomHtmlUnitDriver(boolean enableJavascript) {
        super(enableJavascript);
    }

    public CustomHtmlUnitDriver(Capabilities capabilities) {
        super(capabilities); // To change body of overridden methods use File |
                                // Settings | File Templates.
        System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "fatal");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(Level.OFF);

    }

    /**
     * Overridden to customize the WebClient that the HtmlUnit driver uses, in
     * order to ignore Javascript errors.
     * 
     * @param client
     *            - the client to modify
     * @return WebClient: the modified client
     */
    @Override
    protected WebClient modifyWebClient(WebClient client) {
        // currently does nothing, but may be changed in future versions
        WebClient modifiedClient = super.modifyWebClient(client);

        modifiedClient.getOptions().setThrowExceptionOnScriptError(false);
        return modifiedClient;
    }
}