package delivery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 *  A manifest Class.
 *  Holds a list of trucks. Used to calculate the cost of restocking
 * @author Gary Bagnall
 *
 */
public class Manifest {

	private Stock storeStock;
	private Stock reorder; //Final stock to reorder
	private Stock reducingStock; //Stock to add to truckls
	ArrayList<Truck> trucks;
	
	public Manifest(Stock storeStock) {
		this.storeStock = storeStock;
		trucks = new ArrayList<>();
		populateTrucks();
	}
	
	private void populateTrucks()  {
		//first get everything we have to reorder. Then sort through and place them into trucks

		//Get the amounts that need to be reordered
		reorder = new Stock();
		reducingStock = new Stock();
		List<Item> sortedItem  =new ArrayList<Item>();
		for (Item itemToOrder : storeStock.toSet()){
			int quantity = storeStock.getQuantity(itemToOrder);
			if (quantity <= itemToOrder.getItemReorderPoint()) {
				reorder.addItems(itemToOrder, itemToOrder.getItemReorderAmount());
				reducingStock.addItems(itemToOrder, itemToOrder.getItemReorderAmount());
				sortedItem.add(itemToOrder);
			}
		}
		
		sortedItem.sort((Item o1, Item o2)-> (compareTo(o1,o2))); //A list of sorted item by their temp Desc
		int i = 0;
		int leftOver = 0; 
		//while (reducingStock.getTotalQuantity() > 0) {
		while (i < sortedItem.size()-1) {
			Item start = sortedItem.get(i);
			Truck newTruck;
			boolean cold = false;
			if (start.getItemTemperature() < 1000) {
				newTruck = new RefrigeratedTruck();
				cold = true;
			} else {
				newTruck = new OrdinaryTruck();
			}
			
			Stock truckStock = new Stock();
			//Fill a truck Stock
			int remainingCapacity = newTruck.getRemainingCapacity();
			while (remainingCapacity > 0) {
				start = sortedItem.get(i);
				//Don't add cold item to ordinary Truck
				if (start.getItemTemperature() < 1000 && cold == false) {
					remainingCapacity = 0;
				}
				int itemQuantity = start.getItemReorderAmount();
				if (leftOver > 0) {
					itemQuantity = leftOver;
					leftOver=0;
				}
				//if there is room add it.	
				//System.out.println("remainingCapacity"+remainingCapacity);
				//System.out.println("itemQuantity"+itemQuantity);
				if (remainingCapacity >= itemQuantity) {
					truckStock.addItems(start, itemQuantity);
					remainingCapacity-=itemQuantity;
					i++;

				} else {
					truckStock.addItems(start, remainingCapacity);
					leftOver = itemQuantity - remainingCapacity;
					remainingCapacity = 0;
				}
			}
			try {
				if (truckStock.getTotalQuantity() > 0) {
					if (cold){
						newTruck = new RefrigeratedTruck(truckStock);
	
					} else {
						newTruck = new OrdinaryTruck(truckStock);
	
					}
					trucks.add(newTruck);
				}
				
				//newTruck.addStock(truckStock);
			} catch (DeliveryException e) {
				e.printStackTrace();
			}
			
		}
	}

	 private int compareTo(Item o, Item o2) {
		    return Integer.compare(o.getItemTemperature(), o2.getItemTemperature());
	}
	 
	 //not used, replaced with List
	private Item FindColdestItem() {
		int lowestTemp = 1000;
		Item coldItem = null;
		for (Item item : storeStock.toSet()){
			if (item.getItemTemperature() < lowestTemp && storeStock.getQuantity(item) > 0) {
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
	public Stock getReorderStock() {
		return reorder;
	}

	public String getStockString() {
		// TODO Auto-generated method stub
		String list ="";
		for (Truck truck : trucks) {
			try {
				list += truck.getManifest();
			} catch (StockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
}
