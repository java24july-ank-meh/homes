package com.revature.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	
}
