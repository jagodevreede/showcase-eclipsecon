package webshop.testdata;

import org.amdatu.mongo.MongoDBService;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

import webshop.products.api.ProductService;

public class Activator extends DependencyActivatorBase {
	@Override
	public synchronized void init(BundleContext context,
			DependencyManager manager) throws Exception {
		manager.add(createComponent()
				.setImplementation(TestdataInserter.class)
				.add(createServiceDependency().setService(ProductService.class)
						.setRequired(true))
				.add(createServiceDependency().setService(MongoDBService.class)
						.setRequired(true)));
	}

	@Override
	public synchronized void destroy(BundleContext context,
			DependencyManager manager) throws Exception {
	}
}