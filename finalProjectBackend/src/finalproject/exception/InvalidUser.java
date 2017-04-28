package finalproject.exception;

public class InvalidUser extends Exception implements MyException{

	private static final long serialVersionUID = 1L;
	private static final String  message = "Invalid user";
	
	public InvalidUser(){
		super(message);
	}

	public String getMessage(){
		return message;
	}
}
