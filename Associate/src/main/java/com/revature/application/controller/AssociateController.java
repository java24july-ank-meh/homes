package com.revature.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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

	@GetMapping("associates")
	public ResponseEntity<Object> findAll() {
		return ResponseEntity.ok(associateService.listAll());
	}

	@GetMapping("associates/{id}")
	public ResponseEntity<Object> findByContactId(@PathVariable("id") Long id) {
		return ResponseEntity.ok(associateService.findByAssociateId(id));
	}

	@GetMapping("associates/{id}/unit")
	public ResponseEntity<Object> findByUnitId(@PathVariable("id") Long id) {
		//return ResponseEntity.ok(associateService.findByUnitId(id));
		List<Associate> people = associateService.findByUnitId(id);
		
		System.out.println(people);
		
		
		//It will never be null because we never know if the unit does not exist. We only know if none of the Associates
		//are assigned to the particular unit with the ID we are using
		/*if(people == null) {
			//if the unit does not exist in the database, null is returned
			//return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
			return new ResponseEntity<Object>(null, HttpStatus.I_AM_A_TEAPOT);
		}*/
		
		if(people.size() == 0) {
			//because there is no one in this unit
			return new ResponseEntity<Object>(people, HttpStatus.NO_CONTENT);
		}
		
		return ResponseEntity.ok(people);
	}

	/**
	 * Gets you an associate from the database
	 * @param associate the associate you want
	 * @return the actual associate from the database
	 */
	@PostMapping("associates/createOrUpdate")
	public ResponseEntity<Object> createAssociate(@RequestBody Associate resident, HttpSession session) {
		/* possible responses: 
		 * OK - update worked
		 *X CREATED - the associate was created (can't be known at this time as there is no way to distinguish between
		 * a save or an update)
		 * CONFLICT - if they tried to create an associate with information that already exists, like with an email
		 * already used for another account
		 * (known if an exception is thrown)*/
		try {
			Associate a = associateService.saveOrUpdate(associate);
			return ResponseEntity.ok(a);
		} catch (DataAccessException e){
			//TODO: Test to make sure this works properly.
			e.printStackTrace(); //for testing purposes. Comment out later.
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}

	@GetMapping("associates/{email:.+}/email")
	public Associate findByEmail(@PathVariable("email") String email) {
		return associateService.findByEmail(email);
	}

	@DeleteMapping("associates/{id}")
	public ResponseEntity<Object> removeResidentFromApartment(@PathVariable("id") Long id) {
		associateService.delete(id);
		return ResponseEntity.ok().build();

	}
}
