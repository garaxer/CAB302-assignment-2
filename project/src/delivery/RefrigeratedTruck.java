package delivery;

import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 * 
 * @author Gary Bagnall
 *
 */
public class RefrigeratedTruck extends Truck {
	
	/*public RefrigeratedTruck(Stock stock) throws DeliveryException {
		super(800,stock);
	}*/
	
	
	private Stock stock;
	public RefrigeratedTruck() {
		
	}
	
	public void addStock(Stock stockToAdd) throws DeliveryException {
		int quantity = stockToAdd.getTotalQuantity();
		if (quantity > getCapacity()) {
			throw new DeliveryException("There isn't enough room on the truck");
		}
		this.stock = stockToAdd;
	}
	
	@Override
	public double getCost() {
		//int quantity = stock.getTotalQuantity();
		int coldest = 1000; //Arbitary number Max
		for (Item item : stock.toSet()) {
			int temp = item.getItemTemperature();
			if (temp != 0 && temp < coldest) {
				coldest = temp;
			}
		}
		return 900 + (200 * Math.pow(0.7, coldest/5));
	}
	
	@Override
	public Stock getStock() {
		return this.stock;
	}
	
	@Override
	public String getType() {
		return "Refrigerated";
	}
	
	public void removeStock(Stock stock) {
		// TODO Auto-generated method stub
		
	}
	
	public int getRemainingCapacity() {
		return getCapacity() - stock.getTotalQuantity();
		
	}
	
	@Override
	public String getManifest() throws StockException {
		String manifest = ">Refrigerated\n";
		for (Item item : stock.toSet()) {
			manifest += item.getItemName() + ','  +  stock.getQuantity(item)+'\n';
		}
		System.out.println(manifest);
		return manifest;
	}
	
}
