package com.project.rest.webservices.restfuwebservices.first;

public class FirstBean {

	private String message;

	public FirstBean(String message) {
		// TODO Auto-generated constructor stub
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "FirstBean [message=" + message + "]";
	}
	
	

}
