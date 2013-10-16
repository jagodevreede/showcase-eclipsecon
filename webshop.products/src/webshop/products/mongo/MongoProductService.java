package webshop.products.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;

import org.amdatu.mongo.MongoDBService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import webshop.products.api.Product;
import webshop.products.api.ProductNotFoundException;
import webshop.products.api.ProductService;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoProductService implements ProductService {
	private static final String COLLECTION_NAME = "products";
	private volatile MongoDBService mongoDBService;
	private volatile DBCollection productCollection;
	private volatile EventAdmin eventAdmin;

	/**
	 * DBCollection is thread safe, we can re-use the same instance.
	 */
	public void start() {
		productCollection = mongoDBService.getDB().getCollection(
				COLLECTION_NAME);
	}

	public void stop() {
		productCollection = null;
	}

	@Override
	public List<Product> listProductsInCategory(String category) {
		JacksonDBCollection<Product, String> products = JacksonDBCollection
				.wrap(productCollection, Product.class, String.class);

		List<Product> result = new ArrayList<>();
		DBCursor<Product> cursor = products.find(new BasicDBObject("category",
				category));

		while (cursor.hasNext()) {
			result.add(cursor.next());
		}

		return result;
	}

	/**
	 * Categories are not stored in a separate collection, but are collected
	 * from the product.category field using a group function.
	 */
	@Override
	public List<String> listCategories() {
		BasicDBObject group = new BasicDBObject("$group", new BasicDBObject(
				"_id", "$category"));
		AggregationOutput aggregate = productCollection.aggregate(group);
		List<String> categories = new ArrayList<>();

		for (DBObject resultObject : aggregate.results()) {
			categories.add((String) resultObject.get("_id"));
		}

		return categories;
	}

	@Override
	public Product getProductById(String id) {
		JacksonDBCollection<Product, String> products = JacksonDBCollection
				.wrap(productCollection, Product.class, String.class);
		Product findOneById = products.findOneById(id);
		if (findOneById == null) {
			throw new ProductNotFoundException(id);
		}
		return findOneById;
	}

	@Override
	public void saveProduct(Product product) {
		JacksonDBCollection<Product, String> products = JacksonDBCollection
				.wrap(productCollection, Product.class, String.class);
		String savedId = products.save(product).getSavedId();
		product.set_id(savedId);

		// Post an event to EventAdmin
		Map<String, Object> properties = new HashMap<>();
		properties.put("product", product);
		eventAdmin.postEvent(new Event("products/updated", properties));
	}

	@Override
	public void removeProduct(String id) {
		JacksonDBCollection<Product, String> products = JacksonDBCollection
				.wrap(productCollection, Product.class, String.class);
		products.removeById(id);
	}

	@Override
	public List<Product> listProducts() {
		JacksonDBCollection<Product, String> products = JacksonDBCollection
				.wrap(productCollection, Product.class, String.class);

		List<Product> result = new ArrayList<>();
		DBCursor<Product> cursor = products.find();

		while (cursor.hasNext()) {
			result.add(cursor.next());
		}

		return result;
	}
}
