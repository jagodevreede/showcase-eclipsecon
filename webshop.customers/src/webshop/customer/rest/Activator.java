package webshop.customer.rest;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

import webshop.customer.api.CustomerService;


public class Activator extends DependencyActivatorBase {
    @Override
    public synchronized void init(BundleContext context, DependencyManager manager) throws Exception {
        manager.add(createComponent()
        	.setInterface(Object.class.getName(), null)
            .setImplementation(CustomerResource.class)
            .add(createServiceDependency()
                .setService(CustomerService.class)
                .setRequired(true))
            );
    }

    @Override
    public synchronized void destroy(BundleContext context, DependencyManager manager) throws Exception {
    }
}