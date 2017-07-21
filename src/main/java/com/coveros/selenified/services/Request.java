package com.coveros.selenified.services;

import java.util.HashMap;

import com.google.gson.JsonObject;

public class Request {
	private JsonObject data = new JsonObject();
	private HashMap<String, String> params = new HashMap<>();
	
	public Request(JsonObject data) {
		this.data = data;
	}
	
	public Request(HashMap<String, String> params) {
		this.params = params;
	}

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}
}
