package webshop.search.events;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import webshop.products.api.Product;
import webshop.search.api.SearchService;

public class ProductIndexer implements EventHandler {
	private volatile SearchService searchService;

	@Override
	public void handleEvent(Event event) {
		switch (event.getTopic()) {
		case "products/updated":
			Product product = (Product) event.getProperty("product");
			searchService.removeFromIndex(product.get_id());
			searchService.indexProduct(product);
			break;
		case "products/removed":
			searchService.removeFromIndex((String) event.getProperty("id"));
			break;

		default:
			break;

		}

	}

}
