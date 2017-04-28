package finalproject.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import finalproject.util.JsonUtil;

@Component
public class JsonLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private JsonUtil jsonUtil;
	   
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		response = jsonUtil.buildJSONResponse(response, 200, "User logout");
	}

}
