package webshop.orders.api;

public class OrderEvent {
	private OrderEventType type;
	private long timestamp;

	public OrderEvent() {
	}
	
	public OrderEvent(OrderEventType type, long timestamp) {
		this.type = type;
		this.timestamp = timestamp;
	}

	public OrderEventType getType() {
		return type;
	}

	public void setType(OrderEventType type) {
		this.type = type;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public static enum OrderEventType {
		ORDER_CREATED, PAYMENT_RECEIVED, ORDER_SENT, ORDER_CANCELED
	}
}
