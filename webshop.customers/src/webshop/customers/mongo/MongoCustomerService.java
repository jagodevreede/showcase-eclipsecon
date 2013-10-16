package webshop.customers.mongo;

import net.vz.mongodb.jackson.JacksonDBCollection;

import org.amdatu.mongo.MongoDBService;
import org.apache.commons.codec.digest.DigestUtils;

import webshop.customer.api.Customer;
import webshop.customer.api.CustomerNotFoundException;
import webshop.customer.api.CustomerService;
import webshop.customer.api.DuplicateEmailException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MongoCustomerService implements CustomerService {
	private static final String COLLECTION = "customers";
	private volatile MongoDBService m_mongoDBService;
	private DBCollection customerCollection;
	
	public void start() {
		customerCollection = m_mongoDBService.getDB().getCollection(COLLECTION);
	}
	
	public void stop() {
		customerCollection = null;
	}
	
	@Override
	public Customer getCustomerByEmail(String email) {
        JacksonDBCollection<Customer, String> customers = JacksonDBCollection.wrap(customerCollection, Customer.class, String.class);
        Customer findOne = customers.findOne(new BasicDBObject("email", email));
        if(findOne == null) {
        	throw new CustomerNotFoundException(email);
        }
		return findOne;
	}

	@Override
	public void saveCustomer(Customer customer) {
		JacksonDBCollection<Customer, String> customers = JacksonDBCollection.wrap(customerCollection, Customer.class, String.class);
		
		if(customer.get_id() == null) {
			if(customers.findOne(new BasicDBObject("email", customer.getEmail())) != null) {
				throw new DuplicateEmailException(customer.getEmail());
			}
		}
		
		String savedId = customers.save(customer).getSavedId();
		customer.set_id(savedId);
	}

	@Override
	public Customer getCustomerByEmailAndPassword(String email, String password) {
		String hash = DigestUtils.sha256Hex(password);
		JacksonDBCollection<Customer, String> customers = JacksonDBCollection.wrap(customerCollection, Customer.class, String.class);
        Customer findOne = customers.findOne(new BasicDBObject("email", email).append("password", hash));
        if(findOne == null) {
        	throw new CustomerNotFoundException(email);
        }
		return findOne;
	}
}
