package finalproject.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import finalproject.dto.ErrorResponse;
import finalproject.exception.InvalidUser;
import finalproject.exception.MyException;
import finalproject.security.CustomeUserDetail;

@Component
public class Util {


	public  Response buildErrorResponse(int statusCode, MyException entity ){
		return Response.status(statusCode).entity(new ErrorResponse(statusCode,entity.getMessage())).build();
	}
	
	public  Response buildErrorResponse(int statusCode, String message ){
		return Response.status(statusCode).entity(new ErrorResponse(statusCode,message)).build();
	}

	public Response buildOkResponse(Object entity){
		return Response.ok().entity(entity).build();
	}
	
	public  boolean isParameterValid(String value){
		return value!=null && !isStringEmpty(value);
	}
	
	public  boolean isStringEmpty(String value){
		return "".equals(value);
	}
	
	public void isTheRightUser(Integer id) throws InvalidUser{
		CustomeUserDetail user =(CustomeUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(!id.equals(user.getUser().getId())){
			throw new InvalidUser();
		}
	}
	
	 public  String getDate(){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			return df.format(new Date());
	 }
}
