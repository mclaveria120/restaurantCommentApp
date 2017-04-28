package finalproject.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

import finalproject.model.User;

@Component
public class JsonUtil {

	
	
	public HttpServletResponse buildJSONResponse(HttpServletResponse response, int status, String message) throws IOException{
			response.setContentType("application/json");
	        response.setStatus(status);
	        PrintWriter out = response.getWriter();
	     
	        try {
	   	        JSONObject jsonObj =  new JSONObject();
				jsonObj.put("status", status);
				jsonObj.put("message", message);
				 out.print(jsonObj);
				 
			} catch (JSONException e) {
				out.print(e);
				e.printStackTrace();
			}
			return response;
	}
	
	public HttpServletResponse buildJsonLoginResponse(HttpServletResponse response, int status, User user) throws IOException{
		response.setContentType("application/json");
        response.setStatus(status);
        PrintWriter out = response.getWriter();
     
        try {
   	        JSONObject jsonObj =  new JSONObject();
   	        jsonObj.put("id", user.getId());
			jsonObj.put("name", user.getName());
			jsonObj.put("surname", user.getSurname());
			jsonObj.put("role", user.getRole());
			 out.print(jsonObj);
			 
		} catch (JSONException e) {
			out.print(e);
			e.printStackTrace();
		}
		return response;
}
	
}
