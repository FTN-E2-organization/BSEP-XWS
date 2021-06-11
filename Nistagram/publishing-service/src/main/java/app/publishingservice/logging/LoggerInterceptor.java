package app.publishingservice.logging;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerInterceptor implements HandlerInterceptor {

	private static Logger log = LoggerFactory.getLogger(LoggerInterceptor.class);
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		System.out.println(request.getPathInfo() + "  -------------------------");				
		
		if (request.getRequestURI().equals("/error"))
			return true;

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		String body = getParameters(request);
		try {
			log.info("Publishing service - " + request.getMethod() + " " + request.getRequestURI() + " " + ipAddress + " " 
					+ " parameters " + body);
		} catch (Exception e) {
		}
		return true;
	}	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (request.getRequestURI().equals("/error"))
			return;
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		try {
			log.info("Publishing service - Response code: " + response.getStatus() + " " + ipAddress + " ");		
		}catch(Exception e) {			
		}
	}	
	
	private String getParameters(HttpServletRequest request) {
		StringBuffer posted = new StringBuffer();
		Enumeration<?> e = request.getParameterNames();
		if (e != null && e.hasMoreElements()) {
			posted.append("?");
		}
		while (e.hasMoreElements()) {
			if (posted.length() > 1) {
				posted.append("&");
			}
			String curr = (String) e.nextElement();
			posted.append(curr + "=");
			if (curr.contains("password") || curr.contains("pass") || curr.contains("pwd") || curr.contains("email")) {
				posted.append("*****");
			} else {
				posted.append(request.getParameter(curr));
			}
		}
		return posted.toString();
	}	
	
}
