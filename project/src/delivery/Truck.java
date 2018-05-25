package delivery;

import stock.Stock;
import stock.StockException;

/**
 * 
 * @author Alex Koppon
 *
 */

public abstract class Truck {
	
	private int capacity;
	private Stock stock;
		
	public Truck(int capacity, Stock stock) throws DeliveryException {
		this.capacity = capacity;
		if (stock.getTotalQuantity() < capacity) {
			this.stock = stock;
		} else {
			throw new DeliveryException("Stock does not fit in truck");
		}				
	}
	
	public Truck(Stock stock) throws DeliveryException {
		this.capacity = 1000;
		if (stock.getTotalQuantity() < capacity) {
			this.stock = stock;
		} else {
			throw new DeliveryException("Stock does not fit in truck");
		}				
	}
	
	public Truck() {
		this.capacity = 1000;
	}
	
		
	public int getCapacity() {
		return capacity;
	}
	
	public Stock getStock() {
		return stock;
	}
	
	public abstract void addStock(Stock stock) throws DeliveryException;

	
	public abstract double getCost();

	
	public abstract String getType();
	
	
	public abstract int getRemainingCapacity();

	
	public abstract String getManifest() throws StockException;


	

}
