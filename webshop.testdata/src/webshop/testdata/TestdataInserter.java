package webshop.testdata;

import org.amdatu.mongo.MongoDBService;

import com.mongodb.BasicDBObject;

import webshop.products.api.Product;
import webshop.products.api.ProductService;

public class TestdataInserter {
	private volatile ProductService productService;
	private MongoDBService mongoDBService;
	
	public void start() {
		insertProducts();
	}
	
	private void insertProducts() {
		mongoDBService.getDB().getCollection("products").remove(new BasicDBObject());
		
		productService.saveProduct(new Product("Modular Java in the Cloud", "books", 30));
		productService.saveProduct(new Product("Clean Code", "books", 32));
		productService.saveProduct(new Product("OSGi in Action", "books", 40));
		productService.saveProduct(new Product("Effective Java", "books", 29));
		productService.saveProduct(new Product("Enterprise OSGi", "books", 45));
		
		productService.saveProduct(new Product("Starcraft II", "games", 35));
		productService.saveProduct(new Product("Battlefield 3", "games", 50));
		productService.saveProduct(new Product("Far Cry 3", "games", 55));
		productService.saveProduct(new Product("Modern Warfare 3", "games", 40));
	}
}
