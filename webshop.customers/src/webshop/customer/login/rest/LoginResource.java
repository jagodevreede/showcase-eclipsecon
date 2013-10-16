package webshop.customer.login.rest;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.amdatu.security.tokenprovider.InvalidTokenException;
import org.amdatu.security.tokenprovider.TokenProvider;
import org.amdatu.security.tokenprovider.TokenProviderException;

import webshop.customer.api.Customer;
import webshop.customer.api.CustomerNotFoundException;
import webshop.customer.api.CustomerService;

@Path("login")
public class LoginResource {
	private volatile CustomerService customerService;
	private volatile TokenProvider tokenProvider;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Login login) {
		try {
			Customer customer = customerService.getCustomerByEmailAndPassword(login.getEmail(), login.getPassword());
			SortedMap<String, String> userMap = new TreeMap<>();
			userMap.put(TokenProvider.USERNAME, login.getEmail());
			
			String token = tokenProvider.generateToken(userMap);
			
			return Response.ok().entity(customer).cookie(new NewCookie(TokenProvider.TOKEN_COOKIE_NAME, token)).build();
		} catch(CustomerNotFoundException ex) {
			return Response.status(403).build();
		} catch (TokenProviderException e) {
			return Response.serverError().entity("Error while logging in").build();
		}
	}
	
	@Path("me")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Customer me(@Context HttpServletRequest request) throws TokenProviderException, InvalidTokenException {
		String token = tokenProvider.getTokenFromRequest(request);
		SortedMap<String, String> userMap = tokenProvider.verifyToken(token);
		return customerService.getCustomerByEmail(userMap.get(TokenProvider.USERNAME));
	}
	
	@Path("logout")
	@POST
	public void logout(@Context HttpServletRequest request) {
		String token = tokenProvider.getTokenFromRequest(request);
		tokenProvider.invalidateToken(token);
	}
}
