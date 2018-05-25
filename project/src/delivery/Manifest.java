package delivery;

import java.util.ArrayList;
import java.util.Collection;

import stock.Item;
import stock.Stock;

public class Manifest {

//	/ArrayList<Truck> trucks;
	private Stock storeStock;
	private Stock reducingStock;
	//private Stock ;
	
	ArrayList<Truck> trucks;
	
	public Manifest(Stock storeStock) {
		this.storeStock = storeStock;
		this.reducingStock = storeStock;
		trucks = new ArrayList<>();
		populateTruck();
	}
	
	private void populateTruck() {
		
		Item start = FindColdestItem();
		Stock thisTruck = new Stock();
		Truck newTruck  = null;
		if (start.getItemTemperature() < 1000) {
			newTruck = new RefrigeratedTruck();
		} else {
			newTruck = new OrdinaryTruck(thisTruck);
		}
		
		if (getRemainingStock() < newTruck.getRemainingCapicity()) {
			
		}
		
		
	}

	private Item FindColdestItem() {
		int lowestTemp = 1000;
		Item coldItem = null;
		for (Item item : reducingStock.toSet()){
			if (item.getItemTemperature() < lowestTemp) {
				lowestTemp = item.getItemTemperature();
				coldItem = item;
			}
		}
		return coldItem;
	}

	public ArrayList<String[]> reStock() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getTotalCost() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
