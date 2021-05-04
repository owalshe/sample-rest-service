package rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rest.service.exceptions.ResourceAlreadyExistsException;
import rest.service.exceptions.ResourceNotFoundException;
import rest.service.model.Address;
import rest.service.model.Person;
import rest.service.response.DeleteResponse;
import rest.service.response.UpdateResponse;

@RestController
public class ResourceController {

	@Autowired
	private ResourceService service;
	
	@GetMapping(value = "/person", produces = "application/json")
	public @ResponseBody ResponseEntity<List<Person>> getAll() {
		return new ResponseEntity<>(this.service.getAllResources(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/address", produces = "application/json")
	public @ResponseBody ResponseEntity<List<Address>> getAllAddresses() {
		return new ResponseEntity<>(this.service.getAllAddresses(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/person/{id}", produces = "application/json")
	public @ResponseBody ResponseEntity<Person> get(@PathVariable Long id) throws ResourceNotFoundException {
		return new ResponseEntity<>(this.service.getResource(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/person/count", produces = "application/json")
	public @ResponseBody ResponseEntity<Long> count() {
		Long count = this.service.countPersons();
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
	
	@GetMapping(value = "/address/count", produces = "application/json")
	public @ResponseBody ResponseEntity<Long> countAddress() {
		Long count = this.service.countAddresses();
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
	
	@PostMapping(value = "/person", produces = "application/json")
	public @ResponseBody ResponseEntity<Person> create(@RequestBody Person dataResource) throws ResourceAlreadyExistsException {
		return new ResponseEntity<>(this.service.newResource(dataResource), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/address/{id}", produces = "application/json")
	public @ResponseBody ResponseEntity<Address> createAddress(@PathVariable Long id, @RequestBody Address address) throws ResourceNotFoundException {
		return new ResponseEntity<>(this.service.newResource(id, address), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/person/{id}", produces = "application/json")
	public @ResponseBody ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person dataResource) {
		UpdateResponse response = this.service.updateResource(id, dataResource);
		if(response.isUpdated()) {
			return new ResponseEntity<>(response.getResource(), HttpStatus.OK);
		}
		return new ResponseEntity<>(response.getResource(), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/address/{id}", produces = "application/json")
	public @ResponseBody ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) throws ResourceNotFoundException {
		Address updatedAddress = this.service.updateResource(id, address);
		return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/person/{id}", produces = "application/json")
	public @ResponseBody ResponseEntity<String> delete(@PathVariable Long id) {
		DeleteResponse deleteReponse = this.service.deleteResource(id);
		if(deleteReponse.isDeleted()) {
			return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(value = "/address/{id}", produces = "application/json")
	public @ResponseBody ResponseEntity<String> deleteAddress(@PathVariable Long id) {
		DeleteResponse deleteReponse = this.service.deleteAddress(id);
		if(deleteReponse.isDeleted()) {
			return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
}
