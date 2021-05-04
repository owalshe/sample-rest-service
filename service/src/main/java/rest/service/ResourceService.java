package rest.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import rest.service.exceptions.ResourceAlreadyExistsException;
import rest.service.exceptions.ResourceNotFoundException;
import rest.service.model.Address;
import rest.service.model.Person;
import rest.service.repository.AddressRepository;
import rest.service.repository.PersonRepository;
import rest.service.response.DeleteResponse;
import rest.service.response.UpdateResponse;

@Service
public class ResourceService {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);
	
	@Autowired
	private PersonRepository personRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	public Person getResource(Long id) throws ResourceNotFoundException {
		Optional<Person> optional = this.personRepo.findById(id);
		if(optional.isPresent()) {
			Person dataResource = optional.get();
			LOG.info(String.format("found resource %s", dataResource.toString()));
			return dataResource;
		}
		throw new ResourceNotFoundException(String.format("Unable to find resource for id %d", id));
	}
	
	public List<Person> getAllResources() {
		return this.personRepo.findAll();
	}
	
	public List<Address> getAllAddresses() {
		return this.addressRepo.findAll();
	}
	
	public Person newResource(Person person) throws ResourceAlreadyExistsException {
		Example<Person> example = Example.of(person);
	    Optional<Person> optional = personRepo.findOne(example);
	    if(optional.isPresent()) {
			throw new ResourceAlreadyExistsException(String.format("Resource already exists for %s", person));
		}
	    Person newResource = this.personRepo.save(person);
	    LOG.info(String.format("created resource %s", newResource.toString()));
		return newResource;
	}
	
	public Address newResource(Long id, Address address) throws ResourceNotFoundException {
		Optional<Person> optional = this.personRepo.findById(id);
	    if(optional.isPresent()) {
	    	Person existingResource = optional.get();		
	    	existingResource.addAddress(address);
	    	Person updatedResource = this.personRepo.save(existingResource);
	    	List<Address> addresses = updatedResource.getAddresses();
	    	return addresses.get(addresses.size()-1);
	    }
	    throw new ResourceNotFoundException(String.format("Unable to find person for id %d", id));
	}
	
	public UpdateResponse updateResource(Long id, Person dataResource) {
		Optional<Person> optional = this.personRepo.findById(id);
		if(optional.isPresent()) {
			Person existingResource = optional.get();
			existingResource.setFirstName(dataResource.getFirstName());
			existingResource.setSecondName(dataResource.getSecondName());
			existingResource.clearAddress();
			for(Address address : dataResource.getAddresses()) {
				existingResource.addAddress(address);
			}
			Person updatedResource = this.personRepo.save(existingResource);
			LOG.info(String.format("updated person %s", updatedResource.toString()));
			return new UpdateResponse(updatedResource, true);
		}
		Person newResource = this.personRepo.save(dataResource);
		LOG.info(String.format("created resource %s", newResource.toString()));
		return new UpdateResponse(newResource, false);
	}
	
	public Address updateResource(Long id, Address address) throws ResourceNotFoundException {
		Optional<Address> optional = this.addressRepo.findById(id);
		if(optional.isPresent()) {
			Address existingResource = optional.get();
			existingResource.setStreet(address.getStreet());
			existingResource.setCity(address.getCity());
			existingResource.setState(address.getState());
			existingResource.setPostalCode(address.getPostalCode());
			Address updatedResource = this.addressRepo.save(existingResource);
			LOG.info(String.format("updated address %s", updatedResource.toString()));
			return updatedResource;
		}
	    throw new ResourceNotFoundException(String.format("Unable to find address for id %d", id));
	}

	public DeleteResponse deleteResource(Long id) {
		boolean exists = this.personRepo.existsById(id);
		if(exists) {
			this.personRepo.deleteById(id);
			LOG.info(String.format("deleted resource with id %d", id));
			return new DeleteResponse(true);
		}
		LOG.info(String.format("unable to find resource by id %d", id));
		return new DeleteResponse(false);
	}

	public DeleteResponse deleteAddress(Long id) {
		boolean exists = this.addressRepo.existsById(id);
		if(exists) {
			this.addressRepo.deleteById(id);
			LOG.info(String.format("deleted address with id %d", id));
			return new DeleteResponse(true);
		}
		LOG.info(String.format("unable to find address by id %d", id));
		return new DeleteResponse(false);
	}

	public Long countPersons() {
		return Long.valueOf(this.personRepo.count());
	}
	
	public Long countAddresses() {
		return Long.valueOf(this.addressRepo.count());
	}
}
