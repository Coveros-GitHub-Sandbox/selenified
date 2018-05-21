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

package com.coveros.selenified.utilities.jira;

import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Response;

import java.lang.reflect.Method;

public class Jira {

    private static final String JIRA_LINK = "jira.link";

    private final Annotation annotation;
    private final HTTP http;

    public Jira(Method method) {
        this.annotation = new Annotation(method);
        this.http = getJiraHttp();
    }

    public Boolean uploadToJira() {
        return !(System.getProperty(JIRA_LINK) == null || "".equals(System.getProperty(JIRA_LINK)));
    }

    public HTTP getJiraHttp() {
        if (!uploadToJira()) {
            return null;
        }
        String link = System.getProperty(JIRA_LINK);
        String username = "";
        if (System.getProperty("jira.username") != null) {
            username = System.getProperty("jira.username");
        }
        String pswd = "";
        if (System.getProperty("jira.password") != null) {
            pswd = System.getProperty("jira.password");
        }
        return new HTTP(link, username, pswd);
    }

    public HTTP getHTTP() {
        return http;
    }

    public int getProjectId() {
        String project = annotation.getProject();
        if (project == null) {
            return 0;
        }
        Response response = http.get("/rest/api/2/project/" + project);
        if (response.getObjectData() != null && response.getObjectData().has("id")) {
            return response.getObjectData().get("id").getAsInt();
        }
        return 0;
    }

    public int getIssueId() {
        String issue = annotation.getIssue();
        if (issue == null) {
            return 0;
        }
        Response response = http.get("/rest/api/2/issue/" + issue);
        if (response.getObjectData() != null && response.getObjectData().has("id")) {
            return response.getObjectData().get("id").getAsInt();
        }
        return 0;
    }
}