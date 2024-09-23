package com.example.Upload_File_To_DB.message;

/* ResponseMessage for notification/information message */
public class ResponeMessage {
	
	private String message;
	
	public ResponeMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
