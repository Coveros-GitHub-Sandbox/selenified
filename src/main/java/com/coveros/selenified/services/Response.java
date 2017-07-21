package com.coveros.selenified.services;

import com.google.gson.JsonObject;

public class Response {
	private int code;
	private JsonObject data;
	private String message;
	
	public Response(int code, JsonObject data, String message) {
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
