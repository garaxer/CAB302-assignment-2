package stock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stock.Item;
/**
 * 
 * @author Gary Bagnall
 *
 */
public class ItemTests {
	
	/* Test 0: Declaring Item object
	 */
	Item item;
	
	// Clear the item object before every test.
	@Before
	public void setUpItem() {
		item = null;
	}
	
	/* Test 1: Constructing an Item object without Temperature
	 * 
	 */
	@Test public void testConstruction() {
		String name = "rice";
		int cost = 2;
		int price = 3;
		int reorderPoint = 225;
		int reorderAmount = 300;
		item = new Item(name,cost,price,reorderPoint,reorderAmount);
	}
	
	/* Test 2: Constructing an Item object with Temperature
	 * 
	 */
	@Test public void testConstructionWithTemperature() {
		String name = "mushrooms";
		int cost = 2;
		int price = 4;
		int reorderPoint = 200;
		int reorderAmount = 325;
		int temperature = 10;
		item = new Item(name,cost,price,reorderPoint,reorderAmount,temperature);
	}
	
	/* Test 3: Get the Item Name
	 */
	@Test public void testName() {
		String name = "mushrooms";
		int cost = 2;
		int price = 4;
		int reorderPoint = 200;
		int reorderAmount = 325;
		int temperature = 10;
		item = new Item(name,cost,price,reorderPoint,reorderAmount,temperature);
		assertEquals(name, item.getItemName());
	}
	
	/* Test 4: Get the Item cost
	 */
	@Test public void testCost() {
		String name = "mushrooms";
		int cost = 2;
		int price = 4;
		int reorderPoint = 200;
		int reorderAmount = 325;
		int temperature = 10;
		item = new Item(name,cost,price,reorderPoint,reorderAmount,temperature);
		assertEquals(cost, item.getItemCost());
	}
	
	/* Test 5: Get the Item Price
	 */
	@Test public void testPrice() {
		String name = "mushrooms";
		int cost = 2;
		int price = 4;
		int reorderPoint = 200;
		int reorderAmount = 325;
		int temperature = 10;
		item = new Item(name,cost,price,reorderPoint,reorderAmount,temperature);
		assertEquals(price, item.getItemPrice());
	}
	
	/* Test 6: Get the Item reorderPoint
	 */
	@Test public void testReorderPoint() {
		String name = "mushrooms";
		int cost = 2;
		int price = 4;
		int reorderPoint = 200;
		int reorderAmount = 325;
		int temperature = 10;
		item = new Item(name,cost,price,reorderPoint,reorderAmount,temperature);
		assertEquals(reorderPoint, item.getItemReorderPoint());
	}
	
	/* Test 7: Get the Item reorderAmount
	 */
	@Test public void testReorderAmount() {
		String name = "mushrooms";
		int cost = 2;
		int price = 4;
		int reorderPoint = 200;
		int reorderAmount = 325;
		int temperature = 10;
		item = new Item(name,cost,price,reorderPoint,reorderAmount,temperature);
		assertEquals(reorderAmount, item.getItemReorderAmount());
	}
	
	/* Test 8: Get the Item Temperature
	 */
	@Test public void testTemperature() {
		String name = "mushrooms";
		int cost = 2;
		int price = 4;
		int reorderPoint = 200;
		int reorderAmount = 325;
		int temperature = 10;
		item = new Item(name,cost,price,reorderPoint,reorderAmount,temperature);
		assertEquals(temperature, item.getItemTemperature());
	}
	
}
