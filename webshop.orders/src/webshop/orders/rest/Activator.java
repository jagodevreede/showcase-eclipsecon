package webshop.orders.rest;

import org.amdatu.security.tokenprovider.TokenProvider;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

import webshop.customer.api.CustomerService;
import webshop.orders.api.OrderService;

public class Activator extends DependencyActivatorBase {
	@Override
	public synchronized void init(BundleContext context,
			DependencyManager manager) throws Exception {
		manager.add(createComponent()
				.setInterface(Object.class.getName(), null)
				.setImplementation(OrdersResource.class)
				.add(createServiceDependency().setService(TokenProvider.class)
						.setRequired(true))
				.add(createServiceDependency().setService(OrderService.class)
						.setRequired(true))
				.add(createServiceDependency()
						.setService(CustomerService.class).setRequired(true))

		);
	}

	@Override
	public synchronized void destroy(BundleContext context,
			DependencyManager manager) throws Exception {
	}
}