package GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JFrame;

import delivery.Manifest;
import stock.Item;
import stock.Stock;
import stock.StockException;



/**
 * 
 * @author Gary Bagnall
 *
 */
public class Store {

	public double capital = 100000;
	//private ArrayList<String[]> inventory; //change to Stock when created
	public String name;
	public Stock inventory;
	
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
	
	public static Store getInstance() {
		return StoreHolder.INSTANCE; 
	}
	
	public String getName() {
		return name;
	}
	
	public String getCapitalString() {
		return "$"+String.format("%,.2f", capital);
	}
	
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

	public void loadSales(HashMap<String, Integer> log) {
		// TODO Auto-generated method stub
		for (Entry<String, Integer> entry : log.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println(key + "=" + value);
		}
	}

	public String generateManifest() {
		// TODO Auto-generated method stub
		Manifest manifest = new Manifest(inventory);
		//inventory.addStock((manifest.reStock());
		//capital += manifest.getCapital();
		return manifest.getStockString();
	}

}
