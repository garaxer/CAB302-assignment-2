package delivery;

import java.awt.Window.Type;
import java.util.ArrayList;
import java.util.Set;

import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 * 
 * @author Alexandra Koppon
 *
 */

public class OrdinaryTruck extends Truck {
	
	//public int capacity = 1000;
	//private Stock stock = new Stock();
	private String type = "Ordinary";


	public OrdinaryTruck(Stock stock) throws DeliveryException {
		super(1000,stock);
		checkStockForColdItems(stock);	
	}
	
	public OrdinaryTruck() {
		super(1000);	
	}
	
	@Override
	public double getCost() {
		double cost = 750 + 0.25*getStock().getTotalQuantity();
		return cost;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getManifest() throws StockException {	
		String output = "";
		output += ">" + type + "\n";
		for (Item item : getStock().toSet()) {
			output += item.getItemName() + ",";
			output += Integer.toString(getStock().getQuantity(item)) + "\n";
		}
		return output;
	}

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

	@Override
	public int getRemainingCapacity() {
		return getCapacity() - getStock().getTotalQuantity();
	}
	


}
