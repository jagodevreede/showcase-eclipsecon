package webshop.search.amdatusearch;

import org.amdatu.search.Index;
import org.amdatu.search.Search;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

import webshop.products.api.ProductService;
import webshop.search.api.SearchService;

public class Activator extends DependencyActivatorBase {
	@Override
	public void init(BundleContext bc, DependencyManager dm) throws Exception {
		dm.add(createComponent()
				.setInterface(SearchService.class.getName(), null)
				.setImplementation(SolrSearch.class)
				.add(createServiceDependency().setService(Index.class)
						.setRequired(true))
				.add(createServiceDependency().setService(Search.class)
						.setRequired(true))
				.add(createServiceDependency().setService(ProductService.class)
						.setRequired(true))
				.add(createServiceDependency().setService(LogService.class)));
	}

	@Override
	public void destroy(BundleContext bc, DependencyManager dm)
			throws Exception {

	}

}
