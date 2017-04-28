package finalproject.exception;

public class InvalidInputData extends Exception implements MyException{

	private static final long serialVersionUID = 1L;
	private static final String  message = "Invalid input data";
	
	public InvalidInputData(){
		super(message);
	}

	public String getMessage(){
		return message;
	}
}
