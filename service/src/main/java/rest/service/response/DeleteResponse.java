package rest.service.response;

public class DeleteResponse {

	private boolean isDeleted;
	
	public DeleteResponse(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isDeleted() {
		return isDeleted;
	}
}
