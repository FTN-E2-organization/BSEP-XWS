package app.java.zuulserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import app.java.zuulserver.client.AuthClient;
import app.java.zuulserver.dto.VerificationResponseDTO;
import feign.FeignException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends ZuulFilter {
	
	@Autowired
    private AuthClient authClient;

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}
	
	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if (request.getHeader("Authorization") == null) {
            return null;
        }

        String token = request.getHeader("Authorization");
        System.out.println(token);
        try {
            VerificationResponseDTO dto = authClient.verify(token);
            if (dto == null) {
                setFailedRequest("Token is invalid.", 403);
            }
            ctx.addZuulRequestHeader("username", dto.username);
            ctx.addZuulRequestHeader("roles", dto.roles);
            ctx.addZuulRequestHeader("Auth", token);
            ctx.addZuulRequestHeader("Authorization", token);
        } catch (FeignException.NotFound e) {
            setFailedRequest("The user does not exist in the system.", 403);
        }
        return null;
    }
	
	private void setFailedRequest(String body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

}
