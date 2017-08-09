package com.coveros.selenified.services;

import org.testng.log4testng.Logger;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Assert.Result;
import com.coveros.selenified.tools.General;

public class Call {
	private static final Logger log = Logger.getLogger(General.class);

	// this will be the name of the file we write all commands out to
	private OutputFile file;

	// what services will we be interacting with
	private HTTP http;
	
	// constants
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String PUT = "PUT";
	private static final String PATCH = "PATCH";
	private static final String DELETE = "DELETE";

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
	public Response get(String endpoint) {
		return call(GET, endpoint, null, true);
	}

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
		return call(GET, endpoint, params, true);
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
		return call(GET, endpoint, params, print);
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
		return call(POST, endpoint, params, true);
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
		return call(POST, endpoint, params, print);
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
		return call(PUT, endpoint, params, true);
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
		return call(PUT, endpoint, params, print);
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
		return call(PATCH, endpoint, params, true);
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
		return call(PATCH, endpoint, params, print);
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
		return call(DELETE, endpoint, params, true);
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
		return call(DELETE, endpoint, params, print);
	}

	/**
	 * performs an http call
	 * 
	 * @param call
	 *            - what http method call is being made. should be in all caps
	 * @param endpoint
	 *            - the endpoint of the service under test
	 * @param params
	 *            - the parameters to be passed to the endpoint for the service
	 *            call
	 * @param print
	 *            - do we want to print this statement out to the output file
	 * @return Response: the response provided from the http call
	 */
	private Response call(String call, String endpoint, Request params, boolean print) {
		StringBuilder action = new StringBuilder();
		action.append("Making <i>" + call + "</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>");
		action.append(appendCredentials());
		action.append(file.outputRequestProperties(params));
		String expected = "<i>" + call + "</i> call was made successfully";
		Response response = new Response(file);
		try {
			switch (call) {
			case GET:
				response = http.get(endpoint, params);
				break;
			case POST:
				response = http.post(endpoint, params);
				break;
			case PUT:
				response = http.put(endpoint, params);
				break;
			case PATCH:
				response = http.patch(endpoint, params);
				break;
			case DELETE:
				response = http.delete(endpoint, params);
				break;
			default:
				log.error("Unknown method call named");
			}
			response.setOutputFile(file);
			if (print) {
				file.recordAction(action.toString(), expected, expected, Result.SUCCESS);
			}
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action.toString(), expected, "<i>" + call + "</i> call failed. " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			response = new Response(0);
			response.setOutputFile(file);
			return response;
		}
		return response;
	}

	private String appendCredentials() {
		StringBuilder credentials = new StringBuilder();
		if (http.useCredentials()) {
			credentials.append("<br/> with credentials: ");
			credentials.append("<div><i>");
			credentials.append("Username: " + http.getUser());
			credentials.append("</div><div>");
			credentials.append("Password: " + http.getPass());
			credentials.append("</i></div>");
		}
		return credentials.toString();
	}
}