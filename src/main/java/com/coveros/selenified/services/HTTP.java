package com.coveros.selenified.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.testng.log4testng.Logger;

import com.coveros.selenified.tools.General;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HTTP {

	private static final Logger log = Logger.getLogger(General.class);

	private String serviceBaseUrl;
	private String user = "";
	private String pass = "";

	public HTTP(String serviceBaseUrl) {
		this.serviceBaseUrl = serviceBaseUrl;
	}

	public HTTP(String serviceBaseUrl, String user, String pass) {
		this.serviceBaseUrl = serviceBaseUrl;
		this.user = user;
		this.pass = pass;
	}

	public String getServiceBaseUrl() {
		return serviceBaseUrl;
	}

	public void setServiceBaseUrl(String serviceBaseUrl) {
		this.serviceBaseUrl = serviceBaseUrl;
	}

	public String getuser() {
		return user;
	}

	public void setuser(String user) {
		this.user = user;
	}

	public String getpass() {
		return pass;
	}

	public void setpass(String pass) {
		this.pass = pass;
	}

	public Response get(String service) {
		return get(service, null);
	}

	public Response get(String service, Request request) {
		StringBuilder params = new StringBuilder();
		if (request != null) {
			params.append("?");
			for (String key : request.getParams().keySet()) {
				params.append(key);
				params.append("=");
				params.append(request.getParams().get(key));
				params.append("&");
			}
		}
		HttpURLConnection connection = null;
		try {
			URL url = new URL(this.serviceBaseUrl + service
					+ params.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection = setupConnection(connection);
			connection.connect();

			return getResponse(connection);
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		return null;
	}

	public Response post(String service, Request request) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(
					this.serviceBaseUrl + service);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection = setupConnection(connection);
			connection.connect();
			writeDataRequest(connection, request);
			return getResponse(connection);
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		return null;
	}

	public Response put(String service, Request request) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(
					this.serviceBaseUrl + service);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("PUT");
			connection = setupConnection(connection);
			connection.connect();
			writeDataRequest(connection, request);
			return getResponse(connection);
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		return null;
	}

	public Response delete(String service) {
		return delete(service, null);
	}

	public Response delete(String service, Request request) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(
					this.serviceBaseUrl + service);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			connection = setupConnection(connection);
			connection.connect();
			if (request != null) {
				writeDataRequest(connection, request);
			}
			return getResponse(connection);
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		return null;
	}

	private HttpURLConnection setupConnection(HttpURLConnection connection) {
		connection.setRequestProperty("Content-length", "0");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setAllowUserInteraction(false);
		if (!"".equals(this.user) && !"".equals(this.pass)) {
			String userpass = user + ":" + pass;
			String encoding = new String(Base64.encodeBase64(userpass.getBytes()));
			connection.setRequestProperty("Authorization", "Basic " + encoding);
		}
		return connection;
	}
	
	private void writeDataRequest(HttpURLConnection connection, Request request) {
		try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());) {
			wr.write(request.getData().toString());
			wr.flush();
		} catch (IOException e) {
			log.error(e);
		}
	}

	private Response getResponse(HttpURLConnection connection) {
		int status;
		try {
			status = connection.getResponseCode();
		} catch (IOException e) {
			log.error(e);
			return null;
		}
		JsonObject json = new JsonObject();
		String data = "";
		// grab the output from our connection. If it's a 200 or 201, it'll be
		// from the input stream. If it's an error, it'll be from the error
		// stream
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e) {
			log.warn(e);
			rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		} finally {
			StringBuilder sb = new StringBuilder();
			String line;
			if (rd != null) {
				try {
					while ((line = rd.readLine()) != null) {
						sb.append(line);
					}
				} catch (IOException e) {
					log.error(e);
				}
				try {
					rd.close();
				} catch (IOException e) {
					log.error(e);
				}
				data = sb.toString();
				JsonParser parser = new JsonParser();
				json = (JsonObject) parser.parse(data);
			}
		}
		return new Response(status, json, data);
	}
}