package webshop.products.api;

import net.vz.mongodb.jackson.ObjectId;

public class Product {
	@ObjectId
    private String _id;
	private String name;
	private String category;
	private double price;
    
	public Product() {
	}
	
    public Product(String name, String category, double price) {
		this.name = name;
		this.category = category;
		this.price = price;
	}

	public String get_id() {
    	return _id;
    }
    
    public void set_id(String id) {
    	this._id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}