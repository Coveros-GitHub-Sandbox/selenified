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

import com.coveros.selenified.utilities.Jira;

import java.lang.reflect.Method;

public class Annotation {

    Method method;

    public Annotation(Method method) {
        this.method = method;
    }

    public Boolean isAnnotationPresent() {
        if (method.getDeclaringClass().isAnnotationPresent(Jira.class)) {
            return true;
        }
        return method.isAnnotationPresent(Jira.class);
    }

    public Boolean isProjectPresent() {
        if (!isAnnotationPresent()) {
            return false;
        }
        if (method.getDeclaringClass().isAnnotationPresent(Jira.class) &&
                !"".equals(method.getDeclaringClass().getAnnotation(Jira.class).project())) {
            return true;
        }
        return !"".equals(method.getAnnotation(Jira.class).project());
    }

    public Boolean isIssuePresent() {
        if (!isAnnotationPresent()) {
            return false;
        }
        if (method.getDeclaringClass().isAnnotationPresent(Jira.class) &&
                !"".equals(method.getDeclaringClass().getAnnotation(Jira.class).issue())) {
            return true;
        }
        return !"".equals(method.getAnnotation(Jira.class).issue());
    }

    public String getProject() {
        if (!isProjectPresent()) {
            return null;
        }
        if (method.isAnnotationPresent(Jira.class) && !"".equals(method.getAnnotation(Jira.class).project())) {
            return method.getAnnotation(Jira.class).project();
        }
        return method.getDeclaringClass().getAnnotation(Jira.class).project();
    }

    public String getIssue() {
        if (!isIssuePresent()) {
            return null;
        }
        if (method.isAnnotationPresent(Jira.class) && !"".equals(method.getAnnotation(Jira.class).issue())) {
            return method.getAnnotation(Jira.class).issue();
        }
        return method.getDeclaringClass().getAnnotation(Jira.class).issue();
    }
}