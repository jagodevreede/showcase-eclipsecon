package webshop.orders.rest;

import java.util.List;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.amdatu.security.tokenprovider.InvalidTokenException;
import org.amdatu.security.tokenprovider.TokenProvider;
import org.amdatu.security.tokenprovider.TokenProviderException;

import webshop.customer.api.Customer;
import webshop.customer.api.CustomerService;
import webshop.orders.api.Order;
import webshop.orders.api.OrderEvent.OrderEventType;
import webshop.orders.api.OrderService;

@Path("orders")
public class OrdersResource {
	private volatile OrderService orderService;
	private volatile TokenProvider tokenProvider;
	private volatile CustomerService customerService;
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void placeOrder(@Context HttpServletRequest request, Order order) throws TokenProviderException, InvalidTokenException {
		String customerEmail = getCustomerEmailFromToken(request);
		Customer customer = customerService.getCustomerByEmail(customerEmail);
		order.setCustomer(customer);
		orderService.placeOrder(order);
    }

	
	@GET
	@Path("mine")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> myOrders(@Context HttpServletRequest request) throws TokenProviderException, InvalidTokenException{
		String customerEmail = getCustomerEmailFromToken(request);
		
		return orderService.listOrdersForCustomer(customerEmail);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> listOrders(@Context HttpServletRequest request) throws TokenProviderException, InvalidTokenException {
		if(!isAdmin(request)) {
			throw new WebApplicationException(403);
		}
		
		return orderService.listOrders();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{order}/events")
	public void addEvent(@PathParam("order") String orderId, String status) {
		orderService.updateOrderStatus(orderId, OrderEventType.valueOf(status));
	}
	
	private String getCustomerEmailFromToken(HttpServletRequest request)
			throws TokenProviderException, InvalidTokenException {
		String token = tokenProvider.getTokenFromRequest(request);
		SortedMap<String, String> userDetails = tokenProvider.verifyToken(token);
		
		String customerEmail = userDetails.get(TokenProvider.USERNAME);
		return customerEmail;
	}
	
	private boolean isAdmin(HttpServletRequest request) throws TokenProviderException, InvalidTokenException {
		String token = tokenProvider.getTokenFromRequest(request);
		SortedMap<String, String> userDetails = tokenProvider.verifyToken(token);
		
		String username = userDetails.get(TokenProvider.USERNAME);
		return "admin".equals(username);
	}
	
}
