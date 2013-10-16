package webshop.admin.security;

import java.io.IOException;
import java.util.SortedMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amdatu.security.tokenprovider.InvalidTokenException;
import org.amdatu.security.tokenprovider.TokenProvider;
import org.amdatu.security.tokenprovider.TokenProviderException;

import webshop.admin.login.api.AdminLoginService;

public class SecurityFilter implements Filter {
	private TokenProvider tokenProvider;
	private AdminLoginService adminLoginService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		
		if(servletRequest.getPathInfo().startsWith("/admin/ui/") || servletRequest.getPathInfo().equals("/adminlogin")) {
			chain.doFilter(request, response);
			return;
		} else if(servletRequest.getPathInfo().startsWith("/admin")) {
			String token = tokenProvider.getTokenFromRequest(servletRequest);
			boolean isAdmin = false;
			try {
				SortedMap<String, String> userDetails = tokenProvider.verifyToken(token);
				if(adminLoginService.getUsername().equals(userDetails.get(TokenProvider.USERNAME))) {
					isAdmin = true;
				}
				
			} catch (TokenProviderException | InvalidTokenException e) {
				//The user will be redirect to login below
			}
			
			if(isAdmin) {
				chain.doFilter(request, response);
			} else {
				servletResponse.sendRedirect("/admin/ui/index.html");
			}
		} else {
			chain.doFilter(request, response);
		}
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	@Override
	public void destroy() {
		
	}

}
