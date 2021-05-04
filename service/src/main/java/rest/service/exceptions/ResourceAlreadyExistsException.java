package rest.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT) 
public class ResourceAlreadyExistsException extends Exception {

	public ResourceAlreadyExistsException(String msg) {
		super(msg);
	}
}
