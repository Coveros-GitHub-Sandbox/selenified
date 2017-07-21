package com.coveros.selenified.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HTTP {
	public enum Protocol {
		HTTP, HTTPS;
	}

	private Protocol protocol;
	private String domain;
	private String username = "";
	private String password = "";
	private String serviceBase = "";

	public HTTP(Protocol protocol, String domain) {
		this.protocol = protocol;
		this.domain = domain;
	}

	public HTTP(Protocol protocol, String domain, String username, String password, String serviceBase) {
		this.protocol = protocol;
		this.domain = domain;
		this.username = username;
		this.password = password;
		this.serviceBase = serviceBase;
	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServiceBase() {
		return serviceBase;
	}

	public void setServiceBase(String serviceBase) {
		this.serviceBase = serviceBase;
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
		BufferedReader rd = null;
		try {
			URL url = new URL(this.protocol.toString().toLowerCase() + "://" + this.domain + this.serviceBase + service
					+ params.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-length", "0");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			if (!"".equals(this.username) && !"".equals(this.password)) {
				String userPassword = username + ":" + password;
				String encoding = new String(Base64.encodeBase64(userPassword.getBytes()));
				connection.setRequestProperty("Authorization", "Basic " + encoding);
			}
			connection.connect();
			int status = connection.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				break;
			default:
				rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				break;
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(sb.toString());
			return new Response(status, json, sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public Response post(String service, Request request) {
		HttpURLConnection connection = null;
		BufferedReader rd = null;
		try {
			URL url = new URL(
					this.protocol.toString().toLowerCase() + "://" + this.domain + this.serviceBase + service);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			if (!"".equals(this.username) && !"".equals(this.password)) {
				String userPassword = username + ":" + password;
				String encoding = new String(Base64.encodeBase64(userPassword.getBytes()));
				connection.setRequestProperty("Authorization", "Basic " + encoding);
			}

			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(request.getData().toString());
			wr.flush();

			int status = connection.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				break;
			default:
				rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				break;
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(sb.toString());
			return new Response(status, json, sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public Response put(String service, Request request) {
		HttpURLConnection connection = null;
		BufferedReader rd = null;
		try {
			URL url = new URL(
					this.protocol.toString().toLowerCase() + "://" + this.domain + this.serviceBase + service);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			if (!"".equals(this.username) && !"".equals(this.password)) {
				String userPassword = username + ":" + password;
				String encoding = new String(Base64.encodeBase64(userPassword.getBytes()));
				connection.setRequestProperty("Authorization", "Basic " + encoding);
			}

			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(request.getData().toString());
			wr.flush();

			int status = connection.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				break;
			default:
				rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				break;
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(sb.toString());
			return new Response(status, json, sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
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
		BufferedReader rd = null;
		try {
			URL url = new URL(
					this.protocol.toString().toLowerCase() + "://" + this.domain + this.serviceBase + service);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setAllowUserInteraction(false);
			if (!"".equals(this.username) && !"".equals(this.password)) {
				String userPassword = username + ":" + password;
				String encoding = new String(Base64.encodeBase64(userPassword.getBytes()));
				connection.setRequestProperty("Authorization", "Basic " + encoding);
			}
			
			if (request != null) {
				OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
				wr.write(request.getData().toString());
				wr.flush();
			} else {
				connection.connect();
			}

			int status = connection.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				break;
			default:
				rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				break;
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(sb.toString());
			return new Response(status, json, sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
