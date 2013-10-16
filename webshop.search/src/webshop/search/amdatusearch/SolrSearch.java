package webshop.search.amdatusearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.amdatu.search.Document;
import org.amdatu.search.Index;
import org.amdatu.search.Search;
import org.amdatu.search.SearchException;
import org.amdatu.search.SearchResponse;
import org.osgi.service.log.LogService;

import webshop.products.api.Product;
import webshop.products.api.ProductNotFoundException;
import webshop.products.api.ProductService;
import webshop.search.api.SearchService;

public class SolrSearch implements SearchService {
	private volatile Search search;
	private volatile Index index;
	private volatile LogService logService;
	private volatile ProductService productService;
	
	@Override
	public void indexProduct(Product product) {
		Document document = index.newDocument();
		document.setField("uri", product.get_id());
		document.setField("content", product.getName());
		
		try {
			index.update(document);
		} catch (SearchException e) {
			logService.log(LogService.LOG_ERROR, "Error indexing product with name '" + product.getName() + "'", e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void removeFromIndex(String id) {
		try {
			index.deleteById(id);
		} catch (SearchException e) {
			logService.log(LogService.LOG_ERROR, "Error deleting indexed document with id '" + id + "'", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Product> findProducts(String query) {
		try {
			SearchResponse select = search.select(query, 0, 10, null);
			List<Product> products = new ArrayList<>();
			for (Document document : select.getResults()) {
				Collection<Object> fieldValues = document.getFieldValues("uri");
				Iterator<Object> idIterator = fieldValues.iterator();
				while (idIterator.hasNext()) {
					try {
						products.add(productService.getProductById((String)idIterator.next()));
					} catch(ProductNotFoundException ex) {
						logService.log(LogService.LOG_WARNING, "Product retrieved from index with id '" + query + "' could not be loaded from ProductService");
					}
				}
			}
			
			return products;
			
		} catch (SearchException e) {
			logService.log(LogService.LOG_ERROR, "Error search with query '" + query + "'", e);
			throw new RuntimeException(e);
		}
		
	}

}
