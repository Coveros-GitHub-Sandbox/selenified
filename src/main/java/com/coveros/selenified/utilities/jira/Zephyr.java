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
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Zephyr {

    private static final String PROJECT = "projectId";
    private static final String VERSION = "versionId";

    private final Jira jira;
    private final HTTP service;

    public int cycleId = 0;

    public Zephyr(Method method) {
        this.jira = new Jira(method);
        this.service = jira.getHTTP();
    }

    public boolean createCycle() {
        //TODO pull these from cmd properties
        String cycleName = "Sample";
        String cycleDescription = "";
        String build = "";
        String environment = "";
        String sprintId = null;

        JsonObject cycle = new JsonObject();
        cycle.addProperty("clonedCycleId", "");
        cycle.addProperty(PROJECT, String.valueOf(jira.getProjectId()));
        cycle.addProperty("name", cycleName);
        cycle.addProperty("description", cycleDescription);
        cycle.addProperty("build", build);
        cycle.addProperty("environment", environment);
        Date today = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("dd/MMM/yy");
        String start = dt.format(today);
        cycle.addProperty("startDate", start);
        cycle.addProperty("endDate", start);
        if (sprintId != null) {
            cycle.addProperty("sprintId", start);
        }
        cycle.addProperty(VERSION, -1);
        Response response = service.post("/rest/zapi/latest/cycle", new Request(cycle));
        if (response.getObjectData() != null && response.getObjectData().has("id")) {
            cycleId = response.getObjectData().get("id").getAsInt();
        }
        return cycleId != 0;
    }

    public boolean addTestToCycle(String... testIds) {
        JsonArray tests = new JsonArray();
        for (String testId : testIds) {
            tests.add(testId);
        }
        JsonObject testForCycle = new JsonObject();
        testForCycle.addProperty(PROJECT, jira.getProjectId());
        testForCycle.addProperty("cycleId", cycleId);
        testForCycle.add("issues", tests);
        testForCycle.addProperty(VERSION, -1);
        testForCycle.addProperty("method", 1);
        Response response = service.post("/rest/zapi/latest/execution/addTestsToCycle", new Request(testForCycle));
        return !response.getMessage().contains("Tests added: <strong>-</strong>");
    }

    public int createExecution(int testId) {
        JsonObject execution = new JsonObject();
        execution.addProperty(PROJECT, jira.getProjectId());
        execution.addProperty("cycleId", cycleId);
        execution.addProperty("issueId", testId);
        execution.addProperty(VERSION, -1);
        Response response = service.post("/rest/zapi/latest/execution", new Request(execution));
        if (response.getCode() != 200) {
            return 0;
        }
        if (response.getObjectData() != null && response.getObjectData().size() > 0) {
            for (Map.Entry<String, JsonElement> entry : response.getObjectData().entrySet()) {
                JsonObject newExecution = entry.getValue().getAsJsonObject();
                return newExecution.get("id").getAsInt();
            }
        }
        return 0;
    }

    private boolean executeTestInCycle(int executionId, int status) {
        JsonObject execution = new JsonObject();
        execution.addProperty("status", status);
        Response response =
                service.put("/rest/zapi/latest/execution/" + executionId + "/execute", new Request(execution));
        return response.getCode() == 200;
    }

    public boolean markExecutionPassed(int executionId) {
        return executeTestInCycle(executionId, 1);
    }

    public boolean markExecutionFailed(int executionId) {
        return executeTestInCycle(executionId, 2);
    }

    public boolean markExecutionWIP(int executionId) {
        return executeTestInCycle(executionId, 3);
    }

    public boolean markExecutionBlocked(int executionId) {
        return executeTestInCycle(executionId, 4);
    }

    public boolean markExecutionUnexecuted(int executionId) {
        return executeTestInCycle(executionId, -1);
    }

    public boolean uploadExecutionResults(int executionId, File results) {
        //set our upload xsrf headers
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Atlassian-Token", "no-check");
        service.addHeaders(headers);
        //make our call and response
        Map<String, String> params = new HashMap<>();
        params.put("entityId", String.valueOf(executionId));
        params.put("entityType", "EXECUTION");
        Response response = service.post("/rest/zapi/latest/attachment", new Request(params), results);
        //reset our headers
        service.resetHeaders();
        return response.getCode() == 200;
    }
}