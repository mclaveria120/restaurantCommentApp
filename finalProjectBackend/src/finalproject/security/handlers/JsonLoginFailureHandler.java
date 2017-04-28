package finalproject.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import finalproject.util.JsonUtil;

@Component
public class JsonLoginFailureHandler  extends SimpleUrlAuthenticationFailureHandler {


	@Autowired
	private JsonUtil jsonUtil;
	
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		response=jsonUtil.buildJSONResponse(response, 401, "Invalid User");
    }
}
