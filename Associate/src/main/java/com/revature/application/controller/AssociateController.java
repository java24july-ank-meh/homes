package com.revature.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.model.Associate;
import com.revature.application.repository.AssociateRepository;

@RestController
public class AssociateController {

	@Autowired
	AssociateRepository associateRepository;

	@GetMapping("Associates")
	public ResponseEntity<Object> findAll() {
		return ResponseEntity.ok(associateRepository.findAll());
	}

	@GetMapping("Associates/{id}")
	public Associate findByContactId(@PathVariable("id") int id) {
		return associateRepository.findByAssociateId(id);
	}

	@GetMapping("Associates/{id}/unit")
	public ResponseEntity<Object> findByUnitId(@PathVariable("id") int id) {
		return ResponseEntity.ok(associateRepository.findByUnitId(id));
	}

	@PostMapping("Associates/create")
	public ResponseEntity<Object> createAssociate(Associate associate) {

		return ResponseEntity.ok(associateRepository.saveAndFlush(associate));
	}

	@PutMapping("Associates/update")
	public ResponseEntity<Object> updateResident(Associate associate) {
		return ResponseEntity.ok(associateRepository.saveAndFlush(associate));
	}

	@GetMapping("Associates/{email:.+}/email")
	public Associate findByEmail(String email) {
		return associateRepository.findByEmail(email);
	}

	@DeleteMapping("Associates/{id}")
	public ResponseEntity<Object> removeResidentFromApartment(@PathVariable("id") int id) {
		associateRepository.deleteByAssociateId(id);
		return ResponseEntity.ok().build();

	}
}
