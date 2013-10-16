package webshop.customer.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.digest.DigestUtils;

import webshop.customer.api.Customer;
import webshop.customer.api.CustomerService;

@Path("customers")
public class CustomerResource {
	private volatile CustomerService customerService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    public void create(Customer customer) {
		//Hash the password before saving
		customer.setPassword(DigestUtils.sha256Hex(customer.getPassword()));
		
    	customerService.saveCustomer(customer);
    }	
}
