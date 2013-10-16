package webshop.search.api;

import java.util.List;

import webshop.products.api.Product;

public interface SearchService {
	void indexProduct(Product product);
	List<Product> findProducts(String query);
	void removeFromIndex(String id);
}
