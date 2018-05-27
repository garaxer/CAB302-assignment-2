package delivery;

import java.util.ArrayList;
import java.util.List;


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
	private Stock reducingStock; //Stock to add to trucks
	ArrayList<Truck> trucks;
	
	/**
	 * Creates an lists of trucks and populates them with the items that need to be reordered.
	 * @param storeStock the stock to generate a manifest from
	 */
	public Manifest(Stock storeStock) {
		this.storeStock = storeStock;
		trucks = new ArrayList<>();
		populateTrucks();
	}
	
	/**
	 * Populate the trucks
	 * First get everything that need to reorder. Then place items with their reorder amount into trucks
	 */
	private void populateTrucks()  {
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

		while (i < sortedItem.size()) {
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
				if (remainingCapacity >= itemQuantity) {
					truckStock.addItems(start, itemQuantity);
					remainingCapacity-=itemQuantity;
					i++;
					if (i == sortedItem.size()) { //no more items
						remainingCapacity = 0;
					}
				} else {
					truckStock.addItems(start, remainingCapacity);
					leftOver = itemQuantity - remainingCapacity;
					remainingCapacity = 0;
				}
			}
			//place the stock into truck
			try {
				if (cold){
					newTruck = new RefrigeratedTruck(truckStock);

				} else {
					newTruck = new OrdinaryTruck(truckStock);
				}
				trucks.add(newTruck);
			} catch (DeliveryException e) {
				e.printStackTrace();
				
			}
		}
	}

	/**
	 * A comparator that finds the smallest Item's temperature
	 * @param o an Item
	 * @param o2 an Item
	 * @return the comparison between two items
	 */
	private int compareTo(Item o, Item o2) {
		return Integer.compare(o.getItemTemperature(), o2.getItemTemperature());
	}
	
	/**
	 * Get the cost of the trucks and the items within them.
	 * @return the total cost of the trucks and the items within them
	 */
	public double getTotalCost() {
		double cost = 0;
		double itemcost = 0;
		for (Truck truck : trucks) {
			cost += truck.getCost();//cost of Trucks
			for(Item item : truck.getStock().toSet()) {
				itemcost += truck.getStock().getQuantity(item)*item.getItemCost();
			}	
		}
		return cost+itemcost;
	}
	
	/**
	 * @return the item's to reorder
	 */
	public Stock getReorderStock() {
		return reorder;
	}

	/**
	 * @return A string of trucks and their items
	 */
	public String getStockString() {
		String list ="";
		for (Truck truck : trucks) {
			try {
				list += truck.getManifest();
			} catch (StockException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
}
