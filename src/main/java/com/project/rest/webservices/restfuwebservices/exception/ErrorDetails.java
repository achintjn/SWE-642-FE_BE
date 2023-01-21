package com.project.rest.webservices.restfuwebservices.exception;

import java.time.LocalDateTime;

public class ErrorDetails{

	private LocalDateTime timeStamp;
	private String message;
	private String details;
	public ErrorDetails(LocalDateTime localDateTime, String message, String details) {
		super();
		this.timeStamp = localDateTime;
		this.message = message;
		this.details = details;
	}
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
