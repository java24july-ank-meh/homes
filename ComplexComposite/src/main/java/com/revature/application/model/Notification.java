package com.revature.application.model;

public class Notification {
	private String message;

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Notification(String message) {
		super();
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
		return "Notification [message=" + message + "]";
	}
	
}
