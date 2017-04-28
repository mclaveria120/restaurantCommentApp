package finalproject.security.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import finalproject.util.JsonUtil;

@Component
public class JsonUnauthorizedHandler implements AuthenticationEntryPoint{
	 
	   @Autowired
	   private JsonUtil jsonUtil;
	   
	   @Override
	   public void commence( HttpServletRequest request, HttpServletResponse response,  AuthenticationException authException ) throws IOException{
		   
		   response = jsonUtil.buildJSONResponse(response, HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	   }

}