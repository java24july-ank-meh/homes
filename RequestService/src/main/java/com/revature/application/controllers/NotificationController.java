package com.revature.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.models.Notification;
import com.revature.application.services.NotificationService;

@RestController
public class NotificationController {

	@Autowired
	NotificationService notificationService;
	
	@PostMapping("notifications/create")
	public ResponseEntity<Object> createNotification(@RequestBody Notification notification) {
		notificationService.createNotification(notification);
		return ResponseEntity.ok().build();
	}
}
