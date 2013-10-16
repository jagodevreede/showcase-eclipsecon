package webshop.admin.login.api;

public interface AdminLoginService {
	boolean login(String username, String password);

	String getUsername();
}
