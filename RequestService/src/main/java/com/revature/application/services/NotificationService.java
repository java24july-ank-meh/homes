package com.revature.application.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.application.models.Notification;
import com.revature.application.repository.NotificationRepository;

public class NotificationService {

	@Autowired
	NotificationRepository notificationRepository;
	
	public void createNotification(Notification notification) {
		notificationRepository.saveAndFlush(notification);
	}
}
