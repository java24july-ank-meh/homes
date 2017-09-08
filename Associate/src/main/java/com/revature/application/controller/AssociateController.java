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
import com.revature.application.services.AssociateService;

@RestController
public class AssociateController {

	private AssociateService associateService;
	
	@Autowired
	public void setAssociateService(AssociateService associateService) {
		this.associateService = associateService;
	}

	@GetMapping("Associates")
	public ResponseEntity<Object> findAll() {
		return ResponseEntity.ok(associateService.listAll());
	}

	@GetMapping("Associates/{id}")
	public Associate findByContactId(@PathVariable("id") Long id) {
		return associateService.findByAssociateId(id);
	}

	@GetMapping("Associates/{id}/unit")
	public ResponseEntity<Object> findByUnitId(@PathVariable("id") Long id) {
		return ResponseEntity.ok(associateService.findByUnitId(id));
	}

	@PostMapping("Associates/createOrUpdate")
	public ResponseEntity<Object> createAssociate(Associate associate) {

		return ResponseEntity.ok(associateService.saveOrUpdate(associate));
	}

	@GetMapping("Associates/{email:.+}/email")
	public Associate findByEmail(String email) {
		return associateService.findByEmail(email);
	}

	@DeleteMapping("Associates/{id}")
	public ResponseEntity<Object> removeResidentFromApartment(@PathVariable("id") Long id) {
		associateService.delete(id);
		return ResponseEntity.ok().build();

	}
}
