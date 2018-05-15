package com.coveros.selenified.utilities.jira;

import com.coveros.selenified.utilities.jira.Jira;

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
        if (method.getDeclaringClass().isAnnotationPresent(Jira.class)) {
            if (!"".equals(method.getDeclaringClass().getAnnotation(Jira.class).project())) {
                return true;
            }
        }
        return !"".equals(method.getAnnotation(Jira.class).project());
    }

    public Boolean isIssuePresent() {
        if (!isAnnotationPresent()) {
            return false;
        }
        if (method.getDeclaringClass().isAnnotationPresent(Jira.class)) {
            if (!"".equals(method.getDeclaringClass().getAnnotation(Jira.class).issue())) {
                return true;
            }
        }
        return !"".equals(method.getAnnotation(Jira.class).issue());
    }

    public String getProject() {
        if (!isProjectPresent()) {
            return null;
        }
        if (method.isAnnotationPresent(Jira.class)) {
            if (!"".equals(method.getAnnotation(Jira.class).project())) {
                return method.getAnnotation(Jira.class).project();
            }
        }
        return method.getDeclaringClass().getAnnotation(Jira.class).project();
    }

    public String getIssue() {
        if (!isIssuePresent()) {
            return null;
        }
        if (method.isAnnotationPresent(Jira.class)) {
            if (!"".equals(method.getAnnotation(Jira.class).issue())) {
                return method.getAnnotation(Jira.class).issue();
            }
        }
        return method.getDeclaringClass().getAnnotation(Jira.class).issue();
    }
}