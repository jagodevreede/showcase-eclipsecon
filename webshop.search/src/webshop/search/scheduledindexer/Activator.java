package webshop.search.scheduledindexer;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.quartz.Job;

import webshop.products.api.ProductService;
import webshop.search.api.SearchService;

public class Activator extends DependencyActivatorBase {
	@Override
	public void init(BundleContext bc, DependencyManager dm) throws Exception {
		dm.add(createComponent()
				.setInterface(Job.class.getName(), null)
				.setImplementation(Indexer.class)
				.add(createServiceDependency().setService(SearchService.class)
						.setRequired(true))
				.add(createServiceDependency().setService(ProductService.class)
						.setRequired(true)));
	}

	@Override
	public void destroy(BundleContext bc, DependencyManager dm)
			throws Exception {

	}

}
