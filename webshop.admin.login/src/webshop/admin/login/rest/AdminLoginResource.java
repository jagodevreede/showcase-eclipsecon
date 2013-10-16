package webshop.admin.login.rest;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.amdatu.security.tokenprovider.TokenProvider;
import org.amdatu.security.tokenprovider.TokenProviderException;

import webshop.admin.login.api.AdminLoginService;

@Path("adminlogin")
public class AdminLoginResource {
	private volatile AdminLoginService adminLoginService;
	private volatile TokenProvider tokenProvider;
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Login login) throws TokenProviderException {
    	if(adminLoginService.login(login.getUsername(), login.getPassword())) {
    		SortedMap<String, String> userMap = new TreeMap<>();
    		userMap.put(TokenProvider.USERNAME, "admin");
    		
    		String token = tokenProvider.generateToken(userMap);
    		
    		return Response.ok().cookie(new NewCookie(TokenProvider.TOKEN_COOKIE_NAME, token)).build();
    	}
    	
    	return Response.status(403).build();
    }	
}
