package webshop.orders.api;

import java.util.List;

import webshop.orders.api.OrderEvent.OrderEventType;

public interface OrderService {
    List<Order> listOrders();
    List<Order> listOrdersForCustomer(String email);
    Order getOrderById(String id);
    void placeOrder(Order order);
	void updateOrderStatus(String orderId, OrderEventType status);
}