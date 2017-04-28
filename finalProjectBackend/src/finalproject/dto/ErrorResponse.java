package finalproject.dto;

import javax.ws.rs.core.Response;

import finalproject.exception.MyException;

public class ErrorResponse {

	private int status;
	private String message;
	
	
	public ErrorResponse(int statusCode, String message) {
		this.setStatus(statusCode);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
