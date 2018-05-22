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
import java.util.Set;

public class Zephyr {

    private static final String PROJECT = "projectId";
    private static final String VERSION = "versionId";

    private final Jira jira;
    private final HTTP service;

    private int cycleId = 0;

    public Zephyr(Method method) {
        this.jira = new Jira(method);
        this.service = jira.getHTTP();
    }

    public void createCycle() {
        //TODO pull these from cmd properties
        String cycleName = "Sample";
        String cycleDescription = "";
        String build = "";
        String environment = "";

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
        cycle.addProperty(VERSION, -1);
        Response response = service.post("/rest/zapi/latest/cycle", new Request(cycle));
        if (response.getObjectData().has("id")) {
            cycleId = response.getObjectData().get("id").getAsInt();
        }
        cycleId = 0;
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
        return response.getCode() == 200;
    }

    public int createExecution(int testId) {
        JsonObject execution = new JsonObject();
        execution.addProperty(PROJECT, jira.getProjectId());
        execution.addProperty("cycleId", cycleId);
        execution.addProperty("issueId", testId);
        execution.addProperty(VERSION, -1);
        Response response = service.post("/rest/zapi/latest/execution", new Request(execution));
        Set<Map.Entry<String, JsonElement>> entries = response.getObjectData().entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            JsonObject newExecution = entry.getValue().getAsJsonObject();
            return newExecution.get("id").getAsInt();
        }
        return -1;
    }

    private void executeTestInCycle(int executionId, int status) {
        JsonObject execution = new JsonObject();
        execution.addProperty("status", status);
        service.put("/rest/zapi/latest/execution/" + executionId + "/execute", new Request(execution));
    }

    public void markExecutionPassed(int executionId) {
        executeTestInCycle(executionId, 1);
    }

    public void markExecutionFailed(int executionId) {
        executeTestInCycle(executionId, 2);
    }

    public void markExecutionWIP(int executionId) {
        executeTestInCycle(executionId, 3);
    }

    public void markExecutionBlocked(int executionId) {
        executeTestInCycle(executionId, 4);
    }

    public void markExecutionUnexecuted(int executionId) {
        executeTestInCycle(executionId, -1);
    }

    public void uploadExecutionResults(int executionId, File results) {
        //set our upload xsrf headers
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Atlassian-Token", "no-check");
        service.addHeaders(headers);
        //make our call and response
        Map<String, String> params = new HashMap<>();
        params.put("entityId", String.valueOf(executionId));
        params.put("entityType", "EXECUTION");
        service.post("/rest/zapi/latest/attachment", new Request(params), results);
        //reset our headers
        service.resetHeaders();
    }
}