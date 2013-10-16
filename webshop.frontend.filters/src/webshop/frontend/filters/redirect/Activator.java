package webshop.frontend.filters.redirect;

import java.util.Properties;

import javax.servlet.Filter;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;


public class Activator extends DependencyActivatorBase {
    @Override
    public synchronized void init(BundleContext context, DependencyManager manager) throws Exception {
    	
    	Properties props = new Properties();
    	props.put("pattern", "/*.*");
    	
        manager.add(createComponent()
        	.setInterface(Filter.class.getName(), props)
            .setImplementation(RedirectFilter.class)
            .add(createServiceDependency()
                .setService(LogService.class)
                .setRequired(false))
            );
    }

    @Override
    public synchronized void destroy(BundleContext context, DependencyManager manager) throws Exception {
    }
}