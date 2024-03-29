		package stock;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
/**
 * Unit tests for the Stock Class
 * @author Gary Bagnall
 *
 */
public class StockTests {

	/* Setup The item for testing
	 */
	String name = "rice";
	int cost = 2;
	int price = 3;
	int reorderPoint = 225;
	int reorderAmount = 300;
	Item item = new Item(name,cost,price,reorderPoint,reorderAmount);
	String nameTwo = "ice cream";
	int temperature = -10;
	Item item2 = new Item(nameTwo,cost,price,reorderPoint,reorderAmount,temperature);
	
	/* Test 0: Declaring Stock objects
	 */
	Stock stock;
	
	/* Test 1: Constructing a Stock object
	 */
	@Before @Test public void setUpStockList() {
		stock = new Stock();
	}
	
	
	/* Test 2: Adding a new item to the list
	 */
	@Test public void addAnItem() throws StockException {
		stock.addItems(item);
	}
	
	/*  Adding a new item to the list that is already there
	 */
	@Test(expected = StockException.class)
	public void addAnItemExists() throws StockException {
		stock.addItems(item);
		stock.addItems(item);
	}
	
	/* Test 3: Associating a quantity to an item. Adds the quantity to the current quantity or creates a new item with that quantity
	 */
	@Test 
	public void addAQuantity() {
		stock.addItems(item,500);
	}
	
	/* Test 4: Get an item's amount from the list
	 */
	@Test 
	public void getItemQuantityByString() throws StockException {
		String name = "rice";
		stock = new Stock();
		stock.addItems(item);
		assertEquals(0, stock.getQuantity(name));
	}
	
	/* Test 4.5: Get an item's amount from the list
	 */
	@Test 
	public void getItemQuantity() throws StockException {
		stock = new Stock();
		stock.addItems(item,100);
		assertEquals(100, stock.getQuantity(item));
	}
	
	/* Test 5: Remove a quantity from an item
	 */
	@Test 
	public void removeItemQuantity() throws StockException {
		stock.addItems(item,100);
		stock.removeItems(item,100);
	}
	
	/* Test 6: Error if no item exists
	 */
	@Test(expected = StockException.class)
	public void removeBadItem() throws StockException {
		stock.addItems(item,100);
		stock.removeItems(item2,100);
	}
		
	/* Test 7: Error if item Quantity is below 0
	 */
	@Test(expected = StockException.class)
	public void belowZero() throws StockException {
		stock.addItems(item,100);
		stock.removeItems(item,200);
	}
	
	/* Test 6: Test adding the same Item twice
	 */
	@Test public void getItemStockTwice() throws StockException  {
		stock.addItems(item,100);
		stock.addItems(item,100);
		assertEquals(200, stock.getQuantity(name));
	}
	
	
	/* Test 7: Get printable list of items
	 */
	@Test public void getList() throws StockException {
		String listing = "rice,100,2,3,225,300,N/A\n" +
				"ice cream,200,2,3,225,300,-10\n";
		String listing2 = "ice cream,200,2,3,225,300,-10\n" +
				"rice,100,2,3,225,300,N/A\n" ;
		stock.addItems(item,100);
		stock.addItems(item2,200);
		assertTrue(listing.equals(stock.getList()) || listing2.equals(stock.getList()));
	}
	
	/* Test 8: Adding another Stock 
	 * (Used in the process of loading a manifest)
	 */
	@Test public void addStock() throws StockException {
		Stock stock2 = new Stock();
		stock2.addItems(item,100);
		stock.addItems(item,100);
		stock.addStock(stock2);
		assertEquals(200,stock.getQuantity("rice"));
	}
	
	
	/* Test 9: Test removing stock
	 *  (Load in Sales log)
	 */
	@Test public void removeStock() throws StockException {
		Stock stock2 = new Stock();
		stock2.addItems(item,100);
		stock.addItems(item,100);
		stock.removeStock(stock2);
		assertEquals(0,stock.getQuantity("rice"));
	}
	
	/* Test 10: Test removing more inventory that exists
	 * (sells more item in stock)
	 */
	@Test(expected = StockException.class)
	public void removeMoreStock() throws StockException {
		Stock stock2 = new Stock();
		stock2.addItems(item,200);
		stock.addItems(item,100);
		stock.removeStock(stock2);
	}
	
	
	/*
	 * Test 12 get an iterable list of items
	 */
	@Test
	public void itemIteration() throws StockException {
		ArrayList<Item> valuesProduced = new ArrayList<Item>();
		Object[] values;
		stock.addItems(item,100);
		stock.addItems(item2,200);
		for (Item item : stock.toSet()) {
			valuesProduced.add(item);
		}
		values = valuesProduced.toArray();
		assertTrue(item == values[0] || item == values[1]);
	}
	
	/*
	 * Test 13 Get the total quantity
	 */
	@Test
	public void testGetTotalQuantity() throws StockException {
		stock.addItems(item,500);
		assertEquals(500, stock.getTotalQuantity());
	}
	
	/*
	 * Want an array list needed for GUI, includes quantity
	 */
	@Test
	public void getArrayList() throws StockException {
		ArrayList<String[]> inventory = new ArrayList<String[]>();
		String[] theItem = {"rice","500","2","3","225","300","N/A"};
		inventory.add(theItem);
		stock.addItems(item,500);
		ArrayList<String[]> storeInventory = stock.getArrayList();
		String[] storeItem =  storeInventory.get(0);
		String[] testItem =  inventory.get(0);
		assertTrue(storeItem[0].equals(testItem[0]) );
	}
}
