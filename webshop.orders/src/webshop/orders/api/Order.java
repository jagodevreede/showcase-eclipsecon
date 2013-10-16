package webshop.orders.api;

import java.util.ArrayList;
import java.util.List;

import webshop.customer.api.Customer;

import net.vz.mongodb.jackson.ObjectId;

public class Order {
	@ObjectId
    private String _id;
	private List<OrderProduct> products = new ArrayList<>();
	private List<OrderEvent> eventLog = new ArrayList<>();
	private Customer customer;
    
    public String get_id() {
    	return _id;
    }
    
    public void set_id(String id) {
    	this._id = id;
    }

	public List<OrderProduct> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}

	public List<OrderEvent> getEventLog() {
		return eventLog;
	}

	public void setEventLog(List<OrderEvent> eventLog) {
		this.eventLog = eventLog;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}