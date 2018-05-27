package delivery;

import stock.Item;
import stock.Stock;
import stock.StockException;

/**
  * An extension of the abstract class Truck, RefrigeratedTruck implements the abstract 
 * 	methods for Truck. It holds a 800 items. Used by manifest to return the cost
 * 
 * @author Gary Bagnall
 *
 */
public class RefrigeratedTruck extends Truck {
	
	/**
	 * Constructs a Refrigerated Truck
	 * @param stock The stock to give the truck
	 * @throws DeliveryException
	 */
	public RefrigeratedTruck(Stock stock) throws DeliveryException {
		super(800,stock);
	}
	
	/**
	 *  Constructs a Refrigerated Truck
	 */
	public RefrigeratedTruck() {
		super(800); 
	}
	
	/**
	 *  Adds stock to the truck's stock
	 * 
	 */
	public void addStock(Stock stockToAdd) throws DeliveryException {
		int quantity = stockToAdd.getTotalQuantity();
		if (quantity > getCapacity()) {
			throw new DeliveryException("There isn't enough room on the truck");
		}
		getStock().addStock(stockToAdd);
	}
	
	@Override
	/**
	 * Get the cost of the truck
	 */
	public double getCost() {
		int coldest = 1000; //Arbitary number Max
		for (Item item : getStock().toSet()) {
			int temp = item.getItemTemperature();
			if (temp < coldest) {
				coldest = temp;
			}
		}
		
		double pow = (double) coldest/5;
		return 900 + (200 * Math.pow(0.7, pow));
	}
	
		
	@Override
	/**
	 * @Return the type of Truck in a string
	 */
	public String getType() {
		return "Refrigerated";
	}
	
	/**
	 * Removes stock from the truck
	 * @param stockToRemove  stock to remove from the truck
	 * @throws StockException 
	 */
	public void removeStock(Stock stockToRemove) throws StockException {
		getStock().removeStock(stockToRemove);
	}
	
	/**
	 * @return the current remaining capacity of the truck
	 */
	public int getRemainingCapacity() {
		return getCapacity() - getStock().getTotalQuantity();
		
	}
	
	@Override
	/**
	 * Generates a manifest.csv's content
	 * @return a manifest in string format to be exported to a csv
	 */
	public String getManifest() throws StockException {
		String manifest = ">Refrigerated\n";
		for (Item item : getStock().toSet()) {
			manifest += item.getItemName() + ','  +  getStock().getQuantity(item)+'\n';
		}
		return manifest;
	}
	
}
