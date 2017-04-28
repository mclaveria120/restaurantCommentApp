package finalproject.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import finalproject.util.JsonUtil;

@Component
public class JsonAccesDeniedHandler extends AccessDeniedHandlerImpl {

	@Autowired
	private JsonUtil jsonUtil;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,AccessDeniedException accessDeniedException) throws IOException, ServletException {
		  response=jsonUtil.buildJSONResponse(response, 403, accessDeniedException.getMessage());
	}
}
