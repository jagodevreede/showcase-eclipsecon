package webshop.products.api;

import java.util.List;

public interface ProductService {
    List<Product> listProductsInCategory(String category);
    List<Product> listProducts();
    List<String> listCategories();
    Product getProductById(String id);
	void saveProduct(Product product);
	void removeProduct(String id);
}