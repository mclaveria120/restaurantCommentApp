package finalproject.security.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import finalproject.model.User;
import finalproject.security.CustomeUserDetail;
import finalproject.util.JsonUtil;

public class JsonLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
 
    private RequestCache requestCache = new HttpSessionRequestCache();
 
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
// 
//        if (savedRequest == null) {
//            clearAuthenticationAttributes(request);
//            return;
//        }
//        String targetUrlParam = getTargetUrlParameter();
//        if (isAlwaysUseDefaultTargetUrl() || 
//          (targetUrlParam != null && 
//          StringUtils.hasText(request.getParameter(targetUrlParam)))) {
//            requestCache.removeRequest(request, response);
//            clearAuthenticationAttributes(request);
//            return;
//        }
// 
//        clearAuthenticationAttributes(request);
        JsonUtil jsonUtil = new JsonUtil();
        CustomeUserDetail principal = (CustomeUserDetail)authentication.getPrincipal();
        
		response=jsonUtil.buildJsonLoginResponse(response, 200, principal.getUser());
    }
 
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}   