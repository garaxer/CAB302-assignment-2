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
	
	private int capacity = 1000;
	private Stock stock = new Stock();
	private String type = "Ordinary";

	public OrdinaryTruck(int capacity, Stock stock) throws DeliveryException{
		this.capacity = capacity;
		checkStockForColdItems(stock);
	}
	
	public OrdinaryTruck(Stock stock) throws DeliveryException {
		checkStockForColdItems(stock);	
	}
	
	public Stock getStock() {
		return stock;
	}

	@Override
	public double getCost() {
		double cost = 750 + 0.25*stock.getTotalQuantity();
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
		for (Item item : stock.toSet()) {
			output += item.getItemName() + ",";
			output += Integer.toString(stock.getQuantity(item)) + "\n";
		}
		return output;
	}

	@Override
	public void addStock(Stock stock) throws DeliveryException {
		Set<Item> allItems = stock.toSet();
		for (Item item : allItems) {
			if (item.getItemTemperature() < 0) {
				throw new DeliveryException("This truck cannot carry refrigerated goods");
			} else {
				int currentQuantity = this.stock.getTotalQuantity();
				int newQuantity = stock.getTotalQuantity();		
				System.out.println("This is the quantity:" + (currentQuantity+newQuantity) + "This is the cap:" + this.capacity);
				if ((currentQuantity + newQuantity) > this.capacity) {
					throw new DeliveryException("There isn't enough room on the truck");
				} else {
					this.stock.addStock(stock);	
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
				this.stock.addStock(stock);	
			}
		}
	}


}
