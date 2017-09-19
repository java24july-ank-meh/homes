package com.revature.application.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "NOTIFICATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "notificationId")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long notificationId;
	private String message;

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Notification(long notificationId, String message) {
		super();
		this.notificationId = notificationId;
		this.message = message;
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", message=" + message + "]";
	}

}