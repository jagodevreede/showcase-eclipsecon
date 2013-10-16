package webshop.orders.tests;

import java.util.List;
import java.util.Properties;

import org.amdatu.bndtools.test.BaseOSGiServiceTest;
import org.amdatu.mongo.MongoDBService;

import webshop.customer.api.Customer;
import webshop.orders.api.Order;
import webshop.orders.api.OrderEvent;
import webshop.orders.api.OrderProduct;
import webshop.orders.api.OrderService;

import com.mongodb.BasicDBObject;

public class OrderServiceTest extends BaseOSGiServiceTest<OrderService> {

	private volatile MongoDBService mongoDBService;
	
	public OrderServiceTest() {
		super(OrderService.class);
	}
	
	@Override
	public void setUp() throws Exception {
		Properties mongoProperties = new Properties();
		mongoProperties.put("dbName", "webshoptests");
		configureFactory("org.amdatu.mongo", mongoProperties);
		
		addServiceDependencies(MongoDBService.class);
		super.setUp();
		
		mongoDBService.getDB().getCollection("orders").remove(new BasicDBObject());
	}
	
    public void testSaveOrder() throws Exception {
    	Order order = new Order();
    	Customer customer = new Customer();
    	customer.setEmail("someuser@somedomain.com");
    	order.setCustomer(customer);
    	order.getProducts().add(new OrderProduct());
    	
    	instance.placeOrder(order);
    	
    	assertNotNull(order.get_id());
    	assertEquals(1, order.getEventLog().size());
    	assertEquals(OrderEvent.OrderEventType.ORDER_CREATED, order.getEventLog().get(0).getType());
    }
    
    public void testFindOrdersForCustomer() throws Exception {
    	Order order = new Order();
    	Customer customer = new Customer();
    	customer.setEmail("someuser@somedomain.com");
    	order.setCustomer(customer);
    	instance.placeOrder(order);
    	
    	Order order2 = new Order();
    	order2.setCustomer(customer);
    	instance.placeOrder(order2);
    	
    	Order order3 = new Order();
    	Customer customer2 = new Customer();
    	customer2.setEmail("someoneelse@somedomain.com");
    	order.setCustomer(customer2);
    	instance.placeOrder(order3);
    	
    	List<Order> orders = instance.listOrdersForCustomer("someuser@somedomain.com");
    	assertEquals(2, orders.size());
    }
}
