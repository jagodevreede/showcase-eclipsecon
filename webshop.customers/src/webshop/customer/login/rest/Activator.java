package webshop.customer.login.rest;

import org.amdatu.security.tokenprovider.TokenProvider;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

import webshop.customer.api.CustomerService;

public class Activator extends DependencyActivatorBase {

	@Override
	public void init(BundleContext bc, DependencyManager dm) throws Exception {
		dm.add(createComponent()
				.setInterface(Object.class.getName(), null)
				.setImplementation(LoginResource.class)
				.add(createServiceDependency().setService(TokenProvider.class)
						.setRequired(true))
				.add(createServiceDependency()
						.setService(CustomerService.class).setRequired(true)));
	}

	@Override
	public void destroy(BundleContext bc, DependencyManager dm)
			throws Exception {
	}

}
