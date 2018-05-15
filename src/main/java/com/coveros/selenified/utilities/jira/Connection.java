package com.coveros.selenified.utilities.jira;

import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Response;

import java.lang.reflect.Method;

public class Connection {

    Annotation annotation;
    HTTP jira;

    public Connection(Method method) {
        this.annotation = new Annotation(method);
        this.jira = getJiraHttp();
    }

    public HTTP getJiraHttp() {
        String link = Property.getJiraProperty("link");
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

    public int getProjectId() {
        String project = annotation.getProject();
        Response response = jira.get("/rest/api/2/project/" + project);
        if (response.getObjectData().has("id")) {
            return response.getObjectData().get("id").getAsInt();
        }
        return 0;
    }

    public int getIssueId(String key) {
        Response response = jira.get("/rest/api/2/issue/" + key);
        if (response.getObjectData().has("id")) {
            return response.getObjectData().get("id").getAsInt();
        }
        return 0;
    }
}