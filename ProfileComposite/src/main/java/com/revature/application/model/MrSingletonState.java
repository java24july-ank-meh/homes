package com.revature.application.model;

public class MrSingletonState {
	private static String state;
	private static String date;
	

	public MrSingletonState() {
		if(state == null) {
			state = "";
		}
	}
	
	public MrSingletonState(String state) {
		this.state = state;
	}
	
	public static String getState() {
		return state;
	}

	public static void setState(String state) {
		MrSingletonState.state = state;
	}
	
	public void wipeState() {
		state = null;
	}
	
	public static String getDate() {
		return date;
	}

	public static void setDate(String date) {
		MrSingletonState.date = date;
	}
}
