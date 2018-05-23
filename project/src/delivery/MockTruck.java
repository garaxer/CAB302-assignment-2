package delivery;

import stock.Stock;

//Make sure Truck has the correct abstract methods
public class MockTruck extends Truck {
	
	public MockTruck(Stock stock) throws DeliveryException {
		super(5,stock);
	}
	
	public int getCapacity() {
		return 0;
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

	@Override
	public void addStock(Stock stock) {
		// TODO Auto-generated method stub
		
	}
}