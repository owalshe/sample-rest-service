package rest.service.response;

import rest.service.model.Person;

public class UpdateResponse {

	private Person resource;
	private boolean isUpdated;
	
	public UpdateResponse(Person resource, boolean isUpdated) {
		this.resource = resource;
		this.isUpdated = isUpdated;
	}

	public Person getResource() {
		return resource;
	}

	public boolean isUpdated() {
		return isUpdated;
	}
}
