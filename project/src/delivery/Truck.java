package delivery;

import stock.Stock;
import stock.StockException;

/**
 * Truck is the abstract class that provides the framework for both OrdinaryTruck
 * and RefrigeratedTruck. A Truck has and can return its capacity and stock. Trucks
 * are used by Manifest to reorder the stock needed by the store. 
 * @author Alex Koppon
 *
 */

public abstract class Truck {
	
	// define the capacity and stock carried by the Truck
	private int capacity;
	private Stock stock;
		
	/**
	 * Construct a Truck with both a Stock and a capacity
	 * @param capacity
	 * @param stock
	 * @throws DeliveryException is thrown when there is not enough room on a truck
	 */
	public Truck(int capacity, Stock stock) throws DeliveryException {
		this.capacity = capacity;
		if (stock.getTotalQuantity() <= capacity) {
			this.stock = stock;
		} else {
			throw new DeliveryException("Stock does not fit in truck");
		}				
	}
	
	/**
	 * Construct a Truck with a Stock. The default capacity will be used
	 * @param stock
	 * @throws DeliveryException is thrown when there is not enough room on a truck
	 */
	public Truck(Stock stock) throws DeliveryException {
		this.capacity = 1000;
		if (stock.getTotalQuantity() <= capacity) {
			this.stock = stock;
		} else {
			throw new DeliveryException("Stock does not fit in truck");
		}				
	}
	
	/**
	 * Constructs a Truck with just capacity. A new empty stock object is made
	 * @param capacity
	 */
	public Truck(int capacity) {
		this.capacity = capacity;
		this.stock = new Stock();
	}
	
	/**
	 * Constructs a Truck object. This uses the default capacity. 
	 */
	public Truck() {
		this.capacity = 1000;
	}
	
		
	/**
	 * @returns the int value for capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * @returns the Stock object
	 */
	public Stock getStock() {
		return stock;
	}
	
	/**
	 * Abstract method. 
	 * addStock(Stock stock) should add the stock from the stock object 
	 * passed into the function to the stock currently in the truck
	 * @param stock
	 * @throws DeliveryException if cold stock is added to an OrdinaryTruck
	 */
	public abstract void addStock(Stock stock) throws DeliveryException;

	/**
	 * Abstract method.
	 * @returns the double cost. The calculation for this is different for different
	 * truck types
	 */
	public abstract double getCost();

	/**
	 * Abstract method.
	 * @return the String of the truck's type
	 */
	public abstract String getType();
	
	/**
	 * Abstract method. 
	 * @returns the remaining capacity in a truck i.e(capacity-totalStockQuantity)
	 */
	public abstract int getRemainingCapacity();

	/**
	 * Abstract method. 
	 * @returns the a string of the stock contained in a truck in the format 
	 * used by Manifest. This takes the form >type\nitem,quantity\nitem,quantity\n.."
	 * @throws StockException
	 */
	public abstract String getManifest() throws StockException;


	
}
