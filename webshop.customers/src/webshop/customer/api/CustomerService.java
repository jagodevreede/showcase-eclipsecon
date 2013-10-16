package webshop.customer.api;


public interface CustomerService {
    Customer getCustomerByEmail(String email);
    
    /**
     * @param email
     * @param password The unhashed password. This method will create the hash for lookup.
     * @return The user for this login, or a CustomerNotFoundException
     */
    Customer getCustomerByEmailAndPassword(String email, String password);
    void saveCustomer(Customer customer);
}