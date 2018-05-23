package stock;

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
	
	public int getQuantity(String name) throws StockException{
		Set<Item> keyset = stock.keySet();
		int quantity = -1;
		for (Item key : keyset) {
			if (key.getItemName() == name) {
				quantity = stock.get(key);
			}
		}
		if (quantity < 0){
			throw new StockException();
		}
		return quantity;
	}
	
	public int getQuantity(Item item) throws StockException{
		Set<Item> keyset = stock.keySet();
		int quantity = -1;
		for (Item key : keyset) {
			if (key == item) {
				quantity = stock.get(key);
			}
		}
		if (quantity < 0) {
			throw new StockException();
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
			outputString += Integer.toString(stock.get(item));
			outputString += Integer.toString(item.getItemCost())+",";
			outputString += Integer.toString(item.getItemPrice())+",";
			outputString += Integer.toString(item.getItemReorderAmount())+",";
			outputString += Integer.toString(item.getItemReorderPoint())+",";
			int temp = item.getItemTemperature();
			if (temp == 0) {
				outputString += "N/A\n";
			} else {
				outputString += item.getItemTemperature()+"\n";
			}
			
		}
		return outputString;
	}

	public void addStock(Stock stock2) {
		Set<Item> keys = stock2.toSet();
		for (Item key : keys) {
			if (stock.containsKey(key)) {
				try {
					int newQuantity = stock.get(key) + stock2.getQuantity(key);
					stock.put(key, newQuantity);
				} catch (StockException e) {
					
				}
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

	public int getTotalQuantity() {
		Set<Item> keyset = stock.keySet();
		int totalQuantity = 0;
		for (Item key : keyset) {
			totalQuantity += stock.get(key);
		}
		return totalQuantity;
	}

	public void addItems(String[] details, int quantity) {
	}
}

	