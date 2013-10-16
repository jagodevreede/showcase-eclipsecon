package webshop.customers.mongo;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.amdatu.mongo.MongoDBService;

import webshop.customer.api.CustomerService;

public class Activator extends DependencyActivatorBase {
    @Override
    public synchronized void init(BundleContext context, DependencyManager manager) throws Exception {
        manager.add(createComponent()
        	.setInterface(CustomerService.class.getName(), null)
            .setImplementation(MongoCustomerService.class)
            .add(createServiceDependency()
                .setService(MongoDBService.class)
                .setRequired(true))
            .add(createServiceDependency()
                .setService(LogService.class)
                .setRequired(false))
            );
    }

    @Override
    public synchronized void destroy(BundleContext context, DependencyManager manager) throws Exception {
    }
}