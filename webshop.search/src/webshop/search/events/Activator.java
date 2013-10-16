package webshop.search.events;

import java.util.Properties;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import webshop.search.api.SearchService;

public class Activator extends DependencyActivatorBase {

	@Override
	public void init(BundleContext bc, DependencyManager dm) throws Exception {
		Properties properties = new Properties();
		properties.put(EventConstants.EVENT_TOPIC, new String[] {"products/saved", "products/updated", "products/removed"});
		dm.add(createComponent()
				.setInterface(EventHandler.class.getName(), properties)
				.setImplementation(ProductIndexer.class)
				.add(createServiceDependency().setService(SearchService.class)
						.setRequired(true)));
	}

	@Override
	public void destroy(BundleContext bc, DependencyManager dm)
			throws Exception {
	}
}
