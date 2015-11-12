package com.db;

public class DBResponse {

	private String[] responseRecords;

	public String[] getResponseRecords() {
		return responseRecords;
	}

	public void setResponseRecords(String[] responseRecords) {
		this.responseRecords = responseRecords;
	}

	public DBResponse(String[] responseRecords) {
		super();
		this.responseRecords = responseRecords;
	}
	
	
}
