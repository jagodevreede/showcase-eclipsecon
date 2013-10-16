package webshop.admin.login.configadminprovider;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;

import webshop.admin.login.api.AdminLoginService;

public class Activator extends DependencyActivatorBase {
	@Override
	public void init(BundleContext bc, DependencyManager dm) throws Exception {
		dm.add(createComponent()
				.setInterface(AdminLoginService.class.getName(), null)
				.setImplementation(ConfigAdminLoginProvider.class)
				.add(createConfigurationDependency().setPid(
						"webshop.admin.login.configadminprovider")));	
	}

	@Override
	public void destroy(BundleContext bc, DependencyManager dm)
			throws Exception {
	}
}
