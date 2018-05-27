package stock;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Stock {
	
	public Map<Item, Integer> stock = new HashMap<Item, Integer>();
	
	public void addItems(Item item) throws StockException {
		if (stock.containsKey(item)) {
			throw new StockException();
		} else {
			int quantity = 0;
			stock.put(item, quantity);
		}
		
	}
	
	public void addItems(Item item, int quantity) {
		try {		
			int currentQuantity = stock.get(item);
			int newQuantity = currentQuantity + quantity;
			stock.put(item, newQuantity);
		} catch (Exception e) {
			stock.put(item,  quantity);
		}
	}
	
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

	public void removeItems(Item item, int i) throws StockException {
		try {
			int currentQuantity = stock.get(item);
			int newQuantity = currentQuantity - i;
			if (newQuantity < 0) {
				throw new StockException();
			} else {
				stock.put(item, newQuantity);
			}
		} catch (Exception e) {
			throw new StockException();
		}
				
	}


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

	public void removeStock(Stock stock2) throws StockException {
		Set<Item> keys = stock2.toSet();
		for (Item key : keys) {
			if (stock.containsKey(key)) {
				try {
					int newQuantity = stock.get(key) - stock2.getQuantity(key);
					if (newQuantity < 0) {
						throw new StockException("Not enough stock to remove specified amount");
					} else {
						stock.put(key, newQuantity);
					}					
				} catch (StockException e) {
					throw new StockException("Stock item being removed does not exist in current stock");
				}
			}
		}	
	}

	public Set<Item> toSet() {
		Set<Item> items= stock.keySet();
		return items;
	}
	
	public ArrayList<String[]> getArrayList() {
		ArrayList<String[]> stockString = new ArrayList<String[]>();
		Set<Item> allItems = stock.keySet();
		for (Item curr : allItems) {
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
			stockString.add(currentItem);
		}
		
		return stockString;
	}

	
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

	public void addItems(String[] details, int quantity) {
	}
}

	