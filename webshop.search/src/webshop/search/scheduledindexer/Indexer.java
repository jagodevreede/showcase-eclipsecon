package webshop.search.scheduledindexer;

import org.amdatu.scheduling.annotations.RepeatForever;
import org.amdatu.scheduling.annotations.RepeatInterval;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import webshop.products.api.Product;
import webshop.products.api.ProductService;
import webshop.search.api.SearchService;

@RepeatForever
@RepeatInterval(period=RepeatInterval.MINUTE, value = 1)
public class Indexer implements Job{

	private volatile ProductService productService;
	private volatile SearchService searchService;
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		for(Product product : productService.listProducts()) {
			searchService.indexProduct(product);
		}
	}

}
