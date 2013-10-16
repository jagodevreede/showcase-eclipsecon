package webshop.products.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import webshop.products.api.Product;
import webshop.products.api.ProductService;

@Path("products")
public class ProductsResource {
	private volatile ProductService productService;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("categories")
    public List<String> listCategories() {
		return productService.listCategories();
    }
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("{category}")
    public List<Product> listProducts(@PathParam("category") String category) {
		return productService.listProductsInCategory(category);
    }	
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	@Path("all/{product}")
    public Product getProduct(@PathParam("product") String productId) {
		return productService.getProductById(productId);
    }	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateProduct(Product product) {
		productService.saveProduct(product);
	}

	@DELETE
	@Path("all/{product}")
	public void delete(@PathParam("product") String productId) {
		productService.removeProduct(productId);
	}
}
