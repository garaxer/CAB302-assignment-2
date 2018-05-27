package delivery;

import stock.Stock;

/**
 * A mock truck to ensure the Truck class has the correct abstract classes
 * @author garybagnall
 *
 */
public class MockTruck extends Truck {
	
	public MockTruck(Stock stock) throws DeliveryException {
		super(5,stock);
	}
	
	public double getCost() {
		return 0.0;
	}

	public Stock getStock() {
		return null;
	}
	
	public String getType() {
		return null;
	}
	
	public String getManifest() {
		return null;
	}

	public void addStock(Stock stock) {
	}
	
	public void setStock(Stock stock) {
	}
	
	public int getRemainingCapacity() {
		return 0;
	}
}