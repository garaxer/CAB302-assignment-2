package delivery;

import stock.Item;
import stock.Stock;

/**
 * 
 * @author Gary Bagnall
 *
 */
public class RefrigeratedTruck extends Truck {
	
	public RefrigeratedTruck(Stock stock) throws DeliveryException {
		super(800,stock);
	}
		
	@Override
	public double getCost() {
		int quantity = stock.getTotalQuantity();
		int coldest;
		for (Item item : stock) {
			int temp = item.getItemTemperature();
			if (temp != 0 && temp < coldest) {
				coldest = temp;
			}
		}
		return 900 + (200 * Math.pow(0.7, coldest/5));
	}
	
	@Override
	public String getStock() {
		return this.stock;
	}
	
	@Override
	public String getType() {
		return "Refrigerated";
	}
	
	@Override
	public String getManifest() {
		String manifest = ">Refrigerated";
		for (Item item : stock) {
			manifest += item.getItemName() + ','  +  stock.getQuantity(item);
		}
		return manifest;
	}
	
}
