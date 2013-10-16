package webshop.search.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import webshop.products.api.Product;
import webshop.search.api.SearchService;

@Path("productsearch")
public class SearchResource {
	private volatile SearchService searchService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> search(@QueryParam("query") String query) {
		return searchService.findProducts(query);
	}
}
