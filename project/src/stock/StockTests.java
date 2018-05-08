package stock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * 
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
	Item item = new item(name,cost,price,reorderPoint,reorderAmount);
	String nameTwo = "potato";
	Item item2 = new item(nameTwo,cost,price,reorderPoint,reorderAmount);
	
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
	@Test public void addAnItem() {
		stock.addItem(item);
	}
	
	/* Test 3: Associating a quantity to an item 
	 */
	@Test 
	public void addAQuantity() {
		stock.addItem(item,500);
	}
	
	/* Test 4: Get an item's amount from the list
	 */
	@Test 
	public void getItemQuantity() throws StockException {
		String name = "rice";
		stock = new Stock();
		stock.addItem(item);
		assertEquals(0, stock.getQuantity(name));
	}
	
	/* Test 5: Remove a quantity from an item
	 */
	@Test 
	public void removeItemQuantity() throws StockException {
		stock.addItem(item,100);
		stock.removeItemQuantity(item,100);
	}
	
	/* Test 5: Error if no item exists
	 */
	@Test(expected = StockException.class)
	public void removeBadItem() throws StockException {
		stock.addItem(item,100);
		stock.removeItemQuantity(item2,100);
	}
		
	/* Test 5: Error if item Quantity is below 0
	 */
	@Test(expected = StockException.class)
	public void belowZero() throws StockException {
		stock.addItem(item,100);
		stock.removeItem(item,200);
	}
	
	/* Test 6: Test adding the same Item twice
	 */
	@Test public void getItemStockTwice() throws StockException  {
		stock.addItem(item,100);
		stock.addItem(item,100);
		assertEquals(200, stock.getQuantity(name));
	}
	
	
	/* Test 7: Get printable list of items
	 */
	@Test public void getList() throws StockException {
		String listing = "rice, 100\n" +
				"potato, 200\n";
		stock.addItem(item,100);
		stock.addItem(item2,200);
		assertEquals(listing,stock.getList());
	}
	
	/* Test 8: Adding another Stock 
	 * (Used in the process of loading a manifest)
	 */
	@Test public void addStock() throws StockException {
		Stock stock2 = new Stock();
		stock2.addItem(item,100);
		stock.addItem(item,100);
		stock.addStock(stock2);
		assertEquals(200,stock.getQuantity("rice"));
	}
	
	
	/* Test 9: Test removing stock
	 *  (Load in Sales log)
	 */
	@Test public void removeStock() throws StockException {
		Stock stock2 = new Stock();
		stock2.addItem(item,100);
		stock.addItem(item,100);
		stock.removeStock(stock2);
		assertEquals(0,stock.getQuantity("rice"));
	}
	
	/* Test 10: Test removing more inventory that exists
	 * (sells more item in stock)
	 */
	@Test(expected = StockException.class)
	public void removeStock() throws StockException {
		Stock stock2 = new Stock();
		stock2.addItem(item,200);
		stock.addItem(item,100);
		stock.removeStock(stock2);
	}
}
