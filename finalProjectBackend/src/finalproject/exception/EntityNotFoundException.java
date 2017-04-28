package finalproject.exception;

public class EntityNotFoundException extends Exception implements MyException {

	private static final long serialVersionUID = 1L;
	private static final String  message = "Entity not found: ";

	public EntityNotFoundException(String entityName) {
		super(message+entityName);
	}

	@Override
	public String getMessage() {
		return message;
	}

}
