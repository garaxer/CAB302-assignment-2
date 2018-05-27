package stock;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A Stock object is a HashMap of items and their quantities. The items serve as keys, with their 
 * respective quantities being their associated values. Stocks can be added together, Items can be 
 * added or removed from Stock, and the stock can be returned in various formats to the caller. 
 * @author Alex Koppon
 *
 */

public class Stock {
	
	// Create the Stock object
	public Map<Item, Integer> stock = new HashMap<Item, Integer>();
	
	/**
	 * Adds the passed item to the stock with a quantity of 0
	 * @param item
	 * @throws StockException if the item already exists in stock
	 */
	public void addItems(Item item) throws StockException {
		if (stock.containsKey(item)) {
			throw new StockException();
		} else {
			int quantity = 0;
			stock.put(item, quantity);
		}
		
	}
	
	/**
	 * Adds the passed item to the stock with the quantity given. If the item 
	 * already exists, then the quantity given is added to the current quantity 
	 * of the item. 
	 * @param item
	 * @param quantity
	 */
	public void addItems(Item item, int quantity) {
		try {		
			int currentQuantity = stock.get(item);
			int newQuantity = currentQuantity + quantity;
			stock.put(item, newQuantity);
		} catch (Exception e) {
			stock.put(item,  quantity);
		}
	}
	
	/**
	 * @param name
	 * @return the int value of the quantity of an item using
	 * the itemName. If the item is not present 0 is returned. 
	 */
	public int getQuantity(String name) {
		Set<Item> keyset = stock.keySet();
		int quantity = 0;
		for (Item key : keyset) {
			if (key.getItemName() == name) {
				quantity = stock.get(key);
			}
		}
		return quantity;
	}
	
	/**
	 * @param item
	 * @return the int value of the quantity of an item using
	 * the Item. If the item is not present 0 is returned. 
	 */
	public int getQuantity(Item item) {
		Set<Item> keyset = stock.keySet();
		int quantity = 0;
		for (Item key : keyset) {
			if (key == item) {
				quantity = stock.get(key);
			}
		}
		return quantity;
	}

	/**
	 * Removes the given quantity of an item from stock
	 * @param item
	 * @param quantity
	 * @throws StockException if the quantity of the item being removed is greater
	 * than the amount contained in stock, or the item doesn't exist in stock
	 */
	public void removeItems(Item item, int quantity) throws StockException {
		try {
			int currentQuantity = stock.get(item);
			int newQuantity = currentQuantity - quantity;
			if (newQuantity < 0) {
				throw new StockException("The quantity of the item in stock is too low to remove that amount");
			} else {
				stock.put(item, newQuantity);
			}
		} catch (StockException e){
			throw new StockException("The quantity of the item in stock is too low to remove that amount");
		}catch (Exception e) {
			throw new StockException("This item doesn't exist in the stock");
		}

	}

	/**
	 * @return a string of the items contained in stock. Each row in an item and its properties
	 * E.g. "itemName,itemCost,itemPrice,itemReorderPoint,itemReorderAmount,[itemTemperate,N/A]\n"
	 */
	public String getList() {
		String outputString = "";
		Set<Item> items = stock.keySet();
		for (Item item : items) {
			outputString += item.getItemName()+",";
			outputString += Integer.toString(stock.get(item))+",";
			outputString += Integer.toString(item.getItemCost())+",";
			outputString += Integer.toString(item.getItemPrice())+",";
			outputString += Integer.toString(item.getItemReorderPoint())+",";
			outputString += Integer.toString(item.getItemReorderAmount())+",";
			int temp = item.getItemTemperature();
			if (temp == 1000) {
				outputString += "N/A\n";
			} else {
				outputString += Integer.toString(item.getItemTemperature())+"\n";
			}					
		}
		return outputString;
	}

	/**
	 * Adds the items in the passed stock2 to the current stock. 
	 * In the case of no such item existing, a new item is made with the 
	 * given quantity, and if an item already exists, the quantity of the item 
	 * from stock2 is added to its quantity in the existing stock. 
	 * @param stock2
	 */
	public void addStock(Stock stock2) {
		Set<Item> keys = stock2.toSet();
		for (Item key : keys) {
			if (stock.containsKey(key)) {
				int newQuantity = stock.get(key) + stock2.getQuantity(key);
				stock.put(key, newQuantity);
			} else {
				int quantity = stock2.getQuantity(key);
				stock.put(key, quantity);
			}
		}		
	}

	/**
	 * Removes the items in the passed stock2 from stock
	 * @param stock2
	 * @throws StockException if there is no such item to remove from the stock
	 * or the quantity of the item is lower than the quantity being removed or 
	 * the item doesn't exist in stock
	 */
	public void removeStock(Stock stock2) throws StockException {
		Set<Item> keys = stock2.toSet();
		for (Item key : keys) {
			if (stock.containsKey(key)) {
				try {
					int newQuantity = stock.get(key) - stock2.getQuantity(key);
					if (newQuantity < 0) {
						throw new StockException("The quantity of the item in stock is too low to remove that amount");
					} else {
						stock.put(key, newQuantity);
					}					
				} catch (StockException e) {
					throw new StockException("The item being removed does not exist in the stock");
				}
			}
		}	
	}

	/**
	 * @return a Set<Item> to assist in iterating through a stock array
	 */
	public Set<Item> toSet() {
		Set<Item> items= stock.keySet();
		return items;
	}
	
	/**
	 * @return an ArrayList<String[]> where each String[] is an item and its
	 * properties
	 */
	public ArrayList<String[]> getArrayList() {
		ArrayList<String[]> stockString = new ArrayList<String[]>();
		Set<Item> allItems = stock.keySet();
		for (Item curr : allItems) {
			// Generate the String[] and populate it with the item's properties
			String[] currentItem = new String[7];
			currentItem[0] = curr.getItemName();
			currentItem[1] = Integer.toString(stock.get(curr));
			currentItem[2] = Integer.toString(curr.getItemCost());
			currentItem[3] = Integer.toString(curr.getItemPrice());
			currentItem[4] = Integer.toString(curr.getItemReorderPoint());
			currentItem[5] = Integer.toString(curr.getItemReorderAmount());
			int temp = curr.getItemTemperature();
			if (temp == 1000) {
				currentItem[6] = "N/A";
			} else {
				currentItem[6] = Integer.toString(curr.getItemTemperature());			
			}
			// Add the String[] of the item to the ArrayList
			stockString.add(currentItem);
		}		
		return stockString;
	}

	/**
	 * @return the total quantity of all items in the stock as an int
	 */
	public int getTotalQuantity() {
		Set<Item> keyset = stock.keySet();
		int totalQuantity = 0;
		for (Item key : keyset) {
			totalQuantity += stock.get(key);
		}
		return totalQuantity;
	}
	
	public Item getItem(String itemString) {
		Item item = null;
		Set<Item> allItems = stock.keySet();
		for (Item curr : allItems) {
			if (curr.getItemName().equals(itemString)) {
				item = curr;
			}
		}
		return item;
	}

}

	