package webshop.orders.api;

import webshop.products.api.Product;

public class OrderProduct extends Product{
	private int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
