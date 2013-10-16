package webshop.orders.mongo;

import java.util.ArrayList;
import java.util.List;

import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;

import org.amdatu.mongo.MongoDBService;

import webshop.orders.api.Order;
import webshop.orders.api.OrderEvent;
import webshop.orders.api.OrderEvent.OrderEventType;
import webshop.orders.api.OrderService;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MongoOrdersService implements OrderService {
	private static final String COLLECTION = "orders";
	private volatile MongoDBService m_mongoDBService;
	private DBCollection ordersCollection;

	public void start() {
		ordersCollection = m_mongoDBService.getDB().getCollection(COLLECTION);
	}
	
	public void stop() {
		ordersCollection = null;
	}
	
	@Override
	public List<Order> listOrders() {
        JacksonDBCollection<Order, String> orders = JacksonDBCollection.wrap(ordersCollection, Order.class, String.class);
        
        List<Order> result = new ArrayList<>();
        DBCursor<Order> cursor = orders.find();
        
        while(cursor.hasNext()) {
        	result.add(cursor.next());
        }
        	
        return result;
	}

	@Override
	public List<Order> listOrdersForCustomer(String email) {
		JacksonDBCollection<Order, String> orders = JacksonDBCollection.wrap(ordersCollection, Order.class, String.class);
        
        List<Order> result = new ArrayList<>();
        DBCursor<Order> cursor = orders.find(new BasicDBObject("customer.email", email));
        
        while(cursor.hasNext()) {
        	result.add(cursor.next());
        }
        	
        return result;
	}

	@Override
	public Order getOrderById(String id) {
		JacksonDBCollection<Order, String> orders = JacksonDBCollection.wrap(ordersCollection, Order.class, String.class);
		return orders.findOneById(id);
	}

	@Override
	public void placeOrder(Order order) {
		JacksonDBCollection<Order, String> orders = JacksonDBCollection.wrap(ordersCollection, Order.class, String.class);
		
		order.getEventLog().add(new OrderEvent(OrderEvent.OrderEventType.ORDER_CREATED, System.currentTimeMillis()));
		
		String savedId = orders.insert(order).getSavedId();
		order.set_id(savedId);
	}
	
	@Override
	public void updateOrderStatus(String orderId, OrderEventType type) {
		JacksonDBCollection<Order, String> orders = JacksonDBCollection.wrap(ordersCollection, Order.class, String.class);
		Order order = orders.findOneById(orderId);
		order.getEventLog().add(new OrderEvent(type, System.currentTimeMillis()));
		
		orders.save(order);
	}
}
