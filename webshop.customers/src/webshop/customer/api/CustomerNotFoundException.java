package webshop.customer.api;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String email) {
		super("Customer for email '" + email + "' was not found");
	}
}
