package webshop.customer.api;

public class DuplicateEmailException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateEmailException(String email) {
		super("Duplicate email address " + email);
	}
}
