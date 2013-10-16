package webshop.search.test;

import java.util.List;
import java.util.Properties;

import org.amdatu.bndtools.test.BaseOSGiServiceTest;
import org.amdatu.mongo.MongoDBService;

import webshop.products.api.Product;
import webshop.products.api.ProductService;
import webshop.search.api.SearchService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class SearchTest extends BaseOSGiServiceTest<SearchService> {
	private volatile MongoDBService mongoDBService;
	private volatile DBCollection collection;
	private volatile ProductService productService;
	
	public SearchTest() {
		super(SearchService.class);
	}
	
	@Override
	public void setUp() throws Exception {
		Properties searchProperties = new Properties();
		searchProperties.put("repository", "webshop");
		configureFactory("org.amdatu.search.solr", searchProperties);
		
		Properties mongoProperties = new Properties();
		mongoProperties.put("dbName", "webshoptests");
		configureFactory("org.amdatu.mongo", mongoProperties);
		
		addServiceDependencies(MongoDBService.class, ProductService.class);
		super.setUp();
		
		collection = mongoDBService.getDB().getCollection("products");
		
		collection.remove(new BasicDBObject());
		
		Product product = new Product();
    	product.setName("Modular Java in the Cloud");
    	productService.saveProduct(product);
    	
    	Product product2 = new Product();
    	product2.setName("OSGi in Action");
    	productService.saveProduct(product2);
    	
    	Product product3 = new Product();
    	product3.setName("Effective Java");
    	productService.saveProduct(product3);
    	
		instance.indexProduct(product);
		instance.indexProduct(product2);
		instance.indexProduct(product3);
	}
	
    public void testFindProductsByCompleteName() throws Exception {
    	List<Product> findProducts = instance.findProducts("Modular AND Java AND Cloud");
    	assertEquals(1, findProducts.size());
    }
    
    public void testFindProductsByPartialName() throws Exception {
    	List<Product> findProducts = instance.findProducts("Java");
    	assertEquals(2, findProducts.size());
    }
    
}