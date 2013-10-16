package webshop.admin.login.configadminprovider;

import java.util.Dictionary;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import webshop.admin.login.api.AdminLoginService;

public class ConfigAdminLoginProvider implements ManagedService, AdminLoginService{

	private volatile String username;
	private volatile String password;
	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void updated(@SuppressWarnings("rawtypes") Dictionary properties) throws ConfigurationException {
		String usernameProperty = (String)properties.get("username");
		String passwordProperty = (String)properties.get("password");
		
		if(usernameProperty == null) {
			throw new ConfigurationException("username", "Required property username missing");
		}
		
		if(passwordProperty == null) {
			throw new ConfigurationException("password", "Required property passsword missing");
		}
		
		username = usernameProperty;
		password = passwordProperty;
	}

	@Override
	public boolean login(String username, String password) {
		return username.equals(this.username) && password.equals(this.password);
	}

}
