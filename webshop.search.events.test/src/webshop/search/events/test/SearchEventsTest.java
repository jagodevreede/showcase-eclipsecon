package webshop.search.events.test;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.amdatu.bndtools.test.BaseOSGiServiceTest;
import org.amdatu.mongo.MongoDBService;

import webshop.products.api.Product;
import webshop.products.api.ProductService;
import webshop.search.api.SearchService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class SearchEventsTest extends BaseOSGiServiceTest<SearchService> {
	private volatile MongoDBService mongoDBService;
	private volatile DBCollection collection;
	private volatile ProductService productService;
	
	public SearchEventsTest() {
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
	}
	
    public void testNewProductIndexEvent() throws InterruptedException {
    	Product newProduct = new Product();
    	newProduct.setName("Modular Java in the Cloud");
    	productService.saveProduct(newProduct);
    	
    	//Events are posted asynchronously.
    	TimeUnit.SECONDS.sleep(1);
    	
    	List<Product> findProducts = instance.findProducts("modular AND java");
    	assertEquals(1, findProducts.size());
    }
    
    public void testUpdatedProductIndexEvent() throws InterruptedException {
    	Product newProduct = new Product();
    	newProduct.setName("Something with Cloud");
    	productService.saveProduct(newProduct);
    	
    	//Events are posted asynchronously.
    	TimeUnit.SECONDS.sleep(1);
    	
    	assertEquals(0, instance.findProducts("modular AND java").size());
    	assertEquals(1, instance.findProducts("cloud").size());
    	
    	newProduct.setName("modular java");
    	productService.saveProduct(newProduct);
    	
    	TimeUnit.SECONDS.sleep(1);
    	
    	assertEquals(1, instance.findProducts("modular AND java").size());
    	assertEquals(0, instance.findProducts("cloud").size());
    }
    
    public void testRemovedProductIndexEvent() throws InterruptedException {
    	Product newProduct = new Product();
    	newProduct.setName("Modular Java in the Cloud");
    	productService.saveProduct(newProduct);
    	
    	//Events are posted asynchronously.
    	TimeUnit.SECONDS.sleep(1);
    	
    	assertEquals(1, instance.findProducts("modular AND java").size());
    	
    	productService.removeProduct(newProduct.get_id());
    	
    	TimeUnit.SECONDS.sleep(1);
    	
    	assertEquals(0, instance.findProducts("modular AND java").size());
    }
}
