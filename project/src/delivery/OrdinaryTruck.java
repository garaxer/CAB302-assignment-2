package delivery;

import java.awt.Window.Type;
import java.util.ArrayList;
import java.util.Set;

import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 * An extension of the abstract class Truck, OrdinaryTruck implements the abstract 
 * methods for Truck. Ordinary Truck can hold stock, and calculates its cost based
 * on the quantity of stock it transports. This class is used to transport goods 
 * to the store when they need to be reordered. 
 * @author Alex Koppon
 *
 */

public class OrdinaryTruck extends Truck {
	
	// define the type of Truck
	private String type = "Ordinary";
	
	/**
	 * A constructor for OrdinaryTruck which takes a Stock object
	 * @param stock
	 * @throws DeliveryException when the stock being loaded contains an item that
	 * needs refrigeration 
	 */
	public OrdinaryTruck(Stock stock) throws DeliveryException {
		super(1000,stock);
		checkStockForColdItems(stock);	
	}
	
	/**
	 * A constructor for OrdinaryTruck which doesn't require a Stock object
	 */
	public OrdinaryTruck() {
		super(1000);	
	}
	
	/**
	 * An implementation of the abstract method in Truck. It calculates the cost 
	 * of the truck using the formula 750 + 0.25*quantity, where quantity is the total
	 * quantity of stock stored in the truck. It returns a double. 
	 */
	@Override
	public double getCost() {
		double cost = 750 + 0.25*getStock().getTotalQuantity();
		return cost;
	}

	/**
	 * Returns a string with the type of this truck
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * Returns a string of the stock and the type of the truck 
	 * in the format used by Manifest. 
	 * The format for one item is as follows: ">Ordinay\nitem,quantity\n"
	 */
	@Override
	public String getManifest() {
		String output = "";
		output += ">" + type + "\n";
		for (Item item : getStock().toSet()) {
			output += item.getItemName() + ",";
			output += Integer.toString(getStock().getQuantity(item)) + "\n";
		}
		return output;
	}

	/**
	 * Adds a new Stock item to the current Stock item. 
	 * Throws a DeliveryException is the stock exceeds the capacity of the truck
	 * or the stock contains refrigerated goods
	 */
	@Override
	public void addStock(Stock stocktoAdd) throws DeliveryException{
		Set<Item> allItems = stocktoAdd.toSet();
		for (Item item : allItems) {
			if (item.getItemTemperature() < 0) {
				throw new DeliveryException("This truck cannot carry refrigerated goods");
			} else {		
				if (stocktoAdd.getTotalQuantity() > getRemainingCapacity()) {
					System.out.println("capacity"+getCapacity());
					System.out.println("c"+stocktoAdd.getTotalQuantity() + " "+ getRemainingCapacity());
					throw new DeliveryException("There isn't enough room on the truck");
				} else {
					getStock().addStock(stocktoAdd);	
				}
			}
		}
	}
	
	/**
	 * A helper method to check whether the stock being added to the truck in the constructor
	 * contains any items that require refrigeration, and if so, throws a DeliveryException
	 * @param stock
	 * @throws DeliveryException
	 */
	private void checkStockForColdItems(Stock stock) throws DeliveryException{
		Set<Item> allItems = stock.toSet();
		for (Item item : allItems) {
			if (item.getItemTemperature() < 0) {
				throw new DeliveryException("This truck cannot carry refrigerated goods");
			} else {
				int newQuantity = stock.getTotalQuantity();		
				if ((newQuantity) > getCapacity()) {
					throw new DeliveryException("There isn't enough room on the truck");
				} 
			}
		}
	}

	/**
	 * Returns the remaining capacity in the truck, which is the initial capacity 
	 * minus the total quantity of the stock stored in the truck
	 */
	@Override
	public int getRemainingCapacity() {
		return getCapacity() - getStock().getTotalQuantity();
	}
	


}
