package com.coveros.selenified.services;

import org.testng.log4testng.Logger;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.Assert.Result;
import com.coveros.selenified.tools.General;

public class Call {
    private static final Logger log = Logger.getLogger(General.class);

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what services will we be interacting with
    private HTTP http;

    public Call(HTTP http, OutputFile file) {
        this.http = http;
        this.file = file;
    }

    ///////////////////////////////////////////////////////////////////
    // some simple actions for our services
    ///////////////////////////////////////////////////////////////////

    /**
     * performs a get http call and writes that out to the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response get(String endpoint, Request params) {
        return get(endpoint, params, true);
    }

    /**
     * performs a get http call
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @param print
     *            - do we want to print this statement out to the output file
     * @return Response: the response provided from the http call
     */
    public Response get(String endpoint, Request params, boolean print) {
        String action = "Making <i>GET</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>";
        action += file.outputRequestProperties(params);
        String expected = "<i>GET</i> call was made successfully";
        Response response;
        try {
            response = http.get(endpoint, params);
            if (print) {
                file.recordAction(action, expected, expected, Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "<i>GET</i> call failed. " + e.getMessage(), Result.FAILURE);
            file.addError();
            return null;
        }
        return response;
    }

    /**
     * performs a post http call and writes that out to the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response post(String endpoint, Request params) {
        return post(endpoint, params, true);
    }

    /**
     * performs a post http call
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @param print
     *            - do we want to print this statement out to the output file
     * @return Response: the response provided from the http call
     */
    public Response post(String endpoint, Request params, boolean print) {
        String action = "Making <i>POST</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>";
        action += file.outputRequestProperties(params);
        String expected = "<i>POST</i> call was made successfully";
        Response response;
        try {
            response = http.post(endpoint, params);
            if (print) {
                file.recordAction(action, expected, expected, Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "<i>POST</i> call failed. " + e.getMessage(), Result.FAILURE);
            file.addError();
            return null;
        }
        return response;
    }

    /**
     * performs a put http call and writes that out to the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response put(String endpoint, Request params) {
        return put(endpoint, params, true);
    }

    /**
     * performs a put http call
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @param print
     *            - do we want to print this statement out to the output file
     * @return Response: the response provided from the http call
     */
    public Response put(String endpoint, Request params, boolean print) {
        String action = "Making <i>PUT</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>";
        action += file.outputRequestProperties(params);
        String expected = "<i>PUT</i> call was made successfully";
        Response response;
        try {
            response = http.put(endpoint, params);
            if (print) {
                file.recordAction(action, expected, expected, Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "<i>PUT</i> call failed. " + e.getMessage(), Result.FAILURE);
            file.addError();
            return null;
        }
        return response;
    }

    /**
     * performs a patch http call and writes that out to the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response patch(String endpoint, Request params) {
        return patch(endpoint, params, true);
    }

    /**
     * performs a patch http call
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @param print
     *            - do we want to print this statement out to the output file
     * @return Response: the response provided from the http call
     */
    public Response patch(String endpoint, Request params, boolean print) {
        String action = "Making <i>PATCH</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>";
        action += file.outputRequestProperties(params);
        String expected = "<i>PATCH</i> call was made successfully";
        Response response;
        try {
            response = http.patch(endpoint, params);
            if (print) {
                file.recordAction(action, expected, expected, Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "<i>PATCH</i> call failed. " + e.getMessage(), Result.FAILURE);
            file.addError();
            return null;
        }
        return response;
    }

    /**
     * performs a delete http call and writes that out to the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response delete(String endpoint, Request params) {
        return delete(endpoint, params, true);
    }

    /**
     * performs a delete http call
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @param print
     *            - do we want to print this statement out to the output file
     * @return Response: the response provided from the http call
     */
    public Response delete(String endpoint, Request params, boolean print) {
        StringBuilder action = new StringBuilder();
        action.append("Making <i>DELETE</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>");
        if (http.useCredentials()) {
            action.append("<br/> with credentials: ");
            action.append("<div><i>");
            action.append("Username: " + http.getUser());
            action.append("</div><div>");
            action.append("Password: " + http.getPass());
            action.append("</i></div>");
        }
        action.append(file.outputRequestProperties(params));
        String expected = "<i>DELETE</i> call was made successfully";
        Response response;
        try {
            response = http.delete(endpoint, params);
            if (print) {
                file.recordAction(action.toString(), expected, expected, Result.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action.toString(), expected, "<i>DELETE</i> call failed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return null;
        }
        return response;
    }
}
