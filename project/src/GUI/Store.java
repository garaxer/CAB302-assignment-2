package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import delivery.Manifest;
import stock.Item;
import stock.Stock;
import stock.StockException;



/**
 * A Store class that holds information regarding the current state of the store.
 * This is a singleton pattern class.
 * Only one instance of this class can be made.
 * @author Gary Bagnall
 */
public class Store {

	public double capital = 100000;
	public String name;
	public Stock inventory;
	
	/**
	 * Sets up the store with a blank stock
	 */
	protected Store() {
		this.inventory = new Stock();
	}
		
	/**
	* SingletonHolder is loaded on the first execution of
	* Singleton.getInstance() or the first access to
	* SingletonHolder.INSTANCE, not before.
	* Taken from CAB302 Lecture.
	*/
	private static class StoreHolder {
		private final static Store INSTANCE = new Store();
	}
	
	/**
	 * Gets the current instance of this class
	 * @return The same instance of store every time.
	 */
	public static Store getInstance() {
		return StoreHolder.INSTANCE; 
	}
	
	/**
	 * Get's the name of the store
	 * @return the name of the store
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCapitalString() {
		return "$"+String.format("%,.2f", capital);
	}
	
	/**
	 * 
	 * @param arrayList
	 * @throws StockException
	 */
	public void loadInventory(ArrayList<String[]> arrayList) throws StockException {
		for (String[] list : arrayList) {
			Item item = null;
			for (int j = 0; j < list.length; j++) {
				if (list.length == 6) {
					//create cold item
					item = new Item(list[0],Integer.parseInt(list[1]),Integer.parseInt(list[2]),Integer.parseInt(list[3]),Integer.parseInt(list[4]),Integer.parseInt(list[5]));
				} else {
					//create normal item
					item = new Item(list[0],Integer.parseInt(list[1]),Integer.parseInt(list[2]),Integer.parseInt(list[3]),Integer.parseInt(list[4]));
				}
			}
			inventory.addItems(item);
		}		
	}

	/**
	 * 
	 * @return
	 */
	public String[][] getInventoryArray() {

		ArrayList<String[]> inventoryList = inventory.getArrayList();
		
		String[][] data = new String[inventoryList.size()][7];	
		int i = 0;
		for (String[] alist : inventoryList) {
			for (int j = 0; j < alist.length; j++) {
				 data[i][j] = alist[j];
			}
			i++;
		}
		return data;
	}

	/**
	 * 
	 * @param log
	 * @throws StockException
	 */
	public void loadSales(HashMap<String, Integer> log) throws StockException {
		for (Entry<String, Integer> entry : log.entrySet()) {
		    String key = entry.getKey();
		    int value = entry.getValue();
		    for(Item item : inventory.toSet()) {
		    	if (item.getItemName().equals(key)) {
		    		inventory.removeItems(item, value);
		    		capital += item.getItemPrice()*value;
		    	}
		    }
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String generateManifest() {
		Manifest manifest = new Manifest(inventory);
		this.inventory.addStock(manifest.getReorderStock());
		capital -= manifest.getTotalCost();
		return manifest.getStockString();
	}

	public double getCapital() {
		return capital;
	}

	public Stock getStock() {
		return inventory;
	}

	public void addCapital(double i) {
		capital += i;
	}

	public void removeCapital(double i) {
		capital -= i;
	}

	public void addStock(Stock stock) {
		inventory.addStock(stock);
	}

}
