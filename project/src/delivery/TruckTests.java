package delivery;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import stock.Item;
import stock.Stock;

/**
 * 
 * @author Gary Bagnall
 *
 */
public class TruckTests {
	
	Truck truck;
	Item item;
	Stock stock;
	
	@Before
	public void setUpTruck() {
		int capacity = 800;
		item = new Item("Test",50,100,5,25);
		stock = new Stock();
		stock.addItems(item,500);
		truck = new Truck(capacity, stock);
	}

	// test 0
	@Test
	public void getTruckCapacity() {
		int capacity = 800;
		assertEquals(capacity, truck.getCapacity());
	}
	
	@Test
	public void testAbstractMethods() {
		Stock mockStock = null;
		MockTruck mock = new MockTruck(mockStock);
		
		assertEquals(0,mock.getCapacity());
		assertEquals(0,mock.getCost());
		assertEquals(null,mock.getType());
		assertEquals(null,mock.getManifest());
	}
}

//Make sure Truck has the correct abstract methods
public class MockTruck extends Truck {
	
	public MockTruck(Stock stock) {
		super(5,stock);
	}
	
	public int getCapacity() {
		return 0;
	}
	
	public int getCost() {
		return 0;
	}
	
	public String getType() {
		return null;
	}
	
	public String getManifest() {
		return null;
	}
}