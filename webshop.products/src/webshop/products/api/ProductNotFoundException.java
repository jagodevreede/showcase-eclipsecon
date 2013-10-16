package webshop.products.api;

public class ProductNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String id) {
		super("Product with id '" + id + "' could not be found");
	}
}
