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
			try {
				int quantity = storeStock.getQuantity(itemToOrder);
				if (quantity <= itemToOrder.getItemReorderPoint()) {
					reorder.addItems(itemToOrder, itemToOrder.getItemReorderAmount());
					reducingStock.addItems(itemToOrder, itemToOrder.getItemReorderAmount());
					sortedItem.add(itemToOrder);
				}
			} catch (StockException e) {
				e.printStackTrace();
			}
		//	System.out.println(itemToOrder.getItemName() +"  "+itemToOrder.getItemReorderAmount());
		}
		
	
		sortedItem.sort((Item o1, Item o2)-> (compareTo(o1,o2)));
		int i = 0;
		while (reducingStock.getTotalQuantity() > 0) {
			Item start = sortedItem.get(i);
			
			Truck newTruck;
			Stock truckStock;
			if (start.getItemTemperature() < 1000) {
				newTruck = new RefrigeratedTruck();
			} else {
				newTruck = new OrdinaryTruck();
			}
			int capacity = newTruck.getRemainingCapacity();
			
			
			//int capacity = newTruck.g
					
			/*while (newTruck.getRemainingCapacity() > 0) {
				newTruck.addStock(stock);
			}*/
			
		}
		
		/*
		int leftOverAmount = 0;
	
		
		for (int i = 0; i < sortedItem.size(); i++) {
			
			Item nextItem = sortedItem.get(i);
	
			System.out.println(nextItem.getItemName());
	
			Truck newTruck;
			Stock thisTruck = new Stock();
			if (nextItem.getItemTemperature() < 1000) {
				newTruck = new RefrigeratedTruck();
			} else {
				newTruck = new OrdinaryTruck();
			}
			
			while
			
			if (nextItem.getItemReorderAmount() < newTruck.getRemainingCapacity() ) {
				
			}
			
			
			
			
			trucks.add(newTruck);
		}
		*/
		
		
		
		
		//sortedItem = new TreeSet<Item>(compareTo());
		
		//Comparator<Item> items = (Item o1, Item o2) -> (compareTo(o1,o2));
		//Set<String> ts = new TreeSet<>((Item o1, Item o2) -> (compareTo(o1,o2));

		
		//check if reducingStock is not null ie don't bother ordering nuthin.		
		/*
		while (reducingStock.getTotalQuantity() > 0) {
			System.out.println(reducingStock.getTotalQuantity());

			boolean cold = false;
			Item start = FindColdestItem(); //find the coldestItem	
		
			Truck newTruck;
			if (start.getItemTemperature() < 1000) {
				newTruck = new RefrigeratedTruck();
			} else {
				newTruck = new OrdinaryTruck();
			}
			
			Stock thisTruck = new Stock();
			thisTruck.addItems(start,start.getItemReorderAmount());
			
			try {
				reducingStock.removeStock(thisTruck);
			} catch (StockException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(reducingStock.getTotalQuantity());
			trucks.add(newTruck);
		}*/

	}

	 private int compareTo(Item o, Item o2) {
		    return Integer.compare(o.getItemTemperature(), o2.getItemTemperature());
	}

	private Item FindColdestItem() {
		int lowestTemp = 1000;
		Item coldItem = null;
		for (Item item : storeStock.toSet()){
			try {
				if (item.getItemTemperature() < lowestTemp && storeStock.getQuantity(item) > 0) {
					lowestTemp = item.getItemTemperature();
					coldItem = item;
				}
			} catch (StockException e) {
				e.printStackTrace();
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
	
}
