package webshop.search.rest;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

import webshop.search.api.SearchService;

public class Activator extends DependencyActivatorBase {
	@Override
	public void init(BundleContext bc, DependencyManager dm) throws Exception {
		dm.add(createComponent()
				.setInterface(Object.class.getName(), null)
				.setImplementation(SearchResource.class)
				.add(createServiceDependency().setService(SearchService.class)
						.setRequired(true)));
	}

	@Override
	public void destroy(BundleContext bc, DependencyManager dm)
			throws Exception {

	}

}
