package com.revature.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.application.model.Office;

public interface OfficeRepo extends JpaRepository<Office, Integer> {

}
